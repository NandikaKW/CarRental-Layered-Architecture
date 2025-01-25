package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.VehicleRentDetailBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.VehicleRentDetailDAO;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;
import lk.ijse.gdse.carrentalsystem.entity.VechileRentDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleRentDetailBOImpl implements VehicleRentDetailBO {
    VehicleRentDetailDAO vehicleRentDetailDAO= (VehicleRentDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.VEHICLE_RENT);
    @Override
    public boolean saveVehicleRentList(ArrayList<VechileRentDetailDto> vechileRentDetailDtos) throws SQLException, ClassNotFoundException {

        try {
            return vehicleRentDetailDAO.saveVehicleRentList(vechileRentDetailDtos);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isVehicleRentUpdated(VechileRentDetailDto vechileRentDetailDto) throws SQLException, ClassNotFoundException {

        return vehicleRentDetailDAO.isVehicleRentUpdated(vechileRentDetailDto);
    }

    @Override
    public boolean deleteVehicleRent(String rentId, String vehicleId) throws SQLException, ClassNotFoundException {

        return vehicleRentDetailDAO.deleteVehicleRent(rentId, vehicleId);
    }

    @Override
    public VechileRentDetailDto searchVehicleRent(String vehicleId, String rentId) throws SQLException, ClassNotFoundException {

        return vehicleRentDetailDAO.searchVehicleRent(vehicleId, rentId);
    }

    @Override
    public ArrayList<VechileRentDetailDto> getAllVehicleRentDetails() throws SQLException, ClassNotFoundException {

        ArrayList<VechileRentDetail> vechileRentDetails = vehicleRentDetailDAO.getAll();


        ArrayList<VechileRentDetailDto> vechileRentDetailDtos = new ArrayList<>();


        for (VechileRentDetail vechileRentDetail : vechileRentDetails) {
            VechileRentDetailDto dto = new VechileRentDetailDto(
                    vechileRentDetail.getVehicle_id(),
                    vechileRentDetail.getRent_id(),
                    vechileRentDetail.getStart_date(),
                    vechileRentDetail.getEnd_date(),
                    vechileRentDetail.getVehicle_quantity(),
                    vechileRentDetail.getVehicle_condition()
            );
            vechileRentDetailDtos.add(dto);
        }


        return vechileRentDetailDtos;
    }
    @Override
    public String getNextRentId() throws SQLException, ClassNotFoundException {

        return vehicleRentDetailDAO.getNextId();
    }
    @Override
    public String loadCurrentRentId() throws SQLException, ClassNotFoundException {
        return vehicleRentDetailDAO.loadCurrentRentId();
    }
    @Override
    public String loadCurrentVehicleId() throws SQLException, ClassNotFoundException {
        return vehicleRentDetailDAO.loadCurrentVehicleId();
    }



}
