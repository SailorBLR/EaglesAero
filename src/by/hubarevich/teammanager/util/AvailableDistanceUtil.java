/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.util;

/**
 * Util-class to define the limit distance for each TeamMember
 */

public class AvailableDistanceUtil {

    /**
     * Calculates available distance
     * @param qualification String TeamMember qualification
     * @return String available distance. If qualification is not defined, returns 0
     */

    public static String calculateAvailableDistance (String qualification) {
        String availableDistance="100";

        switch (qualification) {
            case "1":
                availableDistance = "10000";
                break;
            case "2":
                availableDistance = "5000";
                break;
            case "3":
                availableDistance = "500";
                break;
            case "not specified":
                availableDistance = "10000";
                break;
            default:
                availableDistance = "0";
                break;
        }
        return availableDistance;
    }
}
