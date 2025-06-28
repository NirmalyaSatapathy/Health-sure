package com.java.jsf.provider.controller;

import java.sql.SQLException;
import java.util.List;

import javax.faces.context.FacesContext;

import com.java.ejb.provider.bean.ProviderEjbImpl;
import com.java.ejb.provider.model.MedicalProcedure;
import com.java.ejb.provider.model.PrescribedMedicines;
import com.java.ejb.provider.model.Prescription;
import com.java.ejb.provider.model.ProcedureTest;
import com.java.jsf.provider.daoImpl.InsuranceDaoImpl;
import com.java.jsf.provider.model.PatientInsuranceDetails;

public class ProviderController {
ProviderEjbImpl providerEjb;
MedicalProcedure medicalProcedure;
List<PatientInsuranceDetails> patientInsuranceList;
PatientInsuranceDetails selectedItem;
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
public String addMedicalProcedureController(MedicalProcedure medicalProcedure) throws ClassNotFoundException, SQLException
{
	System.out.println("in controller");
	 return  providerEjb.addMedicalProcedure(medicalProcedure);
}
public String addPresribedMedicinesController(PrescribedMedicines prescribedMedicines) throws ClassNotFoundException, SQLException
{
	
	return providerEjb.addPrescribedMedicines(prescribedMedicines);
}
public String addPrescriptionController(Prescription prescription) throws ClassNotFoundException, SQLException
{
	return providerEjb.addPrescription(prescription);
}
public String addTestController(ProcedureTest procedureTest) throws ClassNotFoundException, SQLException
{
	return providerEjb.addTest(procedureTest);
}
public String procedureSubmit()
{
	return "ProviderDashboard?faces-redirect=true";
}
public String prescribedMedicinesSubmit()
{
	return "ProcedureDashboard?faces-redirect=true";
}
public List<PatientInsuranceDetails> showInsuranceDetailsController(String hId)
{
	patientInsuranceList= insuranceDaoImpl.showInsuranceOfRecipient(hId);
	return patientInsuranceList;
}

public String redirect(PatientInsuranceDetails insurance) {
	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedInsurance", insurance);
    return "viewMembers?faces-redirect=true";
}
public PatientInsuranceDetails loadSubscribedMembers() {
        Object obj = FacesContext.getCurrentInstance()
                                 .getExternalContext()
                                 .getSessionMap()
                                 .get("selectedInsurance");
        
    return (PatientInsuranceDetails)obj;
}

}
