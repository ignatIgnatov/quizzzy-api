INSERT INTO `quizzzy-db`.`role`
(id, name)
VALUES(1, 'ROLE_ADMIN');

INSERT INTO `quizzzy-db`.`role`
(id, name)
VALUES(2, 'ROLE_USER');

INSERT INTO `quizzzy-db`.`user`
(id, email, first_name, last_name, password, points)
VALUES(1, 'admin@abv.bg', 'Ignat', 'Ignatov', '$2a$12$5gAyBXXStWkqW6Eg1aiKrOTthm1EgLhC0CfjdRuBSNBaG7Bopwup.', 0);

INSERT INTO `quizzzy-db`.user_roles
(user_id, role_id)
VALUES(1, 1);