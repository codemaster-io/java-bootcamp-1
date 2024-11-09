
CREATE TABLE users (
   id INT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(100),
   email VARCHAR(100),
   age INT,
   country VARCHAR(50)
);

CREATE TABLE audit (
   audit_id INT PRIMARY KEY AUTO_INCREMENT,
   user_name VARCHAR(20),   --- if user name cross 20 lenght, trnsaction rollback example
   operation_type VARCHAR(50),
   timestamp TIMESTAMP
);

-- Create login_info table
CREATE TABLE email_password (
    email VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

-- Insert sample values into login_info
INSERT INTO email_password (email, password) VALUES ('admin@gmail.com', 'password123');
INSERT INTO email_password (email, password) VALUES ('manager@gmail.com', 'securePass!45');

