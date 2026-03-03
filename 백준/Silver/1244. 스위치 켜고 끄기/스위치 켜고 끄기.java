import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int board[] = new int[n];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < n; i++) {
			board[i] = Integer.parseInt(st.nextToken());
		}
		
		int m = Integer.parseInt(br.readLine());
		
		int student[][] = new int [m][2];
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			student[i][0]  = Integer.parseInt(st.nextToken());
			student[i][1]  = Integer.parseInt(st.nextToken());
		}
		
		//남자 1, 여자2
		
		for(int i = 0; i < m; i++) {
			int status = student[i][0];
			int num = student[i][1];
			
			if(status == 1) { // 남자
				for(int j = 0; j < n; j++) {
					if((j + 1) / num > 0 && (j + 1) % num == 0) {
						if(board[j] == 0) {
							board[j] = 1;
						}
						else {
							board[j] = 0;
						}
					}
				}
			}
			else { // 여자
				int cnt = 0;
				num = num - 1;
				while(true) {
					if(cnt == 0) {
						if(board[num] == 0) {
							board[num] = 1;
						}
						else {
							board[num] = 0;
						}
					}
					else {
						if(num - cnt >= 0 && num + cnt < n) {
							if(board[num - cnt] == board[num + cnt]) {
								if(board[num - cnt] == 0) {
									board[num - cnt] = 1;
								}
								else {
									board[num - cnt] = 0;
								}
								
								if(board[num + cnt] == 0) {
									board[num + cnt] = 1;
								}
								else {
									board[num + cnt] = 0;
								}
							}
							else {
								break;
							}
						}
						else {
							break;
						}
					}
					
					cnt++;
				}
			}
		}
		
        for (int i = 0; i < n; i++) {
            System.out.print(board[i] + " ");
            if ((i + 1) % 20 == 0) System.out.println();
        }
		
		

	}

}
