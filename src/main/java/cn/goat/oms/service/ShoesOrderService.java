package cn.goat.oms.service;

import cn.goat.oms.entity.ShoesOrder;
import cn.goat.oms.entity.dto.ShoesOrderDTO;
import cn.goat.oms.entity.request.ShoesOrderRequest;
import cn.goat.oms.entity.response.ResponseResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface ShoesOrderService extends IService<ShoesOrder> {

    IPage<ShoesOrder> findAll(ShoesOrderRequest shoesOrderRequest, Page<ShoesOrder> pageable);

    ResponseResult saveOrder(ShoesOrderDTO shoesOrderDTO);

    ResponseResult inStorage(ShoesOrderRequest shoesOrderRequest);

    ShoesOrder findOne(Long id);

    ResponseResult edit(Long id, ShoesOrderDTO shoesOrderDTO);

    ShoesOrder findOneByStatus(Long id);
}
