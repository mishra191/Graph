/**
 * 
 */
package com.test.graphs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Anurag
 *
 */
public class UnionByRank {

	private Map<Long, Node> map = new HashMap<>();

	class Node {
		long data;
		Node parent;
		int rank;
	}

	/**
	 * Create a set with only one element.
	 */
	public void makeSet(long data) {
		Node node = new Node();
		node.data = data;
		node.parent = node;
		node.rank = 0;
		map.put(data, node);
	}

	/**
	 * Combines two sets together to one. Does union by rank
	 *
	 * @return true if data1 and data2 are in different set before union else
	 *         false.
	 */
	public boolean union(long data1, long data2) {
		// both will have parent for sure---
		Node node1 = map.get(data1);
		Node node2 = map.get(data2);

		// both parents will be returned---
		Node parent1 = findSet(node1);
		Node parent2 = findSet(node2);

		// if they are part of same set do nothing
		if (parent1.data == parent2.data) {
			return false;
		}

		// else whoever's rank is higher becomes parent of other
		if (parent1.rank >= parent2.rank) {
			// increment rank only if both sets have same rank
			parent1.rank = (parent1.rank == parent2.rank) ? parent1.rank + 1 : parent1.rank;
			parent2.parent = parent1;
		} else {
			parent1.parent = parent2;
		}
		return true;
	}

	/**
	 * Finds the representative of this set
	 */
	public long findSet(long data) {
		return findSet(map.get(data)).data;
	}

	/**
	 * Find the representative recursively and does path compression as well.
	 */
	private Node findSet(Node node) {
		Node parent = node.parent;
		if (parent == node) {
			return parent;
		}
		node.parent = findSet(node.parent);
		return node.parent;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UnionByRank solution = new UnionByRank();
		File file = new File("D://input.txt");
		Scanner in = null;
		try {
			in = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String query = in.nextLine();

		String[] subQuery = query.split(" ");

		int N = Integer.parseInt(subQuery[0]);
		int P = Integer.parseInt(subQuery[1]);
		if (N >= 1 && N <= Math.pow(10, 5)) {

			for (int count = 0; count < N; count++) {
				solution.makeSet(count);
			}

			if (P >= 1 && P <= Math.pow(10, 4)) {

				for (int count = 0; count < P; count++) {

					String set = in.nextLine();

					String[] subSet = set.split(" ");

					int A = Integer.parseInt(subSet[0]);

					int B = Integer.parseInt(subSet[1]);

					if (A >= 0 && A <= N - 1 && B >= 0 && B <= N - 1) {

						solution.union(A, B);
					}

				}

			}

			// output the sum of different pairs possible
			solution.outputSet(N);

		}

	}

	private void outputSet(int n) {
		Map<Long, Integer> sum = new HashMap<Long, Integer>();

		for (int count = 0; count < n; count++) {			
			Node node = map.get(Long.valueOf(count));
			Node parent = findSet(node);

			if (sum.get(parent.data) != null) {
				int number = sum.get(parent.data);
				sum.put(parent.data, ++number);
			} else {
				sum.put(parent.data, 1);
			}
		}

		List<Integer> list = new ArrayList<Integer>(sum.values());

		
		
		long output = 0;
		long result = 0;
		for(int size : list)
		 {
		   result += output*size;
		   output += size;    
		 }   
		System.out.println(result);



	}
}