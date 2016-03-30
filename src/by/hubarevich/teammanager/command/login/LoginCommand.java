/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command.login;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.domain.User;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.CRUDService;
import by.hubarevich.teammanager.service.LoginService;
import by.hubarevich.teammanager.service.dispatcher.RefreshService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command to Log-in
 * @see by.hubarevich.teammanager.command.ActionCommand
 * @see by.hubarevich.teammanager.domain.User
 * all parameters are attributes of request Object
 */

public class LoginCommand implements ActionCommand {

    public final static String PARAM_NAME_LOGIN = "login";
    public final static String PARAM_NAME_PASSWORD = "password";
    public final static String ADMIN_ROLE = "admin";
    public final static String HR_ROLE = "hr-manager";
    public final static String DISPATCHER_ROLE = "dispatcher";
    public final static String ROLE = "role";
    public final static String LOCALE = "locale";
    public final static String FLIGHTS = "flights";
    public final static String USERS = "users";
    public final static String FLIGHT_CREW = "flightcrew";
    public final static String GUEST = "guest";

    private String role;
    private List domains;
    private String include;

    /**
     *
     * @param request get HTTP request from controller
     * @return address of defined include
     * @throws CommandException if the LogicException have been caught
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        String login = request.getParameter(PARAM_NAME_LOGIN);
        String pass = request.getParameter(PARAM_NAME_PASSWORD);
        role = request.getSession().getAttribute(ROLE).toString();

        try {
            if(login==null&pass==null&&role.equals(GUEST)) {
                return ConfigurationManager.getProperty("path.page.mainLogin");
            }

            if(!GUEST.equals(role)) {
                domains = CRUDService.getInstance().showAllDomains(role);
                return defineRoleAction(request,role);
            }
            if(!checkLogin(request,login,pass)){
                role = GUEST;
                include = ConfigurationManager.getProperty("path.page.mainLogin");
                request.setAttribute("errorLoginPassMessage",
                        MessageManagerWrapper
                                .getMessage("message.loginerror",
                                        request.getSession().getAttribute(LOCALE).toString()));
                return include;
            }

            include = defineRoleAction(request,role);
            domains = CRUDService.getInstance().showAllDomains(role);
            request.getSession().setAttribute(ROLE,role);

        } catch (LogicException e) {
            throw new CommandException(e);
        }

        return include;
    }

    /**
     * Verifies inputed login and password
     * @param request
     * @param login
     * @param password
     * @return true if login and password are correct
     * @throws LogicException
     */

    private boolean checkLogin(HttpServletRequest request, String login, String password) throws LogicException {
        User user;
        if (LoginService.getInstance().checkLogin(login, password)) {
            user = LoginService.getInstance().getUser(login);
            request.getSession().setAttribute(PARAM_NAME_LOGIN, user.getLogin());
            role = user.getRole().toLowerCase();
            domains = CRUDService.getInstance().showAllDomains(role);
            return true;
        } else {

            return false;
        }
    }

    /**
     * defines what logic must be performed, based on user role
     * @param request
     * @param role
     * @return defined include
     * @throws LogicException
     */

    private String defineRoleAction (HttpServletRequest request, String role) throws LogicException {
        switch (role) {
            case (ADMIN_ROLE):
                request.setAttribute(USERS, domains);
                include = ConfigurationManager.getProperty("path.page.mainAdmin");
                break;
            case (HR_ROLE):
                request.setAttribute(FLIGHT_CREW, domains);
                include = ConfigurationManager.getProperty("path.page.mainHR");
                break;
            case (DISPATCHER_ROLE):
                RefreshService.getInstance().doRefresh();
                domains = CRUDService.getInstance().showAllDomains(role);
                RefreshService.getInstance().updateFlightTeamStatus(domains);
                request.setAttribute(FLIGHTS, domains);
                include = ConfigurationManager.getProperty("path.page.mainDispatcher");
                break;
            default:
                include = ConfigurationManager.getProperty("path.page.mainLogin");
                request.setAttribute("errorLoginPassMessage",
                        MessageManagerWrapper
                                .getMessage("message.roleerror",
                                        request.getSession().getAttribute(LOCALE).toString()));
                break;
        }

        return include;
    }
}
