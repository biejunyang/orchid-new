package com.orchid.web.config;

import cn.hutool.json.JSONUtil;
import com.orchid.core.Result;
import com.orchid.core.exception.BaseException;
import com.orchid.core.exception.ExceptionBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;
import java.util.Map;


@Configuration
@RestControllerAdvice
public class GlobalControllerAdvice implements ResponseBodyAdvice {


    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }

    /**
     * restful请求统一格式输出
     * @param body
     * @param methodParameter
     * @param mediaType
     * @param aClass
     * @param serverHttpRequest
     * @param serverHttpResponse
     * @return
     */
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(body instanceof Result){
            return body;
        }else if(body instanceof Map && ((Map) body).containsKey("error")){
            throw ExceptionBuilder.build(Integer.valueOf(((Map) body).get("status").toString()), ((Map) body).get("message").toString());
        }

        Result result=Result.success(body);
        if(body instanceof String){
            serverHttpResponse.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
            return JSONUtil.toJsonStr(result);
        }
        return result;
    }


    /**
     * 统一异常处理
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result<Object> handleException(Exception ex){
        if(ex instanceof BaseException){
            BaseException baseException=((BaseException)ex);
            return Result.error(baseException.code(), baseException.msg());
        }else if(ex instanceof BindException){
            BindException bindException=((BindException)ex);
            String msg=bindException.getAllErrors().get(0).getDefaultMessage();
            return Result.error(msg);
        }else if(ex instanceof MissingServletRequestParameterException){
            String msg=ex.getMessage();
            ex.printStackTrace();
            return Result.error(msg);
        }else if(ex instanceof HttpMessageNotReadableException){
            return Result.error("请求参数错误");
        }else if(ex instanceof MethodArgumentNotValidException){
            StringBuffer buffer = new StringBuffer();
            BindingResult result  = ((MethodArgumentNotValidException) ex).getBindingResult();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();

                errors.forEach(p ->{

                    FieldError fieldError = (FieldError) p;
//                    log.error("Data check failure : object{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+
//                            "},errorMessage{"+fieldError.getDefaultMessage()+"}");
                    buffer.append(fieldError.getDefaultMessage()).append(",");
                });
            }
            String msg = buffer.toString().substring(0, buffer.toString().length()-1);
            return Result.error(msg);
        }else{
            ex.printStackTrace();
            return Result.error(ex.getMessage());
        }
    }

}
