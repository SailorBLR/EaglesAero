/**Copyright (c) 2016, Anton Hubarevich. All rights reserved.**/

package by.hubarevich.teammanager.command.admin;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.domain.User;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command to create User edit form
 * @see by.hubarevich.teammanager.domain.User
 * all parameters are attributes of request Object
 */

public class EditUserFormCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String USERID = "userId";
    public final static String USERS = "users";

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException{
        User user;
        String include;

        try {

            if(request.getParameter(USERID)==null) {
                include = ConfigurationManager.getProperty("path.page.mainAdmin");
                request.setAttribute(USERS,CRUDService
                        .getInstance().showAllDomains(request.getSession().getAttribute(ROLE).toString()));
                request.setAttribute("userErrorMessage", MessageManagerWrapper
                        .getMessage("message.editerror",request.getSession().getAttribute(ROLE).toString()));
            } else {
                user = (User) CRUDService.getInstance
                        ().showDomainToEdit(
                        (Integer.valueOf(request.getParameter(USERID))),request.getSession()
                                .getAttribute(ROLE).toString());
                request.setAttribute(USERID,user);
                include=ConfigurationManager.getProperty("path.page.adminEdit");
            }

        } catch (LogicException e) {
            throw new CommandException(e);
        }


        return include;
    }
}
