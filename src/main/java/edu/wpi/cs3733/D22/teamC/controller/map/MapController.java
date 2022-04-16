package edu.wpi.cs3733.D22.teamC.controller.map;


import edu.wpi.cs3733.D22.teamC.entity.floor.Floor;
import edu.wpi.cs3733.D22.teamC.entity.location.Location;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Facilitates Map interaction as a go between for data (managers, nodes), panel (interaction, controls), and the Map.
 */
public class MapController {
    // Constants
    protected final static double MAX_SCALE = 1.3f;
    protected final static double MIN_SCALE = 0.7f;

    // FXML
    @FXML private Pane stackPane;
    @FXML private Pane mapPane;
    @FXML private ScrollPane scrollPane;
    @FXML private ImageView mapImage;

    @FXML private HBox bottomOverlay;

    // References
    MapViewController mapViewController;

    //#region External Events
        public void setup(MapViewController mapViewController) {
            this.mapViewController = mapViewController;
        }

        public void setFloor(Floor newFloor) {
            // TODO: Pull Image from DB
            Path filePath = Paths.get("maps/" + newFloor.getImageSrc());
            Image image = new Image("file:" + filePath);
            mapImage.setImage(image);
            mapPane.setPrefWidth(image.getWidth());
            mapPane.setPrefHeight(image.getHeight());
        }
    //#endregion

    //#region FXML Events
        @FXML
        protected void onMouseClickedMap(MouseEvent event) {
            mapViewController.getLocationManager().unfocus();
            if (mapViewController.getLocationManager().isEditMode()) doubleClickAddLocation(event);

            event.consume();
        }

        @FXML
        protected void onMousePressedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.MIDDLE)) {
                // Middle-Click allow map panning
                scrollPane.setPannable(true);
            }
        }

        @FXML
        protected void onMouseReleasedMap(MouseEvent event) {
            if (event.getButton().equals(MouseButton.MIDDLE)) {
                // Middle-Click disallow map panning
                scrollPane.setPannable(false);
            }
        }

        @FXML
        protected void onMouseScrollMap(ScrollEvent event) {
            if (event.getDeltaY() != 0) {
                double scale = (event.getDeltaY() < 0)
                        ? Math.max(stackPane.getScaleX() - 0.1, MIN_SCALE)
                        : Math.min(stackPane.getScaleX() + 0.1, MAX_SCALE);
                stackPane.setScaleX(scale);
                stackPane.setScaleY(scale);

                event.consume();
            }
        }
    //#endregion

    //#region Toggleable Events
        /**
         * Double Click Map, Add Location.
         * @param event MouseEvent
         */
        public void doubleClickAddLocation(MouseEvent event) {
            if (event.getClickCount() == 2) {
                Location location = new Location();
                location.setX((int) event.getX());
                location.setY((int) event.getY());
                location.setFloor(mapViewController.getFloorManager().getCurrent().getID());
                location.setNodeType(Location.NodeType.values()[0]);

                mapViewController.getLocationManager().addObject(location);
                mapViewController.getLocationManager().changeCurrent(location);
            }
        }
    //#endregion

    //#region Getters
        public Pane getMap() {
            return mapPane;
        }

        public HBox getBottomOverlay() {
            return bottomOverlay;
        }
    //#endregion
}
