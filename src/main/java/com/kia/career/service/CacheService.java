package com.mcaniri.restapi.sample.service;

import com.mcaniri.restapi.sample.domain.CacheData;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

//https://sunghs.tistory.com/132
@Service
public class CacheService {
    private static final CacheData CHECK_DATA = new CacheData();

    @Cacheable(cacheNames = "HtmlMergeData", key = "#key")
    public CacheData getCheckData(final String key){
        System.out.println("Cache 없음 데이터 재조회 및 캐시 생성 필요");
        return CHECK_DATA;
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
