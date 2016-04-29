package muni.fi.dp.jz.jbatch.dtos;

import java.util.List;

public class JobExecutionListDto {
	private List<JobExecutionDto> allJobExecutions;

	public List<JobExecutionDto> getAllJobExecutions() {
		return allJobExecutions;
	}

	public void setAllJobExecutions(List<JobExecutionDto> allJobExecutions) {
		this.allJobExecutions = allJobExecutions;
	}
	
	
}
