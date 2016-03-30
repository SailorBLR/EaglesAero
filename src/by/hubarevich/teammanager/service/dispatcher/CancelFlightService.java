/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.service.dispatcher;

import by.hubarevich.teammanager.dao.FlightDAO;
import by.hubarevich.teammanager.dao.connection.ConnectionPool;
import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.service.exception.LogicException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Service-class to maintain cancel action
 */

public class CancelFlightService {

    private static final Logger LOG = LogManager.getLogger(CancelFlightService.class);

    private static CancelFlightService instance = new CancelFlightService();

    public static CancelFlightService getInstance() {
        return instance;
    }

    /**
     * performs cancel action for Flight instance
     *
     * @param directFlightId String Direct Flight Identifier
     * @param backFlightId   String Back Flight Identifier
     * @param teamStatus     boolean true if the Team for Flight is formed
     * @return true if the action finished successfully
     * @throws LogicException if the DAOException was thrown
     */

    public boolean cancelFlight(String directFlightId, String backFlightId, boolean teamStatus) throws LogicException {
        boolean success;

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightDAO flightDao = new FlightDAO(connection);
            if (!teamStatus) {
                    success = (flightDao.cancelFlightTeamNotFormed(directFlightId) &&
                            (flightDao.cancelFlightTeamNotFormed(backFlightId)));
            } else {
                    success = (flightDao.cancelFlightTeamFormed(directFlightId) &&
                            (flightDao.cancelFlightTeamFormed(backFlightId)));
            }

        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return success;

    }
}
