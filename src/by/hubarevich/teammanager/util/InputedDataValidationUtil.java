/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.util;

import by.hubarevich.teammanager.domain.TeamMember;
import by.hubarevich.teammanager.domain.User;
import org.junit.Test;

/**
 * Util-class to validate inputed data for creating new User
 */

public class InputedDataValidationUtil {
    private static final String NAME_PATTERN = "([А-ЯA-Z]{1})[а-яa-z]{2,20}";

    /**
     * Validates inputed User data
     * @param user User object to validate
     * @return true if the data is valid
     */

    public static boolean validateUserData (User user) {

        final String LOGIN_PATTERN = "[A-Za-z0-9]{3,20}";
        final String PASSWORD_PATTERN = "[A-Za-z0-9]{6,10}";
        return ((user.getName().matches(NAME_PATTERN))&&(user.getSurname().matches(NAME_PATTERN))&&
        user.getLogin().matches(LOGIN_PATTERN)&&user.getPassword().matches(PASSWORD_PATTERN));
    }

    /**
     * Validates inputed TeamMember data
     * @param teamMember TeamMember object to validate
     * @return true if the data is valid
     */

    public static boolean validateTeamMemberData (TeamMember teamMember) {
        final String DATE_PATTERN = "(19)([4-9]{2})\\-(0?[1-9]|1[012])\\-([0][1-9]|[12][0-9]|3[01])";

        return ((teamMember.getName().matches(NAME_PATTERN))&&(teamMember.getSurname().matches(NAME_PATTERN))&&
                (teamMember.getBirthDay()).matches(DATE_PATTERN));
    }

}
