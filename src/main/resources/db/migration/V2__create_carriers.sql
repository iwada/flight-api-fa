CREATE TABLE IF NOT EXISTS carriers(
  id Integer Primary Key Generated Always as Identity,
  name VARCHAR(50),
  carrier_code VARCHAR(50)
);

 INSERT INTO carriers(name,carrier_code) VALUES('Carrier_Zodiac', 'ZOD');
 INSERT INTO carriers(name,carrier_code) VALUES('Carrier_Alpha','ALP');
 INSERT INTO carriers(name,carrier_code) VALUES('Carrier_Zagon','ZAG');
 INSERT INTO carriers(name,carrier_code) VALUES('Carrier_Argon','ARG');