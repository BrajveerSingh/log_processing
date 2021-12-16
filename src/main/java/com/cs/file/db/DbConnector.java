package com.cs.file.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {
    private Logger logger = LoggerFactory.getLogger(DbConnector.class);
    private final String dbURL = "jdbc:hsqldb:file:/database/"+"testdb;ifexists=true";
    private final String user = "SA";
    private final String password = "";
    public Connection getConnection(){
        Connection conn = null;
        try{
            logger.info("Connecting to database.dbURL={}", dbURL);
            conn = DriverManager.getConnection(dbURL, user, password);
            logger.info("Connected to database.");
        }catch (SQLException e){
            logger.error("Unable to connect to the database.dbURL={}", dbURL);
            throw new RuntimeException("Unable to connect to the database.");
        }
        return conn;
    }
}
