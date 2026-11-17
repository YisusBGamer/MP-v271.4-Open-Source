package net.swordie.webapi.site;

/** Settings of the public data the /api routes serve. */
public final class SiteConfig {

    /** Show which map online players are on. */
    public static final boolean SHOW_PLAYER_LOCATION = false;
    /** How long database results (rankings, totals) are cached. */
    public static final int CACHE_SECONDS = 60;

    private SiteConfig() {
    }
}
