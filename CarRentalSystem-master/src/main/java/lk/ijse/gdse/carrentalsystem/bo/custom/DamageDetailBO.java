package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.DamageDto;
import lk.ijse.gdse.carrentalsystem.dto.EmployeeDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DamageDetailBO extends SuperBO {
    boolean save(DamageDto damageDto) throws SQLException;;
    boolean update(DamageDto damageDto) throws SQLException;
    boolean delete(String id) throws SQLException;
    DamageDto search(String id) throws SQLException;
    ArrayList<DamageDto> getAll() throws SQLException;
    String getNextId() throws SQLException;

}
