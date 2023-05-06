package com.backend.warehousebackend.utils;

import com.backend.warehousebackend.entity.AppProduct;

import java.util.Comparator;

public class AppProductSkuComparator implements Comparator<AppProduct> {

    @Override
    public int compare(AppProduct p1, AppProduct p2) {
        String[] o1String = p1.getSku().split("PRDSKU");
        String[] o2String = p2.getSku().split("PRDSKU");

        int num1 = Integer.parseInt(o1String[1]);
        int num2 = Integer.parseInt(o2String[1]);
        return Integer.compare(num1, num2);
    }
}
