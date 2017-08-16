package com.zzz.mt.jdbc;

import java.sql.PreparedStatement;

/**
 * Created by hushengjun on 2017/8/14.
 */
public interface PreparedStatementCallback<T> {

    T doSomething(PreparedStatement preparedStatement);

}
