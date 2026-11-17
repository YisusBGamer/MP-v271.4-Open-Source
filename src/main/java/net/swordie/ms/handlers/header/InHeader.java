package net.swordie.ms.handlers.header;

import net.swordie.ms.ServerConstants;
import net.swordie.ms.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static net.swordie.ms.ServerConstants.UNVERIFIED_OP;

/**
 * Values are GMS v271.4. UNVERIFIED_OP marks entries whose v271 value is still unknown (the last known number is
 * in the trailing comment); they are never matched on receive.
 */
public enum InHeader {
    LEAVE_BUTTON_PRESSED(-1),
    DUMMY_CODE(100),
    BEGIN_SOCKET(101),
    SECURITY_PACKET(102),
    PERMISSION_REQUEST(113),
    LOGIN_BASIC_INFO(114),
    CHECK_LOGIN_AUTH_INFO(115),
    VIEW_CHANNEL_REQUEST(118),
    SELECT_WORLD(119),
    CHECK_SPW_REQUEST(121),
    CHAR_SELECT(122),
    CLIENT_LOADING_TIME_LOG(123),
    CHECK_SPW_EXIST_REQUEST(124),
    MIGRATE_IN(125),
    SELECT_CHARACTER(126), // ?
    SELECT_ACCOUNT(127),
    WORLD_INFO_REQUEST(131),
    CHECK_DUPLICATE_ID(132),
    WORLD_LIST_REQUEST(133), // the reference tree's LOGOUT_WORLD; resets the client's world list, 7 ints + 1 byte
    PERMISSION_REQUEST_FAKE(134),
    CHECK_LOGIN_AUTH_INFO_FAKE(135),
    CHANGE_SPW_REQUEST_OLD(UNVERIFIED_OP), // v232 135
    CREATE_MAPLE_ACCOUNT_FAKE(136),
    SELECT_ACCOUNT_FAKE(137),
    SELECT_WORLD_FAKE(138),
    SELECT_CHARACTER_FAKE(139),
    CREATE_NEW_CHARACTER_FAKE(140),
    CREATE_NEW_CHARACTER(141),
    DELETE_CHARACTER(144),
    RESERVED_DELETE_CHARACTER_CONFIRM(145),
    RESERVED_DELETE_CHARACTER_CANCEL(146),
    RENAME_CHARACTER(147),
    EXCEPTION_LOG(148),
    EXCEPTION_LOG_2(149),
    PONG(150),
    SOCKET_NOTICE_151(151), // v271 new, sender 0x1431fcac0 in the socket class: 4 (0 at world select); no reply
    PRIVATE_SERVER_PACKET(153),
    RESET_LOGIN_STATE_ON_CHECK_OTP(154),
    CHANGE_SPW_REQUEST(156), // v271 (v265 155): no plain sender in either client; +1 from the 151 insert, unverified
    // v271: 151 is new (socket log sender 0x1431fcac0), so v265 151..162 are +1 (157..163 by send-site alignment)
    CHECK_SOW_REQUEST_FAKE(157),
    CHECK_OTP_REQUEST(158),
    CHECK_DELETE_CHARACTER_OTP(159),
    CHECK_OTP_FOR_WEB_LAUNCHING_REQUEST(160),
    CREATE_SECURITY_HANDLE(161),
    ALBA_REQUEST(162),
/* 163 */    UPDATE_CHARACTER_CARD_REQUEST(163), // v271 sender 0x1439cd2d0 (the SET_PHYSICAL_WORLD_ID handler); no reply
    ALIVE_ACK(169), // v271 (v265 168)
    UNK288_169(UNVERIFIED_OP), // v232 169
    PING_CHECK_ACK(170), // v271 in-game answer to PING_CHECK_RESULT: 11 opaque ints
    ANTI_MACRO_RESPONSE(UNVERIFIED_OP), // v232 170
    CLIENT_ERROR(172), // v271 (v265 171): the client's copy of a packet it failed to read
    APPLY_HOTFIX(175), // v271 (v265 174)
    SERVER_STATUS_REQUEST(177), // v271 (v265 176), bracket only
    WVS_SET_UP_STEP(180), // v271 (v265 179): login setup step numbers (0x11..0x1d at start)
    WVS_CRASH_CALLBACK(181), // v271 (v265 180): sender 0x1431f9f40, S
    CREATE_NEW_CHARACTER_CHECKER(182), // v271 (v265 181): sender 0x1439be2d0, S
    SOME_CHANNEL_INFO_REQUEST(UNVERIFIED_OP), // v232 182
    WORLD_STATUS_REQUEST(184), // v271 (v265 183): sender 0x1439d21b0, 1 4 4 1 4 (Burning dialog)
    SELECT_CHARACTER_AFTER_CREATE(185), // v271 (v265 184): sent by the create result 0x1439ca2f0, 4 1 S S
    CHAR_POSITION_CHANGE(186), // v271 (v265 185): sender 0x14206a6f0, 4 account, 4 n, n x 4
    CHAR_SELECT_NO_PIC(UNVERIFIED_OP), // v232 190
    USE_AUTH_SERVER(194), // v271 (v265 192)
    UNK232_195(UNVERIFIED_OP), // v232 195; before login, 8-byte machine/session value
    UNK232_197(UNVERIFIED_OP), // v232 197; empty, right after CHECK_SPW_EXIST_REQUEST
    DONE_LOADING(UNVERIFIED_OP), // v232 198
    SOME_DATE_REQUEST(UNVERIFIED_OP), // v232 199
    AUTH_FAILURE(200), // v271 (v265 198): sender 0x145900f00
    UNKNOWN_OPCODE_202_SINK(204), // v271 (v265 202): bracket 203=201 / 205=203, byte // T: discard-only
    LOGIN_PANEL_NOTICE(208), // v271 0x143a82520 (v265 206, never handled): byte from the world/channel panel, no reply
    USER_MIGRATE_TO_MONSTER_FARM(UNVERIFIED_OP), // v232 208
    WORLD_LEAP_REQUEST(209), // v271 (v265 207): sender 0x1439d33d0, 4
    USER_MIGRATE_TO_MONSTER_FARM_BY_INVITE_ITEM(UNVERIFIED_OP), // v232 209
    CLIENT_LOCALE_REPORT(210), // v271: three ints, each the client's Windows locale id (0x0409 on an en-US PC), sent at character select after SELECT_WORLD_RESULT_ACK; no literal send site (built off the usual path); GMS doesn't reply
    USER_TRANSFER_ASWAN_REQUEST(UNVERIFIED_OP), // v232 210
    USER_TRANSFER_ASWAN_READY_REQUEST(UNVERIFIED_OP), // v232 211
    UNKNOWN_OPCODE_212_SINK(212), // 0x1404a4080..: int type 0/1/3/4, int
    ASWAN_RETIRE_REQUEST(UNVERIFIED_OP), // v232 212
    USER_EMOTICON_ITEM_USE_REQUEST(UNVERIFIED_OP), // v232 217
    QUICK_LOGIN_REQUEST(218), // v271 0x1439acad0, new: empty, login screen opened; answered with QUICK_LOGIN
    USER_GET_TIME_REQUEST(225), // v271 (v265 223): sender 0x1439c0b40, 4 S S S
    SPECIAL_LOGIN_AUTH_REQUEST(230), // v271 (v265 228); byte, int token (the hook's "WebStart <int>")
    CLIENT_LOAD_DONE_NOTICE(234), // v271 0x14398a5a0 (v265 232): empty, the login loader thread finished its four loads
    SELECT_WORLD_RESULT_ACK(235), // v271 0x1439c63a0 (v265 233): empty, end of the select-world result handler
    LANGUAGE_SETTING_CHANGE_ANSWER(237), // v271 0x141282c40 (called after the "Language Setting Change Notice" dialog): int counter, int option, int agreed; no reply
    BEGIN_USER(247),
    WORLD_TRANSFER_REQUEST(251),
    USER_TRANSFER_FIELD_REQUEST(252), // v271 (v265 248); the portal form has no plain client sender: layout as v265, unverified
    WORLD_TRANSFER_SHINNING_STAR_REQUEST(UNVERIFIED_OP), // v265 252; v271 252 is USER_TRANSFER_FIELD_REQUEST
    USER_STORE_BANK_REQUEST(UNVERIFIED_OP), // v232 252
    USER_TRANSFER_CHANNEL_REQUEST(253), // v271 (v265 249): send-site alignment, 1 4
    USER_TRANSFER_TO_HUB_REQUEST(254), // v265 250
    USER_SPECIAL_EFFECT_LOCAL(UNVERIFIED_OP), // v232 255
    USER_MIGRATE_TO_CASH_SHOP_REQUEST(256), // v271 (v265 253): send-site alignment, 4 1
    USER_UNK214_259(UNVERIFIED_OP), // v232 256
    USER_MIGRATE_AUCTION_REQUEST(257), // v271 (v265 254): sender 0x145999f10, 4 (v265 B4 4); the server reads nothing
    USER_REQUEST_PV_P_STATUS(UNVERIFIED_OP), // v265 257; v271 257 is USER_MIGRATE_AUCTION_REQUEST
    USER_RECIPE_COOLTIME_REQUEST(UNVERIFIED_OP), // v232 260
    USER_MIGRATE_TO_PVE_REQUEST(261), // v265 258
    USER_MOVE(263), // v271 (v265 260): bracket 262=259/264=261, test-14 bytes; path grammar v271 (MovementInfo)
    UNK233_264(UNVERIFIED_OP), // v232 263
    USER_SIT_REQUEST(264), // v271 (v265 261): sender 0x1459c2220, 2
    USER_PORTABLE_CHAIR_SIT_REQUEST(265), // v271 (v265 262): sender 0x1459c1380, 4 4 4 1 4 4 4 + chair data (+0x48 encoders equal)
    UNKNOWN_OPCODE_263_SINK(266), // v271 (v265 263) // 0x144e88560: empty
    USER_DANCE_STOP_REQUEST(269), // v271 (v265 266): send-site alignment
    USER_MELEE_ATTACK(270), // v271 (v265 267): senders 0x1453d9550 etc. (ctor 0x10e); new int after the mask byte and per mob before the damage lines
    USER_SHOOT_ATTACK(271), // v271 (v265 268): sender 0x143393730; same two new ints
    USER_MAGIC_ATTACK(272), // v271 (v265 269): sender 0x143391400; same two new ints
    USER_BODY_ATTACK(273), // v271 (v265 270): bracket only (no literal sender in either client), medium
    USER_AREA_DOT_ATTACK(274), // v271 (v265 271): senders 0x1455e9a10/0x1455f4290, melee deltas
    USER_AREA_DOT_ATTACK_NO_AREA(275), // v271: sender 0x1455ee5a0 (Angelic Buster, GMS 234x): the area-dot sequence without the area object id and one int
    USER_AFFECTED_AREA_FOR_SCREEN_ATTACK(276), // v271 (v265 273): send-site alignment, same encode sequence
    //
    USER_HIT(278), // v271 (v265 275): sender 0x142a1bb00 (ctor 0x116); all 20 senders write as in v265
    //
    USER_CHAT(280), // v271 (v265 277): senders 0x143462310/0x1435631b0, 4 S 1
    USER_ITEM_LINKED_CHAT(281), // v271 by send-site alignment (high, same encode order), sender 0x143462310
    USER_AD_BOARD_CLOSE(282), // v265 279
    USER_OPTION_CHANGE_REQUEST(UNVERIFIED_OP), // v232 282
    USER_EMOTION(283), // v271 (v265 280): 4 4 1
    USER_MONKEY_EFFECT_ITEM(UNVERIFIED_OP), // v265 283; v271 283 is USER_EMOTION
    ANDROID_EMOTION(284), // v265 281: sender 0x145a67ca0, 4 4
    USER_ACTIVATE_EFFECT_ITEM(285), // v265 282
    USER_ACTIVATE_DAMAGE_SKIN_UNK(UNVERIFIED_OP), // collides with 285; probably a duplicate
    USER_ACTIVATE_DAMAGE_SKIN_PREMIUM(UNVERIFIED_OP), // v265 286 (the skill ring window); no v271 sender found
    USER_ACTIVATE_NICK_ITEM(289), // v271 (v265 284): send-site alignment, 4 4 (field entry sends 0 0)
    USER_ACTIVATE_DAMAGE_SKIN(290), // v271 (v265 285): senders 0x14269d611/6a3, B4 B4 [4 4] as v265
    USER_MONSTER_LIFE_INVITE_ITEM_USE_REQUEST(UNVERIFIED_OP), // v232 290
    USER_ACTIVATE_DAMAGE_SKIN_UNIT(291), // v271 (v265 287): 8 4
    USER_KAISER_TRANSFORM_WING(UNVERIFIED_OP), // v265 291; v271 291 is USER_ACTIVATE_DAMAGE_SKIN_UNIT (send sites: v271 295)
    USER_DAMAGE_SKIN_SAVE_REQUEST(292), // v271 (v265 288): types 0/1/2/3/6/7 with the v265 bodies
    USER_KAISER_TRANSFORM_TAIL(UNVERIFIED_OP), // v265 292; v271 292 is USER_DAMAGE_SKIN_SAVE_REQUEST (send sites: v271 296)
    CHARACTER_INFO_BACKGROUND_SET(293), // v271 sender 0x142bb4e60 (v265 289): 4 background id; GMS keeps it in quest 7295 (started, value = id)
    USER_CHAR_RENAME_ITEM_USE_REQUEST(UNVERIFIED_OP), // v232 293
    USER_DEFAULT_WING_ITEM(294), // v271 (v265 290): send-site alignment, 4 item
    USER_UPGRADE_TOMB_EFFECT(297), // v265 293 // v271 client 0x1453a6fd0: 0x129, Encode4(5510000 Respawn Token), x, y
    USER_BAN_MAP_BY_MOB(298), // v271 (v265 294): send-site alignment
    USER_MONSTER_BOOK_SET_COVER(299), // v265 295
    USER_SELECT_NPC(300), // v271 (v265 296): 4 2 2 4
    USER_SCRIPT_MESSAGE_ANSWER(301), // v271 (v265 297): the 70 answer senders read as in v265
/* 303*/    USER_SHOP_REQUEST(303), // v271 (v265 299): senders 0x143fca830.. types 0-4 as v265, quantities 4 bytes
    USER_TRUNK_REQUEST(306), // v271 (v265 300): senders 0x144280600.. types as v265, quantities 4 bytes
    USER_PARCEL_REQUEST(307), // v271 (v265 301): senders 0x143e233a0 (5 claim), 0x143e23590 (6 delete), 0x143e24b00 / 0x143e26c40 (3 send), 0x143e2a990 (8 close), 0x141a3d810 (0/1 open)
    USER_EFFECT_LOCAL(309), // v271 by send-site alignment (high, same encode order), sender 0x1455b8120,0x1455b8250
    USER_CREATE_AREA_DOT_REQUEST(311), // v271 (v265 305): GMS 311 = 4, 4 skill, 4, 2 n, n x rect, 4 -1 (as the handler)
    USER_CREATE_HOLIDOM_REQUEST(313), // v265 307
    AUCTION_REQUEST(316), // v271 (v265 310): 20 senders, bodies as v265; types 57+ are 2 lower (old-claim 40-42 gone)
    AUCTION_LEAVE_REQUEST(317), // v271 (v265 311): sender 0x142b14720, empty
    USER_GATHER_ITEM_REQUEST(320), // v271 (v265 314): 4 tick, 1 inv
    USER_POUR_IN_BAG_TO_BAG(UNVERIFIED_OP), // v265 320; v271 320 is USER_GATHER_ITEM_REQUEST (send sites: v271 326)
    USER_SORT_ITEM_REQUEST(321), // v271 (v265 315): 4 tick, 1 inv
    USER_CHANGE_SLOT_POSITION_REQUEST(322), // v271 (v265 316): sender 0x1459ace60, 4 1 2 2 4(qty) B8 4 4
    USER_TEXT_EQUIP_INFO(323), // v271 (v265 317): send-site alignment, 4 S
    USER_UNK228_323(UNVERIFIED_OP), // v232 323
    USER_POP_OR_PUSH_BAG_ITEM_TO_INVEN(324), // v265 318
    USER_BAG_TO_BAG_ITEM(325), // v265 319
    USER_UNK227_322_MABYBE_FLAME(UNVERIFIED_OP), // v232 325
    USER_USE_NAVI_FLYING_SKILL_REQUEST(UNVERIFIED_OP), // v232 326
    USER_STAT_CHANGE_ITEM_USE_REQUEST(329), // v271 (v265 323): senders 0x1459afb70/0x1459b1860, 4 tick 2 slot 4 item 4
    USER_STAT_CHANGE_ITEM_CANCEL_REQUEST(330), // v271 (v265 324): 4
    USER_STAT_CHANGE_BY_PORTABLE_CHAIR_REQUEST(331), // v271 (v265 325): send-site alignment, same shape
    USER_MOB_SUMMON_ITEM_USE_REQUEST(332), // v271 (v265 326): send-site alignment, 4 2 4
    USER_ADDITIONAL_SLOT_EXTEND_ITEM_USE_REQUEST(UNVERIFIED_OP), // v265 332 (stamp, 4 2 2): no v271 sender, stamps removed
    USER_PET_FOOD_ITEM_USE_REQUEST(333), // v271 (v265 327): send-site alignment, 4 2 4
    USER_TAMING_MOB_FOOD_ITEM_USE_REQUEST(334), // v271 (v265 328): send-site alignment, same shape
    USER_SCRIPT_ITEM_USE_REQUEST(335), // v271 (v265 329): sender 0x1459b4a00, 4 tick 2 slot 4 item 4
    USER_RECIPE_OPEN_ITEM_USE_REQUEST(336), // v271 (v265 330): send-site alignment, 4 2 4
    USER_CONSUME_CASH_ITEM_USE_REQUEST(337), // v271 (v265 331): every cash-use sender starts 4 0, 4 tick, 2 slot, 4 item; the cube one (0x1433df8a0) ends 1 1 S
    USER_CASH_PET_PICK_UP_ON_OFF_REQUEST(338), // v271 (v265 333): send-site alignment, 4 1 1 4
    SET_SON_OF_LINKED_SKILL_REQUEST(UNVERIFIED_OP), // v232 338
    USER_CASH_PET_SKILL_SETTING_REQUEST(339), // v271 (v265 334): send-site alignment, 4 4 1
    UNLINK_SKILL_REQUEST(UNVERIFIED_OP), // v232 339
    USER_DESTROY_PET_ITEM_REQUEST(340), // v271 (v265 335): send-site alignment, 4 B8
    USER_EXP_CONSUME_ITEM_USE_REQUEST(UNVERIFIED_OP), // 340 is the AP change request
    USER_BRIDLE_ITEM_USE_REQUEST(341), // v271 (v265 336): send-site alignment, 4 2 4 4
    USER_SKILL_LEARN_ITEM_USE_REQUEST(342), // v271 (v265 337): send-site alignment, 4 2 4
    USER_SKILL_RESET_ITEM_USE_REQUEST(343), // v271 (v265 338): bracket 342/346, no literal sender in either client; 4 tick, 2 slot, 4 item
    USER_SKILL_UNK227_340(UNVERIFIED_OP), // v232 343
    USER_ABILITY_RESET_ITEM_USE_REQUEST(344), // v271 (v265 339): bracket, as above; 4 tick, 2 slot, 4 item
    USER_ABILITY_CHANGE_ITEM_USE_REQUEST(345), // v271 (v265 340): bracket, as above; 4 tick, 2 slot, 4 item, B8, B8
    USER_EXP_ITEM_GET_REQUEST(346), // v271 (v265 341): send-site alignment, 4 2 4
    USER_CHAR_SLOT_INC_ITEM_USE_REQUEST(347), // v271 (v265 342): send-site alignment, 2 4
    USER_KAISER_COLOR_CHANGE_ITEM_USE_REQUEST(348), // v271 (v265 343): send-site alignment, 4 4 1
    USER_SKILL_KEYDOWN_CHARGE_REQUEST(UNVERIFIED_OP), // v232 348
    USER_MAP_TRANSFER_ITEM_USE_REQUEST(351), // v271 (v265 346): send-site alignment, 2 4 4
    // Between 297 and 301 a new Header was added
    USER_PORTAL_SCROLL_USE_REQUEST(352), // v271 (v265 347): send-site alignment, 4 2 4
    USER_UNK218_347(UNVERIFIED_OP), // v232 352
    USER_FIELD_TRANSFER_REQUEST(353), // v271 (v265 348): send-site alignment, 4
    USER_FIELD_TRANSFER_REQUEST_2(354), // v271 (v265 349): sender 0x145a66c70, 4 field id (Town Portal UI Move)
    USER_UPGRADE_ITEM_USE_REQUEST(355), // v271 (v265 350): send-site alignment, 4 2 2 2 2 1
    USER_UPGRADE_ASSIST_ITEM_USE_REQUEST(356), // v271 (v265 351): 4 2 2 2 1
    USER_TEMPORARY_STAT_UPDATE_REQUEST(UNVERIFIED_OP), // v232 356
    USER_HYPER_UPGRADE_ITEM_USE_REQUEST(357), // v271 (v265 352): 4 2 2 1 1
    USER_EX_ITEM_UPGRADE_ITEM_USE_REQUEST(358), // v271 (v265 353): sender 0x1433da170, 4 tick, 4 n, n x 2 use pos, 4 flame (negative = meso reset), 2 equip, 1 from window, 1 count, 1 1 S
    USER_MEMORIAL_EX_UPGRADE_ITEM_OPTION_REQUEST(359), // v271 (v265 354): sender 0x141a4a250, 4 pick (-1 = keep before), 1 1 S
    USER_KARMA_CONSUME_ITEM_USE_REQUEST(360), // v271 (v265 355): 4 2 2 2
    USER_EVENT_UPGRADE_ITEM_USE_REQUEST(361), // v271 (v265 356): 4 2 2 1
    USER_ITEM_OPTION_UPGRADE_ITEM_USE_REQUEST(362), // v271 (v265 357): 4 2 2 1
    USER_ANTI_MACRO_ITEM_USE_REQUEST(UNVERIFIED_OP), // v232 362
    USER_ADDITIONAL_OPT_UPGRADE_ITEM_USE_REQUEST(363), // v271 (v265 358): 4 2 2 1
    USER_ITEM_SLOT_EXTEND_ITEM_USE_REQUEST(UNVERIFIED_OP), // v265 363 (stamp, 4 2 2): no v271 sender, stamps removed
    USER_ANTI_MACRO_SKILL_USE_REQUEST(UNVERIFIED_OP), // v232 363
    USER_EXCEPTIONAL_ITEM_USE_REQUEST(364), // v271 (v265 359): 4 1 2 1 2 1
    USER_ANTI_MACRO_REFRESH_REQUEST(UNVERIFIED_OP), // v232 364
    USER_EXCEPTIONAL_ENCHANT_REQUEST(365), // v271 (v265 360): B4 0 enhance / B4 1 extract, as v265
    USER_WEAPON_TEMP_ITEM_OPTION_REQUEST(368), // v271 (v265 364): 4 1
    USER_ITEM_SKILL_SOCKET_UPGRADE_ITEM_USE_REQUEST(369), // v271 (v265 365): 4 2 2
    USER_ITEM_SKILL_OPTION_UPGRADE_ITEM_USE_REQUEST(370), // v271 (v265 366): 4 2 2
    USER_FREE_MIRACLE_CUBE_ITEM_USE_REQUEST(371), // v271 (v265 367): sender 0x1433df8a0, 4 tick, 2 cube, 2 equip, 4, 1 1 S
    USER_BONUS_MYSTICAL_CUBE_ITEM_USE_REQUEST(372), // v271 (v265 368): 4 tick, 2 cube, 4 item, 4 equip, 4, 1 1 S
    USER_BONUS_BRIGHT_CUBE_ITEM_USE_REQUEST(373), // v271 (v265 369): 4 tick, 4 item, 2 cube, 2 equip, 4, 1 count, 1 1 S
    USER_EQUIPMENT_ENCHANT_WITH_SINGLE_UI_REQUEST(375), // v271 (v265 371): senders 0x1421f4980 (52) / 0x1421f4b50 (1 / 7) / scroll tab
    USER_SELF_ENCHANT_REQUEST(376), // v271 (v265 372): 4 4 4 B8
    USER_UI_OPEN_ITEM_USE_REQUEST(UNVERIFIED_OP), // was 376 (that is the Grand Sacred Symbol request); no handler
    USER_SELF_ENCHANT_REQUEST_2(377), // v271 (v265 373): 4 4 4 B8
    USER_ARCANE_SYMBOL_REQUEST(378), // v271 (v265 374): send-site alignment, 4 4 | 4 4 4 | 4 4 4 4
    USER_AUTHENTIC_SYMBOL_REQUEST(379), // v271 (v265 375): send-site alignment, same shapes
    USER_GRAND_AUTHENTIC_SYMBOL_REQUEST(380), // v271 (v265 376): send-site alignment, same shapes
    USER_REPAIR_DURABILITY_ALL(UNVERIFIED_OP), // v232 380
    USER_REPAIR_DURABILITY(UNVERIFIED_OP), // v232 381
    USER_BAG_ITEM_USE_REQUEST(382), // v271 (v265 378): send-site alignment, 4 2 4 1
    USER_QUEST_RECORD_SET_STATE(UNVERIFIED_OP), // v232 382
    USER_ITEM_RELEASE_REQUEST(383), // v271 (v265 379): bracket 382/384, no literal sender in either client
    USER_MEMORIAL_CUBE_OPTION_REQUEST(386), // v271 (v265 382): senders 0x141a4d920 (4 tick, 4 pick, 1 1 S) / 0x141a55ed0 (4 4)
    USER_SELECT_PQ_REWARD(UNVERIFIED_OP), // v232 386
    USER_MIRACLE_CIRCULATOR_OPTION_REQUEST(387), // v271 (v265 383): 4 tick, 1 keep new, 4 item, 1 preset
    USER_REQUEST_PQ_REWARD(UNVERIFIED_OP), // v232 387
    USER_TOADS_HAMMER_REQUEST(389), // v271 (v265 385): 2 2 2
    USER_TOADS_HAMMER_HELP_REQUEST(390), // v271 (v265 386)
    // Item Pot: gone from the v265 client (no sender between 438 and 439, its strings unreferenced; the imps say
    // "can't be used now"), kept unnumbered
    USER_REQUEST_CREATE_ITEM_POT(UNVERIFIED_OP), // v232 390
    USER_REQUEST_REMOVE_ITEM_POT(UNVERIFIED_OP), // v232 391
    USER_REQUEST_INC_ITEM_POT_LIFE_SATIETY(UNVERIFIED_OP), // v232 392
    USER_REQUEST_CURE_ITEM_POT_LIFE_SICK(UNVERIFIED_OP), // v232 393
    USER_REQUEST_COMPLATE_TO_ITEM_POT(UNVERIFIED_OP), // v232 394
    USER_SELECT_SOUL_SKILL_UP_REQUEST(396), // v271 (v265 389): 4 1 4 4 4
    USER_CHANGE_SOUL_COLLECTION_REQUEST(398), // v271 Soul Collection window (sender 0x14236dfb0): 4 n, n x (4 inventory type, 4 slot); GMS answers CHANGE_SOUL_COLLECTION_RESULT
    USER_ABILITY_UP_REQUEST(400), // v271 (v265 393): 4 B8
    USER_ABILITY_MASS_UP_REQUEST(401), // v271 (v265 394): 4 4 B8 4
    USER_DOT_HEAL(402), // v265 395; both clients send 4 count + count*(4 stat, 4 amount); the v265 handler only read two ints (count as mask)
    USER_PARTY_REQUEST(UNVERIFIED_OP), // v265 402; v271 402 is USER_DOT_HEAL
    USER_CHANGE_STAT_REQUEST(403), // v271 (v265 396): sender 0x145a660a0, 4 tick 2 hp 2 mp 1 (v265 had a mask)
    USER_CHANGE_STAT_REQUEST_BY_ITEM_OPTION(404), // v271 (v265 397): sender 0x145a66150 from the same recovery tick as 403, 4 tick 2 hp 2 mp (v265 had a mask); item-option recovery every 4 s
    GUILD_ATTEND_MAP_INFO_REQUEST(405), // v271 only: senders 0x14086aab0 / 0x14086ab20, 4 version (answered with Out 2805)
    USER_CHECK_TEMPORARY_STAT_DURATION_REQUEST(UNVERIFIED_OP), // v265 405 (RE 0x145542910: empty, 200 ms throttle); gone in v271: its thunk became 0x145b4d320, which sends the 160-byte expired mask as EXPIRED_TEMP_STAT_NOTICE (413)
    USER_UPDATE_DEBUFF_OBJ(UNVERIFIED_OP), // v232 405
    USER_SKILL_UP_REQUEST(406), // v271 (v265 398): 4 4 4
    USER_DROP_MONEY_REQUEST(407), // v265 399
    USER_GIVE_POPULARITY_REQUEST(408), // v271 (v265 400): sender 0x145a67000, 4 char id, 1 up/down; GMS answers GIVE_POPULARITY_RESULT
    USER_ACTIVATE_PET_REQUEST(411), // v271 (v265 403): sender 0x145a67310, 4 tick 2 slot
    USER_REQUEST_ASWAN_TIME_TABLE_CLIENT_INIT(UNVERIFIED_OP), // v232 411
    USER_REGISTER_PET_AUTO_BUFF_REQUEST(412), // v271 (v265 404): senders 0x145a67880/0x145a67ab0, 4 pet 4 slot 4 skill 1
    USER_PROTECT_BUFF_DIE_ITEM_REQUEST(UNVERIFIED_OP), // v232 412
    EXPIRED_TEMP_STAT_NOTICE(413), // v271 0x145b4d320: 160-byte expired local stat mask (bit = client stat index), exactly EncodeBuf(0xa0) and no trailing int
    USER_PROTECT_BUFF_DIE_MAPLE_POINT_REQUEST(UNVERIFIED_OP), // v232 413
    USER_PROTECT_EXP_DIE_MAPLE_POINT_REQUEST(UNVERIFIED_OP), // v232 413
    USER_PORTAL_SCRIPT_REQUEST(414), // v271 (v265 406): 1 S 2 2
    USER_PORTAL_TELEPORT_REQUEST(417), // v271 (v265 409): send-site alignment, 1 S 2 2 2 2 (seen: "pt_up01")
    USER_CALLING_TELEPORT_REQUEST(418), // v271 (v265 410): send-site alignment, 1 1 4
    USER_MAP_TRANSFER_REQUEST(419), // v271 (v265 411): send-site alignment, 1 1 4
    USER_CLAIM_REQUEST(421), // v271 (v265 413): send-site alignment, 1 S S
    USER_QUEST_REQUEST(422), // v271 (v265 414): 1 type, 4 quest, 4 npc, 2 2 [4]; QUEST_RESULT 12 sends 422 (7, quest)
    USER_MEDAL_REISSUE_REQUEST(423), // v271 (v265 415): send-site alignment, 4 4
    USER_CALC_DAMAGE_STAT_SET_REQUEST(424), // v271 (v265 416): empty, sent by the TEMPORARY_STAT_SET/RESET handlers
    USER_B2_BODY_REQUEST(425), // v271 (v265 417): low alignment, but the packets carry short type + own char id
    USER_THROW_GRENADE(426), // v271 (v265 418): bracket 425/427; v265 body after a new leading int (Bat Swarm, Shadow Spark, Thunder Dive seen)
    USER_DESTROY_GRENADE(427), // v271 (v265 419): send-site alignment, 4 1 4
    USER_CREATE_AURA_BY_GRENADE(428), // v271 (v265 420): send-site alignment, 4 4 2 2 1 1 1
    USER_SET_MOVE_GRENADE(429), // v271 (v265 421): seen as int skill, int, 8 bytes, int, byte
    USER_MACRO_SYS_DATA_MODIFIED(432), // v271 (v265 424): sender 0x143a9bd00 -> 0x1407a1db0, 1 n (<= 5) x macro
    UNK212_437(UNVERIFIED_OP), // v232 432
    USER_CLIENT_TIMER_END_REQUEST(433),
    BROADCAST_MSG(UNVERIFIED_OP), // v232 433
    UNK209_434(UNVERIFIED_OP), // v232 436
    USER_SELECT_NPC_ITEM_USE_REQUEST(438), // v271 (v265 428): send-site alignment, 2 4
    USER_LOTTERY_ITEM_USE_REQUEST(439), // v271 (v265 429): send-site alignment, 2 4 1 4
    USER_ROULETTE_START_REQUEST(440), // v271 (v265 430): send-site alignment, 4
    USER_ROULETTE_RESULT_REQUEST(441), // v271 (v265 431): send-site alignment
    UNK212_443(UNVERIFIED_OP), // v232 441
    USER_ITEM_MAKE_REQUEST(442), // v271 (v265 432): sender 0x144798990 (mov edx, 0x1ba), 4 x 8, 1, 4 4 as v265
/*444 */    USER_CLIENT_RESOLUTION_RESULT(444), // v265 434
    USER_FOLLOW_CHARACTER_REQUEST(445), // v265 435
    EXPEDITION_REQUEST(UNVERIFIED_OP), // v232 445
    USER_FOLLOW_CHARACTER_WITHDRAW(446), // v265 436: sender 0x145a6b040, 1
    PARTY_ADVER_REQUEST(UNVERIFIED_OP), // v232 446
    SET_PASSENGER_RESULT(447), // v265 437
    URSUS_PARTY_REQUEST(UNVERIFIED_OP), // v232 447
    USER_REQUEST_INSTANCE_TABLE(448), // v271 (v265 438): S 4 4; test 15 polled ("hyper", 28, 0) ~1,900 times
    USER_REQUEST_RESPAWN(449), // v271 (v265 439): send-site alignment
    USER_CONSUME_HAIR_ITEM_USE_REQUEST(450), // v271 (v265 440): sender 0x145af71b0, 4 4 2 4 1 B8 as v265
    GUILD_JOIN_REQUEST(UNVERIFIED_OP), // v232 450; GUILD_REQUEST type 3 in v265
    USER_MIX_DYE_ITEM_USE_REQUEST(451), // v271 (v265 441): sender 0x145af8460, as v265; v265 RE 0x1454f44d0: Use-tab mix dye coupons
    GUILD_JOIN_CANCEL_REQUEST(UNVERIFIED_OP), // v232 451; type 4
    USER_FORCE_ATOM_COLLISION(452), // v271 (v265 442): seen after Nether Shield, v265 body plus one int
    GUILD_JOIN_ACCEPT(UNVERIFIED_OP), // v232 452; type 6
    USER_DEBUFF_OBJ_COLLISION(453), // v265 443
    USER_REQUEST_OCCUMPATION_DATA(UNVERIFIED_OP), // v265 453; v271 453 is USER_DEBUFF_OBJ_COLLISION
    GUILD_JOIN_REJECT(UNVERIFIED_OP), // v232 453; type 7
    USER_CREATE_LUCK_OF_THE_DRAW(455), // v265 445
    TOWER_RANK_REQUEST(UNVERIFIED_OP), // v232 455
    USER_UPDATE_PARTY_BUFF_AREA(456), // v265 446 // v232 403; v265 RE medium (empty, Benediction sender)
    UNKNOWN_OPCODE_447(457), // v265 447 // v265 RE 0x144e9a850: int time, on a skill cooldown gate; no handler
    USER_UPDATE_LAPIDIFICATION(459), // v271 (v265 449): int phase 1-4 while petrified, 0 = escape gauge emptied
    USER_REQUEST_CHARACTER_POTENTIAL_SKILL_RAND_VALUE_SET(460), // v271 (v265 450): send-site alignment, 1 4; v271 450 is USER_CONSUME_HAIR_ITEM_USE_REQUEST (send sites: v271 460); v265 RE: Honor reset
    USER_REQUEST_CHARACTER_POTENTIAL_SKILL_RAND_SET_UI(461), // v271 (v265 451): send-site alignment, 1 4 4; v271 451 is USER_MIX_DYE_ITEM_USE_REQUEST (send sites: v271 461); v265 RE: Circulator (Use)
    CLIENT_RESOURCE_ERROR(UNVERIFIED_OP), // v232 461; short-length string, e.g. PlaySE_0x80070057|ERR_DESC||UOL|Sound/...|SEV|15|
    USER_REQUEST_CHARACTER_POTENTIAL_SKILL_CHANGE_PRESET(462), // v271 (v265 452): send-site/ctx alignment; v265 RE: byte preset
    LOAD_ACCOUNT_ID_OF_CHARACTER_FRIEND_REQUEST(UNVERIFIED_OP), // v232 463
    USER_KEY_DOWN_AREA_MOVING(464), // v265 454
    UNKNOWN_OPCODE_464_SINK(UNVERIFIED_OP), // was 464; v271 464 is USER_KEY_DOWN_AREA_MOVING // v271 0x145484660: 1 n, n x 1, 4 context
    USER_CHECK_WEDDING_EX_REQUEST(465), // v265 455
    MEMO_FLAG_REQUEST(UNVERIFIED_OP), // v232 465
    USER_CATCH_DEBUFF_COLLISION(466), // v265 456
    USER_AFFECTED_AREA_CREATED(467), // v271 (v265 457): send-site alignment, 4 4
    USER_AFFECTED_AREA_REMOVED(468), // v271 (v265 458): send-site alignment, 4
    SLIDE_REQUEST(UNVERIFIED_OP), // v232 469
    USER_DAZZLE_HIT(470), // v271 (v265 459)
    USER_MESO_EXCHANGE_REQUEST(471), // v271 (v265 460): send-site/ctx alignment; v265 RE: byte subtype, sent from UIMesoMarket.img (no handler)
    R_P_S_GAME(UNVERIFIED_OP), // v232 472
    USER_GROUP_CHAIR_INVITE_REQUEST(473), // v271 (v265 462): send-site alignment; v265 RE 0x145469080: int target ("You cannot invite a player who has become a giant.")
    G_S_R_P_S_GAME(UNVERIFIED_OP), // v232 473
    USER_GROUP_CHAIR_INVITE_RESPONSE(474), // v271 (v265 463): send-site alignment; v232 421; v265 RE medium (4 4=7/11/12)
    G_S_R_P_S_FORCE_SELECT(UNVERIFIED_OP), // v232 474
    USER_GROUP_CHAIR_REMOVE_REQUEST(475), // v271 (v265 464): send-site alignment; v265 RE 0x145469240: int target ("You cannot eject that player from your Group Chair.")
    MARRIAGE_REQUEST(UNVERIFIED_OP), // v232 475
    USER_GROUP_CHAIR_SIT_REQUEST(476), // v271 (v265 465): send-site alignment; v265 RE 0x145469330: int chair owner ("You cannot sit on a group chair.")
    WEDDING_WISH_LIST_REQUEST(UNVERIFIED_OP), // v232 476
    USER_RENAME_REQUEST(477),
    GUEST_BLESS(UNVERIFIED_OP), // v232 477
    BOOBY_TRAP_ALERT(UNVERIFIED_OP), // v232 478
    ZERO_TAG(479), // v271 (v265 468): send-site alignment, 4 4
    STALK_BEGIN(UNVERIFIED_OP), // v232 479
    ZERO_SHARE_CASH_EQUIP_PART(480), // v271 (v265 469): send-site alignment, 4 1 4
    ALLIANCE_REQUEST(UNVERIFIED_OP), // v232 480
    ZERO_LAST_ASSIST_STATE(481), // v271 (v265 470): send-site alignment; 470 is USER_DAZZLE_HIT
    ALLIANCE_RESULT(UNVERIFIED_OP), // v232 481
    SHOOT_ATTACK_IN_FPS_REQUEST(482), // v271 (v265 471): sender 0x14548a6f0 (opcode 0x1e2) is the v265 0x144ed1780 twin: 4 tick, 2 x, 2 y, 4 n, n x (4 mob, 4 4 1 1 1 2 2 2 2), stride 0x24
    TALK_TO_TUTOR(UNVERIFIED_OP), // v232 482
    USER_LUCKY_ITEM_USE_REQUEST(483), // v271 (v265 472): sender 0x14461c330 emits 0x1e3 (4 tick, 2 slot, 2) behind the same lucky-set scroll guard as the v265 sub_143FE5110 (item 0x1f4384..0x1f43e7, 500 held)
    TALK_TO_PARTNER(UNVERIFIED_OP), // v232 483
    USER_MOB_MOVE_ABILITY_CHANGE(484), // v271 (v265 473): sender 0x14538a960 emits 0x1e4 (4 2) and 923, as the v265 sub_144DCC1F0 emits 473 and 921
    USER_SWITCH_R_P(UNVERIFIED_OP), // v232 484
    USER_DRAGON_ACTION(485), // v271 (v265 474): send-site alignment, 4 4 4 as v265
    USER_DRAGON_BREATH_EARTH_EFFECT(486), // v271 (v265 475): send-site alignment, 4 4 4 4 4 4 as v265
    REQUEST_INC_COMBO(UNVERIFIED_OP), // v232 486
    REQUEST_DEC_COMBO(UNVERIFIED_OP), // v232 487
    UNK227_486(UNVERIFIED_OP), // v232 489
    GROUP_MESSAGE(490), // v271 (v265 479): send-site alignment, same shape
    DEMON_BLOOD_SPILL_REQUEST(UNVERIFIED_OP), // v232 490
    ITEM_LINKED_GROUP_MESSAGE(491), // v271 (v265 480)
    XENON_CORE_OVERLOAD(UNVERIFIED_OP), // v232 491
    WHISPER(493), // v271 (v265 482): types 5 find, 6 whisper as v265
    MOB_CRC_KEY_CHANGED_REPLY(UNVERIFIED_OP), // v232 493
    ITEM_LINKED_WHISPER(494), // v271 (v265 483); v265 RE 0x142e78160 (same sender as WHISPER 482): 1=6 4 S S S + item link
    MESSENGER(495), // v271 (v265 484): send site, medium; not in the allow lists (UI_MESSENGER not ported)
    MINI_ROOM(496), // v271 (v265 485): the trade dialog's own sends (0x1f0); types from 16 on +2 (MiniRoomAction)
    PARTY_REQUEST(498), // v271 (v265 487): types 0-12 as v265, 14+ one higher (PartyHandler)
    PARTY_RESULT(499), // v271 (v265 488): reply types 47-49 = v265 44-46
    PARTY_INVITABLE_SET(500), // v271 (v265 489): send-site alignment, 1
    REQUEST_SESSION_VALUE(UNVERIFIED_OP), // v232 501
    GUILD_REQUEST(502), // v271 (v265 491): types renumbered (GuildRequestType.fromV271); v232 448; int type (GuildRequestType), ~50 client senders; join/accept/reject are types now
    ADMIN(UNVERIFIED_OP), // v265 502; v271 502 is GUILD_REQUEST
    USER_TRANSFER_FREE_MARKET_REQUEST(UNVERIFIED_OP), // v232 502
    GUILD_RESULT(503), // v271 (v265 492); v232 449; int type, str inviter, str self: declining an invitation
    LOG(UNVERIFIED_OP), // v265 503; v271 503 is GUILD_RESULT (v271 LOG is 514 by send site)
    GUILD_CONTENT_RANK_REQUEST(504), // v271 (v265 493); v232 454; byte 1 ranking popup, 5 contents page (0x14280e9d0/ea60)
    RESET_CROSS_HUNTER_ALERT(UNVERIFIED_OP), // v232 512
    CROSS_HUNTER_COMPLETE_REQUEST(UNVERIFIED_OP), // v232 513
    CROSS_HUNTER_SHOP_REQUEST(UNVERIFIED_OP), // v232 514
    FRIEND_REQUEST(516), // v271 (v265 505): account friends only, types renumbered (FriendHandler)
    UNKNOWN_OPCODE_506_SINK(517), // v271 (v265 506) // 0x1455635e0: byte
    UNKNOWN_OPCODE_507_SINK(518), // v271 (v265 507) // 0x145563660: byte
    MEMO_REQUEST(519), // v271 (v265 508), types as v265; v271 senders 0x145a5cde0/0x145a5ceb0/0x145a5d530/0x145ab2c20, first byte = type 5/2/3/4
    ENTER_TOWN_PORTAL_REQUEST(522), // v265 511
    ENTER_RANDOM_PORTAL_REQUEST(523), // v265 512
    USER_DRAGON_LINK_REQUEST(UNVERIFIED_OP), // v232 523
    ENTER_OPEN_GATE_REQUEST(524), // v265 513
    REQUEST_SET_BLESS_OF_DARKNESS(UNVERIFIED_OP), // v265 524; v271 524 is ENTER_OPEN_GATE_REQUEST
    FUNC_KEY_MAPPED_MODIFIED(527), // v271 (v265 516): the 7 senders as in v265
    LINK_SKILL_REQUEST(529), // v271 (v265 518): send-site alignment, 1 | 1 4
    GET_SERVER_TIME(UNVERIFIED_OP), // v232 533
    GET_CHARACTER_POSITION(UNVERIFIED_OP), // v232 534
    REQUEST_SET_HP_BASE_DAMAGE(535), // v265 525
    EVOLVING_REQUEST(UNVERIFIED_OP), // v232 536
    USER_MIXER_REQUEST(UNVERIFIED_OP), // v232 537
    SUMMON_EVENT_REWARD(UNVERIFIED_OP), // v232 538
    MYSTIC_FIELD_MOVE(UNVERIFIED_OP), // v232 539
    ARAN_SKILL_AREA_NOTICE(540), // v271 (v265 530): send-site alignment; int skill, -1: sent with Aran attacks, no reply
    DEBUG_ONLY_COMMAND(UNVERIFIED_OP), // v265 540; v271 540 is ARAN_SKILL_AREA_NOTICE
    YUT_GAME_REQUEST(UNVERIFIED_OP), // v232 540
    VALUE_PACK_REQUEST(UNVERIFIED_OP), // v232 541
/* 546 */    MOB_CRC_DATA_RESULT(543), // v271 (v265 534): send-site alignment
    WAIT_QUEUE_REQUEST(UNVERIFIED_OP), // v232 543
    MAKING_SKILL_REQUEST(544), // v271: sender logs "MakingSkillRequest"; 0 recipe, 1 extract (Alchemy window), 2 fuse
    CHECK_TRICK_OR_TREAT_REQUEST(UNVERIFIED_OP), // v232 544
    MONSTER_FARM_MIGRATE_OUT_REQUEST(UNVERIFIED_OP), // v232 545
    BROADCAST_EFFECT_TO_SPLIT(546), // v265 537
    HALLOWEEN_CANDY_RANKING_REQUEST(UNVERIFIED_OP), // v232 546
    BROADCAST_ONE_TIME_ACTION_TO_SPLIT(547), // v265 538
    USER_REQUEST_STEAL_SKILL(UNVERIFIED_OP), // v265 547; v271 547 is BROADCAST_ONE_TIME_ACTION_TO_SPLIT
    GET_REWARD_REQUEST(UNVERIFIED_OP), // v232 547
    BROADCAST_AFFECTED_EFFECT_TO_SPLIT(548), // v265 539: sender 0x14538bec0, 4 1
    MAPLE_STYLE_BONUS_REQUEST(UNVERIFIED_OP), // v232 548
    MAPLE_STYLE_ADVICE_REQUEST(UNVERIFIED_OP), // v232 549
    MAPLE_STYLE_SET_SCORE_REQUEST(UNVERIFIED_OP), // v232 550
    MENTORING(UNVERIFIED_OP), // v232 550
    MICRO_BUFF_END_TIME(551), // v265 542
    USER_LV_UP_GUIDE_NOTICE(UNVERIFIED_OP), // v265 551; v271 551 is MICRO_BUFF_END_TIME
    GET_LOTTERY_RESULT(UNVERIFIED_OP), // v232 551
    ROOT_ABYSS_ENTER_REQUEST(UNVERIFIED_OP), // v232 552
    USER_REQUEST_SET_STEAL_SKILL_SLOT(553), // v271 (v265 544): sender 0x1455f89b0, 4 4; GMS answers RESULT_SET_STEAL_SKILL
    USER_SET_ITEM_ACTION(UNVERIFIED_OP), // v232 553
    USER_REQUEST_STEAL_SKILL_MEMORY(554), // v265 545
    USER_SET_BITS_CASE(UNVERIFIED_OP), // v232 554
    USER_REQUEST_STEAL_SKILL_LIST(555), // v265 546
    USER_SET_BITS_SLOT(UNVERIFIED_OP), // v232 555
    USER_ANTI_MACRO_QUESTION_RESULT(UNVERIFIED_OP), // v232 556
    RECALL_SUMMON(557), // v271 (v265 548): the v271 function 0x145623570 holds both 557 (0x22d, 4 4 1 ...) and SEND_SUMMON_ATTACK_STATE_CHANGE 992, as the v265 0x145089a30 held 548 and 986; +9 matches REWARD_MOB_LIST_REQUEST 550 = 559
    USER_PINKBEAN_ROLLING(UNVERIFIED_OP), // v232 557
    USER_WIND_ENERGY_REQUEST(UNVERIFIED_OP), // v232 558
    REWARD_MOB_LIST_REQUEST(559), // v271 (v265 550): send-site alignment, 2 4
    USER_EQUIP_SLOT_LEVEL_MINUS_ITEM_USE_REQUEST(561), // v265 552
    USER_REQUEST_FLYING_SWORD_START(562), // v265 553
    BOARD_GAME_REQUEST(564), // v265 555
    BINGO_REQUEST(565), // v265 556
    BINGO_CASSANDRA_REQUEST(566), // v265 557
    ACTION_BAR_REQUEST(567), // v265 558
    USER_DAMAGE_FALLING_CHECK(UNVERIFIED_OP), // v232 567
    USER_REQUEST_SET_OFF_TRINITY(568), // v271 by send-site alignment (high, same encode order), sender 0x1448af260,0x1448af320
    USER_COMPLETE_PERSONAL_SHOP_BUY_CHECK(UNVERIFIED_OP), // v232 568
    USER_HYPER_SKILL_UP_REQUEST(569), // v271 (v265 561): send-site alignment, 4 4
    USER_DAILY_COMMITMENT_CHECK(UNVERIFIED_OP), // v232 569
    USER_HYPER_SKILL_RESET_REQUEST(570), // v271 (v265 562): 4 1 1
    USER_MOB_DROP_MESO_PICKUP(UNVERIFIED_OP), // v232 570
    USER_RUN_ACT_QUEST(UNVERIFIED_OP), // v232 570
    USER_HYPER_STAT_SKILL_UP_REQUEST(571), // v271 (v265 563): 4 4 4 4
    USER_BREAK_TIME_FIELD_ENTER(UNVERIFIED_OP), // v232 571
    JOURNAL_AVATAR_REQUEST(UNVERIFIED_OP), // v232 571
    USER_HYPER_STAT_SKILL_RESET_REQUEST(572), // v271 (v265 564): 4 4
    USER_HYPER_SKILL_PRESET_REQUEST(573), // v271 (v265 565): 4 4
    USER_SET_DRESS_CHANGED_REQUEST(574), // v271 (v265 566): send-site alignment, 1 1 1
    USER_ZERO_SKILL_COOLTIME_REQUEST(UNVERIFIED_OP), // v232 574
    ENTRY_RECORD_REQUEST(575), // v265 567
    SET_MAX_GAUGE(576), // v265 568
    USER_RETURN_EFFECT_RESPONSE(577), // v271 by send-site alignment (high, same encode order), sender 0x143efd1d0
    USER_AFFECTED_AREA_REMOVE_BY_TIME(UNVERIFIED_OP), // v232 578
    // v265 client reports / periodic packets with no server answer (log noise; discard-only)
    UNKNOWN_OPCODE_579_SINK(579), // v271 (v265 571, a login packet with a discard-only handler) // 0x145ace8f0: empty, after a 10 s/10 min + 30 s/30 min timing gate; called from 0x1439afcb0, the login path that also sends CREATE_SECURITY_HANDLE
    REQUEST_FREE_CHANGE_JOB(UNVERIFIED_OP), // v232 579
    USER_REQUEST_CHANGE_MOB_ZONE_STATE(581), // v271 (v265 573): send-site alignment, 4 4 4
    CHANNEL_USER_COUNT_REQUEST(UNVERIFIED_OP), // v232 581
    UN_URSUS_SELECTED_SKILL_LIST(UNVERIFIED_OP), // v232 582
    SOUL_DUNGEON_SYS(UNVERIFIED_OP), // v232 583
    USER_COORDINATION_CONTEST_REQUEST(UNVERIFIED_OP), // v232 584
    REQUEST_RELOGIN_COOKIE(587), // v271 (v265 579): sender 0x1459f17a0 (logout / exit path 0x1459fd330, after In 1963)
    USER_SPIN_OFF_NEW_MODIFY_REQUEST(UNVERIFIED_OP), // v232 587
    // v265 RE client telemetry, ignored
    TELEMETRY_CHAR_NAME_SINK(590), // v271 (v265 582): same bytes // 0x144f21c20: every 10 min, int charId, str name
    SKILL_HELPER_POPUP(UNVERIFIED_OP), // v232 590
    BLOCKBUSTER(UNVERIFIED_OP), // v232 591
    M_TALK_OFFLINE_ACCOUNT_FRIENDS_NAME_REQUEST(UNVERIFIED_OP), // v232 592
    CHARACTER_BURNING(UNVERIFIED_OP), // v232 592
    USER_TOWER_CHAIR_SETTING(UNVERIFIED_OP), // v232 594
    BULLET_BARRAGE_DURATION_EXTENSION_REQUEST(UNVERIFIED_OP), // v232 595
    USER_COMPLETE_MULTI_KILL_COUNT_CHECK(601),
    GRAND_GUARDIAN_REQUEST(UNVERIFIED_OP), // v232 601
    USER_QUICK_MOVE_SCRIPT(602), // v265 594 // v271 0x144a7b270: int entry code (100+), sent when the client allows the entry
    USER_INVENTORY_SHOP_REQUEST(604), // v265 596 // v265 client 0x141871f30: int chr id, int, int x, int y (the inventory's Shop button)
    //    TIME_GATE_REQUEST(562), // ?
    USER_COMPLETE_NPC_SPEECH(605), // v265 597
    USER_COMPLETE_COMBO_KILL_COUNT_CHECK(607), // v271 (v265 599): send-site alignment, 4 4 4; no reply
    USER_COMPLETE_MULTI_KILL_CHECK(609), // v265 600
    USER_SIGN_REQUEST(610),
    QUEST_EX_RUN_ACT(614), // v271 sender 0x145a7c770: 4 quest id; GMS answers quest-ex <id> "RunAct=1"
    USER_REVIVE_REQUEST(UNVERIFIED_OP), // v232 614
    REQUEST_EVENT_LIST(617), // v265 609
    ADD_ATTACK_RESET(619), // v271 (v265 611): sender 0x14161d170 (4), same job gates and post-send shape as v265 0x1415c7750; 611 is emitted from the guild sender 0x145b260b0 instead
    SET_EVENT_NAME_TAG(620), // v265 612
    UNK_232_621(UNVERIFIED_OP), // v232 621
    LIBRARY_START_SCRIPT(629), // v265 621
    UNK232_629(UNVERIFIED_OP), // v232 629; large int-triplet list (count, hash, skill id) or opaque blob; client report
    //
    SPAM_HEADER(UNVERIFIED_OP), // v232 632
    USER_SOUL_EFFECT_REQUEST(633), // v271 (v265 625): 1
    // v271 (v265 627): actor RPCs to the server, sender 0x1466854f0: byte 9, byte, then {u16 len, int object, u16 rpc, args}
    USER_ACTOR_RPC(635),
    BLACK_LIST(636), // v271 (v265 628): send-site alignment, same shape
    QUEST_EX_7966_CLEAR(637), // v271 sender 0x1459f7aa0, empty; GMS answers quest-ex 7966 = ""
    TRY_REGISTER_TELEPORT(640), // v265 632 // v265 RE 0x145041e20: int skill (checks 20031205 Shroud Walk)
    //
    UNK228_643(UNVERIFIED_OP), // v232 643
    UNION_REQUEST(647), // v271 (v265 639): send-site alignment, 1
    TRADE_KING_SHOP_REQ(648), // v265 640 // v265 RE 0x1448c7d60: byte buy (1) / sell (0), int list index, int count
    TRADE_KING_SHOP_INFO_REQ(649), // v265 641 // v265 RE 0x1448c7930: no body, the shop's offer expired
    KEY_DOWN_SKILL_COST(658), // v265 650
    UNKNOWN_OPCODE_661_SINK(661), // v271 0x14545e590 (mob attack hit path 0x1453bb880): 4 0, 4 mob, 4, 4 attack index
    MATRIX_CRAFT_NODESTONE_REQUEST(UNVERIFIED_OP), // v265 661; v271 has no node crafting
    UNKNOWN_OPCODE_662_SINK(662), // v271 0x1453bb880: 4 mob, 4 index, 4, then rects; same hit path
    USER_KEYDOWN_SKILL_STATE(667), // v271 start 0x1455d2ff0: 4 0, 4 skill, 4 x, 4 y; end 0x1455b86a0: 4 1, 4 skill, 4 reason; + the 4-byte tail
    MATRIX_UPDATE_REQUEST(668), // v271: one opcode for the new V matrix, 4 type first (senders 0x1459c79b0..7d40), V271Matrix
    MOD_SPECTRA_ENERGY(670), // v265 663 // v265 RE 0x145549060: int spectra (Ark; checks 155000007/155001008/155101006)
    SUMMONED_ACTION_CHANGE_REQUEST(UNVERIFIED_OP), // v232 671
    USER_IN_BOSS_REVIVE_REQUEST(672), // v271 (v265 665): send-site alignment, 1 1
    GREATER_DARK_SERVANT_SWAP_REQUEST(673), // v271 sender 0x1455b40a0 (Shadow Slide 400041095 / 500061004): 4 skill, 1, 4 x, 4 y
    //
    INHUMAN_SPEED_FORCE_ATOM_REQUEST(674), // v271 (v265 668): sender 0x145604010, 4 4; GMS answers CREATE_FORCE_ATOM
    //
    CRYSTAL_SKILL_REQUEST(676), // v265 670
    DRAGON_UNK218_668(UNVERIFIED_OP), // v232 678
    DRAGON_UNK218_669(UNVERIFIED_OP), // v232 679
    UNKNOWN_OPCODE_675_SINK(681), // v271 (v265 675): send-site alignment (medium), 1208-byte report // T: discard-only
    UNKNOWN_OPCODE_678_SINK(684), // v271 (v265 678): bracket 680=674 / 685=679, 56 bytes // 0x14369d6f0: 14 x B4, mob code
    PASSIVE_SKILL_INFO_UPDATE(UNVERIFIED_OP), // v232 692
    UPDATE_CLIENT_ENVIRONMENT(UNVERIFIED_OP), // v232 693
    DIRECTION_NODE_COLLISION(UNVERIFIED_OP), // v232 696
    RETURN_TELEPORT_DEBUFF(UNVERIFIED_OP), // v232 698
    CHECK_PROCESS(UNVERIFIED_OP), // v232 699
    MEMO_IN_GAME_REQUEST(UNVERIFIED_OP), // v232 699
    USER_CHARACTER_INFO_SELF_REQUEST(702), // v271 (v265 695): send-site alignment; test 15 sent 8 bytes when a window opened
    USER_CHARACTER_INFO_REQUEST(703), // v271 (v265 696): GMS 703 before each 209: int tick, str name
    USER_POTENTIAL_MESO_RESET_REQUEST(705), // v271 (v265 697): sender 0x1433e0520, 4 type (-1 main, -2 bonus), 4 equip, 1 count, 1 1 S
    MIRROR_READING_SELECT_BOOK_REQUEST(UNVERIFIED_OP), // v232 707
    BOSS_UI_REQUEST(708), // v271 (v265 700): sender 0x145b06680 (mov edx 0x2c4) via encoder 0x1403ef320 = v265 0x140342710 (4 1 1 4 1 1)
    LIKE_POINT(UNVERIFIED_OP), // v232 708
    USER10TH_STREAMING_URL_REQUEST(UNVERIFIED_OP), // v232 709
    USER_UPDATE_MAPLE_TV_SHOW_TIME(UNVERIFIED_OP), // v232 710
    MONSTER_PARK_UI_REQUEST(711), // v271 (v265 703), send-site alignment; v265 RE 0x142195d90: B4 (1), B4, int (reference: extreme, type, index)
    B_E_G_I_N__P_E_T(UNVERIFIED_OP), // v265 711; v271 711 is MONSTER_PARK_UI_REQUEST
    REDUCE_DOT_DAMAGE_BASE_BUFF_REQUEST(UNVERIFIED_OP), // v232 711
    MIRROR_STUDY_COMPLETE(UNVERIFIED_OP), // v232 712
    USER_TIME_EVENT_UI_OPEN_REQUEST(UNVERIFIED_OP), // v232 717
    USER_TIME_EVENT_RESULT_REQUEST(UNVERIFIED_OP), // v232 718
    USER_TIME_EVENT_COMPLETE_REQUEST(UNVERIFIED_OP), // v232 719
    UNKNOWN_OPCODE_720_SINK(720), // v271: the field test saw int 0, int 1 at the login screen; no COutPacket ctor in the exe carries 720, so the emitter is indirect
    E_N_D__P_E_T(UNVERIFIED_OP), // v265 720
    USER_TIME_EVENT_PASSED_TIME_SET(UNVERIFIED_OP), // v232 720
    B_E_G_I_N__S_K_I_L_L_P_E_T(UNVERIFIED_OP), // v265 721
    INVASION_SETTING_REQUEST(UNVERIFIED_OP), // v232 721
    USER_FIELD_ATTACK_OBJ_REQUEST_BOARDING(UNVERIFIED_OP), // v232 722
    USER_FIELD_ATTACK_OBJ_REQUEST_GET_OFF(UNVERIFIED_OP), // v232 723
    PET_MOVE(724), // v271 (v265 712): the pet's move path is built with opcode 0x2d4 (pet init 0x143e3c370)
    //MONSTER_BATTLE(714),
    BOSS_ARENA_MATCH_REQUEST(UNVERIFIED_OP), // v232 724
    PET_ACTION(725), // v271 (v265 713): 4 4 1 1 S
    BOSS_ARENA_MIGRATE_REQUEST(UNVERIFIED_OP), // v232 725
    PET_INTERACTION_REQUEST(726), // v271 (v265 714): send-site alignment, 4 1 1 1
    MOB_USER_CONTROL_SKILL_Q_PUSH(UNVERIFIED_OP), // v232 726
    PET_DROP_PICK_UP_REQUEST(727), // v271 (v265 715): send-site alignment, 4 1 4 4 2 2 4 4 4 2 2 4 4
    E_N_D__S_K_I_L_L_P_E_T(UNVERIFIED_OP), // v265 727
    MOB_USER_CONTROL_SKILL_Q_POP(UNVERIFIED_OP), // v232 727
    PET_STAT_CHANGE_ITEM_USE_REQUEST(728), // v271 (v265 716): sender 0x1459b2150, as v265
    B_E_G_I_N__S_U_M_M_O_N_E_D(UNVERIFIED_OP), // v265 728; v271 728 is PET_STAT_CHANGE_ITEM_USE_REQUEST
    MOB_USER_CONTROL_SKILL_FORCED_Q_POP(UNVERIFIED_OP), // v232 728
    PET_UPDATE_EXCEPTION_LIST_REQUEST(729), // v271 (v265 717): send-site alignment, 4 B8 1 4
    PARTY_QUEST_RANKING_REQUEST(UNVERIFIED_OP), // v232 729
    PET_FOOD_ITEM_USE_REQUEST(730), // v271 (v265 718): send-site alignment, 4 4 2 4
    ATTENDANCE_EVENT(UNVERIFIED_OP), // v232 730
    PET_OPEN_SHOP(731), // v271 (v265 719): send-site alignment, 4
    ATTENDANCE_EVENT_REWARD_REQ(UNVERIFIED_OP), // v232 731
    UI_ATTENDANCE_GHOST_CHANGE_REQ(UNVERIFIED_OP), // v232 732
    EVENT_UI_REQ(UNVERIFIED_OP), // v232 733
    SKILL_PET_MOVE(734), // v271 (v265 722): send-site alignment, 4 1
    ATTENDANCE_EVENT_SELECT_REWARD(UNVERIFIED_OP), // v232 734
    SKILL_PET_ACTION(735), // v271 (v265 723): send-site alignment, 4 4 1 1 S
    UNK_232_734(UNVERIFIED_OP), // v232 735
    SKILL_PET_STATE(736), // v271 (v265 724): send-site alignment, 4 1
    SKILL_PET_DROP_PICK_UP_REQUEST(737), // v271 (v265 725): send-site alignment, 4 1 4 2 2 4 4
    SKILL_PET_UPDATE_EXCEPTION_LIST_REQUEST(738), // v271 (v265 726): send-site alignment, 4 1 4
    SUMMONED_MOVE(741), // v271 (v265 729): send sites, 4 + path
    SUMMONED_ATTACK(742), // v271 (v265 730): senders 0x14413eb40 etc. (ctor 0x2e6); new int after the summon id, sub 0x1411d34d0 changed, int before each mob's damage lines
    SUMMONED_HIT(743), // v271 (v265 731): sender 0x144166c50, as v265
    SUMMONED_SKILL(744), // v271 (v265 732): send sites
    SUMMONED_CREATE_SHOOT_OBJ(745), // v271 (v265 733): send sites
    SUMMONED_REMOVE(746), // v271 (v265 734): send sites, 4 4
    E_N_D__S_U_M_M_O_N_E_D(UNVERIFIED_OP), // v265 746; v271 746 is SUMMONED_REMOVE
    UI_SCRIPT_REQUEST(UNVERIFIED_OP), // v232 746
    SUMMONED_ATTACK_PV_P(747), // v271 (v265 735)
    B_E_G_I_N__D_R_A_G_O_N(UNVERIFIED_OP), // v265 747; v271 747 is SUMMONED_ATTACK_PV_P
    TIDE_OF_BATTLE_INCREASE_STACK_REQUEST(UNVERIFIED_OP), // v232 748
    SUMMONED_ACTION(750), // v271 (v265 738): sender 0x144164690, 4 1
    E_N_D__D_R_A_G_O_N(UNVERIFIED_OP), // v265 750; v271 750 is SUMMONED_ACTION
    SUMMONED_ASSIST_ATTACK_DONE(751), // v271 (v265 739): 4 1
    B_E_G_I_N__A_N_D_R_O_I_D(UNVERIFIED_OP), // v265 751; v271 751 is SUMMONED_ASSIST_ATTACK_DONE
    SUMMONED_SKILL_ATTACK(752), // v271 (v265 740): 4 4 4 4 4 1
    MOVE_TO_URSUS_WAITING_FIELD(UNVERIFIED_OP), // v232 752
    SUMMONED_INTERACTION_REQUEST(753), // v271 (v265 741): 4 4 4 4
    SUMMONED_AREA_NOTICE(754), // v271 (v265 742): summon id, int, skill id, int, duration, 4 ints (area); GMS sends no reply
    E_N_D__A_N_D_R_O_I_D(UNVERIFIED_OP), // v271 754 is SUMMONED_AREA_NOTICE
    SUMMONED_END_NOTICE(755), // v271 (v265 743): summon id, when its duration from SUMMONED_AREA_NOTICE runs out; GMS sends no reply
    B_E_G_I_N__F_O_X_M_A_N(UNVERIFIED_OP), // v271 755 is SUMMONED_END_NOTICE
    UNK227_748(UNVERIFIED_OP), // v232 755
    SUMMONED_SECOND_ATOM_ATTACK_REQUEST(756), // v271 (v265 744): 4 4 1
    FOX_MAN_MOVE(UNVERIFIED_OP), // v265 756; v271 756 is SUMMONED_SECOND_ATOM_ATTACK_REQUEST
    E_N_D__F_O_X_M_A_N(758),
    UNK228_758(UNVERIFIED_OP), // v232 759
    DRAGON_MOVE(760), // v271 (v265 748): send-site alignment
    DRAGON_GLIDE(761), // v271 (v265 749): send-site alignment, 4
    B_E_G_I_N__E_V_E_N_T__G_R_O_U_P(UNVERIFIED_OP), // v232 762
    INVITATION_ACCEPT_USER(UNVERIFIED_OP), // v232 763
    ANDROID_MOVE(764), // v271 (v265 752): send-site alignment, 4 + path
    INVITATION_CANCEL_USER(UNVERIFIED_OP), // v232 764
    ANDROID_ACTION_SET(765), // v271 (v265 753): send-site alignment, 1 1
    EVENT_GROUP_LEAVE(UNVERIFIED_OP), // v232 766
    B_E_G_I_N__E_V_E_N_T__G_R_O_U_P__P_A_C_K_E_T(UNVERIFIED_OP), // v232 766
    E_N_D__E_V_E_N_T__G_R_O_U_P__P_A_C_K_E_T(UNVERIFIED_OP), // v232 767
    E_N_D__E_V_E_N_T__G_R_O_U_P(UNVERIFIED_OP), // v232 768
    FOX_MAN_ACTION_SET_USE_REQUEST(769), // v265 757
    B_E_G_I_N__E_V_E_N_T__R_A_N_K_I_N_G(UNVERIFIED_OP), // v232 769
    EVENT_RANKING_OPEN(UNVERIFIED_OP), // v232 770
    QUICKSLOT_KEY_MAPPED_MODIFIED(771), // v271 (v265 759): sender 0x143813a80, 32 ints
    EVENT_RANKING_HELP(UNVERIFIED_OP), // v232 771
    UNKNOWN_OPCODE_760_SINK(772), // v271 (v265 760) // 0x144ebc6a0: 4 4 1 1, skill helper; v271 0x145474ee0 (about 60 callers): id, client tick, 2 bytes; not Quick Move
    E_N_D__E_V_E_N_T__R_A_N_K_I_N_G(UNVERIFIED_OP), // v232 772
    TELEMETRY_HARDWARE_SINK(773), // v271 (v265 761), +12 as its neighbours // 0x142c0d820: GPU name, driver, ...
    B_E_G_I_N__A_F_R_E_E_C_A_T_V(UNVERIFIED_OP), // v232 773
    TELEMETRY_DISPLAY_SINK(774), // v271 (v265 762) // 0x142c0dd60: screen size, doubles
    AFREECA_TV_BROAD_START(UNVERIFIED_OP), // v232 774
    AFREECA_TV_BROAD_STOP(UNVERIFIED_OP), // v232 775
    E_N_D__A_F_R_E_E_C_A_T_V(UNVERIFIED_OP), // v232 776
    EGO_EQUIP_CREATE_UPGRADE_ITEM(UNVERIFIED_OP), // v232 701; v265 779 (0x143fe6a50: int type, after the 2803 confirm prompt), not bound
    USER_LASER_INFO_FOR_REMOTE(782), // v271 (v265 769): send-site alignment, 4 4 1
    USER_SURVEY_REQUEST(UNVERIFIED_OP), // v232 785
    NPS_INFO(UNVERIFIED_OP), // v232 786
    USER__SAD_RESULT_UI__CLOSE(UNVERIFIED_OP), // v232 788
    FREE_LOOK_CHANGE_REQUEST(UNVERIFIED_OP), // v232 789
    EGO_EQUIP_GAUGE_COMPLETE_RETURN(790), // v265 777 // v232 700; v265 RE 0x143fe3530: empty, when the Zero weapon window's gauge runs out
    FREE_LOOK_CHANGE_UI_OPEN_FAILED(UNVERIFIED_OP), // v232 790
    USER_SMART_PHONE_CALL_REQUEST(UNVERIFIED_OP), // v232 791
    EGO_EQUIP_CREATE_UPGRADE_ITEM_COST_REQUEST(793), // v265 780 // v232 702, IDB medium
    USER_JAGUAR_CHANGE_REQUEST(UNVERIFIED_OP), // v232 793
    EGO_EQUIP_TALK_REQUEST(794), // v265 781 // v232 703; v265 RE 0x143fe10c0 button 0x3ef: empty
    FPS_LOG(UNVERIFIED_OP), // v232 794
    EGO_EQUIP_CHECK_UPDATE_ITEM_REQUEST(795), // v271 by send-site alignment (high, same encode order), sender 0x14461beb0
    PACKET_MODIFY_LOG(UNVERIFIED_OP), // v232 795
    INHERITANCE_INFO_REQUEST(796), // v265 783 // v232 705; v265 RE 0x143fe10c0 button 0x3ee: empty
    BATTLE_USER_AVATAR_SELECT(UNVERIFIED_OP), // v232 796
    INHERITANCE_UPGRADE_REQUEST(797), // v271 sender 0x14474e780 (= v265 0x144129590): 4 weapon, 1 1 1, 4, 1 a [4 4 4 4], 1
    BATTLE_USER_ATTACK(UNVERIFIED_OP), // v232 797
    BATTLE_USER_ATTACK_EXPIRE(UNVERIFIED_OP), // v232 798
    BATTLE_USER_ATTACK_POSITION(UNVERIFIED_OP), // v232 799
    BATTLE_USER_HIT(UNVERIFIED_OP), // v232 800
    UNKNOWN_OPCODE_801_SINK(801), // v271 // 0x1459fd2c0: empty, when the 0x1459e5ae0 guard is clear; called from the menu/UI dispatchers 0x1423348f0 and 0x14271c5e0
    BATTLE_USER_HIT_BY_MOB(UNVERIFIED_OP), // v232 801
    FIELD_INIT_NOTICE_789(802), // v271 (v265 789) // 0x14536c3b0: empty, first field entry after migrate (with 846)
    BATTLE_STAT_CORE_REQUEST(UNVERIFIED_OP), // v232 802
    BATTLE_USER_ALIVE(UNVERIFIED_OP), // v232 803
    USER_CONTENTS_BOOK_REQUEST(UNVERIFIED_OP), // v232 804
    REQUEST_ARROW_PLATTER_OBJ(805), // v265 792 // *
    PERFORMANCE_CLIENT_LOGIN(UNVERIFIED_OP), // v232 805
    PERFORMANCE_CLIENT_IN_FIELD(UNVERIFIED_OP), // v232 806
    PING_CHECK_REQUEST__CLIENT_TO_GAME(UNVERIFIED_OP), // v232 807
    PING__CLIENT_TO_GAME(UNVERIFIED_OP), // v232 808
    PLANT_POT_CLICK(UNVERIFIED_OP), // v232 809
    RANDOM_MISSION_REQUEST(UNVERIFIED_OP), // v232 810
    ITEM_COLLECTION_SET_FLAG(UNVERIFIED_OP), // v232 811
    ITEM_COLLECTION_CHECK_COMPLETE(UNVERIFIED_OP), // v232 812
    SELF_STAT_CHANGE_REQUEST(UNVERIFIED_OP), // v232 813
    CASH_BUFF_EVENT_CANCEL(UNVERIFIED_OP), // v232 814
    SURPRISE_MISSION_CLAIM_REWARD_REQUEST(UNVERIFIED_OP), // v232 816
    RESERVE_DAMAGE_PSYCHIC_LOCK(UNVERIFIED_OP), // v232 824
    DO_ACTIVE_PSYCHIC_AREA(UNVERIFIED_OP), // v232 826; psychic areas are gone in v271 (906 is a 4-byte report)
    USER_SET_CUSTOMIZE_EFFECT(828), // v265 815 // *
    USER_CUSTOMIZE_EFFECT_ITEM_MODIFY(829), // v265 816
    USER_GROWTH_HELPER_REQUEST(830), // v271 (v265 817): send-site alignment
    //
    PSYCHIC_OVER_REQUEST(UNVERIFIED_OP), // v232 830; no v265 sender (Mental Overdrive PP is a server tick); 909 is the Psychic Tornado area tick (4 key, 0x1432cb590)
    USER_CONTENTS_MAP_REQUEST(831), // v265 818
    USER_TIMER_REQ(832), // v265 819
    TOUCH_ME_END_REQUEST(UNVERIFIED_OP), // v232 832
    BITE_ATTACK_RESPONSE(UNVERIFIED_OP), // v232 832
    USER_MANNEQUIN(833), // v265 820
    SAVE_URSUS_SKILL(UNVERIFIED_OP), // v232 833
    GET_SAVED_URSUS_SKILL(UNVERIFIED_OP), // v232 834
    USER_NON_TARGET_FORCE_ATOM_ATTACK(835), // v271 (v265 822, run +13): sender 0x1453dc2b0; GMS 835 (Orbital Flame hits): 20 bytes, 4, 4, then the magic-attack body
    URSUS_SHOP_REQUEST(UNVERIFIED_OP), // v232 835
    URSUS_PARTY_MEMBER_LIST(UNVERIFIED_OP), // v232 835
//    USER_BUFFER_FLY_REQ(730),
    USER_RUN_SCRIPT(836), // v271 (v265 823): Legion window senders 0x144f0a300 (0x344, types 17/19 unchanged)
    USER_SHADOW_MOMENTUM_REQUEST(837), // v265 824 // client 0x142e1e090: int skill, int
    BEAST_FORM_WING_OFF(UNVERIFIED_OP), // v232 840
    RESET_AIR_HIT_COUNT_REQUEST(UNVERIFIED_OP), // v232 841
    RW_ACTION_CANCEL(UNVERIFIED_OP), // v232 843
    CHECK_BOSS_PARTY_BY_SCRIPT(844), // v271 (v265 833): send-site alignment, 4 4 4
    MONSTER_COLLECTION_COMPLETE_REWARD_REQ(845), // v271 (v265 834): send-site alignment; v232 753; IDB: the collection UI's sender (type, region, session, group, explore, -1, S, 1), next to EXPLORE's
    RW_CLEAR_CURRENT_ATTACK_REQUEST(UNVERIFIED_OP), // v232 845
    MONSTER_COLLECTION_EXPLORE_REQ(846), // v271 (v265 835): send-site alignment; v232 754
    BOSS_TELEPORT_REQUEST(847), // v271 (v265 836): send-site alignment, 4 4 4 4
    STIGMA_DELIVERY_REQUEST(850), // v271 (v265 839): send-site alignment, 4 4 4; v265 RE high
    FUNCTION_FOOTHOLD_MAN(UNVERIFIED_OP), // v232 851
    UNION_RAID_RETREAT(852), // v271 (v265 841, not 837: the v265 client never emits 837, v265.3 says so): two ints via 0x1449f1ca0 / 0x1459fc7f0, the v265.3 sender pair; +11 fits STIGMA 839 = 850 and SPECIAL_GAME_EXIT 845 = 856
    USER_WORLD_MAP_TELEPORT_REQUEST(853), // v271 0x1459fc880 (world map double-click 0x1458b6a20, navigation 0x1426d5630): 4 field id, sets the request lock
    SPECIAL_GAME_EXIT(856), // v271 (v265 845): exit-button UI 0x1446a0f50 (int type, int field type) and a confirm dialog 0x142721090 (type 12); v265 RE exit-button UI 0x1440710a0: int type (2 Legion raid, 3 Spirit Savior), int current map id
    FIELD_INIT_NOTICE_846(857), // v271 (v265 846) // 0x14536c3b0: empty, right after 789
    LOADED_DICE_SELECTION_RESULT(UNVERIFIED_OP), // v232 861
    UNK227_869(UNVERIFIED_OP), // v232 877
    USER_FLAME_ORB_REQUEST(880), // v271 sender 0x1455b93f0 (Orbital Flame 12001020/12100020/12110020/12120006): 4, 4 skill, 1 slv, 2 dir, 4 4 pos, 1, 4, 1, 4 4 4 4, 1 1, 4 0, 4 dir, 4, 4 n, n x atom
    USER_TELEPORT_ROCK_ALLOWED_SETTING_REQUEST(UNVERIFIED_OP), // v232 885
    ANTI_MACRO_VIOLETTA_RESPONSE(UNVERIFIED_OP), // v232 897
    UNK227_892(UNVERIFIED_OP), // v232 900
    CREATE_PSYCHIC_LOCK(UNVERIFIED_OP), // v265 901; Kinesis psychic grab/areas are gone in v271 (no sender; GMS never sends 901)
    RESET_PATH_PSYCHIC_LOCK(UNVERIFIED_OP), // v265 902; Kinesis psychic grab/areas are gone in v271 (no sender; GMS never sends 902)
    TELEMETRY_FIELD_ENTER_SINK(903), // v271 (v265 893) // 0x1453038b0: int, long (field-enter timing)
    RELEASE_PSYCHIC_LOCK(UNVERIFIED_OP), // v265 903; v271 903 is TELEMETRY_FIELD_ENTER_SINK // v232 823; v265 RE medium; 675 is a 24-byte client report in v265 (T: discard)
    TELEMETRY_FIELD_POS_SINK(904), // v271 (v265 894) // 0x145303940: doubles, longs
    TELEMETRY_895_SINK(905), // v271 (v265 895): bracket, same 20-byte shape
    CREATE_KINESIS_PSYCHIC_AREA(UNVERIFIED_OP), // v265 905; v271 905 is TELEMETRY_895_SINK // v232 825; v265 RE high
    DEBUFF_PSYCHIC_AREA(UNVERIFIED_OP), // v265 907; Kinesis psychic grab/areas are gone in v271 (no sender; GMS never sends 907)
    RELEASE_PSYCHIC_AREA(UNVERIFIED_OP), // v265 908; Kinesis psychic grab/areas are gone in v271 (no sender; GMS never sends 908)
    DEC_PSYCHIC_POINT_REQUEST(UNVERIFIED_OP), // v265 910; Kinesis psychic grab/areas are gone in v271 (no sender; GMS never sends 910)
    PSYCHIC_TORNADO_TICK(912), // v265 909 // v265 RE 0x1432cb590: 4 area key, at most once a second while a Psychic Tornado area lives
    USER_KEY_DOWN_STEP_REQUEST(916), // v271 (v265 915): sender 0x1455e46b0, 4 skill 4 duration
    DAILY_GIFT_REQUEST(917), // v271 (v265 916): sender 0x144534ee0, 4
    // v271 skill windows (0x144b6aeb0, 0x144b94fe0, 0x144bacc40, 0x14473e2b0) send one of three right-click toggles, int skillId
    SKILL_COMMAND_LOCK_ARAN(918), // v271 (v265 917): command-lock list 0x14101d6b0 (Aran, Sia, DS/DA wings, Zero, Ark...), quest-ex 21770 index 0x14101d950
    SKILL_COMMAND_LOCK_ARK(919), // v271: right-click on/off for a fixed skill list (Magic Debris auto mode, 5311013, 11121018, 155141034, 162101012...); int skillId, state in quest-ex 1544
    SKILL_ACTION_TOGGLE(920), // v271: list 0x140eccae0 (Archer/Pathfinder Double Jump "Action Customization", Shadow Jump...); answered by SKILL_ON_OFF_EFFECT
    ARAN_COMBO_ADJUST(923), // v271 (v265 921): ctor 0x14538afc1 inside 0x14538a960; the field test saw one int (FF FF FF FF), the client's combo count
    RELEASE_RW_GRAB(925), // v271 (v265 923): send-site alignment, 4 4
    RW_MULTI_CHARGE_CANCEL_REQUEST(927), // v271 (v265 925): send-site alignment
    UNK232_927(UNVERIFIED_OP), // v232 927; int + long, sent with the damage skin sync on field entry
    DIMENSIONAL_MIRROR_WARP_REQUEST(929), // v271 (v265 927): send-site alignment, 4 4
    UNK232_931(UNVERIFIED_OP), // v232 931; empty, every field entry
    WEAPON_AURA_READY_NOTICE(932), // v271 (v265 930): empty: the aura wave timer is ready
    WEAPON_AURA_TRIGGER_NOTICE(933), // v271 (v265 931): int triggering skill; the wave follows as a shoot attack
    SHOOT_OBJECT_CREATE_REQUEST(934), // v271 sender 0x14560a5f0 (v265 932): new leading int, a byte after the skill-use info; GMS answers Out 1425 (Wild Hunter's Wild Arrow Blast 33001000)
    UNK227_933(UNVERIFIED_OP), // v232 935
    SHOOT_OBJECT_EXPLODE_REQUEST(937), // v271 send site 0x145397a64 (user update): 4 n, n x 4 shoot object id (v265 933)
    RIOTOUS_HEART_USE_NOTICE(940), // v271 0x143fa5320 (v265 938): int skill (Riotous Heart) while sword charges are left; no reply
    JUPTIER_THUNDER_UNK227_943(UNVERIFIED_OP), // v232 945
    SPOTLIGHT_STACK_REQUEST(951), // v265 947 // client 0x144f131d0 (byte on, int count), sent by the Superstar / Triple Color Spotlight counters
    UNION_PRESET_CHANGED(953), // v271 (v265 953): sender 0x143369d90 emits 0x3b9 with int, int count, then the list encode helper, as the v265 0x144621090
    UNION_ASSIGN_REQUEST(955), // v271 (v265 951): send-site alignment, 4 + board
    UNION_OPEN_NOTICE(956), // v271 (v265 952) // 0x14537a6b0: empty, follows UNION_REQUEST from the info window / open-UI packet
    UNION_PRESET_INFO_REQUEST(958), // v271 (v265 954): sender 0x14499d5b0 has the same guard (argument 3000..3003) and encodes the same field (+0x370) as the v265 954 sender; v265 958 is a different, empty packet
    USER_SKILL_INFO(963), // v271 (v265 959): send-site alignment
    ACHIEVEMENT_REQUEST(967), // v265 962 // v265 RE: int type 6 load / 7 refresh / 0, 2 (+int); achievement StringPool messages
    // PROBABLY START 218 SKILLS
    GUIDE_FORCE_ATOM_FOR_REMOTE(969), // v265 964 // v232 881, IDB high
    INCREASE_SKILL_STACK_REQUEST(978), // v271 (v265 973): GMS 978 = 4 stack, 4 skill, 4 -1 (4516x)
    INFERNO_SPHERE_FEATHER_COUNT(988),
    DIVINE_ECHO_EXPIRE_REQUEST(991), // v265 985
    SEND_SUMMON_ATTACK_STATE_CHANGE(992), // v265 986 // v232 899; v265 RE high (Dimensional Sword sender)
    STACK_OVER_TIME_SKILL_INCREASE_REQUEST(994), // v271 (v265 990): no literal send site in either client; same +4 as its neighbours
    LARA_STACK_OVER_TIME_SKILL_INCREASE_REQUEST(996), // v271 (v265 992): send-site alignment, 4 4
    BULLET_BLAST_RELOAD_REQUEST(998), // v271 (v265 994): send-site alignment, 4 1
    UNKNOWN_OPCODE_1007_SINK(1007), // v271 0x145b99400: list report, up to ~950 bytes
    // Beast Tamer
    BEAST_TAMER_REGROUP_REQUEST(1012),
    DEMONIC_BLAST_KEYDOWN_COST(1017), // v265 1013
    UNKNOWN_OPCODE_1017_SINK(UNVERIFIED_OP), // was 1017; v271 1017 is DEMONIC_BLAST_KEYDOWN_COST // v271 0x145a08740: 4 4
    CLIENT_SYNC_COOLTIME_REQUEST(1019), // v271 (v265 1015): sender 0x145b59130, 4 tick 4 n, n x (4 skill 4 ms left)
    V_BLESSING_UPDATE_REQUEST(1020), // v265 1016
    AURA_SKILL_UPDATE_REQUEST(1021), // v271 by send-site alignment (high, same encode order), sender 0x145b566d0
    TELEMETRY_CLIENT_REPORT_SINK(1026), // v271 (v265 1022) // 0x145401ad0: CHAR_JOB / VER_CLIENT report
    CLIENT_DEBUG_REPORT(1027), // v265 1023 // 0x142f50080 / 0x1431fa360: string (e.g. "[NIGHTLORD_SECRET_BOOK ForceAtom Invalid Pos ...")
    EMOTICON_REQUEST(1028), // v265 1024 // v232 929; IDB: the emoticon UI's two senders (1 type, 2 from, 2 to | 1 type, 4 id)
    UNKNOWN_OPCODE_1026_SINK(1030), // v271 (v265 1026) // 0x144f341c0: empty
    UNK232_1031(UNVERIFIED_OP), // v232 1031; 4 bytes (constant per client), every field entry
    NOTICE_SEEN(1037), // v271 sender 0x144577c10 (notice window closed), empty; GMS sets quest-ex 100603 noticeTime
    REVENANT_FURY_UPDATE(1041), // v265 1037 // v232 937, IDB medium
    JUPITER_THUNDER_CREATE_REQUEST(1043), // v271 sender 0x14544e4d0 (refers to 400021094): 4 skill, 4 4 4, rect, 4
    JUPITER_THUNDER_UPDATE_REQUEST(1045), // v271 senders 0x142e03dc0/0x142e04fe0 (the remove 1046 is 0x142e06380): 4 n, n x 8 ints
    JUPITER_THUNDER_REMOVE_REQUEST(1046), // v265 1042
    // END 218 SKILLS
    KAIN_DEATHBLESSING_REQUEST(1054), // v271 (v265 1050): send-site alignment, 4 4; v265 RE 0x144f39a70: int n, n x int mob (called from the skill dispatcher 0x144dcf2c0; medium)
    KAIN_STACK_OVER_TIME_SKILL_INCREASE_REQUEST(1055), // v271 sender 0x1454ca2f0 (Kain skill stack timer): 4 skill, 1 0; v232 953
    LARA_DRAGON_VEIN_READING_REQUEST(UNVERIFIED_OP), // v265 1062; gone in v271 with Dragon Vein Reading
    UNKNOWN_OPCODE_1063_SINK(1063), // v271 0x141ad39d0 and others: 4 type + ints, UI step/skip/done report
    SLASH_COMMAND(UNVERIFIED_OP), // v232 1071
    UNKNOWN_OPCODE_1076_SINK(1076), // v271 0x140c57120: once per run, 4 + 8 + 8
    MOB_DROP_PICK_UP_REQUEST(UNVERIFIED_OP), // v232 1083
    MOB_HIT_BY_OBSTACLE(UNVERIFIED_OP), // v232 1084
    MOB_HIT_BY_OBSTACLE_ATOM(UNVERIFIED_OP), // v232 1085
    MOB_SELF_DESTRUCT(UNVERIFIED_OP), // v232 1087
    MOB_SELF_DESTRUCT_COLLISION_GROUP(UNVERIFIED_OP), // v232 1088
    ADVENTURER_MARK_TARGET_GONE(1089), // v271 sender 0x1454f1090: 4 mark skill, 4 slv, 4 mob object id (the marked mob is gone); GMS answers SET_MOB_ADVENTURER_MARK unset + TEMPORARY_STAT_RESET
    MOB_TIME_BOMB_END(UNVERIFIED_OP), // v232 1092
    MOB_REQUEST_ESCORT_INFO(UNVERIFIED_OP), // v232 1094
    MOB_ESCORT_STOP_END_REQUEST(UNVERIFIED_OP), // v232 1095
    UNKNOWN_OPCODE_1098_SINK(1098), // v271 0x1454d2030: empty, at most once a second
    USER_MEDAL_MODIFIED(1100), // v265 1099 // v265 RE (ref): toggle medal display on/off
    USER_TITLE_MODIFIED(1101), // v265 1100 // v265 RE (ref): toggle title display on/off
    MOB_DAMAGE_SHARE_INFO(UNVERIFIED_OP), // v232 1102
    MOB_DOWN_RESPONSE(UNVERIFIED_OP), // v232 1104
    UNKNOWN_OPCODE_1105_SINK(1105), // v271 // seen once in the field test: 13 bytes, 1 + 4 + 4 + 4 (0A | 69219969h | 4E9 | 4B9) right after GUILD_REQUEST type 34; the only inline sender (0x145a12d30) writes two ints, so this emitter is table-driven and has no name or reply
    UNKNOWN_OPCODE_1109_SINK(1111), // v271 (v265 1109) // 0x144f4ff70: byte
    BONUS_ATTACK_FAILED(1114), // v271 0x145600050: 4 skill, 4 arg (bonus-attack failure for 500001031/33/35); no reply
    USER_OUTFIT_PRESET_CHANGE_REQUEST(1117), // v271 by send-site alignment (high, same encode order), sender 0x1459abf90
    USER_OUTFIT_PRESET_TOGGLE_REQUEST(1118), // v265 1115 // client 0x1453a3bf0: byte (outfit window check box)
    USER_EQUIPMENT_PRESET_CHANGE_REQUEST(1119), // v265 1116 // client 0x1453a3eb0: int tick, byte preset 0..2
    // HEXA Matrix and Union Artifacts: the reference tree's v265.3 values, checked against the client's read order
    PATHFINDER_CURSE_PICK_REQUEST(1123), // v271 (v265 1120): send-site alignment; int curse (arrow keys: 1-3) after Curse Enchant Force
    HEXA_SOL_JANUS_REQUEST(1124), // v271 (v265 1121): send-site alignment, 4
    HEXA_SOL_HECATE_PACTUM_REQUEST(1125), // v271 only, sender 0x14226db70: 4 value, 4 slot (0 Charon command, 1/2 bound skills)
    HEXA_MATRIX_OPERATION(1129), // v271 (v265 1124): all 10 senders as v265
    HEXA_ERDA_CONVERSION(1130), // v271 (v265 1125): | 4 4
    ITEM_USE_COUNT_REQUEST(1131), // v271 only, sender 0x14298f3af: 4 item id, 4 count (the "how many?" box; Sol Erda energy)
    REACTOR_UNK229_1136(UNVERIFIED_OP), // v232 1131
    UNION_ARTIFACT_UPDATE_REQUEST(1132), // v271 (v265 1126): send-site alignment; type 7 on field entry // T value, client read order
    UNION_CHAMPION_REQUEST(1133), // v271 (v265 1127): send-site/ctx alignment; v265 RE (medium): Legion champion UI 0x1421db6c0/de7a0/de8f0: B4 type 0/1/2, 1, [type 0: 8], 0x1421e6d10 list, [type 0: 4 4]
    REACTOR_UNK208_1094(UNVERIFIED_OP), // v232 1133
    USER_QUICK_SLOT_MODIFIED(1135), // v271 (v265 1129): same layout, 63 game-setting keys // T value: game settings (int type, byte count, int key/value pairs)
    B_E_G_I_N__F_I_S_H_I_N_G_Z_O_N_E_P_O_O_L(UNVERIFIED_OP), // v232 1137
    FISHING_INFO(UNVERIFIED_OP), // v232 1138
    FISHING_END(UNVERIFIED_OP), // v232 1139
    E_N_D__F_I_S_H_I_N_G_Z_O_N_E_P_O_O_L(UNVERIFIED_OP), // v232 1140
    B_E_G_I_N__P_E_R_S_O_N_A_L__O_B_J_E_C_T(UNVERIFIED_OP), // v232 1141
    NODESTONE_BULK_USE_REQUEST(1142), // v271 by send-site alignment (high, same encode order), sender 0x1429be000
    DECOMPOSER_REQUEST(UNVERIFIED_OP), // v232 1142
    HEXA_FATE_SHUFFLE_REQUEST(1143), // v271 (v265 1137): send-site alignment, 4
    E_N_D__P_E_R_S_O_N_A_L__O_B_J_E_C_T(UNVERIFIED_OP), // v232 1143
    B_E_G_I_N__E_V_E_N_T__F_I_E_L_D(UNVERIFIED_OP), // v232 1144
    EVENT_START(UNVERIFIED_OP), // v232 1145
    SNOW_BALL_HIT(UNVERIFIED_OP), // v232 1146
    DEMON_LASH_NEXT_SKILL(1147), // v265 1140 // client 0x14507a030/170/3b0 (Demon jobs): 4 next Demon Lash / Awakening slash id (0 = reset); client-side state, needs no reply
    UNKNOWN_OPCODE_1147_SINK(UNVERIFIED_OP), // was 1147; v271 1147 is DEMON_LASH_NEXT_SKILL // v271 0x145614d20: 4 skill, 4 context (Demon job/attack state)
    SNOW_BALL_TOUCH(UNVERIFIED_OP), // v232 1147
    COCONUT_HIT(UNVERIFIED_OP), // v232 1148
    TOURNAMENT_MATCH_TABLE(UNVERIFIED_OP), // v232 1149
    PULLEY_HIT(UNVERIFIED_OP), // v232 1150
    SKILL_ICON_ON_OFF_REQUEST(1151), // v271 sender 0x144b5d840: skills with skillIconOnOff (Akatsuki Samurai...), 4 skill
    E_N_D__E_V_E_N_T__F_I_E_L_D(UNVERIFIED_OP), // v232 1151
    USER_SWITCH_CHARACTER_REQUEST(1152), // v271 (v265 1145): send-site alignment, 4 S 1
    B_E_G_I_N__M_O_N_S_T_E_R__C_A_R_N_I_V_A_L__F_I_E_L_D(UNVERIFIED_OP), // v232 1152
    MAPLE_PLANNER_MODIFIED(1153), // v271 Maple Planner: 4 request, 4 tab, then per request (0: 4 row; 1: [4 row] 4 quest; 5: 4 n, n x 4 checked row); GMS keeps quest-ex 39395/39396/39397
    M_CARNIVAL_REQUEST(UNVERIFIED_OP), // v232 1153
    UNKNOWN_OPCODE_1154_SINK(1154), // v271 // 0x1422ea120 (type 0, the 4 bytes seen in the field test) is called from the UI command dispatcher 0x145988b00; 0x1422e7e40 sends type 1 + 4 date-derived ints
    E_N_D__M_O_N_S_T_E_R__C_A_R_N_I_V_A_L__F_I_E_L_D(UNVERIFIED_OP), // v232 1154
    B_E_G_I_N__D_E_F_E_N_S_E__F_I_E_L_D(UNVERIFIED_OP), // v232 1155
    DEFENSE_GAME_REQUEST(UNVERIFIED_OP), // v232 1156
    QUEST_ALERT_SEEN(1157), // v271 sender 0x1427b3ae0, empty: quest-ex 1099 "qid" equals the quest shown; GMS clears 1099
    E_N_D__D_E_F_E_N_S_E__F_I_E_L_D(UNVERIFIED_OP), // v232 1157
    BOSS_UI_CURSOR_MOVE(1162), // v271 sender 0x14225ab30 (boss selection window): 4 npc, 4 row; GMS remembers it in quest-ex 68317 "cursor=<row>"
    UNION_CHAMPION_RAID_REQUEST(1163), // v271 (v265 1154): send-site/ctx alignment; v265 RE: champion raid; type 0 from field script "clearchampioninfo" (0x14183f650), type 1 + int + byte = tag/replace member (0x1421e8410)
    USER_SKILL_USE_REQUEST(1165), // v271 (v265 1156): ~50 senders 0x48d, same prefix + skill-use info 0x141099180; tail 0x141020f30 lost the int after the string
    UNKNOWN_OPCODE_1157_SINK(1166), // v271 (v265 1157): send-site alignment, 13-byte report
    USER_SKILL_USE_PRE_NOTICE(1167), // v271 senders 0x145649da0 / 0x1455bc690 (v265 1158): sent just before some USER_SKILL_USE_REQUESTs (Illium's Crystalline Wings); GMS sends no reply
    USER_SKILL_CANCEL_REQUEST(1168), // v271 (v265 1159): bracket only (no literal sender), low; the handler reads only the skill id
    USER_SKILL_PREPARE_STOP(1169), // v271 (v265 1160): senders 0x145445b00 etc., 4 1 4 as v265
    C_O_N_T_I_S_T_A_T_E(UNVERIFIED_OP), // v232 1169
    USER_SKILL_PREPARE_START(1170), // v271 (v265 1161): senders 0x1455c2860/0x14564da70; new int first
    B_E_G_I_N__P_A_R_T_Y__M_A_T_C_H(UNVERIFIED_OP), // v232 1170
    USER_SKILL_KEYDOWN_STOP(1171), // v271 (v265 1162): skill id, -1; sent after PREPARE_STOP on key release (GMS sends no reply)
    INVITE_PARTY_MATCH(UNVERIFIED_OP), // v232 1171
    CANCEL_INVITE_PARTY_MATCH(UNVERIFIED_OP), // v232 1172
    URSUS_PARTY_MEMBER_CANDIDATE_REQUEST(UNVERIFIED_OP), // v232 1174
    INTRUSION_FRIEND_CANDIDATE_REQUEST(UNVERIFIED_OP), // v232 1176
    INTRUSION_LOBBY_CANDIDATE_REQUEST(UNVERIFIED_OP), // v232 1177
    UNKNOWN_OPCODE_1178_SINK(1178), // v271 0x145ac91a0: 1, queued message/UI action done
    E_N_D__P_A_R_T_Y__M_A_T_C_H(UNVERIFIED_OP), // v232 1178
    REQUEST_FOOT_HOLD_MOVE(UNVERIFIED_OP), // v232 1180
    UNK221_1143(UNVERIFIED_OP), // v232 1183
    ACT_CHANGE_REACTOR_USE_REQUST(UNVERIFIED_OP), // v232 1184
    USER_ANTI_MACRO_BOMB_REQUEST(UNVERIFIED_OP), // v232 1185
    MAKE_ENTER_FIELD_PACKET_FOR_QUICK_MOVE(UNVERIFIED_OP), // v232 1186
    TIMER_GAUGE_TIMEOUT(UNVERIFIED_OP), // v232 1191
    SCENE_SKIP_REQUEST(1193), // v271 senders 0x1421bbc10/0x1421bbcf0: S the key from SET_IN_GAME_DIRECTION_MODE (GMS "skip_<id>")
    COOK_GAME_REQUEST(UNVERIFIED_OP), // v232 1193
    B_E_G_I_N__R_H_Y_T_H_M_G_A_M_E(UNVERIFIED_OP), // v232 1194
    MAKE_ENTER_FIELD_PACKET_FOR_MIRROR_DUNGEON(UNVERIFIED_OP), // v232 1195
    RHYTHM_GAME_HIT_NOTE(UNVERIFIED_OP), // v232 1195
    LEAVE_MIRROR_DUNGEON(UNVERIFIED_OP), // v232 1196
    RHYTHM_GAME_START_REGION(UNVERIFIED_OP), // v232 1196
    PEACEMAKER_TRAVEL_CONTACT_COUNT(UNVERIFIED_OP), // v232 1197
    //RHYTHM_GAME_END_REGION(1134),
    //RHYTHM_GAME_END_SPECIAL_ATTACK(1135),
    //RHYTHM_GAME_SELECT_MODE(1104),
    //RHYTHM_GAME_ATTACK_REQUEST(1105),
    RHYTHM_GAME_NPC_ACTION_REQUEST(UNVERIFIED_OP), // v232 1201
    ILLIUM_RESONANCE_LINK(UNVERIFIED_OP), // v232 1202
    //RHYTHM_GAME_EXIT_GAME(1119),
    E_N_D__R_H_Y_T_H_M_G_A_M_E(UNVERIFIED_OP), // v232 1203
    FIELD_ENTER_INT_NOTICE(1204), // v271 0x14193ae60, new (no v265 sender): int = the client's MobPatternSystem hotfix version (pattern object +0x94; 0 = none), field entry and after Out 679; PatternHotfixHandler
    B_E_G_I_N__M_O_U_N_T_A_I_N_R_I_D_I_N_G(UNVERIFIED_OP), // v232 1204
    OPENED_RECORD_UPDATE(1205), // v271 senders 0x144d32ba0/0x144d330b0: 4 1, S reset time, S opened list ("-1" = none); GMS keeps quest-ex 102810 "resetTime=..;opened=.."
    MOUNTAIN_RIDING_HP_INFO(UNVERIFIED_OP), // v232 1205
    MOUNTAIN_RIDING_MOB_INFO(UNVERIFIED_OP), // v232 1206
    E_N_D__M_O_U_N_T_A_I_N_R_I_D_I_N_G(UNVERIFIED_OP), // v232 1207
    B_E_G_I_N__P_I_R_A_N_H_A(UNVERIFIED_OP), // v232 1208
    PIRANHA_INFO(UNVERIFIED_OP), // v232 1209
    PIRANHA_DIE(UNVERIFIED_OP), // v232 1210
    PIRANHA_DAMAGE(UNVERIFIED_OP), // v232 1211
    PIRANHA_PREPARE(UNVERIFIED_OP), // v232 1212
    E_N_D__P_I_R_A_N_H_A(UNVERIFIED_OP), // v232 1213
    GACHAPON_REQUEST(1214), // v265 1174 // v265 RE: Gachapierrot window 0x14409bae0 (1=0, 4 ticket) / 0x14409bdf0 (1=1); 1034 is a 2020 event UI
    B_E_G_I_N__R_O_B_B_I_N_S(UNVERIFIED_OP), // v232 1214
    POTION_POT_USE_REQUEST(1215), // v265 1175 // v265 RE 0x14540d5d0: 4 tick, 2 slot, 4 pot item (5820000-5820999); drink from the pot
    USER_BEAUTY_DATA_REQUEST(UNVERIFIED_OP), // v265 1215 (Beauty Album); its v265 senders (4 B1 4 ...) have no v271 counterpart
    ROBBINS__INC_BOMB(UNVERIFIED_OP), // v232 1215
    POTION_POT_ADD_REQUEST(1216), // v265 1176 // v265 RE 0x14540d760: 4 tick, 2 slot, 4 potion item, 4 quantity (Potion Pot window)
    ROBBINS__DEC_BOMB(UNVERIFIED_OP), // v232 1216
    POTION_POT_OPTION_REQUEST(1217), // v265 1177 // v265 RE 0x1443f8750: 1 HP box, 1 MP box (client also keeps them under keys 5441/5442)
    ROBBINS__TOSS_BOMB(UNVERIFIED_OP), // v232 1217
    POTION_POT_INCREASE_REQUEST(1218), // v271 by send-site alignment (high, same encode order), sender 0x1449f0800
    E_N_D__R_O_B_B_I_N_S(UNVERIFIED_OP), // v232 1218
    FAMILIAR_ADD_REQUEST(1219), // v271 (v265 1179): sender 0x145a18fa0, 4 tick, 2 slot, 4 item, 4 count
    B_E_G_I_N__F_A_L_L_I_N_G_S_T_O_N_E(UNVERIFIED_OP), // v232 1219
    FAMILIAR_SUMMON_REQUEST(1220), // v271 (v265 1180): 0x14551c4e0, one byte
    FALLING_STONE_INFO(UNVERIFIED_OP), // v232 1220
    // v271 (v265 1182): 1 type; 0-5 as v265, 12 = 4 preset (select), 13 = 4 preset, B12 (that preset's familiars),
    // 14 = 1 slot, 4 badge (255 = none; current preset), 15 = Mystic Frontier (v265 12)
    FAMILIAR_EDIT_REQUEST(1221),
    FALLING_STONE_HP_INFO(UNVERIFIED_OP), // v232 1221
    FAMILIAR_SHOP_OPEN_REQUEST(1222), // v271 (v265 1183): empty (senders 0x1412c34b0 / 0x1464592f0)
    FALLING_STONE_PREPARE(UNVERIFIED_OP), // v232 1222
    E_N_D__F_A_L_L_I_N_G_S_T_O_N_E(UNVERIFIED_OP), // v232 1223
    B_E_G_I_N__B_A_T_T_L_E_F_I_E_L_D(UNVERIFIED_OP), // v232 1224
    BATTLE_FIELD_DAMAGE(UNVERIFIED_OP), // v232 1225
    BATTLE_FIELD_SHEEP_REVIVAL_REQ(UNVERIFIED_OP), // v232 1226
    BATTLE_FIELD_SHEEP_REVIVE(UNVERIFIED_OP), // v232 1227
    E_N_D__B_A_T_T_L_E_F_I_E_L_D(UNVERIFIED_OP), // v232 1228
    B_E_G_I_N__B_O_S_S_A_R_E_N_A(UNVERIFIED_OP), // v232 1229
    BOSS_ARENA_SELECT_BOSS(UNVERIFIED_OP), // v232 1230
    BOSS_ARENA_SELECT_SKILL(UNVERIFIED_OP), // v232 1231
    BOSS_ARENA_SELECT_DIFFICULTY(UNVERIFIED_OP), // v232 1232
    BOSS_ARENA_SELECT_CONFIRM(UNVERIFIED_OP), // v232 1233
    GUILD_BBS(1234), // v271 (v265 1195): literal 0x4d2 in the board senders 0x1418ccf00..0x1418ce1e0; v232 1001; v265 RE medium (types 0/1/2/4/5/6/8, mgp_newArticle UI); 1157 is a periodic 13-byte client report in v265 (T: no handler)
    E_N_D__B_O_S_S_A_R_E_N_A(UNVERIFIED_OP), // v232 1234
    B_E_G_I_N__C_A_T_A_P_U_L_T(UNVERIFIED_OP), // v232 1235
    CATAPULT__UPGRADE_SKILL(UNVERIFIED_OP), // v232 1236
    E_N_D__C_A_T_A_P_U_L_T(UNVERIFIED_OP), // v232 1237
    FIELD_ATTACK_OBJ_PUSH_ACT(UNVERIFIED_OP), // v232 1238
    CASH_SHOP_SURPRISE_ITEM_OPEN(1239), // v271 (v265 1200): senders 0x14203fb10 / 0x145796cd0, long SN
    FIELD_RESPAWN(UNVERIFIED_OP), // v232 1239
    B_E_G_I_N__S_O_U_L__D_U_N_G_E_O_N(UNVERIFIED_OP), // v232 1240
    SOUL_ITEM_USE(UNVERIFIED_OP), // v232 1241
    SOUL_ITEM_USE_VOTE(UNVERIFIED_OP), // v232 1242
    SOUL_DUNGEON_SUMM_SOUL_BOSS(UNVERIFIED_OP), // v232 1243
    E_N_D__S_O_U_L__D_U_N_G_E_O_N(UNVERIFIED_OP), // v232 1244
    B_E_G_I_N__S_P_A_C_E_M_O_O_N_R_A_B_B_I_T(UNVERIFIED_OP), // v232 1245
    SPACE_MOON_RABBIT_HIT_EFF(UNVERIFIED_OP), // v232 1246
    SPACE_MOON_RABBIT_DAMAGE(UNVERIFIED_OP), // v232 1247
    SET_USER_CUSTOM_TITLE_REQUEST(1248), // v232 1069, IDB medium
    E_N_D__S_P_A_C_E_M_O_O_N_R_A_B_B_I_T(UNVERIFIED_OP), // v232 1248
    UPDATE_USER_CUSTOM_TITLE_REQUEST(UNVERIFIED_OP), // v265 1249 (0x14413e990: int slot); v271 1249 is an empty request, sender not found
    B_E_G_I_N__H_U_N_D_R_E_D_B_I_N_G_O(UNVERIFIED_OP), // v232 1249
    H_BINGO_CHECK_NUMBER(UNVERIFIED_OP), // v232 1250
    H_BINGO_CHECK_BINGO(UNVERIFIED_OP), // v232 1251
    E_N_D__H_U_N_D_R_E_D_B_I_N_G_O(UNVERIFIED_OP), // v232 1252
    B_E_G_I_N__T_Y_P_I_N_G_G_A_M_E(UNVERIFIED_OP), // v232 1253
    TYPING_GAME_CHECK_MSG(UNVERIFIED_OP), // v232 1254
    TYPING_GAME_WORD_MOB_ATTACK(UNVERIFIED_OP), // v232 1255
    TYPING_GAME_LEVEL_SELECT(UNVERIFIED_OP), // v232 1256
    TYPING_GAME_SKILL_ATTACK(UNVERIFIED_OP), // v232 1257
    TYPING_GAME_FIELD_OUT(UNVERIFIED_OP), // v232 1258
    E_N_D__T_Y_P_I_N_G_G_A_M_E(UNVERIFIED_OP), // v232 1259
    B_E_G_I_N__H_U_N_D_R_E_D_O_X_Q_UI_Z(UNVERIFIED_OP), // v232 1260
    H_OX_QUIZ_SELECTED(UNVERIFIED_OP), // v232 1261
    E_N_D__H_U_N_D_R_E_D_O_X_Q_UI_Z(UNVERIFIED_OP), // v232 1262
    FAMILIAR_MOVE(1263), // v271 (v265 1227): 0x145820290, 1 idx, 4 familiar, path (with key pads), same calls as v265
    B_E_G_I_N__S_T_A_R_P_L_A_N_E_T_R_P_S(UNVERIFIED_OP), // v232 1263
    FAMILIAR_ATTACK(1264), // v271 (v265 1228): 0x143443f70, 1 4 1 1 4 4 4 8 as v265
    S_P_RPS_GAME_USER_SELECT(UNVERIFIED_OP), // v232 1264
    FAMILIAR_SKILL(1265), // v271 (v265 1229): 0x143444680, 1 4 1 4 4 as v265
    S_P_RPS_GAME_ANSWER(UNVERIFIED_OP), // v232 1265
    S_P_RPS_GAME_LEAVE(UNVERIFIED_OP), // v232 1266
    E_N_D__S_T_A_R_P_L_A_N_E_T_R_P_S(UNVERIFIED_OP), // v232 1267
    B_E_G_I_N__S_I_D_E_S_C_R_O_L_L_F_L_Y_S_H_O_O_T_I_N_G(UNVERIFIED_OP), // v232 1268
    SSFS_HIT_USER(UNVERIFIED_OP), // v232 1269
    SSFS_LETHAL_ATTACK(UNVERIFIED_OP), // v232 1270
    SSFS_EXIT(UNVERIFIED_OP), // v232 1271
    E_N_D__S_I_D_E_S_C_R_O_L_L_F_L_Y_S_H_O_O_T_I_N_G(UNVERIFIED_OP), // v232 1272
    B_E_G_I_N__G_H_O_S_T_P_A_R_K(UNVERIFIED_OP), // v232 1273
    GHOST_PARK_USE_RUNE_REQ(UNVERIFIED_OP), // v232 1274
    E_N_D__U_S_E_R(1275),
    GHOST_PARK_RUNE_HEAL_REQ(UNVERIFIED_OP), // v232 1275
    B_E_G_I_N__F_I_E_L_D(1276),
    GHOST_PARK_HIT_BULLET(UNVERIFIED_OP), // v232 1276
    B_E_G_I_N__L_I_F_E_P_O_O_L(1277),
    E_N_D__G_H_O_S_T_P_A_R_K(UNVERIFIED_OP), // v232 1277
    B_E_G_I_N__M_O_B(1278),
    B_E_G_I_N__F_I_E_L_D__U_R_U_S(UNVERIFIED_OP), // v232 1278
    URSUS_SAVE_DEADLY_USER(UNVERIFIED_OP), // v232 1279
    E_N_D__F_I_E_L_D__U_R_U_S(UNVERIFIED_OP), // v232 1280
    FIELD_SKILL_USE_REQUEST(UNVERIFIED_OP), // v232 1281
    FIELD_WEATHER_MSG(UNVERIFIED_OP), // v232 1282
    FIELD_STAT_QR_EX_DAY(UNVERIFIED_OP), // v232 1283
    B_E_G_I_N__P_O_L_O_F_R_I_T_O_B_O_U_N_T_Y_H_U_N_T_I_N_G(UNVERIFIED_OP), // v232 1284
    POLO_FRITO_BOUNTY_HUNTING_REQUEST(UNVERIFIED_OP), // v232 1285
    RAIL_RUSH_END_REQUEST(UNVERIFIED_OP), // v232 1286
    E_N_D__P_O_L_O_F_R_I_T_O_B_O_U_N_T_Y_H_U_N_T_I_N_G(UNVERIFIED_OP), // v232 1287
    B_E_G_I_N__P_O_L_O_F_R_I_T_O_T_O_W_N_D_E_F_E_N_S_E(UNVERIFIED_OP), // v232 1287
    POLO_FRITO_TOWN_DEFENSE_REQUEST(UNVERIFIED_OP), // v232 1288
    E_N_D__P_O_L_O_F_R_I_T_O_T_O_W_N_D_E_F_E_N_S_E(UNVERIFIED_OP), // v232 1289
    LOTUS_FOOTHOLD_OBJECT_FALL_ACK(UNVERIFIED_OP), // v232 1292
    ERDA_LINK_REQUEST(1298), // v265 1267 // v265 RE 0x14211dbb0 (type 0 activate) / 0x1421203b0 (type 1 enhance): B4 type, 4, 4 stone, B4 category, [type 0 cat 3/4: 4 ints] [type 1: 4 from, 4 to]
    E_N_D__F_I_E_L_D(UNVERIFIED_OP), // v232 1301
    MOB_MOVE(1313), // v271 (v265 1279): sender writes as v265; path in the v271 grammar (MovementInfo)
    MOB_APPLY_CTRL(1314), // v271 (v265 1280): 4 4 4
    CHECK_S_P_W_CREATE_NEW_CHARACTER(UNVERIFIED_OP), // v232 1317
    MOB_HIT_BY_MOB(1318), // v271 (v265 1284): send-site alignment, 4 4 4
    B_E_G_I_N__P_O_L_O_F_R_I_T_O_C_O_U_R_T_S_H_I_P_D_A_N_C_E(UNVERIFIED_OP), // v232 1318
    SET_S_P_W(UNVERIFIED_OP), // v232 1318
    POLLO_FRITTO_COURTSHIP_DANCE_RESULT(UNVERIFIED_OP), // v232 1319
    E_N_D__M_O_B(1320),
    E_N_D__P_O_L_O_F_R_I_T_O_C_O_U_R_T_S_H_I_P_D_A_N_C_E(UNVERIFIED_OP), // v232 1320
    B_E_G_I_N__N_P_C(1321), // *
    B_E_G_I_N__B_O_S_S__D_E_M_I_A_N__F_L_Y_I_N_G_S_W_O_R_D(UNVERIFIED_OP), // v232 1321
    MOB_ATTACK_MOB(1322), // v271 (v265 1288 MobAttackMobRequest, v232 1090): sender 0x143c0aa90 emits 4 4 4 1 1 8 1 4 4 2 2 1; between HIT_BY_MOB 1318 and SKILL_DELAY_END 1323 (+34)
    MOB_SKILL_DELAY_END(1323), // v271 (v265 1289): send-site alignment, 4 4 4 1 4 [4]
    NPC_SPECIAL_ACTION(UNVERIFIED_OP), // v265 1323; v271 1323 is MOB_SKILL_DELAY_END
    E_N_D__N_P_C(1324),
    DEMIAN_OBJECT_ERR__RECREATE(UNVERIFIED_OP), // v232 1324
    MOB_ESCORT_COLLISION(1325), // v271 (v265 1291): send-site alignment, 4 4
    E_N_D__L_I_F_E_P_O_O_L(UNVERIFIED_OP), // v265 1325; v271 1325 is MOB_ESCORT_COLLISION
    E_N_D__B_O_S_S__D_E_M_I_A_N__F_L_Y_I_N_G_S_W_O_R_D(UNVERIFIED_OP), // v232 1325
    B_E_G_I_N__D_R_O_P_P_O_O_L(1326),
    MOB_AREA_ATTACK_DISEASE(1328), // v271 (v265 1294): send-site alignment, 4 4 4 4 4
    E_N_D__D_R_O_P_P_O_O_L(UNVERIFIED_OP), // v265 1328; v271 1328 is MOB_AREA_ATTACK_DISEASE
    MOB_EXPLOSION_START(1329), // v271 (v265 1295): send-site alignment, 4 4 4
    B_E_G_I_N__R_E_A_C_T_O_R_P_O_O_L(UNVERIFIED_OP), // v265 1329; v271 1329 is MOB_EXPLOSION_START
    MOB_LIFTING_END(1330), // v271 (v265 1296 MobLiftingEndRequest, v232 1098): 4 mob object id; sender 0x143bad680, the +34 shift fits the v265 neighbours (1329 = 1295, 1332 = 1298)
    MOB_UPDATE_FIXED_MOVE_DIR(1331), // v271 (v265 1297 MobUpdateFixedMoveDirRequest, v232 1099): 4 mob object id; sender 0x143c29bd0, the +34 shift fits the v265 neighbours (1329 = 1295, 1332 = 1298)
    MOB_CREATE_FIRE_WALK(1332), // v271 (v265 1298): send-site alignment, 4 4 4 2 2
    MOB_AFTER_DEAD_REQUEST(1333), // v271 (v232 1101): between CREATE_FIRE_WALK 1332 and CREATE_AFFECTED_AREA 1335, as in v232; 4 mob
    MOB_CREATE_AFFECTED_AREA(1335), // v271 (v265 1301): send-site alignment, 4 1
    VERUS_HILLA_RED_THREAD_CATCH(1339), // v271 (v265 1305): send-site alignment, 4 4 4 2 2 1
    E_N_D__R_E_A_C_T_O_R_P_O_O_L(1340),
    // Castle Column
    CASTLE_COLUMN_PLACE(UNVERIFIED_OP), // v232 1345
    CASTLE_COLUMN_PLACE_2(UNVERIFIED_OP), // v232 1346
    CASTLE_COLUMN_RETREAT(UNVERIFIED_OP), // v232 1347
    MOB_FREEZE_EFFECT_ACK(1350), // v271 (v265 1316): send-site alignment, 4 1
    MOB_REQUEST(1351), // v271 (v265 1317): 4 mob, 4 subtype, ...; 0x0e = pattern use request (0x141fd62a0), answered by Out 1827 subtype 0x0e; MobPatternHandler
    NPC_MOVE(1359), // v265 1322
    DROP_PICK_UP_REQUEST(1364), // v271 (v265 1327): bracket 1360=1323 / 1367=1330 with no gap; no plain sender in either client, layout as v265
    REACTOR_HIT(1367), // v271 (v265 1330): send-site alignment, 4 4 2 4
    REACTOR_CLICK(1368), // v271 (v265 1331): send-site alignment, 4
    REACTOR_RECT_IN_MOB(1369), // v271 (v265 1332): send-site alignment, 4 4
    REACTOR_KEY(1371), // v271 (v265 1334): send-site alignment, 4 4 2
    //REACTOR_UNK208_1095(1139),
    REACTOR_TOUCH(1372), // v271 sender 0x143ee08a0: 4 reactor object id, 2 (GMS: Midnight Chaser furniture search)
    REACTOR_GATHER(1374), // v271 (v265 1337): send-site alignment, 4; v265 RE 0x144ee0310: int reactor object id (sent from the user key handler 0x144dda480)
    UNKNOWN_OPCODE_1342_SINK(1379), // v271 (v265 1342) // 0x142e67390: empty, on map transitions
    B_E_G_I_N__B_L_A_C_K__M_A_G_E(UNVERIFIED_OP), // v232 1402
    BLACK_MAGE_ORB_HIT(UNVERIFIED_OP), // v232 1403
    E_N_D__B_L_A_C_K__M_A_G_E(UNVERIFIED_OP), // v232 1404
    PARTY_MEMBER_CANDIDATE_REQUEST(1410), // v271 (v265 1373): empty, party search "find members"; GMS answers Out 213
    PARTY_CANDIDATE_REQUEST(1412), // v271 (v265 1375): empty, party search "find a party"; GMS answers Out 216
    GATHER_REQUEST(1417), // v271 (v265 1380): send-site alignment, 4; v265 RE 0x144ec0550: int object id (user key handler 0x144dda480)
    GATHER_END_NOTICE(1418), // v271 (v265 1381): send-site alignment, 4; v265 RE 0x144ebfbd0: int (reactor oid), in the professions/herbalism effect code; follows GATHER_REQUEST 1380
    RUNE_STONE_USE_REQ(1420), // v271 (v265 1383): send-site alignment, 4 4
    RUNE_STONE_END_NOTICE(1421), // v271 (v265 1384): send-site alignment, same sequence; sent after the arrows
    RUNE_STONE_ARROW_INPUT(1422), // v271 (v265 1385): send-site alignment, 4 4
    OBSTACLE_ATOM_COLLISION(1423), // v271 (v265 1386): send-site alignment, 4 4 4 4 4; v265 RE medium
    FALLING_CATCHER_HACK_CHECK(1425), // v265 1388 // v232 1192; v271 sender 0x1434407a0 (v265 0x142e536a0): byte caught, int n, n x int other users under it
    FPS_MODE_EXIT_REQUEST(1427), // v265 1390 // v265 RE 0x142fc6680: no body, ESC + "Forfeit the mission?" in the Eagle Hunt field
    MONSTER_PYRAMID_REQUEST(UNVERIFIED_OP), // v232 1427
    PEACEMAKER_EXPLOSION_CONTACT_COUNT(1432), // v265 1395 // v265 RE 0x144f180f0: 4 skill 4 4 B16 rect 4 4 (caller checks 400021077)
    GOLLUX_MINI_MAP_UPDATE_REQUEST(UNVERIFIED_OP), // v232 1476
    GOLLUX_UNK223_1419(UNVERIFIED_OP), // v232 1477
    GOLLUX_MINI_MAP_LEAVE_REQUEST(UNVERIFIED_OP), // v232 1478
    ESFERA_GUARDIAN_REQUEST(1485), // v271 GMS: int type (1 fire), int chr id, int (5, 4 on the last shot), byte 1
    CASH_SHOP_CHARGE_PARAM_REQUEST(UNVERIFIED_OP), // v232 1512
    CASH_SHOP_MEMBER_SHOP_REQUEST(UNVERIFIED_OP), // v232 1517
    CASH_SHOP_GIFT_MATE_INFO_REQUEST(UNVERIFIED_OP), // v232 1518
    CASH_SHOP_SEARCH_LOG(UNVERIFIED_OP), // v232 1519
    CASH_SHOP_COODINATION_REQUEST(UNVERIFIED_OP), // v232 1520
    CASH_SHOP_CHECK_MILEAGE_REQUEST(UNVERIFIED_OP), // v232 1521
    CASH_SHOP_NAVER_USAGE_INFO_REQUEST(UNVERIFIED_OP), // v232 1522
    MVP_DAILY_PACK_REQUEST(UNVERIFIED_OP), // v232 1523
    VERUS_HILLA_SOUL_ALTAR_REQUEST(1524), // v271 (v265 1487): send-site alignment, 4; v265 RE (boss-mech): 0x1440ee040 from the VH field class, int 9
    MVP_SPECIAL_PACK_REQUEST(UNVERIFIED_OP), // v232 1524
    EPIC_DUNGEON_ALTAR_REQUEST(1525), // v271 (v265 1488), send-site alignment; client 0x1423494d0: no body, standing on the High Mountain heal altar
    CASH_SHOP_BUTTON_PRESS(UNVERIFIED_OP), // v232 1530
    E_N_D__C_A_S_H_S_H_O_P(UNVERIFIED_OP), // v232 1531
    UNK216_1471(UNVERIFIED_OP), // v232 1532
    UNK227_1500(UNVERIFIED_OP), // v232 1533
    B_E_G_I_N__R_A_I_S_E(UNVERIFIED_OP), // v232 1536
    RAISE_REFRESH(UNVERIFIED_OP), // v232 1537
    RAISE_UI_STATE(UNVERIFIED_OP), // v232 1538
    RAISE_INC_EXP(UNVERIFIED_OP), // v232 1539
    RAISE_ADD_PIECE(UNVERIFIED_OP), // v232 1540
    E_N_D__R_A_I_S_E(UNVERIFIED_OP), // v232 1541
    SEND_MATE_MAIL(UNVERIFIED_OP), // v232 1542
    REQUEST_GUILD_BOARD_AUTH_KEY(UNVERIFIED_OP), // v232 1543
    REQUEST_CONSULT_AUTH_KEY(UNVERIFIED_OP), // v232 1544
    DEMIAN_OBJECT_MAKE_ENTER_ACK(1545), // v271 (v265 1508): send-site alignment, 4 1 [4 ...]; v265 RE 0x142fb1110 (reply to out 2012): int sword oid, byte 0 / byte 1 + int + (2 2 B8); no handler
    REQUEST_CLASS_COMPETITION_AUTH_KEY(UNVERIFIED_OP), // v232 1545
    DEMIAN_OBJECT_NODE_END(1546), // v271 (v265 1509): send-site alignment, 4 2 2 B8 B8; v265 RE high
    REQUEST_WEB_BOARD_AUTH_KEY(UNVERIFIED_OP), // v232 1546
    IRBOX_EVENT(UNVERIFIED_OP), // v232 1547
    B_E_G_I_N__I_T_E_M_U_P_G_R_A_D_E(UNVERIFIED_OP), // v232 1547; *
    GOLD_HAMMER_REQUEST(UNVERIFIED_OP), // v232 1548; 1836 is the UIWindow2 "raise" UI (4 2 4 4), not the hammer
    GOLD_HAMMER_COMPLETE(UNVERIFIED_OP), // v232 1549; 1843/1844 are the ClothingBox dialog
    // Lucid begins here somewhere
    LUCID_UNK212_1302(1550), // v271 (v265 1513): send-site alignment (no body); v265 RE (boss-mech): no body, from the Lucid dragon object 0x143018610
    E_N_D__I_T_E_M_U_P_G_R_A_D_E(UNVERIFIED_OP), // v232 1550
    LUCID_STATUE_USE_REQUEST(1551), // v271 (v265 1514): send-site alignment, 1; v265 RE (boss-mech): byte 1, from the Trumpet Statue object 0x14349db30
    LUCID_WELCOME_BARRAGE_END_REQUEST(1552), // v271 (v265 1515): send-site alignment (no body); v265 RE (boss-mech): no body, phase 2 object 0x14302d100 / 0x143030500
    PAPULATUS_LASER_TOUCH(1556), // v271 sender 0x14367ab30 (0x614, no body) after its laser hit check; v232 1334
    PAPULATUS_TWEEZER_GRAB(1558), // v271 sender 0x14367ab30 (0x616): int tweezer index, int x; v232 1335
    PAPULATUS_TWEEZER_RELEASE(1559), // v271 sender 0x14367b0f0 (0x617): int tweezer index, from the grabbed state's timeout; v232 1336
    WILL_INFECTION_HIT(1563), // v271 (v265 1526): send-site alignment, 4 4 4 4 4; v265 RE high
    EPIC_DUNGEON_MORPH_REQUEST(1566), // v271 (v265 1529), send-site alignment; client 0x142689f50: no body, Tab on the Mitra skill bar (answer: Out 1274)
    TYNOS_FLARE_REQUEST(1567), // v271 (v265 1530), send-site alignment; client 0x1422bc4f0: no body, standing in Mitra's Flare (Out 2211)
    TYNOS_SPINE_DONE(1568), // v271 (v265 1531), send-site alignment; client 0x1422bcad0: int id, the delay of Out 2212 ended
    EPIC_DUNGEON_TIMER_DONE(1569), // v271 (v265 1532), send-site alignment; client 0x142349fb0: int id from Out 847 type 3
    TYNOS_ALTAR_REQUEST(1570), // v271 (v265 1533), send-site alignment; client 0x1422bc5d0: no body, touching the Tynos summoning altar
    B_E_G_I_N__F_A_R_M(UNVERIFIED_OP), // v232 1575
    INSERT_FARM_OBJECT(UNVERIFIED_OP), // v232 1576
    DELETE_FARM_OBJECT(UNVERIFIED_OP), // v232 1577
    INSERT_FARM_TILE(UNVERIFIED_OP), // v232 1578
    DELETE_FARM_TILE(UNVERIFIED_OP), // v232 1579
    TRANSFER_FARM_FIELD(UNVERIFIED_OP), // v232 1580
    BUY_FARM_OBJECT(UNVERIFIED_OP), // v232 1581
    SEL_FARM_OBJECT(UNVERIFIED_OP), // v232 1582
    MY_HOUSE_UPGRADE(UNVERIFIED_OP), // v232 1582
    FARM_ADMIN_COMMAND(UNVERIFIED_OP), // v232 1583
    REQUEST_QUEST_COMPLETE(UNVERIFIED_OP), // v232 1584
    CHECK_FARM_NAME(UNVERIFIED_OP), // v232 1585
    REQUEST_INTERACT_OBJECT(UNVERIFIED_OP), // v232 1586
    REQUEST_CONSUME_OBJECT(UNVERIFIED_OP), // v232 1587
    REQUEST_CLICK_NPC(UNVERIFIED_OP), // v232 1588
    REQUEST_RANDOM_FARM(UNVERIFIED_OP), // v232 1589
    REQUEST_FARM_VISIT(UNVERIFIED_OP), // v232 1590
    REQUEST_ADD_FARM_FRIEND(UNVERIFIED_OP), // v232 1591
    FARM_IN_GAME_FRIEND_REQUEST(UNVERIFIED_OP), // v232 1592
    REQUEST_DELETE_FARM_FRIEND(UNVERIFIED_OP), // v232 1593
    REQUEST_ADD_BLACK_LIST(UNVERIFIED_OP), // v232 1594
    REQUEST_DELETE_BLACK_LIST(UNVERIFIED_OP), // v232 1595
    UNK228_1604(UNVERIFIED_OP), // v232 1596
    REQUEST_FARM_INFO_UI(UNVERIFIED_OP), // v232 1597
    CHECK_FARM_NAME_BY_CONSUME(UNVERIFIED_OP), // v232 1598
    REQUEST_AUTO_HARVEST(UNVERIFIED_OP), // v232 1599
    // Castle Column
    // Will begins here somewhere
    WILL_MOONLIGHT_REQUEST(1600), // v271 (v265 1563): send-site alignment (no body); v265 RE medium (willLunarPowerBuff)
    REQUEST_SOUL_RECHARGE(UNVERIFIED_OP), // v232 1600
    WILL_EYE_ORB_HIT(1601), // v271 (v265 1564): send-site alignment (no body); v265 RE medium (hit box, same field class)
    REQUEST_TRANSFER_FARM_BY_NAME(UNVERIFIED_OP), // v232 1601
    WILL_WEB_HIT(1602), // v271 (v265 1565): send-site alignment, 4 1 4
    MY_FARM_MONSTER_REQUEST(UNVERIFIED_OP), // v232 1602
    FARM_MONSTER_COMBINE(UNVERIFIED_OP), // v232 1603
    FARM_MONSTER_SET_NAME_REQUEST(UNVERIFIED_OP), // v232 1604
    FARM_MONSTER_CARE(UNVERIFIED_OP), // v232 1605
    FARM_MONSTER_PLAY(UNVERIFIED_OP), // v232 1606
    FARM_MONSTER_RELEASE(UNVERIFIED_OP), // v232 1607
    FARM_MONSTER_SEARCH_REQUEST(UNVERIFIED_OP), // v232 1608
    FARM_MONSTER_POS_RECODE(UNVERIFIED_OP), // v232 1609
    WORLD_MAP_BOOKMARK_UPDATE_REQUEST(UNVERIFIED_OP), // v232 1610
    // FARM_MONSTER_LOCKER_REQUEST(1618),
    FARM_MONSTER_LIFE_EXTEND(UNVERIFIED_OP), // v232 1611
    PHOTO_UPDATE(UNVERIFIED_OP), // v232 1612
    MY_HOME_SEND_NOTICE_REQUEST(UNVERIFIED_OP), // v232 1612
    PHOTO_GET_REQUEST(UNVERIFIED_OP), // v232 1613
    MY_HOME_LOAD_MAIN_LOG_REQUEST(UNVERIFIED_OP), // v232 1613
    MY_HOME_DELETE_LOG_REQUEST(UNVERIFIED_OP), // v232 1614
    FARM_WHISPER(UNVERIFIED_OP), // v232 1615
    FARM_GROUP_MESSAGE(UNVERIFIED_OP), // v232 1616
    XENON_CORE_INC_REQUEST(UNVERIFIED_OP), // v232 1618; *
    REQUEST_FARM_FRIEND_RECOMMEND(UNVERIFIED_OP), // v232 1619
    FARM_REQUEST_IN_GAME_INFO_SET(UNVERIFIED_OP), // v232 1620
    FARM_REQUEST_IN_GAME_INFO_MOD(UNVERIFIED_OP), // v232 1621
    REQUEST_FARM_FRIEND_INFO(UNVERIFIED_OP), // v232 1622
    REQUEST_FARM_TODAY(UNVERIFIED_OP), // v232 1623
    UNK232_1624(UNVERIFIED_OP), // v232 1624; int + zeros, once after login
    REQUEST_FIRST_ENTER_REWARD(UNVERIFIED_OP), // v232 1627
    UNK227_1591(UNVERIFIED_OP), // v232 1628
    CHECK_ADMIN_ACCOUNT_LOG(UNVERIFIED_OP), // v232 1629
    CHARACTER_LIST_REQUEST(UNVERIFIED_OP), // v232 1629
    COPY_CHARACTER_TO_TEST_REQUEST(UNVERIFIED_OP), // v232 1629
    E_N_D__F_A_R_M(UNVERIFIED_OP), // v232 1630
    ERDA_SPECTRUM_COLLECTOR_REQUEST(1638), // v271 GMS: empty; harvest key next to the Erda Collector
    ERDA_SPECTRUM_LASER_REQUEST(1639), // v271 GMS: int char id, int 1 on / 0 off, int Arma Jr. object id
    ERDA_SPECTRUM_CLUSTER_REQUEST(1640), // v271 GMS: int, int 1 (right field), int cluster object id
    REWARD_REQUEST(UNVERIFIED_OP), // v232 1641
    SEREN_RAY_OF_LIGHT_END(1646), // v271 Seren phase 2 0x142d8acf0: int start tick, int now tick, int key (from SEREN_RAY_OF_LIGHT)
    CASH_SHOP_TUTORIAL_DONE(UNVERIFIED_OP), // v232 1655
    SPIRIT_SAVIOR_POLL(1663), // v271 (v265 1627), send-site alignment; v265 RE 0x1431090d0 (Spirit Savior, every 1000 ms): int, int (meaning unknown)
    SPIRIT_SAVIOR_REQUEST(1666), // v271 (v265 1630), send-site alignment; v265 RE 0x143108b60 (Spirit Savior field, mouse-up): empty body
    USER_SECOND_ATOM_COMMAND_REQUEST(1683), // v271 (v265 1647): send-site alignment
    USER_SECOND_ATOM_REMOVE_REQUEST(1684), // v271 (v265 1648): send-site alignment
    USER_SECOND_ATOM_COLLISION_REQUEST(1685), // v271 (v265 1649): send-site alignment
    HUNGRY_MUTO_DELIVER(1689), // v271 sender 0x143420650 (v265 1653): int x, int y, repeated while the player stands in Muto's goal area
    GUARDIAN_ANGEL_SLIME_CRYSTAL_DROP(1700), // v271 gauge window 0x142f6ea50: int value, int x, int y (Crystal Drop during the Guardian Wave)
    GUARDIAN_ANGEL_SLIME_WAVE_RESULT(1703), // v271 0x141c6c9f0: int count, int value (the Guardian Wave reached a gate)
    GUARDIAN_ANGEL_SLIME_REQUEST(1704), // v271 0x141c5c000 callers: int type; 4 spot used, 5 launcher, 7 aim, 9 launch, 12 crystal hit, 14 gauge used
    KALOS_FIELD_REQUEST(1706), // v271 Kalos fields (0x142d02a50, 0x142da6320, 0x142f7f5c0, ...): int type, int sub, body
    ARKARIUM_SCREEN_CRACK_ACK(1707), // v271 Arkarium field 0x142d01360 / 0x142d013e0: byte 1 when the warning timer ends; byte 2, byte, byte when the crack timer ends
    KALING_FIELD_REQUEST(1708), // v271 (v265 1672): send-site alignment, as v265; v265 RE Kaling field: int sub; 1 = black flame trigger (0x142499ba0: int id, int value), 0x3ed = shock-zone stun (0x14245fed0)
    KALING_SEASON_PICK_REQUEST(1709), // v271 Kaling's Four Seasons hub (GMS): int sub; 0 = int season (0 Spring, 1 Summer, 2 Fall), 1 = byte ready, 2 = start
    KALING_ELECTRIC_REQUEST(1711), // v271 (v265 1675): send-site alignment, as v265; v265 RE Taowu's field (0x1424622b0 family): int sub; 2 = zone spots, 4 = byte Taowu in current + 16-byte rect (0x1424643b0), 5 = int mini coil key (0x142461da0), 8 = int char id (0x142464130), 12 = byte flag [+ x, y] (0x142464260)
    FIELD_SKILL_REQUEST(1712), // v271 (v265 1676): send-site alignment, 4 4 1 as v265; v265 RE field skill 100028 (Dark Cloud, 0x142b860e0/0x142b863e0): int sub (0 = boss in cloud, 3 = boss heads for cloud), int key, int mob object id, byte
    UNKNOWN_OPCODE_1726_SINK(1726), // v271 0x1428b33d0 (boss pattern code): 4 object id, 4, 4, when an object timer starts/ends
    RANHEIM_DEFENSE_LAUNCH_REQUEST(1734), // v271 (v265 1697): int launcher 0-5 (harvest key on a Light Magic Launcher)
    RANHEIM_DEFENSE_SHOT_REQUEST(1735), // v271 (v265 1698): int launcher (its shot landed)
    ACTOR_RPC_ALT(1737), // v271 (v265 1700): same body as USER_ACTOR_RPC; the client picks it when its other actor channel is unset
    MONSTER_PARK_EXTREME_ALTAR_REQUEST(1743), // v271 (v265 1706), send-site alignment; v265 RE 0x142488310: int altar value, sent after holding the NPC chat key on an enabled altar
    MIDNIGHT_CHASER_REQUEST(1753), // v271 (v265 1716), send-site alignment; v265 RE 0x1423deb30 (Midnight Chaser field code): empty body
    UNKNOWN_OPCODE_1772_SINK(1772), // v271 0x14198a0b0: 4 type (7 = field timer expired), 4
    UNKNOWN_OPCODE_1777_SINK(1777), // v271 0x141f3d240: 4 object id, 4 4 4
    EPIC_DUNGEON2_PROTO_SUIT_REQUEST(1804), // v271 (v265 1761), send-site alignment; client 0x141fdae20: int object id, Angler Proto-Suit terminal (849 object type 6)
    EPIC_DUNGEON2_FIELD_REQUEST(1808), // v271 (v265 1765), send-site alignment; client 0x142025060: no body, Angler Area 3 field (375)
    EPIC_DUNGEON2_OBJECT_REQUEST(1809), // v271 (v265 1766), send-site alignment; Angler fields/objects: int sub-op (18 hack, 19 str str, 22 int, 23 int, 32, 33), then by sub-op
    B_E_G_I_N__C_A_S_H_S_H_O_P(1813), // *
    UNKNOWN_OPCODE_1831_SINK(1831), // v271 0x1435d12c0: empty, sent with 1832 when a window closes
    UNKNOWN_OPCODE_1832_SINK(1832), // v271 0x1435d12c0: empty
    VOYAGE_COMPLETE_REQUEST(1835), // v265 1780 // v265 RE 0x1430d23d0/0x1430d2440: byte 0/1, from the Sailing UI (UIWindowBT.img/Sailing)
    B_E_G_I_N__B_A_T_T_L_E_R_E_C_O_R_D(1849),
    CASH_SHOP_QUERY_CASH_REQUEST(1869), // v271 (v265 1814): sender 0x14203a020, empty
    CASH_SHOP_CASH_ITEM_REQUEST(1870), // v271 (v265 1815): 1 type first; types 2..0x34 as v265 (senders compared), higher ones moved
    CASH_SHOP_CHECK_COUPON_REQUEST(1871), // v271 (v265 1816): sender 0x142038f70, 1 0, S code, 1 flag; result on 2703 sub-type 16 / 18
    CASH_SHOP_SN_TEXT_REQUEST(1872), // v271 (v265 1818): sender 0x142036cc0 (priced commodity + two texts), 4 sn S S; meaning unknown
    CASH_SHOP_ACTION(1873), // v271 (v265 1819): sender 0x1420eda50, 1 4 S
    CASH_SHOP_LOOK_REQUEST(1874), // v271 (v265 1820): 0x14202f9d0 `4 1, look` (locks the window) / 0x14202fb40 `4 2, ...`
    TELEMETRY_SNAPSHOT_1892(1892), // 0x145453440: common head (str date, ints, str name, ...), then lists
    TELEMETRY_SNAPSHOT_1893(1893), // 0x145454f10
    TELEMETRY_SNAPSHOT_1894(1894), // 0x14552f670: link skills
    TELEMETRY_SNAPSHOT_1896(1896), // 0x145456360: hyper skills
    TELEMETRY_SNAPSHOT_1897(1897), // 0x1454546b0
    TELEMETRY_SNAPSHOT_1898(1898), // 0x145455670
    TELEMETRY_SNAPSHOT_1899(1899), // 0x145450960: equips ("expired" / date)
    TELEMETRY_SNAPSHOT_1901(1901), // 0x145458ac0
/* 1903 */    BATTLE_RECORD_ON_OFF_REQUEST(1903), // v265 1850
    BATTLE_RECORD_SKILL_DAMAGE_LOG(1904), // v265 1851
    TELEMETRY_SNAPSHOT_1904(UNVERIFIED_OP), // v265 1904; v271 1904 is BATTLE_RECORD_SKILL_DAMAGE_LOG // 0x14545b6b0: long, long, int
    E_N_D__B_A_T_T_L_E_R_E_C_O_R_D(1905), // v265 1852
    TELEMETRY_SNAPSHOT_1905(UNVERIFIED_OP), // v265 1905; v271 1905 is E_N_D__B_A_T_T_L_E_R_E_C_O_R_D // 0x145453270: int reason, int, int, str, int
    UNKNOWN_OPCODE_1855_SINK(1908), // v271 0x145ac7710 (v265 1855, unanswered there too): empty
    // 0x1433e5530 etc. from 0x1433df8a0: 4 method, 2, B mask, [1: varint n, n x 4 cube slot] [2: 4 equip] [per method],
    // then B, 2 reply header (555), 4 call id; answered on OutHeader.POTENTIAL_RPC_RESULT
    REQUEST_STATUS_CHECK(1913), // v271 (v265 1860): sender 0x143106930 (4 id, 4 type, 1), reply handler 0x1431063a0 reads 4 id, 1; reply number unknown // T value: time pulse; answered with OutHeader.REQUEST_STATUS_CHECK
    POTENTIAL_RPC_REQUEST(1916), // v271 (v265 1863, unsent there): the Enhance window's cube slot as an RPC call; senders
    TELEMETRY_RPCTEST_SINK(1918), // v271 (v265 1865); also a 13-byte periodic form // 0x142f64eb0: "/rpctest"
    EXTRA_SYSTEM_ACK(1919), // v271: the reply opcode GMS names in EXTRA_SYSTEM_RESULT type 48: the 4 echoed bytes, 4, 4
    TEMPORARY_STAT_RESET2(1924), // v271 bracket 1923=1870 / 1925=1872 (v265 1871, unanswered there too): 18 bytes, periodic
    UNKNOWN_OPCODE_1930_SINK(1930), // 0x143471790 / 0x143481e70: empty (world list release)
    // character snapshot log, sent as one burst by 0x1454093a0 (int reason 1..6): 1900 first, then the rest
    TELEMETRY_SNAPSHOT_START(1950), // v271 (v265 1900) // int reason, str
    USER_OPEN_QUICK_MOVE_REQUEST(1957), // v265 1909 // v271 0x144a7c940: the Quick Move panel button, short = last SET_QUICK_MOVE_INFO flag; answer USER_OPEN_QUICK_MOVE
    UNKNOWN_OPCODE_1957_SINK(UNVERIFIED_OP), // was 1957; v271 1957 is USER_OPEN_QUICK_MOVE_REQUEST // v271 0x144a7c940: 2 field value
    HYPER_ROCK_PRICE_REQUEST(1960), // v271 sender 0x1459c8220, empty: the Hyper Teleport Rock rental window asks for its prices
    HYPER_ROCK_BUY_REQUEST(1961), // v271 sender 0x1426dac00: 4 option index
    BATTLE_RECORD_MAX_DAMAGE(1962), // v271 sender 0x1459c8290 (called from the battle-record sender 0x145a61df0): byte flag, 4 max damage (999999999 = reset), 4, and 4 more when the flag is set; no reply
    UNKNOWN_OPCODE_1915_SINK(1963), // v271 0x145a63bf0 (v265 1915): byte, before REQUEST_RELOGIN_COOKIE on exit
    UNKNOWN_OPCODE_1917_SINK(1965), // v271 0x145a163d0 (v265 1917): byte, field entry
    CHANNEL_ENTER_FLAG_ACK(1966), // v271 protected socket code 0x141968de0: 1 (01), the answer to Out 2958; no reply
    QUICK_LOGIN_WORLD_CHANGE(1980), // v271 0x143a6c380: empty, Quick Login state dropped when another world is picked
    QUICK_LOGIN_CANCEL(1983), // v271 0x145a5fde0 (from 0x145b17e80): empty, Quick Login state dropped; no reply
    VOYAGE_REQUEST(2005), // v265 1942 // v232 1630, IDB high
    UNKNOWN_OPCODE_2006_SINK(2006), // v271 0x14531f9e0: empty
    ZERO_STAT_REPORT(2008), // v271 (v265 1949): Zero only (job 10000/101xx): byte (0 on field entry, 1 when it changes), int from CharacterStat +0x73; no GMS reply
    UNKNOWN_OPCODE_2022_SINK(2022), // v271 0x145ad3b20: 4
    NO(4000),
    // v265 1181 (the masked codex encoder); no v271 sender: presets and badges go through EDIT types 12-14
    FAMILIAR_CODEX_MODIFY_REQUEST(UNVERIFIED_OP),
    ;

    private static List<InHeader> spam = Arrays.asList(
            SELECT_WORLD, // can carry a relogin cookie; the handler logs it shortened
            UPDATE_CLIENT_ENVIRONMENT,
            CREATE_SECURITY_HANDLE, // v271 161: machine GUID + 2 ints on login; v265.3 ignores it too
            WVS_SET_UP_STEP,
            SECURITY_PACKET,
            PRIVATE_SERVER_PACKET,
            MOB_MOVE,
            USER_MOVE,
            USER_HIT,
            USER_EMOTION,
            PASSIVE_SKILL_INFO_UPDATE,
            USER_CHANGE_STAT_REQUEST,
            SUMMONED_MOVE,
            UPDATE_CHARACTER_CARD_REQUEST, //163
            USER_TEMPORARY_STAT_UPDATE_REQUEST,
            USER_CALC_DAMAGE_STAT_SET_REQUEST,
            MOB_APPLY_CTRL,
            USER_REQUEST_INSTANCE_TABLE,
            USER_QUEST_REQUEST,
            CHECK_LOGIN_AUTH_INFO,
            NPC_MOVE,
            FAMILIAR_MOVE,
            PET_MOVE,
            ALIVE_ACK,
            PING_CHECK_ACK,
            OBSTACLE_ATOM_COLLISION,
            USER_COMPLETE_COMBO_KILL_COUNT_CHECK,
            USER_COMPLETE_MULTI_KILL_COUNT_CHECK,
            USER_COMPLETE_MULTI_KILL_CHECK,
            USER_CHAT,
            XENON_CORE_OVERLOAD,
            MOB_SKILL_DELAY_END,
            MOB_FREEZE_EFFECT_ACK,
            DEMON_LASH_NEXT_SKILL,
            ANDROID_MOVE,
            DRAGON_MOVE,
            STACK_OVER_TIME_SKILL_INCREASE_REQUEST,
            MOD_SPECTRA_ENERGY,
            MOB_LIFTING_END,
            SKILL_PET_MOVE,
            MY_HOME_DELETE_LOG_REQUEST,
            INFERNO_SPHERE_FEATHER_COUNT,
            FOX_MAN_MOVE,
            SPAM_HEADER,
            EGO_EQUIP_CREATE_UPGRADE_ITEM_COST_REQUEST,
            USER_WIND_ENERGY_REQUEST,
            FARM_REQUEST_IN_GAME_INFO_SET,
            USER_TIMER_REQ,
            EXTRA_SYSTEM_ACK,
            TELEMETRY_SNAPSHOT_START, TELEMETRY_SNAPSHOT_1892, TELEMETRY_SNAPSHOT_1893, TELEMETRY_SNAPSHOT_1894,
            TELEMETRY_SNAPSHOT_1896, TELEMETRY_SNAPSHOT_1897, TELEMETRY_SNAPSHOT_1898, TELEMETRY_SNAPSHOT_1899,
            TELEMETRY_SNAPSHOT_1901, TELEMETRY_SNAPSHOT_1904, TELEMETRY_SNAPSHOT_1905,
            ARAN_SKILL_AREA_NOTICE, WEAPON_AURA_READY_NOTICE, WEAPON_AURA_TRIGGER_NOTICE,
            USER_CHANGE_STAT_REQUEST_BY_ITEM_OPTION, ANDROID_ACTION_SET, UNKNOWN_OPCODE_661_SINK,
            UNKNOWN_OPCODE_1726_SINK,
            USER_SHOP_REQUEST,//                303
            USER_CLIENT_RESOLUTION_RESULT,//     444
            MOB_CRC_DATA_RESULT,
            PET_DROP_PICK_UP_REQUEST,// 727
            BATTLE_RECORD_ON_OFF_REQUEST,//             1903
            // per-frame traffic: attacks, skill use, slot/position updates and unnamed telemetry sinks
            USER_MELEE_ATTACK, USER_SHOOT_ATTACK, USER_SKILL_USE_REQUEST, USER_SKILL_UP_REQUEST,
            USER_CHANGE_SLOT_POSITION_REQUEST, USER_QUICK_SLOT_MODIFIED, USER_SCRIPT_MESSAGE_ANSWER,
            USER_PORTAL_SCRIPT_REQUEST, CASH_SHOP_CASH_ITEM_REQUEST, PARTY_INVITABLE_SET, MOB_LIFTING_END,
            UNKNOWN_OPCODE_202_SINK, UNKNOWN_OPCODE_263_SINK, UNKNOWN_OPCODE_506_SINK, UNKNOWN_OPCODE_507_SINK,
            UNKNOWN_OPCODE_675_SINK, UNKNOWN_OPCODE_760_SINK, UNKNOWN_OPCODE_1109_SINK, UNKNOWN_OPCODE_1157_SINK,
            UNKNOWN_OPCODE_1342_SINK, UNKNOWN_OPCODE_2022_SINK
    );

    private short value;
    private final static Map<Short, InHeader> opToHeaderMap = new HashMap<>();

    static {
        for (InHeader ih : values()) {
            if (ih.value != UNVERIFIED_OP) {
                opToHeaderMap.put(ih.getValue(), ih);
            }
        }
    }

    InHeader(int op) {
        this.value = (short) op;
    }

    public short getValue() {
        return value;
    }

    public static InHeader getInHeaderByOp(int op) {
        return opToHeaderMap.getOrDefault((short) op, null);
    }

    public static boolean isSpamHeader(InHeader inHeaderByOp) {
        return spam.contains(inHeaderByOp);
    }

    public boolean isAlwaysShowExceptions() {
        switch (this) {
            case USER_TRANSFER_CHANNEL_REQUEST:
            case MIGRATE_IN:
            case CREATE_NEW_CHARACTER:
            case DELETE_CHARACTER:
            case CHECK_SPW_REQUEST:
            case SELECT_WORLD:
            case USER_CHARACTER_INFO_REQUEST:
                return true;
        }
        return false;
    }

    public boolean isIgnoreShowExceptions() {
        switch (this) {
            case USER_MELEE_ATTACK:
            case USER_SHOOT_ATTACK:
            case USER_MAGIC_ATTACK:
            case SUMMONED_HIT:
            case SUMMONED_ATTACK:
            case MOB_MOVE:
            case USER_MOVE:
            case USER_FORCE_ATOM_COLLISION:
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        File file = new File(ServerConstants.DIR + "\\src\\main\\java\\net\\swordie\\ms\\handlers\\header\\InHeader.java");
        int change = -2;
        InHeader checkOp = null;
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.contains(",") && line.contains("(")) {
                    String[] split = line.split("[()]");
                    String name = split[0];
                    if (!Util.isNumber(split[1])) {
                        org.apache.logging.log4j.LogManager.getLogger(InHeader.class).info(line);
                        continue;
                    }
                    int val = Integer.parseInt(split[1]);
                    InHeader ih = Arrays.stream(InHeader.values()).filter(o -> o.toString().equals(name.trim())).findFirst().orElse(null);
                    if (ih != null) {
                        InHeader start = B_E_G_I_N__C_A_S_H_S_H_O_P;
                        if (ih.getValue() >= start.getValue() && ih.getValue() <= CASH_SHOP_LOOK_REQUEST.getValue()) {
                            if (line.contains("*")) {
                                checkOp = ih;
                            }
                            val += change;
                            org.apache.logging.log4j.LogManager.getLogger(InHeader.class).info(String.format("%s(%d), %s", name, val, start == ih ? " // *" : ""));
                        } else {
                            org.apache.logging.log4j.LogManager.getLogger(InHeader.class).info(line);
                        }
                    } else {
                        org.apache.logging.log4j.LogManager.getLogger(InHeader.class).info(line);
                    }
                } else {
                    org.apache.logging.log4j.LogManager.getLogger(InHeader.class).info(line);
                }
            }
        } catch (FileNotFoundException e) {
            org.apache.logging.log4j.LogManager.getLogger(InHeader.class).error("InHeader.main", e);
        }
        if (checkOp != null) {
            org.apache.logging.log4j.LogManager.getLogger(InHeader.class).error(String.format("Current op (%s) contains a * (= updated). Be sure to check for overlap.", checkOp));
        }
    }
}
