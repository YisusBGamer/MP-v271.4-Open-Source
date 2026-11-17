-- The weekend buff (2x monster HP and EXP, Friday to Sunday) is on for every channel. Runs once; the server_rates
-- table can change it later.
insert into server_rates (name, value) values ('buffed', 2), ('buffed_channels', 2), ('buffed_hp', 2), ('buffed_exp', 2)
    on duplicate key update value = values(value);
