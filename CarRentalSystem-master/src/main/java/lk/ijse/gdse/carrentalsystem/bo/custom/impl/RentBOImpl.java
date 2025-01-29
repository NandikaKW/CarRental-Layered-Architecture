package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.RentBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.PaymentDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.RentDAO;
import lk.ijse.gdse.carrentalsystem.dto.RentDto;
import lk.ijse.gdse.carrentalsystem.entity.Rent;

import java.sql.SQLException;
import java.util.ArrayList;

public class RentBOImpl implements RentBO {
    RentDAO rentDAO= (RentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RENT);
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
}
