
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int m_dx[] = {-1, 1, 0, 0};
	static int m_dy[] = {0, 0, -1, 1};
	static int dx[] = {0, 1, 0, -1};
	static int dy[] = {-1, 0, 1, 0};
	static int n, m;
	static int order[][];
	static int board[][];
	static int arr[];
	static int first, second, third;
	static int start_x, start_y;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		start_x = n / 2;
		start_y = n / 2;
		board = new int[n][n];
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < n; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		order = new int[m][2];
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int d = Integer.parseInt(st.nextToken()) - 1;
			int s = Integer.parseInt(st.nextToken());
			order[i][0] = d;
			order[i][1] = s;
		}
		
		solve();
	}
	static void p() {
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	static void solve() {
		for(int i = 0; i < m; i++) {
			// 블리자드
			magic(i);
			
			// arr 만들기
			make_arr();

			// arr 빈곳 당기기
			check_arr();
			
			while(true) {
				// 구슬 폭발
				boolean check = bomb();
				if(check == false) {
					break;
				}
				// arr 빈곳 당기기
				check_arr();
			}
			
			change_arr();
			board = new int[n][n];
			
			//보드 만들기
			make_board();
		}
		int total = first + second * 2 + third * 3;
		System.out.println(total);
	}
	static void make_board() {
		int x = start_x;
		int y = start_y;
		int direction = 3;
		int arr_cnt = 0;

		while(true) {
//			System.out.println(x + " " + y + " " + direction);
			//왼쪽 먼저 바라보기
			if(arr_cnt >= n * n - 1) break;
			if(arr[arr_cnt] == 0) break;
			int d = (direction + 1) % 4;
			
			int lx = x + dx[d];
			int ly = y + dy[d];
			
			if(lx >= 0 && lx < n && ly >= 0 && ly < n && board[lx][ly] == 0 &&!(lx == start_x && ly == start_y)) {
				direction = (direction + 1) % 4;
				x = lx;
				y = ly;
				board[x][y] = arr[arr_cnt];
//				System.out.println(x + " " + y + " " + arr[arr_cnt]);
				arr_cnt++;
				
			}
			else {
				x += dx[direction];
				y += dy[direction];
				board[x][y] = arr[arr_cnt];
//				System.out.println(x + " " + y + " " + arr[arr_cnt]);
				arr_cnt++;
			}

		}

	}
	static void change_arr() {
		int new_arr[] = new int[n * n - 1];
		int arr_cnt = 0;
		boolean visited[] = new boolean[n * n];
		for(int i = 0; i < arr.length; i++) {
			if(visited[i]) continue;
			visited[i] = true;
			int cnt = 1;
			int color = arr[i];
			if(arr[i] == 0)break;
			while(true) {
				if(arr[i + cnt] == color) {
					visited[i + cnt] = true;
					cnt++;
				}
				else {
					break;
				}
			}
			if(arr_cnt > new_arr.length || arr_cnt + 1 > new_arr.length) break;
			new_arr[arr_cnt] = cnt;
			new_arr[arr_cnt + 1] = color;
			arr_cnt += 2;

		}
		arr = new_arr;
	}
	
	static boolean bomb() {
		boolean visited[] = new boolean[n * n];
		boolean flag = false;
		for(int i = 0; i < arr.length; i++) {
			if(visited[i]) continue;
			visited[i] = true;
			int cnt = 1;
			int color = arr[i];
			if(arr[i] == 0)break;
			while(true) {
				if(arr[i + cnt] == color) {
					visited[i + cnt] = true;
					cnt++;
				}
				else {
					break;
				}
			}

			if(cnt >= 4) {
				flag = true;
				int tmp = 1;
				color = arr[i];
				arr[i] = 0;
				while(true) {
					if(arr[i + tmp] == color) {
						arr[i + tmp] = 0;
						tmp++;
					}
					else {
						break;
					}
				}
				if(color == 1) {
					first += cnt;
				}
				else if(color == 2) {
					second += cnt;
				}
				else if(color == 3) {
					third += cnt;
				}
			}
		}
		return flag;
	}
	static void check_arr() {
		int new_arr[] = new int[n * n - 1];
		int cnt = 0;

		for(int i = 0; i < new_arr.length; i++) {
			if(arr[i] != 0) {
				new_arr[cnt] = arr[i];
				cnt++;
			}
		}
		arr = new_arr;
	}
	
	static void make_arr() {
		arr = new int[n * n - 1];
		int cx = start_x;
		int cy = start_x;
		int direction = 0;
		int arr_cnt = 0;
		int cnt = 1;
		boolean flag = true;
		while(true) {
			for(int i = 0; i < cnt; i++) {
				cx += dx[direction];
				cy += dy[direction];
				if(cx < 0 || cx >= n || cy < 0 || cy >= n) {
					flag = false;
					break;
				}
				arr[arr_cnt] = board[cx][cy];
				arr_cnt++;
			}
			
			if(flag == false) {
				break;
			}
			
			direction = (direction + 1) % 4;
			for(int i = 0; i < cnt; i++) {
				cx += dx[direction];
				cy += dy[direction];
				if(cx < 0 || cx >= n || cy < 0 || cy >= n) {
					flag = false;
					break;
				}
				arr[arr_cnt] = board[cx][cy];
				arr_cnt++;
			}
			if(flag == false) {
				break;
			}
			
			direction = (direction + 1) % 4;
			cnt++;
		}
		
//		for(int i = 0; i < arr.length; i++) {
//			System.out.print(arr[i] + " ");
//		}

	}
	
	static void magic(int i) {
		int direction = order[i][0];
		int distance = order[i][1];
		int x = start_x;
		int y = start_y;
		for(int a = 0; a < distance; a++) {
			int nx = x + m_dx[direction] * (a + 1); 
			int ny = y + m_dy[direction] * (a + 1);
			board[nx][ny] = 0;
		}
	}
}
