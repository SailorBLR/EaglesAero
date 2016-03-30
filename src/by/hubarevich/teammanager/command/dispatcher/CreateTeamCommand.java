/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.service.dispatcher.CreateTeamService;
import by.hubarevich.teammanager.service.dispatcher.RefreshService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to create Flight team
 * @see by.hubarevich.teammanager.domain.Flight
 * all parameters are attributes of request Object
 */

public class CreateTeamCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String LOCALE = "locale";
    public final static String FLIGHTS = "flights";
    public final static String LIST_ID = "listId";
    public final static String FLIGHT_ID = "flightId";
    public final static String BACK_FLIGHT_ID = "backflight";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        try {

            if (!CreateTeamService.getInstance().createTeam
                    (request.getParameter(LIST_ID), request.getParameter(FLIGHT_ID)) & !CreateTeamService
                    .getInstance().createTeam(request.getParameter(LIST_ID), request.getParameter(BACK_FLIGHT_ID))) {

                request.setAttribute("errorMessage", MessageManagerWrapper
                        .getMessage("message.errorcreateteam",
                        request.getSession().getAttribute(LOCALE).toString()));
            }

            RefreshService.getInstance().doRefresh();
            List flights = CRUDService.getInstance().showAllDomains(request.getSession()
                    .getAttribute(ROLE).toString());
            RefreshService.getInstance().updateFlightTeamStatus(flights);
            request.setAttribute(FLIGHTS, flights);

        } catch (LogicException e) {
            throw new CommandException(e);
        }


        return ConfigurationManager.getProperty("path.page.mainDispatcher");
    }
}
