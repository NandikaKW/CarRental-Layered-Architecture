package lk.ijse.gdse.carrentalsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.carrentalsystem.bo.BOFactory;
import lk.ijse.gdse.carrentalsystem.bo.custom.AdminBO;
import lk.ijse.gdse.carrentalsystem.bo.custom.MaintainBO;
import lk.ijse.gdse.carrentalsystem.bo.custom.VehicleBO;
import lk.ijse.gdse.carrentalsystem.dto.MaintainDto;
import lk.ijse.gdse.carrentalsystem.dto.tm.MaintainTM;
//import lk.ijse.gdse.carrentalsystem.model.MaintinModel;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

public class MaintainScheduleController  implements Initializable {
    @FXML
    private ComboBox<Integer> CombMonth;

    @FXML
    private ComboBox<Integer> ComboDay;

    @FXML
    private ComboBox<Integer> ComboYear;

    public TextField txtDescription;

    @FXML
    private JFXButton btnDelete;


    @FXML
    private JFXButton btnReset;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnSearch;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<MaintainTM, BigDecimal> colCost;

    @FXML
    private TableColumn<MaintainTM, String> colDescription;

    @FXML
    private TableColumn<MaintainTM, String> colDuration;

    @FXML
    private TableColumn<MaintainTM, Date> colMaintainDate;

    @FXML
    private TableColumn<MaintainTM, String> colMaintainID;

    @FXML
    private TableColumn<MaintainTM, String> colVechleId;

    @FXML
    private Label lblCost;

    @FXML
    private Label lblDiscription;

    @FXML
    private Label lblDuration;

    @FXML
    private Label lblMaintainDate;

    @FXML
    private Label lblMaintainID;

    @FXML
    private Label lblVehicleID;

    @FXML
    private TableView<MaintainTM> tblMaintain;

    @FXML
    private TextField txtCost;

    @FXML
    private TextField txtDuration;

    @FXML
    private TextField txtMaintainDate;

    @FXML
    private TextField txtMaintainID;

    @FXML
    private TextField txtVehicleID;
    VehicleBO vehicleBO= (VehicleBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.VEHICLE);
    MaintainBO maintainBO= (MaintainBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.MAINTAINANCE);

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        String maintainId = txtMaintainID.getText();

        // Check if Maintain ID is empty
        if (maintainId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Maintain Id").show();
            return;
        }

        // Confirmation alert before deletion
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this Maintain?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        // If user confirms deletion
        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            try {
                // Attempt to delete the maintain record
                boolean isDeleted = maintainBO.deleteMaintain(maintainId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Maintain deleted successfully!").show();
                    clearFields();
                    loadCurrentVehicleId();
                    loadNextMaintainId();
                    refreshTableData();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete Maintain!").show();
                }

            } catch (SQLException e) {
                // SQL exception handling
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting Maintain: Database error - " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                // ClassNotFoundException handling
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting Maintain: Class not found - " + e.getMessage()).show();
            } catch (Exception e) {
                // Catching any other unforeseen errors
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Unexpected error occurred while deleting Maintain: " + e.getMessage()).show();
            }
        }

    }
    private  void refreshTableData() throws SQLException, ClassNotFoundException {
        ArrayList<MaintainDto> maintainDtos= (ArrayList<MaintainDto>) maintainBO.getAllMaintains();
        ObservableList<MaintainTM> maintainTMS = FXCollections.observableArrayList();
        for (MaintainDto dto:maintainDtos){
            MaintainTM maintainTM=new MaintainTM(
                    dto.getMaintain_id(),
                    dto.getCost(),
                    dto.getMaintain_date(),
                    dto.getDescription(),
                    dto.getDuration(),
                    dto.getVehicle_id()
            );
            maintainTMS.add(maintainTM);

        }
        tblMaintain.setItems(maintainTMS);
    }



    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
        loadCurrentVehicleId();
        loadNextMaintainId();

    }
    private void clearFields(){
        txtMaintainID.setText("");
        txtCost.setText("");
        txtDuration.setText("");
        txtMaintainDate.setText("");
        txtDescription.setText("");
        txtVehicleID.setText("");
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        try {
            // Get input values
            String maintainId = txtMaintainID.getText();
            String cost = txtCost.getText();
            String maintainDateStr = txtMaintainDate.getText();
            String description = txtDescription.getText();
            String duration = txtDuration.getText();
            String vehicleId = txtVehicleID.getText();

            // Regex patterns for validation
            String maintainIdPattern = "^M\\d{3}$"; // Matches M001, M002, etc.
            String costPattern = "^\\d+(\\.\\d{1,2})?$"; // Valid decimal format (e.g., 200.00, 350)
            String datePattern = "^\\d{4}-\\d{2}-\\d{2}$"; // Valid date format YYYY-MM-DD
            String durationPattern = "^\\d+\\s(hour|hours|minute|minutes)$"; // Valid duration (e.g., "2 hours", "45 minutes")
            String vehicleIdPattern = "^V\\d{3}$"; // Matches V001, V002, etc.

            // Reset field styles
            resetFieldStyles();

            // Validation checks
            boolean isValidMaintainId = maintainId.matches(maintainIdPattern);
            boolean isValidCost = cost.matches(costPattern);
            boolean isValidDate = maintainDateStr.matches(datePattern);
            boolean isValidDuration = duration.matches(durationPattern);
            boolean isValidVehicleId = vehicleId.matches(vehicleIdPattern);

            // Alert message for validation errors
            if (!isValidMaintainId || !isValidCost || !isValidDate || !isValidDuration || vehicleId.isEmpty()) {
                StringBuilder errorMessage = new StringBuilder("Please fix the following errors:\n");

                if (!isValidMaintainId) {
                    txtMaintainID.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Maintain ID (Expected format: M001)\n");
                }
                if (!isValidCost) {
                    txtCost.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Cost format (Expected format: 200.00 or 350)\n");
                }
                if (!isValidDate) {
                    txtMaintainDate.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Date format (Expected format: YYYY-MM-DD)\n");
                }
                if (!isValidDuration) {
                    txtDuration.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Duration (Expected format: '2 hours' or '45 minutes')\n");
                }
                if (!isValidVehicleId) {
                    txtVehicleID.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Vehicle ID (Expected format: V001)\n");
                }

                new Alert(Alert.AlertType.WARNING, errorMessage.toString()).show();
                return; // Exit if validation fails
            }

            // Convert cost to BigDecimal
            BigDecimal decimalCost;
            try {
                decimalCost = new BigDecimal(cost);
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid cost format!").show();
                return; // Exit if cost format is invalid
            }

            // Parse maintainDate from String to Date
            java.util.Date maintainDate;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                maintainDate = dateFormat.parse(maintainDateStr);
            } catch (ParseException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid date format! Use YYYY-MM-DD.").show();
                return; // Exit if date format is invalid
            }

            // Create MaintainDto object with the validated input values
            MaintainDto maintainDto = new MaintainDto(maintainId, decimalCost, maintainDate, description, duration, vehicleId);

            // Save the MaintainDto object to the database
            try {
                boolean isSaved = maintainBO.saveMaintain(maintainDto);
                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Maintain saved successfully!").show();
                    loadNextMaintainId();
                    loadCurrentVehicleId();
                    refreshPage();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save Maintain!").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
                e.printStackTrace(); // Log for debugging
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Database connection error: " + e.getMessage()).show();
                e.printStackTrace(); // Log for debugging
            }

        } catch (Exception e) {
            // Catch any other unexpected errors
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
            e.printStackTrace(); // Log for debugging
        }
    }

    private void resetFieldStyles() {
        txtMaintainDate.setStyle("");
        txtCost.setStyle("");
        txtDuration.setStyle("");
        txtDescription.setStyle("");
        txtVehicleID.setStyle("");
        txtMaintainID.setStyle("");
    }


    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String maintainId = txtMaintainID.getText(); // Get the user input, not the component ID

        // Check if the Maintain ID is empty and show an error alert
        if (maintainId.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "Please Enter Maintain Id").show();
            return; // Exit the method if Maintain ID is empty
        }

        try {
            // Attempt to search for the Maintain record using the given Maintain ID
            MaintainDto maintain =maintainBO.searchMaintain(maintainId); // Correct model method

            if (maintain != null) {

                txtMaintainID.setText(maintain.getMaintain_id());
                txtCost.setText(maintain.getCost().toString());
                txtMaintainDate.setText(maintain.getMaintain_date().toString());
                txtDescription.setText(maintain.getDescription());
                txtDuration.setText(maintain.getDuration());
                txtVehicleID.setText(maintain.getVehicle_id());

                // Show a success message if the Maintain record is found
                new Alert(Alert.AlertType.INFORMATION, "Maintain found").show();
            } else {
                // If no record is found, clear fields and show an error message
                new Alert(Alert.AlertType.ERROR, "Maintain not found").show();
                clearFields(); // Clear fields if Maintain is not found
                loadCurrentVehicleId(); // Reset vehicle ID field
                loadNextMaintainId(); // Load the next Maintain ID
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            e.printStackTrace(); // Log the exception details for debugging
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Database connection error: " + e.getMessage()).show();
            e.printStackTrace(); // Log the exception details for debugging
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
            e.printStackTrace(); // Log the exception details for debugging
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        try {
            // Retrieve input values from text fields
            String maintainId = txtMaintainID.getText();
            String costStr = txtCost.getText();
            String maintainDateStr = txtMaintainDate.getText();
            String description = txtDescription.getText();
            String duration = txtDuration.getText();
            String vehicleId = txtVehicleID.getText();

            // Regex patterns
            String maintainIdPattern = "^M\\d{3}$"; // Matches M001, M002, etc.
            String costPattern = "^\\d+(\\.\\d{1,2})?$"; // Matches valid decimal numbers
            String maintainDatePattern = "^\\d{4}-\\d{2}-\\d{2}$"; // Matches YYYY-MM-DD format
            String descriptionPattern = "^[\\w\\s]+$"; // Matches letters, digits, and spaces
            String durationPattern = "^[\\w\\s]+$"; // Matches letters, digits, and spaces
            String vehicleIdPattern = "^V\\d{3}$"; // Matches V001, V002, etc.

            // Reset field styles
            resetFieldStyles();

            // Validation checks
            boolean isValidMaintainId = maintainId.matches(maintainIdPattern);
            boolean isValidCost = costStr.matches(costPattern);
            boolean isValidMaintainDate = maintainDateStr.matches(maintainDatePattern);
            boolean isValidDescription = description.matches(descriptionPattern);
            boolean isValidDuration = duration.matches(durationPattern);
            boolean isValidVehicleId = vehicleId.matches(vehicleIdPattern);

            // Handle validation errors
            if (!isValidMaintainId || !isValidCost || !isValidMaintainDate || !isValidDescription || !isValidDuration || !isValidVehicleId) {
                StringBuilder errorMessage = new StringBuilder("Please fix the following errors:\n");

                if (!isValidMaintainId) {
                    txtMaintainID.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Maintain ID (Expected format: M001)\n");
                }
                if (!isValidCost) {
                    txtCost.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Cost (Must be a valid decimal number)\n");
                }
                if (!isValidMaintainDate) {
                    txtMaintainDate.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Maintain Date (Expected format: YYYY-MM-DD)\n");
                }
                if (!isValidDescription) {
                    txtDescription.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Description (Only letters, digits, and spaces allowed)\n");
                }
                if (!isValidDuration) {
                    txtDuration.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Duration (Only letters, digits, and spaces allowed)\n");
                }
                if (!isValidVehicleId) {
                    txtVehicleID.setStyle("-fx-border-color: red;");
                    errorMessage.append("- Invalid Vehicle ID (Expected format: V001)\n");
                }

                new Alert(Alert.AlertType.WARNING, errorMessage.toString()).show();
                return;
            }

            // Convert cost to BigDecimal
            BigDecimal cost;
            try {
                cost = new BigDecimal(costStr); // Attempt to parse the cost
            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid cost format!").show();
                return; // Exit if cost format is invalid
            }

            // Parse maintainDate from String to Date
            java.util.Date maintainDate;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust to your date format
                maintainDate = dateFormat.parse(maintainDateStr);
            } catch (ParseException e) {
                new Alert(Alert.AlertType.ERROR, "Invalid date format! Use YYYY-MM-DD.").show();
                return; // Exit if date format is invalid
            }

            // Create MaintainDto object with the provided details
            MaintainDto maintainDto = new MaintainDto(maintainId, cost, maintainDate, description, duration, vehicleId);

            // Update the database with the updated MaintainDto
            boolean isUpdated = maintainBO.updateMaintain(maintainDto);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Maintain updated successfully!").show();
                refreshPage(); // Refresh the page after successful update
                loadNextMaintainId(); // Load the next maintain ID
                loadCurrentVehicleId(); // Load the current vehicle ID
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Maintain!").show();
            }

        } catch (SQLException e) {
            // Catch and handle SQLException separately
            new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage()).show();
            e.printStackTrace(); // Log the exception for debugging
        } catch (ClassNotFoundException e) {
            // Catch and handle ClassNotFoundException separately
            new Alert(Alert.AlertType.ERROR, "Database connection error: " + e.getMessage()).show();
            e.printStackTrace(); // Log the exception for debugging
        } catch (Exception e) {
            // Catch any other unforeseen exceptions
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
            e.printStackTrace(); // Log the exception for debugging
        }
    }

    @FXML
    void tblMaintainOnClick(MouseEvent event) {
        MaintainTM selectedItem = tblMaintain.getSelectionModel().getSelectedItem();
        if(selectedItem!=null){
            txtMaintainID.setText(selectedItem.getMaintain_id());
            txtCost.setText(selectedItem.getCost().toString());
            txtMaintainDate.setText(selectedItem.getMaintain_date().toString());
            txtDescription.setText(selectedItem.getDescription());
            txtDuration.setText(selectedItem.getDuration());
            txtVehicleID.setText(selectedItem.getVehicle_id());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);

        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colMaintainID.setCellValueFactory(new PropertyValueFactory<>("maintain_id"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colMaintainDate.setCellValueFactory(new PropertyValueFactory<>("maintain_date"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colDuration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        colVechleId.setCellValueFactory(new PropertyValueFactory<>("vehicle_id"));

        try {
            // Refresh and load data
            refreshPage(); // Refresh the page view
            loadNextMaintainId(); // Load the next maintain ID
            loadCurrentVehicleId(); // Load current vehicle ID
            refreshTableData(); // Refresh table with data

        } catch (SQLException | ClassNotFoundException e) {
            // Handle SQL and class loading errors
            e.printStackTrace(); // Print stack trace for debugging
            new Alert(Alert.AlertType.ERROR, "Error occurred while loading Maintain: " + e.getMessage()).show();
        } catch (Exception e) {
            // Catch any other unforeseen exceptions
            e.printStackTrace(); // Print stack trace for debugging
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
        }

        // Initialize date combo boxes (additional functionality, not wrapped in try-catch)
        initializeDateCombos();
    }

    private void initializeDateCombos(){
        // Populate ComboYear with the last 50 years up to the current year
        ComboYear.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(1970, YearMonth.now().getYear()).boxed().toList()
        ));
        ComboYear.getSelectionModel().selectLast();

        // Populate CombMonth with values 1 to 12
        CombMonth.setItems(FXCollections.observableArrayList(
                IntStream.rangeClosed(1, 12).boxed().toList()
        ));
        CombMonth.getSelectionModel().selectFirst();

        // Update days based on initial year and month selection
        updateDays();

    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextMaintainId();
        loadTableData();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();

    }
    private void loadTableData() throws SQLException, ClassNotFoundException {
        // Clear the existing items before loading new data
        tblMaintain.getItems().clear();

        // Load data from the database
        ArrayList<MaintainDto> maintainDtos = (ArrayList<MaintainDto>) maintainBO.getAllMaintains();
        ObservableList<MaintainTM> maintainTMS = FXCollections.observableArrayList();

        for (MaintainDto dto : maintainDtos) {
            // Convert maintain_date to java.util.Date
            Date maintainDate = new Date(dto.getMaintain_date().getTime()); // Convert if needed
            MaintainTM maintainTM = new MaintainTM(
                    dto.getMaintain_id(),
                    dto.getCost(),
                    maintainDate,
                    dto.getDescription(),
                    dto.getDuration(),
                    dto.getVehicle_id()
            );
            maintainTMS.add(maintainTM);
        }

        // Set the items in the table
        tblMaintain.setItems(maintainTMS);
    }

    public  void loadNextMaintainId() throws SQLException, ClassNotFoundException {
        String nextId=maintainBO.getNextMaintainId();
        txtMaintainID.setText(nextId);
    }
    public void loadCurrentVehicleId() throws SQLException, ClassNotFoundException {
        String currentVehicleId =vehicleBO.loadCurrentVehicleId();
        txtVehicleID.setText(currentVehicleId);
    }



    public void ComboYearOnAction(ActionEvent actionEvent) {
        updateDays();

    }

    public void ComboMonthOnAction(ActionEvent actionEvent) {
        updateDays();


    }

    public void ComboDayOnAction(ActionEvent actionEvent) {
        showSelectedDate();

    }
    private void updateDays() {
        Integer year = ComboYear.getValue();
        Integer month = CombMonth.getValue();

        if (year != null && month != null) {
            int daysInMonth = YearMonth.of(year, month).lengthOfMonth();
            ComboDay.setItems(FXCollections.observableArrayList(
                    IntStream.rangeClosed(1, daysInMonth).boxed().toList()
            ));
            ComboDay.getSelectionModel().selectFirst();
            showSelectedDate();
        }


    }
    private void showSelectedDate(){
        Integer year = ComboYear.getValue();
        Integer month = CombMonth.getValue();
        Integer day = ComboDay.getValue();

        if (year != null && month != null && day != null) {
            txtMaintainDate.setText(String.format("%04d-%02d-%02d", year, month, day));
        }


    }
}
