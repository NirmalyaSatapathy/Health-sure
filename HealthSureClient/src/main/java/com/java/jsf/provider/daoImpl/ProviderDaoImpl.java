package com.java.jsf.provider.daoImpl;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.java.ejb.provider.model.Appointment;
import com.java.ejb.provider.model.Doctor;
import com.java.ejb.provider.model.MedicalProcedure;
import com.java.ejb.provider.model.Provider;
import com.java.ejb.recipient.model.Recipient;
import com.java.jsf.util.Converter;


public class ProviderDaoImpl {
	 static SessionFactory sessionFactory;
	    static {
	        sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
	    }
	  public Recipient searchRecipientByHealthId(String healthId) {
	        Session session = sessionFactory.openSession();
	        com.java.jsf.recipient.model.Recipient recipient=null;
	        recipient= (com.java.jsf.recipient.model.Recipient) session.get(com.java.jsf.recipient.model.Recipient.class, healthId);
	        session.close();
	        return Converter.convertToEJBRecipient(recipient);
	        }
	    public Doctor searchDoctorById(String doctorId) {
	        Session session = sessionFactory.openSession();
	        com.java.jsf.provider.model.Doctor doctor=null;
	        doctor=(com.java.jsf.provider.model.Doctor) session.get(com.java.jsf.provider.model.Doctor .class, doctorId);
	        session.close();
	       return Converter.convertToEJBDoctor(doctor);
	    }
	    public Provider searchProviderById(String providerId) {
	        Session session = sessionFactory.openSession();
	        com.java.jsf.provider.model.Provider provider=null;
	        provider=( com.java.jsf.provider.model.Provider) session.get( com.java.jsf.provider.model.Provider.class, providerId);
	        session.close();
	        return Converter.convertToEJBProvider(provider);
	    }
	   public Appointment searchAppointmentById(String appointmentId) {
	        Session session = sessionFactory.openSession();
	        com.java.jsf.provider.model.Appointment appointment=null;
	        appointment=(com.java.jsf.provider.model.Appointment) session.get(com.java.jsf.provider.model.Appointment.class, appointmentId);
	        session.close();
	        return Converter.convertToEJBAppointment(appointment);
	    }
	   public Date getProcedureStartDate(String procedureId1) {
	        Date startDate = null;
	            Session session = sessionFactory.openSession();
	            Transaction tx = session.beginTransaction();
	            com.java.jsf.provider.model.MedicalProcedure procedure = (com.java.jsf.provider.model.MedicalProcedure) session.get(com.java.jsf.provider.model.MedicalProcedure.class, procedureId1);

	            if (procedure != null) {
	            	startDate = procedure.getFromDate();
	            }
	            tx.commit();
	            session.close();
	        return startDate;
	    }
}
