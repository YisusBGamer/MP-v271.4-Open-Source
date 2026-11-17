# Muto's Descent (450002200): the fall from Damp Falls into Chu Chu Island (450001250_PCS00 sends players who haven't
# started [Chu Chu] Arcane Blockage, 34200, here in a solo copy). The Flying Fish carries the player down the river
# to Muto (450002021), where 34200 starts.
from net.swordie.ms.client.character.skills.temp import CharacterTemporaryStat

FLYING_FISH_MOUNT = 1932399

if sm.hasQuest(34200) or sm.hasQuestCompleted(34200):
    sm.warpInstanceOut(450002021, 1)
else:
    try:
        sm.lockUI()
        sm.removeAdditionalEffect()
        sm.removeEscapeButton()
        sm.hideUser(True)
        sm.blind(1, 255, 0, 0)
        sm.sendDelay(1000)

        sm.OnOffLayer_On(2000, "0", 0, 0, 0, "Map/Effect2.img/ArcaneRiver2/drop1", 4, 1, -1, 0)
        sm.setPlayerBoxChat()
        sm.sendNext("AHHHHHHHHHHHHHHHH! How did I get so high up!?")
        sm.OnOffLayer_Off(1000, "0", 0)
        sm.sendDelay(2000)
        sm.showFade(500)

        sm.OnOffLayer_On(2000, "0", 0, 0, 0, "Map/Effect2.img/ArcaneRiver2/drop2", 4, 1, -1, 0)
        sm.setPlayerBoxChat()
        sm.sendNext("W-what?!")
        sm.OnOffLayer_Off(1000, "0", 0)
        sm.sendDelay(1500)
        sm.showFade(500)

        sm.OnOffLayer_On(2000, "0", 0, 0, 0, "Map/Effect2.img/ArcaneRiver2/drop3", 4, 1, -1, 0)
        sm.setPlayerBoxChat()
        sm.sendNext("OOF!")
        sm.OnOffLayer_Off(1000, "0", 0)
        sm.sendDelay(4000)

        sm.blind(0, 0, 0, 1000)
        sm.hideUser(False)
        sm.rideVehicle(FLYING_FISH_MOUNT)
        sm.forcedInput(3)
        sm.sendDelay(1300)
        sm.forcedInput(2)
        sm.setPlayerBoxChat()
        sm.sendNext("Wha? I'm... alive?! Wait... am I riding #ba f-flying fish?!#k\r\nYou know what? It doesn't even matter. "
                    "Flying Fish, you're my best friend right now.")
        sm.sendDelay(1000)
        sm.setPlayerBoxChat()
        sm.sendNext("Maybe you're one of the #bmany allies#k Kao spoke of.")
        sm.sendDelay(1000)
        sm.setPlayerBoxChat()
        sm.sendNext("All right, Flying Fish! Swim-fly me straight to the #rBlack Mage#k!")
        sm.sendDelay(2000)
        sm.forcedInput(0)
        sm.setPlayerBoxChat()
        sm.sendNext("Huh? Flying Fish, why did you stop?")
        sm.forcedInput(4)
        sm.sendDelay(800)
        sm.forcedInput(0)
        sm.setPlayerBoxChat()
        sm.sendNext("I don't have time for this. I've got to get to the end of the #bArcane River#k!\r\nDon't you speak English?")
        sm.setPlayerBoxChat()
        sm.sendNext("W-woah!")
    finally:
        # also when the dialog is closed or the player leaves: no fish, UI and player back, out to Muto
        sm.removeCTS(CharacterTemporaryStat.RideVehicle)
        sm.hideUser(False)
        sm.unlockUI()
        sm.warpInstanceOut(450002021, 1)
