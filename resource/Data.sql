/* Setting up PROD DB */
create database if not exists paymybuddy;
use paymybuddy;

drop table IF EXISTS balance,client,friend,friend_client,money_transaction,payment;

CREATE TABLE friend (
                friend_id BIGINT NOT NULL,

                PRIMARY KEY (friend_id)
);


CREATE TABLE client (
                client_id  BIGINT  AUTO_INCREMENT NOT NULL,
                email_account VARCHAR(200) NOT NULL,
                client_password VARCHAR(200),
                first_name VARCHAR(200) ,
                last_name VARCHAR(200) ,
                client_type VARCHAR(200) ,
                PRIMARY KEY (client_id)
);


CREATE TABLE friend_client (
                friend_id BIGINT NOT NULL,
                client_id BIGINT NOT NULL,
                PRIMARY KEY (friend_id, client_id)
);


CREATE TABLE money_transaction (
                id BIGINT  AUTO_INCREMENT NOT NULL,
                money_transaction_timestamp DATETIME ,
                sender_client_id BIGINT ,
                receiver_client_id BIGINT ,
                PRIMARY KEY (id)
);


CREATE TABLE payment (
                transaction_id BIGINT,
                motive VARCHAR(200),
                amount DOUBLE  ,
                fee DOUBLE
);


CREATE TABLE balance (
                amount NUMERIC(10,2),
                client_id BIGINT
);


ALTER TABLE friend_client ADD CONSTRAINT friend_friend_client_fk
FOREIGN KEY (friend_id)
REFERENCES friend (friend_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE balance ADD CONSTRAINT client_balance_fk
FOREIGN KEY (client_id)
REFERENCES client (client_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE money_transaction ADD CONSTRAINT client_transaction_fk
FOREIGN KEY (sender_client_id)
REFERENCES client (client_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE friend_client ADD CONSTRAINT client_friend_client_fk
FOREIGN KEY (client_id)
REFERENCES client (client_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE payment ADD CONSTRAINT transaction_payment_fk
FOREIGN KEY (transaction_id)
REFERENCES money_transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE `client`
ADD UNIQUE INDEX `email_account_UNIQUE` (`email_account` ASC) VISIBLE;



INSERT INTO `paymybuddy`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('1','khalil@gmail.com', 'password', 'Khalil', 'Sleaby', 'person');
INSERT INTO `paymybuddy`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('2', 'yomna@gmail.com', 'password', 'yomna', 'Sleaby', 'person');
INSERT INTO `paymybuddy`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('3', 'aram@gmail.com', 'password', 'aram', 'Sleaby', 'person');
INSERT INTO `paymybuddy`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('4', 'elena@gmail.com', 'password', 'elena', 'Sleaby', 'person');
INSERT INTO `paymybuddy`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('5', 'newFriend@gmail.com', 'password', 'New', 'Friend', 'person');

UPDATE `paymybuddy`.`client` SET `client_password` = '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '1');
UPDATE `paymybuddy`.`client` SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '2');
UPDATE `paymybuddy`.`client` SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '3');
UPDATE `paymybuddy`.`client` SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '4');
UPDATE `paymybuddy`.`client` SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '5');


insert INTO `paymybuddy`.`balance`(`amount`,`client_id`) values (100 , 1);
insert INTO `paymybuddy`.`balance`(`amount`,`client_id`) values (100 , 2);
insert INTO `paymybuddy`.`balance`(`amount`,`client_id`) values (100 , 3);
insert INTO `paymybuddy`.`balance`(`amount`,`client_id`) values (100 , 4);
insert INTO `paymybuddy`.`balance`(`amount`,`client_id`) values (100 , 5);


INSERT INTO `paymybuddy`.`money_transaction` (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('1', '2021/09/15 20:14:00', '1', '2');
INSERT INTO `paymybuddy`.`money_transaction` (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('2', '2021/09/15 20:40:00', '1', '3');
INSERT INTO `paymybuddy`.`money_transaction` (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('3', '2021/09/15 20:43:00', '1', '4');
INSERT INTO `paymybuddy`.`money_transaction` (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('4', '2021/09/15 20:30:00', '1', '2');

insert INTO `paymybuddy`.`payment`(`transaction_id`,`motive`,`amount`,`fee`) values (1 , 'Trip money',40,0.2);
insert INTO `paymybuddy`.`payment`(`transaction_id`,`motive`,`amount`,`fee`) values (2 , 'Movie Ticket ',10,0.05);
insert INTO `paymybuddy`.`payment`(`transaction_id`,`motive`,`amount`,`fee`) values (3 , 'Restaurant Share',60,0.3);
insert INTO `paymybuddy`.`payment`(`transaction_id`,`motive`,`amount`,`fee`) values (4 , 'Loyer Share',80,0.4);

INSERT INTO `paymybuddy`.`friend` (`friend_id`) VALUES ('2');
INSERT INTO `paymybuddy`.`friend` (`friend_id`) VALUES ('3');
INSERT INTO `paymybuddy`.`friend` (`friend_id`) VALUES ('4');

INSERT INTO `paymybuddy`.`friend_client` (`friend_id`, `client_id`) VALUES ('2', '1');
INSERT INTO `paymybuddy`.`friend_client` (`friend_id`, `client_id`) VALUES ('3', '1');
INSERT INTO `paymybuddy`.`friend_client` (`friend_id`, `client_id`) VALUES ('4', '1');
commit;




/* Setting up TEST DB */
create database  if not exists paymybuddytest;
use paymybuddytest;

drop table IF EXISTS balance,client,friend,friend_client,money_transaction,payment;
CREATE TABLE friend (
                friend_id BIGINT NOT NULL,

                PRIMARY KEY (friend_id)
);


CREATE TABLE client (
                client_id  BIGINT  AUTO_INCREMENT NOT NULL,
                email_account VARCHAR(200) NOT NULL,
                client_password VARCHAR(200),
                first_name VARCHAR(200) ,
                last_name VARCHAR(200) ,
                client_type VARCHAR(200) ,
                PRIMARY KEY (client_id)
);


CREATE TABLE friend_client (
                friend_id BIGINT NOT NULL,
                client_id BIGINT NOT NULL,
                PRIMARY KEY (friend_id, client_id)
);


CREATE TABLE money_transaction (
                id BIGINT  AUTO_INCREMENT NOT NULL,
                money_transaction_timestamp DATETIME ,
                sender_client_id BIGINT ,
                receiver_client_id BIGINT ,
                PRIMARY KEY (id)
);


CREATE TABLE payment (
                transaction_id BIGINT,
                motive VARCHAR(200),
                amount DOUBLE  ,
                fee DOUBLE
);


CREATE TABLE balance (
                amount NUMERIC(10,2),
                client_id BIGINT
);


ALTER TABLE friend_client ADD CONSTRAINT friend_friend_client_fk
FOREIGN KEY (friend_id)
REFERENCES friend (friend_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE balance ADD CONSTRAINT client_balance_fk
FOREIGN KEY (client_id)
REFERENCES client (client_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE money_transaction ADD CONSTRAINT client_transaction_fk
FOREIGN KEY (sender_client_id)
REFERENCES client (client_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE friend_client ADD CONSTRAINT client_friend_client_fk
FOREIGN KEY (client_id)
REFERENCES client (client_id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE payment ADD CONSTRAINT transaction_payment_fk
FOREIGN KEY (transaction_id)
REFERENCES money_transaction (id)
ON DELETE NO ACTION
ON UPDATE NO ACTION;

ALTER TABLE `client`
ADD UNIQUE INDEX `email_account_UNIQUE` (`email_account` ASC) VISIBLE;



INSERT INTO `paymybuddytest`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('1','khalil@gmail.com', 'password', 'Khalil', 'Sleaby', 'person');
INSERT INTO `paymybuddytest`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('2', 'yomna@gmail.com', 'password', 'yomna', 'Sleaby', 'person');
INSERT INTO `paymybuddytest`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('3', 'aram@gmail.com', 'password', 'aram', 'Sleaby', 'person');
INSERT INTO `paymybuddytest`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('4', 'elena@gmail.com', 'password', 'elena', 'Sleaby', 'person');
INSERT INTO `paymybuddytest`.`client` (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('5', 'newFriend@gmail.com', 'password', 'New', 'Friend', 'person');

UPDATE `paymybuddytest`.`client` SET `client_password` = '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '1');
UPDATE `paymybuddytest`.`client` SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '2');
UPDATE `paymybuddytest`.`client` SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '3');
UPDATE `paymybuddytest`.`client` SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '4');
UPDATE `paymybuddytest`.`client` SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' WHERE (`client_id` = '5');


insert INTO `paymybuddytest`.`balance`(`amount`,`client_id`) values (100 , 1);
insert INTO `paymybuddytest`.`balance`(`amount`,`client_id`) values (100 , 2);
insert INTO `paymybuddytest`.`balance`(`amount`,`client_id`) values (100 , 3);
insert INTO `paymybuddytest`.`balance`(`amount`,`client_id`) values (100 , 4);
insert INTO `paymybuddytest`.`balance`(`amount`,`client_id`) values (100 , 5);


INSERT INTO `paymybuddytest`.`money_transaction` (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('1', '2021/09/15 20:14:00', '1', '2');
INSERT INTO `paymybuddytest`.`money_transaction` (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('2', '2021/09/15 20:40:00', '1', '3');
INSERT INTO `paymybuddytest`.`money_transaction` (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('3', '2021/09/15 20:43:00', '1', '4');
INSERT INTO `paymybuddytest`.`money_transaction` (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('4', '2021/09/15 20:30:00', '1', '2');

insert INTO `paymybuddytest`.`payment`(`transaction_id`,`motive`,`amount`,`fee`) values (1 , 'Trip money',40,0.2);
insert INTO `paymybuddytest`.`payment`(`transaction_id`,`motive`,`amount`,`fee`) values (2 , 'Movie Ticket ',10,0.05);
insert INTO `paymybuddytest`.`payment`(`transaction_id`,`motive`,`amount`,`fee`) values (3 , 'Restaurant Share',60,0.3);
insert INTO `paymybuddytest`.`payment`(`transaction_id`,`motive`,`amount`,`fee`) values (4 , 'Loyer Share',80,0.4);

INSERT INTO `paymybuddytest`.`friend` (`friend_id`) VALUES ('2');
INSERT INTO `paymybuddytest`.`friend` (`friend_id`) VALUES ('3');
INSERT INTO `paymybuddytest`.`friend` (`friend_id`) VALUES ('4');

INSERT INTO `paymybuddytest`.`friend_client` (`friend_id`, `client_id`) VALUES ('2', '1');
INSERT INTO `paymybuddytest`.`friend_client` (`friend_id`, `client_id`) VALUES ('3', '1');
INSERT INTO `paymybuddytest`.`friend_client` (`friend_id`, `client_id`) VALUES ('4', '1');


commit;
