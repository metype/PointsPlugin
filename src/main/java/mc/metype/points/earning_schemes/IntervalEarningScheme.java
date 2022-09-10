package mc.metype.points.earning_schemes;

import mc.metype.points.PointsBalanceHandler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class IntervalEarningScheme extends EarningScheme {

    static int secondsInterval = 0;
    static int earningPerInterval = 0;

    public static void Init(boolean en, int secInt, int earnPerInt, JavaPlugin plugin) {
        enabled = en;
        secondsInterval = secInt;
        earningPerInterval = earnPerInt;
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(() -> {
            for (Player player : plugin.getServer().getOnlinePlayers()) PointsBalanceHandler.GivePoints(player.getUniqueId(), earningPerInterval);
        }
        , 0, secInt, TimeUnit.SECONDS);
    }
}
