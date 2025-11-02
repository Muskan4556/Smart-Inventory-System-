package org.smartinventory.services;

import org.smartinventory.db.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseService {
    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }
}
