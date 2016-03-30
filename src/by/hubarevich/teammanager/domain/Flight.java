/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class-entity for Flight instance
 */

public class Flight extends Domain {

    private String flightId;
    private String plane;
    private Integer planeId;
    private String status;
    private String flightFrom;
    private String flightTo;
    private Calendar departureTime;
    private Calendar arrivingTime;
    private Long flightTime;
    private Integer flightDistance;
    private Boolean flightTeam;
    private String direction;


    public Flight() {
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public Long getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Long flightTime) {
        this.flightTime = flightTime;
    }

    public String getFlightFrom() {
        return flightFrom;
    }

    public void setFlightFrom(String flightFrom) {
        this.flightFrom = flightFrom;
    }

    public String getFlightTo() {
        return flightTo;
    }

    public void setFlightTo(String flightTo) {
        this.flightTo = flightTo;
    }

    public Calendar getDepartureTime() {

        return departureTime;
    }

    public void setDepartureTime(Calendar departureTime) {
        this.departureTime = departureTime;
    }

    public Calendar getArrivingTime() {
        return arrivingTime;
    }

    public void setArrivingTime(Calendar arrivingTime) {
        this.arrivingTime = arrivingTime;
    }

    public String getPlane() {
        return plane;
    }

    public void setPlane(String plane) {
        this.plane = plane;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getFlightDistance() {
        return flightDistance;
    }

    public void setFlightDistance(Integer flightDistance) {
        this.flightDistance = flightDistance;
    }

    public Integer getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Integer planeId) {
        this.planeId = planeId;
    }

    public Boolean getFlightTeam() {
        return flightTeam;
    }

    public void setFlightTeam(Boolean flightTeam) {
        this.flightTeam = flightTeam;
    }


    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * formats Calendar field to String with the defined format
     * @param calendar Calendar time object
     * @return String formatted date and time
     */

    public String getFormattedDate(Calendar calendar ) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return (sdf.format(calendar.getTime()));
    }

    /**
     * formats Calendar field to String with the defined format
     * @param calendar Calendar time object
     * @return String formatted date
     */

    public String getDate(Calendar calendar ) {
        String output;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        output=sdf.format(calendar.getTime());
        output = output.substring(0,10);
        return output;
    }

    /**
     * formats Calendar field to String with the defined format
     * @param calendar Calendar time object
     * @return String formatted time
     */

    public String getFormattedTime(Calendar calendar ) {
        String output;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        output = sdf.format(calendar.getTime());
        output = output.substring(11,output.length());
        return output;
    }

    @Override
    public String toString() {
        return "Flight from "+flightFrom + " to " + flightTo + "\n" + "" +
                "departure: " + departureTime.getTime() + ",\n" +
                " arriving: " + arrivingTime.getTime();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Flight)) return false;

        Flight flight = (Flight) o;

        if (getFlightId() != null ? !getFlightId().equals(flight.getFlightId()) : flight.getFlightId() != null)
            return false;
        if (getPlane() != null ? !getPlane().equals(flight.getPlane()) : flight.getPlane() != null) return false;
        if (getPlaneId() != null ? !getPlaneId().equals(flight.getPlaneId()) : flight.getPlaneId() != null)
            return false;
        if (getStatus() != null ? !getStatus().equals(flight.getStatus()) : flight.getStatus() != null) return false;
        if (getFlightFrom() != null ? !getFlightFrom().equals(flight.getFlightFrom()) : flight.getFlightFrom() != null)
            return false;
        if (getFlightTo() != null ? !getFlightTo().equals(flight.getFlightTo()) : flight.getFlightTo() != null)
            return false;
        if (getDepartureTime() != null ? !getDepartureTime()
                .equals(flight.getDepartureTime()) : flight.getDepartureTime() != null)
            return false;
        if (getArrivingTime() != null ? !getArrivingTime()
                .equals(flight.getArrivingTime()) : flight.getArrivingTime() != null)
            return false;
        if (getFlightTime() != null ? !getFlightTime().equals(flight.getFlightTime()) : flight.getFlightTime() != null)
            return false;
        if (getFlightDistance() != null ? !getFlightDistance()
                .equals(flight.getFlightDistance()) : flight.getFlightDistance() != null)
            return false;
        if (getFlightTeam() != null ? !getFlightTeam().equals(flight.getFlightTeam()) : flight.getFlightTeam() != null)
            return false;
        return !(getDirection() != null ? !getDirection()
                .equals(flight.getDirection()) : flight.getDirection() != null);

    }

    @Override
    public int hashCode() {
        int result = getFlightId() != null ? getFlightId().hashCode() : 0;
        result = 31 * result + (getPlane() != null ? getPlane().hashCode() : 0);
        result = 31 * result + (getPlaneId() != null ? getPlaneId().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        result = 31 * result + (getFlightFrom() != null ? getFlightFrom().hashCode() : 0);
        result = 31 * result + (getFlightTo() != null ? getFlightTo().hashCode() : 0);
        result = 31 * result + (getDepartureTime() != null ? getDepartureTime().hashCode() : 0);
        result = 31 * result + (getArrivingTime() != null ? getArrivingTime().hashCode() : 0);
        result = 31 * result + (getFlightTime() != null ? getFlightTime().hashCode() : 0);
        result = 31 * result + (getFlightDistance() != null ? getFlightDistance().hashCode() : 0);
        result = 31 * result + (getFlightTeam() != null ? getFlightTeam().hashCode() : 0);
        result = 31 * result + (getDirection() != null ? getDirection().hashCode() : 0);
        return result;
    }
}
