package com.uncia.drools_runtime.model;

public class Deviation {

	private String workFlowLevel;
	private String deviationDesc;
	private String entityName;
	private String entityValue;
	private String roles;

	public Deviation() {
	}

	public Deviation(String workFlowLevel, String deviationDesc, String entityName, String entityValue, String roles) {
		this.workFlowLevel = workFlowLevel;
		this.deviationDesc = deviationDesc;
		this.entityName = entityName;
		this.entityValue = entityValue;
		this.roles = roles;
	}

	public String getWorkFlowLevel() {
		return workFlowLevel;
	}

	public void setWorkFlowLevel(String workFlowLevel) {
		this.workFlowLevel = workFlowLevel;
	}

	public String getDeviationDesc() {
		return deviationDesc;
	}

	public void setDeviationDesc(String deviationDesc) {
		this.deviationDesc = deviationDesc;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getEntityValue() {
		return entityValue;
	}

	public void setEntityValue(String entityValue) {
		this.entityValue = entityValue;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
