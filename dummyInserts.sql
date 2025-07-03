-- Insurance Company
use healthsure;
INSERT INTO Insurance_company VALUES
('IC001', 'HealthPlus Insurance Co.', 'https://logo.healthplus.com', 'New Delhi, India', 'support@healthplus.com', '011-23456789');



-- Insurance Plans
INSERT INTO Insurance_plan VALUES
('PLAN001', 'IC001', 'Individual Shield Plan', 'SELF', 18, 65, 'Covers individual up to 5L', '300000,500000', '3 months', '2025-06-01', '2099-12-31', 'NO'),
('PLAN002', 'IC001', 'Family Secure Plan', 'FAMILY', 18, 65, 'Covers up to 5 family members', '500000,1000000', '6 months', '2025-06-01', '2099-12-31', 'YES');



-- Coverage Options
INSERT INTO Insurance_coverage_option VALUES
('COV001', 'PLAN001', 5000.00, 500000.00, 'ACTIVE'),
('COV002', 'PLAN002', 12000.00, 1000000.00, 'ACTIVE');



-- Recipients
INSERT INTO Recipient VALUES
('H001', 'Amit', 'Verma', '9876543210', 'amitv', 'MALE', '1990-01-15', '123, Sector 45, Delhi', CURRENT_TIMESTAMP, 'amit@123', 'amitv@example.com', 'ACTIVE', 0, NULL, NULL, CURRENT_TIMESTAMP),
('H002', 'Priya', 'Sharma', '9988776655', 'priyash', 'FEMALE', '1985-08-20', '456, MG Road, Mumbai', CURRENT_TIMESTAMP, 'priya@123', 'priya@example.com', 'ACTIVE', 0, NULL, NULL, CURRENT_TIMESTAMP);
INSERT INTO Recipient VALUES
('H003', 'Sonia', 'Kapoor', '9998887770', 'soniak', 'FEMALE', '1992-04-10', '789, South Ex, Delhi', CURRENT_TIMESTAMP, 'sonia@123', 'sonia@example.com', 'ACTIVE', 0, NULL, NULL, CURRENT_TIMESTAMP),
('H004', 'Aarav', 'Gupta', '8887776665', 'aaravg', 'MALE', '1995-06-15', '321, West Enclave, Mumbai', CURRENT_TIMESTAMP, 'aarav@123', 'aarav@example.com', 'ACTIVE', 0, NULL, NULL, CURRENT_TIMESTAMP),
('H005', 'Neha', 'Singh', '7776665554', 'nehas', 'FEMALE', '1993-12-22', '654, East End, Bangalore', CURRENT_TIMESTAMP, 'neha@123', 'neha@example.com', 'ACTIVE', 0, NULL, NULL, CURRENT_TIMESTAMP);


-- Subscribe Table
INSERT INTO subscribe VALUES
('SUB001', 'H001', 'COV001', '2023-08-01', '2024-08-01', 'INDIVIDUAL', 'ACTIVE', 5000.00, 5000.00),
('SUB002', 'H001', 'COV002', '2024-01-15', '2025-01-15', 'FAMILY', 'ACTIVE', 1000.00, 12000.00),
('SUB003', 'H001', 'COV002', '2024-10-10', '2025-10-10', 'Individual', 'ACTIVE', 500.00, 1000.00),
('SUB004', 'H001', 'COV002', '2023-12-05', '2024-12-05', 'FAMILY', 'ACTIVE', 1100.00, 1100.00);
INSERT INTO subscribe VALUES
('SUB005', 'H003', 'COV001', '2024-02-01', '2025-02-01', 'INDIVIDUAL', 'ACTIVE', 10000.00, 10000.00),
('SUB006', 'H004', 'COV001', '2023-11-15', '2024-11-15', 'INDIVIDUAL', 'ACTIVE', 5000.00, 5000.00),
('SUB007', 'H005', 'COV001', '2024-07-01', '2025-07-01', 'INDIVIDUAL', 'ACTIVE', 5000.00, 5000.00);
-- Subscribed Members
INSERT INTO subscribed_members VALUES
('MEM001', 'SUB002', 'Priya Sharma', 40, 'FEMALE', 'Self', 'AADHAR1234'),
('MEM002', 'SUB002', 'Rohan Sharma', 42, 'MALE', 'Spouse', 'AADHAR5678'),
('MEM003', 'SUB002', 'Ananya Sharma', 15, 'FEMALE', 'Child', 'AADHAR9101'),
('MEM004', 'SUB004', 'rohit Sharma', 15, 'MALE', 'child', 'AADHAR9101'),
('MEM005', 'SUB004', 'karn Sharma', 15, 'MALE', 'Child', 'AADHAR9102');
INSERT INTO subscribed_members VALUES
('MEM006', 'SUB005', 'Sonia Kapoor', 32, 'FEMALE', 'Self', 'AADHAR003'),
('MEM007', 'SUB006', 'Aarav Gupta', 30, 'MALE', 'Self', 'AADHAR004'),
('MEM008', 'SUB007', 'Neha Singh', 31, 'FEMALE', 'Self', 'AADHAR005');


-- Doctors
-- Providers
-- Providers
INSERT INTO Providers (provider_id, provider_name, hospital_name, email, address, city, state, zip_code, status)
VALUES
('PROV001', 'Dr. Mehta', 'Sunrise Hospital', 'mehta@sunrisehosp.com', '123 MG Road', 'Mumbai', 'Maharashtra', '400001', 'APPROVED'),
('PROV002', 'Dr. Kavita Sharma', 'GreenCare Hospital', 'kavita@greencare.org', '45 Nehru Street', 'Bengaluru', 'Karnataka', '560001', 'APPROVED');



-- Doctors
INSERT INTO Doctors (doctor_id, provider_id, doctor_name, qualification, specialization, license_no, email, address, gender, password, login_status, doctor_status) VALUES
('DOC001', 'PROV001', 'Dr. Alice Mehta', 'MBBS, MD', 'Cardiology', 'LIC1001', 'alice.mehta@medmail.com', '21 Heart Street, New Delhi', 'FEMALE', 'passAlice@123', 'APPROVED', 'ACTIVE'),
('DOC002', 'PROV001', 'Dr. Rajiv Menon', 'MBBS, MS', 'Orthopedics', 'LIC1002', 'rajiv.menon@medmail.com', '22 Bone Road, Mumbai', 'MALE', 'passRajiv@123', 'APPROVED', 'ACTIVE'),
('DOC003', 'PROV002', 'Dr. Nisha Reddy', 'MBBS, DGO', 'Gynecology', 'LIC1003', 'nisha.reddy@medmail.com', '23 Blossom Lane, Hyderabad', 'FEMALE', 'passNisha@123', 'APPROVED', 'ACTIVE');



-- Accounts
INSERT INTO Accounts (provider_id, bank_name, ifsc_code, account_number) VALUES
('PROV001', 'State Bank of India', 'SBIN0001234', '1234567890'),
('PROV002', 'ICICI Bank', 'ICIC0005678', '9876543210');



-- Otp_logs
INSERT INTO Otp_logs (user_type, otp_code, is_used) VALUES
('PROVIDER', '123456', FALSE),
('RECIPIENT', '654321', TRUE),
('PHARMACY', '112233', FALSE);



-- Provider_password
INSERT INTO Provider_password (provider_id, old_password, new_password) VALUES
('PROV001', 'oldPass123', 'newPass123'),
('PROV002', 'passOld456', 'passNew456');



-- Doctor_availability
INSERT INTO Doctor_availability (availability_id, doctor_id, available_date, start_time, end_time, slot_type, max_capacity, is_recurring, notes) VALUES
('AVAIL001', 'DOC001', '2025-07-01', '09:00:00', '12:00:00', 'STANDARD', 10, FALSE, 'Morning slot'),
('AVAIL002', 'DOC002', '2025-07-01', '14:00:00', '17:00:00', 'STANDARD', 8, FALSE, 'Afternoon slot'),
('AVAIL003', 'DOC003', '2025-07-01', '10:00:00', '13:00:00', 'STANDARD', 12, TRUE, 'Regular weekly');



-- Otp (Recipient
-- Appointments
INSERT INTO Appointment (appointment_id, doctor_id, h_id, availability_id, provider_id, requested_at, status, notes) VALUES
('APT001', 'DOC001', 'H001', 'AVAIL001', 'PROV001', '2025-06-01 10:00:00', 'BOOKED', 'Routine Checkup'),
('APT002', 'DOC002', 'H002', 'AVAIL002', 'PROV001', '2025-06-02 14:30:00', 'BOOKED', 'Follow-up Visit');
INSERT INTO Appointment (appointment_id, doctor_id, h_id, availability_id, provider_id, requested_at, status, notes) VALUES
('APT007', 'DOC002', 'H001', 'AVAIL001', 'PROV001', '2025-07-01 09:15:00', 'BOOKED', 'Initial consultation'),
('APT006', 'DOC002', 'H003', 'AVAIL001', 'PROV001', '2025-07-01 09:15:00', 'BOOKED', 'Initial consultation');
INSERT INTO Appointment (appointment_id, doctor_id, h_id, availability_id, provider_id, requested_at, status, notes) VALUES
('APT003', 'DOC001', 'H003', 'AVAIL001', 'PROV001', '2025-07-01 09:15:00', 'BOOKED', 'Initial consultation'),
('APT004', 'DOC001', 'H004', 'AVAIL001', 'PROV001', '2025-07-01 09:45:00', 'BOOKED', 'Back pain evaluation'),
('APT005', 'DOC001', 'H005', 'AVAIL001', 'PROV001', '2025-07-01 10:15:00', 'BOOKED', 'Follow-up visit');

-- Medical Procedures
INSERT INTO medical_procedure VALUES
('PROC001', 'APT001', 'H001', 'PROV001', 'DOC001', '2025-06-03', 'Appendicitis', 'Surgery recommended', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
('PROC002', 'APT002', 'H002', 'PROV001', 'DOC002', '2025-06-04', 'Fracture in arm', 'Cast applied; rest for 6 weeks', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);
INSERT INTO medical_procedure VALUES
('PROC003', 'APT006', 'H003', 'PROV001', 'DOC002', '2025-07-01', 'Cold & fever', 'Rest and medication advised', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
('PROC004', 'APT007', 'H004', 'PROV001', 'DOC002', '2025-07-01', 'Sprain', 'Bandage and 1 week rest', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
('PROC005', 'APT005', 'H005', 'PROV001', 'DOC001', '2025-07-01', 'Thyroid checkup', 'Blood test prescribed', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
('PROC008', 'APT003', 'H003', 'PROV001', 'DOC001', '2025-07-01', 'Migraine', 'Medication and rest advised', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
('PROC009', 'APT004', 'H004', 'PROV001', 'DOC001', '2025-07-01', 'Lower back pain', 'Physiotherapy suggested', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
('PROC0010', 'APT005', 'H005', 'PROV001', 'DOC001', '2025-07-01', 'Thyroid checkup', 'Blood test prescribed', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);


-- Claims
INSERT INTO Claims VALUES
('CLM001', 'SUB001', 'PROC001', 'PROV001', 'H001', 'APPROVED', '2025-06-05 19:06:47', 10000.00, 8000.00),
('CLM002', 'SUB002', 'PROC002', 'PROV001', 'H002', 'APPROVED', '2025-06-05 19:06:47', 15000.00, 12000.00);
INSERT INTO Claims VALUES
('CLM003', 'SUB005', 'PROC003', 'PROV001', 'H003', 'APPROVED', CURRENT_TIMESTAMP, 3000.00, 2500.00),
('CLM004', 'SUB006', 'PROC004', 'PROV001', 'H004', 'APPROVED', CURRENT_TIMESTAMP, 4000.00, 3000.00),
('CLM005', 'SUB007', 'PROC005', 'PROV001', 'H005', 'APPROVED', CURRENT_TIMESTAMP, 2000.00, 1800.00);


-- Claim_history
INSERT INTO Claim_history VALUES
('CH001', 'CLM001', 'Claim approved successfully', '2025-06-06 10:00:00'),
('CH002', 'CLM002', 'Claim approved after review', '2025-06-06 11:30:00');
 INSERT INTO Claim_history VALUES
('CH003', 'CLM003', 'Claim approved successfully', CURRENT_TIMESTAMP),
('CH004', 'CLM004', 'Claim processed and approved', CURRENT_TIMESTAMP),
('CH005', 'CLM005', 'Claim approved after verification', CURRENT_TIMESTAMP);