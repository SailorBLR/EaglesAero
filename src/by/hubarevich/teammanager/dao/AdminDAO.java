/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.dao;

import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.domain.User;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.util.InputedDataValidationUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class extends abstract Class AbstractDAO and defines methods for admin requests to Database
 *
 * @see by.hubarevich.teammanager.domain.User
 */

public class AdminDAO extends AbstractDAO<User> {
    public AdminDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * finds User field in database by user Login
     *
     * @param login inputed value of login
     * @return class User Object
     * @throws DAOException if SQLException thrown
     */

    public User findUserByLogin(String login) throws DAOException {

        User user = null;
        ResultSet resultSet;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.SELECT_LOGIN_PASSWORD_ROLE.getValue())) {
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    user = new User();
                    user.setLogin(resultSet.getString(1));
                    user.setPassword(resultSet.getString(2));
                    user.setRole(resultSet.getString(3));
                    user.setName(resultSet.getString(4));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws DAOException {
        PreparedStatement preparedStatement = null;
        List<User> users = new ArrayList<>();
        ResultSet resultSet;
        try {
            preparedStatement = connection.prepareStatement(QueryEnum.SELECT_ALL_FROM_USERS.getValue());
            resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {

                while (resultSet.next()) {
                    User user = new User();
                    user.setUserId(resultSet.getInt(1));
                    user.setLogin(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setRole(resultSet.getString(4));
                    user.setName(resultSet.getString(5));
                    user.setSurname(resultSet.getString(6));
                    users.add(user);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            close(preparedStatement);
        }
        return users;
    }

    @Override
    public User findDomainById(int id) throws DAOException {

        User user = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryEnum.SELECT_USER_BY_ID.getValue())) {

            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    user = new User();
                    user.setUserId(resultSet.getInt(1));
                    user.setLogin(resultSet.getString(2));
                    user.setPassword(resultSet.getString(3));
                    user.setRole(resultSet.getString(4));
                    user.setName(resultSet.getString(5));
                    user.setSurname(resultSet.getString(6));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return user;
    }

    @Override
    public boolean delete(int id) throws DAOException {

        boolean deleteSuccess = false;
        if (findDomainById(id) != null) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(QueryEnum.DELETE_USER_BY_ID.getValue())) {
                preparedStatement.setString(1, String.valueOf(id));
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
            deleteSuccess = true;
        }
        return deleteSuccess;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public boolean create(User user) throws DAOException {

        boolean createSuccess = false;
        Blob blob = null;
        if (!findDomain(user)&& InputedDataValidationUtil.validateUserData(user)) {
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement(QueryEnum.INSERT_NEW_USER.getValue())) {

                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getRole());
                preparedStatement.setString(4, user.getName());
                preparedStatement.setString(5, user.getSurname());
                preparedStatement.setBlob(6, blob);

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            createSuccess = true;
        }
        return createSuccess;
    }

    @Override
    public User update(User user) throws DAOException {

        User searchResultUser = null;
        if ((findDomainById(user.getUserId()) != null)&&(InputedDataValidationUtil.validateUserData(user))) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QueryEnum.EDIT_USER.getValue())) {


                preparedStatement.setString(1, user.getLogin());
                preparedStatement.setString(2, user.getPassword());
                preparedStatement.setString(3, user.getRole());
                preparedStatement.setString(4, user.getName());
                preparedStatement.setString(5, user.getSurname());
                preparedStatement.setString(6, String.valueOf(user.getUserId()));

                preparedStatement.executeUpdate();

                searchResultUser = findDomainById(user.getUserId());

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return searchResultUser;
    }

    @Override
    public void close(Statement st) {
        super.close(st);
    }

    @Override
    public boolean findDomain(User user) throws DAOException {

        boolean searchSuccess = false;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.SELECT_USER_IF_EXIST.getValue())) {

            preparedStatement.setString(1, user.getLogin());
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.isBeforeFirst()) {
                searchSuccess = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return searchSuccess;
    }


}
