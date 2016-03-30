/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.domain;

/**
 * Class-entity for City instance
 */

public class City extends Domain {


    private String cityCode;
    private String cityName;
    private String fieldType;
    private Double northLatitude;
    private Double eastLongitude;

    public City() {
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Double getNorthLatitude() {
        return northLatitude;
    }

    public void setNorthLatitude(Double northLatitude) {
        this.northLatitude = northLatitude;
    }

    public Double getEastLongitude() {
        return eastLongitude;
    }

    public void setEastLongitude(Double eastLongitude) {
        this.eastLongitude = eastLongitude;
    }


    @Override
    public String toString() {
        return "City{" +
                "cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", northLatitude=" + northLatitude +
                ", eastLongitude=" + eastLongitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        if (getCityCode() != null ? !getCityCode().equals(city.getCityCode()) : city.getCityCode() != null)
            return false;
        if (getCityName() != null ? !getCityName().equals(city.getCityName()) : city.getCityName() != null)
            return false;
        if (getFieldType() != null ? !getFieldType().equals(city.getFieldType()) : city.getFieldType() != null)
            return false;
        if (getNorthLatitude() != null ? !getNorthLatitude()
                .equals(city.getNorthLatitude()) : city.getNorthLatitude() != null)
            return false;
        return !(getEastLongitude() != null ? !getEastLongitude()
                .equals(city.getEastLongitude()) : city.getEastLongitude() != null);

    }

    @Override
    public int hashCode() {
        int result = getCityCode() != null ? getCityCode().hashCode() : 0;
        result = 31 * result + (getCityName() != null ? getCityName().hashCode() : 0);
        result = 31 * result + (getFieldType() != null ? getFieldType().hashCode() : 0);
        result = 31 * result + (getNorthLatitude() != null ? getNorthLatitude().hashCode() : 0);
        result = 31 * result + (getEastLongitude() != null ? getEastLongitude().hashCode() : 0);
        return result;
    }
}
