CREATE TABLE transaction_events
(
    id                BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    amount            DOUBLE PRECISION                    NOT NULL,
    note              VARCHAR(1024)                       NOT NULL,
    date              DATE                                NOT NULL,
    created_date_time TIMESTAMPTZ                         NOT NULL,

    CONSTRAINT transaction_events_pk
        PRIMARY KEY (id)
);

CREATE INDEX transaction_events_date
    ON transaction_events (date);
