create table xmls (
id serial not null primary key,
original_xml CLOB not null,
trandformed_xml CLOB not null,
age integer not null
);