package muni.fi.dp.jz.jbatch.dtos;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import javax.batch.runtime.BatchStatus;


public class JobExecutionDto {
	private long jobExecutionId;
	private String jobName;
//        private JobInstanceDto jobInstance;
//	private String parameters;
	private Date createTime;
	private Date startTime;
        private Date endTime;
        private Date lastUpdatedTime;
	private BatchStatus batchStatus;
	private String exitStatus;
//	private boolean stop = false;
	private Properties jobParameters;

    public long getJobExecutionId() {
        return jobExecutionId;
    }

    public String getJobName() {
        return jobName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public void setLastUpdatedTime(Date lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }    

    public BatchStatus getBatchStatus() {
        return batchStatus;
    }

    public String getExitStatus() {
        return exitStatus;
    }

    public Properties getJobParameters() {
        return jobParameters;
    }

    public void setJobExecutionId(long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public void setBatchStatus(BatchStatus batchStatus) {
        this.batchStatus = batchStatus;
    }

    public void setExitStatus(String exitStatus) {
        this.exitStatus = exitStatus;
    }

    public void setJobParameters(Properties jobParameters) {
        this.jobParameters = jobParameters;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (int) (this.jobExecutionId ^ (this.jobExecutionId >>> 32));
        hash = 29 * hash + Objects.hashCode(this.jobName);
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
        final JobExecutionDto other = (JobExecutionDto) obj;
        if (this.jobExecutionId != other.jobExecutionId) {
            return false;
        }
        if (!Objects.equals(this.jobName, other.jobName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "JobExecutionDto{" + "jobExecutionId=" + jobExecutionId + ", jobName=" + jobName + ", createTime=" + createTime + ", startTime=" + startTime + ", endTime=" + endTime + ", lastUpdatedTime=" + lastUpdatedTime + ", batchStatus=" + batchStatus + ", exitStatus=" + exitStatus + ", jobParameters=" + jobParameters + '}';
    }

    	
}
