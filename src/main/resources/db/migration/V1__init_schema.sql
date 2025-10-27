CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

CREATE TABLE cases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT,
    case_name VARCHAR(255),
    case_description TEXT,
    status VARCHAR(100),
    court_name VARCHAR(255),
    location VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_cases_category FOREIGN KEY (category_id) REFERENCES categories(id)
);

CREATE TABLE persons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    role ENUM('plaintiff', 'defendant', 'lawyer'),
    contact_info VARCHAR(255)
);

CREATE TABLE case_persons (
    case_id INT,
    person_id INT,
    PRIMARY KEY (case_id, person_id),
    CONSTRAINT fk_case_person_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE CASCADE,
    CONSTRAINT fk_case_person_person FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE
);

CREATE TABLE case_tags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE case_case_tags (
    case_id INT,
    tag_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (case_id, tag_id),
    CONSTRAINT fk_case_tag_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE CASCADE,
    CONSTRAINT fk_case_tag_tag FOREIGN KEY (tag_id) REFERENCES case_tags(id) ON DELETE CASCADE
);

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL,
    email VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE case_files (
    id INT AUTO_INCREMENT PRIMARY KEY,
    case_id INT,
    file_name VARCHAR(255),
    file_path VARCHAR(255),
    file_type VARCHAR(50),
    uploaded_by INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_case_file_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE CASCADE,
    CONSTRAINT fk_case_file_user FOREIGN KEY (uploaded_by) REFERENCES users(id) ON DELETE SET NULL
);

CREATE TABLE audit_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    action VARCHAR(100),
    case_id INT,
    file_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL,
    CONSTRAINT fk_audit_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE SET NULL,
    CONSTRAINT fk_audit_file FOREIGN KEY (file_id) REFERENCES case_files(id) ON DELETE SET NULL
);

INSERT INTO categories (name, description) VALUES
('Category 1', 'Description for Category 1'),
('Category 2', 'Description for Category 2');

INSERT INTO cases (category_id, case_name, case_description, status, court_name, location) VALUES
(1, 'Case 1', 'Description for Case 1', 'Open', 'Court A', 'Location A'),
(2, 'Case 2', 'Description for Case 2', 'Closed', 'Court B', 'Location B');

INSERT INTO persons (name, role, contact_info) VALUES
('John Doe', 'plaintiff', 'john.doe@example.com'),
('Jane Smith', 'defendant', 'jane.smith@example.com');

INSERT INTO case_persons (case_id, person_id) VALUES
(1, 1),
(2, 2);

INSERT INTO case_tags (tag_name) VALUES
('Tag 1'),
('Tag 2');

INSERT INTO case_case_tags (case_id, tag_id) VALUES
(1, 1),
(2, 2);

INSERT INTO users (username, password, role, email) VALUES
('user1', '1', 'user', 'user1@example.com'),
('user2', '2', 'user', 'user2@example.com');

INSERT INTO case_files (case_id, file_name, file_path, file_type, uploaded_by) VALUES
(1, 'file1.txt', '/files/file1.txt', 'text', 1),
(2, 'file2.pdf', '/files/file2.pdf', 'pdf', 2);

INSERT INTO audit_logs (user_id, action, case_id, file_id) VALUES
(1, 'Created case', 1, NULL),
(2, 'Uploaded file', 2, 2);
