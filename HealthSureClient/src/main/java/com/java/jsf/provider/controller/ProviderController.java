package com.java.jsf.provider.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.java.ejb.provider.bean.ProviderEjbImpl;
import com.java.ejb.provider.model.Doctor;
import com.java.ejb.provider.model.MedicalProcedure;
import com.java.ejb.provider.model.PrescribedMedicines;
import com.java.ejb.provider.model.Prescription;
import com.java.ejb.provider.model.ProcedureTest;
import com.java.ejb.provider.model.Provider;
import com.java.ejb.recipient.model.Recipient;
import com.java.jsf.provider.daoImpl.InsuranceDaoImpl;
import com.java.jsf.provider.daoImpl.ProviderDaoImpl;
import com.java.ejb.provider.model.Appointment;
import com.java.jsf.provider.model.PatientInsuranceDetails;

public class ProviderController {
ProviderEjbImpl providerEjb;
ProviderDaoImpl providerDao;
String doctorId;
String healthId;
List<Recipient> associatedPatients;
MedicalProcedure medicalProcedure;
List<PatientInsuranceDetails> patientInsuranceList;
PatientInsuranceDetails selectedItem;
private String topMessage;

public String getTopMessage() {
    return topMessage;
}

public void setTopMessage(String topMessage) {
    this.topMessage = topMessage;
}

public PatientInsuranceDetails getSelectedItem() {
	return selectedItem;
}
public void setSelectedItem(PatientInsuranceDetails selectedItem) {
	this.selectedItem = selectedItem;
}
public List<PatientInsuranceDetails> getPatientInsuranceList() {
	return patientInsuranceList;
}
public void setPatientInsuranceList(List<PatientInsuranceDetails> patientInsuranceList) {
	this.patientInsuranceList = patientInsuranceList;
}
public InsuranceDaoImpl getInsuranceDaoImpl() {
	return insuranceDaoImpl;
}
public void setInsuranceDaoImpl(InsuranceDaoImpl insuranceDaoImpl) {
	this.insuranceDaoImpl = insuranceDaoImpl;
}
InsuranceDaoImpl insuranceDaoImpl;
public MedicalProcedure getMedicalProcedure() {
	return medicalProcedure;
}
public void setMedicalProcedure(MedicalProcedure medicalProcedure) {
	this.medicalProcedure = medicalProcedure;
}
public ProviderEjbImpl getProviderEjb() {
	return providerEjb;
}
public void setProviderEjb(ProviderEjbImpl providerEjb) {
	this.providerEjb = providerEjb;
}
public ProviderController() {
	super();
	// TODO Auto-generated constructor stub
}
public ProviderDaoImpl getProviderDao() {
	return providerDao;
}
public void setProviderDao(ProviderDaoImpl providerDao) {
	this.providerDao = providerDao;
}
public String addMedicalProcedureController(MedicalProcedure medicalProcedure) throws ClassNotFoundException, SQLException {
	providerDao=new ProviderDaoImpl();
    FacesContext context = FacesContext.getCurrentInstance();
    boolean isValid = true;
    System.out.println(providerDao);
    System.out.println(medicalProcedure.getRecipient().gethId());
    // --- Validate Recipient Existence ---
    Recipient recipient = providerDao.searchRecipientByHealthId(medicalProcedure.getRecipient().gethId());
    if (recipient == null) {
        context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Invalid Patient", "Recipient with given Health ID not found."));
        context.validationFailed();
        isValid = false;
    }

    // --- Validate Provider Existence ---
    Provider provider = providerDao.searchProviderById(medicalProcedure.getProvider().getProviderId());
    if (provider == null) {
        context.addMessage("providerId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Invalid Provider", "Provider with given ID not found."));
        context.validationFailed();
        isValid = false;
    }

    // --- Validate Doctor Existence ---
    Doctor doctor = providerDao.searchDoctorById(medicalProcedure.getDoctor().getDoctorId());
    if (doctor==null) {
        context.addMessage("doctorId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Invalid Doctor", "Doctor with given ID not found."));
        context.validationFailed();
        isValid = false;
    }

    // --- Validate Appointment Existence and Association ---
    Appointment appointment = providerDao.searchAppointmentById(medicalProcedure.getAppointment().getAppointmentId());
    if (appointment == null) {
        context.addMessage("appointmentId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Invalid Appointment", "Appointment with given ID not found."));
        context.validationFailed();
        isValid = false;
    } else {
        // Validate if appointment is linked with given doctor/provider/recipient
        if (!appointment.getProvider().getProviderId().equals(medicalProcedure.getProvider().getProviderId())) {
            context.addMessage("providerId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Mismatch", "This appointment does not belong to the selected provider."));
            context.validationFailed();
            isValid = false;
        }

        if (!appointment.getDoctor().getDoctorId().equals(medicalProcedure.getDoctor().getDoctorId())) {
            context.addMessage("doctorId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Mismatch", "This appointment does not involve the selected doctor."));
            context.validationFailed();
            isValid = false;
        }

        if (!appointment.getRecipient().gethId().equals(medicalProcedure.getRecipient().gethId())) {
            context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Mismatch", "This appointment is not for the selected patient."));
            context.validationFailed();
            isValid = false;
        }
    }
    // --- Date Validations ---
    Date fromDate = medicalProcedure.getFromDate();
    Date toDate = medicalProcedure.getToDate();
    Date procedureDate=medicalProcedure.getProcedureDate();
    Date today = new Date();
    if (fromDate != null && toDate != null) {
        if (fromDate.after(today)) {
            context.addMessage("fromDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid Date", "From Date cannot be in the future."));
            context.validationFailed();
            isValid = false;
        }
        if(fromDate.after(procedureDate))
        {
        	 context.addMessage("fromDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                     "Invalid Date", "from Date cannot be after procedure date."));
             context.validationFailed();
             isValid = false;
        }
        if (toDate.before(fromDate)) {
            context.addMessage("toDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid Date", "To Date cannot be before from date."));
            context.validationFailed();
            isValid = false;
        }
        if (toDate.after(today)) {
            context.addMessage("toDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid Date", "To Date cannot be in the future."));
            context.validationFailed();
            isValid = false;
        }
    }
    // --- If any error found ---
    if (!isValid) {
        return null;
    }
    // --- Save via EJB ---
    
    return providerEjb.addMedicalProcedure(medicalProcedure);
}
public String addPresribedMedicinesController(PrescribedMedicines prescribedMedicines) throws ClassNotFoundException, SQLException
{
	
	return providerEjb.addPrescribedMedicines(prescribedMedicines);
}
public String addPrescriptionController(Prescription prescription) throws ClassNotFoundException, SQLException {
    providerDao = new ProviderDaoImpl();
    FacesContext context = FacesContext.getCurrentInstance();
    Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();

    // Retrieve IDs from session and set reference objects
    MedicalProcedure procedure = new MedicalProcedure();
    procedure.setProcedureId((String) sessionMap.get("procedureId"));
    prescription.setProcedure(procedure);

    Provider provider = new Provider();
    provider.setProviderId((String) sessionMap.get("providerId"));
    prescription.setProvider(provider);

    Doctor doctor = new Doctor();
    doctor.setDoctorId((String) sessionMap.get("doctorId"));
    prescription.setDoctor(doctor);

    Recipient recipient = new Recipient();
    recipient.sethId((String) sessionMap.get("recipientHid"));
    prescription.setRecipient(recipient);

    // Validate writtenOn field
    if (prescription.getWrittenOn() == null) {
        context.addMessage("writtenOn", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Please enter the Written On date.", null));
        return null;
    }

    // Fetch Procedure Date
    Date startDate = providerDao.getProcedureStartDate(prescription.getProcedure().getProcedureId());

    if (startDate == null) {
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Missing Procedure Date for Procedure ID: " + prescription.getProcedure().getProcedureId(), null));
        return null;
    }

    // Validate: writtenOn must not be before procedureDate
    if (prescription.getWrittenOn().before(startDate)) {
        context.addMessage("writtenOn", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Written On date (" + prescription.getWrittenOn() +
                ") cannot be before the Procedure start Date (" + startDate + ").", null));
        return null;
    }
    if (prescription.getStartDate().before(prescription.getWrittenOn())) {
        context.addMessage("startDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "prescription start date(" + prescription.getStartDate() +
                ") cannot be before the prescription written Date (" + prescription.getWrittenOn() + ").", null));
        return null;
    }
    if (prescription.getEndDate().before(prescription.getStartDate())) {
        context.addMessage("endDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "prescription end date(" + prescription.getEndDate() +
                ") cannot be before the prescription start Date (" + prescription.getStartDate() + ").", null));
        return null;
    }
    System.out.println("all validations passed");
    return providerEjb.addPrescription(prescription);  // Proceed if all validations pass
}


public String addTestController(ProcedureTest procedureTest) throws ClassNotFoundException, SQLException
{
	return providerEjb.addTest(procedureTest);
}
public String procedureSubmit()
{
	return "ProviderDashboard?faces-redirect=true";
}
public String prescriptionDetailsSubmit()
{
	return "ProcedureDashboard?faces-redirect=true";
}
public String handleSearch() {
	insuranceDaoImpl=new InsuranceDaoImpl();
	providerDao=new ProviderDaoImpl();
    FacesContext context = FacesContext.getCurrentInstance();
    topMessage = null;
    patientInsuranceList = null; // Clear insurance list
    associatedPatients = null; // Clear patient list
    // Validate doctorId is not empty
    if (doctorId == null || doctorId.trim().isEmpty()) {
        context.addMessage("doctorId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Doctor ID is required.", null));
        return null;
    }

    Doctor doctor = providerDao.searchDoctorById(doctorId);
    if (doctor == null) {
        context.addMessage("doctorId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Doctor with ID " + doctorId + " does not exist.", null));
        return null;
    }

    // Case 1: Doctor and patient ID both provided
    if (healthId != null && !healthId.trim().isEmpty()) {
        Recipient recipient = providerDao.searchRecipientByHealthId(healthId);
        if (recipient == null) {
            context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Patient with Health ID " + healthId + " does not exist.", null));
            return null;
        }

        // Check doctor-patient association
        if (!providerDao.isDoctorPatientAssociatedByAppointment(doctorId, healthId)) {
            context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Access denied: The doctor is not associated with this patient via an appointment.", null));
            return null;
        }

        // Show insurance for this specific patient
        patientInsuranceList = insuranceDaoImpl.showInsuranceOfRecipient(healthId);
        if (patientInsuranceList == null || patientInsuranceList.isEmpty()) {
            context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "No insurance found for patient ID: " + healthId, null));
        }
        
    } 
    // Case 2: Only doctor ID provided
    else {
        associatedPatients = providerDao.getPatientListByDoctorId(doctorId);
        if (associatedPatients == null || associatedPatients.isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "No patients found for Doctor ID: " + doctorId, null));
        }
       
    }

    return null;
}

public String showInsuranceForPatient(String hId) {
    System.out.println("view members called from nested table for hid " + hId);
    patientInsuranceList = insuranceDaoImpl.showInsuranceOfRecipient(hId);
    
    if (patientInsuranceList == null || patientInsuranceList.isEmpty()) {
        topMessage = "No insurance found for patient ID: " + hId;
    } else {
        topMessage = null; // Clear message if insurance is found
    }
    
    return null;
}



public String getDoctorId() {
	return doctorId;
}
public void setDoctorId(String doctorId) {
	this.doctorId = doctorId;
}
public String getHealthId() {
	return healthId;
}
public void setHealthId(String healthId) {
	this.healthId = healthId;
}
public List<Recipient> getAssociatedPatients() {
	return associatedPatients;
}
public void setAssociatedPatients(List<Recipient> associatedPatients) {
	this.associatedPatients = associatedPatients;
}
public String redirect(PatientInsuranceDetails insurance) {
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedInsurance", insurance);
    return "viewMembers?faces-redirect=true&ts=" + System.currentTimeMillis(); // timestamp tricks JSF into fresh redirect
}

public PatientInsuranceDetails loadSubscribedMembers() {
        Object obj = FacesContext.getCurrentInstance()
                                 .getExternalContext()
                                 .getSessionMap()
                                 .get("selectedInsurance");
    return (PatientInsuranceDetails)obj;
}

}
