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

import javax.batch.runtime.StepExecution;
import muni.fi.dp.jz.jbatch.dtos.StepExecutionDto;

/**
 *
 * @author Zorz
 */
public class StepExecutionToDto {
    
     public static StepExecutionDto createDtoFromStepExecution(StepExecution stepExecution){
         StepExecutionDto stepExecutionDto = new StepExecutionDto();
         stepExecutionDto.setStepExecutionId(stepExecution.getStepExecutionId());
         stepExecutionDto.setStepName(stepExecution.getStepName());
         stepExecutionDto.setBatchStatus(stepExecution.getBatchStatus());
         stepExecutionDto.setExitStatus(stepExecution.getExitStatus());
         stepExecutionDto.setStartTime(stepExecution.getStartTime());
         stepExecutionDto.setEndTime(stepExecution.getEndTime());
         stepExecutionDto.setMetrics(stepExecution.getMetrics());
         
         return stepExecutionDto;
     }
    
}
