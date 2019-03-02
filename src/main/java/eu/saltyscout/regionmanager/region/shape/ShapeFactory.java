package eu.saltyscout.regionmanager.region.shape;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector2;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.*;
import com.sk89q.worldedit.world.World;
import eu.saltyscout.booleregion.region.*;
import eu.saltyscout.regionmanager.region.RegionType;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Peter on 17-Nov-16.
 */
public class ShapeFactory {
    private ShapeFactory() {

    }

    public synchronized static HashMap<String, Object> serialize(@Nonnull com.sk89q.worldedit.regions.Region shape) {
        checkNotNull(shape);

        HashMap<String, Object> map = new HashMap<>();
        if (shape instanceof RegionWrapper) {
            HashMap<String, Object> shapeMap = serialize(((RegionWrapper) shape).getRegion());
            shapeMap.put("operation", "wrap");
            shapeMap.put("type", getRegionType(((RegionWrapper) shape).getRegion()).toString());
            map.put("shape", shapeMap);
        } else if (shape instanceof CuboidRegion) {
            map.put("edg1", toArray(shape.getMinimumPoint()));
            map.put("edg2", toArray(shape.getMaximumPoint()));
        } else if (shape instanceof Polygonal2DRegion) {
            Polygonal2DRegion region = (Polygonal2DRegion) shape;
            List<List<Integer>> points = new ArrayList<>();
            for (BlockVector2 point : region.getPoints()) {
                points.add(Arrays.asList((int) Math.round(point.getX()), (int) Math.round(point.getZ())));
            }
            map.put("points", points);
            map.put("min-y", region.getMinimumY());
            map.put("max-y", region.getMaximumY());
        } else if (shape instanceof CylinderRegion) {
            CylinderRegion region = (CylinderRegion) shape;
            map.put("center", toArray(region.getCenter()));
            map.put("radius", toArray(region.getRadius()));
            map.put("min-y", region.getMinimumY());
            map.put("max-y", region.getMaximumY());
        } else if (shape instanceof EllipsoidRegion) {
            EllipsoidRegion region = (EllipsoidRegion) shape;
            map.put("center", toArray(region.getCenter()));
            map.put("radius", toArray(region.getRadius()));
        } else if (shape instanceof ConvexPolyhedralRegion) {
            ConvexPolyhedralRegion region = (ConvexPolyhedralRegion) shape;
            List<List<Integer>> points = new ArrayList<>();
            for (BlockVector3 point : region.getVertices()) {
                points.add(Arrays.asList((int) Math.round(point.getX()), (int) Math.round(point.getY()), (int) Math.round(point.getZ())));
            }
            map.put("vertices", points);
        } else if (shape instanceof PairWrapper) {
            map.put("operation", ((PairWrapper) shape).getOperation());
            map.put("a", serialize(((PairWrapper) shape).getA()));
            map.put("b", serialize(((PairWrapper) shape).getB()));
        }
        return map;
    }

    public static RegionType getRegionType(@Nonnull Region shape) {
        checkNotNull(shape);

        if (shape instanceof BooleRegion) {
            return RegionType.BOOLE;
        }
        if (shape instanceof CuboidRegion) {
            return RegionType.CUBOID;
        }
        if (shape instanceof Polygonal2DRegion) {
            return RegionType.POLYGONAL;
        }
        if (shape instanceof EllipsoidRegion) {
            return RegionType.ELLIPSOID;
        }
        if (shape instanceof CylinderRegion) {
            return RegionType.CYLINDER;
        }
        if (shape instanceof ConvexPolyhedralRegion) {
            return RegionType.CONVEX;
        }
        return RegionType.UNKNOWN;
    }

    public synchronized static com.sk89q.worldedit.regions.Region deserialize(@Nonnull RegionType type, @Nonnull ConfigurationSection dataSection) {
        checkNotNull(type);
        checkNotNull(dataSection);

        ConfigurationSection shapeSection = dataSection.getConfigurationSection("shape");
        World world = new BukkitWorld(Bukkit.getWorld(dataSection.getString("world", Bukkit.getWorlds().get(0).getName())));
        com.sk89q.worldedit.regions.Region shape = null;
        if (type == RegionType.CUBOID) {
            List<Integer> edg1 = shapeSection.getIntegerList("edg1");
            BlockVector3 edgeOne = BlockVector3.at(edg1.get(0), edg1.get(1), edg1.get(2));
            List<Integer> edg2 = shapeSection.getIntegerList("edg2");
            BlockVector3 edgeTwo = BlockVector3.at(edg2.get(0), edg2.get(1), edg2.get(2));
            shape = new CuboidRegion(world, edgeOne, edgeTwo);
        } else if (type == RegionType.POLYGONAL) {
            List<BlockVector2> points = new ArrayList<>();
            for (Object item : shapeSection.getList("points", new ArrayList<List<Integer>>())) {
                List<Integer> point = (List<Integer>) item;
                points.add(BlockVector2.at(Math.round(point.get(0)), Math.round(point.get(1))));
            }
            int minY = shapeSection.getInt("min-y", 0);
            int maxY = shapeSection.getInt("max-y", 256);
            shape = new Polygonal2DRegion(world, points, minY, maxY);
        } else if (type == RegionType.CYLINDER) {
            List<Integer> cntr = shapeSection.getIntegerList("center");
            BlockVector3 center = BlockVector3.at(cntr.get(0), cntr.get(1), cntr.get(2));
            List<Integer> rd = shapeSection.getIntegerList("radius");
            Vector2 radius = Vector2.at(rd.get(0), rd.get(1));
            int minY = shapeSection.getInt("min-y", 0);
            int maxY = shapeSection.getInt("max-y", 256);
            shape = new CylinderRegion(world, center, radius, minY, maxY);
        } else if (type == RegionType.ELLIPSOID) {
            List<Integer> cntr = shapeSection.getIntegerList("center");
            BlockVector3 center = BlockVector3.at(cntr.get(0), cntr.get(1), cntr.get(2));
            List<Integer> rd = shapeSection.getIntegerList("radius");
            Vector3 radius = Vector3.at(rd.get(0), rd.get(1), rd.get(2));
            shape = new EllipsoidRegion(world, center, radius);
        } else if (type == RegionType.CONVEX) {
            shape = new ConvexPolyhedralRegion(world);
            for (Object item : shapeSection.getList("vertices", new ArrayList<List<Integer>>())) {
                List<Integer> point = (List<Integer>) item;
                ((ConvexPolyhedralRegion) shape).addVertex(BlockVector3.at(Math.round(point.get(0)), Math.round(point.get(1)), Math.round(point.get(2))));
            }
        } else if (type == RegionType.BOOLE) {
            /*System.out.println(shapeSection);
            System.out.println(shapeSection.getString("operation"));
            System.out.println(
                    String.join(
                            ", ",
                            shapeSection.getKeys(false).stream()
                                    .map(
                                            key -> String.valueOf(key + "=" + shapeSection.get(key))
                                    ).collect(Collectors.toList())
                    )
            );*/
            switch (shapeSection.getString("operation")) {
                case "union": {
                    BooleRegion a = (BooleRegion) deserialize(type, shapeSection.getConfigurationSection("a"));
                    BooleRegion b = (BooleRegion) deserialize(type, shapeSection.getConfigurationSection("b"));
                    shape = new UnionWrapper(a, b);
                    break;
                }
                case "difference": {
                    BooleRegion a = (BooleRegion) deserialize(type, shapeSection.getConfigurationSection("a"));
                    BooleRegion b = (BooleRegion) deserialize(type, shapeSection.getConfigurationSection("b"));
                    shape = new DifferenceWrapper(a, b);
                    break;
                }
                case "intersection": {
                    BooleRegion a = (BooleRegion) deserialize(type, shapeSection.getConfigurationSection("a"));
                    BooleRegion b = (BooleRegion) deserialize(type, shapeSection.getConfigurationSection("b"));
                    shape = new IntersectionWrapper(a, b);
                    break;
                }
                case "wrap": {
                    shape = new RegionWrapper(deserialize(RegionType.valueOf(shapeSection.getString("type")), shapeSection.getParent()));
                    break;
                }
            }
        }
        if (shape != null) {
            shape.setWorld(world);
        }
        return shape;
    }

    private static int[] toArray(BlockVector3 center) {
        return new int[]{center.getX(), center.getY(), center.getZ()};
    }

    private static double[] toArray(Vector3 center) {
        return new double[]{center.getX(), center.getY(), center.getZ()};
    }

    private static int[] toArray(BlockVector2 center) {
        return new int[]{center.getX(), center.getZ()};
    }

    private static double[] toArray(Vector2 center) {
        return new double[]{center.getX(), center.getZ()};
    }
}
