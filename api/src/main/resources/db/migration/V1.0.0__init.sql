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
   version varchar(255) not null
);
