create table if not exists apod(
    id serial primary key,
    copyright text,
    day date,
    explanation text,
    media_type text,
    service_version text,
	title text,
    url text,
    hdurl text
);