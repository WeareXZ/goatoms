package cn.goat.oms.controller;


import cn.goat.oms.entity.ShoesOrder;
import cn.goat.oms.entity.dto.ShoesOrderDTO;
import cn.goat.oms.entity.response.ResponseResult;
import cn.goat.oms.service.ShoesOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public ResponseResult<Page<ShoesOrder>> findAll(ShoesOrderDTO shoesOrderDTO, Page<ShoesOrder> request){
        if(!Optional.ofNullable(shoesOrderDTO).isPresent()){
            shoesOrderDTO = new ShoesOrderDTO();
        }
        IPage<ShoesOrder> all = shoesOrderService.findAll(shoesOrderDTO, request);
        Page<ShoesOrder> shoesOrderPage = new Page<>(all.getCurrent(),all.getSize(),all.getTotal());
        shoesOrderPage.setRecords(all.getRecords());
        return ResponseResult.SUCCESS(shoesOrderPage);
    }

    @PostMapping("/saveOrder")
    public ResponseResult saveOrder(@RequestBody @Valid ShoesOrderDTO shoesOrderDTO){
        return shoesOrderService.saveOrder(shoesOrderDTO);
    }

    @PostMapping("/inStorage")
    public ResponseResult inStorage(@RequestBody ShoesOrderDTO shoesOrderDTO){
        return shoesOrderService.inStorage(shoesOrderDTO);
    }
}
