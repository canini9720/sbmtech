package com.sbmtech.security.services;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sbmtech.model.User;
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Long userId;
	
	private String username;
	private String firstname;
	private String lastname;
	private Integer memberCategory;
	@JsonIgnore
	private String password;
	private Boolean enabled;
	private Boolean verified;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public UserDetailsImpl(Long userId, String username, String firstname,String lastname,Integer memberCategory, String password,Boolean enabled,
			Boolean verified, Collection<? extends GrantedAuthority> authorities) {
		this.userId = userId;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.memberCategory = memberCategory;
		this.password = password;
		this.enabled=enabled;
		this.verified=verified;
		this.authorities = authorities;
	}
	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());
		return new UserDetailsImpl(
				user.getUserId(), 
				user.getUsername(), 
				user.getFirstname(),
				user.getLastname(),
				user.getMemberCategory(),
				user.getPassword(), 
				user.getEnabled(),
				user.getVerified(),
				authorities);
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	public Long getUserId() {
		return userId;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	public boolean isVerified() {
		return verified;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(userId, user.userId);
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public Integer getMemberCategory() {
		return memberCategory;
	}
	
}