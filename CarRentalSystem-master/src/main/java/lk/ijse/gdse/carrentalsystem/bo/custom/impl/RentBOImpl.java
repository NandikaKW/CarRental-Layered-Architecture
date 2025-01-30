package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.RentBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.PaymentDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.RentDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.VehicleRentDetailDAO;
import lk.ijse.gdse.carrentalsystem.db.DBConnection;
import lk.ijse.gdse.carrentalsystem.dto.RentDto;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;
import lk.ijse.gdse.carrentalsystem.entity.Rent;
import lk.ijse.gdse.carrentalsystem.entity.VechileRentDetail;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RentBOImpl implements RentBO {
    RentDAO rentDAO= (RentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RENT);
    VehicleRentDetailDAO vehicleRentDetailDAO= (VehicleRentDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.VEHICLE_RENT);
    @Override
    public boolean saveRent(RentDto rentDto) throws SQLException, ClassNotFoundException {
        Rent rent = new Rent(
                rentDto.getRentId(),
                rentDto.getStartDate(),
                rentDto.getEndDate(),
                rentDto.getCustId(),
                rentDto.getAgreementId()
        );
        try {
            return rentDAO.save(rent);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateRent(RentDto rentDto) throws SQLException, ClassNotFoundException {
        Rent rent = new Rent(
                rentDto.getRentId(),
                rentDto.getStartDate(),
                rentDto.getEndDate(),
                rentDto.getCustId(),
                rentDto.getAgreementId()
        );
        return rentDAO.update(rent);
    }

    @Override
    public boolean deleteRent(String rentId) throws SQLException, ClassNotFoundException {
        return rentDAO.delete(rentId);
    }

    @Override
    public RentDto searchRent(String rentId) throws SQLException, ClassNotFoundException {
        Rent rent = rentDAO.search(rentId);
        if (rent != null) {
            return new RentDto(
                    rent.getRentId(),
                    rent.getStartDate(),
                    rent.getEndDate(),
                    rent.getCustId(),
                    rent.getAgreementId()
            );
        }
        return null;
    }

    @Override
    public ArrayList<RentDto> getAllRents() throws SQLException, ClassNotFoundException {
        ArrayList<Rent> rentList = rentDAO.getAll();
        ArrayList<RentDto> rentDtos = new ArrayList<>();
        for (Rent rent : rentList) {
            rentDtos.add(new RentDto(
                    rent.getRentId(),
                    rent.getStartDate(),
                    rent.getEndDate(),
                    rent.getCustId(),
                    rent.getAgreementId()
            ));
        }
        return rentDtos;
    }

    @Override
    public String getNextRentId() throws SQLException, ClassNotFoundException {
        return rentDAO.getNextId();
    }

    @Override
    public ArrayList<String> getAllRentIds() throws SQLException, ClassNotFoundException {
        return rentDAO.getAllRentIds();
    }

    @Override
    public String loadCurrentRentId() throws SQLException {
        try {
            return rentDAO.loadCurrentRentId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean processRentTransaction(String rentId, String startDateStr, String endDateStr, String custId, String agreementId, ArrayList<VechileRentDetailDto> vehicleRentDetailDtos) throws SQLException, ClassNotFoundException, ParseException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false); // Start transaction

            // Parse dates from input strings
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            // Create RentDto object
            RentDto rentDto = new RentDto(rentId, startDate, endDate, custId, agreementId);

            // Save rent details
            boolean isRentSaved = saveRent(rentDto);

            if (isRentSaved) {
                // Save associated vehicle rent details
                // Convert VechileRentDetailDto list to VechileRentDetail list
                ArrayList<VechileRentDetail> vehicleRentDetails = new ArrayList<>();
                for (VechileRentDetailDto dto : vehicleRentDetailDtos) {
                    VechileRentDetail entity = new VechileRentDetail(
                            dto.getVehicle_id(),
                            dto.getRent_id(),
                            dto.getStart_date(),
                            dto.getEnd_date(),
                            dto.getVehicle_quantity(),
                            dto.getVehicle_condition());
                    vehicleRentDetails.add(entity);
                }

// Save associated vehicle rent details
                boolean isVehicleRentSaved = vehicleRentDetailDAO.saveVehicleRentList(vehicleRentDetails);


                if (isVehicleRentSaved) {
                    connection.commit(); // Commit transaction
                    return true;
                } else {
                    connection.rollback(); // Rollback transaction
                    return false;
                }
            } else {
                connection.rollback(); // Rollback transaction
                return false;
            }
        } catch (ParseException e) {
            throw new RuntimeException("Invalid date format! Use 'yyyy-MM-dd'.", e);
        } catch (SQLException | ClassNotFoundException e) {
            if (connection != null) {
                connection.rollback(); // Rollback transaction
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true); // Restore auto-commit
            }
        }
    }

}
