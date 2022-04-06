package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class FacilityMaintenanceSRFormEvaluator extends ServiceRequestFormEvaluator {

    public FacilityMaintenanceSRFormEvaluator() {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getFacilityMaintenanceSRValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, SingleSelectionModel maintenanceType)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(location, assigneeID, priority, status));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(location));
        errorList.add(checkMaintenanceTypeFilled(maintenanceType));

        return errorList;
    }

    /**
     * Determine if the Maintenance Type of Facility Maintenance Service Request is filled
     * @param maintenanceType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkMaintenanceTypeFilled(SingleSelectionModel maintenanceType)
    {
        if(maintenanceType.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[5];
        }
        else
        {
            return null;
        }
    }

    @Override
    public boolean noServiceRequestFormUserInputErrors(ArrayList<ServiceRequestUserInputValidationErrorItem> l) {
        return super.noServiceRequestFormUserInputErrors(l);
    }
}