package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/***
 * [문제]
 * 어떤 컴퓨터 A가 다른 컴퓨터 B에 의존한다면 B가 감염될 시 일정 시간 후 A도 감염
 * 해킹한 컴퓨터 번호와 각 의존성이 주어질 때, 해킹 당하는 컴퓨터의 개수와, 그에 걸리는 시간 출력
 *
 * 문제를 가중치가 있는 방향 그래프로 표현할 수 있음.
 * 간선의 가중치는 시간, 시작 정점(컴퓨터 번호)에서 각 정점으로 가는 최단 시간을 구하고, 가장 긴 시간을 출력.
 * 다익스트라 알고리즘 수행
 *
 * [입력]
 * n (컴퓨터 개수, 1<=n<=10000)
 * d (의존성 개수, 1<=d<=10000)
 * c (해킹당한 컴퓨터 번호, 1<=c<=n)
 */

public class Main_BJ_10282_해킹 {

    //인접 리스트로 가중치가 있는 방향 그래프 표현
    static class Node {
        int v;
        int weight;
        Node next;
        Node(int v, int weight, Node next) {
            this.v = v;
            this.weight = weight;
            this.next = next;
        }
    }

    //QP의 노드
    static class PQNode implements Comparable<PQNode> {
        int v;
        int totalWeight;
        PQNode(int v, int totalWeight) {
            this.v = v;
            this.totalWeight = totalWeight;
        }
        @Override
        public int compareTo(PQNode x) {
            return Integer.compare(this.totalWeight, x.totalWeight);
        }
    }

    static int[] time; //time[x] = 시작 정점에서 x 정점을 감염시키는 최소 시간
    static boolean[] visit;
    static Node[] adjList;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int testCase = Integer.parseInt(br.readLine());
        for(int t=0; t<testCase; t++) {
            st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            adjList = new Node[n+1];
            visit = new boolean[n+1];
            time = new int[n+1];

            for(int i=0; i<d; i++) {
                st = new StringTokenizer(br.readLine());
                int to = Integer.parseInt(st.nextToken());
                int from = Integer.parseInt(st.nextToken());
                int weight = Integer.parseInt(st.nextToken());
                adjList[from] = new Node(to, weight, adjList[from]);
            }

            //dijkstra를 위한 초기화
            Arrays.fill(time, Integer.MAX_VALUE);

            Dijkstra(c);

            //답을 StringBuilder에 저장
            int infectedComputer = 0;
            int maxSecond = 0;
            for(int i=1; i<=n; i++) {
                if(time[i] != Integer.MAX_VALUE) {
                    infectedComputer++;
                    maxSecond = Math.max(maxSecond, time[i]);
                }
            }
            sb.append(infectedComputer).append(' ').append(maxSecond).append('\n');
        }
        System.out.println(sb);
    }

    static void Dijkstra(int start) {
        PriorityQueue<PQNode> pq = new PriorityQueue<>();
        time[start] = 0;
        pq.offer(new PQNode(start, time[start]));

        while(!pq.isEmpty()) {
            PQNode cur = pq.poll();
            if(visit[cur.v]) continue;

            visit[cur.v] = true;

            for(Node node = adjList[cur.v]; node != null; node = node.next) {
                if(visit[node.v]) continue;

                if(time[node.v] > cur.totalWeight + node.weight) {
                    //최단 경로 갱신
                    time[node.v] = cur.totalWeight + node.weight;
                    pq.offer(new PQNode(node.v, time[node.v]));
                }
            }
        }
    }
}


