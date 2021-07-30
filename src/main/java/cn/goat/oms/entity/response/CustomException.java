package cn.goat.oms.entity.response;

import lombok.Data;

@Data
public class CustomException extends RuntimeException {

    private final  transient Object[] params;

    public CustomException(String msg) {
        this(msg,null);
    }

    public CustomException(String msg,Object[] params) {
        super(msg);
        this.params = params;
    }

}
