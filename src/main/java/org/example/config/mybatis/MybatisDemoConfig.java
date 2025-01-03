package org.example.config.mybatis;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.example.config.datasource.DataSourceTypeEnum;
import org.example.config.datasource.DynamicDataSource;
import org.example.config.sharding.core.ShardingJdbcConfig;
import org.example.core.Mapper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 */
@Configuration
@MapperScan(basePackages = MybatisDemoConfig.SCAN_PACKAGE, sqlSessionFactoryRef = MybatisDemoConfig.BEAN_SQL_SESSION_FACTORY_NAME)
public class MybatisDemoConfig {

    static final String SCAN_PACKAGE = "org.example.dao.demo";
    static final String MAPPER_LOCATION = "classpath*:mapper/demo/*.xml";
    static final String DATA_SOURCE_PROPERTIES_PREFIX = "spring.datasource.demo";
    static final String BEAN_NAME_PREFIX = "demo";

    static final String BEAN_DATA_SOURCE_NAME = BEAN_NAME_PREFIX + "DataSource";
    static final String DYNAMIC_BEAN_DATA_SOURCE_NAME = BEAN_NAME_PREFIX + "DynamicDataSource";
    static final String BEAN_SQL_SESSION_FACTORY_NAME = BEAN_NAME_PREFIX + "SqlSessionFactory";
    static final String BEAN_TRANSACTION_MANAGER_NAME = BEAN_NAME_PREFIX + "TransactionManager";
    static final String BEAN_SQL_SESSION_TEMPLATE_NAME = BEAN_NAME_PREFIX + "SqlSessionTemplate";
    static final String BEAN_MAPPER_HELPER_NAME = BEAN_NAME_PREFIX + "MapperHelper";

    @Bean(BEAN_DATA_SOURCE_NAME)
    @ConfigurationProperties(prefix = DATA_SOURCE_PROPERTIES_PREFIX)
    public DruidDataSource dataSource() {
        return new DruidDataSource();
    }

    /**
     * 动态数据源
     *
     * @param shardingDataSource
     * @param springDataSource
     * @return
     */
    @Bean(name = DYNAMIC_BEAN_DATA_SOURCE_NAME)
    public DynamicDataSource dynamicDataSource(
            @Qualifier(value = ShardingJdbcConfig.SHARDING_ACTUAL_BEAN_DATA_SOURCE_NAME) DataSource shardingDataSource,
            @Qualifier(value = BEAN_DATA_SOURCE_NAME) DruidDataSource springDataSource
    ) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceTypeEnum.SHARDING_DATASOURCE, shardingDataSource);
        targetDataSources.put(DataSourceTypeEnum.SPRING_DATASOURCE, springDataSource);

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        dataSource.setDefaultTargetDataSource(springDataSource); // 默认spring数据源
        return dataSource;
    }

    @Bean(name = BEAN_TRANSACTION_MANAGER_NAME)
    public DataSourceTransactionManager transactionManager(@Qualifier(value = DYNAMIC_BEAN_DATA_SOURCE_NAME) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = BEAN_SQL_SESSION_FACTORY_NAME)
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier(value = DYNAMIC_BEAN_DATA_SOURCE_NAME) DataSource dataSource
    ) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        //设置mapper location
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(MybatisDemoConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean(name = BEAN_SQL_SESSION_TEMPLATE_NAME)
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier(value = BEAN_SQL_SESSION_FACTORY_NAME) SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * Mybatis 通用Mapper配置
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name = BEAN_MAPPER_HELPER_NAME)
    public MapperHelper mapperHelper(@Qualifier(value = BEAN_SQL_SESSION_FACTORY_NAME) SqlSessionFactory sqlSessionFactory) {
        MapperHelper mapperHelper = new MapperHelper();
        //特殊配置
        Config config = new Config();
        config.setNotEmpty(false);
        config.setIDENTITY("MYSQL");
        //更多详细配置: http://git.oschina.net/free/Mapper/blob/master/wiki/mapper3/2.Integration.md
        mapperHelper.setConfig(config);
        mapperHelper.registerMapper(Mapper.class);
        mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());
        return mapperHelper;
    }
}
