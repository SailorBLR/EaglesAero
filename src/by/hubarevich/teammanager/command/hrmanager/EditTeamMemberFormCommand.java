/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.hrmanager;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.hrmanager.TeamMemberDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to create Team Member edit form
 * @see by.hubarevich.teammanager.command.ActionCommand
 * @see by.hubarevich.teammanager.domain.TeamMember
 * all parameters are attributes of request Object
 */

public class EditTeamMemberFormCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String TEAM_MEMBER_ID = "personId";
    public final static String TEAM_MEMBER = "teamMember";
    public final static String FLIGHTS = "flights";
    public final static String NO_DATA = "no data";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException{
        TeamMember teamMember;
        List <Flight> flights;

        try {
            teamMember = (TeamMember) CRUDService.getInstance()
                    .showDomainToEdit(
                    (Integer.valueOf(request.getParameter(TEAM_MEMBER_ID))),
                    request.getSession().getAttribute(ROLE).toString());
            flights = TeamMemberDetailsService.getInstance()
                    .getAllFlightsOfTeamMember(request.getParameter(TEAM_MEMBER_ID));

        } catch (LogicException e) {
            throw new CommandException(e);
        }

        if(flights!=null) {
            request.setAttribute(FLIGHTS,flights);
        } else {
            request.setAttribute(FLIGHTS,NO_DATA);
        }

        request.setAttribute(TEAM_MEMBER, teamMember);

        return ConfigurationManager.getProperty("path.page.editTeamMember");
    }
}
