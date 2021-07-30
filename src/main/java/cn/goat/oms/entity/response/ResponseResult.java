package cn.goat.oms.entity.response;

import lombok.Data;

@Data
public class ResponseResult<T> implements Response {

    private int code = SUCCESS_CODE;

    private boolean success = SUCCESS;

    private String msg = SUCCESS_MSG;

    private T data;

    public ResponseResult(ResultCode resultCode,T data){
        this.code = resultCode.code();
        this.success = resultCode.success();
        this.msg = resultCode.msg();
        this.data = data;
    }

    public static <T> ResponseResult SUCCESS(T data){
        return new ResponseResult(CommonCode.SUCCESS,data);
    }

    public static ResponseResult SUCCESS(){
        return new ResponseResult(CommonCode.SUCCESS,null);
    }

    public static <T> ResponseResult FAIL(T data){
        return new ResponseResult(CommonCode.FAIL,data);
    }

    public static ResponseResult FAIL(){
        return new ResponseResult(CommonCode.FAIL,null);
    }


}
