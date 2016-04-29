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
package muni.fi.dp.jz.jbatch.util;

import javax.batch.runtime.JobExecution;
import muni.fi.dp.jz.jbatch.dtos.JobExecutionDto;

/**
 *
 * @author Zorz
 */
public class JobExecutionToDto {
    
    public static JobExecutionDto createDtoFromJobExecution(JobExecution jobExecution){
        JobExecutionDto jobExecutionDto = new JobExecutionDto();
        jobExecutionDto.setJobExecutionId(jobExecution.getExecutionId());
        jobExecutionDto.setJobName(jobExecution.getJobName());
        jobExecutionDto.setCreateTime(jobExecution.getCreateTime());
        jobExecutionDto.setEndTime(jobExecution.getEndTime());
        jobExecutionDto.setStartTime(jobExecution.getStartTime());
        jobExecutionDto.setLastUpdatedTime(jobExecution.getLastUpdatedTime());
        jobExecutionDto.setJobParameters(jobExecution.getJobParameters());
        jobExecutionDto.setBatchStatus(jobExecution.getBatchStatus());
        jobExecutionDto.setExitStatus(jobExecution.getExitStatus());
        
        return jobExecutionDto;
        
    }
    
}
