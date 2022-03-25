-- INSERT INTO roles
-- (id, name) VALUES
--      (1, 'ROLE_USER'),
--      (2, 'ROLE_ADMIN');

INSERT INTO options
(option_id, name, price, connection_cost) VALUES
--     (1, 'option1', 10.00, 150.00),
    (10, 'WebOnly', 20.00, 150.00),
    (11, 'SafeVPN', 30.00, 150.00),
    (12, 'FreePizza', 40.00, 150.00),
    (13, 'NoRoaming', 50.00, 150.00),
    (14, 'AddDevice', 60.00, 150.00),
    (15, 'CallMom', 70.00, 150.00),
    (16, 'HideNumber', 80.00, 150.00),
    (17, 'NoSpam', 90.00, 150.00);

INSERT INTO tariffs
(tariff_id, name, price, archived, marked) VALUES
--     (1, 'tariff100', 100.00, false),
    (10, 'FAMILY', 200.00, false, false),
    (11, 'KIDS', 300.00, false, false),
    (12, 'BUSINESS', 400.00, false, false),
    (13, 'TRAVEL', 500.00, false, false);

INSERT INTO contracts
(contract_id, blocked_by_admin, blocked_by_client, number, tariff_id) VALUES
    (10, false, false, 49091238754, 11),
    (11, false, false, 49091653309, 12),
    (12, false, false, 49091983303, 13),
    (13, false, false, 49091118817, 13),
    (14, false, false, 49091109898, 13);

INSERT INTO clients
(client_id, name, last_name, birth_date, passport, address, e_mail, password, role, contract_id) VALUES
    (10, 'Anna', 'Schulz', '1983-12-01', '33JJ4789L', 'Bremen', 'anna@gmail.com', '$2a$10$O6RcZj8/S7mjYqVgOfpBguZk9JqBAT3TzlR0/YfMWuq6Voa.a8msW', 'USER', 10),
    (11, 'Mike', 'Hoffmann', '2002-06-11', '87FR2986P', 'Stuttgart', 'mike@gmail.com', '$2a$10$O6RcZj8/S7mjYqVgOfpBguZk9JqBAT3TzlR0/YfMWuq6Voa.a8msW', 'USER', 11),
    (12, 'Lena', 'King', '1977-04-10', '12SW2177E', 'Chemnitz', 'lena@gmail.com', '$2a$10$O6RcZj8/S7mjYqVgOfpBguZk9JqBAT3TzlR0/YfMWuq6Voa.a8msW', 'USER', null),
    (13, 'Kate', 'Lutz', '1999-05-05', '76VC8809Y', 'Hamburg', 'kate@gmail.com', '$2a$10$O6RcZj8/S7mjYqVgOfpBguZk9JqBAT3TzlR0/YfMWuq6Voa.a8msW', 'USER', null),
    (14, 'ADMIN', 'ADMIN', '2000-01-01', 'NO', 'NO', 'admin@gmail.com', '$2a$10$O6RcZj8/S7mjYqVgOfpBguZk9JqBAT3TzlR0/YfMWuq6Voa.a8msW', 'ADMIN', null),
    (15, 'Nina', 'Richter', '1973-01-05', '34HH2876W', 'Kiel', 'nina@gmail.com', '$2a$10$O6RcZj8/S7mjYqVgOfpBguZk9JqBAT3TzlR0/YfMWuq6Voa.a8msW', 'USER', 12),
    (16, 'Vera', 'Knappmann', '1990-12-12', '98NB2243Q', 'Stollenberg', 'vera@gmail.com', '$2a$10$O6RcZj8/S7mjYqVgOfpBguZk9JqBAT3TzlR0/YfMWuq6Voa.a8msW', 'USER', 13),
    (17, 'Lola', 'Schmidt', '1998-10-03', '65DF7890E', 'Thalheim', 'lola@gmail.com', '$2a$10$O6RcZj8/S7mjYqVgOfpBguZk9JqBAT3TzlR0/YfMWuq6Voa.a8msW', 'USER', 14);

INSERT INTO contracts_options
(option_id, contract_id) VALUES
    (10, 10),
    (11, 10),
    (13, 11),
    (14, 11);

INSERT INTO options_tariffs
(option_id, tariff_id) VALUES
    (10, 10),
    (11, 10),
    (10, 11),
    (11, 11),
    (14, 11),
    (13, 12),
    (14, 12);

-- INSERT INTO roles_clients
-- (client_id, role_id) VALUES
--       (3, 1),
--       (6, 2);

INSERT INTO additional_options
(option_id, additional_option_id) VALUES
                           (10, 11),
                           (16, 12);

INSERT INTO conflicting_options
(option_id, conflicting_option_id) VALUES
                                      (15, 10),
                                      (16, 13),
                                      (17, 14);