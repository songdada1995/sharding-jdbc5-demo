# sharding-jdbc5-demo
sharding-jdbc5-demo

spring-boot 3.3.2
shardingsphere-jdbc-core 5.3.2

采用动态数据源，配置了sharding-jdbc分片数据源和普通数据源，默认普通数据源。

此举是应对数据库中只有部分表需要分片，如果只配置1个分片数据源，sharding-jdbc对于复杂sql会解析失败。

故采用自定义注解+动态数据源切换，对需要sharding-jdbc介入的地方执行分片改写sql