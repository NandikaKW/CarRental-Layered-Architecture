package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.DamageDetailDAO;
import lk.ijse.gdse.carrentalsystem.entity.Damage;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DamageDetailDAOImpl implements DamageDetailDAO {

    @Override
    public boolean save(Damage dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO damage VALUES(?,?,?,?)",dto.getDamage_id(),dto.getRepair_cost(),dto.getDetail(),dto.getRent_id());

    }

    @Override
    public boolean update(Damage dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE damage SET repair_cost=?,detail=?,rent_id=? WHERE damage_id=?",dto.getRepair_cost(),dto.getDetail(),dto.getRent_id(),dto.getDamage_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM damage WHERE damage_id = ?", id);
    }


    @Override
    public Damage search(String id) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM damage WHERE damage_id = ?", id);

        if (resultSet.next()) {
            System.out.println("Damage record found in database"); // Debugging line

            return new Damage(
                    resultSet.getString("damage_id"),
                    resultSet.getBigDecimal("repair_cost"),
                    resultSet.getString("detail"),
                    resultSet.getString("rent_id")
            );
        } else {
            System.out.println("No damage record found for Damage ID: " + id); // Debugging line
        }

        return null;

    }

    @Override
    public ArrayList<Damage> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet =CrudUtil.execute("SELECT * FROM damage");
        ArrayList<Damage> damageDtos=new ArrayList<>();
        while(resultSet.next()){
            Damage damageDto=new Damage(
                    resultSet.getString("damage_id"),
                    resultSet.getBigDecimal("repair_cost"),
                    resultSet.getString("detail"),
                    resultSet.getString("rent_id")
            );
            damageDtos.add(damageDto);

        }
        return damageDtos;

    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet=CrudUtil.execute("SELECT damage_id  FROM damage ORDER BY damage_id DESC LIMIT 1");
        if(resultSet.next()){
            String lastID=resultSet.getString("damage_id");
            String substring=lastID.substring(1);
            int id=Integer.parseInt(substring);
            int newId=id+1;
            return String.format("D%03d",newId);

        }
        return "D001";

    }


    @Override
    public String loadCurrentRentId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT rent_id FROM rent ORDER BY rent_id DESC LIMIT 1");

        if (resultSet.next()) {
            return resultSet.getString("rent_id");  // Return the most recent rent_id directly
        }

        return null;
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
}
