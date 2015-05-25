package org.me.server.model.Databases;


public class OracleXeDatabase extends Database {
    public final String class_name = "oracle.jdbc.driver.OracleDriver";
    public final String connection = "jdbc:oracle:thin:@localhost:1521:xe";
    public final String username = "behdad";
    public final String password = "whocares";
}
