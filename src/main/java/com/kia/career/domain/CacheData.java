package com.kia.career.domain;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CacheData {
    private String value;
    private LocalDateTime expirationDate;
}
