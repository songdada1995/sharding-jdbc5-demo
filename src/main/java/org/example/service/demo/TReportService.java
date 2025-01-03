package org.example.service.demo;

import org.example.core.Service;
import org.example.model.demo.TReport;

import java.util.List;

/**
 * @author songbo
 * @date 2024/12/31
 */
public interface TReportService extends Service<TReport> {

    void shardingSave(TReport tReport);

    List<TReport> shardingSelect(TReport tReport);
}
