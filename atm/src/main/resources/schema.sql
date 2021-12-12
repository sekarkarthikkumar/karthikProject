CREATE TABLE USER_ACCOUNT (
  account_number INTEGER(9) NOT NULL,
  pin INTEGER(4) NOT NULL,
  balance_amount DOUBLE (10),
  overdraft_amount DOUBLE(10),  
  PRIMARY KEY (account_number)
);

CREATE TABLE ATM_CASH_DETAILS (
  notes_value INTEGER NOT NULL ,
  notes_volume INTEGER,
  PRIMARY KEY (notes_value)
);