package net.swordie.ms.enums;

import java.util.Arrays;

public enum DimensionalPortalTownType
{
    // GMS v271 order and labels (slide menu type 6: "#i# area: town")
    Henesys(0, 100000000, "Henesys: Henesys"),
    Ellinia(1, 101000000, "Ellinia: Ellinia"),
    Perion(2, 102000000, "Perion: Perion"),
    KerningCity(3, 103000000, "Kerning City: Kerning City"),
    LithHarbor(4, 104000000, "Lith Harbor: Lith Harbor"),
    Sleepywood(5, 105000000, "Sleepywood: Sleepywood"),
    Nautilus(6, 120000000, "Nautilus: Nautilus Harbor"),
    Ereve(7, 130000000, "Ereve: Ereve"),
    Rien(8, 140000000, "Rien: Rien"),
    Orbis(9, 200000000, "Sky Road: Orbis"),
    ElNath(10, 211000000, "El Nath Mountains: El Nath"),
    Ludibrium(11, 220000000, "Ludibrium Castle: Ludibrium"),
    OmegaSector(12, 221000000, "Ludus Lake: Omega Sector"),
    KoreanFolkTown(13, 222000000, "Korean Folk Town: Korean Folk Town"),
    Aquarium(14, 230000000, "Aqua Road: Aquarium"),
    Leafre(15, 240000000, "Minar Forest: Leafre"),
    MuLung(16, 250000000, "Mu Lung Garden: Mu Lung"),
    HerbTown(17, 251000000, "Mu Lung Garden: Herb Town"),
    Ariant(18, 260000000, "Burning Road: Ariant"),
    Magatia(19, 261000000, "Sunset Road: Magatia"),
    Edelstein(20, 310000000, "Black Wing Territory: Edelstein"),
    Elluel(21, 101050000, "Fairy Forest: Elluel"),
    Pantheon(22, 400000000, "Nova's Sanctum: Pantheon"),
    Kritias(23, 241000000, "Kritias: Marketplace"),
    Haven(24, 310070000, "Scrapyard: Haven"),
    SavageTerminal(25, 402000000, "Savage Terminal: Brigand's Row");

    private int val;
    private int mapID;
    private String desc;
    DimensionalPortalTownType(int val, int mapID, String desc)
    {
        this.val = val;
        this.mapID = mapID;
        this.desc = desc;
    }
    public int getVal() {return val;}
    public int getMapID() {return mapID; }
    public String getDesc() {return desc; }

    public static DimensionalPortalTownType getByVal(int val) {
        return Arrays.stream(values()).filter(dpt -> dpt.getVal() == val).findAny().orElse(null);
    }
}
