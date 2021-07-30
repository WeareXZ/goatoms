package cn.goat.oms.entity.response;

public enum CommonCode implements ResultCode{
    SUCCESS(1,true,"操作成功"),
    FAIL(-1,false,"操作失败"),
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
        return code();
    }

    @Override
    public boolean success() {
        return success();
    }

    @Override
    public String msg() {
        return msg();
    }
}
