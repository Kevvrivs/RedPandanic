package Model;

import java.util.ArrayList;

public class Group {
	private int groupId;
	private String name;
	private ArrayList<Member> memberList;
	private ArrayList<EmergencyContact> contactList;
	private ArrayList<Item> itemList;
	
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Member> getMemberList() {
		return memberList;
	}
	public void setMemberList(ArrayList<Member> memberList) {
		this.memberList = memberList;
	}
	public ArrayList<EmergencyContact> getContactList() {
		return contactList;
	}
	public void setContactList(ArrayList<EmergencyContact> contactList) {
		this.contactList = contactList;
	}
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}
	
	
}
