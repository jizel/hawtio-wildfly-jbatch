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
package muni.fi.dp.jz.jbatch.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.batch.runtime.StepExecution;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import muni.fi.dp.jz.jbatch.batchapi.BatchExecutionBean;
import muni.fi.dp.jz.jbatch.dtos.JobExecutionDto;
import muni.fi.dp.jz.jbatch.dtos.JobInstanceDto;
import muni.fi.dp.jz.jbatch.dtos.StepExecutionDto;
import muni.fi.dp.jz.jbatch.exception.BatchExecutionException;
import muni.fi.dp.jz.jbatch.service.JobService;
import muni.fi.dp.jz.jbatch.util.JobExecutionToDto;
import muni.fi.dp.jz.jbatch.util.JobInstanceToDto;
import muni.fi.dp.jz.jbatch.util.StepExecutionToDto;

/**
 *
 * @author Zorz
 */
@Stateless
public class JobServiceImpl implements JobService{

    @EJB
//    @Inject
    BatchExecutionBean batchExecutor;        
    
//    TODO - catch exceptions
    
    @Override
    public long submitJob(String jobName) throws BatchExecutionException{
        return batchExecutor.submitJob(jobName);
    }
    
    @Override
    public long submitJob(String jobName, Properties propertis) throws BatchExecutionException {
        return batchExecutor.submitJob(jobName, propertis);
    }

    @Override
    public JobExecution getJobExecution(long executionId) throws BatchExecutionException {
        return batchExecutor.getJobExecution(executionId);
    }

    @Override
    public long restartJob(long executionId) throws BatchExecutionException {
        return batchExecutor.restartJob(executionId);
    }

    @Override
    public Set<String> getJobNames() throws BatchExecutionException {
        return batchExecutor.getJobNames();
    }

    @Override
    public int getJobInstanceCount(String jobName) throws BatchExecutionException {
        return batchExecutor.getJobInstanceCount(jobName);
    }

    @Override
    public List<Long> getRunningExecutions(String jobName) throws BatchExecutionException{
        return batchExecutor.getRunningExecutions(jobName);
    }

    @Override
    public List<JobInstanceDto> getJobInstances(String jobName) throws BatchExecutionException{
        List<JobInstance> jobInstances = batchExecutor.getJobInstances(jobName);
        List<JobInstanceDto> jobInstanceDtos = new ArrayList<>();
        for(JobInstance jobInstance:jobInstances){
                jobInstanceDtos.add(JobInstanceToDto.createDtoFromJobInstace(jobInstance));
            }
        return jobInstanceDtos;
    }

    @Override
    public List<JobExecutionDto> getJobExecutions(JobInstance instance) throws BatchExecutionException{
        //TODO: implement with Dtos and catch NoSuchJobException
//        List<JobInstance> jobInstances = batchExecutor.getJobInstances(instance.getJobName(), (int) instance.getJobInstanceId(), 1);        
//        List<JobExecution> executions = batchExecutor.getJobExecutions(jobInstances.get(0));
        List<JobExecution> executions = batchExecutor.getJobExecutions(instance);
        List<JobExecutionDto> jobExecutionDtos = new ArrayList<>();        
        for(JobExecution jobExec:executions){
            jobExecutionDtos.add(JobExecutionToDto.createDtoFromJobExecution(jobExec));
        }
        return jobExecutionDtos;
    }

    @Override
    public List<JobExecutionDto> getJobExecutions(String jobName, long instanceId) throws BatchExecutionException{
//TODO: make this work with dtos (after previous method!)
//       List<JobInstanceDto> allJobInstanceDtos = getJobInstances(jobName);       
//       JobInstanceDto jobInstance = null;
//       for(JobInstanceDto instance:allJobInstanceDtos){
//           if(instance.getJobInstanceId() == instanceId){
//               jobInstance = instance;
//           }
//       }
        
        List<JobInstance> allInstances = batchExecutor.getJobInstances(jobName);
        JobInstance jobInstance = null;
        for(JobInstance inst: allInstances){
            if(inst.getInstanceId() == instanceId){
                jobInstance = inst;
            }
        }
        return getJobExecutions(jobInstance);
    }
    
    @Override
    public List<StepExecutionDto> getStepExecutions(long jobExecutionId) throws BatchExecutionException{
        List<StepExecutionDto> stepExecutionDtos = new ArrayList<>();        
        List<StepExecution> stepExecutions = batchExecutor.getStepExecutions(jobExecutionId);
        for(StepExecution stepExec:stepExecutions){
            stepExecutionDtos.add(StepExecutionToDto.createDtoFromStepExecution(stepExec));
        }
        return stepExecutionDtos;
    }

    @Override
    public Map<String,List<JobInstanceDto>> getAllJobInstances() throws BatchExecutionException{
        Set<String> allJobs = getJobNames();
        
        Map<String,List<JobInstanceDto>> allJobInstances = new HashMap<>();
        
        
        for(String job:allJobs){
            List<JobInstanceDto> jobInstances = getJobInstances(job);
            
            allJobInstances.put(job, jobInstances);
        }
        System.out.println(allJobInstances);
        return allJobInstances;
    }

    @Override
    public List<JobInstanceDto> getAllInstances() throws BatchExecutionException{
       Set<String> allJobs = getJobNames();  
       List<JobInstanceDto> jobInstances = new ArrayList<>();
        for(String job:allJobs){
            jobInstances = getJobInstances(job);            
                        
        }        
        return jobInstances;
    }    

    @Override
    public void stop(long executionId) throws BatchExecutionException{
        batchExecutor.stop(executionId);
    }

    @Override
    public void abandon(long executionId) throws BatchExecutionException{
        batchExecutor.abandon(executionId);
    }    

    
    
}
