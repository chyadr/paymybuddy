        CREATE TABLE pay_user
            (
            id INT PRIMARY KEY NOT NULL,
            first_name VARCHAR(50),
            last_name varchar(50),
            email varchar(100) UNIQUE,
            user_password varchar (60)
            );


        CREATE TABLE pay_connection
            (
            user_id int  NOT NULL,
            connected_user_id int NOT NULL,
            PRIMARY KEY(user_id,connected_user_id),
            FOREIGN KEY (user_id) REFERENCES pay_user(id),
            FOREIGN KEY (connected_user_id) REFERENCES pay_user(id)
            );


        CREATE TABLE pay_transaction
            (
            id INT PRIMARY KEY NOT NULL,
            user_id int  NOT NULL,
            connected_user_id int  NOT NULL,
            amount DECIMAL(10,2),
            description varchar(200),
            type varchar(10),
            FOREIGN KEY (user_id,connected_user_id) REFERENCES pay_connection(user_id,connected_user_id)
            );

            CREATE TABLE pay_account
            (
            id INT PRIMARY KEY NOT NULL,
            user_id int  NOT NULL,
            balance DECIMAL(10,2),
            FOREIGN KEY (user_id) REFERENCES pay_user(id)
            );

            CREATE TABLE bank_account
            (
            id INT PRIMARY KEY NOT NULL,
            user_id int  NOT NULL,
            iban varchar(200),
            bic varchar(200),
            FOREIGN KEY (user_id) REFERENCES pay_user(id)
            );

        create SEQUENCE seq_ids increment by 1;
