# Holiday event quests (see HolidayEvents). NpcHandler opens this instead of the NPC's own script while an event
# that's on has a quest this NPC starts or hands in: the client won't offer quests whose Quest.wz dates are long past,
# so the quest's official dialogue (Quest.wz Say.img) is played from here. "(Talk)" opens the NPC's own shop or
# script.
# An event's host NPC can also start and hand in the quests of NPCs that aren't around (the Moon Festival's Smartie
# Moon Bunny for Grandpa Moon Bunny, Cassandra, Gaga...): the dialogue is always the quest's own NPC's.
from net.swordie.ms.world import HolidayEvents as H
from net.swordie.ms.loaders import StringData

NPC = parentID
sm.setSpeakerID(NPC)
options = list(H.npcOptions(chr, NPC))

choices = []
text = ""
for o in options:
    kind, q = o.split(":")
    q = int(q)
    name = StringData.getQuestStringById(q)
    if name is None:
        name = "Quest %d" % q
    if kind == "s":
        label = "#b(New) " + name + "#k"
    elif kind == "e":
        label = "#r(Done!) " + name + "#k"
    else:
        label = "(In progress) " + name
    text += "#L%d#%s#l\r\n" % (len(choices), label)
    choices.append((kind, q))
text += "#L99#(Talk)#l"

event = H.eventName(choices[0][1]) if choices else ""
sel = sm.sendNext(("#e%s#n\r\n" % event if event else "") + text)
if sel == 99 or sel < 0 or sel >= len(choices):
    # dispose(False) frees the talk without ending this script; a plain dispose() would end it here, so "(Talk)"
    # couldn't open the NPC's own shop or script
    sm.dispose(False)
    H.talkNormally(chr, NPC)
else:
    kind, q = choices[sel]
    if kind == "s":
        sm.questStartDialogue(q, 0)
    elif kind == "e":
        if not sm.questEndDialogue(q, 0):
            sm.sendSayOkay("Come back when you have everything I asked for. (Make sure there's room in your "
                           "inventory too.)")
    else:
        sm.sendSayOkay("You're not done yet. Check the quest in your quest log to see what's left.")
    sm.dispose()
