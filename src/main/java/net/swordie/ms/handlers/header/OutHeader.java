package net.swordie.ms.handlers.header;

import net.swordie.ms.util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static net.swordie.ms.ServerConstants.UNVERIFIED_OP;

/**
 * Created on 2/18/2017.
 * Values are GMS v271.4. UNVERIFIED_OP marks entries whose v271 value is still unknown (the last known number is
 * in the trailing comment); packets with that header are dropped before sending.
 */
public enum OutHeader {
    // CLogin::OnPacket        v223.1
    CHECK_PASSWORD_RESULT(0), // v271: login, unshifted below 41; in the GMS login
    WORLD_INFORMATION(11), // v271: login, unshifted below 41; in the GMS login
    LATEST_CONNECTED_WORLD(12),
    RECOMMENDED_WORLD_MESSAGE(15),
    SET_CLIENT_KEY(16), // v271: login, unshifted below 41; in the GMS login
/* 17 */    SET_PHYSICAL_WORLD_ID(17), // v271: login, unshifted below 41; in the GMS login
    SELECT_WORLD_BUTTON(18), // v271: login, unshifted below 41; in the GMS login
    SELECT_WORLD_RESULT(19), // v271: login, unshifted below 41; in the GMS login
    SELECT_CHARACTER_RESULT(20), // v271: login, unshifted below 41; in the GMS login
    ACCOUNT_INFO_RESULT(21), // v271: login, unshifted below 41; in the GMS login
    CREATE_MAPLE_ACCOUNT_RESULT(22),
    CHECK_DUPLICATED_ID_RESULT(23), // v271: login, unshifted below 41; in the GMS login
    CREATE_NEW_CHARACTER_RESULT(25), // v271: login, unshifted below 41; in the GMS login
    DELETE_CHARACTER_RESULT(26), // v271: login, unshifted below 41; in the GMS login
    CHANGE_SPW_RESULT(UNVERIFIED_OP), // v232 26
    UNK218_27(UNVERIFIED_OP), // v232 27
    RESERVED_DELETE_CHARACTER_RESULT(28),
    UNK218_28(UNVERIFIED_OP), // v232 28
    RESERVED_DELETE_CHARACTER_CANCEL_RESULT(29),
    SET_CHARACTER_ID(UNVERIFIED_OP), // v271: 29 is RESERVED_DELETE_CHARACTER_CANCEL_RESULT
    UNK218_29(UNVERIFIED_OP), // v232 29
    ALBA_RESULT(UNVERIFIED_OP), // v232 30
    MIGRATE_COMMAND(31), // v271: unshifted below 41 (socket dispatcher is VM code); handler 0x1431f6180 reads 1 4 2 4
    UNK218_32(UNVERIFIED_OP), // v232 31
    ALIVE_REQ(32), // v271: login, unshifted below 41; in the GMS login
    UNK218_33(UNVERIFIED_OP), // v232 32
    PING_CHECK_RESULT(33), // in-game keepalive, empty; answered by In PING_CHECK_ACK
    UNK218_34(UNVERIFIED_OP), // v232 33
    IN_GAME_PING_REQ(34),
    ILLEGAL_PROGRAM(UNVERIFIED_OP), // v232 34
    AUTH_CODE_CHANGED(35),
    SET_AUTO_SELECTED_WORLD(UNVERIFIED_OP), // v232 35
    AUTH_MESSAGE(36),
    WORLD_INFO_FOR_SHINING_RESULT(UNVERIFIED_OP), // v232 36
    PRIVATE_SERVER_PACKET(37),
    UNK212_38(UNVERIFIED_OP), // v232 37
    SECURITY_PACKET(38), // v271: login, unshifted below 41; in the GMS login
    UNK212_39(UNVERIFIED_OP), // v232 38
    SECURITY_PACKET_CODE(39), // v271: login, unshifted below 41; in the GMS login
    UNK212_40(UNVERIFIED_OP), // v232 39
    UNK212_41(UNVERIFIED_OP), // v232 40
    UNK212_42(UNVERIFIED_OP), // v232 41
    SECURITY_PACKET_CODE_ATTACH(UNVERIFIED_OP), // v265 42 (empty); v271 42 is unknown, not sent
    OPCODE_ENCRYPTION(44), // v265 43
    IDK_BUT_CRASHES(UNVERIFIED_OP), // v232 44
    UNK212_47(UNVERIFIED_OP), // v232 45
    SOME_CHANNEL_REQUEST_RESULT(UNVERIFIED_OP), // v232 46
    UNK212_48(UNVERIFIED_OP), // v232 47
    UNK212_49(UNVERIFIED_OP), // v232 48
    UNK212_50(UNVERIFIED_OP), // v232 49
    UI_PERMISSIONS(UNVERIFIED_OP), // v232 50
    UNK212_51(UNVERIFIED_OP), // v232 51
    UNK212_53(UNVERIFIED_OP), // v232 52
    UNK212_54(UNVERIFIED_OP), // v232 53
    UNK212_55(UNVERIFIED_OP), // v232 54
    UNK218_56(UNVERIFIED_OP), // v232 55
    UNK212_57(UNVERIFIED_OP), // v232 56
    UNK218_57(UNVERIFIED_OP), // v232 57
    UNK218_58(UNVERIFIED_OP), // v232 58
    UNK218_59(UNVERIFIED_OP), // v232 59
    UNK218_60(UNVERIFIED_OP), // v232 60
    SERVER_STATUS(UNVERIFIED_OP), // v232 61
    HOTFIX(62), // v271: receiver 0x1431fef20: packed size, D4 SHA-1[0:4] of the file, then 0x10000 bytes per packet (later packets bytes only) -> Data.wz; size -1 = none (deletes Data.wz), 0 = apply the one on disk
    // v271: a new CLogin case 43 shifts v265 43..110 by +1 (CLogin table; v265 95 stays 95, 111 and 126 are +4)
    WZ_CHECK_LIST(UNVERIFIED_OP), // v265 61; v271 62 is the Data.wz hotfix receiver, real number unknown, not sent
    FILE_CHECK_LIST(UNVERIFIED_OP), // v265 62; handled in the protected dispatcher, v271 number unknown (63 closed the client in test 10)
    TRY_AGAIN(UNVERIFIED_OP), // v232 62
    FREEZE_CLIENT_ON_BLACK_SCREEN(UNVERIFIED_OP), // v232 63
    UNK212_61(UNVERIFIED_OP), // v232 64
    SPW_SET_RESULT(UNVERIFIED_OP), // v232 65
    SPW_DESET_RESULT(UNVERIFIED_OP), // v232 66
    AUTH_SERVER(UNVERIFIED_OP), // v232 69
    SERVER_VALUE(70), // v265 69; the v232 UI_PERMISSIONS list (index, key, value, "All"); v271 reader 0x14060ab50
    SERVER_ENVIRONMENT(71), // v265 70; v271 reader 0x14060ac90
    AUTH_INFO_REQUEST(UNVERIFIED_OP), // v232 71
    UNK212_68(UNVERIFIED_OP), // v232 72
    UNK212_69(UNVERIFIED_OP), // v232 73
    SOME_DATE_RESPONSE(UNVERIFIED_OP), // v232 74
    CLIENT_ALIVE(82), // v271 (v265 81): CLogin, empty in the GMS login
    UNK69(UNVERIFIED_OP), // v232 91
    UNK212_97(UNVERIFIED_OP), // v232 93
    LOGIN_JOB_VALUES(100), // v271 (v265 99): CLogin, 38 entries
    USER_DAMAGE_ON_FALLING_CHECK(UNVERIFIED_OP), // v232 104
    USER_GET_TIME_RESULT(106), // v271 (v265 105): CLogin, FT
    GAME_KICK(107), // v265 106
    EXCL_REQUEST_2(UNVERIFIED_OP), // v232 111
    // CMapLoadable::OnPacket        v223.1
    SET_BACK_EFFECT(113),
    UNK218_114(UNVERIFIED_OP), // v232 113
    UNK203_111(UNVERIFIED_OP), // v232 117
    ANTI_MACRO_RESULT(UNVERIFIED_OP), // v232 118
    ANTI_MACRO_BOMB_RESULT(UNVERIFIED_OP), // v232 119
    // blank
    UNK_218_122(UNVERIFIED_OP), // v232 121
    UNK_200_114(UNVERIFIED_OP), // v232 122
    UNK_200_115(UNVERIFIED_OP), // v232 123
    SET_MAP_TAGGED_OBJECT_SMOOTH_VISIBLE(127), // v271 (v265 123): stage virtual +0x70; GMS: 1 n, n x (S 1 4 4), used for most tag toggles
    SET_MAP_TAGGED_OBJECT_VISISBLE(128), // v271 (v265 124): map-loadable table 0x143aa7860 (base 116, v265 112) -> 0x143ad52d0: 4 n, n x (S 1 4 4)
    UNK227_129(UNVERIFIED_OP), // v232 128
    SET_MAP_TAGGED_OBJECT_ANIMATION(129), // v271 (v265 125): 0x143aecb60, 4 n, n x (S 4)
    UNK203_122(UNVERIFIED_OP), // v232 129
/* 130 */    SET_MAP_TAGGED_OBJECT_VISIBLE_LOGIN(130), // v265 126 (v271 CLogin case 130)
    SET_MAP_OBJECT_ANIMATION(131), // v271 (v265 127): 0x143aec660, S 4
    SET_MAP_OBJECT_VISIBLE(132), // v271 (v265 128): 0x143aecca0, S 4
    SET_MAP_OBJECT_MOVE(133), // v271 (v265 129): 0x143aed340, S 4 4 4
    SET_MAP_OBJECT_CREATE_LAYER(134), // v271 (v265 130): map-loadable table, +4 as the whole table
    SET_SPINE_BACK_EFFECT(135), // v271 (v265 131): map-loadable table, +4 as the whole table
    SET_SPINE_OBJECT_EFFECT(136), // v271 (v265 132): map-loadable table, +4 as the whole table
    CREATE_SPINE_RECT_EVENT(137), // v271 (v265 133): map-loadable table, +4 as the whole table
    REMOVE_SPINE_RECT_EVENT(138), // v271 (v265 134): map-loadable table, +4 as the whole table
    SPINE_RE_ADD_BACK_EVENT(139), // v271 (v265 135): map-loadable table, +4 as the whole table
    SPINE_RE_ADD_OBJECT_EVENT(140), // v271 (v265 136): map-loadable table, +4 as the whole table
    CREATE_CAMERA_CTRL_ZONE(141), // v271 (v265 137): map-loadable table, +4 as the whole table
    REMOVE_CAMERA_CTRL_ZONE(142), // v271 (v265 138): map-loadable table, +4 as the whole table
    URSUS_PARTY_MEMBER_CANDIDATE_RESULT(UNVERIFIED_OP), // v232 145
    RELOAD_BACK(147), // v271 (v265 143): map-loadable table, +4 as the whole table
    UNK212_155(UNVERIFIED_OP), // v232 147
    // CWvsContext::OnPacket        v223.1
    INVENTORY_OPERATION(148), // v271 (v265 144): op1/op8 quantity 4 bytes (ported); reads otherwise as v265
    // blank
    EXPEDITION_RESULT(UNVERIFIED_OP), // v232 152
    UNK_232_156(UNVERIFIED_OP), // v232 156
    INVENTORY_GROW(159), // v265 155
    // CWvsContext boss reward storage (jump table index 0x9c/0x9d)
    BOSS_REWARD_STORAGE(160), // v271 (v265 156): CWvsContext 0x1459c8620; lists and record (B8 4 4 8 B8 B8) as v265; 0x1453bf820: 5 x (int n, n items), int n records; refreshes the inventory BossReward panel
    BOSS_REWARD_STORAGE_RESULT(161), // v265 157 // 0x1453bf900: int type (5 = can't open while incapacitated)
    STAT_CHANGED(162), // v271 (v265 158): money is read after every other stat (WvsContext.statChanged)
    TEMPORARY_STAT_SET(163), // v271 (v265 159, Codex): 160-byte mask; not ported, held by the field test
    TEMPORARY_STAT_RESET(164), // v271 (v265 160, Codex): 160-byte mask; not ported, held by the field test
    FORCED_STAT_SET(165), // v271 (v265 161): ctx 0x145a71c90 -> forced-stat decode 0x1410cdf20 (4 mask, fields by bit)
    UNK200_158(UNVERIFIED_OP), // v232 165
    FORCED_STAT_RESET(166), // v271 (v265 162): ctx 0x145a71ce0, no body; GMS sends it on every field entry
    CHANGE_SKILL_RECORD_RESULT(167), // v271 (v265 163): handler 0x145b4b180 reads as v265: 1 1 2 n {4 4 4 B8} 1
    CHANGE_STEAL_MEMORY_RESULT(168), // v271 (v265 164): ctx 0x145b4c0e0; encoder matches (emulated, all six types)
    MOB_DROP_MESO_PICKUP(170), // v271 (v265 166): ctx 0x145a7c4c0, 4 mesos picked up (meso trackers / quests); GMS sends it after each meso pickup
    CASH_LOOK_CHANGE_RESULT(UNVERIFIED_OP), // v232 170
    BREAK_TIME_FIELD_ENTER(171), // v271 171: dispatch 0x145A7C630 matches v265 167 (0x14547C030) instruction-for-instruction; 167 is CHANGE_SKILL_RECORD_RESULT
    RUNE_ACT_SUCCESS(172), // v271 (v265 168): ctx table, reads 4 as v265
    RESULT_STEAL_SKILL_LIST(173), // v271 (v265 169): ctx table, reads as v265
    SKILL_USE_RESULT(174), // v271 (v265 170): ctx table, reads as v265
    EXCL_REQUEST(175), // v271 (v265 171): handler 0x145a71d30 = v265 0x145471410 (xor edx; jmp), bracket 174/177
    UNK_232_176(UNVERIFIED_OP), // v232 176
    GIVE_POPULARITY_RESULT(177), // v271 (v265 173): ctx table, reads as v265
/* 179 */    MESSAGE(179), // v271 (v265 175): sub-types 1 (quest record) and 13 (quest ex) read as v265; others unchecked; 0 (drop pickup) and 3 (EXP, new mask bit 43 unused) as v265; a new type 32 moves v265 32-49 up by one (MessageType)
    MEMO_RESULT(180), // v271 (v265 176): ctx table, read sequence equal
    MAP_TRANSFER_RESULT(181), // v271 (v265 177): ctx table, 1 1 4 4
    // blank
    CLAIM_RESULT(189), // v265 185
    SET_CLAIM_SVR_AVAILABLE_TIME(190), // v265 186
    CLAIM_SVR_STATUS_CHANGED(191), // v271 (v265 187): ctx table, reads as v265
    QUEST_CLEAR(241), // v271 (v265 236): CWvsContext::OnQuestClear 0x145AA6CA0, same body as v265 236 (0x1454A3E00): 4, opens UI 0x67d; v232 191
    NOTIFY_LEVEL_UP(UNVERIFIED_OP), // v232 191
    NOTIFY_WEDDING(UNVERIFIED_OP), // v232 192
    ENTRUSTED_SHOP_CHECK_RESULT(193), // v271 193: dispatch 0x145AA7FE0 runs the same 28 calls as v265 189 (0x1454A4F90); v232 132
    NOTIFY_JOB_CHANGE(UNVERIFIED_OP), // v232 193
    SET_TAMING_MOB_INFO(194), // v271 (v265 190): ctx table, reads as v265
    SKILL_LEARN_ITEM_RESULT(196), // v265 192
    SKILL_RESET_ITEM_RESULT(197), // v271 (v265 193): ctx table, reads as v265
    ABILITY_RESET_ITEM_RESULT(198), // v271 (v265 194): ctx table, reads as v265
    EXP_CONSUME_ITEM_RESULT(199), // v265 195
    EXP_ITEM_GET_RESULT(200), // v271 200: dispatch 0x145AA9AC0, same 12-call body as v265 196 (0x1454A6A70); v232 137
    CHAR_SLOT_INC_RESULT(201), // v271 (v265 197): dispatch 0x145AA9CF0 reads 1 4 4 4 and tail-jumps to 0x1459AB700, the instruction-for-instruction twin of v265 0x1454A6CA0/0x1453A3010 (offsets +0xD0); fills the only gap in 200/202/203
    GATHER_ITEM_RESULT(202), // v271 (v265 198): context table, reads 1
    SORT_ITEM_RESULT(203), // v271 (v265 199): context table, reads 1
    UNK212_213(UNVERIFIED_OP), // v232 204
    UNK223_203(UNVERIFIED_OP), // v232 205
    UNK228_205(UNVERIFIED_OP), // v232 206
    // blank
    CHARACTER_INFO_LOCAL(207), // v271 (v265 202): answer to In USER_CHARACTER_INFO_SELF_REQUEST; reads as v265
    STAT_WINDOW_GUIDE(208), // v271 ctx 0x145b15110: 4 type (1 open, 2 open + tutorial bubble named by the string, 3, 5, 6), S name; GMS 2 "apInstantTutorial" after the job-advancement auto-assign
    UNK209_209(UNVERIFIED_OP), // v232 208
    CHARACTER_INFO(209), // v271 (v265 204): ctx 0x145b151b0, B4 4 + CharacterData (210 is a new sibling)
    UNK212_217(UNVERIFIED_OP), // v232 210
    PARTY_RESULT(212), // v271 (v265 206): 0x142dc6fd0; result types renumbered (PartyResult.v271Type)
    WILD_HUNTER_INFO_REMOVED(UNVERIFIED_OP), // v232 212
    PARTY_MEMBER_CANDIDATE_RESULT(213), // v271 0x142dca2a0 (case 0xd5, shared with 216); GMS answers In 1410 with it
    PARTY_CANDIDATE_RESULT(216), // v271 (v265 210): ctx, shares 0x142dca2a0 with 213
    URSUS_PARTY_RESULT(217), // v265 211
    EPIC_DUNGEON_CLEAR_REWARD(218), // v271 (v265 212): ctx table 0x1459c8dd0 -> 0x142c37a40, the two tier tables swapped (EpicDungeon.rewardWindow). ClearRewardUI 0x1426739b0 (Epic Dungeon clear reward window)
    EPIC_DUNGEON_BONUS_REWARD(219), // v271 (v265 213): 0x1459c8fa0 -> 0x142c30480, tier tables swapped as 218. BonusRewardUI 0x14266c3c0 (Epic Dungeon bonus reward window)
    // somewhere below, something is removed.
    SET_SON_OF_LINKED_SKILL_RESULT(UNVERIFIED_OP), // v232 223
    FRIEND_RESULT(224), // v271 (v265 218): ctx 145b6fc30, types renumbered (FriendResult)
    GUILD_REQUEST(225), // v271 (v265 219): only the alliance invitation is left (type 50)
    GUILD_RESULT(226), // v271 (v265 220): ctx 145b260b0, types renumbered (GuildType.v271)
    ALLIANCE_RESULT(227), // v271 (v265 221): 0x1408767e0, int type, guild-data events (1 = 4, GuildData; 0x22/0x25/0x2c 4 4; 0x74 4 4 4 skill; 0x81 4 4 S), as v265 0x14073e430; AllianceResult's v232 layout is not it: not sent on login/join
    UNK228_226(UNVERIFIED_OP), // v232 227
    TOWN_PORTAL(228), // v271 (v265 222): ctx table, reads as v265
    CROSS_WORLD_BOSS_MSG(229), // v271 (v265 223): dispatch 0x145A7C980 is the 6-insn wrapper twin of v265 0x14547C370; v265's 229 WEDDING_GIFT_RESULT body has no v271 twin
    BROADCAST_MSG(230), // v271 (v265 224): ctx 0x145a7ca10, types 0-0x17 read as v265 (0x18 balloon B480, 0x22+ new)
    INCUBATOR_RESULT(231), // v271 (v265 225): ctx table, reads as v265; encoder matches the v271 handler 0x145acd3c0 (emulated)
    UNK_200_218(UNVERIFIED_OP), // v232 231
    INCUBATOR_HOT_ITEM_RESULT(232), // v271 232: dispatch 0x145ACE160 matches v265 226 (0x1454C6F20) instruction-for-instruction; 226 is GUILD_RESULT
    UNK_200_219(UNVERIFIED_OP), // v232 232
    MARRIAGE_REQUEST(233), // v271 233: dispatch 0x145AA8430, same 21-call body as v265 227 (0x1454A53D0); 227 is ALLIANCE_RESULT
    MARRIAGE_RESULT(234), // v265 228
    NOTIFY_MARRIED_PARTNER_MAP_TRANSFER(235), // v271 235: dispatch 0x1459CA090 matches v265 230 (0x1453C13D0) instruction-for-instruction; 230 is BROADCAST_MSG
    ACHIEVEMENT_RESULT(236), // v271 (v265 231): ctx 0x145af38b0, reads as v265; 0x1454efe70: int type; 6 = int yyyymmdd, int n, n x (int achievement, int completions); 7 = int (update done). 0-3 never: client integrity checks
    // blank
    // blank
    SHUTDOWN_MESSAGE(UNVERIFIED_OP), // v232 238
    UNK205_234(UNVERIFIED_OP), // v232 242
    CASH_PET_FOOD_RESULT(243), // v265 238
    UNK211_250(UNVERIFIED_OP), // v232 243
    CASH_PET_PICK_UP_ON_OFF_RESULT(244), // v271 (v265 239): context table, reads 1 1
    CASH_PET_SKILL_SETTING_RESULT(245), // v271 (v265 240)
    CASH_PET_ITEM_POUCH_SKILL_RESULT(246), // v271 (v265 241): CWvsContext::OnCashPetItemPouchSkillResult 0x1459C9420, same body as v265 241
    CASH_PET_DYEING_RESULT(248), // v271 (v265 242): v271 248-257 are v265 242-251 shifted +6 by the new op at 247
    SET_WEEK_EVENT_MESSAGE(249), // v265 243
    SET_POTION_DISCOUNT_RATE(250), // v271 (v265 244): ctx, CWvsContext::OnSetPotionDiscountRate, 1; GMS byte 0 at login
    BRIDLE_MOB_CATCH_FAIL(251), // v265 245
    IMITATED_NPC_RESULT(252), // v271 (v265 246): v271 handler 0x1459CB5C0 reads one byte
    CROSS_HUNTER_COMPLETE_RESULT(UNVERIFIED_OP), // v232 252
    // Blank
    IMITATED_NPC_DATA(253), // v271 (v265 247): v271 handler 0x1459CB5D0 forwards the packet to the UI manager (case 253)
    CROSS_HUNTER_SHOP_RESULT(UNVERIFIED_OP), // v232 253
    MONSTER_BOOK_SET_CARD(254), // v271 (v265 248): ctx table, reads as v265
    UNK_218(UNVERIFIED_OP), // v232 254
    MONSTER_BOOK_SET_COVER(255), // v265 249
    HOUR_CHANGED(256), // v271 (v265 250): ctx table, reads as v265; T value (HOUR_CHANGE): short day of week, short hour
    MINIMAP_ON_OFF(257), // v265 251
    CONSULT_AUTH_KEY_UPDATE(258), // v265 252
    BOARD_GAME_RESULT(UNVERIFIED_OP), // v232 258
    CLASS_COMPETITION_AUTH_KEY_UPDATE(259), // v265 253
    YUT_GAME_RESULT(UNVERIFIED_OP), // v232 259
    WEB_BOARD_AUTH_KEY_UPDATE(260), // v265 254
    // v271 255..266 -> 261..272 (ctx table, read sequences equal; 260 / 266 read nothing)
    SESSION_VALUE(261), // v271 (v265 255): ctx table, reads as v265; S S
    PARTY_VALUE(262), // S S
    FIELD_SET_VARIABLE(263), // v271 (v265 257): ctx table, reads as v265; S S
    FIELD_VALUE(264), // v271 (v265 258): ctx table, reads as v265; S S
    BONUS_EXP_RATE_CHANGED(265), // 4 4 4 8 1 1
    SET_BUY_EQUIP_EXT(266), // v271 (v265 260): ctx 0x1459cd0e0
    SET_PASSENGER_REQUEST(267), // v271 (v265 261): 4
    SCRIPT_PROGRESS_MESSAGE_BY_SOUL(268), // v271 (v265 262): S 4
    SCRIPT_PROGRESS_MESSAGE(269), // v271 (v265 263); reads S, as v265
    SCRIPT_PROGRESS_ITEM_MESSAGE(270), // v271 (v265 264): 4 S
    STATIC_SCREEN_MESSAGE(271), // v271 (v265 265): 1 S 1
    OFF_STATIC_SCREEN_MESSAGE(272),
    WEATHER_EFFECT_NOTICE(273), // v271 (v265 267): ctx table 0x1459cd590, S 4 4 1 as v265
    WEATHER_EFFECT_NOTICE_Y(274), // v271 (v265 268): ctx table 0x1459cd9a0, S 4 4 4 as v265
    UNK_200_259(UNVERIFIED_OP), // v232 275
    UNK_200_260(UNVERIFIED_OP), // v232 276
    WILD_HUNTER_INFO(UNVERIFIED_OP), // v265 279; v265 279 has no v271 case (v271 279 is the v265 272 message)
    WHITE_ADDITIONAL_CUBE_RESULT(UNVERIFIED_OP), // v232 279
    PROGRESS_MESSAGE_FONT(280), // v271 (v265 273): ctx table 0x1459ce3a0, 4 4 4 4 1 S as v265
    DATA_CRC_CHECK_FAILED(281), // v271 281: dispatch 0x1459CE720, same 18-call body as v265 274 (0x1453C5940), one field offset moved; 274 is WEATHER_EFFECT_NOTICE_Y
    MEMORIAL_CUBE_RESULT(UNVERIFIED_OP), // v232 281
    UNK227_280(UNVERIFIED_OP), // v232 283
    UNK227_281(UNVERIFIED_OP), // v232 284
    REBIRTH_FLAME_RESULT(UNVERIFIED_OP), // v232 285
    ZERO_INFO(286), // v271 (v265 281): ctx 0x145a5d950 -> ZeroInfo decoder; v265 279/280 have no v271 case
    ZERO_WP(287), // v271 (v265 282): ctx 0x145a5dc60, 4 (WP) as v265 0x14545d8d0
    UNK_200_270(UNVERIFIED_OP), // v232 287
    ZERO_INFO_SUB_HP(288), // v271 (v265 283): ctx 0x145a5dcd0, 1 4
    RESET_STATE_FOR_OFF_SKILL(UNVERIFIED_OP), // v232 288
    CLEAR_ANNOUNCED_QUEST(289), // v271 (v265 284): ctx 0x1459cf0e0, no body
    SET_OFF_STATE_FOR_OFF_SKILL(UNVERIFIED_OP), // v232 289
    EVOLVING_RESULT(UNVERIFIED_OP), // v232 292
    RESULT_INSTANCE_TABLE(294), // v271 (v265 288): S 4 4 1 4, reads as v265
    COOL_TIME_SET(295), // v271 295: dispatch 0x145B32C00, same 4-insn thunk as v265 289 (0x1455290F0); 289 is CLEAR_ANNOUNCED_QUEST
    ITEM_POT_CHANGE(296), // v265 290
    RESET_BUY_LIMIT_COUNT(UNVERIFIED_OP), // v265 296; v271 296 is ITEM_POT_CHANGE
    SET_ITEM_COOL_TIME(297), // v265 291
    SET_AD_DISPLAY_INFO(298), // v271 (v265 292): dispatch 0x145AAB180 is an exact twin of v265 0x1454A8050; fork's 292 is the v265 287 UNK_BOSS_OUT body
    SET_AD_DISPLAY_STATUS(299), // v265 293
    SET_MAPLE_STYLE_INFO(300), // v271 300: dispatch 0x145AABA60, same 2-call body as v265 294 (0x1454A8940); 294 is RESULT_INSTANCE_TABLE
    SET_BUY_LIMIT_COUNT(301), // v265 295
    UPDATE_UI_EVENT_LIST_INFO(303), // v265 297
    DOJANG_RANKING_RESULT(304), // v271 (v265 298): ctx table, reads as v265; v265 RE 0x1453c6460: Mu Lung Dojo ranking window (UIWindow4.img/muruengRank)
    GHOST_PARK_RANKING_RESULT(305), // v265 299 // v265 RE 0x1453c67d0: Ghost Park ranking window (UIGhostPark.img/Rank), not sent
    SET_GACHAPON_FEVER_TIME_INFO(UNVERIFIED_OP), // v232 305
    UNLINKED_SKILL_INFO(307), // v271 (v265 301): send-site/ctx alignment; v232 225, IDB medium
    RESULT_SET_STEAL_SKILL(309), // v271 (v265 303): ctx table, reads as v265
    SLASH_COMMAND(311), // v265 305
    START_NAVIGATION(312), // v265 306
    FUNCKEY_SET_BY_SCRIPT(314), // v271 (v265 308): ctx 0x145ab4f40
    CHARACTER_POTENTIAL_SET(315), // v271 (v265 309): send-site/ctx alignment
    CHARACTER_POTENTIAL_RESET(316), // v271 (v265 310): send-site/ctx alignment
    CHARACTER_POTENTIAL_CHANGE_PRESET(317), // v271 (v265 311): send-site/ctx alignment; T value, sent at login
    CHARACTER_HONOR_EXP(318), // v271 (v265 312): send-site/ctx alignment
    READY_FOR_RESPAWN(319), // v271 (v265 313): dispatch 0x1459E5D60 == v265 handler 0x145AB4400 (sim 0.793); 313 runs a different reader
    READY_FOR_RESPAWN_BY_POINT(320), // v265 314
    OPEN_READY_FOR_RESPAWN_UI(321), // v271 (v265 315): send-site/ctx alignment
    CHARACTER_HONOR_GIFT(322), // v271 (v265 316): send-site/ctx alignment
    SET_CASH_ITEM_NOTICE(323), // v271 323: dispatch 0x145AAB370, same 3-call body as v265 317 (0x1454A8250)
    SET_SPECIAL_CASH_ITEM(324), // v271 324: dispatch 0x145AAB3C0, same 5-call body as v265 318 (0x1454A82A0)
    SHOW_EVENT_NOTICE(325), // v271 (v265 319): dispatch 0x145AAB460 == v265 handler 0x1459E5D60 (sim 0.533)
    VALUE_PACK_RESULT(327), // v271 327: dispatch 0x1459E7840, same 6-call body as v265 321 (0x1453DE4F0)
    UNK_200_309(UNVERIFIED_OP), // v232 327
    NAVI_FLYING_RESULT(328), // v271 (v265 322): send-site/ctx alignment
    SET_EXCL_REQUEST_SENT(329), // v271 (v265 323): dispatch 0x1459E7920, 21 insns identical to the v265 body; 323 is SET_CASH_ITEM_NOTICE
    LIMIT_GOODS_NOTICE_RESULT(UNVERIFIED_OP), // v232 329
    CHECK_WEDDING_EX_RESULT(330), // v265 324
    UNIVERSE_BOSS_POSSIBLE(UNVERIFIED_OP), // v232 330
    BINGO_RESULT(331), // v265 325
    UNIVERSE_BOSS_IMPOSSIBLE(UNVERIFIED_OP), // v232 331
    BINGO_CASSANDRA_RESULT(332), // v265 326
    UPDATE_VIP_GRADE(333), // v265 327
    MESO_RANGER_RESULT(334), // v271 (v265 328): send-site/ctx alignment
    SET_MAPLE_POINT(335), // v265 329; v271 reads 1 (1 = shown balance), 4
    PENDANT_SLOT_INC_RESULT(UNVERIFIED_OP), // v232 336
    SET_MIRACLE_TIME_INFO(338), // v271 338: dispatch 0x142FFA9F0, same 11-call body as v265 332 (0x142A2CB30); 332 is BINGO_CASSANDRA_RESULT
    HYPER_SKILL_RESET_RESULT(339), // v271 (v265 333): ctx table, reads 1 4 1
    GET_SERVER_TIME(341), // v271 (v265 335): ctx table, reads as v265
    RETURN_EFFECT_MODIFIED(UNVERIFIED_OP), // v265 341; v271 341 is GET_SERVER_TIME
    GET_CHARACTER_POSITION(342), // v265 336
    RETURN_EFFECT_CONFIRM(346), // v271 (v265 340): ctx table, reads as v265
    ELITE_MOB_WMI(UNVERIFIED_OP), // v232 347
    BLACK_CUBE_RESULT(348), // v271 (v265 342): ctx 0x145b39140; memorial entry (0x140792530) gains a trailing 4, window body as v265
    MEMORIAL_CUBE_MODIFIED(349), // v271 (v265 343): ctx 0x145b392e0, no body
    UNK_200_332(UNVERIFIED_OP), // v232 349
    UNK_200_333(UNVERIFIED_OP), // v232 350
    BLACK_FLAME_OF_RESURRECTION_MODIFIED(352), // v271 (v265 346): ctx 0x145b20e20, 4 type; 1 = flame info [B8 2 4 2, 1 n, n x 8] + window, 2 = close; type 0 reads nothing now
    DRESS_UP_INFO_MODIFIED(354), // v271 354: dispatch 0x1459CA740 is the v265 347 body (0x1453C1AD0): 111 insns, 13 calls map 1:1; 352 is BLACK_FLAME_OF_RESURRECTION_MODIFIED
    GET_SAVED_URSUS_SKILL(UNVERIFIED_OP), // v232 397; its v265 347 collided with DRESS_UP_INFO_MODIFIED, and v271 354 is that same handler (0x1459CA740), so no client opcode is known for it
    MIRACLE_CIRCULATOR_RESULT(355), // v271 (v265 348): ctx table, reads as v265
    UNK_200_338(UNVERIFIED_OP), // v232 355
    ISSUE_RELOGIN_COOKIE(356), // v271 (v265 349): ctx table, S 4 S as v265
    UNK_200_339(UNVERIFIED_OP), // v232 356
    AVATAR_PACK_TEST(357), // v271 (v265 350): dispatch 0x145AAA7A0 == v265 handler 0x1454A76D0 (sim 0.851); 350 runs an unrelated reader
    IRON_BOX_PACKET(UNVERIFIED_OP), // v232 357
    ACTION_BAR_RESULT(358), // v271 (v265 351): ctx table, reads as v265
    GUILD_CONTENT_RANK_RESULT(359), // v271 (v265 352): read sequence equal
    GUILD_SEARCH_RESULT(360), // v271 (v265 353): read sequence equal
    COMPLETE_NPC_SPEECH_SUCCESS(UNVERIFIED_OP), // v265 361; v271 sends it as NPC_SPEECH_DONE (dispatch 0x1459E89B0 is the twin of v265 0x1453DFA60), and v271 361 is the butterfly customize body
    COMPLETE_SPECIAL_CHECK_SUCCESS(362),
    HALLOWEEN_CANDY_RANKING_RESULT(363), // v271 363: dispatch 0x1459CF830, same 11-call body as v265 355 (0x1453C6A40); 355 is MIRACLE_CIRCULATOR_RESULT
    GET_REWARD_RESULT(364), // v265 356
    MENTORING(365), // v265 357
    GET_LOTTERY_RESULT(366), // v265 358
    CHECK_PROCESS_RESULT(367), // v271 367: dispatch 0x145AB88B0, same 2-call body as v265 359 (0x1454B4020); 359 is GUILD_CONTENT_RANK_RESULT
    // unsure about the 3 here
    NPC_SPEECH_DONE(369), // v271 ctx 0x1459e89b0: empty; refreshes the quest window after USER_COMPLETE_NPC_SPEECH (GMS every time)
    QUEST_EX_RUN_ACT_RESULT(370), // v271 ctx 0x1459e89d0: 4 quest id; GMS answers QUEST_EX_RUN_ACT with it
    SET_ACCOUNT_INFO(371), // v265 363
    AVATAR_MEGAPHONE_RES(373), // v265 365
    SET_AVATAR_MEGAPHONE(374), // v271 (v265 366): ctx table, reads as v265
    CLEAR_AVATAR_MEGAPHONE(375), // v271 (v265 367): dispatch 0x145AB9C90 == v265 handler 0x145AB88B0 (sim 1.000)
    REQUEST_EVENT_LIST(376), // v271 (v265 368): ctx table, reads as v265
    EVENT_LIST(378), // v232 310
    UNK_372(380), // v271 (v265 372): ctx 0x1459f0a40, no body; T value, empty, sent at login
    LIKE_POINT(381), // v265 373
    SIGN_ERROR_ACK(382), // v265 374
    ASK_AFTER_ERROR_ACK(383), // v265 375
    EVENT_NAME_TAG(384), // v271 (v265 376): 5 x (S, 1), handler 0x1459e9cd0
    ACQUIRE_EVENT_NAME_TAG(385), // v271 (v265 377)
    JOB_FREE_CHANGE_RESULT(386), // v265 378
    EVENT_LOTTERY_OPEN(387), // v271 387: dispatch 0x145AB9DF0 matches v265 380 (0x1454B5940) instruction-for-instruction; 380 is UNK_372
    UNK218_378(UNVERIFIED_OP), // v232 387
    EVENT_LOTTERY_RESULT(388), // v271 388: dispatch 0x145AB9F60 matches v265 381 (0x1454B5AB0) instruction-for-instruction; 381 is LIKE_POINT
    ON_12TH_TREASURE_RESULT(UNVERIFIED_OP), // v232 390
    ON_12TH_TREASURE_BUFF(UNVERIFIED_OP), // v232 391
    SCREEN_MSG(398), // v265 391
    UNK227_394(UNVERIFIED_OP), // v232 398
    MVP_ALARM(UNVERIFIED_OP), // v232 401
/* 403*/    CASH_SHOP_PREVIEW_INFO(403), // v271 (v265 395): ta-ctx 0x1459dd310; box section changed (see cashShopPreviewInfo)
    CHANGE_SOUL_COLLECTION_RESULT(404), // v271 (v265 396): ctx 0x1459cad10; reads changed (v265 4 4, v271 1 + 5 x B4)
    MASTER_PIECE_REWARD(406), // v265 398
    UNK_364(UNVERIFIED_OP), // v232 406
    BOSS_ARENA_MATCH_SUCCESS(407), // v271 (v265 399): dispatch 0x145ABAB50 (sim 1.000 against the v265 handler of 399); 399 is an unrelated reader
    BOSS_ARENA_MATCH_FAIL(408), // v271 (v265 400): dispatch 0x145ABABA0 (sim 0.848); 400 falls through to the dispatcher default 0x146512EC0
    BOSS_ARENA_MATCH_REQUEST_DONE(409), // v265 401
    UNK214_415(UNVERIFIED_OP), // v232 409
    USER_SOUL_MATCHING(410), // v265 402
    CATAPULT_UPGRADE_SKILL(411), // v271 411: dispatch 0x1459F0FB0 matches v265 403 (0x1453E7C10) instruction-for-instruction; 403 is CASH_SHOP_PREVIEW_INFO
    UNK203_402(UNVERIFIED_OP), // v232 411
    CATAPULT_RESET_SKILL(412), // v265 404
    RECYCLE_ITEM(UNVERIFIED_OP), // v232 412
    PARTY_QUEST_RANKING_RESULT(413), // v271 (v265 405): ctx 0x145aa8360, reads as v265
    UNK203_404(UNVERIFIED_OP), // v232 413
    SET_COORDINATION_CONTEST_INFO(414), // v265 406
    WORLD_TRANSFER_RESULT(415), // v265 407
    TRUNK_SLOT_INC_RESULT(416), // v265 408
    RANDOM_PORTAL_NOTICE(417), // v271 417: dispatch 0x1459F1290 matches v265 409 (0x1453E7EF0) instruction-for-instruction; 409 is BOSS_ARENA_MATCH_REQUEST_DONE
    NODE_VANISHED(UNVERIFIED_OP), // v232 419
    EQUIPMENT_ENCHANT(420), // v271 (v265 412): ctx 0x1459ead40, renumbered sub-types (EquipmentEnchantType)
    CREATE_KOREAN_JUMPING_GAME(UNVERIFIED_OP), // v265 420; v271 420 is EQUIPMENT_ENCHANT
    UNK_232_421(UNVERIFIED_OP), // v232 421
    TOP_TOWER_RANK_RESULT(422), // v271 (v265 414): ctx table, reads as v265
    UNK203_412(UNVERIFIED_OP), // v232 422
    FRIEND_TOWER_RANK_RESULT(423), // v271 (v265 415): ctx table, reads as v265
    UNK203_413(UNVERIFIED_OP), // v232 423
    TOWER_RESULT_UI_OPEN(424), // v271 (v265 416): ctx table, reads as v265
    UNK203_414(UNVERIFIED_OP), // v232 424
    MANNEQUIN_RESULT(425), // v271 (v265 417): ctx 0x145af6520, B1 B1 B4 as v265; v265 RE CWvsContext 0x1454f28c0: B1 type, B1, B4, then more; messages via 0x1428b5020 (mannequin StringPool 0x3700..)
    UNK223_416(UNVERIFIED_OP), // v232 425
    MANNEQUIN_UPDATE(426), // v271 (v265 none): ctx 0x145af6590 OnUnk426, B1 type B1 action B1 mode; mode 0 = full container (2 bytes + 48 records), mode 2 = slot + look, mode 3 = delete -> look
    UNK227_422(UNVERIFIED_OP), // v232 427
    CREATE_SWING_GAME(428), // v271 (v265 421): dispatch 0x1459F1510 (sim 0.623); 421 runs an unrelated reader
    UNK227_423(UNVERIFIED_OP), // v232 428
    INAPPROPRIATE_BAN_MSG(UNVERIFIED_OP), // v232 429
    OPEN_CHUCHU_SHOP(UNVERIFIED_OP), // v232 430
    UPDATE_MAPLE_TV_SHOW_TIME(431), // v265 423
    RESET_CHUCHU_INVENTORY(UNVERIFIED_OP), // v232 431
    RETURN_TO_TITLE(432), // v271 (v265 424): CWvsContext 0x145abb060, no payload
    OPEN_UI_TEST(UNVERIFIED_OP), // v265 432; v271 432 is RETURN_TO_TITLE
    UI_STAR_PLANET_MINI_GAME_RESULT(UNVERIFIED_OP), // v265 432; v271 432 is RETURN_TO_TITLE
    UNK203_419(UNVERIFIED_OP), // v232 432
    RETURN_TO_CHARACTER_SELECT(433), // v271 (v265 425): CWvsContext 0x1459f1720, no payload; the client answers with In 587
    UNK203_420(UNVERIFIED_OP), // v232 433
    FLAME_WIZARD_FLAME_WALK_EFFECT(434), // v271 (v265 426): ctx table, reads as v265
    FLAME_WIZARD_FLARE_BLINK(435), // v271 (v265 427): ctx table, reads as v265
    UNK203_423(UNVERIFIED_OP), // v232 435
    UNK203_424(UNVERIFIED_OP), // v232 436
    SUMMONED_AVATAR_SYNC(437), // v265 429
    CASH_SHOP_EVENT_INFO(438), // v271 (v265 430): dispatch 0x1459F7420 runs the same 5 calls as the v265 handler (57 vs 46 insns); 430 runs an item-list loop
    UNK203_426(UNVERIFIED_OP), // v232 438
    BLACK_LIST(439), // v271 (v265 431): read sequence equal
    UNK203_427(UNVERIFIED_OP), // v232 439
    UNK203_428(UNVERIFIED_OP), // v232 440
    SWITCH_BLACK_LIST_VIEW(441), // v271 (v265 433): read sequence equal
    UNK203_429(UNVERIFIED_OP), // v232 441
    UNK203_430(UNVERIFIED_OP), // v232 443
    UNK203_431(UNVERIFIED_OP), // v232 444
    UNK203_432(UNVERIFIED_OP), // v232 445
    UNK203_433(UNVERIFIED_OP), // v232 446
    COMBAT_POWER_MSG(UNVERIFIED_OP), // v232 447
    SCROLL_UPGRADE_FEVER_TIME(448), // v265 434
    UNK203_435(UNVERIFIED_OP), // v232 448; *
    TEXT_EQUIP_INFO(449), // v265 435
    UNK203_436(UNVERIFIED_OP), // v232 449
    TEXT_EQUIP_UI_OPEN(450), // v265 436
    UNK203_437(UNVERIFIED_OP), // v232 450
    CREATE_INGAME_CHAR_RESULT(UNVERIFIED_OP), // v232 451
    UNK203_439(UNVERIFIED_OP), // v232 452
    UNK203_440(UNVERIFIED_OP), // v232 453
    TRAINEE_INFO(UNVERIFIED_OP), // v232 454
    UNK212_460(UNVERIFIED_OP), // v232 455
    UNK212_461(UNVERIFIED_OP), // v232 456
    // blank
    UNK212_471(UNVERIFIED_OP), // v232 458; *
    BACK_SPEED_CTRL(459), // v265 445 // *
    RANK_POINT_RESULT(UNVERIFIED_OP), // v232 459
    SET_MAZE_AREA(460), // v265 446
    BRAWLOSSEUM_RANKING_RESULT(UNVERIFIED_OP), // v232 460
    CHARACTER_BURNING(461), // v271 (v265 447): ctx table, handler 0x1459f6ce0 reads 1 [4, stat extra 0x140782360]
    HERO_SQUAD_RESULT(UNVERIFIED_OP), // v232 461; *
    BATTLE_STAT_CORE_INFO(462), // v265 448
    UNK212_478(UNVERIFIED_OP), // v232 462
    BATTLE_STAT_CORE_ACK(463), // v265 449
    BEAUTY_COUPON_TEST_RESULT(464), // v271 (v265 450): context table, reads as v265 (unused)
    // blank
    UNK212_480(UNVERIFIED_OP), // v232 464
    HAIR_COLOR_CHANGE_RESULT(UNVERIFIED_OP), // v232 465
    CARTA_RESULT(UNVERIFIED_OP), // v232 466
    UNK209_472(UNVERIFIED_OP), // v232 467
    UNK203_444(UNVERIFIED_OP), // v232 467
    NICK_SKILL_EXPIRED(468), // v271 (v265 452): ctx 0x1459f8e70
    UNK203_443(UNVERIFIED_OP), // v232 468
    UNK203_445(UNVERIFIED_OP), // v232 468
    RANDOM_MISSION_RESULT(469), // v271 (v265 453): ctx table, reads as v265
    UNK203_446(UNVERIFIED_OP), // v232 469
    UNK212_486(UNVERIFIED_OP), // v232 470; * -8
    ITEM_COLLECTION_RESULT(471), // v265 455
    SHERANIAN_CULVERT_CAN_BE_ENTERED(UNVERIFIED_OP), // v232 471
    CHECK_COLLECTION_COMPLETE_RESULT(472), // v271 (v265 456): dispatch 0x145B33FA0 (sim 1.000)
    EMOTICON_RESULT(UNVERIFIED_OP), // v232 472; v265 is 541 (0x145518d30, byte sub-type 0..7) but our emoticonResult still writes v232 sub-types: bind with that rewrite
    ITEM_COLLECTION_LIST(473), // v265 457
    UNK212_489(UNVERIFIED_OP), // v232 473
    RECEIVE_TOADS_HAMMER_REQUEST_RESULT(474), // v271 (v265 458): ctx 0x145ac2000
    UNK218_471(UNVERIFIED_OP), // v232 474
    UNK218_472(UNVERIFIED_OP), // v232 475
    ARAN_DIRE_WOLF_CURSE(UNVERIFIED_OP), // v232 476
    // blank
    UNK220_476(UNVERIFIED_OP), // v232 478
    UNK220_477(UNVERIFIED_OP), // v232 479
    RECEIVE_HYPER_STAT_SKILL_RESET_RESULT(480), // v271 (v265 460): ctx table, reads 1 4 1
    NODE_STONE_OPEN_RESULT(UNVERIFIED_OP), // v265 480; v271 has no node results (ctx 499 -> 500 -> 501)
    UNK218_475(UNVERIFIED_OP), // v232 480
    UNK218_476(UNVERIFIED_OP), // v232 481
    NODESTONE_RESULT_BULK(UNVERIFIED_OP), // v265 481; v271 gemstones answer through MATRIX_RESULT 500 type 1 // v265 RE 0x1453c22b0: int, int n, n x (int icon, level, skill1, skill2, skill3)
    NODE_ENHANCE_RESULT(UNVERIFIED_OP), // v265 482; gone in v271
    UNK218_477(UNVERIFIED_OP), // v232 482
    INVENTORY_OPERATION_RESULT(483), // v265 463
    UNK223_483(UNVERIFIED_OP), // v232 483
    SET_ROLE_PLAYING_CHARACTER_INFO(484), // v271 (v265 464): dispatch 0x145AC7900 reads 4 1, stores them at [user+0x1554]/[user+0x4470] and compares 0x401 exactly like the v265 handler 0x1454C0B30 (sim 0.641; 464 is BEAUTY_COUPON_TEST_RESULT)
    NODE_DISASSEMBLE_RESULT(UNVERIFIED_OP), // v265 484; gone in v271
    UNK227_490(UNVERIFIED_OP), // v232 484
    NODE_CRAFT_RESULT(UNVERIFIED_OP), // v265 485; gone in v271
    MONSTER_COLLECTION_RESULT(486), // v271 (v265 466): context table, 4 4 4 4 4 4 1 S as v265
    UNK223_485(UNVERIFIED_OP), // v232 486
    TOWER_CHAIR_SETTING_RESULT(487), // v271 (v265 467): context table, next to 486
    NODE_OPEN_VMATRIX(UNVERIFIED_OP), // v265 487; not in the v271 ctx table
    UNK227_493(UNVERIFIED_OP), // v232 487
    UNK227_494(UNVERIFIED_OP), // v232 488
    NEED_CLIENT_RESPONSE(489), // v265 469
    UNK228_497(UNVERIFIED_OP), // v232 489
    CHARACTER_MODIFIED(490), // v271 (v265 470): dispatch 0x1459F6D50 (sim 0.767)
    UNK214_488(UNVERIFIED_OP), // v232 491
    UNK203_453(UNVERIFIED_OP), // v232 491
    TRADE_KING_SHOP_ITEM(492), // v271 (v265 472): ctx 0x1459fc320, reads as v265
    UNK214_489(UNVERIFIED_OP), // v232 492
    TRADE_KING_SHOP_RES(493), // v271 (v265 473): ctx table, reads as v265
    UNK214_490(UNVERIFIED_OP), // v232 493
    UNK214_491(UNVERIFIED_OP), // v232 494
    PLATFORMAR_ENTER_RESULT(495), // v271 (v265 475): ctx table, reads as v265
    PLATFORMAR_OXYZEN(496), // v271 (v265 476): ctx table, reads as v265
    // blank
    UNK203_449(UNVERIFIED_OP), // v232 497
    MATRIX_UPDATE(499), // v271 (v265 479): 0x1459cab50, core map 0x140793990 (CharacterData F50), 1, [4]; V271Matrix
    MATRIX_RESULT(500), // v271 only: 0x145817710, B4 type, B4 code, per-type body (V271Matrix.result)
    UNK203_461(UNVERIFIED_OP), // v232 503
    UNK203_462(UNVERIFIED_OP), // v232 504
    // blank
    UNK205_488(UNVERIFIED_OP), // v232 507
    TRANSFER_REQUEST_RESULT(UNVERIFIED_OP), // v232 508
    PERSONAL_SHOP_SHUTDOWN_BY_GM_MSG(UNVERIFIED_OP), // v232 509
    HACK_BLOCK_MSG(UNVERIFIED_OP), // v232 510
    PREMIUM_SILVER_BOX_RESULT(UNVERIFIED_OP), // v232 511
    UNION_ASSIGN_RESULT(512), // v271 (v265 501): 0x1459feb50, union block 0x1411c6b70 read as v265 0x1411ffc00
    NEW_YEAR_CARD_SEND_RESULT(UNVERIFIED_OP), // v232 512
    GIFT_BLESSING_RESULT(UNVERIFIED_OP), // v232 513
    UNION_PRESETS(515), // v271 (v265 504): case stub -> 0x145a26ff0, preset record 0x1411c7de0 (1, 8 x 4, 4 n members) // v265 RE 0x14541d9f0 -> 0x1412011b0: 5 Legion presets (byte has, 8 ints grid, int n members, ...); not champions
    // blank
    CREW_RESULT(UNVERIFIED_OP), // v232 515
    UNION_PRESET_INFO_RESULT(516), // v271 (v265 505): case stub -> 0x145a27030 (4, preset record)
    TRANSFORM_POTION(UNVERIFIED_OP), // v232 516
    CANCEL_REQUEST_RESULT(UNVERIFIED_OP), // v232 517
    MAPLE_TEAM_LETTER(UNVERIFIED_OP), // v232 518
    UNK203_477(UNVERIFIED_OP), // v232 519
    POWER_CRYSTAL_INFO(520), // v271 (v265 509): 0x1459ffe00, records 0x140798d60 (B8 4 4 8 B8 B8) as v265
    PAM_SONG_INTERVENE(UNVERIFIED_OP), // v232 520
    CASH_GACHAPON_RESULT(521), // v271 (v265 510): 0x145b08d20, 1 1 4 box, 4 n, n x (item, 4); plays the box's UIGachapon window (ssb, wonderBerry ...)
    RELOAD_MAP(UNVERIFIED_OP), // v232 521
    SUMMON_SKILL_RESULT(UNVERIFIED_OP), // v232 525
    UNK203_485(UNVERIFIED_OP), // v232 526
    UPDATE_DBCHAR(UNVERIFIED_OP), // v232 527
    MAGIC_GACHAPON_WHEEL_MSG(UNVERIFIED_OP), // v232 528
    FIRST_ENTER_REWARD(UNVERIFIED_OP), // v232 529
    ACCEPT_SOME_QUEST(UNVERIFIED_OP), // v232 530
    UNK203_491(UNVERIFIED_OP), // v232 531
    UNK203_492(UNVERIFIED_OP), // v232 532
    UNK203_493(UNVERIFIED_OP), // v232 533
    UNK203_494(UNVERIFIED_OP), // v232 534
    UNK203_499(UNVERIFIED_OP), // v232 537
    UNK203_500(UNVERIFIED_OP), // v232 538
    UNK203_501(UNVERIFIED_OP), // v232 539
    UNK203_502(UNVERIFIED_OP), // v232 540
    UNK203_503(UNVERIFIED_OP), // v232 541
    UNK203_504(UNVERIFIED_OP), // v232 542
    UNK203_505(UNVERIFIED_OP), // v232 543
    UNK203_538(UNVERIFIED_OP), // v232 546
    UNK203_539(UNVERIFIED_OP), // v232 547
    UNK203_540(UNVERIFIED_OP), // v232 548
    UNK220_539(UNVERIFIED_OP), // v232 549
    UNK227_554(UNVERIFIED_OP), // v232 550
    // ok: the method's reply, B mask, [1: 4 result (0 = ok)] [2: 4 rank up] [4: 8] [8: 4]; any reply clears the request lock
    SKILL_USE_COUNT(553), // v271 (v265 544): ctx table, byte reset, int n, n x (int skill, int uses left)
    SET_FARM_FIELD(UNVERIFIED_OP), // v232 553
    EXTRA_SYSTEM_RESULT(554), // v271 (v265 545): ctx 0x145a2a700; reads as v265 plus one int; T value; the client's case opens with int, short (Mo Xuan's gauge)
    // v265 class packets: the reference tree's values
    POTENTIAL_RPC_RESULT(555), // v271 ctx case 0x1459a0fee -> 0x1467def00: 4 call id, 4 status (0 = ok, else the call fails),
    LINKED_SKILL_INFO(565), // v271 (v265 556): 0x145b39820, types 0-4 read as v265 0x1455302d0
    SUMMON_ITEM_UNAVAILABLE(UNVERIFIED_OP), // v232 565
    LINK_SKILL_RESULT(566), // v271 (v265 557): ctx table, 0x145b39e30 reads B4 as v265 0x145530760
    V232_SOME_DEBUG_COMMAND(UNVERIFIED_OP), // v232 567
    UNK_517(UNVERIFIED_OP), // v232 568
    UNK_518(UNVERIFIED_OP), // v232 569
    DEATH_PENALTY(570), // v271 (v265 561): ctx table, reads as v265; v265 RE 0x145406d70: short type (0/1 = 4 ints, 2/3 clear), 4 ints
    STACK_SKILL_REQUEST_RESULT(UNVERIFIED_OP), // v265 570; v271 570 is DEATH_PENALTY // T value; the client's case opens with int, byte
    UNK_519(UNVERIFIED_OP), // v232 570
    ACHIEVEMENT_UPDATE(571), // v271 (v265 562): ctx 0x145a10730; reads changed (4 S 4 1 more at the end); T value, sent at login
    UNK_520(UNVERIFIED_OP), // v232 571
    UNK_521(UNVERIFIED_OP), // v232 572
    SIXTH_SKILL_COUNT(573), // v271 ctx 0x145b63070: int n, n x (int skill, int uses); GMS sends each Ascent skill's 6thCount at a boss entry
    UNK_522(UNVERIFIED_OP), // v232 573
    ADMIN_RESULT(UNVERIFIED_OP), // v232 576
    QUIZ(UNVERIFIED_OP), // v232 577
    UNK214_575(UNVERIFIED_OP), // v232 580
    BOSS_CLEAR_RECORD_TABLE(581), // v271 (v265 568): user dispatcher 0x14599f4f0 case 581 -> 0x1406cfe50: 4 n, n x (4 mobId, 4 record, 4 second record, 4)
    UNK214_576(UNVERIFIED_OP), // v232 581
    GUILD_CONTENTS_TABLE(582), // v271 (v265 569): ctx case 582 -> 0x14086c550 (v265 0x140734590) + B8 B8 4 4 4 4 4 at the end
    UNK214_577(UNVERIFIED_OP), // v232 582
    MESO_FLAME_ITEM(583), // v271 only: ctx 0x1421e2950, 4 flame id; a bonus-stat reset for mesos sends -id (0 = off)
    STACK_OVER_TIME_SKILL_RESULT(586), // v271 ctx 0x145b62b00: 4 skill (or key < 0), 1 restart, 4 (-1 for skills); GMS answers each stack-over-time increase request
    UNK214_581(UNVERIFIED_OP), // v232 586
    UNK214_582(UNVERIFIED_OP), // v232 587
    UNK214_583(UNVERIFIED_OP), // v232 588
    HYPER_STAT_SKILL_UPDATE_RESULT(589), // v271 (v265 572), handler 0x145a12270 reads as v265; v265 RE 0x145407ea0: byte preset, byte has, presets x (int n, n x 4 4 4)
    UNK214_584(UNVERIFIED_OP), // v232 589
    UNK214_585(UNVERIFIED_OP), // v232 590
    // back
    UNK214_587(UNVERIFIED_OP), // v232 592
    QUICKSLOT_INIT(UNVERIFIED_OP), // v232 593; v265 sends the quick slots in FUNC_KEY_MAPPED_MAN_INIT
    UNK_545(UNVERIFIED_OP), // v232 596
    // HEXA Matrix and Union Artifacts: v271 numbers from the ctx table, handlers compared with v265's
    HEXA_MESSAGE(597), // v271 (v265 581): 0x145a03920, B4 type, B4 code, tail 0x142cc2120 as v265
    HEXA_SKILL_UPDATE(598), // v271 (v265 582): 0x145a039d0, skills 0x140793f80 (= F59) + 1
    HEXA_STAT_UPDATE(599), // v271 (v265 583): 0x145a03c60, stats 0x140794710 (= F60)
    DESTROY(UNVERIFIED_OP), // v232 599
    HEXA_MATRIX_INFORMATION(600), // v271 (v265 584): 0x14298d220, two new tables after the upgrade costs
    HEXA_MATRIX_ACTIVATION_RESULT(601), // v271 (v265 585): 0x145aa8ee0, 1
    HEXA_SKILL_ERDA_CONVERSION(603), // v271 (v265 586): 0x1426f84c0, record vector 0x140ecf810 as v265
    UNION_ARTIFACT_RESULT(604), // v271 (v265 587): 0x145a03d90, 4 n {4, 1407950f0: 5 x 4, B8}
    HEKATON_FIELD_CHANGE(UNVERIFIED_OP), // v232 604
    UNION_ARTIFACT_INFORMATION(605), // v271 (v265 588): 0x145024580, 4 n {4 4 4}
    UNION_ARTIFACT_MSG(606), // v271 (v265 589): 4 4 4
    UNION_ARTIFACT_UPDATE(607), // v271 (v265 590): case 0x145a04110 -> 0x144f0d9f0 as v265, only while the artifact window exists
    UNION_ARTIFACT_QUEST_MSG(608), // v271 (v265 591): B4, 4 4 4 4 4
    UNION_CHAMPION_RESULT(610), // v271 (v265 592): send-site/ctx alignment; v265 RE 0x1421e6560: int type 0/1 (int), 2 (int; 0 -> byte, byte)
    UNK_562(UNVERIFIED_OP), // v232 614
    SET_PHYSICAL_WORLD_ATTACH(616), // v271 (v265 600): the field-stage switch base moved from 0x90 to 0x94
    UNK_510(UNVERIFIED_OP), // v232 616
    UNK_209_605(UNVERIFIED_OP), // v232 618
    UPDATE_VOYAGE_INFO(619), // v271 (v265 603): ctx 0x1459ac3e0; encoder does not match yet (route unlock leaves a byte, inventory info reads past the end)
    SET_MIRROR_DUNGEON_INFO(UNVERIFIED_OP), // v232 623
    USER_SWITCH_CHARACTER_RESULT(625), // v271 (v265 609): CWvsContext table, reads 1 4 1 S as v265
    DAMAGE_SKIN_SAVE_RESULT(626), // v271 (v265 610): context table, 1 1 1 as v265
    MAPLE_PLANNER_UPDATE(629), // v271 (v265 613): CUserLocal dispatcher 0x14599f4f0 case 629 -> 0x142b1b080: 4 request, 4 result (0 = ok)
    CHAT_INDUCE_CLIENT_CONNECT(UNVERIFIED_OP), // v232 630
    USER_OPEN_QUICK_MOVE(634), // v271 (v265 619): ctx table, reads as v265; T value: answer to USER_OPEN_QUICK_MOVE_REQUEST
    SKILL_SEQUENCE_SKILLS(636), // v271 (v265 621): ctx 0x1459cb5f0, reads as v265; T value, sent at login
    SKILL_SEQUENCE_BUFFS(637), // v271 (v265 622): ctx 0x1459cb630, reads as v265; T value, sent at login
    // Back
    UNK_209_625(UNVERIFIED_OP), // v232 637
    UNK_585(UNVERIFIED_OP), // v232 638
    REN_IMUGI_SPIRIT_SWORD_REQUEST(UNVERIFIED_OP), // T 639
    UNK_586(UNVERIFIED_OP), // v232 639
    UNK_587(UNVERIFIED_OP), // v232 640
    UNION_CHAMPION_INFO(644), // v271 (v265 626): ctx alignment, same handler as 645; v265 RE 0x1421e6fe0 -> 0x1421e8e40: int part (0-6), int, int slot (<6), short has, part body (0 = CharacterData decode)
    UNION_CHAMPION_RAID_RESULT(645), // v271 (v265 627): send-site/ctx alignment; v265 RE 0x1421e6fe0: int type switch
    UNK_594(UNVERIFIED_OP), // v232 647
    UNK_595(UNVERIFIED_OP), // v232 648
    // Contributed by Arixal (from GitLab).
    SKILL_CHAIN_RESULT(656), // v271: ctx 0x145b64c70: int type; 0 = byte ok, int skill (ok clears the next step's pending flag, 0 rolls back a step); 1 = int root skill (resets the chain)
    // blank
    UNK_606(UNVERIFIED_OP), // v232 659
    UNK_608(UNVERIFIED_OP), // v232 661
    VERUS_HILLA_FIELD_MAYBE_UNK218_648(UNVERIFIED_OP), // v232 663
    VERUS_HILLA_FIELD_MAYBE_UNK218_649(UNVERIFIED_OP), // v232 664
    // back
    UNK218_650(UNVERIFIED_OP), // v232 665
    UNK218_651(UNVERIFIED_OP), // v232 666
    UNK227_671(UNVERIFIED_OP), // v232 667
    // blank
    UNK218_653(UNVERIFIED_OP), // v232 669
    UNK218_654(UNVERIFIED_OP), // v232 670
    UNK218_655(UNVERIFIED_OP), // v232 671
    UNK228_680(UNVERIFIED_OP), // v232 672
    MOB_PATTERN_SYSTEM_HOTFIX(679), // v271 CWvsContext case 679 -> 0x14193aee0: D4 write offset (0 = first: + packed size), bytes; blob = WZ image of version-named overlays for Mob/BossPattern/PatternSystem.img, client answers In 1204; sent by PatternHotfixHandler when test.patternFile is set
    // prob an inbetween function here (UNK218_656~UNK218_662)
    UNK228_689(UNVERIFIED_OP), // v232 681
    UNK228_690(UNVERIFIED_OP), // v232 682
    UNK228_691(UNVERIFIED_OP), // v232 683
    UNK228_692(UNVERIFIED_OP), // v232 684
    UNK232_685(UNVERIFIED_OP), // v232 685
    UNK218_663(UNVERIFIED_OP), // v232 686; *
    UNK220_668(UNVERIFIED_OP), // v232 687
    // blank
    // blank
    SKIN_EQUIP_MESSAGE(690), // v271 0x1417063f0: mode 0 "The %s skin has been equipped", 1 "has been enabled"; 4 skin id
    UPDATE_USER_CUSTOM_TITLE(UNVERIFIED_OP), // v265 695; v265 695 has no v271 ctx case (v271 695 is another packet); v265 RE CWvsContext 0x1454106a0: record (4 4 S FT), opens the window
    POTION_POT_RESULT(696), // v271 (v265 652): ctx table, reads as v265; v265 RE 0x1454c8e60: 1 type; 0 -> 1 msg (1 no item, 2 full, 3 max capacity, 4 rusty, 5 not rusty, 6 empty, 8 blocked); 2 HP/MP saved, 3 capacity raised, 4 expiry extended (from the record 654 sent)
    SHOW_ITEM_SKILL_SOCKET_UPGRADE_EFFECT(UNVERIFIED_OP), // v232 697
    POTION_POT_UPDATE(698), // v271 (v265 654): ctx 0x145ad0b50; v265 RE 0x1454c9480: 4 item, 4 char id (key), 4 max, 4 hp, 4 mp, 8 FT start, 8 FT end; upserts the pot record
    SHOW_ITEM_SKILL_OPTION_UPGRADE_EFFECT(UNVERIFIED_OP), // v232 698
    // v265 RE: v232 495..505 sit at +157 here (652 pot, 655 report, 657 trade limit, 659 BBS, 662 potion discount)
    USER_REPORT_RESULT(699), // v265 655 // v232 498; v265 RE 0x1454cb450 ("You have been reported to the GM's by a user.")
    SHOW_ITEM_RELEASE_EFFECT(UNVERIFIED_OP), // v232 699; 951 is an empty stub in v265
    SHOW_ITEM_UNRELEASE_EFFECT(UNVERIFIED_OP), // v232 700
    // blank
    TRADE_MESO_LIMIT_REACHED(701), // v265 657 // v232 500; v265 RE 0x1454c95d0 ("Players that are Level 15 and below ... trade 1 million")
    SHOW_ITEM_MEMORIAL_EFFECT(UNVERIFIED_OP), // v232 702
    GUILD_BBS_RESULT(703), // v271 (v265 659): ctx -> 0x145a18f90, new board (GuildBBSPacket); v232 502; v265 RE 0x145525f30: byte type, 3 = pages (int, int, int n, ...), 7 = record
    SHOW_ITEM_ADDITIONAL_UN_RELEASE_EFFECT(UNVERIFIED_OP), // v232 703
    SHOW_ITEM_ADDITIONAL_SLOT_EXTEND_EFFECT(UNVERIFIED_OP), // v232 704
    SHOW_ITEM_FIRE_WORKS_EFFECT(UNVERIFIED_OP), // v232 705
    REDUCE_POTIONS_PRICE_BY_EQUIP(706), // v265 662 // v232 505; v265 RE 0x14540dd40 ("For equipping %s, potions will be discounted %d%%")
    SHOW_ITEM_OPTION_CHANGE_EFFECT(UNVERIFIED_OP), // v232 706
    RED_CUBE_RESULT(UNVERIFIED_OP), // v232 707; no slot here in v265 (951..954 are four small handlers)
    BONUS_CUBE_RESULT(UNVERIFIED_OP), // v232 708; same
    HIT_BY_USER(UNVERIFIED_OP), // v232 709
    DOT_BY_USER(UNVERIFIED_OP), // v232 710
    RESET_ALL_DOT(UNVERIFIED_OP), // v232 711
    DAMAGE_BY_USER(UNVERIFIED_OP), // v232 712
    SHOW_PQ_REWARD(UNVERIFIED_OP), // v232 715
    DRAGON_BALL_UPDATE(717), // v271 0x145a19300: 4, 1, 1, 1; UI/UIWindow.img/DragonBall_A and DragonBall_B windows
    RATE_EVENT_NOTICE(725), // v271 0x145ad0d40: the [Event] drop/exp rate event will take place / has ended notices; schedule entries with %02d:%02d times
    SET_PREMIUM_DAMAGE_SKIN(UNVERIFIED_OP), // v232 728; none in v265 (970 is SET_SOUL_EFFECT): 969 carries the skin
    SET_UNIT_DAMAGE_SKIN(UNVERIFIED_OP), // v232 729; none in v265
    SET_OTHER_DAMAGE_SKIN(UNVERIFIED_OP), // v232 730; none in v265
    SET_USER_CUSTOM_TITLE(732), // v271 (v265 694): ctx 0x145a1b0c0, record (4 4 S B8) then 4 B4 4 (v265 4 4 4); v265 RE CWvsContext 0x145410420: record (4 4 S FT), int 0 ok / 1 + int reason
    TRANSFER_PVP_REQ_IGNORED(735),
    ERDA_LINK_INFO(736), // v271 (v265 699): ctx 0x145a1c6e0, reads as v265; v271 699 is USER_REPORT_RESULT; v265 RE CWvsContext 0x145413570: decoder 0x1406525d0 (int n, n x (stone, int, level), byte, int m, m x int)
    UNK205_700(UNVERIFIED_OP), // v232 736
    ERDA_LINK_RESULT(737), // v271 (v265 700): ctx 0x145a1c790, B4 as v265; v265 RE CWvsContext 0x145413620: int type; 0/1: int code + (stone, B4 category[, before, after]); 2 clears
    UNK209_711(UNVERIFIED_OP), // v232 737
    UNK_667(UNVERIFIED_OP), // v232 744
    SET_ACTIVE_EMOTICON_ITEM(UNVERIFIED_OP), // v232 745; v265 is 983 (CUser 0x144cf1820: int itemId 3120000..3129999, int value), not bound yet
    UNK209_720(UNVERIFIED_OP), // v232 746
    SKILL_ON_OFF_EFFECT_2(UNVERIFIED_OP), // v232 755
    UNK_683(UNVERIFIED_OP), // v232 757
    SPIRIT_FLOW_INFO(UNVERIFIED_OP), // v232 758
    UNK_685(UNVERIFIED_OP), // v232 759
    UNK_688(UNVERIFIED_OP), // v232 762
    UNK218_740(UNVERIFIED_OP), // v232 763
    UNK_689(UNVERIFIED_OP), // v232 764
    MACRO_SYS_DATA_INIT(765), // v265 718; v271 765 -> 0x145aab150 (macro reset + 0x143a9b9e0), layout unchanged
    // CStage::OnPacket        v223.1
    SET_FIELD(766), // v271 (v265 719): stage selector 0x14411e430 -> handler 0x14411e540; 720/721 are 767/768 there
    UNK205_730(UNVERIFIED_OP), // v232 766
    SET_AUCTION_HOUSE_FIELD(767), // v271 (v265 720): stage selector 0x14411e430 -> 0x144120fe0: B8, CharacterData mode 0 (the auction ctor reads nothing)
    STALK_RESULT(UNVERIFIED_OP), // v265 767; v271 767 is SET_AUCTION_HOUSE_FIELD
    UNK212_752(UNVERIFIED_OP), // v232 767
    // blank
    SET_CASH_SHOP(768), // v271 (v265 721): stage selector 0x14411e430 -> 0x144120c50 (B8, CharacterData, CCashShop ctor 0x14202d6e0)
    // CField_Massacre::OnPacket
    MASSACRE_INC_GAUGE(UNVERIFIED_OP), // v265 768; v271 768 is SET_CASH_SHOP
    FOOT_HOLD_MOVE(770),
    UNK228_782(UNVERIFIED_OP), // v232 774
    // CField_KillCount::OnPacket
    FIELD_KILL_COUNT(777),
    // CField::OnPacket        v223.1
    TRANSFER_FIELD_REQ_IGNORED(780), // v265 733 // *
    TRANSFER_CHANNEL_REQ_IGNORED(781), // v271 (v265 734): 0x1434889c0, 1 reason (2 = no cash shop)
    PV_P_MIGRATE_INFO_RESULT(782),
    FIELD_SPECIFIC_DATA(783), // v265 736
    GROUP_MESSAGE(784), // v271 (v265 737): field table 143489380; the item-linked form is the same packet
    FIELD_UNIVERSE_MESSAGE(785), // v265 738
    WHISPER(786), // v271 (v265 739): field table 14348a100
    FIELD_EFFECT(789), // v271 (v265 742): field table 0x14348c430; types 0-64 read as v265 (65 adds 4 floats, 66+ renumbered)
    BLOW_WEATHER(797), // v271 (v265 750): field 0x143497220, 4 S 4 1 B480 1 (v265 B120)
    PLAY_JUKE_BOX(798), // v265 751
    // CUser::OnSkillPetPacket      v223.1
    SKILL_PET_UNK(UNVERIFIED_OP), // v232 800
    FIELD_SET_DESC(802), // v265 755
    CLOCK(803), // v271 (v265 756): field vtable +0x2c0 -> 0x14347d5d0, as v265 (+0x278 -> 0x142eab520)
    SET_INTRUSION(805),
    SET_QUEST_CLEAR(808), // v271 (v265 761): field 0x14349c130, no body
    SET_QUEST_TIME(809), // v271 (v265 762): field table, reads as v265
    SET_OBJECT_STATE(810), // v271 (v265 763): field table, reads as v265
    COORDINATION_CONTEST_PACKET(813),
    UNK_730(UNVERIFIED_OP), // v232 817
    CORRECT_FOOT_HOLD_MOVE(818), // v265 771
    UNK_731(UNVERIFIED_OP), // v232 818
    UNK_732(UNVERIFIED_OP), // v232 819
    UNK_733(UNVERIFIED_OP), // v232 820
    FOOT_HOLD_APPEAR(821), // v271 (v265 774): field table, 4 S 1 4 4 4
    UNK_734(UNVERIFIED_OP), // v232 821
    UNK_736(UNVERIFIED_OP), // v232 822
    // blank
    SMART_MOB_NOTICE(825), // v271 (v265 778): field table, 4 4 4 4 S
    CHANGE_PHASE(826), // v271 (v265 779): field table 0x14349d190, 4 4 1
    CHANGE_MOB_ZONE(827), // v271 (v265 780): field vtable +0x2c8 -> 0x14349d4b0, 4 4 (v265 +0x280)
    // CUser::Unk      v223.1
    USER_UNK212_800(UNVERIFIED_OP), // v232 827
    FIELD_SKILL_DELAY(828),
    USER_UNK212_801(UNVERIFIED_OP), // v232 828
    USER_UNK212_802(UNVERIFIED_OP), // v232 829
    CUR_NODE_EVENT_END(830), // v271 (v265 783): field table, 1
    USER_UNK214_814(UNVERIFIED_OP), // v232 830
    CREATE_FORCE_ATOM(831), // v271 (v265 784): field table 0x1434ac3a0; layout changed (V271 force atom block)
    CREATE_ARK_FORCE_ATOM(832), // v271 (v265 785): field table, as v265
    REMOVE_FORCE_ATOM(833), // v271 (v265 786): field table 0x1434adcc0, as v265; v265 RE 0x1431f90d0 (via 0x142ebc230): int type; 1: faKey, chrId (remove); 2: faKey, chrId (fade); else n keys, chrId
    GUIDE_FORCE_ATOM(834), // v271 (v265 787): field table 0x1434adca0, as v265; v265 RE 0x1431f9020 (via 0x142ebc210): int faKey, int chrId, then the atom's own reader (type, ...)
    SET_ACHIEVE_RATE(836), // v271 (v265 789): field table, 4
    SET_QUICK_MOVE_INFO(837), // v271 (v265 790): field table, as v265
    CREATE_OBSTACLE(838), // v271 (v265 791): field table 0x1435d9c10, as v265
    CLEAR_OBSTACLE(840), // v271 (v265 793): field table, no payload
    B2_FOOT_HOLD_CREATE(845), // v271 (v265 796): field table, as v265
    DEBUFF_OBJ_ON(848), // v271 (v265 799): field table, 1 4 4 S S 1
    CREATE_FALLING_CATCHER(849), // v271 (v265 800): field table, S 4 4 n {4 4}
    CHASE_EFFECT_SET(850), // v271 (v265 801): field table, 4 2 4
    MESO_EXCHANGE_RESULT(851), // v265 802
    CANNOT_DROP(856), // v265 807
    FOOT_HOLD_OFF(857), // v271 (v265 808): field table, 4
    LADDER_ROPE_OFF(858), // v271 (v265 809): field table, 4
    MOMENT_AREA_OFF(859), // v271 (v265 810): field table, 4 S 4
    MOMENT_AREA_OFF_ALL(860), // v271 (v265 811): field table, as v265
    ELITE_STATE(863), // v271 (v265 814): field table, as v265
    PLAY_SOUND(864), // v271 (v265 815): field table, S
    STACK_EVENT_GAUGE(866), // v271 (v265 817): field table, 4
    SET_UNION_FIELD(867), // v265 818
    REMOTE_UNK_788(UNVERIFIED_OP), // v232 871
    EVENT_SWITCH_STATE(872), // v271 field table 0x1434bf720: 4 event id, 1 on, 8 end time when on; ids 0x48e 0x493 0x497 0x49e 0x507 0x52a 0x76c each set their own event flag
    ZODIAC_EVENT_STATE(873), // v271 field table 0x1434bf920: 4 event id, 1 on, 8 end time; looks up Etc/ZodiacEvent.img/<id>/info questId
    PUBLIC_SHARE_STATE(874), // v271 (v265 825): field table (out-map), 1 S S S S S S as v265
    FUNCTION_TEMP_BLOCK(875), // v271 (v265 826): field table, no reads as v265
    UI_STATUS_BAR_PACKET(876), // v271 (v265 827): field table (out-map), 4 1 B4 S 4 as v265
    FIELD_SKILL(879), // v271 (v265 830): field table, 4 4
    REMOTE_UNK_729(UNVERIFIED_OP), // v232 882
    WEATHER_PACKET_ADD(883), // v271 (v265 833): field table (out-map), 4 as v265
    REMOTE_UNK_800(UNVERIFIED_OP), // v232 883
    WEATHER_PACKET_REMOVE(884), // v271 (v265 834): field table (out-map), 4 as v265
    REMOTE_UNK_801(UNVERIFIED_OP), // v232 884
    WEATHER_PACKET_MSG(885), // v271 (v265 835): field table (out-map), 4 as v265
    REMOTE_UNK_802(UNVERIFIED_OP), // v232 885
    ADD_WRECKAGE(886), // v271 (v265 836): field table (out-map), 9 x 4 as v265
    DEL_WRECKAGE(887), // v271 (v265 837): field table (out-map), 4 4 1 1 [4] as v265
    CREATE_MIRROR_IMAGE(888), // v271 (v265 838): field table, as v265
    FUNCTION_FOOTHOLD_MAN(889), // v271 (v265 839): field table (out-map), 4 [4 S] as v265
    GROUP_CHAIR_SIT_RESULT(890), // v271 (v265 840): field table (out-map), chair record: seat 4 1 4 4 (v265 4 1 4), then 1 1 1 S (v265 1 S); v265 RE CField 0x142edb1a0: 4 chr, 1, 1, chair record(s) 0x14072eaf0
    GROUP_CHAIR_INVITATION_RESULT(891), // v271 (v265 841): field table (out-map), 4 1 [4] as v265; v265 RE CField 0x142edb3e0: int type, byte, [type 3: int]
    PAPULATUS_FIELD_OBJECT_CHANGED(894), // v271 (v265 844): Papulatus field 0x14367b010 -> 0x14367b160, as v265; v265 RE Papulatus field 0x14307bf50 -> 0x14307c0a0: int type 0/1/2/4/5/6 (3 unused), then by type
    // Somewhere
    VERUS_HILLA_FIELD_OBJECT_CHANGED(896), // v271 (v265 846): VH field 0x143199da0 -> 0x14319cee0, as v265; v265 RE (boss-mech): VH field 0x142bce9e0 -> 0x142bd1e50, int subtype
    EPIC_DUNGEON_FIELD(897), // v271 (v265 847): field class 0x1428dc8b0 -> 0x1428dcd80, reads equal; High Mountain fields 0x142349ab0: int type; 0 int x, int y, int max (heal altar); 1 int value; 2 byte success; 3 int id, int delayMs
    UNK227_894(UNVERIFIED_OP), // v232 897
    EPIC_DUNGEON2_OBJECTS(900), // v271 (v265 849): Angler field class 0x1428dd3a0 -> 0x1428ddb50, reads equal
    EPIC_DUNGEON2_OBJECT(901), // v271 (v265 850): 0x1428ddbc0, reads equal
    UNK_831(UNVERIFIED_OP), // v232 913
    UNK208_879(UNVERIFIED_OP), // v232 917
    UNK214_908(UNVERIFIED_OP), // v232 924
    // v265 0x145d5a500 on the field's actor channel (CField::OnPacket case 874): as USER_ACTOR_REPLICATION, no char
    FIELD_ACTOR_REPLICATION(928), // v271 (v265 874): field table 0x146685760 = the v265 0x145d5a500 actor stream; GMS on every field entry
    SET_NEXT_SHOOT_EX_JABLIN(UNVERIFIED_OP), // v232 930
    // v265 RE: Ranheim Defense field 286 (OnPacket 0x142777cf0)
    RANHEIM_DEFENSE_877(931), // v271 (v265 877): int state (2 running, 3 cleared), int fragments destroyed, int Light Magic charge 0-1000
    UNK221_914(UNVERIFIED_OP), // v232 932
    RANHEIM_DEFENSE_879(933), // v271 (v265 879): int char id, int launcher (the launcher fires)
    UNK221_915(UNVERIFIED_OP), // v232 933
    RANHEIM_DEFENSE_880(934), // v271 (v265 880): int launcher, int target mob object id (the shot hit)
    UNK232_934(UNVERIFIED_OP), // v232 934
    UNK_779(UNVERIFIED_OP), // v232 944
    UNK223_940(UNVERIFIED_OP), // v232 954
    UNK223_941(UNVERIFIED_OP), // v232 955
    // v271 field OnPacket 0x14345e5e0: 978-986 all go to the mob pattern selector (0x1434e6690: 4 mob id, then
    // 0x14289aa40 by op); the v232-era names above on these numbers are not what the v271 client reads there
    MOB_PATTERN_EVENT_RESULT(956), // v271 field 0x1434e5430: 4 mob, 4 run id (0x1428b3510), 4 event (0x1427e5ea0 -> run callback); 9: 4 facing (1 = left), 4 action group (0x141a0bb10); 10: 8 position, 4 key, 4 has facing, [4 facing] (0x141a0b7c0)
    TRICK_OR_TREAT_RESULT(UNVERIFIED_OP), // v232 962
    // v271 CField::OnPacket 0x14345e5e0: the field-set context store at [field+0xd8]+0x3170 (BossFieldSetInfo.img Context)
    FIELD_SET_CONTEXT_INIT(971), // v271 (v265 917): 0x1434e56c0 -> 0x140c59c90; 4 n, n x (S key, 4 0, 4 max, 4 min, 4 value) field defs, 4 0, 4 users, users x (4 char, 4 n, n x (S, 4 0, 4 max, 4 min, 4 value), 4 0); field type 453 also builds the Malefic Star gauge + predation windows
    FIELD_SET_CONTEXT_ALIAS(972), // v271 (v265 918): 0x1434e5960; two maps (4 n, n x (S, S)) and two sets (4 n, n x S); e.g. DeathCountKey -> context name; sent after 971 by FieldSetContext.send (Jupiter)
    FIELD_SET_CONTEXT_UPDATE(973), // v271 new: 0x1434e60d0, 4 type: 0 field S key 4 amount 4 everyMs 4 forMs; 1 field S key (remove); 2 user 4 char + as 0; 3 user 4 char S; 4 user 4 char, 8 double time scale. Not sent (971 resends values)
    BATTLE_ATTACK_HIT(UNVERIFIED_OP), // v232-era guess; v271 973 is FIELD_SET_CONTEXT_UPDATE
    GO_MONSTER_FARM(UNVERIFIED_OP), // v232 973
    MONSTER_LIFE_INVITE_ITEM_RESULT(UNVERIFIED_OP), // v232 974
    PHOTO_GET_RESULT(UNVERIFIED_OP), // v232 975
    FREEZE_HOT_EVENT_INFO(977),
    EVENT_BEST_FRIEND_INFO(978),
    CREATE_PSYCHIC_LOCK(UNVERIFIED_OP), // v265 984; v265 984..990 have no v271 user-pool case; v271 978..986 are mob pattern packets
    RELEASE_PSYCHIC_LOCK(UNVERIFIED_OP), // v265 986; v265 984..990 have no v271 user-pool case; v271 978..986 are mob pattern packets
    RELEASE_PSYCHIC_LOCK_MOB(UNVERIFIED_OP), // v265 987; v265 984..990 have no v271 user-pool case; v271 978..986 are mob pattern packets
    CREATE_PSYCHIC_AREA(UNVERIFIED_OP), // v265 988; v265 984..990 have no v271 user-pool case; v271 978..986 are mob pattern packets
    RELEASE_PSYCHIC_AREA(UNVERIFIED_OP), // v265 989; v265 984..990 have no v271 user-pool case; v271 978..986 are mob pattern packets
    MOB_PATTERN_RESUME(979), // v271: field 0x1434e6690 (978..986); 4 mob: clears the "pattern used" flag; 4 mob: clears the "pattern used, waiting for the server" flag the client sets on each use
    SET_REPEAT_ONE_TIME_ACTION(UNVERIFIED_OP), // v232-era; v271 979 is MOB_PATTERN_RESUME
    MOB_PATTERN_SYNC(980), // 4 mob, 4 owner char, 4, 4, ctx, 4 n + n x 4 (queue), 4 n + n x (4 id, 4 4 4 4 4 4 4 1 1 ?); not sent
    UNK209_949(UNVERIFIED_OP), // v232 982
    MOB_PATTERN_QUEUE(985), // v271: field 0x1434e6690; 4 mob, 4 n, n x 4 global pattern id; the selector only tries the head; 4 mob, 4 n, n x 4 global pattern id (cat*100000 + diff*1000 + n): the selector only tries the head; empty = no patterns
    RECREATE_PATH_PSYCHIC_LOCK(UNVERIFIED_OP), // v232 748; v271 985 is MOB_PATTERN_QUEUE, real number unknown
    // CUserPool::OnPacket
    USER_ENTER_FIELD(990), // v271 (v265 929): CUserPool 0x1456741e0, init 0x14568e800 (UserPool.userEnterField)
    RW_ZERO_BUNKER_MOB_BIND(UNVERIFIED_OP), // v265 990; v271 990 is USER_ENTER_FIELD
    USER_LEAVE_FIELD(991), // v271 (v265 930): reads 4
    // CUserPool::OnCommonPacket
    CHAT(995), // v271 (v265 931): user router 0x145675ce0 case 0x3e3; OnChat and its chat-info reads as v265
    REFRESH_NAME_TAG_MARK(UNVERIFIED_OP), // v265 995; v271 995 is CHAT
    USER_CTRL_MOB_SKILL_FAIL(UNVERIFIED_OP), // v232 995
    ITEM_LINKED_CHAT(1006), // v271 (v265 942): user common table, reads as v265
    AD_BOARD(1007), // v271 (v265 943): user common table, reads as v265
    MINI_ROOM_BALLOON(1008), // v271 (v265 944): user common table, 1 4 S 1 1 1 1 1 as v265
    SET_CONSUME_ITEM_EFFECT(1009), // v265 945
    UNK_921(UNVERIFIED_OP), // v232 1009
    ENCHANT_EFFECT(1010), // v271 0x1450a4b60: 1 success, plays Effect/BasicEff.img/Enchant/Success or Failure
    SHOW_ITEM_LUCKY_ITEM_EFFECT(1012), // v271 (v265 948): user common table, 1 4
    // v265 RE: user router 0x1450d70d0 cases 946 (0x144ae6430: remote only, byte -> Enchant/Success|Failure),
    // 949 fireworks, 951/952 empty stubs, 953 no reads
    SHOW_ITEM_UPGRADE_EFFECT(1014), // v271 (v265 950): user common table, 1 4 4 as v265
    BOSS_PARTY_CHECK_DONE(UNVERIFIED_OP), // v232 1015
    TESLA_TRIANGLE(1019), // v271 (v265 955): user common table, reads as v265
    FOLLOW_CHARACTER(1021), // v271 (v265 957): user common table, reads as v265
    SET_ONE_TIME_ACTION(1022), // v271 (v265 958): user common table, reads as v265
    MAKING_SKILL_RESULT(1023), // v271 (v265 959): user common table
    SET_MAKING_MEISTER_SKILL_EFF(1024), // v271 (v265 960): user common table, 4
    GATHER_RESULT(1025), // v271 (v265 961): user common table, 1
    USER_EXPLODE(1026), // v265 962
    USER_HIT_BY_COUNTER(1027), // v271 (v265 963): user common table, 4 as v265
    PYRAMID_LETHAL_ATTACK(1028), // v265 964
    UNK_940(UNVERIFIED_OP), // v232 1028
    MIXER_RESULT(1029), // v265 965
    WAIT_QUEUE_RESPONSE(1030), // v265 966
    CATEGORY_EVENT_NAME_TAG(1031), // v265 967
    DRAW_EVENT_NAME_TAGS(1032), // v265 968
    SET_DAMAGE_SKIN(1033), // v271 (v265 969): user common table, 4 S S as v265
    PET_HUE_CHANGED(UNVERIFIED_OP), // v265 1033; v271 1033 is SET_DAMAGE_SKIN (v271 1096 by the pet table)
    SET_SOUL_EFFECT(1034), // v271 (v265 970): user common table, 1 1
    PET_MODIFIED(UNVERIFIED_OP), // v265 1034; v271 1034 is SET_SOUL_EFFECT (pet dispatcher: 1097)
    SIT_RESULT(1035), // v271 (v265 971): user common table; 0x1452aea30 reads 1 [2] as v265 0x144ce88e0
    TOSSED_BY_MOB_SKILL(1036), // v271 (v265 972): user common table 0x1452aeaa0, 4 4 4 4 4
    CAMERA_ROTATION(UNVERIFIED_OP), // v265 1036; v271 1036 is TOSSED_BY_MOB_SKILL
    BATTLE_USER_HIT_BY_MOB(1038), // v265 974
    UNK205_996(UNVERIFIED_OP), // v232 1043
    SET_REPLACE_MOVE_ACTION(1044), // v271 (v265 980): user common table, 4 4 as v265
    SELF_ENCHANT_RESULT(1045), // v265 981 // v265 RE: Chaos Ring self-enchant window (Etc/SelfEnchantInfo.img); not a cube result
    SKILL_ON_OFF_EFFECT(1049), // v271 (v265 992): user pool case 1049 -> 0x1452da170: 1 on, 4 skill; sets the local toggle map 0x1452dd660 reads
    ADD_MESO_CHAIR_COUNT(1050), // v271 (v265 993): user common table (position), read sequence equal
    URSUS_REUSLT_UI_OPEN(UNVERIFIED_OP), // v232 1051
    SOME_DECODE4(UNVERIFIED_OP), // v232 1053
    STIGMA_EFFECT(1054), // v271 (v265 996): user common table 0x1452b9eb0, 1
    SKILL_PET_MOVE(UNVERIFIED_OP), // v265 1054; v271 1054 is STIGMA_EFFECT
    UNK_881(UNVERIFIED_OP), // v232 1054
    SHOOT_OBJECT_EXPLODE_RESULT(1055), // v271 (v265 997): user common table, reads as v265
    SKILL_PET_ACTION(UNVERIFIED_OP), // v265 1055; v271 1055 is SHOOT_OBJECT_EXPLODE_RESULT
    UNK_690(1056), // v271 (v265 998): user common table, reads as v265
    SKILL_PET_STATE(UNVERIFIED_OP), // v265 1056; v271 1056 is UNK_690
    UNK214_1038(UNVERIFIED_OP), // v232 1057
    UNK200_969(UNVERIFIED_OP), // v232 1059
    HO_YOUNG_SHAPESHIFT_RESULT(1062), // v271 (v265 1004): user common table, reads as v265
    // v271 (v265 1005): CUserPool 0x145675ce0 case 0x427 -> 0x146685760 on the user's actor manager: int char,
    // byte (9: RPC calls, else an object stream)
/* 1063*/    USER_ACTOR_REPLICATION(1063), // v271 (v265 1005): user common table between 1062 and 1064 (both read as v265); GMS 1063 is the most frequent packet
    IMMOVABLE_OBJ_CREATED(1064), // v271 (v265 1006): user common table, reads as v265
    IMMOVABLE_OBJ_REMOVED(1065), // v271 (v265 1007): user common table, reads as v265
    UNK203_987(UNVERIFIED_OP), // v232 1065
    JUPITER_THUNDER_CREATED(1066), // v271 (v265 1008): user common table, reads 4 1 4 as v265
    BURNING_RESULT(UNVERIFIED_OP), // v232 1066
    JUPITER_THUNDER_REMOVED(1067), // v271 (v265 1009): user common table, reads as v265
    UNK203_990(UNVERIFIED_OP), // v232 1067
    JUPITER_THUNDER_UPDATE_RESULT(1068), // v271 (v265 1010): user common table, reads as v265
    UNK227_1069(UNVERIFIED_OP), // v232 1071
    UNK227_1070(UNVERIFIED_OP), // v232 1072
    UNK202_987(UNVERIFIED_OP), // v232 1073
    UNK202_988(UNVERIFIED_OP), // v232 1074
    SKILL_REQUEST_REQUEST(UNVERIFIED_OP), // v232 1076
    // blank
    UNK209_1046(UNVERIFIED_OP), // v232 1080
    UNK203_1006(UNVERIFIED_OP), // v232 1084
    UNK203_1007(UNVERIFIED_OP), // v232 1085
    MEDAL_OBTAIN(UNVERIFIED_OP), // v232 1086
    UNK203_1009(UNVERIFIED_OP), // v232 1087
    VERTICAL_JUMP(UNVERIFIED_OP), // v232 1088
    UNK203_1011(UNVERIFIED_OP), // v232 1089
    UNK203_1012(UNVERIFIED_OP), // v232 1090
    // CUser::OnPetPacket       v223.1
    PET_ACTIVATED(1091), // v271 (v265 1028): user router 0x145675ce0 sends 0x443..0x44c to the pet dispatcher 0x1450a5d50; 0x1456a7670 reads 4 1 [1 + pet init 0x143e3c370, which gained an int after the template id]
    UNK203_1013(UNVERIFIED_OP), // v232 1091
    PET_MOVE(1092), // v271 (v265 1029): pet dispatcher case 0, 4 idx + move path
    UNK203_1014(UNVERIFIED_OP), // v232 1092
    PET_ACTION(1093), // v271 (v265 1030): case 1, 4 idx 1 1 S
    UNK203_1015(UNVERIFIED_OP), // v232 1093
    PET_ACTION_SPEAK(1094), // v271 (v265 1031): case 2, 4 idx S
    UNK205_1046(UNVERIFIED_OP), // v232 1094
    PET_NAME_CHANGED(1095), // v271 (v265 1032): pet dispatcher case 4, S; v271 1032 is DRAW_EVENT_NAME_TAGS
    DISCOVERY_SKILL(UNVERIFIED_OP), // v232 1095
    UNK203_1017(UNVERIFIED_OP), // v232 1096
    UNK203_1018(UNVERIFIED_OP), // v232 1097
    UNK214_1079(UNVERIFIED_OP), // v232 1098
    REMOTE_SKILL_PREPARE(UNVERIFIED_OP), // v265 1099; v271 1099 is a pet packet; v271 reader 0x145699030 (remote attacks) changed, number not mapped
    UNK214_1080(UNVERIFIED_OP), // v232 1099
    //
    //
    PET_ACTION_COMMAND(1100), // v271 (v265 1037): pet dispatcher case 9, 1 1 1 1 1 4
    REMOTE_MOVING_SHOOT_ATTACK_PREPARE(UNVERIFIED_OP), // v265 1100; v271 1100 is a pet packet; v271 reader 0x145699030 (remote attacks) changed, number not mapped
    UNK220_1079(UNVERIFIED_OP), // v232 1100
    // CUser::OnDragonPacket       v223.1
    // v271: user router 0x145675ce0 sends 0x44d..0x450 to the dragon dispatcher 0x1450a9330
    DRAGON_CREATED(1101), // v271 (v265 1038): Init 0x14338d890 reads 4 4 1 2 2 (x, y, action, fh, job), as v265
    STOP_KEY_DOWN_SKILL_REQUEST(UNVERIFIED_OP), // v232 1101
    DRAGON_MOVE(1102), // v271 (v265 1039): move path
    UNK218_1075(UNVERIFIED_OP), // v232 1102
    DRAGON_REMOVE(1103), // v271 (v265 1040): no body
    UNK203_1022(UNVERIFIED_OP), // v232 1103
    DRAGON_REMOVE_RP_CHAR(1104), // v271: no body; removes Mir unless the job is Evan (2001, 2200-2299)
    UNK227_1102(UNVERIFIED_OP), // v232 1104
    // CUser::OnAndroidPacket       v223.1
    ANDROID_CREATED(1105), // v265 1042; v271 android router 0x1450a9610 cases 0x451..0x455
    UNK216_1082(UNVERIFIED_OP), // v232 1105
    ANDROID_MOVE(1106), // v271 (v265 1043): android router 0x1450a9610, move path
    BLACK_MAGE_DEATHCOUNT_MAP_EFFECT(UNVERIFIED_OP), // v232 1106
    ANDROID_ACTION_SET(1107), // v271 (v265 1044): android router 0x1450a9610
    UNK203_1024(UNVERIFIED_OP), // v232 1107
    ANDROID_MODIFIED(1108), // v271 (v265 1045): android router 0x1450a9610
    UNK209_1069(UNVERIFIED_OP), // v232 1108
    ANDROID_REMOVED(1109), // v271 (v265 1046): android router 0x1450a9610
    UNK209_1070(UNVERIFIED_OP), // v232 1109
    // CUser::OnFoxManPacket        v223.1
    FOX_MAN_ENTER_FIELD(1110), // v265 1047; v271 fox-man router 0x1450a9900 cases 0x456..0x45b, same order as v265 0x144aeb2d0: 2, 2 2 pos, 1, 2 fh, 4, 4
    UNK209_1071(UNVERIFIED_OP), // v232 1110
    FOX_MAN_MOVE(1111), // v271 (v265 1048): fox-man router, movement path
    UNK209_1072(UNVERIFIED_OP), // v232 1111
    FOX_MAN_EXCL_RESULT(1112), // v271 (v265 1049): fox-man router 0x1450a9900; v265 1049: no reads
    UNK209_1073(UNVERIFIED_OP), // v232 1112
    FOX_MAN_SHOW_CHANGE_EFFECT(1113), // v271 (v265 1050): fox-man router 0x1450a9900; v265 1050: no reads
    UNK209_1074(UNVERIFIED_OP), // v232 1113
    FOX_MAN_MODIFIED(1114), // v271 (v265 1051): fox-man router 0x1450a9900; v265 1051: 1 [4 fan], then 1 for the local user
    UNK209_1075(UNVERIFIED_OP), // v232 1114
    FOX_MAN_LEAVE_FIELD(1115), // v271 (v265 1052): fox-man router 0x1450a9900; v265 1052: no reads
    UNK209_1077(UNVERIFIED_OP), // v232 1116
    UNK212_1091(UNVERIFIED_OP), // v232 1117
    UNK212_1092(UNVERIFIED_OP), // v232 1118
    UNK212_1094(UNVERIFIED_OP), // v232 1120
    TRUE_NOBILITY_SHIELD(UNVERIFIED_OP), // v232 1121
    SKILL_PET_CREATED(1122), // v271 (v265 1059): skill-pet dispatcher 0x1452d3ad0 cmp 0x462 -> user vfunc; v265 RE skill-pet dispatcher 0x144d16980 -> user vfunc +0x1b0 (0x145122670): int oid, then 0x142c10a40: int skill, byte, short x, short y, byte, short fh
    // v271 1184 (0x1456a6b10: 4 4 S, mark, the 4 4 4 1 guild bytes) and 1187 (0x1456a6e90: 4 4 4 1) are new, not sent
    REMOTE_PVP_TEAM_CHANGED(UNVERIFIED_OP), // v265 1123 has no v271 remote case
    REMOTE_SET_CUSTOMIZE_EFFECT(UNVERIFIED_OP), // v265 1123 has no v271 remote case
    // CUser::OnSecondAtomPacket       v223.1
    CREATE_SECOND_ATOM(1124), // v271 (v265 1061): CUserPool range 0x464-0x469 -> 0x1452ae2b0 -> 0x142e2bfe0 (cases in v265 order)
    SECOND_ATOM_COMMAND_RESULT(1125), // v271 (v265 1062)
    REMOTE_UPDATE_PVP_HP_TAG(UNVERIFIED_OP), // v265 1125; v271 1125 is SECOND_ATOM_COMMAND_RESULT
    CREATE_MULTIPLE_SECOND_ATOMS(1126), // v271 (no v265 case): 0x142e2c580, int count + records
    SECOND_ATOM_ATTACK(1127), // v271 (v265 1064): 0x142e2d080, 4 4
    REMOTE_DRAGON_GLIDE(UNVERIFIED_OP), // v265 1127; v271 1127 is SECOND_ATOM_ATTACK
    SECOND_ATOM_COLLISION_RESULT(1128), // v271 (v265 1065)
    REMOTE_KEY_DOWN_AREA_MOVE_PATH(UNVERIFIED_OP), // v265 1128; v271 1128 is SECOND_ATOM_COLLISION_RESULT
    REMOVE_SECOND_ATOM(1129), // v271 (v265 1066)
    REMOTE_LASER_INFO_FOR_REMOTE(UNVERIFIED_OP), // v265 1129; v271 1129 is REMOVE_SECOND_ATOM
    REMOTE_CHAT(1135), // v271 (v265 1068): same handler as CHAT
    REMOTE_ITEM_LINKED_CHAT(1136), // v271 (v265 1069): user common table, reads as v265
    REMOTE_INTRUSION(UNVERIFIED_OP), // v265 1136; v271 1136 is REMOTE_ITEM_LINKED_CHAT
    UNK218_1106(UNVERIFIED_OP), // v232 1136
    // CUserPool::OnCommonPacket (last part )        v223.1
    GACHAPON_EFFECT(1137), // v271 (v265 1070): user common table, last case (empty as v265); v265 RE: user pool 1070 -> 0x144d6d1e0, plays Effect/BasicEff.img/Gachapon/Open
    // CFamiliar::OnPacket      v223.1
    // v271 (v265 1076-1079): user router 0x14531f920 (0x472-0x475); enter = user vtable +0x230 (remote 0x1456c8290,
    // local 0x14551b150), 1 1 1 1 {1 [4 4 S 2 2 1 2]} as v265; the others: 1 idx, then move (path, no key pads:
    // 0x143446c00 passes 0), info 1 4 4 4 8, rename S
    FAMILIAR_ENTER_FIELD(1138), // v271 (v265 1076): familiar router 0x14531f920
    FAMILIAR_MOVE(1139), // v271 (v265 1077): familiar router 0x14531f920
    FAMILIAR_INFO(1140), // v271 (v265 1078): familiar router 0x14531f920
    FAMILIAR_RENAME(1141), // v271 (v265 1079): familiar router 0x14531f920
    UNK223_1129(UNVERIFIED_OP), // v232 1142
    UNK223_1130(UNVERIFIED_OP), // v232 1143
    REMOTE_KINESIS_PSYCHIC_ENERGY_SHIELD_EFFECT(UNVERIFIED_OP), // v265 1144; v265 1144 has no v271 remote case
    UNK223_1131(UNVERIFIED_OP), // v232 1144
    UNK223_1132(UNVERIFIED_OP), // v232 1145
    // CUserRemote::OnPacket        v223.1
    REMOTE_MOVE(1146), // v271 (v265 1084): remote router case 0x47a -> 0x145698f90, path decoder as MOB_MOVE
    UNK227_1144(UNVERIFIED_OP), // v232 1146
    UNK227_1145(UNVERIFIED_OP), // v232 1147
    UNK227_1146(UNVERIFIED_OP), // v232 1148
    UNK227_1147(UNVERIFIED_OP), // v232 1149
    REMOTE_SCOUTER_MAX_DAMAGE_UPDATE(1150),
    UNK227_1148(UNVERIFIED_OP), // v232 1150
    UNK228_1159(UNVERIFIED_OP), // v232 1151
    UNK228_1160(UNVERIFIED_OP), // v232 1152
    UNK228_1161(UNVERIFIED_OP), // v232 1153
    UNK228_1162(UNVERIFIED_OP), // v232 1154
    UNK228_1163(UNVERIFIED_OP), // v232 1155
    REMOTE_MELEE_ATTACK(1157), // v271 (v265 1095): cases 0x485..0x488 -> 0x145699030; tail partly VM-protected
    UNK228_1165(UNVERIFIED_OP), // v232 1157
    REMOTE_SHOOT_ATTACK(1158), // v271 (v265 1096)
    UNK228_1166(UNVERIFIED_OP), // v232 1158
    REMOTE_MAGIC_ATTACK(1159), // v271 (v265 1097)
    REMOTE_BODY_ATTACK(1160), // v271 (v265 1098)
    UNK228_1168(UNVERIFIED_OP), // v232 1160
    UNK228_1169(UNVERIFIED_OP), // v232 1161
    UNK228_1170(UNVERIFIED_OP), // v232 1162
    REMOTE_SKILL_CANCEL(1163), // v271 (v265 1101): ta-uremote, reads 4 4
    UNK228_1171(UNVERIFIED_OP), // v232 1163
    REMOTE_HIT(1164), // v271 (v265 1102): 0x1456a4b80; type -12 reads one int more
    UNK232_1164(UNVERIFIED_OP), // v232 1164
    REMOTE_EMOTION(1165), // v271 (v265 1103): 4 4 1
    REMOTE_ANDROID_EMOTION(1166), // v271 (v265 1104): ta-uremote, reads 4 4
    REMOTE_SET_ACTIVE_EFFECT_ITEM(1167), // v265 1105
    REMOTE_SET_MONKEY_EFFECT_ITEM(1168), // v265 1106
    // blank
    // blank
    // blank
    // blank
    RANDOM_MESO_SACK_SUCCEED(UNVERIFIED_OP), // v232 1168; *
    RANDOM_MESO_SACK_FAIL(UNVERIFIED_OP), // v232 1169
    REGISTER_FADE_IN_OUT_ANIMATION(UNVERIFIED_OP), // v232 1170
    REMOTE_SET_ACTIVE_NICK_ITEM(1171), // v271 (v265 1107): remote table 0x1456a5bd0, reads as v265
    UNK203_1028(UNVERIFIED_OP), // v232 1171
    UNK203_1029(UNVERIFIED_OP), // v232 1172
    REMOTE_SET_DEFAULT_WING_ITEM(1173), // v271 (v265 1109): remote user table, reads as v265
    UNK203_1030(UNVERIFIED_OP), // v232 1173
    REMOTE_SET_KAISER_TRANSFORM_ITEM(1174), // v265 1110
    SET_CASH_SHOP_2(UNVERIFIED_OP), // v232 1174
    REMOTE_SET_CUSTOM_RIDING(1175), // v271 (v265 1111): remote byte table 0x145675adc -> 0x1456a9090, 4 as v265
    EVENT_LEVELUP_CHECKPOINT(UNVERIFIED_OP), // v232 1175
    UNK203_1033(UNVERIFIED_OP), // v232 1176
    REMOTE_SHOW_UPGRADE_TOMB_EFFECT(1177), // v265 1113
    MESO_GIVE_FAILED(UNVERIFIED_OP), // v265 1177; v271 1177 is REMOTE_SHOW_UPGRADE_TOMB_EFFECT
    SHOW_QUEST_MESSENGER(UNVERIFIED_OP), // v232 1177
    REMOTE_SET_ACTIVE_PORTABLE_CHAIR(1178), // v271 (v265 1114): remote byte table -> 0x1456a5cd0, 4 4 1 + chair (+0x18 readers equal)
    UNK203_1035(UNVERIFIED_OP), // v232 1178
    REMOTE_AVATAR_MODIFIED(1179), // v271 (v265 1115): 0x1456a6040, one counted byte list more at the end
    UNK203_1036(UNVERIFIED_OP), // v232 1179
    REMOTE_EFFECT(1180), // v271 (v265 1116): 0x145085370, the EFFECT reader (v271 type numbers)
    USER_PET_SKILL_CHANGED(UNVERIFIED_OP), // v265 1180; v271 1180 is REMOTE_EFFECT
    MOUNT_RESULT(UNVERIFIED_OP), // v232 1180
    REMOTE_SET_TEMPORARY_STAT(1181), // v271 (v265 1117): 0x1456a8020, remote stat reader 0x1413ff7b0
    REMOTE_RESET_TEMPORARY_STAT(1182), // v271 (v265 1118): 0x1456a8290
    BALLOON_MSG(UNVERIFIED_OP), // v265 1182; v271 1182 is REMOTE_RESET_TEMPORARY_STAT
    REMOTE_RECEIVE_HP(1183), // v271 (v265 1119): remote table -> 0x1456a6920, reads as v265 0x145106060
    SWITCH_RP(UNVERIFIED_OP), // v265 1183; v271 1183 is a remote user packet
    REMOTE_GUILD_UPDATE(1184), // v271 (new): remote router -> 0x1456a6b10, 4 4 guild S 2 1 2 1 4 [4 1 len+bytes] + 4 4 4 1; v232 855
    REMOTE_GUILD_NAME_CHANGED(1185), // v271 (v265 1121): remote router 0x145675080 second table -> 0x1456a6cd0, S (as v265)
    JUMPSTART_CHARACTER_CREATE(UNVERIFIED_OP), // v232 1185
    REMOTE_GUILD_MARK_CHANGED(1186), // v271 (v265 1122): -> 0x1456a6d50, 4 2 1 2 1 4 [4 1 len+bytes] (as v265 0x145106470)
    UNK203_1043(UNVERIFIED_OP), // v232 1186
    UNK203_1044(UNVERIFIED_OP), // v232 1187
    UNK203_1045(UNVERIFIED_OP), // v232 1188
    REMOTE_GATHER_ACTION_SET(1189), // v271 (v265 1124): remote table, 1 4 4
    GUARDIANS_BLESSING_RESULT(UNVERIFIED_OP), // v232 1189
    UNK203_1047(UNVERIFIED_OP), // v232 1190
    UNK203_1048(UNVERIFIED_OP), // v232 1191
    UNK203_1049(UNVERIFIED_OP), // v232 1192
    SET_CAMERA_ON_POSITION(UNVERIFIED_OP), // v232 1193
    CREATE_NAVIGATION(UNVERIFIED_OP), // v232 1195
    REMOTE_KAISER_COLOR_OR_MORPH_CHANGE(1196), // v271 (v265 1131): remote second table 0x1456a95c0, 4 4 1
    UNK205_1089(UNVERIFIED_OP), // v232 1196
    REMOTE_DESTROY_GRENADE(1197), // v271 (v265 1132): remote user table, reads as v265
    DISPLAY_LOCATION(UNVERIFIED_OP), // v232 1197
    UNK203_1055(UNVERIFIED_OP), // v232 1198
    REMOTE_SET_ITEM_ACTION(1199), // v265 1134
    REMOTE_ZERO_TAG(1200), // v271 (v265 1135): remote second table 0x1456a96b0; the avatar block changed (B528); v271 1135 is REMOTE_CHAT
    UNK203_1057(UNVERIFIED_OP), // v232 1200
    UNK203_1058(UNVERIFIED_OP), // v232 1201
    REMOTE_ZERO_LAST_ASSIST_STATE(1202), // v271 (v265 1137): remote second table 0x1456a99e0
    UNK203_1059(UNVERIFIED_OP), // v232 1202
    REMOTE_SET_MOVE_GRENADE(1203), // v271 (v265 1138): remote table, 4 4 F8 4 1 as v265; v271 1138 is FAMILIAR_ENTER_FIELD
    PUSH(UNVERIFIED_OP), // v232 1203
    OPEN_SKILL_GUIDE(1206),
    UNK203_1064(UNVERIFIED_OP), // v232 1207
    REMOTE_RUNE_STONE_ACTION(1208), // v271 (v265 1143): uremote table, reads 1 4 as v265
    REMOTE_DRAGON_ACTION(1209), // v271 (v265 1145): ta-uremote, reads 4 4 4
    UNK203_1066(UNVERIFIED_OP), // v232 1209
    UNK203_1067(UNVERIFIED_OP), // v232 1210
    REMOTE_DRAGON_BREATH_EARTH_EFFECT(1211), // v271 (v265 1147): ta-uremote, reads 4 4 4 4 4 4
    UNK203_1068(UNVERIFIED_OP), // v232 1211
    REMOTE_RW_MULTI_CHARGE_CANCEL_REQUEST(1212), // v271 (v265 1149): remote table 0x1456c3c30, 1 n, n x 4 skill (between 1211 and 1213)
    UNK203_1069(UNVERIFIED_OP), // v232 1212
    REMOTE_RELEASE_RW_GRAB(1213), // v271 (v265 1148): remote user table, reads as v265
    UNK203_1071(UNVERIFIED_OP), // v232 1214
    REMOTE_STIGMA_DELIVERY_RESPONSE(1215), // v271 (v265 1151): remote second table 0x1456c4eb0, 4 4 4
    PASSIVE_MOVE(UNVERIFIED_OP), // v265-era 1215; v271 1215 is REMOTE_STIGMA_DELIVERY_RESPONSE
    UNK203_1072(UNVERIFIED_OP), // v232 1215
    REMOTE_SHOOT_OBJECT(1216), // v271 (v265 1152): remote table 0x1456c4f80
    UNK203_1073(UNVERIFIED_OP), // v232 1216
    UNK203_1074(UNVERIFIED_OP), // v232 1217
    // empty op
    UNK203_1076(UNVERIFIED_OP), // v232 1219
    UNK203_1077(UNVERIFIED_OP), // v232 1221
    UNK203_1079(UNVERIFIED_OP), // v232 1222
    UNK203_1078(UNVERIFIED_OP), // v232 1223
    UNK203_1080(UNVERIFIED_OP), // v232 1224
    REMOTE_THROW_GRENADE(1225), // v271 (v265 1160): remote 0x1456a6f20; encoder matches (emulated, 53 bytes read)
    UNK203_1081(UNVERIFIED_OP), // v232 1225
    // CUserLocal::OnPacket        v223.1
    EMOTION(1226), // v271 (v265 1161): 4 4 1
    MONAD_REWARD(UNVERIFIED_OP), // v232 1226
    // empty op
    UNK203_1083(UNVERIFIED_OP), // v232 1228
    UNK203_1085(UNVERIFIED_OP), // v232 1229
    UNK212_1155(UNVERIFIED_OP), // v232 1230
    UNK221_1184(UNVERIFIED_OP), // v232 1231
    UNK227_1216(UNVERIFIED_OP), // v232 1232
    UNK227_1217(UNVERIFIED_OP), // v232 1233
    INC_LARKNESS_RESPONSE(UNVERIFIED_OP), // v265 1236; no v271 handler, the gauge rides on the Larkness stat
    ANDROID_EMOTION(1237), // v271 (v265 1172): CUserLocal table, reads as v265
    EFFECT(1238), // v271 (v265 1173): local table case 1238 -> 0x145085370 (v271 type numbers, Effect.v271Type)
    TELEPORT(1240), // v271 (v265 1174): CUserLocal 0x1453a2f80; type != 0 reads 1 flag, 4 more after the position
    MESO_GIVE_SUCCEEDED(1242), // v265 1176
    QUEST_RESULT(1244), // v271 (v265 1178): CUserLocal handler 0x1453a3220, result cases 8-26 read as v265
    NOTIFY_HP_DEC_BY_FIELD(1245), // v271 (v265 1179): local table, 4 as v265
    PLAY_EVENT_SOUND(1249), // v271 (v265 1183): local table, S as v265
    PLAY_MINIGAME_SOUND(1250), // v271 (v265 1184): local table, S as v265
    MAKER_RESULT(1251), // v271 (v265 1185): CUserLocal table, read sequence equal (20 reads)
    B2_BODY_RESULT(UNVERIFIED_OP), // v265 1251; v271 1251 is MAKER_RESULT
    // blank
    OPEN_UI(1253), // v271 (v265 1187): CUserLocal 0x1453a84d0, 4 as v265; only UI 0x4fd reads more (a list with looks), not sent
    CLOSE_UI(1254), // v271 (v265 1188): CUserLocal table, reads as v265
    OPEN_UI_WITH_OPTION(1255), // v271 (v265 1189): CUserLocal 0x1453a8800, 4 ui 4 option 4 n x 4; GMS: skill window at Summon Familiars (3, 80011951)
    SIT_TIME_CAPSULE(1259),
    SET_DIRECTION_MODE(1260), // v271 (v265 1193): local 0x1453a96b0, 1
    SUMMONED_UNK1065(UNVERIFIED_OP), // v232 1260
    SET_IN_GAME_DIRECTION_MODE(1261), // v271 (v265 1194): 0x1453a96f0, 1 mode 1 exit flag / mode != 0: 4 x 1, str
    SUMMONED_UNK228_1268(UNVERIFIED_OP), // v232 1261
    SET_STAND_ALONE_MODE(1262), // v271 (v265 1195): CUserLocal 0x1453a9c00, 1 (run +67 from SET_DIRECTION_MODE)
    SET_GUN(UNVERIFIED_OP), // v265 1262; v271 1262 is SET_STAND_ALONE_MODE
    SUMMONED_GRAVITY_CRUSH_DAMAGE(UNVERIFIED_OP), // v232 1262
    HIRE_TUTOR(1263), // v271 (v265 1196): CUserLocal table, reads as v265
    SET_AMMO(UNVERIFIED_OP), // v265 1263; v271 1263 is HIRE_TUTOR
    TUTOR_MSG(1264), // v271 (v265 1197): CUserLocal table, reads as v265
    CREATE_GUN(UNVERIFIED_OP), // v265 1264; v271 1264 is TUTOR_MSG
    SUMMONED_JETT_SUMMON(UNVERIFIED_OP), // v232 1264
    HIRE_TUTOR_BY_ID(1265), // v265 1198
    CLEAR_GUN(UNVERIFIED_OP), // v265 1265; v271 1265 is HIRE_TUTOR_BY_ID
    SET_PARTNER(1266), // v265 1199
    SET_PARTNER_ACTION(1267), // v265 1200
    SET_PARTNER_FORCE_FLIP(1268), // v265 1201
    REGISTER_MIRROR_DUNGEON_BOSS(1270), // 0x144ed2730: int n, n x int
    MOD_COMBO_RESPONSE(1271), // v271 (v265 1204): CUserLocal table, 4 as v265 (Aran combo counter)
    MOB_BLOCK_ATTACK(UNVERIFIED_OP), // v232 1271
    INC_COMBO_RESPONSE_BY_COMBO_RECHARGE(1272), // v271 (v265 1205): CUserLocal table, 4 4 as v265
    NOTICE_MSG(1274), // v271 (v265 1207): local 0x145470b60, S 1 1
    MOB_UNK212_1195(UNVERIFIED_OP), // v232 1274
    CHAT_MSG(1275), // v271 (v265 1208): 2 S
    OPEN_URL(UNVERIFIED_OP), // v265 1275; v271 1275 is CHAT_MSG
    MOB_UNK218_1214(UNVERIFIED_OP), // v232 1275
    ZERO_COMBAT_RECOVERY(1276),
    MIRROR_STUDY_UI_OPEN(1277),
    SET_UTIL_DLG(1278), // v271 (v265 1211): local table, S 4 as v265
    BUFFZONE_EFFECT(1279), // v265 1212
    TIME_BOMB_ATTACK(1280), // v271 (v265 1213): user local table 0x1453aa770, 4 4 4 4 4 as v265
    USER_CTRL_MOB_SKILL_Q_PUSH(UNVERIFIED_OP), // v265 1280; v271 1280 is TIME_BOMB_ATTACK
    EXPLOSION_ATTACK(1281), // v271 (v265 1214): CUserLocal table, reads as v265
    USER_CTRL_MOB_SKILL_Q_POP(1282),
    FOLLOW_CHARACTER_FAILED(1283), // v265 1216
    GATHER_REQUEST_RESULT(1285), // v271 (v265 1218): CUserLocal table, 4 4
    USER_CTRL_MOB_SKILL_FORCED_POP(1286),
    MOB_CRC_KEY_CHANGED(UNVERIFIED_OP), // v232 1288
    RUNE_STONE_USE_ACK(1289), // v271 (v265 1222): local table, between 1288 and BAG_ITEM_USE_RESULT 1290 (VM code in both)
    BAG_ITEM_USE_RESULT(1290), // v271 (v265 1223): CUserLocal table, reads as v265
    RANDOM_TELEPORT_KEY(1291), // v265 1224
    // blank
    IN_GAME_DIRECTION_EVENT(1293), // v271 (v265 1226): direction object reader 0x14385dd30; delay done -> In 301
    DO_ACTIVE_EVENT_SKILL_BY_SCRIPT(UNVERIFIED_OP), // v265 1293; v271 1293 is IN_GAME_DIRECTION_EVENT
    MEDAL_REISSUE_RESULT(1294), // v271 (v265 1227): CUserLocal table, reads as v265
    DODGE_SKILL_READY(1295), // v271 (v265 1228): local table, between 1294/1296 (+67) with the same handler spacing; empty
    REMOVE_MICRO_BUFF_SKILL(1296), // v265 1229
    VIDEO_BY_SCRIPT(1297), // v271 (v265 1230): CUserLocal table, reads as v265
    REWARD_MOB_LIST_RESULT(1299), // v271 (v265 1232): CUserLocal table, reads as v265
    INC_JUDGEMENT_STACK_RESPONSE(1300), // v271 (v265 1233): CUserLocal table, reads as v265
    INC_CHARM_BY_CASH_PR_MSG(1301), // v265 1234
    FLAME_WIZARD_ELEMENT_FLAME_SUMMON(UNVERIFIED_OP), // v265 1301; v271 1301 is INC_CHARM_BY_CASH_PR_MSG
    SET_BUFF_PROTECTOR(1302), // v271 (v265 1235): local table, 4 1
    DETONATE_BOMB(1303), // v271 (v265 1237): same body, 4 1
    MOB_UNK204_1151(UNVERIFIED_OP), // v232 1303
    AGGRO_RANK_INFO_NAME(1304), // v271 (v265 1238): user local table, 4 S
    FREE_LOOK_CHANGE_UI_OPEN(UNVERIFIED_OP), // v265 1304; v271 1304 is AGGRO_RANK_INFO_NAME
    MOB_UNK204_1152(UNVERIFIED_OP), // v232 1304
    DEATH_COUNT_INFO(1305), // v271 (v265 1239): user local table 0x145486890, 4
    FREE_LOOK_CHANGE_SUCCESS(UNVERIFIED_OP), // v265 1305; v271 1305 is DEATH_COUNT_INFO
    MOB_UNK204_1153(UNVERIFIED_OP), // v232 1305
    DEATH_COUNT_INFO_2(1306), // v271 (v265 1240): user local table, 4 4
    GRAY_BACKGROUND(UNVERIFIED_OP), // v265 1306; v271 1306 is DEATH_COUNT_INFO_2
    MOB_UNK205_1194(UNVERIFIED_OP), // v232 1309
    SET_DRESS_CHANGED(1310), // v271 (v265 1244): CUserLocal table, 1 1
    MOB_UNK214_1238(UNVERIFIED_OP), // v232 1310
    SERVER_ACK_MOB_ZONE_STATE_CHANGE(1311), // v271 (v265 1245): user local table, 1
    MOB_UNK205_1195(UNVERIFIED_OP), // v232 1311
    MOB_UNK228_1319(UNVERIFIED_OP), // v232 1312
    MOB_TIME_RESIST(UNVERIFIED_OP), // v232 1313
    // blank
    // blank
    RANDOM_EMOTION(1314), // v265 1248
    SET_FLIP_THE_COIN_ENABLED(1315), // v271 (v265 1249): CUserLocal table, reads as v265
    REMOVE_POPUP_SAY(UNVERIFIED_OP), // v265 1315; v271 1315 is SET_FLIP_THE_COIN_ENABLED
    GIANT_PET_BUFF(1316), // v265 1250
    JAGUAR_SKILL(UNVERIFIED_OP), // v265 1316; v271 1316 is GIANT_PET_BUFF
    B2_BODY_RESULT_NEW(1318), // v271 (v265 1252): CUserLocal table, reads as v265
    SET_DEAD(1319), // v271 (v265 1253): user local table, 1 4
    OPEN_UI_DEAD(1321), // v271 (v265 1255): user local table 0x145488860
    EXPIRED_NOTICE(1322), // v265 1256
    DO_LOTTERY_UI(1323), // v271 (v265 1257): CUserLocal table, reads as v265
    ROULETTE_START(1324), // v265 1258
    JAGUAR_ACTIVE(UNVERIFIED_OP), // v265 1324; v271 1324 is ROULETTE_START
    SIT_DUMMY_PORTABLE_CHAIR(1326), // v271 (v265 1260): CUserLocal table, 4 4
    FINAL_ATTACK_REQUEST(1327), // v271 (v265 1261): CUserLocal table, 4 4 4 4 4 4 [4] as v265
    UNK205_1213(UNVERIFIED_OP), // v232 1330
    UNK205_1214(UNVERIFIED_OP), // v232 1331
    RESULT_SHOOT_ATTACK_IN_FPS_MODE(1332), // v271 (v265 1266): CUserLocal table, reads as v265
    UNK205_1215(UNVERIFIED_OP), // v232 1332
    MIRROR_DUNGEON_ENTER_FAIL(1334), // v265 1268 // CUserLocal 0x144ed23d0: byte reason (1-7), 3/4/6 add names
    UNK205_1217(UNVERIFIED_OP), // v232 1334
    MIRROR_DUNGEON_UNIT_CLEARED(1335), // v271 (v265 1269): local 0x14548b520; 0x144ed2690: byte reset, byte noEffect, int n, n x int room index
    HIT_BLEND_EFFECT(UNVERIFIED_OP), // v232 1335
    PLANT_POT_EFFECT(1336),
    MIRROR_DUNGEON_RECORD(1337), // v271 (v265 1271): CUserLocal table; 0x144ed2750: clear window (seconds, best seconds, -, long EXP, int WP, str, int level cap)
    UNK218_1275(UNVERIFIED_OP), // v232 1337
    EPIC_DUNGEON_STAGE_UI(1338), // v271 (v265 1272): CUserLocal table 0x14548baa0, B36 as v265; CUserLocal 0x144ed2c20: 36-byte block (uiType, tooltip, -, stage, time?, mission?, missionMax, deaths?, maxDeaths)
    DAMAGE(UNVERIFIED_OP), // v265 1338; v271 1338 is EPIC_DUNGEON_STAGE_UI (DAMAGE is 1403 by the table)
    EPIC_DUNGEON_MISSION(1339), // v271 (v265 1273): 0x14548bba0; 0x144ed2d20: int mission count
    EPIC_DUNGEON_MORPH_RESULT(1340), // v271 (v265 1274): 0x14548bc20; 0x144ed2da0: no body; ends the Mitra morph request (In 1529)
    DO_ACTIVE_PSYCHIC_AREA(UNVERIFIED_OP), // v265 1340; v271 1340 is EPIC_DUNGEON_MORPH_RESULT
    LEAVE_FIELD_PSYCHIC_INFO(1342),
    TOUCH_ME_STATE_RESULT(1343),
    SKILL_COOLTIME_REDUCE(1344), // v271 (v265 1278): local table, 4 1 4
    FIELD_SCORE_UPDATE(UNVERIFIED_OP), // v265 1344
    MIRROR_READING_UI_OPEN(1345), // v265 1279
    SET_NO_MORE_LIFE(1346),
    ZERO_LEVEL_UP_ALARM(1347), // v265 1281
    MOB_UNK213_214_1274(UNVERIFIED_OP), // v232 1348
    MOB_UNK213_214_1275(UNVERIFIED_OP), // v232 1349
    FORCE_SUMMONED_REMOVE(1350), // v265 1284
    MOB_UNK213_214_1276(UNVERIFIED_OP), // v232 1350
    USER_RESPAWN(1351), // v271 (v265 1285): local table, 4 4 as v265
    MOB_UNK213_214_1277(UNVERIFIED_OP), // v232 1351
    MOB_UNK213_214_1278(UNVERIFIED_OP), // v232 1352
    IS_UNIVERSE(1353), // v271 (v265 1287): CUserLocal table, reads as v265
    MOB_UNK218_1307(UNVERIFIED_OP), // v232 1353
    PORTAL_GROUP(1354), // v265 1288
    MOB_UNK218_1292(UNVERIFIED_OP), // v232 1354
    MOB_UNK218_1308(UNVERIFIED_OP), // v232 1354
    SET_MOVABLE(1355), // v271 (v265 1289): local table, 4 as v265
    MOB_UNK213_214_1280(UNVERIFIED_OP), // v232 1355
    MOB_UNK213_214_1293(UNVERIFIED_OP), // v232 1355
    USER_RENAME_RESULT(1356),
    MOB_UNK213_214_1281(UNVERIFIED_OP), // v232 1356
    MOB_UNK218_1310(UNVERIFIED_OP), // v232 1356
    USER_CTRL_MOB_SKILL_PUSH_COOL_TIME(1357), // v265 1291
    MOB_UNK213_214_1282(UNVERIFIED_OP), // v232 1357
    MOVE_PARTICLE_EFF(1358), // v271 (v265 1292): CUserLocal table, reads as v265
    MOB_UNK213_214_1283(UNVERIFIED_OP), // v232 1358
    MOB_UNK213_214_1284(UNVERIFIED_OP), // v232 1359
    MOB_UNK214_1285(UNVERIFIED_OP), // v232 1360
    RUNE_STONE_SKILL_ACK(1361), // v271 (v265 1295): local table, reads 4 as v265
    MOB_UNK213_214_1286(UNVERIFIED_OP), // v232 1361
    RESET_RUNE_STONE_ACTION(1362), // v271 (v265 1296): local table, between 1361 and 1363
    MOB_UNK213_214_1288(UNVERIFIED_OP), // v232 1362
    MOVE_TO_CONTENTS_CANNOT_MIGRATE(1363), // v265 1297
    MOB_UNK213_214_1289(UNVERIFIED_OP), // v232 1363
    PLAY_AMBIENT_SOUND(1364), // v265 1298
    GIANT_SHADOW_SPEAR_ATTACK(UNVERIFIED_OP), // v265 1364; v271 1364 is PLAY_AMBIENT_SOUND
    MOB_UNK213_214_1290(UNVERIFIED_OP), // v232 1364
    STOP_AMBIENT_SOUND(1365), // v265 1299
    MOB_UNK213_214_1291(UNVERIFIED_OP), // v232 1365
    // blank
    MOB_UNK218_1306(UNVERIFIED_OP), // v232 1367
    CAMERA_MODE(1368), // v271 (v265 1302): local table, 1
    SPOTLIGHT_TO_CHARACTER(1369), // v271 (v265 1303): local table, 4 as v265
    MOB_UNK218_1311(UNVERIFIED_OP), // v232 1372
    GET_NPC_CURRENT_ACTION(1373), // v265 1307
    MOB_UNK223_1338(UNVERIFIED_OP), // v232 1373
    MOB_UNK223_1339(UNVERIFIED_OP), // v232 1374
    MOB_UNK213_214_1294(UNVERIFIED_OP), // v232 1375
    CAMERA_SWITCH(1376), // v271 (v265 1311): local table, reads as v265
    MOB_UNK213_214_1295(UNVERIFIED_OP), // v232 1376
    NETWORK(1377), // v265 1312
    MOB_UNK213_214_1296(UNVERIFIED_OP), // v232 1377
    USER_SET_FIELD_FLOATING(1378), // v265 1313
    ADD_POPUP_SAY(1379), // v271 (v265 1314): local table, 4 4 S S 1 4 1 1 4 1
    // blank
    MOB_UNK213_214_1298(UNVERIFIED_OP), // v232 1379
    MOB_UNK213_214_1299(UNVERIFIED_OP), // v232 1380
    ACTION_LAYER_RELMOVE(1382), // v265 1318
    MOB_UNK213_214_1301(UNVERIFIED_OP), // v232 1382; *
    SEND_CLIENT_RESOLUTION(1384), // v271 (v265 1320): local 0x1454a2b10, empty
    USER_BONUS_ATTACK_REQUEST(1385), // v271 (v265 1321): Out, 0x145600050: 4 skill 4 n 1 4 4, n x (4 4), per-skill extras
    USER_RAND_AREA_ATTACK_REQUEST(1386), // v271 (v265 1322): local table, reads 4 4 4 4 4 4 as v265
    NPC_ENTER_FIELD_FOR_QUICK_MOVE(UNVERIFIED_OP), // v232 1386
    USER_EXTRA_ATTACK_REQUEST(1387), // v265 1323
    NPC_UNK232_1388(UNVERIFIED_OP), // v232 1388
    USER_RECIPE_COOLTIME_RESULT(1389), // v271 (v265 1325): local 0x1453aab30, 4 4
    NPC_UNK232_1389(UNVERIFIED_OP), // v232 1389
    COOLTIME_SET_MS(1390), // v271: GMS's cooldown packet (local, VM-protected handler): 4 n {4 skill, 4 ms, 4 (0 or a linked skill)}; GMS never sends 1646
    NPC_UNK232_1390(UNVERIFIED_OP), // v232 1390
    SKILL_COOLTIME_SET_M(1391), // v271 (v265 1326): CUserLocal table position (+65, handler 0x145458800 is VM code like v265's), medium
    SET_CARRY_REACTOR_INFO(1395), // v265 1330
    // CUnk::OnPacket
    UNK203_1246(UNVERIFIED_OP), // v232 1395
    REACTOR_SKILL_USE_REQUEST(1396), // v265 1331
    UNK203_1247(UNVERIFIED_OP), // v232 1396
    OPEN_BATTLE_PVP_CHAMP_SELECT_UI(1397), // v265 1332
    UNK203_1248(UNVERIFIED_OP), // v232 1397
    BATTLE_PVP_ITEM_DROP_SOUND(1398), // v265 1333
    UNK203_1249(UNVERIFIED_OP), // v232 1398
    SET_MESO_COUNT_BY_SCRIPT(1399), // v265 1334
    UNK203_1250(UNVERIFIED_OP), // v232 1399
    PLANT_POT_CLICK_RESULT(1400), // v265 1335
    ROYAL_GUARD_ATTACK(1404), // v271 (v265 1339): local table, reads 1 as v265 (Mihile Royal Guard counter)
    ENTER_FIELD_PSYCHIC_INFO(1405), // v271 (v265 1341): CUserLocal table, reads as v265
    NPC_UNK200_1222(UNVERIFIED_OP), // v232 1405
    NPC_UNK200_1223(UNVERIFIED_OP), // v232 1406
    NPC_UNK200_1224(UNVERIFIED_OP), // v232 1407
    NPC_UNK209_1299(UNVERIFIED_OP), // v232 1408
    BONUS_ATTACK_DELAY_REQUEST(UNVERIFIED_OP), // v271 1410 (and v265 1348) read 2 2 1, not a skill/delay list; delayed bonus attacks use USER_BONUS_ATTACK_REQUEST's delay int
    CREATE_AREA_DOT_INFO(1411), // v265 1349
    GROUP_CHAIR_INVITE(1413), // v271 (v265 1351): CUserLocal 0x1454b62b0, 4 as v265; v232 1058; v265 RE 0x144f001a0: int inviter; answers with in 463 USER_GROUP_CHAIR_INVITE_RESPONSE
    SET_SLOW_DOWN(1415), // v271 (v265 1353): CUserLocal table, reads as v265
    REGISTER_EXTRA_SKILL(1416), // v271 (v265 1354): local table, entry reader 0x1411b0c00 as v265
    RES_WARRIOR_LIFT_MOB_INFO(1417), // v265 1355
    SHOOT_OBJECT_CREATED(1425), // v271: GMS answers In 934 with 1, the request's leading int, skill, slv, n, n x object id (21 bytes for one)
    LIGHTNING_UNION_SUB_ATTACK(1426), // v271 (v265 1363): CUserLocal table, reads as v265
    GREATER_DARK_SERVANT_SWAP_RESULT(1430), // v271 (v265 1369): local 0x1454c8040, 4 4 4 4 1 1 (v265 4 4 1)
    ACE_IN_THE_HOLE_FINISHER(1431), // v271 (v265 1370): CUserLocal table, reads as v265
    TIDE_OF_BATTLE(1432), // v265 1371
    CADENA_VOID_STRIKE_REQUEST(1435), // v271 (v265 1374): CUserLocal table, reads as v265
    REACTOR_IDK(UNVERIFIED_OP), // v232 1435
    USER_WEATHER_EFFECT(1438), // v265 1377
    PINK_BEAN_DECREASE(1439), // v271 (v265 1378): user local table, as v265
    REACTOR_IDK2(UNVERIFIED_OP), // v232 1440
    REACTOR_UNK200_1259(UNVERIFIED_OP), // v232 1441
    MEDAL_OBTAINED_MESSAGE(1442), // v271 0x145492670: 4, 4 medal id -> chat line "Obtained <%s> medal."
    REACTOR_UNK202_1266(UNVERIFIED_OP), // v232 1442
    // CField_MultiStage::OnPacket
    MULTI_STAGE_SET_STAGE(UNVERIFIED_OP), // v232 1442
    REACTOR_UNK202_1267(UNVERIFIED_OP), // v232 1443
    MULTI_STAGE_SET_MONSTER_GAUGE(UNVERIFIED_OP), // v232 1443
    // CUnk::OnPacket      v223.1
    UNK203_1284(UNVERIFIED_OP), // v232 1450
    UNK203_1285(UNVERIFIED_OP), // v232 1451
    SUMMON_SEQUENCE_ATTACK_INSTRUCTIONS(UNVERIFIED_OP), // v265 1452; no v271 case (v265 1450 -> 1508, 1453 -> 1509)
    UNK203_1286(UNVERIFIED_OP), // v232 1452
    QUEST_ARRIVED_EFFECT(1453), // v271 0x1454cfcb0: Effect/BasicEff.img/QuestEffect/<name>, "A quest has arrived" light bulb notice
    UNK203_1287(UNVERIFIED_OP), // v232 1453
    // CFieldUnk      v223.1
    UNK212_1366(UNVERIFIED_OP), // v232 1454
    UNK212_1367(UNVERIFIED_OP), // v232 1455
    UNK212_1368(UNVERIFIED_OP), // v232 1456
    UNK212_1369(UNVERIFIED_OP), // v232 1457
    // CFieldUnk
    UNK212_1370(UNVERIFIED_OP), // v232 1458
    UNK212_1371(UNVERIFIED_OP), // v232 1459
    // CFieldUnk
    UNK212_1372(UNVERIFIED_OP), // v232 1460
    UNK212_1373(UNVERIFIED_OP), // v232 1461
    USER_CONSUME_ITEM_COOLTIME(1467), // v271 (v265 1407): local 0x1454de290
    URSUS_SURVIVORS_COUNTER(1470), // v271 (v265 1410): CUserLocal table, reads as v265
    // CFieldUnk
    UNK212_1384(UNVERIFIED_OP), // v232 1472
    // CFieldUnk
    UNK212_1386(UNVERIFIED_OP), // v232 1474
    UNK212_1387(UNVERIFIED_OP), // v232 1475
    // CFieldUnk
    UNK212_1388(UNVERIFIED_OP), // v232 1476
    // CFieldUnk
    UNK212_1390(UNVERIFIED_OP), // v232 1478
    UNK212_1391(UNVERIFIED_OP), // v232 1479
    UNK212_1392(UNVERIFIED_OP), // v232 1480
    UNK212_1393(UNVERIFIED_OP), // v232 1481
    UNK212_1394(UNVERIFIED_OP), // v232 1482
    MOB_STUN_IMMUNE_MESSAGE(1483), // v271 0x1454ea760: 4, 4 mob; shows "Protected by monster's unique energy and cannot be stunned." throttled to 30 s
    UNK212_1395(UNVERIFIED_OP), // v232 1483
    UNK212_1396(UNVERIFIED_OP), // v232 1484
    UNK212_1397(UNVERIFIED_OP), // v232 1485
    POOLMAKER(1486), // v271 (v265 1426): CUserLocal table, reads as v265
    UNK212_1398(UNVERIFIED_OP), // v232 1486
    UNK212_1399(UNVERIFIED_OP), // v232 1487
    EGO_WEAPON(1488), // v271 (v265 1428): CUserLocal table, reads as v265
    UNK212_1400(UNVERIFIED_OP), // v232 1488
    UNK212_1401(UNVERIFIED_OP), // v232 1489
    UNK212_1402(UNVERIFIED_OP), // v232 1490
    // CNewPyramid
    NEW_PYRAMID_SET_WAVE(UNVERIFIED_OP), // v232 1491
    DEATH_BLESSING(1492), // v271 (v265 1433): user local table, as v265
    NEW_PYRAMID_SET_LIFE(UNVERIFIED_OP), // v232 1492
    DEATH_BLESSING_MATERIALISATION(1493), // v271 (v265 1434): local 0x145501950
    NEW_PYRAMID_SET_POINT(UNVERIFIED_OP), // v232 1493
    KAIN_STACK_OVER_TIME_SKILL_INCREASE_RESULT(1494), // v271 local 0x1454f29d0 -> 0x1406dbe10: 4 skill, 4 stack, 4 max, 4 interval, 1 (1503 reads summon data)
    REMAIN_INCENSE_ACTIVATION(1495), // v271 (v265 1436): local 0x1454f2a80
    NEW_PYRAMID_RESULT(UNVERIFIED_OP), // v232 1495
    // CFieldUnk (2 diff funcs)
    UNK212_1408(UNVERIFIED_OP), // v232 1496
    UNK212_1409(UNVERIFIED_OP), // v232 1497
    UNK212_1410(UNVERIFIED_OP), // v232 1498
    UNK212_1411(UNVERIFIED_OP), // v232 1499
    UNK212_1412(UNVERIFIED_OP), // v232 1500
    // CFieldUnk (Battlefield)
    UNK212_1413(UNVERIFIED_OP), // v232 1501
    UNK212_1414(UNVERIFIED_OP), // v232 1502
    UNK212_1415(UNVERIFIED_OP), // v232 1503
    UNK212_1416(UNVERIFIED_OP), // v232 1504
    UNK212_1417(UNVERIFIED_OP), // v232 1505
    UNK212_1418(UNVERIFIED_OP), // v232 1506
    UNK212_1419(UNVERIFIED_OP), // v232 1507
    UNK212_1420(UNVERIFIED_OP), // v232 1508
    UNK212_1421(UNVERIFIED_OP), // v232 1509
    // CFieldUnk
    UNK212_1422(UNVERIFIED_OP), // v232 1510
    UNK212_1423(UNVERIFIED_OP), // v232 1511
    // CFieldUnk
    UNK212_1424(UNVERIFIED_OP), // v232 1512
    // CFieldUnk
    UNK212_1425(UNVERIFIED_OP), // v232 1513
    UNK212_1426(UNVERIFIED_OP), // v232 1514
    UNK212_1427(UNVERIFIED_OP), // v232 1515
    UNK212_1428(UNVERIFIED_OP), // v232 1516
    UNK212_1429(UNVERIFIED_OP), // v232 1517
    UNK212_1430(UNVERIFIED_OP), // v232 1518
    SET_MONSTER_DEBUFF_MARK(1519), // v271 (v265 1463): local table
    UNK212_1431(UNVERIFIED_OP), // v232 1519
    UNK212_1432(UNVERIFIED_OP), // v232 1520
    // CUserLocal monster marks (local dispatcher 0x144de0270)
    ATTACK_MONSTER_DEBUFF_MARK(1521), // v271 (v265 1465): int skill, int max stack, int, int n, n x (int mob, int delay), int attack skill
    UNK212_1433(UNVERIFIED_OP), // v232 1521
    SET_MOB_ADVENTURER_MARK(1522), // v271 (v265 1466): int skill, byte isSet; set: rest is read by the mark object of that skill
    UNK212_1434(UNVERIFIED_OP), // v232 1522
    ATTACK_ADVENTURER_MARK(1523), // v271 (v265 1467): 4 4 4 4 4 4: int original skill, int skill, int, int n, n x (int mob, int)
    UNK212_1435(UNVERIFIED_OP), // v232 1523
    UNK212_1436(UNVERIFIED_OP), // v232 1524
    // CFieldUnk (also includes the next cfieldunk op
    UNK212_1437(UNVERIFIED_OP), // v232 1525
    UNK212_1438(UNVERIFIED_OP), // v232 1526
    UNK212_1439(UNVERIFIED_OP), // v232 1527
    // CFieldUnk
    UNK212_1440(UNVERIFIED_OP), // v232 1528
    // CFieldUnk
    UNK212_1444(UNVERIFIED_OP), // v232 1532
    UNK212_1445(UNVERIFIED_OP), // v232 1533
    UNK212_1446(UNVERIFIED_OP), // v232 1534
    // CFieldUnk (with 2 other lower ops) (was bingo, not sure now)
    BINGO_ENTER_GAME(UNVERIFIED_OP), // v232 1539
    BINGO_HOST_NUMBER(UNVERIFIED_OP), // v232 1540
    BINGO_HOST_NUMBER_READY(UNVERIFIED_OP), // v232 1541
    BINGO_ADD_RANK(UNVERIFIED_OP), // v232 1542
    BINGO_REMOVE_RANK(UNVERIFIED_OP), // v232 1543
    BINGO_FINISH_RANK(UNVERIFIED_OP), // v232 1544
    BINGO_CHECK_NUMBER_ACK(UNVERIFIED_OP), // v232 1545
    BINGO_GAME_STATE(UNVERIFIED_OP), // v232 1546
    UNK212_1459(UNVERIFIED_OP), // v232 1547
    KANNA_SOUL_BOMB(UNVERIFIED_OP), // v265 1550; v265 1542..1550 have no v271 local case
    RED_LEAF_INFO(1553),
    // CField_Hekaton::OnPacket
    DO_HEKATON_SKILL(UNVERIFIED_OP), // v232 1553
    DO_HEKATON_SKILL_2(UNVERIFIED_OP), // v232 1554
    HEKATON_UNK_205_1430(UNVERIFIED_OP), // v232 1555
    HEKATON_UNK_205_1431(UNVERIFIED_OP), // v232 1556
    // CField_Hekaton::OnPacket
    HEKATON_UNK_205_1434(UNVERIFIED_OP), // v232 1559
    OPEN_EGG_HATCH(1560),
    MOD_HAYATO_COMBO(UNVERIFIED_OP), // v265 1561; v265 1560..1562 have no v271 local case; v265 RE CUserLocal 1561 -> 0x144f71610: short combo, drives a gauge UI (between 1560 egg and 1563 sit)
    HYPER_ROCK_PRICE_LIST(1562), // v271 CUserLocal 0x14550bc60: opens the Hyper Teleport Rock window (UI 0x652); 4 n, n x (4 minutes, 4, 4 mesos)
    HYPER_ROCK_BUY_RESULT(1563), // v271 CUserLocal 0x14550bd80: 1 ok, 4, 4 minutes; reads quest-ex 1953 "time" for the message
    // CFieldUnk
    UNK212_1476(UNVERIFIED_OP), // v232 1564
    UNK212_1477(UNVERIFIED_OP), // v232 1565
    UNK212_1478(UNVERIFIED_OP), // v232 1566
    UNK212_1479(UNVERIFIED_OP), // v232 1567
    UNK212_1480(UNVERIFIED_OP), // v232 1568
    COMPLETE_RUNE(UNVERIFIED_OP), // v232 1569
    UNK212_1482(UNVERIFIED_OP), // v232 1570
    SCROLL_RESULT_NOTICE(1575), // v271 local 0x1454e80e0: 1 success, 4 scroll, 4 item, 1; chat line after a potential scroll
    // CFieldUnk
    UNK212_1487(UNVERIFIED_OP), // v232 1575
    // CUserLocal soul weapon results (local dispatcher 0x144de0270)
    SOUL_ENCHANTER_RESULT(1576), // v271 (v265 1516): local table, 1
    // CField
    CHASER_MOVE(UNVERIFIED_OP), // v232 1576
    SOUL_OPTION_RESULT(1577), // v271 (v265 1517): local table, 1 1 4 4 (medium)
    // CFieldUnk
    UNK212_1492(UNVERIFIED_OP), // v232 1577
    CUBE_RESULT_NOTICE(1578), // v271 local 0x1454e8990: 1 ok, 4 cube, 4, 4 item, 1; chat line after a cube
    UNK212_1493(UNVERIFIED_OP), // v232 1578
    // CField_MonsterDefense::OnPacket
    BOUNTY_HUNT_SET_CURRENT_WAVE(UNVERIFIED_OP), // v232 1579
    // CFieldUnk
    GUARD_CASTLE_SET_WAVE(UNVERIFIED_OP), // v232 1580
    GUARD_CASTLE_SET_LIFE(UNVERIFIED_OP), // v232 1581
    // CField_CourtshipDancing::OnPacket
    COURTSHIP_DANCING_STAGE(UNVERIFIED_OP), // v232 1582
    COURTSHIP_DANCING_QUICK_TIME_EVENT(UNVERIFIED_OP), // v232 1583
    // CFieldUnk
    UNK212_1503(UNVERIFIED_OP), // v232 1584
    // CFieldUnk
    UNK212_1499(UNVERIFIED_OP), // v232 1585
    UNK212_1500(UNVERIFIED_OP), // v232 1586
    UNK212_1501(UNVERIFIED_OP), // v232 1587
    UNK212_1502(UNVERIFIED_OP), // v232 1588
    // CChuChuPQ - FoodType
    UNK212_1504(UNVERIFIED_OP), // v232 1590
    UNK212_1505(UNVERIFIED_OP), // v232 1591
    // CField
    UNK203_1427(UNVERIFIED_OP), // v232 1592
    // CFieldUnk
    UNK212_1515(UNVERIFIED_OP), // v232 1601
    // CField
    UNK203_1437(UNVERIFIED_OP), // v232 1602
    UNK203_1438(UNVERIFIED_OP), // v232 1603
    // v271 (v265 1537): 0x14551b500, no mask: B23 codex record, then B12 / B8 into the record's preset (byte +0x16);
    // read only once CharacterData F85 bit 1 created the record
    CODEX_UPDATE(1608), // v271 (v265 1537): local table (1536 -> 1607, 1538 -> 1609)
    FAMILIAR_ADD_RESULT(1609), // v271 (v265 1538): 0x14551b5d0, int n, n x B56 GW_Familiar (no key, no mask)
    // v271 (v265 1539): 0x14551b940, int n, n x 21 bytes: int owner, byte page (badge id / 4), 4 x int collected mask
    FAMILIAR_BADGE_ADD_RESULT(1610), // v271 (v265 1539): local table
    FAMILIAR_MODIFY_RESULT(1611), // v271 (v265 1540): 0x14551bad0, as v265 except Mystic Frontier is type 15 (was 12)
    LUCID_UNK_200_1431(UNVERIFIED_OP), // v232 1614
    // CPapulatusField
    PAPULATUS_TELEPORT(UNVERIFIED_OP), // v232 1615
    // Damien/Lucid field?
    UNK_232_FIELD_1618(UNVERIFIED_OP), // v232 1618
    USER_BEAUTY_DATA_RESULT(1621), // v271 (v265 1557): CUserLocal table (keyed sub-readers not compared); v265 case 1557 -> keyed Beauty Album responses (0x142f45670)
    // CBossPixRog
    UNK212_1535(UNVERIFIED_OP), // v232 1622
    UNK212_1538(UNVERIFIED_OP), // v232 1622
    HARVEST_RESULT(1623), // v271 (v265 1559): CUserLocal table, reads as v265
    UNK212_1536(UNVERIFIED_OP), // v232 1623
    SUMMONED_GRAVITY_CRUSH_MAX_DAMAGE(UNVERIFIED_OP), // v265 1624; v271 table tail (1684-1688) not matched, not sent
    UNK212_1537(UNVERIFIED_OP), // v232 1624
    CHARACTER_DATA_LOG(1626), // v265 1568
    UNK212_1539(UNVERIFIED_OP), // v232 1626
    // CFieldUnk
    UNK212_1540(UNVERIFIED_OP), // v232 1627
    // CFieldUnk
    UNK212_1541(UNVERIFIED_OP), // v232 1628
    UNK212_1542(UNVERIFIED_OP), // v232 1629
    SUMMONED_SPIRIT_DOMAIN_STATE(UNVERIFIED_OP), // v265 1630; no v271 case found, not sent
    UNK212_1543(UNVERIFIED_OP), // v232 1630
    UNK212_1544(UNVERIFIED_OP), // v232 1631
    // CField::OnPacket, part 3
    HORNTAIL_TIMER(UNVERIFIED_OP), // v232 1631
    UNK212_1545(UNVERIFIED_OP), // v232 1632
    ZAKUM_TIMER(UNVERIFIED_OP), // v232 1632
    UNK212_1546(UNVERIFIED_OP), // v232 1633
    UNK212_1547(UNVERIFIED_OP), // v232 1634
    UNK212_1548(UNVERIFIED_OP), // v232 1635
    UNK212_1549(UNVERIFIED_OP), // v232 1636
    // CFieldUnk
    UNK212_1550(UNVERIFIED_OP), // v232 1637
    // CFieldUnk
    UNK212_1551(UNVERIFIED_OP), // v232 1638
    UNK212_1552(UNVERIFIED_OP), // v232 1639
    UNK212_1553(UNVERIFIED_OP), // v232 1640
    UNK212_1555(UNVERIFIED_OP), // v232 1642
    UNK212_1556(UNVERIFIED_OP), // v232 1643
    UNK212_1557(UNVERIFIED_OP), // v232 1644
    UNK228_1652(UNVERIFIED_OP), // v232 1645
    SKILL_COOLTIME_SET(1646), // v271 (v265 1586): CUserLocal 0x145458560, 4 n {4 skill, 4 seconds} (client x1000)
    UNK228_1653(UNVERIFIED_OP), // v232 1646
    UNK212_1558(UNVERIFIED_OP), // v232 1647
    // CSummonedPool::OnPacket      v223.1
    SUMMONED_CREATED(1648), // v271 (v265 1588): CSummonedPool 0x1441febc0; Init 0x14413ac20 (see Summoned.summonedCreated)
    UNK212_1559(UNVERIFIED_OP), // v232 1648
    UNK212_1560(UNVERIFIED_OP), // v232 1649
    UNK212_1561(UNVERIFIED_OP), // v232 1650
    UNK212_1562(UNVERIFIED_OP), // v232 1651
    UNK212_1563(UNVERIFIED_OP), // v232 1652
    UNK212_1564(UNVERIFIED_OP), // v232 1653
    UNK212_1565(UNVERIFIED_OP), // v232 1654
    UNK212_1566(UNVERIFIED_OP), // v232 1655
    UNK228_1663(UNVERIFIED_OP), // v232 1656
    UNK228_1664(UNVERIFIED_OP), // v232 1657
    UNK212_1567(UNVERIFIED_OP), // v232 1658
    SUMMONED_REMOVED(1659), // v271 (v265 1599): CSummonedPool 0x1441febc0 case 0x1442012d0, reads 1
    UNK212_1568(UNVERIFIED_OP), // v232 1659
    SUMMONED_MOVE(1660), // v271 (v265 1600): CSummonedPool 0x1441febc0 case 0x144200350, path
    UNK212_1569(UNVERIFIED_OP), // v232 1660
    SUMMONED_ATTACK(1661), // v271 (v265 1601): CSummonedPool 0x1441febc0 case 0x144201520, reads as v265
    UNK212_1570(UNVERIFIED_OP), // v232 1661
    SUMMONED_ATTACK_PVP(1662), // v271 (v265 1602): CSummonedPool 0x1441febc0 case 0x144201b80
    UNK212_1571(UNVERIFIED_OP), // v232 1662
    SUMMONED_SET_REFERENCE(1663), // v271 (v265 1603): CSummonedPool 0x1441febc0 case 0x144201c00
    UNK212_1572(UNVERIFIED_OP), // v232 1663
    SUMMONED_SKILL(1664), // v271 (v265 1604): CSummonedPool 0x1441febc0 case 0x144201560, reads as v265
    UNK212_1573(UNVERIFIED_OP), // v232 1664
    UNK228_1672(UNVERIFIED_OP), // v232 1665
    // blank
    SUMMONED_SKILL_PVP(1666), // v271 (v265 1606): CSummonedPool 0x1441febc0 case 0x144201bc0
    UNK228_1673(UNVERIFIED_OP), // v232 1666
    SUMMONED_UPDATE_HP_TAG(1667), // v271 (v265 1607): CSummonedPool 0x1441febc0 case 0x1442015e0, 4
    UNK228_1674(UNVERIFIED_OP), // v232 1667
    SUMMONED_ATTACK_DONE(1668), // v271 (v265 1608): CSummonedPool 0x1441febc0 case 0x144201620
    UNK228_1675(UNVERIFIED_OP), // v232 1668
    SUMMONED_SET_RESIST(1669), // v271 (v265 1609): CSummonedPool 0x1441febc0 case 0x144201660, 1
    UNK212_1574(UNVERIFIED_OP), // v232 1669
    SUMMONED_ACTION_CHANGE(1670), // v271 (v265 1610): CSummonedPool 0x1441febc0 case 0x1442016a0, 1
    UNK212_1575(UNVERIFIED_OP), // v232 1670
    SUMMONED_ASSIST_ATTACK_REQUEST(1671), // v271 (v265 1611): CSummonedPool 0x1441febc0 case 0x1442016e0
    UNK228_1678(UNVERIFIED_OP), // v232 1671
    SUMMONED_SUMMON_ATTACK_ACTIVE(1672), // v271 (v265 1612): CSummonedPool 0x1441febc0 case 0x144201740, 1
    UNK228_1679(UNVERIFIED_OP), // v232 1672
    UNK228_1680(UNVERIFIED_OP), // v232 1673
    SUMMONED_BEHOLDER_REVENGE_ATTACK(1674), // v271 (v265 1614): CSummonedPool 0x1441febc0 case 0x144201800, 4 n {4 mob}
    UNK228_1681(UNVERIFIED_OP), // v232 1674
    SUMMONED_DO_ATTACK_REQUEST(1675), // v271 (v265 1615): CSummonedPool 0x1441febc0 case 0x144201860, 4 1
    UNK228_1682(UNVERIFIED_OP), // v232 1675
    SUMMONED_STATE_CHANGED(1676), // v271 (v265 1616): CSummonedPool 0x1441febc0 case 0x1442018c0
    UNK228_1683(UNVERIFIED_OP), // v232 1676
    SUMMONED_SET_NEW_POSITION(1677), // v271 (v265 1617): CSummonedPool 0x1441febc0 case 0x144201900, 4 4
    // CFieldUnk (Boss/Event)
    UNK212_1576(UNVERIFIED_OP), // v232 1677
    SUMMONED_REPOSITION_SUMMON(1678), // v271 (v265 1618): CSummonedPool 0x1441febc0 case 0x144201940, 4 4 4 4
    UNK212_1577(UNVERIFIED_OP), // v232 1678
    SUMMONED_ASSIST_SPECIAL_ATTACK_REQUEST(1679), // v271 (v265 1619): CSummonedPool 0x1441febc0 case 0x144201980, reads only 4 skill (v265 had 1 4 4 more)
    UNK212_1578(UNVERIFIED_OP), // v232 1679
    UNK212_1579(UNVERIFIED_OP), // v232 1680
    SUMMONED_EFFECT(1681), // v271 (v265 1620): CSummonedPool 0x1441febc0 case 0x144201a00; 1680 is a new no-payload case
    UNK212_1580(UNVERIFIED_OP), // v232 1681
    SUMMONED_SUMMON_USE_SPECIFIED_SKILL(1682), // v271 (v265 1621): CSummonedPool 0x1441febc0 case 0x144201a40, 4 4 1
    UNK212_1581(UNVERIFIED_OP), // v232 1682
    SUMMONED_UPGRADE_STAGE(1683), // v271 (v265 1622): CSummonedPool 0x1441febc0 case 0x144201a80
    SUMMONED_HIT(1689), // v271 (v265 1631): CSummonedPool 0x1441febc0 case 0x1442015a0, 1 4 4 1 as v265
    // CMobPool::OnPacket      v223.1
    MOB_ENTER_FIELD(1690), // v271 (v265 1632): handler 0x143cabf70: new string after the first byte; init 0x143b75e90 one int fewer near the end; MobStat sent empty
    MOB_LEAVE_FIELD(1701), // v271 (v265 1643): handler 0x143cac810, three ints (v265 two) for death types 0, 1, 5-11
    MOB_CHANGE_CONTROLLER(1702), // v271 (v265 1644): handler 0x143cac900 -> 0x143cacff0 reads as v265 (no new string); init as MOB_ENTER_FIELD
    MOB_SET_AFTER_ATTACK(1703), // v271 (v265 1645): CMobPool stub 13 0x143cac980, 4 2 4 4 1 as v265
    MOB_EFFECT_BY_SKILL(1704), // v271 (v265 1646): CMobPool stub 14 0x143caca20, 4 4 4 as v265
    // CHundredRSP
    UNK212_1601(UNVERIFIED_OP), // v232 1705
    UNK212_1602(UNVERIFIED_OP), // v232 1706
    UNK212_1603(UNVERIFIED_OP), // v232 1707
    UNK212_1604(UNVERIFIED_OP), // v232 1708
    // CrcKeyChanged (below)
    // CMobPool::OnMobPacket      v223.1
    MOB_MOVE(1709), // v271 (v265 1651): per-mob router 0x143cad580 (1709..1845) case 0 -> 0x143bb2740, reads as v265 (22 + path 0x143cde610)
    UNK212_1605(UNVERIFIED_OP), // v232 1709
    // CFieldUnk
    UNK212_1606(UNVERIFIED_OP), // v232 1710
    // CAdminShopDlg::OnPacket
    ADMIN_SHOP_RESULT(UNVERIFIED_OP), // v232 1710
    UNK212_1607(UNVERIFIED_OP), // v232 1711
    ADMIN_SHOP_COMMODITY(UNVERIFIED_OP), // v232 1711
    UNK212_1608(UNVERIFIED_OP), // v232 1712
    // CFieldUnk
    UNK212_1609(UNVERIFIED_OP), // v232 1713
    // CHundredShooting
    UNK212_1610(UNVERIFIED_OP), // v232 1714
    UNK212_1611(UNVERIFIED_OP), // v232 1715
    UNK212_1612(UNVERIFIED_OP), // v232 1716
    UNK212_1613(UNVERIFIED_OP), // v232 1717
    // CFieldUnk
    UNK212_1614(UNVERIFIED_OP), // v232 1718
    // CFieldUnk (Interesting)
    UNK212_1615(UNVERIFIED_OP), // v232 1719
    MOB_CTRL_ACK(1720), // v271 (v265 1662): 0x143bb3560, 2 1 4 4 2 4 4 1 as v265
    UNK212_1616(UNVERIFIED_OP), // v232 1720
    UNK212_1617(UNVERIFIED_OP), // v232 1721
    MOB_STAT_SET(1722), // v271 (v265 1664): mob router 0x143bb3980; MobStat bits renumbered (V271MobStat), body after the stats as v265
    // CFieldUnk
    UNK212_1618(UNVERIFIED_OP), // v232 1722
    MOB_STAT_RESET(1723), // v271 (v265 1665): mob router 0x143bb3a00; mask renumbered (V271MobStat), body as v265
    // CCrazyPlash
    UNK212_1619(UNVERIFIED_OP), // v232 1723
    MOB_SUSPEND_RESET(1724), // v271 (v265 1666): mob router 0x143bb3b30, 1
    UNK212_1620(UNVERIFIED_OP), // v232 1724
    MOB_AFFECTED(1725), // v271 (v265 1667): mob router 0x143bb4180, 4 2 1 4 as v265
    UNK212_1621(UNVERIFIED_OP), // v232 1725
    MOB_DAMAGED(1726), // v271 (v265 1668): 0x143bb4280, 1 8 [8 8] as v265
    UNK212_1622(UNVERIFIED_OP), // v232 1726
    MOB_DAMAGED_BY_SKILL(1727), // v271 (v265 1669): mob router 0x143bb4370, as v265
    UNK212_1623(UNVERIFIED_OP), // v232 1727
    UNK212_1624(UNVERIFIED_OP), // v232 1728
    // CField::OnPacket
    ZAKUM_TIMER2(UNVERIFIED_OP), // v232 1728
    MOB_SPECIAL_EFFECT_BY_SKILL(1729), // v271 (v265 1671): mob router 0x143bb4c30, 4 4 2 as v265
    UNK212_1625(UNVERIFIED_OP), // v232 1729
    UNK212_1626(UNVERIFIED_OP), // v232 1730
    MOB_CRC_DATA_REQUEST(1731), // v271 (v265 1673): mob router 0x143bbb3b0, no payload
    UNK212_1627(UNVERIFIED_OP), // v232 1731
    MOB_HP_INDICATOR(1732), // v271 (v265 1674): 0x143bb5c50, 4 1 1 (one byte more)
    // CFieldUnk (Seatron?)
    UNK212_1628(UNVERIFIED_OP), // v232 1732
    MOB_CATCH_EFFECT(1733), // v271 (v265 1675): mob router 0x143bb4a90, 1 1
    UNK212_1629(UNVERIFIED_OP), // v232 1733
    MOB_STEAL_EFFECT(1734), // v271 (v265 1676): mob router 0x143bb4af0, 4
    UNK212_1630(UNVERIFIED_OP), // v232 1734
    MOB_EFFECT_BY_ITEM(1735), // v271 (v265 1677): mob router 0x143bb4be0, 4 1
    // CFieldUnk
    UNK212_1631(UNVERIFIED_OP), // v232 1735
    MOB_SPEAKING(1736), // v271 (v265 1678): mob router 0x143bb6330, 4 4
    // CPolloFritto::TreasureHunting
    POLLO_FRITTO_TREASURE_HUNT_UNK216_1646(UNVERIFIED_OP), // v232 1736
    POLLO_FRITTO_TREASURE_HUNT_UNK216_1647(UNVERIFIED_OP), // v232 1737
    MOB_SKILL_DELAY(1738), // v271 (v265 1680): mob router 0x143bb63d0, 4 4 4 4 4 n x rect; GMS 1738 = 28 bytes, answered by In 1323
    // CFieldUnk (plus 2 below seren?)
    UNK227_1706(UNVERIFIED_OP), // v232 1738
    ESCORT_FULL_PATH(1739), // v271 (v265 1681): mob router 0x143bb6440, as v265
    //These somehow only work in Hilla's Tower(262031300)
    HILLA_VAMP_EFFECT_START(UNVERIFIED_OP), // v232 1739
    ESCORT_STOP_END_PERMISSION(1740), // v271 (v265 1682): mob router 0x143c11990, no payload
    HILLA_VAMP_EFFECT_MOB_ABSORB(UNVERIFIED_OP), // v232 1740
    ESCORT_STOP_BY_SCRIPT(1741), // v271 (v265 1683): mob router 0x143c11a10, no payload
    HILLA_VAMP_EFFECT_HILLA_ABSORB(UNVERIFIED_OP), // v232 1741
    ESCORT_STOP_SAY(1742), // v271 (v265 1684): mob router 0x143c11aa0, 4 4 1 1 [S 4]
    // CFieldUnk
    UNK227_1710(UNVERIFIED_OP), // v232 1742
    ESCORT_RETURN_BEFORE(1743), // v271 (v265 1685): mob router 0x143c11cd0, [4]
    UNK227_1711(UNVERIFIED_OP), // v232 1743
    MOB_NEXT_ATTACK(1744), // v271 (v265 1686): mob router 0x143c121c0, 4
    MOB_FORCE_ATTACK(1745), // v271 mob router 0x143c12320: 4 (> 0), 4 attack index, 4 (read for index 7); GMS: the Black Mage phase 2 attack 0 [1, 0, 0]
    // CFieldUnk
    UNK227_1713(UNVERIFIED_OP), // v232 1745
    UNK227_1715(UNVERIFIED_OP), // v232 1747
    MOB_TELEPORT_REQUEST(1748), // v271 (v265 1690): mob router 0x143c11e90, as v265
    MOB_ATTACK_ACTION(1750), // v271 mob router 0x143bb6960: 4 attack index, 1; plays that attack's action (GMS: after each MOB_SKILL_DELAY)
    MOB_FORCED_ACTION(1751), // v271 mob router 0x143bb69b0: 4 action (plays 30 + it), 1 force (0: skipped while the mob is busy); GMS: the Black Mage's field skill actions
    // CFieldUnk, new 228 slime boss?
    UNK228_1757(UNVERIFIED_OP), // v232 1751; *
    MOB_SKILL_ACTION(1752), // v271 mob router stub 0x143cad99e -> 0x143bb6a00: 4 mob, 4 action - 1 (client action = 30 + it: skill1..), byte with hit (also runs the skill's hit on the targets), byte flag (mob +0x1118); GMS: Arkarium's Screen Crack [10, 1, 0] with ARKARIUM_SCREEN_CRACK type 2
    // CFieldUnk
    UNK228_1758(UNVERIFIED_OP), // v232 1752
    UNK228_1759(UNVERIFIED_OP), // v232 1753
    UNK228_1760(UNVERIFIED_OP), // v232 1754
    MOB_FORCED_SKILL_ACTION(1755), // v271 (v265 1697): mob router 0x143bbb470, 4
    UNK228_1761(UNVERIFIED_OP), // v232 1755
    UNK228_1762(UNVERIFIED_OP), // v232 1756
/* 1757 */    MOB_BIND_REGISTER(1757), // v271 (v265 1699): mob router 0x143bb8730, 1 + 5-int records as v265
    UNK228_1763(UNVERIFIED_OP), // v232 1757
    // CMultiBingo
    UNK212_1632(UNVERIFIED_OP), // v232 1758
    MOB_ON_EKILL_DAMAGE(1759), // v271 (v265 1701): mob router 0x143bb8bd0, no payload
    UNK212_1633(UNVERIFIED_OP), // v232 1759
    MOB_ATTACK_BLOCK(1760), // v271 (v265 1702): mob router 0x143bb8c10, 4 n {4}
    UNK212_1634(UNVERIFIED_OP), // v232 1760
    MOB_ATTACK_PRIORITY(1761), // v271 (v265 1703): mob router 0x143bb8d70, as v265
    UNK212_1635(UNVERIFIED_OP), // v232 1761
    MOB_ATTACK_TIME_INFO(1762), // v271 (v265 1704): mob router 0x143bb9050, as v265
    UNK212_1636(UNVERIFIED_OP), // v232 1762
    MOB_DAMAGE_SHARE_INFO_TO_LOCAL(1763), // v271 (v265 1705): mob router 0x143c2d830
    UNK212_1637(UNVERIFIED_OP), // v232 1763
    MOB_DAMAGE_SHARE_INFO_TO_REMOTE(1764), // v271 (v265 1706): mob router 0x143c2d8c0
    UNK212_1638(UNVERIFIED_OP), // v232 1764
    MOB_BREAK_DOWN_TIME_ZONE_TIME_OUT(1765), // v271 (v265 1707): mob router 0x143c2d920, no payload
    UNK212_1639(UNVERIFIED_OP), // v232 1765
    MOB_MOVE_AREA_SET(1766), // v271 (v265 1708): mob router 0x143bb9220, 4
    // CMultiBingo2?
    UNK212_1640(UNVERIFIED_OP), // v232 1766
    MOB_DO_SKILL_BY_HIT(1767), // v271 (v265 1709): mob router 0x143c36e10, 4 4
    UNK212_1641(UNVERIFIED_OP), // v232 1767
    MOB_CASTING_BAR_SKILL(1768), // v271 (v265 1710): mob router 0x143bb9260, as v265
    UNK212_1642(UNVERIFIED_OP), // v232 1768
    MOB_FLY_TARGET(1769), // v271 (v265 1711): mob router 0x143c36fb0, 4 4
    UNK212_1643(UNVERIFIED_OP), // v232 1769
    MOB_BOUNCE_ATTACK_SKILL(1770), // v271 (v265 1712): mob router 0x143bb9ff0, as v265
    UNK212_1644(UNVERIFIED_OP), // v232 1770
    MOB_AREA_INSTALL_BY_HIT(1771), // v271 (v265 1713): mob router 0x143bba060, 4 4
    UNK212_1645(UNVERIFIED_OP), // v232 1771
    MOB_LTRB_DAMAGE_SKILL(1772), // v271 (v265 1714): mob router 0x143bba5e0
    UNK212_1646(UNVERIFIED_OP), // v232 1772
    MOB_SUMMON_SUB_BODY(1773), // v271 (v265 1715): mob router 0x143bba890, 4 4
    UNK212_1647(UNVERIFIED_OP), // v232 1773
    MOB_LASER_CONTROL(1774), // v271 (v265 1716): mob router 0x143bbac80, 4 4 1
    UNK212_1648(UNVERIFIED_OP), // v232 1774
    UNK212_1649(UNVERIFIED_OP), // v232 1775
    UNK212_1650(UNVERIFIED_OP), // v232 1776
    UNK212_1651(UNVERIFIED_OP), // v232 1777
    // CFieldUnk (Boss/Event)
    UNK212_1652(UNVERIFIED_OP), // v232 1778
    MOB_SCALE(1779), // v271 (v265 1720): mob router 0x143c36040, 4
    UNK212_1653(UNVERIFIED_OP), // v232 1779
    UNK212_1654(UNVERIFIED_OP), // v232 1780
    MOB_PART_HIT(1781), // v271 mob router: CMob vtable +0x158 (base empty 0x143c45420; body-part mobs 0x143ff53c0): str part name -> that part (map at +0x15d8) steps to its next damage frame (0x140f2ef80/0x140f2ef10). GMS: Zakum body "box_body" / arms "R_Arm_00N_bound" on every hit
    UNK212_1655(UNVERIFIED_OP), // v232 1781
    MOB_FORCE_CHASE(1782), // v271 (v265 1723): mob router 0x143bbaf00, 1
    UNK212_1656(UNVERIFIED_OP), // v232 1782
    UNK212_1657(UNVERIFIED_OP), // v232 1783
    MOB_HANG_OVER_REQUEST(1784), // v271 (v265 1725): mob router 0x143bbb0f0, 1
    UNK212_1658(UNVERIFIED_OP), // v232 1784
    MOB_HANG_OVER_RELEASE_REQUEST(1785), // v271 (v265 1726): mob router 0x143bbb150, 1
    // CSurvivorField
    SURVIVOR_FIELD_UNK215_1674(UNVERIFIED_OP), // v232 1785
    MOB_DEAD_FPS_MODE(1786), // v271 (v265 1727): mob router 0x143bbb1b0, 4
    SURVIVOR_FIELD_UNK215_1675(UNVERIFIED_OP), // v232 1786
    MOB_AIR_HIT(1787), // v271 (v265 1728): mob router 0x143bbb1e0, 2 2
    SURVIVOR_FIELD_UNK215_1676(UNVERIFIED_OP), // v232 1787
    MOB_DEMIAN_DELAYED_ATTACK_CREATE(1788), // v271 (v265 1729): mob router 0x143c46860, as v265
    MOB_REGISTER_REL_MOB_ZONE(1789), // v271 (v265 1730): mob router 0x143c28240, 4 4
    SURVIVOR_FIELD_UNK215_1678(UNVERIFIED_OP), // v232 1789
    MOB_UNREGISTER_REL_MOB_ZONE(1790), // v271 (v265 1731): mob router 0x143c28440, 4 4
    SURVIVOR_FIELD_UNK215_1679(UNVERIFIED_OP), // v232 1790
    SURVIVOR_FIELD_OPEN_SURVIVOR_VOTE(UNVERIFIED_OP), // v232 1791
    MOB_BURNED_INFO_REMOVE(1792), // v271 (v265 1733): mob router 0x143c485d0, 4 n {4}; v265 RE mob router -> 0x1436412c0: int n, n x int (erases MobStat+0x770 entries with that key)
    NPC_UPDATE_LIMITED_INFO(UNVERIFIED_OP), // v265 1792; v271 1792 is MOB_BURNED_INFO_REMOVE
    SURVIVOR_FIELD_UNK215_1681(UNVERIFIED_OP), // v232 1792
    MOB_SPECIAL_SELECTED_EFFECT_BY_SKILL(1793), // v271 (v265 1734): mob router 0x143c486d0, 4 4 4 4; v265 RE 0x1436413d0: int skill, int chr, int, int (stored on the mob)
    NPC_SET_QUIZ_SCORE(UNVERIFIED_OP), // v265 1793; v271 1793 is MOB_SPECIAL_SELECTED_EFFECT_BY_SKILL
    SURVIVOR_FIELD_UNK215_1682(UNVERIFIED_OP), // v232 1793
    NPC_SET_QUIZ_SCORE_ANI(1794),
    SURVIVOR_FIELD_UNK215_1683(UNVERIFIED_OP), // v232 1794
    // CFieldUnk
    VOYAGE_FIELD_INFO(UNVERIFIED_OP), // v232 1795
    // CField
    UNK203_1570(UNVERIFIED_OP), // v232 1796
    NPC_SET_EMOTION(1797),
    UNK203_1571(UNVERIFIED_OP), // v232 1797
    NPC_SET_CHARACTER_BASE_ACTION(1798),
    // CFieldUnk
    UNK212_1672(UNVERIFIED_OP), // v232 1798
    MOB_STATUS_EFFECT_BY_SKILL(1799), // v271 (v265 1740): mob router 0x143c12950, 4 4; v265 RE 0x14360b200: int skill (0x141078180: 152100012 Umbral Brand + 3 others), int chrId
    NPC_VIEW_OR_HIDE(UNVERIFIED_OP), // v265 1799; v271 1799 is MOB_STATUS_EFFECT_BY_SKILL
    UNK212_1673(UNVERIFIED_OP), // v232 1799
    NPC_PRESENT_ITEM_SET(1800),
    UNK212_1674(UNVERIFIED_OP), // v232 1800
    VERUS_HILLA_BLACK_HAND(1801), // v271 (v265 1742): mob router 0x143c28550, as v265; v265 RE (boss-mech): mob pool 0x14369eb30 (int oid) -> 0x1436201c0; n x (4 id, skill sub 0x140b699b0)
    NPC_PRESENT_TIME_SET(UNVERIFIED_OP), // v265 1801; v271 1801 is VERUS_HILLA_BLACK_HAND
    UNK212_1675(UNVERIFIED_OP), // v232 1801
    VERUS_HILLA_SPIRIT(1802), // v271 (v265 1743): mob router 0x143c28650, as v265; v265 RE (boss-mech): mob pool -> 0x1436202c0; 4, int list, 2, 2, skill sub (not the ref's spirit layout)
    NPC_RESET_SPECIAL_ACTION(UNVERIFIED_OP), // v265 1802; v271 1802 is VERUS_HILLA_SPIRIT
    UNK212_1676(UNVERIFIED_OP), // v232 1802
    UNK212_1677(UNVERIFIED_OP), // v232 1803
    UNK212_1678(UNVERIFIED_OP), // v232 1804
    NPC_SET_NOTICE_BOARD_INFO(1805),
    // CFieldUnk
    UNK212_1679(UNVERIFIED_OP), // v232 1805
    UNK212_1680(UNVERIFIED_OP), // v232 1806
    UNK212_1681(UNVERIFIED_OP), // v232 1807
    UNK212_1682(UNVERIFIED_OP), // v232 1808
    UNK212_1683(UNVERIFIED_OP), // v232 1809
    UNK212_1684(UNVERIFIED_OP), // v232 1810
    UNK212_1685(UNVERIFIED_OP), // v232 1811
    // CFieldUnk
    UNK212_1686(UNVERIFIED_OP), // v232 1812
    UNK212_1687(UNVERIFIED_OP), // v232 1813
    UNK212_1688(UNVERIFIED_OP), // v232 1814
    UNK212_1689(UNVERIFIED_OP), // v232 1815
/*1816*/    MOB_NEXT_TARGET_FROM_SVR(1816), // v271 (v265 1757): mob router 0x143c58120, 4 char id -> CMob+0x1384 (target); GMS: (mob, controller) right before each control grant, (mob, 0) for every controlled mob when leaving the map
    UNK212_1690(UNVERIFIED_OP), // v232 1816
    UNK212_1691(UNVERIFIED_OP), // v232 1817
    UNK212_1692(UNVERIFIED_OP), // v232 1818
    // CFieldUnk
    UNK212_1693(UNVERIFIED_OP), // v232 1819
    UNK212_1694(UNVERIFIED_OP), // v232 1820
    // CMessageBoxPool::OnPacket      v223.1
    CREATE_MESSAGE_BOX_FAILED(1821),
    UNK212_1695(UNVERIFIED_OP), // v232 1821
    MESSAGE_BOX_ENTER_FIELD(1822),
    UNK212_1696(UNVERIFIED_OP), // v232 1822
    MESSAGE_BOX_LEAVE_FIELD(1823),
    UNK212_1697(UNVERIFIED_OP), // v232 1823
    UNK212_1698(UNVERIFIED_OP), // v232 1824
    UNK212_1699(UNVERIFIED_OP), // v232 1825
    MOB_FREEZE_EFFECT(1826), // v271 (v265 1767): mob router 0x143c484e0, 4
    // v271 mob router 0x143cad580 case 1827 -> CMob vtable +0x178 (0x141fd3bc0): 4 mob, 4 subtype (raw), by subtype;
    // 0x0e (0x141fd9050) starts a pattern: 4, 4 run id, 4 pattern id, 8, 4, then the runner's start data
    MOB_REQUEST_RESULT(1827), // v271: mob router 0x143cad580 case 1827 -> CMob vtable +0x178: 4 mob, 4 subtype
    MOB_LAST_HIT_INFO(1831), // v271 mob router 0x143bbef10: 4 char, 4 -> CMob+0x14b0/+0x14b4 (stored only); GMS sends (mob, killer, 3000) on every kill, before the control release and MOB_LEAVE_FIELD
    // CCastleColumn::OnPacket  v223
    CASTLE_COLUMN_START(UNVERIFIED_OP), // v232 1834
    FIELD_SET_EVENT(1835), // v271: BossFieldSetInfo handler output; GMS First Adversary: type 25 timer tick (4 ms, str name), type 4 field effect
    CASTLE_COLUMN_UPDATE(UNVERIFIED_OP), // v232 1835
    CASTLE_COLUMN_END(UNVERIFIED_OP), // v232 1836
    // CFieldUnk
    UNK220_1751(UNVERIFIED_OP), // v232 1838
    UNK223_1775(UNVERIFIED_OP), // v232 1838
    UNK220_1752(UNVERIFIED_OP), // v232 1839
    UNK220_1753(UNVERIFIED_OP), // v232 1840
    UNK220_1754(UNVERIFIED_OP), // v232 1841
    UNK220_1755(UNVERIFIED_OP), // v232 1842
    UNK220_1756(UNVERIFIED_OP), // v232 1843
    UNK220_1757(UNVERIFIED_OP), // v232 1844
    MOB_ATTACKED_BY_MOB(1845), // v271 (v265 1783): mob router 0x143bb5e00, as v265
    UNK220_1758(UNVERIFIED_OP), // v232 1845
    UNK220_1759(UNVERIFIED_OP), // v232 1846
    // CNpcPool::OnPacket      v223.1
    NPC_ENTER_FIELD(1847), // v271 (v265 1785): 4 objid, 4 template, CNpc::Init 0x143d6a8b0 reads as v265; an objid the client already has: 4 only
    NPC_LEAVE_FIELD(1848), // v271 (v265 1786): 4
    NPC_CHANGE_CONTROLLER(1849), // v271 (v265 1787): 1 4, then 4 template + Init only for an objid the client doesn't have (on)
    // CFieldUnk
    UNK220_1762(UNVERIFIED_OP), // v232 1849
    UNK220_1763(UNVERIFIED_OP), // v232 1850
    UNK220_1764(UNVERIFIED_OP), // v232 1851
    UNK220_1765(UNVERIFIED_OP), // v232 1852
    // CNpcPool::OnNpcPacket      v223.1
    NPC_MOVE(1853), // v271 (v265 1791): NPC router 0x143dabc40 (base 1847, same 28 cases as v265)
    UNK220_1766(UNVERIFIED_OP), // v232 1853
    UNK220_1767(UNVERIFIED_OP), // v232 1854
    NPC_SET_FORCE_MOVE(1857), // v271 (v265 1795): NPC router 0x143dabc40 (base 1847, same 28 cases as v265)
    // CFishingZonePool::OnPacket      v223.1
    FISHING_INFO(UNVERIFIED_OP), // v265-era 1857; v271 1857 is NPC_SET_FORCE_MOVE
    NPC_SET_FORCE_FLIP(1858), // v271 (v265 1796): NPC router 0x143dabc40 (base 1847, same 28 cases as v265)
    FISHING_REWARD(UNVERIFIED_OP), // v265-era 1858; v271 1858 is NPC_SET_FORCE_FLIP
    UNK223_1772(UNVERIFIED_OP), // v232 1858
    FISHING_ZONE_INFO(1859),
    UNK223_1773(UNVERIFIED_OP), // v232 1859
    // CPersonalMapObjectPool::OnPacket      v223.1
    DECOMPOSER_ENTER_FIELD(1860),
    UNK223_1774(UNVERIFIED_OP), // v232 1860
    DECOMPOSER_LEAVE_FIELD(1861),
    NPC_LOCAL_NPC_REPEAT_EFFECT(1866), // v271 (v265 1804): NPC router 0x143d77d80 (+62), reads S as v265
    NPC_SET_SPECIAL_ACTION(1872), // v271 (v265 1810): NPC router 0x143dabc40 (base 1847, same 28 cases as v265)
    //TODO Not updated to NEO yet
    // CDropPool::OnPacket      v223.1
    DROP_ENTER_FIELD(1880), // v271 (v265 1818): handler 0x1433ab9e0 reads as v265 (49 reads)
    DROP_LEAVE_FIELD(1882), // v271 (v265 1820): 0x1433b10f0, as v265 except type 4's delay is an int
    // CField_GuildBoss::OnPacket
    GUILD_BOSS_HEALER_MOVE(UNVERIFIED_OP), // v265 1882; v271 1882 is DROP_LEAVE_FIELD
    GUILD_BOSS_PULLEY_STATE_CHANGE(1883),
    // CMonsterCarnival::OnPacket (2 functions for this)
    MONSTER_CARNIVAL_CARNIVAL_ENTER(1884),
    MONSTER_CARNIVAL_PERSONAL_CP(1885),
    // CAffectedAreaPool::OnPacket      v223.1
    AFFECTED_AREA_CREATED(1886), // v271 (v265 1824): area pool 0x141655ed0 -> 0x141662db0; one int fewer after the element
    MONSTER_CARNIVAL_TEAM_CSCORE(UNVERIFIED_OP), // v265 1886; v271 1886 is AFFECTED_AREA_CREATED
    INSTALLED_AREA_FIRE(1887), // v271 (v265 1825): area pool -> 0x14167a520, as v265
    MONSTER_CARNIVAL_SPELL_COOLTIME(UNVERIFIED_OP), // v265 1887; v271 1887 is INSTALLED_AREA_FIRE
    AFFECTED_AREA_REMOVED(1888), // v271 (v265 1826): area pool -> 0x141664b90, 4 4 4 as v265
    MONSTER_CARNIVAL_RESULT_SUCCESS(UNVERIFIED_OP), // v265 1888; v271 1888 is AFFECTED_AREA_REMOVED
    // CFieldUnk
    UNK212_1782(UNVERIFIED_OP), // v232 1888
    // CTownPortalPool::OnPacket      v223.1
    TOWN_PORTAL_CREATED(1889), // v271 (v265 1827): town portal pool 0x144275910; v223.1 numbering; v271 1827 is in the mob router (MOB_REQUEST_RESULT)
    MONSTER_CARNIVAL_RESULT_FAIL(UNVERIFIED_OP), // v265-era 1889; v271 1889 is TOWN_PORTAL_CREATED
    UNK212_1783(UNVERIFIED_OP), // v232 1889
    TOWN_PORTAL_REMOVED(1890), // v271 (v265 1828): town portal pool 0x144275910 (1889..1890)
    MONSTER_CARNIVAL_DEATH(UNVERIFIED_OP), // v265-era 1890; v271 1890 is TOWN_PORTAL_REMOVED
    UNK212_1784(UNVERIFIED_OP), // v232 1890
    // CRandomPortalPool::OnPacket      v223.1
    RANDOM_PORTAL_CREATED(1891), // v265 1829; v271 router 0x144278b80 (base 0x763)
    MONSTER_CARNIVAL_MEMBER_OUT(UNVERIFIED_OP), // v265 1891; v271 1891 is RANDOM_PORTAL_CREATED
    UNK212_1785(UNVERIFIED_OP), // v232 1891
    RANDOM_PORTAL_TRY_ENTER_REQUEST(1892), // v265 1830
    MONSTER_CARNIVAL_SHOW_GAME_RESULT(UNVERIFIED_OP), // v265 1892; v271 1892 is RANDOM_PORTAL_TRY_ENTER_REQUEST
    // CHiddenCatch
    UNK212_1786(UNVERIFIED_OP), // v232 1892
    RANDOM_PORTAL_REMOVED(1893), // v271 (v265 1831): router 0x144278b80 (base 0x763)
    MONSTER_CARNIVAL_UPDATE_RANK_INFO(UNVERIFIED_OP), // v265 1893; v271 1893 is RANDOM_PORTAL_REMOVED
    UNK212_1787(UNVERIFIED_OP), // v232 1893
    // COpenGatePool::OnPacket      v223.1
    OPEN_GATE_CREATED(1894), // v271 (v265 1832): open-gate pool 0x143e0e760 (1894..1897)
    UNK212_1788(UNVERIFIED_OP), // v232 1894
    OPEN_GATE_CLOSE(1895), // v271 (v265 1833): open-gate pool 0x143e0e760 (1894..1897)
    UNK212_1789(UNVERIFIED_OP), // v232 1895
    UNK212_1790(UNVERIFIED_OP), // v232 1896
    OPEN_GATE_REMOVED(1897), // v271 (v265 1835): open-gate pool 0x143e0e760 (1894..1897)
    // CReactorPool::OnPacket      v223.1
    REACTOR_CHANGE_STATE(1898), // v271 (v265 1836): reactor pool 0x143ed3b50 (+62) idx 0, 4 1 2 2 2 1 4 4 as v265
    REACTOR_ENTER_FIELD(1910), // v271 (v265 1848): reactor pool idx 12, as v265
    REACTOR_RESET_STATE(1911), // v271 (v265 1849): reactor pool idx 13, 4 1 as v265
    REACTOR_OWNER_INFO(1912), // v271 (v265 1850): reactor pool idx 14, 4 4 as v265
    REACTOR_REMOVE(1913), // v271 (v265 1851): reactor pool idx 15, 4 as v265
    REACTOR_LEAVE_FIELD(1918), // v271 (v265 1856): reactor pool idx 20, as v265
    // CFieldUnk
    UNK212_1817(UNVERIFIED_OP), // v232 1924
    // //
    RPS_GAME_DLG(UNVERIFIED_OP), // v232 1929
    GO_STOP_RPS_GAME_DLG(UNVERIFIED_OP), // v232 1930
    UNK_1444(UNVERIFIED_OP), // v232 1933
    SET_CASH_SHOP_INITIAL_ITEM(UNVERIFIED_OP), // v232 1934
    TRY_MIGRATE_CASH_SHOP(UNVERIFIED_OP), // v232 1935
    UNK1447(UNVERIFIED_OP), // v232 1936
    // CFieldUnk
    UNK212_1831(UNVERIFIED_OP), // v232 1937
    UNK212_1832(UNVERIFIED_OP), // v232 1938
    UNK212_1833(UNVERIFIED_OP), // v232 1939
    UNK212_1834(UNVERIFIED_OP), // v232 1940
    UNK212_1835(UNVERIFIED_OP), // v232 1941
    // CFieldUnk
    UNK212_1836(UNVERIFIED_OP), // v232 1942
    UNK212_1837(UNVERIFIED_OP), // v232 1943
    // CGhostPark::OnPacket
    UNK212_1839(UNVERIFIED_OP), // v232 1945
    UNK212_1840(UNVERIFIED_OP), // v232 1946
    UNK212_1841(UNVERIFIED_OP), // v232 1947
    UNK212_1842(UNVERIFIED_OP), // v232 1948
    UNK212_1843(UNVERIFIED_OP), // v232 1949
    UNK212_1844(UNVERIFIED_OP), // v232 1950
    FIELD_RANK_SET_USERNAME(1952),
    FIELD_RANK_NOTIFY_DATA(1953),
    // CField_Piranha::OnPacket
    PIRANHA_INIT(1954),
    // CFieldUnk
    UNK212_1848(UNVERIFIED_OP), // v232 1954
    PIRANHA_INFO(1955),
    UNK212_1849(UNVERIFIED_OP), // v232 1955
    UNK212_1850(UNVERIFIED_OP), // v232 1956
    // CFieldUnk
    UNK212_1851(UNVERIFIED_OP), // v232 1957
    UNK212_1852(UNVERIFIED_OP), // v232 1958
    UNK212_1853(UNVERIFIED_OP), // v232 1959
    UNK212_1854(UNVERIFIED_OP), // v232 1960
    // CField_BonusReward::OnPacket
    BONUS_REWARD_BOARD_FAO(1961),
    UNK212_1855(UNVERIFIED_OP), // v232 1961
    // CHilla::OnPacket (?)
    IDK_BOSS_FIELD_212_1856(UNVERIFIED_OP), // v232 1962
    VERUS_HILLA_FIELD_212_1857(UNVERIFIED_OP), // v232 1963
    // CFieldUnk
    UNK212_1859(UNVERIFIED_OP), // v232 1965
    UNK212_1860(UNVERIFIED_OP), // v232 1966
    UNK212_1861(UNVERIFIED_OP), // v232 1967
    UNK212_1862(UNVERIFIED_OP), // v232 1968
    // CField_BossArena::OnPacket
    BOSS_ARENA_FIELD_STATE_CHANGE(1970),
    // CFieldUnk
    UNK212_1864(UNVERIFIED_OP), // v232 1970
    BOSS_ARENA_CURRENT_TEAM_INFO(1971),
    UNK212_1865(UNVERIFIED_OP), // v232 1971
    BOSS_ARENA_SELECTED_TEAM_INFO(1972),
    UNK212_1866(UNVERIFIED_OP), // v232 1972
    BOSS_ARENA_SELECT_OPPONENT_SELECT_BOSS(1973),
    UNK212_1867(UNVERIFIED_OP), // v232 1973
    BOSS_ARENA_SELECT_OPPONENT_CONFIRM(1974),
    UNK212_1868(UNVERIFIED_OP), // v232 1974
    // CFieldUnk
    UNK212_1869(UNVERIFIED_OP), // v232 1975
    // CFieldUnk
    UNK212_1870(UNVERIFIED_OP), // v232 1976
    UNK212_1871(UNVERIFIED_OP), // v232 1977
    UNK212_1872(UNVERIFIED_OP), // v232 1978
    // CField_BossArena::OnPacket
    BOSS_ARENA_SCORE(1979),
    UNK212_1873(UNVERIFIED_OP), // v232 1979
    BOSS_ARENA_RESULT_INFO(1980),
    UNK212_1874(UNVERIFIED_OP), // v232 1980
    // CFieldUnk
    UNK212_1875(UNVERIFIED_OP), // v232 1981
    // CField_BossArena::OnPacket
    BOSS_ARENA_FADE_IN_OUT(1982),
    BOSS_ARENA_SUMMON_HEAD_GEAR(1983),
    BOSS_ARENA_BOSS_INFO(1984),
    BOSS_ARENA_USER_HP_CHANGED(1985),
    // CGloryPRField::OnPacket (?)
    UNK212_1879(UNVERIFIED_OP), // v232 1985
    UNK212_1880(UNVERIFIED_OP), // v232 1986
    UNK212_1881(UNVERIFIED_OP), // v232 1987
    UNK212_1882(UNVERIFIED_OP), // v232 1988
    UNK212_1883(UNVERIFIED_OP), // v232 1989
    UNK232_1990(UNVERIFIED_OP), // v232 1990
    // CFieldUnk
    UNK212_1890(UNVERIFIED_OP), // v232 1991; *
    // CFieldUnk228
    UNK228_2003(UNVERIFIED_OP), // v232 1992
    UNK228_2004(UNVERIFIED_OP), // v232 1993
    UNK228_2005(UNVERIFIED_OP), // v232 1994
    UNK228_2006(UNVERIFIED_OP), // v232 1995
    UNK228_2007(UNVERIFIED_OP), // v232 1996
    // CFieldUnk228
    UNK228_2008(UNVERIFIED_OP), // v232 1997
    UNK228_2009(UNVERIFIED_OP), // v232 1998
    UNK228_2010(UNVERIFIED_OP), // v232 1999
    UNK228_2011(UNVERIFIED_OP), // v232 2000
    UNK228_2012(UNVERIFIED_OP), // v232 2001
    UNK228_2013(UNVERIFIED_OP), // v232 2002
    UNK228_2014(UNVERIFIED_OP), // v232 2003
    UNK228_2015(UNVERIFIED_OP), // v232 2004
    UNK228_2016(UNVERIFIED_OP), // v232 2005
    // v265 RE: Hungry Muto field 148 (OnPacket 0x1430536a0)
    HUNGRY_MUTO_VALUE(UNVERIFIED_OP), // v265 2006: int (stored at +0x800); GMS v271 doesn't send it
    UNK228_2017(UNVERIFIED_OP), // v232 2006
    UNK228_2018(UNVERIFIED_OP), // v232 2007
    // CFieldUnk228
    UNK228_2019(UNVERIFIED_OP), // v232 2008
    UNK228_2020(UNVERIFIED_OP), // v232 2009
    UNK228_2021(UNVERIFIED_OP), // v232 2010
    UNK228_2022(UNVERIFIED_OP), // v232 2011
    UNK228_2023(UNVERIFIED_OP), // v232 2012
    // CField::OnPacket part 2
    FIELD_ON_RANK_CREATE(2013), // v265 1951
    UNK228_2024(UNVERIFIED_OP), // v232 2013
    // CField
    UNK228_2025(UNVERIFIED_OP), // v232 2014
    // CFieldUnk
    UNK228_2026(UNVERIFIED_OP), // v232 2015
    UNK228_2027(UNVERIFIED_OP), // v232 2016
    UNK228_2028(UNVERIFIED_OP), // v232 2017
    // CFieldUnk (new 218)
    UNK218_1901(UNVERIFIED_OP), // v232 2018
    UNK218_1902(UNVERIFIED_OP), // v232 2019
    //TODO Not updated to NEO yet
    // CField_MomentSwimArea::OnPacket
    MOMENT_SWIM_AREA_SET_WATER_LEVEL(2020),
    UNK218_1903(UNVERIFIED_OP), // v232 2020
    UNK218_1904(UNVERIFIED_OP), // v232 2021
    UNK218_1905(UNVERIFIED_OP), // v232 2022
    UNK218_1906(UNVERIFIED_OP), // v232 2023
    // CFieldUnk (new 220)
    UNK220_1919(UNVERIFIED_OP), // v232 2024
    UNK220_1920(UNVERIFIED_OP), // v232 2025
    // ..
    UNK220_1922(UNVERIFIED_OP), // v232 2026
    // ..
    UNK227_1966(UNVERIFIED_OP), // v232 2027
    // CFieldUnk (Boss/Event map)
    UNK212_1895(UNVERIFIED_OP), // v232 2028
    UNK212_1896(UNVERIFIED_OP), // v232 2029
    UNK212_1897(UNVERIFIED_OP), // v232 2030
    UNK212_1898(UNVERIFIED_OP), // v232 2031
    UNK212_1899(UNVERIFIED_OP), // v232 2032
    UNK212_1900(UNVERIFIED_OP), // v232 2033
    UNK212_1901(UNVERIFIED_OP), // v232 2034
    UNK212_1902(UNVERIFIED_OP), // v232 2035
    UNK212_1903(UNVERIFIED_OP), // v232 2036
    UNK212_1904(UNVERIFIED_OP), // v232 2037
    UNK212_1905(UNVERIFIED_OP), // v232 2038
    UNK224_1955(UNVERIFIED_OP), // v232 2039
    // CField
    UNK228_2051(UNVERIFIED_OP), // v232 2040
    UNK228_2052(UNVERIFIED_OP), // v232 2041
    // CCashShop::OnPacket (v271 0x142058140: 84, 86, 2701, table 2702..2735 = v265 2624..2639 +78, 2641..2658 +77)
    CASH_SHOP_UNK220_1936(UNVERIFIED_OP), // v232 2042; can open some window in chrome
    CASH_SHOP_UNK_208_1848(UNVERIFIED_OP), // v232 2045
    CASH_SHOP_GIFT_RESULT(UNVERIFIED_OP), // v232 2046
    CASH_SHOP_UNK_208_1851(UNVERIFIED_OP), // v232 2048
    CASH_SHOP_UNK_208_1852(UNVERIFIED_OP), // v232 2049
    CASH_SHOP_OUTFIT(UNVERIFIED_OP), // v232 2050
    CASH_SHOP_UNK_208_1854(UNVERIFIED_OP), // v232 2051
    CASH_SHOP_UNK_208_1855(UNVERIFIED_OP), // v232 2052
    CASH_SHOP_UNK_208_1856(UNVERIFIED_OP), // v232 2053
    CASH_SHOP_UNK_208_1857(UNVERIFIED_OP), // v232 2054
    // CField
    FIELD_FADE_OBJECT_INFO(2055), // v265 1993
    CASH_SHOP_UNK_208_1858(UNVERIFIED_OP), // v232 2055
    RUNE_STONE_CLEAR_AND_ALL_REGISTER(2056), // v271 (v265 1994): field table, reads as v265
    CASH_SHOP_UNK_208_1859(UNVERIFIED_OP), // v232 2056
    RUNE_STONE_DISAPPEAR(2057), // v271 (v265 1995): 4 4 4 1 1 as v265
    CASH_SHOP_UNK_227_1996(UNVERIFIED_OP), // v232 2057
    RUNE_STONE_APPEAR(2058), // v271 (v265 1996): 4 4 (4) 4, rune 4 4 1 as v265
    CASH_SHOP_UNK_227_1997(UNVERIFIED_OP), // v232 2058
    CASH_SHOP_UNK_208_1861(UNVERIFIED_OP), // v232 2059
    CASH_SHOP_UNK_208_1867(UNVERIFIED_OP), // v232 2068
    HUNGRY_MUTO_INFO(2070), // v271 (v265 2007), GMS 271.4: int type; 1: int ms; 2: clear; 3: dish, food, phase, int, int, n, n x (item, need, have); 4: dish, food, phase, n, n x (item, need, have); 5: n, n x (chr, item, count)
    // CField_Demian::OnPacket
    DEMIAN_STIGMA_INCINERATE_OBJECT_PACKET(2072), // v271 (v265 2009): Demian field 0x1435ae3b0 (+63), as v265
    DEMIAN_STIGMA_OBJECT_EFFECT(2073), // v271 (v265 2010): Demian field, as v265
    DEMIAN_FLYING_SWORD_CREATE(2074), // v271 (v265 2011): Demian field, as v265
    DEMIAN_FLYING_SWORD_MAKE_ENTER_REQUEST(2075), // v271 (v265 2012): Demian field, as v265
    CASH_SHOP_UNK_213_1931(UNVERIFIED_OP), // v232 2075
    DEMIAN_FLYING_SWORD_MAKE_ENTER_INFO(2076), // v271 (v265 2013): Demian field, as v265
    CASH_SHOP_UNK_216_1957(UNVERIFIED_OP), // v232 2076
    DEMIAN_FLYING_SWORD_NODE(2077), // v271 (v265 2014): Demian field, as v265
    CASH_SHOP_UNK_208_1879(UNVERIFIED_OP), // v232 2077
    DEMIAN_FLYING_SWORD_TARGET(2078), // v271 (v265 2015): Demian field, as v265
    UNK204_1799(UNVERIFIED_OP), // v232 2079
    // CUIHandler::OnPacket
    ATTENDANCE_EVENT_INFO(UNVERIFIED_OP), // v232 2081
    DEMIAN_CORRUPTION_CHANGE(2082), // v271 (v265 2019): Demian field, as v265
    UNK200_1779(UNVERIFIED_OP), // v232 2082
    ARK_FISHING_INFO(UNVERIFIED_OP), // v232 2083
    UNK200_1781(UNVERIFIED_OP), // v232 2084
    UNK200_1782(UNVERIFIED_OP), // v232 2085
    // CField_Lucid::OnPacket
    LUCID_BUTTERFLY_CREATED(2086), // v271 (v265 2023): Lucid fields 0x14361b6a0 / 0x143625160 (+63), as v265
    UNK200_1783(UNVERIFIED_OP), // v232 2086
    LUCID_BUTTERFLY_ACTION(2087), // v271 (v265 2024): Lucid fields, as v265
    TOWER_OF_OZ_TIMER_INFO(UNVERIFIED_OP), // v232 2087
    LUCID_DRAGON_CREATED(2089), // v271 (v265 2026): Lucid fields, as v265; v265 RE (boss-mech): phase 1 field object +0x7f0 -> 0x143018400 (4 4 4 4 4 1)
    // 1 skipped
    CLEAR_RESULT(UNVERIFIED_OP), // v232 2089
    DO_LUCID_SKILL(2090), // v271 (v265 2027): Lucid fields, as v265; v265 RE: Lucid field 0x14301c620 -> 0x14301f910 (int, int, ...)
    UNK200_1786(UNVERIFIED_OP), // v232 2090
    LUCID_PHASE_2_STAINED_GLASS_ON_OFF(2091), // v271 (v265 2028): Lucid phase 2, as v265
    UNK200_1787(UNVERIFIED_OP), // v232 2091
    LUCID_PHASE_2_STAINED_GLASS_BREAK(2092), // v271 (v265 2029): Lucid phase 2, as v265
    UNK200_1788(UNVERIFIED_OP), // v232 2092
    LUCID_STATUE_STATE_CHANGE(2093), // v271 (v265 2030): Lucid fields, as v265
    CHAR_SECURITY_MESSAGE(UNVERIFIED_OP), // v232 2093
    LUCID_PHASE_2_SET_FLYING_MODE(2094), // v271 (v265 2031): Lucid phase 2, as v265; v265 RE: Lucid phase 2 field 0x1430263e0 -> 0x14302acb0 (byte)
    LUCID_PHASE_2_WELCOME_BARRAGE(2095), // v271 (v265 2032): Lucid phase 2 0x14362f3f0, as v265; v265 RE (boss-mech) handler 0x143030500: int type; 0 (4,4), 3 (4x4), 4/5 (8 ints), 6 end -> client sends 1515
    JIGSAW_PUZZLE(UNVERIFIED_OP), // v232 2095
    SOME_GIFT_THING(UNVERIFIED_OP), // v232 2097
    PAPULATUS_CLOCK(2098), // v271 (v265 2035): Papulatus field 0x14367b010 -> 0x14367b8d0, as v265; v265 RE: Papulatus field 0x14307bf50 -> 0x14307c7d0 (byte type, ints)
    PAPULATUS_UNK3(2099), // v271 (v265 2036): -> 0x14367bb10, 4 as v265; v265 RE: Papulatus field -> 0x14307ca10 (int)
    UNK200_1795(UNVERIFIED_OP), // v232 2099
    // CUnionField::OnPacket
    // v265 RE: Legion raid field (fieldType 155, OnPacket 0x14312afc0) handles 2038-2040
    UNION_RAID_DAMAGE(2101), // v271 (v265 2038): union raid field 0x14372ad80 -> 0x14372a710, 8 as v265; v232 1619; 0x14312a930: long damage
    UNION_RAID_COIN(2102), // v271 (v265 2039): -> 0x14372a740, 4 1 as v265
    AUTH_SYMBOL_ENHANCE(UNVERIFIED_OP), // v232 2102
    UNION_RAID_BOSS_UPDATE(2103), // v271 (v265 2040): -> 0x14372a830, 4 8 8 4 8 8 as v265; v232 1621; 0x14312aa50: int mob, long hp, long max, int mob2, long hp2, long max2
    PARTICIPATION_EVENT(UNVERIFIED_OP), // v232 2103
    UNK200_1799(UNVERIFIED_OP), // v232 2104
    STAR_STAMP_CLAIM_NOTE(UNVERIFIED_OP), // v232 2105
    DREAM_DEFENDER_RANKING(UNVERIFIED_OP), // v232 2106
    UNION_RANKING(UNVERIFIED_OP), // v232 2107
    UNK200_1803(UNVERIFIED_OP), // v232 2108
    ONLY_SEALED_RUNE_MSG(UNVERIFIED_OP), // v232 2109
    UNK214_1990(UNVERIFIED_OP), // v232 2110
    UNK200_1806(UNVERIFIED_OP), // v232 2111
    UNK200_1809(UNVERIFIED_OP), // v232 2113
    UNK200_1810(UNVERIFIED_OP), // v232 2114
    ALLIANCE_RANK_UNLOCK_MSG(UNVERIFIED_OP), // v232 2115
    UNK200_1812(UNVERIFIED_OP), // v232 2116
    UNK200_1813(UNVERIFIED_OP), // v232 2117
    // not in this sub
    UNK214_1999(UNVERIFIED_OP), // v232 2118
    UNK214_2000(UNVERIFIED_OP), // v232 2119
    UNK217_2001(UNVERIFIED_OP), // v232 2120
    // back to sub
    REWARD_CLAIMED_FAIL(UNVERIFIED_OP), // v232 2121
    REWARD_CLAIMED_SUCCESS(UNVERIFIED_OP), // v232 2122
    UNK200_1816(UNVERIFIED_OP), // v232 2123
    UNK228_2135(UNVERIFIED_OP), // v232 2124
    OPEN_DAILY_QUEST_BOARD(UNVERIFIED_OP), // v232 2125
    UNK200_1818(UNVERIFIED_OP), // v232 2126
    UNK200_1819(UNVERIFIED_OP), // v232 2127
    UNK200_1820(UNVERIFIED_OP), // v232 2128
    UNK200_1821(UNVERIFIED_OP), // v232 2129
    UNK200_1822(UNVERIFIED_OP), // v232 2130
    UNK200_1823(UNVERIFIED_OP), // v232 2131
    UNK200_1824(UNVERIFIED_OP), // v232 2132
    UNK200_1825(UNVERIFIED_OP), // v232 2133
    UNK200_1826(UNVERIFIED_OP), // v232 2134
    UNK200_1827(UNVERIFIED_OP), // v232 2135
    UNK205_1901(UNVERIFIED_OP), // v232 2136
    UNK205_1902(UNVERIFIED_OP), // v232 2137
    UNK205_1903(UNVERIFIED_OP), // v232 2138
    UNK205_1904(UNVERIFIED_OP), // v232 2139
    UNK223_2051(UNVERIFIED_OP), // v232 2140
    UNK223_2052(UNVERIFIED_OP), // v232 2141
    OZ_BUY_RESULT(UNVERIFIED_OP), // v232 2142
    UNK227_2081(UNVERIFIED_OP), // v232 2143
    // Unk sub
    UNK202_1857(UNVERIFIED_OP), // v232 2154; *
    UNK202_1858(UNVERIFIED_OP), // v232 2155
    UNK202_1859(UNVERIFIED_OP), // v232 2156
    UNK202_1860(UNVERIFIED_OP), // v232 2157
    UNK202_1861(UNVERIFIED_OP), // v232 2158
    UNK202_1862(UNVERIFIED_OP), // v232 2159
    // CField_Will::OnPacket
    WILL_UNK_207_1538(2165), // v271 (v265 2102): Will field case 65 is a plain ret in both; v232 1683; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    // Unk
    UNK220_2054(UNVERIFIED_OP), // v232 2165
    WILL_UNK_207_1539(2166), // v271 (v265 2103): Will field 0x143777680 (+63) case 66, 4 4 as v265; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2055(UNVERIFIED_OP), // v232 2166
    WILL_MOONLIGHT_STATUS(2167), // v271 (v265 2104): Will field case 67, 4 as v265; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2056(UNVERIFIED_OP), // v232 2167
    WILL_MOONLIGHT_COOLDOWN(2168), // v271 (v265 2105): Will field case 68, 4 as v265; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2057(UNVERIFIED_OP), // v232 2168
    WILL_BEHOLDER(2169), // v271 (v265 2106): Will phase fields 0x143784270/0x14378aea0/0x14378dd80 -> 0x1437770c0, as v265 (type 0: 4 4 4; 1: 4 4 4 4 4 1 [B16]); v232 1687; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2058(UNVERIFIED_OP), // v232 2169
    WILL_WEB(2170), // v271 (v265 2107): Will web field 0x14378dd80 -> 0x14378d3c0, as v265; v232 1688; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2059(UNVERIFIED_OP), // v232 2170
    WILL_WEB_HIT(2171), // v271 (v265 2108): 0x14378e120, 1 4 as v265; v232 1689; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2060(UNVERIFIED_OP), // v232 2171
    WILL_INFECTIONS(2172), // v271 (v265 2109): 0x14378ea00, as v265; v232 1690; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2061(UNVERIFIED_OP), // v232 2172
    WILL_INFECTION_CREATE(2173), // v271 (v265 2110): 0x14378e410, as v265; v232 1691; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2062(UNVERIFIED_OP), // v232 2173
    WILL_INFECTION_REMOVE(2174), // v271 (v265 2111): 0x14378e680, as v265; v232 1692; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    WILL_INFECTION_TRANSFORM(2175), // v271 (v265 2112): 0x14378e770, 4 4 4 as v265; v232 1693; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2064(UNVERIFIED_OP), // v232 2175
    WILL_INFECTION_ATTACK(2176), // v271 (v265 2113): 0x14378e210, 4 as v265; v232 1694; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2065(UNVERIFIED_OP), // v232 2176
    WILL_MOONLIGHT_USE_TRANSITION(2177), // v271 (v265 2114): Will field case 77, 4 as v265; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    WILL_P1_TIMER(2178), // v271 only: Will phase 1 field 0x143784270 case 0x882, 4 state 4 ms (GMS: 0 countdown, 1 Will unlocks his power, 2 pupil test, 3 phase over)
    WILL_TRIGGER_BLOCK_P1(2179), // v271 (v265 2115, +64: v271 adds 2178): 0x143786bb0, as v265; v232 1696; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    // ..
    UNK220_2068(UNVERIFIED_OP), // v232 2179
    WILL_TRIGGER_BLOCK_P2(2180), // v271 (v265 2116): Will phase field 0x14378aea0 -> 0x14378b450, as v265; v232 1697; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2069(UNVERIFIED_OP), // v232 2180
    WILL_MOOONLIGHT_SHOW_EFFECT(2181), // v271 (v265 2117): 0x1437874d0 / 0x14378b7c0, no reads as v265; v232 1698; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2070(UNVERIFIED_OP), // v232 2181
    WILL_UNK_207_1554(2182), // v271 (v265 2118): no reads as v265; v232 1699; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2071(UNVERIFIED_OP), // v232 2182
    WILL_UNK_207_1555(2183), // v271 (v265 2119): no reads as v265; v232 1700; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2072(UNVERIFIED_OP), // v232 2183
    // CField_Gloom::OnPacket
    GLOOM_GAUGE_UPDATE(2184), // v271 (v265 2120): Gloom field 0x142d3f700 -> 0x142d3fbc0, 1 4 4 as v265; v232 1701; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    UNK220_2073(UNVERIFIED_OP), // v232 2184
    GLOOM_UNK214_1613(2185), // v271 (v265 2121): 0x142d3fc50, 4 as v265; v232 1702; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    GLOOM_UNK214_1614(2186), // v271 (v265 2122): 0x142d3fe50, 4 as v265; v232 1703; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    // will again
    WILL_USE_SKILL(2187), // v271 (v265 2123): Will field case 87 (v265 86, +64), as v265; v265 RE (boss-mech): Will/Gloom field OnPacket, +419
    // Inheritance
    INHERITANCE_BEGIN(UNVERIFIED_OP), // v232 2214
    INHERITANCE_END(UNVERIFIED_OP), // v232 2218
    SEREN_SUN_PHASE(2225), // v271 Seren phase 2 field 0x142d8add0 -> 0x142d8af00: int phase (1 noon, 2 dusk, 3 midnight, 4 dawn): BGM + effect
    // Seren field (fieldType 270/271, OnPacket 0x1427bd720)
    SEREN_SUN_GAUGE(2226), // v271 (v265 2164): Seren field 0x142d89350 -> 0x142d8a230, 4 4 as v265; 0x1427be2c0: int cur, int max
    SEREN_SKILL_POSITIONS(2228), // v271 (v265 2166): Seren field -> 0x142d89610, 8 x 4 as v265
    SEREN_FIELD_RESET(2229), // v271 (v265 2167): Seren field -> 0x142d898e0, no reads as v265
    UNK_200_1900(UNVERIFIED_OP), // v232 2229
    SEREN_RAY_OF_LIGHT(2231), // v271 Seren phase 2 field -> 0x142d8b010: int key, int ms; the client answers In 1646 when the time is up
    BLACK_MAGE_FIELD_TIMER(2234), // v271 Black Mage fields (14184eb70 -> vt+0x4f8): int type; 2 = int ms to the next big pattern (P1 Shrieking Wall 40000, P2 70000), 1 = phase over, 3 = failed
    // Kalos fields 292/293 (P1 0x142d0ad10, P2 0x142d0ba90)
    GUARDIAN_ANGEL_SLIME_FIELD(2235), // v271 Guardian Angel Slime fields (fieldType 279, 0x142d49510): int type, body (GuardianAngelSlimeFieldPacket)
    UNK_203_1930(UNVERIFIED_OP), // v232 2235
    KALOS_FIELD_RESULT(2236), // v271 P1 0x142d0ad40, P2 0x142d0bf30: int type, int sub, body (KalosFieldPacket)
    FIELD_ATTACK_INFO(UNVERIFIED_OP), // v232 2236
    ARKARIUM_SCREEN_CRACK(2237), // v271 Arkarium field 0x142d00ba0 -> 0x142d00c30: int type, int ms; 1 = warning timer (In 1707 byte 1 when up), 2 = crack timer + warning text (In 1707 byte 2, byte, byte)
    // Kaling field (base OnPacket 0x14245eb00)
    KALING_SEASON_PICK(2238), // v271 Kaling's Four Seasons hub (fieldType 312, 0x142a115a0): int type; 0 init (int count, per member: int charId, 4 pick, int pick-extra, byte ready, 480 packed look, str name, int job; then FileTime deadline; 0x140405760), 1 member (int charId + block; 0x140405160), 2/6 notices 0x3f41/0x3f6d, 3 pick (int charId, 4 pick, int extra), 4 ready (int charId, byte), 5 member left (int charId)
    KALING_FIELD_EVENT(2239), // v271 (v265 2175): Kaling field 0x1429ffbc0 -> 0x1429ffe50, as v265 (59 reads); 0x14245ed90: int subtype 0x3e9.., per-subtype body
    KALING_SPRING_RESULT(2240), // v271 Qiongqi's field 0x142a088a0 -> 0x142a08a20: int type; 0 = int ms to the next disturbance (Torn Spring)
    KALING_ELECTRIC_EVENT(2241), // v271 (v265 2177): Taowu field 0x142a03010 -> 0x142a03280, as v265 (65 reads)
    KALING_HUNDUN_FIELD(2242), // v271 Hundun's field 0x142a08d00 -> 0x142a08ff0: int type; 1 = int field skill; 2 = byte on [8 bar max]; 4 = byte, int mob, str, 2, str, 20 zero (GMS); 5 = int ms; 6 = 8 bar value; 7 = int mob template
    KALING_FIELD_RESULT(2244), // v271 (v265 2180): -> 0x1429ffdc0, as v265; 0x14245ed00: int type; 0 = int willpower, 1 = 3 x (int, byte), 2 = int, 3 = (0x140358830) byte
    // CField::OnPacket  v204.1
    UNK204_1942(UNVERIFIED_OP), // v232 2244
    // CUIEgoEquip  ::OnPacket continued
    SOCKET_CREATE_RESULT(UNVERIFIED_OP), // v232 2246
    // Lotus fields 368-370 (OnPacket 0x142808410; 2248 -> 0x142807600)
    LOTUS_ALERT_GAUGE(2247), // v271: 0x142808e30, 4 type; 0 = create (4 difficulty, 4 phase, 4 mode, 4 gauge, 4 gauge2; difficulty 3: no UI), 1 = 4 gauge, 4 reason (0), 1; 2 = the same for gauge2; 3 = 4 mode (0 off, 1 alert, 2 overload off, 3 overload on; 0x141f38cc0)
    // CEventGroupMan::OnPacket
    INVITATION_INFO(UNVERIFIED_OP), // v232 2247
    INVITATION(UNVERIFIED_OP), // v232 2248
    INVITATION_FAIL(UNVERIFIED_OP), // v232 2249
    // Limbo fields 380-385 (OnPacket 0x1422155f0)
    LIMBO_FIELD_RESULT(2250), // v271 (v265 2186): Limbo field 0x14279dc70 -> 0x14279e360 -> 0x14279e3a0, as v265; 0x142215c50 -> 0x142215c90: int type; 0 = int phase, int erosion left, int boss hp permille; 1 = int phase, int erosion left, byte effect; 2 = int boss hp permille
    INVITATION_ACCEPT(UNVERIFIED_OP), // v232 2250
    EVENT_GROUP_INFO(UNVERIFIED_OP), // v232 2251
    EVENT_GROUP_PACKET(UNVERIFIED_OP), // v232 2252
    // CStarPlanetMatchingMan::OnPacket
    OPEN_UI_STAR_PLANET(UNVERIFIED_OP), // v232 2255
    REGISTER_ACK_STAR_PLANET(UNVERIFIED_OP), // v232 2256
    UNREGISTER_ACK_STAR_PLANET(UNVERIFIED_OP), // v232 2257
    WAIT_ASSENT_STAR_PLANET(UNVERIFIED_OP), // v232 2258
    TRANSFER_STAR_PLANET(UNVERIFIED_OP), // v232 2259
    ERROR_STAR_PLANET(UNVERIFIED_OP), // v232 2260
    // CStarPlanetInvitationMan::OnPacket
    RUN_MATCHING_INVITATION(UNVERIFIED_OP), // v232 2263
    REGISTER_INVITATION_ACK(UNVERIFIED_OP), // v232 2264
    END_INVITATION(UNVERIFIED_OP), // v232 2265
    TYNOS_FLASH(2274), // v271 (v265 2210): field class 0x14284abd0, reads equal; Tynos fields (358/363, OnPacket 0x1422bca70) 0x1422bcc30: no body, 1.5 s screen flash
    TYNOS_MITRA_FLARE(2275), // v271 (v265 2211); 0x1422bcc50: int on; on 1: int x, int y, int l, int t, int r, int b, str sound
    TYNOS_SPINE(2276), // v271 (v265 2212); 0x1422bd090: int delayMs, int id (In 1531 when the delay ends), str spine, int, int n, n x (str ani, int loop)
    TYNOS_PHASE(2277), // v271 (v265 2213); 0x1422bd550: str, Effect/ClientDirection.img/highMountain/tynus/phaseStart/%s
    // CMonsterLife::OnPacket  v204.1
    UNK204_1975(UNVERIFIED_OP), // v232 2277
    UNK204_1976(UNVERIFIED_OP), // v232 2278
    CHAMPION_RAID_FIELD(2279), // v271 (v265 2215): field class 0x14209e4f0 -> 0x14209e6b0, reads as v265; Champion Raid field (fieldType 409, OnPacket 0x141caab90) 0x141caad50: int type 0-11
    UNK204_1977(UNVERIFIED_OP), // v232 2279
    UNK204_1978(UNVERIFIED_OP), // v232 2280
    UNK204_1979(UNVERIFIED_OP), // v232 2281
    UNK204_1980(UNVERIFIED_OP), // v232 2282
    UNK204_1981(UNVERIFIED_OP), // v232 2283
    UNK204_1983(UNVERIFIED_OP), // v232 2285
    UNK204_1984(UNVERIFIED_OP), // v232 2286
    UNK204_1985(UNVERIFIED_OP), // v232 2287
    UNK204_1986(UNVERIFIED_OP), // v232 2288
    UNK204_1987(UNVERIFIED_OP), // v232 2289
    FARM_LEVEL_UP(UNVERIFIED_OP), // v232 2290
    UNK204_1989(UNVERIFIED_OP), // v232 2291
    UNK204_1990(UNVERIFIED_OP), // v232 2292
    UNK204_1991(UNVERIFIED_OP), // v232 2293
    UNK204_1992(UNVERIFIED_OP), // v232 2294
    UNK204_1993(UNVERIFIED_OP), // v232 2295
    UNK204_1994(UNVERIFIED_OP), // v232 2296
    UNK204_1995(UNVERIFIED_OP), // v232 2297
    UNK204_1996(UNVERIFIED_OP), // v232 2298
    UNK204_1997(UNVERIFIED_OP), // v232 2299
    UNK204_1998(UNVERIFIED_OP), // v232 2300
    UNK204_1999(UNVERIFIED_OP), // v232 2301
    UNK204_2000(UNVERIFIED_OP), // v232 2302
    UNK204_2001(UNVERIFIED_OP), // v232 2303
    UNK204_2002(UNVERIFIED_OP), // v232 2304
    UNK204_2003(UNVERIFIED_OP), // v232 2305
    UNK204_2004(UNVERIFIED_OP), // v232 2306
    UNK204_2005(UNVERIFIED_OP), // v232 2307
    UNK204_2006(UNVERIFIED_OP), // v232 2308
    UNK204_2007(UNVERIFIED_OP), // v232 2309
    UNK204_2008(UNVERIFIED_OP), // v232 2310
    UNK204_2009(UNVERIFIED_OP), // v232 2311
    UNK204_2010(UNVERIFIED_OP), // v232 2312
    UNK204_2012(UNVERIFIED_OP), // v232 2314
    UNK204_2014(UNVERIFIED_OP), // v232 2316
    UNK204_2015(UNVERIFIED_OP), // v232 2317
    UNK204_2016(UNVERIFIED_OP), // v232 2318
    UNK204_2017(UNVERIFIED_OP), // v232 2319
    UNK204_2018(UNVERIFIED_OP), // v232 2320
    UNK204_2019(UNVERIFIED_OP), // v232 2321
    // CMonsterUnk::OnPacket  v204.1
    UNK204_2022(UNVERIFIED_OP), // v232 2324
    UNK204_2023(UNVERIFIED_OP), // v232 2325
    UNK204_2024(UNVERIFIED_OP), // v232 2326
    UNK204_2025(UNVERIFIED_OP), // v232 2327
    UNK204_2026(UNVERIFIED_OP), // v232 2328
    UNK204_2027(UNVERIFIED_OP), // v232 2329
    UNK204_2028(UNVERIFIED_OP), // v232 2330
    UNK204_2029(UNVERIFIED_OP), // v232 2331
    UNK204_2030(UNVERIFIED_OP), // v232 2332
    // sub_14BF240  v204.1
    UNK204_2036(UNVERIFIED_OP), // v232 2333
    // CField::OnPacket       v223.1
    UNK204_2039(UNVERIFIED_OP), // v232 2341
    // sub_149B4E0       v223.1
    UNK204_2040(UNVERIFIED_OP), // v232 2342
    UNK204_2041(UNVERIFIED_OP), // v232 2343
    // CField::OnPacket       v223.1
    UNK204_2042(UNVERIFIED_OP), // v232 2344
    // Random
    UPDATE_TEMPORARY_SKILL_BAR(2381), // v271 (v265 2317): CTemporarySkillMan 0x144216500 (2381..2390) -> 0x144218950; skill record 2 bytes more before its sub-record, 1 after; 4 S 4 after the records; v265 RE: CTemporarySkillMan dispatcher 0x143bf5460 (2317..2323) -> 0x143bf7010
    TEMPORARY_SKILL_2318(UNVERIFIED_OP), // v265 2318 (0x143bf7300: 4 4 4); v271 2382 (4 4 4 1) or 2383 (4 4 4)
    TEMPORARY_SKILL_2319(UNVERIFIED_OP), // v265 2319 (0x143bf74a0: 4 4 4); v271 2383 likely
    TEMPORARY_SKILL_2320(2384), // v271 (v265 2320): 0x144219080, 4 1 record 1 (record as UPDATE_TEMPORARY_SKILL_BAR)
    TEMPORARY_SKILL_2321(2385), // v271 (v265 2321): 0x144219380, 4 1 4 4 4 + records
    TEMPORARY_SKILL_2322(UNVERIFIED_OP), // v265 2322 (0x143bf7a20: 4); v271 2386..2390 not paired
    TEMPORARY_SKILL_2323(UNVERIFIED_OP), // v265 2323 (0x143bf7b90: 4); v271 2386..2390 not paired
    // CScriptMan::OnPacket
    SCRIPT_MESSAGE(2436), // v271 (v265 2367): stub 0x143f3ac90; head and type switch read as v265
    // CShopDlg::OnPacket
    SHOP_OPEN(2438), // v271 (v265 2369): field chain 0x986 -> 0x143fbdb30; dialog 0x143fb5a70
    // CMonsterPyramid::OnPacket
    MONSTER_PYRAMID_UPDATE(UNVERIFIED_OP), // v265 2438; v271 2438 is SHOP_OPEN. v232 1969, IDB medium
/* 2439 */    SHOP_RESULT(2439), // v271 (v265 2370): result types >= 4 are one higher
    // CField random
    SHOP_COLLECTOR_INIT(2440), // v265 2371; v271 adds 1, 4 before the period list
    // CGachDlg::OnPacket
    GACH_OPEN(2441), // v271 (v265 2372): 0x1446c98b0 (2441..2442); v265 RE: CField -> 0x14409cba0, open reader 0x1440948f0 (= GachaponDlg.openGach)
    GACH_RESULT(2442), // v271 (v265 2373): 0x1446c98b0; v265 RE: 0x14409cba0 result: 1 r; r 0: 4 item, 2 qty, item; r 2: S
    // CField_Part3
    TRUNK_DLG(2465), // v271 (v265 2396): 0x144286030, types and item lists as v265
    UI_MESSENGER(2468), // v271 (v265 2399): field table, 1 type
    MINI_ROOM_BASE_DLG(2469), // v271 (v265 2400): field table -> 0x143b5cd70; 4 type, types from 16 on +2 (MiniRoomAction)
    VERUS_HILLA_FIELD_212_1858(2487), // v271 (v265 2418): field table, reads as v265
    // v265 RE: Erda Spectrum fields 220 (OnPacket 0x142bc7c30) and 222 (0x142bca530); 221 is a plain field. Names are guesses
    ERDA_SPECTRUM_VALUES(2489), // v271 (v265 2420), GMS: 220: int time ms, int goal (600000, 10) | 222: int time ms (150000)
    ERDA_SPECTRUM_STATE(2490), // v271 (v265 2421), GMS: int 2 running, 3 collector on / cleared, 4 phase end
    ERDA_SPECTRUM_REFRESH(UNVERIFIED_OP), // v265 2422 (no body); v271 2491 carries 20 bytes, not this
    // v265 RE: Esfera Guardian field 282 (OnPacket 0x1427b9580)
    ESFERA_GUARDIAN_INFO(2565), // v271 (v265 2493), GMS: 6 ints (150, 10, 15, 45, 1620, 120), int n, n x mob id
    ESFERA_GUARDIAN_GAUGE(2566), // v271 (v265 2494), GMS: int erda, int gates down
    ESFERA_GUARDIAN_END(2567), // v271 (v265 2495): no body
    // v265 RE: Monster Park Extreme field (fieldType 310, OnPacket 0x142487b80); layouts in ContentPacket
    MONSTER_PARK_EXTREME_START(2582), // v271 (v265 2510): field class 0x142a27570, reads equal; 0x142487f20: int timer s, int hold ms, int x, int y, 4 ints key area (Etc/monsterParkExtreme.img/altar)
    MONSTER_PARK_EXTREME_RESET(2583), // v271 (v265 2511); 0x1424880c0: no body; clears the hunt list
    MONSTER_PARK_EXTREME_HUNT(2584), // v271 (v265 2512); 0x142488110: int mob id, int killed, int needed, int (one hunt row, upserted by mob)
    MONSTER_PARK_EXTREME_BOSS(2585), // v271 (v265 2513); 0x142488240: int, int, FT leaves at, int, int hp 1/1000, int ms on the map
    MONSTER_PARK_EXTREME_ALTAR(2586), // v271 (v265 2514); 0x142488280: int value, int state 0 disabled, 1 enabled, 2 activating, 3 activated
    // v265 RE: Midnight Chaser field (fieldType 318, OnPacket 0x1423dd520, Etc/midnightChaser.img); names are guesses
    MIDNIGHT_CHASER_ROOM(2594), // v271 (v265 2522): field class 0x142972030, reads equal; 0x1423de1f0: byte room (1-based), byte value
    MIDNIGHT_CHASER_OBJECTS(2595), // v271 (v265 2523); 0x1423de170: byte n, n x (byte area, byte object) -> "%s/area/%d/pos:object/%d"
    MIDNIGHT_CHASER_STATE(2596), // v271 (v265 2524); 0x1423de3a0: int 1 start, 2 clear, 3 fail
    MIDNIGHT_CHASER_STEP_RESULT(2597), // v271 (v265 2525); 0x1423de440: byte step, byte result (SID_MIDNIGHT_CHASTER_STEP_SUCCESS/FAIL_%d, "perfect")
    // pattern-system boss fields, Baldrix 411-417 (OnPacket 0x1417a63d0)
    BOSS_PATTERN_FIELD_RESULT(2666), // v271 (v265 2593): pattern field 0x141b6d940 -> 0x141b6e5d0 -> 0x141b6e7a0 (= v265 0x1417a70a0, 39 reads); 0x1417a7060: int type; 7 = byte effect, int value for the 4-digit deathCountNumber (0x1417a70a0)
    DISGUISE(2678),
    // CField
    PARCEL_DLG(2700), // v271 (v265 2622): field table, reads as v265; v265 RE: CField stub 0x142e7115a -> parcel OnPacket 0x143818b10
    CASH_SHOP_QUERY_CASH_RESULT(2702), // v271 (v265 2624): 4 4 4
    CASH_SHOP_CASH_ITEM_RESULT(2703), // v271 (v265 2625): sub-types 2..106 as v265; cash item record quantity 4 bytes
    CASH_SHOP_CHAR_STAT_CHANGED(2706), // v271 (v265 2628): table position (unused)
    CASH_SHOP_REQUEST_LOCK_RESULT(2711), // v271 CCashShop table 0x142058140 case 0xa97: clears the window's request lock, then byte 1-3 drive a sub window
    CASH_SHOP_SURPRISE_ITEM_RESULT(2724), // v271: CCashShop table 0x142058140 -> 0x14203f340, 1 type: 0xb8 open done, 0xb7 / 0xb9 failed, 0xba multi; v232 2066
    AUCTION_MESSAGE(2736), // v271 (v265 2659): auction stage 0x1427b1d70 -> 0x142b03730 (1 type, 1 code, as v265)
    // CAuctionMan::OnPacket
    AUCTION_RESULT(2737), // v271 (v265 2660): auction stage 0x1427b1d70 -> 0x142b03800; types as v265 up to 56, 57/58 gone
/* 2753 */    DAILY_GIFT_INIT(2753), // v265 2676; v271 UI router 0x1444f5800 (base 2741); calendar ends with one more list
    DIMENSIONAL_MIRROR(2757), // v271 (v265 2680): UI router 0x1444f5800 -> 0x1444f6cf0, reads as v265
    // contents dispatcher 0x143ebfcb0 (2664-2739)
    UNION_RESULT(2759), // v271 (v265 2682): UI table 0x1444f5800 (base 2741) -> 0x1444f6d80 // 0x143ec10d0: int unclaimed coins, byte open artifact tab, union block 0x1411ffc00; opens the Legion window
    UNION_REQUEST_FAIL(2760), // v271 (v265 2683): -> 0x1444f7010 -> 0x144f0dc20 (2) // 0x144911970: short 1/2 "while using", 3 "incapacitated", 4 "in the middle of a task"
    UNION_UNCLAIMED_COIN(2761), // v271 (v265 2684): -> 0x1444f7060 (reads only while the Legion window exists) // 0x143ec13b0: int unclaimed coins (open Legion window)
    ARCANE_SYMBOL_ENHANCE(2762), // v271 (v265 2685): -> 0x1444f72f0 by table position; unused
    SACRED_SYMBOL_ENHANCE_RESULT(2763), // v271 0x1444f7740: Sacred Symbol enhance failure and max-level messages; next to ARCANE_SYMBOL_ENHANCE
    UNION_COIN(2774), // v271 (v265 2697): -> 0x1444fad20 (4 4) // 0x143ec46e0: int seconds until the unclaimed coins are full (<0 clears), int cap; popup when QR 18098 coin == cap
    GOLD_HAMMER_ITEM_UPGRADE_REQUEST(2776),
    EQUIP_POLISH_RESULT(2787), // v271 0x1421e2880: polish result messages (untradable only, seal/lock, level, mesos, materials)
    EPIC_DUNGEON_UI_OPEN(2792), // v271 (v265 2713): UI router 0x1444f5800 -> 0x142c41640, reads equal; 0x1426833c0: int region, int n, n x (int region, int, int nxRecordKey, str record)
    MONSTER_PARK_RESULT(2794), // v271 (v265 2715): 0x1426cbfb0; window kind 1 reads 6 ints (v265 5), see ContentPacket; v265 RE 0x143ebfcb0 -> 0x142192810: int kind; 1: int window (1 MP, 2 MP Extreme), [1: int, 5 ints, int n, n x (int, int, str, str, int, int, int)]; 2: int result, str message (Spiegelmann dialog unless 1)
    GUILD_ATTEND_MAP_INFO(2805), // v271 only: router 0x1444f5800 -> 0x14086ab70: 4 n {4 map}, 4 m {4 lo, 4 hi}, 4 version
    FIELD_ATTACK_REMOVE_LIST(2820),
    FIELD_ATTACK_REMOVE_ALL(2821),
    FIELD_ATTACK_RESULT_BOARD(2825),
    FIELD_ATTACK_RESULT_GET_OFF(2826),
    FIELD_ATTACK_PUSH_ACT(2827),
    AGGRO_MONITORING_INFO(2836),
    SKILL_DAMAGE_LOG(2837),
    // CUIEgoEquip::OnPacket
    GOLD_HAMMER_ITEM_UPGRADE_RESULT(2868), // v271 (v265 2775): 0x1434db6b0 (2855..2868 = v265 2762..2775)
    ALLOW_LOGIN(2873),
    // CFuncKeyMappedMan::OnPacket (3 below CStage::OnPacket)
    FUNC_KEY_MAPPED_MAN_INIT(2877), // v271 (v265 2784): stub 0x143812ea0; 4 key presets now
    PET_CONSUME_HP_INIT(2879), // v271 (v265 2786): stub 0x143812ea0 case 0xb3f, int item
    PET_CONSUME_MP_INIT(2882), // v271 (v265 2787): case 0xb42, int item (0xb40/0xb41 are new)
    PET_CONSUME_AC_INIT(2883), // v271 (v265 2788): case 0xb43, int item
    CREATE_NEW_CHARACTER_CHECKER_RESULT(2884), // v271 CLogin case 0xb44 -> 0x1439cccf0 (v265 2789): 1 0, then the 38-race job list
    // ZeroWeaponGrade
    EGO_EQUIP_GAUGE_COMPLETE_RETURN(2897), // v271 (v265 2802): 0x14461b4b0 (cmp 0xb51), empty; v265 RE CUIEgoEquip::OnPacket 0x143fe4280 (2801..2806 via 0x142ead830): empty
    EGO_EQUIP_CREATE_UPGRADE_ITEM_COST_RESULT(2898), // v271 (v265 2803): 0x14461b4b0 (cmp 0xb51), 4 4 4 1 1 1; v265 RE: 4 type, 4 meso, 4 wp, 1 ask, 1, 1 sealed
    EGO_EQUIP_CHECK_UPDATE_ITEM_RESULT(2899), // v271 (v265 2804): 0x14461b4b0 (cmp 0xb51), 1 S 4; v265 RE: 1 ok, S error, 4 slot
    EGO_EQUIP_COMPLETE(2900), // v271 (v265 2805): 0x14461b4b0 (cmp 0xb51); v271 2805 is GUILD_ATTEND_MAP_INFO; v265 RE: 1 success, 1 effect (2794 is a CField sub-router, not this)
    INHERITANCE_INFO(2903), // v271 (v265 2808): 0x14474fc00 (cmp 0xb57); 1, 7 x 4, 1, 4 x 4 (v265 6 x 4 at the end); v265 RE CUIInheritance 0x14412a980 (2807..2811 via 0x142ead870): 1 open, 7 ints, 1 a [if a: 6 ints]
    INHERITANCE_COMPLETE(2904), // v271 (v265 2809): 0x14474fc00, 1; v265 RE: 1 max rank notice, closes the window
    INHERITANCE_FAILED(2905), // v271 (v265 2810): 0x14474fc00, empty; v265 RE: empty ("Try again later."), closes the window
    // v265 RE: Spirit Savior field (fieldType 168) handles 2816 in its own vfunc (slot 15, 0x143106340)
    SPIRIT_SAVIOR_SAVING_COUNT(2911), // v271 (v265 2816): 0x143704c30 cmp 0xb5f, 4; int -> UI/UIWindow6.img/spiritSavior/savingCount/%d
    // CFieldAttackObjPool::OnPacket        v223.1
    FIELD_ATTACK_CREATE(2913), // v271 (v265 2818): 0x143558de0 (2913..2924) -> 0x14355a080, 4 4 4 4 1 4 4 1
    FIELD_ATTACK_REMOVE_BY_KEY(2914), // v271 (v265 2819): 0x14355a750, 4
    FIELD_ATTACK_SET_ATTACK(2919), // v271 (v265 2824): same router, 4 obj, 4 attack
    // CBattleRecordMan::OnPacket       v223.1
    DOT_DAMAGE_INFO(2926), // v271 (v265 2831): 0x141f06c30 (2925..2934), reads as v265
    KILL_DAMAGE_INFO(2928), // v271 (v265 2833): 0x141f06c30 (2925..2934), reads as v265
    BATTLE_DAMAGE_INFO(2929), // v271 (v265 2834): 0x141f06c30 (2925..2934), reads as v265
/* 2930 */    SERVER_ON_CALC_REQUEST_RESULT(2930), // v271 (v265 2835): 0x141f06c30 (2925..2934), reads as v265
    MOBS_DEFEATED_INC(2933), // v271 0x141f06c30 case 2933 -> 0x141f0b160: no payload, while recording +1 Enemies Defeated (inc [+0x98]) (v232 2244)
    REQUEST_STATUS_CHECK(2936), // v271 (v265 2841): answer to In REQUEST_STATUS_CHECK: int id, byte (matches GMS)
    CHANNEL_ENTER_FLAG(2958), // v271 protected socket code (cmp at 0x141968d03); GMS sends 01 on channel entry before SET_FIELD, the client answers In 1966 01
    QUICK_LOGIN(2965), // v271 handler 0x1439b9ba0 (v265 2874)
    // CField_GiantBoss::OnPacket       v223.1
    GOLLUX_MINI_MAP(2975), // v271 (v265 2884): GiantBoss field 1435d1370, 4 + n x (S S)
    GOLLUX_PORTAL_OPEN(2976), // v271 (v265 2885): GiantBoss field 1435d1370, S 4
    NO(9999),
    ;

    private static List<OutHeader> spam = Arrays.asList(
            SET_PHYSICAL_WORLD_ID,// 17
            ISSUE_RELOGIN_COOKIE, // the cookie stays out of the log
            SET_MAP_TAGGED_OBJECT_VISIBLE_LOGIN,// 130
            ALIVE_REQ,
            PING_CHECK_RESULT,
            PRIVATE_SERVER_PACKET,
            MOB_CTRL_ACK,
            CHAT_MSG,
            MOB_HP_INDICATOR,
            STAT_CHANGED,
            MOB_CHANGE_CONTROLLER,
            MOB_MOVE,
            FAMILIAR_MOVE,
            REMOTE_MOVE,
            REMOTE_EMOTION,
            EXCL_REQUEST,
            NPC_MOVE,
            DROP_LEAVE_FIELD,
            TEMPORARY_STAT_RESET,
            PET_MOVE,
            NPC_ENTER_FIELD,
            NPC_CHANGE_CONTROLLER,
            MOB_ENTER_FIELD,
            RESULT_INSTANCE_TABLE,
            TEMPORARY_STAT_SET,
            MOB_STAT_SET,
            MOB_STAT_RESET,
            MOB_SKILL_DELAY,
            CASH_SHOP_PREVIEW_INFO,   // 403
            MOB_FORCE_CHASE,
            ANDROID_MOVE,
            CREATE_OBSTACLE,
            REMOTE_SET_TEMPORARY_STAT,
            QUEST_RESULT,
            SUMMONED_MOVE,
            USER_ACTOR_REPLICATION, //                      1063
            EGO_EQUIP_CREATE_UPGRADE_ITEM_COST_RESULT,
            MOB_NEXT_TARGET_FROM_SVR,                       //1816
            WILL_MOONLIGHT_STATUS,
            SHOP_RESULT,//  2439
            // per-frame traffic: messages, inventory, mob death/control, damage numbers, cooldowns, ground drops
            MESSAGE,
            INVENTORY_OPERATION,
            MOB_LEAVE_FIELD,
            MOB_LAST_HIT_INFO,
            MOB_DROP_MESO_PICKUP,
            DROP_ENTER_FIELD,
            SKILL_COOLTIME_SET,
            MOB_PATTERN_SYSTEM_HOTFIX,// 679
            MOB_BIND_REGISTER, //               1757
            DAILY_GIFT_INIT,//                  2753
            SERVER_ON_CALC_REQUEST_RESULT,//         2930
            REMOTE_EFFECT
    );

    private static final Map<Short, OutHeader> opMap = new HashMap<>();

    static {
        for (var header : values()) {
            if (header.value != UNVERIFIED_OP) {
                opMap.put(header.getValue(), header);
            }
        }
    }

    private short value;

    OutHeader(int value) {
        this.value = (short) value;
    }

    public short getValue() {
        return value;
    }

    public static OutHeader getOutHeaderByOp(int op) {
        var val = (short) op;
        return opMap.getOrDefault(val, null);
    }

    public static boolean isSpamHeader(OutHeader outHeader) {
        return spam.contains(outHeader);
    }

    public static void main(String[] args) {
        File file = new File("D:\\Swordie\\Swordie\\src\\main\\java\\net\\swordie\\ms\\handlers\\header\\OutHeader.java");
        int change = -2;
        boolean check = false;
        OutHeader checkOp = null;
        try (Scanner s = new Scanner(file)) {
            while (s.hasNextLine()) {
                String line = s.nextLine();
                if (line.contains(",") && line.contains("(")) {
                    String[] split = line.split("[()]");
                    String name = split[0];
                    if (!Util.isNumber(split[1])) {
                        org.apache.logging.log4j.LogManager.getLogger(OutHeader.class).info(line);
                        continue;
                    }
                    int val = Integer.parseInt(split[1]);
                    OutHeader oh = Arrays.stream(OutHeader.values()).filter(o -> o.toString().equals(name.trim())).findFirst().orElse(null);
                    if (oh != null) {
                        OutHeader start = UNK202_1857;
                        if (oh.ordinal() >= start.ordinal() && oh.ordinal() < NO.ordinal()) {
                            if (line.contains("*")) {
                                check = true;
                                checkOp = oh;
                            }
                            val += change;
                            org.apache.logging.log4j.LogManager.getLogger(OutHeader.class).info(String.format("%s(%d), %s%n", name, val, start == oh ? "// *" : ""));
                        } else {
                            org.apache.logging.log4j.LogManager.getLogger(OutHeader.class).info(line);
                        }
                    }
                } else {
                    org.apache.logging.log4j.LogManager.getLogger(OutHeader.class).info(line);
                }
            }
        } catch (FileNotFoundException e) {
            org.apache.logging.log4j.LogManager.getLogger(OutHeader.class).error("OutHeader.main", e);
        }
        if (check) {
            org.apache.logging.log4j.LogManager.getLogger(OutHeader.class).error(String.format("Current op (%s) contains a * (= updated). Be sure to check for overlap.", checkOp));
        }
    }
}
