-- 创建数据库
CREATE DATABASE icu_clinical_system;
USE icu_clinical_system;

-- 1. 核心基础表
CREATE TABLE patients (
    patient_id INT PRIMARY KEY AUTO_INCREMENT,
    medical_record_no VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    gender ENUM('男', '女') NOT NULL,
    age INT,
    admission_time DATETIME NOT NULL,
    discharge_time DATETIME,
    diagnosis TEXT,
    status ENUM('住院', '出院', '转科') DEFAULT '住院',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_medical_record_no (medical_record_no),
    INDEX idx_admission_time (admission_time),
    INDEX idx_status (status)
);

CREATE TABLE beds (
    bed_id INT PRIMARY KEY AUTO_INCREMENT,
    bed_no VARCHAR(20) UNIQUE NOT NULL,
    room_no VARCHAR(20),
    department VARCHAR(50) NOT NULL,
    status ENUM('空闲', '占用', '维护','预约') DEFAULT '空闲',
    patient_id INT,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    INDEX idx_bed_no (bed_no),
    INDEX idx_department (department),
    INDEX idx_status (status)
);

-- 2. 临床护理工作站相关表
CREATE TABLE vital_signs (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    record_time DATETIME NOT NULL,
    temperature DECIMAL(3,1),
    heart_rate INT,
    systolic_bp INT,
    diastolic_bp INT,
    respiratory_rate INT,
    spo2 DECIMAL(3,1),
    nurse_id INT NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    INDEX idx_patient_record_time (patient_id, record_time),
    INDEX idx_record_time (record_time)
);

CREATE TABLE nursing_records (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    record_time DATETIME NOT NULL,
    record_type ENUM('日常', '特殊', '交班') NOT NULL,
    content TEXT NOT NULL,
    nurse_id INT NOT NULL,
    signature VARCHAR(100),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    INDEX idx_patient_record_time (patient_id, record_time),
    INDEX idx_record_type (record_type)
);

CREATE TABLE shift_records (
    shift_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    shift_time DATETIME NOT NULL,
    shift_type ENUM('白班', '夜班') NOT NULL,
    handover_nurse INT NOT NULL,
    takeover_nurse INT NOT NULL,
    patient_status TEXT,
    special_notes TEXT,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    INDEX idx_shift_time (shift_time),
    INDEX idx_shift_type (shift_type)
);

-- 3. 临床辅诊工作站相关表
CREATE TABLE assessments (
    assessment_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    assessment_time DATETIME NOT NULL,
    assessment_type ENUM('APACHE', 'SOFA', 'GCS', '其他') NOT NULL,
    score INT,
    assessor INT NOT NULL,
    details TEXT,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    INDEX idx_patient_assessment (patient_id, assessment_type),
    INDEX idx_assessment_time (assessment_time)
);

CREATE TABLE medical_orders (
    order_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    order_time DATETIME NOT NULL,
    order_type ENUM('长期', '临时') NOT NULL,
    order_content TEXT NOT NULL,
    doctor_id INT NOT NULL,
    status ENUM('新开', '执行', '停止') DEFAULT '新开',
    execution_time DATETIME,
    executor INT,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    INDEX idx_patient_order_time (patient_id, order_time),
    INDEX idx_status (status)
);

CREATE TABLE treatment_records (
    treatment_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    treatment_time DATETIME NOT NULL,
    treatment_type ENUM('查房', '会诊', '操作') NOT NULL,
    doctor_id INT NOT NULL,
    content TEXT NOT NULL,
    outcome TEXT,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    INDEX idx_patient_treatment (patient_id, treatment_time),
    INDEX idx_treatment_type (treatment_type)
);

CREATE TABLE medical_records (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    record_type ENUM('入院', '病程', '出院') NOT NULL,
    record_time DATETIME NOT NULL,
    content TEXT NOT NULL,
    doctor_id INT NOT NULL,
    department VARCHAR(50),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    INDEX idx_patient_record_type (patient_id, record_type),
    INDEX idx_record_time (record_time)
);

-- 4. 系统管理平台相关表
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    real_name VARCHAR(100) NOT NULL,
    role ENUM('医生', '护士', '管理员') NOT NULL,
    department VARCHAR(50),
    status ENUM('启用', '禁用') DEFAULT '启用',
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_role (role),
    INDEX idx_department (department)
);

CREATE TABLE statistics (
    stat_id INT PRIMARY KEY AUTO_INCREMENT,
    stat_date DATE NOT NULL,
    stat_type ENUM('床位使用', '患者出入', '质控指标') NOT NULL,
    department VARCHAR(50),
    value DECIMAL(10,2),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_stat_date (stat_date),
    INDEX idx_stat_type (stat_type)
);

CREATE TABLE quality_indicators (
    indicator_id INT PRIMARY KEY AUTO_INCREMENT,
    indicator_name VARCHAR(200) NOT NULL,
    target_value DECIMAL(10,2),
    actual_value DECIMAL(10,2),
    calculation_period ENUM('日', '周', '月', '季度', '年'),
    department VARCHAR(50),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_indicator_name (indicator_name),
    INDEX idx_department (department)
);

CREATE TABLE system_config (
    config_id INT PRIMARY KEY AUTO_INCREMENT,
    config_key VARCHAR(100) UNIQUE NOT NULL,
    config_value TEXT,
    description VARCHAR(500),
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_config_key (config_key)
);

-- 5. 关联关系表
CREATE TABLE permissions (
    permission_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    module VARCHAR(50) NOT NULL,
    operation VARCHAR(50) NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_user_module (user_id, module)
);

CREATE TABLE operation_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    operation_time DATETIME NOT NULL,
    module VARCHAR(50) NOT NULL,
    operation VARCHAR(200) NOT NULL,
    ip_address VARCHAR(45),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_user_operation_time (user_id, operation_time),
    INDEX idx_module (module)
);

-- 6.任务模板表
CREATE TABLE task_templates (
    template_id INT PRIMARY KEY AUTO_INCREMENT,
    task_name VARCHAR(200) NOT NULL,
    description TEXT,
    task_type ENUM('日常护理', '医疗操作', '文书工作', '其他') NOT NULL,
    frequency ENUM('一次性', '每日', '每周', '每月', '按需') NOT NULL,
    department VARCHAR(50) NOT NULL,
    trigger_condition TEXT COMMENT '触发条件，如：患者入院后2小时',
    default_assignee_role ENUM('医生', '护士') NOT NULL,
    estimated_duration INT COMMENT '预计耗时(分钟)',
    is_active BOOLEAN DEFAULT TRUE,
    created_by INT NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    INDEX idx_department (department),
    INDEX idx_task_type (task_type),
    INDEX idx_frequency (frequency)
);

-- 7.任务实例表
CREATE TABLE tasks (
    task_id INT PRIMARY KEY AUTO_INCREMENT,
    template_id INT,
    patient_id INT,
    task_name VARCHAR(200) NOT NULL,
    description TEXT,
    assigned_to INT NOT NULL,
    scheduled_time DATETIME NOT NULL,
    due_time DATETIME,
    status ENUM('待完成', '进行中', '已完成', '已取消', '已过期') DEFAULT '待完成',
    priority ENUM('低', '中', '高', '紧急') DEFAULT '中',
    completion_time DATETIME,
    completion_notes TEXT,
    created_by INT NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (template_id) REFERENCES task_templates(template_id),
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
    FOREIGN KEY (assigned_to) REFERENCES users(user_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    INDEX idx_scheduled_time (scheduled_time),
    INDEX idx_status (status),
    INDEX idx_assigned_to (assigned_to),
    INDEX idx_patient_id (patient_id)
);

--8. 任务提醒表
CREATE TABLE task_reminders (
    reminder_id INT PRIMARY KEY AUTO_INCREMENT,
    task_id INT NOT NULL,
    user_id INT NOT NULL,
    reminder_time DATETIME NOT NULL,
    message TEXT NOT NULL,
    reminder_type ENUM('系统', '短信', '邮件', '推送') DEFAULT '系统',
    status ENUM('待发送', '已发送', '已读', '失败') DEFAULT '待发送',
    sent_time DATETIME,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES tasks(task_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    INDEX idx_reminder_time (reminder_time),
    INDEX idx_status (status),
    INDEX idx_user_id (user_id)
);

-- 9. 科室常规任务配置表
CREATE TABLE department_routines (
    routine_id INT PRIMARY KEY AUTO_INCREMENT,
    department VARCHAR(50) NOT NULL,
    routine_name VARCHAR(200) NOT NULL,
    description TEXT,
    trigger_event ENUM('患者入院', '交班时', '特定时间', '医嘱下达') NOT NULL,
    trigger_time TIME COMMENT '如果是特定时间触发',
    delay_minutes INT COMMENT '延迟分钟数',
    template_id INT NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_by INT NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (template_id) REFERENCES task_templates(template_id),
    FOREIGN KEY (created_by) REFERENCES users(user_id),
    INDEX idx_department (department),
    INDEX idx_trigger_event (trigger_event)
);

-- 10.实验室检验数据表
CREATE TABLE lab_results (
    result_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    test_time DATETIME NOT NULL,
    test_type ENUM('血气', '血糖', '电解质', '其他') NOT NULL,
    parameter_name VARCHAR(100) NOT NULL, -- 如 'pH', '葡萄糖'
    value DECIMAL(8,2),
    unit VARCHAR(20),
    reference_range VARCHAR(100),
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

-- 11. 出入量明细表
CREATE TABLE intake_output (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    record_time DATETIME NOT NULL,
    type ENUM('摄入', '排出') NOT NULL,
    category ENUM('晶体', '胶体', '肠胃营养', '尿液', '其他') NOT NULL,
    volume_ml INT NOT NULL, -- 容量（毫升）
    description VARCHAR(200),
    shift_type ENUM('白班', '夜班'), -- 关联班次
    nurse_id INT NOT NULL,
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);

-- 12. 管路和皮肤信息表
CREATE TABLE patient_tubes_skin (
    record_id INT PRIMARY KEY AUTO_INCREMENT,
    patient_id INT NOT NULL,
    record_time DATETIME NOT NULL,
    type ENUM('管路', '皮肤') NOT NULL,
    anatomical_location VARCHAR(100) NOT NULL, -- 解剖位置，如 '右颈内静脉'
    description TEXT, -- 详细信息
    status ENUM('正常', '异常', '风险'),
    image_or_model_ref VARCHAR(255), -- 关联3D模型坐标
    created_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(patient_id)
);


-- 插入初始系统配置
INSERT INTO system_config (config_key, config_value, description) VALUES
('system_name', '临床ICU信息系统', '系统名称'),
('hospital_name', '', '医院名称'),
('icu_department', '重症监护室', 'ICU科室名称'),
('default_password', '123456', '默认用户密码'),
('session_timeout', '30', '会话超时时间(分钟)');

-- 插入管理员用户
INSERT INTO users (username, password, real_name, role, department, status) VALUES
('admin', 'admin123', '系统管理员', '管理员', '信息科', '启用');
