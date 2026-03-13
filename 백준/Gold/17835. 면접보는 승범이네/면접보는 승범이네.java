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
		long INF = Long.MAX_VALUE / 4;
		
		List<int[]> graph[] = new ArrayList[n + 1];
		long dist[] = new long[n + 1];
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();
			dist[i] = INF;
		}
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			graph[to].add(new int[] {cost, from});
		}
		
		int dest[] = new int[k];
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < k; i++) {
			dest[i] = Integer.parseInt(st.nextToken());
		}
		
		PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
		for(int i = 0; i < k; i++) {
			pq.add(new long[] {0, dest[i]});
			dist[dest[i]] = 0;
		}
		
		while(!pq.isEmpty()) {
			long cur[] = pq.poll();
			long cost = cur[0];
			int v = (int)cur[1];
			
			if(dist[v] < cost) continue;
			
			for(int next[] : graph[v]) {
				long nextCost = next[0]; 
				int nextV = next[1];
				
				long newCost = cost + nextCost;
				if(dist[nextV] > newCost) {
					dist[nextV] = newCost;
					pq.add(new long[] {newCost, nextV});
				}
			}
		}
		long result = 0;
		int num = -1;
		for(int i = 1; i < n + 1; i++) {
			if(result < dist[i]) {
				result = dist[i];
				num = i;
			}
		}
		System.out.println(num);
		System.out.println(result);
	}
}
