package com.example.demo.controller;

import com.example.demo.common.exception.ExceptionCode;
import com.example.demo.common.utils.JsonUtil;
import com.example.demo.model.dto.GenericResponse;
import com.example.demo.model.dto.SwaggerRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhouwei on 2018/1/3
 **/
@RestController
@Api(value = "SwaggerDemo 接口", description = "RestApi 样例")
public class SwaggerDemoController {
    private static  final Logger logger = LoggerFactory.getLogger(SwaggerDemoController.class);

    /**
     * Swagger访问http://localhost:8082/swagger-ui.html
     * @param req
     * @return
     */
    @ApiOperation(value = "restApi样例", notes = "Swagger生成restApi样例")
//    @ApiImplicitParam(name = "req", value = "请求参数", dataType = "SwaggerRequest")
    @RequestMapping(value = "/restApiDemo", method = RequestMethod.POST)
    public GenericResponse<String> restApiDemo(@RequestBody SwaggerRequest req){
        GenericResponse<String> resp = new GenericResponse<String>(ExceptionCode.SUCC);
        logger.info(JsonUtil.objectToJson(req));
        resp.setResultContent("12345");
        return resp;
    }
}
