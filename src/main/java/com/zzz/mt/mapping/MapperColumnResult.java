package com.zzz.mt.mapping;

/**
 * Created by hushengjun on 2017/8/15.
 */
public class MapperColumnResult {

    private String columnName;
    private Object value;
    private boolean isId = false;
    private int index;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
