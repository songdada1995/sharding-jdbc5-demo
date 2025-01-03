package org.example.service.demo.impl;

import org.example.annotation.DataSourceSwitch;
import org.example.config.datasource.DataSourceTypeEnum;
import org.example.core.AbstractService;
import org.example.dao.demo.TReportMapper;
import org.example.model.demo.TReport;
import org.example.service.demo.TReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author songbo
 * @date 2024/12/31
 */
@Service
public class TReportServiceImpl extends AbstractService<TReport> implements TReportService {
    @Resource
    private TReportMapper tReportMapper;

    @DataSourceSwitch(
            beforeExecute = DataSourceTypeEnum.SHARDING_DATASOURCE,
            afterExecute = DataSourceTypeEnum.SPRING_DATASOURCE
    )
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public void shardingSave(TReport tReport) {
        tReportMapper.insertOne(tReport);
    }

    @DataSourceSwitch(
            beforeExecute = DataSourceTypeEnum.SHARDING_DATASOURCE,
            afterExecute = DataSourceTypeEnum.SPRING_DATASOURCE
    )
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<TReport> shardingSelect(TReport tReport) {
        List<TReport> dataList = tReportMapper.selectByPeriod(tReport.getInAccountPeriod());
        return dataList;
    }
}
