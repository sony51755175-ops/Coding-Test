import java.io.*;
import java.util.*;

public class Main {
	public static int N, D, minDist[], shortcut[][];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		shortcut = new int[N][3];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int length = Integer.parseInt(st.nextToken());
			
			if(length > end - start) continue;
			if(end > D) continue;
			
			shortcut[i][0] = start;
			shortcut[i][1] = end;
			shortcut[i][2] = length;
		}
		
		minDist = new int[D+1];
		for(int i = 0; i <= D; i++)
			minDist[i] = i;

		for(int i = 0; i <= D; i++) {
			
			if(i > 0) {
				minDist[i] = Math.min(minDist[i], minDist[i-1]+1);
			}
			
			for(int[] road : shortcut) {
				if(road[0] == i) {
					if(minDist[i] + road[2] < minDist[road[1]]) {
						minDist[road[1]] = minDist[i] + road[2];
					}
				}
			}
		}
		System.out.println(minDist[D]);
	}
}