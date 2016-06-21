package com.sujian.finalandroid.entity;

/**
 * 购物车订单返回的数据
 *
 * @author 12111
 */
public class ShopCarOrderInfo {

    private long shopping_cart_id;
    private int checked;
    private int commodity_quantity;

    private long commodity_id;
    private String description_pcture;
    private String commodity_name;
    private float commodity_price;


    public long getShopping_cart_id() {
        return shopping_cart_id;
    }

    public void setShopping_cart_id(long shopping_cart_id) {
        this.shopping_cart_id = shopping_cart_id;
    }

    public int isChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public int getCommodity_quantity() {
        return commodity_quantity;
    }

    public void setCommodity_quantity(int commodity_quantity) {
        this.commodity_quantity = commodity_quantity;
    }

    public long getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(long commodity_id) {
        this.commodity_id = commodity_id;
    }

    public String getDescription_pcture() {
        return description_pcture;
    }

    public void setDescription_pcture(String description_pcture) {
        this.description_pcture = description_pcture;
    }

    public String getCommodity_name() {
        return commodity_name;
    }

    public void setCommodity_name(String commodity_name) {
        this.commodity_name = commodity_name;
    }

    public float getCommodity_price() {
        return commodity_price;
    }

    public void setCommodity_price(float commodity_price) {
        this.commodity_price = commodity_price;
    }

    @Override
    public String toString() {
        return "ShopCarOrderInfo [checked=" + checked + ", commodity_quantity="
                + commodity_quantity + ", commodity_id=" + commodity_id
                + ", description_pcture=" + description_pcture
                + ", commodity_name=" + commodity_name + ", commodity_price="
                + commodity_price + "]";
    }


}
