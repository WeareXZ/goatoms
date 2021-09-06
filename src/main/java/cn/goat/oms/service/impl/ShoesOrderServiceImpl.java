package cn.goat.oms.service.impl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.goat.oms.entity.dto.ShoesOrderDTO;
import cn.goat.oms.entity.poi.ShoesOrderImp;
import cn.goat.oms.entity.poi.ShoesOrderPoi;
import cn.goat.oms.entity.request.ShoesOrderRequest;
import cn.goat.oms.entity.response.CustomException;
import cn.goat.oms.entity.response.OrderStatusCode;
import cn.goat.oms.entity.response.ResponseResult;
import cn.goat.oms.uitls.RandomUtil;
import com.alibaba.druid.sql.dialect.odps.ast.OdpsAddStatisticStatement;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.goat.oms.entity.ShoesOrder;
import cn.goat.oms.service.ShoesOrderService;
import cn.goat.oms.mapper.ShoesOrderMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.awt.image.ShortInterleavedRaster;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class ShoesOrderServiceImpl extends ServiceImpl<ShoesOrderMapper, ShoesOrder> implements ShoesOrderService {

    @Autowired
    private ShoesOrderMapper shoesOrderMapper;

    @Override
    public IPage<ShoesOrder> findAll(ShoesOrderRequest shoesOrderRequest) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (StringUtils.isNotBlank(shoesOrderRequest.getShoeCode())) {
            queryWrapper.like("shoe_code", shoesOrderRequest.getShoeCode());
        }
        if (StringUtils.isNotBlank(shoesOrderRequest.getShoeSize())) {
            queryWrapper.like("shoe_size", shoesOrderRequest.getShoeSize());
        }
        if (Optional.ofNullable(shoesOrderRequest.getOrderStatus()).isPresent()) {
            queryWrapper.eq("order_status", shoesOrderRequest.getOrderStatus());
        }
        if (StringUtils.isNotBlank(shoesOrderRequest.getStartTime())) {
            queryWrapper.ge("created_time", shoesOrderRequest.getStartTime());
        }
        if (StringUtils.isNotBlank(shoesOrderRequest.getEndTime())) {
            queryWrapper.le("created_time", shoesOrderRequest.getEndTime());
        }
        Page request = new Page(shoesOrderRequest.getPage(),shoesOrderRequest.getSize());
        IPage<ShoesOrder> page = shoesOrderMapper.selectPage(request, queryWrapper);
        return page;
    }

    @Override
    public ResponseResult saveOrder(ShoesOrderDTO shoesOrderDTO) {
        ShoesOrder shoesOrder = new ShoesOrder();
        BeanUtils.copyProperties(shoesOrderDTO, shoesOrder);
        String orderNum = RandomUtil.getOrderNum();
        shoesOrder.setOrderNumber(orderNum);
        int insert = shoesOrderMapper.insert(shoesOrder);
        if (insert > 0) {
            return ResponseResult.SUCCESS();
        }
        return ResponseResult.FAIL();
    }

    @Override
    public ResponseResult inStorage(ShoesOrderRequest shoesOrderRequest) {
        Long oderId = shoesOrderRequest.getOderId();
        if (!Optional.ofNullable(oderId).isPresent()) {
            throw new CustomException("id不能为空!");
        }
        ShoesOrder old = shoesOrderMapper.selectById(oderId);
        old.setProfit(shoesOrderRequest.getProfit());
        old.setUpdatedTime(LocalDateTime.now());
        old.setOrderStatus(OrderStatusCode.OUT_STORE.code());
        shoesOrderMapper.updateById(old);
        return ResponseResult.SUCCESS(old);
    }

    @Override
    public ShoesOrder findOne(Long id) {
        if (!Optional.ofNullable(id).isPresent()) {
            throw new CustomException("id不能为空");
        }
        ShoesOrder shoesOrder = shoesOrderMapper.selectById(id);
        return shoesOrder;
    }

    @Override
    public ResponseResult edit(Long id, ShoesOrderDTO shoesOrderDTO) {
        ShoesOrder old = shoesOrderMapper.selectById(id);
        if (!Optional.ofNullable(old).isPresent()) {
            throw new CustomException("数据已被删除!");
        }
        BeanUtils.copyProperties(shoesOrderDTO, old);
        shoesOrderMapper.updateById(old);
        return ResponseResult.SUCCESS();
    }

    @Override
    public ShoesOrder findOneByStatus(Long id) {
        if (!Optional.ofNullable(id).isPresent()) {
            throw new CustomException("id不能为空");
        }
        ShoesOrder shoesOrder = shoesOrderMapper.selectById(id);
        if (shoesOrder.getOrderStatus().equals(OrderStatusCode.OUT_STORE.code())) {
            throw new CustomException("订单已出库!");
        }
        return shoesOrder;
    }

    /**
     * 取消订单
     *
     * @param id
     * @return
     */
    @Override
    public ResponseResult cancel(Long id) {
        if (!Optional.ofNullable(id).isPresent()) {
            throw new CustomException("id不能为空");
        }
        ShoesOrder shoesOrder = shoesOrderMapper.selectById(id);
        if (shoesOrder.getOrderStatus().equals(OrderStatusCode.IN_STORE.code())) {
            throw new CustomException("入库订单无法取消!");
        }
        shoesOrder.setOrderStatus(OrderStatusCode.IN_STORE.code());
        shoesOrder.setProfit(BigDecimal.ZERO);
        shoesOrderMapper.updateById(shoesOrder);
        return ResponseResult.SUCCESS();
    }

    @Override
    public ResponseResult calculate(ShoesOrderRequest shoesOrderRequest) {
        IPage<ShoesOrder> all = this.findAll(shoesOrderRequest);
        List<ShoesOrder> records = all.getRecords();
        long longValue = BigDecimal.ZERO.longValue();
        Long total = 0L;
        if (!CollectionUtils.isEmpty(records)) {
            total = records.stream().mapToLong(shoesOrder ->
                    BigDecimal.ZERO.compareTo(shoesOrder.getProfit()) == longValue ?
                            longValue : shoesOrder.getProfit().longValue()
            ).sum();
        }
        return ResponseResult.SUCCESS(total);
    }

    @Override
    public ResponseResult<List<ShoesOrderPoi>> orderExport(ShoesOrderRequest shoesOrderRequest) {
        IPage<ShoesOrder> all = this.findAll(shoesOrderRequest);
        List<ShoesOrder> records = all.getRecords();
        List<ShoesOrderPoi> shoesOrderPois = new ArrayList<>();
        records.stream().forEach(shoesOrder -> {
            ShoesOrderPoi poi = new ShoesOrderPoi();
            BeanUtils.copyProperties(shoesOrder,poi);
            shoesOrderPois.add(poi);
        });
        return ResponseResult.SUCCESS(shoesOrderPois);
    }

    @Override
    public ResponseResult orderImport(List<ShoesOrderImp> orders) {
        if(!CollectionUtils.isEmpty(orders)){
            orders.stream().forEach(order -> {
                ShoesOrder shoesOrder = new ShoesOrder();
                String orderNum = RandomUtil.getOrderNum();
                shoesOrder.setOrderNumber(orderNum);
                BeanUtils.copyProperties(order,shoesOrder);
                if(StringUtils.isNotBlank(order.getCreatedTime())){
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    //进行转换
                    LocalDate date = LocalDate.parse(order.getCreatedTime(), fmt);
                    LocalTime now = LocalTime.now();
                    LocalDateTime localDateTime = date.atTime(now);
                    shoesOrder.setCreatedTime(localDateTime);
                }
                if(StringUtils.isNotBlank(order.getOrderStatus())){
                    if (order.getOrderStatus().equals("入库")) {
                        shoesOrder.setOrderStatus(OrderStatusCode.IN_STORE.code());
                    } else if(order.getOrderStatus().equals("出库")){
                        shoesOrder.setOrderStatus(OrderStatusCode.OUT_STORE.code());
                    }
                }
                if(StringUtils.isNotBlank(order.getPrice())){
                    BigDecimal bigDecimal = new BigDecimal(order.getPrice());
                    shoesOrder.setPrice(bigDecimal);
                }
                if(StringUtils.isNotBlank(order.getProfit())){
                    BigDecimal bigDecimal = new BigDecimal(order.getProfit());
                    shoesOrder.setProfit(bigDecimal);
                }
                if(StringUtils.isNotBlank(order.getUpdatedTime())){
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    //进行转换
                    LocalDate date = LocalDate.parse(order.getUpdatedTime(), fmt);
                    LocalTime now = LocalTime.now();
                    LocalDateTime localDateTime = date.atTime(now);
                    shoesOrder.setUpdatedTime(localDateTime);
                }
                shoesOrderMapper.insert(shoesOrder);
            });
        }
        return ResponseResult.SUCCESS();
    }
}




