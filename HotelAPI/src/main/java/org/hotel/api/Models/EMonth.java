package org.hotel.api.Models;

import java.util.HashMap;
import java.util.Map;

public enum EMonth {
    Jan(0), Feb(1), Mar(2), Apr(3), May(4), June(5), July(6), Aug(7), Sept(8), Oct
            (9), Nov(10), Dec(11);

    private int value;

    private static Map map = new HashMap();

    private EMonth(int month) {
        this.value = month;
    }

    static {
        for(EMonth eMonth : EMonth.values()) {
            map.put(eMonth.value, eMonth);
        }
    }

    public  static EMonth ValueOf(int month) {
        return (EMonth)map.get(month);
    }

    public int GetValue() {
        return value;
    }
}
