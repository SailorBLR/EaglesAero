/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.login;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Class implements interface ActionCommand and gives the command to perform Log-out
 * @see by.hubarevich.teammanager.command.ActionCommand
 * @see by.hubarevich.teammanager.domain.TeamMember
 * all parameters are attributes of request Object
 */

public class LogoutCommand implements ActionCommand {

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     */

    @Override
    public String execute(HttpServletRequest request) {
        // уничтожение сессии
        request.getSession().invalidate();
        return ConfigurationManager.getProperty("path.page.index");
    }
}
