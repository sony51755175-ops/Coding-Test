
import java.io.*;
import java.util.*;

public class Main {
	static int n, D;
    static final int INF = Integer.MAX_VALUE;
	static List<int[]> graph[];
	static int dist[];
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[D + 1];
		dist = new int[D + 1];
		
		for(int i = 0;  i < D + 1; i++) {
			graph[i] = new ArrayList<>();
			dist[i] = INF;
		}
		
        for (int i = 0; i < D; i++) {
            graph[i].add(new int[] {i + 1, 1});
        }
        
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int dist = Integer.parseInt(st.nextToken());
			
			if(end > D) continue;
			if(end - start <= dist) continue;
			graph[start].add(new int[] {end, dist});
		}
		
		dij(0);
		System.out.println(dist[D]);
	}
	
	static void dij(int start) {
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
		dist[start] = 0;
		pq.offer(new int[] {start, 0});
		
		while(!pq.isEmpty()) {
			int cur[] = pq.poll();
			int v = cur[0];
			int cost = cur[1];
			
			if(cost > dist[v]) continue;
			
            for (int[] next : graph[v]) {
                int nextv = next[0];
                int nextCost = next[1];
                int newCost = nextCost + cost;
                
                if(dist[nextv] > newCost) {
                	dist[nextv] = newCost;
                	pq.add(new int[] {nextv, newCost});
                }
            }
		}
	}
}
