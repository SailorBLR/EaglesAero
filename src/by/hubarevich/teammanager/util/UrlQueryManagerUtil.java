package by.hubarevich.teammanager.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Anton on 03.03.2016.
 */
public class UrlQueryManagerUtil {

    public static String manageQuery(HttpServletRequest request) {
        String query="command=login";
        if(request.getQueryString()!=null) {
            query = request.getQueryString();
        }
        return query;
    }
}
