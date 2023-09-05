import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

/**
 * 풀이 시작 : 9:35
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * 0 ~ N-1까지 들어 있는 배열 P의 모든 요소를 이진 검색 트리에 채울 때
 * 모든 노드 높이의 합을 출력해야 함
 *
 * 구해야 하는 것
 * 모든 노드 높이의 합
 *
 * 문제 입력
 * 첫째 줄 : N
 * 둘째 줄 ~ N개 줄 : P[0] ~ P[N - 1] 원소 한 개
 *
 * 제한 요소
 * 1 <= N <= 250_000
 * 높이의 합 < long
 *
 * 생각나는 풀이
 * 과연 이 문제에 대한 해답이 bbst밖에 없는걸까
 * 다른 방법은 생각나지 않는다
 * 1. 트리맵에 현재 값을 키로, 높이를 밸류로 넣어줌
 * 2. 트리맵 메서드인 ceiling과 floor을 이용해 가장 비슷한 친구들을 찾아줌
 * 3. 삽입되는 높이는 두 인접 노드의 높이 중 큰 값 + 1
 * 구현해야 하는 기능
 *
 */
public class BOJ_1539_이진검색트리Hard {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long sum = 1L;
        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        treeMap.put(Integer.parseInt(br.readLine()), 1);
        Map.Entry<Integer, Integer> small = null;
        Map.Entry<Integer, Integer> big = null;

        while (N-- > 1) {
            int nowKey = Integer.parseInt(br.readLine());
            small = treeMap.higherEntry(nowKey);
            big = treeMap.lowerEntry(nowKey);

            int leftHeight = small == null ? -1 : small.getValue();
            int rightHeight = big == null ? -1 : big.getValue();
            int nowValue = Math.max(leftHeight, rightHeight) + 1;

            sum += nowValue;
            treeMap.put(nowKey, nowValue);
        }

        System.out.println(sum);
    }

}