# Field script: q2639M
# Hooks: onUserEnter
# Maps: 910350230
# Dialogo portado de Quest.wz/Say.img/2639/0 (1 pagina(s), rama yes 0, rama no 1)
# Evidencia Quest.wz/Check.img/2639/0: npc 1057001, lvmin 45, normalAutoStart, job 0/400/430/431/432/433/434/435, req 2636 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q2639s).
if not sm.hasHadQuest(2639) and sm.hasQuestCompleted(2636):
    sm.setSpeakerID(1057001) # Ryden
    if not sm.sendAskYesNo("#h0#. The last time we spoke of the Dark Lord, I was troubled. But we must confirm what we've learned. Meet me at the Kerning City Jazz Bar."):
        sm.sendSayOkay("You're the one who unearthed all this information. We need you to be there.")
