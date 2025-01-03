package org.example.dao.demo;

import org.apache.ibatis.annotations.Param;
import org.example.core.Mapper;
import org.example.model.demo.TReport;

import java.util.List;

public interface TReportMapper extends Mapper<TReport> {
    void insertOne(TReport tReport);

    List<TReport> selectByPeriod(@Param("period") String period);
}