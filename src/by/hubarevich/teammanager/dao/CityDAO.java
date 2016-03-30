/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.dao;

import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.domain.City;
import by.hubarevich.teammanager.dao.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class extends abstract Class AbstractDAO and defines methods for accessing to Cities data in Database
 *
 * @see by.hubarevich.teammanager.domain.City
 */

public class CityDAO extends AbstractDAO<City> {

    public CityDAO(ProxyConnection connection) {
        super(connection);
    }

    @Override
    public List<City> findAll() throws DAOException {
        List<City> cities = new ArrayList<>();
        ResultSet resultSet;

        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryEnum.SELECT_CITIES.getValue())) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                City city = new City();
                city.setCityCode(resultSet.getString(1));
                city.setCityName(resultSet.getString(2));
                city.setFieldType(resultSet.getString(3));
                city.setNorthLatitude(resultSet.getDouble(4));
                city.setEastLongitude(resultSet.getDouble(5));

                cities.add(city);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return cities;
    }

    @Override
    public City findDomainById(int id) throws DAOException {
        return null;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(City city) throws DAOException {
        return false;
    }

    @Override
    public boolean create(City city) throws DAOException {
        return false;
    }

    @Override
    public City update(City city) throws DAOException {
        return null;
    }

    @Override
    public boolean findDomain(City city) throws DAOException {
        return false;
    }

    @Override
    public void close(Statement st) {

        super.close(st);
    }
}
