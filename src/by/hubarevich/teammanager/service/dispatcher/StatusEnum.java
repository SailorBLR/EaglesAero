package by.hubarevich.teammanager.service.dispatcher;

/**
 * Created by Anton on 18.02.2016.
 */
public enum StatusEnum {
    IN_FUTURE("future"),
    IN_AIR("in progress"),
    LANDED("landed"),
    CANCELLED("cancelled");

    private String value;

    private StatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
