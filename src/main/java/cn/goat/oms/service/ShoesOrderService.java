package cn.goat.oms.service;

import cn.goat.oms.entity.ShoesOrder;
import cn.goat.oms.entity.dto.ShoesOrderDTO;
import cn.goat.oms.entity.poi.ShoesOrderImp;
import cn.goat.oms.entity.poi.ShoesOrderPoi;
import cn.goat.oms.entity.request.ShoesOrderRequest;
import cn.goat.oms.entity.response.ResponseResult;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 */
public interface ShoesOrderService extends IService<ShoesOrder> {

    IPage<ShoesOrder> findAll(ShoesOrderRequest shoesOrderRequest);

    ResponseResult saveOrder(ShoesOrderDTO shoesOrderDTO);

    ResponseResult inStorage(ShoesOrderRequest shoesOrderRequest);

    ShoesOrder findOne(Long id);

    ResponseResult edit(Long id, ShoesOrderDTO shoesOrderDTO);

    ShoesOrder findOneByStatus(Long id);

    ResponseResult cancel(Long id);

    ResponseResult calculate(ShoesOrderRequest shoesOrderRequest);

    ResponseResult<List<ShoesOrderPoi>> orderExport(ShoesOrderRequest shoesOrderRequest);

    ResponseResult orderImport(List<ShoesOrderImp> orders);
}
