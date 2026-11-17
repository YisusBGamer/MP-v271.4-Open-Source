# Field script: enter_23265
# Hooks: onUserEnter
# Maps: 931050212
# Dialogo portado de Quest.wz/Say.img/23265/0 (1 pagina(s), rama yes 1, rama no 1)
# Evidencia Quest.wz/Check.img/23265/0: npc 2151009, lvmin 140, normalAutoStart, job 3110/3120/3111/3121/3112/3122/3113/3123, req 23264 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q23265s).
if not sm.hasHadQuest(23265) and sm.hasQuestCompleted(23264):
    sm.setSpeakerID(2151009) # Mastema
    if sm.sendAskYesNo("Hey, #h0#. If you're going to #m270000100#, could you do a little something for me?"):
        sm.sendSayOkay("I heard there's a #r#t04032972##k that you can get from #r#o8200000#s#k in #b#m270000100#. #kRegressive Hourglasses can move people back through time, so I thought, hey, that could move me back to my previous form!")
    else:
        sm.sendSayOkay("Oh, don't worry, it's really easy. Heh, it's hardly even a favor at all! Just a little thing to keep in mind when you go to the Temple of Time. You don't mind helping, right?")
