package by.hubarevich.teammanager.service.dispatcher;

import by.hubarevich.teammanager.command.dispatcher.EditFlightFormCommand;
import by.hubarevich.teammanager.command.dispatcher.EditTeamFormCommand;
import by.hubarevich.teammanager.dao.FlightTeamDAO;
import by.hubarevich.teammanager.dao.TeamMemberDAO;
import by.hubarevich.teammanager.dao.connection.ConnectionPool;
import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.service.exception.LogicException;
import com.sun.jmx.remote.internal.ArrayQueue;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Anton on 12.03.2016.
 */
public class EditFlightTeamService {

    private static final Logger LOG = LogManager.getLogger(EditFlightTeamService.class);
    private static EditFlightTeamService instance;

    public static EditFlightTeamService getInstance() {
        instance = new EditFlightTeamService();
        return instance;
    }

    private EditFlightTeamService() {
    }

    public List<TeamMember> getFlightTeam(String flightId) throws LogicException {

        List<TeamMember> teamMembers;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightTeamDAO flightTeamDao = new FlightTeamDAO(connection);
            teamMembers = flightTeamDao.findFlightTeamByFlightId(flightId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return teamMembers;
    }

    public void editTeam(String teamArray, String flightNumber) throws LogicException {
        LOG.debug(teamArray + "\n" + flightNumber);
        List<String> items = Arrays.asList(teamArray.split("\\s*,\\s*"));
        List<TeamMember> teamMembers;
        boolean successCreate = false;



        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            FlightTeamDAO flighTeamDao = new FlightTeamDAO(connection);
            for (String item : items) {

                LOG.info (flighTeamDao.createFlightTeamByIds(item, flightNumber));
            }
            teamMembers = getFlightTeam(flightNumber);
            int counter;
            for(int i = 0; i<teamMembers.size();i++) {
                counter = 0;
                for (String item : items) {
                   if(teamMembers.get(i).getPersonId().equals(Integer.parseInt(item))){
                       counter++;
                   }
                }
                if(counter == 0){
                    flighTeamDao.deleteTeamMemberFromFlight(teamMembers.get(i).getPersonId().toString(),flightNumber);
                    LOG.info(teamMembers.get(i).getPersonId() + " member deleted");
                }

            }

        } catch (DAOException e) {
            throw new LogicException(e);
        }
    }
}
