/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.dao;

import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.domain.Plane;
import by.hubarevich.teammanager.dao.exception.DAOException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class extends abstract Class AbstractDAO and defines methods for dispatcher requests to Database
 *
 * @see by.hubarevich.teammanager.domain.Plane
 */

public class PlaneDAO extends AbstractDAO<Plane> {
    private static final Logger LOG = LogManager.getLogger(PlaneDAO.class);

    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    public PlaneDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * searches for a plane by its  model
     *
     * @param model String Plane model
     * @return object Plane
     * @throws DAOException if SQLException where thrown
     */

    public Plane findPlaneByModel(String model) throws DAOException {

        Plane plane = null;
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(QueryEnum.SELECT_PLANE_BY_MODEL.getValue())) {
            preparedStatement.setString(1, model);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    plane = new Plane();
                    Calendar freeFrom = new GregorianCalendar();
                    plane.setPlaneId(resultSet.getInt(1));
                    plane.setPlaneName(resultSet.getString(2));
                    plane.setPilot(resultSet.getShort(3));
                    plane.setTechnic(resultSet.getShort(4));
                    plane.setStuart(resultSet.getShort(5));
                    plane.setPassengers(resultSet.getInt(6));
                    plane.setSpeed(resultSet.getInt(7));
                    plane.setRange(resultSet.getInt(8));
                    plane.setLocation(resultSet.getString(9));
                    freeFrom.setTimeInMillis(resultSet.getTimestamp(10).getTime());
                    plane.setFreeFrom(freeFrom);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return plane;
    }

    @Override
    public List<Plane> findAll() throws DAOException {
        List<Plane> planes = null;
        ResultSet resultSet;
        Calendar freeFrom;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.SELECT_PLANES.getValue())) {
            resultSet = preparedStatement.executeQuery();

            if (resultSet != null) {
                planes = new ArrayList<>();

                while (resultSet.next()) {
                    Plane plane = new Plane();
                    freeFrom = new GregorianCalendar();
                    plane.setPlaneId(resultSet.getInt(1));
                    plane.setPlaneName(resultSet.getString(2));
                    plane.setPilot(resultSet.getShort(3));
                    plane.setTechnic(resultSet.getShort(4));
                    plane.setStuart(resultSet.getShort(5));
                    plane.setPassengers(resultSet.getInt(6));
                    plane.setSpeed(resultSet.getInt(7));
                    plane.setRange(resultSet.getInt(8));
                    plane.setLocation(resultSet.getString(9));
                    freeFrom.setTimeInMillis(resultSet.getTimestamp(10).getTime());
                    plane.setFreeFrom(freeFrom);

                    planes.add(plane);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return planes;
    }

    @Override
    public Plane findDomainById(int id) throws DAOException {

        Plane plane = null;
        Calendar freeFrom;
        ResultSet resultSet;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.SELECT_PLANE_BY_ID.getValue())) {
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    plane = new Plane();
                    freeFrom = new GregorianCalendar();
                    plane.setPlaneId(resultSet.getInt(1));
                    plane.setPlaneName(resultSet.getString(2));
                    plane.setPilot(resultSet.getShort(3));
                    plane.setTechnic(resultSet.getShort(4));
                    plane.setStuart(resultSet.getShort(5));
                    plane.setPassengers(resultSet.getInt(6));
                    plane.setSpeed(resultSet.getInt(7));
                    plane.setRange(resultSet.getInt(8));
                    plane.setLocation(resultSet.getString(9));
                    freeFrom.setTimeInMillis(resultSet.getTimestamp(10).getTime());
                    plane.setFreeFrom(freeFrom);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return plane;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Plane plane) throws DAOException {
        return false;
    }

    @Override
    public boolean create(Plane plane) throws DAOException {
        return false;
    }

    @Override
    public Plane update(Plane plane) throws DAOException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        PreparedStatement preparedStatement;
        String maxDate = "00-00-00";
        try {
            if (findDomainById(plane.getPlaneId()) != null) {
                preparedStatement = connection.prepareStatement(QueryEnum.PREPARE_UPDATE_PLANE.getValue());
                preparedStatement.setInt(1, plane.getPlaneId());
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.isBeforeFirst()){
                    while(resultSet.next()) {
                        maxDate = resultSet.getString(1);
                    }
                } else {
                    maxDate = sdf.format(Calendar.getInstance().getTimeInMillis());
                }

                preparedStatement =
                        connection.prepareStatement(QueryEnum.UPDATE_PLANE.getValue());
                preparedStatement.setString(1, maxDate);
                preparedStatement.setInt(2, plane.getPlaneId());
                preparedStatement.executeUpdate();
                close(preparedStatement);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return plane;
    }

    @Override
    public boolean findDomain(Plane plane) throws DAOException {
        return false;
    }

    @Override
    public void close(Statement st) {
        super.close(st);
    }

}
