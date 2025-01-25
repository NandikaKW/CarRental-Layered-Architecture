package lk.ijse.gdse.carrentalsystem.bo.custom.impl;

import lk.ijse.gdse.carrentalsystem.bo.custom.AgrimentBO;
import lk.ijse.gdse.carrentalsystem.dao.DAOFactory;
import lk.ijse.gdse.carrentalsystem.dao.custom.AgrimentDAO;
import lk.ijse.gdse.carrentalsystem.dto.AgrimentDto;
import lk.ijse.gdse.carrentalsystem.entity.Agriment;

import java.sql.SQLException;
import java.util.ArrayList;

public class AgrimentBOImpl implements AgrimentBO {
     AgrimentDAO agrimentDAO = (AgrimentDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.AGREEMENT);

    @Override
    public boolean saveAgriment(AgrimentDto dto) throws SQLException, ClassNotFoundException {
        Agriment agriment = new Agriment(
                dto.getAgreement_id(),
                dto.getPayment_terms(),
                new java.sql.Date(dto.getStart_date().getTime()),
                new java.sql.Date(dto.getEnd_date().getTime()),
                dto.getDeposit_amount(),
                dto.getTotal_rent_cost()
        );
        return agrimentDAO.save(agriment);
    }

    @Override
    public boolean updateAgriment(AgrimentDto dto) throws SQLException, ClassNotFoundException {
        Agriment agriment = new Agriment(
                dto.getAgreement_id(),
                dto.getPayment_terms(),
                new java.sql.Date(dto.getStart_date().getTime()),
                new java.sql.Date(dto.getEnd_date().getTime()),
                dto.getDeposit_amount(),
                dto.getTotal_rent_cost()
        );
        return agrimentDAO.update(agriment);
    }

    @Override
    public boolean deleteAgriment(String id) throws SQLException, ClassNotFoundException {
        return agrimentDAO.delete(id);
    }

    @Override
    public AgrimentDto searchAgriment(String id) throws SQLException, ClassNotFoundException {
        Agriment agriment = agrimentDAO.search(id);
        if (agriment != null) {
            return new AgrimentDto(
                    agriment.getAgreement_id(),
                    agriment.getPayment_terms(),
                    agriment.getStart_date(),
                    agriment.getEnd_date(),
                    agriment.getDeposit_amount(),
                    agriment.getTotal_rent_cost()
            );
        }
        return null;
    }

    @Override
    public ArrayList<AgrimentDto> getAllAgriments() throws SQLException, ClassNotFoundException {
        ArrayList<Agriment> agriments = agrimentDAO.getAll();
        ArrayList<AgrimentDto> agrimentDtos = new ArrayList<>();
        for (Agriment agriment : agriments) {
            agrimentDtos.add(new AgrimentDto(
                    agriment.getAgreement_id(),
                    agriment.getPayment_terms(),
                    agriment.getStart_date(),
                    agriment.getEnd_date(),
                    agriment.getDeposit_amount(),
                    agriment.getTotal_rent_cost()
            ));
        }
        return agrimentDtos;
    }

    @Override
    public String getNextAgreementId() throws SQLException, ClassNotFoundException {
        return agrimentDAO.getNextId();
    }

    @Override
    public String loadCurrentAgreementId() throws SQLException, ClassNotFoundException {
        return agrimentDAO.loadCurrentAgreementId();
    }
}
