package cn.goat.oms.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.goat.oms.annotation.ResubmitAnnotation;
import cn.goat.oms.entity.ShoesOrder;
import cn.goat.oms.entity.dto.ShoesOrderDTO;
import cn.goat.oms.entity.poi.ShoesOrderImp;
import cn.goat.oms.entity.poi.ShoesOrderPoi;
import cn.goat.oms.entity.request.ShoesOrderRequest;
import cn.goat.oms.entity.response.ResponseResult;
import cn.goat.oms.service.ShoesOrderService;
import cn.goat.oms.uitls.ExcelDownUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
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
    public ResponseResult<ShoesOrder> findOne(Long id){
        ShoesOrder all = shoesOrderService.findOne(id);
        return ResponseResult.SUCCESS(all);
    }

    @GetMapping("/findOneByStatus")
    public ResponseResult<ShoesOrder> findOneByStatus(Long id){
        ShoesOrder all = shoesOrderService.findOneByStatus(id);
        return ResponseResult.SUCCESS(all);
    }

    @PostMapping("/saveOrder")
    @ResubmitAnnotation
    public ResponseResult saveOrder(@RequestBody @Valid ShoesOrderDTO shoesOrderDTO){
        return shoesOrderService.saveOrder(shoesOrderDTO);
    }

    @PutMapping("/inStorage/{id}")
    @ResubmitAnnotation
    public ResponseResult inStorage(@PathVariable("id") Long id,@RequestBody ShoesOrderRequest shoesOrderRequest){
        shoesOrderRequest.setOderId(id);
        return shoesOrderService.inStorage(shoesOrderRequest);
    }

    @PutMapping("/edit/{id}")
    @ResubmitAnnotation
    public ResponseResult edit(@PathVariable("id") Long id,@RequestBody ShoesOrderDTO shoesOrderDTO){
        return shoesOrderService.edit(id,shoesOrderDTO);
    }

    @PutMapping("/cancel/{id}")
    @ResubmitAnnotation
    public ResponseResult cancel(@PathVariable("id") Long id){
        return shoesOrderService.cancel(id);
    }

    @GetMapping("/calculate")
    public ResponseResult calculate(ShoesOrderRequest shoesOrderRequest, Page<ShoesOrder> request){
        if(!Optional.ofNullable(shoesOrderRequest).isPresent()){
            shoesOrderRequest = new ShoesOrderRequest();
        }
        return shoesOrderService.calculate(shoesOrderRequest,request);
    }

    @PostMapping("/orderExport")
    @ResubmitAnnotation
    public ResponseResult orderExport(ShoesOrderRequest shoesOrderRequest, Page<ShoesOrder> request,HttpServletResponse response){
        if(!Optional.ofNullable(shoesOrderRequest).isPresent()){
            shoesOrderRequest = new ShoesOrderRequest();
        }
        ResponseResult<List<ShoesOrderPoi>> shoesOrderPoiResponseResult = shoesOrderService.orderExport(shoesOrderRequest, request);
        ExportParams exportParams = new ExportParams(null, "sheet1", ExcelType.XSSF);
        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, ShoesOrderPoi.class,shoesOrderPoiResponseResult.getData() );
        ExcelDownUtil.downLoadExcel("order.xls",response,sheets);
        return null;
    }

    @PostMapping("/orderImport")
    @ResubmitAnnotation
    public ResponseResult orderImport(@RequestBody List<ShoesOrderImp> orders){
        return shoesOrderService.orderImport(orders);
    }
}
