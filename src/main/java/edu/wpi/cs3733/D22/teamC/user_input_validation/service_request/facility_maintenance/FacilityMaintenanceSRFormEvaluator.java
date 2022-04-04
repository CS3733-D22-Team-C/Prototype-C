package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.facility_maintenance;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;

public class FacilityMaintenanceSRFormEvaluator extends ServiceRequestFormEvaluator {

    public FacilityMaintenanceSRFormEvaluator() {}

    @Override
    public ServiceRequestUserInputValidationErrorItem getValidateAssigneeIDResult(int ID) {
        return super.getValidateAssigneeIDResult(ID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem getValidateLocationIDResult(int location) {
        return super.getValidateLocationIDResult(location);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkAssigneeIDFilled(int assigneeID) {
        return super.checkAssigneeIDFilled(assigneeID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkLocationIDFilled(int locationID) {
        return super.checkLocationIDFilled(locationID);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkStatusFilled(String status) {
        return super.checkStatusFilled(status);
    }

    @Override
    public ServiceRequestUserInputValidationErrorItem checkPriorityFilled(String priority) {
        return super.checkPriorityFilled(priority);
    }

    /**
     * Determine if the Maintenance Type of a Facility Maintenance Service Request is filled
     * @param maintenanceType
     * @return ServiceRequestValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkMaintenanceTypeFilled(String maintenanceType)
    {
        if(maintenanceType.length() == 0)
        {

        }
        return null;
    }
}
