/* COP3503 - CS II
* Lab 01 - Prim’s algorithm
* Submitted by:
*     Priyansh Patel (Solo)
*/

import java.util.*;
import java.io.*;

// class to make graph and keep track of algorithm data
class Matrix
{
  static double MAX = Double.MAX_VALUE;       // +inf
  static int vertex;                          // holds max vertex
  int edges;                                  // holds max edges
  double matrix[][];                          // adjacency matrix

  // to track information for the algorithm
  int[] parent;
  int[] known;
  double[] cost;
  double[] weight;

  // constructor
  public Matrix(int v, int e)
  {
    vertex = v;
    edges = e;
    matrix = new double[v][v];
    parent = new int[v];
    known = new int[v];
    cost = new double[v];
    weight = new double[v];
  }

  // intialize before running the alogrithm
  static void intialize(int[] parent, int[] known, double[] cost, double[] weight)
  {
    for (int i = 0; i < vertex; i++)
    {
      parent[i] = 0;
      known[i] = 0;
      cost[i] = MAX;
      weight[i] = 0.0;
    }
  }
}

public class Lab01
{
  // prims algorithm
  public static Matrix prims_MST(Matrix graph)
  {
    int index;           // holds the best vertex

    // intialize the graph's tracking information
    graph.intialize(graph.parent, graph.known, graph.cost, graph.weight);

    // start from first vertex (index 0)
    graph.cost[0] = 0.0;
    graph.parent[0] = -1;

    // run the algorithm to make the mst
    for (int i = 0; i < graph.vertex; i++)
    {
      // find the best vertex
      index = min(graph);

      // mark the known vertex
      graph.known[index] = 1;

      // run through other vertices
      for (int j = 0; j < graph.vertex; j++)
      {
        // check edges from created adjacency matrix
        if (graph.matrix[index][j] > 0)
        {
          // check if adjacent vertex need to update cost
          if ((graph.matrix[index][j] < graph.cost[j]) && (graph.known[j] == 0))
          {
            // update parent
            graph.parent[j] = index;

            // update cost to represent correct weight
            graph.cost[j] = graph.matrix[index][j];

            // update weight (for printing)
            graph.weight[j] = graph.cost[j];
          }
        }
      }
    }
    // return graph for printing
    return graph;
  }

  // finds the closest vertex
  public static int min(Matrix graph)
  {
    // intialize variables
    double min = graph.MAX;
    int index = -1;

    for (int i = 0; i < graph.vertex; i++)
    {
      // find minimun weight with unknown vertices
      if ((graph.cost[i] < min) && (graph.known[i] == 0))
      {
        index = i;               // vertex of the shortest path
        min = graph.cost[i];    // update min to find smallest cost
      }
    }

    // return the closest vertex
    return index;
  }

  // main function
  public static void main(String[] args) throws Exception
  {
    Scanner scnr = new Scanner(new FileInputStream(args[0]));
    int v;                           // max vertices
    int e;                           // number of edges
    int x;                           // x vertex
    int y;                           // y vertex
    double weight;                   // weight between edges
    double total = 0.0;              // print total weight

    // read max vertices and num of edges in the input file
    v = scnr.nextInt();
    e = scnr.nextInt();

    // intialize graph
    Matrix graph = new Matrix(v, e);

    // make matrix from given input
    for (int i = 0; i < graph.edges; i++)
    {
      x = scnr.nextInt();
      y = scnr.nextInt();
      weight = scnr.nextDouble();
      graph.matrix[x][y] = weight;
      graph.matrix[y][x] = weight;
    }

    // run prims algorithm to create MST
    prims_MST(graph);

    // print MST and total weight
    for (int i = 1; i < graph.vertex; i++)
    {
      // parent already contains vertex 0
      System.out.print(graph.parent[i] + "-" + i + " ");
      System.out.printf("%.5f\n", graph.weight[i]);

      // add weight from array to total weight
      total = graph.weight[i] + total;
    }

    System.out.printf("%.5f\n", total);
  }
}
