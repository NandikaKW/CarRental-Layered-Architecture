package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.PackageDAO;
import lk.ijse.gdse.carrentalsystem.dto.PackageDto;
import lk.ijse.gdse.carrentalsystem.dto.PaymentDto;
import lk.ijse.gdse.carrentalsystem.entity.Package;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class PackageDAOImpl implements PackageDAO {


    @Override
    public boolean save(Package entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO package VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getPackageId(),
                entity.getPackageName(),
                entity.getTotalCost(),
                entity.isInsuranceIncluded(),
                entity.getRentalDuration(),
                entity.getRentDate(),
                entity.getMileageLimit(),
                entity.getDescription());
    }

    @Override
    public boolean update(Package entity) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "UPDATE package SET package_name = ?, total_cost = ?, insurance_included = ?, rental_duration = ?, rent_date = ?, mileage_limit = ?, description = ? WHERE package_id = ?",
                entity.getPackageName(),
                entity.getTotalCost(),
                entity.isInsuranceIncluded(),
                entity.getRentalDuration(),
                entity.getRentDate(),
                entity.getMileageLimit(),
                entity.getDescription(),
                entity.getPackageId()
        );
    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM package WHERE package_id = ?",Id);
    }

    @Override
    public Package search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM package WHERE package_id = ?", id);
        if (resultSet.next()) {
            return new Package(
                    resultSet.getString("package_id"),
                    resultSet.getString("package_name"),
                    resultSet.getBigDecimal("total_cost"),
                    resultSet.getBoolean("insurance_included"),
                    resultSet.getString("rental_duration"),
                    resultSet.getDate("rent_date"),
                    resultSet.getString("mileage_limit"),
                    resultSet.getString("description")
            );
        }
        return null;
    }

    @Override
    public ArrayList<Package> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM package");
        ArrayList<Package> packages = new ArrayList<>();
        while (resultSet.next()) {
            packages.add(new Package(
                    resultSet.getString("package_id"),
                    resultSet.getString("package_name"),
                    resultSet.getBigDecimal("total_cost"),
                    resultSet.getBoolean("insurance_included"),
                    resultSet.getString("rental_duration"),
                    resultSet.getDate("rent_date"),
                    resultSet.getString("mileage_limit"),
                    resultSet.getString("description")
            ));
        }
        return packages;
    }


    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst=CrudUtil.execute("SELECT package_id FROM package ORDER BY package_id DESC LIMIT 1");
        if(rst.next()){
            String lastId= rst.getString("package_id"); //P001=rst.getString("package_id");
            String substring=lastId.substring(1);
            int id=Integer.parseInt(substring);
            int newIndex=id+1;
            return String.format("P%03d",newIndex);

        }
        return "P001";
    }

    @Override
    public ArrayList<String> getAllPackageIds() throws SQLException, ClassNotFoundException {
        // Execute SQL query to get all item IDs
        ResultSet rst = CrudUtil.execute("select package_id from package");

        // Create an ArrayList to store the item IDs
        ArrayList<String> packageIds = new ArrayList<>();

        // Iterate through the result set and add each item ID to the list
        while (rst.next()) {
            packageIds.add(rst.getString(1));
        }

        // Return the list of item IDs
        return packageIds;
    }

    @Override
    public String loadCurrentPackageId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT package_id FROM package ORDER BY package_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("package_id");  // Return the most recent package_id directly
        }

        return null;  // Return null if there are no records in the package table

    }
    @Override
    public ArrayList<PackageDto> getAllPackages() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM package");

        ArrayList<PackageDto> packageList = new ArrayList<>();
        while (rst.next()) {
            packageList.add(new PackageDto(
                    rst.getString("package_id"),
                    rst.getString("package_name"),
                    rst.getBigDecimal("total_cost"), // Corrected to BigDecimal
                    rst.getBoolean("insurance_included"),
                    rst.getString("rental_duration"), // Changed from int to String
                    rst.getDate("rent_date"),
                    rst.getString("mileage_limit"), // Changed from int to String
                    rst.getString("description")
            ));
        }
        return packageList;
    }

}
