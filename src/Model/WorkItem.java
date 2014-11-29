package Model;

public class WorkItem {
	private String id;
	private String description;
	private String memberId;
	private String groupId;
	private boolean isDone;
	
	
	public String getWorkId() {
		return id;
	}
	public void setWorkId(String workId) {
		this.id = workId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public boolean isDone() {
		return isDone;
	}
	public void setDone(boolean isDone) {
		this.isDone = isDone;
	}
	
	
}
