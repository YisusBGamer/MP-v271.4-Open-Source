package net.swordie.ms.world.field.instance.instancehelper.boss;

/** Guardian Angel Slime fight map layout (160040000 / 160020000), as GMS v271 toggles it. */
final class GuardianAngelSlimeData {

    private GuardianAngelSlimeData() {
    }

    /** Map objects (tags) in the order of the full reset list, and their state after a reset. */
    static final String[] OBJ_ALL = {
            "hole1", "hole2", "hole3", "bottom1", "bottom2", "bottom4", "bottom15", "bottom14", "bottom12",
            "bottom13", "bottom11", "bottom9", "bottom10", "bottom8", "bottom6", "bottom7", "bottom5", "bottom3",
            "aset2", "aset3", "aset5", "aset4", "aset18", "aset16", "aset17", "aset15", "aset14", "aset6",
            "aset1", "aset29", "aset28", "aset25", "aset26", "aset27", "aset7", "aset12", "aset8", "aset9",
            "aset11", "aset10", "aset19", "aset20", "aset21", "aset23", "aset22", "aset30", "aset34", "aset31",
            "aset32", "aset33", "aset35", "aset55", "aset54", "aset52", "aset53", "aset40", "aset39", "aset36",
            "aset37", "aset38", "aset44", "aset41", "aset42", "aset43", "aset51", "aset49", "aset46", "aset62",
            "aset63", "aset61", "aset60", "aset58", "aset59", "aset57", "aset56", "aset50", "aset48", "aset47",
            "aset99", "aset98", "aset13", "aset24", "bset7", "bset8", "bset9", "bset10", "bset11", "bset12",
            "bset1", "bset2", "bset3", "bset4", "bset5", "bset6", "bset53", "bset54", "bset55", "bset56",
            "bset47", "bset48", "bset49", "bset50", "bset13", "bset14", "bset15", "bset16", "bset17", "bset18",
            "bset25", "bset26", "bset27", "bset45", "bset28", "bset40", "bset41", "bset42", "bset43", "bset44",
            "bset46", "bset29", "bset30", "bset31", "bset32", "bset34", "bset19", "bset20", "bset21", "bset22",
            "bset23", "bset35", "bset36", "bset37", "bset38", "bset57", "bset58", "bset59", "bset60", "bset61",
            "bset62", "bset63", "bset64", "bset65", "bset66", "bset99", "bset24", "bset97", "bset86", "bset85",
            "cset25", "cset24", "cset23", "cset21", "cset20", "cset22", "cset35", "cset34", "cset33", "cset36",
            "cset40", "cset39", "cset37", "cset38", "cset4", "cset5", "cset3", "cset2", "cset1", "cset13",
            "cset14", "cset12", "cset10", "cset15", "cset8", "cset7", "cset9", "cset6", "cset19", "cset16",
            "cset18", "cset17", "cset31", "cset26", "cset28", "cset27", "cset30", "cset29", "cset49", "cset42",
            "cset44", "cset43", "cset46", "cset45", "cset48", "cset47", "cset99", "cset41", "dset95", "dset99",
            "dset1", "dset2", "dset3", "dset4", "dset5", "dset6", "dset32", "dset33", "dset34", "dset35",
            "dset36", "dset37", "dset38", "dset39", "dset7", "dset9", "dset10", "dset11", "dset12", "dset14",
            "dset8", "dset13", "dset15", "dset17", "dset16", "dset18", "dset19", "dset21", "dset20", "dset22",
            "dset23", "dset25", "dset24", "dset26", "dset27", "dset29", "dset28", "dset30", "dset41", "dset43",
            "dset42", "dset47", "dset45", "dset46", "dset44", "dset88", "eset4", "eset2", "eset1", "eset5",
            "eset3", "eset9", "eset10", "eset8", "eset7", "eset11", "eset6", "eset15", "eset16", "eset14",
            "eset12", "eset17", "eset13", "eset20", "eset21", "eset19", "eset18", "eset24", "eset25", "eset22",
            "eset23", "eset33", "eset32", "eset31", "eset26", "eset34", "eset29", "eset28", "eset27", "eset30",
            "eset38", "eset39", "eset37", "eset36", "eset42", "eset35", "eset40", "eset41", "eset90", "eset91"
    };
    static final boolean[] OBJ_ALL_ON = {
            true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true,
            true, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false, false, false, false, false, false, false, false, false, false,
            false, false, false, false, false
    };
    static final String[] OBJ_HOLES = {"hole1", "hole2", "hole3"};
    static final String[] OBJ_BOTTOM = {
            "bottom1", "bottom2", "bottom4", "bottom15", "bottom14", "bottom12", "bottom13", "bottom11",
            "bottom9", "bottom10", "bottom8", "bottom6", "bottom7", "bottom5", "bottom3"
    };
    /** Wave layouts a-d: the objects shown and the footholds taken away while the layout is up. */
    static final String[][] OBJ_SET = {
            {"aset2", "aset3", "aset5", "aset4", "aset18", "aset16", "aset17", "aset15", "aset14", "aset6",
                    "aset1", "aset29", "aset28", "aset25", "aset26", "aset27", "aset7", "aset12", "aset8", "aset9",
                    "aset11", "aset10", "aset19", "aset20", "aset21", "aset23", "aset22", "aset30", "aset34", "aset31",
                    "aset32", "aset33", "aset35", "aset55", "aset54", "aset52", "aset53", "aset40", "aset39", "aset36",
                    "aset37", "aset38", "aset44", "aset41", "aset42", "aset43", "aset51", "aset49", "aset46", "aset62",
                    "aset63", "aset61", "aset60", "aset58", "aset59", "aset57", "aset56", "aset50", "aset48", "aset47",
                    "aset99", "aset98", "aset13", "aset24"},
            {"bset7", "bset8", "bset9", "bset10", "bset11", "bset12", "bset1", "bset2", "bset3", "bset4", "bset5",
                    "bset6", "bset53", "bset54", "bset55", "bset56", "bset47", "bset48", "bset49", "bset50", "bset13",
                    "bset14", "bset15", "bset16", "bset17", "bset18", "bset25", "bset26", "bset27", "bset45", "bset28",
                    "bset40", "bset41", "bset42", "bset43", "bset44", "bset46", "bset29", "bset30", "bset31", "bset32",
                    "bset34", "bset19", "bset20", "bset21", "bset22", "bset23", "bset35", "bset36", "bset37", "bset38",
                    "bset57", "bset58", "bset59", "bset60", "bset61", "bset62", "bset63", "bset64", "bset65", "bset66",
                    "bset99", "bset24", "bset97", "bset86", "bset85"},
            {"cset25", "cset24", "cset23", "cset21", "cset20", "cset22", "cset35", "cset34", "cset33", "cset36",
                    "cset40", "cset39", "cset37", "cset38", "cset4", "cset5", "cset3", "cset2", "cset1", "cset13",
                    "cset14", "cset12", "cset10", "cset15", "cset8", "cset7", "cset9", "cset6", "cset19", "cset16",
                    "cset18", "cset17", "cset31", "cset26", "cset28", "cset27", "cset30", "cset29", "cset49", "cset42",
                    "cset44", "cset43", "cset46", "cset45", "cset48", "cset47", "cset99", "cset41"},
            {"dset95", "dset99", "dset1", "dset2", "dset3", "dset4", "dset5", "dset6", "dset32", "dset33",
                    "dset34", "dset35", "dset36", "dset37", "dset38", "dset39", "dset7", "dset9", "dset10", "dset11",
                    "dset12", "dset14", "dset8", "dset13", "dset15", "dset17", "dset16", "dset18", "dset19", "dset21",
                    "dset20", "dset22", "dset23", "dset25", "dset24", "dset26", "dset27", "dset29", "dset28", "dset30",
                    "dset41", "dset43", "dset42", "dset47", "dset45", "dset46", "dset44", "dset88"},
    };
    static final int[][] FH_SET = {
            {51, 54, 56, 62, 65, 69, 70, 71, 72, 73, 74, 75, 80, 89, 93, 94, 95, 96, 97, 99, 100, 135, 136, 137,
                    138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148, 149, 150, 151, 152, 153, 154, 155, 156, 157,
                    158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168, 169, 170, 171, 173, 174, 175, 176, 177, 178},
            {203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 217, 218, 219, 220, 221, 222,
                    223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242,
                    243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254, 255, 256, 257, 258, 259, 260, 261, 262,
                    263, 264, 265, 266, 267, 268},
            {269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288,
                    289, 290, 291, 292, 294, 295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309,
                    310, 311, 312, 313, 314, 315, 316, 317},
            {293, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333, 334, 335, 336,
                    337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353, 354, 355, 356,
                    357, 358, 359, 360, 361, 362, 363, 364},
    };
    static final int[] FH_ALL = {
            78, 98, 102, 53, 66, 103, 104, 105, 106, 172, 179, 180, 181, 182, 183, 184, 185, 186, 45, 46, 47, 48,
            49, 52, 59, 60, 63, 64, 67, 68, 90, 91, 92, 51, 54, 56, 62, 65, 69, 70, 71, 72, 73, 74, 75, 80, 89,
            93, 94, 95, 96, 97, 99, 100, 135, 136, 137, 138, 139, 140, 141, 142, 143, 144, 145, 146, 147, 148,
            149, 150, 151, 152, 153, 154, 155, 156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168,
            169, 170, 171, 173, 174, 175, 176, 177, 178, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213,
            214, 215, 216, 217, 218, 219, 220, 221, 222, 223, 224, 225, 226, 227, 228, 229, 230, 231, 232, 233,
            234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253,
            254, 255, 256, 257, 258, 259, 260, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273,
            274, 275, 276, 277, 278, 279, 280, 281, 282, 283, 284, 285, 286, 287, 288, 289, 290, 291, 292, 294,
            295, 296, 297, 298, 299, 300, 301, 302, 303, 304, 305, 306, 307, 308, 309, 310, 311, 312, 313, 314,
            315, 316, 317, 293, 318, 319, 320, 321, 322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333,
            334, 335, 336, 337, 338, 339, 340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353,
            354, 355, 356, 357, 358, 359, 360, 361, 362, 363, 364, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14,
            15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39,
            40, 41, 42, 43, 44
    };
    static final int[] FH_ALL_OFF = {78, 98, 102};
    /** Surface floor over the pit (off during a wave). */
    static final int[] FH_TOP = {45, 46, 47, 48, 49, 52, 59, 60, 63, 64, 67, 68, 90, 91, 92};
    /** Pit floor (on during a wave). */
    static final int[] FH_BOTTOM = {53, 66, 103, 104, 105, 106, 172, 179, 180, 181, 182, 183, 184, 185, 186};
    static final int[] FH_HOLES = {78, 98, 102};
}
