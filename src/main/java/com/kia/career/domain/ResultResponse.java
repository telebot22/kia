package com.kia.career.domain;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;

@Data
public class ResultResponse {
    @JsonSetter("Status")
    public String Status;
    @JsonSetter("Merge")
    public String Merge;

    public ResultResponse(String status, String merge){
        this.Status = status;
        this.Merge  = merge;
    }
}
