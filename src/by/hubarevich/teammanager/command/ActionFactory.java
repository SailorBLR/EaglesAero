/**
 * Copyright (c) 2013, Ihar Blinou. All rights reserved.
 */

package by.hubarevich.teammanager.command;

import by.hubarevich.teammanager.resource.MessageManagerWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * Class-factory for command creating
 * all parameters are attributes of request Object
 */

public class ActionFactory {

    public final static String LOCALE = "locale";
    public final static String LAST_COMMAND = "lastcommand";
    public final static String COMMAND = "command";
    public final static String WRONG_ACTION  = "wrongAction";

    /**
     * defines the according to session attribute 'role'
     * @param request
     * @return defined command
     */

    public static ActionCommand defineCommand(HttpServletRequest request) {

        ActionCommand current = new EmptyCommand();
        String action = getCommandName(request);

        if (action == null || action.isEmpty()) {
            return current;
        }
        if (LOCALE.equals(action)) {
            action = request.getSession().getAttribute(LAST_COMMAND).toString();
        }
        try {

            CommandEnum currentEnum = CommandEnum.valueOf(action.toUpperCase());
            current = currentEnum.getCurrentCommand();
        } catch (IllegalArgumentException e) {
            request.setAttribute(WRONG_ACTION, action
                    + MessageManagerWrapper
                    .getMessage("message.wrongaction",request.getSession().getAttribute(LOCALE).toString()));
        }
        return current;
    }

    public static String getCommandName(HttpServletRequest request) {
        return request.getParameter(COMMAND);
    }
}
