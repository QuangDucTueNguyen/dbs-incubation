CREATE TABLE IF NOT EXISTS public.guest_profile
(
    id uuid PRIMARY KEY,
    username varchar NOT NULL,
    name varchar,
    phone_number varchar,
    address varchar,
    credit_card bigint
);

CREATE TABLE IF NOT EXISTS public.password
(
    id serial PRIMARY KEY,
    user_id uuid,
    password varchar NOT NULL,
    FOREIGN KEY (user_id) REFERENCES public.guest_profile (id)
                    ON UPDATE CASCADE ON DELETE CASCADE
);