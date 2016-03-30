/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.service.hrmanager;

import by.hubarevich.teammanager.dao.CityDAO;
import by.hubarevich.teammanager.dao.FlightDAO;
import by.hubarevich.teammanager.dao.connection.ConnectionPool;
import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.domain.City;
import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.service.exception.LogicException;

import java.util.List;

/**
 * Service-class, provides logic to operate with TeamMember's data
 */

public class TeamMemberDetailsService {

    private static TeamMemberDetailsService instance = new TeamMemberDetailsService();

    public static TeamMemberDetailsService getInstance() {
        return instance;
    }

    private TeamMemberDetailsService() {}

    /**
     * Searches for all flights of TeamMember
     * @param teamMemberId int TeamMember identifier
     * @return List of Flights
     * @throws LogicException if the DAOException was thrown
     */

    public List<Flight> getAllFlightsOfTeamMember(String teamMemberId) throws LogicException {
        List<Flight> flights;

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightDAO flightDAO = new FlightDAO(connection);
            flights = flightDAO.findFlightsByTeamMemberID(teamMemberId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }

        return flights;
    }

    public List<City> getAllCities() throws LogicException {
        List<City> cities;

        try (ProxyConnection proxyConnection = ConnectionPool.getInstance().getConnection()) {
            CityDAO cityDAO = new CityDAO(proxyConnection);
            cities = cityDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return cities;
    }

}
