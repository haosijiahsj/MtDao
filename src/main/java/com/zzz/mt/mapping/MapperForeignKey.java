package com.zzz.mt.mapping;

import com.zzz.mt.support.AssociationType;

import javax.persistence.FetchType;

/**
 * Created by 胡胜钧 on 9/3 0003.
 */
public class MapperForeignKey extends MapperColumn {
    private String foreignColumnName;
    private AssociationType associationType;
    private boolean insertable;
    private boolean updatable;
    private String columnDefinition;
    private FetchType fetchType;
    public String getForeignColumnName() {
        return foreignColumnName;
    }

    public void setForeignColumnName(String foreignColumnName) {
        this.foreignColumnName = foreignColumnName;
    }

    public AssociationType getAssociationType() {
        return associationType;
    }

    public void setAssociationType(AssociationType associationType) {
        this.associationType = associationType;
    }

    public boolean isInsertable() {
        return insertable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public FetchType getFetchType() {
        return fetchType;
    }

    public void setFetchType(FetchType fetchType) {
        this.fetchType = fetchType;
    }

    public String getColumnDefinition() {
        return columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }
}
