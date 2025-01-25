package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.PaymentBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.PackageDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.PaymentDAO;
import lk.ijse.gdse.carrentalsystem.dto.PaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.Payment;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO= (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);
    @Override
    public boolean savePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(
                dto.getPay_id(),
                dto.getAmount(),
                new java.sql.Date(dto.getDate().getTime()), // Convert to SQL Date
                dto.getInvoice(),
                dto.getMethod(),
                dto.getTransaction_reference(),
                dto.getTax(),
                dto.getDiscount_applied()
        ));
    }

    @Override
    public boolean updatePayment(PaymentDto dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.update(new Payment(
                dto.getPay_id(),
                dto.getAmount(),
                new java.sql.Date(dto.getDate().getTime()), // Convert to SQL Date
                dto.getInvoice(),
                dto.getMethod(),
                dto.getTransaction_reference(),
                dto.getTax(),
                dto.getDiscount_applied()
        ));
    }

    @Override
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(id);
    }

    @Override
    public PaymentDto searchPayment(String id) throws SQLException, ClassNotFoundException {
        Payment payment = paymentDAO.search(id);
        if (payment != null) {
            return new PaymentDto(
                    payment.getPay_id(),
                    payment.getAmount(),
                    new Date(payment.getDate().getTime()), // Convert to Util Date
                    payment.getInvoice(),
                    payment.getMethod(),
                    payment.getTransaction_reference(),
                    payment.getTax(),
                    payment.getDiscount_applied()
            );
        }
        return null;
    }

    @Override
    public ArrayList<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<Payment> payments = paymentDAO.getAll();
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        for (Payment payment : payments) {
            paymentDtos.add(new PaymentDto(
                    payment.getPay_id(),
                    payment.getAmount(),
                    new Date(payment.getDate().getTime()), // Convert to Util Date
                    payment.getInvoice(),
                    payment.getMethod(),
                    payment.getTransaction_reference(),
                    payment.getTax(),
                    payment.getDiscount_applied()
            ));
        }
        return paymentDtos;
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }



    @Override
    public BigDecimal getAvailablePaymentAmount(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.getAvailablePaymentAmount(paymentId);
    }
}
