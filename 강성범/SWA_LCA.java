package day0905;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/*
* [문제 요약]
* 이진 트리에서 임의의 두 정점 중 가장 가까운 공통 조상 구하기 - LCA
*
* [제약 조건]
* V(10 ≤ V ≤ 10000)
* 이진 트리이기 때문에 간선(E)는 V-1
*
* [문제 설명]
* 부모 방향으로 향하는 upper 트리 생성
* 임의의 정점이 루트로 향하는 route를 dfs로 구함
* 
* 루트부터 각 노드로 가는 정점 중 공통 조상을 구해서 갱신함
* 
* 자식 방향으로 향하는 lower 트리 생성
* 최소공통조상에서 시작되는 서브트리의 노드 개수를 dfs로 구함
* 
*
* */
public class SWA_LCA {

    private static List<Integer>[] upper; // 방향이 위로 -> 루트 쪽으로
    private static List<Integer>[] lower; // 방향이 아래로 -> 리프 쪽으로

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stz;

        int t = Integer.parseInt(br.readLine());

        for(int testCase = 1; testCase <= t; testCase++) {
            int lca = 0;

            stz = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(stz.nextToken());
            int e = Integer.parseInt(stz.nextToken());
            int a = Integer.parseInt(stz.nextToken()) - 1;
            int b = Integer.parseInt(stz.nextToken()) - 1;

            upper = new List[v];
            lower = new List[v];
            for(int i=0; i<v; i++){
                upper[i] = new ArrayList<>();
                lower[i] = new ArrayList<>();
            }

            stz = new StringTokenizer(br.readLine());
            while (e-- > 0){
                int parent = Integer.parseInt(stz.nextToken()) - 1;
                int child = Integer.parseInt(stz.nextToken()) - 1;

                upper[child].add(parent);
                lower[parent].add(child);
            }

            // 루트~임의의 두 정점까지 갈 때 거쳐가는 노드들 저장
            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            dfs(a, sb1);
            dfs(b, sb2);

            String[] sp1 = sb1.toString().split(" ");
            String[] sp2 = sb2.toString().split(" ");

            int len = Math.min(sp1.length, sp2.length); // 두 개중 짧은 길을 구함

            for(int i=0; i<len; i++){
                if(sp1[i].equals(sp2[i])){ // 루트부터 비교해서 lca를 구함 
                    lca = Integer.parseInt(sp1[i]);
                }
            }

            // lca의 서브트리 크기를 구함
            int subTreeSize = subTree(lca);

            System.out.printf("#%d %d %d%n", testCase, lca+1, subTreeSize);
        }

        br.close();
    }

    private static void dfs(int node, StringBuilder route) {
        if(node == 0) return;

        for(int nn : upper[node]){
            dfs(nn, route);
            route.append(nn).append(" ");
        }
    }

    private static int subTree(int node) {
        int size = 1;

        for(int nn : lower[node]){
            size += subTree(nn);
        }
        return size;
    }
}
