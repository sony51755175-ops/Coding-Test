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
		
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		List<int[]>graph[] = new ArrayList[n + 1];
		int dist[] = new int[n + 1];
		
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();
			dist[i] = INF;
		}

		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new int[] {to, cost});
			graph[to].add(new int[] {from, cost});
		}
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		pq.add(new int[] {1, 0});
		dist[1] = 0;
		
		while(!pq.isEmpty()) {
			int num[] = pq.poll();
			int v = num[0];
			int cost = num[1];
			
			if(dist[v] < cost) continue;
			
			for(int []next : graph[v]) {
				int nextv = next[0];
				int nextCost = next[1];
				
				int newCost = nextCost + cost;
				
				if(dist[nextv] > newCost) {
					dist[nextv] = newCost;
					pq.add(new int[] {nextv, newCost});
				}
			}
		}

		System.out.println(dist[n]);
	}
}
