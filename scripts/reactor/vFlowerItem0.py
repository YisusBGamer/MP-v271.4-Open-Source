# Ellinia Alphine Plant
# 1000023
# Close to the Wind (101020000)
# Drops a Ellivera Fruit: for Wing the Fairy's Request 1, or the Explorer story's Tree Medicine (32244, Sabitrama)

elliveraFruit = 4033004
storyElliveraFruit = 4033912  # Tree Medicine (32244)

reactor.incHitCount()
reactor.increaseState()

if reactor.getHitCount() >= 3:
	item = storyElliveraFruit if sm.hasQuest(32244) else elliveraFruit
	sm.dropItem(item, sm.getPosition(objectID).getX(), sm.getPosition(objectID).getY())
	sm.removeReactor()
