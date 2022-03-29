package edu.wpi.cs3733.D22.teamC.controller.service_request;

import edu.wpi.cs3733.D22.teamC.App;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import edu.wpi.cs3733.D22.teamC.entity.service_request.facility_maintenance.FacilityMaintenanceServiceRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;

import java.awt.event.InputMethodEvent;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class FacilitiesMaintenanceController implements Initializable {

    //Fields:

    @FXML private TextField assigneeID;
    @FXML private JFXTextArea description;
    @FXML private TextField maintType;
    @FXML private TextField location;

    //Dropdowns:

    @FXML JFXComboBox<String> status;
    @FXML JFXComboBox<String> priority;

    //Buttons
    @FXML private JFXButton goBackButton;
    @FXML private JFXButton resetButton;
    @FXML private JFXButton submitButton;

    @FXML private JFXTreeTableView<?> table;

    @FXML
    public void initialize(URL url, ResourceBundle rb) {

        //Add options to priority dropdown list:
        priority.getItems().add("Low"); //getItems() returns a list of dropdown items from the JFXComboBox variable, and add() allows you to add a dropdown item to the list.
        priority.getItems().add("Medium");
        priority.getItems().add("High");

        //Add options to status dropdown list:
        status.getItems().add("Blank");
        status.getItems().add("Processing");
        status.getItems().add("Done");

        //Columns for table
        TreeTableColumn ID = new TreeTableColumn("ID");
        ID.setPrefWidth(80);
        TreeTableColumn Assignee = new TreeTableColumn("Assignee");
        Assignee.setPrefWidth(80);
        TreeTableColumn Status = new TreeTableColumn("Status");
        Status.setPrefWidth(80);
        TreeTableColumn Location = new TreeTableColumn("Location");
        Location.setPrefWidth(80);
        TreeTableColumn Type = new TreeTableColumn("Type");
        Type.setPrefWidth(80);
        TreeTableColumn TypeID = new TreeTableColumn("Type ID");
        TypeID.setPrefWidth(80);

        table.getColumns().addAll(ID, Assignee, Status, Location, Type, TypeID);
    }

    @FXML
    void clickReset(ActionEvent event) { //A JavaFX action event is a JavaFX Event, which represents some kind of action performed by the user or the program.
        //clearing text fields/areas
        assigneeID.clear();
        location.clear();
        maintType.clear();
        description.clear();

        //Clearing combo boxes
        priority.valueProperty().set(null); //valueProperty() returns the selected item by the user or the value inputted by the user or the last selected item, and set just sets this item or value to Null (clears the dropdown selection).
        status.valueProperty().set(null);
    }

    @FXML
    void clickGoBack(ActionEvent event) {
        App.instance.setView(""); //Set view to list of service requests page once it is created
    }

    @FXML
    void clickSubmit(ActionEvent event) {
        FacilityMaintenanceServiceRequest fmsr = new FacilityMaintenanceServiceRequest();
        java.util.Date date = new java.util.Date();
        fmsr.setCreationTimestamp(new Timestamp(date.getTime()));
        fmsr.setMaintenanceType(maintType.getText());
        fmsr.setAssigneeID(assigneeID.getText());
        fmsr.setLocation(location.getText());
        fmsr.setPriority(priority.getValue()); //getValue directly returns the value of a selected item from a JavaFX ComboBox
        fmsr.setStatus(status.getValue());
        fmsr.setDescription(description.getText());
        fmsr.setRequestType("Facilities Maintenance");
        //Not sure how to handle requestID, creatorID since I would assume we need login info for these
    }

}