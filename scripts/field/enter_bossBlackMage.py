# Temple of Darkness phases and Beyond Existence (450013750, reward map): break the Genesis Crux there.
REWARD_MAP = 450013750
GENESIS_CRUX = 8880518

if sm.getFieldID() == REWARD_MAP and sm.hasMobsInField(REWARD_MAP, GENESIS_CRUX):
    sm.chatScript("Break the Genesis Crux to claim the Black Mage's treasure.")
