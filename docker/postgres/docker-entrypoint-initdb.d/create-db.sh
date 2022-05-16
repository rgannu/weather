#!/bin/bash

PGPASSWORD=postgres
psql -U postgres <<EOSQL
    CREATE USER ${DB_NAME} WITH PASSWORD 'admin';
    CREATE DATABASE ${DB_NAME} OWNER ${DB_NAME};
    \connect ${DB_NAME}
    CREATE SCHEMA ${DB_NAME} AUTHORIZATION ${DB_NAME};

    CREATE EXTENSION postgis;
    CREATE EXTENSION postgis_topology;
    CREATE EXTENSION postgis_sfcgal;
    CREATE EXTENSION btree_gist;
    CREATE EXTENSION pg_trgm schema public;
    CREATE EXTENSION "uuid-ossp";
    CREATE LANGUAGE plpython3u;
    UPDATE pg_language SET lanpltrusted = true WHERE lanname = 'plpython3u';
EOSQL
