package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.AdminDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AdminBO extends SuperBO {
    boolean save(AdminDto adminDto) throws SQLException;;
    boolean update(AdminDto adminDto) throws SQLException;
    boolean delete(String adminId) throws SQLException;
    AdminDto search(String adminId) throws SQLException;
    ArrayList<AdminDto> getAll() throws SQLException;
    String getNextId() throws SQLException;
    String loadCurrentId() throws SQLException;
}
