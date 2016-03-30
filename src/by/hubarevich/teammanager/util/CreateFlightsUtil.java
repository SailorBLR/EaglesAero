/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.util;

import by.hubarevich.teammanager.domain.Flight;
import by.hubarevich.teammanager.service.dispatcher.StatusEnum;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Util-class to create Direct and Back Flight Objects
 */

public class CreateFlightsUtil {

    private static final Logger LOG = LogManager.getLogger(CreateFlightsUtil.class);
    private static CreateFlightsUtil instance = new CreateFlightsUtil();
    private final String CITIESFROM = "citiesFrom";
    private final String CITIESTO = "citiesTo";
    private final String DATEFORMAT = "yyyy-MM-dd HH:mm";
    private final String FLIGHT_TIME = "flightTime";
    private final String TIME_FORMAT = "HH:mm";
    private final String DISTANCE = "flightDistance";
    private final String ARRIVING = "timeArrive";
    private final String DEPARTURE = "timeDepart";
    private final String DATE = "date";
    private final String PLANES = "planes";
    private final String BACKDATE = "backDepDate";
    private final String BACK_DEPART_TIME = "backTimeDepart";
    private final String BACK_ARRIVING_TIME = "timeBackArrive";

    private final String BACK_DEPARTURE = "backDeparture";
    private final String BACK_ARRIVING = "backArriving";
    private final String DIRECT_DEPARTURE = "directDeparture";
    private final String DIRECT_ARRIVING = "directArriving";
    private final String DIRECT_ID = "directFlightId";
    private final String BACK_ID = "backFlightId";
    private final String DIRECT = "direct";
    private final String BACK = "back";


    public static CreateFlightsUtil getInstance() {
        return instance;
    }

    private CreateFlightsUtil() {}

    /**
     * Builds direct and back Flights
     * @param request HTTP Request object
     * @param flights empty List of objects
     * @return List of constructed Flight objects
     */

    public List<Flight> buildFlightObjects(HttpServletRequest request, List flights) {
        List pareOfFlights = new ArrayList<>();
        Calendar flightTime = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        String departureTime = "";
        String departureTimeBack = "";
        String flightIddirect = "";
        String flightIdback = "";
        String flightIndex;

        Flight direct = new Flight();
        Flight back = new Flight();

        flightIddirect = flightIddirect.concat(request.getParameter(CITIESFROM).
                substring(0, 2));
        flightIdback = flightIdback.concat(request.getParameter(CITIESTO).
                substring(0, 2));

        flightIndex = String.valueOf(FlightCounterUtil.getInstance().calculateFlightId(flights));
        flightIdback = flightIdback.concat(flightIndex);
        flightIddirect = flightIddirect.concat(flightIndex);

        flightIddirect = flightIddirect.concat(request.getParameter(CITIESTO).
                substring(0, 2));
        flightIdback = flightIdback.concat(request.getParameter(CITIESFROM).
                substring(0, 2));


        back.setFlightId(flightIdback);
        direct.setFlightId(flightIddirect);

        direct.setStatus(StatusEnum.IN_FUTURE.getValue());
        back.setStatus(StatusEnum.IN_FUTURE.getValue());

        direct.setFlightFrom(request.getParameter(CITIESFROM));
        direct.setFlightTo(request.getParameter(CITIESTO));

        back.setFlightFrom(request.getParameter(CITIESTO));
        back.setFlightTo(request.getParameter(CITIESFROM));

        direct.setDirection(DIRECT);

        try {
            departureTime = departureTime.concat(request.getParameter(DATE)).concat(" ")
                    .concat(request.getParameter(DEPARTURE));

            flightTime.setTime(sdf.parse(departureTime));
            direct.setDepartureTime(flightTime);
            flightTime = new GregorianCalendar();
            flightTime.setTime(sdf.parse(request.getParameter(ARRIVING)));

            direct.setArrivingTime(flightTime);

            departureTimeBack = departureTimeBack.concat(request.getParameter(BACKDATE)).concat(" ")
                    .concat(request.getParameter(BACK_DEPART_TIME));
            flightTime = new GregorianCalendar();
            flightTime.setTime(sdf.parse(departureTimeBack));

            back.setDepartureTime(flightTime);

            flightTime = new GregorianCalendar();
            flightTime.setTime(sdf.parse(request.getParameter(BACK_ARRIVING_TIME)));
            back.setArrivingTime(flightTime);

            direct.setPlane(request.getParameter(PLANES));
            back.setPlane(request.getParameter(PLANES));

            direct.setFlightDistance(Integer.parseInt(request.getParameter(DISTANCE)));
            back.setFlightDistance(Integer.parseInt(request.getParameter(DISTANCE)));

            flightTime = new GregorianCalendar();
            sdf = new SimpleDateFormat(TIME_FORMAT);
            flightTime.setTime(sdf.parse(request.getParameter(FLIGHT_TIME)));
            direct.setFlightTime(flightTime.getTimeInMillis());
            back.setFlightTime(flightTime.getTimeInMillis());

            direct.setDirection(DIRECT);
            back.setDirection(BACK);

        } catch (ParseException e) {
            LOG.warn(e);
        }

        if ((direct != null) && (back != null)) {
            pareOfFlights.add(direct);
            pareOfFlights.add(back);

        }
        return pareOfFlights;
    }

    /**
     * Creates a pare of Flights to be updated
     * @param request HTTPRequest object
     * @return List of flights
     */

    public List<Flight> createFlightsToUpdate(HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        String directId = request.getParameter(DIRECT_ID);
        String backId = request.getParameter(BACK_ID);
        String backDeparture = request.getParameter(BACK_DEPARTURE);
        String backArriving = request.getParameter(BACK_ARRIVING);
        String directDeparture = request.getParameter(DIRECT_DEPARTURE);
        String directArriving = request.getParameter(DIRECT_ARRIVING);
        Flight directFlight = new Flight();
        Flight backFlight = new Flight();
        Calendar calendar = new GregorianCalendar();
        List <Flight>pareOfFlights = new ArrayList<>();

        try {
            if ((backDeparture != null) & (backArriving != null) & (directDeparture != null)
                    & (directArriving != null) & (directId != null) & (backId != null)) {
                directFlight.setFlightId(directId);
                calendar.setTime(sdf.parse(request.getParameter(DIRECT_DEPARTURE)));
                directFlight.setDepartureTime(calendar);
                calendar = new GregorianCalendar();
                calendar.setTime(sdf.parse(request.getParameter(DIRECT_ARRIVING)));
                directFlight.setArrivingTime(calendar);
                pareOfFlights.add(directFlight);

                backFlight.setFlightId(backId);
                calendar = new GregorianCalendar();
                calendar.setTime(sdf.parse(request.getParameter(BACK_DEPARTURE)));
                backFlight.setDepartureTime(calendar);
                calendar = new GregorianCalendar();
                calendar.setTime(sdf.parse(request.getParameter(BACK_ARRIVING)));
                backFlight.setArrivingTime(calendar);
                pareOfFlights.add(backFlight);
            }
        } catch (ParseException e) {
            LOG.warn(e);
        }

        return pareOfFlights;
    }
}
