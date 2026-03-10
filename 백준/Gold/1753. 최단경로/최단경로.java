
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static int INF = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		int start = Integer.parseInt(br.readLine());
		
		List<int[]> graph[] = new ArrayList[V + 1];
		int dist[] = new int[V + 1];
		
		for(int i = 1; i < V + 1; i++) {
			graph[i] = new ArrayList<>();
			dist[i] = INF;
		}
		
		for(int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new int[] {end, cost});
		}
		
		dist[start] = 0;
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		pq.add(new int[] {start, 0});
		
		while(!pq.isEmpty()) {
			int cur[] = pq.poll();
			int v = cur[0];
			int cost = cur[1];
			
			if(dist[v] < cost) continue;
			
			for(int[] next : graph[v]) {
				int nextV = next[0];
				int nextCost = next[1];
				
				int newCost = nextCost + cost;
				
				if(dist[nextV] > newCost) {
					dist[nextV] = newCost;
					pq.add(new int[] {nextV, newCost});
				}
			}
		}
		
		for(int i = 1; i < V + 1; i++) {
			int num = dist[i];
			if(num == INF) {
				System.out.println("INF");
			}
			else {
				System.out.println(num);
			}
		}
		
	}
}
