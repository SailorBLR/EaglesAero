/**
 * Copyright (c) 2016, Anton Hubarevich. All rights reserved.
 **/

package by.hubarevich.teammanager.resource;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Class provides the access to system messages
 */

public class MessageManagerWrapper {

    /**
     * Enumeration of the existing bundles
     */
    private enum MessageManager {
        EN( ResourceBundle.getBundle("resources.messages_en", new Locale("en", "EN"))),
        RU( ResourceBundle.getBundle("resources.messages_ru", new Locale("ru", "RU")));

        private ResourceBundle bundle;

        MessageManager(ResourceBundle bundle){
            this.bundle = bundle;
        }
        public String getMessage(String key) {
            return bundle.getString(key);
        }

    }

    /**
     * Gets system localized messages
     * @param locale
     * @param key
     * @return localized message
     */

    public static String getMessage (String key, String locale ){
        if ("en_US".equals(locale)){
            return MessageManager.EN.getMessage(key);
        } else {
            return MessageManager.RU.getMessage(key);
        }
    }

}
