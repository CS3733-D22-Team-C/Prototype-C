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
import edu.wpi.cs3733.D22.teamC.entity.service_request.ServiceRequest;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSR;
import edu.wpi.cs3733.D22.teamC.entity.service_request.medical_equipment.MedicalEquipmentSRDAO;
import edu.wpi.cs3733.D22.teamC.models.builders.DialogBuilder;
import edu.wpi.cs3733.D22.teamC.models.builders.NotificationBuilder;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Manages Medical Equipment data for MapControllers. An optional manager for Map to work with Medical Equipment Functionality.
 */
public class MedicalEquipmentManager extends ManagerMapNodes<MedicalEquipment> {
    // Constants
    public static final String[] COUNTER_PATHS = {
            "view/map/nodes/medical_equipment/bed.fxml",
            "view/map/nodes/medical_equipment/recliner.fxml",
            "view/map/nodes/medical_equipment/xray.fxml",
            "view/map/nodes/medical_equipment/pump.fxml"
    };

    // Variables
    Consumer<Location> onPreviewLocationEvent = this::previewLocation;
    Consumer<Location> onFocusLocationEvent = this::focusLocation;
    private final MedicalEquipmentCounter[] counters = new MedicalEquipmentCounter[MedicalEquipment.EquipmentType.values().length];

    AtomicBoolean updateAutomation = new AtomicBoolean(false);
    AtomicBoolean askUpdateAutomation = new AtomicBoolean(true);

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

    //#region Medical Equipment Manipulation
        public void moveMedicalEquipment(MedicalEquipment medicalEquipment, Location location) {
            if (location != null) {
                String movementMessage = medicalEquipment + " moved to " + location.getLongName() + "";

                // Alert Prompt - Update Medical Equipment
                if (askUpdateAutomation.get()) {
                    boolean updateable = false;
                    String prompt = movementMessage;
                    movementMessage += ".";

                    switch (location.getNodeType()) {
                        case DIRT:
                            if (!medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)) {
                                prompt += ", which is a DIRT location. Would you like to update this equipment's status to be Dirty?";
                                updateable = true;
                            }
                            break;
                        case STOR:
                            if (!medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Available)) {
                                prompt += ", which is a STOR location. Would you like to update this equipment's status to be Available?";
                                updateable = true;
                            }
                            break;
                        default:
                            if (!medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Unavailable)) {
                                prompt += ". Would you like to update this equipment's status to be Unavailable?";
                                updateable = true;
                            }
                            break;
                    }

                    if (updateable) {
                        Alert alert = DialogBuilder.createAlertWithOptOut(Alert.AlertType.NONE, "Update Medical Equipment Status",
                                null, prompt, "Do not ask again",
                                param -> askUpdateAutomation.set(!param), ButtonType.YES, ButtonType.NO);
                        alert.showAndWait().ifPresent(btnType -> {
                            if (btnType.getButtonData() == ButtonBar.ButtonData.YES) {
                                updateAutomation.set(true);
                            } else if (btnType.getButtonData() == ButtonBar.ButtonData.NO) {
                                updateAutomation.set(false);
                            }
                        });
                    }
                }

                // Update Medical Equipment
                if (updateAutomation.get()) {
                    switch (location.getNodeType()) {
                        case DIRT:
                            medicalEquipment.setStatus(MedicalEquipment.EquipmentStatus.Dirty);
                            movementMessage += " Its status has been updated to Dirty.";
                            break;
                        case STOR:
                            medicalEquipment.setStatus(MedicalEquipment.EquipmentStatus.Available);
                            movementMessage += " Its status has been updated to Available.";
                            break;
                        default:
                            medicalEquipment.setStatus(MedicalEquipment.EquipmentStatus.Unavailable);
                            movementMessage += " Its status has been updated to Unavailable.";
                            break;
                    }
                }

                // Push Notification
                NotificationBuilder.createNotification("Medical Equipment Moved", movementMessage);

                // Service Request Automation
                switch (medicalEquipment.getEquipmentType()) {
                    case Bed:
                        if (medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)) {
                            List<MedicalEquipment> dirtyBeds = new MedicalEquipmentDAO().getEquipmentByFloor(location.getFloor()).stream().filter(
                                    medEq -> medEq.getEquipmentType().equals(MedicalEquipment.EquipmentType.Bed)
                                            && medEq.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)
                            ).collect(Collectors.toList());
                            if (dirtyBeds.size() >= 6) {
                                createServiceRequest(medicalEquipment, new LocationDAO().getByLongName("OR Bed Park Floor L1"),
                                        "As there are already " + dirtyBeds.size() + " dirty beds on " + mapViewController.getFloorManager().getByID(location.getFloor()).getLongName() + ",");
                            }
                        }
                        break;
                    case Infusion_Pump:
                        if (medicalEquipment.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)) {
                            List<MedicalEquipment> dirtyPumps = new MedicalEquipmentDAO().getEquipmentByFloor(location.getFloor()).stream().filter(
                                    medEq -> medEq.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)
                                            && medEq.getStatus().equals(MedicalEquipment.EquipmentStatus.Dirty)
                            ).collect(Collectors.toList());
                            if (dirtyPumps.size() >= 10) {
                                createServiceRequest(medicalEquipment, new LocationDAO().getByLongName("West Plaza"),
                                        "As there are already " + dirtyPumps.size() + " dirty beds on " + mapViewController.getFloorManager().getByID(location.getFloor()).getLongName() + ",");
                            } else {
                                List<MedicalEquipment> cleanPumps = new MedicalEquipmentDAO().getEquipmentByFloor(location.getFloor()).stream().filter(
                                        medEq -> medEq.getEquipmentType().equals(MedicalEquipment.EquipmentType.Infusion_Pump)
                                                && medEq.getStatus().equals(MedicalEquipment.EquipmentStatus.Available)
                                ).collect(Collectors.toList());
                                if (cleanPumps.size() < 5) {
                                    createServiceRequest(medicalEquipment, new LocationDAO().getByLongName("West Plaza"),
                                            ((cleanPumps.size() == 1) ? "As there is only 1 clean pump remaining on " : "As there are" + (cleanPumps.size() == 0 ? "" : " only") + " " + cleanPumps.size() + " clean pumps remaining on ") + mapViewController.getFloorManager().getByID(location.getFloor()).getLongName() + ",");
                                }
                            }
                        }
                        break;
                    default:
                        break;
                }
            }

            // Make Updates
            medicalEquipment.setLocationID(location == null ? "" : location.getID());
            new MedicalEquipmentDAO().update(medicalEquipment);
            onUpdateDataEvents.forEach(Runnable::run);
        }

        private void createServiceRequest(MedicalEquipment medicalEquipment, Location defaultLocation, String notification) {
            MedicalEquipmentSR serviceRequest = new MedicalEquipmentSR();

            serviceRequest.setCreationTimestamp(new Timestamp(System.currentTimeMillis()));
            serviceRequest.setCreator(App.instance.getUserAccount());
            serviceRequest.setModifiedTimestamp(new Timestamp(System.currentTimeMillis()));
            serviceRequest.setModifier(App.instance.getUserAccount());

            serviceRequest.setRequestType(ServiceRequest.RequestType.Medical_Equipment);
            serviceRequest.setPriority(ServiceRequest.Priority.Low);
            serviceRequest.setStatus(ServiceRequest.Status.Blank);
            serviceRequest.setDescription("");

            serviceRequest.setEquipmentType(medicalEquipment.getEquipmentType());
            serviceRequest.setEquipmentID(medicalEquipment.getID());
            serviceRequest.setEquipmentStatus(MedicalEquipmentSR.EquipmentStatus.Available);
            serviceRequest.setLocation(defaultLocation == null ? "" : defaultLocation.getID());

            MedicalEquipmentSRDAO dao = new MedicalEquipmentSRDAO();
            dao.insert(serviceRequest);
            serviceRequest = dao.getByID(serviceRequest.getID());

            notification += " Service Request " + serviceRequest + " has been created to clean " + medicalEquipment;
            if (defaultLocation != null) {
                notification += " at " + defaultLocation + ".";
            } else {
                notification += ".";
            }

            // Push Notification
            NotificationBuilder.createNotification("Medical Equipment Service Request", notification);
        }
    //#endregion
}
