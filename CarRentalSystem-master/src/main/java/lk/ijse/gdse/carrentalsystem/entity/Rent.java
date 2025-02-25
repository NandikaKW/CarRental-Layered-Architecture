package lk.ijse.gdse.carrentalsystem.entity;

import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;

import java.util.ArrayList;
import java.util.Date;

public class Rent {
    private String rentId; // changed to camelCase
    private Date startDate; // changed to camelCase
    private Date endDate; // changed to camelCase
    private String custId; // changed to camelCase
    private String agreement_id; // added agreementId field
    private ArrayList<VechileRentDetailDto> vehicleRentDetailDtos;

    // Constructor including vehicleRentDetailDtos
    public Rent(String rentId, Date startDate, Date endDate, String custId, String agreement_id, ArrayList<VechileRentDetailDto> vehicleRentDetailDtos) {
        this.rentId = rentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.custId = custId;
        this.agreement_id = agreement_id; // initializing the new field
        this.vehicleRentDetailDtos = vehicleRentDetailDtos;
    }

    // Constructor without vehicleRentDetailDtos for flexibility
    public Rent(String rentId, Date startDate, Date endDate, String custId, String agreement_id) {
        this.rentId = rentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.custId = custId;
        this.agreement_id = agreement_id;
        this.vehicleRentDetailDtos = new ArrayList<>(); // initializing empty list by default
    }

    public ArrayList<VechileRentDetailDto> getVehicleRentDetailDtos() {
        return vehicleRentDetailDtos;
    }

    public void setVehicleRentDetailDtos(ArrayList<VechileRentDetailDto> vehicleRentDetailDtos) {
        this.vehicleRentDetailDtos = vehicleRentDetailDtos;
    }

    public String getRentId() {
        return rentId;
    }

    public void setRentId(String rentId) {
        this.rentId = rentId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getAgreementId() { // added getter for agreementId
        return agreement_id;
    }

    public void setAgreementId(String agreement_id) { // added setter for agreementId
        this.agreement_id = agreement_id;
    }

    @Override
    public String toString() {
        return "RentDto{" +
                "rentId='" + rentId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", custId='" + custId + '\'' +
                ", agreementId='" + agreement_id + '\'' +
                ", vehicleRentDetailDtos=" + vehicleRentDetailDtos +
                '}';
    }
}
