-- cubes used on an item since its last rank-up, for the cube ceiling (Etc/CubeCeiling.img)
alter table items add column cubeceilingmain smallint not null default 0;
alter table items add column cubeceilingbonus smallint not null default 0;
