/**Copyright (c) 2016, Anton Hubarevich. All rights reserved.**/

package by.hubarevich.teammanager.command.admin;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.domain.User;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.util.MD5Util;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command to create new User
 * @see by.hubarevich.teammanager.domain.User
 * all parameters are attributes of request Object
 */

public class CreateUserCommand implements ActionCommand {
    public final static String ROLE = "role";
    public final static String LOCALE = "locale";
    public final static String USERS = "users";
    public final static String NAME = "name";
    public final static String SURNAME = "surname";
    public final static String LOGIN = "login";
    public final static String PASSWORD = "password";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */
    @Override
    public String execute(HttpServletRequest request) throws CommandException{
        User user = new User();
        user.setName(request.getParameter(NAME));
        user.setSurname(request.getParameter(SURNAME));
        user.setLogin(request.getParameter(LOGIN));
        user.setPassword(MD5Util.md5CodePassword(request.getParameter(PASSWORD)));
        user.setRole(request.getParameter(ROLE));


        try {
            if (!CRUDService.getInstance().addDomain(user,request.getSession().getAttribute(ROLE).toString())) {
                request.setAttribute("userErrorMessage", MessageManagerWrapper
                        .getMessage("message.userexist",request.getSession().getAttribute(LOCALE).toString()));
            }

            request.setAttribute(USERS,
                    CRUDService.getInstance().showAllDomains(request.getSession().getAttribute(ROLE).toString()));


        } catch (LogicException e) {
            throw new CommandException(e);
        }
        return ConfigurationManager.getProperty("path.page.mainAdmin");
    }
}
