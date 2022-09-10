package mc.metype.points;

import java.util.HashMap;
import java.util.UUID;

public class PointsBalanceHandler {
    static HashMap<UUID, Integer> points = new HashMap<>();

    public static int GetPoints(UUID uuid) {
        if(points.containsKey(uuid)) {
            return points.get(uuid);
        } else {
            points.put(uuid, 0);
            return 0;
        }
    }

    public static void SetPoints(UUID uuid, int value) {
        points.put(uuid, value);
    }

    public static void GivePoints(UUID uuid, int value) {
        points.put(uuid, GetPoints(uuid) + value);
    }
}
