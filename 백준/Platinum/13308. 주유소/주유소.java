import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		int[] oil = new int[n + 1];
		st = new StringTokenizer(br.readLine());
		
		for(int i = 1; i <= n; i++) {
			oil[i] = Integer.parseInt(st.nextToken());
		}
		
		List<int[]>[] graph = new ArrayList[n + 1];
		long[][] dist = new long[n + 1][2501];
		long INF = Long.MAX_VALUE / 4;
		
		for(int i = 1; i <= n; i++) {
			graph[i] = new ArrayList<>();
			Arrays.fill(dist[i], INF);
		}
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new int[] {cost, to});
			graph[to].add(new int[] {cost, from});
		}
		
		PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
		dist[1][oil[1]] = 0;
		pq.add(new long[] {0, 1, oil[1]}); // 현재까지 기름값, Vertex, 지금까지 만난 기름 최소값
		
		while(!pq.isEmpty()) {
			long cur[] = pq.poll();
			long cost = cur[0];
			long v = (int)cur[1];
			long minoil = cur[2];
			
			if(dist[(int)v][(int)minoil] < cost) continue;
			
			for(int[] next : graph[(int)v]) {
				int len = next[0];
				int nextV = next[1];
				
				long newCost = cost + (long)len * minoil;
				int nextoil = (int) Math.min(minoil, oil[nextV]);
				if(dist[nextV][(int)nextoil] > newCost) {
					dist[nextV][(int)nextoil] = newCost;
					pq.add(new long[] {newCost, nextV, nextoil});
				}
			}
		}
		long ans = INF;
		for(int i = 1; i <= 2500; i++) {
			ans = Math.min(ans, dist[n][i]);
		}
		
		System.out.println(ans);
	}
}