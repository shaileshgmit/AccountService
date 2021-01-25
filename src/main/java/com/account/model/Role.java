package com.account.model;

public class Role {
	private String roleName;
	private String roleCode;
	
	public Role() {
		
	}
	public Role(String roleName, String roleCode) {
		super();
		this.roleName = roleName;
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}
