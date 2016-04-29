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
package muni.fi.dp.jz.jbatch.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.ejb.Stateless;
import muni.fi.dp.jz.jbatch.dtos.JobExecutionDto;
import muni.fi.dp.jz.jbatch.dtos.JobInstanceDto;
import muni.fi.dp.jz.jbatch.dtos.StepExecutionDto;
import muni.fi.dp.jz.jbatch.exception.BatchExecutionException;

/**
 *
 * @author Zorz
 */
@Stateless
public interface JobService {

    public long submitJob(String jobName) throws BatchExecutionException;
    
    public long submitJob(String jobName, Properties propertis) throws BatchExecutionException;

    public JobExecution getJobExecution(long executionId) throws BatchExecutionException;         

    public long restartJob(long executionId) throws BatchExecutionException;

    public Set<String> getJobNames() throws BatchExecutionException;

    public int getJobInstanceCount(String jobName) throws BatchExecutionException;

    public List<Long> getRunningExecutions(String jobName) throws BatchExecutionException;

    public List<JobInstanceDto> getJobInstances(String jobName) throws BatchExecutionException;

    public List<JobExecutionDto> getJobExecutions(JobInstance instance) throws BatchExecutionException;
    
    public List<JobExecutionDto> getJobExecutions(String jobName, long instanceId) throws BatchExecutionException;

    public List<StepExecutionDto> getStepExecutions(long jobExecutionId) throws BatchExecutionException;
    
    public Map<String,List<JobInstanceDto>> getAllJobInstances() throws BatchExecutionException;
    
    public List<JobInstanceDto> getAllInstances() throws BatchExecutionException;   
    
    public void stop(long executionId) throws BatchExecutionException;
    
    public void abandon(long executionId) throws BatchExecutionException;
    
}
