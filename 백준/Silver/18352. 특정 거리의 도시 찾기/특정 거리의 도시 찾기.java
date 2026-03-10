
import java.io.*;

import java.util.*;


public class Main {
    static final int INF = Integer.MAX_VALUE;
    static int dist[];
    static List<int[]>[] graph;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int x = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[n + 1];
		dist = new int[n + 1];
		
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();
			dist[i] = INF;
		}
		
		for(int i = 0 ; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from  = Integer.parseInt(st.nextToken());
			int to  = Integer.parseInt(st.nextToken());
			graph[from].add(new int[] {to, 1});
		}
		
		dij(x);
		int count = 0;
		boolean flag = false;
		for(int i = 1; i < n + 1; i++) {
			if(dist[i] == k) {
				System.out.println(i);
				flag = true;
			}
		}
		if(!flag) {
			System.out.println(-1);
		}
	}
	static void dij(int start) {
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		pq.offer(new int[] {start, 0});
		dist[start] = 0;
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int v = cur[0];
			int cost = cur[1];
			
			if(dist[v] < cost) continue;
			
			for(int[] next : graph[v]) {
				int nextV = next[0];
				int nextCost = next[1];
				
				int newCost = nextCost + cost;
				
				if(dist[nextV] > newCost) {
					dist[nextV] = newCost;
					pq.offer(new int[] {nextV, newCost});
				}
			}
		}
		
	}
}
