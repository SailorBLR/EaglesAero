/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.dispatcher.RefreshService;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.util.CreateFlightsUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to edit Flight
 * @see by.hubarevich.teammanager.domain.Flight
 * all parameters are attributes of request Object
 */

public class EditFlightCommand implements ActionCommand {

    public final static String LOCALE = "locale";
    public final static String ROLE = "role";
    public final static String FLIGHTS = "flights";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String role;

        List<Flight> flights = CreateFlightsUtil.getInstance().createFlightsToUpdate(request);
        try {
            for (Object flight : flights) {
                if (!RefreshService.getInstance().editFlight((Flight) flight)) {
                    request.setAttribute("errorMessage", MessageManagerWrapper
                            .getMessage("message.flighterror", request.getSession()
                                    .getAttribute(LOCALE).toString()));
                    break;
                }
            }
            role = request.getSession().getAttribute(ROLE).toString();
            RefreshService.getInstance().doRefresh();
            flights = CRUDService.getInstance().showAllDomains(role);
            RefreshService.getInstance().updateFlightTeamStatus(flights);
        } catch (LogicException e) {
            throw new CommandException(e);
        }

        request.setAttribute(FLIGHTS, flights);

        return ConfigurationManager.getProperty("path.page.mainDispatcher");
    }
}
