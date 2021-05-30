CREATE TABLE IF NOT EXISTS public.credential
(
    id uuid PRIMARY KEY,
    guest_id uuid NOT NULL,
    username varchar NOT NULL,
    password varchar NOT NULL
);