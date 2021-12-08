CREATE TABLE User_Account (
  account_Number INTEGER(9) NOT NULL,
  pin INTEGER(4) NOT NULL,
  balance_Amount DOUBLE (10),
  overdraft_Amount DOUBLE(10),  
  PRIMARY KEY (account_Number)
);

CREATE TABLE Atm_Cash_Details (
  notes_Value INTEGER NOT NULL ,
  notes_Volume INTEGER,
  PRIMARY KEY (notes_Value)
);