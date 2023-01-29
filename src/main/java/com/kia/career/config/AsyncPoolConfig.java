package com.mcaniri.restapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;


@Configuration   //스프링 컨테이너에 빈 등록.
@EnableAsync     //주의 Application X, Async 설정 클래스에 선언 필요.
public class AsyncPoolConfig {

    //Thread 관리를 위한 Executor 선언.
    //name : @Async 사용시 threadPoolTaskExecutor 이름을 통해 Thread를 사용할 수 있음.
    //참고 : https://steady-coding.tistory.com/611
    @Bean(name="threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor(){
        //Notice.
        //요청 30개가 오면 30개의 Thread를 생성하는 것이 아닌,
        //3개의 기본 Thread가 Queue 100개를 모두 사용시 MaxSize까지 새로 생성함.
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(3);    //기본 Core Thread 생성 갯수.
        threadPoolTaskExecutor.setMaxPoolSize(30);    //최대 Core Thread 생성 제한 갯수.
        threadPoolTaskExecutor.setQueueCapacity(100); //Thread가 요청처리 시 보관한 Queue 갯수.
        threadPoolTaskExecutor.setThreadNamePrefix("threadPool-");
        threadPoolTaskExecutor.initialize();
        return threadPoolTaskExecutor;
    }
}
