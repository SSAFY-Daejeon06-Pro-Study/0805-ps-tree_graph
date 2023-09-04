package kr.ac.lecture.samsung.special_lecture.second;

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
*
*
* */
public class SWA_LCA {

    private static List<Integer>[] upper; // 방향이 위로
    private static List<Integer>[] lower; // 방향이 아래로

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

            StringBuilder sb1 = new StringBuilder();
            StringBuilder sb2 = new StringBuilder();

            dfs(a, sb1);
            dfs(b, sb2);

            String[] sp1 = sb1.toString().split(" ");
            String[] sp2 = sb2.toString().split(" ");

            int len = Math.min(sp1.length, sp2.length);

            for(int i=0; i<len; i++){
                if(sp1[i].equals(sp2[i])){
                    lca = Integer.parseInt(sp1[i]) + 1;
                }
            }

            int subTreeSize = subTree(lca-1);

            System.out.printf("#%d %d %d%n", testCase, lca, subTreeSize);
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
