package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/***
 * [문제]
 * 친구 관계 트리가 주어졌을 때, 모든 개인이 새 아이디어를 수용하기 위한 최소 얼리 어답터의 수 구하기
 * N (트리의 정점 개수, 2 <= N <= 1,000,000)
 * 각 정점은 1부터 N까지의 일련 번호로 표현
 * 얼리 어탑터가 아닌 사람은 자신의 모든 친구가 얼리어답터일때만 아이디어를 받아들임
 *
 * [풀이]
 * 자식 노드가 없다면(리프 노드이면) 얼리어답터 X
 * 자식이 다 얼리어답터가 아니면 얼리어답터가 됨
 *
 * 0. 입력으로부터 무방향 인접 리스트 생성
 * 1. 임의 노드에서 dfs 수행
 * 
 * v가 얼리어답터면 true, 아니면 false 리턴
 * boolean dfs(int v) {
 *    visit[v] = true
 *
 *    boolean result = false
 *    for(Node node=adjList[v]; node!=null; node=node.next) {
 *        if(visit[node.v]) continue;
 *        if(!dfs(node.v)) result = true
 *    }
 *
 *    if(result) answer++
 *    return result
 * }
 */

public class Main_BJ_2533_사회망서비스 {
    static class Node {
        int v;
        Node next;
        Node(int v, Node next) {
            this.v = v;
            this.next = next;
        }
    }

    static Node[] adjList;
    static boolean[] visit;
    static int answer;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int N = Integer.parseInt(br.readLine());

        adjList = new Node[N+1];
        visit = new boolean[N+1];
        answer = 0;

        for(int i=0; i<N-1; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            adjList[from] = new Node(to, adjList[from]);
            adjList[to] = new Node(from, adjList[to]);
        }

        dfs(1);
        System.out.println(answer);
    }

    static boolean dfs(int v) {
        visit[v] = true;

        boolean result = false;
        for(Node cur=adjList[v]; cur != null; cur=cur.next) {
            if(visit[cur.v]) continue;
            if(!dfs(cur.v)) result = true;
        }

        if(result) answer++;
        return result;
    }
}
