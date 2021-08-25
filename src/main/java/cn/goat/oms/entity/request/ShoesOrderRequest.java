package cn.goat.oms.entity.request;

import lombok.Data;

import java.math.BigDecimal;

/**
* @Description:订单请求模型
* @author: heyz
* @Date: 2021/8/25 13:48
*/
@Data
public class ShoesOrderRequest extends BaseRequest{

    private Long oderId;

    /**
     * 鞋型号编码
     */
    private String shoeCode;

    /**
     * 鞋尺码
     */
    private String shoeSize;

    /**
     * 订单状态 0入库 1出库
     */
    private Integer orderStatus;
    /**
     * 利润 出库后填入
     */
    private BigDecimal profit;
}
