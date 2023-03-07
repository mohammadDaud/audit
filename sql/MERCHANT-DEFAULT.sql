
-----------READ ME-----------------

Before creating any trxn in test/sign up please verify:
 1- User is active and verified through email.
 2- Key Store file is defined for 'LOGIBIZ' institution.
 3- Run this script before maps start, App configuration is cached on maps start.
 4- Every single change against terminal is cached, if you done any change through table it will reflect after re-run only.
 5- Brand ranges table is cached on server startup.

-----------------------------------

Insert into MCA_GROUP (ID,MCA_GROUP_NAME,DESCRIPTION,INST_ID,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON) values (MCA_GROUP_SEQ.nextval,'MCA_DEFAULT',null,1,'SYSTEM',sysdate,null,null);
Insert into INSTITUTION (ID,INSTITUTION_ID,INSTITUTION_NAME,DESCRIPTION,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,STATUS,WEB_ADDRESS,LANGUAGE,DATA_ENCRYPTION_KEY,NUMBER_OF_USER,LOGO) 
values (INSTITUTION_SEQ.nextval,'LOGIBIZ','LogiBizTech',null,'SYSTEM',sysdate,null,null,'A','www.logibiztech.com','en',null,10,null);
Insert into MERCHANT_CATEGORY (ID,CODE,DESCRIPTION,INST_ID) values (MC_SEQ.nextval,'5469','MCC_DEFAULT',(select id from institution where institution_id = 'LOGIBIZ'));
Insert into CURRENCY (ID,CURRENCY_CODE,DESCRIPTION,MINOR_DIGIT,SYMBOL,CURRENCY_NAME,INST_ID) values (CURRENCY_SEQ.nextval,'692','SAR',9,'SAR','SAR',(select id from institution where institution_id = 'LOGIBIZ'));
Insert into COUNTRY (ID,NAME,CODE,ISO_CODE,DESCRIPTION,CREATED_BY,CREATED_DATE,MODIFIED_DATE) values (COUNTRY_SEQ.nextval,'KSA','692','692',null,'SYSTEM',sysdate, sysdate);
Insert into COUNTRY (ID,NAME,CODE,ISO_CODE,DESCRIPTION,CREATED_BY,CREATED_DATE,MODIFIED_DATE) values (COUNTRY_SEQ.nextval,'PAK','586','586',null,'SYSTEM',sysdate, sysdate);
Insert into COUNTRY (ID,NAME,CODE,ISO_CODE,DESCRIPTION,CREATED_BY,CREATED_DATE,MODIFIED_DATE) values (COUNTRY_SEQ.nextval,'UAE','784','784',null,'SYSTEM',sysdate, sysdate);
Insert into COUNTRY (ID,NAME,CODE,ISO_CODE,DESCRIPTION,CREATED_BY,CREATED_DATE,MODIFIED_DATE) values (COUNTRY_SEQ.nextval,'UK','092','092',null,'SYSTEM',sysdate, sysdate);


Insert into EX_CONNECTIONS (ID,EX_CONNECTION_ID,INST_ID,DESCRIPTION,PROTOCOL,TIMEOUT,SYSTEM,RETRY_ATTEMPTS,DESTINATION,MAX_CONNECTIONS) 
values (EX_CONNECTIONS_SEQ.nextval,'DEFAULT_EX-CONNECTION',(select id from institution where institution_id = 'LOGIBIZ'),null,'HTTPS',1,'PANA_AUTH',1,'auth01.logibiztech.com:8443/auth/auth.htm',1);

Insert into RISK_PROFILE (ID,RISK_PROFILE_ID,CURRENCY_ID,ENABLE_NEGATIVE_BIN,ENABLE_NEGATIVE_IP,ENABLE_NEGATIVE_CARD,ENABLE_DECLINED_CARD,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON,DESCRIPTION, INST_ID)
values (RISK_PROFILE_SEQ.nextval,'DEFAULT_RISK',(select id from currency where currency_code = '692'),0,0,0,0,'anonymousUser',to_date('13-DEC-21','DD-MON-RR'),null,null,null,(select id from institution where institution_id = 'LOGIBIZ'));

Insert into RISK_PROFILE_CARD_CONFIG (ID,RISK_PROFILE_ID,RETENTION_DURATION,MAX_DEBIT_AMT_PER_CARD,MAX_CREDIT_AMT_PER_CARD,MIN_TXN_AMT_PER_CARD,MAX_TXN_COUNT_PER_CARD,LEVEL1_THRESHOLD_PER,LEVEL1_MOBILE_NUMBERS,LEVEL1_EMAIL_IDS,LEVEL2_THRESHOLD_PER,LEVEL2_MOBILE_NUMBERS,LEVEL2_EMAIL_IDS,LEVEL3_MOBILE_NUMBERS,LEVEL3_EMAIL_IDS) 
values (RISK_PROFILE_CARD_SEQ.nextval,(SELECT ID FROM RISK_PROFILE WHERE RISK_PROFILE_ID ='DEFAULT_RISK'),0,0,0,0,0,0,null,null,0,null,null,null,null);

Insert into RISK_PROFILE_TERMINAL_CONFIG (ID,RISK_PROFILE_ID,MAX_FLOOR_LIMIT_TXN_AMT,RETENTION_DURATION,MAX_FLOOR_LIMIT_TXN_COUNT,MAX_PROCESSING_AMT,MAX_CREDIT_PROCESSING_AMT,ENABLE_AUTO_INACTIVE_TERMINAL,LEVEL1_THRESHOLD_PER,LEVEL1_MOBILE_NUMBERS,LEVEL1_EMAIL_IDS,LEVEL2_THRESHOLD_PER,LEVEL2_MOBILE_NUMBERS,LEVEL2_EMAIL_IDS,LEVEL3_MOBILE_NUMBERS,LEVEL3_EMAIL_IDS) 
values (RISK_PROFILE_TERMINAL_SEQ.nextval,(SELECT ID FROM RISK_PROFILE WHERE RISK_PROFILE_ID ='DEFAULT_RISK'),0,0,0,500,500,null,0,null,null,0,null,null,null,null);

Insert into PAYMENT_INSTRUMENT(ID,NAME,CREATED_ON,CREATED_BY,MODIFIED_BY,MODIFIED_ON, INST_ID) 
values (PAYMENT_INSTRUMENT_SEQ.nextval,'DEFAULT_INSTR',sysdate,'SYSTEM',null,null,(select id from institution where institution_id = 'LOGIBIZ'));

Insert into BRAND_TYPE(ID,BRAND_TYPE_NAME,INST_ID,DESCRIPTION,CREATED_BY,CREATED_ON,MODIFIED_BY,MODIFIED_ON)
values(BRAND_TYPE_SEQ.nextval,'DEFAULT_TYPE',(select id from institution where institution_id = 'LOGIBIZ'),null,'SYSTEM',sysdate,null,null);
Insert into BRAND (ID,BRAND_NAME,BRAND_TYPE_ID,DESCRIPTION,PAYMENT_INSTRUMENT,INST_ID,BIN_RANGE)
values (BRAND_SEQ.nextval,'VISA',(select id from brand_type where BRAND_TYPE_NAME = 'DEFAULT_TYPE'),null,
(select id from PAYMENT_INSTRUMENT where name = 'DEFAULT_INSTR'),(select id from institution where institution_id = 'LOGIBIZ'),null);
Insert into BRAND (ID,BRAND_NAME,BRAND_TYPE_ID,DESCRIPTION,PAYMENT_INSTRUMENT,INST_ID,BIN_RANGE)
values (BRAND_SEQ.nextval,'MASTERCARD',(select id from brand_type where BRAND_TYPE_NAME = 'DEFAULT_TYPE'),null,
(select id from PAYMENT_INSTRUMENT where name = 'DEFAULT_INSTR'),(select id from institution where institution_id = 'LOGIBIZ'),null);
Insert into BRAND (ID,BRAND_NAME,BRAND_TYPE_ID,DESCRIPTION,PAYMENT_INSTRUMENT,INST_ID,BIN_RANGE)
values (BRAND_SEQ.nextval,'AMEX',(select id from brand_type where BRAND_TYPE_NAME = 'DEFAULT_TYPE'),null,
(select id from PAYMENT_INSTRUMENT where name = 'DEFAULT_INSTR'),(select id from institution where institution_id = 'LOGIBIZ'),null);
Insert into BRAND (ID,BRAND_NAME,BRAND_TYPE_ID,DESCRIPTION,PAYMENT_INSTRUMENT,INST_ID,BIN_RANGE)
values (BRAND_SEQ.nextval,'MADA',(select id from brand_type where BRAND_TYPE_NAME = 'DEFAULT_TYPE'),null,
(select id from PAYMENT_INSTRUMENT where name = 'DEFAULT_INSTR'),(select id from institution where institution_id = 'LOGIBIZ'),null);

insert into app_config values (app_config_seq.nextval, 'DEFAULT_USER_ROLE', 'MERCHANT_DEFAULT','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_MCA_GROUP', 'MCA_DEFAULT','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_INSTITUTION', 'LOGIBIZ','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_MERCHANT_CATEGORY', '5469','MERCHANT_SIGNUP');           
insert into app_config values (app_config_seq.nextval, 'DEFAULT_CURRENCY', '692','MERCHANT_SIGNUP');

insert into app_config values (app_config_seq.nextval, 'DEFAULT_RISK_PROFILE', 'DEFAULT_RISK','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_AUTH', 'DEFAULT_EX-CONNECTION','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_BRAND', 'VISA,MASTERCARD,AMEX,MADA','MERCHANT_SIGNUP');

insert into app_config values (app_config_seq.nextval, 'DEFAULT_CARD_ACCEPTOR_ID', '01','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_CARD_ACCEPTOR_TERMINAL_ID', 'T1','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_CARD_INSTITUTION_ID', 'LOGB','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_TERMINAL_PASSWORD', 'p@ssw0rd','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_TERMINAL_LANGUAGE', 'ENG','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_MERCHANT_SECRET_KEY', '1234567812345678','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_SAF_COUNT', '1','MERCHANT_SIGNUP');
insert into app_config values (app_config_seq.nextval, 'DEFAULT_RISK_FLAG', 'false','MERCHANT_SIGNUP');

INSERT INTO app_config VALUES (app_config_seq.nextval, 'PURC' , '1', 'ACTION_CODE');
INSERT INTO app_config VALUES (app_config_seq.nextval, 'REVERSAL' , '2', 'ACTION_CODE');
INSERT INTO app_config VALUES (app_config_seq.nextval, 'INQ' , '5', 'ACTION_CODE');
INSERT INTO app_config VALUES (app_config_seq.nextval, 'VAUTH' , '6', 'ACTION_CODE');
INSERT INTO app_config VALUES (app_config_seq.nextval, 'VCAPT' , '7', 'ACTION_CODE');
INSERT INTO app_config VALUES (app_config_seq.nextval, 'VPURC' , '8', 'ACTION_CODE');
INSERT INTO app_config VALUES (app_config_seq.nextval, 'VCRED' , '9', 'ACTION_CODE');
INSERT INTO app_config VALUES (app_config_seq.nextval, 'NOTF' , '10', 'ACTION_CODE');

INSERT INTO BRAND_RANGE(ID,brand_id,to_range,from_range,priority)
VALUES (BRAND_RANGE_SEQ.nextval,(select id from brand where brand_name='VISA'),499999,400000,9);
INSERT INTO BRAND_RANGE(ID,brand_id,to_range,from_range,priority)
VALUES (BRAND_RANGE_SEQ.nextval,(select id from brand where brand_name='MASTERCARD'),599999,500000,9)
INSERT INTO BRAND_RANGE(ID,brand_id,to_range,from_range,priority)
VALUES (BRAND_RANGE_SEQ.nextval,(select id from brand where brand_name='MADA'),491234,491234,1);
INSERT INTO BRAND_RANGE(ID,brand_id,to_range,from_range,priority)
VALUES (BRAND_RANGE_SEQ.nextval,(select id from brand where brand_name='MADA'),411199,411111,1);
INSERT INTO BRAND_RANGE(ID,brand_id,to_range,from_range,priority)
VALUES (BRAND_RANGE_SEQ.nextval,(select id from brand where brand_name='MADA'),449999,440000,1);
INSERT INTO BRAND_RANGE(ID,brand_id,to_range,from_range,priority)
VALUES (BRAND_RANGE_SEQ.nextval,(select id from brand where brand_name='AMEX'),399999,300000,9);

insert into ex_con_brand(brand_id,ex_con_id) values((select id from brand where brand_name = 'VISA'),(select id from ex_connections where ex_connection_id = 'DEFAULT_ACCESS_POINT'));
insert into ex_con_brand(brand_id,ex_con_id) values((select id from brand where brand_name = 'VISA'),(select id from ex_connections where ex_connection_id = 'DEFAULT_DIRECTY_SERVER'));
insert into ex_con_brand(brand_id,ex_con_id) values((select id from brand where brand_name = 'MADA'),(select id from ex_connections where ex_connection_id = 'DEFAULT_ACCESS_POINT'));
insert into ex_con_brand(brand_id,ex_con_id) values((select id from brand where brand_name = 'MADA'),(select id from ex_connections where ex_connection_id = 'DEFAULT_DIRECTY_SERVER'));
insert into ex_con_brand(brand_id,ex_con_id) values((select id from brand where brand_name = 'MASTERCARD'),(select id from ex_connections where ex_connection_id = 'DEFAULT_ACCESS_POINT'));
insert into ex_con_brand(brand_id,ex_con_id) values((select id from brand where brand_name = 'MASTERCARD'),(select id from ex_connections where ex_connection_id = 'DEFAULT_DIRECTY_SERVER'));
insert into ex_con_brand(brand_id,ex_con_id) values((select id from brand where brand_name = 'AMEX'),(select id from ex_connections where ex_connection_id = 'DEFAULT_ACCESS_POINT'));
insert into ex_con_brand(brand_id,ex_con_id) values((select id from brand where brand_name = 'AMEX'),(select id from ex_connections where ex_connection_id = 'DEFAULT_DIRECTY_SERVER'));


Insert into INSTITUTION_CONFIGURATION (ID,INSTITUTION_ID,ENABLE_CAPTCHA,BLOCK_UNSECURE_IP,ENABLE_PROFILE_PASSWORD_AUTH,ENABLE_FEES,ENABLE_SECURE_AUTH,ALLOWED_BATCH_THREAD_COUNT,CRYPTOGRAPHIC_METHOD,AUTH_LEVEL,
ENABLE_STANDING_INSTRUCTION,ENABLE_ONE_CLICK_CHECKOUT,AUTH_ENTITY,ENABLE_IFRAME,ENABLE_RISK_ALERT,ENABLE_PAYMENT_ADVICE,ACTIVATE_PAY_NOW,DECLINE_TXN_RETENTION_MIN,DECLINE_MAX_COUNT,BATCH_UPLOAD_DIR,BATCH_DOWNLOAD_DIR,BATCH_ARCHIVE_DIR,
ENABLE_CARD_NUMBER_MASK,CARD_EXPIRY_YEAR_TYPE,CARD_EXPIRY_YEAR,SELECT_YEAR,ENABLE_MULTIPLE_CAPTURE_AUTH,ENABLE_CAPTRE_AMT_GT_AUTH_AMT,ENABLE_MULTIPLE_VOID_TXN,ENABLE_VOID_AMT_GT_ORIG_AMT,ENABLE_MULTPLE_CR_PR_DEBT,ENABLE_CREDIT_AMT_GT_DEBIT,ENABLE_BATCH_FILE_PROTECTION,
ENABLE_CVO2CVV,MASK_START_LENGTH,MASK_LAST_LENGTH,MASK_SYMBOL) 
values (INSTITUTION_CONFIGURATION_SEQ.nextval,(select id from institution),null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,6,4,'*');

insert into state(ID,COUNTRY_ID,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE,STATE_CODE,STATE_NAME)
values(state_seq.nextval,(select id from country where iso_code = '692'),'SYSTEM',sysdate,null,null,'123','Riyadh');
insert into city(ID,STATE_ID,CREATED_BY,CREATED_DATE,MODIFIED_BY,MODIFIED_DATE,CITY_CODE,CITY_NAME)
values(city_seq.nextval,(select id from state where state_code = '123'),'SYSTEM',sysdate,null,null,'123','Riyadh')
