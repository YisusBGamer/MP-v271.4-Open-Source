-- The admin account (admin / admin). The password is hashed at its first login. Change it after that.

insert into `users` (`name`, `password`, `accounttype`, `chatunblockdate`, `pic`, `characterslots`)
values ('admin', 'admin', 4, 0, '111111', 40);
