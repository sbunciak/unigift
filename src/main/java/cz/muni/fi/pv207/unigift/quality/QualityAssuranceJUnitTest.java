package cz.muni.fi.pv207.unigift.quality;

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

@SuppressWarnings("deprecation")
public class QualityAssuranceJUnitTest extends JbpmJUnitTestCase {

    public QualityAssuranceJUnitTest() {
        super(true);
    }

    @Test
    public void test() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-quality.bpmn2");

        ksession.getWorkItemManager().registerWorkItemHandler("Email", new EmailWorkItemHandler());

        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());

        ksession.getWorkItemManager().registerWorkItemHandler("Send Task",
                new SendTaskWorkItemHandler());

        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));

        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // initialize variables here if necessary
        // params.put("qualityOk", value); // type Boolean
        // params.put("willImprove", value); // type Boolean
        // params.put("qualityMessage", value); // type String
        params.put("customerEmail", "pipistik.bunciak@gmail.com"); // type String
        // params.put("complaint", value); // type cz.muni.fi.pv207.unigift.complaints.Complaint
        // params.put("issueType", value); // type String
        ProcessInstance processInstance = ksession.startProcess("unigift-quality", params);
        // execute task
        String actorId = "sales-rep";
        List<String> groups = new ArrayList<String>();
        groups.add("sales");
        
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary analyzeComplaintTask = list.get(0);
        taskService.claim(analyzeComplaintTask.getId(), actorId, groups);
        taskService.start(analyzeComplaintTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        results.put("issueType", "delivery"); // type String
        taskService.completeWithResults(analyzeComplaintTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node Ask for more reliable delivery:
        // return issueType == "delivery";
        
        // if necessary, complete request for service task "Email"
        // please make sure that the following constraint is selected to node Upgrade the contract:
        // return willImprove == true;
        // execute task
        
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary upgradeContractTask = list.get(0);
        taskService.claim(upgradeContractTask.getId(), actorId, groups);
        taskService.start(upgradeContractTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        taskService.completeWithResults(upgradeContractTask.getId(), actorId, results);

        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();
    }
    
    @Test
    public void test2() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-quality.bpmn2");

        ksession.getWorkItemManager().registerWorkItemHandler("Email", new EmailWorkItemHandler());

        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());

        ksession.getWorkItemManager().registerWorkItemHandler("Send Task",
                new SendTaskWorkItemHandler());

        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));

        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // initialize variables here if necessary
        // params.put("qualityOk", value); // type Boolean
        // params.put("willImprove", value); // type Boolean
        // params.put("qualityMessage", value); // type String
        params.put("customerEmail", "pipistik.bunciak@gmail.com"); // type String
        // params.put("complaint", value); // type cz.muni.fi.pv207.unigift.complaints.Complaint
        // params.put("issueType", value); // type String
        ProcessInstance processInstance = ksession.startProcess("unigift-quality", params);
        // execute task
        String actorId = "sales-rep";
        List<String> groups = new ArrayList<String>();
        groups.add("sales");
        
        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary analyzeComplaintTask = list.get(0);
        taskService.claim(analyzeComplaintTask.getId(), actorId, groups);
        taskService.start(analyzeComplaintTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        results.put("issueType", "product"); // type String
        taskService.completeWithResults(analyzeComplaintTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node Check quality of products:
        // return issueType == "product";
        // execute task
        
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary checkQualityTask = list.get(0);
        taskService.claim(checkQualityTask.getId(), actorId, groups);
        taskService.start(checkQualityTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("qualityOk", false); // type Boolean
        results.put("qualityMessage", "Products do not meet our quality requirements."); // type
        taskService.completeWithResults(checkQualityTask.getId(), actorId, results);

        assertProcessInstanceActive(processInstance.getId(), ksession);
        
        // please make sure that the following constraint is selected to node Ask for better quality:
        // return qualityOk == false;
        // if necessary, complete request for service task "Email"
        // please make sure that the following constraint is selected to node Upgrade the contract:
        // return willImprove == true;
        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary upgradeContractTask = list.get(0);
        taskService.claim(upgradeContractTask.getId(), actorId, groups);
        taskService.start(upgradeContractTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        taskService.completeWithResults(upgradeContractTask.getId(), actorId, results);

        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();        
    }

}