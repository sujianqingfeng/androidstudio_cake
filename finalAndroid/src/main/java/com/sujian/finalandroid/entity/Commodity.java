package com.sujian.finalandroid.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品实体类
 * Created by Admin on 2016/5/30.
 */
public class Commodity {

    private long commodity_id;

    public long getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(long commodity_id) {
        this.commodity_id = commodity_id;
    }

    /**
     * 商品描述
     */
    private String commodity_description;
    /**
     * 商品价格
     */
    private float commodity_price;
    /**
     * 商品详细图片
     */
    private long pcture_id;
    /**
     * 商品类型id
     */
    private long commodity_type_id;
    /**
     * 商品尺寸
     */
    private float commodity_size;
    /**
     * 商品的口味
     */
    private String commodity_taste;
    /**
     * 商品的描述图片
     */
    private String description_pcture;

    @Override
    public String toString() {
        return "Commodity [commodity_id=" + commodity_id
                + ", commodity_description=" + commodity_description
                + ", commodity_price=" + commodity_price + ", pcture_id="
                + pcture_id + ", commodity_type_id=" + commodity_type_id
                + ", commodity_size=" + commodity_size + ", commodity_taste="
                + commodity_taste + ", description_pcture=" + description_pcture
                + ", commodity_name=" + commodity_name + ", jsonRoot=" + jsonRoot
                + "]";
    }

    private String commodity_name;

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    private Map<String, Object> jsonRoot = new HashMap<String, Object>();


    public Map<String, Object> getJsonRoot() {
        return jsonRoot;
    }

    public void setJsonRoot(Map<String, Object> jsonRoot) {
        this.jsonRoot = jsonRoot;
    }

    public String getCommodity_description() {
        return commodity_description;
    }

    public void setCommodity_description(String commodity_description) {
        this.commodity_description = commodity_description;
    }

    public float getCommodity_price() {
        return commodity_price;
    }

    public void setCommodity_price(float commodity_price) {
        this.commodity_price = commodity_price;
    }

    public long getPcture_id() {
        return pcture_id;
    }

    public void setPcture_id(long pcture_id) {
        this.pcture_id = pcture_id;
    }

    public long getCommodity_type_id() {
        return commodity_type_id;
    }

    public void setCommodity_type_id(long commodity_type_id) {
        this.commodity_type_id = commodity_type_id;
    }

    public float getCommodity_size() {
        return commodity_size;
    }

    public void setCommodity_size(float commodity_size) {
        this.commodity_size = commodity_size;
    }

    public String getCommodity_taste() {
        return commodity_taste;
    }

    public void setCommodity_taste(String commodity_taste) {
        this.commodity_taste = commodity_taste;
    }

    public String getDescription_pcture() {
        return description_pcture;
    }

    public void setDescription_pcture(String description_pcture) {
        this.description_pcture = description_pcture;
    }
}
