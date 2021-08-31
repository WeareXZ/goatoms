package cn.goat.oms.entity.poi;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* @Description:导出对象
* @author: heyz
* @Date: 2021/8/31 10:01
*/
@Data
public class ShoesOrderPoi implements Serializable {

    /**
     * 订单号 时间戳+4位随机数
     */
    @Excel(name = "订单号")
    private String orderNumber;

    /**
     * 鞋型号编码
     */
    @Excel(name = "鞋型号编码")
    private String shoeCode;

    /**
     * 鞋尺码
     */
    @Excel(name = "鞋尺码")
    private String shoeSize;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 利润 出库后填入
     */
    @Excel(name = "利润")
    private BigDecimal profit;

    /**
     * 订单状态 0入库 1出库
     */
    @Excel(name = "订单状态", replace = { "入_0", "出_1" }, suffix = "库")
    private Integer orderStatus;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 入库时间
     */
    @Excel(name = "入库时间", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private LocalDateTime createdTime;

    /**
     * 出库时间
     */
    @Excel(name = "出库时间", databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    private LocalDateTime updatedTime;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}