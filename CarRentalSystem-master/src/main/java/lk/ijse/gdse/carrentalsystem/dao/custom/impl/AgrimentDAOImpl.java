package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.AgrimentDAO;
import lk.ijse.gdse.carrentalsystem.entity.Agriment;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AgrimentDAOImpl implements AgrimentDAO {
    @Override
    public boolean save(Agriment dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO rental_agreement VALUES (?,?,?,?,?,?)",dto.getAgreement_id(),dto.getPayment_terms(),dto.getStart_date(),dto.getEnd_date(),dto.getDeposit_amount(),dto.getTotal_rent_cost());

    }

    @Override
    public boolean update(Agriment dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE rental_agreement SET payment_terms=?,start_date=?,end_date=?,deposit_amount=?,total_rent_cost=? WHERE agreement_id=?",dto.getPayment_terms(),dto.getStart_date(),dto.getEnd_date(),dto.getDeposit_amount(),dto.getTotal_rent_cost(),dto.getAgreement_id());

    }

    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM  rental_agreement WHERE agreement_id = ?", Id);

    }

    @Override
    public Agriment search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("Select * FROM rental_agreement WHERE agreement_id = ?",id);
        if (resultSet.next()){
            return new Agriment(
                    resultSet.getString("agreement_id"),
                    resultSet.getString("payment_terms"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    resultSet.getBigDecimal("deposit_amount"),
                    resultSet.getBigDecimal("total_rent_cost")
            );
        }
        return null;

    }

    @Override
    public ArrayList<Agriment> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM rental_agreement");
        ArrayList<Agriment> agriments = new ArrayList<>();

        while (resultSet.next()) {
            Agriment agriment = new Agriment(
                    resultSet.getString("agreement_id"),
                    resultSet.getString("payment_terms"),
                    resultSet.getDate("start_date"),
                    resultSet.getDate("end_date"),
                    resultSet.getBigDecimal("deposit_amount"),
                    resultSet.getBigDecimal("total_rent_cost")
            );
            agriments.add(agriment);  // Add each AgrimentDto to the list
        }

        return agriments;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT agreement_id FROM rental_agreement ORDER BY agreement_id DESC LIMIT 1");

        if (resultSet.next()) {
            String lastID = resultSet.getString("agreement_id");

            // Extract the numeric part after "AG"
            String numericPart = lastID.substring(2);

            // Parse the numeric part and increment
            int id = Integer.parseInt(numericPart);
            int newId = id + 1;

            // Format the new ID with "AG" prefix and three-digit number
            return String.format("AG%03d", newId);
        }

        // Default if no ID exists in the table
        return "AG001"; // Default ID in case the table is empty

    }

    @Override
    public String loadCurrentAgreementId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT agreement_id FROM rental_agreement ORDER BY agreement_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("agreement_id");  // Return the latest agreement ID directly
        }
        return null;  // Return null if no records are available

    }
}
