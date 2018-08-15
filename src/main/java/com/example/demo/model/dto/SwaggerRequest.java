package com.example.demo.model.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zhouwei on 2018/1/3
 **/
public class SwaggerRequest{

    @ApiModelProperty(value = "请求参数api")
    private String api;

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }
}
