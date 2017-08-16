package com.zzz.mt.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public interface RowMapper<T> {

    T rowMapping(ResultSet rs, int index) throws SQLException;

}
