/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.dispatcher.CreateFormService;
import by.hubarevich.teammanager.service.dispatcher.RefreshService;
import by.hubarevich.teammanager.util.CreateFlightsUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to create Flight
 * @see by.hubarevich.teammanager.domain.Flight
 * all parameters are attributes of request Object
 */

public class CreateFlightCommand implements ActionCommand {
    private static final Logger LOG = LogManager.getLogger(CreateFlightCommand.class);
    public final static String ROLE = "role";
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

        String role;
        List existFlights;
        List flightsToPaste;

        try {
            role = request.getSession().getAttribute(ROLE).toString();
            existFlights = CRUDService.getInstance().showAllDomains(role);

            flightsToPaste = CreateFlightsUtil.getInstance().buildFlightObjects(request, existFlights);

            for (Object flight : flightsToPaste) {
                LOG.info(((Flight)flight).getFormattedDate(((Flight) flight).getArrivingTime()));

                if (!CreateFormService.getInstance().createNewFlight((Flight) flight)) {
                    request.setAttribute("errorMessage", MessageManagerWrapper
                            .getMessage("message.flighterror", request.getSession()
                                    .getAttribute(LOCALE).toString()));
                }
            }
            RefreshService.getInstance().doRefresh();

            existFlights = CRUDService.getInstance().showAllDomains(role);
            RefreshService.getInstance().updateFlightTeamStatus(existFlights);

        } catch (LogicException e) {
            throw new CommandException(e);
        }


        request.setAttribute(FLIGHTS, existFlights);

        return ConfigurationManager.getProperty("path.page.mainDispatcher");
    }
}