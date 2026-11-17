# Field script: q2635M
# Hooks: onUserEnter
# Maps: 910510700
# Dialogo portado de Quest.wz/Say.img/2635/1 (1 pagina(s), rama yes 1, rama no 0)
# Evidencia Quest.wz/Check.img/2635/1: npc 1061014
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q2635e).
# El slot tiene ademas 1 linea(s) en stop/: recordatorios del cliente al volver sin terminar, sin disparador equivalente en un hook de mapa; se documentan y no se suenan aqui.
if sm.hasQuest(2635):
    sm.setSpeakerID(1061014) # Mu Young
    if sm.sendAskAccept("Oh, brother. The Storage Room was raided? Did someone really think I'd leave valuables in there? All the good stuff was looted years ago!"):
        sm.sendSayOkay("Anyway, here, take this, my master's diary, or list of records. Whatever you want to call it.")
