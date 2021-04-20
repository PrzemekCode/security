CREATE TABLE finger_print
(
    finger_print_id bigint NOT NULL GENERATED BY DEFAULT AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    city character varying(255),
    country character varying(255),
    country_code character varying(255),
    device character varying(255),
    lat character varying(255),
    lon character varying(255),
    os character varying(255),
    user_agent character varying(255),
    zip character varying(255),
    user_id bigint,
    CONSTRAINT finger_print_pkey PRIMARY KEY (finger_print_id),
    CONSTRAINT fkl4u7hok15kyfn1k8cx5kcqp47 FOREIGN KEY (user_id)
        REFERENCES "user" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)