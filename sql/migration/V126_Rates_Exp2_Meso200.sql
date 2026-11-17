-- EXP rate 2 (from level 10 on) and meso rate 200, applied at the next start. Runs once (Flyway); later changes
-- made in the server_rates table are kept.
insert into server_rates (name, value) values ('exp', 2), ('meso', 200)
    on duplicate key update value = values(value);
