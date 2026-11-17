# Remnant of the Goddess: each room's instructions (the stages are run by GoddessInstanceHelper)
MESSAGES = {
    920010100: "Find the 4 Goddess Statue Pieces through Chamberlain Eak's doors!",
    920010200: "Defeat all the pixies on the Walkway!",
    920010300: "Leave exactly the cats Chamberlain Eak asks for alive!",
    920010400: "Play the music that suits today on the phonograph (talk to Chamberlain Eak)!",
    920010700: "Find the right platform on each floor and reach the top!",
    920010800: "Defeat the Nependeaths and Papa Pixie!",
}
msg = MESSAGES.get(sm.getFieldID())
if msg is not None and sm.getChr() is not None:  # a first-enter script can run without a character
    sm.chatScript(msg)
sm.dispose()
