package edu.umich.med.michr.scheduling.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "USER_ACCOUNT",uniqueConstraints={@UniqueConstraint(name="USER_ACCOUNT_UQ",columnNames={"USER_NAME"})})
public abstract class AbstractUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	// Static constants are created for Hibernate criteria queries to easily refer to correct class field names.
	public static final String ENABLED = "enabled";
	public static final String USER_NAME = "username";
	public static final String PASSWORD = "password";
	public static final String PASSWORD_ANSWER = "passwordAnswer";
	public static final String LOCKED = "locked";
	public static final String LAST_LOGIN_DATE = "lastLoginDate";
	public static final String LAST_PWD_CHANGE_DATE = "lastPasswordChangeDate";

	@Id @Column(name="ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userIdSequence")
	@SequenceGenerator(name="userIdSequence", sequenceName = "SEQ_USER_ACCOUNT")
	private long id;
	@NotBlank()
	@Size(min=0,max=32)
	@NaturalId @Column(name="USER_NAME")
	private String username;
	@Column(name="PASSWORD_SALT")
	private String passwordSalt;
	@NotBlank
	@Size(min=7,max=128)
	@Column(name="PASSWORD")
	private String password;
	@Email
	@Column(name="EMAIL")
	private String email;
	@NotBlank()
	@Size(min=0,max=256)
	@Column(name="PASSWORD_QUESTION")
	private String passwordQuestion;
	@NotBlank()
	@Size(min=0,max=128)
	@Column(name="PASSWORD_ANSWER")
	private String passwordAnswer;
	@Column(name="ENABLED") 
	private Boolean enabled = false;
	@Column(name="LOCKED") 
	private Boolean locked = false;
	@Column(name="CREATED_DATE")
	private Date createdDate;
	@Column(name="LAST_LOGIN_DATE")
	private Date lastLoginDate;
	@Column(name="LAST_PASSWORD_CHANGED_DATE")
	private Date lastPasswordChangeDate;
	@Column(name="LAST_LOCK_OUT_DATE")
	private Date lastLockOutDate;
	@Column(name="COMMENTS")
	private String comments;
	@Transient
	private DateTime currentDateTime = new DateTime();
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="USER_ROLE", joinColumns=@JoinColumn(name="USER_ID"))
	@Column(name="ROLE_ID")
	@Type(type="edu.umich.med.michr.scheduling.helper.RoleUserType")
	private Set<Role> roles;
	
	/*Constructors start*/
	public AbstractUser(){}
	public AbstractUser(String username){
		this.username=username.toLowerCase();
	}
	public AbstractUser(String username,String pwd){
		this(username);
		this.password=pwd;
	}
	public AbstractUser(String username, String password, Boolean enabled) {
		this(username,password);
		this.enabled = enabled;
	}
	public AbstractUser(String username, String password, String email, Boolean enabled) {
		this(username,password,enabled);
		this.password = password;
		this.email = email;
	}
	public AbstractUser(String username, String password, String email,
			String passwordQuestion, String passwordAnswer, Boolean enabled) {
		this(username,password,email,enabled);
		this.passwordQuestion = passwordQuestion;
		this.passwordAnswer = passwordAnswer;
	}
	/*Constructors end*/
	
	public long getId() {
		return id;
	}
	@Override
	public String getUsername() {
		return username;
	}
	public void setUsername(String username){
		this.username=username.toLowerCase();
	}
	public String getPasswordSalt() {
		return passwordSalt;
	}
	public void setPasswordSalt(String passwordSalt) {
		this.passwordSalt = passwordSalt;
	}
	@Override
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
	public String getPasswordQuestion() {
		return passwordQuestion;
	}
	public void setPasswordQuestion(String passwordQuestion) {
		this.passwordQuestion = passwordQuestion;
	}
	public String getPasswordAnswer() {
		return passwordAnswer;
	}
	public void setPasswordAnswer(String passwordAnswer) {
		this.passwordAnswer = passwordAnswer;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public Boolean isLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastLoginDate() {
		return lastLoginDate;
	}
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	public Date getLastPasswordChangeDate() {
		return lastPasswordChangeDate;
	}
	public void setLastPasswordChangeDate(Date lastPasswordChangeDate) {
		this.lastPasswordChangeDate = lastPasswordChangeDate;
	}
	public Date getLastLockOutDate() {
		return lastLockOutDate;
	}
	public void setLastLockOutDate(Date lastLockOutDate) {
		this.lastLockOutDate = lastLockOutDate;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public Set<Role> getRoles(){
		return roles;
	}
	 public void setRoles(Set<Role> roles) {
	 	this.roles = roles;
	 }
	public void addRole(Role r){
		if(roles==null)
			roles=new HashSet<Role>();
		roles.add(r);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getRoles();
	}
	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}
	@Override
	public boolean isAccountNonExpired() {
		//TODO find a way to get account inactivity grace period from outside
		Days d = Days.daysBetween(new DateTime(getLastLoginDate()), currentDateTime);
		return d.getDays()<365;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		//TODO find a way to password grace period from outside
		Days d = Days.daysBetween(new DateTime(getLastPasswordChangeDate()), currentDateTime);
		return d.getDays()<365;
	}


}
