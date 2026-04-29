import java.util.*;

public class GraphColoringDSatur {
    private final Graph graph;
    private int[] color;
    private int[] coloringOrder;
    private int numColors;
    private boolean isValid;

    // Siglas dos estados para o getLabel
    private static final String[] ESTADOS = {
            "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA",
            "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN",
            "RO", "RR", "RS", "SC", "SE", "SP", "TO"
    };

    public GraphColoringDSatur(Graph graph) {
        if (graph == null) {
            throw new IllegalArgumentException("graph nao pode ser nulo");
        }
        this.graph = graph;
        this.color = new int[graph.V()];
        Arrays.fill(this.color, -1);
        this.coloringOrder = new int[graph.V()];
        this.numColors = 0;
    }

    public Graph getGraph() {
        return graph;
    }

    public void color() {
        int V = graph.V();
        List<Set<Integer>> adjColors = new ArrayList<>();
        for (int i = 0; i < V; i++) adjColors.add(new HashSet<>());

        boolean[] isColored = new boolean[V];

        for (int step = 0; step < V; step++) {
            int bestV = -1;
            int maxDS = -1;
            int maxDeg = -1;

            for (int v = 0; v < V; v++) {
                if (!isColored[v]) {
                    int ds = adjColors.get(v).size();
                    int deg = graph.degree(v);

                    if (ds > maxDS || (ds == maxDS && deg > maxDeg)) {
                        bestV = v;
                        maxDS = ds;
                        maxDeg = deg;
                    }
                }
            }

            boolean[] usedInAdj = new boolean[V + 1];
            for (int w : graph.adj(bestV)) {
                if (isColored[w]) usedInAdj[color[w]] = true;
            }

            int c = 0;
            while (usedInAdj[c]) c++;

            color[bestV] = c;
            isColored[bestV] = true;
            coloringOrder[step] = bestV;
            numColors = Math.max(numColors, c + 1);

            for (int w : graph.adj(bestV)) {
                if (!isColored[w]) adjColors.get(w).add(c);
            }
        }
        validate();
    }

    private void validate() {
        isValid = true;
        for (int v = 0; v < graph.V(); v++) {
            for (int w : graph.adj(v)) {
                if (color[v] != -1 && color[v] == color[w]) {
                    isValid = false;
                    return;
                }
            }
        }
    }

    public int getColor(int vertex) {
        return color[vertex];
    }

    public int getColorCount() {
        return numColors;
    }

    public int[] getColoringOrder() {
        return coloringOrder;
    }

    public boolean isValidColoring() {
        return isValid;
    }

    public String getLabel(int vertex) {
        if (vertex >= 0 && vertex < ESTADOS.length) return ESTADOS[vertex];
        return String.valueOf(vertex);
    }
}