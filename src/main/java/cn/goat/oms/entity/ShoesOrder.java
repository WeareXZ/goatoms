package cn.goat.oms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName shoes_order
 */
@TableName(value ="shoes_order")
@Data
public class ShoesOrder implements Serializable {
    /**
     * 
     */
    @TableId
    private Long oderId;

    /**
     * 订单号 时间戳+4位随机数
     */
    private Long oderNumber;

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
    private BigDecimal price;

    /**
     * 利润 出库后填入
     */
    private BigDecimal profit;

    /**
     * 订单状态 0入库 1出库
     */
    private Byte orderStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 入库时间
     */
    private Date createdTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 出库时间
     */
    private Date updatedTime;

    /**
     * 最后修改人
     */
    private String updatedBy;

    /**
     * 逻辑删除字段 1代表删除
     */
    private Byte delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}