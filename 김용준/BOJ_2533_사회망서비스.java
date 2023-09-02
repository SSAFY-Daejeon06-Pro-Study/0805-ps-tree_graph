import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 5:50
 * 풀이 완료 : 6:20
 * 풀이 시간 : 30분
 *
 * 문제 해석
 * 트리가 주어졌을 때 모든 노드는 얼리 아답터이거나 얼리 아답터가 아님
 * 얼리 아답터가 아닌 사람은 모든 친구가 얼리 아답터라면 아이디어를 받아들임
 * 친구 관계 트리가 주어졌을 때 모든 개인이 새로운 아이디어를 수용하기 위해 필요한 최소 얼리아답터의 수를 구해야 함
 *
 * 구해야 하는 것
 * 친구 관계 트리가 주어졌을 때 모든 개인이 새로운 아이디어를 수용하기 위해 필요한 최소 얼리아답터의 수를 구해야 함
 *
 * 문제 입력
 * 첫 번째 줄 : 정점 개수 N
 * 두 번째 줄 ~ N - 1개 줄 : 간선을 나타내는 u, v가 주어짐
 *
 * 제한 요소
 * 2 <= N <= 1_000_000
 * 1 <= 인덱스 <= N
 *
 * 생각나는 풀이
 * 트리 dp
 * 각 노드가 가질 수 있는 상태
 * 1. 얼리아답터
 * 2. 얼리아답터 X
 * - 얼리아답터인 경우
 *      인접 친구를 고려하지 않아도 됨
 * - 얼리아답터가 아닌 경우
 *      인접 친구가 모두 얼리아답터여야 함
 *
 * 리프노드를 기저상태로 생각했을 때
 *  부모 노드가 얼리아답터 상태로 가능한 최소 얼리아답터 수
 *      - min(자식이 모두 얼리아답터일 때 얼리아답터의 수 합 + 1, 그렇지 않았을 때 얼리아답터의 수)
 *  부모 노드가 얼리아답터가 아니라면
 *      - 자식이 모두 얼리아답터일 때 얼리아답터의 수 합
 *
 * 구현해야 하는 기능
 * 1. 입력에 따른 트리 구현
 * 2. 각 노드에서의 최소 얼리아답터 수를 저장할 2차원 dp 배열
 *  - dp[0][i] = 현재 노드가 얼리아답터가 아닌 경우
 *      dp[0][i] += dp[1][모든 자식 노드]
 *  - dp[1][i] = 현재 노드가 얼리아답터인 경우
 *      dp[1][i] += min(dp[0][모든 자식 노드], dp[1][모든 자식 노드])
 * 3. 루트에서의 min dp 값 출력
 *
 */
public class BOJ_2533_사회망서비스 {
    static int N;
    static final int INF = 987654321;
    static ArrayList<Integer>[] tree;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        N = Integer.parseInt(br.readLine());
        tree = new ArrayList[N + 1];
        dp = new int[2][N + 1];

        for (int i = 1; i <= N; i++) {
            dp[0][i] = dp[1][i] = INF;
            tree[i] = new ArrayList<>();
        }

        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            tree[u].add(v);
            tree[v].add(u);
        }

        // 루트를 어디로 잡든 같은 결과가 나옴. 루트를 1로 설정하고 트리 순회
        memoization(0, 1, 0); // 루트가 얼리아답터가 아닌 경우
        memoization(0, 1, 1); // 루트가 얼리아답터인 경우

        System.out.println(Math.min(dp[0][1], dp[1][1]));
    }

    private static int memoization(int parent, int idx, int status) {
        if (dp[status][idx] != INF) return dp[status][idx]; // 이미 값을 구했으면 리턴
        dp[status][idx] = 0;

        if (status == 0) { // 현재 노드가 얼리아답터가 아닌 경우
            for (int child : tree[idx]) { // 모든 자식 노드가 얼리아답터여야 함
                if (child == parent) continue;
                dp[0][idx] += memoization(idx, child, 1); // 모든 자식이 얼리아답터일 때의 최소 얼리아답터 수
            }
        } else {
            for (int child : tree[idx]) { // 현재 노드가 얼리아답터인 경우
                if (child == parent) continue;
                dp[1][idx] += Math.min(memoization(idx, child, 0), memoization(idx, child, 1)); // 자식이 얼리아답터인지 아닌지 상관 없음, 최솟값
            }
            dp[1][idx] += 1; // 자기 자신이 얼리아답터이므로 + 1
        }
        return dp[status][idx];
    }
}