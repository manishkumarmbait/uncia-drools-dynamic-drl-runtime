package com.uncia.drools_runtime.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import org.drools.compiler.compiler.io.memory.MemoryFileSystem;
import org.drools.compiler.kie.builder.impl.InternalKieModule;
import org.drools.compiler.kie.builder.impl.KieBuilderImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.KieRepository;
import org.kie.api.builder.Message;
import org.kie.api.builder.Message.Level;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.uncia.drools_runtime.config.RuleConfig;
import com.uncia.drools_runtime.exception.RecordNotFoundException;
import com.uncia.drools_runtime.mapper.RuleMapper;
import com.uncia.drools_runtime.message.BaseResponse;
import com.uncia.drools_runtime.message.CustomMessage;
import com.uncia.drools_runtime.model.Participant;
import com.uncia.drools_runtime.model.Rate;
import com.uncia.drools_runtime.model.Rule;
import com.uncia.drools_runtime.pojo.RulePojo;
import com.uncia.drools_runtime.repository.RuleRepository;

//==================================================

import jakarta.annotation.PostConstruct;

@Service
public class RuleEngineService {

	String drlFilePath = "C:\\Users\\E1849\\Desktop\\generateddrlruntime\\generated-rule.drl";

	@Autowired
	private RuleRepository ruleRepository;

//	@Autowired
//	private KieContainer kieContainer;

	@Autowired
	RuleConfig ruleConfig;

	@Autowired
	KieServices kieServices;
	
//	===============================
	
	private KieFileSystem kieFileSystem;
	private KieBuilder kieBuilder;
	
    private KieContainer kieContainer;
//    @Autowired
//    private KieSession kieSession;
	
//	===============================

	@PostConstruct
	public void init() {
		reLoadContainer();
	}

	public KieSession getKieSession() {
		return kieContainer.newKieSession();
	}

//	private void reLoadContainer() {
//		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//
//		List<Rule> rules = ruleRepository.findAll();
//		String drlContent = ruleConfig.generateDrlContent(rules);
//
//		try (PrintWriter writer = new PrintWriter(drlFilePath)) {
//			writer.write(drlContent);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		kieFileSystem.write(ResourceFactory.newFileResource(new File(drlFilePath)));
//
//		kieServices.newKieBuilder(kieFileSystem).buildAll();
//		KieRepository kieRepository = kieServices.getRepository();
//		ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
//
//		kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
//	}
//	==================================================================================================================
	
	private void reLoadContainer() {
		KieFileSystem kieFileSystem = kieServices.newKieFileSystem();

		List<Rule> rules = ruleRepository.findAll();
		String drlContent = ruleConfig.generateDrlContent(rules);

		try (PrintWriter writer = new PrintWriter(drlFilePath)) {
			writer.write(drlContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		kieFileSystem.write(ResourceFactory.newFileResource(new File(drlFilePath)));

		kieServices.newKieBuilder(kieFileSystem).buildAll();
		KieRepository kieRepository = kieServices.getRepository();
		ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();

		kieContainer = kieServices.newKieContainer(krDefaultReleaseId);
//		--------------------------------------------------------------
//		KieServices ks = KieServices.Factory.get();
//		// Create a new jar for version 1.1.0
//        ReleaseId releaseId2 = ks.newReleaseId( "org.kie", "test-upgrade", "1.1.0" );
//        KieModule km = createAndDeployJar( ks, releaseId2, "path", drl1, drl2_2 );
        
		
	}
	
	
//  ==================================================================================================================
	public BaseResponse saveRule(RulePojo rulePojo) {
		Rule ruleName = ruleRepository.findByRuleName(rulePojo.getRuleName());
		if (ruleName != null) {
			return new BaseResponse(CustomMessage.DUPLICATE_RULE_MESSAGE, true, HttpStatus.CREATED.value());
		}
		Rule rule = RuleMapper.copyRulePojoToEntity(rulePojo);
		ruleRepository.save(rule);
		reLoadContainer();
		return new BaseResponse(CustomMessage.SAVE_SUCCESS_MESSAGE, true, HttpStatus.CREATED.value());
	}

	public BaseResponse updateRule(RulePojo rulePojo) {
		Rule rule = RuleMapper.copyRulePojoToEntity(rulePojo);
		ruleRepository.saveAndFlush(rule);
		reLoadContainer();
		return new BaseResponse(CustomMessage.SAVE_SUCCESS_MESSAGE, true, HttpStatus.CREATED.value());
	}

	public List<RulePojo> getRuleList() {
		return ruleRepository.findAll().stream().map(RuleMapper::copyRuleToPojo).collect(Collectors.toList());
	}

	public RulePojo getRuleById(Long ruleId) {
		Rule rule = ruleRepository.findById(ruleId)
				.orElseThrow(() -> new RecordNotFoundException("Rule id '" + ruleId + "' does not exist !"));
		return RuleMapper.copyRuleToPojo(rule);
	}

	public BaseResponse deleteRuleById(Long ruleId) {
		if (ruleRepository.existsById(ruleId)) {
			ruleRepository.deleteById(ruleId);
		} else {
			return new BaseResponse("No record found for given id: " + ruleId, false, HttpStatus.OK.value());
		}
		return new BaseResponse(CustomMessage.DELETE_SUCCESS_MESSAGE, true, HttpStatus.OK.value());
	}

	
	public BaseResponse deleteRuleByName(String ruleName) {
		Rule rule = ruleRepository.findByRuleName(ruleName);
		if (rule != null) {
			ruleRepository.delete(rule);
			KieBase kieBase = kieContainer.getKieBase();
			kieBase.removeRule("kierule", ruleName);

		} else {
			return new BaseResponse("No record found for given Rule Name: " + ruleName, false, HttpStatus.OK.value());
		}
		return new BaseResponse(CustomMessage.DELETE_SUCCESS_MESSAGE, true, HttpStatus.OK.value());
	}

//	public Rate getDeviation(Participant applicantRequest) throws FileNotFoundException {
//
//		KieSession kieSession = kieContainer.newKieSession();
//
//		Rate rate = new Rate();
//		kieSession.setGlobal("rate", rate);
//		kieSession.insert(applicantRequest);
//		kieSession.fireAllRules();
//		return rate;
//
//	}
	
	
	public Rate getDeviation(Participant applicantRequest) throws FileNotFoundException {

		KieServices ks = KieServices.Factory.get();
		// Create a new jar for version 1.1.0
        ReleaseId releaseId2 = ks.newReleaseId("org.default", "artifact", "1.1.0");
        
        
        RuleConfig ruleConfig = new RuleConfig();
        String drlContent = ruleConfig.generateDrlContent(ruleRepository.findAll());
        
//        KieModule km = createAndDeployJar( ks, releaseId2, "path", drlContent);

        // try to update the container to version 1.1.0
        kieContainer.updateToVersion( releaseId2 );
        KieSession kieSession = kieContainer.newKieSession();
        
		Rate rate = new Rate();
		kieSession.setGlobal("rate", rate);
		kieSession.insert(applicantRequest);
		kieSession.fireAllRules();
		return rate;
		
	}
	
//	public KieModule createAndDeployJar(KieServices kieServices, ReleaseId releaseId, String path, String ... drls) {
////        KieServices kieServices = KieServices.Factory.get();
//        byte[] jar = createKJar(kieServices, releaseId, path, null, drls);
//        return deployJar(kieServices, jar);
//    }
//	
//	public byte[] createKJar(KieServices kieServices, ReleaseId releaseId, String path, String pom, String... drls) {
//		return createKJar(kieServices, null, releaseId, path, pom, drls);
//	}
//	
//	public byte[] createKJar(KieServices ks, KieModuleModel kproj, ReleaseId releaseId, String path, String pom, String... drls) {
//        if (kieFileSystem == null) kieFileSystem = ks.newKieFileSystem();
//        if(kproj != null) {
//        	kieFileSystem.writeKModuleXML(kproj.toXML());
//        }
//        if (pom != null) {
//            kieFileSystem.write("pom.xml", pom);
//        } else {
//            kieFileSystem.generateAndWritePomXML(releaseId);
//        }
//        for (int i = 0; i < drls.length; i++) {
//            if (drls[i] != null) {
//                kieFileSystem.write("src/main/resources/drl/test_"+ path +"_"+ i + ".drl", drls[i]);
//            }
//        }
//        
//        KieBuilder kb = ks.newKieBuilder(kieFileSystem);
////        kb.setDependencies(baseKieModule);
//        kb.buildAll();
//
//        if (kb.getResults().hasMessages(Level.ERROR)) {
//			List<Message> errors = kb.getResults().getMessages(Level.ERROR);
//			StringBuilder sb = new StringBuilder("Errors:");
//			for (Message msg : errors) {
////				sb.append("\n  " + prettyBuildMessage(msg));
//			}
//			//throw new KieBuildException(sb.toString());
//			throw new RuntimeException(sb.toString());
//		}
//
//        InternalKieModule kieModule = (InternalKieModule) ks.getRepository().getKieModule(releaseId);
//        byte[] jar = kieModule.getBytes();
//       
//        MemoryFileSystem mfs = MemoryFileSystem.readFromJar( jar );
//         org.drools.compiler.compiler.io.File file = mfs.getFile( KieBuilderImpl.getCompilationCachePath( releaseId, "SimpleKModelBase") );
//        System.out.println("Fiel: " + file.getPath());
//        
//        return jar;
//    }
//	
//	   public static KieModule deployJar(KieServices ks, byte[] jar) {
//	        // Deploy jar into the repository - in-memory, not to maven repo
//	        Resource jarRes = ks.getResources().newByteArrayResource(jar);
//	        KieModule km = ks.getRepository().addKieModule(jarRes);
//	        return km;
//	    }

	
	
}
