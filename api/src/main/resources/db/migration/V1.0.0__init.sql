create table fuser (
   id uuid not null constraint fuser_key primary key,
   name varchar(255) not null,
   password varchar(255) not null,
   role varchar(255) not null
);

create table trip (
   id uuid not null constraint trip_key primary key,
   date date not null,
   duration integer not null,
   highwaydistance numeric(32, 2) not null,
   streetdistance numeric(32, 2) not null,
   simplefailure integer not null,
   criticalfailure integer not null,
   location varchar(255) not null,
   condition varchar(255) not null,
   version varchar(255) not null,
   user_id uuid not null
       constraint fk_trip_user_id
           references fuser
);

insert into fuser (id, name, password, role)
    values ('11aaf116-41b2-48a5-9d74-c58659e1399c', 'jendib', '$2a$12$T0jPtsldK7hCI8nzvWCLnuPil7/AiOsdkfZSqujrqeitYgoYMjfu2', 'MEMBER');