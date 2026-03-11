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
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int INF = Integer.MAX_VALUE;
		List<int[]> []graph = new ArrayList[n + 1];
		int[] prev = new int[n + 1];
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
			
			graph[from].add(new int[] {cost, to});
			graph[to].add(new int[] {cost, from});
		}
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		
		dist[1] = 0;
		pq.add(new int[] {0, 1});
		
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
					prev[nextV] = v;   // 최단경로 바로 이전 정점 저장
					pq.add(new int[] {newCost, nextV});
				}
			}
		}
		int target_num = dist[n];
		
		List<int[]> list = new ArrayList<>();
		int start = n;
					
		while(true) {
			int num = prev[start];

			list.add(new int[] {num, start});
			if(num == 1) break;
			start = num;
		}
		
//		for(int[] l : list) {
//			System.out.println(l[0] + " " + l[1]);
//		}
		int max_diff = -1;
		int result = 0;
		for(int[] l : list) {
			int s = l[0];
			int e = l[1];
			
			dist = new int[n + 1];
			pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
			for(int i = 1; i < n + 1; i++) {
				dist[i] = INF;
			}
			dist[1] = 0;
			pq.add(new int[] {0, 1});
			while(!pq.isEmpty()) {
				int cur[] = pq.poll();
				int cost = cur[0];
				int v = cur[1];
				
				if (cost > dist[v]) continue;
				
				for(int[] next : graph[v]) {
					
					int nextCost = next[0];
					int nextV = next[1];
					int newCost = cost + nextCost;
					if(v == s && nextV == e) continue;
					if(dist[nextV] > newCost) {
						dist[nextV] = newCost;
						prev[nextV] = v;   // 최단경로 바로 이전 정점 저장
						pq.add(new int[] {newCost, nextV});
					}
				}
			}
			
			int ot = dist[n] - target_num;
			if(max_diff < ot) {
				
				max_diff = ot;
				result = dist[n];
			}
		}
		System.out.println(result);
	}
}
