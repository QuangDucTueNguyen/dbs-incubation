CREATE TABLE IF NOT EXISTS public.reservation
(
    id uuid PRIMARY KEY,
    user_id uuid NOT NULL,
    hotel_id integer NOT NULL,
    room_type_id integer NOT NULL,
    created_time timestamp with time zone,
    update_time timestamp with time zone,
    from_date date NOT NULL,
    to_date date NOT NULL,
    status varchar NOT NULL,
    total numeric NOT NULL,
    number_rooms integer NOT NULL
);