/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.dao;

import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.domain.Domain;
import by.hubarevich.teammanager.dao.exception.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Abstract Class for DAO instances
 * @param <T>
 */

public abstract class AbstractDAO <T extends Domain> {
    private static final Logger LOG = LogManager.getLogger(AbstractDAO.class);
    protected ProxyConnection connection;
    public AbstractDAO(ProxyConnection connection) {
        this.connection = connection;
    }

    /**
     * @return List of all Objects Domain from database
     * @throws DAOException if SQLException thrown
     */
    public abstract List<T> findAll() throws DAOException;

    /**
     * finds the Domain by it's id
     * @param id unique Domain identification number
     * @return Domain object
     * @throws DAOException if SQLException thrown
     */
    public abstract T findDomainById(int id) throws DAOException;

    /**
     * deletes Domain by it's ID
     * @param id unique Domain identification number
     * @return true if the deleting is successfully
     * @throws DAOException if SQLException thrown
     */
    public abstract boolean delete(int id) throws DAOException;

    /**
     * deletes the field in DB by Object class Domain
     * @param domain Object class T extends Domain
     * @return true if the deleting is successfully
     * @throws DAOException if SQLException thrown
     */
    public abstract boolean delete(T domain) throws DAOException;

    /**
     * creates new field in DB by Object class extends Domain
     * @param domain Object class T extends Domain
     * @return true if the creating is successfully
     * @throws DAOException if SQLException thrown
     */
    public abstract boolean create(T domain) throws DAOException;

    /**
     * updates a field in DB by Object class extends Domain
     * @param domain Object class T extends Domain
     * @return true if the updating is successfully
     * @throws DAOException if SQLException thrown
     */
    public abstract T update(T domain) throws DAOException;

    /**
     * finds a field in DB by Object class extends Domain
     * @param domain Object class T extends Domain
     * @return Domain object
     * @throws DAOException if SQLException thrown
     */
    public abstract boolean findDomain(T domain) throws DAOException;

    /**
     * closes opened Statement
     * @param st class ? extends Statement
     */

    public void close(Statement st){
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException e) {
            LOG.warn("Statement not closed!");
        }
    }
}