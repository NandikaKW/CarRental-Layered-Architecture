package lk.ijse.gdse.carrentalsystem.bo.custom;

import lk.ijse.gdse.carrentalsystem.bo.SuperBO;
import lk.ijse.gdse.carrentalsystem.dto.MaintainDto;

import java.sql.SQLException;
import java.util.List;

public interface MaintainBO extends SuperBO {
    boolean saveMaintain(MaintainDto dto) throws SQLException;
    boolean updateMaintain(MaintainDto dto) throws SQLException;
    boolean deleteMaintain(String id) throws SQLException;
    MaintainDto searchMaintain(String id) throws SQLException;
    List<MaintainDto> getAllMaintains() throws SQLException;
    String getNextMaintainId() throws SQLException;

}
