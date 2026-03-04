import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int Student[][] = new int[n * n + 1][4];
		Deque<int[]> queue = new ArrayDeque<>();
		
		
		for(int i = 0; i < n * n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			queue.add(new int[] {a, b, c, d, e});
			Student[a][0] = b;
			Student[a][1] = c;
			Student[a][2] = d;
			Student[a][3] = e;
		}
		
		int dx[] = {-1, 0, 1, 0};
		int dy[] = {0, 1, 0, -1};
		int board[][] = new int[n][n];
		int cnt = 0;
		while(!queue.isEmpty()) {
			int num[] = queue.poll();
			List<int[]> list = new ArrayList<>();
			int final_count = -1;
			for(int i = 0; i < n; i++) {
				for(int j = 0; j < n; j++) {
					if(board[i][j] == 0) {
						int count = 0;
						for(int k = 0; k < 4; k++) {
							int nx = i + dx[k];
							int ny = j + dy[k];
							if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
							for(int a = 1; a < 5; a++) {
								if(board[nx][ny] == num[a]) {
									count++;
								}
							}
						}
						if(final_count < count) {
							list.clear();
							final_count = count;
							list.add(new int[] {i, j});
						}
						else if(final_count == count){
							list.add(new int[] {i, j});
						}
					}
				}
			}
			list.sort((a, b)->{
				if(a[0] == b[0]) {
					return a[1] - b[1];
				}
				return a[0] - b[0];});
				int final_x = -1;
				int final_y = -1;
				int max_count = -1;
				for(int i = 0 ; i < list.size(); i++) {
					int x = list.get(i)[0];
					int y = list.get(i)[1];
					int count = 0;
					for(int j = 0; j < 4; j++) {
						int nx = x + dx[j];
						int ny = y + dy[j];
						
						if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
						if(board[nx][ny] == 0) {
							count++;
						}
					}

					if(max_count < count) {
						max_count = count;
						final_x = x;
						final_y = y;
					}
				}
				board[final_x][final_y] = num[0];
				
			
			
//			for(int a = 0; a < n; a++) {
//				for(int b = 0; b < n; b++) {
//					System.out.print(board[a][b] + " ");
//				}System.out.println();
//			}
//			System.out.println();
			cnt ++;		
		}
		
		int result = 0;
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				int tm_cnt = 0;
				
				for(int k = 0; k < 4; k++) {
					int nx = i + dx[k];
					int ny = j + dy[k];
					
					if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
					for(int a = 0; a < 4; a++) {
						if(board[nx][ny] == Student[board[i][j]][a]) {
							tm_cnt++;
						}
					}
				}
				if(tm_cnt == 1) {
					result += 1;
				}
				else if(tm_cnt == 2) {
					result += 10;
				}
				else if(tm_cnt == 3) {
					result += 100;
				}
				else if(tm_cnt == 4) {
					result += 1000;
				}
			}
		}
		
		
		
		System.out.println(result);
		
	}
}
