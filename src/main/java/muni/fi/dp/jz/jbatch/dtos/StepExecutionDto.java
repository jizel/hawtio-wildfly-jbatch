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
package muni.fi.dp.jz.jbatch.dtos;

import java.util.Date;
import java.util.Objects;
import javax.batch.runtime.BatchStatus;
import javax.batch.runtime.Metric;

/**
 *
 * @author Zorz
 */
public class StepExecutionDto {
    private long stepExecutionId;
    private String stepName;
    private BatchStatus batchStatus;
    private Date startTime;
    private Date endTime;
    private String exitStatus;
    private Metric[] metrics;

    public StepExecutionDto() {
    }    
    
    public long getStepExecutionId() {
        return stepExecutionId;
    }

    public void setStepExecutionId(long stepExecutionId) {
        this.stepExecutionId = stepExecutionId;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public BatchStatus getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(BatchStatus batchStatus) {
        this.batchStatus = batchStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getExitStatus() {
        return exitStatus;
    }

    public void setExitStatus(String exitStatus) {
        this.exitStatus = exitStatus;
    }

    public Metric[] getMetrics() {
        return metrics;
    }

    public void setMetrics(Metric[] metrics) {
        this.metrics = metrics;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.stepExecutionId ^ (this.stepExecutionId >>> 32));
        hash = 97 * hash + Objects.hashCode(this.stepName);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StepExecutionDto other = (StepExecutionDto) obj;
        if (this.stepExecutionId != other.stepExecutionId) {
            return false;
        }
        if (!Objects.equals(this.stepName, other.stepName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StepExecutionDto{" + "stepExecutionId=" + stepExecutionId + ", stepName=" + stepName + ", batchStatus=" + batchStatus + ", startTime=" + startTime + ", endTime=" + endTime + ", exitStatus=" + exitStatus + ", metrics=" + metrics + '}';
    }
    
    
    
}
