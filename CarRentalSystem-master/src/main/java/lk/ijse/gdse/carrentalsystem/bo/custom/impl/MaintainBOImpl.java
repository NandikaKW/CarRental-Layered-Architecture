package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.MaintainBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.MaintainDAO;
import lk.ijse.gdse.carrentalsystem.dto.MaintainDto;
import lk.ijse.gdse.carrentalsystem.entity.Maintain;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaintainBOImpl implements MaintainBO {
    private final MaintainDAO maintainDAO = (MaintainDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.MAINTAINANCE);

    @Override
    public boolean saveMaintain(MaintainDto dto) throws SQLException {
        try {
            return maintainDAO.save(new Maintain(
                    dto.getMaintain_id(),
                    dto.getCost(),
                    dto.getMaintain_date(),
                    dto.getDescription(),
                    dto.getDuration(),
                    dto.getVehicle_id()
            ));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateMaintain(MaintainDto dto) throws SQLException {
        try {
            return maintainDAO.update(new Maintain(
                    dto.getMaintain_id(),
                    dto.getCost(),
                    dto.getMaintain_date(),
                    dto.getDescription(),
                    dto.getDuration(),
                    dto.getVehicle_id()
            ));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteMaintain(String id) throws SQLException {
        try {
            return maintainDAO.delete(id);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public MaintainDto searchMaintain(String id) throws SQLException {
        Maintain maintain = null;
        try {
            maintain = maintainDAO.search(id);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (maintain != null) {
            return new MaintainDto(
                    maintain.getMaintain_id(),
                    maintain.getCost(),
                    maintain.getMaintain_date(),
                    maintain.getDescription(),
                    maintain.getDuration(),
                    maintain.getVehicle_id()
            );
        }
        return null;
    }

    @Override
    public List<MaintainDto> getAllMaintains() throws SQLException {
        ArrayList<Maintain> maintains = null;
        try {
            maintains = maintainDAO.getAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        List<MaintainDto> dtoList = new ArrayList<>();
        for (Maintain maintain : maintains) {
            dtoList.add(new MaintainDto(
                    maintain.getMaintain_id(),
                    maintain.getCost(),
                    maintain.getMaintain_date(),
                    maintain.getDescription(),
                    maintain.getDuration(),
                    maintain.getVehicle_id()
            ));
        }
        return dtoList;
    }

    @Override
    public String getNextMaintainId() throws SQLException {
        try {
            return maintainDAO.getNextId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String loadNextVehicleId() throws SQLException, ClassNotFoundException {
        return maintainDAO.loadNextVehicleId();
    }

    @Override
    public String loadCurrentVehicleId() throws SQLException, ClassNotFoundException {
        return maintainDAO.loadCurrentVehicleId();
    }
}
