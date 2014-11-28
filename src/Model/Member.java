package Model;

public class Member {
	private String memberId;
	private String memberName;
	private String username;
	private String password;
	private String email;
	
	
	public Member(String memberName,String username,String password, String email){
		this.memberName = memberName;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
