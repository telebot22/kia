package com.kia.career.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class StringController {
    static String[] RefArr  = {"A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    @RequestMapping(value = "/", method = { RequestMethod.GET, RequestMethod.POST })
    public String base(){

        return solution("<html>124<div>;;<ABCD>efgta.BleImg'1");


        //http://localhost:8080/
    }

    public String solution(String strInput) {
        String strReturn = "";

        //1. 특문제거
        strInput = strInput.replaceAll("[^a-zA-Z0-9]", "");

        // string을 list로 변경
        List<String> list = new ArrayList<String>();
        list = Arrays.asList(strInput.split(""));

        // 중복제거 (input 순서 유지)
        // LinkedHashSet add시 중복 비허용
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();

        for(String item : list){
            linkedHashSet.add(item);
            if(linkedHashSet.size()==(26+26+10)){
                break;
            }

        }

        System.out.println("문자열길이-"+linkedHashSet.size());

        // 중복제거 후 담을 리스트
        List<String> atomiclist = new ArrayList<String>();
        atomiclist.addAll(linkedHashSet);

        // 대소문자 구분하여 오름차순 (근데 숫자가 앞에오네..)
        // 여기를 custom 해야함 sort comparator
        atomiclist.sort(comp);

        List<String> numberarr = new ArrayList<>();
        //atomiclist에서 숫자 분리
        for(String str : atomiclist){
            if(str.charAt(0) >= 48 && str.charAt(0) <= 57){
                numberarr.add(str);
            }
        }
        System.out.print(numberarr);
        //기존리스트에서 숫자제거
        /*
        Iterator<String> itr = atomiclist.iterator();
        while(itr.hasNext()){
            String s = itr.next();
            if(Arrays.asList(numberarr).contains(s)){
                System.out.println(atomiclist.indexOf(s));
                break;
            }
        }
        */
        // 지금 문자가 다음 문자와 lowercase로 비교했을 때 같으면
        // 결과 문자열 (신규) 에 number를 하나 가지고 온다.
        // number의 cnt는 별도 관리
        int i;
        int numberindex = 0;
        int numbermaxindex = numberarr.size();
        List<String> finalResult = new ArrayList<String>();

        //첫 문자열 보장
        finalResult.add(atomiclist.get(0));

        for(i = 1; i < atomiclist.size() - numbermaxindex ; i++){

            System.out.println("---");
            System.out.println(atomiclist.get(i).toLowerCase());
            System.out.println(atomiclist.get(i-1).toLowerCase());
            System.out.println(atomiclist.get(i).toLowerCase().equals(atomiclist.get(i-1).toLowerCase()) );
            System.out.println("---");

            // 마지막 순서가 아니고 내 문자가 다음 문자보다 작으면 // i < atomiclist.size() - numbermaxindex -1 &&
            if(!atomiclist.get(i).toLowerCase().equals(atomiclist.get(i-1).toLowerCase())){
                System.out.print("79");
                if(numberindex < numbermaxindex) {
                    finalResult.add(numberarr.get(numberindex));
                    System.out.print("82");
                }
                    numberindex++;
            }
            finalResult.add(atomiclist.get(i));
        }
        //문자열 처리 후 숫자가 남아 있으면 숫자 add

        if (numberindex < numbermaxindex){
            for( numberindex; numberindex < numbermaxindex; numberindex++){

            }
        }

        StringBuilder a = new StringBuilder();
        for(String str : finalResult){
            a.append(str);
        }
        strReturn = a.toString();

        return strReturn;
    }


    // 익명 객체
    public static Comparator<String> comp = new Comparator<String>() {
        @Override
        public int compare(String s1, String s2) {
            List<String> reference = new ArrayList<String>(Arrays.asList(RefArr));

            Integer i1 = reference.indexOf(s1);
            Integer i2 = reference.indexOf(s2);
            return i1.compareTo(i2);
        }
    };
}

