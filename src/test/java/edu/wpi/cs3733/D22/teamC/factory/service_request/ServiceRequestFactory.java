package edu.wpi.cs3733.D22.teamC.factory.service_request;

import edu.wpi.cs3733.D22.teamC.entity.employee.Employee;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.factory.Factory;
import edu.wpi.cs3733.D22.teamC.factory.employee.EmployeeFactory;

import java.sql.Timestamp;
import java.util.Random;

public class ServiceRequestFactory<T extends ServiceRequest> implements Factory<T> {
    protected Random generator = new Random();
    EmployeeFactory employeeFactory = new EmployeeFactory();

    @SuppressWarnings("unchecked")
    public T create() {
        T serviceRequest = (T) new ServiceRequest();
        return create(serviceRequest);
    }

    public T create(T serviceRequest) {
        Employee creator = null;
        Employee assignee = null;
        //String assigneeID = String.valueOf(generator.nextInt(200000));
        String locationID = String.valueOf(generator.nextInt(200000));
        Timestamp creationTimestamp = new Timestamp(System.currentTimeMillis());
        ServiceRequest.Status status = ServiceRequest.Status.values()[generator.nextInt(ServiceRequest.Status.values().length)];
        ServiceRequest.Priority priority = ServiceRequest.Priority.values()[generator.nextInt(ServiceRequest.Priority.values().length)];
        String description = "jsjsjsjsjskssksksksksprrrr";
        String modifierID = String.valueOf(generator.nextInt(200000));
        Timestamp modifiedTimeStamp = new Timestamp(System.currentTimeMillis());

        serviceRequest.setCreator(creator);
        serviceRequest.setAssignee(assignee);
        serviceRequest.setLocation(locationID);
        serviceRequest.setCreationTimestamp(creationTimestamp);
        serviceRequest.setStatus(status);
        serviceRequest.setPriority(priority);
        serviceRequest.setDescription(description);
        serviceRequest.setModifierID(modifierID);
        serviceRequest.setModifiedTimestamp(modifiedTimeStamp);
        
        return serviceRequest;
    }
}
