package com.kia.career.service;

import com.kia.career.domain.CacheData;
import com.kia.career.domain.ResultResponse;
import com.kia.career.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class ApiService {

    @Autowired
    WebCrawlingService webCrawlingService;

    @Autowired
    CacheService cacheService;

    @Value("${cache.key}")
    private String CacheKey;

    public ResultResponse ProcessString(){

        //1.Get Html Data
        String htmlData = getHtmlData();

        //2.특수문자 제거
        String baseData = StringUtils.OnlyAlphaAndNumber(htmlData);

        //3.String to Array
        List<String> list = new ArrayList<>();
        list = Arrays.asList(baseData.split(""));

        //4.중복제거
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        for(String item : list){
            linkedHashSet.add(item);
        }

        //5.중복제거 후 1차 오름차순 소트 ( 대소문자 정렬 후 + 숫자 )
        List<String> atomicList = new ArrayList<String>();
        atomicList.addAll(linkedHashSet);
        System.out.println("중복제거         : " + linkedHashSet);
        atomicList.sort(Comp);

        System.out.println("1차 오름차순 정렬 : " + atomicList);
        //6.리스트에서 숫자 분리
        List<String> numberArray = new ArrayList<>();
        //atomiclist에서 숫자 분리
        for(String str : atomicList){
            if(str.charAt(0) >= 48 && str.charAt(0) <= 57){
                numberArray.add(str);
            }
        }

        //7.최종 정렬
        int numberIndex = 0;
        int numberMaxIndex = numberArray.size();
        List<String> ResultArray = new ArrayList<String>();

        //첫 문자열 생성
        ResultArray.add(atomicList.get(0));

        //숫자 재배치
        for(int i = 1; i < atomicList.size() - numberMaxIndex ; i++){

            // 마지막 순서가 아니고 내 문자가 다음 문자보다 작으면 // i < atomiclist.size() - numbermaxindex -1 &&
            if(!atomicList.get(i).toLowerCase().equals(atomicList.get(i-1).toLowerCase())){
                if(numberIndex < numberMaxIndex) {
                    ResultArray.add(numberArray.get(numberIndex));
                }
                numberIndex++;
            }
            ResultArray.add(atomicList.get(i));
        }

        //8.남은 숫자 재배치
        if(numberIndex < numberMaxIndex)
        {
            for(int remainPoint = numberIndex ; remainPoint < numberMaxIndex ; remainPoint++){
                ResultArray.add(numberArray.get(remainPoint));
            }
        }

        //9.최종 문자열 생성
        StringBuilder finalResultStringBuilder = new StringBuilder();
        for(String str : ResultArray){
            finalResultStringBuilder.append(str);
        }

        return new ResultResponse("200", finalResultStringBuilder.toString());
    }

    public String getHtmlData() {

        CacheData checkCacheData = cacheService.getCacheData(CacheKey);

        if(cacheService.isEmpty(checkCacheData)) {
            //캐시 없데이트
            System.out.println("Html데이터 생성 후 Cache 등록");
            String htmlData = "";
            try {
                htmlData = AsyncWebCall();
            } catch (ExecutionException e) {
                htmlData = "";
            } catch (InterruptedException e) {
                htmlData = "";
            }
            checkCacheData = cacheService.updateCacheData(CacheKey, htmlData);
        }else{
            System.out.println("Cache 데이터의 Html 사용");
        }

        CacheData cacheData = cacheService.getCacheData(CacheKey);

        return cacheData.getValue();

    }

    public String AsyncWebCall() throws ExecutionException, InterruptedException {

        String result;

        //비동기 호출.
        CompletableFuture<String> completableFuture_shop = webCrawlingService.AsyncWebCrawling("https://shop.hyundai.com");
        CompletableFuture<String> completableFuture_kia  = webCrawlingService.AsyncWebCrawling("https://www.kia.com");
        CompletableFuture<String> completableFuture_gene = webCrawlingService.AsyncWebCrawling("https://www.genesis.com");

        //로깅 및 세부 설정
        completableFuture_shop
                .thenAccept(msg -> {
                    WriteLog("completableFuture_shop", true); })
                .exceptionally(error -> {
                    System.out.println(error.getMessage());
                    WriteLog("completableFuture_shop", false);
                    return null;
                });

        completableFuture_kia
                .thenAccept(msg -> {
                    WriteLog("completableFuture_kia", true); })
                .exceptionally(error -> {
                    System.out.println(error.getMessage());
                    WriteLog("completableFuture_kia", false);
                    return null;
                });

        completableFuture_gene
                .thenAccept(msg -> {
                    WriteLog("completableFuture_gene", true); })
                .exceptionally(error -> {
                    System.out.println(error.getMessage());
                    WriteLog("completableFuture_gene", false);
                    return null;
                });

        // CompletableFuture 컴바인 ( 모두 완료될때까지 블로킹, 각 스레드는 Timeout이 있어 블로킹 최대시간 보장됨. )
        CompletableFuture completableFuture = CompletableFuture.allOf(completableFuture_shop, completableFuture_kia, completableFuture_gene);

        //블로킹으로 모두 대기.
        completableFuture.join();

        //문자열 합치기
        //concat은 주소를 재계산 & null이  불가능 하기 때문에 사용 불가
        //StringBuilder로 문자열 합침.
        // completableFuture.join()으로 모든 블로킹을 기다렸기 때문에 get으로 진행해도 동시성 확보됨.
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(completableFuture_shop.get()).append(completableFuture_kia.get()).append(completableFuture_gene.get());

        return stringBuilder.toString();

    }

    public void WriteLog(String target, boolean isSuccess){
        System.out.println(String.format("%s call is %s", target, isSuccess ? "successed" : "failed"));
    }


    public Comparator<String> Comp = new Comparator<String>() {

        static String[] RefArr  = {"A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        @Override
        public int compare(String s1, String s2) {
            List<String> reference = new ArrayList<String>(Arrays.asList(RefArr));

            Integer i1 = reference.indexOf(s1);
            Integer i2 = reference.indexOf(s2);
            return i1.compareTo(i2);
        }
    };

}
