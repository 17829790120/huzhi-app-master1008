package com.wlwq.framework.interceptor;
import com.wlwq.common.apiResult.ApiCode;
import com.wlwq.common.apiResult.ApiResult;
import com.wlwq.common.exception.ApiException;
import com.wlwq.common.exception.ApiLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Renbowen
 * @ClassName LoginExceptionHandle
 * @Description 登录异常拦截
 * @Date 2020/9/29 19:55
 */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ApiExceptionHandlerInterceptor {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public ApiResult handle(Exception e){
        if(e instanceof ApiLoginException){
            return ApiResult.result(ApiCode.DONT_LOGIN);
        }else if (e instanceof ApiException){
            ApiException apiException=(ApiException) e;
            log.error("自定义异常抛出：{{}}",apiException.getMessage());
            return ApiResult.fail(apiException.getMessage());
        }
        // TODO 开发阶段可以注释掉这段代码
        else {
            //TODO 可以去掉，就不打印报错日志了
            e.printStackTrace();
            return ApiResult.result( ApiCode.ERROR);
        }
    }

}
