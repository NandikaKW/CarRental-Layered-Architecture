package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.EmployeeBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.EmployeeDAO;
import lk.ijse.gdse.carrentalsystem.dto.EmployeeDto;
import lk.ijse.gdse.carrentalsystem.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory
            .getInstance().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    // Save Employee
    @Override
    public boolean save(EmployeeDto employeeDto) throws SQLException {
        try {
            // Create a new Employee object from EmployeeDto
            Employee employee = new Employee(
                    employeeDto.getEmp_id(),
                    employeeDto.getEmp_name(),
                    employeeDto.getAddress(),
                    employeeDto.getJob(),
                    employeeDto.getSalary(),
                    employeeDto.getAdmin_id()
            );

            // Save the employee using the employeeDAO and return the result
            try {
                return employeeDAO.save(employee);  // Return the result of the save method
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            System.out.println("Error saving employee: " + e.getMessage());
            throw e; // Rethrow or handle exception as needed
        }
    }

    // Update Employee
    @Override
    public boolean update(EmployeeDto employeeDto) throws SQLException {
        try {
            return employeeDAO.update(new Employee(
                    employeeDto.getEmp_id(),
                    employeeDto.getEmp_name(),
                    employeeDto.getAddress(),
                    employeeDto.getJob(),
                    employeeDto.getSalary(),
                    employeeDto.getAdmin_id()
            ));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete Employee by ID
    @Override
    public boolean delete(String employeeId) throws SQLException {
        try {
            return employeeDAO.delete(employeeId);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Get all Employees
    @Override
    public ArrayList<EmployeeDto> getAll() throws SQLException {
        ArrayList<Employee> employeeList = null;
        try {
            employeeList = employeeDAO.getAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<EmployeeDto> employeeDtoList = new ArrayList<>();

        // Convert entity to DTO
        for (Employee employee : employeeList) {
            employeeDtoList.add(new EmployeeDto(
                    employee.getEmp_id(),
                    employee.getEmp_name(),
                    employee.getAddress(),
                    employee.getJob(),
                    employee.getSalary(),
                    employee.getAdmin_id()
            ));
        }
        return employeeDtoList;
    }

    // Get the next Employee ID
    @Override
    public String getNextId() throws SQLException {
        try {
            return employeeDAO.getNextId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // Search Employee by ID
    @Override
    public EmployeeDto search(String employeeId) throws SQLException {
        EmployeeDto employeeDto = null;
        try {
            // Call to employeeDAO.search(employeeId) returns an Employee object
            Employee employee = employeeDAO.search(employeeId);

            if (employee != null) {
                // Convert Employee object to EmployeeDto
                employeeDto = new EmployeeDto(
                        employee.getEmp_id(),      // Get emp_id from Employee object
                        employee.getEmp_name(),    // Get emp_name from Employee object
                        employee.getAddress(),     // Get address from Employee object
                        employee.getJob(),         // Get job from Employee object
                        employee.getSalary(),      // Get salary from Employee object
                        employee.getAdmin_id()     // Get admin_id from Employee object
                );
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return employeeDto;  // Return the EmployeeDto object
    }
}
