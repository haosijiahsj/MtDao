package com.zzz.mt.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 胡胜钧 on 8/13 0013.
 */
public interface PreparedStatementSetter {

    void setValues(PreparedStatement ps) throws SQLException;

}
