import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static int INF = Integer.MAX_VALUE;
	static int dx[] = {-1, 0, 1, 0};
	static int dy[] = {0, 1, 0, -1};
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int test_case = Integer.parseInt(br.readLine());
		for(int t = 1; t < test_case + 1; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int k = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			int h = Integer.parseInt(st.nextToken());

			int time[] = new int[256];
			
			for(int i = 0; i < k; i++) {
				st = new StringTokenizer(br.readLine());
				char a = st.nextToken().charAt(0);
				int num = Integer.parseInt(st.nextToken());
				time[(int)(a) - 64] = num;
			}
			
			int start[] = new int[2];
			int map[][] = new int[h][w];
			for(int i = 0; i < h; i++) {
				String ot = br.readLine();
				for(int j = 0; j < w; j++) {
					char a = ot.charAt(j);
					if(a == 'E') {
						start[0] = i;
						start[1] = j;
						continue;
					}
					int num = (int)a - 64;
					map[i][j] = num;
				}
			}
			int dist[][] = new int[h][w];
			for(int i = 0; i < h; i++) {
				for(int j = 0 ; j < w; j++) {
					dist[i][j] = INF;
				}
			}
			dist[start[0]][start[1]] = 0;
			
			PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
			pq.add(new int[] {0, start[0], start[1]});
			
			while(!pq.isEmpty()) {
				int cur[] = pq.poll();
				int x = cur[1];
				int y = cur[2];
				int cost = cur[0];
				
				if(dist[x][y] < cost) continue;
				
				for(int i = 0; i < 4; i++) {
					int nx = x + dx[i];
					int ny = y + dy[i];
					
					if(nx < 0 || nx >= h || ny < 0 || ny >= w) continue;
					
					int NewCost = cost + time[map[nx][ny]];
					if(NewCost < dist[nx][ny]) {
						dist[nx][ny] = NewCost;
						pq.add(new int[] {NewCost, nx, ny});
					}
				}
			}
//			for(int i = 0; i < h; i++) {
//				for(int j = 0; j < w; j++) {
//					System.out.print(dist[i][j] + " ");
//				}
//				System.out.println();
//			}
			int min_value = INF;
			for(int i = 0; i < h; i++) {
				for(int j = 0; j < w; j++) {
					if(j == 0 || j == w - 1 || i == 0 || i == h - 1) {
						if(dist[i][j] < min_value) {
							min_value = dist[i][j];
						}
					}
				}
			}
			System.out.println(min_value);
		}
	}
}
