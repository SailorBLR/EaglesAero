/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 */

package by.hubarevich.teammanager.resource;

import java.util.ResourceBundle;

/**
 * Class get information from config.properties file
 */

public class ConfigurationManager {
    private final static ResourceBundle resourceBundle = ResourceBundle.getBundle("resources.config");
    private ConfigurationManager() { }
    public static String getProperty(String key) {
        return resourceBundle.getString(key);
    }
}
