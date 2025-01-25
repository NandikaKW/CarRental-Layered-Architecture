package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.AgrimentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface AgrimentBO extends SuperBO {
    boolean saveAgriment(AgrimentDto dto) throws SQLException, ClassNotFoundException;
    boolean updateAgriment(AgrimentDto dto) throws SQLException, ClassNotFoundException;
    boolean deleteAgriment(String id) throws SQLException, ClassNotFoundException;
    AgrimentDto searchAgriment(String id) throws SQLException, ClassNotFoundException;
    ArrayList<AgrimentDto> getAllAgriments() throws SQLException, ClassNotFoundException;
    String getNextAgreementId() throws SQLException, ClassNotFoundException;
    String loadCurrentAgreementId() throws SQLException, ClassNotFoundException;


}
