package com.java.jsf.provider.controller;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.java.ejb.provider.bean.ProviderEjbImpl;
import com.java.ejb.provider.model.Doctor;
import com.java.ejb.provider.model.MedicalProcedure;
import com.java.ejb.provider.model.MedicineType;
import com.java.ejb.provider.model.PrescribedMedicines;
import com.java.ejb.provider.model.Prescription;
import com.java.ejb.provider.model.ProcedureTest;
import com.java.ejb.provider.model.Provider;
import com.java.ejb.recipient.model.Recipient;
import com.java.jsf.provider.daoImpl.ProviderDaoImpl;
import com.java.jsf.util.ProcedureIdGenerator;
import com.java.ejb.provider.model.Appointment;

public class ProcedureController {
    private ProviderEjbImpl providerEjb;
    private ProviderDaoImpl providerDao;
    MedicalProcedure procedure;
    Prescription prescription;
    PrescribedMedicines prescribedMedicines;
    ProcedureTest procedureTest;
    public ProcedureController() {
        super();
    }

    public ProviderEjbImpl getProviderEjb() {
        return providerEjb;
    }

    public void setProviderEjb(ProviderEjbImpl providerEjb) {
        this.providerEjb = providerEjb;
    }

    public ProviderDaoImpl getProviderDao() {
        return providerDao;
    }

    public void setProviderDao(ProviderDaoImpl providerDao) {
        this.providerDao = providerDao;
    }

    public String addMedicalProcedureController(MedicalProcedure medicalProcedure) throws ClassNotFoundException, SQLException {
        providerDao = new ProviderDaoImpl();
        FacesContext context = FacesContext.getCurrentInstance();
        boolean isValid = true;
        
        // Validate Recipient Existence
        Recipient recipient = providerDao.searchRecipientByHealthId(medicalProcedure.getRecipient().gethId());
        if (recipient == null) {
            context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid Patient", "Recipient with given Health ID not found."));
            context.validationFailed();
            isValid = false;
        }

        // Validate Provider Existence
        Provider provider = providerDao.searchProviderById(medicalProcedure.getProvider().getProviderId());
        if (provider == null) {
            context.addMessage("providerId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid Provider", "Provider with given ID not found."));
            context.validationFailed();
            isValid = false;
        }

        // Validate Doctor Existence
        Doctor doctor = providerDao.searchDoctorById(medicalProcedure.getDoctor().getDoctorId());
        if (doctor == null) {
            context.addMessage("doctorId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid Doctor", "Doctor with given ID not found."));
            context.validationFailed();
            isValid = false;
        }

        // Validate Appointment Existence and Association
        Appointment appointment = providerDao.searchAppointmentById(medicalProcedure.getAppointment().getAppointmentId());
        if (appointment == null) {
            context.addMessage("appointmentId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Invalid Appointment", "Appointment with given ID not found."));
            context.validationFailed();
            isValid = false;
        } else {
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
        
        // Date Validations
        Date fromDate = medicalProcedure.getFromDate();
        Date toDate = medicalProcedure.getToDate();
        Date procedureDate = medicalProcedure.getProcedureDate();
        Date today = new Date();
        
        if (fromDate != null && toDate != null) {
            if (fromDate.after(today)) {
                context.addMessage("fromDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Invalid Date", "From Date cannot be in the future."));
                context.validationFailed();
                isValid = false;
            }
            if (fromDate.after(procedureDate)) {
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
        
        if (!isValid) {
            return null;
        }
        String res=providerEjb.addMedicalProcedure(medicalProcedure);
        this.procedure=null;
        return res;
    }
    public String addTestController(ProcedureTest procedureTest) throws ClassNotFoundException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        providerDao = new ProviderDaoImpl();

        String prescriptionId = (String) sessionMap.get("prescriptionId");
        procedureTest.getPrescription().setPrescriptionId(prescriptionId);

        // Fetch prescription writtenOn and procedure endDate
        Date writtenOn = providerDao.getPrescriptionWrittenOnDate(prescriptionId);
        String procedureId = (String) sessionMap.get("procedureId"); // assume stored in session
        Date procedureEndDate = providerDao.getProcedureEndDate(procedureId);

        // 1. Validate Test Name
        String testName = procedureTest.getTestName();
        if (testName == null || testName.trim().length() < 2 || !testName.matches("^[a-zA-Z0-9 ()/\\-.]+$")) {
            context.addMessage("testName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Test name must be at least 2 characters and contain only letters, numbers, spaces, (), /, -, and .", null));
            return null;
        }
        testName = testName.trim().replaceAll("\\s+", " ");
        procedureTest.setTestName(testName);

        // 2. Validate Test Date
        Date testDate = procedureTest.getTestDate();
        if (testDate == null) {
            context.addMessage("testDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Test date is required.", null));
            return null;
        }

        if (writtenOn != null && testDate.before(writtenOn)) {
            context.addMessage("testDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Test date (" + testDate + ") cannot be before prescription written date (" + writtenOn + ").", null));
            return null;
        }

        if (procedureEndDate != null && testDate.after(procedureEndDate)) {
            context.addMessage("testDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Test date (" + testDate + ") cannot be after procedure end date (" + procedureEndDate + ").", null));
            return null;
        }

        // 3. Validate Result Summary
        String result = procedureTest.getResultSummary();
        if (result == null || result.trim().isEmpty()) {
            context.addMessage("resultSummary", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Result summary is required.", null));
            return null;
        }

        return providerEjb.addTest(procedureTest);
    }


    public String addPresribedMedicinesController(PrescribedMedicines prescribedMedicines)
            throws ClassNotFoundException, SQLException {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> sessionMap = context.getExternalContext().getSessionMap();
        providerDao = new ProviderDaoImpl();
        String prescriptionId = (String) sessionMap.get("prescriptionId");
        prescribedMedicines.getPrescription().setPrescriptionId(prescriptionId);

        // 1. Medicine Name Validation
        String medicineName = prescribedMedicines.getMedicineName();
        if (medicineName == null || !medicineName.matches("^[a-zA-Z0-9()\\-+/'. ]{2,50}$")) {
            context.addMessage("medicineName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Medicine name must be 2–50 characters and can include letters, digits, -, /, +, (), '.', and spaces.", null));
            return null;
        }

        // Normalize medicine name
        medicineName = medicineName.trim().replaceAll("\\s+", " ");
        prescribedMedicines.setMedicineName(medicineName);

     // 2. Check for duplicate medicine in same prescription
        List<String> existingMedicineNames = providerDao.getMedicineNamesByPrescriptionId(prescriptionId);
        System.out.println(existingMedicineNames);
        System.out.println(medicineName);
        System.out.println("________"+prescriptionId);
        for (String existingName : existingMedicineNames) {
            if (existingName.equalsIgnoreCase(medicineName)) {
                context.addMessage("medicineName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "This medicine is already prescribed in this prescription.", null));
                return null;
            }
        }


        // 3. Dosage Validation
        String dosage = prescribedMedicines.getDosage();
        if (dosage == null || dosage.trim().isEmpty()) {
            context.addMessage("dosage", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Dosage is required.", null));
            return null;
        }

        MedicineType type = prescribedMedicines.getType();
        String pattern;

        switch (type) {
            case TABLET:
                pattern = "^\\d+\\s*tablet(s)?$";
                break;
            case SYRUP:
                pattern = "^\\d+(\\.\\d+)?\\s*ml$";
                break;
            case INJECTION:
                pattern = "^(\\d+(\\.\\d+)?\\s*ml|\\d+\\s*dose(s)?)$";
                break;
            case DROP:
                pattern = "^\\d+\\s*drop(s)?$";
                break;
            default:
                context.addMessage("type", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Invalid or missing medicine type.", null));
                return null;
        }

        if (!dosage.trim().toLowerCase().matches(pattern)) {
            context.addMessage("dosage", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Dosage format is invalid for type: " + type, null));
            return null;
        }

        // 4. Fetch Prescription Dates
        List<Date> prescriptionDates = providerDao.getPrescriptionDates(prescriptionId);
        if (prescriptionDates == null || prescriptionDates.size() != 2 ||
            prescriptionDates.get(0) == null || prescriptionDates.get(1) == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Could not retrieve valid prescription dates for validation.", null));
            return null;
        }

        Date prescriptionStart = prescriptionDates.get(0);
        Date prescriptionEnd = prescriptionDates.get(1);
        Date medStart = prescribedMedicines.getStartDate();
        Date medEnd = prescribedMedicines.getEndDate();

        // 5. Validate Medicine Date Range
        if (medStart == null) {
            context.addMessage("startDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "enter from which date to start taking medicine", null));
            return null;
        }
        if (medStart == null) {
            context.addMessage("endDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "enter till which date to take the medicines", null));
            return null;
        }
        if(medStart.after(medEnd))
        {
        	context.addMessage("endDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "end date (" + medEnd + ") cannot be after medicine start date (" + medStart + ").", null));
            return null;
        }
        if (medStart.before(prescriptionStart)) {
            context.addMessage("startDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Start date (" + medStart + ") cannot be before prescription start date (" + prescriptionStart + ").", null));
            return null;
        }

        if (medEnd.after(prescriptionEnd)) {
            context.addMessage("endDate", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "End date (" + medEnd + ") cannot be after prescription end date (" + prescriptionEnd + ").", null));
            return null;
        }

        // 6. Duration Validation
        long dayDiff = (medEnd.getTime() - medStart.getTime()) / (1000 * 60 * 60 * 24) + 1;

        try {
            int durationDays = Integer.parseInt(prescribedMedicines.getDuration().trim());

            if (durationDays != dayDiff) {
                context.addMessage("duration", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Duration (" + durationDays + " days) does not match actual period (" + dayDiff + " days).", null));
                return null;
            }
        } catch (NumberFormatException e) {
            context.addMessage("duration", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Duration must be a valid integer number.", null));
            return null;
        }

        // ✅ All validations passed
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
        
        return providerEjb.addPrescription(prescription);
    }
    public String createNewProcedure() throws ClassNotFoundException, SQLException
    {
    	procedure =new MedicalProcedure();
    	procedure.setProcedureId(providerEjb.generateNewProcedureId());
    	System.out.println("______________________________________new procedure created with values "+procedure);
    	return "AddMedicalProcedure?faces-redirect=true";
    }
    public String createNewPrescription() throws ClassNotFoundException, SQLException
    {
    	prescription =new Prescription();
    	prescription.setPrescriptionId(providerEjb.generateNewPrescriptionId());
    	return "AddPrescription?faces-redirect=true";
    }
    public String createNewPrescribedMedicine() throws ClassNotFoundException, SQLException
    {
    	prescribedMedicines =new PrescribedMedicines();
    	prescribedMedicines.setPrescribedId(providerEjb.generateNewPrescribedMedicineId());
    	return "AddPrescribedMedicine?faces-redirect=true";
    }
    public String createNewProcedureTest() throws ClassNotFoundException, SQLException
    {
    	procedureTest =new ProcedureTest();
    	
    	procedureTest.setTestId(providerEjb.generateNewProcedureTestId());
    	return "AddTest?faces-redirect=true";
    }
    public MedicalProcedure getProcedure() {
        return procedure;
    }


	public void setProcedure(MedicalProcedure procedure) {
		this.procedure = procedure;
	}

	public Prescription getPrescription() {
		return prescription;
	}

	public void setPrescription(Prescription prescription) {
		this.prescription = prescription;
	}

	public PrescribedMedicines getPrescribedMedicines() {
		return prescribedMedicines;
	}

	public void setPrescribedMedicines(PrescribedMedicines prescribedMedicines) {
		this.prescribedMedicines = prescribedMedicines;
	}

	public ProcedureTest getProcedureTest() {
		return procedureTest;
	}

	public void setProcedureTest(ProcedureTest procedureTest) {
		this.procedureTest = procedureTest;
	}

    public String procedureSubmit() {
        return "ProviderDashboard?faces-redirect=true";
    }

    public String prescriptionDetailsSubmit() {
        return "ProcedureDashboard?faces-redirect=true";
    }
}