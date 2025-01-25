package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.entity.Admin;

import java.sql.SQLException;

public interface AdminDAO extends CrudDAO<Admin> {
     String loadCurrentAdminId() throws SQLException, ClassNotFoundException;

}
