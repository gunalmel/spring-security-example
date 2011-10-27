package edu.umich.med.michr.scheduling.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.springframework.util.StringUtils;

@MappedSuperclass
public abstract class AbstractPerson {
	
	@Column(name="LAST_NAME")
	private String last;
	@Column(name="FIRST_NAME")
	private String first;
	@Column(name="MIDDLE_NAME")	
	private String middle;

	public AbstractPerson(){}
	
	public AbstractPerson (String last, String first) 
	{
		this.last = last==null?null:last.trim();
		this.first = first==null?null:first.trim();
	}
	public AbstractPerson (String last, String first, String middle) 
	{
		this (last, first);
		this.middle = middle==null?null:middle.trim();
	}
	
	public String getLast() {
		return last;
	}
	public void setLast(String last) {
		this.last = StringUtils.capitalize(last==null?null:last.trim());
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = StringUtils.capitalize(first==null?null:first.trim());
	}
	public String getMiddle() {
		return middle;
	}
	public void setMiddle(String middle) {
		this.middle = middle==null?null:middle.trim();
	}
	public String getFullName(){
		String full = new String();
		if(middle==null||middle.trim().length()==0)
			full=first+" "+last;
		else
			full=first+" "+middle.trim()+" "+last;
		return full;
	}

	// Field name constants to be used by Hibernate Criteria API
	public static final String LAST="last";
	public static final String FIRST="first";
	public static final String MIDDLE="middle";
}
