package com.sujian.finalandroid.entity;

/**
 * 购物车实体
 *
 * @author 12111
 */
public class ShopCart {

    /**
     * 购物车id
     */
    private long shopping_cart_id;
    /**
     * 用户id
     */
    private long user_id;
    /**
     * 商品id
     */

    private long commodity_id;

    /**
     * 是否被选中
     */
    private int checked;
    /**
     * 商品的数量
     */
    private int commodity_quantity;


    public long getShopping_cart_id() {
        return shopping_cart_id;
    }

    public void setShopping_cart_id(long shopping_cart_id) {
        this.shopping_cart_id = shopping_cart_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getCommodity_id() {
        return commodity_id;
    }

    public void setCommodity_id(long commodity_id) {
        this.commodity_id = commodity_id;
    }

    public int getCommodity_quantity() {
        return commodity_quantity;
    }

    public void setCommodity_quantity(int commodity_quantity) {
        this.commodity_quantity = commodity_quantity;
    }


    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "ShopCart [shopping_cart_id=" + shopping_cart_id + ", user_id="
                + user_id + ", commodity_id=" + commodity_id + ", checked="
                + checked + ", commodity_quantity=" + commodity_quantity + "]";
    }

    public ShopCart(int checked, long commodity_id, int commodity_quantity, long shopping_cart_id, long user_id) {
        this.checked = checked;
        this.commodity_id = commodity_id;
        this.commodity_quantity = commodity_quantity;
        this.shopping_cart_id = shopping_cart_id;
        this.user_id = user_id;
    }
}
