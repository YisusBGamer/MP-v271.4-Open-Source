# id 940011000 (Nova's Sanctum : Pantheon), field 940011000 - Angelic Buster is created here.
# Classic plays the tutorial as on the official servers (it starts with Angelic Buster's childhood, angelic_tuto1);
# Express skips it to the Secret Plaza at level 10 with the tutorial's skills (see PlayStyleModule).
MAPLE_ADMINISTRATOR = 2007
skills_to_add = [
    60011222,
    60011216,
    60011218,
    60011220,
    60010217,
]
map_to_warp = 400000000  # Secret Plaza
target_level = 10

sm.setSpeakerID(MAPLE_ADMINISTRATOR)
sm.removeEscapeButton()
if sm.askTutorialSkip(map_to_warp):
    if sm.getChr().getLevel() < target_level:
        sm.addLevel(target_level - sm.getChr().getLevel())
    for skillID in skills_to_add:
        sm.giveSkill(skillID)
    sm.warp(map_to_warp)
else:
    sm.warp(940011010)  # Heliseum Hideout, years ago
