# Evening Conference Pavilion (993060090): Trailing the Traces of the Black Mage (35650), the Genesis Weapon quest
# line's tracker, starts here (its starting NPC isn't placed anywhere).
if chr.getLevel() >= 255 and sm.hasQuestCompleted(35815) and not sm.hasQuest(35650) \
        and not sm.hasQuestCompleted(35650):
    sm.startQuestNoCheck(35650)
