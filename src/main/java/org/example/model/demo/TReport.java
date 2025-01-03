package org.example.model.demo;

import jakarta.persistence.*;
import lombok.ToString;

/**
 * 表名：t_report
*/
@ToString
@Table(name = "t_report")
public class TReport {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 入账期间
     */
    @Column(name = "in_account_period")
    private String inAccountPeriod;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * SKU
     */
    @Column(name = "sku_code")
    private String skuCode;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取入账期间
     *
     * @return inAccountPeriod - 入账期间
     */
    public String getInAccountPeriod() {
        return inAccountPeriod;
    }

    /**
     * 设置入账期间
     *
     * @param inAccountPeriod 入账期间
     */
    public void setInAccountPeriod(String inAccountPeriod) {
        this.inAccountPeriod = inAccountPeriod;
    }

    /**
     * 获取数量
     *
     * @return qty - 数量
     */
    public Integer getQty() {
        return qty;
    }

    /**
     * 设置数量
     *
     * @param qty 数量
     */
    public void setQty(Integer qty) {
        this.qty = qty;
    }

    /**
     * 获取SKU
     *
     * @return skuCode - SKU
     */
    public String getSkuCode() {
        return skuCode;
    }

    /**
     * 设置SKU
     *
     * @param skuCode SKU
     */
    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }
}