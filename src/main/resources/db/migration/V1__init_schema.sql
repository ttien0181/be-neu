-- =============================
-- 1. CATEGORIES
-- =============================
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

INSERT INTO categories (name, description) VALUES
('Tranh chấp hợp đồng thương mại', 'Các vụ kiện liên quan đến hợp đồng mua bán, cung ứng dịch vụ, thuê tài sản...'),
('Ly hôn và hôn nhân gia đình', 'Các vụ án liên quan đến ly hôn, tranh chấp tài sản, quyền nuôi con.'),
('Lao động', 'Các vụ kiện giữa người lao động và người sử dụng lao động.'),
('Hành chính', 'Các vụ khiếu kiện quyết định hành chính, hành vi hành chính của cơ quan nhà nước.'),
('Đất đai', 'Tranh chấp quyền sử dụng đất, bồi thường giải phóng mặt bằng.'),
('Sở hữu trí tuệ', 'Tranh chấp về bản quyền, nhãn hiệu, sáng chế.'),
('Doanh nghiệp', 'Các tranh chấp giữa cổ đông, thành viên công ty.');

-- =============================
-- 2. CASES
-- =============================
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

INSERT INTO cases (category_id, case_name, case_description, status, court_name, location) VALUES
(1, 'Công ty A kiện Công ty B vì vi phạm hợp đồng cung ứng', 'Công ty B không giao hàng đúng tiến độ.', 'Đang xét xử', 'TAND TP. Hà Nội', 'Hà Nội'),
(2, 'Ly hôn giữa Nguyễn Văn An và Trần Thị Bình', 'Tranh chấp quyền nuôi con và tài sản.', 'Đã giải quyết', 'TAND Quận 1', 'TP. Hồ Chí Minh'),
(3, 'Nguyễn Văn Dũng kiện Công ty TNHH XYZ', 'Chấm dứt hợp đồng lao động trái luật.', 'Đang thụ lý', 'TAND TP. Đà Nẵng', 'Đà Nẵng');

-- =============================
-- 3. PERSONS
-- =============================
CREATE TABLE persons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    role ENUM('plaintiff', 'defendant', 'lawyer'),
    contact_info VARCHAR(255)
);

INSERT INTO persons (name, role, contact_info) VALUES
('Đinh Quang Đăng', 'lawyer', 'dangdien1223@gmail.com'),
('Nguyễn Thị Mai', 'lawyer', 'mai.lawyer@firm.vn'),
('Phạm Văn Hùng', 'plaintiff', 'hungpham@gmail.com');

-- =============================
-- 4. CASE_PERSONS
-- =============================
CREATE TABLE case_persons (
    case_id INT,
    person_id INT,
    PRIMARY KEY (case_id, person_id),
    CONSTRAINT fk_case_person_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE CASCADE,
    CONSTRAINT fk_case_person_person FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE RESTRICT
);

INSERT INTO case_persons (case_id, person_id) VALUES
(1, 1), (1, 3),
(2, 2), (2, 3),
(3, 1), (3, 3);

-- =============================
-- 5. TAGS
-- =============================
CREATE TABLE case_tags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO case_tags (tag_name) VALUES
('Thương mại'), ('Gia đình'), ('Lao động'), ('Đất đai'), ('Sở hữu trí tuệ');

CREATE TABLE case_case_tags (
    case_id INT,
    tag_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (case_id, tag_id),
    CONSTRAINT fk_case_tag_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE CASCADE,
    CONSTRAINT fk_case_tag_tag FOREIGN KEY (tag_id) REFERENCES case_tags(id) ON DELETE CASCADE
);

INSERT INTO case_case_tags (case_id, tag_id) VALUES
(1, 1), (2, 2), (3, 3);

-- =============================
-- 6. USERS
-- =============================
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO users (username, password, role, email) VALUES
('admin', '$2a$12$NhtvMcr0F32iJJXNY82S3OMe5SWNzFaACtrpqhxneZlGIuMAOa6aO', 'ADMIN', 'admin@lawfirm.vn'),
('dang', '$2a$12$/ACaiuD1MpfqmTDG.0XuxOlWlSbBBA7y77yMEz0TZN3r7V8uPTyv6', 'USER', 'dangdien1223@gmail.com'),
('mai', '$2a$12$4au3AaYdMhzwHDU8n3iNY.V1nHJN3vdVgpluD/QlRp7Ap4WievQ.a', 'USER', 'mai.lawyer@firm.vn'),
('hung', '$2a$12$rvuErAe42zpZtE8gsOi9R.g5CcOtbszcnL0.Ws9xLCNI1YoxDI9h.', 'USER', 'hungpham@gmail.com');

-- =============================
-- 7. CASE FILES
-- =============================
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

INSERT INTO case_files (case_id, file_name, file_path, file_type, uploaded_by) VALUES
(1, 'hop_dong_mua_ban.pdf', '/uploads/files/1/hop_dong_mua_ban.pdf', 'pdf', 1),
(2, 'don_ly_hon.pdf', '/uploads/files/2/don_ly_hon.pdf', 'pdf', 2),
(3, 'hop_dong_lao_dong.docx', '/uploads/files/3/hop_dong_lao_dong.docx', 'docx', 2);

-- =============================
-- 8. AUDIT LOGS
-- =============================
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

INSERT INTO audit_logs (user_id, action, case_id, file_id) VALUES
(1, 'Tạo vụ án mới', 1, NULL),
(2, 'Tải lên hồ sơ vụ án', 2, 2),
(2, 'Chỉnh sửa thông tin vụ án', 3, NULL);

-- =============================
-- 9. VERIFICATION CODES
-- =============================
CREATE TABLE verification_codes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    code VARCHAR(255) NOT NULL,
    type ENUM('REGISTRATION', 'PASSWORD_RESET') NOT NULL,
    expiry_time DATETIME NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE
);

INSERT INTO verification_codes (email, code, type, expiry_time, used) VALUES
('newuser@lawfirm.vn', '123456', 'REGISTRATION', DATE_ADD(NOW(), INTERVAL 10 MINUTE), FALSE);

-- =============================
-- 10. QUESTIONS
-- =============================
CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_questioner INT NOT NULL,
    id_lawyer INT NULL,
    case_id INT NULL,
    content VARCHAR(500) NOT NULL,
    answer TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_question_user_questioner FOREIGN KEY (id_questioner) REFERENCES users(id) ON DELETE RESTRICT,
    CONSTRAINT fk_question_person_lawyer FOREIGN KEY (id_lawyer) REFERENCES persons(id) ON DELETE RESTRICT,
    CONSTRAINT fk_question_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE SET NULL
);

INSERT INTO questions (id_questioner, id_lawyer, case_id, content, answer) VALUES
(2, 1, 1, 'Tôi cần tư vấn cách nộp hồ sơ khởi kiện đúng quy định.', 'Bạn cần nộp tại TAND nơi bị đơn cư trú.'),
(4, 2, 2, 'Ly hôn có bắt buộc hòa giải không?', 'Có, tòa án sẽ tổ chức hòa giải trước khi xét xử.'),
(3, 1, 3, 'Nếu bị sa thải trái luật, tôi có được bồi thường không?', 'Có, bạn được nhận lương và bồi thường thiệt hại.');

-- =============================
-- 11. APPOINTMENTS
-- =============================
CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_user INT NOT NULL,
    id_lawyer INT NOT NULL,
    appointment_time DATETIME NOT NULL,
    notes TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    CONSTRAINT fk_appointments_user FOREIGN KEY (id_user) REFERENCES users(id),
    CONSTRAINT fk_appointments_lawyer FOREIGN KEY (id_lawyer) REFERENCES persons(id) ON DELETE RESTRICT
);

INSERT INTO appointments (id_user, id_lawyer, appointment_time, notes) VALUES
(2, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), 'Trao đổi về vụ việc thương mại.'),
(4, 2, DATE_ADD(NOW(), INTERVAL 2 DAY), 'Tư vấn ly hôn.'),
(3, 1, DATE_ADD(NOW(), INTERVAL 3 DAY), 'Tư vấn lao động.');


