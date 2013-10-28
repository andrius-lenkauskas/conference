
INSERT INTO demo (id, data) VALUES (1, 'Hello, world!');
INSERT INTO demo (id, data) VALUES (2, 'Hello, world again!');
INSERT INTO demo (id, data) VALUES (3, 'Hello, world for the third time!');

INSERT INTO conference (id, name, startDate, endDate) VALUES (1, 'NFQ Akademijos atidarymas. Įvadas į šiuolaikinio programuotojo darbą', '2013-09-30 17:00:00', '2013-09-30 18:00:00');
INSERT INTO conference (id, name, startDate, endDate) VALUES (2, 'Projekto valdymas ir Agile', '2013-10-02 17:00:00', '2013-10-02 18:00:00');
INSERT INTO conference (id, name, startDate, endDate) VALUES (3, 'J2EE programavimui naudojami įrankiai', '2013-10-07 17:00:00', '2013-10-07 18:00:00');
INSERT INTO conference (id, name, startDate, endDate) VALUES (4, 'Web puslapių vartotojo sąsajos kūrimas (1-a dalis)', '2013-10-09 17:00:00', '2013-10-09 18:00:00');

INSERT INTO users (id,name,surname,email,country,town,password,role) VALUES (1, 'Andrius', 'Lenkauskas', 'admin@admin.com', 'Lithuania', 'Kaunas', '6bc378308cc6b4ca87c0f7da6a1109c9f55538cd7c2076fcee718e6eaa775e7a', 'ROLE_REGULAR,ROLE_ADMIN');

COMMIT;