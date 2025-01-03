package org.example.controller;

import org.example.model.demo.TReport;
import org.example.service.demo.TReportService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class TReportControllerTest {

    @Resource
    private TReportService tReportService;

    @Test
    public void test1() {
        try {
            TReport tReport = new TReport();
            tReport.setInAccountPeriod("2024-12");
            tReport.setQty(50);
            tReport.setSkuCode("AAA001");
            tReportService.shardingSave(tReport);
            List<TReport> tReportList = tReportService.shardingSelect(tReport);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}