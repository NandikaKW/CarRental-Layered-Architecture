package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.CustomerPaymentBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.CustomerDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.CustomerPaymentDAO;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.CustomerPayment;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerPaymentBOImpl implements CustomerPaymentBO {

    CustomerPaymentDAO customerPaymentDAO = (CustomerPaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER_PAYMENT);

    @Override
    public boolean save(CustomerPaymentDto dto) throws SQLException, ClassNotFoundException {
        // Convert DTO to Entity and wrap it in a list
//        ArrayList<CustomerPaymentDto> customerPaymentDtos = new ArrayList<>();
//        customerPaymentDtos.add(dto);
//
//        // Call the DAO save method
//        try {
//            return customerPaymentDAO.saveCustomerPaymentList(customerPaymentDtos);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        CustomerPayment customerPayment = new CustomerPayment(
                dto.getCust_id(),
                dto.getPay_id(),
                dto.getPayment_date(),
                dto.getAmount()
        );

        // Call the DAO save method for a single entity
        try {
            return customerPaymentDAO.saveCustomerPayment(customerPayment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean update(CustomerPaymentDto dto) throws SQLException, ClassNotFoundException {
        // Convert DTO to Entity
        CustomerPayment entity = new CustomerPayment(
                dto.getCust_id(),
                dto.getPay_id(),
                dto.getPayment_date(),
                dto.getAmount()
        );
        // Call the DAO update method
        return customerPaymentDAO.update(entity);
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        // Call the DAO delete method
        return customerPaymentDAO.delete(id);
    }

    @Override
    public CustomerPaymentDto search(String id) throws SQLException, ClassNotFoundException {
        // Call the DAO search method
        CustomerPayment entity = customerPaymentDAO.search(id);
        if (entity != null) {
            return new CustomerPaymentDto(
                    entity.getCust_id(),
                    entity.getPay_id(),
                    entity.getPayment_date(),
                    entity.getAmount()
            );
        }
        return null;
    }

    @Override
    public ArrayList<CustomerPaymentDto> getAll() throws SQLException, ClassNotFoundException {
        // Call the DAO getAll method
        ArrayList<CustomerPayment> entities = customerPaymentDAO.getAll();
        ArrayList<CustomerPaymentDto> dtos = new ArrayList<>();
        for (CustomerPayment entity : entities) {
            dtos.add(new CustomerPaymentDto(
                    entity.getCust_id(),
                    entity.getPay_id(),
                    entity.getPayment_date(),
                    entity.getAmount()
            ));
        }
        return dtos;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        // Call the DAO getNextId method
        return customerPaymentDAO.getNextId();
    }

    @Override
    public boolean saveCustomerPaymentList(ArrayList<CustomerPaymentDto> customerPaymentDtos) {
//        System.out.println("saveCustomerPaymentList BOO");
//        try {
//            try {
//
//                return customerPaymentDAO.saveCustomerPaymentList(customerPaymentDtos);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
        try {
            // Convert DTOs to Entities
            ArrayList<CustomerPayment> customerPayments = new ArrayList<>();
            for (CustomerPaymentDto dto : customerPaymentDtos) {
                CustomerPayment entity = new CustomerPayment(
                        dto.getCust_id(),
                        dto.getPay_id(),
                        dto.getPayment_date(),
                        dto.getAmount()
                );
                customerPayments.add(entity);
            }

            // Pass the converted entity list to the DAO layer
            return customerPaymentDAO.saveCustomerPaymentList(customerPayments);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


}
