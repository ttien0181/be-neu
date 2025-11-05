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
('Hành chính', 'Các vụ khiếu kiện quyết định hành chính, hành vi hành chính của cơ quan nhà nước.');

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
(1, 'Công ty A kiện Công ty B vì vi phạm hợp đồng cung ứng', 'Công ty B không thực hiện đúng tiến độ giao hàng theo hợp đồng ký kết.', 'Đang xét xử', 'Tòa án nhân dân TP. Hà Nội', 'Hà Nội'),
(2, 'Ly hôn giữa Nguyễn Văn An và Trần Thị Bình', 'Tranh chấp quyền nuôi con và phân chia tài sản chung sau ly hôn.', 'Đã giải quyết', 'Tòa án nhân dân quận 1', 'TP. Hồ Chí Minh'),
(3, 'Nguyễn Văn Dũng kiện Công ty TNHH XYZ', 'Tranh chấp về việc chấm dứt hợp đồng lao động trái luật.', 'Đang thụ lý', 'Tòa án nhân dân TP. Đà Nẵng', 'Đà Nẵng');

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
('Nguyễn Văn An', 'plaintiff', 'an.nguyen@example.com'),
('Trần Thị Bình', 'defendant', 'binh.tran@example.com'),
('Luật sư Phạm Hữu Minh', 'lawyer', 'minh.lawyer@firm.vn'),
('Nguyễn Văn Dũng', 'plaintiff', 'dung.nguyen@example.com'),
('Công ty TNHH XYZ', 'defendant', 'contact@xyzcorp.vn');

-- =============================
-- 4. CASE_PERSONS
-- =============================
CREATE TABLE case_persons (
    case_id INT,
    person_id INT,
    PRIMARY KEY (case_id, person_id),
    CONSTRAINT fk_case_person_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE CASCADE,
    CONSTRAINT fk_case_person_person FOREIGN KEY (person_id) REFERENCES persons(id) ON DELETE CASCADE
);

INSERT INTO case_persons (case_id, person_id) VALUES
(1, 3), -- luật sư
(1, 1),
(2, 2),
(3, 4),
(3, 5);

-- =============================
-- 5. TAGS
-- =============================
CREATE TABLE case_tags (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tag_name VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO case_tags (tag_name) VALUES
('Thương mại'),
('Gia đình'),
('Lao động'),
('Hành chính'),
('Bồi thường thiệt hại');

CREATE TABLE case_case_tags (
    case_id INT,
    tag_id INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (case_id, tag_id),
    CONSTRAINT fk_case_tag_case FOREIGN KEY (case_id) REFERENCES cases(id) ON DELETE CASCADE,
    CONSTRAINT fk_case_tag_tag FOREIGN KEY (tag_id) REFERENCES case_tags(id) ON DELETE CASCADE
);

INSERT INTO case_case_tags (case_id, tag_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- =============================
-- 6. USERS
-- =============================
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'USER') NOT NULL,
    email VARCHAR(255) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

INSERT INTO users (username, password, role, email) VALUES
('admin', '$2a$12$NhtvMcr0F32iJJXNY82S3OMe5SWNzFaACtrpqhxneZlGIuMAOa6aO', 'ADMIN', 'admin@lawfirm.vn'), -- password: admin
('luatsu_minh', '$2a$10$XQy3YzYwqM/2wGHlLuvQvOC3NPOX1rw8zDlT5e1zA2qzZzTo6Bb9q', 'USER', 'minh.lawyer@firm.vn'); -- password: 123456

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
