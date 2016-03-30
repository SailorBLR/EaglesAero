/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.dispatcher.CreateTeamService;
import by.hubarevich.teammanager.service.dispatcher.EditFlightTeamService;
import by.hubarevich.teammanager.service.exception.LogicException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to create edit Flight Team form
 * @see by.hubarevich.teammanager.command.ActionCommand
 * @see by.hubarevich.teammanager.domain.TeamMember
 * all parameters are attributes of request Object
 */

public class EditTeamFormCommand implements ActionCommand {
    public final static String LOCALE = "locale";
    public final static String DIRECT_FLIGHT = "directFlight";
    public final static String BACK_FLIGHT = "backFlight";
    public final static String TEAM_STATUS = "teamStatus";
    public final static String FLIGHT_TEAM = "flightTeam";
    public final static String AVAILABLE_PERSONS = "teamMembers";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        List<TeamMember> flightTeam = null;
        List<TeamMember> availableTeamMembers = null;
        String teamFormed = MessageManagerWrapper
                .getMessage("data.team-status",
                request.getSession().getAttribute(LOCALE).toString());
        try {
            if (teamFormed.equals(request.getParameter(TEAM_STATUS))) {
                flightTeam = EditFlightTeamService.getInstance().getFlightTeam(request.getParameter(DIRECT_FLIGHT));
                availableTeamMembers = CreateTeamService.getInstance()
                        .getAvailableTeamMembers(request.getParameter(DIRECT_FLIGHT));
            }
        } catch (LogicException e) {
            throw new CommandException(e);
        }

        request.setAttribute(FLIGHT_TEAM, flightTeam);
        request.setAttribute(AVAILABLE_PERSONS, availableTeamMembers);
        request.setAttribute(DIRECT_FLIGHT, request.getParameter(DIRECT_FLIGHT));
        request.setAttribute(BACK_FLIGHT, request.getParameter(BACK_FLIGHT));
        return ConfigurationManager.getProperty("path.page.editTeam");
    }
}
