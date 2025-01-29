package lk.ijse.gdse.carrentalsystem.bo;

import lk.ijse.gdse.carrentalsystem.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes {
        ADMIN, EMPLOYEE, PACKAGE, DAMAGE_DETAIL, MAINTAINANCE,AGRIMENT,VEHICLE,VEHICLE_RENT,PAYMENT,RENT_PAYMENT,RENT,CUSTOMER,CUSTOMER_PAYMENT;
    }

    public SuperBO getBO(BOTypes type) {
        switch (type) {
            case ADMIN:
                return new AdminBOImpl();
            case EMPLOYEE:
                return new EmployeeBOImpl();
            case PACKAGE:
                return new PackageBOImpl();
            case DAMAGE_DETAIL:
                return new DamageDetailBOImpl();
            case MAINTAINANCE:
                return new MaintainBOImpl();
            case AGRIMENT:
                return new AgrimentBOImpl();
            case VEHICLE:
                return new VehicleBOImpl();
            case VEHICLE_RENT:
                return new VehicleRentDetailBOImpl();
            case PAYMENT:
                return new PaymentBOImpl();
            case RENT_PAYMENT:
                return new RentPaymentBOImpl();
            case RENT:
                return new RentBOImpl();
            case CUSTOMER:
                return new CustomerBOImpl();
            case CUSTOMER_PAYMENT:
                return new CustomerPaymentBOImpl();
            default:
                return null;
        }
    }
}
