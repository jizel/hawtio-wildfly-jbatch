/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.dp.jz.jbatch.webservice;

import java.io.IOException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jzelezny
 */
@WebFilter("/AuthFilter")
public class AuthFilter implements Filter {

    private static final Logger LOG = Logger.getLogger(AuthFilter.class.getName());
//    private final AuthenticationConfiguration configuration = new AuthenticationConfiguration();
    //    private HttpSession session = null;

    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);

        if (session == null) {
            LOG.info("Session is null.... ");
        } else {
//            LOG.info("Session not null yeeey! " + session.getId());
            Subject subject = (Subject) session.getAttribute("subject");
            if (subject != null) {
//                LOG.info("Session subject: " + subject);                
                executeAs(request, response, fc, subject);
                return;
            }
            session.invalidate();
        }

//        Debugging
//        LOG.info("\n\n request:\n");
//        LOG.info("Method: " + httpRequest.getMethod());
//        LOG.info("Remote user: " + httpRequest.getRemoteUser());
//        LOG.info("Request URI: " + httpRequest.getRequestURI());
//        LOG.info("Requested sessionId: " + httpRequest.getRequestedSessionId() + httpRequest.getSession(false));
//        LOG.info("Is Requested sessionId from cookie? :-) : " + httpRequest.isRequestedSessionIdFromCookie());
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        LOG.info("Respopnse: " + httpResponse.toString());        
//        Collection<String> respHeaders= httpResponse.getHeaderNames();
//        for(String s:respHeaders){            
//            LOG.info("Response header: " + s + " = " + httpResponse.getHeader(s));
//        }
//        String path = httpRequest.getServletPath();
//        LOG.info("My request for path: " + path);
//        LOG.info("Requeste: " + request.toString());
//        LOG.info("Requeste attrs: " + request.getAttributeNames());
//        LOG.info("Requeste params: " + request.getParameterMap().toString());
//        LOG.info("My request for path: " + path);
//        LOG.info("Response: " + response.toString());
//        Enumeration<String> headerNames = httpRequest.getHeaderNames();        
//        if (headerNames != null) {
//            while (headerNames.hasMoreElements()) {
//                String nextElement = headerNames.nextElement();
//                LOG.info("Header: " + nextElement + " = " +  httpRequest.getHeader(nextElement));
//            }
//        }
//        Cookie[] cookies = httpRequest.getCookies();
//        LOG.info("Cookies: ");
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                LOG.info("Cookie: " + cookie.getName());
//            }
//        }
//        LOG.info("Sessions: " + SessionWatcher.getAllSessions());
//        HttpSession session = SessionWatcher.find("wqpN5mfAUVwuo1HFMlDw8NPO-bewXmlo357R62sd.jzelezny990");
    }

    @Override
    public void destroy() {
//        session.invalidate();
    }

//    Help
    private static void executeAs(final ServletRequest request, final ServletResponse response, final FilterChain chain, Subject subject) {
        try {
            Subject.doAs(subject, new PrivilegedExceptionAction<Object>() {
                @Override
                public Object run() throws Exception {
                    chain.doFilter(request, response);
                    return null;
                }
            });
        } catch (PrivilegedActionException e) {
            LOG.info("Failed to invoke action " + ((HttpServletRequest) request).getPathInfo() + " due to:" + e);
        }
    }

//    Headers For Cross-origin resource sharing...not really our case
//    private void addCorsHeader(HttpServletResponse response) {
//        //TODO: externalize the Allow-Origin
//        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
////        response.addHeader("Access-Control-Expose-Headers", "Cookie");
//        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
//        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, Cookie");
//        response.addHeader("Access-Control-Allow-Credentials", "true");
//    }
}
