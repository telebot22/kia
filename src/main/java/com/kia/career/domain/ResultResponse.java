package com.kia.career.domain;

import lombok.Data;

@Data
public class Response {
    public String Status;
    public String Merge;

    public Response(String status, String merge){
        this.Status = status;
        this.Merge  = merge;
    }
}
