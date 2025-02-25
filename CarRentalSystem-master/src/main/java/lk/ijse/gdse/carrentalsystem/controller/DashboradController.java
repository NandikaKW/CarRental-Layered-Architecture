package lk.ijse.gdse.carrentalsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboradController implements Initializable {
    @FXML
    private JFXButton btnBusiness;
    @FXML
    public JFXButton btnEnd;
    @FXML
    private AnchorPane content;
    @FXML
    private Label lblDate;
    @FXML
    private AnchorPane DashboardPane;

    @FXML
    private Label lblTime;
    @FXML
    private JFXButton btnAgrimentDetail;



    @FXML
    void btnAdminOnAction(ActionEvent event) throws IOException {
        navigateto("/view/Admin.fxml");

    }
    @FXML
    void btnAgrimentDetailOnAction(ActionEvent event) throws IOException {
        navigateto("/view/RentAgriment.fxml");

    }

    @FXML
    void btnAgrimentOnAction(ActionEvent event) throws IOException {
        navigateto("/view/VehicleDetails.fxml");

    }

    @FXML
    void btnCustomerOnAction(ActionEvent event) throws IOException {
        navigateto("/view/Customer.fxml");

    }


    @FXML
    void btnDamageOnAction(ActionEvent event) throws IOException {
        navigateto("/view/DamageDetails.fxml");

    }


    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        navigateto("/view/employee.fxml");

    }

    @FXML
    void btnMaintainOnAction(ActionEvent event) throws IOException {
        navigateto("/view/MaintainSchedule.fxml");

    }

    @FXML
    void btnPackageOnAction(ActionEvent event) throws IOException {
        navigateto("/view/PackageOffers.fxml");

    }


    @FXML
    void btnPaymentDetailOnAction(ActionEvent event) throws IOException {
        navigateto("/view/PaymentTracking.fxml");

    }


    @FXML
    void btnRentOnAction(ActionEvent event) throws IOException {
        navigateto("/view/RentService.fxml");

    }

    @FXML
    void btnRentPaymentOnAction(ActionEvent event) throws IOException {
        navigateto("/view/RentPaymentDetails.fxml");

    }
    @FXML
    void btnVehicleRentOnAction(ActionEvent event) throws IOException {
        navigateto("/view/VehicleRentDetails.fxml");

    }


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        setDateAndTime();
        try {
            navigateto("/view/Design.fxml");  // Ensure this is the correct path
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Error during initialization: " + e.getMessage()).show();
            throw new RuntimeException(e);
        }
    }


    private void navigateto(String fxmlPath) throws IOException {
        try {
            content.getChildren().clear();  // Ensure 'content' is initialized correctly
            AnchorPane load = FXMLLoader.load(this.getClass().getResource(fxmlPath));
            content.getChildren().add(load);
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load form: " + fxmlPath).show();
        }
    }


    private void setDateAndTime() {

        if (lblTime == null || lblDate == null) {
            System.out.println("lblTime is null, check FXML binding.");
            return;
        }


        Timeline time = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    lblDate.setText(LocalDateTime.now().format(dateFormatter));
                    lblTime.setText(LocalDateTime.now().format(timeFormatter));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }
    private boolean isLocationViewActive = true;


    public void btnBusinessOnAction(ActionEvent actionEvent) {
        try {
            // Load the FXML file for the new stage
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SignIn.fxml"));
            AnchorPane root = fxmlLoader.load();

            // Create a new stage for the Sign In window
            Stage signInStage = new Stage();
            signInStage.setTitle("Sign In");
            signInStage.setScene(new Scene(root));

            // Optional: Prevent interaction with other windows until this stage is closed
            signInStage.initModality(Modality.APPLICATION_MODAL);

            // Show the new stage
            signInStage.show();

            // Close the current (dashboard) stage
            Stage currentStage = (Stage) btnBusiness.getScene().getWindow(); // Get the current stage
            currentStage.close(); // Close it
        } catch (IOException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Failed to load SignIn form: " + e.getMessage()).show();
        }
    }

}



