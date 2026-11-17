# Field script: check_q20748
# Hooks: onUserEnter
# Maps: 220070400 (Ludibrium: Forgotten Passage)
# The 100/10/10 kill demands of quest 20748 are tracked by QuestManager from Quest.wz; the
# old infoex flag this map script used to set is not evaluated by the server.
# Dialogo portado de Quest.wz/Say.img/20748/0 (2 pagina(s), rama yes 1, rama no 1)
# Evidencia Quest.wz/Check.img/20748/0: npc 9073007, lvmin 110, job 1211/1212/1411/1412, req 20747 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q20748s).
if not sm.hasHadQuest(20748) and sm.hasQuestCompleted(20747):
    sm.setSpeakerID(9073007) # Sodane
    sm.sendNext("Listen up and I'll lay it out for you.\r\nFirst up, we've got the suspicious guy in the black robe showing up on the bottom floor of the Clocktower.\r\nThen, Thanatos starts going bananas and shows up in Origin of the Clocktower AND Helios Tower. Weird, right?\r\nAfter that, the Ludibrium Clocktower blows up and breaks down.")
    if sm.sendAskYesNo("What do you think? Starting to make sense, isn't it? That suspicious guy MUST be up to some majorly bad business!"):
        sm.sendSayOkay("Go to #e#m220060400#, #m220070300#, and #m220070400##n check for anything out of the ordinary... like that Mystic Gate at the Crossroad of Time. You should probably go ahead and beat up some monsters to make the trips worth your while.")
    else:
        sm.sendSayOkay("I need you to gather up some more info for me and I should be able to figure out who's behind it! I have an inkling of an idea, but I want definite proof!")
