
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
		
		List<int[]> graph[] = new ArrayList[n + 1];
		int dist[] = new int[n + 1];
		
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();
			dist[i] = Integer.MAX_VALUE;
		}
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new int[] {cost, to});
			graph[to].add(new int[] {cost ,from});
		}
		
		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		pq.add(new int[] {0, start});
		
		dist[start] = 0;
		while(!pq.isEmpty()) {
			int cur[] = pq.poll();
			int cost = cur[0];
			int v = cur[1];
			
			if(dist[v] < cost) continue;
			
			for(int[] next : graph[v]) {
				int nextCost = next[0];
				int nextV = next[1];
				int newCost = cost + nextCost;
				
				if(dist[nextV] > newCost) {
					dist[nextV] = newCost;
					pq.add(new int[] {newCost, nextV});
				}
			}
		}
//		for(int i = 1; i < n; i++) {
//			System.out.println(dist[i]);
//		}
		System.out.println(dist[end]);
		
	}
}
