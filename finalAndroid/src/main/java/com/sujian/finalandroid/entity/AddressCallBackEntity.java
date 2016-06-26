package com.sujian.finalandroid.entity;

import java.util.List;

/**
 * 地址返回解析实体
 * Created by sujian on 2016/6/26.
 * Mail:121116111@qq.com
 */
public class AddressCallBackEntity {
    private List<Address> addresslist;
    private boolean success;


    public List<Address> getAddresslist() {
        return addresslist;
    }

    public void setAddresslist(List<Address> addresslist) {
        this.addresslist = addresslist;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AddressCallBackEntity{" +
                "addresslist=" + addresslist +
                ", success=" + success +
                '}';
    }
}
