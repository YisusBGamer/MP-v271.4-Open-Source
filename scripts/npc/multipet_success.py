# NPC 9102100 "?" - Henesys: Pet-Walking Road (100000202)
# WZ: Npc.wz/9102100.img/info/script/0/script = multipet_success
# Both this NPC and its "fail" twin are hidden (life hide=1, info hideName=1)
# and String.wz/Npc.img/9102100 has no dialogue text. The multi-pet
# success/fail feedback is client-driven; keep a safe no-op so dispatch
# never throws.
sm.dispose()
