# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table car (
  id                        bigint not null,
  make                      varchar(255),
  model                     varchar(255),
  year                      integer,
  status                    bigint,
  user_id                   bigint,
  unreg_user_id             bigint,
  constraint pk_car primary key (id))
;

create table orders (
  id                        bigint not null,
  date                      timestamp,
  amount                    integer,
  status                    integer,
  car_id                    bigint,
  constraint ck_orders_status check (status in (0,1,2)),
  constraint pk_orders primary key (id))
;

create table unreg_user (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  constraint pk_unreg_user primary key (id))
;

create table users (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  birth_date                varchar(255),
  address                   varchar(255),
  phone                     bigint,
  email                     varchar(255),
  constraint pk_users primary key (id))
;

create sequence car_seq;

create sequence orders_seq;

create sequence unreg_user_seq;

create sequence users_seq;

alter table car add constraint fk_car_user_1 foreign key (user_id) references users (id);
create index ix_car_user_1 on car (user_id);
alter table car add constraint fk_car_unregUser_2 foreign key (unreg_user_id) references unreg_user (id);
create index ix_car_unregUser_2 on car (unreg_user_id);
alter table orders add constraint fk_orders_car_3 foreign key (car_id) references car (id);
create index ix_orders_car_3 on orders (car_id);



# --- !Downs

drop table if exists car cascade;

drop table if exists orders cascade;

drop table if exists unreg_user cascade;

drop table if exists users cascade;

drop sequence if exists car_seq;

drop sequence if exists orders_seq;

drop sequence if exists unreg_user_seq;

drop sequence if exists users_seq;

