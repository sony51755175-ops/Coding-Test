import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static final long INF = Long.MAX_VALUE;
	public static void main(String[] args) throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		List<long[]> graph[] = new ArrayList[n];
		long dist[] = new long[n];
		
		for(int i = 0; i < n; i++) {
			graph[i] = new ArrayList<>();
			dist[i] = INF;
		}
		
		boolean visited[] = new boolean[n];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < n; i++) {
			int num = Integer.parseInt(st.nextToken());
			if(num == 0) {
				visited[i] = false;
			}
			else {
				visited[i] = true;
			}
		}
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			long cost = Long.parseLong(st.nextToken());
			
			graph[from].add(new long[] {cost, to});
			graph[to].add(new long[] {cost ,from});
		}
		
		PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
		pq.add(new long[] {0, 0});
		dist[0] = 0;
		
		while(!pq.isEmpty()) {
			long cur[] = pq.poll();
			long cost = cur[0];
			int v = (int)cur[1];
			
			if(dist[v] < cost) continue;
			
			for(long[] next : graph[v]) {
				
				long nextCost = next[0];
				int nextV = (int) next[1];
				long newCost = cost + nextCost;
				if(visited[nextV] == true && nextV != n - 1) continue;
				if(dist[nextV] > newCost) {
					dist[nextV] = newCost;
					pq.add(new long[] {newCost, nextV});
				}
			}
		}
//		for(int i = 1; i < n; i++) {
//			System.out.println(dist[i]);
//		}
		if(dist[n - 1] == Long.MAX_VALUE) {
			System.out.println(-1);
		}
		else {
			System.out.println(dist[n - 1]);
		}

		
	}
}
