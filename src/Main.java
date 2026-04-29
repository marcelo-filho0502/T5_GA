public class Main {
    public static void main(String[] args) {
        String path = (args.length < 1) ? "Dados/brasil.txt" : args[0];

        In in = new In(path);
        Graph graph = new Graph(in);
        GraphColoringDSatur dsatur = new GraphColoringDSatur(graph);

        StdOut.println("=== Lista de Adjacencia (Brasil) ===");
        StdOut.println(graph.toString());

        dsatur.color();

        StdOut.println("Ordem de coloracao:");
        int[] order = dsatur.getColoringOrder();
        for (int v : order) {
            StdOut.print(dsatur.getLabel(v) + " ");
        }

        StdOut.println("\n\nResultado da Coloracao:");
        for (int v = 0; v < graph.V(); v++) {
            StdOut.printf("%s (Vertice %d): Cor %d\n", dsatur.getLabel(v), v, dsatur.getColor(v));
        }

        StdOut.println("\nTotal de cores: " + dsatur.getColorCount());
        StdOut.println("Coloracao valida? " + (dsatur.isValidColoring() ? "Sim" : "Nao"));
    }
}