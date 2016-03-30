/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command;

import by.hubarevich.teammanager.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command
 * to redirect to Login page in case of command errors
 * @see by.hubarevich.teammanager.command.ActionCommand
 * all parameters are attributes of request Object
 */

public class EmptyCommand implements ActionCommand {

    /**
     *
     * @param request get HTTP request from controller
     * @return address of Login page
     */

    @Override
    public String execute(HttpServletRequest request) {
        return ConfigurationManager.getProperty("path.page.mainLogin");
    }
}
