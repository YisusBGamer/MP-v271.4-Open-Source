# Portal script TroubleField_Out.
# Shinsoo International School (FriendStory): troublemaker fight -> back to where it started.
BACK = {330005000: 330001600, 330005100: 330001900, 330005200: 330000500, 330005300: 330001700, 330005400: 330004200, 330005500: 330001900, 330005600: 330000690}
sm.warp(BACK.get(sm.getFieldID(), 330000000), 0)
sm.dispose()
