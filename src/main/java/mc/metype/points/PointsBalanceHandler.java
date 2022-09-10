package mc.metype.points;

import java.util.HashMap;
import java.util.UUID;

public class PointsBalanceHandler {
    static HashMap<UUID, Integer> points;

    public static int GetPointsBalance(UUID uuid) {
        if(points.containsKey(uuid)) {
            return points.get(uuid);
        } else {
            points.put(uuid, 0);
            return 0;
        }
    }
}
