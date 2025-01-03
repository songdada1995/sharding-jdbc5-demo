package org.example.annotation;

import org.example.config.datasource.DataSourceTypeEnum;

import java.lang.annotation.*;

/**
 * @author songbo
 * @version 1.0
 * @date 2021/8/13 16:58
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceSwitch {

    /**
     * 执行前切换为指定数据源
     *
     * @return
     */
    DataSourceTypeEnum beforeExecute();

    /**
     * 执行后切换为指定数据源
     *
     * @return
     */
    DataSourceTypeEnum afterExecute();

}
