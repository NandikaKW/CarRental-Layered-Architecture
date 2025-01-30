package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.VehicleDAO;
import lk.ijse.gdse.carrentalsystem.dto.VechileRentDetailDto;
import lk.ijse.gdse.carrentalsystem.entity.VechileRentDetail;
import lk.ijse.gdse.carrentalsystem.entity.Vehicle;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class VehicleDAOImpl implements VehicleDAO {
    @Override
    public boolean save(Vehicle dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO vehicle VALUES (?,?,?,?,?,?)",dto.getVehicle_id(),dto.getModel(),dto.getColour(),dto.getCategory(),dto.getQuantity(),dto.getPackage_id());

    }

    @Override
    public boolean update(Vehicle dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE vehicle SET model=?,colour=?,category=?,quantity=?,package_id=? WHERE vehicle_id=?",dto.getModel(),dto.getColour(),dto.getCategory(),dto.getQuantity(),dto.getPackage_id(),dto.getVehicle_id());

    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM vehicle WHERE vehicle_id = ?", Id);

    }

    @Override
    public Vehicle search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT * FROM vehicle WHERE vehicle_id = ?", id);
        if (resultSet.next()){
            return new Vehicle(
                    resultSet.getString("vehicle_id"),
                    resultSet.getString("model"),
                    resultSet.getString("colour"),
                    resultSet.getString("category"),
                    resultSet.getInt("quantity"),
                    resultSet.getString("package_id")

            );

        }
        return null;

    }

    @Override
    public ArrayList<Vehicle> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =CrudUtil.execute("SELECT * FROM vehicle");
        ArrayList<Vehicle> vehicles=new ArrayList<>();
        while (resultSet.next()){
          vehicles.add(new Vehicle(
                  resultSet.getString("vehicle_id"),
                  resultSet.getString("model"),
                  resultSet.getString("colour"),
                  resultSet.getString("category"),
                  resultSet.getInt("quantity"),
                  resultSet.getString("package_id")
          ));
        }
        return vehicles;

    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT vehicle_id FROM vehicle ORDER BY vehicle_id DESC LIMIT 1");
        if (resultSet.next()) {
            String lastID = resultSet.getString("vehicle_id");
            String substring = lastID.substring(1); // Get the numeric part
            int id = Integer.parseInt(substring);   // Convert it to an integer
            int newId = id + 1;                     // Increment by 1
            return String.format("V%03d", newId);   // Format as "V" followed by a 3-digit number
        }
        return "V001"; // Default if no vehicles are found

    }



//    @Override
//    public boolean reduceVehicleQuantity(VechileRentDetailDto vechileRentDetailDto) throws SQLException, ClassNotFoundException {
//        try {
//            // Execute the update statement to reduce quantity by 1
//            return CrudUtil.execute("UPDATE vehicle SET quantity = quantity - ? WHERE vehicle_id = ?", vechileRentDetailDto.getVehicle_quantity(), vechileRentDetailDto.getVehicle_id());
//        } catch (SQLException e) {
//            System.err.println("Error while reducing vehicle quantity for vehicle_id: " + vechileRentDetailDto.getVehicle_id());
//            e.printStackTrace();
//            return false;
//        } catch (ClassNotFoundException e) {
//            System.err.println("Database driver not found.");
//            e.printStackTrace();
//            return false;
//        }
//    }
@Override
public boolean reduceVehicleQuantity(VechileRentDetail vechileRentDetail) throws SQLException, ClassNotFoundException {
//    try {
//        // Execute the update statement to reduce quantity by 1
//        return CrudUtil.execute("UPDATE vehicle SET quantity = quantity - ? WHERE vehicle_id = ?",
//                vechileRentDetail.getVehicle_quantity(), vechileRentDetail.getVehicle_id());
//    } catch (SQLException e) {
//        System.err.println("Error while reducing vehicle quantity for vehicle_id: " + vechileRentDetail.getVehicle_id());
//        e.printStackTrace();
//        return false;
//    } catch (ClassNotFoundException e) {
//        System.err.println("Database driver not found.");
//        e.printStackTrace();
//        return false;
//    }
    return CrudUtil.execute("UPDATE vehicle SET quantity = quantity - ? WHERE vehicle_id = ? AND quantity >= ?",
            vechileRentDetail.getVehicle_quantity(), vechileRentDetail.getVehicle_id(), vechileRentDetail.getVehicle_quantity());
}


    @Override
    public ArrayList<String> getAllVehicleIds() throws SQLException, ClassNotFoundException {
        // Execute SQL query to get all item IDs
        ResultSet rst = CrudUtil.execute("select vehicle_id from vehicle");

        // Create an ArrayList to store the item IDs
        ArrayList<String> vehicleIds = new ArrayList<>();

        // Iterate through the result set and add each item ID to the list
        while (rst.next()) {
            vehicleIds.add(rst.getString(1));
        }

        // Return the list of item IDs
        return vehicleIds;

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
