/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.dispatcher.EditFlightTeamService;
import by.hubarevich.teammanager.service.dispatcher.RefreshService;
import by.hubarevich.teammanager.service.exception.LogicException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to edit Flight Team
 * all parameters are attributes of request Object
 */

public class EditTeamCommand implements ActionCommand {

    public final static String LOCALE = "locale";
    public final static String DIRECT_FLIGHT = "directId";
    public final static String BACK_FLIGHT = "backId";
    public final static String FLIGHTS = "flights";
    public final static String ROLE = "role";
    public final static String STUFF_LIST = "stuffList";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        try {
            EditFlightTeamService.getInstance().editTeam(request.getParameter(STUFF_LIST),
                    request.getParameter(DIRECT_FLIGHT));
            EditFlightTeamService.getInstance().editTeam(request.getParameter(STUFF_LIST),
                    request.getParameter(BACK_FLIGHT));


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
