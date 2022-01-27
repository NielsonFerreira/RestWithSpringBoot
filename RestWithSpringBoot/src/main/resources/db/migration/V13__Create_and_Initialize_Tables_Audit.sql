CREATE TABLE IF NOT EXISTS `revision` (
    `rev_id` bigint NOT NULL,
    `revision_date` datetime DEFAULT NULL,
    `user` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`rev_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE IF NOT EXISTS `person_aud` (
    `id` bigint NOT NULL,
    `rev` bigint NOT NULL,
    `revtype` tinyint DEFAULT NULL,
    `address` varchar(100) DEFAULT NULL,
    `enabled` bit(1) DEFAULT NULL,
    `first_name` varchar(80) DEFAULT NULL,
    `gender` varchar(6) DEFAULT NULL,
    `last_name` varchar(80) DEFAULT NULL,
    PRIMARY KEY (`id`,`rev`),
    KEY `FKc5na6emj2l0ir751jxn1bl3ix` (`rev`),
    CONSTRAINT `FKc5na6emj2l0ir751jxn1bl3ix` FOREIGN KEY (`rev`) REFERENCES `revision` (`rev_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

INSERT INTO revision (rev_id, revision_date, user) VALUES (1, CURRENT_TIMESTAMP, "Initializer");

INSERT INTO person_aud (
    rev,
    revtype,
	id,
	address,
	enabled,
	first_name,
	gender,
	last_name
) SELECT
    1,
    0,
    id,
    address,
    enabled,
    first_name,
    gender,
    last_name FROM person