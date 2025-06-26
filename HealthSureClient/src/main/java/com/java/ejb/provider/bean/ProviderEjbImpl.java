package com.java.ejb.provider.bean;

import java.sql.SQLException;

import javax.naming.NamingException;

import com.java.ejb.provider.model.MedicalProcedure;
import com.java.ejb.provider.model.Prescription;
import com.java.ejb.provider.model.ProcedureTest;

public class ProviderEjbImpl {
static ProviderBeanRemote remote;
static
{
	try {
		remote=ProviderRemoteHelper.lookupRemoteStatelessProvider();
	} catch (NamingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
public String addMedicalProcedure(MedicalProcedure medicalProcedure) throws ClassNotFoundException, SQLException
{
	
	return remote.addMedicalProcedure(medicalProcedure);
}
public String addPrescription(Prescription prescription) throws ClassNotFoundException, SQLException
{
	remote.addPrescription(prescription);
	return "AddPrescription?faces-redirect=true";
}
public String addTest(ProcedureTest test) throws ClassNotFoundException, SQLException
{
	remote.addTest(test);
	return "AddTest?faces-redirect=true";
}
}
