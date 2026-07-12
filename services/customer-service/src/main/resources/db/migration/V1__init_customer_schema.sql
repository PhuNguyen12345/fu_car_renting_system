CREATE TABLE users (
                       id uuid NOT NULL,
                       email character varying(255) NOT NULL,
                       password_hash character varying(255) NOT NULL,
                       full_name character varying(255) NOT NULL,
                       phone character varying(50),
                       date_of_birth date,
                       cccd character varying(50),
                       cccd_url character varying(500),
                       gplx_number character varying(50),
                       address character varying(255),
                       avatar_url character varying(500),
                       gplx_url character varying(500),
                       status character varying(20) NOT NULL,
                       role character varying(20) NOT NULL,
                       admin_note text,
                       created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                       updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                       deleted_at timestamp without time zone
);

ALTER TABLE ONLY users ADD CONSTRAINT users_pkey PRIMARY KEY (id);
ALTER TABLE ONLY users ADD CONSTRAINT users_email_key UNIQUE (email);