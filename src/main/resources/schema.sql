CREATE TABLE accounts (
  account_number  NUMBER(9,0) PRIMARY KEY NOT NULL,
  pin             NUMBER(4,0) NOT NULL,
  opening_balance NUMBER(10,0) NOT NULL,
  overdraft       NUMBER(10,0) NOT NULL);

CREATE TABLE atm (
  note_amount NUMBER(3,0) PRIMARY KEY NOT NULL,
  note_count  NUMBER(5,0) NOT NULL);