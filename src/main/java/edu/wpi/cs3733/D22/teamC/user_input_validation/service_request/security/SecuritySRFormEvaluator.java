package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.security;

import edu.wpi.cs3733.D22.teamC.error.error_item.service_request_user_input_validation.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;

public class SecuritySRFormEvaluator extends ServiceRequestFormEvaluator {

    public SecuritySRFormEvaluator()
    {}

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
     * Determine if the Security Type of SecurityServiceRequest has been filled
     * @param securityType
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkSecurityTypeFilled(String securityType)
    {
        if(securityType.length() == 0 && securityType == null)
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[15];
        }
        else
        {
            return null;
        }
    }
}
