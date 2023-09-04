package 정건준;
import java.io.*;
import java.util.*;

/***
 * [문제]
 * P 배열에는 0보다 크거나 같고 N-1보다 작거나 같은 정수가 중복없이 채워짐
 * N (1<=N<=250,000)
 * P로 이진 검색 트리를 만들었을 때, 모든 노드의 높이 합 출력
 *
 * [풀이]
 * 이진 검색 트리 삽입 시간 복잡도 = 트리의 높이
 * 단순하게 풀 경우 시간 초과 발생
 * 이진 검색 트리를 중위 순회하면 정렬된 리스트가 됨.
 *
 * 1. 리스트에서 노드 X가 들어갈 위치를 찾아낼 수 있음(lowerBound로 찾음) -> O(log N)
 * 2. 2번에서 찾은 위치에 노드 X 삽입 -> O(log N)
 * 3. 노드 X는 lowerBound 이전 위치 노드 또는 lowerBound 위치 노드에 붙음
 * 3-1. 노드 X의 높이 = max(lowerBound 이전 위치 노드의 높이, lowerBound 위치 노드의 높이) + 1
 * 3-4. lowerBound 이전 위치가 없다면 lowerBound 이전 위치 노드의 높이는 0으로 간주
 * 3-5. lowerBound가 없다면 lowerBound 위치 노드의 높이는 0으로 간주
 *
 * [변수]
 * TreeSet<Integer> set //리스트
 */

public class Main_BJ_1539_이진검색트리 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] H = new int[N];
        TreeSet<Integer> set = new TreeSet<>();
        long answer = 0;

        for(int i=0; i<N; i++) {
            int num = Integer.parseInt(br.readLine());

            Integer pre = set.lower(num);
            Integer next = set.higher(num);
            int preHeight = 0, nextHight = 0;

            preHeight = (pre == null) ? 0 : H[pre];
            nextHight = (next == null) ? 0 : H[next];

            set.add(num);
            H[num] = Math.max(preHeight, nextHight) + 1;
            answer += H[num];
        }
        System.out.println(answer);
    }
}
