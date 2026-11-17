# Field script: q1744end
# Hooks: onUserEnter
# Maps: 956040300, 956040301, 956040302, 956040303, 956040304, 956040305, 956040306, 956040307, ... (10 total)
# Dialogo portado de Quest.wz/Say.img/1744/0 (1 pagina(s))
# Evidencia Quest.wz/Check.img/1744/0: lvmin 50, req 1743 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q1744s).
if not sm.hasHadQuest(1744) and sm.hasQuestCompleted(1743):
    sm.sendNext("More wolves will be here in no time. I'd better get a move on.")
