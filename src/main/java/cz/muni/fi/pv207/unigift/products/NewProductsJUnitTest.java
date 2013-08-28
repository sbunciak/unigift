package cz.muni.fi.pv207.unigift.products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.process.ProcessInstance;
import org.jbpm.task.TaskService;
import org.jbpm.task.identity.DefaultUserGroupCallbackImpl;
import org.jbpm.task.identity.UserGroupCallbackManager;
import org.jbpm.task.query.TaskSummary;
import org.jbpm.test.JbpmJUnitTestCase;
import org.junit.Test;

import cz.muni.fi.pv207.unigift.ManualTaskWorkItemHandler;
import cz.muni.fi.pv207.unigift.SendTaskWorkItemHandler;
import cz.muni.fi.pv207.unigift.ServiceTaskWorkItemHandler;

@SuppressWarnings("deprecation")
public class NewProductsJUnitTest extends JbpmJUnitTestCase {

    public NewProductsJUnitTest() {
        super(true);
    }

    @Test
    public void testImplicit() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-newproducts.bpmn2");
        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Send Task",
                new SendTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
                new ServiceTaskWorkItemHandler());
        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:/home/sbunciak/jbpm/jbpm-installer/auth/roles.properties"));

        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // params.put("product", value); // type
        // cz.muni.fi.pv207.unigift.products.Product
        // params.put("favouriteProducts", value); // type String
        // params.put("needs", value); // type String
        // params.put("summary", value); // type String
        // params.put("name", value); // type String
        // params.put("description", value); // type String
        ProcessInstance processInstance = ksession.startProcess("unigift-newproducts", params);
        assertProcessInstanceActive(processInstance.getId(), ksession);
        // if necessary, complete request for service task "Manual Task"
        // execute task
        String actorId = "sales-rep"; // user "sales-rep" has role "sales"
        List<String> groups = new ArrayList<String>();
        groups.add("sales");
        
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary identifyFavouriteProductsTask = list.get(0);
        taskService.claim(identifyFavouriteProductsTask.getId(), actorId, groups);
        taskService.start(identifyFavouriteProductsTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        results.put("favouriteProducts", "TEXT CONTAINING SUMMARY OF FAVOURITE PRODUCTS"); // type
                                                                                           // String
        taskService.completeWithResults(identifyFavouriteProductsTask.getId(), actorId, results);
        // assertNodeTriggered(processInstance.getId(),
        // identifyFavouriteProductsTask.getName());

        // TODO: Trigger Error event
        // TODO: fix roles
        //
        TaskSummary identifyCustomerNeedsTask = list.get(1);
        taskService.claim(identifyCustomerNeedsTask.getId(), actorId, groups);
        taskService.start(identifyCustomerNeedsTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("favouriteProducts", "TEXT CONTAINING SUMMARY OF CUSTOMER NEEDS"); // type
                                                                                       // String
        taskService.completeWithResults(identifyCustomerNeedsTask.getId(), actorId, results);

        // execute task

        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary summarizeResearchTask = list.get(0);
        taskService.claim(summarizeResearchTask.getId(), actorId, groups);
        taskService.start(summarizeResearchTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("summary", "TEXT CONTAINING RESEARCH SUMMARY"); // type
                                                                    // String
        taskService.completeWithResults(summarizeResearchTask.getId(), actorId, results);

        // if necessary, complete request for service task "Manual Task"
        // execute task

        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary designNewProductTask = list.get(0);
        taskService.claim(designNewProductTask.getId(), actorId, groups);
        taskService.start(designNewProductTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("description", "DESCRIPTION FOR NEW PRODUCT"); // type
                                                                   // String
        results.put("name", "NAME OF NEW PRODUCT"); // type String
        taskService.completeWithResults(designNewProductTask.getId(), actorId, results);

        // if necessary, complete request for service task "Send Task"
        // if necessary, complete request for service task "Manual Task"
        // if necessary, complete request for service task "Service Task"
        // execute task

        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary addProductTask = list.get(0);
        taskService.claim(addProductTask.getId(), actorId, groups);
        taskService.start(addProductTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        taskService.completeWithResults(addProductTask.getId(), actorId, results);

        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);

        ksession.dispose();
    }

}