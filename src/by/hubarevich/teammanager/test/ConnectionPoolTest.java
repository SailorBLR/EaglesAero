/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.test;

import com.mysql.jdbc.Driver;

import static org.junit.Assert.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * JUnit test class
 */

public class ConnectionPoolTest {

    private static final Logger LOG = LogManager.getLogger(ConnectionPoolTest.class);
    public final int POOL_SIZE = 5;
    BlockingQueue<Connection> availableConnections = new ArrayBlockingQueue<>(POOL_SIZE);
    BlockingQueue<Connection> connectionsInUse = new ArrayBlockingQueue<>(POOL_SIZE);

    /**
     * Tests the PoolConnection creating
     */
    @Test
    public void createPoolConnections() {

        int i;
        try {
            ResourceBundle resource = ResourceBundle.getBundle("resources.database");
            Driver driver = new Driver();

            Properties properties = new Properties();
            properties.put("user", resource.getString("user"));
            properties.put("password", resource.getString("password"));

            driver.database(properties);
            DriverManager.registerDriver(driver);

            for (i = 1; i <= POOL_SIZE; i++) {
                Connection connection = (DriverManager.getConnection(
                        resource.getString("url"), properties));
                availableConnections.offer(connection);
            }

            assertEquals("Wrong connections quantity:", POOL_SIZE, availableConnections.size(), 0.01);
        } catch (SQLException e) {
            LOG.error(e);
        }
    }

    /**
     * Tests the PoolConnection destroying
     */

    @After
    public void closeConnections() {
        Connection connection;

        while (!availableConnections.isEmpty() & connectionsInUse.isEmpty()) {
            try {
                connection = availableConnections.take();

                connection.close();
            } catch (InterruptedException | SQLException e) {
                LOG.error(e);
            }
        }
        assertEquals("Queue not empty:", true, availableConnections.isEmpty());
        assertEquals("Queue not empty:", true, connectionsInUse.isEmpty());
    }
}
