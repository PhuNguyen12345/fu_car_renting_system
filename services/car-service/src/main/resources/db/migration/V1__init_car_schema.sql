CREATE TABLE brands (
                        id uuid NOT NULL,
                        name character varying(255) NOT NULL,
                        logo_url character varying(500)
);

CREATE TABLE locations (
                           id uuid NOT NULL,
                           name character varying(255) NOT NULL,
                           city character varying(100) NOT NULL,
                           address character varying(500) NOT NULL
);

CREATE TABLE cars (
                      id uuid NOT NULL,
                      name character varying(255) NOT NULL,
                      brand_id uuid NOT NULL,
                      type character varying(50) NOT NULL,
                      price_per_day numeric(15,2) NOT NULL,
                      seats integer NOT NULL,
                      transmission character varying(50) NOT NULL,
                      fuel_type character varying(50) NOT NULL,
                      fuel_consumption character varying(50),
                      license_plate character varying(50) NOT NULL,
                      status character varying(50) NOT NULL,
                      description text,
                      created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                      updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
                      deleted_at timestamp without time zone,
                      location_id uuid,
                      version bigint DEFAULT 0
);

CREATE TABLE car_images (
                            id uuid NOT NULL,
                            car_id uuid NOT NULL,
                            image_url character varying(500) NOT NULL,
                            is_primary boolean DEFAULT false NOT NULL
);

CREATE TABLE car_features (
                              car_id uuid NOT NULL,
                              feature_name character varying(255) NOT NULL
);

ALTER TABLE ONLY brands ADD CONSTRAINT brands_pkey PRIMARY KEY (id);
ALTER TABLE ONLY locations ADD CONSTRAINT locations_pkey PRIMARY KEY (id);
ALTER TABLE ONLY cars ADD CONSTRAINT cars_pkey PRIMARY KEY (id);
ALTER TABLE ONLY car_images ADD CONSTRAINT car_images_pkey PRIMARY KEY (id);
ALTER TABLE ONLY car_features ADD CONSTRAINT car_features_pkey PRIMARY KEY (car_id, feature_name);

ALTER TABLE ONLY cars ADD CONSTRAINT cars_license_plate_key UNIQUE (license_plate);

ALTER TABLE ONLY cars ADD CONSTRAINT cars_brand_id_fkey FOREIGN KEY (brand_id) REFERENCES brands(id);
ALTER TABLE ONLY cars ADD CONSTRAINT cars_location_id_fkey FOREIGN KEY (location_id) REFERENCES locations(id);
ALTER TABLE ONLY car_images ADD CONSTRAINT car_images_car_id_fkey FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE;
ALTER TABLE ONLY car_features ADD CONSTRAINT car_features_car_id_fkey FOREIGN KEY (car_id) REFERENCES cars(id) ON DELETE CASCADE;