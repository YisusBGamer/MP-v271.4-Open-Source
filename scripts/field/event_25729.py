# Field script: event_25729
# Hooks: onUserEnter
# Maps: 940000060
# Dialogo portado de Quest.wz/Say.img/25729/0 (3 pagina(s), rama yes 1, rama no 1, ask=1)
# Evidencia Quest.wz/Check.img/25729/0: npc 3000014, lvmin 10, normalAutoStart, job 6100/6110/6111/6112/6113, req 25728 state 2
# Guardia = estado de la quest segun ese Check.img; el WZ no define aqui un "solo una vez", asi que la escena suena al entrar.
# Iniciar/cerrar la quest no va en este hook de mapa: es tarea del script del NPC (q25729s).
if not sm.hasHadQuest(25729) and sm.hasQuestCompleted(25728):
    sm.setSpeakerID(3000014) # Velderoth
    sm.sendNext("Hey, what did you do to #p3000013#? She booked it out of here in a hurry. And after I came all this way to comfort her...\rSo, since we're not busy...how about you and me have a little hunting match?\r\n#L0##bThat's cool, but I don't get why you want me to kick your butt like that right now.#l\n#k")
    sm.sendSay("Well, I thought you might need some training since you became Kaiser. And maybe a little humbling. Eh? Eh?\r\n#L0##bOh, I'm gonna train all right. I'm gonna train you right into the ground.#l\n#k")
    if sm.sendAskYesNo("Then, let's do this thing!"):
        sm.sendSayOkay("Okay, so, we'll see who can defeat #b#o2400005##k monsters at #b#m400040100##k, gather #b#t4000709# x30#k, and return to the hideout first. Sound good? Then, let's go!")
    else:
        sm.sendSayOkay("Pfft, way to take your Kaisering seriously. Someone with some guts should have gotten the power.")
