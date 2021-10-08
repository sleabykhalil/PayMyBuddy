/* Setting up PROD DB */
create database if not exists paymybuddy;
use paymybuddy;

drop table IF EXISTS balance,client,CLIENT_FRIENDS,money_transaction,payment;


create TABLE client (
                client_id  BIGINT  AUTO_INCREMENT NOT NULL,
                email_account VARCHAR(200) NOT NULL,
                client_password VARCHAR(200),
                first_name VARCHAR(200) ,
                last_name VARCHAR(200) ,
                client_type VARCHAR(200) ,
                PRIMARY KEY (client_id)
);


create TABLE CLIENT_FRIENDS  (
                client_client_id BIGINT NOT NULL,
                friends_client_id BIGINT NOT NULL
);


create TABLE money_transaction (
                id BIGINT  AUTO_INCREMENT NOT NULL,
                money_transaction_timestamp DATETIME ,
                sender_client_id BIGINT ,
                receiver_client_id BIGINT ,
                PRIMARY KEY (id)
);


create TABLE payment (
                transaction_id BIGINT,
                motive VARCHAR(200),
                amount DOUBLE  ,
                fee DOUBLE
);


create TABLE balance (
                amount NUMERIC(10,2),
                client_id BIGINT
);



alter table balance add CONSTRAINT client_balance_fk
FOREIGN KEY (client_id)
REFERENCES client (client_id)
ON delete NO ACTION
ON update NO ACTION;

alter table money_transaction add CONSTRAINT client_transaction_fk
FOREIGN KEY (sender_client_id)
REFERENCES client (client_id)
ON delete NO ACTION
ON update NO ACTION;



alter table payment add CONSTRAINT transaction_payment_fk
FOREIGN KEY (transaction_id)
REFERENCES money_transaction (id)
ON delete NO ACTION
ON update NO ACTION;

alter table `client`
ADD UNIQUE INDEX `email_account_UNIQUE` (`email_account` ASC) VISIBLE;



insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('1','khalil@gmail.com', 'password', 'Khalil', 'Sleaby', 'person');
insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('2', 'yomna@gmail.com', 'password', 'yomna', 'Sleaby', 'person');
insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('3', 'aram@gmail.com', 'password', 'aram', 'Sleaby', 'person');
insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('4', 'elena@gmail.com', 'password', 'elena', 'Sleaby', 'person');
insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('5', 'newFriend@gmail.com', 'password', 'New', 'Friend', 'person');

update client SET `client_password` = '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '1');
update client SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '2');
update client SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '3');
update client SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '4');
update client SET `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '5');


insert into balance(`amount`,`client_id`) values (100 , 1);
insert into balance(`amount`,`client_id`) values (100 , 2);
insert into balance(`amount`,`client_id`) values (100 , 3);
insert into balance(`amount`,`client_id`) values (100 , 4);
insert into balance(`amount`,`client_id`) values (100 , 5);


insert into money_transaction (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('1', '2021/09/15 20:14:00', '1', '2');
insert into money_transaction (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('2', '2021/09/15 20:40:00', '1', '3');
insert into money_transaction (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('3', '2021/09/15 20:43:00', '1', '4');
insert into money_transaction (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('4', '2021/09/15 20:30:00', '1', '2');

insert into payment(`transaction_id`,`motive`,`amount`,`fee`) values (1 , 'Trip money',40,0.2);
insert into payment(`transaction_id`,`motive`,`amount`,`fee`) values (2 , 'Movie Ticket ',10,0.05);
insert into payment(`transaction_id`,`motive`,`amount`,`fee`) values (3 , 'Restaurant Share',60,0.3);
insert into payment(`transaction_id`,`motive`,`amount`,`fee`) values (4 , 'Loyer Share',80,0.4);



insert into CLIENT_FRIENDS (`client_client_id`, `friends_client_id`) VALUES ('2', '1');
insert into CLIENT_FRIENDS (`client_client_id`, `friends_client_id`) VALUES ('1', '2');
commit;




/* Setting up TEST DB */
create database  if not exists paymybuddy;
use paymybuddy;

drop table IF EXISTS balance,client,CLIENT_FRIENDS,money_transaction,payment;



create TABLE client (
                client_id  BIGINT  AUTO_INCREMENT NOT NULL,
                email_account VARCHAR(200) NOT NULL,
                client_password VARCHAR(200),
                first_name VARCHAR(200) ,
                last_name VARCHAR(200) ,
                client_type VARCHAR(200) ,
                PRIMARY KEY (client_id)
);


create TABLE CLIENT_FRIENDS  (
                client_client_id BIGINT NOT NULL,
                friends_client_id BIGINT NOT NULL
);


create TABLE money_transaction (
                id BIGINT  AUTO_INCREMENT NOT NULL,
                money_transaction_timestamp DATETIME ,
                sender_client_id BIGINT ,
                receiver_client_id BIGINT ,
                PRIMARY KEY (id)
);


create TABLE payment (
                transaction_id BIGINT,
                motive VARCHAR(200),
                amount DOUBLE  ,
                fee DOUBLE
);


create TABLE balance (
                amount NUMERIC(10,2),
                client_id BIGINT
);



alter table balance add CONSTRAINT client_balance_fk
FOREIGN KEY (client_id)
REFERENCES client (client_id)
ON delete NO ACTION
ON update NO ACTION;

alter table money_transaction add CONSTRAINT client_transaction_fk
FOREIGN KEY (sender_client_id)
REFERENCES client (client_id)
ON delete NO ACTION
ON update NO ACTION;



alter table payment add CONSTRAINT transaction_payment_fk
FOREIGN KEY (transaction_id)
REFERENCES money_transaction (id)
ON delete NO ACTION
ON update NO ACTION;

alter table `client`
ADD UNIQUE INDEX `email_account_UNIQUE` (`email_account` ASC) VISIBLE;



insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('1','khalil@gmail.com', 'password', 'Khalil', 'Sleaby', 'person');
insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('2', 'yomna@gmail.com', 'password', 'yomna', 'Sleaby', 'person');
insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('3', 'aram@gmail.com', 'password', 'aram', 'Sleaby', 'person');
insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('4', 'elena@gmail.com', 'password', 'elena', 'Sleaby', 'person');
insert into client (`client_id`, `email_account`, `client_password`, `first_name`, `last_name`, `client_type`) VALUES ('5', 'newFriend@gmail.com', 'password', 'New', 'Friend', 'person');

update client set `client_password` = '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '1');
update client set `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '2');
update client set `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '3');
update client set `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '4');
update client set `client_password` =  '$2a$10$ku7127I3J817VOIcMgvwfO.CBEKo1YtDBNvL6qCbal2sSfA2QrJea' where (`client_id` = '5');


insert into balance(`amount`,`client_id`) values (100 , 1);
insert into balance(`amount`,`client_id`) values (100 , 2);
insert into balance(`amount`,`client_id`) values (100 , 3);
insert into balance(`amount`,`client_id`) values (100 , 4);
insert into balance(`amount`,`client_id`) values (100 , 5);


insert into money_transaction (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('1', '2021/09/15 20:14:00', '1', '2');
insert into money_transaction (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('2', '2021/09/15 20:40:00', '1', '3');
insert into money_transaction (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('3', '2021/09/15 20:43:00', '1', '4');
insert into money_transaction (`id`, `money_transaction_timestamp`, `sender_client_id`, `receiver_client_id`) VALUES ('4', '2021/09/15 20:30:00', '1', '2');

insert into payment(`transaction_id`,`motive`,`amount`,`fee`) values (1 , 'Trip money',40,0.2);
insert into payment(`transaction_id`,`motive`,`amount`,`fee`) values (2 , 'Movie Ticket ',10,0.05);
insert into payment(`transaction_id`,`motive`,`amount`,`fee`) values (3 , 'Restaurant Share',60,0.3);
insert into payment(`transaction_id`,`motive`,`amount`,`fee`) values (4 , 'Loyer Share',80,0.4);


insert into CLIENT_FRIENDS (`client_client_id`, `friends_client_id`) VALUES ('2', '1');
insert into CLIENT_FRIENDS (`client_client_id`, `friends_client_id`) VALUES ('1', '2');




commit;
