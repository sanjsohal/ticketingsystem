-- src/main/resources/db/migration/V1__init.sql
CREATE TABLE test_table (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);