package com.sujian.finalandroid.entity;

/**
 * 返回的单个信息的实体
 * Created by sujian on 2016/6/26.
 * Mail:121116111@qq.com
 */
public class AddressInfoEntity {
    private Address address;
    private boolean success;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AddressInfoEntity{" +
                "address=" + address +
                ", success=" + success +
                '}';
    }
}
