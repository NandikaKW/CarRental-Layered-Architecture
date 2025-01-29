package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.RentPaymentBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.PaymentDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.RentPayemntDAO;
import lk.ijse.gdse.carrentalsystem.dto.RentPayemntDto;
import lk.ijse.gdse.carrentalsystem.entity.RentPayemnt;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RentPaymentBOImpl implements RentPaymentBO {
    RentPayemntDAO rentPayemntDAO= (RentPayemntDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RENT_PAYMENT);
    @Override
    public boolean saveRentPayment(RentPayemntDto dto) throws SQLException, ClassNotFoundException {
        RentPayemnt rentPayment = new RentPayemnt(
                dto.getRent_id(),
                dto.getPay_id(),
                dto.getPayment_date(),
                dto.getDuration(),
                dto.getDescription(),
                dto.getPay_amount(),
                dto.getPayment_method()
        );
        return rentPayemntDAO.save(rentPayment);
    }

    @Override
    public boolean updateRentPayment(RentPayemntDto dto) throws SQLException, ClassNotFoundException {
        RentPayemnt rentPayment = new RentPayemnt(
                dto.getRent_id(),
                dto.getPay_id(),
                dto.getPayment_date(),
                dto.getDuration(),
                dto.getDescription(),
                dto.getPay_amount(),
                dto.getPayment_method()
        );
        return rentPayemntDAO.update(rentPayment);
    }

    @Override
    public boolean deleteRentPayment(String rentId, String paymentId) throws SQLException, ClassNotFoundException {
        // Add logic if required to combine rentId and paymentId
        return rentPayemntDAO.deleteRentPayment(rentId, paymentId);
    }

    @Override
    public RentPayemntDto searchRentPayment(String rentId, String paymentId) throws SQLException, ClassNotFoundException {
        return rentPayemntDAO.searchRentPayment(rentId, paymentId);
    }

    @Override
    public List<RentPayemntDto> getAllRentPayments() throws SQLException, ClassNotFoundException {
        ArrayList<RentPayemnt> all = rentPayemntDAO.getAll();
        List<RentPayemntDto> dtos = new ArrayList<>();
        for (RentPayemnt rentPayemnt : all) {
            dtos.add(new RentPayemntDto(
                    rentPayemnt.getRent_id(),
                    rentPayemnt.getPay_id(),
                    rentPayemnt.getPayment_date(),
                    rentPayemnt.getDuration(),
                    rentPayemnt.getDescription(),
                    rentPayemnt.getPay_amount(),
                    rentPayemnt.getPayment_method()
            ));
        }
        return dtos;
    }


}
