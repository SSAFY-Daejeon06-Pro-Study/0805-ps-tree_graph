package kr.ac.lecture.baekjoon.Num10001_20000;

import java.io.*;
import java.util.*;

/*
* [문제 요약]
* 의존성이 주어질 때, 몇 개의 노드가 전파되며, 그 시간을 구하여라
*
* [제약 조건]
* 테케 100개 이하
* n : 컴퓨터 개수. 10,000이하
* d : 의존성 개수. 100,000이하
* 시작 노드 : c. 1이상 n 이하
* 같은 의존성이 두 번 이상 존재하지 않음
*
* [문제 설명]
* a가 b를 의존한다면, b에서 s초 뒤에 a로 전파됨 -> b에서 a로 연결되는 단방향, 간선이 s인 그래프
* 시작 노드가 주어지고 가중치가 주어지기 때문에 다익스트라를 생각해 볼 수 있음.
* 다익스트라를 적용한면 각 노드의 최소 이동 가중치를 알 수 있음.
* 자연스럽게 가중치가 갱신된 노드들이 감염된 노드라는 것을 알 수 있음
*
* 딱히, 문제에서 연결 안되는 경우의 출력을 명시하지 않았기 때문에 모든 테케는 정답이 도출될 것임
*
* [문제 풀이 순서]
* 1. 가중치가 s인 단방향 그래프 생성
* 2. 다익스트라로 거리 배열 만들기
* 3. 거리배열을 탐색하여 초기값(MAX)가 아닌 것의 개수와 그 최대 가중치 저장
*
* [메모리 초과 해결 핵심]
* 처음 제출 했을 때, 메모리 초과가 발생함
* 보통 bfs 문제에서 메모리 초과가 발생하면 bfs 내부 로직 문제임
* 대부분 방문을 처리 안했을 경우 발생
* 다익스트라는 방문 boolean을 쓰지 않고, 거리 배열을 사용하기 때문에
* 거리의 판단을 잘 해야 메모리 초과를 해결할 수 있음
*
* if(dis[nn.a] < nextDistance) continue;
* 이 코드를
*
* if(dis[nn.a] <= nextDistance) continue;
* 이와 같이 변경했다니 메모리 초과를 해결할 수 있었음
*
* 어쩌피 같으면 다음 탐색에서 더 커지기 때문에 qu에 들어가는 노드를 줄일 수 있음
*
*
*
*
* */
public class Main_BJ_10282_해킹 {

    private static final int MAX = 987_654_321;

    private static int n, d, c;
    private static List<Node>[] graph;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer stz;

        int testCase = Integer.parseInt(br.readLine());

        while (testCase-- > 0){
            stz = new StringTokenizer(br.readLine());
            n = Integer.parseInt(stz.nextToken());
            d = Integer.parseInt(stz.nextToken());
            c = Integer.parseInt(stz.nextToken()) - 1;

            graph = new List[n];
            for(int i=0; i<n; i++){
                graph[i] = new ArrayList<Node>();
            }

            // 의존성 개수(d)만큼 반복
            while (d-- > 0){
                stz = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(stz.nextToken()) - 1;
                int b = Integer.parseInt(stz.nextToken()) - 1;
                int s = Integer.parseInt(stz.nextToken());

                // a가 b를 의존하고 있음
                graph[b].add(new Node(a, s));
            }

            int[] dis = dijkstra();
            int[] ans = findAnswer(dis);

            bw.write(String.format("%d %d%n", ans[0], ans[1]));
        }

        bw.flush();
        bw.close();
        br.close();
    }

    private static int[] dijkstra() {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dis = new int[n];

        Arrays.fill(dis, MAX);
        pq.add(new Node(c, 0));
        dis[c] = 0;

        while (!pq.isEmpty()){
            Node cn = pq.poll();

            // 저장된 가중치의 크기가 현재 길이보다 작을 때 -> 최소를 갱신할 수 없음
            if(dis[cn.a] < cn.s) continue;

            for(Node nn : graph[cn.a]){
                int nextDistance = nn.s + cn.s; // nn.a 노드로 갈 수 있는 거리
                if(dis[nn.a] <= nextDistance) continue; // 거리가 더 멀 때

                dis[nn.a] = nextDistance;
                pq.add(new Node(nn.a, nextDistance));
            }
        }

        return dis;
    }

    private static int[] findAnswer(int[] dis) {
        int count = 0;
        int maxDis = 0;

        for(int i=0; i<n; i++){
            if(dis[i] == MAX) continue;

            count++;
            maxDis = Math.max(maxDis, dis[i]);
        }

        return new int[]{count, maxDis};
    }

    private static class Node implements Comparable<Node>{
        int a, s;

        public Node(int a, int s) {
            this.a = a;
            this.s = s;
        }

        @Override
        public int compareTo(Node o) {
            return this.s - o.s;
        }
    }
}
