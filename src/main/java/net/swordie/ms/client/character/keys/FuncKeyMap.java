package net.swordie.ms.client.character.keys;

import net.swordie.ms.connection.OutPacket;
import net.swordie.orm.dao.KeymappingDao;
import net.swordie.orm.dao.SworDaoFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 1/4/2018.
 */
@Entity
@Table(name = "funckeymap")
public class FuncKeyMap {

    private static final KeymappingDao keymappingDao = (KeymappingDao) SworDaoFactory.getByClass(Keymapping.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(
            mappedBy = "funcKeyMap",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    private List<Keymapping> keymap;

    @Transient
    private static final int MAX_KEYBINDS = 89;
    @Transient
    private static final int MAX_COMBINATIONS = 10;
    @Transient
    private static final int COMBINATION_START = 102;


    public FuncKeyMap(boolean init) {
        if (init) {
            keymap = new ArrayList<>();
        }
    }

    public List<Keymapping> getKeymap() {
        if (keymap == null) {
            keymap = keymappingDao.byFuncKeyMap(this);
        }
        return keymap;
    }

    public void setKeymap(ArrayList<Keymapping> keymap) {
        this.keymap = keymap;
    }

    public Keymapping getMappingAt(int index) {
        for (Keymapping km : getKeymap()) {
            if (km.getIndex() == index) {
                return km;
            }
        }
        return null;
    }

    /**
     * One key preset: empty flag, then 89 keys and the 10 second-layer number keys (client keys 2-11, our indices
     * 102-111; the client reports them as 100 + key).
     */
    public void encode(OutPacket outPacket) {
        int size = getKeymap() == null ? 0 : getKeymap().size();
        outPacket.encodeByte(size == 0);
        if (size > 0) {
            for (int i = 0; i < MAX_KEYBINDS; i++) {
                encodeMapping(outPacket, getMappingAt(i));
            }
            for (int i = 0; i < MAX_COMBINATIONS; i++) {
                encodeMapping(outPacket, getMappingAt(COMBINATION_START + i));
            }
        }
    }

    private static void encodeMapping(OutPacket outPacket, Keymapping tuple) {
        // an entry the client refuses would cost the whole preset: send it as empty
        if (tuple == null || !FuncKeyRules.isAllowed(tuple.getType(), tuple.getVal())) {
            outPacket.encodeByte(0);
            outPacket.encodeInt(0);
        } else {
            outPacket.encodeByte(tuple.getType());
            outPacket.encodeInt(tuple.getVal());
        }
    }

    public void putKeyBinding(int index, byte type, int value) {
        Keymapping km = getMappingAt(index);
        if (km == null) {
            km = new Keymapping();
            km.setIndex(index);
            km.setType(type);
            km.setVal(value);
            getKeymap().add(km);
        } else {
            km.setType(type);
            km.setVal(value);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // {key (DirectInput scan code), type (4 = window/function, 5 = action), function id (StatusBar3.img/KeyConfig/icon)}
    // Same as the client's own defaults, which match live GMS.
    private static final int[][] DEFAULT_CLASSIC = {
            {1, 4, 46},   // Esc: Menu
            {59, 4, 39},  // F1: Maple Guide
            {60, 4, 214}, // F2: Planner
            {62, 4, 27},  // F4: Bosses
            {63, 4, 22},  // F5: Cash Shop
            {64, 4, 25},  // F6: Legion
            {65, 4, 49},  // F7: Achievements
            {66, 4, 42},  // F8: Monster Collection
            {67, 4, 35},  // F9: Battle Analysis
            {87, 4, 9},   // F11: Key Bindings
            {70, 4, 47},  // Scroll Lock: Screenshot
            {2, 4, 10},   // 1: To All
            {3, 4, 12},   // 2: To Party
            {4, 4, 13},   // 3: To Buddy
            {5, 4, 18},   // 4: To Guild
            {6, 4, 23},   // 5: To Alliance
            {13, 4, 217}, // =: Auto-Harvest
            {16, 4, 8},   // Q: Quests
            {17, 4, 5},   // W: World Map
            {18, 4, 0},   // E: Equip
            {19, 4, 4},   // R: Friends
            {23, 4, 1},   // I: Items
            {24, 4, 216}, // O: Decorations
            {25, 4, 19},  // P: Party
            {27, 4, 15},  // ]: Quick Slots
            {31, 4, 2},   // S: Character Info
            {34, 4, 17},  // G: Guild
            {35, 4, 11},  // H: Whisper
            {37, 4, 3},   // K: Skill
            {38, 4, 20},  // L: Quest Helper
            {39, 4, 26},  // ;: Medals
            {40, 4, 16},  // ': Chat+
            {44, 5, 50},  // Z: Pick Up
            {45, 5, 51},  // X: Sit
            {47, 4, 31},  // V: Events
            {48, 4, 29},  // B: Profession
            {50, 4, 7},   // M: Mini-map
            {51, 4, 45},  // ,: Change Channel
            {29, 5, 52},  // Ctrl: Attack
            {56, 5, 53},  // Alt: Jump
            {57, 5, 54},  // Space: NPC/Gather
    };

    private static final int[][] DEFAULT_ALTERNATIVE = {
            {1, 4, 46},   // Esc: Menu
            {59, 4, 39},  // F1: Maple Guide
            {60, 4, 214}, // F2: Planner
            {62, 4, 27},  // F4: Bosses
            {63, 4, 22},  // F5: Cash Shop
            {64, 4, 25},  // F6: Legion
            {65, 4, 49},  // F7: Achievements
            {66, 4, 42},  // F8: Monster Collection
            {67, 4, 35},  // F9: Battle Analysis
            {87, 4, 9},   // F11: Key Bindings
            {70, 4, 47},  // Scroll Lock: Screenshot
            {82, 4, 10},  // Ins: To All
            {71, 4, 12},  // Home: To Party
            {73, 4, 13},  // Page Up: To Buddy
            {83, 4, 18},  // Del: To Guild
            {79, 4, 23},  // End: To Alliance
            {13, 4, 217}, // =: Auto-Harvest
            {22, 4, 0},   // U: Equip
            {23, 4, 1},   // I: Items
            {24, 4, 216}, // O: Decorations
            {25, 4, 19},  // P: Party
            {27, 4, 15},  // ]: Quick Slots
            {34, 4, 17},  // G: Guild
            {35, 4, 11},  // H: Whisper
            {36, 4, 8},   // J: Quests
            {37, 4, 3},   // K: Skill
            {38, 4, 20},  // L: Quest Helper
            {39, 4, 26},  // ;: Medals
            {40, 4, 16},  // ': Chat+
            {44, 5, 50},  // Z: Pick Up
            {45, 5, 51},  // X: Sit
            {46, 4, 2},   // C: Character Info
            {47, 4, 31},  // V: Events
            {48, 4, 29},  // B: Profession
            {49, 4, 5},   // N: World Map
            {50, 4, 7},   // M: Mini-map
            {51, 4, 45},  // ,: Change Channel
            {52, 4, 4},   // .: Friends
            {29, 5, 52},  // Ctrl: Attack
            {56, 5, 53},  // Alt: Jump
            {57, 5, 54},  // Space: NPC/Gather
    };

    public static FuncKeyMap getDefaultMapping(int keySettingType) {
        FuncKeyMap fkm = new FuncKeyMap(true);
        for (int[] key : keySettingType == 0 ? DEFAULT_CLASSIC : DEFAULT_ALTERNATIVE) {
            fkm.putKeyBinding(key[0], (byte) key[1], key[2]);
        }
        return fkm;
    }
}
