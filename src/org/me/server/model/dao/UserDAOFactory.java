package org.me.server.model.dao;

import org.me.server.model.Databases.Database;

import java.sql.SQLException;


public class UserDAOFactory {

    public static UserDao getUserDAO (Database db) throws SQLException, ClassNotFoundException {
        String class_name = db.getClass().getName();
        String last_part = class_name.substring(class_name.lastIndexOf('.') + 1);

        if (last_part.equals("FileDatabase"))
            return new FileUserDao(db);
        if (last_part.equals("OracleXeDatabase")) {
            org.me.server.model.Databases.OracleXeDatabase xe_db = new org.me.server.model.Databases.OracleXeDatabase();
            return new OracleXeUserDao(xe_db.class_name, xe_db.connection, xe_db.username, xe_db.password);
        }
        return null;
    }
}
