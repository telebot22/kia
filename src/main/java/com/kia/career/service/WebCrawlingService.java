package com.mcaniri.restapi.sample.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

@Service
public class WebCrawlingService {
    
    //Jsoup time out 설정
    @Value("${Jsoup.timeout}")
    private int timeOut;


    public String BasicCall(String url){

        String webInfo;

        try {
            Connection conn = Jsoup.connect(url);
            Document document = conn.get();

            Elements parsingDivs = document.getElementsByClass("parsingDiv"); // class가 parsingDiv인 element 찾기
            Element parsingDiv = parsingDivs.get(0);

            Element parsingTitle = parsingDiv.getElementById("parsingTitle"); // id가 parsingTitle인 element 찾기
            Element partsingContents = parsingDiv.getElementById("partsingContents"); // id가 parsingContents인 element 찾기

            String title = parsingTitle.getElementsByTag("h3").get(0).text(); // 첫 번째 h3태그의 text값 찾기
            String contents = partsingContents.getElementsByTag("p").get(0).text(); // 첫 번째 p태그의 text값 찾기

            webInfo = String.format("파싱한 제목: [%s] 파싱한 내용: [%s]", title, contents);


        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            webInfo = e.toString();
        }

        return webInfo;
    }


    //@Aync는 기본형이 Void이며,
    //리턴형을 받기 위해선 Future, ListenableFuture, CompletableFuture 타입을 리턴 타입으로 사용
    //메소드의 반환 형태 : new AsyncResult()
    //future.get() 은 블로킹을 통해 요청 결과가 올 때까지 기다림.
    //비동기 블로킹 방식이 되어버려 성능이 좋지 않고, 보통 Future는 사용하지 않음.
    @Async("threadPoolTaskExecutor")
    public Future<String> TestASyncCallReturn(int waitSecond) {

        String result = "Chk Value.";
        String threadName = Thread.currentThread().getName();
        System.out.println("TestASyncCallReturn Called. Execute method asynchronously - " + threadName);

        try {
            result = String.format("[TestASyncCallReturn] [%s] Thread. waitSecond : %d", threadName, waitSecond);
            Thread.sleep(waitSecond);
        } catch (InterruptedException e) {
            result = String.format("[TestASyncCallReturn] [%s] Thread. waitSecond : %d Error : [%s]", threadName, waitSecond, e.toString());
        }

        System.out.println("IN_METHOD ----" + result);

        return new AsyncResult<String>(result);
    }

    //[ListenableFuture]
    //ListenableFuture만으로도 논블로킹 로직을 구현할 수 있지만,
    // 콜백 안에 콜백이 필요할 경우 콜백 지옥이라고 불리는 매우 복잡한 코드를 유발하게 된다.
    // 대충 이런 코드임
    // ListenableFuture<String> listenableFuture_5 = webCrawlingService.TestASyncCallRFReturn(5000);
    // listenableFuture_5.addCallback(System.out::println, error -> System.out.println(error.getMessage()));
    // addCallback() 메소드의 첫 번째 파라미터는 작업 완료 콜백 메소드, 두 번째 파라미터는 작업 실패 콜백 메소드를 정의
    @Async("threadPoolTaskExecutor")
    public ListenableFuture<String> TestASyncCallLFReturn(int waitSecond) {

        String result = "Chk Value.";
        String threadName = Thread.currentThread().getName();
        System.out.println("TestASyncCallReturn Called. Execute method asynchronously - " + threadName);

        try {
            result = String.format("[TestASyncCallReturn] [%s] Thread. waitSecond : %d", threadName, waitSecond);
            Thread.sleep(waitSecond);
        } catch (InterruptedException e) {
            result = String.format("[TestASyncCallReturn] [%s] Thread. waitSecond : %d Error : [%s]", threadName, waitSecond, e.toString());
        }

        System.out.println("IN_METHOD ----" + result);

        return new AsyncResult<String>(result);
    }

    //[CompletableFuture]
    //논블로킹 기능을 완벽하게 수행하여
    //@Async를 사용할 때 리턴 값이 필요하다면 CompletableFuture를 사용할 것을 권장
    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> TestASyncCallCFReturn(int waitSecond) {

        String result = "Chk Value.";
        String threadName = Thread.currentThread().getName();
        System.out.println("TestASyncCallReturn Called. Execute method asynchronously - " + threadName);

        try {
            result = String.format("[TestASyncCallReturn] [%s] Thread. !! waitSecond : %d", threadName, waitSecond);
            Thread.sleep(waitSecond);
        } catch (InterruptedException e) {
            result = String.format("[TestASyncCallReturn] [%s] Thread. waitSecond : %d Error : [%s]", threadName, waitSecond, e.toString());
        }

        System.out.println("IN_METHOD Done ----" + result);

        return new AsyncResult<String>(result).completable();
    }

    @Async("threadPoolTaskExecutor")
    public CompletableFuture<String> aSyncJsoup(String url){
        System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());

        String webHtml = "";

        try {

            Connection conn = Jsoup.connect(url).timeout(timeOut);
            Document document = conn.get();  //단순 html 코드가 전체를 다 말하는 것인지 의아하긴 하다..

            webHtml = document.toString();

        } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
            webHtml = "";
        }

        return new AsyncResult<String>(webHtml).completable();

    }
}
