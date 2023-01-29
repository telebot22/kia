package com.kia.career.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class WebCrawlingService {
    
    //Jsoup time out 설정
    @Value("${jsoup.timeout}")
    private int timeOut;

    //@Aync는 기본형이 Void이며,
    //리턴형을 받기 위해선 Future, ListenableFuture, CompletableFuture 타입을 리턴 타입으로 사용
    //메소드의 반환 형태 : new AsyncResult()
    //future.get() 은 블로킹을 통해 요청 결과가 올 때까지 기다림.
    //비동기 블로킹 방식이 되어버려 성능이 좋지 않고, 보통 Future는 사용하지 않음.
    //테스트 결과 CompletableFuture 가 가장 좋음. 권고대상이기도 함
    @Async("ThreadPoolTaskExecutor")
    public CompletableFuture<String> AsyncWebCrawling(String url){
        System.out.println("Execute method asynchronously - " + Thread.currentThread().getName());

        String webHtml = "";

        try {
            Connection conn = Jsoup.connect(url).timeout(timeOut);
            Document document = conn.get();  //Html

            webHtml = document.toString();

        } catch (IOException e) {
            e.printStackTrace();
            webHtml = "";
        }

        return new AsyncResult<String>(webHtml).completable();
    }
}
