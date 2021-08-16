package cn.goat.oms.service.impl;

import cn.goat.oms.entity.dto.ShoesOrderDTO;
import cn.goat.oms.entity.response.ResponseResult;
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
    public IPage<ShoesOrder> findAll(ShoesOrderDTO shoesOrderDTO, Page<ShoesOrder> request) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if(StringUtils.isNotBlank(shoesOrderDTO.getShoeCode())){
            queryWrapper.eq("shoe_code",shoesOrderDTO.getShoeCode());
        }
        if(StringUtils.isNotBlank(shoesOrderDTO.getShoeSize())){
            queryWrapper.eq("shoe_size",shoesOrderDTO.getShoeSize());
        }
        if(Optional.ofNullable(shoesOrderDTO.getOrderStatus()).isPresent()){
            queryWrapper.eq("order_status",shoesOrderDTO.getOrderStatus());
        }
        if(Optional.ofNullable(shoesOrderDTO.getCreatedTime()).isPresent()){
            queryWrapper.ge("created_time",shoesOrderDTO.getCreatedTime());
        }
        IPage<ShoesOrder> page = shoesOrderMapper.selectPage(request, queryWrapper);
        return page;
    }

    @Override
    public ResponseResult saveOrder(ShoesOrderDTO shoesOrderDTO) {
        ShoesOrder shoesOrder = new ShoesOrder();
        BeanUtils.copyProperties(shoesOrderDTO,shoesOrder);
        int insert = shoesOrderMapper.insert(shoesOrder);
        if(insert>0){
            return ResponseResult.SUCCESS();
        }
        return ResponseResult.FAIL();
    }

    @Override
    public ResponseResult inStorage(ShoesOrderDTO shoesOrderDTO) {
        Long oderId = shoesOrderDTO.getOderId();
        ShoesOrder old = shoesOrderMapper.selectById(oderId);
        old.setProfit(shoesOrderDTO.getProfit());
        old.setUpdatedTime(LocalDateTime.now());
        old.setOrderStatus(1);
        shoesOrderMapper.updateById(old);
        return ResponseResult.SUCCESS(old);
    }
}




