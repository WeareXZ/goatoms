package cn.goat.oms.entity.poi;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;

@Data
public class ShoesOrderImp implements Serializable {

    /**
     * 鞋型号编码
     */
    private String shoeCode;

    /**
     * 鞋尺码
     */
    private String shoeSize;

    /**
     * 价格
     */
    private String price;

    /**
     * 利润 出库后填入
     */
    private String profit;

    /**
     * 订单状态 0入库 1出库
     */
    private String orderStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 入库时间
     */
    private String createdTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 出库时间
     */
    private String updatedTime;

    /**
     * 最后修改人
     */
    private String updatedBy;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
