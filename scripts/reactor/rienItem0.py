# Snow Island, Cold Forest 4 (140090400): Putzki's boxes. 'A Gift for the Hero' (21013): "Break open boxes with regular
# attacks until you find a Piece of Bamboo and a regular Wood."
# A box gives one of the two the quest still needs (Wood otherwise).
import random

QUEST = 21013
BAMBOO = 4032309
WOOD = 4032310

need = [item for item in (BAMBOO, WOOD) if sm.hasQuest(QUEST) and not sm.hasItem(item)]
item = random.choice(need) if need else WOOD
sm.dropItem(item, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
sm.removeReactor()
