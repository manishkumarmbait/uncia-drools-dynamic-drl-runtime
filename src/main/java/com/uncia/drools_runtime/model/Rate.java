package com.uncia.drools_runtime.model;

import java.util.ArrayList;
import java.util.List;

public class Rate {

//	private String loanStatus;
//	private double loanRate;
	private List<Deviation> deviations = new ArrayList<>();;

	public void addDeviation(String workFlowLevel, String deviationDesc, String entityName, String entityValue,
			String roles) {

		Deviation deviation1 = new Deviation();
		deviation1.setWorkFlowLevel(workFlowLevel);
		deviation1.setDeviationDesc(deviationDesc);
		deviation1.setEntityName(entityName);
		deviation1.setEntityValue(entityValue);
		deviation1.setRoles(roles);
		deviations.add(deviation1);
	}

//	public String getLoanStatus() {
//		return loanStatus;
//	}
//
//	public void setLoanStatus(String loanStatus) {
//		this.loanStatus = loanStatus;
//	}
//
//	public double getLoanRate() {
//		return loanRate;
//	}
//
//	public void setLoanRate(double loanRate) {
//		this.loanRate = loanRate;
//	}

	public List<Deviation> getDeviations() {
		return deviations;
	}

	public void setDeviations(List<Deviation> deviations) {
		this.deviations = deviations;
	}

}
