

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main{
    static int MAX = 100000;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] dist = new int[MAX + 1];
        
        Arrays.fill(dist, Integer.MAX_VALUE);

        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(n);
        dist[n] = 0;
        
        while(!q.isEmpty()) {
        	int cur = q.pollFirst();
        	
        	if(cur == k) break;
        	
        	int nxt = cur * 2;
        	
        	if(nxt <= MAX && dist[nxt] > dist[cur]) {
        		dist[nxt] = dist[cur];
        		q.addFirst(nxt);
        	}
        	
        	
        	nxt = cur + 1;
        	
        	if(nxt >=0 && nxt <= MAX && dist[nxt] > dist[cur] + 1) {
        		dist[nxt] = dist[cur] + 1;
        		q.add(nxt);
        	}
        	
        	nxt = cur -1;
        	
        	if(nxt >=0 && nxt <= MAX && dist[nxt] > dist[cur] + 1) {
        		dist[nxt] = dist[cur] + 1;
        		q.add(nxt);
        	}
        	
        }
        System.out.println(dist[k]);
    }
}
