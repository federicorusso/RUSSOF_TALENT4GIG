import java.sql.Date;

import lombok.Data;

@Data
public class ProcessInfo {

	private int id;
	
	private String name;
	
	private String description;
	
	private String periodicity;
	
	private String category;
	
	private Date creationTime;
	
	private Date processingStartTime;
	
	private Date processingEndTime;
	
	private ProcessStateEnum processState = ProcessStateEnum.NOTSTARTED;
	
	private String processStateDesc;
	
	public ProcessInfo(int id, String name, String desc, String period, Date startTime, Date endTime) {
		this.id = id;
		this.name = name;
		this.description = desc;
		this.periodicity = period;
		this.processingStartTime = startTime;
		this.processingEndTime = endTime;
	}
}
