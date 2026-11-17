# Snow Island Ice Cave (140090013): Putzki's boxes. v265's A Gift for the Hero (36904) asks for the Piece of Bamboo
# (4037290) and the Wood (4037291): a box gives one of the two the quest still needs (Wood otherwise).
import random

QUEST = 36904
BAMBOO = 4037290
WOOD = 4037291

need = [item for item in (BAMBOO, WOOD) if sm.hasQuest(QUEST) and not sm.hasItem(item)]
item = random.choice(need) if need else WOOD
sm.dropItem(item, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
sm.removeReactor()
