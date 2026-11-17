# Field script: end_battle_23270
# Hooks: onUserEnter
# Maps: 926150010
# Dialogo portado de Quest.wz/Say.img/23270/0 (4 pagina(s), rama yes 1, rama no 1, ask=1)
# Evidencia Quest.wz/Check.img/23270/0: npc 2111000, lvmin 90, normalAutoStart, job 3110/3120/3111/3121/3112/3122/3113/3123, req 23269 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q23270s).
if not sm.hasHadQuest(23270) and sm.hasQuestCompleted(23269):
    sm.setSpeakerID(2111000) # Carson
    sm.sendNext("And how did YOU know about the Forbidden Book of Alchemy, anyway?\r\n#L0##bI seek relics of the Black Mage, and destroy them. Rumors of Dark Alchemy are common enough, and I followed them here. Now, tell me what happened to the book.#l\n#k")
    sm.sendSay("Those...people...stole it. I don't know who they are, but their magic was strong enough to stun me, open the safe, and escape with the book. However, we only had half of the book. The Alcadno have the other half...but if the thieves get theirs as well, they will gain access to Dark Alchemy...\r\n#L0##bThe other half?#l\n#k")
    sm.sendSay("We tried to protect the world from Dark Alchemy by splitting the book in half. Each half is meaningless without the other. Giving one half to both Zenumists and Alcadnos seemed logical...at least until now.\r\n#L0##bThen, we must ensure the other half is secure!#l\n#k")
    if sm.sendAskYesNo("Indeed! You'll find the other half with #p2159320#. I wonder if the thieves have found #p2159320# yet... You must hurry! Stop them, if you can!"):
        sm.sendSayOkay("Hurry to the Alcadno association, and see if the #t04032975# is safe. I'll take care of matters here, and join you when I can. Check with the president of Alcadno, #b#p2159320##k.")
    else:
        sm.sendSayOkay("What, now all of a sudden, you don't care? What kind of a hero ARE you?")
