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
import java.util.Properties;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import muni.fi.dp.jz.jbatch.service.CliService;
import org.apache.log4j.Logger;
import javax.ws.rs.ForbiddenException;
import org.json.JSONObject;

/**
 *
 * @author Zorz
 */
@Stateless
@Path("cli")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@DeclareRoles({"admin", "supervisor", "user"})
public class CliBatchResource {
    
    @EJB
    private CliService cliService;
    private static final Logger LOG = Logger.getLogger( CliBatchResource.class.getName() );        
    
    @GET
    @Path("start/{deployment}/{jobName}")
    @RolesAllowed("admin")   
    public Response startJobCli(@PathParam("deployment") String deploymentName, @PathParam("jobName") String jobName){
        try {
        String resp = cliService.startJobCli(deploymentName, jobName); 
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();    
        } catch(EJBAccessException e){
            JSONObject jsonResp = constructJsonFailed("failed","User not allowed to start the job",e);
            return Response.ok(jsonResp, MediaType.APPLICATION_JSON).build();
        }

            
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
        try{
        String resp = cliService.startJobCli(deploymentName, jobName, props);
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();  
        } catch(EJBAccessException e){
            jsonResp = constructJsonFailed("failed","User not allowed to start the job",e);
            return Response.ok(jsonResp, MediaType.APPLICATION_JSON).build();
        }
//        LOG.info("Job " + jobName + " started via cli! Server response returned.\n");
              
    }   
    
    @GET        
    @Path("deployments")
    @PermitAll
    public Response getDeploymentInfo(){
       String resp = cliService.getDeploymentInfo();        
//        LOG.info("\nDeployments from server requested\n");                
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }
    
    @GET        
    @Path("batchDepl")
    @PermitAll
    public Response getBatchDeployments(){         
       String resp = cliService.getBatchDeploymentsWithJobs();        
//        LOG.info("\nBatch deployments only requested from server\n");  
        
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }
    
    @GET        
    @Path("deploymentJobs/{deployment}")
    @PermitAll
    public Response getDeploymentJobs(@PathParam("deployment") String deployment){
       String deploymentJobs = cliService.getJobsFromDeployment(deployment);
//       Take just the job part from resp??
//        LOG.info("\nAll possible jobs for deployment" + deployment + "returned via rest\n");                
        return Response.ok(deploymentJobs, MediaType.APPLICATION_JSON).build();
    }
    
//    Help
    public JSONObject constructJsonFailed(String outcome, String description, Exception e){
            JSONObject jsonResp = new JSONObject();
            jsonResp.put("outcome",outcome);
            jsonResp.put("description",description + ". Exception: " + e.toString());
            return jsonResp;
    }
}
