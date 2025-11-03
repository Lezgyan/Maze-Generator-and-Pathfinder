package maze.path;

import maze.Cell;
import maze.Coordinate;
import maze.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BfsPathFinder implements PathFinder {

    private List<List<Integer>> adj;

    @Override
    public List<Coordinate> findPath(Maze maze, Coordinate from, Coordinate to) {
        this.adj = new ArrayList<>();

        int a = from.y() * maze.width() + from.x();
        int b = to.y() * maze.width() + to.x();
        int w = maze.width();
        List<Integer> p = findBFS(maze, a, b);
        List<Coordinate> path = new ArrayList<>();
        for (Integer i : p) {
            path.add(new Coordinate(i - (i / w) * w, i / w));
        }
        return path;
    }

    private void createAdj(Maze maze) {
        int h = maze.height();
        int w = maze.width();

        for (int i = 0; i < h * w; i++) {
            adj.add(new ArrayList<>());
        }

        for (int i = 1; i < maze.height(); i++) {
            for (int j = 0; j < maze.width(); j++) {
                if (maze.grid().get(i).get(j) == Cell.WALL) {
                    continue;
                }
                if (i + 1 < h && maze.grid().get(i + 1).get(j) != Cell.WALL) {
                    adj.get(i * w + j).add((i + 1) * w + j);
                }
                if (i - 1 >= 0 && maze.grid().get(i - 1).get(j) != Cell.WALL) {
                    adj.get(i * w + j).add((i - 1) * w + j);
                }
                if (j + 1 < w && maze.grid().get(i).get(j + 1) != Cell.WALL) {
                    adj.get(i * w + j).add(i * w + j + 1);
                }
                if (j - 1 >= 0 && maze.grid().get(i).get(j - 1) != Cell.WALL) {
                    adj.get(i * w + j).add(i * w + j - 1);
                }
            }
        }
    }

    public List<Integer> findBFS(Maze maze, int from, int to) {
        createAdj(maze);
        int n = maze.height() * maze.width();
        List<Integer> dist = new ArrayList<>(Collections.nCopies(n, n));
        List<Integer> p = new ArrayList<>(Collections.nCopies(n, -1));
        dist.set(from, 0);
        Queue<Integer> q = new LinkedList<>();
        q.add(from);

        while (!q.isEmpty()) {
            int v = q.poll();
            for (var u : adj.get(v)) {
                if (dist.get(u) > dist.get(v) + 1) {
                    p.set(u, v);
                    dist.set(u, dist.get(v) + 1);
                    q.add(u);
                }
            }
        }

        List<Integer> path = new ArrayList<>();
        if (dist.get(to) == n) {
            return path;
        }

        int current = to;
        while (current != -1) {
            path.add(current);
            current = p.get(current);
        }

        Collections.reverse(path);

        return path;
    }

}
