package cz.muni.fi.pv207.unigift;

import org.drools.process.instance.WorkItemHandler;
import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemManager;

public class RestTaskWorkItemHandler implements WorkItemHandler {

    @Override
    public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
        manager.abortWorkItem(workItem.getId());
    }

    @Override
    public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
        System.out.println("Executing REST service");
        
        // TODO: Here can go custom implementation of REST calls, e.g. leveraging apache http client or resteasy
        
        manager.completeWorkItem(workItem.getId(), workItem.getResults());
    }

}
