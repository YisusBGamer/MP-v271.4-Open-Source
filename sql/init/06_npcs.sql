-- NPCs added to maps, and map NPCs that are hidden.

insert into `npc` (`id`, `npcid`, `mapid`, `x`, `y`, `cy`, `rx0`, `rx1`, `fh`)
values
    (3, 9030000, 910000000, -857, -184, -176, -907, -807, 81);

insert into `removednpcs` (`id`, `npcid`, `mapid`)
values
    (1, 9000133, 910000000);
