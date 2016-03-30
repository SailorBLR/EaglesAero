/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command;

import by.hubarevich.teammanager.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Interface defines the behavior of Command classes
 */

public interface ActionCommand {
    /**
     *
     * @param request
     * @return
     */


    String execute(HttpServletRequest request) throws CommandException;
}
