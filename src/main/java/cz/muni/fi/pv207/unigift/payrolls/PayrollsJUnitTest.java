package cz.muni.fi.pv207.unigift.payrolls;

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
import cz.muni.fi.pv207.unigift.ServiceTaskWorkItemHandler;

@SuppressWarnings("deprecation")
public class PayrollsJUnitTest extends JbpmJUnitTestCase {

    public PayrollsJUnitTest() {
        super(true);
    }
// TODO: test event
    @Test
    public void testConstraint1() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-payrolls.bpmn2");
        ksession.getWorkItemManager().registerWorkItemHandler("Email", new EmailWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
                new ServiceTaskWorkItemHandler());
        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));

        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // params.put("payroll", value); // type
        // cz.muni.fi.pv207.unigift.payrolls.Payroll
        params.put("employee", new Employee("John Dummy", 1, true)); // type
                                                                     // cz.muni.fi.pv207.unigift.payrolls.Employee
        // params.put("pay", value); // type Integer
        // params.put("hourPay", value); // type Integer
        // params.put("hours", value); // type Integer
        params.put("id", 1); // type Integer
        params.put("name", "John Dummy"); // type String
        params.put("payInCash", true); // type Boolean
        // params.put("employeeExist", value); // type Boolean
        ProcessInstance processInstance = ksession.startProcess("unigift-payrolls", params);
        // execute task
        String actorId = "krisv";
        List<String> groups = new ArrayList<String>();
        groups.add("finances");

        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary createPayrollTask = list.get(0);
        taskService.claim(createPayrollTask.getId(), actorId, groups);
        taskService.start(createPayrollTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        results.put("employeeExist", true); // type Boolean
        taskService.completeWithResults(createPayrollTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Gateway:
        // return employeeExist == true;
        // execute task

        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary assignEmployeeTask = list.get(0);
        taskService.claim(assignEmployeeTask.getId(), actorId, groups);
        taskService.start(assignEmployeeTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        taskService.completeWithResults(assignEmployeeTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary addWorkingHoursTask = list.get(0);
        taskService.claim(addWorkingHoursTask.getId(), actorId, groups);
        taskService.start(addWorkingHoursTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("hours", 88); // type Integer
        results.put("hourPay", 3); // type Integer
        taskService.completeWithResults(addWorkingHoursTask.getId(), actorId, results);

        // execute task

        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary cfoValidationTask = list.get(0);
        taskService.claim(cfoValidationTask.getId(), actorId, groups);
        taskService.start(cfoValidationTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        taskService.completeWithResults(cfoValidationTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary generatePayrollTask = list.get(0);
        taskService.claim(generatePayrollTask.getId(), actorId, groups);
        taskService.start(generatePayrollTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        // results.put("payroll", value); // type
        // cz.muni.fi.pv207.unigift.payrolls.Payroll
        taskService.completeWithResults(generatePayrollTask.getId(), actorId, results);

        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();
    }

    @Test
    public void testConstraint2() {
        StatefulKnowledgeSession ksession = createKnowledgeSession("unigift-payrolls.bpmn2");
        ksession.getWorkItemManager().registerWorkItemHandler("Email",
                new EmailWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Manual Task",
                new ManualTaskWorkItemHandler());
        ksession.getWorkItemManager().registerWorkItemHandler("Service Task",
                new ServiceTaskWorkItemHandler());
        TaskService taskService = getTaskService(ksession);

        UserGroupCallbackManager.getInstance().setCallback(
                new DefaultUserGroupCallbackImpl(
                        "file:src/main/resources/META-INF/usergroups.properties"));

        Map<String, Object> params = new HashMap<String, Object>();
        // initialize variables here if necessary
        // params.put("payroll", value); // type
        // cz.muni.fi.pv207.unigift.payrolls.Payroll
        // params.put("employee", value); // type
        // cz.muni.fi.pv207.unigift.payrolls.Employee
        // params.put("pay", value); // type Integer
        // params.put("hourPay", value); // type Integer
        // params.put("hours", value); // type Integer
        // params.put("id", value); // type Integer
        // params.put("name", value); // type String
        // params.put("payInCash", value); // type Boolean
        // params.put("employeeExist", value); // type Boolean
        ProcessInstance processInstance = ksession.startProcess("unigift-payrolls", params);
        // execute task
        String actorId = "krisv";
        List<String> groups = new ArrayList<String>();
        groups.add("finances");

        List<TaskSummary> list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups,
                "en-UK");
        TaskSummary createPayrollTask = list.get(0);
        taskService.claim(createPayrollTask.getId(), actorId, groups);
        taskService.start(createPayrollTask.getId(), actorId);
        Map<String, Object> results = new HashMap<String, Object>();
        // add results here
        results.put("employeeExist", false); // type Boolean
        taskService.completeWithResults(createPayrollTask.getId(), actorId, results);

        // please make sure that the following constraint is selected to node
        // Collect personal data of employee:
        // return employeeExist == false;
        // execute task
        actorId = "john";
        List<String> groups2 = new ArrayList<String>();
        groups2.add("employee");
        
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups2, "en-UK");
        TaskSummary collectDataTask = list.get(0);
        taskService.claim(collectDataTask.getId(), actorId, groups2);
        taskService.start(collectDataTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("id", 0); // type Integer
        results.put("payInCash", false); // type Boolean
        results.put("name", "John Dummy"); // type String
        taskService.completeWithResults(collectDataTask.getId(), actorId, results);

        // if necessary, complete request for service task "Service Task"
        // execute task
        actorId = "krisv"; // switch back to krisv from finances
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary assignEmployeeTask = list.get(0);
        taskService.claim(assignEmployeeTask.getId(), actorId, groups);
        taskService.start(assignEmployeeTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        taskService.completeWithResults(assignEmployeeTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary addWorkingHoursTask = list.get(0);
        taskService.claim(addWorkingHoursTask.getId(), actorId, groups);
        taskService.start(addWorkingHoursTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        results.put("hours", 88); // type Integer
        results.put("hourPay", 3); // type Integer
        taskService.completeWithResults(addWorkingHoursTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary cfoValidationTask = list.get(0);
        taskService.claim(cfoValidationTask.getId(), actorId, groups);
        taskService.start(cfoValidationTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        taskService.completeWithResults(cfoValidationTask.getId(), actorId, results);

        // execute task
        list = taskService.getTasksAssignedAsPotentialOwner(actorId, groups, "en-UK");
        TaskSummary generatePayrollTask = list.get(0);
        taskService.claim(generatePayrollTask.getId(), actorId, groups);
        taskService.start(generatePayrollTask.getId(), actorId);
        results = new HashMap<String, Object>();
        // add results here
        // results.put("payroll", value); // type
        // cz.muni.fi.pv207.unigift.payrolls.Payroll
        taskService.completeWithResults(generatePayrollTask.getId(), actorId, results);

        // do your checks here
        assertProcessInstanceCompleted(processInstance.getId(), ksession);
        ksession.dispose();
    }

}