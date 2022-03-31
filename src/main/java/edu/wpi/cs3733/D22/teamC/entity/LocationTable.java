package edu.wpi.cs3733.D22.teamC.entity;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentServiceRequest;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.util.Callback;

public class LocationTable extends RecursiveTreeObject<LocationTable> {
    StringProperty nodeID;
    StringProperty nodeType;
    StringProperty shortName;
    StringProperty longName;
    StringProperty floor;
    StringProperty building;
    StringProperty x;
    StringProperty y;

    public LocationTable(Location location) {
        this.nodeID    = new SimpleStringProperty(location.getNodeID());
        this.nodeType  = new SimpleStringProperty(location.getNodeType());
        this.shortName = new SimpleStringProperty(location.getShortName());
        this.longName  = new SimpleStringProperty(location.getLongName());
        this.floor     = new SimpleStringProperty(location.getFloor());
        this.building  = new SimpleStringProperty(location.getBuilding());
    }

    public static void createTableColumns(JFXTreeTableView<LocationTable> table) {
        table.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        //Columns for table
        JFXTreeTableColumn<LocationTable, String> nodeIDCol = new JFXTreeTableColumn<>("Node ID");
        nodeIDCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        nodeIDCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().nodeID;
            }
        });
        JFXTreeTableColumn<LocationTable, String> nodeTypeCol = new JFXTreeTableColumn<>("Node Type");
        nodeTypeCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        nodeTypeCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().nodeType;
            }
        });
        JFXTreeTableColumn<LocationTable, String> shortNameCol = new JFXTreeTableColumn<>("Short Name");
        shortNameCol.setMaxWidth(1f * Integer.MAX_VALUE * 13.0);
        shortNameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().shortName;
            }
        });
        JFXTreeTableColumn<LocationTable, String> longNameCol = new JFXTreeTableColumn<>("Long Name");
        longNameCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        longNameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().longName;
            }
        });
        JFXTreeTableColumn<LocationTable, String> floorCol = new JFXTreeTableColumn<>("Floor");
        floorCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        floorCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().floor;
            }
        });
        JFXTreeTableColumn<LocationTable, String> buildingCol = new JFXTreeTableColumn<>("Building");
        buildingCol.setMaxWidth(1f * Integer.MAX_VALUE * 12.5);
        buildingCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().building;
            }
        });
        JFXTreeTableColumn<LocationTable, String> xCol = new JFXTreeTableColumn<>("X");
        xCol.setMaxWidth(1f * Integer.MAX_VALUE * 5.0);
        xCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().x;
            }
        });
        JFXTreeTableColumn<LocationTable, String> yCol = new JFXTreeTableColumn<>("Y");
        yCol.setMaxWidth(1f * Integer.MAX_VALUE * 5.0);
        yCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<LocationTable, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<LocationTable, String> param) {
                return param.getValue().getValue().y;
            }
        });

        // Set Columns
        table.getColumns().setAll(nodeIDCol, nodeTypeCol, shortNameCol, longNameCol, floorCol, buildingCol, xCol, yCol);
    }
}
