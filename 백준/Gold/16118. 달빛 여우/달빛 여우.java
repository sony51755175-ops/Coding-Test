import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class Main {
    public static void main(String[] args) throws IOException{
        int INF = Integer.MAX_VALUE;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        List<int[]> graph[] = new ArrayList[n + 1];
        int dist[] = new int[n + 1];
        int dist_wolf[][] = new int[n + 1][2];

        for (int i = 1; i < n + 1; i++) {
            graph[i] = new ArrayList<>();
            dist[i] = INF;
            dist_wolf[i][0] = INF;
            dist_wolf[i][1] = INF;
        }
        for(int i = 0; i < m; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken()) * 2;

            graph[from].add(new int[]{cost, to});
            graph[to].add(new int[]{cost, from});
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        dist[1] = 0;
        pq.add(new int[]{0, 1});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0];
            int v = cur[1];

            if (dist[v] < cost) continue;

            for (int[] next : graph[v]) {
                int nextCost = next[0];
                int nextV = next[1];

                if (dist[nextV] > cost + nextCost) {
                    dist[nextV] = cost + nextCost;
                    pq.add(new int[]{dist[nextV], nextV});
                }
            }
        }

//        for(int i = 1; i < n + 1; i++){
//            System.out.println(dist[i]);
//        }

        pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        dist_wolf[1][0] = 0; // 다음 이동은 빠르게
        pq.add(new int[]{0, 1, 0}); // cost, vertex, state

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0];
            int v = cur[1];
            int state = cur[2];

            if (dist_wolf[v][state] < cost) continue;

            for (int[] next : graph[v]) {
                int nextCost = next[0];
                int nextV = next[1];
                // 현재 state에 따라 이번 간선 이동
                if (state == 0) { // 빠르게 이동
                    if (dist_wolf[nextV][1] > cost + nextCost / 2) {
                        dist_wolf[nextV][1] = cost + nextCost / 2;
                        pq.add(new int[]{dist_wolf[nextV][1], nextV, 1});
                    }
                } else { // 느리게 이동
                    if (dist_wolf[nextV][0] > cost + nextCost * 2) {
                        dist_wolf[nextV][0] = cost + nextCost * 2;
                        pq.add(new int[]{dist_wolf[nextV][0], nextV, 0});
                    }
                }
            }
        }
//        System.out.println();
//        for(int i = 1; i < n + 1; i++){
//            System.out.println(dist_wolf[i][0] + " " + dist_wolf[i][1]);
//        }
        int cnt = 0;
        for(int i = 1; i < n + 1; i++){
            int num = Math.min(dist_wolf[i][0], dist_wolf[i][1]);
            if(dist[i] < num){
                cnt++;
            }
        }
        System.out.println(cnt);
    }
}
