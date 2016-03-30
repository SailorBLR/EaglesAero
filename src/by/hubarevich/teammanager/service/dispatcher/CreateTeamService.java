/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.service.dispatcher;

import by.hubarevich.teammanager.dao.*;
import by.hubarevich.teammanager.dao.connection.ConnectionPool;
import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.domain.Plane;
import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.service.exception.LogicException;

import java.util.Arrays;
import java.util.List;

/**
 * Service-class, provides logic for team creating
 */

public class CreateTeamService {

    private static CreateTeamService instance = new CreateTeamService();

    public static CreateTeamService getInstance() {
        return instance;
    }

    private CreateTeamService() {}

    /**
     * Searches for an available Team Members to form the Flight Team
     * @param flightId String Flight unique identifier
     * @return List of TeamMember Objects
     * @throws LogicException if the DAOException was thrown
     */

    public List<TeamMember> getAvailableTeamMembers(String flightId) throws LogicException {

        List<TeamMember> teamMembersList;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            TeamMemberDAO teamMemberDAO = new TeamMemberDAO(connection);
            teamMembersList = teamMemberDAO.findAvailableTeamMembers(flightId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return teamMembersList;
    }

    /**
     * Searches for a plane model to get model parameters
     * @param model String Plane model
     * @return Plane object
     * @throws LogicException if the DAOException was thrown
     */

    public Plane getPlane(String model) throws LogicException {
        Plane plane;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            PlaneDAO planeDAO = new PlaneDAO(connection);
            plane = planeDAO.findPlaneByModel(model);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return plane;
    }

    /**
     * Creates team from String of TeamMember ID's and Flight ID
     * @param teamArray String of ID's, splitted by ","
     * @param flightNumber String Flight Identifier
     * @return true if the Team is successfully created
     * @throws LogicException if the DAOException was thrown
     */

    public boolean createTeam(String teamArray, String flightNumber) throws LogicException {
        List<String> items = Arrays.asList(teamArray.split("\\s*,\\s*"));
        boolean successCreate = false;
        for (String item : items) {

            try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
                FlightTeamDAO flightTeamDao = new FlightTeamDAO(connection);
                TeamMemberDAO teamMemberDAO = new TeamMemberDAO(connection);
                if (flightTeamDao.createFlightTeamByIds(item, flightNumber)
                        && (teamMemberDAO.updateTeamMemberFreeFrom(flightNumber, item))) {
                    successCreate = true;
                }
            } catch (DAOException e) {
                throw new LogicException(e);
            }
        }
        return successCreate;
    }
}
