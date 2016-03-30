/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.domain.Plane;
import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.service.dispatcher.CreateTeamService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to create Flight team creating form
 * @see by.hubarevich.teammanager.domain.Plane
 * @see by.hubarevich.teammanager.domain.TeamMember
 * all parameters are attributes of request Object
 */

public class CreateTeamFormCommand implements ActionCommand {
    public final static String ROLE = "role";
    public final static String LOCALE = "locale";
    public final static String TEAM_MEMBERS = "teamMembers";
    public final static String STUART = "stuart";
    public final static String TECHNIC = "technic";
    public final static String PILOT = "pilot";
    public final static String PLANE = "plane";
    public final static String ID_VALUE = "idvalue";
    public final static String BACK_ID_VALUE = "backFlight";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        try {

            List<TeamMember> teamMemberList = CreateTeamService.getInstance()
                    .getAvailableTeamMembers(request.getParameter(ID_VALUE));
            Plane plane = CreateTeamService.getInstance().getPlane(request.getParameter(PLANE));
            if (plane == null) {
                request.setAttribute("errorMessage",
                        MessageManagerWrapper
                                .getMessage("message.planeerror",
                                        request.getSession().getAttribute(LOCALE).toString()));
                return ConfigurationManager.getProperty("path.page.mainDispatcher");
            }

            if (teamMemberList == null) {
                request.setAttribute("errorMessage",
                        MessageManagerWrapper
                                .getMessage("message.team-members-error",
                                        request.getSession().getAttribute("locale").toString()));
                return ConfigurationManager.getProperty("path.page.mainDispatcher");
            }


            request.setAttribute(TEAM_MEMBERS, teamMemberList);


            request.setAttribute(ID_VALUE, request.getParameter(ID_VALUE));
            request.setAttribute(BACK_ID_VALUE, request.getParameter(BACK_ID_VALUE));
            request.setAttribute(PLANE, plane.getPlaneName());
            request.setAttribute(PILOT, plane.getPilot());
            request.setAttribute(TECHNIC, plane.getTechnic());
            request.setAttribute(STUART, plane.getStuart());

        } catch (LogicException e) {
            throw new CommandException(e);
        }

        return ConfigurationManager.getProperty("path.page.dispatcherTeam");
    }
}