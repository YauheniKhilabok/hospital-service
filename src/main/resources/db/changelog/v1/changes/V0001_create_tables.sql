CREATE TABLE IF NOT EXISTS staff_members
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT,
    name              VARCHAR(255) NOT NULL,
    uuid              VARCHAR(36)  NOT NULL DEFAULT (uuid()),
    registration_date TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE INDEX staff_members_uuid_idx ON staff_members (uuid);

CREATE TABLE IF NOT EXISTS patients
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT,
    staff_member_id   BIGINT       NOT NULL,
    name              VARCHAR(255) NOT NULL,
    age               SMALLINT     NOT NULL,
    last_visit_date   TIMESTAMP,
    FOREIGN KEY (staff_member_id) REFERENCES staff_members (id)
);

CREATE INDEX patients_last_visit_date_idx ON patients (last_visit_date);
CREATE INDEX patients_staff_member_id_idx ON patients (staff_member_id);