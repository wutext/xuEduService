package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@ControllerAdvice
public class ExceptionCatch {

    //定义map存放错误类型和编码<ExceptionClass, code>
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;
    private static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    /**
     * 可预知的错误捕捉
     * @param customException
     * @return
     */
    @ResponseBody
    @ExceptionHandler(CustomException.class)
    public ResponseResult custonException(CustomException customException) {
        log.error("catch Exception {}", customException.getMessage());
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    /**
     * 框架层次的，不可预知的错误捕捉
     * @param exception
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseResult exception(Exception exception) {
        log.error("system Exception {}", exception.getMessage());
        if(EXCEPTIONS==null) {
            EXCEPTIONS = builder.build(); //构建EXCEPTIONS异常类
        }
        //从EXCEPTIONS中找到异常类型所对应的类，有则返回错误代码，没有则返回统一错误代码：99999
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if(resultCode!=null) {
            return new ResponseResult(resultCode);
        }else {
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    //预定义异常类并定义错误编码,当有此异常时返回此异常编码
    static {
        builder.put(HttpMediaTypeNotAcceptableException.class, CommonCode.INVALID_PAAMS);
        builder.put(HttpRequestMethodNotSupportedException.class,CommonCode.INVALID_PAAMS);
        builder.put(NullPointerException.class,CommonCode.NLL_POINT_EXCEPTION);
    }
}
