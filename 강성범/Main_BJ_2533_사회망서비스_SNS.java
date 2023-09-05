package day0905;

import java.io.*;
import java.util.*;


/*
* [문제 요약]
* 사이클이 발생하지 않는 그래프(트리) 무방향 정보가 주어질 때,
* 최소 노드를 선택해서 모든 간선이 선택되게 한다.
*
* [제약 조건]
* 2 ≤ N ≤ 1,000,000
* 각 정점은 1~N까지의 일련 번호로 이루어짐
* N-1개의 줄에는 각 줄마다 친구 관계 트리 엣지가 주어짐
*
* [문제 설명]
* 특정 노드 몇개를 선택했을 때, 모든 간선이 선택되어서 모든 정점을 갈 수 있어야 함
* N-1개의 정보가 주어짐.
* 입력의 그래프는 사이클이 발생하지 않는다고 했고, N-1개의 정보가 주어지기 때문에
* 모든 노드들이 연결되어 있음.
*
* 즉, 간선이 많은 특정 노드를 우선적으로 선택해야 될 것 같음
*
* 모든 노드가 가지는 간선의 수 배열에 저장
* 그래프에서 간선 많은 노드부터 선택
* 간선과 연결된 노드들 탐색해서 간선의 수 제거
* 모든 노드가 간선의 수가 0이하가 될 때 까지 반복
* 노드의 개수가 N이니 무방향이기 때문에 결국 3*N으로 끝날거 같음 -> 시작 노드 정보, 연결된 노드들, 매번 n개의 노드 확인
*
* 위의 방법으로는 반례가 존재함 -> 불가능
*
* dp활용
* 구조가 트리이기 때문에 어디에서 시작하든 상관 없음
* 편의상 0에서 시작
* 이차원 dp 배열 필요.
*   - 1차원에는 노드번호 저장
*   - 2차원은 크기가 2임
*       - [0] : 해당 노드가 얼리어답터일 때
*       - [1] : 해당 노드가 얼리어답턴가 아닐 때
* dfs 탐색
*
* */
public class Main_BJ_2533_사회망서비스_SNS {

    private static int n;
    private static List<Integer>[] graph;
    private static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz;

        n = Integer.parseInt(br.readLine());
        graph = new List[n];
        dp = new int[n][2];

        for(int i=0; i<n; i++){
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < n-1; i++) {
            stz = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(stz.nextToken()) - 1;
            int v = Integer.parseInt(stz.nextToken()) - 1;

            graph[u].add(v);
            graph[v].add(u);
        }

        boolean[] visited = new boolean[n];
        visited[0] = true;
        dfs(0, visited); // 임의의 정점인 0에서 시작. 트리이기 때문에 어디서든 시작가능

        // 시작한 위치가 얼리어답터일 때와 아닐때의 최솟값을 비교해서 출력
        System.out.println(Math.min(dp[0][0], dp[0][1]));

        br.close();
    }


    private static void dfs(int node, boolean[] visited){
        dp[node][0] = 1; // 얼리어답터일 때
        dp[node][1] = 0; // 얼리어답터가 아닐 때

        for(int nn : graph[node]){
            if(visited[nn]) continue;
            visited[nn] = true;

            dfs(nn, visited);

            // 현재 노드가 얼리어답터 일 때, 자식은 얼리어답터일 수도 있고 아닐 수 있음. 때문에 최소를 가져옴
            dp[node][0] += Math.min(dp[nn][0], dp[nn][1]);

            // 현재 노드가 얼리어답터가 아닐 때, 자식은 무조건 얼리어답터여야 함
            dp[node][1] += dp[nn][0];
        }
    }
}
