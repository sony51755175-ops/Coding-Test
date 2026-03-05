
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static int n, m, k, w;
	static int board[][];
	static int wall[][][][];
	static int dx[] = {0, 0, -1, 1};
	static int dy[] = {1, -1, 0, 0};
	static int heater[][];
	static int check[][];
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		List<int[]> heater_list = new ArrayList<>();
		List<int[]> check_list = new ArrayList<>();
		board = new int [n][m];
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < m; j++) {
				int num = Integer.parseInt(st.nextToken());
				if(num == 5) {
					check_list.add(new int[] {i, j});
					board[i][j] = 0;
				}
				else if(num != 5 && num >= 1) {
					heater_list.add(new int[] {i, j, num});
					board[i][j] = 0;
				}
			}
		}
		
		heater = new int[heater_list.size()][3];
		check = new int[check_list.size()][2];
		for(int i = 0; i < heater_list.size(); i++) {
			int x = heater_list.get(i)[0];
			int y = heater_list.get(i)[1];
			int num = heater_list.get(i)[2] - 1;
			heater[i][0] = x;
			heater[i][1] = y;
			heater[i][2] = num;
			
			
		}
		
		for(int i = 0; i < check_list.size(); i++) {
			int x = check_list.get(i)[0];
			int y = check_list.get(i)[1];
			check[i][0] = x;
			check[i][1] = y;
		}
		
		w = Integer.parseInt(br.readLine());
		wall = new int[n][m][n][m];
		for(int i = 0; i < w; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int tmp = Integer.parseInt(st.nextToken());
			
			if(tmp == 0) {
				wall[x][y][x - 1][y] = 1;
				wall[x - 1][y][x][y] = 1;

			}
			else {
				wall[x][y][x][y + 1] = 1;
				wall[x][y + 1][x][y] = 1;
			}
		}
		solve();
		
	}
	
	static void solve() {
		int cnt = 0;
		while(true) {
			// 온풍기 바람
			heater_operation();

			// 온도 조절
			temp_move();

			// 바깥칸 온도 감소
			temp_down();
			cnt++;
			if(cnt >= 101) {
				System.out.println(101);
				break;
			}
			boolean flag = check_temp();
			if(flag) {
				System.out.println(cnt);
				break;
			}
			
		}
	}
	
	static boolean check_temp() {
		boolean tmp = true;
		for(int i = 0; i < check.length; i++) {
			int x = check[i][0];
			int y = check[i][1];
			if(board[x][y] < k) {
				tmp = false;
			}
			
		}
		return tmp;
	}
	
	static void temp_down() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				if(i == 0 || i == (n - 1) || j == 0 || j == (m - 1)) {
					if(board[i][j] > 0) {
						board[i][j] -= 1;
					}
				}
			}
		}
	}
	
	static void temp_move() {
		int new_board[][] = new int[n][m];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < m; j++) {
				int x = i;
				int y = j;
				for(int k = 0; k < 4; k++) {
					int nx = x + dx[k];
					int ny = y + dy[k];
					if(nx >= 0 && nx < n && ny >= 0 && ny < m) {
						if(wall[x][y][nx][ny] != 1) {
							 {
								int dist = board[x][y] - board[nx][ny];
								if(dist == 0) continue;
								if(dist > 0) {
									new_board[x][y] -= dist / 4;
									new_board[nx][ny] += dist / 4;
								}
								else {
									new_board[x][y] += (-dist) / 4;
									new_board[nx][ny] -= (-dist) / 4;
								}
							}
						}
					}
				}
			}
		}
		
		for(int a = 0; a < n; a++) {
			for(int b = 0; b < m; b++) {
				board[a][b] += new_board[a][b] / 2;
				if(board[a][b] < 0) {
					board[a][b] = 0;
				}
			}
		}
	}
	
	static void heater_operation() {
		for(int i = 0; i < heater.length; i++) {
			int new_board[][] = new int[n][m];
			Deque<int[]> queue = new ArrayDeque<>();
			int start_x = heater[i][0];
			int start_y = heater[i][1];
			boolean[][] visited = new boolean[n][m];
			int direction = heater[i][2];
			visited[start_x][start_y] = true;
			int temp = 5;
			queue.add(new int[] {start_x, start_y, temp});
			
			while(!queue.isEmpty()) {
				int num[] = queue.poll();
				int x = num[0];
				int y = num[1];
				int h = num[2];
				
				if(h < 0 ) continue;

				if(h == 5) {
					int nx = x + dx[direction];
					int ny = y + dy[direction];
					if(nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
					if(visited[nx][ny]) continue;
					visited[nx][ny] = true;
					new_board[nx][ny] = h;
					queue.add(new int[] {nx, ny, h - 1});
				}
				else {
					// 직진
					int nx = x + dx[direction];
					int ny = y + dy[direction];

					if(nx >= 0 && nx < n && ny >= 0 && ny < m) {
						if(wall[nx][ny][x][y] != 1) {
							if(visited[nx][ny] == false)
							{
								visited[nx][ny] = true;
								new_board[nx][ny] = h;
								queue.add(new int[] {nx, ny, h - 1});
							}
						}
					}
					
					if(direction == 0 || direction == 1) {
						// 위
						int mx = x - 1;
						int my = y;
						
						nx = mx;
						ny = my + dy[direction];
						if(mx >= 0 && mx < n && my >= 0 && my < m &&
								   nx >= 0 && nx < n && ny >= 0 && ny < m) {
							if(wall[x][y][mx][my] != 1 && wall[mx][my][nx][ny] != 1 && new_board[nx][ny] == 0) {
								
								new_board[nx][ny] = h;
								queue.add(new int[] {nx, ny, h - 1});
							}
						}
						
						
						// 아래
						mx = x + 1;
						my = y;
						
						nx = mx;
						ny = my + dy[direction];
						if(mx >= 0 && mx < n && my >= 0 && my < m &&
								   nx >= 0 && nx < n && ny >= 0 && ny < m) {
							if(wall[x][y][mx][my] != 1 && wall[mx][my][nx][ny] != 1 && new_board[nx][ny] == 0) {
								new_board[nx][ny] = h;
								queue.add(new int[] {nx, ny, h - 1});
							}
						}
					}
					
					else if(direction == 2 || direction == 3) {
						// 위
						int mx = x;
						int my = y - 1;

						nx = mx + dx[direction];
						ny = my;

						if(mx >= 0 && mx < n && my >= 0 && my < m &&
								   nx >= 0 && nx < n && ny >= 0 && ny < m) {
							if(wall[x][y][mx][my] != 1 && wall[mx][my][nx][ny] != 1 && new_board[nx][ny] == 0) {
								new_board[nx][ny] = h;
								queue.add(new int[] {nx, ny, h - 1});
							}
						}
						
						
						// 아래
						mx = x;
						my = y + 1;

						nx = mx + dx[direction];
						ny = my;

						if(mx >= 0 && mx < n && my >= 0 && my < m &&
								   nx >= 0 && nx < n && ny >= 0 && ny < m) {
							if(wall[x][y][mx][my] != 1 && wall[mx][my][nx][ny] != 1 && new_board[nx][ny] == 0) {
								new_board[nx][ny] = h;
								
								queue.add(new int[] {nx, ny, h - 1});
							}
						}
					}
				}
			}
			
			for(int a = 0; a < n; a++) {
				for(int b = 0; b < m; b++) {
					board[a][b] += new_board[a][b];
				}
			}
		}

		

	}
}
