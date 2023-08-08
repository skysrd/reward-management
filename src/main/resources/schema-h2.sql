DROP TABLE IF EXISTS `rewards`;

CREATE TABLE `rewards` (
                           `reward_id`	BIGINT	NOT NULL,
                           `member_id`	BIGINT	NOT NULL,
                           `balance`	INT     NULL,
                           `initial_price`	INT     NULL,
                           `created_time`	DATETIME	NULL
);

DROP TABLE IF EXISTS `history`;

CREATE TABLE `history` (
                           `history_id`	BIGINT	NOT NULL,
                           `member_id`	BIGINT	NOT NULL,
                           `price`	INT     NULL,
                           `created_time`	DATETIME	NULL,
                           `reward_type`	VARCHAR(4)	NULL
);

DROP TABLE IF EXISTS `members`;

CREATE TABLE `members` (
                           `member_id`	BIGINT	NOT NULL,
                           `reward_summary`	INT     NULL
);

ALTER TABLE `rewards` ADD CONSTRAINT `PK_REWARDS` PRIMARY KEY (
                                                               `reward_id`
    );

ALTER TABLE `history` ADD CONSTRAINT `PK_HISTORY` PRIMARY KEY (
                                                               `history_id`
    );

ALTER TABLE `members` ADD CONSTRAINT `PK_MEMBERS` PRIMARY KEY (
                                                               `member_id`
    );