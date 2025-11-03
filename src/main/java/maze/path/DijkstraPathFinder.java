package maze.path;

import maze.Cell;
import maze.Coordinate;
import maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DijkstraPathFinder implements PathFinder {

    private static final int INF = 1000000000;

    private static final int ORDINARY_SURFACE = 100;
    private static final int GOOD_SURFACE = 0;
    private static final int BAD_SURFACE = 10000;

    private List<List<Pair>> adj;

    @Override
    public List<Coordinate> findPath(Maze maze, Coordinate from, Coordinate to) {
        this.adj = new ArrayList<>();

        int w = maze.width();
        int a = from.y() * maze.width() + from.x();
        int b = to.y() * maze.width() + to.x();

        List<Integer> p = dijkstra(maze, a, b);
        List<Coordinate> path = new ArrayList<>();
        for (Integer i : p) {
            path.add(new Coordinate(i - (i / w) * w, i / w));
        }

        return path;
    }

    private Pair change(int b, Cell a) {
        if (a == Cell.ROAD) {
            return new Pair(b, ORDINARY_SURFACE);
        }
        if (a == Cell.ROAD_GOOD) {
            return new Pair(b, GOOD_SURFACE);
        }
        return new Pair(b, BAD_SURFACE);
    }

    private void createAdj(Maze maze) {
        int h = maze.height();
        int w = maze.width();

        for (int i = 0; i < h * w; i++) {
            var row = new ArrayList<Pair>();
            adj.add(row);
        }

        for (int i = 1; i < maze.height(); i++) {
            for (int j = 0; j < maze.grid().get(i).size(); j++) {
                if (Cell.WALL == maze.grid().get(i).get(j)) {
                    continue;
                }
                if (i + 1 < h && maze.grid().get(i + 1).get(j) != Cell.WALL) {
                    adj.get(i * w + j).add(change((i + 1) * w + j, maze.grid().get(i + 1).get(j)));
                }
                if (i - 1 >= 0 && maze.grid().get(i - 1).get(j) != Cell.WALL) {
                    adj.get(i * w + j).add(change((i - 1) * w + j, maze.grid().get(i - 1).get(j)));
                }
                if (j + 1 < w && maze.grid().get(i).get(j + 1) != Cell.WALL) {
                    adj.get(i * w + j).add(change(i * w + j + 1, maze.grid().get(i).get(j + 1)));
                }
                if (j - 1 >= 0 && maze.grid().get(i).get(j - 1) != Cell.WALL) {
                    adj.get(i * w + j).add(change(i * w + j - 1, maze.grid().get(i).get(j - 1)));
                }
            }
        }
    }

    private List<Integer> dijkstra(Maze maze, int start, int finish) {
        createAdj(maze);
        int n = adj.size();
        List<Integer> d = new ArrayList<>(Collections.nCopies(n, INF));
        List<Integer> p = new ArrayList<>(Collections.nCopies(n, -1));
        List<Boolean> u = new ArrayList<>(Collections.nCopies(n, false));

        d.set(start, 0);

        for (int i = 0; i < n; i++) {
            int v = -1;
            for (int j = 0; j < n; j++) {
                if (!u.get(j) && (v == -1 || d.get(j) < d.get(v))) {
                    v = j;
                }
            }

            if (d.get(v) == INF) {
                break;
            }
            u.set(v, true);

            for (int j = 0; j < adj.get(v).size(); j++) {
                int to = adj.get(v).get(j).vertex;
                int len = adj.get(v).get(j).weight;
                if (d.get(v) + len < d.get(to)) {
                    d.set(to, d.get(v) + len);
                    p.set(to, v);
                }
            }
        }
        List<Integer> path = new ArrayList<>();

        if (p.get(finish) == -1) {
            return path;
        }

        for (int v = finish; v != start; v = p.get(v)) {
            path.add(v);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    private static class Pair {
        int vertex;
        int weight;

        Pair(int vertex, int distance) {
            this.vertex = vertex;
            this.weight = distance;
        }
    }

}

