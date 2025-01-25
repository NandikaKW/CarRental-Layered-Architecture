package lk.ijse.gdse.carrentalsystem.dao.custom;

import lk.ijse.gdse.carrentalsystem.dao.CrudDAO;
import lk.ijse.gdse.carrentalsystem.entity.Agriment;

import java.sql.SQLException;

public interface AgrimentDAO extends CrudDAO<Agriment> {
    String loadCurrentAgreementId() throws SQLException, ClassNotFoundException;

}
