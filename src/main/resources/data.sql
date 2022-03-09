
INSERT INTO options
(option_id, name, price, connection_cost) VALUES
    (1, 'option1', 50.00, 150.00),
    (2, 'option2', 50.00, 150.00);

INSERT INTO tariffs
(tariff_id, name, price) VALUES
    (1, 'tariff100', 100.00),
    (2, 'tariff200', 200.00);

INSERT INTO contracts
(contract_id, blocked_by_admin, blocked_by_client, number, tariff_id) VALUES
    (1, false, false, 123456789, 1),
    (2, false, false, 987654321, 2);

INSERT INTO clients
(id, name, last_name, birth_date, passport, address, e_mail, password, contract_id) VALUES
    (1, 'anna', 'winter', '2001-01-01', '0000111111', 'moscow', 'anna@mail', 'mypass1', 1),
    (2, 'mike', 'summer', '2002-02-02', '0000222222', 'perm', 'mike@mail', 'mypass2', 2),
    (3, 'lena', 'smith', '2003-03-03', '0000333333', 'kiev', 'lena@mail', 'mypass3', null);



