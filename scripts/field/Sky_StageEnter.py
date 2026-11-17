# Dragon Rider: each area's instructions (the stages are run by DragonRiderInstanceHelper)
MESSAGES = {
    240080100: "Defeat the Soaring Hawks and Soaring Eagles! (Use Soaring to fly.)",
    240080200: "Defeat the Soaring Wyverns and the Soaring Griffey!",
    240080300: "Dragonoir blocks the way! Defeat it!",
    240080400: "Fly through the falling rocks and spears to the nest!",
    240080500: "Defeat the Dragon Rider!",
}
msg = MESSAGES.get(sm.getFieldID())
if msg is not None:
    sm.chatScript(msg)
sm.dispose()
