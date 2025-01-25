package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.VehicleBO;
import lk.ijse.gdse.carrentalsystem.dao.custom.VehicleDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.VehicleRentDetailDAO;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;
import lk.ijse.gdse.carrentalsystem.entity.VechileRentDetail;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static lk.ijse.gdse.carrentalsystem.model.VehicleRentDetailModel.vehicleModel;

public class VehicleRentDetailDAOImpl implements VehicleRentDetailDAO {
    VechileRentDetail VechileRentDetail=new VechileRentDetail();
    @Override
    public boolean save(VechileRentDetail dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO vehicle_rent_details VALUES (?,?,?,?,?,?)",
                dto.getVehicle_id(),
                dto.getRent_id(),
                dto.getStart_date(),
                dto.getEnd_date(),
                dto.getVehicle_quantity(),
                dto.getVehicle_condition()
        );


    }

    @Override
    public boolean update(VechileRentDetail dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public VechileRentDetail search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }



    @Override
    public ArrayList<VechileRentDetail> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =CrudUtil.execute("SELECT * FROM vehicle_rent_details");
        ArrayList<VechileRentDetail> vechileRentDetails=new ArrayList<>();
        while(resultSet.next()){
           vechileRentDetails.add(new VechileRentDetail(
                   resultSet.getString("vehicle_id"),
                   resultSet.getString("rent_id"),
                   resultSet.getDate("start_date"),
                   resultSet.getDate("end_date"),
                   resultSet.getInt("quantity"),
                   resultSet.getString("vehicle_condition")
           ));


        }
        return vechileRentDetails;

    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT vehicle_id FROM vehicle ORDER BY vehicle_id DESC LIMIT 1");
        if (resultSet.next()){
            String lastID=resultSet.getString("vehicle_id");
            String substring=lastID.substring(1);
            int id=Integer.parseInt(substring);
            int newId=id+1;
            return String.format("V%03d",newId);
        }
        return "V001";

    }

    @Override
    public boolean isVehicleRentUpdated(VechileRentDetailDto vechileRentDetailDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE vehicle_rent_details SET start_date=?, end_date=?, quantity=?, vehicle_condition=? WHERE vehicle_id=? AND rent_id=?",
                vechileRentDetailDto.getStart_date(),
                vechileRentDetailDto.getEnd_date(),
                vechileRentDetailDto.getVehicle_quantity(),
                vechileRentDetailDto.getVehicle_condition(),
                vechileRentDetailDto.getVehicle_id(),
                vechileRentDetailDto.getRent_id()
        );

    }

    @Override
    public boolean saveVehicleRentList(ArrayList<VechileRentDetailDto> vechileRentDetailDtos) throws SQLException, ClassNotFoundException {
        for (VechileRentDetailDto vechileRentDetailDto : vechileRentDetailDtos) {

            // Save individual vehicle rent detail
            boolean isVehicleRentSaved = save(VechileRentDetail);
            if (!isVehicleRentSaved) {
                return false;  // If saving fails, return false to trigger rollback
            }

            // Update vehicle quantity after saving rent detail
            boolean isVehicleUpdated = vehicleModel.reduceVehicleQuantity(vechileRentDetailDto);
            if (!isVehicleUpdated) {
                return false;  // If update fails, return false to trigger rollback
            }
        }
        return true;

    }

    @Override
    public boolean deleteVehicleRent(String rentId, String vehicleId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM vehicle_rent_details WHERE rent_id = ? AND vehicle_id = ?", rentId, vehicleId);

    }

    @Override
    public VechileRentDetailDto searchVehicleRent(String vehicleId, String rentId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM vehicle_rent_details WHERE vehicle_id = ? AND rent_id = ?", vehicleId, rentId);
        if (resultSet.next()) {
            return new VechileRentDetailDto(
                    resultSet.getString("vehicle_id"),
                    resultSet.getString("rent_id"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    resultSet.getInt("quantity"),
                    resultSet.getString("vehicle_condition")
            );
        }
        return null;

    }

    @Override
    public String loadCurrentRentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT rent_id FROM rent ORDER BY rent_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("rent_id");  // Return the most recent rent_id directly
        }

        return null;
    }
    @Override
    public String loadCurrentVehicleId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT vehicle_id FROM vehicle ORDER BY vehicle_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("vehicle_id");  // Return the most recent vehicle_id directly
        }

        return null;  //
    }
}
