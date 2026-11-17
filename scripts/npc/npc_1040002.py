from net.swordie.ms.constants import QuestConstants
from net.swordie.ms.enums import QuestStatus

# Fanzy NPC Configuration
QUEST_ID = 100200  # Unique Quest ID
REQUIRED_ITEM = 4032021  # Required Item ID
REQUIRED_QUANTITY = 10  # Number of required items
REWARD_EXP = 5000  # Reward experience
REWARD_ITEM = 2000005  # Reward item ID
REWARD_ITEM_QTY = 5  # Quantity of reward items
REQUIRED_LEVEL = 30  # Minimum level required to talk to the NPC

def start_quest(player):
    if player.getLevel() < REQUIRED_LEVEL:
        player.sendSay("Sorry, but you need to be at least level {} to help me.".format(REQUIRED_LEVEL))
        return

    quest = player.getQuestManager().getQuest(QUEST_ID)

    if quest is None or quest.getStatus() == QuestStatus.NOT_STARTED:
        player.sendSay("Hello, adventurer. Could you help me collect {} units of [Item Name]?\n\nI will reward you well for your effort.".format(REQUIRED_QUANTITY))
        player.sendAcceptDecline("Will you accept this challenge?")

    elif quest.getStatus() == QuestStatus.STARTED:
        if player.getInventory().hasItem(REQUIRED_ITEM, REQUIRED_QUANTITY):
            complete_quest(player)
        else:
            player.sendSay("You haven't collected the {} [Item Name] yet. Come back when you have them!".format(REQUIRED_QUANTITY))

    elif quest.getStatus() == QuestStatus.COMPLETED:
        player.sendSay("Thanks again for your help! You are a true hero!")

def complete_quest(player):
    # Remove required items
    player.getInventory().removeItem(REQUIRED_ITEM, REQUIRED_QUANTITY)

    # Give rewards
    player.addExp(REWARD_EXP)
    player.getInventory().addItem(REWARD_ITEM, REWARD_ITEM_QTY)

    # Mark quest as completed
    player.getQuestManager().completeQuest(QUEST_ID)

    # Final dialogue
    player.sendSay("Amazing work! Here is your reward: {} experience and {} [Item Name].".format(REWARD_EXP, REWARD_ITEM_QTY))

def handle_accept(player):
    player.getQuestManager().startQuest(QUEST_ID)
    player.sendSay("Good luck! Remember to return when you have {} [Item Name].".format(REQUIRED_QUANTITY))

def handle_decline(player):
    player.sendSay("Oh, what a shame. Well, if you change your mind, I'll be here.")

def handle_npc_interaction(player, selection):
    if selection == 0:  # Start dialogue
        start_quest(player)
    elif selection == 1:  # Accept the quest
        handle_accept(player)
    elif selection == 2:  # Decline the quest
        handle_decline(player)
