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

import java.util.List;
import javax.batch.runtime.JobExecution;
import javax.batch.runtime.JobInstance;
import javax.ejb.EJB;
import muni.fi.dp.jz.jbatch.dtos.JobInstanceDto;
import muni.fi.dp.jz.jbatch.service.JobService;

/**
 *
 * @author Zorz
 */
public class JobInstanceToDto {
    
    public static JobInstanceDto createDtoFromJobInstace(JobInstance jobInstance){
        JobInstanceDto jobInstanceDto = new JobInstanceDto();
        jobInstanceDto.setJobName(jobInstance.getJobName());
        jobInstanceDto.setJobInstanceId(jobInstance.getInstanceId());       
//        Get list of instance's jobexecutions'        
//        List<JobExecution> jobExecutions = jobService.getJobExecutions(jobInstance);
//        long[] jobInstanceExecutionsIds = null;
//        int i = 0;
//        for(JobExecution jobExec:jobExecutions){
//            jobInstanceExecutionsIds[i] = jobExec.getExecutionId();
//            i++;
//        }
//        jobInstanceDto.setJobInstanceExecutionsIds(jobInstanceExecutionsIds);
        return jobInstanceDto;
    }
}
