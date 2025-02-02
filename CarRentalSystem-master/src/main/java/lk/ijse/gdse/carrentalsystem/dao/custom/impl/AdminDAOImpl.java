package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.AdminDAO;
import lk.ijse.gdse.carrentalsystem.entity.Admin;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class AdminDAOImpl implements AdminDAO {

    @Override
    public boolean save(Admin entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Admin (admin_id, username, email, password) VALUES (?, ?, ?, ?)",
                entity.getAdmin_id(),
                entity.getUserName(),
                entity.getEmail(),
                entity.getPassword());

    }
    @Override
    public boolean update(Admin admin) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Admin SET username = ?, email = ?, password = ? WHERE admin_id = ?",
                admin.getUserName(),
                admin.getEmail(),
                admin.getPassword(),
                admin.getAdmin_id());

    }
    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Admin WHERE admin_id = ?", Id);

    }

    @Override
    public Admin search(String adminId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Admin WHERE admin_id = ?", adminId);
        if (resultSet.next()) {
            return new Admin(
                    resultSet.getString("admin_id"),
                    resultSet.getString("username"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        }
        return null;

    }

    @Override
    public ArrayList<Admin> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM Admin");
        ArrayList<Admin> adminList = new ArrayList<>();
        while (resultSet.next()) {
            adminList.add(new Admin(
                    resultSet.getString("admin_id"),
                    resultSet.getString("username"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            ));
        }
        return adminList;




    }
    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT MAX(admin_id) FROM Admin");
        if (resultSet.next()) {
            String maxAdminId = resultSet.getString(1);
            if (maxAdminId != null) {
                int nextId = Integer.parseInt(maxAdminId.substring(1)) + 1;
                return "A" + String.format("%03d", nextId);
            } else {
                return "A001";
            }
        }
        return "A001";

    }

    @Override
    public String loadCurrentAdminId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT admin_id FROM Admin ORDER BY admin_id DESC LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getString("admin_id");
        }
        return null;
    }
}
