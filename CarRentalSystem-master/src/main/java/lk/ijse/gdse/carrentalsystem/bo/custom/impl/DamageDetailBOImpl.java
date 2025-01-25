package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.DamageDetailBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.DamageDetailDAO;
import lk.ijse.gdse.carrentalsystem.dto.DamageDto;
import lk.ijse.gdse.carrentalsystem.dto.EmployeeDto;
import lk.ijse.gdse.carrentalsystem.entity.Damage;

import java.sql.SQLException;
import java.util.ArrayList;

public class DamageDetailBOImpl implements DamageDetailBO {
 DamageDetailDAO damageDetailDAO= (DamageDetailDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.DAMAGE_DETAIL);
    @Override
    public boolean save(DamageDto damageDto) throws SQLException {
       try {
           Damage damage=new Damage(
                   damageDto.getDamage_id(),
                   damageDto.getRepair_cost(),
                   damageDto.getDetail(),
                   damageDto.getRent_id()
           );

           try {
               return damageDetailDAO.save(damage);
           } catch (ClassNotFoundException e) {
               throw new RuntimeException(e);
           }

       }catch (SQLException e){
           System.out.println("Error saving employee: " + e.getMessage());
           throw e;
       }
    }

    @Override
    public boolean update(DamageDto damageDto) throws SQLException {
         try {
             return damageDetailDAO.update(new Damage(damageDto.getDamage_id(),damageDto.getRepair_cost(),damageDto.getDetail(),damageDto.getRent_id()));
         } catch (ClassNotFoundException e) {
             throw new RuntimeException(e);
         }
    }

    @Override
    public boolean delete(String id) throws SQLException {
        try {
            return damageDetailDAO.delete(id);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public DamageDto search(String id) throws SQLException {
        DamageDto damageDto = null;
        try {
            // Call to employeeDAO.search(employeeId) returns an Employee object
            Damage damage = damageDetailDAO.search(id);

            if (damage != null) {
                // Convert Employee object to EmployeeDto
                 damageDto = new DamageDto(
                        damage.getDamage_id(),      // Get emp_id from Employee object
                        damage.getRepair_cost(),    // Get emp_name from Employee object
                        damage.getDetail(),     // Get address from Employee object
                        damage.getRent_id()         // Get job from Employee object
                );
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return damageDto;  // Return the EmployeeDto object

    }

    @Override
    public ArrayList<DamageDto> getAll() throws SQLException {
        ArrayList<Damage> damageList = null;
        try {
            damageList = damageDetailDAO.getAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<DamageDto> damagedtoList = new ArrayList<>();

        // Convert entity to DTO
        for (Damage employee : damageList) {
           damagedtoList.add(new DamageDto(
                   employee.getDamage_id(),
                   employee.getRepair_cost(),
                   employee.getDetail(),
                   employee.getRent_id()
           ));
        }
        return damagedtoList;

    }

    @Override
    public String getNextId() throws SQLException {
        try {
            return damageDetailDAO.getNextId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String loadCurrentRentId() throws SQLException {
        try {
            return damageDetailDAO.loadCurrentRentId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
