package edu.wpi.cs3733.D22.teamC.controller.map.data.medical_equipment;

import edu.wpi.cs3733.D22.teamC.App;
import edu.wpi.cs3733.D22.teamC.controller.map.MapViewController;
import edu.wpi.cs3733.D22.teamC.controller.map.data.ManagerMapNodes;
import edu.wpi.cs3733.D22.teamC.controller.map.data.MapNode;
import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.floor.FloorDAO;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import edu.wpi.cs3733.D22.teamC.entity.location.LocationDAO;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipment;
import edu.wpi.cs3733.D22.teamC.entity.medical_equipment.MedicalEquipmentDAO;
import javafx.scene.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Manages Medical Equipment data for MapControllers. An optional manager for Map to work with Medical Equipment Functionality.
 */
public class MedicalEquipmentManager extends ManagerMapNodes<MedicalEquipment> {
    // Constants
    public static final String[] COUNTER_PATHS = {
            "view/map/nodes/medical_equipment/bed.fxml",
            "view/map/nodes/medical_equipment/pump.fxml",
            "view/map/nodes/medical_equipment/recliner.fxml",
            "view/map/nodes/medical_equipment/xray.fxml"
    };

    // Variables
    Consumer<Location> onPreviewLocationEvent = this::previewLocation;
    Consumer<Location> onFocusLocationEvent = this::focusLocation;
    private final MedicalEquipmentCounter[] counters = new MedicalEquipmentCounter[MedicalEquipment.EquipmentType.values().length];

    public MedicalEquipmentManager(MapViewController mapViewController) {
        super(mapViewController);

        mapViewController.getLocationManager().onPreviewLocationEvents.add(onPreviewLocationEvent);
        mapViewController.getLocationManager().onFocusLocationEvents.add(onFocusLocationEvent);

        // Create Counters
        for (int i = 0; i < MedicalEquipment.EquipmentType.values().length; i++) {
            App.View<MedicalEquipmentCounter> view = App.instance.loadView(COUNTER_PATHS[i]);

            // Setup Controller
            MedicalEquipmentCounter controller = view.getController();

            getMapController().getBottomOverlay().getChildren().add(view.getNode());
            controller.setType(MedicalEquipment.EquipmentType.values()[i]);

            counters[i] = controller;
        }

        // Set Counters
        List<Location> locations = new LocationDAO().getAll();
        List<String> locationIDs = locations.stream().map(Location::getID).collect(Collectors.toList());
        List<MedicalEquipment> medicalEquipments = new MedicalEquipmentDAO().getAll();
        medicalEquipments = medicalEquipments.stream().filter(medicalEquipment -> !locationIDs.contains(medicalEquipment.getLocationID())).collect(Collectors.toList());

        for (MedicalEquipment.EquipmentType equipmentType : MedicalEquipment.EquipmentType.values()) {
            List<MedicalEquipment> medicalEquipmentsByType = medicalEquipments.stream().filter(medicalEquipment -> medicalEquipment.getEquipmentType() == equipmentType).collect(Collectors.toList());
            counters[equipmentType.ordinal()].setMedicalEquipments(medicalEquipmentsByType);
        }

        focusLocation(mapViewController.getLocationManager().getCurrent());
    }

    public void shutdown() {
        mapViewController.getLocationManager().onPreviewLocationEvents.remove(onPreviewLocationEvent);
        mapViewController.getLocationManager().onFocusLocationEvents.remove(onFocusLocationEvent);

        previewLocation(null);
        focusLocation(null);

        // Delete Counters
        for (MedicalEquipmentCounter counter : counters) {
            counter.root.getChildren().clear();
            getMapController().getBottomOverlay().getChildren().remove(counter.root);
        }
    }

    //#region Location Changes
        public void previewLocation(Location location) {
            if (focused == null || focused.getLocation() != location) {
                if (previewed != null) {
                    removeNode(previewed);
                    previewed = null;
                }

                if (focused != null && focused.getLocation() == location) {
                    ((MedicalEquipmentNode) focused).toPreviewMode();
                    previewed = focused;
                    focused = null;
                } else if (location != null) {
                    previewed = new MedicalEquipmentNode(this, location);
                    ((MedicalEquipmentNode) previewed).toPreviewMode();
                }
            }
        }

        public void focusLocation(Location location) {
            if (focused != null) {
                removeNode(focused);
                focused = null;
            }

            if (previewed != null && previewed.getLocation() == location) {
                ((MedicalEquipmentNode) previewed).toFocusMode();
                focused = previewed;
                previewed = null;
            } else if (location != null) {
                focused = new MedicalEquipmentNode(this, location);
                ((MedicalEquipmentNode) focused).toFocusMode();
            }
        }
    //#endregion

    //#region Map Node Manipulation
        @Override
        public void removeNode(MapNode<MedicalEquipment> mapNode) {
            ((MedicalEquipmentNode) mapNode).removeNode();
        }
    //#endregion

    //#region Free Medical Equipment
        public int getFreeCount(MedicalEquipment.EquipmentType equipmentType) {
            return counters[equipmentType.ordinal()].getCount();
        }

        public MedicalEquipment removeFree(MedicalEquipment.EquipmentType equipmentType) {
            MedicalEquipmentCounter counter = counters[equipmentType.ordinal()];
            return counter.removeMedicalEquipment();
        }

        public void addFree(MedicalEquipment medicalEquipment) {
            MedicalEquipmentCounter counter = counters[medicalEquipment.getEquipmentType().ordinal()];
            counter.addMedicalEquipment(medicalEquipment);
        }
    //#endregion
}