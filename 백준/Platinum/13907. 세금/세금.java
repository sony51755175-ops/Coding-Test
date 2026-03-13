import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		
		int INF = Integer.MAX_VALUE;
		List<int[]> graph[] = new ArrayList[n + 1];
		int dist[][] = new int[n + 1][n + 1];
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();
			for(int j = 0; j < n + 1; j++) {
				dist[i][j] = INF;
			}
		}
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new int[] {cost, to});
			graph[to].add(new int[] {cost, from});
		}
		
		int ot[] = new int[k];
		for(int i = 0; i < k; i++) {
			ot[i] = Integer.parseInt(br.readLine());
		}

		
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		
		pq.add(new int[] {0, start, 0}); // cost, v, 간선 몇개?
		dist[start][0] = 0;
		
		while(!pq.isEmpty()) {
			int cur[] = pq.poll();
			int cost = cur[0];
			int v = cur[1];
			int node_count = cur[2];
			
			if(dist[v][node_count] < cost) continue;
			
			for(int[] next : graph[v]) {
				int nextCost = next[0];
				int nextV = next[1];
				
				int newCost = nextCost + cost;
				if(node_count + 1 >= n) continue;
				
				boolean flag = false;
				for (int i = 0; i < node_count + 1; i++) {
				    if (dist[nextV][i] <= newCost) {
				        flag = true;
				        break;
				    }
				}
				if (flag) continue;
				
				if(dist[nextV][node_count + 1] > newCost) {
					pq.add(new int[] {newCost, nextV, node_count + 1});
					dist[nextV][node_count + 1] = newCost;
				}
			}
		}
//		for(int i = 1; i < n + 1; i++) {
//			System.out.println(i);
//			for(int j = 0; j < n + 1; j++) {
////				if(dist[i][j] == INF) break;
//				System.out.print(dist[i][j] + " ");
//			}
//			System.out.println();
//		}
		int min_value = INF;
		for(int i = 1; i < n + 1; i++) {
			int num = dist[end][i];
			if(num!= INF) {
				if(num < min_value) {
					min_value = num;
				}
			}
		}
		System.out.println(min_value);
		int tax = 0;
		for(int i = 0; i < k; i++) {
			tax += ot[i];
			int result = 0;
			int max_value = INF;
			
			for(int j = 1; j < n + 1; j++) {
				if(dist[end][j] == INF) continue;
				result = j * tax + dist[end][j];
				
				if(result < max_value) {
					max_value = result;
				}
			}
			System.out.println(max_value);
		}
		
	}
}
