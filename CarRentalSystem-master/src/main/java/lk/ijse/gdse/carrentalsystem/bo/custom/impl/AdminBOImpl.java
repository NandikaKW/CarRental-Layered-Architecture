package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.AdminBO;
import lk.ijse.gdse.carrentalsystem.dao.custom.AdminDAO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dto.AdminDto;
import lk.ijse.gdse.carrentalsystem.entity.Admin;

import java.sql.SQLException;
import java.util.ArrayList;

public class AdminBOImpl implements AdminBO {
    // DAO object for Admin
    AdminDAO adminDAO = (AdminDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOTypes.ADMIN);


    // Save Admin
    @Override
    public boolean save(AdminDto adminDto) throws SQLException {
        try {
            // Create a new Admin object from AdminDto
            Admin admin = new Admin(
                    adminDto.getAdmin_id(),
                    adminDto.getUserName(),
                    adminDto.getEmail(),
                    adminDto.getPassword()
            );

            // Save the admin using the adminDAO and return the result
            try {
                return adminDAO.save(admin);  // Return the result of the save method
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println("Error saving admin: " + e.getMessage());
            throw e; // Rethrow or handle exception as needed
        }

    }


    // Update Admin
    @Override
    public boolean update(AdminDto adminDto) throws SQLException {
        try {
            return adminDAO.update(new Admin(adminDto.getAdmin_id(), adminDto.getUserName(), adminDto.getEmail(), adminDto.getPassword()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    // Delete Admin by ID
    @Override
    public boolean delete(String adminId) throws SQLException {
        try {
            return adminDAO.delete(adminId);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    // Get all Admins
    @Override
    public ArrayList<AdminDto> getAll() throws SQLException {
        ArrayList<Admin> adminList = null;
        try {
            adminList = adminDAO.getAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<AdminDto> adminDtoList = new ArrayList<>();

        // Convert entity to DTO
        for (Admin admin : adminList) {
            adminDtoList.add(new AdminDto(
                    admin.getAdmin_id(),
                    admin.getUserName(),
                    admin.getEmail(),
                    admin.getPassword()
            ));
        }
        return adminDtoList;


    }

    // Get the next Admin ID
    @Override
    public String getNextId() throws SQLException {
        try {
            return adminDAO.getNextId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String loadCurrentId() throws SQLException {
        try {
            return adminDAO.loadCurrentAdminId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AdminDto search(String adminId) throws SQLException {
        AdminDto adminDto = null;
        try {
            // Call to adminDAO.search(adminId) returns an Admin object
            Admin admin = adminDAO.search(adminId);

            if (admin != null) {
                // Convert Admin object to AdminDto
                adminDto = new AdminDto(
                        admin.getAdmin_id(),      // Get admin_id from Admin object
                        admin.getUserName(),      // Get userName from Admin object
                        admin.getEmail(),         // Get email from Admin object
                        admin.getPassword()       // Get password from Admin object
                );
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return adminDto;  // Return the AdminDto object

    }

}
