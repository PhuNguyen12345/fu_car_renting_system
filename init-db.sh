#!/bin/bash
set -e

create_database() {
    local database=$1
    echo "  Creating database '$database'"
    psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
        CREATE DATABASE $database;
EOSQL
}

# Đọc tên DB từ biến môi trường, fallback về tên mặc định nếu không có
create_database "${CUSTOMER_DB_NAME:-customer_db}" || true
create_database "${CAR_DB_NAME:-car_db}" || true
create_database "${RENTING_DB_NAME:-rental_db}" || true
create_database "${NOTIFICATION_DB_NAME:-notification_db}" || true
