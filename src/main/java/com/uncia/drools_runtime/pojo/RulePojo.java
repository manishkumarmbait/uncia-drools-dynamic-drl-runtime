package com.uncia.drools_runtime.pojo;

public class RulePojo {

	private Long ruleId;
	private String ruleName;
	private String participantProp;
	private String conditions;
	private String[] action;

	public Long getRuleId() {
		return ruleId;
	}

	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}

	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String[] getAction() {
		return action;
	}

	public void setAction(String[] action) {
		this.action = action;
	}

	public String getParticipantProp() {
		return participantProp;
	}

	public void setParticipantProp(String participantProp) {
		this.participantProp = participantProp;
	}

}
