/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.dao;

/**
 * Class enumeration of available SQL-queries
 *
 */

public enum QueryEnum {
    SELECT_LOGIN_PASSWORD_ROLE("SELECT login,password,role,name FROM user_roles WHERE login =?"),
    SELECT_ALL_FROM_USERS("SELECT user_id,login,password," +
            "role,name,surname,photo FROM user_roles"),
    SELECT_USER_IF_EXIST("SELECT login,password,role FROM user_roles " +
            "WHERE login=?"),
    SELECT_USER_BY_ID ("SELECT user_id,login,password,role,name,surname FROM user_roles " +
            "WHERE user_id=?"),
    INSERT_NEW_USER("INSERT INTO user_roles " +
            "(login, password, role, name, surname, photo) " +
            "VALUES (?,?,?,?,?,?)"),
    DELETE_USER_BY_ID("DELETE FROM user_roles WHERE user_id=?"),
    EDIT_USER("UPDATE user_roles SET login=?, password=?, " +
            "role=?, name=?, surname=? " +
            "WHERE user_id=?"),

    SELECT_CITIES("SELECT city_code,city,field_type,n_coordinate,e_coordinate FROM cities"),
    SELECT_ALL_FROM_TEAM_MEMBER("SELECT team_member_id, name, surname, role, class, " +
            "date_of_birth, status FROM team_member"),
    SELECT_TEAM_MEMBER_IF_EXIST("SELECT team_member_id, name, surname, role, class, date_of_birth, status " +
            "FROM team_member " +
            "WHERE name=? AND surname=? AND date_of_birth=?"),
    INSERT_NEW_TEAM_MEMBER("INSERT INTO team_member " +
            "(name, surname, role, class, date_of_birth,available_distance,location) " +
            "VALUES (?,?,?,?,?,?,?)"),
    DELETE_TEAM_MEMBER_BY_ID("DELETE FROM team_member WHERE team_member_id=?"),
    SELECT_TEAM_MEMBER_BY_ID ("SELECT team_member_id,name,surname,role,class,date_of_birth,status FROM team_member " +
            "WHERE team_member_id=?"),
    EDIT_TEAM_MEMBER("UPDATE team_member SET name=?, surname=?, " +
            "role=?, class=?, status=? " +
            "WHERE team_member_id=?"),
    UPDATE_TEAM_MEMBER_FREE_FROM("UPDATE team_member SET " +
            "free_from=(SELECT arriving_time FROM flights WHERE flight_id=?) " +
            "WHERE team_member_id=?"),

    SELECT_TEAM_MEMBER_BY_FLIGHT("SELECT team_member_id FROM flight_team WHERE flight_id=?"),

    SELECT_ALL_FROM_FLIGHTS("SELECT flight_id, C2.CITY, C1.CITY, " +
            "departure_time, arriving_time, P.PLANE,F.PLANE_ID ,status,flight_distance,flight_time,direction " +
            "FROM flights F " +
            "JOIN planes P ON (P.PLANE_ID=F.PLANE_ID) " +
            "JOIN cities C1 ON (C1.CITY_CODE=F.END_POINT) " +
            "JOIN cities C2 ON (C2.CITY_CODE=F.START_POINT) " +
            "ORDER BY F.ARRIVING_TIME DESC"),
    REFRESH_FLIGHT("UPDATE flights F " +
            "JOIN planes P " +
            "ON (P.PLANE_ID=F.PLANE_ID) " +
            "SET F.STATUS=? " +
            "WHERE F.FLIGHT_ID=?"),
    SELECT_FLIGHT_BY_ID ("SELECT flight_id, C2.CITY, C1.CITY, " +
            "departure_time, arriving_time, P.PLANE, F.PLANE_ID,status,flight_distance" +
            ",flight_time,direction FROM flights F " +
            "JOIN planes P ON (P.PLANE_ID=F.PLANE_ID) " +
            "JOIN cities C1 ON (C1.CITY_CODE=F.END_POINT) " +
            "JOIN cities C2 ON (C2.CITY_CODE=F.START_POINT) WHERE flight_id=?"),
    CREATE_FLIGHT ("INSERT INTO flights (flight_id, start_point, end_point, departure_time, " +
            "arriving_time, plane_id,status,flight_distance,flight_time,direction) VALUES (?,?,?,?,?,?,?,?,?,?) "),
    DELETE_FLIGHT ("DELETE FROM flights WHERE flight_id=?"),

    EDIT_FLIGHT ("UPDATE flights F" +
            "   INNER JOIN flight_team FT ON FT.FLIGHT_ID=F.FLIGHT_ID" +
            "   INNER JOIN team_member TM ON TM.TEAM_MEMBER_ID=FT.TEAM_MEMBER_ID" +
            "   JOIN planes PL ON PL.PLANE_ID=F.PLANE_ID" +
            "   SET F.DEPARTURE_TIME=?, F.ARRIVING_TIME=?, PL.FREE_FROM=?, TM.FREE_FROM=?" +
            "   WHERE F.FLIGHT_ID=?"),

    CANCEL_FLIGHT ("UPDATE flights F" +
            " JOIN flight_team FT ON FT.FLIGHT_ID=F.FLIGHT_ID " +
            "JOIN team_member TM ON TM.TEAM_MEMBER_ID=FT.TEAM_MEMBER_ID " +
            "JOIN planes PL ON PL.PLANE_ID=F.PLANE_ID " +
            "SET F.STATUS=?, " +
            "PL.FREE_FROM= CASE WHEN PL.FREE_FROM>F.ARRIVING_TIME THEN PL.FREE_FROM " +
            "ELSE CURDATE() END, " +
            "TM.FREE_FROM= CASE WHEN TM.FREE_FROM>F.ARRIVING_TIME THEN TM.FREE_FROM " +
            "ELSE CURDATE() END " +
            "WHERE F.FLIGHT_ID=?"),

    CANCEL_FLIGHT_TEAM_NOT_FORMED ("UPDATE planes PL " +
            "JOIN flights FL " +
            "ON FL.PLANE_ID=PL.PLANE_ID " +
            "SET " +
            "PL.FREE_FROM=(case when " +
            "(PL.FREE_FROM>FL.ARRIVING_TIME) " +
            "then " +
            "PL.FREE_FROM " +
            "else " +
            " curdate() " +
            "end), " +
            "FL.`STATUS`=? " +
            "WHERE FL.FLIGHT_ID=?"),

    SELECT_PLANES("SELECT plane_id, plane, pilot, " +
            "technical_specialist, stuarts, passengers, speed, distance, location, free_from FROM planes"),

    SELECT_AVAILABLE_PLANES("SELECT plane_id, plane, pilot, " +
            "technical_specialist, stuarts, passengers, speed, distance, location FROM planes " +
            "WHERE plane_id NOT IN (SELECT plane_id FROM flights)"),

    PREPARE_UPDATE_PLANE("SELECT MAX(FL.ARRIVING_TIME) from flights FL " +
           "WHERE FL.PLANE_ID = ? " +
           "AND FL.`STATUS` NOT LIKE 'cancelled'"),
    UPDATE_PLANE("UPDATE planes SET free_from=? where plane_id=? "),

    SELECT_PLANE_BY_ID("SELECT plane_id, plane, pilot, " +
            "technical_specialist, stuarts, passengers, speed, distance, location, free_from FROM planes WHERE " +
            "plane_id=?"),
    SELECT_PLANE_BY_MODEL("SELECT plane_id, plane, pilot, " +
            "technical_specialist, stuarts, passengers, speed, distance, location, free_from FROM planes WHERE " +
            "plane=?"),

    SELECT_AVAILABLE_TEAM_MEMBERS("SELECT team_member_id, name, surname, role, class, status " +
            "FROM team_member T1 WHERE (T1.FREE_FROM < (SELECT F2.DEPARTURE_TIME FROM flights F2 WHERE F2.FLIGHT_ID=?))" +
            "AND (T1.LOCATION=(SELECT F3.START_POINT FROM flights F3 WHERE F3.FLIGHT_ID =?))" +
            "AND (T1.AVAILABLE_DISTANCE>=(SELECT F4.FLIGHT_DISTANCE FROM flights F4 WHERE F4.FLIGHT_ID =?)) " +
            "AND (T1.STATUS='free')"),
    CREATE_FLIGHT_TEAM("INSERT INTO flight_team (flight_id,team_member_id) VALUES (?,?)"),
    FIND_IN_FLIGHT_TEAM("SELECT flight_id,team_member_id FROM flight_team WHERE flight_id=? AND team_member_id=?"),
    FIND_FLIGHT_TEAM_BY_FLIGHT("SELECT FT.FLIGHT_ID,FT.TEAM_MEMBER_ID, TM.NAME, TM.SURNAME, " +
            "TM.ROLE, TM.CLASS, TM.`STATUS` FROM flight_team FT " +
            "JOIN team_member TM ON TM.TEAM_MEMBER_ID=FT.TEAM_MEMBER_ID " +
            "WHERE flight_id=?"),
    DELETE_FROM_FLIGHT_TEAM("DELETE FROM flight_team WHERE flight_id=?"),

    DELETE_PERSON_FROM_FLIGHT_TEAM("DELETE FROM flight_team WHERE team_member_id=? and flight_id=?"),

    FIND_ALL_TEAM_MEMBERS_FLIGHTS("SELECT FL.FLIGHT_ID, FL.START_POINT, " +
            "FL.END_POINT, FL.DEPARTURE_TIME, FL.ARRIVING_TIME, FL.STATUS " +
            "FROM team_member TM " +
            "INNER JOIN flight_team FT ON TM.TEAM_MEMBER_ID = FT.TEAM_MEMBER_ID " +
            "INNER JOIN flights FL ON FL.FLIGHT_ID  = FT.FLIGHT_ID WHERE FT.TEAM_MEMBER_ID=?");




    private String value;

    private QueryEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
