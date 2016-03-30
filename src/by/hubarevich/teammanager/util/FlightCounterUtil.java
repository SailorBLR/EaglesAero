package by.hubarevich.teammanager.util;

import by.hubarevich.teammanager.domain.Flight;

import java.util.List;

/**
 * Created by Anton on 24.02.2016.
 */
public class FlightCounterUtil {
    private static int flightCount = 0;
    private static FlightCounterUtil instance;

    public static FlightCounterUtil getInstance() {
        if(instance == null) {
            instance = new FlightCounterUtil();
        }
        return instance;
    }

    private FlightCounterUtil() {
    }

    public int calculateFlightId(List flights){
        int maxCount=0;
        if(flights!=null) {
            for (Object flight : flights) {
                flightCount = Integer.parseInt(((Flight) flight).getFlightId().replaceAll("[A-Za-z]", ""));
                if (flightCount > maxCount) {
                    maxCount = flightCount;
                }
            }
        }

        return maxCount+1;
    }
}
