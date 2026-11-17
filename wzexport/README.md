# wzexport

Exports the v271 client's game data to the XML the server reads. The v271 client splits its .wz files into a Data
folder (Etc_000.wz, Etc_001.wz, .ms packs, ...) and the server can't read that directly, so this runs once.

You need:
- the .NET 10 SDK (https://dotnet.microsoft.com/download)
- MapleLib from HaRepacker Resurrected (https://github.com/lastbattle/Harepacker-resurrected/releases): take
  `MapleLib.dll` (and the other .dll files next to it) from the release and put them in a `maplelib` folder here.

Then run `Export XML.bat` and point it at your MapleStory folder (drag the folder onto it, or pass the path). It
writes everything to the `wz` folder next to the server. Takes a while; around 147,000 files.

Thanks to LastBattle and everyone behind HaRepacker / MapleLib.
