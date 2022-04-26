package Banking.ActivityLog;

import java.time.LocalDateTime;

public class ActivityLog {
	protected ActivityName name;
	protected double balanceChange;
	protected LocalDateTime timeStamp;
	protected String info;

	// CONSTRUCTORS
	public ActivityLog(ActivityName name, double balanceChange, LocalDateTime timeStamp, String info) {
		super();
		this.name = name;
		this.balanceChange = balanceChange;
		this.timeStamp = timeStamp;
		this.info = info;
	}
	
	public ActivityLog(ActivityName name, double balanceChange, LocalDateTime timeStamp) {
		this(name, balanceChange, timeStamp, "");
	}
	
	public ActivityLog(ActivityName name, double balanceChange) {
		this(name, balanceChange, LocalDateTime.now(), "");
	}
	
	public ActivityLog(ActivityName name, double balanceChange, String info) {
		this(name, balanceChange, LocalDateTime.now(), info);
	}
	
	public ActivityLog(ActivityName name){
		this(name, 0f, LocalDateTime.now(), "");
	}
	
	public ActivityLog(){
		this(ActivityName.EMPTY, 0f, LocalDateTime.now(), "");
	}

	@Override
	public String toString() {
		return "ActivityLog ["+ name + ", balanceChange= " + balanceChange + ", timeStamp=" + timeStamp + ", info="+ info + "]";
	}
	
}
