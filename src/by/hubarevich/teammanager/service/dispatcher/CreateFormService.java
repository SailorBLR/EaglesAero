/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.service.dispatcher;

import by.hubarevich.teammanager.dao.CityDAO;
import by.hubarevich.teammanager.dao.FlightDAO;
import by.hubarevich.teammanager.dao.PlaneDAO;
import by.hubarevich.teammanager.dao.connection.ConnectionPool;
import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.service.exception.LogicException;

import java.util.List;

/**
 * Service-class, defines form-creating logic
 */

public class CreateFormService {

    private static CreateFormService instance = new CreateFormService();

    public static CreateFormService getInstance() {
        return instance;
    }

    /**
     * Creates List of cities, available for flight creation
     * @return List of available cities
     * @throws LogicException if the DAOException was thrown
     */

    public List createCityList() throws LogicException {
        List dataList;
        try (ProxyConnection connection=ConnectionPool.getInstance().getConnection()) {
            dataList = new CityDAO(connection).findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return dataList;
    }

    /**
     * Creates List of planes, available for Flight creating
     * @return List of available planes
     * @throws LogicException if the DAOException was thrown
     */

    public List createPlaneList() throws LogicException {
        List dataList;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            dataList = new PlaneDAO(connection).findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return dataList;
    }

    /**
     * Creates new flight
     * @param flight Flight Object with new field parameters
     * @return true if the creation process finished successfully
     * @throws LogicException if the DAOException was thrown
     */

    public boolean createNewFlight(Flight flight) throws LogicException {
        boolean creationSuccess = false;

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()){
            FlightDAO flightDAO = new FlightDAO(connection);
            if (flightDAO.create(flight)) {
                creationSuccess = true;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return creationSuccess;
    }

    /**
     * Searches for a flight by Flight ID
     * @param flightId String Flight Identifier
     * @return Flight object, or null if it isn't found
     * @throws LogicException if the DAOException was thrown
     */

    public Flight getFlight (String flightId) throws LogicException{
        Flight flight;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightDAO flightDAO = new FlightDAO(connection);
            flight = flightDAO.findDomainById(flightId);

        } catch (DAOException e) {
            throw new LogicException(e);
        }

        return flight;
    }

}
