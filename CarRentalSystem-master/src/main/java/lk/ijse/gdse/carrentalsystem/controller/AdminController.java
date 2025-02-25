package lk.ijse.gdse.carrentalsystem.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import lk.ijse.gdse.carrentalsystem.bo.custom.AdminBO;
import lk.ijse.gdse.carrentalsystem.bo.BOFactory;
import lk.ijse.gdse.carrentalsystem.dto.AdminDto;
import lk.ijse.gdse.carrentalsystem.dto.tm.AdminTM;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable {


    public JFXButton btnReset;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public JFXButton btnUpdate;
    @FXML
    private ImageView gifImageView;

    @FXML
    private Label lblAdminID;

    @FXML
    private Label lblAdminName;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblPassword;

    @FXML
    private TextField txtAdminID;

    @FXML
    private TextField txtAdminName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPassword;
    @FXML
    private JFXButton btnSearch;

    AdminBO adminBO= (AdminBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ADMIN);

    @FXML
    void btnDeleteOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String adminId = txtAdminID.getText();

        if (adminId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter an Admin ID to delete!").show();
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this admin?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> optionalButtonType = confirmationAlert.showAndWait();

        if (optionalButtonType.isPresent() && optionalButtonType.get() == ButtonType.YES) {
            try {
                boolean isDeleted = adminBO.delete(adminId);

                if (isDeleted) {
                    new Alert(Alert.AlertType.INFORMATION, "Admin deleted successfully!").show();
                    clearField();         // Clear fields after deletion
                    refreshTableData();    // Refresh table to remove deleted record
                    loadNextAdminId();     // Reset ID field to next available ID

                } else {
                    new Alert(Alert.AlertType.ERROR, "Failed to delete admin!").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "SQL error occurred while deleting admin: " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "Database connection error: " + e.getMessage()).show();
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
            }
        }


    }
    private void refreshTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AdminDto> adminDtos= adminBO.getAll();
        ObservableList<AdminTM> adminTMS=FXCollections.observableArrayList();
        for(AdminDto dto:adminDtos){
            AdminTM adminTM=new AdminTM(
                    dto.getAdmin_id(),
                    dto.getUserName(),
                    dto.getEmail(),
                    dto.getPassword()
            );
            adminTMS.add(adminTM);

        }
        tblAdmin.setItems(adminTMS);

    }


    private void clearField() {
        txtAdminID.setText("");
        txtAdminName.setText("");
        txtEmail.setText("");
        txtPassword.setText("");

    }



    @FXML
    void btnSearchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String adminId = txtAdminID.getText();

        if (adminId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter an Admin ID to search!").show();
            return;
        }

        try {
            AdminDto admin = adminBO.search(adminId);

            if (admin != null) {
                txtAdminID.setText(admin.getAdmin_id());
                txtAdminName.setText(admin.getUserName());
                txtEmail.setText(admin.getEmail());
                txtPassword.setText(admin.getPassword());
                new Alert(Alert.AlertType.INFORMATION, "Admin found!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "Admin not found!").show();
                loadNextAdminId();
                clearField();
            }

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL error occurred while searching for Admin: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Database connection error: " + e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
        }

    }


    @FXML
    void btnResetOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
        loadNextAdminId();


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String adminId = txtAdminID.getText();
        String userName = txtAdminName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        // Regex patterns
        String adminIdPattern = "^A\\d{3}$"; // Matches A001, A002, etc.
        String userNamePattern = "^[A-Za-z ]+$"; // Allows letters and spaces only
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; // Valid email
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"; // At least 8 characters, at least 1 letter and 1 number

        // Reset field styles
        resetFieldStyles();

        // Validation checks
        boolean isValidAdminId = adminId.matches(adminIdPattern);
        boolean isValidUserName = userName.matches(userNamePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPassword = password.matches(passwordPattern);

        // Alert message for validation errors
        if (!isValidAdminId || !isValidUserName || !isValidEmail || !isValidPassword) {
            StringBuilder errorMessage = new StringBuilder("Please fix the following errors:\n");

            if (!isValidAdminId) {
                txtAdminID.setStyle("-fx-border-color: red;");
                errorMessage.append("- Invalid Admin ID (Expected format: A001)\n");
            }
            if (!isValidUserName) {
                txtAdminName.setStyle("-fx-border-color: red;");
                errorMessage.append("- Invalid User Name (Only letters and spaces allowed)\n");
            }
            if (!isValidEmail) {
                txtEmail.setStyle("-fx-border-color: red;");
                errorMessage.append("- Invalid Email format\n");
            }
            if (!isValidPassword) {
                txtPassword.setStyle("-fx-border-color: red;");
                errorMessage.append("- Invalid Password (Minimum 8 characters, at least 1 letter and 1 number)\n");
            }

            new Alert(Alert.AlertType.WARNING, errorMessage.toString()).show();
            return;
        }

        // If all fields are valid, attempt to save
        try {
            AdminDto adminDto = new AdminDto(adminId, userName, email, password);
            boolean isSaved = adminBO.save(adminDto);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Admin saved successfully!").show();
                clearField(); // Clear fields after successful save
                refreshPage(); // Refresh the page
                loadNextAdminId(); // Load the next available Admin ID
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save admin!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL error occurred while saving Admin: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Database connection error: " + e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
        }

    }


    private void resetFieldStyles() {
        txtAdminID.setStyle(""); // Reset to default
        txtAdminName.setStyle(""); // Reset to default
        txtEmail.setStyle(""); // Reset to default
        txtPassword.setStyle(""); // Reset to default
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String adminId = txtAdminID.getText();
        String userName = txtAdminName.getText();
        String email = txtEmail.getText();
        String password = txtPassword.getText();

        // Regex patterns
        String adminIdPattern = "^A\\d{3}$"; // Matches A001, A002, etc.
        String userNamePattern = "^[A-Za-z ]+$"; // Allows letters and spaces only
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; // Valid email
        String passwordPattern = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"; // At least 8 characters, at least 1 letter and 1 number

        // Reset field styles
        resetFieldStyles();

        // Validation checks
        boolean isValidAdminId = adminId.matches(adminIdPattern);
        boolean isValidUserName = userName.matches(userNamePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidPassword = password.matches(passwordPattern);


        if (!isValidAdminId || !isValidUserName || !isValidEmail || !isValidPassword) {
            StringBuilder errorMessage = new StringBuilder("Please fix the following errors:\n");

            if (!isValidAdminId) {
                txtAdminID.setStyle("-fx-border-color: red;");
                errorMessage.append("- Invalid Admin ID (Expected format: A001)\n");
            }
            if (!isValidUserName) {
                txtAdminName.setStyle("-fx-border-color: red;");
                errorMessage.append("- Invalid User Name (Only letters and spaces allowed)\n");
            }
            if (!isValidEmail) {
                txtEmail.setStyle("-fx-border-color: red;");
                errorMessage.append("- Invalid Email format\n");
            }
            if (!isValidPassword) {
                txtPassword.setStyle("-fx-border-color: red;");
                errorMessage.append("- Invalid Password (Minimum 8 characters, at least 1 letter and 1 number)\n");
            }

            new Alert(Alert.AlertType.WARNING, errorMessage.toString()).show();
            return;
        }

        // Attempt to update admin if validation passes
        try {
            AdminDto adminDto = new AdminDto(adminId, userName, email, password);
            boolean isUpdated = adminBO.update(adminDto);

            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Admin updated successfully!").show();
                refreshPage();         // Refresh the page content
                loadNextAdminId();      // Load the next available Admin ID
                refreshTableData();     // Refresh the table to reflect changes
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update admin!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL error while updating Admin: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Database connection error: " + e.getMessage()).show();
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage()).show();
        }

    }

    @FXML
    private TableColumn<AdminTM, String> colAdminID;

    @FXML
    private TableColumn<AdminTM, String> colAdminName;

    @FXML
    private TableColumn<AdminTM, String> colEmail;

    @FXML
    private TableColumn<AdminTM, String> colPassword;
    @FXML
    private TableView<AdminTM> tblAdmin;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colAdminID.setCellValueFactory(new PropertyValueFactory<>("Admin_id"));
        colAdminName.setCellValueFactory(new PropertyValueFactory<>("UserName"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colPassword.setCellValueFactory(new PropertyValueFactory<>("Password"));

        try {
            refreshPage();
            loadNextAdminId();
            refreshTableData();
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Database error occurred while loading admin data: " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Required class not found: " + e.getMessage()).show();
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Unexpected error occurred: " + e.getMessage()).show();
        }
    }
    private void refreshPage() throws SQLException, ClassNotFoundException {
        loadNextAdminId();
        loadTableData();
        btnSave.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        clearField();

    }
    private  void loadTableData() throws SQLException, ClassNotFoundException {
        ArrayList<AdminDto> adminDtos=adminBO.getAll();
        ObservableList<AdminTM> adminTMS=FXCollections.observableArrayList();
        for(AdminDto  adminDto:adminDtos){
            AdminTM adminTM=new AdminTM(
                    adminDto.getAdmin_id(),
                    adminDto.getUserName(),
                    adminDto.getEmail(),
                    adminDto.getPassword()
            );
            adminTMS.add(adminTM);


        }
        tblAdmin.setItems(adminTMS);

    }


    public void loadNextAdminId() throws SQLException, ClassNotFoundException {
        String nextAdminId = adminBO.getNextId();
        txtAdminID.setText(nextAdminId);

    }


    public void onClickTable(MouseEvent mouseEvent) {
        AdminTM adminTM=tblAdmin.getSelectionModel().getSelectedItem();
        if(adminTM!=null){
            txtAdminID.setText(adminTM.getAdmin_id());
            txtAdminName.setText(adminTM.getUserName());
            txtEmail.setText(adminTM.getEmail());
            txtPassword.setText(adminTM.getPassword());
            btnUpdate.setDisable(false);
            btnDelete.setDisable(false);
            btnSave.setDisable(true);

        }

    }


}
