/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.dao;

import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.domain.Domain;
import by.hubarevich.teammanager.domain.TeamMember;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class extends abstract Class AbstractDAO and defines methods for dispatcher requests to operate with flight team
 * @see by.hubarevich.teammanager.dao.AbstractDAO
 * @see by.hubarevich.teammanager.domain.User
 * @see by.hubarevich.teammanager.domain.Flight
 */

public class FlightTeamDAO extends AbstractDAO {


    public FlightTeamDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * search for a flight team field in DB according to teamMemberId and flightId
     * @param teamMemberId String unique Flight identifier
     * @param flightId int unique TeamMember identifier
     * @return true if the flight team is exist
     * @throws DAOException if the SQLException where thrown
     */

    public boolean findFlightTeamByIds(String teamMemberId, String flightId) throws DAOException {
        boolean successSearch = false;

        try (PreparedStatement preparedStatement = connection
                .prepareStatement(QueryEnum.FIND_IN_FLIGHT_TEAM.getValue())) {

            preparedStatement.setString(1, flightId);
            preparedStatement.setString(2, teamMemberId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                successSearch = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return successSearch;
    }

    /**
     * creates flight team field in DB according to teamMemberId and flightId
     * @param teamMemberId String unique Flight identifier
     * @param flightId int unique TeamMember identifier
     * @return true if the flight created successfully
     * @throws DAOException if the SQLException where thrown
     */

    public boolean createFlightTeamByIds(String teamMemberId, String flightId) throws DAOException {

        boolean successCreate = false;

        if (!findFlightTeamByIds(teamMemberId, flightId)) {
            try (PreparedStatement preparedStatement = connection
                    .prepareStatement(QueryEnum.CREATE_FLIGHT_TEAM.getValue())) {
                preparedStatement.setString(1, flightId);
                preparedStatement.setString(2, teamMemberId);

                preparedStatement.executeUpdate();
                successCreate = true;

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return successCreate;
    }

    /**
     * searches for a flight team by the flight id
     * @param flightId String unique Flight identifier
     * @return List of Team members
     * @throws DAOException if the SQLException where thrown
     */

    public List<TeamMember> findFlightTeamByFlightId(String flightId) throws DAOException {
        List<TeamMember> teamMembers = new ArrayList<>();
        TeamMember teamMember;
        try (PreparedStatement preparedStatement = connection.prepareStatement
                (QueryEnum.FIND_FLIGHT_TEAM_BY_FLIGHT.getValue())) {
            preparedStatement.setString(1, flightId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    teamMember = new TeamMember();
                    teamMember.setPersonId(resultSet.getInt(2));
                    teamMember.setName(resultSet.getString(3));
                    teamMember.setSurname(resultSet.getString(4));
                    teamMember.setRole(resultSet.getString(5));
                    teamMember.setQualification(resultSet.getString(6));
                    teamMember.setStatus(resultSet.getString(7));
                    teamMembers.add(teamMember);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return teamMembers;
    }

    /**
     * deletes Team member from flight team
     * @param personId int unique TeamMember identifier
     * @param flightId String unique Flight identifier
     * @return true if the flight created successfully
     * @throws DAOException if the SQLException where thrown
     */

    public boolean deleteTeamMemberFromFlight(String personId, String flightId)throws DAOException{
        try (PreparedStatement preparedStatement=connection
                .prepareStatement(QueryEnum.DELETE_PERSON_FROM_FLIGHT_TEAM.getValue())) {
            preparedStatement.setString(1,personId);
            preparedStatement.setString(2,flightId);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            throw new DAOException(e);
        }
        return true;
    }

    @Override
    public List findAll() throws DAOException {
        return null;
    }

    @Override
    public Domain findDomainById(int id) throws DAOException {
        return null;
    }

    @Override
    public boolean delete(int id) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(Domain domain) throws DAOException {
        return false;
    }

    @Override
    public boolean create(Domain domain) throws DAOException {
        return false;
    }

    @Override
    public Domain update(Domain domain) throws DAOException {
        return null;
    }

    @Override
    public boolean findDomain(Domain domain) throws DAOException {
        return false;
    }
}