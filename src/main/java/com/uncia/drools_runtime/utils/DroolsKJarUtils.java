package com.uncia.drools_runtime.utils;
//
//import org.kie.api.KieServices;
//import org.kie.api.builder.KieFileSystem;
//import org.kie.api.builder.KieRepository;
//import org.kie.api.builder.ReleaseId;
//import org.kie.api.runtime.KieContainer;
//import org.kie.api.runtime.KieSession;
//import org.springframework.beans.factory.annotation.Autowired;
//

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.builder.ReleaseId;
import org.kie.api.io.Resource;
import org.kie.internal.io.ResourceFactory;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;

public class DroolsKJarUtils {
//
////	knowledgeBaseDetails(ksession.getKieBase())
////	@Autowired
////	KieSession kieSession;
//	
////	public static KieContainer knowledgeBaseDetails(kieSession.getKieBase()) {
////		
////		kieSession.getKieBase();
////        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
////
////        // Load rules dynamically from the list
////        for (DroolsRule rule : rules) {
////            kieFileSystem.write("src/main/resources/" + rule.getRuleName() + ".drl", rule.getRuleContent());
////        }
////
////        kieServices.newKieBuilder(kieFileSystem).buildAll();
////        KieRepository kieRepository = kieServices.getRepository();
////        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
////
////        return kieServices.newKieContainer(krDefaultReleaseId);
////    }
////	knowledgeBaseDetails(ksession.getKieBase())
//	
//	 public static void knowledgeBaseDetails(ksession.getKieBase()) {
//		
//		KieSession ksession;
//		ksession.getKieBase();
//	        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
//	        kieServices.newKieBuilder(kieFileSystem).buildAll();
//	        KieRepository kieRepository = kieServices.getRepository();
//	        ReleaseId krDefaultReleaseId = kieRepository.getDefaultReleaseId();
//
//	        return kieServices.newKieContainer(krDefaultReleaseId);
//	    }

	 public static void createKJar(KieServices kieServices, ReleaseId releaseId, String path, String pom, String... drls) {
	        KieFileSystem kfs = kieServices.newKieFileSystem();

	        // Write POM file
	        if (pom != null) {
	            kfs.writePomXML(pom);
	        }

	        // Write kmodule.xml
	        String kmoduleContent = createKModuleXML();
	        kfs.writeKModuleXML(kmoduleContent);

	        // Write DRL files
	        for (int i = 0; i < drls.length; i++) {
//				String drlFilePath = "C:\\Users\\E1849\\Desktop\\generateddrlruntime\\generated-rule.drl";
	            String drlPath = "src/main/resources/" + path + "/rule_" + i + ".drl";
	            kfs.write(drlPath, drls[i]);
	        }

	        // Build the KJar
	        kieServices.newKieBuilder(kfs).buildAll();
	    }

	    private static String createKModuleXML() {
	        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
	               "<kmodule xmlns=\"http://www.drools.org/xsd/kmodule\">\n" +
	               "    <kbase name=\"defaultKieBase\" packages=\"rules\">\n" +
	               "        <ksession name=\"defaultKieSession\"/>\n" +
	               "    </kbase>\n" +
	               "</kmodule>";
	    }
	}
