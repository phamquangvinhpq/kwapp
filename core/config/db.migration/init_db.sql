
create table if not exists public.account
(
    id             bigserial                                                       not null
        constraint account_pkey
        primary key,
    phone_number   varchar(64)                                                     not null
        constraint account_email_uidx
            unique,
    password       varchar(64)                                                     not null,
    avatar         varchar(500),
    created_at     timestamptz default CURRENT_TIMESTAMP,
    updated_at     timestamptz default CURRENT_TIMESTAMP
);

