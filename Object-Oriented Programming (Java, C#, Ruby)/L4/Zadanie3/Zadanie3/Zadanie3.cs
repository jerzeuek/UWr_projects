// Ksawery Plis
// Lista 4 Zadanie 3
// .NET SDK 8.0.100
// Sposób uruchamiania:
//  -wrzucam plik do pustego folderu
//  -w tym folderze w terminalu wywołuję funkcję "dotnet new console"
//  -usuwam plik Program.cs
//  -wywołuję funkcję "dotnet run {nazwapliku}.cs"

using System.Collections.Generic;
using System;

namespace Zadanie3
{
    interface IGraph
    {
        void AddVertex(string label);
        void AddEdge(string source, string destination);
        void RemoveEdge(string source, string destination);
        List<string> GetNeighbors(string vertex);
        int GetVertexCount();
        int GetEdgeCount();
    }

    class AdjacencyListGraph : IGraph
    {
        private Dictionary<string, List<string>> adjacencyList;

        public AdjacencyListGraph()
        {
            adjacencyList = new Dictionary<string, List<string>>();
        }

        public void AddVertex(string label)
        {
            if (!adjacencyList.ContainsKey(label))
                adjacencyList[label] = new List<string>();
        }

        public void AddEdge(string source, string destination)
        {
            if (!adjacencyList.ContainsKey(source) || !adjacencyList.ContainsKey(destination))
                throw new ArgumentException("Jeden z tych wierzchołków nie występuje w tym grafie!");

            adjacencyList[source].Add(destination);
            adjacencyList[destination].Add(source);
        }

        public void RemoveEdge(string source, string destination)
        {
            if (!adjacencyList.ContainsKey(source) || !adjacencyList.ContainsKey(destination))
                throw new ArgumentException("Jeden z tych wierzchołków nie występuje w tym grafie!");

            adjacencyList[source].Remove(destination);
            adjacencyList[destination].Remove(source);
        }

        public List<string> GetNeighbors(string vertex)
        {
            if (!adjacencyList.ContainsKey(vertex))
                throw new ArgumentException("Nie ma wierzchołka " + vertex + " w tym grafie!");

            return adjacencyList[vertex];
        }

        public int GetVertexCount()
        {
            return adjacencyList.Count;
        }

        public int GetEdgeCount()
        {
            int count = 0;
            foreach (var neighbors in adjacencyList.Values)
            {
                count += neighbors.Count;
            }
            return count / 2;
        }
    }

    class AdjacencyMatrixGraph : IGraph
    {
        private Dictionary<string, int> indexes;
        private bool[,] adjacencyMatrix;

        public AdjacencyMatrixGraph()
        {
            indexes = new Dictionary<string, int>();
            adjacencyMatrix = new bool[0, 0];
        }

        public void AddVertex(string label)
        {
            if (indexes.ContainsKey(label))
                return;

            int index = indexes.Count;
            indexes[label] = index;

            bool[,] newMatrix = new bool[index + 1, index + 1];
            for (int i = 0; i < adjacencyMatrix.GetLength(0); i++)
            {
                for (int j = 0; j < adjacencyMatrix.GetLength(1); j++)
                {
                    newMatrix[i, j] = adjacencyMatrix[i, j];
                }
            }
            adjacencyMatrix = newMatrix;
        }

        public void AddEdge(string source, string destination)
        {
            if (!indexes.ContainsKey(source) || !indexes.ContainsKey(destination))
                throw new ArgumentException("Jeden z tych wierzchołków nie występuje w tym grafie!");

            int sourceIndex = indexes[source];
            int destinationIndex = indexes[destination];

            adjacencyMatrix[sourceIndex, destinationIndex] = true;
            adjacencyMatrix[destinationIndex, sourceIndex] = true;
        }

        public void RemoveEdge(string source, string destination)
        {
            if (!indexes.ContainsKey(source) || !indexes.ContainsKey(destination))
                throw new ArgumentException("Jeden z tych wierzchołków nie występuje w tym grafie!");

            int sourceIndex = indexes[source];
            int destinationIndex = indexes[destination];

            adjacencyMatrix[sourceIndex, destinationIndex] = false;
            adjacencyMatrix[destinationIndex, sourceIndex] = false;
        }

        public List<string> GetNeighbors(string vertex)
        {
            if (!indexes.ContainsKey(vertex))
                throw new ArgumentException("Nie ma wierzchołka " + vertex + " w tym grafie!");

            int vertexIndex = indexes[vertex];
            List<string> neighbors = new List<string>();

            for (int i = 0; i < adjacencyMatrix.GetLength(1); i++)
            {
                if (adjacencyMatrix[vertexIndex, i])
                {
                    foreach (var kvp in indexes)
                    {
                        if (kvp.Value == i)
                        {
                            neighbors.Add(kvp.Key);
                            break;
                        }
                    }
                }
            }

            return neighbors;
        }

        public int GetVertexCount()
        {
            return indexes.Count;
        }

        public int GetEdgeCount()
        {
            int count = 0;
            for (int i = 0; i < adjacencyMatrix.GetLength(0); i++)
            {
                for (int j = i + 1; j < adjacencyMatrix.GetLength(1); j++)
                {
                    if (adjacencyMatrix[i, j])
                        count++;
                }
            }
            return count;
        }
    }

    class GraphOperations
    {
        private Random random;

        public GraphOperations()
        {
            random = new Random();
        }

        public void CreateRandom(IGraph g, int vert, int edges)
        {
            for (int i = 0; i < vert; i++)
            {
                g.AddVertex(i.ToString());
            }

            int addedEdges = 0;
            while (addedEdges < edges)
            {
                int source = random.Next(vert);
                int destination = random.Next(vert);

                if (source != destination)
                {
                    string sourceVertex = source.ToString();
                    string destinationVertex = destination.ToString();

                    if (!g.GetNeighbors(sourceVertex).Contains(destinationVertex))
                    {
                        g.AddEdge(sourceVertex, destinationVertex);
                        addedEdges++;
                    }
                }
            }
        }

        public List<string> shortestPath(IGraph g, string from, string to)
        {
            Queue<string> q = new Queue<string>();
            HashSet<string> vis = new HashSet<string>();
            Dictionary<string, string> father = new Dictionary<string, string>();

            q.Enqueue(from);

            while (q.Count != 0)
            {
                string now = q.Dequeue();

                vis.Add(now);
                if (now.Equals(to)) break;

                foreach (string v in g.GetNeighbors(now))
                {
                    if (!vis.Contains(v))
                    {
                        try { father.Add(v, now); }
                        catch (Exception) { };
                        q.Enqueue(v);
                    }
                }
            }
            List<string> res = new List<string>();
            for (string i = to; father.ContainsKey(i); i = father[i])
                res.Add(i);

            if (res.Count > 0 || from.Equals(to)) res.Add(from);

            res.Reverse();
            return res;
        }
    }

    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Stwórzmy dwa grafy, jeden reprezentowany macierzą sąsiedztwa, a drugi listami sąsiedztwa:");
            IGraph graph1 = new AdjacencyListGraph();
            IGraph graph2 = new AdjacencyMatrixGraph();

            Console.WriteLine("Sprawdźmy, ile mają wierzchołków i krawędzi:");
            Console.WriteLine("Graf MS ma " + graph1.GetVertexCount().ToString() + " wierzchołków i " + graph1.GetEdgeCount() + " krawędzi");
            Console.WriteLine("Graf LS ma " + graph2.GetVertexCount().ToString() + " wierzchołków i " + graph2.GetEdgeCount() + " krawędzi");

            Console.WriteLine("Dodajmy teraz do grafu LS losowy graf zawierający 5 wierzchołków i 8 krawędzi.");
            GraphOperations graphOperations = new GraphOperations();
            graphOperations.CreateRandom(graph1, 5, 8);

            Console.WriteLine("Teraz graf LS ma " + graph1.GetVertexCount().ToString() + " wierzchołków i " + graph1.GetEdgeCount() + " krawędzi");

            Console.WriteLine("Wypiszmy teraz wszystkich sąsiadów dla każdego wierzchoka:");
            for (int i = 0; i < 5; i++)
            {
                Console.WriteLine("Sąsiedzi wierzchołka " + i.ToString() + ":");
                List<string> neighbours = graph1.GetNeighbors(i.ToString());
                foreach (string neighbor in neighbours)
                {
                    Console.Write(neighbor + " ");
                }
                Console.WriteLine();
            }

            Console.WriteLine("Zobaczmy jaka jest najkrótsza ścieżka między wierzchołkami 0 i 4:");
            List<string> shortestPath1 = graphOperations.shortestPath(graph1, "0", "4");
            foreach (var vertex in shortestPath1)
            {
                Console.Write(vertex + " ");
            }
            Console.WriteLine();

            Console.WriteLine("Teraz w grafie MS zrobię dodam ręcznie 5 wierzchołków i połączę je w taki sposób: A-B-C-D-E");
            graph2.AddVertex("A");
            graph2.AddVertex("B");
            graph2.AddVertex("C");
            graph2.AddVertex("D");
            graph2.AddVertex("E");

            graph2.AddEdge("A", "B");
            graph2.AddEdge("B", "C");
            graph2.AddEdge("C", "D");
            graph2.AddEdge("D", "E");

            Console.WriteLine("Teraz graf MS ma " + graph2.GetVertexCount().ToString() + " wierzchołków i " + graph2.GetEdgeCount() + " krawędzi");

            Console.WriteLine("Zobaczmy jaka jest najkrótsza ścieżka między wierzchołkami A i E:");
            List<string> shortestPath2 = graphOperations.shortestPath(graph2, "A", "E");
            foreach (var vertex in shortestPath2)
            {
                Console.Write(vertex + " ");
            }
            Console.WriteLine();

            Console.WriteLine("Usunę teraz jedną z krawędzi.");
            graph2.RemoveEdge("C", "D");
            Console.WriteLine("Teraz graf MS ma " + graph2.GetVertexCount().ToString() + " wierzchołków i " + graph2.GetEdgeCount() + " krawędzi");

            Console.WriteLine("Teraz oczywiście nie ma żadnej ścieżki między A i E, ale sprawdźmy to:");
            shortestPath2 = graphOperations.shortestPath(graph2, "A", "E");
            foreach (var vertex in shortestPath2)
            {
                Console.Write(vertex + " ");
            }
            Console.WriteLine();

            Console.WriteLine("Sprawdźmy jeszcze co się stanie jeśli będę chciał dodać albo usunąć krawędź dla jednej z krawędzi która nie instnieje lub wypisać sąsiadów wierzchołka który nie istnieje:");
            Console.WriteLine("Dodawanie krawędzi:");
            try
            {
                graph1.AddEdge("A", "Z");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            Console.WriteLine("Usuwanie krawędzi:");
            try
            {
                graph1.RemoveEdge("A", "Z");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }

            Console.WriteLine("Wypisywanie sąsiadów:");
            try
            {
                graph1.GetNeighbors("Z");
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
            }
        }
    }
}