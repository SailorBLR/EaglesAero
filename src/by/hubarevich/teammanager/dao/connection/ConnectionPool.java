/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.dao.connection;

import com.mysql.jdbc.Driver;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class-singleton JDBC Connection Pool
 *
 */

public class ConnectionPool {

    public static final Logger LOG = LogManager.getLogger(ConnectionPool.class);
    public static final int POOL_SIZE = 5;


    private static ConnectionPool instance;
    private static ReentrantLock lock = new ReentrantLock();

    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> connectionsInUse = new ArrayBlockingQueue<>(POOL_SIZE);
    private static AtomicBoolean instanceExist = new AtomicBoolean(false);

    /**
     * Creates/get instance of ConnectionPool
     * @return instance of ConnectionPool object
     */

    public static ConnectionPool getInstance() {
        if (!instanceExist.get()) {

            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    instanceExist.set(true);
                }
            } finally {
                lock.unlock();
            }

        }
        return instance;
    }

    private ConnectionPool() {
        availableConnections = new ArrayBlockingQueue<>(POOL_SIZE);
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
                ProxyConnection connection = new ProxyConnection(DriverManager.getConnection(
                        resource.getString("url"), properties));
                availableConnections.offer(connection);
                LOG.debug("Connection created " + i);
            }
        } catch (SQLException e) {
            LOG.fatal(e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets Connection from Queue of available Connections
     * and put it to Queue of Connections in use
     * @return Connection from Queue
     */

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            if (!availableConnections.isEmpty()) {

                connection = availableConnections.take();
                connectionsInUse.offer(connection);
                LOG.debug("connection obtained");
            }
        } catch (InterruptedException e) {
            LOG.error(e);
        }

        return connection;
    }

    /**
     * Puts Connection from Connection in use Queue to available connections Queue
     */

    void retrieveConnection() {
        ProxyConnection connection = null;
        try {
            if (!connectionsInUse.isEmpty() && availableConnections.size() < POOL_SIZE) {
                connection = connectionsInUse.take();
                if (!connection.getAutoCommit()) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
                availableConnections.offer(connection);
                LOG.debug("connection returned");
            }
        } catch (InterruptedException | SQLException e) {
            LOG.error(e);
        }
    }

    /**
     * Destroys Connection pool
     */

    public void closePoolConnections() {
        try {

            ProxyConnection connection;
            LOG.debug("Start destroy connections ");
            while (!availableConnections.isEmpty() & connectionsInUse.isEmpty()) {
                connection = availableConnections.take();
                connection.closeConnection();
                LOG.debug("Connection closed ");
            }
            instance = null;
            LOG.debug("Pool destroyed ");
        } catch (InterruptedException e) {
            LOG.error(e);
        }
    }
}
