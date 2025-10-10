create table locations (
    id serial primary key,
    x bigint,
    y real,
    name varchar(255) not null
);

create table routes (
    id serial primary key,
    name varchar(255) not null,
    coordinate_x real,
    coordinate_y double precision check (coordinate_y <= 49),
    creation_date timestamp not null,
    from_id integer references locations (id),
    to_id integer references locations (id),
    distance double precision,
    rating double precision not null
);

create index idx_route_from_location_id on routes (from_id);
create index idx_route_to_location_id on routes (to_id);