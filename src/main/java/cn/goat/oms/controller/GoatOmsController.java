package cn.goat.oms.controller;


import cn.goat.oms.entity.ShoesOrder;
import cn.goat.oms.entity.dto.ShoesOrderDTO;
import cn.goat.oms.entity.request.ShoesOrderRequest;
import cn.goat.oms.entity.response.CustomException;
import cn.goat.oms.entity.response.ResponseResult;
import cn.goat.oms.service.ShoesOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/oms")
public class GoatOmsController {

    @Autowired
    private ShoesOrderService shoesOrderService;

    @GetMapping("/findAll")
    public ResponseResult<Page<ShoesOrder>> findAll(ShoesOrderRequest shoesOrderRequest, Page<ShoesOrder> request){
        if(!Optional.ofNullable(shoesOrderRequest).isPresent()){
            shoesOrderRequest = new ShoesOrderRequest();
        }
        IPage<ShoesOrder> all = shoesOrderService.findAll(shoesOrderRequest, request);
        Page<ShoesOrder> shoesOrderPage = new Page<>(all.getCurrent(),all.getSize(),all.getTotal());
        shoesOrderPage.setRecords(all.getRecords());
        return ResponseResult.SUCCESS(shoesOrderPage);
    }

    @GetMapping("/findOne")
    public ResponseResult<ShoesOrder> findOne(String id){
        if(StringUtils.isNotBlank(id)){
           throw new CustomException("id不能为空");
        }
        ShoesOrder all = shoesOrderService.findOne(id);
        return ResponseResult.SUCCESS(all);
    }

    @PostMapping("/saveOrder")
    public ResponseResult saveOrder(@RequestBody @Valid ShoesOrderDTO shoesOrderDTO){
        return shoesOrderService.saveOrder(shoesOrderDTO);
    }

    @PutMapping("/inStorage")
    public ResponseResult inStorage(@RequestBody ShoesOrderRequest shoesOrderRequest){
        return shoesOrderService.inStorage(shoesOrderRequest);
    }
}
