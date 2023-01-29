package com.kia.career.controller;

import com.kia.career.domain.ResultResponse;
import com.kia.career.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @Autowired
    ApiService apiService;

    @RequestMapping(value = "/", method = { RequestMethod.GET })
    public ResultResponse base(){
        return apiService.ProcessString();
    }
}

