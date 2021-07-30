package cn.goat.oms.entity.response;

/**
* @Description:通用返回代码
* @author: heyz
* @Date: 2021/7/29 16:35
*/
public interface ResultCode {

    /**
     * 返回代码
     * @return
     */
    int code();

    /**
     * 访问标识
     * @return
     */
    boolean success();

    /**
     * 返回信息
     * @return
     */
    String msg();
}
