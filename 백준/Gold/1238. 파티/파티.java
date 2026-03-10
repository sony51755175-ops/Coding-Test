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
		int x = Integer.parseInt(st.nextToken());
		int INF = Integer.MAX_VALUE;
		// x번에서 정방향 다익스트라 -> 돌아가는 것
		List<int[]> graph[] = new ArrayList[n + 1];
		List<int[]> graph_go[] = new ArrayList[n + 1];
		int back[] = new int[n + 1];
		int go[] = new int[n + 1];
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();
			graph_go[i] = new ArrayList<>();
			back[i] = INF;
			go[i] = INF;
		}
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new int[] {cost, to});
			graph_go[to].add(new int[] {cost, from});
		}
		
		PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		
		back[x] = 0;
		pq.add(new int[] {0, x});
		
		while(!pq.isEmpty()) {
			int cur[] = pq.poll();
			int cost = cur[0];
			int v = cur[1];
			
			if(back[v] < cost) continue;
			
			for(int next[] : graph[v]) {
				int nextCost = next[0];
				int nextV = next[1];
				
				int newCost = cost + nextCost;
				if(back[nextV] > newCost) {
					back[nextV] = newCost;
					pq.add(new int[] {newCost, nextV});
				}
			}
		}

		PriorityQueue<int[]> pq2 = new PriorityQueue<>((a, b) -> a[0] - b[0]);
		
		go[x] = 0;
		pq2.add(new int[] {0, x});
		
		while(!pq2.isEmpty()) {
			int cur[] = pq2.poll();
			int cost = cur[0];
			int v = cur[1];
			
			if(go[v] < cost) continue;
			
			for(int next[] : graph_go[v]) {
				int nextCost = next[0];
				int nextV = next[1];
				
				int newCost = cost + nextCost;
				if(go[nextV] > newCost) {
					go[nextV] = newCost;
					pq2.add(new int[] {newCost, nextV});
				}
			}
		}
		int result = -1;
		for(int i = 1; i < n + 1; i++) {
			int num = go[i] + back[i];
			if(result < num) {
				result = num;
			}
		}
		System.out.println(result);

	}
}
