
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
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
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
			
			graph[from].add(new int[] {to, 1});
			graph[to].add(new int[] {from, 1});
		}
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		dist[start] = 0;
		pq.add(new int[] {start, 0});
		
		while(!pq.isEmpty()) {
			int cur[] = pq.poll();
			int v = cur[0];
			int cost = cur[1];
			
			if(cost > dist[v]) continue;
			
			for(int[] next : graph[v]) {
				int nextV = next[0];
				int nextCost = next[1];
				
				int newCost = cost + nextCost;
				
				if(dist[nextV] > newCost) {
					dist[nextV] = newCost;
					pq.add(new int[] {nextV, newCost});
				}
			}
		}
		
		if(dist[end] == INF) {
			System.out.println(-1);
		}
		else {
			System.out.println(dist[end]);
		}

	}
}
