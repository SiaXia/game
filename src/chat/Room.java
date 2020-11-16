package chat;

import java.util.Vector;

public class Room {

	
	private String title; // room title
	private String boss; // room owner
	
	Vector<Service> user; // client info
	
	private int count; // room people
	
	// getter and setter
	public Room() {
		user = new Vector<>();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBoss() {
		return boss;
	}

	public void setBoss(String boss) {
		this.boss = boss;
	}

	public Vector<Service> getUser() {
		return user;
	}

	public void setUser(Vector<Service> user) {
		this.user = user;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
