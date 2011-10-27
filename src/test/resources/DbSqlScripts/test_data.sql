-- Author: Melih Gunal @10/14/2011
-- Test seed data. Administrator account password is: manager.
-- For all user accounts other than Administrator account the password is the same as account user name.
-- Secret password question answer for all accounts is: michr
-- Create user test seed records 
INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_win_start,
	comments)
VALUES
	(0,
	'administrator',
	'gunalmel@med.umich.edu',
	'01dc8f8613aefe48', 
	'408fb42b266374568db95f071c3e26cdb5963d811dab8d01879fdbf8353b8dac', 
	'For which organization are you working for?',
	'223b1dbfb8240a3c30aa1c60b05be1cc19e979d4ccdc20aeb6ee8a3c3a295b7a',
	true, 
	false, 
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	current_timestamp(), 
	current_timestamp(),
	current_timestamp(),
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	'Built-in administrator account.'
	);
INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_win_start,
	comments)
VALUES
	(1,
	'gunalmel',
	'gunalmel@yahoo.com',
	'29d14dc9c10785bd', 
	'9ac55e8b87fdb3ea13c179fe8b2177ccdb12e19a29943ca7bd0cca8d4af1f126', 
	'For which organization are you working for?',
	'0189aa9169f5be91651df11f7c8da582053753c3868c38eaac24f60c7c56135f',
	true, 
	false, 
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	current_timestamp(), 
	current_timestamp(),
	current_timestamp(),
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	'Developer account created for testing purposes in thee Organization_Admin role. Enabled, not locked'
	);

INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_win_start,
	comments)
VALUES
	(2,
	'test_org_admin',
	'test_org_admin@yahoo.com',
	'7f156c99518ca873', 
	'036097b600ff90a0bc80480d3e3c460ae66453049b8a3e123742cb6b986a34a7', 
	'For which organization are you working for?',
	'dc6da63f3898fc58e2beaf97896666945c51de823759ff745fe9669147f2aad3',
	true, 
	false, 
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	current_timestamp(), 
	current_timestamp(),
	current_timestamp(),
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	'Test user account in Organization_Admin role. Enabled, not locked'
	);
	
INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_win_start,
	comments)
VALUES
	(3,
	'test_org_sch',
	'test_org_sch@yahoo.com',
	'6c2d5fc5ef295352', 
	'b4419ff8d804502a0dd493eabb21ce87a3f96a817f3edfe770e0731413b1d12d', 
	'For which organization are you working for?',
	'95b0f5f60d6b07c9603f5687f6535ae5b4d1cf185d2cbf6b42abbf99dd308c73',
	true, 
	false, 
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	current_timestamp(), 
	current_timestamp(),
	current_timestamp(),
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	'Test user account in Organization_Scheduler role. Enabled, not locked'
	);
	
INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_win_start,
	comments)
VALUES
	(4,
	'test_scheduler',
	'test_scheduler@yahoo.com',
	'a683e58ca44ef47b', 
	'a0e2d830003a0e0145a3b586a9f4cafc79e9b853d943bfff9cbc1283679ee0b8', 
	'For which organization are you working for?',
	'a21093ecc5e228c9eab15560ccb6cae870e57699377ec9c7aae609e07b8e143b',
	true, 
	false, 
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	current_timestamp(), 
	current_timestamp(),
	current_timestamp(),
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	'Test user account in External_Scheduler role. Enabled, not locked'
	);
	
INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_win_start,
	comments)
VALUES
	(5,
	'test_disabled',
	'test_disabled@yahoo.com',
	'4bb90ae09ffc0e3f', 
	'd67e5086ed9f2f06d79cb7f647c8e37dbc97e92820d5eafd2fc60d104d31970c', 
	'For which organization are you working for?',
	'ebc96a609d57a9d437f6013361028bc382c243b576155653ec9b7b5781f744c5',
	false, 
	false, 
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	current_timestamp(), 
	current_timestamp(),
	current_timestamp(),
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	'Test user account in Organization_Scheduler role. Not approved (disabled), not locked'
	);
	
INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_win_start,
	comments)
VALUES
	(6,
	'test_locked',
	'test_locked@yahoo.com',
	'a90ef54b2d0c2891', 
	'01e813120ca098563057302f82b85f1e4c1dd8b5c7417eef5371f9709d5ec199', 
	'For which organization are you working for?', 
	'721cf5f7e574644c5cc4cac6d61b91c43ce2cf898c4e09eaff080881357b1140',
	true, 
	true, 
	current_timestamp()-55/60/24,
	current_timestamp()-1461, 
	current_timestamp()-1/24,
	current_timestamp()-100,
	current_timestamp()-1/24,
	'Test user account in External_Scheduler role. Disabled because it is locked, locked'
	);

INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_win_start,
	comments)
VALUES
	(7,
	'test_acc_expired',
	'test_acc_expired@yahoo.com',
	'9197c151a95cba91', 
	'd8cfb60321aff4dfbfc8fb0fa48bfa68cda16666d3c86c058b7a85762af38f57', 
	'For which organization are you working for?',
	'26bbd1241268ad46674ce7b153dda636bb46f96d170fc3cd56caa1184d75d6fb',
	true, 
	false, 
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	current_timestamp()-1461, 
	current_timestamp()-400,
	current_timestamp()-100,
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	'Test user account in External_Scheduler role. Account (disabled) has expired because the last login date is 400 days before current date. (Inactivity grace period is 1 yr.)'
	);

INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_win_start,
	comments)
VALUES
	(8,
	'test_pwd_expired',
	'test_pwd_expired@yahoo.com',
	'3319650a536fc013', 
	'1881e9cfeb432e08bea68077b9168d516a2faf59a7be23c063fd35bead6d9929', 
	'For which organization are you working for?',
	'5a34ffa0d88b8354f6b6aae04159614016df1fdf0af56c8f023c8342e95e5431',
	true, 
	false, 
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	current_timestamp()-1461, 
	current_timestamp()-100,
	current_timestamp()-400,
	PARSEDATETIME('01/01/0000 12:00:00 EST','MM/dd/yyyy HH:mm:ss', 'en', 'EST'),
	'Test user account in External_Scheduler role. Password has expired and the user needs to reset it when the user logs in with the old password for the first time after pwd has expired.)'
	);

INSERT
	INTO public.user_account(id,
	user_name,
	email,
	password_salt,
	password,
	password_question,
	password_answer,
	enabled,
	locked,
	last_lock_out_date,
	created_date,
	last_login_date,
	last_password_changed_date,
	failed_pwd_answer_win_start,
	comments)
VALUES
	(9,
	'test_pwdans_locked',
	'test_pwdans_locked@yahoo.com',
	'357f1034f68b4107', 
	'b6906bb76be4516143af0c7e2e7a50df8f67a31b0ee36d82b0da9bb36a351e2b', 
	'For which organization are you working for?',
	'22bf9aaf79f9db5e06ad731d95c44e392dbfdd7e5f61a40ec15a41975ca8652c',
	true, 
	true, 
	current_timestamp()-55/60/24,
	current_timestamp()-1461, 
	current_timestamp()-100,
	current_timestamp()-100,
	current_timestamp()-1/24,
	'Test user account in External_Scheduler role. Password has expired and the user needs to reset it when the user logs in with the old password for the first time after pwd has expired.)'
	);
--Create USER_DETAILS record for every one of USER_ACCOUNT records
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(0,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(1,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(2,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(3,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(4,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(5,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(6,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(7,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(8,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
INSERT
	INTO PUBLIC.USER_DETAILS(USER_ID,
	LAST_NAME,
	FIRST_NAME,
	MIDDLE_NAME,
	EMPLOYEE_ID,
	UNIQUE_NAME,
	INSTITUTION_NAME,
	DEPARTMENT_NAME,
	PHONE,
	PAGER)
VALUES
	(9,
	'Gunal',
	'Melih',
	'',
	'71389619',
	'gunalmel',
	'University of Michigan',
	'MICHR',
	'7349986145',
	'N/A');
-- Create roles
INSERT INTO PUBLIC.ROLE (ID, ROLE_NAME, DESCRIPTION) VALUES (0, 'Administrator', 'Application administrator who has unlimited privileges within the application domain. User would be responsible for maintaining and setting up the application. Staff from and IT help desk support would be an ideal candidate');
INSERT INTO PUBLIC.ROLE (ID, ROLE_NAME, DESCRIPTION) VALUES (1, 'Organization_Admin', 'Administrator user for a given organization. This user is a power user capable of maintaining user access privileges for the given organization. This user can also post announcements and upload cheat sheet for the given organization');
INSERT INTO PUBLIC.ROLE (ID, ROLE_NAME, DESCRIPTION) VALUES (2, 'Organization_Scheduler', 'User for a given organization who can process');
INSERT INTO PUBLIC.ROLE (ID, ROLE_NAME, DESCRIPTION) VALUES (3, 'External_Scheduler', 'A study team member who can submit/update,cancel scheduling requests. The user can also access/update all scheduling requests for the study protocol the user is affiliated with. Protocol Access List would have affiliation information');
-- Assign roles to users	
insert into public.user_role values (0,0);
insert into public.user_role values (0,1);
insert into public.user_role values (1,1);
insert into public.user_role values (2,1);
insert into public.user_role values (3,2);
insert into public.user_role values (4,3);
insert into public.user_role values (5,2);
insert into public.user_role values (6,3);
insert into public.user_role values (7,3);
insert into public.user_role values (8,3);
insert into public.user_role values (9,3);

