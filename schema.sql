create table 
if not exists 
copyrights(
    id serial primary key,
    copyright text
);

create table if not exists apod(
    id serial primary key,
    copyright int,
    day date,
    explanation text,
    media_type text,
    service_version text,
	title text,
    url text,
    hdurl text,
    constraint fk_copyright foreign key(copyright) references copyrights(id)
);
