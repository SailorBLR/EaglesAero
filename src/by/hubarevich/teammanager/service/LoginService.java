/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.service;

import by.hubarevich.teammanager.dao.AdminDAO;
import by.hubarevich.teammanager.dao.connection.ConnectionPool;
import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.domain.User;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.util.MD5Util;

/**
 * Service class for login action
 */

public class LoginService {
    private static LoginService instance = new LoginService();

    public static LoginService getInstance() {
        return instance;
    }

    private LoginService() {
    }

    /**
     * checks inputed parameters of login and password
     *
     * @param enterLogin String value
     * @param enterPass  String value
     * @return true if the login and password are valid
     * @throws LogicException if the DAOException was thrown
     */
    public boolean checkLogin(String enterLogin, String enterPass) throws LogicException {
        boolean success = false;
        User user;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            AdminDAO adminDAO = new AdminDAO(connection);
            user = adminDAO.findUserByLogin(enterLogin);
            if (user != null && user.getPassword().equals(MD5Util.md5CodePassword(enterPass))) {
                success = true;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return success;
    }

    /**
     * searches for a inputed login in DB
     *
     * @param login String
     * @return User object if the login is in the DB
     * @throws LogicException if the DAOException was thrown
     */
    public User getUser(String login) throws LogicException {
        User user;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            AdminDAO adminDAO = new AdminDAO(connection);
            user = adminDAO.findUserByLogin(login);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return user;
    }
}