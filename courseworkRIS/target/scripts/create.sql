/* CREATION  */
CREATE TABLE parking (id INTEGER NOT NULL,client_id INTEGER NOT NULL,car_id INTEGER NOT NULL,parking_place_id INTEGER NOT NULL,put_date DATETIME YEAR TO SECOND NOT NULL, pickup_date DATETIME YEAR TO SECOND, PRIMARY KEY(id));
CREATE TABLE parking_place (id INTEGER NOT NULL,number CHAR(10) NOT NULL,state BOOLEAN NOT NULL,description CHAR(100),PRIMARY KEY(id));
CREATE TABLE car (id INTEGER NOT NULL,mark_id CHAR(10) NOT NULL,st_number CHAR(10) NOT NULL,region CHAR(15) NOT NULL,PRIMARY KEY(id));
CREATE TABLE client (id INTEGER NOT NULL,surname CHAR(30) NOT NULL,name CHAR(30) NOT NULL,middlename CHAR(30) NOT NULL,passp_number CHAR(11) NOT NULL, PRIMARY KEY(id));
CREATE TABLE payment (id INTEGER NOT NULL,parking_id INTEGER NOT NULL,rate_id INTEGER NOT NULL,cost FLOAT NOT NULL,PRIMARY KEY(id));
CREATE TABLE rate(id INTEGER NOT NULL,h_cost INTEGER NOT NULL,PRIMARY KEY(id));
CREATE TABLE markhelper (id CHAR(10) NOT NULL,name CHAR(50) NOT NULL, PRIMARY KEY(id));

/* FK */
ALTER TABLE parking ADD CONSTRAINT FOREIGN KEY (client_id) REFERENCES client(id);
ALTER TABLE parking ADD CONSTRAINT FOREIGN KEY (car_id) REFERENCES car(id);
ALTER TABLE parking ADD CONSTRAINT FOREIGN KEY (parking_place_id) REFERENCES parking_place(id);
ALTER TABLE payment ADD CONSTRAINT FOREIGN KEY (parking_id) REFERENCES Parking(id);
ALTER TABLE payment ADD CONSTRAINT FOREIGN KEY (rate_id) REFERENCES Rate(id);
ALTER TABLE car ADD CONSTRAINT FOREIGN KEY (mark_id) REFERENCES markhelper(id);

/* PROCEDURES */
CREATE PROCEDURE payment_inserted(parking_id INTEGER)
UPDATE parking 
    SET pickup_date=CURRENT YEAR TO SECOND 
    WHERE id = parking_id;
END PROCEDURE;

CREATE PROCEDURE payment_updated(parking_id_old INTEGER,parking_id_new INTEGER)
DEFINE old_date DATETIME YEAR TO SECOND;
LET old_date = (SELECT parking.pickup_date
	    FROM parking
	    WHERE parking.id = parking_id_old);
UPDATE parking
    SET pickup_date="" 
    WHERE id = parking_id_old;
UPDATE parking 
    SET pickup_date=old_date 
    WHERE id = parking_id_new;
END PROCEDURE;

CREATE PROCEDURE payment_deleted(parking_id INTEGER)
UPDATE parking 
    SET pickup_date="" 
    WHERE id = parking_id;
END PROCEDURE;

CREATE PROCEDURE updatestate()
DEFINE place_id INTEGER;
UPDATE parking_place SET state="f";
FOREACH SELECT parking_place_id INTO place_id FROM parking WHERE pickup_date is NULL  ORDER BY parking_place_id	
	UPDATE parking_place SET state = "t" WHERE id = place_id;	
END FOREACH
END PROCEDURE;

/* TRIGGERS */
CREATE TRIGGER payment_insert
INSERT ON payment
REFERENCING NEW AS newrow
FOR EACH ROW (EXECUTE PROCEDURE payment_inserted(newrow.parking_id))
ENABLED;

CREATE TRIGGER payment_delete
DELETE ON payment
REFERENCING OLD AS newrow
FOR EACH ROW (EXECUTE PROCEDURE payment_deleted(newrow.parking_id))
ENABLED;

CREATE TRIGGER payment_update
UPDATE ON payment
REFERENCING OLD AS oldrow
	    NEW AS newrow
FOR EACH ROW (EXECUTE PROCEDURE payment_updated(oldrow.parking_id,newrow.parking_id))
ENABLED;

CREATE TRIGGER parking_insert
INSERT ON parking
FOR EACH ROW(EXECUTE PROCEDURE updatestate())
ENABLED;

CREATE TRIGGER parking_delete
DELETE ON parking
FOR EACH ROW(EXECUTE PROCEDURE updatestate())
ENABLED;

CREATE TRIGGER parking_update
UPDATE ON parking
FOR EACH ROW(EXECUTE PROCEDURE updatestate())
ENABLED;
