package cn.goat.oms.entity.request;

import lombok.Data;

/**
* @Description:通用请求模型
* @author: heyz
* @Date: 2021/8/25 13:49
*/
@Data
public class BaseRequest {

    private int size = 10;

    private int page = 0;

    private String startTime;

    private String endTime;
}
