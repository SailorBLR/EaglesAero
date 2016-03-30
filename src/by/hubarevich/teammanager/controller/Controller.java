/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.controller;

import by.hubarevich.teammanager.command.ActionCommand;
import by.hubarevich.teammanager.command.ActionFactory;
import by.hubarevich.teammanager.dao.connection.ConnectionPool;
import by.hubarevich.teammanager.command.exception.CommandException;
import by.hubarevich.teammanager.resource.ConfigurationManager;
import by.hubarevich.teammanager.resource.MessageManagerWrapper;
import by.hubarevich.teammanager.util.UrlQueryManagerUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * application controller
 */

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private static final long serialVersionUID = 1L;


    /**
     * Initializes application servlet and creates Connection Pool
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool.getInstance();
    }

    /**
     * Destroys application servlet and Connection Pool
     */

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool.getInstance().closePoolConnections();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);

    }


    private void processRequest(HttpServletRequest request,
                                HttpServletResponse response)
            throws ServletException, IOException {
        final String query;
        final String mainPage = ConfigurationManager.getProperty("path.page.main");
        final String include;

        if (!RequestParametersNames.LOCALE.equals(request.getParameter(RequestParametersNames.COMMAND))) {
            query = UrlQueryManagerUtil.manageQuery(request);

            ActionCommand command = ActionFactory.defineCommand(request);
            try {

                include = command.execute(request);
                if (include.equals(ConfigurationManager.getProperty("path.page.index"))) {
                    response.sendRedirect(ConfigurationManager.getProperty("path.page.index"));
                    return;
                }

                if (include != null) {
                    request.setAttribute(RequestParametersNames.INCLUDE, include);
                    RequestDispatcher dispatcher = getServletContext()
                            .getRequestDispatcher(mainPage);
                    dispatcher.forward(request, response);
                    request.getSession().setAttribute(RequestParametersNames.LAST_QUERY, query);

                } else {

                    request.getSession().setAttribute(RequestParametersNames.NULL_PAGE,
                            MessageManagerWrapper
                                    .getMessage("message.nullpage", request.getSession()
                                            .getAttribute("locale").toString()));
                    response.sendRedirect(request.getContextPath()
                            + ConfigurationManager.getProperty("path.page.index"));
                }
            } catch (CommandException e) {
                LOG.error(e);
                response.sendError(500);
            }
        } else {
            String lastQuery = request.getSession().getAttribute(RequestParametersNames.LAST_QUERY).toString();
            request.getSession().setAttribute(RequestParametersNames.LOCALE,
                    request.getParameter(RequestParametersNames.NEW_LOCALE));
            response.sendRedirect(RequestParametersNames.CONTROLLER + lastQuery);
        }

    }
}
