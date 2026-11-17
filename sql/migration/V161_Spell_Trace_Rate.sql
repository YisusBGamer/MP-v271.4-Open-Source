-- Spell Trace rate 15: drops give 15x the drop tables' Spell Trace amounts, close to what the old level curve gave in
-- Arcane River. Runs once (Flyway); later changes made in the server_rates table are kept.
insert into server_rates (name, value) values ('spell_trace', 15)
    on duplicate key update value = values(value);
