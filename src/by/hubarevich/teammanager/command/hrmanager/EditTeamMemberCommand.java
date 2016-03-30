/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.hrmanager;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.domain.User;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.util.MD5Util;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command to edit Team Member
 * @see by.hubarevich.teammanager.command.ActionCommand
 * @see by.hubarevich.teammanager.domain.TeamMember
 * all parameters are attributes of request Object
 */

public class EditTeamMemberCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String LOCALE = "locale";
    public final static String NAME = "name";
    public final static String SURNAME = "surname";
    public final static String ID = "id";
    public final static String QUALIFICATION = "qualification";
    public final static String FLIGHT_CREW ="flightcrew";
    public final static String STATUS = "current-status";
    public final static String LOGIN = "login";
    public final static String ADMIN_PASSWORD = "adminpassword";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        TeamMember teamMember = new TeamMember();
        User editor = new User();

        teamMember.setPersonId(Integer.valueOf(request.getParameter(ID)));
        teamMember.setName(request.getParameter(NAME));
        teamMember.setSurname(request.getParameter(SURNAME));
        teamMember.setQualification(request.getParameter(QUALIFICATION));
        teamMember.setRole(request.getParameter(ROLE));
        teamMember.setStatus(request.getParameter(STATUS));

        editor.setLogin(request.getSession().getAttribute(LOGIN).toString());
        editor.setPassword(MD5Util.md5CodePassword(request.getParameter(ADMIN_PASSWORD)));
        editor.setRole(request.getSession().getAttribute(ROLE).toString());

        try {

            if (!CRUDService.getInstance()
                    .editDomain(teamMember,editor)) {
                request.setAttribute("personErrorMessage", MessageManagerWrapper
                        .getMessage("message.editerror",request.getSession()
                        .getAttribute(LOCALE).toString()));
            }

            request.setAttribute(FLIGHT_CREW,
                    CRUDService.getInstance().showAllDomains(request.getSession().getAttribute(ROLE).toString()));
        } catch (LogicException e) {
            throw new CommandException(e);
        }
        return ConfigurationManager.getProperty("path.page.mainHR");
    }
}
