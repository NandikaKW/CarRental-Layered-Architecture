package lk.ijse.gdse.carrentalsystem.dao.custom.impl;

import lk.ijse.gdse.carrentalsystem.dao.custom.EmployeeDAO;
import lk.ijse.gdse.carrentalsystem.entity.Employee;
import lk.ijse.gdse.carrentalsystem.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public boolean save(Employee employee) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO employee VALUES(?,?,?,?,?,?)",
                employee.getEmp_id(), employee.getEmp_name(), employee.getAddress(),
                employee.getJob(), employee.getSalary(), employee.getAdmin_id());

    }

    @Override
    public boolean update(Employee employee) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE employee SET name = ?, address = ?, job_role = ?, salary = ?, admin_id = ? WHERE emp_id = ?",
                employee.getEmp_name(), employee.getAddress(), employee.getJob(),
                employee.getSalary(), employee.getAdmin_id(), employee.getEmp_id());
    }
    @Override
    public boolean delete(String Id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM employee WHERE emp_id=?", Id);

    }

    @Override
    public Employee search(String employeeId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee WHERE emp_id=?", employeeId);
        if (resultSet.next()) {
            return new Employee(
                    resultSet.getString("emp_id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("job_role"),
                    resultSet.getBigDecimal("salary"),
                    resultSet.getString("admin_id")
            );
        }
        return null;

    }

    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM employee");
        ArrayList<Employee> employees = new ArrayList<>();
        while (resultSet.next()) {
            employees.add(new Employee(
                    resultSet.getString("emp_id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("job_role"),
                    resultSet.getBigDecimal("salary"),
                    resultSet.getString("admin_id")
            ));
        }
        return employees;


    }


    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT emp_id FROM employee ORDER BY emp_id DESC LIMIT 1");
        if (resultSet.next()) {
            String lastID = resultSet.getString("emp_id");
            String substring = lastID.substring(1);
            int id = Integer.parseInt(substring);
            int newId = id + 1;
            return String.format("E%03d", newId);
        }
        return "E001";

    }
}
