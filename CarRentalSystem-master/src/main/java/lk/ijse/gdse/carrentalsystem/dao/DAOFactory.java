package lk.ijse.gdse.carrentalsystem.dao;

import lk.ijse.gdse.carrentalsystem.dao.custom.VehicleRentDetailDAO;
import lk.ijse.gdse.carrentalsystem.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){

    }
    public static DAOFactory getInstance(){
        return daoFactory==null?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        ADMIN,EMPLOYEE,PACKAGE,DAMAGE_DETAIL,MAINTAINANCE,QUERY,AGREEMENT,VEHICLE,VEHICLE_RENT,PAYMENT,RENT_PAYMENT,RENT,CUSTOMER,CUSTOMER_PAYMENT;
    }
    public SuperDAO getDAO(DAOTypes type) {
        switch (type) {
            case ADMIN:
                return  new AdminDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            case  PACKAGE:
                return new PackageDAOImpl();
            case DAMAGE_DETAIL:
                return new DamageDetailDAOImpl();
            case MAINTAINANCE:
                return new MaintainDAOImpl();
            case AGREEMENT:
                return new AgrimentDAOImpl();
            case VEHICLE:
                return new VehicleDAOImpl();
            case VEHICLE_RENT:
                return new VehicleRentDetailDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case RENT_PAYMENT:
                return new RentPayemntDAOImpl();
            case RENT:
                return new RentDAOImpl();
            case CUSTOMER:
                return new CustomerDAOImpl();
            case CUSTOMER_PAYMENT:
                return new CustomerPaymentDAOImpl();
                default:
                return null;
        }
    }
}
