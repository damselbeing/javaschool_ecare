
INSERT INTO options
(option_id, name, price, connection_cost) VALUES
    (1, 'option1', 10.00, 150.00),
    (2, 'option2', 20.00, 150.00),
    (3, 'option3', 30.00, 150.00),
    (4, 'option4', 40.00, 150.00),
    (5, 'option5', 50.00, 150.00),
    (6, 'option6', 60.00, 150.00),
    (7, 'option7', 70.00, 150.00),
    (8, 'option8', 80.00, 150.00),
    (9, 'option9', 90.00, 150.00);

INSERT INTO tariffs
(tariff_id, name, price, archived) VALUES
    (1, 'tariff100', 100.00, false),
    (2, 'tariff200', 200.00, false),
    (3, 'tariff300', 300.00, false),
    (4, 'tariff400', 400.00, false),
    (5, 'tariff500', 500.00, false);

INSERT INTO contracts
(contract_id, blocked_by_admin, blocked_by_client, number, tariff_id) VALUES
    (1, false, false, 123456789, 1),
    (2, false, false, 987654321, 3);

INSERT INTO clients
(client_id, name, last_name, birth_date, passport, address, e_mail, password, contract_id) VALUES
    (1, 'anna', 'winter', '2001-01-01', '0000111111', 'moscow', 'anna@mail', 'mypass1', 1),
    (2, 'mike', 'summer', '2002-02-02', '0000222222', 'perm', 'mike@mail', 'mypass2', 2),
    (3, 'lena', 'smith', '2003-03-03', '0000333333', 'kiev', 'lena@mail', 'mypass3', null);

INSERT INTO contracts_options
(option_id, contract_id) VALUES
    (1, 1),
    (2, 1),
    (5, 2),
    (6, 2);

INSERT INTO options_tariffs
(option_id, tariff_id) VALUES
    (1, 1),
    (2, 1),
    (3, 1),
    (4, 3),
    (5, 3),
    (6, 3);

INSERT INTO additional_options
(option_id, additional_option_id) VALUES
                           (1, 3),
                           (8, 2);

INSERT INTO conflicting_options
(option_id, conflicting_option_id) VALUES
                                      (7, 2),
                                      (8, 4),
                                      (9, 5);