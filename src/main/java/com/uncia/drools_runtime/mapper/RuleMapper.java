package com.uncia.drools_runtime.mapper;

import org.springframework.beans.BeanUtils;

import com.uncia.drools_runtime.model.Rule;
import com.uncia.drools_runtime.pojo.RulePojo;

public class RuleMapper {

	public static Rule copyRulePojoToEntity(RulePojo rulePojo) {
		Rule rule = new Rule();
		BeanUtils.copyProperties(rulePojo, rule);
		return rule;
	}

	public static RulePojo copyRuleToPojo(Rule rule) {
		RulePojo rulePojo = new RulePojo();
		BeanUtils.copyProperties(rule, rulePojo);
		return rulePojo;
	}
}
