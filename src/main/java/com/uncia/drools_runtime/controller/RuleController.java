package com.uncia.drools_runtime.controller;

import java.io.FileNotFoundException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uncia.drools_runtime.message.BaseResponse;
import com.uncia.drools_runtime.model.Participant;
import com.uncia.drools_runtime.model.Rate;
import com.uncia.drools_runtime.pojo.RulePojo;
import com.uncia.drools_runtime.service.RuleEngineService;

@RestController
@RequestMapping("/api")
public class RuleController {

	@Autowired
	private RuleEngineService ruleEngineService;

	@PostMapping(value = "/saveRule")
	public ResponseEntity<?> saveRule(@RequestBody RulePojo rulePojo) {
		BaseResponse response = ruleEngineService.saveRule(rulePojo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PutMapping(value = "/updateRule")
	public ResponseEntity<?> updateRule(@RequestBody RulePojo rulePojo) {
		BaseResponse response = ruleEngineService.updateRule(rulePojo);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping(value = "/getRuleList")
	public ResponseEntity<?> getNoticePeriodList() {
		List<RulePojo> ruleList = ruleEngineService.getRuleList();
		return new ResponseEntity<List<RulePojo>>(ruleList, HttpStatus.OK);
	}

	@GetMapping(value = "/getRuleById")
	public ResponseEntity<?> getRuleById(@RequestParam Long ruleId) {
		RulePojo rulePojo = ruleEngineService.getRuleById(ruleId);
		return new ResponseEntity<RulePojo>(rulePojo, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteRuleById/{ruleId}")
	public ResponseEntity<?> deleteRuleById(@PathVariable("ruleId") Long ruleId) {
		BaseResponse response = ruleEngineService.deleteRuleById(ruleId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping(value = "/deleteRuleByName")
	public ResponseEntity<?> deleteRuleByName(@RequestParam String ruleName) {
		BaseResponse response = ruleEngineService.deleteRuleByName(ruleName);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/getDeviation")
	public ResponseEntity<?> getDeviation(@RequestBody Participant request) throws FileNotFoundException {
		Rate executeRule = ruleEngineService.getDeviation(request);
		return new ResponseEntity<Rate>(executeRule, HttpStatus.OK);
	}

}
