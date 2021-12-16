package com.cs.file.data.persister;

import com.cs.file.data.Event;
import com.cs.file.db.DbConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

public class DbPersister {
    private Logger logger = LoggerFactory.getLogger(DbPersister.class);
    private final String query = "INSERT INTO \"PUBLIC\".\"EVENTS\"\n" +
            "( \"ID\", \"TYPE\", \"HOST\", \"DURATION\", \"ALERT\" )\n" +
            "VALUES (?,?,?,?,?)";

    public void persistEvent(final Event event) {
        DbConnector connector = new DbConnector();
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = connector.getConnection();
            if(Objects.nonNull(conn)) {
                logger.debug("query={}", query);
                ps = conn.prepareStatement(query);
                ps.setString(1, event.getId());
                ps.setString(2, event.getType());
                ps.setString(3, event.getHost());
                ps.setLong(4, event.getDuration());
                ps.setBoolean(5, event.isAlert());
                int rows = ps.executeUpdate();
                logger.debug("{} rows updated.", rows);
            }
        } catch (SQLException sqe) {
            logger.error("Unable to write to database.", sqe);
            throw new RuntimeException("Unable to write to database.");
        } catch (Exception e) {
            logger.error("Unable to write to database.", e);
            throw new RuntimeException("Unable to write to database.");
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.error("Unable to close DB connection.");
            }
        }
    }
}

