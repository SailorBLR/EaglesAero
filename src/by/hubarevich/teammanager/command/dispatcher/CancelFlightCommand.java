/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 **/

package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.domain.User;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.dispatcher.CancelFlightService;
import by.hubarevich.teammanager.service.dispatcher.RefreshService;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.util.MD5Util;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to cancel Flight
 * @see by.hubarevich.teammanager.domain.Flight
 * @see by.hubarevich.teammanager.domain.User
 * all parameters are attributes of request Object
 */

public class CancelFlightCommand implements ActionCommand {

    private static final Logger LOG = LogManager.getLogger(CancelFlightCommand.class);

    public final static String ROLE = "role";
    public final static String LOGIN = "login";
    public final static String PASSWORD = "password";
    public final static String DIRECT_ID = "directId";
    public final static String BACK_ID = "backId";
    public final static String TEAM_STATUS = "teamStatus";
    public final static String LOCALE = "locale";
    public final static String FLIGHTS = "flights";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        User editor = new User();
        List<Flight> flights;
        String teamFormed = MessageManagerWrapper.getMessage("data.team-status",
                request.getSession().getAttribute(LOCALE).toString());
        boolean teamStatus = teamFormed.equals(request.getParameter(TEAM_STATUS));
        editor.setRole(request.getSession().getAttribute(ROLE).toString());
        editor.setLogin(request.getSession().getAttribute(LOGIN).toString());
        editor.setPassword(MD5Util.md5CodePassword(request.getParameter(PASSWORD)));
        try {
            if (!CRUDService.getInstance().checkEditorsData(editor) ||
                    !CancelFlightService.getInstance().cancelFlight(request.getParameter(DIRECT_ID),
                            request.getParameter(BACK_ID), teamStatus)) {
                request.setAttribute("errorMessage", MessageManagerWrapper.getMessage("message.error.cancel",
                        request.getSession().getAttribute(LOCALE).toString()));
            }
            RefreshService.getInstance().doRefresh();
            flights = CRUDService.getInstance().showAllDomains(request.getSession().getAttribute(ROLE).toString());
            RefreshService.getInstance().updateFlightTeamStatus(flights);
            request.setAttribute(FLIGHTS, flights);


        } catch (LogicException e) {
            throw new CommandException(e);
        }
        return ConfigurationManager.getProperty("path.page.mainDispatcher");
    }
}
