package com.hhssjj.mt.sql;

/**
 * Created by hushengjun on 2017/8/14.
 */
public abstract class SingleParamSqlCreator extends SqlCreator {
    protected Object parameter;

    @Override
    public void setParameters(Object[] parameters) {
        this.parameter = parameters[0];
        super.setParameters(parameters);
    }

}
