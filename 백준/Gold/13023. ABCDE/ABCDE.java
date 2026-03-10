import java.io.*;
import java.util.*;

public class Main {
	public static int N, M;
	public static List<Integer>[] list;
	public static boolean visited[], isAvail;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[N];
		for(int i = 0; i < N; i++)
			list[i] = new ArrayList<>();
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			list[a].add(b);
			list[b].add(a);
		}
		
		visited = new boolean[N];
		isAvail = false;
		for(int student = 0; student < N; student++) {
			visited[student] = true;
			dfs(student, 0);
			visited[student] = false;
			
			if(isAvail == true)
				break;
		}
		
		System.out.println(isAvail == true ? 1 : 0);
	}
	
	public static void dfs(int student, int depth) {
		if(isAvail == true)
			return;
		
		if(depth == 4) {
			isAvail = true;
			return;
		}
		
		for(Integer std : list[student]) {
			if(!visited[std]) {
				visited[std] = true;
				dfs(std, depth+1);
				visited[std] = false;
			}
		}
	}
}
