package com.hhssjj.mt.support;

/**
 * Created by 胡胜钧 on 8/12 0012.
 */
public class Null {
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return true;
        else if (Null.class.equals(obj.getClass())) return true;
        return false;
    }
}
