/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.dp.jz.jbatch.hawtio;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author jzelezny
 */
public class SessionWatcher implements HttpSessionListener{

    private static final Map<String, HttpSession> sessions = new HashMap<String, HttpSession>();
    private static int totalActiveSessions;

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        totalActiveSessions++;
        HttpSession session = event.getSession();
        sessions.put(session.getId(), session);
    }


    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        totalActiveSessions--;
        sessions.remove(event.getSession().getId());
    }

    public static HttpSession find(String sessionId) {
        return sessions.get(sessionId);
    }
    
    public static String getAllSessions(){
        return sessions.toString() + totalActiveSessions;
    }
    
}
