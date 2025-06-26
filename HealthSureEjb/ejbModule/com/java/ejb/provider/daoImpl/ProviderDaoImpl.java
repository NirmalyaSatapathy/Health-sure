package com.java.ejb.provider.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.java.ejb.provider.dao.ProviderDao;
import com.java.ejb.provider.model.MedicalProcedure;
import com.java.ejb.provider.model.Prescription;
import com.java.ejb.provider.model.ProcedureTest;
import com.java.ejb.util.ConnectionHelper;

public class ProviderDaoImpl implements ProviderDao{

	@Override
	public String addMedicalProcedure(MedicalProcedure medicalProcedure) throws ClassNotFoundException, SQLException {
	    Connection con = ConnectionHelper.getConnection();
	    String sql = "INSERT INTO medical_procedure (procedure_id, appointment_id, h_id, provider_id, doctor_id, procedure_date, diagnosis, recommendations, from_date, to_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";


	    PreparedStatement pst = con.prepareStatement(sql);

	    pst.setString(1, medicalProcedure.getProcedureId());
	    pst.setString(2, medicalProcedure.getAppointment().getAppointmentId());
	    pst.setString(3, medicalProcedure.getRecipient().gethId());
	    pst.setString(4, medicalProcedure.getProvider().getProviderId());
	    pst.setString(5, medicalProcedure.getDoctor().getDoctorId());

	    java.sql.Date procDate = new java.sql.Date(medicalProcedure.getProcedureDate().getTime());
	    pst.setDate(6, procDate);

	    pst.setString(7, medicalProcedure.getDiagnosis());
	    pst.setString(8, medicalProcedure.getRecommendations());

	    if (medicalProcedure.getFromDate() != null) {
	        pst.setTimestamp(9, new java.sql.Timestamp(medicalProcedure.getFromDate().getTime()));
	    } else {
	        pst.setTimestamp(9, null);
	    }

	    if (medicalProcedure.getToDate() != null) {
	        pst.setTimestamp(10, new java.sql.Timestamp(medicalProcedure.getToDate().getTime()));
	    } else {
	        pst.setTimestamp(10, null);
	    }
	    System.out.println(medicalProcedure);
	    pst.executeUpdate();
	    pst.close();
	    con.close();

	    return "inserted";
	}

	@Override
	public String addPrescription(Prescription prescription) throws SQLException, ClassNotFoundException {
	    Connection con = ConnectionHelper.getConnection();
	    String sql = "INSERT INTO prescription (" +
	                 "prescription_id, procedure_id, h_id, provider_id, doctor_id, " +
	                 "medicine_name, dosage, duration, notes, written_on, created_at) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    PreparedStatement pst = con.prepareStatement(sql);

	    pst.setString(1, prescription.getPrescriptionId());
	    pst.setString(2, prescription.getProcedure().getProcedureId());
	    pst.setString(3, prescription.getRecipient().gethId());
	    pst.setString(4, prescription.getProvider().getProviderId());
	    pst.setString(5, prescription.getDoctor().getDoctorId());
	    pst.setString(6, prescription.getMedicineName());
	    pst.setString(7, prescription.getDosage());
	    pst.setString(8, prescription.getDuration());
	    pst.setString(9, prescription.getNotes());

	    if (prescription.getWrittenOn() != null) {
	        pst.setTimestamp(10, new java.sql.Timestamp(prescription.getWrittenOn().getTime()));
	    } else {
	        pst.setTimestamp(10, null);
	    }

	    if (prescription.getCreatedAt() != null) {
	        pst.setTimestamp(11, new java.sql.Timestamp(prescription.getCreatedAt().getTime()));
	    } else {
	        pst.setTimestamp(11, new java.sql.Timestamp(System.currentTimeMillis()));
	    }

	    pst.executeUpdate();
	    pst.close();
	    con.close();

	    return "inserted";
	}


	@Override
	public String addTest(ProcedureTest procedureTest) throws ClassNotFoundException, SQLException {
	    Connection con = ConnectionHelper.getConnection();

	    String sql = "INSERT INTO procedure_test (" +
	                 "test_id, procedure_id, test_name, test_date, result_summary, status, created_at) " +
	                 "VALUES (?, ?, ?, ?, ?, ?, ?)";

	    PreparedStatement pst = con.prepareStatement(sql);

	    pst.setString(1, procedureTest.getTestId());
	    pst.setString(2, procedureTest.getProcedure().getProcedureId());
	    pst.setString(3, procedureTest.getTestName());

	    // Convert testDate (java.util.Date) to java.sql.Date
	    if (procedureTest.getTestDate() != null) {
	        pst.setDate(4, new java.sql.Date(procedureTest.getTestDate().getTime()));
	    } else {
	        pst.setDate(4, new java.sql.Date(System.currentTimeMillis())); // fallback
	    }

	    pst.setString(5, procedureTest.getResultSummary());
	    pst.setString(6, procedureTest.getStatus());

	    if (procedureTest.getCreatedAt() != null) {
	        pst.setTimestamp(7, new java.sql.Timestamp(procedureTest.getCreatedAt().getTime()));
	    } else {
	        pst.setTimestamp(7, new java.sql.Timestamp(System.currentTimeMillis()));
	    }

	    pst.executeUpdate();
	    pst.close();
	    con.close();

	    return "inserted";
	}


}
