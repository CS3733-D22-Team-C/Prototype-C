package edu.wpi.cs3733.D22.teamC.controller.service_request.medical_equipment;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.controller.service_request.InsertServiceRequestCreateController;
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequestDAO;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.lab_system.LabSystemSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAOImpl;
import edu.wpi.cs3733.D22.teamC.models.service_request.ServiceRequestTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.lab_system.LabSystemSRTableDisplay;
import edu.wpi.cs3733.D22.teamC.models.service_request.medical_equipment.MedicalEquipmentSRTableDisplay;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MedicalEquipmentSRInsertCreateController implements InsertServiceRequestCreateController<MedicalEquipmentSR>, Initializable {
    @FXML
    private JFXComboBox<String> equipmentType;
    @FXML
    private JFXComboBox<String> equipmentID;
    private String lastType;




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Priority dropdown
        for (MedicalEquipmentSR.EquipmentType EqType : MedicalEquipmentSR.EquipmentType.values()) {
            equipmentType.getItems().add(EqType.toString());
        }
    }

    @Override
    public void clearFields() {
        equipmentType.valueProperty().setValue(null);
    }

    @Override
    public MedicalEquipmentSR createServiceRequest() {
        MedicalEquipmentSR MESR = new MedicalEquipmentSR();

        MESR.setEquipmentType(MedicalEquipmentSR.EquipmentType.valueOf(equipmentType.getValue()));

        return MESR;
    }

    @Override
    public ServiceRequestDAO<MedicalEquipmentSR> createServiceRequestDAO() {
        return new MedicalEquipmentSRDAOImpl();
    }

    @Override
    public ServiceRequestTableDisplay<MedicalEquipmentSR> setupTable(JFXTreeTableView<?> table) {
        return new MedicalEquipmentSRTableDisplay(table);
    }

    @FXML
    void equipTypeChanged(MouseEvent event) {
        //If on the same equipment type
        if (equipmentType.getValue().equals(lastType)) {
            return;
        } else {
            lastType = equipmentType.getValue();

            //Resetting the values
            equipmentID.valueProperty().setValue(null);
            equipmentID.getItems().clear();
            //Number of each equipment item
            int numBeds = 20;
            int numXRay = 1;
            int numInfusion = 30;
            int numRecliners = 6;

            String type = "";
            int nums = 0;

            if (equipmentType.getValue().equals(MedicalEquipmentSR.EquipmentType.Bed.toString())) {
                type = "BED";
                nums = numBeds;
            } else if (equipmentType.getValue().equals(MedicalEquipmentSR.EquipmentType.Recliner.toString())) {
                type = "REC";
                nums = numRecliners;
            } else if (equipmentType.getValue().equals(MedicalEquipmentSR.EquipmentType.Infusion_Pump.toString())) {
                type = "INF";
                nums = numInfusion;
            } else if (equipmentType.getValue().equals(MedicalEquipmentSR.EquipmentType.Portable_X_Ray.toString())) {
                type = "XRA";
                nums = numXRay;
            }

            //Adds all possible values to dropdown
            for (int i = 1; i <= nums; i++) {
                String ID = type;
                ID += String.format("%07d", i);
                equipmentID.getItems().add(ID);
            }
        }
    }
    @Override
    public boolean requiredFieldsPresent(){
        if(equipmentID.getValue() == null)
            return false;
        if(equipmentType.getValue() == null)
            return false;
        return true;
    }
}
