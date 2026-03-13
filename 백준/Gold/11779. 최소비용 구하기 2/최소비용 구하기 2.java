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
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		

		int INF = Integer.MAX_VALUE;
		List<int[]> graph[] = new ArrayList[n + 1];
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();					
		}
		
		for(int i = 0 ; i < m; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new int[] {cost, to});
		}
		StringTokenizer st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken()); 
		int end = Integer.parseInt(st.nextToken());
		int dist[] = new int[n + 1];
		int prev[] = new int[n + 1];
		
		for(int j = 1; j < n + 1; j++) {
			dist[j] = INF;
			prev[j] = j;
		}
		dist[start] = 0; 
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		pq.add(new int[] {0, start});
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
					prev[nextV] = v;
					dist[nextV] = newCost;
					pq.add(new int[] {newCost, nextV});
				}
			}
		}
		List<Integer> list = new ArrayList<>();
		System.out.println(dist[end]);
		list.add(end);
		while(true) {
			int num = prev[end];
			
			list.add(num);
			if(num == start) {
				break;
			}
			end = num;
		}
		System.out.println(list.size());
		for(int i = list.size() - 1; i >= 0; i--) {
			System.out.print(list.get(i) + " ");
		}
	}
}
