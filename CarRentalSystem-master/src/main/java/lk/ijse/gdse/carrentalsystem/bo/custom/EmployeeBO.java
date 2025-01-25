package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    boolean save(EmployeeDto employee) throws SQLException;;
    boolean update(EmployeeDto employee) throws SQLException;
    boolean delete(String employeeId) throws SQLException;
    EmployeeDto search(String employeeId) throws SQLException;
    ArrayList<EmployeeDto> getAll() throws SQLException;
    String getNextId() throws SQLException;

}
