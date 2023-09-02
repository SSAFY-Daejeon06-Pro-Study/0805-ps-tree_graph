import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 5:18
 * 풀이 완료 : 5:40
 * 풀이 시간 : 22분
 *
 * 문제 해석
 * 컴퓨터 해킹
 * 서로 의존하는 컴퓨터는 감염됨
 * a가 b에 의존한다면 b가 감염된 후 일정 시간 뒤 a도 감염됨
 * b가 a를 의존하지 않는다면 a가 감염돼도 b는 안전함
 *
 * 구해야 하는 것
 * 해킹한 컴퓨터 번호와 의존성이 주어질 때 해킹당한 컴퓨터의 개수와 걸리는 시간을 구해야 함
 *
 * 문제 입력
 * 첫째 줄 : tc의 수
 * 테케당 입력
 * 첫째 줄 : 컴퓨터 개수 N, 의존성 개수 D, 해킹당한 컴퓨터 번호 C
 * 둘째 줄 ~ D개 줄 : 각 의존성을 나타내는 정수 a, b, s
 *  - a가 b를 의존하며, 감염에 걸리는 시간이 s라는 의미
 *
 * 제한 요소
 * 1 <= tc <= 100
 * 1 <= N <= 10000
 * 1 <= D <= 100000
 * 1 <= C <= N
 * 1 <= a, b <= N
 * a != b
 * 0 <= s <= 1000
 * 같은 의존성이 2번 주어지지 않음
 *
 * 생각나는 풀이
 * 다익스트라
 * 의존성 -> 해당 방향으로 s만큼 비용이 드는 간선이 있는 것
 * 즉 C에서 시작해 모든 노드로의 최소 비용 -> 다익스트라
 * a가 b를 의존한다는 의미 = b -> a로 가는 비용 s의 간선이 있다
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 그래프 구현
 * 2. 다익스트라 알고리즘 구현
 * 3. 각 컴퓨터로 가는 최소 비용을 비교하여 가장 큰 값과 도달 가능한 개수를 셈
 */
public class BOJ_10282_해킹 {
    static int N, maxTime, cnt;
    static final int INF = 987654321;
    static Edge[] graph;

    static class Edge implements Comparable<Edge> {
        int end, time;
        Edge next;
        public Edge(int end, int time, Edge next) {
            this.end = end;
            this.time = time;
            this.next = next;
        }

        @Override
        public int compareTo(Edge o) {
            return this.time - o.time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int tc = Integer.parseInt(br.readLine());

        while (tc-- > 0) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            maxTime = 0;
            cnt = 0;
            int d = Integer.parseInt(st.nextToken());
            int start = Integer.parseInt(st.nextToken());

            graph = new Edge[N + 1];
            while (d-- > 0) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int time = Integer.parseInt(st.nextToken());

                graph[b] = new Edge(a, time, graph[b]);
            }

            dijkstra(start);
            sb.append(cnt).append(' ').append(maxTime).append('\n');
        }
        System.out.println(sb);
    }

    private static void dijkstra(int start) {
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);
        dist[start] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.offer(new Edge(start, 0, null));

        while (!pq.isEmpty()) {
            Edge now = pq.poll();

            if (dist[now.end] > now.time) continue;

            for (Edge next = graph[now.end]; next != null; next = next.next) {
                if (dist[next.end] > dist[now.end] + next.time) {
                    dist[next.end] = dist[now.end] + next.time;
                    pq.offer(new Edge(next.end, dist[next.end], null));
                }
            }
        }

        for (int i = 1; i <= N; i++) {
            if (dist[i] == INF) continue;
            cnt++;
            maxTime = Math.max(maxTime, dist[i]);
        }
    }

}