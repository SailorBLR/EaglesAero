/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 **/

package by.hubarevich.teammanager.command.admin;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.exception.LogicException;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command to confirm User deleting
 * @see by.hubarevich.teammanager.domain.User
 * all parameters are attributes of request Object
 */

public class DeleteConfirmCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String ID = "id";
    public final static String USERS = "users";
    public final static String LOGIN = "login";
    public final static String USER = "user";
    public final static String USER_ID = "userId";
    public final static String USER_NAME = "userName";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String include;

        try {
            if (request.getSession().getAttribute(LOGIN).equals(request.getParameter(LOGIN))) {
                request.setAttribute("userErrorMessage", MessageManagerWrapper
                        .getMessage("message.error.deleteyourself", request.getSession().getAttribute(ROLE).toString()));
                request.setAttribute(USERS,
                        CRUDService.getInstance().showAllDomains(request.getSession().getAttribute(ROLE).toString()));
                include = ConfigurationManager.getProperty("path.page.mainAdmin");
            } else {

                request.setAttribute(USER_NAME, request.getParameter(USER));
                request.setAttribute(USER_ID, request.getParameter(ID));
                include = ConfigurationManager.getProperty("path.page.adminConfirmDelete");
            }
        } catch (LogicException e) {
            throw new CommandException(e);
        }
        return include;
    }
}
