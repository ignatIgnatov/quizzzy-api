INSERT INTO `quizzzy-db`.`user`
(id, email, first_name, last_name, password, points)
VALUES(2, 'test@abv.bg', 'Test', 'Test', '$2a$12$/jPD9fI3MGDRMGqbsTw5GO9MWezrlcvUGhtYlXIIpzco/IEgaWeGu', 0);

INSERT INTO `quizzzy-db`.user_roles
(user_id, role_id)
VALUES(2, 2);