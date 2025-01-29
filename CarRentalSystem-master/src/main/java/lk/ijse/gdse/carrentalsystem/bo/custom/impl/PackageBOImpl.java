package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.PackageBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.PackageDAO;
import lk.ijse.gdse.carrentalsystem.dto.CustomerPaymentDto;
import lk.ijse.gdse.carrentalsystem.dto.PackageDto;
import lk.ijse.gdse.carrentalsystem.entity.Package;



import java.sql.SQLException;
import java.util.ArrayList;

public class PackageBOImpl implements PackageBO {

    PackageDAO packageDAO = (PackageDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.PACKAGE);



    @Override
    public boolean save(PackageDto packageDto) throws SQLException {
        Package packages =new Package(
            packageDto.getPackageId(),
            packageDto.getPackageName(),
            packageDto.getTotalCost(),
            packageDto.isInsuranceIncluded(),
            packageDto.getRentalDuration(),
            packageDto.getRentDate(),
            packageDto.getMileageLimit(),
            packageDto.getDescription()
        );
        try {
            return packageDAO.save(packages);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(PackageDto packageDto) throws SQLException {
       try {
           return packageDAO.update(new Package(
               packageDto.getPackageId(),
               packageDto.getPackageName(),
               packageDto.getTotalCost(),
               packageDto.isInsuranceIncluded(),
               packageDto.getRentalDuration(),
               packageDto.getRentDate(),
               packageDto.getMileageLimit(),
               packageDto.getDescription()
           ));
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }

    @Override
    public boolean delete(String packageId) throws SQLException {
        try {
            return packageDAO.delete(packageId);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public PackageDto search(String packageId) throws SQLException {
        lk.ijse.gdse.carrentalsystem.entity.Package packageEntity = null;
        try {
            packageEntity = packageDAO.search(packageId);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (packageEntity != null) {
            // Convert Package entity to PackageDto
            return new PackageDto(
                    packageEntity.getPackageId(),
                    packageEntity.getPackageName(),
                    packageEntity.getTotalCost(),
                    packageEntity.isInsuranceIncluded(),
                    packageEntity.getRentalDuration(),
                    packageEntity.getRentDate(),
                    packageEntity.getMileageLimit(),
                    packageEntity.getDescription()
            );
        }
        return null;
    }

    @Override
    public ArrayList<PackageDto> getAll() throws SQLException {
        ArrayList<Package> packages = null;
        try {
            packages = packageDAO.getAll();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ArrayList<PackageDto> packageDtos = new ArrayList<>();

        // Convert each Package entity to PackageDto
        for (Package packageEntity : packages) {
            packageDtos.add(new PackageDto(
                    packageEntity.getPackageId(),
                    packageEntity.getPackageName(),
                    packageEntity.getTotalCost(),
                    packageEntity.isInsuranceIncluded(),
                    packageEntity.getRentalDuration(),
                    packageEntity.getRentDate(),
                    packageEntity.getMileageLimit(),
                    packageEntity.getDescription()
            ));
        }
        return packageDtos;
    }

    @Override
    public String getNextId() throws SQLException {
        try {
            return packageDAO.getNextId();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<String> getIds() throws SQLException {
        try {
            return packageDAO.getAllPackageIds();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String loadCurrentPackageId() throws SQLException, ClassNotFoundException {
        return packageDAO.loadCurrentPackageId();
    }


}
