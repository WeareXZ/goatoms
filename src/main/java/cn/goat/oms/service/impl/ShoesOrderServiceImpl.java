package cn.goat.oms.service.impl;

import cn.goat.oms.entity.dto.ShoesOrderDTO;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 */
@Service
public class ShoesOrderServiceImpl extends ServiceImpl<ShoesOrderMapper, ShoesOrder> implements ShoesOrderService{

    @Autowired
    private ShoesOrderMapper shoesOrderMapper;

    @Override
    public IPage<ShoesOrder> findAll(ShoesOrderRequest shoesOrderRequest, Page<ShoesOrder> request) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(shoesOrderRequest.getShoeCode())){
            queryWrapper.like("shoe_code",shoesOrderRequest.getShoeCode());
        }
        if(StringUtils.isNotBlank(shoesOrderRequest.getShoeSize())){
            queryWrapper.like("shoe_size",shoesOrderRequest.getShoeSize());
        }
        if(Optional.ofNullable(shoesOrderRequest.getOrderStatus()).isPresent()){
            queryWrapper.eq("order_status",shoesOrderRequest.getOrderStatus());
        }
        if(StringUtils.isNotBlank(shoesOrderRequest.getStartTime())){
            queryWrapper.ge("created_time",shoesOrderRequest.getStartTime());
        }
        if(StringUtils.isNotBlank(shoesOrderRequest.getEndTime())){
            queryWrapper.le("created_time",shoesOrderRequest.getEndTime());
        }
        IPage<ShoesOrder> page = shoesOrderMapper.selectPage(request, queryWrapper);
        return page;
    }

    @Override
    public ResponseResult saveOrder(ShoesOrderDTO shoesOrderDTO) {
        ShoesOrder shoesOrder = new ShoesOrder();
        BeanUtils.copyProperties(shoesOrderDTO,shoesOrder);
        String orderNum = RandomUtil.getOrderNum();
        shoesOrder.setOrderNumber(orderNum);
        int insert = shoesOrderMapper.insert(shoesOrder);
        if(insert>0){
            return ResponseResult.SUCCESS();
        }
        return ResponseResult.FAIL();
    }

    @Override
    public ResponseResult inStorage(ShoesOrderRequest shoesOrderRequest) {
        Long oderId = shoesOrderRequest.getOderId();
        if(!Optional.ofNullable(oderId).isPresent()){
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
        if(!Optional.ofNullable(id).isPresent()){
            throw new CustomException("id不能为空");
        }
        ShoesOrder shoesOrder = shoesOrderMapper.selectById(id);
        return shoesOrder;
    }

    @Override
    public ResponseResult edit(Long id, ShoesOrderDTO shoesOrderDTO) {
        ShoesOrder old = shoesOrderMapper.selectById(id);
        if(!Optional.ofNullable(old).isPresent()){
           throw new CustomException("数据已被删除!");
        }
        BeanUtils.copyProperties(shoesOrderDTO,old);
        shoesOrderMapper.updateById(old);
        return ResponseResult.SUCCESS();
    }

    @Override
    public ShoesOrder findOneByStatus(Long id) {
        if(!Optional.ofNullable(id).isPresent()){
            throw new CustomException("id不能为空");
        }
        ShoesOrder shoesOrder = shoesOrderMapper.selectById(id);
        if(shoesOrder.getOrderStatus().equals(OrderStatusCode.OUT_STORE.code())){
            throw new CustomException("订单已出库!");
        }
        return shoesOrder;
    }
}




