# Portal script pt_dlnext.
# Grand Athenaeum: into the episodes (official: the Dimension Library book list).
EPISODES = (
    ("Episode I: The White Mage (Ariant)", 302010000),
    ("Episode II: Empress in Training (Cygnus's Rest)", 303000000),
    ("Episode III: The Black Witch (Gelimer's Lab)", 304000000),
    ("Episode IV: The Lion King (El Nath)", 306000000),
)
text = "Which story would you like to step into?\r\n\r\n#b"
for i in range(len(EPISODES)):
    text += "#L{}#{}#l\r\n".format(i, EPISODES[i][0])
sel = sm.sendNext(text)
if 0 <= sel < len(EPISODES):
    sm.warp(EPISODES[sel][1], 0)
sm.dispose()
