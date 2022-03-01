INSERT INTO clients
(id_client, name, last_name, birth_date, passport, address, e_mail, password) VALUES
    (1, 'anna', 'winter', '2001-01-01', '0000111111', 'moscow', 'anna@mail', 'mypass1'),
    (2, 'mike', 'summer', '2002-02-02', '0000222222', 'perm', 'mike@mail', 'mypass2');

INSERT INTO contracts
(id_contract, blocked_by_admin, blocked_by_client, number) VALUES
    (1, false, false, 123456789),
    (2, false, false, 987654321);