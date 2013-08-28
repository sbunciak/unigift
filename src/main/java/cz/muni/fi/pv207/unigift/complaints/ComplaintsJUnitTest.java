package cz.muni.fi.pv207.unigift.complaints;

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

import cz.muni.fi.pv207.unigift.EmailWorkItemHandler;
import cz.muni.fi.pv207.unigift.ManualTaskWorkItemHandler;
import cz.muni.fi.pv207.unigift.SendTaskWorkItemHandler;
import cz.muni.fi.pv207.unigift.ServiceTaskWorkItemHandler;
import cz.muni.fi.pv207.unigift.products.Product;

@SuppressWarnings("deprecation")
public class ComplaintsJUnitTest extends JbpmJUnitTestCase {

    private Product product = new Product("Jacket", "Jacket with MU logo");
    private String description = "Here I'm describing a complaint.";
    private String contact = "pipistik.bunciak@gmail.com";

    public ComplaintsJUnitTest() {
        super(true);
    }

    @Test
    public void testConstraint1Constraint1() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-complaints.bpmn2");

        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new EmailWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
                new ServiceTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Send Task",
                new SendTaskWorkItemHandler());

        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));

        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // params.put("enoughInfo", value); // type Boolean
        params.put("product", product); // type
        // cz.muni.fi.pv207.unigift.products.Product
        // params.put("inWarranty", value); // type Boolean
        params.put("description", description); // type String
        params.put("contact", contact); // type String
        // params.put("onStock", value); // type Boolean
        ProcessInstance processInstance = ksession.startProcess("unigift-complaints", params);
        // execute task
        String actorId = "sales-rep";
        List<String> groups = new ArrayList<String>();
        groups.add("sales");

        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary obtainComplaintTask = list.get(0);
        taskService.claim(obtainComplaintTask.getId(), actorId, groups);
        taskService.start(obtainComplaintTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        results.put("complaint", new Complaint(contact, description, product));
        taskService.completeWithResults(obtainComplaintTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary checkInfoTask = list.get(0);
        taskService.claim(checkInfoTask.getId(), actorId, groups);
        taskService.start(checkInfoTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("enoughInfo", true); // type Boolean
        taskService.completeWithResults(checkInfoTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Gateway:
        // return enoughInfo == true;
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary checkWarrantyTask = list.get(0);
        taskService.claim(checkWarrantyTask.getId(), actorId, groups);
        taskService.start(checkWarrantyTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("inWarranty", true); // type Boolean
        taskService.completeWithResults(checkWarrantyTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Offer customer new product:
        // return inWarranty == true;
        // if necessary, complete request for service task "Email"
        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();
    }

    @Test
    public void testConstraint1Constraint2Constraint1() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-complaints.bpmn2");
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new EmailWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
                new ServiceTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Send Task",
                new SendTaskWorkItemHandler());

        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));
                
        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // params.put("enoughInfo", value); // type Boolean
        params.put("product", product); // type
        // cz.muni.fi.pv207.unigift.products.Product
        // params.put("inWarranty", value); // type Boolean
        params.put("description", description); // type String
        params.put("contact", contact); // type String
        // params.put("onStock", value); // type Boolean
        ProcessInstance processInstance = ksession.startProcess("unigift-complaints", params);
        // execute task
        String actorId = "sales-rep";
        List<String> groups = new ArrayList<String>();
        groups.add("sales");
        
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary obtainComplaintTask = list.get(0);
        taskService.claim(obtainComplaintTask.getId(), actorId, groups);
        taskService.start(obtainComplaintTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        results.put("complaint", new Complaint(contact, description, product));
        taskService.completeWithResults(obtainComplaintTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkInfoTask = list.get(0);
        taskService.claim(checkInfoTask.getId(), actorId, groups);
        taskService.start(checkInfoTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("enoughInfo", true); // type Boolean
        taskService.completeWithResults(checkInfoTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Gateway:
        // return enoughInfo == true;
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkWarrantyTask = list.get(0);
        taskService.claim(checkWarrantyTask.getId(), actorId, groups);
        taskService.start(checkWarrantyTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("inWarranty", false); // type Boolean
        taskService.completeWithResults(checkWarrantyTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Request physical evidence:
        // return inWarranty == false;
        // if necessary, complete request for service task "Email"
        // if necessary, complete request for service task "Manual Task"
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkStockTask = list.get(0);
        taskService.claim(checkStockTask.getId(), actorId, groups);
        taskService.start(checkStockTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("onStock", false); // type Boolean
        taskService.completeWithResults(checkStockTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Order product from supplier:
        //
        // if necessary, complete request for service task "Send Task"
        // if necessary, complete request for service task "Manual Task"
        // if necessary, complete request for service task "Send Task"
        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();
    }
    
    @Test
    public void testConstraint1Constraint2Constraint2() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-complaints.bpmn2");
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new EmailWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
                new ServiceTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Send Task",
                new SendTaskWorkItemHandler());

        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));

        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // params.put("enoughInfo", value); // type Boolean
        params.put("product", product); // type
        // cz.muni.fi.pv207.unigift.products.Product
        // params.put("inWarranty", value); // type Boolean
        params.put("description", description); // type String
        params.put("contact", contact); // type String
        // params.put("onStock", value); // type Boolean
        ProcessInstance processInstance = ksession.startProcess("unigift-complaints", params);
        // execute task
        String actorId = "sales-rep";
        List<String> groups = new ArrayList<String>();
        groups.add("sales");
        
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary obtainComplaintTask = list.get(0);
        taskService.claim(obtainComplaintTask.getId(), actorId, groups);
        taskService.start(obtainComplaintTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        results.put("complaint", new Complaint(contact, description, product));
        taskService.completeWithResults(obtainComplaintTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkInfoTask = list.get(0);
        taskService.claim(checkInfoTask.getId(), actorId, groups);
        taskService.start(checkInfoTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("enoughInfo", true); // type Boolean
        taskService.completeWithResults(checkInfoTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Gateway:
        // return enoughInfo == true;
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkWarrantyTask = list.get(0);
        taskService.claim(checkWarrantyTask.getId(), actorId, groups);
        taskService.start(checkWarrantyTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("inWarranty", false); // type Boolean
        taskService.completeWithResults(checkWarrantyTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Request physical evidence:
        // return inWarranty == false;
        // if necessary, complete request for service task "Email"
        // if necessary, complete request for service task "Manual Task"
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkStockTask = list.get(0);
        taskService.claim(checkStockTask.getId(), actorId, groups);
        taskService.start(checkStockTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("onStock", true); // type Boolean
        taskService.completeWithResults(checkStockTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Gateway:
        //
        // if necessary, complete request for service task "Send Task"
        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();
    }

    @Test
    public void testConstraint2Constraint2Constraint1() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-complaints.bpmn2");
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new EmailWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
                new ServiceTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Send Task",
                new SendTaskWorkItemHandler());

        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));
        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // params.put("enoughInfo", value); // type Boolean
        params.put("product", product); // type
        // cz.muni.fi.pv207.unigift.products.Product
        // params.put("inWarranty", value); // type Boolean
        params.put("description", description); // type String
        params.put("contact", contact); // type String
        // params.put("onStock", value); // type Boolean
        ProcessInstance processInstance = ksession.startProcess("unigift-complaints", params);
        // execute task
        String actorId = "sales-rep";
        List<String> groups = new ArrayList<String>();
        groups.add("sales");
        
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary obtainComplaintTask = list.get(0);
        taskService.claim(obtainComplaintTask.getId(), actorId, groups);
        taskService.start(obtainComplaintTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        Complaint c = new Complaint(contact, description, product);
        results.put("complaint", c);
        taskService.completeWithResults(obtainComplaintTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkInfoTask = list.get(0);
        taskService.claim(checkInfoTask.getId(), actorId, groups);
        taskService.start(checkInfoTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("enoughInfo", false); // type Boolean
        taskService.completeWithResults(checkInfoTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Collect all needed information:
        // return enoughInfo == false;
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary collectInfoTask = list.get(0);
        taskService.claim(collectInfoTask.getId(), actorId, groups);
        taskService.start(collectInfoTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        c.setDescription(c.getDescription() + "MODIFIED");
        results.put("complaint", c);
        taskService.completeWithResults(collectInfoTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkWarrantyTask = list.get(0);
        taskService.claim(checkWarrantyTask.getId(), actorId, groups);
        taskService.start(checkWarrantyTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("inWarranty", false); // type Boolean
        taskService.completeWithResults(checkWarrantyTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Request physical evidence:
        // return inWarranty == false;
        // if necessary, complete request for service task "Email"
        // if necessary, complete request for service task "Manual Task"
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkStockTask = list.get(0);
        taskService.claim(checkStockTask.getId(), actorId, groups);
        taskService.start(checkStockTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("onStock", false); // type Boolean
        taskService.completeWithResults(checkStockTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Order product from supplier:
        //
        // if necessary, complete request for service task "Send Task"
        // if necessary, complete request for service task "Manual Task"
        // if necessary, complete request for service task "Send Task"
        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();
    }
    
    @Test
    public void testConstraint2Constraint2Constraint2() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-complaints.bpmn2");
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new EmailWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
                new ServiceTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Send Task",
                new SendTaskWorkItemHandler());

        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));
        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // params.put("enoughInfo", value); // type Boolean
        params.put("product", product); // type
        // cz.muni.fi.pv207.unigift.products.Product
        // params.put("inWarranty", value); // type Boolean
        params.put("description", description); // type String
        params.put("contact", contact); // type String
        // params.put("onStock", value); // type Boolean
        ProcessInstance processInstance = ksession.startProcess("unigift-complaints", params);
        // execute task
        String actorId = "sales-rep";
        List<String> groups = new ArrayList<String>();
        groups.add("sales");
        
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary obtainComplaintTask = list.get(0);
        taskService.claim(obtainComplaintTask.getId(), actorId, groups);
        taskService.start(obtainComplaintTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        Complaint c = new Complaint(contact, description, product);
        results.put("complaint", c);
        taskService.completeWithResults(obtainComplaintTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkInfoTask = list.get(0);
        taskService.claim(checkInfoTask.getId(), actorId, groups);
        taskService.start(checkInfoTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("enoughInfo", false); // type Boolean
        taskService.completeWithResults(checkInfoTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Collect all needed information:
        // return enoughInfo == false;
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary collectInfoTask = list.get(0);
        taskService.claim(collectInfoTask.getId(), actorId, groups);
        taskService.start(collectInfoTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("complaint", c);
        taskService.completeWithResults(collectInfoTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkWarrantyTask = list.get(0);
        taskService.claim(checkWarrantyTask.getId(), actorId, groups);
        taskService.start(checkWarrantyTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("inWarranty", false); // type Boolean
        taskService.completeWithResults(checkWarrantyTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Request physical evidence:
        // return inWarranty == false;
        // if necessary, complete request for service task "Email"
        // if necessary, complete request for service task "Manual Task"
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkStockTask = list.get(0);
        taskService.claim(checkStockTask.getId(), actorId, groups);
        taskService.start(checkStockTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("onStock", true); // type Boolean
        taskService.completeWithResults(checkStockTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Gateway:
        //
        // if necessary, complete request for service task "Send Task"
        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();
    }

    @Test
    public void testConstraint2Constraint1() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-complaints.bpmn2");
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new EmailWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
                new ServiceTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Send Task",
                new SendTaskWorkItemHandler());

        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));
        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // params.put("enoughInfo", value); // type Boolean
        params.put("product", product); // type
        // cz.muni.fi.pv207.unigift.products.Product
        // params.put("inWarranty", value); // type Boolean
        params.put("description", description); // type String
        params.put("contact", contact); // type String
        // params.put("onStock", value); // type Boolean
        ProcessInstance processInstance = ksession.startProcess("unigift-complaints", params);
        // execute task
        String actorId = "sales-rep";
        List<String> groups = new ArrayList<String>();
        groups.add("sales");
        
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary obtainComplaintTask = list.get(0);
        taskService.claim(obtainComplaintTask.getId(), actorId, groups);
        taskService.start(obtainComplaintTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        Complaint c = new Complaint(contact, description, product);
        results.put("complaint", c);
        taskService.completeWithResults(obtainComplaintTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkInfoTask = list.get(0);
        taskService.claim(checkInfoTask.getId(), actorId, groups);
        taskService.start(checkInfoTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("enoughInfo", false); // type Boolean
        taskService.completeWithResults(checkInfoTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Collect all needed information:
        // return enoughInfo == false;
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary collectInfoTask = list.get(0);
        taskService.claim(collectInfoTask.getId(), actorId, groups);
        taskService.start(collectInfoTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        // modify complaint
        c.setDescription(c.getDescription() + " MODIFIED");
        results.put("complaint", c);
        taskService.completeWithResults(collectInfoTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary checkWarrantyTask = list.get(0);
        taskService.claim(checkWarrantyTask.getId(), actorId, groups);
        taskService.start(checkWarrantyTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("inWarranty", true); // type Boolean
        taskService.completeWithResults(checkWarrantyTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Offer customer new product:
        // return inWarranty == true;
        // if necessary, complete request for service task "Email"
        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();
    }

    // TODO: test event

}