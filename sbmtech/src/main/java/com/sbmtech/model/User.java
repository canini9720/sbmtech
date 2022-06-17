package com.sbmtech.model;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(	name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long userId;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="member_cat")
	private Integer memberCategory;
	
	private String username;

	private String password;
	
	@Column(name="email")
	private String email;
	
	@Column(name = "enabled", columnDefinition="BIT")
	private Boolean enabled;
	
	@Column(name = "verified", columnDefinition="BIT")
	private Boolean verified;
	
	
	public User() {
	}
	public User(String username, String firstname,String lastname, Integer memberCategory, String password,String email) {
		this.username = username;
		this.password = password;
		this.firstname=firstname;
		this.lastname=lastname;
		this.memberCategory=memberCategory;
		this.email=email;
	}

	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	
	
	@OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="id", insertable=false, updatable=false)
	private MemberPersonalDetailEntity memberPersonalDetailEntity;
	
	
	@OneToMany(mappedBy="userEntity",fetch=FetchType.LAZY,targetEntity=MemberContactDetailEntity.class,cascade = CascadeType.ALL)
	private List<MemberContactDetailEntity> memeberConactList= new ArrayList<MemberContactDetailEntity>();
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public Integer getMemberCategory() {
		return memberCategory;
	}
	public void setMemberCategory(Integer memberCategory) {
		this.memberCategory = memberCategory;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Boolean getVerified() {
		return verified;
	}
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
	public MemberPersonalDetailEntity getMemberPersonalDetailEntity() {
		return memberPersonalDetailEntity;
	}
	public void setMemberPersonalDetailEntity(MemberPersonalDetailEntity memberPersonalDetailEntity) {
		this.memberPersonalDetailEntity = memberPersonalDetailEntity;
	}
	
	public void addContactDetail(MemberContactDetailEntity memberEntity) {
		memeberConactList.add(memberEntity);
		memberEntity.setUserEntity(this);
	}
	public List<MemberContactDetailEntity> getMemeberConactList() {
		return memeberConactList;
	}
	public void setMemeberConactList(List<MemberContactDetailEntity> memeberConactList) {
		this.memeberConactList = memeberConactList;
	}
	
	
}