package com.dianzi.es.dep.exception;

import com.dianzi.common.exception.BizCodeEnum;
import com.dianzi.common.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @ProjectName es
 * @Author DianziEsn
 * @date 2020/7/15
 * @Function 集中处理所有controller里的异常错误
 */



@Slf4j
//@ResponseBody
////@ControllerAdvice(basePackages = "com.dianzi.es.dep.controller")
@RestControllerAdvice(basePackages = "com.dianzi.es.dep.controller")
public class depExceptionControllerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handleValidException(MethodArgumentNotValidException e){
        log.error("数据校验异常{}",e.getMessage());

        BindingResult bindingResult = e.getBindingResult();

        HashMap<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach((item)->{
            String field = item.getField();
            String message = item.getDefaultMessage();
            map.put(field,message);
        });

        return R.error(BizCodeEnum.DEP_VALID_EXCEPTION.getCode(),BizCodeEnum.DEP_VALID_EXCEPTION.getMsg()).put("data",map);
    }


    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable e){
        log.error("数据异常{}",e.getMessage());

        return R.error(BizCodeEnum.DEP_UNKNOWN_EXCEPTION.getCode(),BizCodeEnum.DEP_UNKNOWN_EXCEPTION.getMsg());
    }
}
