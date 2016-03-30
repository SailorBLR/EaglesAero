/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.dao;

import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.util.AvailableDistanceUtil;
import by.hubarevich.teammanager.util.InputedDataValidationUtil;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Class extends abstract Class AbstractDAO and defines
 * methods for hr-manager's and dispatcher's requests to Database
 *
 * @see by.hubarevich.teammanager.domain.TeamMember
 * @see by.hubarevich.teammanager.domain.Flight
 */

public class TeamMemberDAO extends AbstractDAO<TeamMember> {

    private final String DATE_FORMAT = "yyyy-mm-dd";

    public TeamMemberDAO(ProxyConnection connection) {
        super(connection);
    }

    /**
     * searches for Team Members available for booking on flight
     *
     * @param flightId String unique Flight Identifier
     * @return List of available Team Members
     * @throws DAOException if the SQLException where thrown
     */

    public List<TeamMember> findAvailableTeamMembers(String flightId) throws DAOException {

        List<TeamMember> teamMembersList = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement
                (QueryEnum.SELECT_AVAILABLE_TEAM_MEMBERS.getValue())) {
            preparedStatement.setString(1, flightId);
            preparedStatement.setString(2, flightId);
            preparedStatement.setString(3, flightId);


            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TeamMember teamMember = new TeamMember();
                teamMember.setPersonId(resultSet.getInt(1));
                teamMember.setName(resultSet.getString(2));
                teamMember.setSurname(resultSet.getString(3));
                teamMember.setRole(resultSet.getString(4));
                teamMember.setQualification(resultSet.getString(5));
                teamMembersList.add(teamMember);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return teamMembersList;
    }

    /**
     * updates the data of database field TeamMember according to Flight data
     *
     * @param flightId     String Flight unique identifier
     * @param teamMemberId int TeamMember unique identifier
     * @return true if the update where successfully
     * @throws DAOException if the SQLException where thrown
     */

    public boolean updateTeamMemberFreeFrom(String flightId, String teamMemberId) throws DAOException {

        boolean updateSuccess = false;

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(QueryEnum.UPDATE_TEAM_MEMBER_FREE_FROM.getValue())) {
            if (findDomainById(Integer.parseInt(teamMemberId)) != null) {
                preparedStatement.setString(1, flightId);
                preparedStatement.setString(2, teamMemberId);

                preparedStatement.executeUpdate();
                updateSuccess = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return updateSuccess;
    }

    @Override
    public List<TeamMember> findAll() throws DAOException {
        Calendar calendar;
        List<TeamMember> teamMembers = null;
        ResultSet resultSet;
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.SELECT_ALL_FROM_TEAM_MEMBER.getValue())) {

            resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                teamMembers = new ArrayList<>();
                while (resultSet.next()) {
                    calendar = new GregorianCalendar();
                    TeamMember teamMember = new TeamMember();
                    teamMember.setPersonId(resultSet.getInt(1));
                    teamMember.setName(resultSet.getString(2));
                    teamMember.setSurname(resultSet.getString(3));
                    teamMember.setRole(resultSet.getString(4));
                    teamMember.setQualification(resultSet.getString(5));
                    calendar.setTime(resultSet.getDate(6));
                    teamMember.setDateOfBirth(calendar);
                    teamMember.setStatus(resultSet.getString(7));
                    teamMembers.add(teamMember);
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return teamMembers;
    }

    @Override
    public TeamMember findDomainById(int id) throws DAOException {
        TeamMember teamMember = null;
        Calendar calendar = new GregorianCalendar();

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.SELECT_TEAM_MEMBER_BY_ID.getValue())) {

            preparedStatement.setString(1, String.valueOf(id));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.isBeforeFirst()) {
                while (resultSet.next()) {
                    teamMember = new TeamMember();
                    teamMember.setPersonId(resultSet.getInt(1));
                    teamMember.setName(resultSet.getString(2));
                    teamMember.setSurname(resultSet.getString(3));
                    teamMember.setRole(resultSet.getString(4));
                    teamMember.setQualification(resultSet.getString(5));
                    calendar.setTime(resultSet.getDate(6));
                    teamMember.setDateOfBirth(calendar);
                    teamMember.setStatus(resultSet.getString(7));
                }
            }

        } catch (SQLException e) {
            throw new DAOException();
        }
        return teamMember;
    }

    @Override
    public boolean delete(int id) throws DAOException {

        boolean deleteSuccess = false;
        if (findDomainById(id) != null) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(QueryEnum.DELETE_TEAM_MEMBER_BY_ID.getValue())) {
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
    public boolean delete(TeamMember teamMember) {
        return false;
    }

    @Override
    public boolean create(TeamMember teamMember) throws DAOException {
        boolean createSuccess = false;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        if (!findDomain(teamMember) && InputedDataValidationUtil.validateTeamMemberData(teamMember)) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(QueryEnum.INSERT_NEW_TEAM_MEMBER.getValue())) {

                preparedStatement.setString(1, teamMember.getName());
                preparedStatement.setString(2, teamMember.getSurname());
                preparedStatement.setString(3, teamMember.getRole());
                preparedStatement.setString(4, teamMember.getQualification());
                preparedStatement.setString(5, (sdf.format(teamMember.getDateOfBirth().getTime())));
                preparedStatement.setString(6, AvailableDistanceUtil
                        .calculateAvailableDistance(teamMember.getQualification()));
                preparedStatement.setString(7,teamMember.getCityCode());

                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            createSuccess = true;
        }
        return createSuccess;
    }

    @Override
    public TeamMember update(TeamMember teamMember) throws DAOException {
        TeamMember foundTeamMember = null;
        if ((findDomainById(teamMember.getPersonId()) != null) &&
                (InputedDataValidationUtil.validateTeamMemberData(teamMember))) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(QueryEnum.EDIT_TEAM_MEMBER.getValue())) {

                preparedStatement.setString(1, teamMember.getName());
                preparedStatement.setString(2, teamMember.getSurname());
                preparedStatement.setString(3, teamMember.getRole());
                preparedStatement.setString(4, teamMember.getQualification());
                preparedStatement.setString(6, String.valueOf(teamMember.getPersonId()));
                preparedStatement.setString(5, teamMember.getStatus());

                preparedStatement.executeUpdate();

                foundTeamMember = findDomainById(teamMember.getPersonId());

            } catch (SQLException e) {
                throw new DAOException(e);
            }

        }
        return foundTeamMember;
    }

    @Override
    public boolean findDomain(TeamMember teamMember) throws DAOException {
        boolean searchSuccess = false;
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(QueryEnum.SELECT_TEAM_MEMBER_IF_EXIST.getValue())) {
            preparedStatement.setString(1, teamMember.getName());
            preparedStatement.setString(2, teamMember.getSurname());
            preparedStatement.setString(3, sdf.format(teamMember.getDateOfBirth().getTime()));

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.isBeforeFirst()) {
                preparedStatement.close();
                searchSuccess = true;
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return searchSuccess;
    }
}
