/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.dao;

import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.service.dispatcher.StatusEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Class extends abstract Class AbstractDAO and defines methods for dispatcher requests to Database
 *
 * @see by.hubarevich.teammanager.domain.Flight
 */

public class FlightDAO extends AbstractDAO<Flight> {
    private final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
    private static final Logger LOG = LogManager.getLogger(FlightDAO.class);

    public FlightDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * updates Flight status according to departure and arriving dates
     *
     * @param flight class Flight
     * @return true if the update was successfully
     * @throws DAOException if SQLException where thrown
     */

    public boolean refreshTable(Flight flight) throws DAOException {
        boolean refreshSuccess = false;

        if (findDomainById(flight.getFlightId()) != null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QueryEnum.REFRESH_FLIGHT.getValue())) {

                preparedStatement.setString(1, flight.getStatus());
                preparedStatement.setString(2, flight.getFlightId());

                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
            refreshSuccess = true;
        }
        return refreshSuccess;
    }

    /**
     * searches for a fields in table 'flights' with the 'team_member_id' = id
     * @param id the unique identifier of the Object class TeamMember
     * @return List of Objects class flight
     * @throws DAOException if SQLException where thrown
     */

    public List<Flight> findFlightsByTeamMemberID(String id) throws DAOException {
        Flight flight;
        List<Flight> flights = new ArrayList<>();
        Calendar departTime;
        Calendar arrivingTime;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.FIND_ALL_TEAM_MEMBERS_FLIGHTS.getValue())) {

            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {

                    flight = new Flight();
                    departTime = new GregorianCalendar();
                    arrivingTime = new GregorianCalendar();
                    flight.setFlightId(resultSet.getString(1));
                    flight.setFlightFrom(resultSet.getString(2));
                    flight.setFlightTo(resultSet.getString(3));

                    departTime.setTimeInMillis(resultSet.getTimestamp(4).getTime());
                    flight.setDepartureTime(departTime);
                    arrivingTime.setTimeInMillis(resultSet.getTimestamp(5).getTime());
                    flight.setArrivingTime(arrivingTime);

                    flight.setFlightTime(flight.getArrivingTime().getTimeInMillis() -
                            flight.getDepartureTime().getTimeInMillis());
                    flight.setStatus(resultSet.getString(6));

                    flights.add(flight);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return flights;
    }

    /**
     * edits the field data according to Object Flight parameters
     * @param flight Flight object
     * @return true if the edit operation where successfully
     * @throws DAOException if SQLException where thrown
     */

    public boolean editFlight(Flight flight) throws DAOException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryEnum.EDIT_FLIGHT.getValue())) {

            if (findDomainById(flight.getFlightId()) != null) {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(flight.getArrivingTime().getTime());
                calendar.add(Calendar.DATE, 1);
                preparedStatement.setString(1, sdf.format(flight.getDepartureTime().getTime()));
                preparedStatement.setString(2, sdf.format(flight.getArrivingTime().getTime()));
                preparedStatement.setString(3, sdf.format(calendar.getTime()));
                preparedStatement.setString(4, sdf.format(calendar.getTime()));
                preparedStatement.setString(5, flight.getFlightId());

                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }

    /**
     * sets Flight status 'cancelled' for flights with no Flight team formed
     * @param flightId String unique flight identification number
     * @return true if the cancel operation where successfully
     * @throws DAOException if SQLException where thrown
     */

    public boolean cancelFlightTeamNotFormed(String flightId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                QueryEnum.CANCEL_FLIGHT_TEAM_NOT_FORMED.getValue())) {
            LOG.info(flightId);
            if (findDomainById(flightId) != null) {
                preparedStatement.setString(1, StatusEnum.CANCELLED.getValue());
                preparedStatement.setString(2, flightId);
                preparedStatement.executeUpdate();
                LOG.info("exec");
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }

    /**
     * sets Flight status 'cancelled' for flights with Flight team formed
     * @param flightId String unique flight identification number
     * @return true if the cancel operation where successfully
     * @throws DAOException if SQLException where thrown
     */

    public boolean cancelFlightTeamFormed(String flightId) throws DAOException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(QueryEnum.CANCEL_FLIGHT.getValue())) {
            if (findDomainById(flightId) != null) {
                preparedStatement.setString(1, StatusEnum.CANCELLED.getValue());
                preparedStatement.setString(2, flightId);
                preparedStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return false;
    }

    @Override
    public List<Flight> findAll() throws DAOException {

        List<Flight> flights = null;
        ResultSet resultSet;
        Calendar departTime, arrivingTime, inAirTime;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.SELECT_ALL_FROM_FLIGHTS.getValue())) {
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                flights = new ArrayList<>();
                while (resultSet.next()) {
                    Flight flight = new Flight();
                    departTime = new GregorianCalendar();
                    arrivingTime = new GregorianCalendar();
                    inAirTime = new GregorianCalendar();
                    flight.setFlightId(resultSet.getString(1));
                    flight.setFlightFrom(resultSet.getString(2));
                    flight.setFlightTo(resultSet.getString(3));

                    departTime.setTimeInMillis(resultSet.getTimestamp(4).getTime());
                    flight.setDepartureTime(departTime);
                    arrivingTime.setTimeInMillis(resultSet.getTimestamp(5).getTime());
                    flight.setArrivingTime(arrivingTime);

                    flight.setFlightTime(flight.getArrivingTime().getTimeInMillis() -
                            flight.getDepartureTime().getTimeInMillis());
                    flight.setPlane(resultSet.getString(6));
                    flight.setPlaneId(resultSet.getInt(7));
                    flight.setStatus(resultSet.getString(8));
                    flight.setFlightDistance(resultSet.getInt(9));
                    inAirTime.setTime(resultSet.getTimestamp(10));
                    flight.setFlightTime(inAirTime.getTimeInMillis());
                    flight.setDirection(resultSet.getString(11));

                    flights.add(flight);
                }
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return flights;
    }

    @Override
    public Flight findDomainById(int id) throws DAOException {
        return null;
    }

    /**
     * searches for a Flight field in DB by Flight Unique Identifier
     * @param id String Flight Identifier
     * @return Flight Object
     * @throws DAOException if the SQLException where thrown
     */

    public Flight findDomainById(String id) throws DAOException {
        Flight flight = null;
        Calendar departTime;
        Calendar arrivingTime;

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.SELECT_FLIGHT_BY_ID.getValue())) {

            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    flight = new Flight();
                    departTime = new GregorianCalendar();
                    arrivingTime = new GregorianCalendar();
                    flight.setFlightId(resultSet.getString(1));
                    flight.setFlightFrom(resultSet.getString(2));
                    flight.setFlightTo(resultSet.getString(3));

                    departTime.setTimeInMillis(resultSet.getTimestamp(4).getTime());
                    flight.setDepartureTime(departTime);
                    arrivingTime.setTimeInMillis(resultSet.getTimestamp(5).getTime());
                    flight.setArrivingTime(arrivingTime);

                    flight.setFlightTime(flight.getArrivingTime().getTimeInMillis() -
                            flight.getDepartureTime().getTimeInMillis());
                    flight.setPlane(resultSet.getString(6));
                    flight.setPlaneId(resultSet.getInt(7));
                    flight.setStatus(resultSet.getString(8));
                    flight.setDirection(resultSet.getString(9));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return flight;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Flight flight) throws DAOException {
        boolean deleteSuccess = false;
        String flightId;

        if (findDomainById(flight.getFlightId()) != null) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(QueryEnum.DELETE_FLIGHT.getValue())) {

                flightId = flight.getFlightId();
                preparedStatement.setString(1, flightId);
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
            deleteSuccess = true;
        }
        return deleteSuccess;
    }

    @Override
    public boolean create(Flight flight) throws DAOException {
        boolean createSuccess = false;
        final String TIME_FORMAT = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        SimpleDateFormat sdfTime = new SimpleDateFormat(TIME_FORMAT);

        if (findDomainById(flight.getFlightId()) == null) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(QueryEnum.CREATE_FLIGHT.getValue())) {

                preparedStatement.setString(1, flight.getFlightId());
                preparedStatement.setString(2, flight.getFlightFrom());
                preparedStatement.setString(3, flight.getFlightTo());
                preparedStatement.setString(4, sdf.format(flight.getDepartureTime().getTime()));
                preparedStatement.setString(5, sdf.format(flight.getArrivingTime().getTime()));
                preparedStatement.setString(6, flight.getPlane());
                preparedStatement.setString(7, flight.getStatus());
                preparedStatement.setInt(8, flight.getFlightDistance());
                preparedStatement.setString(9, sdfTime.format(flight.getFlightTime()));
                preparedStatement.setString(10, flight.getDirection());
                preparedStatement.executeUpdate();

            } catch (SQLException e) {
                throw new DAOException(e);
            }
            createSuccess = true;
        }
        return createSuccess;
    }

    @Override
    public Flight update(Flight flight) {
        return null;
    }

    @Override
    public boolean findDomain(Flight flight) throws DAOException {
        return false;
    }

    @Override
    public void close(Statement st) {
        super.close(st);
    }


}
