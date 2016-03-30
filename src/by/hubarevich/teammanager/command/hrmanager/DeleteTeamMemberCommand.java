/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.hrmanager;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command to delete Team Member
 * @see by.hubarevich.teammanager.command.ActionCommand
 * @see by.hubarevich.teammanager.domain.TeamMember
 * all parameters are attributes of request Object
 */

public class DeleteTeamMemberCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String LOCALE = "locale";
    public final static String TEAM_MEMBER = "teammemberId";
    public final static String FLIGHT_CREW ="flightcrew";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {


        try {
            if (!CRUDService.getInstance()
                    .deleteDomain(Integer.parseInt(request.getParameter(TEAM_MEMBER)),request.getSession()
                            .getAttribute(ROLE).toString())) {

                request.setAttribute("personErrorMessage", MessageManagerWrapper
                        .getMessage("message.deleterror",request.getSession()
                        .getAttribute(LOCALE).toString()));
            }

            request.setAttribute(FLIGHT_CREW, CRUDService.getInstance()
                    .showAllDomains(request.getSession().getAttribute(ROLE).toString()));
        } catch (LogicException e) {
            throw new CommandException(e);
        }
        return ConfigurationManager.getProperty("path.page.mainHR");
    }
}
