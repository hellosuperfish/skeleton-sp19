package bearmaps.proj2c;

import bearmaps.hw4.WeightedEdge;
import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;
import bearmaps.proj2c.utils.MyTrieSet;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);
        // You might find it helpful to uncomment the line below:
         List<Node> nodes = this.getNodes();
    }

    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        List<Node> nodes = this.getNodes();
        Map<Point, Node> vMap = new HashMap<>();
        List<Point> points = new ArrayList<>();
        for (Node n : nodes) {
            if (!neighbors(n.id()).isEmpty()) {
                Point p = new Point(n.lon(), n.lat());
                points.add(p);
                vMap.put(p, n);
            }
        }
        KDTree kd = new KDTree(points);
        Point near = kd.nearest(lon, lat);
        return vMap.get(near).id();
    }


    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        List<Node> nodes = this.getNodes();
        MyTrieSet cleanNames = new MyTrieSet();
        Map<String, String> nameMap = new HashMap<>();
        for(Node n : nodes) {
            if(!(n.name()==null)) {
                //System.out.println(n.name());
                cleanNames.add(n.name().toLowerCase());
                nameMap.put(n.name().toLowerCase(), n.name());
            }
        }
        List<String> cleanLocations = cleanNames.keysWithPrefix(prefix);
        List<String> locations = new ArrayList<>();
        if((cleanLocations != null) && (!cleanLocations.isEmpty()) ) {
            for (String location : cleanLocations) {
                locations.add(nameMap.get(location));
            }
        }
        return locations;
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Node> nodes = this.getNodes();
        MyTrieSet cleanNames = new MyTrieSet();
        Map<String, Node> nameMap = new HashMap<>();
        for(Node n : nodes) {
            if (!(n.name() == null)) {
                //System.out.println(n.name());
                cleanNames.add(n.name().toLowerCase());
                nameMap.put(n.name().toLowerCase(), n);
            }
        }
        List<Map<String, Object>> locations = new ArrayList<>();

        String lowerName = locationName.toLowerCase();
        if(cleanNames.contains(lowerName)) {
            Map<String, Object> location = new HashMap<>();
            location.put("lat", nameMap.get(lowerName).lat());
            location.put("lon", nameMap.get(lowerName).lon());
            location.put("name", nameMap.get(lowerName).name());
            location.put("id", nameMap.get(lowerName).id());
            locations.add(location);
            System.out.println(location.get("name"));
        }
        return locations;
    }


    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    /**
     * get the street name from
     * @param start
     * @param end
     * @return name of the street
     */

    public String getStreetName (Long start, Long end) {
        List<WeightedEdge<Long>> startNeighbors = neighbors(start);
        for (WeightedEdge<Long> path : startNeighbors) {
            if (path.to().equals(end)) {
                return path.getName();
            }
        }
        return "name not found";
    }

}
