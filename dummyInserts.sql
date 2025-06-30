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



-- Subscribe Table
INSERT INTO subscribe VALUES
('SUB001', 'H001', 'COV001', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 YEAR), 'INDIVIDUAL', 'ACTIVE', 5000.00, 5000.00),
('SUB002', 'H001', 'COV002', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 YEAR), 'FAMILY', 'ACTIVE', 12000.00, 12000.00),
('SUB003', 'H001', 'COV002', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 YEAR), 'Individual', 'ACTIVE', 12000.00, 12000.00),
('SUB004', 'H001', 'COV002', CURDATE(), DATE_ADD(CURDATE(), INTERVAL 1 YEAR), 'FAMILY', 'ACTIVE', 12000.00, 12000.00);


-- Subscribed Members
INSERT INTO subscribed_members VALUES
('MEM001', 'SUB002', 'Priya Sharma', 40, 'FEMALE', 'Self', 'AADHAR1234'),
('MEM002', 'SUB002', 'Rohan Sharma', 42, 'MALE', 'Spouse', 'AADHAR5678'),
('MEM003', 'SUB002', 'Ananya Sharma', 15, 'FEMALE', 'Child', 'AADHAR9101'),
('MEM004', 'SUB004', 'rohit Sharma', 15, 'MALE', 'child', 'AADHAR9101'),
('MEM005', 'SUB004', 'karn Sharma', 15, 'MALE', 'Child', 'AADHAR9102');



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



-- Medical Procedures
INSERT INTO medical_procedure VALUES
('PROC001', 'APT001', 'H001', 'PROV001', 'DOC001', '2025-06-03', 'Appendicitis', 'Surgery recommended', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP),
('PROC002', 'APT002', 'H002', 'PROV001', 'DOC002', '2025-06-04', 'Fracture in arm', 'Cast applied; rest for 6 weeks', CURRENT_TIMESTAMP, NULL, CURRENT_TIMESTAMP);



-- Claims
INSERT INTO Claims VALUES
('CLM001', 'SUB001', 'PROC001', 'PROV001', 'H001', 'APPROVED', '2025-06-05 19:06:47', 10000.00, 8000.00),
('CLM002', 'SUB002', 'PROC002', 'PROV001', 'H002', 'APPROVED', '2025-06-05 19:06:47', 15000.00, 12000.00);



-- Claim_history
INSERT INTO Claim_history VALUES
('CH001', 'CLM001', 'Claim approved successfully', '2025-06-06 10:00:00'),
('CH002', 'CLM002', 'Claim approved after review', '2025-06-06 11:30:00');
 