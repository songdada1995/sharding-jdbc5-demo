package org.example.config.datasource;

/**
 * @author songbo
 * @version 1.0
 * @date 2021/6/24 13:37
 */
public class DatabaseContextHolder {
    private static final ThreadLocal<DataSourceTypeEnum> contextHolder = new ThreadLocal<>();

    public static void setDatabaseType(DataSourceTypeEnum type) {
        contextHolder.set(type);
    }

    public static DataSourceTypeEnum getDatabaseType() {
        return contextHolder.get();
    }

}
