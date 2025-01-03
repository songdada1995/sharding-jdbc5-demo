package org.example.config.sharding.algorithm;

import com.google.common.collect.Range;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.complex.ComplexKeysShardingValue;
import org.example.utils.DateUtils;
import org.example.utils.DigitalUtils;

import java.util.*;

/**
 * 自定义复合分片算法
 *
 * @author Administrator
 */
public class TReportComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    private int limitSuffix = 201711;

    private static String SHARDING_COLUMN = "in_account_period";

    @Override
    public String getType() {
        return "T_REPORT_TYPE";
    }

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> complexKeysShardingValue) {
        Set<String> tableSet = new HashSet<>();

        // "="条件查询
        Map<String, Collection<String>> shardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        if (shardingValuesMap.containsKey(SHARDING_COLUMN)) {
            addTables(shardingValuesMap, availableTargetNames, tableSet, SHARDING_COLUMN);
        }

        // 范围条件查询
        Map<String, Range<String>> rangeValuesMap = complexKeysShardingValue.getColumnNameAndRangeValuesMap();
        if (rangeValuesMap.containsKey(SHARDING_COLUMN)) {
            rangeAddTables(rangeValuesMap, availableTargetNames, tableSet, SHARDING_COLUMN);
        }

        return tableSet;
    }

    private void addTables(Map<String, Collection<String>> shardingValuesMap, Collection<String> availableTargetNames,
                           Set<String> tableSet, String shardingColumn) {
        Collection<String> monthList = shardingValuesMap.get(shardingColumn);
        for (String tableName : availableTargetNames) {
            for (String month : monthList) {
                String suffix = month.replace("-", "");
                if (DigitalUtils.isInteger(suffix)) {
                    if (Integer.parseInt(suffix) < limitSuffix) {
                        tableSet.add("t_report_" + limitSuffix);
                    } else {
                        if (tableName.endsWith(suffix)) {
                            tableSet.add(tableName);
                        }
                    }
                }
            }
        }
    }

    private void rangeAddTables(Map<String, Range<String>> rangeValuesMap, Collection<String> availableTargetNames,
                                Set<String> tableSet, String shardingColumn) {
        Range<String> valueRange = rangeValuesMap.get(shardingColumn);
        String lowerSuffix = valueRange.lowerEndpoint().substring(0, 7).replace("-", "");
        String upperSuffix = valueRange.upperEndpoint().substring(0, 7).replace("-", "");

        if (Integer.parseInt(lowerSuffix) < limitSuffix) {
            lowerSuffix = String.valueOf(limitSuffix);
        }
        if (Integer.parseInt(upperSuffix) > Integer.parseInt(DateUtils.formatDate(new Date(), "yyyyMM"))) {
            upperSuffix = DateUtils.formatDate(new Date(), "yyyyMM");
        }
        List<String> suffixList = DateUtils.getBetweenMonthStrList(lowerSuffix, upperSuffix, "yyyyMM", "yyyyMM");

        for (String tableName : availableTargetNames) {
            if (containTableName(suffixList, tableName)) {
                tableSet.add(tableName);
            }
        }
    }

    private boolean containTableName(List<String> suffixList, String tableName) {
        boolean flag = false;
        for (String s : suffixList) {
            if (tableName.endsWith(s)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

}
