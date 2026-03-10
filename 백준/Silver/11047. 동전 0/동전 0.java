import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = br.readLine();
		StringTokenizer st = new StringTokenizer(str);
		
		int N = Integer.parseInt(st.nextToken()), K = Integer.parseInt(st.nextToken());
		int[] A = new int[N];
		for(int i = 0; i < N; i++)
			A[i] = Integer.parseInt(br.readLine());
			
		int temp = N-1, cnt = 0;
		
		while(K != 0) {
			if(K / A[temp] == 0) {
				temp--;
				continue;
			}
			else {
				cnt += K / A[temp];
				K %= A[temp];
			}
			temp--;		
		}
		System.out.println(cnt);
	}
}