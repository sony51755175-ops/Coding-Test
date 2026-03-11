
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true) {

			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			if(n == 0 && m == 0) {
				break;
			}
			int INF = Integer.MAX_VALUE;
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			List<int[]> graph[] = new ArrayList[n];
			int dist[] = new int[n];
			for(int i = 0; i < n; i++) {
				graph[i] = new ArrayList<>();
				dist[i] = INF;
			}
			
			
			for(int i = 0; i < m; i++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				int cost = Integer.parseInt(st.nextToken());
				
				graph[from].add(new int[] {cost, to});
			}
			
			PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
			List<Integer>[] parent = new ArrayList[n];
			for (int i = 0; i < n; i++) {
			    parent[i] = new ArrayList<>();
			}
			
			dist[s] = 0;
			pq.add(new int[] {0, s});
			
			while(!pq.isEmpty()) {
				int cur[] = pq.poll();
				int cost = cur[0];
				int v = cur[1];
				
				if (cost > dist[v]) continue;
				
				for(int[] next : graph[v]) {
					int nextCost = next[0];
					int nextV = next[1];
					int newCost = cost + nextCost;
					
					if(dist[nextV] > newCost) {
						dist[nextV] = newCost;
						parent[nextV].clear();
						parent[nextV].add(v);
						pq.add(new int[] {newCost, nextV});
					}
					else if(dist[nextV] == newCost) {
						parent[nextV].add(v);
					}
				}
			}
			
			Deque<Integer> queue = new ArrayDeque<>();
			boolean[] visited = new boolean[n];

			queue.add(d);
			
			while (!queue.isEmpty()) {
			    int num = queue.poll();

			    if (visited[num]) continue;
			    visited[num] = true;

			    for (int p : parent[num]) {
			        for (int i = 0; i < graph[p].size(); i++) {
			            int[] edge = graph[p].get(i);
			            int to = edge[1];
			            int cost = edge[0];

			            if (to != num) continue; // p -> num 간선 확인
			            if (dist[p] == INF) continue; // p까지 도달 가능한지 확인
			            if (dist[p] + cost != dist[num]) continue; // 실제 최소 간선인지?
			            
			            edge[0] = INF;
			        }
			        queue.add(p);
			    }
			}
			
			int dist2[] = new int[n];
			for(int i = 0; i < n; i++) {
				dist2[i] = INF;
			}
			PriorityQueue<int[]> pq2 = new PriorityQueue<>((a, b) -> a[0] - b[0]);
			
			dist2[s] = 0;
			pq2.add(new int[] {0, s});
			
			while(!pq2.isEmpty()) {
				int cur[] = pq2.poll();
				int cost = cur[0];
				int v = cur[1];

				if (cost > dist2[v]) continue;
				
				for(int[] next : graph[v]) {
					int nextCost = next[0];
					if (nextCost == INF) continue;
					int nextV = next[1];
					int newCost = cost + nextCost;
					
					if(dist2[nextV] > newCost) {
						dist2[nextV] = newCost;
						pq2.add(new int[] {newCost, nextV});
					}
				}
			}
            System.out.println(dist2[d] == INF ? -1 : dist2[d]);
		}
	}
}