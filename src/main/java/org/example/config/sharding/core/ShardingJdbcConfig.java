package org.example.config.sharding.core;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.driver.api.ShardingSphereDataSourceFactory;
import org.apache.shardingsphere.infra.config.algorithm.AlgorithmConfiguration;
import org.apache.shardingsphere.infra.config.props.ConfigurationPropertyKey;
import org.apache.shardingsphere.sharding.api.config.ShardingRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableReferenceRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.rule.ShardingTableRuleConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.keygen.KeyGenerateStrategyConfiguration;
import org.apache.shardingsphere.sharding.api.config.strategy.sharding.ComplexShardingStrategyConfiguration;
import org.example.config.sharding.algorithm.TReportComplexKeysShardingAlgorithm;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author songbo
 * @version 1.0
 * @date 2021/8/6 14:17
 */
@Configuration
public class ShardingJdbcConfig {

    /* sharding-demo */
    public static final String SHARDING_PRIMARY_DATA_SOURCE = "sharding-demo";
    public static final String SHARDING_PRIMARY_DATA_SOURCE_PROPERTIES_PREFIX = "shardingsphere.datasource." + SHARDING_PRIMARY_DATA_SOURCE;
    public static final String SHARDING_PRIMARY_BEAN_NAME_PREFIX = "shardingDemo";
    public static final String SHARDING_PRIMARY_BEAN_DATA_SOURCE_NAME = SHARDING_PRIMARY_BEAN_NAME_PREFIX + "DataSource";

    public static final String SHARDING_ACTUAL_BEAN_DATA_SOURCE_NAME = "shardingActualDataSource";
    /* 逻辑表 */
    public static final String T_TABLE_LOGIC_TABLE = "t_report";

    @Value("${shardingsphere.rules.tables.t_report.actual-data-nodes}")
    private String T_TABLE_ACTUAL_DATA_NODES;
    @Value("${shardingsphere.rules.tables.t_report.table-strategy.complex.sharding-column}")
    private String T_TABLE_SHARDING_COLUMN;
    @Value("${shardingsphere.rules.tables.t_report.key-generate-strategy.column}")
    private String T_TABLE_KEY_GENERATE_STRATEGY_COLUMN;
    @Value("${shardingsphere.rules.tables.t_report.key-generate-strategy.key-generator-name}")
    private String T_TABLE_KEY_GENERATE_STRATEGY_KEY_GENERATOR_NAME;
    @Value("${shardingsphere.rules.binding-tables}")
    private String SHARDING_BINDING_TABLES;
    @Value("${shardingsphere.props.sql-show}")
    private String SHARDING_PROPS_SQL_SHOW;

    @Bean(name = SHARDING_PRIMARY_BEAN_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = SHARDING_PRIMARY_DATA_SOURCE_PROPERTIES_PREFIX)
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 分片数据源
     *
     * @param dataSource
     * @return
     * @throws SQLException
     */
    @Bean(name = SHARDING_ACTUAL_BEAN_DATA_SOURCE_NAME)
    public DataSource shardingActualDataSource(
            @Qualifier(value = SHARDING_PRIMARY_BEAN_DATA_SOURCE_NAME) DruidDataSource dataSource
    ) throws SQLException {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTables().add(getTReportTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add(new ShardingTableReferenceRuleConfiguration(SHARDING_PRIMARY_DATA_SOURCE, SHARDING_BINDING_TABLES));
        shardingRuleConfig.getShardingAlgorithms().put(new TReportComplexKeysShardingAlgorithm().getType(), new AlgorithmConfiguration(new TReportComplexKeysShardingAlgorithm().getType(), new Properties()));
        shardingRuleConfig.getKeyGenerators().put(T_TABLE_KEY_GENERATE_STRATEGY_KEY_GENERATOR_NAME, new AlgorithmConfiguration("SNOWFLAKE", getKeyGenerateProperties()));
        shardingRuleConfig.setDefaultKeyGenerateStrategy(new KeyGenerateStrategyConfiguration(
                T_TABLE_KEY_GENERATE_STRATEGY_COLUMN,
                T_TABLE_KEY_GENERATE_STRATEGY_KEY_GENERATOR_NAME));
        return ShardingSphereDataSourceFactory.createDataSource(createDataSourceMap(dataSource), Collections.singletonList(shardingRuleConfig), createProperties());
    }

    private Map<String, DataSource> createDataSourceMap(DruidDataSource dataSource) {
        // key为数据源名称，后面分片算法取得就是这个，value为具体的数据源
        HashMap<String, DataSource> shardingDataSourceMap = new HashMap<>();
        shardingDataSourceMap.put(SHARDING_PRIMARY_DATA_SOURCE, dataSource);
        return shardingDataSourceMap;
    }

    public ShardingTableRuleConfiguration getTReportTableRuleConfiguration() {
        ShardingTableRuleConfiguration tableRuleConfiguration = new ShardingTableRuleConfiguration(
                T_TABLE_LOGIC_TABLE,
                T_TABLE_ACTUAL_DATA_NODES);
        tableRuleConfiguration.setTableShardingStrategy(
                new ComplexShardingStrategyConfiguration(
                        T_TABLE_SHARDING_COLUMN,
                        new TReportComplexKeysShardingAlgorithm().getType()));
        tableRuleConfiguration.setKeyGenerateStrategy(new KeyGenerateStrategyConfiguration(
                T_TABLE_KEY_GENERATE_STRATEGY_COLUMN,
                T_TABLE_KEY_GENERATE_STRATEGY_KEY_GENERATOR_NAME));
        return tableRuleConfiguration;
    }

    private static Properties getKeyGenerateProperties() {
        Properties result = new Properties();
        result.setProperty("worker-id", "1");
        result.setProperty("max-vibration-offset", "1");
        result.setProperty("max-tolerate-time-difference-milliseconds", "100000");
        return result;
    }

    private Properties createProperties() {
        Properties result = new Properties();
        result.setProperty(ConfigurationPropertyKey.SQL_SHOW.getKey(), SHARDING_PROPS_SQL_SHOW);
        return result;
    }
}
