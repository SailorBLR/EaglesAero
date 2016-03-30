/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 **/

package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.dispatcher.CreateFormService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to create form for Flight creating
 * all parameters are attributes of request Object
 */

public class CreateFlightFormCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String LOCALE = "locale";
    public final static String PLANES = "planes";
    public final static String CITIES = "cities";
    public final static String FLIGHTS = "flights";



    @Override
    public String execute(HttpServletRequest request) throws CommandException{

        List cities, planes, flights;
        String include;

        try {
            cities = CreateFormService.getInstance().createCityList();
            planes = CreateFormService.getInstance().createPlaneList();
            flights = CRUDService.getInstance().showAllDomains(request.getSession().getAttribute(ROLE).toString());

        } catch (LogicException e) {
            throw new CommandException(e);
        }

        if (cities != null & planes != null) {
            request.setAttribute(CITIES, cities);
            request.setAttribute(PLANES, planes);
            request.setAttribute(FLIGHTS, flights);
            include = ConfigurationManager.getProperty("path.page.dispatcherCreate");
        } else {
            request.setAttribute("errorMessage",
                    MessageManagerWrapper
                            .getMessage("message.nullpage",request.getSession().getAttribute(LOCALE).toString()));
            request.setAttribute(FLIGHTS, flights);
            include = ConfigurationManager.getProperty("path.page.mainDispatcher");
        }

        return include;
    }
}
