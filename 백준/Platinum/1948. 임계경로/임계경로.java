
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
        int[] indegree = new int[n + 1];
		int INF = Integer.MAX_VALUE;
		List<int[]> []graph = new ArrayList[n + 1];
		List<int[]> []reverse = new ArrayList[n + 1];
		int[] prev = new int[n + 1];
		
		for(int i = 1; i < n + 1; i++) {
			graph[i] = new ArrayList<>();
			reverse[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < m; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new int[] {cost, to});
			reverse[to].add(new int[] {cost, from});
            indegree[to]++;
		}
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
        int[] dist = new int[n + 1];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        
        
        while(!queue.isEmpty()) {
        	int v = queue.poll();
        	
        	for(int[] g : graph[v]) {
        		int nextv = g[1];
        		int cost = g[0];
        		
        		dist[nextv] = Math.max(dist[v] + cost, dist[nextv]);
                indegree[nextv]--;
                if (indegree[nextv] == 0) {
                    queue.add(nextv);
                }
        	}
        }
        
        boolean visited[] = new boolean[n + 1];
        int count = 0;
        Queue<Integer> rqueue = new ArrayDeque<>();
        rqueue.add(end);
        while(!rqueue.isEmpty()) {
        	int num = rqueue.poll();
        	
        	for(int[] r : reverse[num]) {
        		int cost = r[0];
        		int a = r[1];
        		int diff = dist[num] - dist[r[1]];
        		if(diff == cost) {
        			count++;
        			if(!visited[a]) {
        				visited[a] = true;
        				rqueue.add(a);
        			}
        		}
        	}
        }
        System.out.println(dist[end]);
        System.out.println(count);
	}
}
