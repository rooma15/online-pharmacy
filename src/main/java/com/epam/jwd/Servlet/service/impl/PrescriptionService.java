package com.epam.jwd.Servlet.service.impl;

import com.epam.jwd.Servlet.dao.AbstractDAO;
import com.epam.jwd.Servlet.dao.impl.MedicineDAO;
import com.epam.jwd.Servlet.dao.impl.PrescriptionDAO;
import com.epam.jwd.Servlet.dao.impl.UserDAO;
import com.epam.jwd.Servlet.model.Medicine;
import com.epam.jwd.Servlet.model.Prescription;
import com.epam.jwd.Servlet.model.PrescriptionDto;
import com.epam.jwd.Servlet.model.User;
import com.epam.jwd.Servlet.service.CommonService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * service for handling with prescriptions
 */
public class PrescriptionService implements CommonService<PrescriptionDto> {


    private final PrescriptionDAO prescriptionDAO;

    private final Function<ResultSet, Optional<Prescription>> adder = resultSet -> {
        try {
            return Optional.of(new Prescription(resultSet));
        } catch (SQLException e) {
            return Optional.empty();
        }
    };
    /**
     * initializes prescription DAO {@link PrescriptionDAO}
     */
    public PrescriptionService() {
        this.prescriptionDAO = new PrescriptionDAO();
    }

    /**
     *
     * @return {@link List} of all prescriptions Dto`s
     */
    @Override
    public List<PrescriptionDto> findAll() {
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptionDAO.findAll().forEach(prescription -> prescription.ifPresent(prescriptions::add));
        return prescriptions.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    /**
     *
     * @param medicineId id of medicine in prescription
     * @param userid id of prescription owner
     * @return true if prescription was created successfully, false otherwise
     */
    public boolean create(int medicineId, int userid){
        return prescriptionDAO.create(new Prescription(userid, medicineId, false));
    }

    /**
     * delete prescription from database by id
     * @param id id of prescription in database to be deleted
     * @return true if prescription was deleted successfully, false otherwise
     */
    @Override
    public boolean deleteById(int id) {
        return prescriptionDAO.deleteById(id);
    }

    /**
     * searches prescription in database by id
     * @param id id of prescription in database
     * @return {@link PrescriptionDto}
     */
    @Override
    public Optional<PrescriptionDto> findById(int id) {
        return prescriptionDAO.findById(id).map(this::convertToDto);
    }


    /**
     * coverts {@link Prescription} to {@link PrescriptionDto}
     * @param prescription to be converted
     * @return converted {@link PrescriptionDto}
     */
    private PrescriptionDto convertToDto(Prescription prescription){
        String medicineName;
        int medicineDose;
        String medicineConsistency;
        String userName;
        String userLogin;
        MedicineDAO medicineDAO = new MedicineDAO();
        Optional<Medicine> medicine = medicineDAO.findById(prescription.getMedicineId());
        if(medicine.isPresent()){
            medicineName = medicine.get().getName();
            medicineDose = medicine.get().getDose();
            medicineConsistency = medicine.get().getConsistency();
        }else {
            throw new IllegalStateException();
        }
        UserDAO userDAO = new UserDAO();
        Optional<User> user = userDAO.findById(prescription.getUserId());
        if(user.isPresent()){
            userName = user.get().getName();
            userLogin = user.get().getLogin();
        }else {
            throw new IllegalStateException();
        }
        return new PrescriptionDto(prescription.getId(),
                medicineName,
                medicineDose,
                medicineConsistency,
                userName,
                userLogin);
    }

    /**
     * checks if user has prescription on current medicine
     * @param medicineId id of medicine
     * @param userId id of user
     * @return true if user has prescription, false otherwise
     */
    public boolean checkPrescription(int medicineId, int userId){
        List<Prescription> prescriptions = findByUserId(userId);
        for(Prescription prescription : prescriptions) {
            if(prescription.getMedicineId() == medicineId && prescription.getAccepted()){
                return true;
            }
        }
        return false;
    }


    /**
     * finds all accepted(or not accepted) prescriptions
     * @param isAccepted if isAccepted is true, then it returns all accepted prescriptions, if false, all not accepted
     * @return list of accepted(or not accepted) prescriptions
     */
    public List<PrescriptionDto> findAccepted(boolean isAccepted){
        return prescriptionDAO.findAccepted(isAccepted)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    /**
     * finds prescription of concrete user on concrete medicine
     * @param userId id of user
     * @param medicineId id of medicine
     * @return {@link Optional} of prescription, empty {@link Optional} otherwise
     */
    public Optional<Prescription> findByUserIdMedicineId(int userId, int medicineId){
        String st = "select * from pharmacy.Prescriptions where user_id=? and medicine_id=?";
        List<Optional<Prescription>> prescriptions = AbstractDAO
                .<Prescription>findByCriteria(st, "ii", adder, userId, medicineId);
        if(!prescriptions.isEmpty()){
            return prescriptions.get(0);
        }else {
            return Optional.empty();
        }
    }

    /**
     * inds prescriptions of concrete medicine
     * @param medicineId id of medicine to be found
     * @return {@link List} of prescriptions of this medicine
     */
    public List<Prescription> findByMedicineId(int medicineId){
        String st = "select * from pharmacy.Prescriptions where medicine_id=?";
        return AbstractDAO.<Prescription>findByCriteria(st, "i", adder, medicineId)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * inds prescriptions of concrete user
     * @param userId id of user
     * @return {@link List} of prescriptions of this user
     */
    public List<Prescription> findByUserId(int userId){
        String st = "select * from pharmacy.Prescriptions where user_id=?";
        return AbstractDAO.<Prescription>findByCriteria(st, "i",adder, userId)
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    /**
     * set prescription with current id accepted
     * @param id id of prescription
     * @return true if everything was successful, false otherwise
     */
    public boolean setAccepted(int id){
        String st = "update pharmacy.Prescriptions set is_accepted=true where id=?";
        return AbstractDAO.updateByCriteria(st, "i", id);
    }

}
