package net.swordie.ms.enums.customscripts;

import java.util.Arrays;

/**
 * Created on 1-12-2018.
 *
 * @author Asura
 */
public enum CustomFieldScripts { // Custom Field Scripts
    easy_zakum_enter(280030200),
    hard_hilla_enter(262031300),
    jaguar_habitat(931000500),
    ninja_jumpquest_in(800040210),
    neo_castle_up(993190400),
    fifth_job_magnus_spawn(940100200),
    cannon_tuto_start(3000600), // Cannoneers start here (no script in the map data): the play style question
    cannon_tuto_bedroom(912060500), // Cannoneer tutorial: Bedroom, after the cannon scene (cannon_tuto_direction2) left the screen locked
    cannon_tuto_beach(3000100), // Cannoneer tutorial: Shallow Beach, after the shipwreck scene left the screen locked
    honnouji_east_wall(807050200), // Hayato story: Hideyoshi, Tadakatsu and the Oda Pursuers (57153-57155)
    phantom_secretary_trap(931050220), // Phantom story: The Sneakiest Secretary (25416), the Black Wing Henchmen's trap
    kain_east_side_1(410000416), // Kain story: Whispershot (39403) - the Regulator's Wooden Boxes
    time_lane_test_1(270010500), // Temple of Time: Memory Lane 5 - Whale of Time (3505)
    time_lane_test_2(270020500), // Temple of Time: Road of Regrets 5 - Ice Knight (3512)
    time_lane_test_3(270030500), // Temple of Time: Road to Oblivion 5 - Temple Guardian (3519)
    rose_garden_golem(211080600), // Rose Garden: Hidden Garden 3 - King Castle Golem (31042)
    twilight_manji_test(273060000), // Twilight Perion: Spirit Zone Limbo - Manji's test, Ghostwood Stumpy (31930)
    explorer_way_to_beach(912090000), // Explorer story: Way to the Beach - Pirate versus Rabbit (32254)
    explorer_hidden_room(910350240), // Explorer story: Thief Hideout - The Hidden Room (32259)
    explorer_small_bridge(130030006), // Explorer story: Phantom on the Small Bridge (32340-32342)
    explorer_farm_entrance(100030400), // Explorer story: Evan's lost piglet (32330)
    xenon_alchemy_lab(926160001), // Xenon story: Encounter (23708) - Beryl
    cygnus_tiru_forest(130010120), // Cygnus Knights: The Lost Treasure (20850) - the Master of Disguise
    cygnus_secret_passage(930030005), // Cygnus Knights: Into the Secret Passage (20944/20947)
    aran_strolling_path(200060001), // Aran: To the Sealed Garden (21739) - the Nependeath stem
    aran_sealed_temple(925040100), // Aran: Catch the Shadow Knight! (21747)
    zero_dark_cave(323090220), // Zero's story: Unexpected Encounter (40207), The Rescue (40209)
    zero_lost_time(328090310), // Zero's story: The Right Choice (40704)
    commerci_canal_end(865020500), // Commerci: The Raging River (17703), [Mission] Eliminate Riverson (17922)
    navigator_relic_room(912070000), // Explorer pirates: Investigating the Discovery (1501)
    ;
    private int id;

    CustomFieldScripts(int val) {
        this.id = val;
    }

    public int getVal() {
        return id;
    }

    public static CustomFieldScripts getByVal(int id) {
        return Arrays.stream(values()).filter(cfs -> cfs.getVal() == id).findAny().orElse(null);
    }
}
