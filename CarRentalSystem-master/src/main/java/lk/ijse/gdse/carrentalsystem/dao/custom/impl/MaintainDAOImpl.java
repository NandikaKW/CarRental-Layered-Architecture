package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.MaintainDAO;
import lk.ijse.gdse.carrentalsystem.dto.PaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.Maintain;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MaintainDAOImpl implements MaintainDAO {

    @Override
    public boolean save(Maintain dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO maintenance VALUES(?,?,?,?,?,?)",dto.getMaintain_id(),dto.getCost(),dto.getMaintain_date(),dto.getDescription(),dto.getDuration(),dto.getVehicle_id());

    }

    @Override
    public boolean update(Maintain dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE maintenance SET cost=?,maintain_date=?,description=?,duration=?,vehicle_id=? WHERE maintain_id=?",dto.getCost(),dto.getMaintain_date(),dto.getDescription(),dto.getDuration(),dto.getVehicle_id(),dto.getMaintain_id());

    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM maintenance WHERE maintain_id = ?", Id);
    }

    @Override
    public Maintain search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM maintenance WHERE maintain_id = ?", id);
        if(resultSet.next()){
            return new Maintain(
                    resultSet.getString("maintain_id"),
                    resultSet.getBigDecimal("cost"),
                    resultSet.getDate("maintain_date"),
                    resultSet.getString("description"),
                    resultSet.getString("duration"),
                    resultSet.getString("vehicle_id")
            );


        }
        return null;

    }

    @Override
    public ArrayList<Maintain> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM maintenance");
        ArrayList<Maintain> maintain=new ArrayList<>();
        while(resultSet.next()){
            maintain.add(new Maintain(
                    resultSet.getString("maintain_id"),
                    resultSet.getBigDecimal("cost"),
                    resultSet.getDate("maintain_date"),
                    resultSet.getString("description"),
                    resultSet.getString("duration"),
                    resultSet.getString("vehicle_id")

            ));

        }
        return maintain;

    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT maintain_id FROM maintenance ORDER BY maintain_id DESC LIMIT 1");
        if (resultSet.next()){
            String lastID=resultSet.getString("maintain_id");
            String substring=lastID.substring(1);
            int id= Integer.parseInt(substring);
            int newId=id+1;
            return String.format("M%03d",newId);

        }
        return "M001";


    }
    @Override
    public String loadNextVehicleId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT vehicle_id FROM vehicle ORDER BY vehicle_id DESC LIMIT 1");
        if (resultSet.next()) {
            String lastID = resultSet.getString("vehicle_id");
            String substring = lastID.substring(1);
            int id = Integer.parseInt(substring);
            int newId = id + 1;
            return String.format("V%03d", newId);
        }
        return "V001";

    }

    @Override
    public String loadCurrentVehicleId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT vehicle_id FROM vehicle ORDER BY vehicle_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("vehicle_id");  // Return the most recent vehicle_id directly
        }

        return null;  // Return null if there are no records in the vehicle table

    }
}
