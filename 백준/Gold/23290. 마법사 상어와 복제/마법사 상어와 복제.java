import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int m, s;
	static int dx[] = {0, -1, -1, -1, 0, 1, 1, 1};
	static int dy[] = {-1, -1, 0, 1, 1, 1, 0, -1};
	static int s_dx[] = {-1, 0, 1, 0};
	static int s_dy[] = {0, -1, 0, 1};
	static int board[][][];
	static int smell_board[][];
	static int shark_x, shark_y;
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		m = Integer.parseInt(st.nextToken());
		s = Integer.parseInt(st.nextToken());
		board = new int[4][4][8];
		smell_board = new int[4][4];
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken()) - 1;
			int y = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;
			board[x][y][d] += 1; 
		}
		
		st = new StringTokenizer(br.readLine());
		shark_x = Integer.parseInt(st.nextToken()) - 1;
		shark_y = Integer.parseInt(st.nextToken()) - 1;
		
		solve();
	}
	static void p() {
		for(int i = 0 ; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 8 ; k++) {
					System.out.print(board[i][j][k] + " ");
				}
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	static void solve() {
		for(int i = 0; i < s; i++) {
			// 복제 마법
			int copy_board[][][] = copy_magic();

			// 물고기 이동
			board = move_fish();

			// 상어 이동
			shark_move();

			// 2턴전 물고기 냄새 사라짐
			fish_smell_discount();
			
			// 물고기 태어남
			born_fish(copy_board);

		}
		
		check_fish_count();
		
	}
	
	static void check_fish_count() {
		int sum = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 8; k++) {
					if(board[i][j][k] > 0) {
						sum += board[i][j][k];
					}
				}
			}
		}
		System.out.println(sum);
	}
	
	static void born_fish(int [][][]copy_board) {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 8; k++) {
					if(copy_board[i][j][k] > 0) {
						board[i][j][k] += copy_board[i][j][k];
					}
				}
			}
		}
	}
	
	static void fish_smell_discount() {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				if(smell_board[i][j] > 0) {
					smell_board[i][j] -= 1;
				}
			}
		}
	}
	
	static void shark_move() {
		int max_eat = -1;
		int move_direction[] = new int[3];

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 4; k++) {
					boolean[][] visited = new boolean[4][4];
					// first_move
					int eat_count = 0;
					int nx = shark_x + s_dx[i];
					int ny = shark_y + s_dy[i];
					
					if(nx < 0 || nx >= 4 || ny < 0 || ny >= 4) continue;

					int sum = 0;
					if(!visited[nx][ny]) {
					    for(int p = 0; p < 8; p++) {
					        sum += board[nx][ny][p];
					    }
					}
					visited[nx][ny] = true;
					eat_count += sum;
					
					// second_move
					int nx1 = nx + s_dx[j];
					int ny1 = ny + s_dy[j];
					if(nx1 < 0 || nx1 >= 4 || ny1 < 0 || ny1 >= 4) continue;
					
					sum = 0;
					if(!visited[nx1][ny1]) {
					    for(int p = 0; p < 8; p++) {
					        sum += board[nx1][ny1][p];
					    }
					}
					visited[nx1][ny1] = true;
					
					eat_count += sum;
					// third_move
					
					int nx2 = nx1 + s_dx[k];
					int ny2 = ny1 + s_dy[k];
					
					if(nx2 < 0 || nx2 >= 4 || ny2 < 0 || ny2 >= 4) continue;
					
					sum = 0;
					if(!visited[nx2][ny2]) {
					    for(int p = 0; p < 8; p++) {
					        sum += board[nx2][ny2][p];
					    }
					}
					visited[nx2][ny2] = true;
					
					eat_count += sum;
					
					if(eat_count > max_eat) {
						max_eat = eat_count;
						move_direction[0] = i;
						move_direction[1] = j;
						move_direction[2] = k;
					}
				}
			}
		}
		
		// shark move

		// first move
		int nx = shark_x + s_dx[move_direction[0]];
		int ny = shark_y + s_dy[move_direction[0]];

		for(int p = 0; p < 8; p++) {
			if(board[nx][ny][p] != 0) {
				board[nx][ny][p] = 0;
				smell_board[nx][ny] = 3;
			}
		}
		
		
		// second_move
		nx += s_dx[move_direction[1]];
		ny += s_dy[move_direction[1]];

		for(int p = 0; p < 8; p++) {
			if(board[nx][ny][p] != 0) {
				board[nx][ny][p] = 0;
				smell_board[nx][ny] = 3;
			}
		}
		// third_move
		nx += s_dx[move_direction[2]];
		ny += s_dy[move_direction[2]];

		for(int p = 0; p < 8; p++) {
			if(board[nx][ny][p] != 0) {
				board[nx][ny][p] = 0;
				smell_board[nx][ny] = 3;
			}
		}
		shark_x = nx;
		shark_y = ny;
	}
	
	static int[][][] copy_magic() {
		int new_board[][][] = new int [4][4][8];
		for(int i = 0 ; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 8; k++) {
					new_board[i][j][k] = board[i][j][k];
				}
			}
		}
		return new_board;
	}
	
	static int[][][] move_fish() {
		int new_board[][][] = new int [4][4][8];
		for(int i = 0 ; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				for(int k = 0; k < 8; k++) {
					if(board[i][j][k] != 0) {
						int cnt = board[i][j][k];
						boolean move = false;
						
						for(int t = 0; t < 8; t++) {
							int direction = (k - t + 8) % 8;
							int nx = i + dx[direction];
							int ny = j + dy[direction];
							if(nx < 0 || nx >= 4 || ny < 0 || ny >= 4) continue;
							if(nx == shark_x && ny == shark_y) continue;
							if(smell_board[nx][ny] != 0) continue;
							move = true;
							new_board[nx][ny][direction] += cnt;
							break;
						}
						
						if(move == false) {
							new_board[i][j][k] += cnt;
						}
					}
				}
			}
		}
		
		return new_board;
	}
}
