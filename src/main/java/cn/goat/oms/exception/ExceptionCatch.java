package cn.goat.oms.exception;

import cn.goat.oms.entity.response.CommonCode;
import cn.goat.oms.entity.response.CustomException;
import cn.goat.oms.entity.response.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionCatch {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    //捕获 CustomException异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e) {
        LOGGER.error("catch exception : {}\r\nexception: ", e.getMessage(), e);
        String message = e.getMessage();
        ResponseResult responseResult = new ResponseResult(CommonCode.FAIL, message);
        return responseResult;
    }
}
