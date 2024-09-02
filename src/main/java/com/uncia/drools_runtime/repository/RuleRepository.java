package com.uncia.drools_runtime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uncia.drools_runtime.model.Rule;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long>{

	Rule findByRuleId(Long ruleId);
	Rule findByRuleName(String ruleName);
}
