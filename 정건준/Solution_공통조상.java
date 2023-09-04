package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/***
 * [문제]
 * 이진 트리
 * 정점의 개수 V(10 <= V <= 10000) 간선의 개수 E
 * 간선은 항상 부모 자식 순서로 표기됨
 * 정점의 번호는 1부터 V까지의 정수이며, 루트 정점은 항상 1번
 * 주어진 정점들의 최소 공통 조상을 구하고, 해당 정점과 해당 정점을 루트로하는 서브 트리의 크기 출력
 *
 * [변수]
 * int[] level, level[x] = x번호 노드의 레벨, 루트 노드의 레벨은 1
 * int[] treeNode, treeNode[x] = x 노드를 루트로하는 트리에 속한 노드의 개수
 * int[] parent, parent[x] = x 노드의 부모 노드, 루트 노드의 부모는 자기 자신
 *
 * [풀이]
 * 1. 입력으로부터 단방향 인접 리스트 생성
 * 2. DFS를 수행해 level, parent, treeNode 배열을 채움
 *
 * 3. 레벨이 다르다면 레벨을 맟춰줌
 * 4. 레벨을 올리면서 최소 공통 조상을 구함
 */


public class Solution_공통조상 {
    static class Node {
        int v;
        Node next;
        Node(int v, Node next) {
            this.v = v;
            this.next = next;
        }
    }

    static Node[] adjList;
    static int[] level;
    static int[] treeNode;
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        int testCase = Integer.parseInt(br.readLine());

        for(int t=1; t<=testCase; t++) {
            st = new StringTokenizer(br.readLine());
            int V = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            int targetV1 = Integer.parseInt(st.nextToken());
            int targetV2 = Integer.parseInt(st.nextToken());

            adjList = new Node[V + 1];
            level = new int[V + 1];
            treeNode = new int[V + 1];
            parent = new int[V + 1];

            st = new StringTokenizer(br.readLine());
            for(int i=0; i<E; i++) {
                int parent = Integer.parseInt(st.nextToken());
                int child = Integer.parseInt(st.nextToken());
                adjList[parent] = new Node(child, adjList[parent]);
            }

            dfs(1, 1);
            int LCA = getLCA(targetV1, targetV2);
            sb.append('#').append(t).append(' ').append(LCA).append(' ').append(treeNode[LCA]).append('\n');
        }
        System.out.println(sb);
    }

    static int dfs(int v, int parentV) {
        parent[v] = parentV;
        level[v] = level[parentV] + 1;

        int treeNodeCount = 1;
        for(Node cur=adjList[v]; cur!=null; cur=cur.next) {
            treeNodeCount += dfs(cur.v, v);
        }

        treeNode[v] = treeNodeCount;
        return treeNodeCount;
    }

    static int getLCA(int v1, int v2) {
        if(level[v1] != level[v2]) {
            if(level[v1] < level[v2]) {
                int temp = v1;
                v1 = v2;
                v2 = temp;
            }
            while(level[v1] != level[v2]) {
                v1 = parent[v1];
            }
        }

        while(v1 != v2) {
            v1 = parent[v1];
            v2 = parent[v2];
        }
        return v1;
    }
}
