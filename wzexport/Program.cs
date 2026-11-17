// Exports a 64-bit (Data folder) MapleStory client's WZ files and .ms packs to classic XML with HaRepacker's MapleLib.
// Layout: Data\<Cat>\<sub>\<X>_NNN.wz  ->  <out>\<Cat>.wz\<sub>\<image>.img.xml   (the split parts merge into one tree)
//         Data\Packs\<Cat>_NNNNN.ms    ->  <out>\<Cat>.wz\...
// Usage: wzexport <client folder or its Data folder> <out folder> [Cat1,Cat2,...] [--threads N] [--list]
using System;
using System.Collections.Concurrent;
using System.Collections.Generic;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using MapleLib.WzLib;
using MapleLib.WzLib.MSFile;
using MapleLib.WzLib.Serializer;

static class Program
{
    static readonly WzMapleVersion[] Versions = { WzMapleVersion.GMS, WzMapleVersion.BMS, WzMapleVersion.EMS, WzMapleVersion.CLASSIC };
    static WzMapleVersion? knownVersion;
    static readonly object versionLock = new();
    static readonly ConcurrentBag<string> errors = new();
    static int images, files;
    static string outRoot;
    static StreamWriter logFile;

    static int Main(string[] args)
    {
        if (args.Length < 2)
        {
            Console.WriteLine("usage: wzexport <client or Data folder> <out folder> [Cat1,Cat2,...] [--threads N] [--list]");
            return 2;
        }
        string data = Path.GetFullPath(args[0]);
        if (Directory.Exists(Path.Combine(data, "Data")))
            data = Path.Combine(data, "Data");
        outRoot = Path.GetFullPath(args[1]);
        var only = args.Skip(2).FirstOrDefault(a => !a.StartsWith("--") && !int.TryParse(a, out _))?
            .Split(',', StringSplitOptions.RemoveEmptyEntries).Select(s => s.Trim()).ToHashSet(StringComparer.OrdinalIgnoreCase);
        int threads = 4;
        int ti = Array.IndexOf(args, "--threads");
        if (ti >= 0 && ti + 1 < args.Length) threads = int.Parse(args[ti + 1]);
        bool listOnly = args.Contains("--list");

        Directory.CreateDirectory(outRoot);
        logFile = new StreamWriter(Path.Combine(outRoot, "_export-log.txt"), true) { AutoFlush = true };
        Log($"export {data} -> {outRoot}" + (only != null ? " only " + string.Join(",", only) : ""));

        var jobs = new List<(string cat, string rel, string[] parts, bool ms)>();
        foreach (var catDir in Directory.GetDirectories(data).OrderBy(d => d))
        {
            string cat = Path.GetFileName(catDir);
            if (cat.Equals("Packs", StringComparison.OrdinalIgnoreCase))
            {
                foreach (var ms in Directory.GetFiles(catDir, "*.ms").OrderBy(f => f))
                {
                    string msCat = Path.GetFileNameWithoutExtension(ms).Split('_')[0];
                    if (only == null || only.Contains(msCat))
                        jobs.Add((msCat, "", new[] { ms }, true));
                }
                continue;
            }
            if (only != null && !only.Contains(cat))
                continue;
            foreach (var dir in Directory.GetDirectories(catDir, "*", SearchOption.AllDirectories).Prepend(catDir))
            {
                if (dir.Split(Path.DirectorySeparatorChar).Any(p => p.Equals("_Canvas", StringComparison.OrdinalIgnoreCase)))
                    continue; // images only: the XML export doesn't need them
                string rel = Path.GetRelativePath(catDir, dir);
                if (rel == ".") rel = "";
                foreach (var ini in Directory.GetFiles(dir, "*.ini"))
                {
                    string baseName = Path.GetFileNameWithoutExtension(ini);
                    int last = ReadLastIndex(ini);
                    var parts = Enumerable.Range(0, last + 1).Select(i => Path.Combine(dir, $"{baseName}_{i:D3}.wz"))
                        .Where(File.Exists).ToArray();
                    if (parts.Length > 0)
                        jobs.Add((cat, rel, parts, false));
                    else if (File.Exists(Path.Combine(dir, baseName + ".wz")))
                        jobs.Add((cat, rel, new[] { Path.Combine(dir, baseName + ".wz") }, false)); // index says no parts: try the base file
                }
                // a .wz with no .ini of its own is a whole file
                foreach (var wz in Directory.GetFiles(dir, "*.wz"))
                {
                    string n = Path.GetFileNameWithoutExtension(wz);
                    bool isPart = n.Length > 4 && n[^4] == '_' && n[^3..].All(char.IsDigit);
                    if (!isPart && !File.Exists(Path.Combine(dir, n + ".ini")))
                        jobs.Add((cat, rel, new[] { wz }, false));
                }
            }
        }
        Log($"{jobs.Count} jobs, {jobs.Sum(j => j.parts.Length)} files");
        if (listOnly)
        {
            foreach (var j in jobs) Console.WriteLine($"{j.cat}.wz\\{j.rel}  <=  {string.Join(" + ", j.parts.Select(Path.GetFileName))}");
            return 0;
        }

        var sw = Stopwatch.StartNew();
        // biggest first, so the long ones don't finish last on their own
        var ordered = jobs.OrderByDescending(j => j.parts.Sum(p => new FileInfo(p).Length)).ToList();
        Parallel.ForEach(ordered, new ParallelOptions { MaxDegreeOfParallelism = threads }, job =>
        {
            foreach (var part in job.parts)
            {
                try
                {
                    if (job.ms) ExportMs(job.cat, part);
                    else ExportWz(job.cat, job.rel, part);
                }
                catch (Exception e)
                {
                    errors.Add($"{part}: {e}");
                    Log($"ERROR {part}: {e.GetType().Name}: {e.Message}"
                        + (e.InnerException != null ? $" ({e.InnerException.GetType().Name}: {e.InnerException.Message})" : ""));
                }
            }
        });
        Log($"done in {sw.Elapsed:hh\\:mm\\:ss}: {files} files, {images} images, {errors.Count} errors");
        if (!errors.IsEmpty)
            File.WriteAllLines(Path.Combine(outRoot, "_export-errors.txt"), errors.OrderBy(e => e));
        return errors.IsEmpty ? 0 : 1;
    }

    static int ReadLastIndex(string ini)
    {
        foreach (var line in File.ReadAllLines(ini))
        {
            var kv = line.Split('|');
            if (kv.Length == 2 && kv[0].Trim().Equals("LastWzIndex", StringComparison.OrdinalIgnoreCase)
                && int.TryParse(kv[1].Trim(), out int n))
                return n;
        }
        return 0;
    }

    static WzFile Open(string path)
    {
        var tryOrder = knownVersion is { } k ? new[] { k }.Concat(Versions.Where(v => v != k)) : Versions;
        foreach (var v in tryOrder)
        {
            var wz = new WzFile(path, v);
            var status = wz.ParseWzFile(null);
            if (status == WzFileParseStatus.Success && HasContent(wz.WzDirectory))
            {
                lock (versionLock)
                {
                    if (knownVersion == null)
                    {
                        knownVersion = v;
                        Log($"encryption: {v}");
                    }
                }
                return wz;
            }
            wz.Dispose();
        }
        throw new InvalidDataException("no known encryption parses this file");
    }

    // the wrong key still "parses", but the names come out as noise: real names are plain ASCII, images end in .img
    static bool HasContent(WzDirectory dir) =>
        dir != null && (dir.WzImages.Count > 0 || dir.WzDirectories.Count > 0)
        && dir.WzImages.Take(20).All(i => i.Name.EndsWith(".img") && Plain(i.Name))
        && dir.WzDirectories.Take(20).All(d => Plain(d.Name));

    static bool Plain(string s) => s.Length > 0 && s.All(c => c >= 0x20 && c < 0x7f);

    static void ExportWz(string cat, string rel, string path)
    {
        WzFile opened;
        try
        {
            opened = Open(path);
        }
        catch (InvalidDataException) when (new FileInfo(path).Length < 4096)
        {
            return; // a small base file with nothing in it (its data is in parts or packs)
        }
        using var wz = opened;
        WriteDirectory(wz.WzDirectory, Path.Combine(outRoot, cat + ".wz", rel));
        Interlocked.Increment(ref files);
        Log($"{cat}.wz\\{rel} <= {Path.GetFileName(path)}");
    }

    static void ExportMs(string cat, string path)
    {
        using var stream = File.OpenRead(path);
        using var ms = new WzMsFile(stream, Path.GetFileName(path), path, true, false);
        ms.ReadEntries();
        using var wz = ms.LoadAsWzFile();
        WriteDirectory(wz.WzDirectory, Path.Combine(outRoot, cat + ".wz"));
        Interlocked.Increment(ref files);
        Log($"{cat}.wz <= {Path.GetFileName(path)} ({ms.Entries.Count} entries)");
    }

    static void WriteDirectory(WzDirectory dir, string outDir)
    {
        Directory.CreateDirectory(outDir);
        var ser = new WzClassicXmlSerializer(0, LineBreak.None, false);
        foreach (var img in dir.WzImages.ToList())
        {
            if (!SafeName(img.Name))
                continue; // placeholder .wz stubs carry undecryptable names that no filesystem would take
            string file = Path.Combine(outDir, img.Name + ".xml");
            ser.SerializeImage(img, file);
            img.UnparseImage(); // keeps memory flat on the big files
            Interlocked.Increment(ref images);
        }
        foreach (var sub in dir.WzDirectories.ToList())
        {
            if (!SafeName(sub.Name))
                continue;
            WriteDirectory(sub, Path.Combine(outDir, sub.Name));
        }
    }

    static bool SafeName(string s) =>
        !string.IsNullOrEmpty(s) && s.All(c => c >= 0x20 && c < 0x7f) && s.IndexOfAny(Path.GetInvalidFileNameChars()) < 0;

    static void Log(string s)
    {
        string line = $"[{DateTime.Now:HH:mm:ss}] {s}";
        lock (logFile ?? (object)Console.Out)
        {
            Console.WriteLine(line);
            logFile?.WriteLine(line);
        }
    }
}
