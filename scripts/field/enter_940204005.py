# Somewhere in Morass (940204005): Jean and Tana's memory after [Morass] The Reunion (34260, q34260s sends players here
# in a solo copy), then back to the Research Lab (450006240).
TANA = 3003400
FLYING_FISH = 3003409
JEAN = 3003406
RESEARCH_LAB = 450006240


def scene():
    sm.lockUI()
    sm.blind(1, 255, 0, 0)
    sm.removeNpc(JEAN)
    sm.sayMonologue("I'd never seen anything like her before.", False)
    sm.sayMonologue("Everything about her was dark, cold, and bleak, just like her prison.", False)
    sm.sayMonologue("The last thing I ever expected...\r\n", False)
    sm.sayMonologue("...was that a ray of hope would find its way into that place.", True)
    sm.blind(0, 0, 0, 0)
    sm.removeEscapeButton()
    me("What's that light?!")
    say(FLYING_FISH, "#face0#Shhh! Hide! Now!")
    me("Flying Fish? Is that you?")
    sm.hideUser(True)
    sm.spawnNpc(JEAN, -404, -43)
    sm.flipNpcByTemplateId(JEAN, False)
    say(JEAN, "#face4#Ooof! Ugh. That wasn't very stealthy...")
    say(JEAN, "#face11#Cough, cough... sputter... So, I guess this really is a dungeon.")
    me("#b(Jean?! What's Jean doing here?!)#k")
    say(JEAN, "#face0#Hey, I remember you... Do you remember me? I'm Jean. Jean the thief...?\r\n"
              "You saved me from that crystal...? Who're you?")
    say(TANA, "#face0#...T-tana.")
    say(JEAN, "#face11#That's a pretty name. Nice to officially meet you, Tana.")
    say(JEAN, "#face10#You don't seem like a big talker... You seem kind of sad, really. I don't blame you.\r\n"
              "This place just sucks the happy right out of you!")
    say(TANA, "#face0#Sad?")
    say(TANA, "#face0#I... I don't know what... sad is.")
    say(JEAN, "#face0#Really? Must be nice.")
    me("#b(How did he get in here?)#k")
    sm.blind(1, 255, 0, 1000)
    sm.OnOffLayer_On(1000, "0", 0, 0, 0, "Map/Effect3.img/morass/JeanNTana1/0", 4, 1, -1, 0)
    sm.OnOffLayer_On(1000, "1", 0, 0, 0, "Map/Effect3.img/morass/JeanNTana1/1", 4, 1, -1, 0)
    sm.OnOffLayer_On(1000, "2", 0, 0, 0, "Map/Effect3.img/morass/JeanNTana1/2", 4, 1, -1, 0)
    sm.sendDelay(500)
    say(JEAN, "#face4#Hm... Uh... I don't think I can get you out of these chains. There's something strange about "
              "them. I'm sorry.")
    sm.sayMonologue("Though they had been reunited at last...", True)
    sm.sayMonologue("Jean knew he couldn't rescue her from that terrible place.", False)
    sm.OnOffLayer_Off(500, "2", 0)
    sm.OnOffLayer_On(500, "3", 0, 0, 0, "Map/Effect3.img/morass/JeanNTana1/3", 4, 1, -1, 0)
    sm.sayMonologue("But he returned every day after that and did his best to keep her loneliness at bay.", True)
    say(JEAN, "#face0#I thought you might be bored so I brought you a book... Can I read it for you?")
    say(TANA, "#face0#Bored...")
    say(TANA, "#face0#...is... What is it to be bored?")
    say(JEAN, "#face0#Being bored is well... um...")
    say(JEAN, "#face0#You know what? It's not important. Why don't I just start reading?")
    say(JEAN, "#face0#A long time ago, in a kingdom far, far away, there was a beautiful princess who was held captive "
              "on a magical island...")
    sm.OnOffLayer_Off(500, "3", 0)
    sm.OnOffLayer_On(500, "4", 0, 0, 0, "Map/Effect3.img/morass/JeanNTana1/4", 4, 1, -1, 0)
    say(JEAN, "#face0#Are you hungry? I brought bread for you...")
    sm.sayMonologue("He was so determined... so dedicated.", False)
    sm.sayMonologue("He did everything in his power to help her.", True)
    sm.OnOffLayer_Off(500, "4", 0)
    sm.OnOffLayer_Off(500, "0", 0)
    sm.OnOffLayer_Off(500, "1", 0)
    sm.sayMonologue("Every day Jean came back to see her again.\r\n", False)
    sm.sayMonologue("But Tana remained distant.", False)
    sm.sayMonologue("There was no way to tell what effect his visits had.", True)
    sm.removeNpc(JEAN)


def say(npc, text):
    sm.setNpcBoxChat(npc)
    sm.sendNext(text)


def me(text):
    sm.setPlayerBoxChat()
    sm.sendNext(text)


try:
    if sm.hasQuestCompleted(34260) or sm.hasQuest(34260):
        scene()
finally:
    sm.blind(0, 0, 0, 1000)
    sm.hideUser(False)
    sm.unlockUI()
    if sm.hasQuest(34260):
        sm.completeQuestNoCheck(34260)
    sm.warpInstanceOut(RESEARCH_LAB)
