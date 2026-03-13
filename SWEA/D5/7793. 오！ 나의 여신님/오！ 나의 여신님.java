
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int Test_case = Integer.parseInt(br.readLine());

        int[] dx = {-1, 0, 1, 0};
        int[] dy = {0, 1, 0, -1};

        for (int t = 1; t <= Test_case; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            int[][] board = new int[n][m];
            boolean[][] visited = new boolean[n][m];

            Deque<int[]> devilQ = new ArrayDeque<>();
            Deque<int[]> suQ = new ArrayDeque<>();

            // 0 : 평지, 1 : 벽, 2 : 여신, 3 : 악마
            for (int i = 0; i < n; i++) {
                String line = br.readLine();
                for (int j = 0; j < m; j++) {
                    char ch = line.charAt(j);

                    if (ch == '.') {
                        board[i][j] = 0;
                    } else if (ch == 'X') {
                        board[i][j] = 1;
                    } else if (ch == 'D') {
                        board[i][j] = 2;
                    } else if (ch == 'S') {
                        board[i][j] = 0;
                        suQ.add(new int[]{i, j});
                        visited[i][j] = true;
                    } else if (ch == '*') {
                        board[i][j] = 3;
                        devilQ.add(new int[]{i, j});
                    }
                }
            }

            int count = 0;
            boolean find = false;

            while (!suQ.isEmpty()) {

                // 1. 악마 먼저 확장
                int dSize = devilQ.size();
                for (int i = 0; i < dSize; i++) {
                    int[] cur = devilQ.poll();
                    int x = cur[0];
                    int y = cur[1];

                    for (int dir = 0; dir < 4; dir++) {
                        int nx = x + dx[dir];
                        int ny = y + dy[dir];

                        if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;

                        // 평지로만 확장 (여신 D로는 안 감)
                        if (board[nx][ny] == 0) {
                            board[nx][ny] = 3;
                            devilQ.add(new int[]{nx, ny});
                        }
                    }
                }

                // 2. 수연 이동
                int sSize = suQ.size();
                for (int i = 0; i < sSize; i++) {
                    int[] cur = suQ.poll();
                    int x = cur[0];
                    int y = cur[1];

                    for (int dir = 0; dir < 4; dir++) {
                        int nx = x + dx[dir];
                        int ny = y + dy[dir];

                        if (nx < 0 || nx >= n || ny < 0 || ny >= m) continue;
                        if (visited[nx][ny]) continue;
                        if (board[nx][ny] == 1 || board[nx][ny] == 3) continue;

                        // 여신 도착
                        if (board[nx][ny] == 2) {
                            find = true;
                            break;
                        }

                        visited[nx][ny] = true;
                        suQ.add(new int[]{nx, ny});
                    }

                    if (find) break;
                }

                count++;
                if (find) break;
            }

            if (find) {
                System.out.println("#" + t + " " + count);
            } else {
                System.out.println("#" + t + " GAME OVER");
            }
        }
    }
}