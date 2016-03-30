/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command;

import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command
 * to redirect main page, according to User Role
 * all parameters are attributes of request Object
 */

public class BackToMainCommand implements ActionCommand {

    public final static String ROLE = "role";
    public final static String ADMIN_ROLE = "admin";
    public final static String HR_ROLE = "hr-manager";
    public final static String DISPATCHER_ROLE = "dispatcher";

    /**
     * Makes a choice between includes according to user role
     * @param request HTTPRequest object
     * @return include for main page
     * @throws CommandException
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String include;
        String role = request.getSession().getAttribute(ROLE).toString();

        switch (role) {
            case ADMIN_ROLE:
                include = ConfigurationManager.getProperty("path.page.mainAdmin");
                break;
            case HR_ROLE:
                include = ConfigurationManager.getProperty("path.page.mainHR");
                break;
            case DISPATCHER_ROLE:
                include = ConfigurationManager.getProperty("path.page.mainDispatcher");
                break;
            default:
                include = ConfigurationManager.getProperty("path.page.mainLogin");
                break;
        }

        return include;
    }
}
