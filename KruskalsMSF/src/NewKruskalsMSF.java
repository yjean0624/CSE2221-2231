import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class NewKruskalsMSF {

    /**
     * Edge class.
     *
     * @author Xingyue Zhao
     *
     */
    class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    /**
     * No argument constructor
     */
    private NewKruskalsMSF() {

    }

    /**
     * Put edges into the priority queue
     */
    public void makePriorityQ(PriorityQueue<Edge> q, Scanner in) {
        while (in.hasNextLine()) {
            String temp = in.nextLine();
            int source = Integer.parseInt(temp.substring(0, temp.indexOf(',')));
            int destination = Integer.parseInt(
                    temp.substring(temp.indexOf(',') + 1, temp.indexOf(':')));
            int weight = Integer
                    .parseInt(temp.substring(temp.indexOf(':') + 1));
            Edge e = new Edge(source, destination, weight);
            q.add(e);
        }
    }

    /**
     * kruskal's mst
     */
    public void kruskal(PriorityQueue<Edge> q, int number) {
        unionFind uf = new unionFind();
        int[] parent = uf.makeSet(number);
        ArrayList<Edge> arr = new ArrayList<>();
        int i = 0;
        while (i < number - 1) {
            Edge e = q.remove();
            int x = uf.findSet(parent, e.source);
            int y = uf.findSet(parent, e.destination);
            if (x != y) {
                arr.add(e);
                uf.union(parent, x, y);
                System.out.println(
                        e.source + "," + e.destination + ":" + e.weight);
                i++;
            }
        }

    }

    class unionFind {
        public int[] makeSet(int number) {
            int[] temp = new int[number];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = i;
            }
            return temp;
        }

        public int findSet(int[] parent, int x) {
            if (parent[x] != x) {
                x = this.findSet(parent, parent[x]);
            }
            return x;
        }

        public void union(int[] parent, int x, int y) {
            parent[this.findSet(parent, y)] = this.findSet(parent, x);
        }
    }

    private static class Comparison implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return Integer.compare(o1.weight, o2.weight);
        }
    }

    public static void main(String[] args) throws IOException {
        //count edges
        long lineCount = Files
                .lines(Paths.get(
                        "C:\\Users\\yjean\\Desktop\\sampleGraphs\\10-20.txt"))
                .count();
        int lines = ((int) lineCount) - 1;

        File graphX = new File(
                "C:\\Users\\yjean\\Desktop\\sampleGraphs\\10-20.txt");
        Scanner graphXfile = new Scanner(graphX);
        //get the number of vertices
        int number = Integer.parseInt(graphXfile.nextLine());
        Comparator<Edge> order = new Comparison();
        PriorityQueue<Edge> q = new PriorityQueue<>(lines, order);
        NewKruskalsMSF graph = new NewKruskalsMSF();
        graph.makePriorityQ(q, graphXfile);
        graph.kruskal(q, number);

    }

}
