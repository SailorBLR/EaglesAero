/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command;

import by.hubarevich.teammanager.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command to redirect to Login page
 * and set User role as the "Guest"
 * @see by.hubarevich.teammanager.command.ActionCommand
 * all parameters are attributes of request Object
 */

public class GuestCommand implements ActionCommand{

    /**
     * @param request get HTTP request from controller
     * @return address of Login page
     */

    @Override
    public String execute(HttpServletRequest request) {

        request.getSession().setAttribute("role","guest");

        return ConfigurationManager.getProperty("path.page.mainLogin");
    }
}
