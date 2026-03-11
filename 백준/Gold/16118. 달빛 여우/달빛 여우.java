import java.io.*;
import java.util.*;

public class Main {
	public static final long INF = 100000 * 4000 * 4;;
	
	public static class Node {
		int v, w;
		public Node(int v, int w) {
			this.v = v;
			this.w = w;
		}
	}
	public static int N, M, a, b, d;
	public static long minFox[], minWolf[][];
	public static List<Node>[] adjList;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());	// 나무 그루터기의 개수
		M = Integer.parseInt(st.nextToken());	// 오솔길의 개수
		
		adjList = new ArrayList[N+1];
		for(int i = 1; i <= N; i++)
			adjList[i] = new ArrayList<>();
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			
			a = Integer.parseInt(st.nextToken());
			b = Integer.parseInt(st.nextToken());
			d = Integer.parseInt(st.nextToken()) * 2;
			
			adjList[a].add(new Node(b, d));
			adjList[b].add(new Node(a, d));
		}
		
		minFox = new long[N+1];
		minWolf = new long[2][N+1];
		Arrays.fill(minFox, INF);
		for(int i = 0; i < 2; i++)
			Arrays.fill(minWolf[i], INF);
		
		dijkstraFox();
		dijkstraWolf();
		
		int cnt = 0;
		for(int i = 2; i <= N; i++) {
			if(minFox[i] < Math.min(minWolf[0][i], minWolf[1][i]))
				cnt++;
		}
		System.out.println(cnt);
	}
	
	public static void dijkstraWolf() {
		PriorityQueue<long[]> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1], o2[1]));
		
		pq.offer(new long[] {1, 0, 0});		// 3번째 변수는 몇번째 방문 vertex인지 저장
		minWolf[0][1] = 0;
		
		while(!pq.isEmpty()) {
			long[] idx = pq.poll();
			int v = (int)idx[0];
			long w = idx[1];
			int state = (int)idx[2];
			
			
			if(w > minWolf[state][v]) continue;
			
			for(Node node : adjList[v]) {
				int nextV = node.v;
				long nextW = 0;
				
				if(state == 0) {
					nextW = w + (node.w / 2);
					if(nextW < minWolf[1][nextV]) {
						minWolf[1][nextV] = nextW;
						pq.offer(new long[] {nextV, nextW, 1});
					}
					
				} else {
					nextW = w + (node.w * 2);
					if(nextW < minWolf[0][nextV]) {
						minWolf[0][nextV] = nextW;
						pq.offer(new long[] {nextV, nextW, 0});
					}
				}
			}	
		}
	}
	
	public static void dijkstraFox() {
		PriorityQueue<long[]> pq = new PriorityQueue<>((o1, o2) -> Long.compare(o1[1], o2[1]));
		
		pq.offer(new long[] {1, 0});
		minFox[1] = 0;
		
		while(!pq.isEmpty()) {
			long[] idx = pq.poll();
			int v = (int)idx[0];
			long w = idx[1];
			
			if(w > minFox[v]) continue;
			
			for(Node node : adjList[v]) {
				int nextV = node.v;
				long nextW = node.w;
				
				if(minFox[v] + nextW < minFox[nextV]) {
					minFox[nextV] = minFox[v] + nextW;
					pq.offer(new long[] {nextV, minFox[nextV]});
				}
			}
		}
	}
}
