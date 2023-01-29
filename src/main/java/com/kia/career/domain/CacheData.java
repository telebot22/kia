package com.mcaniri.restapi.sample.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CacheData {
    private String value;
    private LocalDateTime expirationDate;
}
