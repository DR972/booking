package by.rozmysl.bookingServlet.controller.util;

import by.rozmysl.bookingServlet.model.entity.user.User;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides a service for working with the session.
 */
public final class AppUtil {
    private static int REDIRECT_ID = 0;
    private static final Map<Integer, String> ID_URI = new HashMap<>();
    private static final Map<String, Integer> URI_ID = new HashMap<>();
    private static final String LOGGED_USER = "loggedUser";

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private AppUtil() {
    }

    /**
     * Saves the user's information in the Session.
     *
     * @param session    HttpSession
     * @param loggedUser loggedUser
     */
    public static void saveLoggedUser(HttpSession session, User loggedUser) {
        session.setAttribute(LOGGED_USER, loggedUser);
    }

    /**
     * Gets the user's information stored in the Session.
     *
     * @param session HttpSession
     * @return User
     */
    public static User getLoggedUser(HttpSession session) {
        return (User) session.getAttribute(LOGGED_USER);
    }

    /**
     * Saves redirection after logging in.
     *
     * @param requestUri requestUri
     * @return number of redirect
     */
    public static int saveRedirectAfterLoginUrl(String requestUri) {
        Integer id = URI_ID.get(requestUri);
        if (id == null) {
            id = REDIRECT_ID++;
            URI_ID.put(requestUri, id);
            ID_URI.put(id, requestUri);
        }
        return id;
    }

    /**
     * Gets redirect after logging in.
     *
     * @param redirectId redirectId
     * @return redirection page
     */
    public static String getRedirectAfterLoginUrl(int redirectId) {
        return ID_URI.get(redirectId);
    }
}
