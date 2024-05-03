-- liquibase formatted sql

-- changeset mikekushkov:1

CREATE index student_name_index on students (name);

CREATE index faculty_name_color_index on faculties (name, color);