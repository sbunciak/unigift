package cz.muni.fi.pv207.unigift;

import java.lang.reflect.Method;

import org.drools.runtime.process.WorkItem;
import org.drools.runtime.process.WorkItemHandler;
import org.drools.runtime.process.WorkItemManager;

public class ServiceTaskWorkItemHandler implements WorkItemHandler {

	@Override
	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.err.println("Send Task aborted: " + workItem);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		System.out.println("Executing Service task: " + workItem);

		String className = (String) workItem.getParameter("Interface");
		String methodName = (String) workItem.getParameter("Operation");
		Object methodParam = workItem.getParameter("Parameter");
		String methodParamType = (String) workItem
				.getParameter("ParameterType");

		try {
			// load the AppTest at runtime
			Class cls = Class.forName(className);
			Class paramType = Class.forName(methodParamType);
			Object obj = cls.newInstance();

			// call the printIt method
			Method method = cls.getDeclaredMethod(methodName, paramType);
			method.invoke(obj, methodParam);
		} catch (Exception e) {
			e.printStackTrace();
		}

		manager.completeWorkItem(workItem.getId(), workItem.getResults());
	}

}
