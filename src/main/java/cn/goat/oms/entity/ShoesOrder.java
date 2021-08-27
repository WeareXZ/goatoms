package cn.goat.oms.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;

/**
 * @TableName shoes_order
 */
@TableName(value = "shoes_order")
@Data
public class ShoesOrder implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long orderId;

    /**
     * 订单号 时间戳+4位随机数
     */
    private String orderNumber;

    /**
     * 鞋型号编码
     */
    @NotNull(message = "鞋型号不能为空")
    private String shoeCode;

    /**
     * 鞋尺码
     */
    @NotNull(message = "鞋尺码不能为空")
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
    private Integer orderStatus;

    /**
     * 备注
     */
    private String remark;

    /**
     * 入库时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createdTime;

    /**
     * 创建人
     */
    private String createdBy;

    /**
     * 出库时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8",shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedTime;

    /**
     * 最后修改人
     */
    private String updatedBy;

    /**
     * 逻辑删除字段 1代表删除
     */
    @TableLogic
    private Integer delFlag;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}