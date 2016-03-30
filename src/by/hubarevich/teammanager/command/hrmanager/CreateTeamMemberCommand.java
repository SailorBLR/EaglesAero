/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.hrmanager;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Class implements interface ActionCommand and gives the command to create Team Member
 * @see by.hubarevich.teammanager.command.ActionCommand
 * @see by.hubarevich.teammanager.domain.TeamMember
 * all parameters are attributes of request Object
 */

public class CreateTeamMemberCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String LOCALE = "locale";
    public final static String NAME = "name";
    public final static String SURNAME = "surname";
    public final static String DATE_FORMAT = "yyyy-mm-dd";
    public final static String QUALIFICATION = "qualification";
    public final static String BIRTHDAY = "date-of-birth";
    public final static String FLIGHT_CREW ="flightcrew";
    public final static String CITY = "city";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException{
        GregorianCalendar gregorianCalendar = new GregorianCalendar();

        TeamMember teamMember = new TeamMember();
        try {

            teamMember.setName(request.getParameter(NAME));
            teamMember.setSurname(request.getParameter(SURNAME));
            teamMember.setRole(request.getParameter(ROLE));
            teamMember.setQualification(request.getParameter(QUALIFICATION));
            teamMember.setCityCode(request.getParameter(CITY));
            gregorianCalendar.setTime(new SimpleDateFormat(DATE_FORMAT).parse(request.getParameter(BIRTHDAY)));
            teamMember.setDateOfBirth(gregorianCalendar);

            if (!CRUDService.getInstance().addDomain(teamMember,request.getSession().getAttribute(ROLE).toString())) {
                request.setAttribute("personErrorMessage", MessageManagerWrapper
                        .getMessage("message.error.team-member-error",
                                request.getSession().getAttribute(LOCALE).toString()));
            }

            request.setAttribute(FLIGHT_CREW,
                    CRUDService.getInstance().showAllDomains(request.getSession().getAttribute(ROLE).toString()));


        } catch (ParseException |LogicException e) {
            throw new CommandException(e);
        }

        return ConfigurationManager.getProperty("path.page.mainHR");
    }
}
