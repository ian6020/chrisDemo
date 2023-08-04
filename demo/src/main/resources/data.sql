CREATE SCHEMA IF NOT EXISTS demo;
SET SCHEMA demo;
CREATE TABLE A_TRANSACTION (
	TRANS_ID VARCHAR(100) NOT NULL, 
	USERID VARCHAR(100) NOT NULL,
	TOTAL_PURCHASE INTEGER,
	TRANS_DATE DATE,
	CLAIM_FLAG VARCHAR(3)
);

INSERT INTO A_TRANSACTION (TRANS_ID, USERID, TOTAL_PURCHASE, TRANS_DATE, CLAIM_FLAG) VALUES (1, 'demo', 120, '2023-07-22', 'N');
INSERT INTO A_TRANSACTION (TRANS_ID, USERID, TOTAL_PURCHASE, TRANS_DATE, CLAIM_FLAG) VALUES (2, 'demo', 70, '2023-08-01', 'N');
INSERT INTO A_TRANSACTION (TRANS_ID, USERID, TOTAL_PURCHASE, TRANS_DATE, CLAIM_FLAG) VALUES (3, 'demo', 75, '2023-07-07', 'N');
INSERT INTO A_TRANSACTION (TRANS_ID, USERID, TOTAL_PURCHASE, TRANS_DATE, CLAIM_FLAG) VALUES (4, 'demo', 55, '2023-08-02', 'N');
INSERT INTO A_TRANSACTION (TRANS_ID, USERID, TOTAL_PURCHASE, TRANS_DATE, CLAIM_FLAG) VALUES (5, 'demo', 240, '2023-07-15', 'N');