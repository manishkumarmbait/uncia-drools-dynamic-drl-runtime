package com.uncia.drools_runtime.config;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.Message;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.uncia.drools_runtime.model.Rule;
import com.uncia.drools_runtime.repository.RuleRepository;
import com.uncia.drools_runtime.utils.DroolsKJarUtils;

@Configuration
public class RuleConfig {

//	@Autowired
//	private RuleRepository ruleRepository;
//	
//	@Autowired
//	KieServices kieServices;
//	
//	@Autowired
//	RuleEngineService ruleEngineService;
//	
//	String drlFilePath = "C:\\Users\\E1849\\Desktop\\generateddrl\\generated-rule.drl";
//
//	@Bean
//	public KieServices kieServices() {
//		KieServices kieServices = KieServices.Factory.get();
//		KieFileSystem kfs = kieServices.newKieFileSystem();
//		kfs.write(ResourceFactory.newFileResource(new File(drlFilePath)));
//
//		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
//
//		if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
//			throw new RuntimeException("Rule compilation errors: " + kieBuilder.getResults());
//		}
//		return kieServices;
//	}
//	
//	@Bean
//	public KieContainer kieContainer() {
//		List<Rule> rules = ruleRepository.findAll();
//
//		String drlContent = generateDrlContent(rules);
//
//		try (PrintWriter writer = new PrintWriter(drlFilePath)) {
//			writer.write(drlContent);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		ReleaseId released = kieServices.newReleaseId("org.default", "artifact", "1.0.0");
//		KieContainer kieContainer = kieServices.newKieContainer(released);
//
//		return kieContainer;
//
//	}
//	
//	public String generateDrlContent(List<Rule> rules) {
//		StringBuilder drlContent = new StringBuilder();
//		drlContent
//				.append("package kierule;\n")
//				.append("import com.uncia.dynamic_drl.model.Participant;\n")
//				.append("import com.uncia.dynamic_drl.model.Deviation;\n")
//				.append("global com.uncia.dynamic_drl.model.Rate rate;\n\n");
//		for (Rule rule : rules) {
//
//			String[] rules1 = rule.getAction();
//			String ruleAction = rules1[0] + "\"," + '\"' + rules1[1] + "\"," + '\"' + rules1[2] + "\"," + rules1[3]+"," + '\"' + rules1[4];
//			System.out.println(ruleAction);
//			drlContent.append("rule \"").append(rule.getRuleName() + "\"\n\n").append("when\n").append("Participant(")
//					.append(rule.getConditions()).append(")\n").append("then\n").append("rate.addDeviation(\"")
//					.append(ruleAction).append("\");\n").append("\nend;\n\n");
//		}
//		return drlContent.toString();
//	}
	
//	=======================================================================================================
	
	@Autowired
	private RuleRepository ruleRepository;
	
	/*
	 * @Autowired KieServices kieServices;
	 * 
	 * @Autowired RuleEngineService ruleEngineService;
	 */
	
	String drlFilePath = "C:\\Users\\E1849\\Desktop\\generateddrl\\generated-rule.drl";

//	@Bean
//	public KieServices kieServices() {
//		KieServices kieServices = KieServices.Factory.get();
//		KieFileSystem kfs = kieServices.newKieFileSystem();
//		kfs.write(ResourceFactory.newFileResource(new File(drlFilePath)));
//
//		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();
//
//		if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
//			throw new RuntimeException("Rule compilation errors: " + kieBuilder.getResults());
//		}
//		return kieServices;
//	}
//	
//	@Bean
//	public KieContainer kieContainer() {
//		List<Rule> rules = ruleRepository.findAll();
//
//		String drlContent = generateDrlContent(rules);
//
//		try (PrintWriter writer = new PrintWriter(drlFilePath)) {
//			writer.write(drlContent);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return null;
//		}
//
//		ReleaseId released = kieServices.newReleaseId("org.default", "artifact", "1.0.0");
//		KieContainer kieContainer = kieServices.newKieContainer(released);
//
//		return kieContainer;
//
//	}
	
    @Bean
    public KieServices kieServices11() {
		List<Rule> rules = ruleRepository.findAll();

		String drlContent = generateDrlContent(rules);
		String drlFilePath = "C:\\Users\\E1849\\Desktop\\generateddrlruntime\\generated-rule.drl";
		
		try (PrintWriter writer = new PrintWriter(drlFilePath)) {
			writer.write(drlContent);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		KieServices kieServices = KieServices.Factory.get();
		KieFileSystem kfs = kieServices.newKieFileSystem();
		kfs.write(ResourceFactory.newFileResource(new File(drlFilePath)));

		KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();

		if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
			throw new RuntimeException("Rule compilation errors: " + kieBuilder.getResults());
		}
		
		return kieServices;
    }

    @Bean
    public ReleaseId releaseId(KieServices kieServices) {
        return kieServices.newReleaseId("org.default", "artifact", "1.0.0");
    }

//    @Bean
//    public KieContainer kieContainer(KieServices kieServices, ReleaseId releaseId) {
//        return kieServices.newKieContainer(releaseId);
//    }
    
    
	
	
	public String generateDrlContent(List<Rule> rules) {
		StringBuilder drlContent = new StringBuilder();
		drlContent
				.append("package kierule;\n")
				.append("import com.uncia.drools_runtime.model.Participant;\n")
				.append("import com.uncia.drools_runtime.model.Deviation;\n")
				.append("global com.uncia.drools_runtime.model.Rate rate;\n\n");
		for (Rule rule : rules) {

			String[] rules1 = rule.getAction();
			String ruleAction = rules1[0] + "\"," + '\"' + rules1[1] + "\"," + '\"' + rules1[2] + "\"," + rules1[3]+"," + '\"' + rules1[4];
			System.out.println(ruleAction);
			drlContent.append("rule \"").append(rule.getRuleName() + "\"\n\n").append("when\n").append("Participant(")
					.append(rule.getConditions()).append(")\n").append("then\n").append("rate.addDeviation(\"")
					.append(ruleAction).append("\");\n").append("\nend;\n\n");
		}
		return drlContent.toString();
	}
	
	
	//===================================================================================
	//===================================================================================
	
	
	
	   @Bean
	    public KieServices kieServices() {
			List<Rule> rules = ruleRepository.findAll();

			String drlContent = generateDrlContent(rules);
			String drlFilePath = "C:\\Users\\E1849\\Desktop\\generateddrlruntime\\generated-rule.drl";
			
			try (PrintWriter writer = new PrintWriter(drlFilePath)) {
				writer.write(drlContent);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}

			KieServices kieServices = KieServices.Factory.get();
			KieFileSystem kfs = kieServices.newKieFileSystem();
			kfs.write(ResourceFactory.newFileResource(new File(drlFilePath)));

			KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();

			if (kieBuilder.getResults().hasMessages(Message.Level.ERROR)) {
				throw new RuntimeException("Rule compilation errors: " + kieBuilder.getResults());
			}
			
			return kieServices;
	    }

	    @Bean
	    public KieFileSystem kieFileSystem(KieServices kieServices) {
	        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
	        DroolsKJarUtils kJarUtils = new DroolsKJarUtils();
//	        /uncia-drools-dynamic-drl-runtime/pom.xml
	        String pom = "pom.xml";
//	        String[] drls = {
//	            "package rules;\n rule \"Test Rule 1\" when then System.out.println(\"Rule 1 executed\"); end",
//	            "package rules;\n rule \"Test Rule 2\" when then System.out.println(\"Rule 2 executed\"); end"
//	        };

			List<Rule> rules = ruleRepository.findAll();

			String drlContent = generateDrlContent(rules);
			
	        ReleaseId releaseId = kieServices.newReleaseId("org.default", "artifact", "1.0.0");
//	   	 public static void createKJar(KieServices kieServices, ReleaseId releaseId, String path, String pom, String... drls) {
	        DroolsKJarUtils.createKJar(kieServices, releaseId, "rules", pom, drlContent);

	        return kieFileSystem;
	    }

	    @Bean
	    public KieContainer kieContainer(KieServices kieServices, KieFileSystem kieFileSystem) {
	        KieModule kieModule = kieServices.newKieBuilder(kieFileSystem).buildAll().getKieModule();
	        return kieServices.newKieContainer(kieModule.getReleaseId());
	    }
	}
