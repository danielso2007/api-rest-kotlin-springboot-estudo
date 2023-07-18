CREATE TABLE usuario_role(`id` BIGINT NOT NULL AUTO_INCREMENT,`usuario_id` BIGINT NOT NULL,`role_id` BIGINT NOT NULL,PRIMARY KEY(`id`),FOREIGN KEY(`usuario_id`) REFERENCES `usuario`(`id`),FOREIGN KEY(`role_id`) REFERENCES `role`(`id`));
INSERT INTO usuario_role (`usuario_id`, `role_id`) values (1, 1);
INSERT INTO usuario_role (`usuario_id`, `role_id`) values (2, 1);
INSERT INTO usuario_role (`usuario_id`, `role_id`) values (3, 1);
INSERT INTO usuario_role (`usuario_id`, `role_id`) values (4, 1);
INSERT INTO usuario_role (`usuario_id`, `role_id`) values (5, 1);
