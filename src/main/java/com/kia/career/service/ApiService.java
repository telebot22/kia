package com.kia.career.service;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.Future;

@Service
public class StringService {

    public String call(){

        String webInfo;

        try {
            String URL = "https://jforj.tistory.com/68";
            Connection conn = Jsoup.connect(URL);

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

}
