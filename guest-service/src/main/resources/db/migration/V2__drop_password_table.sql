DROP TABLE IF EXISTS public.password;

ALTER TABLE public.guest_profile
DROP COLUMN IF EXISTS username CASCADE;