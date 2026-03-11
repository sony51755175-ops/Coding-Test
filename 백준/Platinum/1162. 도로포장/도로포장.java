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
		
		List<long[]> graph[] = new ArrayList[n + 1];
		long dist[][] = new long[n + 1][k + 1];
		
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();
			for(int j = 0; j < k + 1; j++) {
				dist[i][j] = Long.MAX_VALUE;	
			}
			
		}
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			long cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new long[] {cost, to});
			graph[to].add(new long[] {cost, from});
		}
		
		PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
		

		dist[1][0] = 0;
		
		pq.add(new long[] {0, 1, 0}); // cost, v, 몇개의 도로를 포장했는지
		
		while(!pq.isEmpty()) {
			long num[] = pq.poll();
			long cost = num[0];
			int v = (int)num[1];
			int cnt = (int)num[2];
			
			if(dist[v][cnt] < cost) continue;
				
			for(long next[] : graph[v]) {
				if(k > cnt) {
					long nextCost = next[0];
					int nextV = (int)next[1];
					
					long newCost = cost;
					if(dist[nextV][cnt + 1] > newCost) {
						dist[nextV][cnt + 1] = newCost;
						pq.add(new long[] {newCost, nextV, cnt + 1});
					}
				}
				
				long nextCost = next[0];
				int nextV = (int)next[1];
				long newCost = cost + nextCost;

				if(dist[nextV][cnt] > newCost) {
					dist[nextV][cnt] = newCost;
					pq.add(new long[] {newCost, nextV, cnt});
				}
			}
		}
		long min_value = Long.MAX_VALUE;
		for(int i = 0; i < k + 1; i++) {
			long num = dist[n][i];
			if(num < min_value) {
				min_value = num;
			}
		}
		System.out.println(min_value);
//		for(int i = 0; i < k + 1; i++) {
//			for(int j = 1; j < n + 1 ; j++) {
//				System.out.print(dist[j][i] + " ");
//			}
//			System.out.println();
//		}
	}
}
