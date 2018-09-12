create table xmls (
id serial not null primary key,
original_xml CLOB not null,
transformed_xml CLOB not null,
order_number VARCHAR not null,
creation_date TIMESTAMP
);