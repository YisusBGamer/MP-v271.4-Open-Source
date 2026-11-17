# Setting up a Swordie Plus v271 server

This goes from a fresh PC to players logging in. It's written for Windows, but the server itself runs anywhere
Java does.

## What you need

- **Java 21 or newer** (a JDK, not just the runtime). [Adoptium Temurin](https://adoptium.net/) works.
- **Maven**, to build the server. [maven.apache.org](https://maven.apache.org/download.cgi). IntelliJ IDEA comes
  with it too.
- **MySQL 8**. [MySQL Community Server](https://dev.mysql.com/downloads/mysql/). Write down the root password you
  pick during the install.
- **MapleStory v271.4 from Steam** (Steam build 26141834). The server reads its game data from that version.
- **About 8 GB of free memory** for the server.

## 1. Get the code

Clone the repo or download it as a zip and unzip it somewhere, like `C:\SwordiePlus`. This guide calls that the
**server folder**: the one with `pom.xml`, `src` and `sql` in it.

The commands below are for **Command Prompt** (`cmd`), opened in the server folder: open the folder in File
Explorer, click the address bar, type `cmd` and press Enter.

## 2. The game data

The server is v271.4 and the game has to match it exactly. Install MapleStory from Steam; if Steam has already
moved on to a newer version, get build 26141834 from the Steam console (Windows + R, `steam://open/console`):

```
download_depot 216150 216151 4702473775019956240
```

The launcher can also download that exact build for players with steamcmd.

The server reads the client's own game files. The easy way: in the launcher, open the cog on the sign-in screen
and pick **Set up game data** (after the first time, admins find it on the Server page). It copies them for you.

By hand: copy everything in the client's `Data` folder into a `wz` folder in
the server folder, so you end up with `wz\Base`, `wz\Etc`, `wz\Map`, ..., `wz\Packs`. The `_Canvas` folders are only
pictures and the server doesn't need them; skip them and it's about 12 GB instead of 66:

```
robocopy "C:\Program Files (x86)\Steam\steamapps\common\MapleStory\Data" wz /E /XD _Canvas
```

A copy keeps working when Steam updates the game. You can also point `data.wzDir` in `server.properties` at the
client's `Data` folder instead (step 6); the server only reads it. If the folder is wrong (empty, `Data` one folder
too deep, files from another version) the server tells you on start what to fix.

An XML export (one folder per .wz, like `Map.wz\Map\Map0\...img.xml`) still works too, if you'd rather have one: the
tool in `wzexport` makes it (see its README).

The first time the server starts, it builds a `dat` folder from the game data. That takes a while; after that it
starts in under a minute. Delete `dat` whenever the game data changes version, or if a first start failed partway.

## 3. Set up the database

Make an empty database called `swordie271`, then run every file in `sql\init` in name order:

```
set PATH=%PATH%;C:\Program Files\MySQL\MySQL Server 8.0\bin
mysql -u root -p -e "create database swordie271"
for %f in (sql\init\*.sql) do mysql -u root -p swordie271 < %f
```

(Change `8.0` if you installed a different MySQL version. In a .bat file write `%%f` instead of `%f`.) It asks for
your password once per file. When it's done you have every table, the game data tables and one account: **admin**
with the password **admin** (change it right away: see step 9).

Anything new in `sql\migration` runs on its own when the server starts, so you only do this step once. See
[Updating](#updating) for later.

## 4. Tell the server how to reach the database

Copy `database.properties.example` to `database.properties` in the server folder and fill it in:

```
url=jdbc:mysql://127.0.0.1:3306/swordie271
username=root
password=your MySQL password
```

This file stays on your PC. It's in `.gitignore`.

## 5. Build it

```
mvn package
```

The first build downloads what it needs and takes a few minutes. You get `bin\maplestory-1.77.3.jar`.

## 6. Settings

Copy `server.properties.example` to `server.properties` to change the defaults: the game data folder
(`data.wzDir`), ports, and whether the web API needs HTTPS. Without it the defaults are used.

The connection settings and the packet filter that keep the game from crashing come with the source in
`resources/server.defaults.properties`. You don't need to copy them; anything you set in `server.properties`
wins over them.

By default, logging in and making accounts only work over https://, from the server PC itself, or from your own
network. If players connect from outside your network and you don't have HTTPS, set `web.requireHttps=false`
(passwords are then sent unencrypted).

- **Worlds and channels:** `resources\world\*.properties`.
- **Rates:** the `server_rates` table. Restart the server after changing them.
- **Play style:** the `server_features` table or `features.preset`, see `docs\playstyle-settings.md`.

## 7. Start it

From the server folder (never from inside `bin`; it reads its scripts and settings from the current folder):

```
java -Xmx8g -jar bin\maplestory-1.77.3.jar
```

It's ready when it says `Finished loading server`.

## 8. Open the ports

| Port | What it's for |
|---|---|
| 8484 | Login |
| 8585 and up | Channels (8585 is channel 1, 8586 channel 2, and so on) |
| 3000 | Web API (launcher login, accounts) |

Allow them through Windows Firewall. For players outside your network, forward the same ports on your router.

## 9. The admin account

The database setup makes one account: **admin** with the password **admin**. Change that password first:

1. Open the launcher and log in as admin. While the password is still `admin`, a yellow warning says so at the top.
2. Press **Change password** in the warning (or go to Settings > Account > Change password), enter `admin` and your
   new password twice, and save.

To make another account an admin (for example your own player account):

1. Create the account in the launcher (Create account on the sign-in screen), or let the player create it.
2. Log in to the launcher as an admin and open **People > Logins**. Search for the account and select it.
3. Set **Account type** to **Admin** and press **Save**. If they're in the game, they relog to get it there.

The same list sets the other account types (Player, Tester, Intern, GameMaster). Nobody can take admin away from the
account they're logged in with, so there's always at least one admin.

## 10. Players connecting

Players install the same v271.4 game and use the launcher from
[swordie-plus-launcher-v271](https://gitlab.com/agentshawnx/swordie-plus-launcher-v271), pointed at your server's
address. It logs in through the web API on port 3000 and starts the game. The client hook that sends the game to
your server instead of Nexon's isn't part of either repo.

Log in as **admin** / **admin** first and change the password. GM commands: `!help` in game.

Players make their own account with **Create account** on the launcher's sign-in screen: a username of 4 to 20
letters, numbers or `_`, a password of 6 characters or more, and an email. The server also takes accounts without an
email; the email is only used to send a forgotten username or a password reset, and without one an admin resets the
password on **People > Logins**. Passwords are
stored as BCrypt hashes. One address can make 3 accounts an hour (`web.accountsPerHour`); `web.registration=false`
closes sign-ups, for example while you wipe.

## Updating

You never redo step 3. `sql\init` is only for a brand new database; after that the server applies anything new in
`sql\migration` by itself when it starts, and skips what it already ran.

**With git (easiest).** Clone once instead of downloading a zip:

```
git clone https://gitlab.com/agentshawnx/swordie-plus-v271.git
```

Git remembers where the code came from. To update, in the server folder:

```
git pull
mvn package
```

then start the server. `git pull` only brings in what changed. Your `database.properties`, `server.properties`,
`dat` and `bin` folders are ignored by git, so it never touches your settings. If you already have a zip copy,
clone into a new folder and copy those two `.properties` files (and `dat`, to skip rebuilding it) over.

**With a zip.** Download the new zip and unzip it over your server folder, overwriting everything. Don't delete
your two `.properties` files or `dat`. Then `mvn package` and start.

Delete `dat` only when the game data changes version (the update notes say so when it does).

## Backups

The server backs up the database by itself: every 6 hours, and before a start runs new files from `sql\migration`.
Each backup is one zip in `backups\db` in the server folder, named after the database, the time and why it was made
(`timed` or `migration`). The newest 28 of each kind are kept. It uses MySQL's `mysqldump`, which it finds on PATH or
in `Program Files\MySQL`; if it says it found none, set `db.backup.mysqldump` in `server.properties` to MySQL's
`bin` folder. The interval, how many to keep and the folder are the `db.backup.*` settings in
`server.properties.example`.

Put the folder on another drive, or copy the zips somewhere else now and then: a backup on the same disk doesn't
help if the disk dies.

**Restoring one:**

1. Stop the server.
2. Right-click the zip, pick **Extract All**. You get one `.sql` file.
3. In Command Prompt (with MySQL's `bin` folder on PATH, see step 3):

   ```
   mysql -u root -p swordie271 < "C:\SwordiePlus\backups\db\swordie271-20261105-180000-timed.sql"
   ```

   Use your database name and the file you extracted. It replaces every table with the one in the backup.
   To look at a backup without touching the live database, restore it into a new one instead
   (`mysql -u root -p -e "create database swordie271_old"`, then the same command with `swordie271_old`).
4. Start the server.

## Fresh start

To wipe the server and start over (a new season, or after testing). Your `.properties` files and the `dat`
folder stay as they are:

1. Stop the server and make sure nobody is on.
2. Back up the database by hand and keep the file somewhere safe:

   ```
   mysqldump -u root -p --single-transaction swordie271 > before-wipe.sql
   ```

3. Drop the database and set it up again, as in step 3:

   ```
   mysql -u root -p -e "drop database swordie271; create database swordie271"
   for %f in (sql\init\*.sql) do mysql -u root -p swordie271 < %f
   ```

   This removes every account and character. You're back to the single **admin** / **admin** account, so change
   its password again (step 9).
4. Rates and play style are in the database too (`server_rates`, `server_features`). If you changed them, set them
   again, or restore just those tables from your backup.
5. Start the server, log in, make a character and check that it saves (log out and back in).

## If something goes wrong

- **"Missing database.properties"**: step 4 wasn't done, or the server wasn't started from the server folder.
  In IntelliJ, open Run > Edit Configurations and set the Working directory to the server folder (the one with
  pom.xml), not the folder above it.
- **"'mvn' is not recognized"** (or `java` or `mysql`): add the program's `bin` folder to your PATH and open a new
  command prompt.
- **"package org.apache.logging.log4j does not exist"** (or other missing packages when building): the code was
  compiled without its libraries. Build with Maven (`mvn package` in the server folder), not by compiling the files
  directly. In IntelliJ, right-click `pom.xml`, pick **Add as Maven Project** and wait for it to finish loading before
  you build. If it still fails, run `mvn -U package` with a working internet connection and look at the lines above
  the error for "Could not resolve" or "Could not transfer".
- **Can't connect to MySQL**: check the password in `database.properties` and that the MySQL service is running.
- **"For your security, use the secure (https://) address"**: see step 6.
- **Players can't connect from outside**: check the firewall and router ports, and that they use your public IP.
- **The first start takes forever**: that's the `dat` folder being built. Let it finish.
