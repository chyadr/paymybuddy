            insert into pay_user (id,first_name,last_name,email,user_password)
            values (nextval('seq_ids'),'David','DUPONT','david.dupont@gmail.com','$2a$10$idhylz2sQ4ILMygmp0YcVe4WkgD1Oy8ExiJaAdvAN/nzBp4Lm2yVm');

            insert into pay_user (id,first_name,last_name,email,user_password)
            values (nextval('seq_ids'),'Caroline','MASA','caroline.masa@gmail.com','$2a$12$WFVxtsP7naiEWb1At.EL5uk2SwUcYaOoxdhonhzardWC9eh3R97Iy');

            insert into pay_account (id,user_id,balance)
            values (nextval('seq_ids'),(select id from pay_user where first_name='David'),2000);

            insert into pay_account (id,user_id,balance)
            values (nextval('seq_ids'),(select id from pay_user where first_name='Caroline'),1000);


            insert into pay_connection (user_id,connected_user_id)
            values ((select id from pay_user where first_name='David') ,(select id from pay_user where first_name='Caroline'));

            insert into pay_transaction (id,user_id,connected_user_id,amount,type,description)
            values(nextval('seq_ids'),(select user_id from pay_connection where user_id in(select id from pay_user where first_name='David')),(select connected_user_id from pay_connection where user_id in(select id from pay_user where first_name='David')),20,'DEBIT','Restraunt');

            insert into pay_transaction (id,user_id,connected_user_id,amount,type,description)
            values(nextval('seq_ids'),(select user_id from pay_connection where user_id in(select id from pay_user where first_name='David')),(select connected_user_id from pay_connection where user_id in(select id from pay_user where first_name='David')),5,'DEBIT','Gift');

