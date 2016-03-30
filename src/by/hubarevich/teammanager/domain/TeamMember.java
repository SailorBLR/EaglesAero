/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class-entity for TeamMember instance
 */

public class TeamMember extends Domain {

    private Integer personId;
    private String name;
    private String surname;
    private Calendar dateOfBirth;
    private String role;
    private String qualification;
    private String cityCode;
    private String status;


    public TeamMember() {
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Calendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Calendar dateOfBirth) {


        this.dateOfBirth = dateOfBirth;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * formats Date of Birth to defined time format
     * @return String formatted date
     */

    public String getBirthDay() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return (sdf.format(dateOfBirth.getTime()));
    }
    @Override
    public String toString() {
        return (name + " " + surname + " " + dateOfBirth + " " + role + " " + qualification);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TeamMember)) return false;

        TeamMember that = (TeamMember) o;

        if (getPersonId() != null ? !getPersonId().equals(that.getPersonId()) : that.getPersonId() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getSurname() != null ? !getSurname().equals(that.getSurname()) : that.getSurname() != null) return false;
        if (getDateOfBirth() != null ? !getDateOfBirth().equals(that.getDateOfBirth()) : that.getDateOfBirth() != null)
            return false;
        if (getRole() != null ? !getRole().equals(that.getRole()) : that.getRole() != null) return false;
        if (getQualification() != null ? !getQualification().equals(that.getQualification()) : that.getQualification() != null)
            return false;
        if (getCityCode() != null ? !getCityCode().equals(that.getCityCode()) : that.getCityCode() != null)
            return false;
        return !(getStatus() != null ? !getStatus().equals(that.getStatus()) : that.getStatus() != null);

    }

    @Override
    public int hashCode() {
        int result = getPersonId() != null ? getPersonId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getSurname() != null ? getSurname().hashCode() : 0);
        result = 31 * result + (getDateOfBirth() != null ? getDateOfBirth().hashCode() : 0);
        result = 31 * result + (getRole() != null ? getRole().hashCode() : 0);
        result = 31 * result + (getQualification() != null ? getQualification().hashCode() : 0);
        result = 31 * result + (getCityCode() != null ? getCityCode().hashCode() : 0);
        result = 31 * result + (getStatus() != null ? getStatus().hashCode() : 0);
        return result;
    }
}
