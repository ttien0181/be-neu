-- ========================================
-- 🔹 1. CREATE TABLES
-- ========================================

-- =============================
-- 1. CATEGORIES
-- =============================
CREATE TABLE categories (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

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

-- =============================
-- 3. PERSONS
-- =============================
CREATE TABLE persons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    role ENUM('plaintiff', 'defendant', 'lawyer'),
    contact_info VARCHAR(255)
);

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

-- =============================
-- 5. TAGS
-- =============================
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

-- ========================================
-- 🔹 2. INSERT DATA
-- ========================================

-- CATEGORIES
INSERT INTO categories (name, description) VALUES
('Tranh chấp hợp đồng thương mại', 'Các vụ kiện liên quan đến hợp đồng mua bán, cung ứng dịch vụ, thuê tài sản...'),
('Ly hôn và hôn nhân gia đình', 'Các vụ án liên quan đến ly hôn, tranh chấp tài sản, quyền nuôi con.'),
('Lao động', 'Các vụ kiện giữa người lao động và người sử dụng lao động.'),
('Hành chính', 'Các vụ khiếu kiện quyết định hành chính, hành vi hành chính của cơ quan nhà nước.'),
('Đất đai', 'Tranh chấp quyền sử dụng đất, bồi thường giải phóng mặt bằng.'),
('Sở hữu trí tuệ', 'Tranh chấp về bản quyền, nhãn hiệu, sáng chế.'),
('Doanh nghiệp', 'Các tranh chấp giữa cổ đông, thành viên công ty.'),
('Dân sự', 'Các tranh chấp chủ yếu liên quan đến tài sản, hợp đồng, quyền sở hữu, thừa kế và nghĩa vụ bồi thường thiệt hại trong đời sống.'),
('Hình sự', 'lĩnh vực quy định về tội phạm và hình phạt, nhằm bảo vệ trật tự, an toàn xã hội, quyền và lợi ích hợp pháp của Nhà nước, tổ chức và cá nhân.');

-- CASES
INSERT INTO cases (category_id, case_name, case_description, status, court_name, location, created_at, updated_at) VALUES
(1, 'Công ty A kiện Công ty B vi phạm hợp đồng cung ứng', 'Công ty B không giao hàng đúng tiến độ.', 'Đang xét xử', 'TAND TP. Hà Nội', 'Hà Nội', '2025-01-05', '2025-01-10'),
(2, 'Ly hôn giữa Nguyễn Văn An và Trần Thị Bình', 'Tranh chấp quyền nuôi con và tài sản.', 'Đã giải quyết', 'TAND Quận 1', 'TP. Hồ Chí Minh', '2025-01-15', '2025-01-20'),
(3, 'Nguyễn Văn Dũng kiện Công ty TNHH XYZ', 'Chấm dứt hợp đồng lao động trái luật.', 'Đang thụ lý', 'TAND TP. Đà Nẵng', 'Đà Nẵng', '2025-01-25', '2025-01-30'),
(4, 'Người dân kiện UBND quận về quyết định hành chính sai phạm', 'Tranh chấp hành chính.', 'Đang xét xử', 'TAND TP. Hải Phòng', 'Hải Phòng', '2025-02-02', '2025-02-05'),
(5, 'Tranh chấp đất giữa ông Trần và bà Lê', 'Mâu thuẫn quyền sử dụng đất tại quận 9.', 'Đang hòa giải', 'TAND TP. Hồ Chí Minh', 'TP. Hồ Chí Minh', '2025-02-10', '2025-02-15'),
(6, 'Công ty ABC kiện xâm phạm nhãn hiệu', 'Công ty DEF sử dụng logo tương tự gây nhầm lẫn thương hiệu.', 'Đang thụ lý', 'TAND TP. Hà Nội', 'Hà Nội', '2025-02-20', '2025-02-25'),
(7, 'Tranh chấp cổ phần giữa các thành viên Công ty TNHH Minh Phát', 'Một thành viên không góp đủ vốn như cam kết.', 'Đang hòa giải', 'TAND TP. Hồ Chí Minh', 'TP. Hồ Chí Minh', '2025-03-01', '2025-03-05'),
(3, 'Nhân viên kiện công ty vì không đóng bảo hiểm xã hội', 'Công ty chậm nộp và không thực hiện đầy đủ nghĩa vụ bảo hiểm.', 'Đang xét xử', 'TAND TP. Đà Nẵng', 'Đà Nẵng', '2025-03-10', '2025-03-15'),
(5, 'Tranh chấp ranh giới đất tại phường Trung Hòa', 'Hai hộ dân tranh chấp phần đất 30m² chưa có sổ đỏ.', 'Đang hòa giải', 'TAND Quận Cầu Giấy', 'Hà Nội', '2025-03-20', '2025-03-25'),
(4, 'Khiếu kiện quyết định thu hồi đất sai phạm', 'Công dân cho rằng UBND quận ra quyết định thu hồi không đúng trình tự.', 'Đang thụ lý', 'TAND TP. Hải Phòng', 'Hải Phòng', '2025-04-01', '2025-04-05'),
(2, 'Ly hôn giữa ông Trần và bà Lê', 'Tranh chấp quyền nuôi con sau ly hôn.', 'Đang xét xử', 'TAND Quận 3', 'TP. Hồ Chí Minh', '2025-04-10', '2025-04-15'),
(1, 'Doanh nghiệp X yêu cầu bồi thường hợp đồng thuê kho', 'Bên thuê không thanh toán đúng hạn theo hợp đồng.', 'Đã giải quyết', 'TAND TP. Cần Thơ', 'Cần Thơ', '2025-04-20', '2025-04-25'),
(6, 'Tác giả kiện công ty sử dụng tác phẩm không xin phép', 'Tác phẩm âm nhạc bị sử dụng trong quảng cáo mà không trả phí bản quyền.', 'Đang thụ lý', 'TAND TP. Hà Nội', 'Hà Nội', '2025-05-01', '2025-05-05'),
(7, 'Tranh chấp quyền điều hành trong công ty cổ phần ABC', 'Các cổ đông mâu thuẫn về quyền biểu quyết trong Đại hội đồng cổ đông.', 'Đang xét xử', 'TAND TP. Hồ Chí Minh', 'TP. Hồ Chí Minh', '2025-05-10', '2025-05-15'),
(3, 'Người lao động kiện công ty vì sa thải trái pháp luật', 'Công ty không tuân thủ quy trình xử lý kỷ luật lao động.', 'Đã giải quyết', 'TAND TP. Đà Nẵng', 'Đà Nẵng', '2025-05-20', '2025-05-25'),
(5, 'Tranh chấp bồi thường khi Nhà nước thu hồi đất', 'Người dân yêu cầu mức bồi thường cao hơn do giá thị trường tăng.', 'Đang thụ lý', 'TAND TP. Hà Nội', 'Hà Nội', '2025-06-01', '2025-06-05'),
(1, 'Công ty xây dựng kiện đối tác vì chậm thanh toán', 'Bên thuê dịch vụ không thanh toán đúng thời hạn hợp đồng.', 'Đang hòa giải', 'TAND TP. Hải Dương', 'Hải Dương', '2025-06-10', '2025-06-15'),
(4, 'Công dân khởi kiện quyết định xử phạt hành chính sai', 'Quyết định xử phạt hành vi vi phạm giao thông được cho là không hợp lý.', 'Đã giải quyết', 'TAND Quận Long Biên', 'Hà Nội', '2025-06-20', '2025-06-25'),
(2, 'Ly hôn giữa bà Nguyễn và ông Phạm', 'Hai bên không thống nhất phân chia tài sản chung.', 'Đang xét xử', 'TAND TP. Đà Nẵng', 'Đà Nẵng', '2025-07-01', '2025-07-05'),
(7, 'Cổ đông nhỏ lẻ kiện công ty vì không chia cổ tức', 'Công ty không thực hiện nghĩa vụ chia cổ tức theo quy định.', 'Đang thụ lý', 'TAND TP. Hồ Chí Minh', 'TP. Hồ Chí Minh', '2025-07-10', '2025-07-15'),
(6, 'Nhà phát minh kiện công ty vi phạm sáng chế', 'Công ty sử dụng sáng chế khi chưa được cấp phép.', 'Đang xét xử', 'TAND TP. Hà Nội', 'Hà Nội', '2025-07-20', '2025-07-25'),
(1, 'Công ty TNHH Thiên Phú yêu cầu hủy hợp đồng thương mại', 'Bên đối tác vi phạm điều khoản về chất lượng hàng hóa.', 'Đang hòa giải', 'TAND TP. Biên Hòa', 'Đồng Nai', '2025-08-01', '2025-08-05'),
(3, 'Nhân viên yêu cầu trả lương làm thêm giờ', 'Công ty không thanh toán tiền làm thêm theo quy định.', 'Đang xét xử', 'TAND TP. Cần Thơ', 'Cần Thơ', '2025-08-10', '2025-08-15'),
(5, 'Tranh chấp thừa kế quyền sử dụng đất tại quận 7', 'Các đồng thừa kế không thống nhất phân chia quyền sử dụng đất.', 'Đã giải quyết', 'TAND TP. Hồ Chí Minh', 'TP. Hồ Chí Minh', '2025-08-20', '2025-08-25'),
(4, 'Doanh nghiệp khiếu kiện về quyết định thuế không hợp lý', 'Công ty cho rằng cơ quan thuế tính sai số tiền thuế phải nộp.', 'Đang thụ lý', 'TAND TP. Hải Phòng', 'Hải Phòng', '2025-09-01', '2025-09-05'),
(8, 'Tranh chấp hợp đồng vay tài sản giữa cá nhân', 'Hai bên ký kết hợp đồng vay nhưng bên vay không hoàn trả đúng hạn.', 'Đang xét xử', 'TAND Quận Ba Đình', 'Hà Nội', '2025-09-10', '2025-09-15'),
(8, 'Yêu cầu bồi thường thiệt hại do tai nạn giao thông', 'Nguyên đơn yêu cầu bồi thường chi phí sửa chữa và điều trị.', 'Đang hòa giải', 'TAND TP. Hồ Chí Minh', 'TP. Hồ Chí Minh', '2025-09-20', '2025-09-25'),
(8, 'Tranh chấp thừa kế nhà đất tại Hà Đông', 'Các đồng thừa kế không thống nhất về việc chia tài sản.', 'Đang thụ lý', 'TAND Quận Hà Đông', 'Hà Nội', '2025-10-01', '2025-10-05'),
(8, 'Tranh chấp quyền sở hữu xe ô tô mua chung', 'Hai cá nhân cùng góp tiền mua xe, nay phát sinh tranh chấp quyền sở hữu.', 'Đang xét xử', 'TAND TP. Đà Nẵng', 'Đà Nẵng', '2025-10-10', '2025-10-15'),
(8, 'Yêu cầu tuyên bố hợp đồng mua bán vô hiệu', 'Hợp đồng được ký kết khi một bên không đủ năng lực hành vi dân sự.', 'Đã giải quyết', 'TAND TP. Hải Phòng', 'Hải Phòng', '2025-10-20', '2025-10-25'),
(9, 'Vụ án trộm cắp tài sản tại quận 1', 'Bị cáo bị bắt quả tang khi lấy cắp xe máy của người dân.', 'Đang xét xử', 'TAND Quận 1', 'TP. Hồ Chí Minh', '2025-10-30', '2025-11-02'),
(9, 'Vụ án cố ý gây thương tích', 'Bị cáo đánh người gây thương tật 15%.', 'Đang thụ lý', 'TAND TP. Hà Nội', 'Hà Nội', '2025-11-03', '2025-11-05'),
(9, 'Vụ án lừa đảo chiếm đoạt tài sản qua mạng', 'Bị cáo sử dụng mạng xã hội để lừa chuyển tiền.', 'Đang thụ lý', 'TAND TP. Đà Nẵng', 'Đà Nẵng', '2025-11-06', '2025-11-08'),
(9, 'Vụ án tàng trữ trái phép chất ma túy', 'Bị cáo bị bắt khi mang theo 5g ma túy tổng hợp.', 'Đang xét xử', 'TAND TP. Hải Phòng', 'Hải Phòng', '2025-11-09', '2025-11-10'),
(9, 'Vụ án đánh bạc qua mạng Internet', 'Nhiều đối tượng tổ chức đánh bạc qua ứng dụng di động.', 'Đang hòa giải', 'TAND TP. Cần Thơ', 'Cần Thơ', '2025-11-09', '2025-11-10');


-- PERSONS
INSERT INTO persons (name, role, contact_info) VALUES
('Đinh Quang Đăng', 'lawyer', 'dangdien1223@gmail.com'),
('Nguyễn Thị Mai', 'lawyer', 'mai.lawyer@firm.vn'),
('Phạm Văn Hùng', 'plaintiff', 'hungpham@gmail.com'),
('Nguyễn Văn An', 'plaintiff', 'nguyenvanan@gmail.com'),
('Trần Thị Bình', 'defendant', 'tranbinh@gmail.com'),
('Lê Minh Tuấn', 'defendant', 'leminhtuan@client.vn'),
('Nguyễn Văn Hòa', 'plaintiff', 'nguyenvanhoa@gmail.com'),
('Lê Thị Thu Trang', 'defendant', 'thutrang.le@gmail.com'),
('Phạm Quang Minh', 'lawyer', 'phamminh.law@firm.vn'),
('Vũ Thị Hạnh', 'plaintiff', 'hanhvu1990@gmail.com'),
('Trần Đức Long', 'defendant', 'longtran@client.vn'),
('Hoàng Thị Lan', 'lawyer', 'lan.hoang@lawfirm.vn'),
('Nguyễn Văn Sơn', 'plaintiff', 'son.nguyen@gmail.com'),
('Lê Minh Huy', 'defendant', 'huy.le@company.vn'),
('Phạm Ngọc Anh', 'lawyer', 'ngocanh.law@firm.vn'),
('Đoàn Thị Mai', 'plaintiff', 'doanmai@gmail.com'),
('Trương Quốc Toàn', 'defendant', 'toan.truong@client.vn'),
('Nguyễn Thu Hằng', 'lawyer', 'hang.nguyen@lawfirm.vn'),
('Đỗ Mạnh Cường', 'plaintiff', 'cuongdo@gmail.com'),
('Lý Thị Hoa', 'defendant', 'lyhoa@client.vn'),
('Bùi Văn Quý', 'lawyer', 'quy.bui@firm.vn'),
('Phan Thị Duyên', 'plaintiff', 'duyen.phan@gmail.com'),
('Nguyễn Tiến Dũng', 'defendant', 'dung.nguyen@company.vn'),
('Trần Thị Phương', 'lawyer', 'phuong.tran@lawfirm.vn'),
('Vũ Mạnh Hùng', 'plaintiff', 'hungvu@gmail.com'),
('Đặng Thị Thảo', 'defendant', 'thao.dang@client.vn');

-- CASE_PERSONS
INSERT INTO case_persons (case_id, person_id) VALUES
(1, 1), (1, 3),
(2, 2), (2, 4), (2, 5),
(3, 1), (3, 6),
(4, 2), (4, 7),
(5, 1), (5, 8),
(6, 3), (6, 9),
(7, 6), (7, 10),
(8, 3), (8, 11),
(9, 2), (9, 12),
(10, 4), (10, 13),
(11, 6), (11, 14),
(12, 1), (12, 15),
(13, 9), (13, 16),
(14, 2), (14, 17),
(15, 3), (15, 18),
(16, 5), (16, 19),
(17, 6), (17, 20),
(18, 9), (18, 21),
(19, 2), (19, 22),
(20, 3), (20, 23),
(21, 1), (21, 24),
(22, 6), (22, 25),
(23, 9), (23, 26),
(24, 3), (24, 4),
(25, 6), (25, 5),
(26, 9), (26, 7),
(27, 3), (27, 8),
(28, 1), (28, 9),
(29, 2), (29, 10),
(30, 3), (30, 11),
(31, 6), (31, 12),
(32, 9), (32, 13),
(33, 1), (33, 14),
(34, 2), (34, 15),
(35, 3), (35, 16),
(9, 17), (10, 18), (11, 19), (12, 20), (13, 21),
(14, 22), (15, 23), (16, 24), (17, 25), (18, 26);

-- TAGS
INSERT INTO case_tags (tag_name) VALUES
('Thương mại'), ('Gia đình'), ('Lao động'), ('Hành chính'), ('Đất đai'), ('Sở hữu trí tuệ'), ('Doanh nghiệp'), ('Dân sự'), ('Hình sự');

INSERT INTO case_case_tags (case_id, tag_id) VALUES
(1, 1), (2, 2), (3, 3), (4, 4), (5, 5);

-- USERS
INSERT INTO users (username, password, role, email) VALUES
('admin', '$2a$12$NhtvMcr0F32iJJXNY82S3OMe5SWNzFaACtrpqhxneZlGIuMAOa6aO', 'ADMIN', 'admin@lawfirm.vn'),
('dang', '$2a$12$/ACaiuD1MpfqmTDG.0XuxOlWlSbBBA7y77yMEz0TZN3r7V8uPTyv6', 'USER', 'dangdien1223@gmail.com'),
('mai', '$2a$12$4au3AaYdMhzwHDU8n3iNY.V1nHJN3vdVgpluD/QlRp7Ap4WievQ.a', 'USER', 'mai.lawyer@firm.vn'),
('hung', '$2a$12$rvuErAe42zpZtE8gsOi9R.g5CcOtbszcnL0.Ws9xLCNI1YoxDI9h.', 'USER', 'hungpham@gmail.com'),
('an', '$2a$12$8PHMJ0o9TwG3s3Vu0H4RrOd1K8OhAEU3m7k79GZxAC5AzXxCDL5XK', 'USER', 'nguyenvanan@gmail.com'),
('binh', '$2a$12$z3LRRQE0vqY4zWkFXUq8HOVxN3HEJgLpK8mYoX4onWl3JqGvOQ3zK', 'USER', 'tranbinh@gmail.com');

-- VERIFICATION CODES
INSERT INTO verification_codes (email, code, type, expiry_time, used) VALUES
('newuser@lawfirm.vn', '123456', 'REGISTRATION', DATE_ADD(NOW(), INTERVAL 10 MINUTE), FALSE),
('dangdien1223@gmail.com', '789101', 'PASSWORD_RESET', DATE_ADD(NOW(), INTERVAL 5 MINUTE), FALSE),
('mai.lawyer@firm.vn', '202020', 'PASSWORD_RESET', DATE_ADD(NOW(), INTERVAL 15 MINUTE), FALSE);

-- QUESTIONS
INSERT INTO questions (id_questioner, id_lawyer, case_id, content, answer) VALUES
(2, 1, 1, 'Tôi cần tư vấn cách nộp hồ sơ khởi kiện đúng quy định.', 'Bạn cần nộp tại TAND nơi bị đơn cư trú.'),
(4, 2, 2, 'Ly hôn có bắt buộc hòa giải không?', 'Có, tòa án sẽ tổ chức hòa giải trước khi xét xử.'),
(3, 1, 3, 'Nếu bị sa thải trái luật, tôi có được bồi thường không?', 'Có, bạn được nhận lương và bồi thường thiệt hại.'),
(5, 2, 5, 'Tranh chấp đất nên nộp đơn ở đâu?', 'Tại Tòa án nhân dân nơi có bất động sản.'),
(6, 1, NULL, 'Thời gian xử lý vụ kiện thương mại mất bao lâu?', 'Thông thường từ 2-4 tháng tùy mức độ.');

-- APPOINTMENTS
INSERT INTO appointments (id_user, id_lawyer, appointment_time, notes) VALUES
(2, 1, DATE_ADD(NOW(), INTERVAL 1 DAY), 'Trao đổi về vụ việc thương mại.'),
(4, 2, DATE_ADD(NOW(), INTERVAL 2 DAY), 'Tư vấn ly hôn.'),
(3, 1, DATE_ADD(NOW(), INTERVAL 3 DAY), 'Tư vấn lao động.'),
(5, 1, DATE_ADD(NOW(), INTERVAL 5 DAY), 'Tư vấn tranh chấp đất.'),
(6, 2, DATE_ADD(NOW(), INTERVAL 7 DAY), 'Gặp để chuẩn bị hồ sơ bảo vệ thân chủ.');
