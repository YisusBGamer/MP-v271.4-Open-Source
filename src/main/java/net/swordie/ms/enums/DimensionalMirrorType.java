package net.swordie.ms.enums;

import java.util.Arrays;
import java.util.List;

/**
 * Dimensional Mirror destinations. The v265 window (UIWindow5.img/dimensionalMirror) has three fixed cards: slot 0 is
 * a locked placeholder, slot 1 Maple Chronicle (Grand Athenaeum), slot 2 Party Quest. Each entry carries its card slot
 * and Move sends that slot back (client 0x143f9a220), so only the {@link #SLOTS} destinations are listed.
 */
public enum DimensionalMirrorType {
    MuLungDojo(1207, "Mu Lung Dojo", "Challenge Mu Lung Dojo and see how strong you REALLY are.", 105, 0, false, List.of(1082392, 1082393, 1082394), 925020000),
    EreveConference(7998, "Ereve Conference Pavilion", "The Maple Alliance has been formed at the Continental Conference. \nBattle the Black Mage in 'Black Heaven' and 'Heroes of Maple'!", 75, 0, false, List.of(1142769, 1142804, 3015030, 3700350, 3017016), 913050010),
    Azwan(7892, "Fight for Azwan", "Eliminate the remainder of Hilla's forces in the Azwan area.", 120, 0, false, List.of(1162009, 2430690, 1032136), 262000000),
    EvoLab(1819, "Evolution Lab", "Ready for the wonders of the Evolution System?", 105, 1801, false, List.of(1162013, 4001832, 2431935, 2431936), 957000000),
    DimensionInvasion(31836, "Dimension Invasion", "Defeat the invaders before it's too late!", 140, 0, false, List.of(1082488, 1082489, 1142527, 1142528, 1142529, 1142530), 940020000),
    CrimsonheartCastle(31265, "Crimsonheart Castle", "A message from Masteria... What's going on?", 130, 31241, false, List.of(1142619, 2431935, 2431936), 301000000),
    GrandAthenaeum(32670, "Grand Athenaeum", "Head to the Grand Athenaeum to get a glipse of the past.", 100, 0, false, List.of(1122263, 2431892), 302000000),
    PartyQuestEntrance(7887, "Party Quest Entrance", "It's more fun when you play with friends! Create a party \nto participate in a special mission.", 50, 0, true, List.of(1003762, 1022073, 1132013, 1022175, 1902048, 2432131), 910002000),
    TowerOfOz(7839, "Tower of Oz", "Conquer the underwater tower known as the Tower of Oz to help Alicia's Soul.", 140, 42009, false, List.of(2028263, 2432465), 992000000),
    FriendStory(33041, "FriendStory", "Experience FriendStory, the first spin-off of MapleStory!", 100, 32707, false, List.of(1182079, 3015119, 3010875, 2432776, 2432788), 100000004),
    MonsterPark(7900, "Monster Park", "Massive EXP and different daily rewards await you!", 105, 0, false, List.of(2434746, 2434747, 2434748, 2434749, 2434750, 2434751, 2434745), 951000000),
    RootAbyss(30028, "Root Abyss", "Battle the seal guardians to weaken them. \nThe future of Maple World is in your hands.", 125, 30000, false, List.of(1003715, 1003716, 1003717, 1003718), 105200000),
    Ursus(33553, "Ursus", "Think you can take on Ursus the Destroyer?", 100, 0, true, List.of(3015279, 2434509, 2434389, 3700334, 1142879), 970072200),
    GhostPark(0, "Ghost Park", "", 1, 0, false, List.of(), 0),
    PrincessNo(58971, "Princess No Party Quest", "Do you want to challenge Princess No?", 140, 58913, true, List.of(2432755, 2432754, 2432753, 3010864), 811000999),
    EventHall(5399, "Event Hall", "Do you want to move to Event Hall?", 1, 0, false, List.of(), 820000000),
    AlienVisitor(17201, "Alien Visitor", "Do you want to move to Alien Visitor?", 200, 0, false, List.of(1113038, 1122256, 1032191, 1132230, 1003893), 861000000),
    Commerci(17699, "Theme Dungeon:\r\n Commerci Republic", "Do you want to move to Commerci Republic?", 140, 17600, false, List.of(1142981, 1302299, 1382232, 1452227, 1332249, 1492200), 865010200),
    HyperspaceCube(0, "Hyperspace Cube", "", 1, 0, false, List.of(), 0),
    Afterlands(63255, "Afterlands", "Do you want to move to Afterlands?", 75, 63020, false, List.of(1202237, 1202238, 1202239, 1202240), 867113100),
    MushroomShrine(0, "Mushroom Shrine", "", 1, 0, false, List.of(), 0),
    AbrupBaseCamp(0, "Abrup Base Camp", "", 1, 0, false, List.of(), 0),
    NewLeafCity(0, "New Leaf City", "", 1, 0, false, List.of(), 0),
    Gollux(0, "Gollux", "", 1, 0, false, List.of(), 0),
    MapleTour(0, "Maple Tour", "", 1, 0, false, List.of(), 0);

    private final int id;
    private final String name;
    private final String desc;
    private final int reqLv;
    private final int questID; // quest that must be completed first, 0 for none
    private final boolean isParty;
    private final List<Integer> items;
    private final int warpTo;

    DimensionalMirrorType(int id, String name, String desc, int reqLv, int questID, boolean isParty,
                          List<Integer> items, int warpTo) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.reqLv = reqLv;
        this.questID = questID;
        this.isParty = isParty;
        this.items = items;
        this.warpTo = warpTo;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getReqLv() {
        return reqLv;
    }

    public int getQuestID() {
        return questID;
    }

    public boolean isParty() {
        return isParty;
    }

    public List<Integer> getItems() {
        return items;
    }

    public int getWarpTo() {
        return warpTo;
    }

    /** Whether the mirror takes players here: GMS has it open and its map is in this client's data. */
    public boolean isAvailable() {
        return id != 0 && warpTo != 0 && net.swordie.ms.loaders.FieldData.getFieldInfoById(warpTo) != null;
    }

    /** Card slot -> destination; null = the locked placeholder card. */
    public static final DimensionalMirrorType[] SLOTS = {null, GrandAthenaeum, PartyQuestEntrance};

    /** The destination on the card the client's Move names, or null. */
    public static DimensionalMirrorType getBySlot(int slot) {
        if (slot < 0 || slot >= SLOTS.length || SLOTS[slot] == null || !SLOTS[slot].isAvailable()) {
            return null;
        }
        return SLOTS[slot];
    }

    /** The open destination the client asks for by its id, or null. */
    public static DimensionalMirrorType getById(int id) {
        if (id == 0) {
            return null;
        }
        return Arrays.stream(values()).filter(d -> d.getID() == id).findAny().orElse(null);
    }
}
