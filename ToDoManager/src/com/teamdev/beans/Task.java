package com.teamdev.beans;

import java.util.Calendar;

public class Task {
	
	private String title;
	private String description;
	private Calendar creationDate;
	private Calendar lastChangedDate;
	private String state;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Calendar getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}
	public Calendar getLastChangedDate() {
		return lastChangedDate;
	}
	public void setLastChangedDate(Calendar lastChangedDate) {
		this.lastChangedDate = lastChangedDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
