#!/bin/bash
set -e

create_database() {
    local database=$1
    echo "  Creating database '$database'"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        CREATE DATABASE $database;
EOSQL
}

create_database "customer_db" || true
create_database "car_db" || true
create_database "renting_db" || true
create_database "notification_db" || true
