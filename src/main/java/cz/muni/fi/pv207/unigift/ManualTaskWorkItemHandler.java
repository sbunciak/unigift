package cz.muni.fi.pv207.unigift;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;
import org.jbpm.process.instance.impl.demo.SystemOutWorkItemHandler;

public class ManualTaskWorkItemHandler extends SystemOutWorkItemHandler {

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.err.println("Manual Task aborted: " + workItem);
		super.abortWorkItem(workItem, manager);
	}

	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		/*
	    System.out.println("====================================");
		System.out.println("Executing Manual task: " + workItem);
		System.out.println("====================================");
		*/
		super.executeWorkItem(workItem, manager);
	}

}
