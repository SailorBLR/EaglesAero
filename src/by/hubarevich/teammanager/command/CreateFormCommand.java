/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.command;

import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.domain.City;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.service.exception.LogicException;
import by.hubarevich.teammanager.service.hrmanager.TeamMemberDetailsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class implements interface ActionCommand and gives the command
 * to redirect to User or Team Member creating form
 * all parameters are attributes of request Object
 */

public class CreateFormCommand implements ActionCommand {
    public final static String ADMIN_ROLE = "admin";
    public final static String HR_ROLE = "hr-manager";
    public final static String ROLE = "role";

    /**
     * Defines User role and needed include
     * @param request get HTTP request from controller
     * @return address of Login page
     */

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        String include;
        String role;
        List<City> cities;
        role = request.getSession().getAttribute(ROLE).toString();
        try {
        switch (role) {
            case ADMIN_ROLE:
                include = ConfigurationManager.getProperty("path.page.adminCreate");
                break;
            case HR_ROLE:
                cities = TeamMemberDetailsService.getInstance().getAllCities();
                request.setAttribute("cities",cities);
                include = ConfigurationManager.getProperty("path.page.hrCreate");
                break;
            default:
                include = ConfigurationManager.getProperty("path.page.mainLogin");
                break;
        }

        } catch (LogicException e) {
            throw new CommandException(e);
        }
        return include;
    }
}
