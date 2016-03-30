/**Copyright (c) 2016, Anton Hubarevich. All rights reserved.**/

package by.hubarevich.teammanager.command.admin;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.domain.User;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.util.MD5Util;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command to delete User
 * @see by.hubarevich.teammanager.domain.User
 * all parameters are attributes of request Object
 */

public class DeleteUserCommand implements ActionCommand {
    public final static String ROLE = "role";
    public final static String LOCALE = "locale";
    public final static String USERS = "users";
    public final static String USERID = "userId";
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
        int userId;
        String role = request.getSession().getAttribute(ROLE).toString();

        try {
            user.setLogin(request.getSession().getAttribute(LOGIN).toString());
            user.setPassword(MD5Util.md5CodePassword(request.getParameter(PASSWORD)));

            userId = Integer.parseInt(request.getParameter(USERID).trim());

            if (!CRUDService.getInstance().checkEditorsData(user)||!CRUDService.getInstance()
                    .deleteDomain(userId,role)) {

                request.setAttribute("userExistMessage",
                        MessageManagerWrapper.getMessage("message.deleterror",request.getSession()
                                .getAttribute(LOCALE).toString()));
            }

            request.setAttribute(USERS, CRUDService.getInstance().showAllDomains(role));
        } catch (LogicException e) {
            throw new CommandException (e);
        }
        return ConfigurationManager.getProperty("path.page.mainAdmin");
    }
}
