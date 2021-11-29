DROP
ALL OBJECTS;

-- create tables --
CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL
);

CREATE TABLE accounts
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(45)  NOT NULL,
    password VARCHAR(150) NOT NULL,
    role_id  BIGINT       NOT NULL,

    FOREIGN KEY (role_id)
        REFERENCES roles (id)
        ON DELETE CASCADE
);

CREATE TABLE patients
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(45) NOT NULL,
    last_name    VARCHAR(45) NOT NULL,
    email        VARCHAR(45) NOT NULL,
    phone_number VARCHAR(45) NOT NULL,
    pesel        VARCHAR(45) NOT NULL,
    account_id   BIGINT      NOT NULL,

    FOREIGN KEY (account_id)
        REFERENCES accounts (id)
        ON DELETE CASCADE
);

CREATE TABLE positions
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(45) NOT NULL
);

CREATE TABLE employees
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name   VARCHAR(45) NOT NULL,
    last_name    VARCHAR(45) NOT NULL,
    email        VARCHAR(45) NOT NULL,
    phone_number VARCHAR(45) NOT NULL,
    account_id   BIGINT      NOT NULL,
    position_id  BIGINT      NOT NULL,

    FOREIGN KEY (account_id)
        REFERENCES accounts (id)
        ON DELETE CASCADE,

    FOREIGN KEY (position_id)
        REFERENCES positions (id)
        ON DELETE CASCADE
);

CREATE TABLE visit_types
(
    id               BIGINT AUTO_INCREMENT PRIMARY KEY,
    name             VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    duration         DOUBLE       NOT NULL,
    price            DECIMAL      NOT NULL,
    patient_can_book BOOLEAN      NOT NULL,
    position_id      BIGINT       NOT NULL,

    FOREIGN KEY (position_id)
        REFERENCES positions (id)
);

CREATE TABLE visit_statuses
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE visits
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    patient_id      BIGINT       NOT NULL,
    employee_id     BIGINT       NOT NULL,
    visit_type_id   BIGINT       NOT NULL,
    visit_status_id BIGINT       NOT NULL,
    description     VARCHAR(255) NOT NULL,
    date            DATE         NOT NULL,
    start_time      TIME         NOT NULL,
    end_time        TIME         NOT NULL,

    FOREIGN KEY (patient_id)
        REFERENCES patients (id),
    FOREIGN KEY (employee_id)
        REFERENCES employees (id),
    FOREIGN KEY (visit_type_id)
        REFERENCES visit_types (id),
    FOREIGN KEY (visit_status_id)
        REFERENCES visit_statuses (id)
);

-- insert data --
INSERT INTO roles (name)
VALUES ('PATIENT'),
       ('EMPLOYEE'),
       ('ADMIN');

INSERT INTO accounts (username, password, role_id)
VALUES
    -- Patients:
    ('karol1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 1),
    ('bartosz1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 1),
    ('mateusz1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 1),
    ('wiktoria1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 1),
    ('kacper1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 1),
    ('justyna1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 1),
    ('tobiasz1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 1),
    ('agelika1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 1),
    -- Employees:
    ('radoslaw1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 2),
    ('nadia1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 2),
    ('wojciech1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 2),
    ('krzysztof1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 2),
    ('aleksandra1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 2),
    ('paulina1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 2),
    ('henryk1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 2),
    -- Admin:
    ('jakub1', '$2a$10$gFZCI/gbmI84My8uqbdFu.hXMomLG5wJbq5aEf3s6LDt3A.qdG.bK', 3);

INSERT INTO patients (first_name, last_name, email, phone_number, pesel, account_id)
VALUES ('Karol', 'Nowak', 'karol1@gmail.com', '698125687', '86022641634', 1),
       ('Bartosz', 'Kowalczyk', 'bartosz1@gmail.com', '472564123', '78092559135', 2),
       ('Mateusz', 'Wierzbicki', 'mateusz1@gmail.com', '145256777', '86040427887', 3),
       ('Wiktoria', 'Dewin', 'wiktoria1@gmail.com', '256444882', '04300917635', 4),
       ('Kacper', 'Krawczyk', 'kacper1@gmail.com', '496334222', '03323027163', 5),
       ('Justyna', 'Grabowska', 'justyna1@gmail.com', '685455223', '96061179159', 6),
       ('Tobiasz', 'Zalewski', 'tobiasz1@gmail.com', '789456122', '90021518824', 7),
       ('Angelika', 'Kaczmarek', 'wiktoria1@gmail.com', '563888124', '04212294763', 8);

INSERT INTO positions (name)
VALUES ('Ortodonta'),
       ('Higienistka dentystyczna'),
       ('Radiolog');

INSERT INTO employees (first_name, last_name, email, phone_number, account_id, position_id)
VALUES ('Radoslaw', 'Bronk', 'radoslaw1@gmail.pl', '562145887', 9, 1),
       ('Nadia', 'Zalewska', 'nadia@gmail.com', '789145788', 10, 1),
       ('Wojciech', 'Michałowski', 'wojciech1@gmail.com', '478114227', 11, 1),
       ('Krzysztof', 'Sadowski', 'krzysztof1@gmail.com', '486211788', 12, 1),
       ('Aleksandra', 'Krzywicka', 'aleksandra1@gmail.com', '568792124', 12, 2),
       ('Paulina', 'Włodarczyk', 'paulina1@gmail.com', '516248972', 13, 2),
       ('Henryk', 'Mazur', 'henryk@gmail.com', '516248972', 14, 3);

INSERT INTO visit_types(name, description, duration, price, patient_can_book, position_id)
VALUES ('Kontrola aparatu', 'Standardowa kontrola aparatu ortodontycznego', 60, 100, true, 1),
       ('Naprawa aparatu', 'Zabieg naprawy aparatu ortodontycznego', 30, 100, true, 1),
       ('Zakładanie aparatu', 'Zabieg zakładania stałego aparatu ortodontycznego', 120, 1400, false, 1),
       ('Usunięcie aparatu', 'Zabieg usunięcia stałego aparatu ortodontycznego', 120, 200, false, 1),
       ('Kontrola retainera', 'Standardowa kontrola retainera', 60, 100, true, 1),
       ('Higienizacja jamy ustnej', 'Higienizacja jamy ustnej wraz z piaskowaniem', 120, 210, true, 2),
       ('Wybielanie zębów', 'Zabieg wybielania zębów', 120, 400, true, 2),
       ('Rentgen stomatologiczny', 'Zdjęcie RTG całego uzębienia', 30, 200, true, 3);

INSERT INTO visit_statuses(name)
VALUES ('WAITING'),
       ('COMPLETED'),
       ('CANCELED');

INSERT INTO visits(patient_id, employee_id, visit_type_id, visit_status_id, description, date, start_time, end_time)
VALUES (1, 1, 1, 1, 'Brak opisu', '2021-10-22', '11:00', '12:00'),
       (1, 5, 6, 1, 'Brak opisu', '2021-10-22', '12:00', '14:00'),
       (1, 6, 7, 1, 'Brak opisu', '2021-11-02', '08:00', '10:00'),
       (1, 7, 8, 1, 'Brak opisu', '2021-11-05', '13:00', '13:30');
