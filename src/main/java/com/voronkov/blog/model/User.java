package com.voronkov.blog.model;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

public class User implements UserDetails {

  private int id;
  private String username;
  private String password;
  private boolean active;
  private Set<Role> roles;
  private String email;
  private String activationCode;

  public int getId() {
    return id;
  }

  public String getPassword() {
    return password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles =
        CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
  }

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles();
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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
    return isActive();
  }

  public String getEmail() {
    return email;
  }

  public String getActivationCode() {
    return activationCode;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setActivationCode(String activationCode) {
    this.activationCode = activationCode;
  }
}
