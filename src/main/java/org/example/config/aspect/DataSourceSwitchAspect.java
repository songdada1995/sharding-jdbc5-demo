package org.example.config.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.annotation.DataSourceSwitch;
import org.example.config.datasource.DataSourceTypeEnum;
import org.example.config.datasource.DatabaseContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 动态数据源切换
 *
 * @author songbo
 * @version 1.0
 * @date 2021/8/13 17:14
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class DataSourceSwitchAspect {

    @Pointcut("@annotation(dataSourceSwitch)")
    public void dataSourceSwitchPointcut(DataSourceSwitch dataSourceSwitch) {
    }

    @Before("dataSourceSwitchPointcut(dataSourceSwitch)")
    public void before(JoinPoint jp, DataSourceSwitch dataSourceSwitch) {
        DataSourceTypeEnum beforeExecuteValue = dataSourceSwitch.beforeExecute();
        if (beforeExecuteValue != null) {
            DatabaseContextHolder.setDatabaseType(beforeExecuteValue);
        }
    }

    /**
     * 后置/最终通知：无论目标方法在执行过程中出现一场都会在它之后调用
     */
    @After("dataSourceSwitchPointcut(dataSourceSwitch)")
    public void after(JoinPoint jp, DataSourceSwitch dataSourceSwitch) {
        DataSourceTypeEnum afterExecuteValue = dataSourceSwitch.afterExecute();
        if (afterExecuteValue != null) {
            DatabaseContextHolder.setDatabaseType(afterExecuteValue);
        }
    }

}
