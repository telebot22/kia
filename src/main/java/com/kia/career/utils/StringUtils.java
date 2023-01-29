package com.kia.career.utils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    public static String OnlyAlphaAndNumber(String msg){

        String newStr = "";
        String regExpAlphaAndNumber = "[^a-zA-Z0-9]";

        Pattern pattern = Pattern.compile(regExpAlphaAndNumber);
        Matcher matcher = pattern.matcher(msg);
        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            matcher.appendReplacement(buffer, "");
        }

        newStr = buffer.toString();

        return newStr;
    }

    public static LinkedHashSet<String> deduplication(List<String> list){

        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
        for(String item : list){
            linkedHashSet.add(item);
        }
        return  linkedHashSet;
    }
    public static List<String> AlphaNumericSort(LinkedHashSet<String> linkedHashSet){

        List<String> atomicList = new ArrayList<String>();
        atomicList.addAll(linkedHashSet);
        System.out.println("중복제거         : " + linkedHashSet);
        atomicList.sort(new Comparator<String>() {

            static String[] RefArr  = {"A", "a", "B", "b", "C", "c", "D", "d", "E", "e", "F", "f", "G", "g", "H", "h", "I", "i", "J", "j", "K", "k", "L", "l", "M", "m", "N", "n", "O", "o", "P", "p", "Q", "q", "R", "r", "S", "s", "T", "t", "U", "u", "V", "v", "W", "w", "X", "x", "Y", "y", "Z", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
            @Override
            public int compare(String s1, String s2) {
                List<String> reference = new ArrayList<String>(Arrays.asList(RefArr));

                Integer i1 = reference.indexOf(s1);
                Integer i2 = reference.indexOf(s2);
                return i1.compareTo(i2);
            }
        });
        return atomicList;
    }

    public static List<String> SeperateNumArray(List<String> list){
        List<String> numberArray = new ArrayList<String>();
        //list에서 숫자 분리
        for(String str : list){
            if(str.charAt(0) >= 48 && str.charAt(0) <= 57){
                numberArray.add(str);
            }
        }
        return numberArray;
    }

    public static List<String> finalSort(List<String> atomicList, List<String> numberArray){
        List<String> ResultArray = new ArrayList<String>();

        int numberIndex = 0;
        int numberMaxIndex = numberArray.size();

        //첫 문자열 생성
        ResultArray.add(atomicList.get(0));

        //숫자 재배치
        for(int i = 1; i < atomicList.size() - numberMaxIndex ; i++){

            // 내 문자가 이전 문자와 다르면 숫자 추가 대상임
            if(!atomicList.get(i).toLowerCase().equals(atomicList.get(i-1).toLowerCase())){
                if(numberIndex < numberMaxIndex) {
                    ResultArray.add(numberArray.get(numberIndex));
                }
                numberIndex++;
            }
            ResultArray.add(atomicList.get(i));
        }

        //8. 문자열을 먼저 모두 소진한 경우 남은 숫자 뒤에 배치
        if(numberIndex < numberMaxIndex)
        {
            for(int remainPoint = numberIndex ; remainPoint < numberMaxIndex ; remainPoint++){
                ResultArray.add(numberArray.get(remainPoint));
            }
        }
        return ResultArray;
    }
}
