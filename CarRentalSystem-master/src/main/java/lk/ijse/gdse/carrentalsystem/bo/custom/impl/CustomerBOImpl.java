package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.BOFactory;
import lk.ijse.gdse.carrentalsystem.bo.custom.CustomerBO;
import lk.ijse.gdse.carrentalsystem.bo.custom.PaymentBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.AdminDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.CustomerDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.CustomerPaymentDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.PaymentDAO;
import lk.ijse.gdse.carrentalsystem.db.DBConnection;
import lk.ijse.gdse.carrentalsystem.dto.CustomerDto;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.Customer;
import lk.ijse.gdse.carrentalsystem.entity.CustomerPayment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    //PaymentDAO paymentDAO = (PaymentDAO) BOFactory.getInstance().getBO(BOFactory.BOTypes.PAYMENT);
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PAYMENT);

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    CustomerPaymentDAO customerPaymentDAO = (CustomerPaymentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.CUSTOMER_PAYMENT);

    @Override
    public boolean save(CustomerDto dto) throws SQLException, ClassNotFoundException {
        // Convert DTO to Entity
        Customer customer = new Customer(
                dto.getCust_id(),
                dto.getCust_name(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getNic(),
                dto.getAdmin_id()
        );
        return customerDAO.save(customer);
    }

    @Override
    public boolean update(CustomerDto dto) throws SQLException, ClassNotFoundException {
        // Convert DTO to Entity
        Customer customer = new Customer(
                dto.getCust_id(),
                dto.getCust_name(),
                dto.getAddress(),
                dto.getEmail(),
                dto.getNic(),
                dto.getAdmin_id()
        );
        return customerDAO.update(customer);
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDto search(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        if (customer != null) {
            return new CustomerDto(
                    customer.getCust_id(),
                    customer.getCust_name(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getNic(),
                    customer.getAdmin_id()
            );
        }
        return null;
    }

    @Override
    public ArrayList<CustomerDto> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDto> customerDtos = new ArrayList<>();
        for (Customer customer : customers) {
            customerDtos.add(new CustomerDto(
                    customer.getCust_id(),
                    customer.getCust_name(),
                    customer.getAddress(),
                    customer.getEmail(),
                    customer.getNic(),
                    customer.getAdmin_id()
            ));
        }
        return customerDtos;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return customerDAO.getNextId();
    }

    @Override
    public String loadCurrentCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.loadCurrentCustomerId();
    }

    @Override
    public boolean isNicExists(String nic) throws SQLException, ClassNotFoundException {
        return customerDAO.isNicExists(nic);
    }
//    @Override
//    public boolean saveCustomerPayment(CustomerPaymentDto customerPaymentDto) throws SQLException, ClassNotFoundException {
//        return customerPaymentDAO.saveCustomerPayment(customerPaymentDto);
//    }
@Override
public boolean saveCustomerPayment(CustomerPaymentDto customerPaymentDto) throws SQLException, ClassNotFoundException {
    // Convert CustomerPaymentDto to CustomerPayment entity
    CustomerPayment customerPayment = new CustomerPayment(
            customerPaymentDto.getCust_id(),
            customerPaymentDto.getPay_id(),
            customerPaymentDto.getPayment_date(),
            customerPaymentDto.getAmount()
    );

    // Pass the entity to the DAO layer
    return customerPaymentDAO.saveCustomerPayment(customerPayment);
}
    public boolean processCustomerPayment(CustomerDto dto, CustomerPaymentDto customerPaymentDto) throws SQLException, ClassNotFoundException {
        Connection connection = null;
        try {
            connection = DBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);

            boolean isCustomerSaved = customerDAO.save(new Customer(
                    dto.getCust_id(),
                    dto.getCust_name(),
                    dto.getAddress(),
                    dto.getEmail(),
                    dto.getNic(),
                    dto.getAdmin_id()
            ));

            if (isCustomerSaved) {
                ArrayList<CustomerPaymentDto> customerPaymentDtos = new ArrayList<>();
                customerPaymentDtos.add(customerPaymentDto);

                boolean isCustomerPaymentSaved = customerPaymentDAO.saveCustomerPayment(new CustomerPayment(
                        customerPaymentDto.getCust_id(),
                        customerPaymentDto.getPay_id(),
                        customerPaymentDto.getPayment_date(),
                        customerPaymentDto.getAmount()
                ));

                if (isCustomerPaymentSaved) {
                    boolean isReducedPayment = paymentDAO.reducePaymentAmount(new CustomerPayment(
                            customerPaymentDto.getCust_id(),
                            customerPaymentDto.getPay_id(),
                            customerPaymentDto.getPayment_date(),
                            customerPaymentDto.getAmount()
                    ));

                    if (isReducedPayment) {
                        connection.commit();
                        return true;
                    } else {
                        connection.rollback();
                        return false;
                    }
                } else {
                    connection.rollback();
                    return false;
                }
            } else {
                connection.rollback();
                return false;
            }
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e;
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        }
    }




}
