/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.service;

import by.hubarevich.teammanager.dao.AbstractDAO;
import by.hubarevich.teammanager.dao.AdminDAO;
import by.hubarevich.teammanager.dao.FlightDAO;
import by.hubarevich.teammanager.dao.TeamMemberDAO;
import by.hubarevich.teammanager.dao.connection.ConnectionPool;
import by.hubarevich.teammanager.dao.connection.ProxyConnection;
import by.hubarevich.teammanager.domain.Domain;
import by.hubarevich.teammanager.dao.exception.DAOException;
import by.hubarevich.teammanager.domain.User;
import by.hubarevich.teammanager.service.exception.LogicException;

import java.util.List;

/**
 * Service class for CRUD operations of all types of users
 */

public class CRUDService {

    private static CRUDService instance = new CRUDService();

    public static CRUDService getInstance() {
        return instance;
    }

    private CRUDService() {
    }

    /**
     * adds new field to DB
     *
     * @param domain Domain to add to DB
     * @param role   String user role to define the exact type of Domain
     * @return true if the adding was successfully finished
     * @throws LogicException if the DAOException was thrown
     */
    public boolean addDomain(Domain domain, String role) throws LogicException {
        boolean success;

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            AbstractDAO abstractDAO = defineRole(role, connection);
            success = abstractDAO.create(domain);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return success;
    }

    /**
     * deletes a field from DB by Domain ID
     *
     * @param domainId int Domain identifier
     * @param role     String user role to define the exact type of Domain
     * @return true if the delete was successfully finished
     * @throws LogicException if the DAOException was thrown
     */

    public boolean deleteDomain(int domainId, String role) throws LogicException {
        boolean success;

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            AbstractDAO abstractDAO = defineRole(role, connection);
            success = abstractDAO.delete(domainId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return success;
    }

    /**
     * updates DB field information according to Domain instance parameters
     *
     * @param domainToEdit Domain
     * @param editor       object Class User
     * @return true if the edit operations was successfully finished
     * @throws LogicException if the DAOException was thrown
     */

    public boolean editDomain(Domain domainToEdit, User editor) throws LogicException {
        Domain updatedDomain;

        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            AbstractDAO abstractDAO = defineRole(editor.getRole(), connection);
            if (CRUDService.getInstance().checkEditorsData(editor)) {
                updatedDomain = abstractDAO.update(domainToEdit);
            } else {
                return false;
            }
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return (updatedDomain != null);
    }

    /**
     * searches for a Domain object to edit
     *
     * @param domainId int object Identifier
     * @param role     String editor's role
     * @return Domain if it found on DB and null if it is not
     * @throws LogicException if the DAOException was thrown
     */

    public Domain showDomainToEdit(int domainId, String role) throws LogicException {
        Domain domain;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            AbstractDAO abstractDAO = defineRole(role, connection);
            domain = abstractDAO.findDomainById(domainId);
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return domain;
    }

    /**
     * searches for all Domains of defined type
     * @param role User role to get all Domains
     * @return List of Domains of defined type
     * @throws LogicException if the DAOException was thrown
     */

    public List showAllDomains(String role) throws LogicException {
        List domainList;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            AbstractDAO abstractDAO = defineRole(role, connection);
            domainList = abstractDAO.findAll();
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return domainList;
    }

    /**
     * checks User data to accept actions
     * @param editor User objects
     * @return true if the Users data is correct
     * @throws LogicException if the DAOException was thrown
     */

    public boolean checkEditorsData(User editor) throws LogicException {
        User userToCompare;
        try (ProxyConnection connection = ConnectionPool.getInstance().getConnection()) {
            AdminDAO localDAO = new AdminDAO(connection);
            userToCompare = localDAO.findUserByLogin(editor.getLogin());
        } catch (DAOException e) {
            throw new LogicException(e);
        }
        return (userToCompare.getPassword().equals(editor.getPassword()));
    }



    private AbstractDAO defineRole(String role, ProxyConnection connection) {
        AbstractDAO abstractDAO = null;
        switch (role) {
            case "admin":
                abstractDAO = new AdminDAO(connection);
                break;
            case "hr-manager":
                abstractDAO = new TeamMemberDAO(connection);
                break;
            case "dispatcher":
                abstractDAO = new FlightDAO(connection);
                break;
        }
        return abstractDAO;
    }
}
