package com.example.demo.model.dto;

import com.example.demo.common.exception.ExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by zhouwei on 2018/1/3
 **/
@ApiModel(value = "响应对象")
public class GenericResponse<T> {

    @ApiModelProperty(value = "响应码", required = true)
    private String resultCode;

    @ApiModelProperty(value = "响应描述", required = true)
    private String resultMessage;

    @ApiModelProperty(value = "响应结果")
    private T resultContent;

    public GenericResponse() {
    }

    public GenericResponse(ExceptionCode ec) {
        this.resultCode = ec.code();
        this.resultMessage = ec.desc();
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMessage() {
        return resultMessage;
    }

    public void setResultMessage(String resultMessage) {
        this.resultMessage = resultMessage;
    }

    public T getResultContent() {
        return resultContent;
    }

    public void setResultContent(T resultContent) {
        this.resultContent = resultContent;
    }
}
