
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
public class Main {
	static int board[][];
	static int n;
	static int m;
	static int l;
	static int info[][];
	static int t_x, t_y;
	static boolean check[];
	static int dx[] = {-1, 0, 1, 0};
	static int dy[] = {0, 1, 0, -1};
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		l = Integer.parseInt(st.nextToken());
		board = new int [n][n];
		check = new boolean[m];
		
		for(int i = 0; i < n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < n; j++) {
				int num = Integer.parseInt(st.nextToken());
				if(num == 1) {
					board[i][j] = -1;	
				}
				else {
					board[i][j] = num;
				}
				 
			}
		}
		
		st = new StringTokenizer(br.readLine());
		t_x = Integer.parseInt(st.nextToken()) - 1;
		t_y = Integer.parseInt(st.nextToken()) - 1;
		
		info = new int [m][2];
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			int c = Integer.parseInt(st.nextToken()) - 1;
			int d = Integer.parseInt(st.nextToken()) - 1;
			board[a][b] = i + 1;
			info[i][0] = c;
			info[i][1] = d;
		}
		boolean succ = true;
		// 연료는 한 칸 이동할때 1 소모		
		while(true) {
			succ = true;
			// 최단 거리 손님 찾기 -> 여러명이면 행 번호 작은 승객, 열번호 작은 승객
			int tmp[] = find_person(); // 승객 번호, 움직인 거리
//			System.out.println(tmp[0]);
			if(tmp[0] == -1) {
				for(int i  = 0; i < m; i++) {
					if(check[i] == false) {
						succ = false;
					}
				}
				if(succ == false) {
					System.out.println(-1);
					return;
				}
				else {
					System.out.println(l);
					return;
				}
			}
			// 연료 체크, 연료가 < 0 일 경우 실패하고 break;
			// 단 0 일때는 가능
			l -= tmp[1];
			if(l < 0) {

				break;
			}

			// 손님 데려다주기
			int move_distance = go_dest(tmp[0]);
			if(move_distance == -1) break;
			
			l -= move_distance;
			if(l < 0) {

				break;
			}

			//연료 채우기(승객 태워 이동하면서 소모한 연료 양의 두배 충전)
			l += move_distance * 2;
			if(l < 0) {

				break;
			}

//			for(int i = 0; i < m; i++) {
//				System.out.println(check[i]);
//			}
//			if(succ) {
//				System.out.println(l);
//				return;
//			}
		}
		System.out.println(-1);
		
	}
	static int go_dest(int target) {
		int move_distance = 0;
		Deque<int[]> queue = new ArrayDeque<>();
		
		queue.add(new int[] {t_x, t_y, 0});
		boolean visited[][] = new boolean[n][n];
		int target_x = info[target - 1][0];
		int target_y = info[target - 1][1];
		visited[t_x][t_y] = true;
		while(!queue.isEmpty()){
			int num[] = queue.poll();
			int x = num[0];
			int y = num[1];
			int cnt = num[2];
			
			if(x == target_x && y == target_y) {
				move_distance = cnt;
				t_x = x;
				t_y = y;
				check[target - 1] = true;
				return move_distance;
				
			}
			for(int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
				if(visited[nx][ny]) continue;
				if(board[nx][ny] == -1) continue;
				queue.add(new int[] {nx, ny, cnt + 1});
				visited[nx][ny] = true;
			}
		}
		return -1;
	}
	static int[] find_person() {
		int move_distance = 0;
		List<int[]> list = new ArrayList<>();
		Deque<int[]> queue = new ArrayDeque<>();
		queue.add(new int[] {t_x, t_y, 0});
		
		boolean visited[][] = new boolean[n][n];
		visited[t_x][t_y] = true;
		int target = -1;
		// 택시와 승객이 같은 위치에 서 있으면 그 승객 까지 거리는 0
		if(board[t_x][t_y] >= 1 && check[board[t_x][t_y] - 1] == false) {
			target = board[t_x][t_y];
			board[t_x][t_y] = 0;
			return new int[] {target, move_distance};
		}
		else {
			while(!queue.isEmpty()){
				int num[] = queue.poll();
				int x = num[0];
				int y = num[1];
				int cnt = num[2];
				
				if(board[x][y] >= 1 && check[board[x][y] - 1] == false) {
					list.add(new int[] {cnt, x, y});
					
//					move_distance = cnt;
//					target = board[x][y];
//					board[x][y] = 0;
//					t_x = x;
//					t_y = y;
				}
				for(int i = 0; i < 4; i++) {
					int nx = x + dx[i];
					int ny = y + dy[i];
					
					if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
					if(visited[nx][ny]) continue;
					if(board[nx][ny] == -1) continue;
					queue.add(new int[] {nx, ny, cnt + 1});
					visited[nx][ny] = true;
				}
			}
			if(list.size() > 0) {
				list.sort((a, b) ->{
					if(a[0] != b[0]) return a[0] - b[0];
					if(a[1] != b[1]) return a[1] - b[1];
					return a[2] - b[2];
					});
				t_x = list.get(0)[1];
				t_y = list.get(0)[2];

				target = board[t_x][t_y];
				board[t_x][t_y] = 0;
				move_distance = list.get(0)[0];
			}
		}
		return new int[] {target, move_distance};
	}
}
