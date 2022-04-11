package edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.medicine_delivery;

import edu.wpi.cs3733.D22.teamC.error.error_item.user_input_validation_error_item.service_request_user_input_validation_error_item.ServiceRequestUserInputValidationErrorItem;
import edu.wpi.cs3733.D22.teamC.error.error_record.service_request_user_input_validation.ServiceRequestUserInputValidationErrorRecord;
import edu.wpi.cs3733.D22.teamC.user_input_validation.service_request.ServiceRequestFormEvaluator;
import javafx.scene.control.SingleSelectionModel;

import java.util.ArrayList;

public class MedicineDeliverySRFormEvaluator extends ServiceRequestFormEvaluator {

    public MedicineDeliverySRFormEvaluator() {}

    public ArrayList<ServiceRequestUserInputValidationErrorItem> getMedicineDeliverySRValidationTestResult(String location, String assigneeID, SingleSelectionModel priority, SingleSelectionModel status, String patientID, String medicine, String dosage)
    {
        ArrayList <ServiceRequestUserInputValidationErrorItem> errorList = new ArrayList <ServiceRequestUserInputValidationErrorItem> ();

        errorList.addAll(super.getBasicRequiredFieldsFilledValidationResult(location, assigneeID, priority, status));
        errorList.add(super.getValidateAssigneeIDResult(assigneeID));
        errorList.add(super.getValidateLocationIDResult(location));
        errorList.add(checkPatientIDFilled(patientID));
        errorList.add(checkMedicineFilled(medicine));
        errorList.add(checkDosageFilled(dosage));

        return errorList;
    }

    /**
     * Determine if the Patient ID of a MedicineDelivery Service Request has been filled
     * @param patientID
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkPatientIDFilled(String patientID)
    {
        if(patientID.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[10];
        }
        else
        {
            int patientIDConv = Integer.parseInt(patientID);
            int patientIDLength = (int)(Math.log10(patientIDConv)+1);

            if(patientIDLength == 0)
            {
                return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[10];
            }
            else
            {
                return null;
            }
        }
    }

    /**
     * Determine if the Medicine of a MedicineDelivery Service Request has been filled
     * @param medicine
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkMedicineFilled(String medicine)
    {
        if(medicine.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[11];
        }
        else
        {
            return null;
        }
    }

    /**
     * Determine if the Dosage of a MedicineDelivery Service Request has been filled
     * @param dosage
     * @return ServiceRequestUserInputValidationErrorItem
     */
    public ServiceRequestUserInputValidationErrorItem checkDosageFilled(String dosage)
    {
        if(dosage.isEmpty())
        {
            return ServiceRequestUserInputValidationErrorRecord.serviceRequestUserInputValidationErrorList[12];
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
