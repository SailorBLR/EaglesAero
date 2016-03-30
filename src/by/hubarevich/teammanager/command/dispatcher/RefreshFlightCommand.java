/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */


package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.dispatcher.RefreshService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to refresh flight information
 * @see by.hubarevich.teammanager.command.ActionCommand
 * all parameters are attributes of request Object
 */

public class RefreshFlightCommand implements ActionCommand {

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
        List flights;

        try {
            RefreshService.getInstance().doRefresh();
            role = request.getSession().getAttribute(ROLE).toString();
            flights = CRUDService.getInstance().showAllDomains(role);
            RefreshService.getInstance().updateFlightTeamStatus(flights);

        } catch (LogicException e) {
            throw new CommandException(e);
        }

        request.setAttribute(FLIGHTS, flights);

        return ConfigurationManager.getProperty("path.page.mainDispatcher");
    }
}
