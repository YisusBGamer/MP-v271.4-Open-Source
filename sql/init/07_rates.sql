-- Server rates. These can be changed while the server runs from the database (server_rates).

insert into `server_rates` (`name`, `value`)
values
    ('exp', 100),            # monster EXP from level 10
    ('exp_low', 1),          # monster EXP below level 10
    ('drop', 20),
    ('meso', 2000),
    ('nx', 1),
    ('buffed', 2),           # weekend buffed channels: 2 = on Friday to Sunday
    ('buffed_channels', 2),
    ('buffed_hp', 2),
    ('buffed_exp', 2);
