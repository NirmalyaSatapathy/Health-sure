package com.java.ejb.provider.model;

import java.io.Serializable;
import java.util.Date;

import com.java.ejb.recipient.model.Recipient;

public class Prescription implements Serializable{

    private String prescriptionId;

    // Foreign key object mappings
    private MedicalProcedure procedure;    // mapped from procedure_id
    private Recipient recipient;           // mapped from h_id
    private Provider provider;             // mapped from provider_id
    private Doctor doctor;                 // mapped from doctor_id

    // Other fields
    private String medicineName;
    private String dosage;
    private String duration;
    private String notes;
    private Date writtenOn;
    private Date createdAt;

    // Getters and Setters
    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public MedicalProcedure getProcedure() {
        return procedure;
    }

    public void setProcedure(MedicalProcedure procedure) {
        this.procedure = procedure;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getWrittenOn() {
        return writtenOn;
    }

    public Prescription() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setWrittenOn(Date writtenOn) {
        this.writtenOn = writtenOn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
