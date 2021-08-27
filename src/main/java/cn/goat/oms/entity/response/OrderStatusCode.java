package cn.goat.oms.entity.response;

import lombok.ToString;

@ToString
public enum OrderStatusCode  implements ResultCode{
    /**
     * 默认通用编码
     */
    IN_STORE(0, "入库"),
    OUT_STORE(1, "出库");

    int code;

    boolean success;

    String msg;

    OrderStatusCode(int code,String msg) {
        this.code = code;
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
