package com.zzz.mt.mapping;

import com.zzz.mt.support.IdGenerationType;

import javax.persistence.GenerationType;

/**
 * Created by 胡胜钧 on 9/3 0003.
 */
public class MapperKey extends MapperColumn {
    private GenerationType generationType;

    public GenerationType getGenerationType() {
        return generationType;
    }

    public void setGenerationType(GenerationType generationType) {
        this.generationType = generationType;
    }
}
