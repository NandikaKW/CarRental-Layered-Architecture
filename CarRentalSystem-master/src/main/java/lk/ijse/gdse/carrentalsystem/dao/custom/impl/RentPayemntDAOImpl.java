package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.RentPayemntDAO;
import lk.ijse.gdse.carrentalsystem.dto.RentPayemntDto;
import lk.ijse.gdse.carrentalsystem.entity.RentPayemnt;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentPayemntDAOImpl implements RentPayemntDAO {


    @Override
    public boolean save(RentPayemnt dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO rent_payment_details VALUES (?,?,?,?,?,?,?)",dto.getRent_id(),dto.getPay_id(),dto.getPayment_date(),dto.getDuration(),dto.getDescription(),dto.getPay_amount(),dto.getPayment_method());

    }

    @Override
    public boolean update(RentPayemnt dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE rent_payment_details SET payment_date=?,duration=?,description=?,pay_amount=?,payment_method=? WHERE rent_id=? AND pay_id=?",dto.getPayment_date(),dto.getDuration(),dto.getDescription(),dto.getPay_amount(),dto.getPayment_method(),dto.getRent_id(),dto.getPay_id());

    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public RentPayemnt search(String id) throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public ArrayList<RentPayemnt> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM rent_payment_details");
        ArrayList<RentPayemnt> rentPayemnts=new ArrayList<>();
        while(resultSet.next()){
           rentPayemnts.add(new RentPayemnt(
                   resultSet.getString("rent_id"),
                   resultSet.getString("pay_id"),
                   resultSet.getDate("payment_date"),
                   resultSet.getString("duration"),
                   resultSet.getString("description"),
                   resultSet.getBigDecimal("pay_amount"),
                   resultSet.getString("payment_method")

           ));
        }
        return rentPayemnts;

    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public String loadCurrentRentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT rent_id FROM rent ORDER BY rent_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("rent_id");  // Return the most recent rent ID directly
        }
        return null;
    }

    @Override
    public String loadCurrentPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT pay_id FROM payment ORDER BY pay_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("pay_id");
        }

        return null;

    }

    @Override
    public RentPayemntDto searchRentPayment(String rentId, String paymentId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM rent_payment_details WHERE rent_id = ? AND pay_id = ?", rentId, paymentId);
        if (resultSet.next()){
            return new RentPayemntDto(
                    resultSet.getString("rent_id"),
                    resultSet.getString("pay_id"),
                    resultSet.getDate("payment_date"),
                    resultSet.getString("duration"),
                    resultSet.getString("description"),
                    resultSet.getBigDecimal("pay_amount"),
                    resultSet.getString("payment_method")
            );

        }
        return null;

    }

    @Override
    public boolean deleteRentPayment(String rentId, String paymentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM rent_payment_details WHERE rent_id = ? AND pay_id = ?", rentId, paymentId);
    }
}
