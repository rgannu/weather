SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: ${db.owner}
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 10
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO ${db.owner};

GRANT SELECT,USAGE ON SEQUENCE hibernate_sequence TO ${db.user};
GRANT ALL ON SEQUENCE hibernate_sequence TO ${db.owner};

--
-- Name: country; Type: TABLE; Schema: public; Owner: ${db.owner}; Tablespace:
--

CREATE TABLE country (
    id bigint NOT NULL,
    name character varying(255) NOT NULL,
    capital text[] NOT NULL,
    cca2 character varying(255) NOT NULL,
    cca3 character varying(255) NOT NULL,
    cioc character varying(255),
    population bigint NOT NULL,
    flag character varying(255)
);


ALTER TABLE country OWNER TO ${db.owner};

ALTER TABLE ONLY country
    ADD CONSTRAINT country_pkey PRIMARY KEY (id);

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE country TO ${db.user};
GRANT ALL ON TABLE country TO ${db.owner};

---

CREATE TABLE currency (
    id bigint NOT NULL,
    code character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    symbol character varying(255)
);


ALTER TABLE currency OWNER TO ${db.owner};

ALTER TABLE ONLY currency
    ADD CONSTRAINT currency_pkey PRIMARY KEY (id);

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE currency TO ${db.user};
GRANT ALL ON TABLE currency TO ${db.owner};
---

CREATE TABLE exchange_rate (
    id bigint NOT NULL,
    rate_date date NOT NULL,
    base_currency_id bigint NOT NULL,
    target_currency_id bigint NOT NULL,
    rate double precision NOT NULL
);


ALTER TABLE exchange_rate OWNER TO ${db.owner};

ALTER TABLE ONLY exchange_rate
    ADD CONSTRAINT exchange_rate_pkey PRIMARY KEY (id);

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE exchange_rate TO ${db.user};
GRANT ALL ON TABLE exchange_rate TO ${db.owner};
---

CREATE TABLE country_currency (
    id bigint NOT NULL,
    country_id bigint NOT NULL,
    currency_id bigint NOT NULL,
    FOREIGN KEY (country_id) REFERENCES country (id),
    FOREIGN KEY (currency_id) REFERENCES currency (id)
);

ALTER TABLE country_currency OWNER TO ${db.owner};

ALTER TABLE ONLY country_currency
    ADD CONSTRAINT country_currency_pkey PRIMARY KEY (id);

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE country_currency TO ${db.user};
GRANT ALL ON TABLE country_currency TO ${db.owner};
---

CREATE TABLE geocode (
    id bigint NOT NULL,
    city character varying(255) NOT NULL,
    country character varying(255) NOT NULL,
    lat double precision NOT NULL,
    lon double precision NOT NULL
);


ALTER TABLE geocode OWNER TO ${db.owner};

ALTER TABLE ONLY geocode
    ADD CONSTRAINT geocode_pkey PRIMARY KEY (id);

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE geocode TO ${db.user};
GRANT ALL ON TABLE geocode TO ${db.owner};
---

---

CREATE TABLE city_weather
(
    id               bigint                 NOT NULL,
    measurement_date date                   NOT NULL,
    description      character varying(255) NOT NULL,
    temp             double precision       NOT NULL,
    min_temp         double precision,
    max_temp         double precision,
    wind_speed       double precision       NOT NULL,
    geocode_id       bigint                 NOT NULL,
    FOREIGN KEY (geocode_id) REFERENCES geocode (id)
);

ALTER TABLE city_weather OWNER TO ${db.owner};

ALTER TABLE ONLY city_weather
    ADD CONSTRAINT city_weather_pkey PRIMARY KEY (id);

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE city_weather TO ${db.user};
GRANT ALL ON TABLE city_weather TO ${db.owner};
---

CREATE TABLE weather (
    id bigint NOT NULL,
    weather_date date NOT NULL,
    country_id bigint NOT NULL,
    exchange_rate_id bigint NOT NULL,
    city_weather_id bigint NOT NULL,
    FOREIGN KEY (country_id) REFERENCES country (id),
    FOREIGN KEY (exchange_rate_id) REFERENCES exchange_rate (id),
    FOREIGN KEY (city_weather_id) REFERENCES city_weather (id)
);


ALTER TABLE weather OWNER TO ${db.owner};

ALTER TABLE ONLY weather
    ADD CONSTRAINT weather_pkey PRIMARY KEY (id);

GRANT SELECT,INSERT,DELETE,UPDATE ON TABLE weather TO ${db.user};
GRANT ALL ON TABLE weather TO ${db.owner};
