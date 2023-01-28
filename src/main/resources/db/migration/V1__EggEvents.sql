CREATE SEQUENCE hibernate_sequence START 1;

CREATE TABLE egg_events
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    amount            INTEGER                             NOT NULL,
    date              DATE                                NOT NULL,
    created_date_time TIMESTAMPTZ                         NOT NULL,

    CONSTRAINT egg_events_pk
        PRIMARY KEY (id)
);

CREATE INDEX egg_events_date
    ON egg_events (date);
