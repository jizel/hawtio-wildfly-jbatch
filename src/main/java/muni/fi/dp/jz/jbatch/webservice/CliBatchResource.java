/*
 * Copyright 2016 JBoss by Red Hat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package muni.fi.dp.jz.jbatch.webservice;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.Cookie;
//import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import muni.fi.dp.jz.jbatch.service.CliService;
import org.apache.log4j.Logger;
import org.jboss.security.annotation.SecurityDomain;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import org.json.JSONObject;

/**
 *
 * @author Zorz
 */
@Stateless
@Path("cli")
//@WebService
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@DeclareRoles({"admin", "supervisor", "user"})
//@SecurityDomain("jboss-web-policy")
//@WebContext(contextRoot="/*", urlPattern="/*", authMethod="BASIC", transportGuarantee="NONE", secureWSDLAccess=false)
public class CliBatchResource {
    
    @EJB
    private CliService cliService;
    private static final Logger LOG = Logger.getLogger( CliBatchResource.class.getName() );
    private static final java.util.logging.Logger LOG1 = java.util.logging.Logger.getLogger(CliBatchResource.class.getName());
    
    
    @GET
    @Path("start/{deployment}/{jobName}")
//    @RolesAllowed("admin")    
    public Response startJobCli(@PathParam("deployment") String deploymentName, @PathParam("jobName") String jobName){
        String resp = cliService.startJobCli(deploymentName, jobName);
//            String resp = jsessionId;
//            Map<String, Cookie> existingCookies = headers.getCookies();
//            JSONObject cookieMap = new JSONObject(existingCookies);
//                List<String> jsid = headers.getRequestHeader("Auth-Token");
//                JSONObject jsonJsid = new JSONObject(jsid);
        LOG1.warning("Job " + jobName + " started via cli! Server response returned.\n");
        System.out.println("Job " + jobName + " started via cli! Server response returned.\n");
//        System.out.println("Cookies: " + cookieMap.toString());
//        System.out.println("CookiesPar: " + jsessionId);
//        System.out.println("Req headers: " + jsid.toString());
//        System.out.println("Auth token header param: " + authToken);
        
//        for(String header : headers.getRequestHeaders().keySet()){
//        LOG1.warning("Header: " + header);
            
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();        
    }   
    
    @GET
    @Path("start/{deployment}/{jobName}/{properties}")
    @RolesAllowed("admin")
    public Response startJobCli(@PathParam("deployment") String deploymentName, @PathParam("jobName") String jobName, @PathParam("properties") String properties){
       
        Properties props = new Properties();
        JSONObject jsonResp = new JSONObject();
        try {
            props.load(new StringReader(properties));
        } catch (IOException ex) {
            LOG.error("Invalid job properties caused an exception: " + ex.toString());
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        } catch(ForbiddenException ex) {
            jsonResp.put("outcome","failed");
            jsonResp.put("description","User not allowed to start the job: " + ex.toString());
             LOG.error("Unauthorized operation: start job via cli: " + ex.toString());
            return Response.ok(jsonResp, MediaType.APPLICATION_JSON).build();
        }
        String resp = cliService.startJobCli(deploymentName, jobName, props);
        LOG.info("Job " + jobName + " started via cli! Server response returned.\n");
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();        
    }   
    
    @GET        
    @Path("deployments")
    @PermitAll
    public Response getDeploymentInfo(){
       String resp = cliService.getDeploymentInfo();        
        LOG.info("\nDeployments from server requested\n");                
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }
    
    @GET        
    @Path("batchDepl")
    @PermitAll
    public Response getBatchDeployments(){
//        @Context HttpHeaders headers, @Context HttpServletRequest request, @CookieParam("JSESSIONID") String jsessionId, 
                    ArrayList<String> values = new ArrayList<>();
//         Map<String, Cookie> existingCookies = headers.getCookies();//                 
//                 Cookie[] cookies = request.getCookies();
//                 for(Cookie cookie:cookies){
//                     values.add(cookie.toString());
//                     values.add(cookie.getName());
//                 }
//            HttpSession session = request.getSession(false);
//            Enumeration foo = session.getAttributeNames();
//            while(foo.hasMoreElements()){
//               String element = (String) foo.nextElement();
//               values.add(element);
//            }
//            String id = session.getId();   
//                Object foo2 = session.getAttribute("WELD_S_HASH");

         
       String resp = cliService.getBatchDeploymentsWithJobs();        
        LOG.info("\nBatch deployments only requested from server\n");  
//        for(Cookie cookie : cookies){
//            LOG.info("\nget cookies: " + cookie.getName() + "val: " + cookie.getValue()); 
//        }
//        for(String header : headers.getRequestHeaders().keySet()){
//        LOG1.warning("Header: " + header);
//        }
        return Response.ok(resp, MediaType.APPLICATION_JSON)
//                .header("Access-Control-Allow-Origin", "http://localhost:8080/")
//                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
//                .header("Access-Control-Allow-Headers", "Cookie")
//                .header("Access-Control-Expose-Headers", "Cookie")
//                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }
    
    @GET        
    @Path("deploymentJobs/{deployment}")
    @PermitAll
    public Response getDeploymentJobs(@PathParam("deployment") String deployment){
       String deploymentJobs = cliService.getJobsFromDeployment(deployment);
//       TODO: Take just the job part from resp??
        LOG.info("\nAll possible jobs for deployment" + deployment + "returned via rest\n");                
        return Response.ok(deploymentJobs, MediaType.APPLICATION_JSON).build();
    }
}
