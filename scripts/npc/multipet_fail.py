# NPC 9102101 "?" - Henesys: Pet-Walking Road (100000202)
# WZ: Npc.wz/9102101.img/info/script/0/script = multipet_fail
# Both this NPC and its "success" twin are hidden (life hide=1, info hideName=1)
# and String.wz/Npc.img/9102101 has no dialogue text. The multi-pet
# success/fail feedback is client-driven; keep a safe no-op so dispatch
# never throws.
sm.dispose()
