package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.VehicleBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.VehicleDAO;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;
import lk.ijse.gdse.carrentalsystem.dto.VehicleDto;
import lk.ijse.gdse.carrentalsystem.entity.Rent;
import lk.ijse.gdse.carrentalsystem.entity.VechileRentDetail;
import lk.ijse.gdse.carrentalsystem.entity.Vehicle;

import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleBOImpl implements VehicleBO {

     VehicleDAO vehicleDAO = (VehicleDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.VEHICLE);

    @Override
    public boolean saveVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle(
                dto.getVehicle_id(),
                dto.getModel(),
                dto.getColour(),
                dto.getCategory(),
                dto.getQuantity(),
                dto.getPackage_id()
        );
        return vehicleDAO.save(vehicle);
    }

    @Override
    public boolean updateVehicle(VehicleDto dto) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = new Vehicle(
                dto.getVehicle_id(),
                dto.getModel(),
                dto.getColour(),
                dto.getCategory(),
                dto.getQuantity(),
                dto.getPackage_id()
        );
        return vehicleDAO.update(vehicle);
    }

    @Override
    public boolean deleteVehicle(String vehicleId) throws SQLException, ClassNotFoundException {
        return vehicleDAO.delete(vehicleId);
    }

    @Override
    public VehicleDto searchVehicle(String vehicleId) throws SQLException, ClassNotFoundException {
        Vehicle vehicle = vehicleDAO.search(vehicleId);
        if (vehicle != null) {
            return new VehicleDto(
                    vehicle.getVehicle_id(),
                    vehicle.getModel(),
                    vehicle.getColour(),
                    vehicle.getCategory(),
                    vehicle.getQuantity(),
                    vehicle.getPackage_id()
            );
        }
        return null;
    }

    @Override
    public ArrayList<VehicleDto> getAllVehicles() throws SQLException, ClassNotFoundException {
        ArrayList<Vehicle> vehicles = vehicleDAO.getAll();
        ArrayList<VehicleDto> vehicleDtos = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            vehicleDtos.add(new VehicleDto(
                    vehicle.getVehicle_id(),
                    vehicle.getModel(),
                    vehicle.getColour(),
                    vehicle.getCategory(),
                    vehicle.getQuantity(),
                    vehicle.getPackage_id()
            ));
        }
        return vehicleDtos;
    }

    @Override
    public String getNextVehicleId() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getNextId();
    }



    @Override
    public boolean reduceVehicleQuantity(VehicleDto vehicleDto) throws SQLException, ClassNotFoundException {

//        VechileRentDetailDto rentDetailDto = new VechileRentDetailDto(
//                vehicleDto.getVehicle_id(),
//                vehicleDto.getQuantity() // Assuming vehicleDto.getQuantity() contains the quantity to reduce
//        );
        VechileRentDetail vechileRentDetail=new VechileRentDetail(
                vehicleDto.getVehicle_id(),
                vehicleDto.getQuantity()
        );


      //  return vehicleDAO.reduceVehicleQuantity(rentDetailDto);
        return vehicleDAO.reduceVehicleQuantity(vechileRentDetail);
    }


    @Override
    public ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException {
        return vehicleDAO.getAllVehicleIds();
    }


    @Override
    public String loadNextVehicleId() throws SQLException, ClassNotFoundException {
        return vehicleDAO.loadNextVehicleId();
    }

    @Override
    public String loadCurrentVehicleId() throws SQLException, ClassNotFoundException {
        return vehicleDAO.loadCurrentVehicleId();
    }


}
