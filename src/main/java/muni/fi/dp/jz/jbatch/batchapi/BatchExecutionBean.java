/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package muni.fi.dp.jz.jbatch.batchapi;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.batch.operations.JobExecutionAlreadyCompleteException;
import javax.batch.operations.JobExecutionIsRunningException;
import javax.batch.operations.JobExecutionNotMostRecentException;
import javax.batch.operations.JobExecutionNotRunningException;
import javax.batch.operations.JobOperator;
import javax.batch.operations.JobRestartException;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.batch.operations.NoSuchJobException;
import javax.batch.operations.NoSuchJobExecutionException;
import javax.batch.operations.NoSuchJobInstanceException;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.batch.runtime.StepExecution;
import javax.ejb.Stateless;
import muni.fi.dp.jz.jbatch.exception.BatchExecutionException;
/**
 *
 * @author Zorz
 * 
 * Service layer bean for batch job operations
 * Implements JSR352 JobOperator methods
 */

@Stateless
public class BatchExecutionBean { //implements JobOperator{ - causes weldClassInspector Exception when deploying
    
    private JobOperator jobOperator;
    private static final Logger LOG = Logger.getLogger(BatchExecutionBean.class.getName());    
    
    @PostConstruct
    private void getJobOperatorFromRuntime(){
        jobOperator = BatchRuntime.getJobOperator();
    }
    
    public long submitJob(String jobName) throws BatchExecutionException {
        if(jobName == null){
            throw new IllegalArgumentException("Cannot start job with jobname null");
        }
        try {
        Properties jobPropertis = new Properties();
        long executionId = jobOperator.start(jobName, jobPropertis);
        return executionId;
        }catch(JobSecurityException | JobStartException e){
            LOG.log(Level.SEVERE, "Exception raised while starting new job: " + e);
            throw new BatchExecutionException("Exception raised while starting new job: ",e);
        }
    }
    
    public long submitJob(String jobName, Properties properties) throws BatchExecutionException {
        if(jobName == null){
            throw new IllegalArgumentException("Cannot start job with jobname null");
        }
        try {        
        long executionId = jobOperator.start(jobName, properties);
        return executionId;
        }catch(JobSecurityException | JobStartException e){
            LOG.log(Level.SEVERE, "Exception raised while starting new job: " + e);
            throw new BatchExecutionException("Exception raised while starting new job: ",e);
        }
    }
        
    public JobExecution getJobExecution(long executionId)throws BatchExecutionException {
        if(executionId < 1){
            throw new IllegalArgumentException("Execution id cannot be less than 1");
        }
        try{
        JobExecution jobExecution = jobOperator.getJobExecution(executionId);
        return jobExecution;
         }catch(NoSuchJobInstanceException|JobSecurityException|NoSuchJobExecutionException e){
             LOG.log(Level.SEVERE, "Exception raised while getting job execution id: {0}\n{1}", new Object[]{executionId, e});
            throw new BatchExecutionException("Exception raised while getting job execution id: " + executionId,e);
        }
    }
     
    public long restartJob(long executionId) throws BatchExecutionException { 
         if(executionId < 1){
            throw new IllegalArgumentException("Execution id cannot be less than 1");
        }
        try {
        long newExecutionId = jobOperator.restart(executionId,jobOperator.getJobExecution(executionId).getJobParameters());
        return newExecutionId;
        }catch(JobSecurityException|JobRestartException|JobExecutionNotMostRecentException|NoSuchJobExecutionException|JobExecutionAlreadyCompleteException e){
            LOG.log(Level.SEVERE, "Exception raised while restarting execution id: " + executionId +"\n" + e);
            throw new BatchExecutionException("Exception raised while restarting execution id: " + executionId,e);
        }
    }
    
    public Set<String> getJobNames() throws BatchExecutionException{
        Set<String> jobNames = null;
        try{
        jobNames = jobOperator.getJobNames();
        }catch(JobSecurityException e){
            LOG.log(Level.SEVERE, "Exception raised while getting job names: " + e);
            throw new BatchExecutionException("Exception raised while getting job names: ",e);
        }
        return jobNames;
    }
    
    public int getJobInstanceCount(String jobName) throws BatchExecutionException{   
        if(jobName == null){
            throw new IllegalArgumentException("Cannot get instance count for jobname null");
        }
       try{
        int jobInstanceCount = jobOperator.getJobInstanceCount(jobName);
        return jobInstanceCount;
       }catch(JobSecurityException | NoSuchJobException e){
           LOG.log(Level.SEVERE, "Exception raised while getting job instance count: " + e);
            throw new BatchExecutionException("Exception raised while getting job instance count: ",e);
        }       
    }
    
    public List<Long> getRunningExecutions(String jobName) throws BatchExecutionException {
        if(jobName == null){
            throw new IllegalArgumentException("Cannot get Running Executions for jobname null");
        }
    	try {
        return jobOperator.getRunningExecutions(jobName);      
       }catch(JobSecurityException | NoSuchJobException e){
           LOG.log(Level.SEVERE, "Exception raised while getting running executions: " + e);
            throw new BatchExecutionException("Exception raised while getting running executions: ",e);
        }        
    }
    
    public List<JobInstance> getJobInstances(String jobName) throws BatchExecutionException {
        if(jobName == null){
            throw new IllegalArgumentException("Cannot get job instances for jobname null");
        }
    	try {
        return jobOperator.getJobInstances(jobName, 0, 200);
        }catch(JobSecurityException | NoSuchJobException e){
             LOG.log(Level.SEVERE, "Exception raised while getting job instances of: " + e);
            throw new BatchExecutionException("Exception raised while getting job instances of: " + jobName,e);
        } 
    }
    
    public List<JobInstance> getJobInstances(String jobName, int start, int count) throws BatchExecutionException {
        if(jobName == null || start < 0){
            throw new IllegalArgumentException("Cannot get job instances for jobname null");
        }
    	try {
        return jobOperator.getJobInstances(jobName, start, count);
        }catch(JobSecurityException e){
            LOG.log(Level.SEVERE, "Exception raised while getting job instances of: " + e);
            throw new BatchExecutionException("Exception raised while getting job instances of: " + jobName,e);
        } 
    }    
    public List<JobExecution> getJobExecutions(JobInstance instance) throws BatchExecutionException {
        if(instance == null){
            throw new IllegalArgumentException("Cannot get job executions for null");
        }
    	try {
        return jobOperator.getJobExecutions(instance);
        }catch(JobSecurityException|NoSuchJobInstanceException e){
             LOG.log(Level.SEVERE, "Exception raised while getting job executions for instance: " + instance.toString() + "\n" + e);
            throw new BatchExecutionException("Exception raised while getting job executions for instance: " + instance.toString(),e);
        } 
    }
     
    public List<StepExecution> getStepExecutions(long jobExecutionId) throws BatchExecutionException{
        if(jobExecutionId < 1){
            throw new IllegalArgumentException("Execution id cannot be less than 1");
        }
    	try{
        return jobOperator.getStepExecutions(jobExecutionId);
        }catch(JobSecurityException|NoSuchJobExecutionException e){
            LOG.log(Level.SEVERE, "Exception raised while getting step executions for execution id: " + e);
            throw new BatchExecutionException("Exception raised while getting step executions for execution id: " + jobExecutionId,e);
        } 
    }
    
    //Gets parent instance for job execution not instance by id!!!
    public JobInstance getJobInstance(long executionId) throws BatchExecutionException {
        if(executionId < 1){
            throw new IllegalArgumentException("Execution id cannot be less than 1");
        }
        try {
        JobInstance jobInstance = jobOperator.getJobInstance(executionId);
        return jobInstance;
        }catch(JobSecurityException|NoSuchJobExecutionException e){
            LOG.log(Level.SEVERE, "Exception raised while getting job instance for execution id: " + e);
            throw new BatchExecutionException("Exception raised while getting job instance for execution id: " + executionId,e);
        } 
    }
    
    
    public void stop(long executionId) throws BatchExecutionException {
        if(executionId < 1){
            throw new IllegalArgumentException("Execution id cannot be less than 1");
        }
        try{
            jobOperator.stop(executionId);
        }catch(JobSecurityException | NoSuchJobExecutionException | JobExecutionNotRunningException e) {
             LOG.log(Level.SEVERE, "Exception raised while getting job names: " + e);
             throw new BatchExecutionException("Exception raised while stopping job id: " + executionId,e);
        }
    }
    
    public void abandon(long executionId) throws BatchExecutionException {
        try {
            jobOperator.abandon(executionId);
         }catch(JobSecurityException | NoSuchJobExecutionException | JobExecutionIsRunningException e) {
             LOG.log(Level.SEVERE, "Exception raised while abandoning execution id: " +executionId + "\n" + e.toString());
             throw new BatchExecutionException("Exception raised trying to abandon execution: ",e);
        }
    }    
    
    public Properties getParameters(long execId) throws BatchExecutionException {
        try{
            return jobOperator.getParameters(execId);
        }catch(JobSecurityException | NoSuchJobExecutionException e) {
            LOG.log(Level.SEVERE, "Exception raised while getting job params" + e);
             throw new BatchExecutionException("Exception raised while getting job params: ",e);
        }
    }
//
//    @Override
//    public long start(String string, Properties prprts) throws JobStartException, JobSecurityException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public long restart(long l, Properties prprts) throws JobExecutionAlreadyCompleteException, NoSuchJobExecutionException, JobExecutionNotMostRecentException, JobRestartException, JobSecurityException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }    
//
//    @Override
//    public JobExecution getJobExecution(long l) throws NoSuchJobExecutionException, JobSecurityException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

   
    
}
