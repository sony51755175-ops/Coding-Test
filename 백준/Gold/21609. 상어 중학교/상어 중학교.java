import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	static int board[][];
	static int n;
	static int m;
	static int dx[] = {-1, 0, 1, 0};
	static int dy[] = {0, 1, 0 ,-1};
	static int score = 0;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		board = new int[n][n];
		
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < n; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		while(true) {
			// 크기가 가장 큰 그룹 찾기 -> 여러개 일 겨우 무지개 블록 많은 수, 그것도 여러개면 행 큰 것, 열 큰 것
			List<int[]> list = find_biggest_block();
//			System.out.println("start");
//			System.out.println("=========");
//			for(int i = 0; i < n; i++) {
//				for(int j = 0; j < n; j++) {
//					System.out.printf("%2d ", board[i][j]);
//				}
//				System.out.println();
//			}
//			System.out.println();
			// 앞에서 찾은 가장 큰 그룹 블록 제거 블록의 수^2점 획득 -> 제거 블록은 9으로 설정
			if(list.size() == 0) break;
			remove_biggest_block(list);
//			System.out.println("remove=========");
//			for(int i = 0; i < n; i++) {
//				for(int j = 0; j < n; j++) {
//					System.out.printf("%2d ", board[i][j]);
//				}
//				System.out.println();
//			}
//			System.out.println();
			// 중력 작용 (검은 블록(-1)은 이동하지 않음)
			gravity_block();
//			System.out.println("gravity=========");
//			for(int i = 0; i < n; i++) {
//				for(int j = 0; j < n; j++) {
//					System.out.printf("%2d ", board[i][j]);
//				}
//				System.out.println();
//			}
//			System.out.println();
			// 격자 90도 반시계 회전
			rotate_board();
//			System.out.println("rotate=========");
//			for(int i = 0; i < n; i++) {
//				for(int j = 0; j < n; j++) {
//					System.out.printf("%2d ", board[i][j]);
//				}
//				System.out.println();
//			}
//			System.out.println();
			// 중력 작용 (검은 블록(-1)은 이동하지 않음)
			gravity_block();
			
//			for(int i = 0; i < n; i++) {
//				for(int j = 0; j < n; j++) {
//					System.out.printf("%2d ", board[i][j]);
//				}
//				System.out.println();
//			}
//			System.out.println("end");
			
		}
		System.out.println(score);
	}
	static void rotate_board() {
		int[][] newBoard = new int[n][n];
		for (int i = 0; i < n; i++) {
		    for (int j = 0; j < n; j++) {
		        newBoard[n - 1 - j][i] = board[i][j];
		    }
		}
		board = newBoard;
	}
	static void gravity_block() {
//		for(int i = 0; i < n; i++) {
//			for(int j = 0; j < n; j++) {
//				System.out.printf("%4d ", board[i][j]);
//			}
//			System.out.println();
//		}
//		
//		System.out.println();
		int arr[] = new int[n];
		for(int i = n - 2; i >= 0; i--) {
			for(int j = 0; j < n; j++) {
				if(board[i][j] != 9 && board[i][j] != -1) {
					int x = i;
					while(true) {
						if(x + 1 < n) {
							if(board[x + 1][j] == 9) {
								board[x + 1][j] = board[x][j];
								board[x][j] = 9;
								x +=1 ;
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}
				}
			}
		}
//	for(int i = 0; i < n; i++) {
//		for(int j = 0; j < n; j++) {
//			System.out.printf("%4d ", board[i][j]);
//		}
//		System.out.println();
//	}
//	System.out.println();
//	System.exit(0);
	}
	
	static void remove_biggest_block(List<int[]> list) {
		int sx = list.get(0)[2];
		int sy = list.get(0)[3];
		int size = list.get(0)[0];
//		System.out.println(size * size);
		score += size * size;
		boolean visited[][] = new boolean[n][n];
		int color = board[sx][sy];
		Deque<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] {sx, sy});
		while(!queue.isEmpty()) {
			int num[] = queue.poll();
			int x = num[0];
			int y = num[1];
			
			for(int k = 0; k < 4; k++) {
				int nx = x + dx[k];
				int ny = y + dy[k];
				if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
				if(board[nx][ny] == -1 || board[nx][ny] == 9) continue;
				if(board[nx][ny] == color || board[nx][ny] == 0) {
					board[nx][ny] = 9;
					queue.add(new int[] {nx, ny});
				}
			}
		}
		
//		for(int i = 0; i < n; i++) {
//			for(int j = 0; j < n; j++) {
//				System.out.print(board[i][j] + " ");
//			}
//			System.out.println();
//		}
//		System.out.println();
		
	}
	static List<int[]> find_biggest_block() {
		List<int[]> list = new ArrayList<>();
		boolean visited[][] = new boolean[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(board[i][j] != -1 && board[i][j] != 9 && board[i][j] != 0 && visited[i][j] == false) {
					int color = board[i][j];
					visited[i][j] = true;
					Deque<int[]> queue = new ArrayDeque<>();
					queue.add(new int[] {i, j});
					int size = 1;
					int rainbow = 0;
					while(!queue.isEmpty()) {
						int num[] = queue.poll();
						int x = num[0];
						int y = num[1];
						
						for(int k = 0; k < 4; k++) {
							int nx = x + dx[k];
							int ny = y + dy[k];
							
							if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
							if(visited[nx][ny]) continue;
							if(board[nx][ny] == -1 || board[nx][ny] == 9) continue;
							if(board[nx][ny] == color || board[nx][ny] == 0) {
								queue.add(new int[] {nx, ny});
								visited[nx][ny] = true;
								if(board[nx][ny] == 0) rainbow++;
								size++;
							}
						}
					}
					if(size >= 2) {
						list.add(new int[] {size, rainbow, i, j});	
					}
					
				}
				
				for(int a = 0; a < n; a++) {
					for(int b = 0; b < n; b++) {
						if(board[a][b] == 0) {
							visited[a][b] = false;
						}
					}
				}
			}
		}
		list.sort((a, b) ->{
			if(a[0] != b[0]) return b[0] - a[0];
			if(a[1] != b[1]) return b[1] - a[1];
			if(a[2] != b[2]) return b[2] - a[2];
			return b[3] - a[3];
		});
		
		return list;
	}
}
