package edu.umich.med.michr.scheduling.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.StringUtils;

@Entity
@Table(name = "USER_DETAILS")
@PrimaryKeyJoinColumn(name = "USER_ID")
public class User extends AbstractUser {

	private static final long serialVersionUID = 1L;
	@NotEmpty()
	@Size(min = 0, max = 64)
	@Column(name = "LAST_NAME")
	private String last;
	@NotEmpty()
	@Size(min = 0, max = 64)
	@Column(name = "FIRST_NAME")
	private String first;
	@Size(min = 0, max = 25)
	@Column(name = "MIDDLE_NAME")
	private String middle;

	@NotEmpty()
	@Size(min = 0, max = 32)
	@Column(name = "UNIQUE_NAME")
	private String uniqueName;
	@NotEmpty()
	@Size(min = 0, max = 32)
	@Column(name = "EMPLOYEE_ID")
	private String employeeId;
	@NotEmpty()
	@Size(min = 0, max = 128)
	@Column(name = "INSTITUTION_NAME")
	private String institutionName;
	@NotEmpty()
	@Size(min = 0, max = 128)
	@Column(name = "DEPARTMENT_NAME")
	private String departmentName;
	@NotEmpty()
	@Size(min = 0, max = 32)
	@Column(name = "PHONE")
	private String phone;
	@Size(min = 0, max = 16)
	@Column(name = "PAGER")
	private String pager;

	public String getLastName() {
		return last;
	}

	public void setLastName(String last) {
		this.last = StringUtils.capitalize(last == null ? null : last.trim());
	}

	public String getFirstName() {
		return first;
	}

	public void setFirstName(String first) {
		this.first = StringUtils
				.capitalize(first == null ? null : first.trim());
	}

	public String getMiddleName() {
		return middle;
	}

	public void setMiddle(String middle) {
		this.middle = middle == null ? null : middle.trim();
	}

	public String getFullName() {
		String full = new String();
		if (middle == null || middle.trim().length() == 0)
			full = first + " " + last;
		else
			full = first + " " + middle.trim() + " " + last;
		return full;
	}

	public String getUniqueName() {
		return uniqueName;
	}

	public void setUniqueName(String uniqueName) {
		this.uniqueName = uniqueName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getInstitutionName() {
		return institutionName;
	}

	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPager() {
		return pager;
	}

	public void setPager(String pager) {
		this.pager = pager;
	}

	/**
	 * Returns the first role in thr list of roles assigned to this user. Useful
	 * in the case where user to role relationship will be one to one
	 * 
	 * @return
	 */
	@Transient
	public Role getRole() {
		if (getRoles() != null && getRoles().size() > 0)
			return getRoles().iterator().next();
		return null;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof User)) {
			return false;
		}
		User other = (User) obj;
		if (this.getUsername() == null) {
			if (other.getUsername() != null)
				return false;
		} else if (!this.getUsername().equals(other.getUsername())) {
			return false;
		}
		return true;
	}

}
