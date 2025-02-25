package lk.ijse.gdse.carrentalsystem.entity;

import java.util.Date;

public class VechileRentDetail {
    private String vehicle_id;
    private String rent_id;
    private Date start_date;
    private Date end_date;
    private int vehicle_quantity;
    private String vehicle_condition;

    public VechileRentDetail() {
    }

    public VechileRentDetail(String vehicle_id, String rent_id, Date start_date, Date end_date, int vehicle_quantity, String vehicle_condition) {
        this.vehicle_id = vehicle_id;
        this.rent_id = rent_id;
        this.start_date = start_date;
        this.end_date = end_date;
        this.vehicle_quantity = vehicle_quantity;
        this.vehicle_condition = vehicle_condition;
    }

    public VechileRentDetail(String vehicleId, int quantity) {
        this.vehicle_id = vehicleId;
        this.vehicle_quantity = quantity;
    }

    public String getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(String vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getRent_id() {
        return rent_id;
    }

    public void setRent_id(String rent_id) {
        this.rent_id = rent_id;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getVehicle_quantity() {
        return vehicle_quantity;
    }

    public void setVehicle_quantity(int vehicle_quantity) {
        this.vehicle_quantity = vehicle_quantity;
    }

    public String getVehicle_condition() {
        return vehicle_condition;
    }

    public void setVehicle_condition(String vehicle_condition) {
        this.vehicle_condition = vehicle_condition;
    }

    @Override
    public String toString() {
        return "VechileRentDetailDto{" +
                "vehicle_id='" + vehicle_id + '\'' +
                ", rent_id='" + rent_id + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", vehicle_quantity=" + vehicle_quantity +
                ", vehicle_condition='" + vehicle_condition + '\'' +
                '}';
    }
}
