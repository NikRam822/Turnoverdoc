BEGIN;


CREATE TABLE IF NOT EXISTS public.contacts
(
    id bigint NOT NULL,
    phone text COLLATE pg_catalog."default" NOT NULL,
    email text COLLATE pg_catalog."default" NOT NULL,
    messanger text COLLATE pg_catalog."default",
    id_order bigint NOT NULL,
    CONSTRAINT contacts_pkey PRIMARY KEY (id_order)
    );

CREATE TABLE IF NOT EXISTS public.orders
(
    id bigint NOT NULL,
    passport_path text COLLATE pg_catalog."default" NOT NULL,
    p45_path text COLLATE pg_catalog."default",
    p60_path text COLLATE pg_catalog."default",
    p85_path text COLLATE pg_catalog."default",
    contract_path text COLLATE pg_catalog."default",
    status text COLLATE pg_catalog."default" NOT NULL,
    id_user bigint,
    CONSTRAINT orders_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.roles
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id)
    );

CREATE TABLE IF NOT EXISTS public.user_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);

CREATE TABLE IF NOT EXISTS public.users
(
    id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    username character varying(255) COLLATE pg_catalog."default",
    password character varying(255) COLLATE pg_catalog."default",
    status character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
    );

ALTER TABLE IF EXISTS public.contacts
    ADD CONSTRAINT order_key FOREIGN KEY (id_order)
    REFERENCES public.orders (id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;
CREATE INDEX IF NOT EXISTS contacts_pkey
    ON public.contacts(id_order);


ALTER TABLE IF EXISTS public.orders
    ADD CONSTRAINT user_key FOREIGN KEY (id_user)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION
    NOT VALID;


ALTER TABLE IF EXISTS public.user_roles
    ADD CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id)
    REFERENCES public.roles (id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;


ALTER TABLE IF EXISTS public.user_roles
    ADD CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id)
    REFERENCES public.users (id) MATCH SIMPLE
    ON UPDATE NO ACTION
       ON DELETE NO ACTION;

END;