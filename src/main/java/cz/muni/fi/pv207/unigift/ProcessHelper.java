package cz.muni.fi.pv207.unigift;

import org.drools.KnowledgeBase;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.jbpm.process.workitem.wsht.HornetQHTWorkItemHandler;

public class ProcessHelper {

	public static KnowledgeBase readKnowledgeBase(String processFile) throws Exception {
        KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
        kbuilder.add(ResourceFactory.newClassPathResource(processFile), ResourceType.BPMN2);
        return kbuilder.newKnowledgeBase();
    }
	
	public static StatefulKnowledgeSession createKnowledgeSession(KnowledgeBase kbase) {
		StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
		HornetQHTWorkItemHandler humanTaskHandler = new HornetQHTWorkItemHandler(ksession);
		humanTaskHandler.setIpAddress("127.0.0.1");
		humanTaskHandler.setPort(5153);
		humanTaskHandler.setOwningSessionOnly(true);
				
		ksession.getWorkItemManager().registerWorkItemHandler("Human Task", humanTaskHandler);
		
		ksession.getWorkItemManager().registerWorkItemHandler("Send Task", new SendTaskWorkItemHandler());
		
		ksession.getWorkItemManager().registerWorkItemHandler("Service Task", new ServiceTaskWorkItemHandler());
		
		ksession.getWorkItemManager().registerWorkItemHandler("Manual Task", new ManualTaskWorkItemHandler());
		return ksession;
	}

}
