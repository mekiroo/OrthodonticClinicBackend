CREATE TABLE roles
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE accounts
(
    id       BIGINT AUTO_INCREMENT NOT NULL,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role_id  BIGINT       NOT NULL,
    PRIMARY KEY (id),

    FOREIGN KEY (role_id)
        REFERENCES roles (id)
);

CREATE TABLE patients
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    pesel        VARCHAR(255) NOT NULL,
    account_id   BIGINT       NOT NULL,
    PRIMARY KEY (id),

    FOREIGN KEY (account_id)
        REFERENCES accounts (id)
);

CREATE TABLE positions
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE employees
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    first_name   VARCHAR(255) NOT NULL,
    last_name    VARCHAR(255) NOT NULL,
    email        VARCHAR(255) NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    account_id   BIGINT       NOT NULL,
    position_id  BIGINT       NOT NULL,
    PRIMARY KEY (id),

    FOREIGN KEY (account_id)
        REFERENCES accounts (id),

    FOREIGN KEY (position_id)
        REFERENCES positions (id)
);

CREATE TABLE visit_types
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    name             VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    duration         DOUBLE       NOT NULL,
    price            DOUBLE       NOT NULL,
    patient_can_book BOOLEAN      NOT NULL,
    position_id      BIGINT       NOT NULL,
    PRIMARY KEY (id),

    FOREIGN KEY (position_id)
        REFERENCES positions (id)
);

CREATE TABLE visit_statuses
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE visits
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    patient_id      BIGINT       NOT NULL,
    employee_id     BIGINT       NOT NULL,
    visit_type_id   BIGINT       NOT NULL,
    visit_status_id BIGINT       NOT NULL,
    description     VARCHAR(255) NOT NULL,
    date            DATE         NOT NULL,
    start_time      TIME         NOT NULL,
    end_time        TIME         NOT NULL,
    PRIMARY KEY (id),

    FOREIGN KEY (patient_id)
        REFERENCES patients (id),

    FOREIGN KEY (employee_id)
        REFERENCES employees (id),

    FOREIGN KEY (visit_type_id)
        REFERENCES visit_types (id),

    FOREIGN KEY (visit_status_id)
        REFERENCES visit_statuses (id)
);


-- insert data
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
VALUES ('Radosław', 'Bronk', 'radoslaw1@gmail.pl', '562145887', 9, 1),
       ('Nadia', 'Zalewska', 'nadia@gmail.com', '789145788', 10, 1),
       ('Wojciech', 'Michałowski', 'wojciech1@gmail.com', '478114227', 11, 1),
       ('Krzysztof', 'Sadowski', 'krzysztof1@gmail.com', '486211788', 12, 1),
       ('Aleksandra', 'Krzywicka', 'aleksandra1@gmail.com', '568792124', 12, 2),
       ('Paulina', 'Włodarczyk', 'paulina1@gmail.com', '516248972', 13, 2),
       ('Henryk', 'Mazur', 'henryk@gmail.com', '516248972', 14, 3);

INSERT INTO visit_types(name, description, duration, price, patient_can_book, position_id)
VALUES ('Konsultacja ortodontyczna', 'Konsultacja ortodontyczna z ortodontą', 30, 100, true, 1),
       ('Kontrola aparatu', 'Standardowa kontrola aparatu ortodontycznego', 60, 100, true, 1),
       ('Naprawa aparatu', 'Zabieg naprawy aparatu ortodontycznego', 30, 100, true, 1),
       ('Zakładanie aparatu', 'Zabieg zakładania stałego aparatu ortodontycznego', 120, 1400, false, 1),
       ('Usunięcie aparatu', 'Zabieg usunięcia stałego aparatu ortodontycznego', 120, 200, false, 1),
       ('Kontrola retainera', 'Standardowa kontrola retainera', 60, 100, true, 1),
       ('Higienizacja jamy ustnej', 'Higienizacja jamy ustnej wraz z piaskowaniem', 120, 210, true, 2),
       ('Wybielanie zębów', 'Zabieg wybielania zębów', 120, 400, true, 2),
       ('Rentgen stomatologiczny', 'Zdjęcie RTG całego uzębienia', 30, 200, true, 3);

INSERT INTO visit_statuses(name)
VALUES ('WAITING'),
       ('CANCELED'),
       ('COMPLETED');

INSERT INTO visits(patient_id, employee_id, visit_type_id, visit_status_id, description, date, start_time, end_time)
VALUES (1, 7, 9, 3, 'Wizyta zakończona', '2021-05-02', '11:00', '11:30'),
       (1, 5, 7, 3, 'Wizyta zakończona', '2021-06-20', '08:00', '10:00'),
       (1, 1, 1, 3, 'Wizyta zakończona', '2021-06-28', '15:00', '15:30'),
       (1, 1, 4, 3, 'Wizyta zakończona. Aparat założony prawidłowo', '2021-07-01', '09:00', '11:00'),
       (1, 1, 2, 3, 'Wizyta zakończona. Wszystkie parametry w normie', '2021-08-04', '08:00', '09:00'),
       (1, 2, 3, 3, 'Wizyta zakończona. Aparat naprawiony', '2021-08-30', '14:00', '15:00'),
       (1, 1, 2, 2, 'Wizyta anulowana przez pacjenta', '2021-09-02', '14:00', '15:00'),
       (1, 1, 3, 2, 'Wizyta anulowana przez pacjenta', '2021-09-07', '14:00', '16:00'),
       (1, 1, 2, 3, 'Wizyta zakończona. Wszystkie parametry w normie', '2021-09-20', '11:00', '12:00'),
       (1, 1, 2, 1, 'Brak opisu', '2021-11-30', '11:00', '12:00'),
       (1, 5, 7, 1, 'Brak opisu', '2021-12-08', '14:00', '16:00'),
       (2, 7, 9, 3, 'Wizyta zakończona', '2021-03-15', '11:00', '11:30'),
       (2, 6, 7, 3, 'Wizyta zakończona', '2021-06-21', '08:00', '10:00'),
       (2, 2, 1, 3, 'Wizyta zakończona', '2021-06-29', '15:00', '15:30'),
       (2, 2, 4, 3, 'Wizyta zakończona. Aparat założony prawidłowo', '2021-07-02', '09:00', '11:00'),
       (2, 2, 2, 2, 'Wizyta anulowana przez pacjenta', '2021-09-03', '14:00', '15:00'),
       (2, 6, 8, 2, 'Wizyta anulowana przez pacjenta', '2021-09-08', '14:00', '16:00'),
       (2, 2, 2, 3, 'Wizyta zakończona. Wszystkie parametry w normie', '2021-09-21', '11:00', '12:00'),
       (2, 2, 2, 1, 'Brak opisu', '2021-11-02', '11:00', '12:00'),
       (2, 2, 7, 1, 'Brak opisu', '2021-12-08', '14:00', '16:00'),
       (3, 6, 7, 3, 'Wizyta zakończona.', '2021-07-15', '11:00', '13:00'),
       (3, 3, 4, 3, 'Wizyta zakończona. Aparat założony prawidłowo', '2021-07-20', '14:00', '16:00'),
       (3, 3, 2, 2, 'Wziyta anulowana przez pacjenta', '2021-08-05', '08:00', '09:00'),
       (3, 3, 2, 3, 'Wizyta zakończona. Wszystkie parametry prawidłowe.', '2021-08-22', '12:00', '13:00'),
       (4, 5, 7, 3, 'Wizyta zakończona.', '2021-07-16', '11:00', '13:00'),
       (4, 1, 4, 3, 'Wizyta zakończona. Aparat założony prawidłowo', '2021-08-21', '14:00', '16:00'),
       (4, 1, 2, 2, 'Wziyta anulowana przez pacjenta', '2021-09-12', '08:00', '09:00'),
       (4, 1, 2, 3, 'Wizyta zakończona. Wszystkie parametry prawidłowe.', '2021-09-26', '12:00', '13:00'),
       (5, 6, 7, 3, 'Wizyta zakończona.', '2021-08-17', '11:00', '13:00'),
       (5, 1, 4, 3, 'Wizyta zakończona. Aparat założony prawidłowo', '2019-08-22', '14:00', '16:00'),
       (5, 4, 2, 2, 'Wziyta anulowana przez pacjenta', '2021-09-13', '08:00', '09:00'),
       (5, 4, 2, 3, 'Wizyta zakończona. Wszystkie parametry prawidłowe.', '2021-09-27', '12:00', '13:00'),
       (6, 5, 7, 3, 'Wizyta zakończona.', '2021-08-18', '11:00', '13:00'),
       (6, 2, 4, 3, 'Wizyta zakończona. Aparat założony prawidłowo', '2021-08-23', '14:00', '16:00'),
       (6, 2, 2, 2, 'Wziyta anulowana przez pacjenta', '2021-09-14', '08:00', '09:00'),
       (6, 2, 2, 3, 'Wizyta zakończona. Wszystkie parametry prawidłowe.', '2021-09-28', '12:00', '13:00'),
       (7, 5, 7, 3, 'Wizyta zakończona.', '2021-08-19', '11:00', '13:00'),
       (7, 1, 4, 3, 'Wizyta zakończona. Aparat założony prawidłowo', '2021-08-24', '14:00', '16:00'),
       (7, 5, 8, 2, 'Wziyta anulowana przez pacjenta', '2021-09-15', '08:00', '09:00'),
       (7, 1, 2, 3, 'Wizyta zakończona. Wszystkie parametry prawidłowe.', '2021-09-29', '12:00', '13:00'),
       (8, 5, 7, 3, 'Wizyta zakończona.', '2021-10-19', '11:00', '13:00'),
       (8, 1, 1, 3, 'Wizyta zakończona. Zlecono założenie aparatu.', '2021-10-22', '14:00', '14:30'),
       -- Today's visits
       (2, 1, 1, 1, 'Brak opisu', '2021-11-24', '08:00', '08:30'),
       (8, 1, 4, 1, 'Brak opisu', '2021-11-24', '09:00', '11:00'),
       (4, 1, 2, 1, 'Brak opisu', '2021-11-24', '13:00', '14:00'),
       (5, 1, 1, 1, 'Brak opisu', '2021-11-24', '14:00', '14:30'),
       (6, 1, 1, 1, 'Brak opisu', '2021-11-24', '14:30', '15:00'),
       (7, 1, 2, 1, 'Brak opisu', '2021-11-24', '15:00', '16:00'),
       (5, 1, 1, 1, 'Brak opisu', '2021-11-25', '14:00', '14:30'),
       (6, 1, 1, 1, 'Brak opisu', '2021-11-25', '14:30', '15:00'),
       (7, 1, 2, 1, 'Brak opisu', '2021-11-25', '15:00', '16:00'),
       (2, 1, 1, 1, 'Brak opisu', '2021-11-26', '08:00', '08:30'),
       -- Future visits
       (2, 1, 3, 1, 'Brak opisu', '2021-11-26', '08:00', '08:30'),
       (8, 1, 6, 1, 'Brak opisu', '2021-11-30', '15:00', '16:00'),
       (4, 1, 1, 1, 'Brak opisu', '2021-12-12', '13:00', '14:00'),
       (5, 1, 2, 1, 'Brak opisu', '2022-01-10', '12:00', '13:00');
