# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table car (
  id                        bigint not null,
  make                      varchar(255),
  model                     varchar(255),
  year                      integer,
  vin                       integer,
  constraint pk_car primary key (id))
;

create table orders (
  id                        bigint not null,
  date                      timestamp,
  amount                    integer,
  vin                       integer,
  constraint ck_orders_vin check (vin in (0,1,2)),
  constraint pk_orders primary key (id))
;

create table user (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  birth_date                varchar(255),
  address                   varchar(255),
  phone                     integer,
  email                     varchar(255),
  constraint pk_user primary key (id))
;

create sequence car_seq;

create sequence orders_seq;

create sequence user_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists car;

drop table if exists orders;

drop table if exists user;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists car_seq;

drop sequence if exists orders_seq;

drop sequence if exists user_seq;

