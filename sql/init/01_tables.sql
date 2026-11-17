-- Every table the server uses, empty. Run the files in this folder in order.

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS
    `accountdailyentries`,
    `accounts`,
    `alliance_gradenames`,
    `alliances`,
    `auction_items`,
    `avatardata`,
    `avatarlook`,
    `bbs_records`,
    `bbs_replies`,
    `bbs_upvotes`,
    `blacklist`,
    `boss_first_clears`,
    `bosscooldowns`,
    `cashiteminfos`,
    `charactercards`,
    `characterpotentials`,
    `characters`,
    `characterstats`,
    `chosenskills`,
    `collectedfamiliars`,
    `contentcooldowns`,
    `cs_categories`,
    `cs_items`,
    `damageskinsavedatas`,
    `emoticons`,
    `emoticonshortcuts`,
    `equip_drops`,
    `extendsp`,
    `familiarcodexmanager`,
    `familiars`,
    `first_enter_rewards`,
    `friends`,
    `funckeymap`,
    `gradenames`,
    `guildmembers`,
    `guildrequestors`,
    `guilds`,
    `guildskill`,
    `hairequips`,
    `hyperrockfields`,
    `hyperstatinfos`,
    `hyperstatsmanager`,
    `ignoreditems`,
    `inventories`,
    `items`,
    `keymaps`,
    `linkskills`,
    `macros`,
    `macroskills`,
    `matrix_records`,
    `matrix_slots`,
    `migration_history`,
    `minigamerecords`,
    `mob_drops`,
    `monster_collection`,
    `monster_collection_explorations`,
    `monster_collection_group_rewards`,
    `monster_collection_mobs`,
    `monster_collection_rewards`,
    `monster_collection_session_rewards`,
    `monster_collections`,
    `monsterbookcards`,
    `monsterbookinfos`,
    `new_player_progress`,
    `noncombatstatdaylimit`,
    `npc`,
    `offense_managers`,
    `offenses`,
    `petids`,
    `questmanagers`,
    `questprogressrequirements`,
    `quests`,
    `rankingresults`,
    `removednpcs`,
    `server_rates`,
    `shopitems`,
    `skillcooltimes`,
    `skills`,
    `soulcollection`,
    `spset`,
    `stolenskills`,
    `systemtimes`,
    `totems`,
    `trade_transaction`,
    `trunks`,
    `unionboards`,
    `unionmembers`,
    `unionraid`,
    `unions`,
    `unseenequips`,
    `users`,
    `vessel`,
    `zeroinfo`;

CREATE TABLE `accountdailyentries` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accId` int DEFAULT NULL,
  `monsterparkentries` int DEFAULT '-1',
  `evolabentries` int DEFAULT '-1',
  `dojoentries` int DEFAULT '-1',
  `pqentries` int DEFAULT '-1',
  `whipthewhelpsquest` int DEFAULT '-1',
  `defeatgoldenwyvernquest` int DEFAULT '-1',
  `powercrystal` int DEFAULT '-1',
  `vanishingjourneyentries` int DEFAULT '-1',
  `chuchuentries` int DEFAULT '-1',
  `lacheleinentries` int DEFAULT '-1',
  `arcanaentries` int DEFAULT '-1',
  `morassentries` int DEFAULT '-1',
  `esferaentries` int DEFAULT '-1',
  `cerniumentries` int DEFAULT '-1',
  `hotelarcusentries` int DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `idx_ade_accId` (`accId`),
  CONSTRAINT `accountdailyentries_ibfk_1` FOREIGN KEY (`accId`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `worldid` int DEFAULT NULL,
  `userid` int DEFAULT NULL,
  `trunkid` int DEFAULT NULL,
  `nxCredit` int DEFAULT '0',
  `nxPrepaid` int DEFAULT '0',
  `monstercollectionid` int DEFAULT NULL,
  `unionid` int DEFAULT NULL,
  `unionraidid` int DEFAULT NULL,
  `completedAccountQuests` varchar(1000) DEFAULT NULL,
  `powercrystals` int DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `userid` (`userid`),
  KEY `trunkid` (`trunkid`),
  KEY `monstercollectionid` (`monstercollectionid`),
  KEY `idx_acc_worldId` (`worldid`),
  CONSTRAINT `accounts_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `accounts_ibfk_2` FOREIGN KEY (`trunkid`) REFERENCES `trunks` (`id`),
  CONSTRAINT `accounts_ibfk_3` FOREIGN KEY (`monstercollectionid`) REFERENCES `monster_collections` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `alliance_gradenames` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gradename` varchar(255) DEFAULT NULL,
  `allianceid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `allianceid` (`allianceid`),
  CONSTRAINT `alliance_gradenames_ibfk_1` FOREIGN KEY (`allianceid`) REFERENCES `alliances` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `alliances` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `maxmembernum` int DEFAULT NULL,
  `notice` varchar(255) DEFAULT NULL,
  `gradenames` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `auction_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT NULL,
  `accountid` int DEFAULT NULL,
  `charid` int DEFAULT NULL,
  `state` int DEFAULT NULL,
  `itemType` int DEFAULT NULL,
  `charName` varchar(255) DEFAULT NULL,
  `price` bigint DEFAULT NULL,
  `secondprice` bigint DEFAULT NULL,
  `directprice` bigint DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `biduserid` int DEFAULT NULL,
  `bidusername` varchar(255) DEFAULT NULL,
  `idk` int DEFAULT NULL,
  `bidworld` int DEFAULT NULL,
  `oid` int DEFAULT NULL,
  `regdate` datetime DEFAULT NULL,
  `deposit` bigint DEFAULT NULL,
  `sstype` int DEFAULT NULL,
  `idk2` int DEFAULT NULL,
  `idk3` int DEFAULT NULL,
  `unkdate` datetime DEFAULT NULL,
  `item` bigint DEFAULT NULL,
  `itemname` varchar(255) DEFAULT NULL,
  `soldquantity` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `accountid` (`accountid`),
  KEY `item` (`item`),
  KEY `idx_auction_items_endDate` (`endDate`),
  CONSTRAINT `auction_items_ibfk_1` FOREIGN KEY (`accountid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `auction_items_ibfk_2` FOREIGN KEY (`item`) REFERENCES `items` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `avatardata` (
  `id` int NOT NULL AUTO_INCREMENT,
  `characterstat` int DEFAULT NULL,
  `avatarlook` int DEFAULT NULL,
  `zeroavatarlook` int DEFAULT NULL,
  `zeroinfo` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `characterstat` (`characterstat`),
  KEY `avatarlook` (`avatarlook`),
  KEY `zeroinfo` (`zeroinfo`),
  CONSTRAINT `avatardata_ibfk_1` FOREIGN KEY (`characterstat`) REFERENCES `characterstats` (`id`),
  CONSTRAINT `avatardata_ibfk_2` FOREIGN KEY (`avatarlook`) REFERENCES `avatarlook` (`id`),
  CONSTRAINT `avatardata_ibfk_3` FOREIGN KEY (`zeroinfo`) REFERENCES `zeroinfo` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `avatarlook` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gender` int DEFAULT NULL,
  `skin` int DEFAULT NULL,
  `face` int DEFAULT NULL,
  `hair` int DEFAULT NULL,
  `weaponstickerid` int DEFAULT NULL,
  `weaponid` int DEFAULT NULL,
  `subweaponid` int DEFAULT NULL,
  `job` int DEFAULT NULL,
  `drawelfear` tinyint(1) DEFAULT NULL,
  `demonslayerdeffaceacc` int DEFAULT NULL,
  `xenondeffaceacc` int DEFAULT NULL,
  `beasttamerdeffaceacc` int DEFAULT NULL,
  `iszerobetalook` tinyint(1) DEFAULT NULL,
  `mixedhaircolor` int DEFAULT NULL,
  `mixhairpercent` int DEFAULT NULL,
  `ears` int DEFAULT NULL,
  `tail` int DEFAULT NULL,
  `totems` varchar(255) DEFAULT NULL,
  `petids` varchar(255) DEFAULT NULL,
  `hairequips` text,
  `unseenequips` text,
  `earstyle` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `bbs_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idforbbs` int DEFAULT NULL,
  `creatorid` int DEFAULT NULL,
  `msg` text,
  `creationdate` datetime DEFAULT NULL,
  `guildid` int DEFAULT NULL,
  `isAnnouncement` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `bbs_replies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idforreply` int DEFAULT NULL,
  `creatorid` int DEFAULT NULL,
  `creationdate` datetime DEFAULT NULL,
  `msg` text,
  `recordid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `recordid` (`recordid`),
  CONSTRAINT `bbs_replies_ibfk_1` FOREIGN KEY (`recordid`) REFERENCES `bbs_records` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `bbs_upvotes` (
  `recordId` int NOT NULL,
  `upvoteId` int NOT NULL,
  PRIMARY KEY (`recordId`,`upvoteId`),
  CONSTRAINT `bbs_upvotes_ibfk_1` FOREIGN KEY (`recordId`) REFERENCES `bbs_records` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `blacklist` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountid` int DEFAULT NULL,
  `tab` int DEFAULT NULL,
  `targetname` varchar(45) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `charid` int DEFAULT NULL,
  `guildid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `accountid` (`accountid`),
  CONSTRAINT `blacklist_ibfk_1` FOREIGN KEY (`accountid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `boss_first_clears` (
  `charid` int NOT NULL,
  `boss` varchar(48) NOT NULL,
  `cleared_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`charid`,`boss`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `bosscooldowns` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accid` int DEFAULT NULL,
  `boss` varchar(255) DEFAULT NULL,
  `nextentrytime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `accid` (`accid`),
  CONSTRAINT `bosscooldowns_ibfk_1` FOREIGN KEY (`accid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `cashiteminfos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accountid` int DEFAULT NULL,
  `characterid` int DEFAULT NULL,
  `commodityid` int DEFAULT NULL,
  `buycharacterid` varchar(255) DEFAULT NULL,
  `paybackrate` int DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `orderno` int DEFAULT NULL,
  `productno` int DEFAULT NULL,
  `refundable` tinyint(1) DEFAULT NULL,
  `sourceflag` tinyint DEFAULT NULL,
  `storebank` tinyint(1) DEFAULT NULL,
  `itemid` bigint DEFAULT NULL,
  `trunkid` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `trunkid` (`trunkid`),
  KEY `itemid` (`itemid`),
  CONSTRAINT `cashiteminfos_ibfk_1` FOREIGN KEY (`trunkid`) REFERENCES `trunks` (`id`) ON DELETE CASCADE,
  CONSTRAINT `cashiteminfos_ibfk_2` FOREIGN KEY (`itemid`) REFERENCES `items` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `charactercards` (
  `id` int NOT NULL AUTO_INCREMENT,
  `characterid` int DEFAULT NULL,
  `job` int DEFAULT NULL,
  `level` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `characterpotentials` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `potkey` tinyint DEFAULT NULL,
  `skillid` int DEFAULT NULL,
  `slv` int DEFAULT NULL,
  `grade` tinyint DEFAULT NULL,
  `charid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `characterpotentials_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `characters` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accid` int DEFAULT NULL,
  `userid` int DEFAULT NULL,
  `avatardata` int DEFAULT NULL,
  `equippedinventory` int DEFAULT NULL,
  `equipinventory` int DEFAULT NULL,
  `consumeinventory` int DEFAULT NULL,
  `etcinventory` int DEFAULT NULL,
  `installinventory` int DEFAULT NULL,
  `cashinventory` int DEFAULT NULL,
  `decinventory` int DEFAULT NULL,
  `funckeymap_id` int DEFAULT NULL,
  `fieldid` int DEFAULT NULL,
  `questmanager` bigint DEFAULT NULL,
  `guild` int DEFAULT NULL,
  `familiarcodexmanager` bigint DEFAULT NULL,
  `rewardPoints` int DEFAULT NULL,
  `monsterbook` int DEFAULT NULL,
  `partyid` int DEFAULT NULL,
  `previousFieldID` bigint DEFAULT NULL,
  `quickslotKeys` varchar(255) DEFAULT NULL,
  `petfunckeymap` varchar(255) DEFAULT NULL,
  `towerChairs` varchar(255) DEFAULT NULL,
  `hyperrockfields` varchar(255) DEFAULT NULL,
  `location` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `avatardata` (`avatardata`),
  KEY `equippedinventory` (`equippedinventory`),
  KEY `equipinventory` (`equipinventory`),
  KEY `consumeinventory` (`consumeinventory`),
  KEY `etcinventory` (`etcinventory`),
  KEY `installinventory` (`installinventory`),
  KEY `cashinventory` (`cashinventory`),
  KEY `decinventory` (`decinventory`),
  KEY `funckeymap_id` (`funckeymap_id`),
  KEY `questmanager` (`questmanager`),
  KEY `monsterbook` (`monsterbook`),
  KEY `idx_chr_accId` (`accid`),
  KEY `idx_chr_fcm` (`familiarcodexmanager`),
  CONSTRAINT `characters_ibfk_1` FOREIGN KEY (`accid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE,
  CONSTRAINT `characters_ibfk_10` FOREIGN KEY (`funckeymap_id`) REFERENCES `funckeymap` (`id`),
  CONSTRAINT `characters_ibfk_11` FOREIGN KEY (`questmanager`) REFERENCES `questmanagers` (`id`),
  CONSTRAINT `characters_ibfk_12` FOREIGN KEY (`monsterbook`) REFERENCES `monsterbookinfos` (`id`),
  CONSTRAINT `characters_ibfk_13` FOREIGN KEY (`familiarcodexmanager`) REFERENCES `familiarcodexmanager` (`id`),
  CONSTRAINT `characters_ibfk_2` FOREIGN KEY (`avatardata`) REFERENCES `avatardata` (`id`),
  CONSTRAINT `characters_ibfk_3` FOREIGN KEY (`equippedinventory`) REFERENCES `inventories` (`id`),
  CONSTRAINT `characters_ibfk_4` FOREIGN KEY (`equipinventory`) REFERENCES `inventories` (`id`),
  CONSTRAINT `characters_ibfk_5` FOREIGN KEY (`consumeinventory`) REFERENCES `inventories` (`id`),
  CONSTRAINT `characters_ibfk_6` FOREIGN KEY (`etcinventory`) REFERENCES `inventories` (`id`),
  CONSTRAINT `characters_ibfk_7` FOREIGN KEY (`installinventory`) REFERENCES `inventories` (`id`),
  CONSTRAINT `characters_ibfk_8` FOREIGN KEY (`cashinventory`) REFERENCES `inventories` (`id`),
  CONSTRAINT `characters_ibfk_9` FOREIGN KEY (`decinventory`) REFERENCES `inventories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `characterstats` (
  `id` int NOT NULL AUTO_INCREMENT,
  `characterid` int DEFAULT NULL,
  `characteridforlog` int DEFAULT NULL,
  `worldidforlog` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gender` int DEFAULT NULL,
  `skin` int DEFAULT NULL,
  `face` int DEFAULT NULL,
  `hair` int DEFAULT NULL,
  `mixbasehaircolor` int DEFAULT NULL,
  `mixaddhaircolor` int DEFAULT NULL,
  `mixhairbaseprob` int DEFAULT NULL,
  `level` int DEFAULT NULL,
  `job` int DEFAULT NULL,
  `str` int DEFAULT NULL,
  `dex` int DEFAULT NULL,
  `inte` int DEFAULT NULL,
  `luk` int DEFAULT NULL,
  `hp` int DEFAULT NULL,
  `maxhp` int DEFAULT NULL,
  `mp` int DEFAULT NULL,
  `maxmp` int DEFAULT NULL,
  `ap` int DEFAULT NULL,
  `sp` int DEFAULT NULL,
  `exp` mediumtext,
  `pop` int DEFAULT NULL,
  `money` mediumtext,
  `wp` int DEFAULT NULL,
  `extendsp` int DEFAULT NULL,
  `posmap` mediumtext,
  `portal` int DEFAULT NULL,
  `subjob` int DEFAULT NULL,
  `deffaceacc` int DEFAULT NULL,
  `fatigue` int DEFAULT NULL,
  `lastfatigueupdatetime` int DEFAULT NULL,
  `charismaexp` int DEFAULT NULL,
  `insightexp` int DEFAULT NULL,
  `willexp` int DEFAULT NULL,
  `craftexp` int DEFAULT NULL,
  `senseexp` int DEFAULT NULL,
  `charmexp` int DEFAULT NULL,
  `noncombatstatdaylimit` int DEFAULT NULL,
  `pvpexp` int DEFAULT NULL,
  `pvpgrade` int DEFAULT NULL,
  `pvppoint` int DEFAULT NULL,
  `pvpmodelevel` int DEFAULT NULL,
  `pvpmodetype` int DEFAULT NULL,
  `eventpoint` int DEFAULT NULL,
  `albaactivityid` int DEFAULT NULL,
  `albastarttime` datetime DEFAULT NULL,
  `albaduration` int DEFAULT NULL,
  `albaspecialreward` int DEFAULT NULL,
  `burning` tinyint(1) DEFAULT NULL,
  `charactercard` int DEFAULT NULL,
  `accountlastlogout` int DEFAULT NULL,
  `lastlogout` datetime DEFAULT NULL,
  `gachexp` int DEFAULT NULL,
  `honorexp` int DEFAULT NULL,
  `nextavailablefametime` datetime DEFAULT NULL,
  `lastlevelobtainedtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `extendsp` (`extendsp`),
  KEY `noncombatstatdaylimit` (`noncombatstatdaylimit`),
  KEY `charactercard` (`charactercard`),
  KEY `accountlastlogout` (`accountlastlogout`),
  KEY `idx_characterstats_name` (`name`),
  KEY `idx_characterstats_characterid` (`characterid`),
  CONSTRAINT `characterstats_ibfk_1` FOREIGN KEY (`extendsp`) REFERENCES `extendsp` (`id`),
  CONSTRAINT `characterstats_ibfk_2` FOREIGN KEY (`noncombatstatdaylimit`) REFERENCES `noncombatstatdaylimit` (`id`),
  CONSTRAINT `characterstats_ibfk_3` FOREIGN KEY (`charactercard`) REFERENCES `charactercards` (`id`),
  CONSTRAINT `characterstats_ibfk_4` FOREIGN KEY (`accountlastlogout`) REFERENCES `systemtimes` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `chosenskills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `skillid` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `chosenskills_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `collectedfamiliars` (
  `charid` int NOT NULL,
  `familiarId` int NOT NULL,
  PRIMARY KEY (`charid`,`familiarId`),
  CONSTRAINT `collectedfamiliars_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `contentcooldowns` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `startDate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `cs_categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idx` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `flag` int DEFAULT NULL,
  `parentIdx` int DEFAULT NULL,
  `stock` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `cs_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `itemID` int NOT NULL,
  `stock` int DEFAULT '1',
  `shopItemFlag` int DEFAULT '0',
  `idk1` int DEFAULT '0',
  `idk2` int DEFAULT '0',
  `oldPrice` int DEFAULT '0',
  `newPrice` int DEFAULT '0',
  `idkTime1` datetime DEFAULT NULL,
  `idkTime2` datetime DEFAULT NULL,
  `idkTime3` datetime DEFAULT NULL,
  `idkTime4` datetime DEFAULT NULL,
  `idk3` int DEFAULT '0',
  `bundleQuantity` int DEFAULT '0',
  `availableDays` int DEFAULT '0',
  `buyableWithMaplePoints` smallint DEFAULT '1',
  `buyableWithCredit` smallint DEFAULT '1',
  `buyableWithPrepaid` smallint DEFAULT '1',
  `likable` smallint DEFAULT '1',
  `meso` smallint DEFAULT '0',
  `favoritable` smallint DEFAULT '1',
  `gender` int DEFAULT '2',
  `likes` int DEFAULT '0',
  `requiredLevel` int DEFAULT '0',
  `idk10` varchar(255) DEFAULT NULL,
  `idk11` int DEFAULT '0',
  `idk13` int DEFAULT '0',
  `idk14` int DEFAULT '0',
  `category` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `damageskinsavedatas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `damageskinid` int DEFAULT NULL,
  `itemid` int DEFAULT NULL,
  `notsave` tinyint(1) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `accid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `accid` (`accid`),
  CONSTRAINT `damageskinsavedatas_ibfk_1` FOREIGN KEY (`accid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `emoticons` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `emoticonid` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `emoticons_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `emoticonshortcuts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `emoticonid` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  `shortcut` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `emoticonshortcuts_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `equip_drops` (
  `id` int NOT NULL,
  `job` varchar(255) NOT NULL,
  `level` int DEFAULT NULL,
  PRIMARY KEY (`id`,`job`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `extendsp` (
  `id` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `familiarcodexmanager` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `summongauge` int DEFAULT '2000',
  `familiarslots` int DEFAULT '100',
  `activefamiliars` varchar(255) DEFAULT NULL,
  `activebadges` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `familiars` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `familiarid` int DEFAULT NULL,
  `name` varchar(13) DEFAULT NULL,
  `locked` tinyint DEFAULT NULL,
  `level` int DEFAULT '1',
  `levelcap` int DEFAULT '5',
  `lvexp` int DEFAULT '0',
  `grade` int DEFAULT '0',
  `gradeexp` int DEFAULT '0',
  `attack` int DEFAULT '0',
  `defense` int DEFAULT '0',
  `options` varchar(255) DEFAULT '0,0',
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `familiars_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `first_enter_rewards` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountId` int DEFAULT NULL,
  `charId` int DEFAULT '0',
  `rewardType` varchar(255) DEFAULT NULL,
  `itemId` int DEFAULT '0',
  `quantity` int DEFAULT '1',
  `descr` varchar(8000) DEFAULT NULL,
  `expireTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_fer_accountId` (`accountId`),
  KEY `idx_fer_charId` (`charId`),
  CONSTRAINT `first_enter_rewards_ibfk_1` FOREIGN KEY (`accountId`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `friends` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ownerid` int DEFAULT NULL,
  `owneraccid` int DEFAULT NULL,
  `friendid` int DEFAULT NULL,
  `friendaccountid` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `flag` tinyint DEFAULT NULL,
  `groupname` varchar(255) DEFAULT NULL,
  `mobile` tinyint DEFAULT NULL,
  `nickname` varchar(255) DEFAULT NULL,
  `memo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `funckeymap` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_funckeymap_charid` (`charid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `gradenames` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gradename` varchar(255) DEFAULT NULL,
  `guildid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `guildid` (`guildid`),
  CONSTRAINT `gradenames_ibfk_1` FOREIGN KEY (`guildid`) REFERENCES `guilds` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `guildmembers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `guildid` int DEFAULT NULL,
  `grade` int DEFAULT NULL,
  `alliancegrade` int DEFAULT NULL,
  `commitment` int DEFAULT NULL,
  `daycommitment` int DEFAULT NULL,
  `igp` int DEFAULT NULL,
  `commitmentinctime` datetime DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `job` int DEFAULT NULL,
  `level` int DEFAULT NULL,
  `loggedin` tinyint(1) DEFAULT NULL,
  `lastOnline` datetime DEFAULT '1970-01-01 00:00:01',
  PRIMARY KEY (`id`),
  KEY `guildid` (`guildid`),
  CONSTRAINT `guildmembers_ibfk_1` FOREIGN KEY (`guildid`) REFERENCES `guilds` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `guildrequestors` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `guildid` int DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `job` int DEFAULT NULL,
  `level` int DEFAULT NULL,
  `loggedin` tinyint(1) DEFAULT NULL,
  `introduction` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `guildid` (`guildid`),
  CONSTRAINT `guildrequestors_ibfk_1` FOREIGN KEY (`guildid`) REFERENCES `guilds` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `guilds` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `leaderid` int DEFAULT NULL,
  `worldid` int DEFAULT NULL,
  `markbg` int DEFAULT NULL,
  `markbgcolor` int DEFAULT NULL,
  `mark` int DEFAULT NULL,
  `markcolor` int DEFAULT NULL,
  `maxmembers` int DEFAULT NULL,
  `notice` varchar(255) DEFAULT NULL,
  `points` int DEFAULT NULL,
  `seasonpoints` int DEFAULT NULL,
  `allianceid` int DEFAULT NULL,
  `level` int DEFAULT NULL,
  `rank` int DEFAULT NULL,
  `ggp` int DEFAULT NULL,
  `appliable` tinyint(1) DEFAULT NULL,
  `joinsetting` int DEFAULT NULL,
  `reqlevel` int DEFAULT NULL,
  `battleSp` int DEFAULT NULL,
  `fk_allianceid` int DEFAULT NULL,
  `gradenames` varchar(255) DEFAULT NULL,
  `gradePermissions` varchar(255) DEFAULT '-1,0,0,0,0',
  `customMark` blob,
  `activeTimes` int DEFAULT '0',
  `mainActivity` int DEFAULT '0',
  `ageGroup` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `guildskill` (
  `id` int NOT NULL AUTO_INCREMENT,
  `guildid` int DEFAULT NULL,
  `skillid` int DEFAULT NULL,
  `level` int DEFAULT NULL,
  `expiredate` datetime DEFAULT NULL,
  `buycharactername` varchar(255) DEFAULT NULL,
  `extendcharactername` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `guildid` (`guildid`),
  CONSTRAINT `guildskill_ibfk_1` FOREIGN KEY (`guildid`) REFERENCES `guilds` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `hairequips` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alid` int DEFAULT NULL,
  `equipid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alid` (`alid`),
  CONSTRAINT `hairequips_ibfk_1` FOREIGN KEY (`alid`) REFERENCES `avatarlook` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `hyperrockfields` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `ord` int DEFAULT NULL,
  `fieldid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `hyperrockfields_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `hyperstatinfos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `hsmid` int DEFAULT NULL,
  `preset` int DEFAULT NULL,
  `skillid` int DEFAULT NULL,
  `lv` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `hsmid` (`hsmid`),
  CONSTRAINT `hyperstatinfos_ibfk_1` FOREIGN KEY (`hsmid`) REFERENCES `hyperstatsmanager` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `hyperstatsmanager` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `currentpreset` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `hyperstatsmanager_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `ignoreditems` (
  `charid` int NOT NULL,
  `ignoreditem` int NOT NULL,
  PRIMARY KEY (`charid`,`ignoreditem`),
  CONSTRAINT `ignoreditems_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `inventories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT NULL,
  `slots` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `inventoryid` int DEFAULT NULL,
  `trunkid` int DEFAULT NULL,
  `itemid` int DEFAULT NULL,
  `bagindex` int DEFAULT NULL,
  `bagitemindex` int DEFAULT '-1',
  `cashitemserialnumber` bigint DEFAULT NULL,
  `dateexpire` datetime DEFAULT NULL,
  `invtype` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `iscash` tinyint(1) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `owner` varchar(255) DEFAULT NULL,
  `bossid` int DEFAULT NULL,
  `playercount` int DEFAULT NULL,
  `price` bigint DEFAULT NULL,
  `obtaineddate` datetime DEFAULT NULL,
  `tradable` tinyint(1) DEFAULT '0',
  `jokertosetitem` tinyint(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `tameness` smallint DEFAULT NULL,
  `repleteness` tinyint DEFAULT NULL,
  `petattribute` smallint DEFAULT NULL,
  `petskill` int DEFAULT NULL,
  `datedead` datetime DEFAULT NULL,
  `remainlife` int DEFAULT NULL,
  `activestate` tinyint DEFAULT NULL,
  `autobuffskill` int DEFAULT NULL,
  `autobuffskill2` int DEFAULT NULL,
  `pethue` int DEFAULT NULL,
  `giantrate` smallint DEFAULT NULL,
  `equippeddate` datetime DEFAULT NULL,
  `prevbonusexprate` int DEFAULT NULL,
  `options` varchar(255) DEFAULT NULL,
  `sockets` varchar(255) DEFAULT NULL,
  `tuc` smallint DEFAULT NULL,
  `cuc` smallint DEFAULT NULL,
  `istr` smallint DEFAULT NULL,
  `idex` smallint DEFAULT NULL,
  `iint` smallint DEFAULT NULL,
  `iluk` smallint DEFAULT NULL,
  `imaxhp` int DEFAULT NULL,
  `imaxmp` smallint DEFAULT NULL,
  `ipad` smallint DEFAULT NULL,
  `imad` smallint DEFAULT NULL,
  `ipdd` smallint DEFAULT NULL,
  `imdd` smallint DEFAULT NULL,
  `iacc` smallint DEFAULT NULL,
  `ieva` smallint DEFAULT NULL,
  `icraft` smallint DEFAULT NULL,
  `ispeed` smallint DEFAULT NULL,
  `ijump` smallint DEFAULT NULL,
  `attribute` smallint DEFAULT NULL,
  `leveluptype` smallint DEFAULT NULL,
  `level` smallint DEFAULT NULL,
  `exp` smallint DEFAULT NULL,
  `durability` smallint DEFAULT NULL,
  `iuc` smallint DEFAULT NULL,
  `ipvpdamage` smallint DEFAULT NULL,
  `ireducereq` smallint DEFAULT NULL,
  `specialattribute` smallint DEFAULT NULL,
  `durabilitymax` smallint DEFAULT NULL,
  `iincreq` smallint DEFAULT NULL,
  `growthenchant` smallint DEFAULT NULL,
  `psenchant` smallint DEFAULT NULL,
  `hyperupgrade` smallint DEFAULT NULL,
  `bdr` smallint DEFAULT NULL,
  `imdr` smallint DEFAULT NULL,
  `damr` smallint DEFAULT NULL,
  `statr` smallint DEFAULT NULL,
  `cuttable` smallint DEFAULT NULL,
  `exgradeoption` bigint DEFAULT NULL,
  `itemstate` smallint DEFAULT NULL,
  `grade` smallint DEFAULT NULL,
  `chuc` smallint DEFAULT NULL,
  `souloptionid` smallint DEFAULT NULL,
  `soulsocketid` smallint DEFAULT NULL,
  `souloption` smallint DEFAULT NULL,
  `specialgrade` int DEFAULT NULL,
  `tradeblock` tinyint(1) DEFAULT NULL,
  `equiptradeblock` tinyint(1) DEFAULT NULL,
  `fixedgrade` int DEFAULT NULL,
  `arc` smallint DEFAULT NULL,
  `symbolexp` int DEFAULT NULL,
  `symbollevel` smallint DEFAULT NULL,
  `bossreward` tinyint(1) DEFAULT NULL,
  `fstr` smallint DEFAULT NULL,
  `fdex` smallint DEFAULT NULL,
  `fint` smallint DEFAULT NULL,
  `fluk` smallint DEFAULT NULL,
  `fatt` smallint DEFAULT NULL,
  `fmatt` smallint DEFAULT NULL,
  `fdef` smallint DEFAULT NULL,
  `fhp` smallint DEFAULT NULL,
  `fmp` smallint DEFAULT NULL,
  `fspeed` smallint DEFAULT NULL,
  `fjump` smallint DEFAULT NULL,
  `fallstat` smallint DEFAULT NULL,
  `fboss` smallint DEFAULT NULL,
  `fdamage` smallint DEFAULT NULL,
  `flevel` smallint DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_inventories_index` (`inventoryid`),
  KEY `idx_trunk_index` (`trunkid`),
  KEY `idx_bagindex_index` (`bagindex`),
  CONSTRAINT `items_ibfk_1` FOREIGN KEY (`trunkid`) REFERENCES `trunks` (`id`) ON DELETE CASCADE,
  CONSTRAINT `items_ibfk_2` FOREIGN KEY (`inventoryid`) REFERENCES `inventories` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `keymaps` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fkmapid` int DEFAULT NULL,
  `idx` int DEFAULT NULL,
  `type` tinyint DEFAULT NULL,
  `val` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fkmapid` (`fkmapid`),
  CONSTRAINT `keymaps_ibfk_1` FOREIGN KEY (`fkmapid`) REFERENCES `funckeymap` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `linkskills` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `accid` int DEFAULT NULL,
  `linkskillid` int DEFAULT NULL,
  `level` int DEFAULT NULL,
  `originid` int DEFAULT NULL,
  `usingid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `accid` (`accid`),
  CONSTRAINT `linkskills_ibfk_1` FOREIGN KEY (`accid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `macros` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `skills` varchar(255) DEFAULT NULL,
  `muted` tinyint(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `macroPos` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `macros_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `macroskills` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ordercol` int DEFAULT NULL,
  `skillid` int DEFAULT NULL,
  `macroid` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `macroid` (`macroid`),
  CONSTRAINT `macroskills_ibfk_1` FOREIGN KEY (`macroid`) REFERENCES `macros` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `matrix_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `iconid` int DEFAULT NULL,
  `skillid1` int DEFAULT NULL,
  `skillid2` int DEFAULT NULL,
  `skillid3` int DEFAULT NULL,
  `slv` int DEFAULT NULL,
  `maxLevel` int DEFAULT NULL,
  `row` int DEFAULT NULL,
  `exp` int DEFAULT NULL,
  `crc` bigint DEFAULT NULL,
  `expiredate` datetime DEFAULT NULL,
  `charid` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  `locked` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `matrix_records_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `matrix_slots` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  `enhancementLv` int DEFAULT NULL,
  `unlockedByPurchase` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `matrix_slots_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `migration_history` (
  `version` int NOT NULL DEFAULT '0',
  `script_name` varchar(255) DEFAULT NULL,
  `executed_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `minigamerecords` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `type` int DEFAULT NULL,
  `win` int DEFAULT NULL,
  `tie` int DEFAULT NULL,
  `loss` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `minigamerecords_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `mob_drops` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `mobid` int DEFAULT NULL,
  `itemid` int DEFAULT NULL,
  `chance` int DEFAULT NULL,
  `minquant` int DEFAULT '1',
  `maxquant` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `monster_collection` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mobid` int DEFAULT NULL,
  `region` int DEFAULT NULL,
  `session` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `monster_collection_explorations` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `collectionid` int DEFAULT NULL,
  `collectionkey` int DEFAULT NULL,
  `monsterkey` varchar(255) DEFAULT NULL,
  `endDate` datetime DEFAULT NULL,
  `position` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `collectionid` (`collectionid`),
  CONSTRAINT `monster_collection_explorations_ibfk_1` FOREIGN KEY (`collectionid`) REFERENCES `monster_collections` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `monster_collection_group_rewards` (
  `region` int NOT NULL,
  `session` int NOT NULL,
  `groupid` int NOT NULL,
  `rewardid` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`region`,`session`,`groupid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `monster_collection_mobs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `collectionid` int DEFAULT NULL,
  `mobid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `collectionid` (`collectionid`),
  CONSTRAINT `monster_collection_mobs_ibfk_1` FOREIGN KEY (`collectionid`) REFERENCES `monster_collections` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `monster_collection_rewards` (
  `region` int NOT NULL,
  `session` int NOT NULL,
  `groupid` int NOT NULL,
  `collectionid` int DEFAULT NULL,
  PRIMARY KEY (`region`,`session`,`groupid`),
  KEY `collectionid` (`collectionid`),
  CONSTRAINT `monster_collection_rewards_ibfk_1` FOREIGN KEY (`collectionid`) REFERENCES `monster_collections` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `monster_collection_session_rewards` (
  `region` int NOT NULL,
  `session` int NOT NULL,
  `rewardid` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`region`,`session`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `monster_collections` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mc_group` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `monsterbookcards` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `bookid` int DEFAULT NULL,
  `cardid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `bookid` (`bookid`),
  CONSTRAINT `monsterbookcards_ibfk_1` FOREIGN KEY (`bookid`) REFERENCES `monsterbookinfos` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `monsterbookinfos` (
  `id` int NOT NULL AUTO_INCREMENT,
  `setid` int DEFAULT NULL,
  `coverid` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `new_player_progress` (
  `userid` int NOT NULL,
  `checkin_day` varchar(10) DEFAULT NULL,
  `checkin_step` int NOT NULL DEFAULT '0',
  `checklist_rewarded` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `noncombatstatdaylimit` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charisma` smallint DEFAULT NULL,
  `charm` smallint DEFAULT NULL,
  `insight` smallint DEFAULT NULL,
  `will` smallint DEFAULT NULL,
  `craft` smallint DEFAULT NULL,
  `sense` smallint DEFAULT NULL,
  `lastupdatecharmbycashpr` datetime DEFAULT NULL,
  `charmbycashpr` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `npc` (
  `id` int NOT NULL AUTO_INCREMENT,
  `npcid` int DEFAULT NULL,
  `mapid` int DEFAULT NULL,
  `x` int DEFAULT NULL,
  `y` int DEFAULT NULL,
  `cy` int DEFAULT NULL,
  `rx0` int DEFAULT NULL,
  `rx1` int DEFAULT NULL,
  `fh` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `offense_managers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `points` int DEFAULT NULL,
  `lieDetectorBans` int DEFAULT NULL,
  `lieDetectorFails` int DEFAULT NULL,
  `trust` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `offenses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `manager_id` int DEFAULT NULL,
  `charid` int DEFAULT NULL,
  `accountid` int DEFAULT NULL,
  `msg` text,
  `type` varchar(255) DEFAULT NULL,
  `issuedate` datetime DEFAULT NULL,
  `issuer_char_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `manager_id` (`manager_id`),
  CONSTRAINT `offenses_ibfk_1` FOREIGN KEY (`manager_id`) REFERENCES `offense_managers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `petids` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alid` int DEFAULT NULL,
  `petid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alid` (`alid`),
  CONSTRAINT `petids_ibfk_1` FOREIGN KEY (`alid`) REFERENCES `avatarlook` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `questmanagers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `questprogressrequirements` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `orderNum` int DEFAULT NULL,
  `progresstype` varchar(255) DEFAULT NULL,
  `questid` bigint DEFAULT NULL,
  `unitid` int DEFAULT NULL,
  `requiredcount` int DEFAULT NULL,
  `currentcount` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `questid` (`questid`),
  CONSTRAINT `questprogressrequirements_ibfk_1` FOREIGN KEY (`questid`) REFERENCES `quests` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `quests` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `questmanager_id` bigint DEFAULT NULL,
  `qrkey` int DEFAULT NULL,
  `qrvalue` text,
  `status` int DEFAULT NULL,
  `completedtime` datetime DEFAULT NULL,
  `qrvalueex` text,
  PRIMARY KEY (`id`),
  KEY `questmanager_id` (`questmanager_id`),
  CONSTRAINT `quests_ibfk_1` FOREIGN KEY (`questmanager_id`) REFERENCES `questmanagers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `rankingresults` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charnames` varchar(555) DEFAULT NULL,
  `amount` bigint DEFAULT '0',
  `amount2` bigint DEFAULT '0',
  `amount3` bigint DEFAULT '0',
  `rankingtime` int DEFAULT NULL,
  `timestamp` datetime DEFAULT NULL,
  `rankingtype` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `removednpcs` (
  `id` int NOT NULL AUTO_INCREMENT,
  `npcid` int DEFAULT NULL,
  `mapid` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `server_rates` (
  `name` varchar(32) NOT NULL,
  `value` double NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `shopitems` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `shopid` int DEFAULT NULL,
  `itemid` int DEFAULT NULL,
  `price` int DEFAULT NULL,
  `tokenitemid` int DEFAULT NULL,
  `tokenprice` int DEFAULT NULL,
  `pointquestid` int DEFAULT NULL,
  `pointprice` int DEFAULT NULL,
  `starcoin` int DEFAULT NULL,
  `questexid` int DEFAULT '0',
  `questexkey` varchar(255) DEFAULT NULL,
  `questexvalue` int DEFAULT NULL,
  `itemperiod` int DEFAULT NULL,
  `levellimited` int DEFAULT NULL,
  `showlevmin` int DEFAULT NULL,
  `showlevmax` int DEFAULT NULL,
  `questid` int DEFAULT NULL,
  `sellstart` datetime DEFAULT NULL,
  `sellend` datetime DEFAULT NULL,
  `tabindex` int DEFAULT NULL,
  `worldblock` tinyint(1) DEFAULT '0',
  `potentialgrade` int DEFAULT '0',
  `buylimit` int DEFAULT NULL,
  `quantity` smallint DEFAULT NULL,
  `unitprice` bigint DEFAULT NULL,
  `maxperslot` smallint DEFAULT NULL,
  `discountperc` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `skillcooltimes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `skillid` int DEFAULT NULL,
  `nextusabletime` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `skillcooltimes_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `skills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `skillid` int DEFAULT NULL,
  `rootid` int DEFAULT NULL,
  `maxlevel` int DEFAULT NULL,
  `currentlevel` int DEFAULT NULL,
  `masterlevel` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `skills_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `soulcollection` (
  `id` int NOT NULL AUTO_INCREMENT,
  `accountid` int DEFAULT NULL,
  `bosssoul` int DEFAULT NULL,
  `flag` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `accountid` (`accountid`),
  CONSTRAINT `soulcollection_ibfk_1` FOREIGN KEY (`accountid`) REFERENCES `accounts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `spset` (
  `id` int NOT NULL AUTO_INCREMENT,
  `extendsp_id` int DEFAULT NULL,
  `joblevel` tinyint DEFAULT NULL,
  `sp` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `extendsp_id` (`extendsp_id`),
  CONSTRAINT `spset_ibfk_1` FOREIGN KEY (`extendsp_id`) REFERENCES `extendsp` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `stolenskills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `skillid` int DEFAULT NULL,
  `position` int DEFAULT NULL,
  `currentlv` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `stolenskills_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `systemtimes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `yr` int DEFAULT NULL,
  `mnth` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `totems` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alid` int DEFAULT NULL,
  `totemid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alid` (`alid`),
  CONSTRAINT `totems_ibfk_1` FOREIGN KEY (`alid`) REFERENCES `avatarlook` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `trade_transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `uuid` varchar(255) NOT NULL,
  `fileTime` datetime NOT NULL,
  `characterIdFrom` int NOT NULL,
  `characterIdTo` int NOT NULL,
  `characterNameFrom` varchar(255) NOT NULL,
  `characterNameTo` varchar(255) NOT NULL,
  `userIdTo` int NOT NULL DEFAULT '0',
  `userIdFrom` int NOT NULL DEFAULT '0',
  `itemId` bigint DEFAULT NULL,
  `templateItemId` int DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `money` bigint DEFAULT NULL,
  `tradeTransactionType` varchar(255) DEFAULT NULL,
  `mapId` int DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_tt_uuid` (`uuid`),
  KEY `idx_tt_fileTime` (`fileTime`),
  KEY `idx_tt_characterIdFrom` (`characterIdFrom`),
  KEY `idx_tt_characterIdTo` (`characterIdTo`),
  KEY `idx_tt_characterNameFrom` (`characterNameFrom`),
  KEY `idx_tt_characterNameTo` (`characterNameTo`),
  KEY `idx_tt_itemId` (`itemId`),
  KEY `idx_tt_templateItemId` (`templateItemId`),
  KEY `idx_tt_money` (`money`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `trunks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `slotcount` int DEFAULT NULL,
  `money` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `unionboards` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `unionid` bigint DEFAULT NULL,
  `unionpower` int DEFAULT NULL,
  `uniondamage` bigint DEFAULT NULL,
  `synergyGrid` text,
  PRIMARY KEY (`id`),
  KEY `unionid` (`unionid`),
  CONSTRAINT `unionboards_ibfk_1` FOREIGN KEY (`unionid`) REFERENCES `unions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `unionmembers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `unionboardid` bigint DEFAULT NULL,
  `type` int DEFAULT NULL,
  `charid` int DEFAULT NULL,
  `mobileName` varchar(30) DEFAULT NULL,
  `gridpos` int DEFAULT NULL,
  `gridrotation` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `unionboardid` (`unionboardid`),
  CONSTRAINT `unionmembers_ibfk_1` FOREIGN KEY (`unionboardid`) REFERENCES `unionboards` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `unionraid` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `totaldamagedone` bigint DEFAULT '0',
  `curboss` int DEFAULT '0',
  `curshield` bigint DEFAULT '0',
  `curhp` bigint DEFAULT '0',
  `unclaimedcoins` int DEFAULT '0',
  `lasttime` bigint DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `unions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `unionCoin` int DEFAULT NULL,
  `unionRank` int DEFAULT NULL,
  `presets` int DEFAULT NULL,
  `activepreset` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `unseenequips` (
  `id` int NOT NULL AUTO_INCREMENT,
  `alid` int DEFAULT NULL,
  `equipid` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `alid` (`alid`),
  CONSTRAINT `unseenequips_ibfk_1` FOREIGN KEY (`alid`) REFERENCES `avatarlook` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `banExpireDate` datetime DEFAULT NULL,
  `banReason` varchar(255) DEFAULT NULL,
  `offensemanager` int DEFAULT NULL,
  `maplePoints` int DEFAULT '0',
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `pic` varchar(255) DEFAULT NULL,
  `mac` varchar(255) DEFAULT NULL,
  `accounttype` int DEFAULT '0',
  `age` int DEFAULT '0',
  `vipgrade` int DEFAULT '0',
  `nblockreason` int DEFAULT '0',
  `gender` tinyint DEFAULT '0',
  `msg2` tinyint DEFAULT '0',
  `purchaseexp` tinyint DEFAULT '0',
  `pblockreason` tinyint DEFAULT '3',
  `chatunblockdate` bigint DEFAULT '0',
  `hascensorednxloginid` tinyint(1) DEFAULT '0',
  `gradecode` tinyint DEFAULT '0',
  `censorednxloginid` varchar(255) DEFAULT NULL,
  `characterslots` int DEFAULT '4',
  `creationdate` datetime DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `registerip` varchar(255) DEFAULT NULL,
  `resetKey` varchar(255) DEFAULT NULL,
  `lastReset` datetime DEFAULT NULL,
  `resetType` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `offensemanager` (`offensemanager`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`offensemanager`) REFERENCES `offense_managers` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `vessel` (
  `id` int NOT NULL AUTO_INCREMENT,
  `charid` int DEFAULT NULL,
  `type` int NOT NULL DEFAULT '0',
  `level` int DEFAULT '1',
  `exp` int DEFAULT '0',
  `energy` int DEFAULT '100',
  PRIMARY KEY (`id`),
  KEY `charid` (`charid`),
  CONSTRAINT `vessel_ibfk_1` FOREIGN KEY (`charid`) REFERENCES `characters` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
CREATE TABLE `zeroinfo` (
  `id` int NOT NULL AUTO_INCREMENT,
  `betastate` tinyint(1) DEFAULT NULL,
  `subhp` int DEFAULT NULL,
  `submhp` int DEFAULT NULL,
  `submp` int DEFAULT NULL,
  `submmp` int DEFAULT '100',
  `subskin` int DEFAULT NULL,
  `subhair` int DEFAULT NULL,
  `subface` int DEFAULT NULL,
  `dbcharzerolinkcashpart` int DEFAULT '0',
  `mixbasehaircolor` int DEFAULT '0',
  `mixaddhaircolor` int DEFAULT '0',
  `mixhairbaseprob` int DEFAULT '0',
  `wpcoin` int DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

SET FOREIGN_KEY_CHECKS = 1;
