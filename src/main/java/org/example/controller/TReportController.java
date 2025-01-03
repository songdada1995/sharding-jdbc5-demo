package org.example.controller;

import org.example.model.demo.TReport;
import org.example.service.demo.TReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author songbo
 * @date 2024/12/31
 */
@RestController
public class TReportController {
    @Resource
    private TReportService tReportService;

    @GetMapping("/test")
    public String test() {
        TReport tReport = new TReport();
        tReport.setInAccountPeriod("2024-12");
        tReport.setQty(50);
        tReport.setSkuCode("AAA001");
        tReportService.shardingSave(tReport);
        List<TReport> tReportList = tReportService.shardingSelect(tReport);
        return tReportList.toString();
    }

}
