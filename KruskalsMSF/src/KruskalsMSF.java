import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

public class KruskalsMSF {

    /**
     * Edge class.
     *
     * @author Xingyue Zhao
     *
     */
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    static class Graph {
        int vertices;
        ArrayList<Edge> edges = new ArrayList<>();

        Graph(int vertices) {
            this.vertices = vertices;
        }

        public void addE(int source, int destination, int weight) {
            Edge temp = new Edge(source, destination, weight);
            this.edges.add(temp);
        }

        public void kruskalMST() {
            PriorityQueue<Edge> pq = new PriorityQueue<>(this.edges.size(),
                    Comparator.comparingInt(o -> o.weight));
            for (int i = 0; i < this.edges.size(); i++) {
                Edge temp = this.edges.get(i);
                pq.add(temp);
            }

            int[] parent = this.makeSet(this.vertices);

            ArrayList<Edge> mst = new ArrayList<>();

            int temp = 0;
            while (temp < this.vertices - 1) {
                Edge e = pq.remove();
                if (this.find(parent, e.source) != this.find(parent,
                        e.destination)) {
                    mst.add(e);
                    temp++;
                    this.union(parent, this.find(parent, e.source),
                            this.find(parent, e.destination));
                }
            }

            for (int i = 0; i < mst.size(); i++) {
                Edge e = mst.get(i);
                System.out.println(
                        e.source + "," + e.destination + ":" + e.weight);
            }
        }

        public int[] makeSet(int parent) {
            int[] temp = new int[parent];
            for (int i = 0; i < temp.length; i++) {
                temp[i] = i;
            }
            return temp;
        }

        public int find(int[] parent, int vertex) {
            int temp = vertex;
            if (parent[vertex] != vertex) {
                temp = this.find(parent, parent[vertex]);
            }
            return temp;
        }

        public void union(int[] parent, int x, int y) {
            parent[this.find(parent, y)] = this.find(parent, x);
        }

    }

    public static void main(String[] args) throws IOException {
        File graphX = new File(
                "C:\\Users\\yjean\\Desktop\\sampleGraphs\\10-20.txt");
        Scanner graphXfile = new Scanner(graphX);
        int number = Integer.parseInt(graphXfile.nextLine());
        Graph graph = new Graph(number);
        while (graphXfile.hasNextLine()) {
            String temp = graphXfile.nextLine();
            int source = Integer.parseInt(temp.substring(0, temp.indexOf(',')));
            int destination = Integer.parseInt(
                    temp.substring(temp.indexOf(',') + 1, temp.indexOf(':')));
            int weight = Integer
                    .parseInt(temp.substring(temp.indexOf(':') + 1));
            graph.addE(source, destination, weight);
        }
        graph.kruskalMST();
    }

}
