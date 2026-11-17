# Field script: event_25748
# Hooks: onUserEnter
# Maps: 940000120, 940000121, 940000122, 940000123, 940000124, 940000125, 940000126, 940000127, ... (20 total)
# Dialogo portado de Quest.wz/Say.img/25748/0 (1 pagina(s), rama yes 1, rama no 1)
# Evidencia Quest.wz/Check.img/25748/0: npc 3000000, lvmin 25, normalAutoStart, job 6100/6110/6111/6112/6113, req 25811 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q25748s).
if not sm.hasHadQuest(25748) and sm.hasQuestCompleted(25811):
    sm.setSpeakerID(3000000) # Cartalion
    if sm.sendAskYesNo("The traitor priests of Nova act boldly and deliberately. There must be a powerful mastermind behind the scenes. Can you look into this?"):
        sm.sendSayOkay("While you are out beating down on the Relic thieves, find any clues you can as to the identity of the mastermind. I'm guessing you'll have to go pretty deep to get it.")
    else:
        sm.sendSayOkay("I don't think wiping out these troops is going to solve our problems.")
