import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<long[]> graph[] = new ArrayList[n + 1];
        long dist[] = new long[n + 1];

        for(int i = 1; i < n + 1; i++){
            graph[i] = new ArrayList<>();
            dist[i] = -1;
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            long cost = Long.parseLong(st.nextToken());

            graph[from].add(new long[]{cost, to});
            graph[to].add(new long[]{cost, from});
        }
        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) ->Long.compare(b[0], a[0]));

        pq.add(new long[]{Long.MAX_VALUE, start});
        dist[start] = Long.MAX_VALUE;

        while(!pq.isEmpty()){
            long cur[] = pq.poll();
            int v = (int)cur[1];
            long cost = cur[0];

            if(dist[v] > cost) continue;
            
            for(long[] next : graph[v]){
                long nextCost = next[0];
                int nextV = (int)next[1];


                long newCost = Math.min(cost, nextCost);

                if(dist[nextV] < newCost){
                    dist[nextV] = newCost;
                    pq.add(new long[]{newCost, nextV});
                }
            }
        }

        System.out.println(dist[end]);


    }
}
