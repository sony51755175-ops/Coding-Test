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
		int e = Integer.parseInt(st.nextToken());
		int INF = Integer.MAX_VALUE;
		List<int[]> graph[] = new ArrayList[n + 1];
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();					
		}
		
		for(int i = 0 ; i < e; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new int[] {cost, to});
			graph[to].add(new int[] {cost, from});
		}
		
		st = new StringTokenizer(br.readLine());
		int v1 = Integer.parseInt(st.nextToken()); 
		int v2 = Integer.parseInt(st.nextToken());
		int first = 0;
		int second = 0;
		for(int i = 0; i < 3 ; i++) {
			int dist[] = new int[n + 1];
			for(int j = 1; j < n + 1; j++) {
				dist[j] = INF; 
			}
			PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
			if(i == 0) { // 1번에서 시작하는 전체 다익
				dist[1] = 0;
				pq.add(new int[] {0, 1});
			}
			else if(i == 1) {
				dist[v1] = 0;
				pq.add(new int[] {0, v1});
			}
			else if(i == 2) {
				dist[v2] = 0;
				pq.add(new int[] {0, v2});
			}
			
			
			
			
			
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
			
			if(i == 0) {
				first += dist[v1];
				second += dist[v2];
				if(dist[n] == INF) {
					System.out.println(-1);
					return;
				}
			}
			else if(i == 1) {
				first += dist[v2];
				second += dist[n];
			}
			else if(i == 2) {
				second += dist[v1];
				first += dist[n];
			}
		}
		int num = Math.min(first, second);
		System.out.println(num);
	}
}
