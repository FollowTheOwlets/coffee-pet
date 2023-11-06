/* init-db.sql */

DROP TABLE IF EXISTS public.change_state;
DROP TABLE IF EXISTS public.error;
DROP TABLE IF EXISTS public.action;
DROP TABLE IF EXISTS public.coffee_resource;
DROP TABLE IF EXISTS public.operation;
DROP TABLE IF EXISTS public.resource;
DROP TABLE IF EXISTS public.state;
DROP TABLE IF EXISTS public.coffee;

CREATE TABLE IF NOT EXISTS public.operation
(
    id   serial PRIMARY KEY,
    name TEXT NOT NULL
) TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.resource
(
    id        serial PRIMARY KEY,
    name      TEXT    NOT NULL,
    count     integer NOT NULL,
    max_count integer NOT NULL
) TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.state
(
    id   serial PRIMARY KEY,
    name TEXT NOT NULL
) TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.coffee
(
    id   serial PRIMARY KEY,
    name TEXT NOT NULL
) TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.coffee_resource
(
    id          serial PRIMARY KEY,
    coffee_id   integer REFERENCES public.coffee (id),
    resource_id integer REFERENCES public.resource (id),
    count       integer NOT NULL
) TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.change_state
(
    id       serial PRIMARY KEY,
    state_id integer REFERENCES public.state (id),
    time     TIMESTAMP
) TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.action
(
    id           serial PRIMARY KEY,
    operation_id integer REFERENCES public.operation (id),
    coffee_id    integer REFERENCES public.coffee (id),
    create_time  TIMESTAMP,
    start_time   TIMESTAMP,
    end_time     TIMESTAMP
) TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.error
(
    id        serial PRIMARY KEY,
    action_id integer REFERENCES public.action (id),
    message   TEXT NOT NULL
) TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.operation
    OWNER to postgres;

ALTER TABLE IF EXISTS public.resource
    OWNER to postgres;

ALTER TABLE IF EXISTS public.state
    OWNER to postgres;

ALTER TABLE IF EXISTS public.coffee
    OWNER to postgres;

ALTER TABLE IF EXISTS public.change_state
    OWNER to postgres;

ALTER TABLE IF EXISTS public.action
    OWNER to postgres;

ALTER TABLE IF EXISTS public.coffee_resource
    OWNER to postgres;

ALTER TABLE IF EXISTS public.error
    OWNER to postgres;

INSERT INTO public.state(name)
VALUES ('ON'),
       ('WORK'),
       ('OFF');

INSERT INTO public.operation(name)
VALUES ('START'),
       ('CANCEL'),
       ('WAIT'),
       ('ERROR'),
       ('END');

INSERT INTO public.coffee(name)
VALUES ('ESPRESSO'),
       ('AMERICANO'),
       ('CAPPUCCINO'),
       ('LATTE');

INSERT INTO public.resource(name, count, max_count)
VALUES ('milk', 3000, 3000),
       ('water', 3000, 3000),
       ('coffee', 2000, 2000);

INSERT INTO public.coffee_resource(coffee_id, resource_id, count)
VALUES (1, 2, 40),
       (1, 3, 10),
       (2, 2, 200),
       (2, 3, 10),
       (3, 2, 40),
       (3, 3, 10),
       (3, 1, 160),
       (4, 2, 40),
       (4, 3, 10),
       (4, 1, 160);