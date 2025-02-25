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
import lk.ijse.gdse.carrentalsystem.bo.custom.DamageDetailBO;
import lk.ijse.gdse.carrentalsystem.bo.custom.RentBO;
import lk.ijse.gdse.carrentalsystem.db.DBConnection;
import lk.ijse.gdse.carrentalsystem.dto.DamageDto;
//import lk.ijse.gdse.carrentalsystem.model.DamageModel;
import lk.ijse.gdse.carrentalsystem.dto.tm.DamageTM;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DamageDetailsController implements Initializable {
    @FXML
    private JFXButton btnReport;

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
    private TableColumn<DamageTM, String> colDamageId;

    @FXML
    private TableColumn<DamageTM, String> colDescription;

    @FXML
    private TableColumn<DamageTM, String> colRentId;

    @FXML
    private TableColumn<DamageTM, BigDecimal> colRepairCost;

    @FXML
    private Label lblDamageId;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblRentId;

    @FXML
    private Label lblRepairCost;

    @FXML
    private TableView<DamageTM> tblDamage;

    @FXML
    private TextField txtDamageId;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtRentId;

    @FXML
    private TextField txtRepairCost;
    DamageDetailBO damageDetailBO= (DamageDetailBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.DAMAGE_DETAIL);
    RentBO rentBO = (RentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.RENT);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String damageId = txtDamageId.getText();

        if (damageId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter damage ID").show();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this damage?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = alert.showAndWait();

        // Only proceed if the user confirms the deletion
        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            try {
                // Attempt to delete the damage record
                boolean isDeleted = damageDetailBO.delete(damageId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Damage deleted successfully!").show();
                    clearFields();
                    loadCurrentRentId();
                    loadNextDamageId();
                    refreshTableData();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete damage!").show();
                }
            } catch (SQLException e) {
                // Handle SQL exception
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while deleting damage: " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                // Handle ClassNotFoundException
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Class not found while deleting damage: " + e.getMessage()).show();
            } catch (Exception e) {
                // Catch any unforeseen exceptions
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
            }
        }
    }
    private  void refreshTableData() throws SQLException, ClassNotFoundException {
        ArrayList<DamageDto> damageDtos=damageDetailBO.getAll();
        ObservableList<DamageTM> damageTMS=FXCollections.observableArrayList();

        for(DamageDto  dto:damageDtos){
            DamageTM damageTM=new DamageTM(
                    dto.getDamage_id(),
                    dto.getRepair_cost(),
                    dto.getDetail(),
                    dto.getRent_id()
            );
            damageTMS.add(damageTM);

        }
        tblDamage.setItems(damageTMS);
    }
    private  void clearFields(){
        txtDamageId.setText("");
        txtDescription.setText("");
        txtRepairCost.setText("");
        txtRentId.setText("");
    }
    @FXML
    void OnClickedTable(MouseEvent event) {
        DamageTM damageTM=tblDamage.getSelectionModel().getSelectedItem();
        if(damageTM!=null){
            txtDamageId.setText(damageTM.getDamage_id());
            txtRepairCost.setText(String.valueOf(damageTM.getRepair_cost()));
            txtDescription.setText(damageTM.getDetail());
            txtRentId.setText(damageTM.getRent_id());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);



        }

    }




    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        clearFields();
        loadCurrentRentId();
        loadNextDamageId();

    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String damageId = txtDamageId.getText();
        String detail = txtDescription.getText();
        String rentId = txtRentId.getText();

        // Regex patterns
        String damageIdPattern = "^D\\d{3}$";
        String detailPattern = "^[\\w\\s,.#-]+$";
        String rentIdPattern = "^R\\d{3}$";

        // Validation checks
        boolean isValidDamageId = damageId.matches(damageIdPattern);
        boolean isValidDetail = detail.matches(detailPattern);
        boolean isValidRentId = rentId.matches(rentIdPattern);

        // Reset field styles
        resetFieldStyles();

        // Highlight invalid fields
        if (!isValidDamageId) {
            txtDamageId.setStyle("-fx-border-color: red;");
            new Alert(Alert.AlertType.WARNING, "Invalid Damage ID. Format should be Dxxx").show();
        }

        if (!isValidDetail) {
            txtDescription.setStyle("-fx-border-color: red;");
            new Alert(Alert.AlertType.WARNING, "Invalid Detail. Format is not valid.").show();
        }

        if (!isValidRentId) {
            txtRentId.setStyle("-fx-border-color: red;");
            new Alert(Alert.AlertType.WARNING, "Invalid Rent ID. Format should be Rxxx").show();
        }

        // Handle repair cost field
        BigDecimal repairCost;
        try {
            repairCost = new BigDecimal(txtRepairCost.getText());
        } catch (NumberFormatException e) {
            txtRepairCost.setStyle("-fx-border-color: red;");
            new Alert(Alert.AlertType.WARNING, "Invalid Repair Cost. Please enter a valid number.").show();
            return; // Stop the operation if repair cost is invalid
        }

        // If all fields are valid, proceed to save
        if (isValidDamageId && isValidDetail && isValidRentId) {
            try {
                DamageDto damageDto = new DamageDto(damageId, repairCost, detail, rentId);
                boolean isSaved = damageDetailBO.save(damageDto);

                if (isSaved) {
                    new Alert(Alert.AlertType.INFORMATION, "Damage saved successfully!").show();
                    clearFields();
                    refreshPage();
                    loadCurrentRentId();
                    loadNextDamageId();
                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to save damage! Please try again later.").show();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Error occurred while saving damage: " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Class not found while saving damage: " + e.getMessage()).show();
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
            }
        }
    }

    private void resetFieldStyles() {
        txtDamageId.setStyle("");
        txtDescription.setStyle("");
        txtRepairCost.setStyle("");
        txtRentId.setStyle("");
    }

    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String damageId = txtDamageId.getText();

        // If the Damage ID is empty, show a warning and stop further processing
        if (damageId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter Damage ID").show();
            return;
        }

        try {
            System.out.println("Searching for Damage ID: " + damageId); // Debugging line


            DamageDto damage = damageDetailBO.search(damageId);

            if (damage != null) {
                System.out.println("Damage Found: " + damage.toString()); // Debugging line


                txtDamageId.setText(damage.getDamage_id());
                txtRepairCost.setText(damage.getRepair_cost().toString());
                txtDescription.setText(damage.getDetail());
                txtRentId.setText(damage.getRent_id());
                new Alert(Alert.AlertType.INFORMATION, "Damage Found!").show();
            } else {

                new Alert(Alert.AlertType.ERROR, "Damage not found!").show();
                clearFields();
                loadCurrentRentId();
                loadNextDamageId();
            }
        } catch (SQLException e) {

            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error occurred while searching for damage: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Class not found error occurred while searching for damage: " + e.getMessage()).show();
        } catch (Exception e) {

            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "An unexpected error occurred: " + e.getMessage()).show();
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String damageId = txtDamageId.getText();
        if (damageId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter damage ID").show();
            return;
        }

        String detail = txtDescription.getText();
        String rentId = txtRentId.getText();

        // Regex patterns
        String damageIdPattern = "^D\\d{3}$"; // Matches D001, D002, etc.
        String detailPattern = "^[\\w\\s,.#-]+$"; // Allows letters, numbers, spaces, and common punctuation
        String rentIdPattern = "^R\\d{3}$"; // Matches R001, R002, etc.

        // Validation checks
        boolean isValidDamageId = damageId.matches(damageIdPattern);
        boolean isValidDetail = detail.matches(detailPattern);
        boolean isValidRentId = rentId.matches(rentIdPattern);

        // Reset field styles
        resetFieldStyles();

        if (!isValidDamageId) {
            txtDamageId.setStyle("-fx-border-color: red;");
            System.out.println("Invalid Damage ID.");
        }

        if (!isValidDetail) {
            txtDescription.setStyle("-fx-border-color: red;");
            System.out.println("Invalid Detail.");
        }

        if (!isValidRentId) {
            txtRentId.setStyle("-fx-border-color: red;");
            System.out.println("Invalid Rent ID.");
        }

        BigDecimal repairCost;
        try {
            repairCost = new BigDecimal(txtRepairCost.getText());
        } catch (NumberFormatException e) {
            txtRepairCost.setStyle("-fx-border-color: red;");
            System.out.println("Invalid Repair Cost.");
            return;
        }


        if (isValidDamageId && isValidDetail && isValidRentId) {
            DamageDto damageDto = new DamageDto(damageId, repairCost, detail, rentId);
            boolean isUpdated = damageDetailBO.update(damageDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Damage Detail updated successfully!").show();
                refreshPage();
                loadCurrentRentId();
                loadNextDamageId();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update Damage Detail!").show();
            }
        }
    }


    @FXML
    void tblDamgeOnAction(MouseEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colDamageId.setCellValueFactory(new PropertyValueFactory<>("damage_id"));
        colRepairCost.setCellValueFactory(new PropertyValueFactory<>("repair_cost"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("detail"));
        colRentId.setCellValueFactory(new PropertyValueFactory<>("rent_id"));
        try{
            refreshPage();
            loadCurrentRentId();
            loadNextDamageId();
            refreshTableData();



        }catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to load Damage Details!").show();

        }
    }
    private  void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextDamageId();
        loadTableDta();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearFields();

    }
    private  void loadTableDta() throws SQLException, ClassNotFoundException {
        ArrayList<DamageDto> damageDtos=damageDetailBO.getAll();
        ObservableList<DamageTM> damageTMS=FXCollections.observableArrayList();
        for(DamageDto damageDto:damageDtos){
            DamageTM damageTM=new DamageTM(
                    damageDto.getDamage_id(),
                    damageDto.getRepair_cost(),
                    damageDto.getDetail(),
                    damageDto.getRent_id()
            );
            damageTMS.add(damageTM);

        }
        tblDamage.setItems(damageTMS);
    }
    public  void loadNextDamageId() throws SQLException, ClassNotFoundException {
        String nextDamageID=damageDetailBO.getNextId();
        txtDamageId.setText(nextDamageID);
    }



    public void loadCurrentRentId() throws SQLException, ClassNotFoundException {
        String currentRentId = rentBO.loadCurrentRentId();
        txtRentId.setText(currentRentId);
    }


    public void btnReportOnAction(ActionEvent actionEvent) {
        try {

            JasperReport jasperReport = JasperCompileManager.compileReport(
                    getClass().getResourceAsStream("/report/DamageReport.jrxml")
            );


            Connection connection = DBConnection.getInstance().getConnection();


            Map<String, Object> parameters = new HashMap<>();
            parameters.put("P_Date", LocalDate.now().toString());  // Example parameter

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport,
                    parameters,
                    connection
            );

            // Display the report
            JasperViewer.viewReport(jasperPrint, false);

        } catch (JRException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate report!").show();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Database error!").show();
        }
    }
}
