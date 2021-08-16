package cn.goat.oms.entity.response;

import lombok.ToString;

@ToString
public enum CommonCode implements ResultCode{
    /**
    * 默认通用编码
    */
    SUCCESS(1,true,"操作成功"),
    FAIL(-1,false,"操作失败"),
    USER_LOGIN_SUCCESS(10000,true,"登录成功"),
    SYSTEM_ERROR(9999,false,"系统错误");

    int code;

    boolean success;

    String msg;

    CommonCode(int code, boolean success, String msg) {
        this.code = code;
        this.success = success;
        this.msg = msg;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public String msg() {
        return msg;
    }
}
