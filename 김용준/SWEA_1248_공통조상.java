import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 9:23
 * 풀이 완료 : 9:51
 * 풀이 시간 : 28분
 *
 * 문제 해석
 * 이진 트리에서 임의의 두 정점의 가장 가까운 공통 조상을 찾고, 그 정점을 루트로 하는 서브트리의 개수를 반환해야 함
 *
 * 구해야 하는 것
 * 이진 트리에서 임의의 두 정점의 가장 가까운 공통 조상을 찾고, 그 정점을 루트로 하는 서브트리의 개수를 반환해야 함
 *
 * 문제 입력
 * 첫째 줄 : 테케 수 T
 * 테케당 입력
 * 첫째 줄 : 정점 개수 V, 간선 개수 E, 공통 조상을 찾을 두 노드 A, B
 * 둘째 줄 ~ E개 줄 : 간선의 정보, 부모 자식 순서
 *
 * 제한 요소
 * 루트노드는 항상 1
 * 10 <= V <= 10000
 *
 * 생각나는 풀이
 * LCA
 *
 * 구현해야 하는 기능
 * 1. 입력에 따라 트리 구현
 * 2. dfs 수행해서 서브트리 크기, depth 채움
 * 3. LCA 수행
 *  3-1. depth를 맞춰줌
 *  3-2. 두 노드가 같아질 때까지 따라 올라감
 * 4. LCA 찾았으면 서브트리 개수 반환
 *
 */
public class SWEA_1248_공통조상 {
    static int N, lca;
    static Node[] tree;
    static int[] subtreeSize;
    static class Node {
        int depth, idx, parent, left, right;

        public Node(int idx) {
            this.idx = idx;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        int tc = Integer.parseInt(br.readLine());

        for (int tcNum = 1; tcNum <= tc; tcNum++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());

            tree = new Node[N + 1];
            subtreeSize = new int[N + 1];
            for (int i = 1; i <= N; i++) tree[i] = new Node(i);
            st = new StringTokenizer(br.readLine());
            while (E-- > 0) {
                int parent = Integer.parseInt(st.nextToken());
                int child = Integer.parseInt(st.nextToken());
                if (tree[parent].left == 0) tree[parent].left = child; // 왼쪽 자식이 비었다면 왼쪽에 채움
                else tree[parent].right = child; // 아니면 오른쪽에 채움
                tree[child].parent = parent;
            }

            dfs(0, 1); // depth 저장 및 subtree의 크기 세기 위한 dfs
            lca = findLCA(A, B);
            sb.append('#').append(tcNum).append(' ').append(lca).append(' ').append(subtreeSize[lca]).append('\n');
        }
        System.out.println(sb);
    }

    private static int findLCA(int a, int b) {
        int depthA = tree[a].depth;
        int depthB = tree[b].depth;

        while (depthA > depthB) { // 높이가 같아질 때까지 부모로 타고 올라감
            a = tree[a].parent;
            depthA--;
        }

        while (depthA < depthB) { // 높이가 같아질 때까지 부모로 타고 올라감
            b = tree[b].parent;
            depthB--;
        }

        while (a != b) { // 값이 다를 동안 타고 올라감
            a = tree[a].parent;
            b = tree[b].parent;
        }

        return a;
    }

    private static int dfs(int depth, int idx) {
        tree[idx].depth = depth;
        subtreeSize[idx] = 1; // 서브트리 크기는 자신 포함
        if (tree[idx].left != 0) subtreeSize[idx] += dfs(depth + 1, tree[idx].left); // 왼쪽 자식이 있으면 dfs
        if (tree[idx].right != 0) subtreeSize[idx] += dfs(depth + 1, tree[idx].right); // 오른쪽 자식이 있으면 dfs
        return subtreeSize[idx];
    }

}