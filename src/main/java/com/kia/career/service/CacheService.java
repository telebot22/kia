package com.kia.career.service;

import com.kia.career.domain.CacheData;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;


@Service
public class CacheService {
    private static final CacheData EMPTY_DATA = new CacheData();

    //Bean에 설정된 ConcurrentMapCache저장소명에 cacheNames = "HtmlMergeData"이 들어간다.
    //해당 Key의 경우 캐시데이터가 있어 Value가 존자해면 getCashData는 수행되지 않고
    //캐시에 있는 Key에 대응된 Value가 리턴된다.
    @Cacheable(cacheNames = "HtmlMergeData", key = "#key")
    public CacheData getCacheData(final String key){
        System.out.println("Cache 없음 데이터 재 조회 및 캐시 생성 필요");
        return EMPTY_DATA;
    }
    
    @CachePut(cacheNames = "HtmlMergeData", key = "#key")
    public CacheData updateCacheData(final String key, final String value){
        CacheData cacheData = new CacheData();
        cacheData.setValue(value);
        cacheData.setExpirationDate(LocalDateTime.now().plusMinutes(5));
        System.out.println("Cache 정보 재생성 완료");
        return cacheData;
    }  
    
    
    @CacheEvict(cacheNames = "HtmlMergeData", key = "#key")
    public boolean expireCacheData(final String key){
        System.out.println("Cache 삭제 구현 생략");
        //필요시 구현 과제에선 패스
        return true;
    }

    public boolean isEmpty(final CacheData cacheData){        
        return StringUtils.isEmpty(cacheData.getValue());
    }
        
}
