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
        int k = Integer.parseInt(st.nextToken());
        long INF = Long.MAX_VALUE / 4;
        List<long[]> graph[] = new ArrayList[n + 1];
        PriorityQueue<Long> dist[] = new PriorityQueue[n + 1];

        for(int i = 1; i < n + 1; i++){
            graph[i] = new ArrayList<>();
            dist[i] = new PriorityQueue<>((a, b) ->Long.compare(b, a));
        }

        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            long cost = Integer.parseInt(st.nextToken());
            graph[from].add(new long[]{cost, to});
        }

        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) ->Long.compare(a[0], b[0]));

        pq.add(new long[]{0, 1});
        dist[1].add(0L);
        while(!pq.isEmpty()){
            long cur[] = pq.poll();
            long cost = cur[0];
            int v = (int)cur[1];

            for(long []next : graph[v]){
                long nextCost = next[0];
                int nextV = (int)next[1];
                long newCost = cost + nextCost;

                if(dist[nextV].size() < k){
                    dist[nextV].add(newCost);
                    pq.add(new long[]{newCost, nextV});
                }
                else if(dist[nextV].size() == k){
                    if(dist[nextV].peek() > newCost){
                        dist[nextV].poll();
                        dist[nextV].add(newCost);
                        pq.add(new long[]{newCost, nextV});
                    }
                }
            }
        }

        for(int i = 1; i < n + 1; i++){
            if(dist[i].size() < k){
                System.out.println(-1);
            }
            else{
                System.out.println(dist[i].peek());
            }
        }
    }
}
