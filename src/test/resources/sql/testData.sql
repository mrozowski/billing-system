INSERT INTO parent(firstname, lastname)
VALUES ('John', 'Kowalski'),
       ('Janusz', 'Smith');

INSERT INTO school(name, hour_price)
VALUES ('SchoolOfRock', 250);

INSERT INTO child(firstname, lastname, parent_id, school_id)
VALUES ('Marek', 'Kowalski', 1, 1),
       ('Elżbieta', 'Kowalska', 1, 1),
       ('Alice', 'Smith', 2, 1);


INSERT INTO attendance(child_id, entry_date, exit_date) VALUES
(1, to_timestamp('01.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('01.02.2024 12:00', 'DD.MM.YYYY HH24:MI')),
(1, to_timestamp('02.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('02.02.2024 14:00', 'DD.MM.YYYY HH24:MI')),
(1, to_timestamp('03.02.2024 06:59', 'DD.MM.YYYY HH24:MI'), to_timestamp('03.02.2024 12:01', 'DD.MM.YYYY HH24:MI')),

(2, to_timestamp('01.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('01.02.2024 12:00', 'DD.MM.YYYY HH24:MI')),
(2, to_timestamp('02.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('02.02.2024 14:00', 'DD.MM.YYYY HH24:MI')),

(3, to_timestamp('01.02.2024 07:30', 'DD.MM.YYYY HH24:MI'), to_timestamp('01.02.2024 12:00', 'DD.MM.YYYY HH24:MI')),
(3, to_timestamp('02.02.2024 07:00', 'DD.MM.YYYY HH24:MI'), to_timestamp('02.02.2024 12:59', 'DD.MM.YYYY HH24:MI')),
(3, to_timestamp('03.02.2024 06:30', 'DD.MM.YYYY HH24:MI'), to_timestamp('03.02.2024 12:30', 'DD.MM.YYYY HH24:MI'));

