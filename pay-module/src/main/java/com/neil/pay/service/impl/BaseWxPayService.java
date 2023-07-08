package com.neil.pay.service.impl;

import com.neil.pay.service.WxPayService;

/**
 * @author nihao
 * @date 2023/7/8
 */
public abstract class BaseWxPayService implements WxPayService {

    @Override
    public void test() {
        System.out.println("hello wx pay");
    }
}
