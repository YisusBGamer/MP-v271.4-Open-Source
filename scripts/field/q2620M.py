# Field script: q2620M
# Hooks: onUserEnter
# Maps: 910350210
# Dialogo portado de Quest.wz/Say.img/2620/0 (3 pagina(s), rama yes 1, rama no 0)
# Evidencia Quest.wz/Check.img/2620/0: npc 1057001, lvmin 20, job 0/400/430/431/432/433/434/435, req 2618 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q2620s).
if not sm.hasHadQuest(2620) and sm.hasQuestCompleted(2618):
    sm.setSpeakerID(1057001) # Ryden
    sm.sendNext("Well, the Delivery Package investigation was a bust, but something'll turn up sooner or later. It has to, with the Dark Lord being who he is. Now, on to our other lead...")
    sm.sendSay("I've had the #b#m103030100##k watched recently, based on what we learned--do you think she's single, by the way?--and we've discovered the Dark Lord meets with someone in the #bHut in the Swamp#k.")
    if sm.sendAskAccept("Is he meeting with potential allies? Head to the Hut in the Swamp and find out. Trust me, it'll be easy."):
        sm.sendSayOkay("While you're in the Hut in the Swamp, pay attention to any potential hiding places, like behind paintings or in #bTrash Cans#k. Never hurts to poke around.")
