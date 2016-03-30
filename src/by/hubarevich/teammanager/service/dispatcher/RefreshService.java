/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.service.dispatcher;

import by.hubarevich.teammanager.dao.FlightDAO;
import by.hubarevich.teammanager.dao.FlightTeamDAO;
import by.hubarevich.teammanager.dao.PlaneDAO;
import by.hubarevich.teammanager.dao.connection.ConnectionPool;
import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.service.exception.LogicException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.List;

/**
 * Service-class, provides logic to refresh Flight data
 */

public class RefreshService {

    private static final Logger LOG = LogManager.getLogger(RefreshService.class);
    private static RefreshService instance = new RefreshService();

    public static RefreshService getInstance() {
        return instance;
    }

    private RefreshService() {
    }

    /**
     * Searches for List of Flights and starts the update logic
     *
     * @throws LogicException if the DAOException was thrown
     */

    public void doRefresh() throws LogicException {
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            List<Flight> flights = getListOfFlights(connection);
            if (flights != null) {
                updateFlights(flights, connection);
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Updates Flight Team status of Flight
     *
     * @param flights List of all flights
     * @throws LogicException if the DAOException was thrown
     */

    public void updateFlightTeamStatus(List<Flight> flights) throws LogicException {

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightTeamDAO flightteamDao = new FlightTeamDAO(connection);
            for (Object flight : flights) {
                if (!flightteamDao.findFlightTeamByFlightId(((Flight) flight).getFlightId()).isEmpty()) {
                    ((Flight) flight).setFlightTeam(true);
                } else {
                    ((Flight) flight).setFlightTeam(false);
                }
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }

    /**
     * Searches for all flights
     *
     * @param connection ProxyConnection to get access to DB
     * @return List of Flights
     * @throws LogicException if the DAOException was thrown
     */

    private List<Flight> getListOfFlights(ProxyConnection connection) throws LogicException {
        List<Flight> flights;
        try {
            FlightDAO flightDAO = new FlightDAO(connection);
            flights = flightDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return flights;
    }

    /**
     * Updates Flight data
     *
     * @param flights    List of Flights to update
     * @param connection ProxyConnection to get access to DB
     * @throws DAOException
     */

    private void updateFlights(List<Flight> flights, ProxyConnection connection) throws DAOException,LogicException {
        final long DAY = 86400000L;
        FlightDAO flightDAO = new FlightDAO(connection);
        PlaneDAO planeDAO = new PlaneDAO(connection);
        final String DIRECTION = "back";
        for (Object flight : flights) {

            if (!StatusEnum.CANCELLED.getValue().equals(((Flight) flight).getStatus()) &&
                    !(StatusEnum.LANDED.getValue()).equals(((Flight) flight).getStatus())) {

                if (((Flight) flight).getDepartureTime().getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
                    ((Flight) flight).setStatus(StatusEnum.IN_FUTURE.getValue());
                }
                if (((Flight) flight).getDepartureTime().getTimeInMillis() < Calendar.getInstance().getTimeInMillis() &
                        ((Flight) flight).getArrivingTime().getTimeInMillis() >
                                Calendar.getInstance().getTimeInMillis()) {
                    ((Flight) flight).setStatus(StatusEnum.IN_AIR.getValue());
                }
                if (((Flight) flight).getArrivingTime().getTimeInMillis() < Calendar.getInstance().getTimeInMillis()) {
                    ((Flight) flight).setStatus(StatusEnum.LANDED.getValue());
                }
                flightDAO.refreshTable((Flight) flight);
                planeDAO.update(planeDAO.findDomainById(((Flight) flight).getPlaneId()));
            }
            if (StatusEnum.IN_FUTURE.getValue().equals(((Flight) flight).getStatus()) &&
                    getFlightTeamStatus(((Flight) flight).getFlightId()) &&
                    (((Flight) flight).getDepartureTime().getTimeInMillis()) <
                            (Calendar.getInstance().getTimeInMillis() + DAY)) {
                CancelFlightService.getInstance().cancelFlight(((Flight) flight).getFlightId(),
                        getBackFlight(((Flight) flight).getFlightId()),
                        !getFlightTeamStatus(((Flight) flight).getFlightId()));
            }
        }

    }

    /**
     * Edits Flight data
     *
     * @param flight Flight object with update data
     * @return true if the edit process finished successfully
     * @throws LogicException if the DAOException was thrown
     */

    public boolean editFlight(Flight flight) throws LogicException {
        boolean success = false;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightDAO flightDao = new FlightDAO(connection);
            if (flightDao.editFlight(flight)) {
                success = true;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return success;
    }

    /**
     * Checks Flight for Flight Team to be created
     *
     * @param flightId String Flight identifier
     * @return true if the Team is formed
     * @throws DAOException
     */

    private boolean getFlightTeamStatus(String flightId) throws DAOException {

        List<TeamMember> teamList;

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightTeamDAO flightTeamDao = new FlightTeamDAO(connection);
            teamList = flightTeamDao.findFlightTeamByFlightId(flightId);
        }
        return teamList.isEmpty();
    }

    private String getBackFlight(String directFlightId) throws DAOException {

        /*String backFlightId;
        backFlightId = directFlightId.substring(directFlightId.length() - 2);
        backFlightId = backFlightId
                .concat(directFlightId.substring(2, directFlightId.length() - 2));
        backFlightId = backFlightId.concat(directFlightId.substring(0, 2));
        Flight flight;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightDAO flightDao = new FlightDAO(connection);
            flight = flightDao.findDomainById(backFlightId);
        }
*/
        String backFlightId;
        backFlightId = directFlightId.substring(directFlightId.length() - 2);
        backFlightId = backFlightId
                .concat(directFlightId.substring(2, directFlightId.length() - 2));
        backFlightId = backFlightId.concat(directFlightId.substring(0, 2));
        /*Flight flight;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightDAO flightDao = new FlightDAO(connection);
            flight = flightDao.findDomainById(backFlightId);
        }*/
        return backFlightId;
    }
}
