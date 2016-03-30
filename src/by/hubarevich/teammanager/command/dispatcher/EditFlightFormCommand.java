/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.dispatcher;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.dispatcher.CreateFormService;
import by.hubarevich.teammanager.service.exception.LogicException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to create edit Flight form
 * @see by.hubarevich.teammanager.domain.Flight
 * all parameters are attributes of request Object
 */

public class EditFlightFormCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String DIRECT = "directFlight";
    public final static String BACK = "backFlight";
    public final static String PLANES = "planes";
    public final static String TEAM_STATUS = "teamStatus";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException{

        List planes;
        Flight directFlight;
        Flight backFlight;
        String insert = null;

        try {
            directFlight = CreateFormService.getInstance().getFlight(request.getParameter(DIRECT));
            backFlight = CreateFormService.getInstance().getFlight(request.getParameter(BACK));
            planes = CreateFormService.getInstance().createPlaneList();

            if(directFlight==null||backFlight==null){
                request.setAttribute("errorMessage", MessageManagerWrapper
                        .getMessage("message.error.edit-flight",request.getSession()
                                .getAttribute(ROLE).toString()));
                insert = ConfigurationManager.getProperty("path.page.mainDispatcher");
            } else {
                request.setAttribute(DIRECT,directFlight);
                request.setAttribute(BACK,backFlight);
                request.setAttribute(PLANES,planes);
                request.setAttribute(TEAM_STATUS,request.getParameter(TEAM_STATUS));
                insert = ConfigurationManager.getProperty("path.page.dispatcherEdit");
            }
        } catch (LogicException e) {
            throw new CommandException(e);
        }
        return insert;
    }
}
