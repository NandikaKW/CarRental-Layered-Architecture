package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.RentDAO;
import lk.ijse.gdse.carrentalsystem.db.DBConnection;
import lk.ijse.gdse.carrentalsystem.entity.Rent;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RentDAOImpl implements RentDAO {
    @Override
    public boolean save(Rent dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "INSERT INTO rent (rent_id, start_date, end_date, cust_id, agreement_id) VALUES (?,?,?,?,?)",
                dto.getRentId(), dto.getStartDate(), dto.getEndDate(), dto.getCustId(), dto.getAgreementId()
        );

    }

    @Override
    public boolean update(Rent dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Rent SET start_date=?, end_date=?, cust_id=?, agreement_id=? WHERE rent_id=?",
                dto.getStartDate(), dto.getEndDate(), dto.getCustId(), dto.getAgreementId(), dto.getRentId());

    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM rent WHERE rent_id = ?", Id);
    }

    @Override
    public Rent search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Rent WHERE rent_id = ?", id);
        if (rst.next()) {
            return new Rent(
                    rst.getString("rent_id"),
                    rst.getDate("start_date"),
                    rst.getDate("end_date"),
                    rst.getString("cust_id"),
                    rst.getString("agreement_id") // Added to retrieve the agreement ID
            );
        }
        return null;

    }

    @Override
    public ArrayList<Rent> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM rent");
        ArrayList<Rent> rents = new ArrayList<>();

        while (resultSet.next()) {
            rents.add(new Rent(
                    resultSet.getString("rent_id"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    resultSet.getString("cust_id"),
                    resultSet.getString("agreement_id")
            ));
        }
        return rents;


    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT rent_id FROM rent ORDER BY rent_id DESC LIMIT 1");

        if (rst.next()) {
            String lastID = rst.getString("rent_id");

            // Validate that the ID starts with "R" and has a numeric part
            if (lastID != null && lastID.startsWith("R") && lastID.length() > 1) {
                try {
                    // Extract the numeric part after "R"
                    String numericPart = lastID.substring(1);

                    // Parse the numeric part and increment it
                    int id = Integer.parseInt(numericPart);
                    int newId = id + 1;

                    // Format the new ID with "R" prefix and three-digit number
                    return String.format("R%03d", newId);
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing rent ID numeric part: " + e.getMessage());
                }
            }
        }

        // Default if no ID exists in the table or if parsing fails
        return "R001"; // Default ID if the table is empty or format is incorrect

    }

    @Override
    public boolean reserveVehicle(String vehicleId) {
        return false;
    }

    @Override
    public String getCurrentId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT rent_id from rent order by rent_id desc limit 1";
        try (Connection connection = DBConnection.getInstance().getConnection();
             ResultSet resultSet = connection.createStatement().executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;

    }

    @Override
    public ArrayList<String> getAllRentIds() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select rent_id from rent");

        // Create an ArrayList to store the item IDs
        ArrayList<String> rentIds = new ArrayList<>();

        // Iterate through the result set and add each item ID to the list
        while (rst.next()) {
            rentIds.add(rst.getString(1));
        }

        // Return the list of item IDs
        return rentIds;

    }

    @Override
    public String loadNextRentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT rent_id FROM rent ORDER BY rent_id DESC LIMIT 1");
        if (resultSet.next()){
            String lastID=resultSet.getString("rent_id");
            String substring=lastID.substring(1);
            int id=Integer.parseInt(substring);
            int newId=id+1;
            return String.format("R%03d",newId);
        }
        return "R001";
    }

    @Override
    public String loadCurrentRentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT rent_id FROM rent ORDER BY rent_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("rent_id");  // Return the most recent rent_id directly
        }

        return null;
    }
}
