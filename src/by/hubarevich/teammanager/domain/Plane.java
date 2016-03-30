/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.domain;

import java.util.Calendar;

/**
 * Class-entity for Plane instance
 */

public class Plane extends Domain {

    private Integer planeId;
    private Integer passengers;
    private Integer speed;
    private Integer range;
    private String planeName;
    private String location;
    private Short pilot;
    private Short technic;
    private Short stuart;
    private Calendar freeFrom;

    public Plane() {
    }

    public Integer getPlaneId() {
        return planeId;
    }

    public void setPlaneId(Integer planeId) {
        this.planeId = planeId;
    }

    public Integer getPassengers() {
        return passengers;
    }

    public void setPassengers(Integer passengers) {
        this.passengers = passengers;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public String getPlaneName() {
        return planeName;
    }

    public void setPlaneName(String planeName) {
        this.planeName = planeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Short getPilot() {
        return pilot;
    }

    public void setPilot(Short pilot) {
        this.pilot = pilot;
    }

    public Short getTechnic() {
        return technic;
    }

    public void setTechnic(Short technic) {
        this.technic = technic;
    }

    public Short getStuart() {
        return stuart;
    }

    public void setStuart(Short stuart) {
        this.stuart = stuart;
    }

    public Calendar getFreeFrom() {
        return freeFrom;
    }

    public void setFreeFrom(Calendar freeFrom) {
        this.freeFrom = freeFrom;
    }


    @Override
    public String toString() {
        return "Plane{" +
                "planeId=" + planeId +
                ", passengers=" + passengers +
                ", speed=" + speed +
                ", range=" + range +
                ", planeName='" + planeName + '\'' +
                ", location='" + location + '\'' +
                ", primePilot=" + pilot +
                ", technic=" + technic +
                ", stuart=" + stuart +
                ", freeFrom=" + freeFrom +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plane)) return false;

        Plane plane = (Plane) o;

        if (getPlaneId() != null ? !getPlaneId().equals(plane.getPlaneId()) : plane.getPlaneId() != null) return false;
        if (getPassengers() != null ? !getPassengers().equals(plane.getPassengers()) : plane.getPassengers() != null)
            return false;
        if (getSpeed() != null ? !getSpeed().equals(plane.getSpeed()) : plane.getSpeed() != null) return false;
        if (getRange() != null ? !getRange().equals(plane.getRange()) : plane.getRange() != null) return false;
        if (getPlaneName() != null ? !getPlaneName().equals(plane.getPlaneName()) : plane.getPlaneName() != null)
            return false;
        if (getLocation() != null ? !getLocation().equals(plane.getLocation()) : plane.getLocation() != null)
            return false;
        if (getPilot() != null ? !getPilot().equals(plane.getPilot()) : plane.getPilot() != null) return false;
        if (getTechnic() != null ? !getTechnic().equals(plane.getTechnic()) : plane.getTechnic() != null) return false;
        if (getStuart() != null ? !getStuart().equals(plane.getStuart()) : plane.getStuart() != null) return false;
        return !(getFreeFrom() != null ? !getFreeFrom().equals(plane.getFreeFrom()) : plane.getFreeFrom() != null);

    }

    @Override
    public int hashCode() {
        int result = getPlaneId() != null ? getPlaneId().hashCode() : 0;
        result = 31 * result + (getPassengers() != null ? getPassengers().hashCode() : 0);
        result = 31 * result + (getSpeed() != null ? getSpeed().hashCode() : 0);
        result = 31 * result + (getRange() != null ? getRange().hashCode() : 0);
        result = 31 * result + (getPlaneName() != null ? getPlaneName().hashCode() : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getPilot() != null ? getPilot().hashCode() : 0);
        result = 31 * result + (getTechnic() != null ? getTechnic().hashCode() : 0);
        result = 31 * result + (getStuart() != null ? getStuart().hashCode() : 0);
        result = 31 * result + (getFreeFrom() != null ? getFreeFrom().hashCode() : 0);
        return result;
    }
}
