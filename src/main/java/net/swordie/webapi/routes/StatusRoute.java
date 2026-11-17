package net.swordie.webapi.routes;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import net.swordie.ms.Server;
import net.swordie.ms.ServerConstants;
import net.swordie.ms.client.character.Char;
import net.swordie.ms.ext.ServerHooks;
import net.swordie.ms.world.Channel;
import net.swordie.ms.world.World;
import net.swordie.webapi.ApiConstants;
import net.swordie.webapi.protocol.result.StatusResult;

@Path("/status")
public class StatusRoute {

    @GET
    @Path("")
    @Produces({MediaType.APPLICATION_JSON})
    public StatusResult getStatus() {
        var result = new StatusResult();
        // Player Count
        result.playerCount = countListedOnline();

        // Server Started Time
        result.startTime = Server.getInstance().getStartedTime().toString();

        // Server Version
        result.version = String.format("%s.%s", ServerConstants.VERSION, ServerConstants.MINOR_VERSION);

        // Shutdown
        result.isShutdown = Server.getInstance().isInShutdown();
        result.remainingMin = Server.getInstance().getMinutesRemaining();
        result.shutdownMin = Server.getInstance().getShutdownScheduledMinutes();

        return result;
    }

    /** Online characters, without hidden GMs, unlisted ones, and staff when WEB_HIDE_STAFF is on. */
    private static int countListedOnline() {
        int n = 0;
        for (World world : Server.getInstance().getWorlds()) {
            for (Channel channel : world.getChannels()) {
                for (Char chr : new java.util.ArrayList<>(channel.getChars().values())) {
                    if (!chr.isHidden() && !ServerHooks.isUnlisted(chr) && (!ServerConstants.WEB_HIDE_STAFF
                            || chr.getUser() == null || chr.getUser().isPlayer())) {
                        n++;
                    }
                }
            }
        }
        return n;
    }

}
