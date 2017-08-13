package com.hhssjj.mt.processors;

import com.hhssjj.mt.utils.Preconditions;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public abstract class SingleParameterMethodProcessor<T> extends BaseMethodProcessor<T> {
    protected Object paremeter;

    @Override
    public void setParameters(Object[] parameters) {
        Preconditions.checkArgument(parameters.length == 1,"this method must need one parameter");
        Preconditions.checkNotNull(parameters[0], "this method need this parameter is not null");

        super.setParameters(parameters);
        this.paremeter = parameters[0];
    }

}
