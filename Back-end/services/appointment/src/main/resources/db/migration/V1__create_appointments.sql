CREATE TABLE IF NOT EXISTS doctor_daily_serials (
  id BIGSERIAL PRIMARY KEY,
  doctor_id VARCHAR(255) NOT NULL,
  service_date DATE NOT NULL,
  last_serial INT NOT NULL DEFAULT 0,
  CONSTRAINT uk_doctor_service_date UNIQUE (doctor_id, service_date)
);

CREATE TABLE IF NOT EXISTS appointments (
  id BIGSERIAL PRIMARY KEY,
  patient_id VARCHAR(255) NOT NULL,
  doctor_id VARCHAR(255) NOT NULL,
  appointment_date DATE NOT NULL,
  time_slot VARCHAR(100) NOT NULL,
  serial_number INT NOT NULL,
  status VARCHAR(20) NOT NULL,
  reason VARCHAR(1000),
  created_at TIMESTAMP NOT NULL DEFAULT NOW(),
  updated_at TIMESTAMP NOT NULL DEFAULT NOW()
);