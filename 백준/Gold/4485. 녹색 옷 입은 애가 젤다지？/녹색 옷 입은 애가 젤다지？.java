
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static int INF = Integer.MAX_VALUE;
	static int dx[] = {-1, 0, 1, 0};
	static int dy[] = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int cnt = 1;
		while(true) {
			int n = Integer.parseInt(br.readLine());
			
			if(n == 0) {
				break;
			}
			
			int board[][] = new int[n][n];
			for(int i = 0; i < n; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j = 0; j < n; j++) {
					board[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int dist[][] = new int[n][n];
			
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					dist[i][j] = INF;
				}
			}
			dist[0][0] = board[0][0];
			
			PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
			pq.add(new int[] {board[0][0], 0, 0});
			
			
			while(!pq.isEmpty()) {
				int cur[] = pq.poll();
				int cost = cur[0];
				int x = cur[1];
				int y = cur[2];
				
				if(dist[x][y] < cost) continue;
				
				for(int i = 0; i < 4; i++) {
					int nx = x + dx[i];
					int ny = y + dy[i];
					
					if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
					int newCost = cost + board[nx][ny];
					if(dist[nx][ny] > newCost) {
						dist[nx][ny] = newCost;
						pq.add(new int[] {newCost, nx, ny});
					}
				}
			}
//			for(int i = 0; i < n; i++) {
//				for(int j = 0; j < n; j++) {
//					System.out.print(dist[i][j] + " ");
//				}
//				System.out.println();
//			}
			System.out.println("Problem " + cnt + ": " + dist[n - 1][n - 1]);
			cnt++;
		}
	}
}
