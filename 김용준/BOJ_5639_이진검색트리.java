import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * 풀이 시작 : 10:21
 * 풀이 완료 :
 * 풀이 시간 :
 *
 * 문제 해석
 * 이진 검색 트리
 * 전위 순회한 결과가 주어졌을 때 후위 순회한 결과를 구해야 함
 *
 * 구해야 하는 것
 * 전위 순회한 결과가 주어졌을 때 후위 순회한 결과를 구해야 함
 *
 * 문제 입력
 * 첫째 줄 ~ N개 줄 : 이진 검색 트리를 전위 순회한 값
 *
 * 제한 요소
 * 1 <= N <= 10000
 * 1 <= V <= 100000
 * 중복 노드 없음
 *
 * 생각나는 풀이
 * 전위 순회 결과와 이진 검색 트리의 특성을 이용해 트리를 만든다
 * 전위 순회에서 얻을 수 있는 힌트
 * 1. 첫 번째 노드는 루트
 * 이진 검색 트리에서 얻을 수 있는 힌트
 * 1. 배열을 오름차순 정렬하면 중위 순회 결과가 됨
 * 2. 현재 노드의 값과 갖고 있는 값을 비교하여 노드가 어디 삽입될 지 알 수 있음
 *
 * 구현해야 하는 기능
 * 1. 입력에 따라 트리 구현
 * 2. 후위 순회
 */
public class BOJ_5639_이진검색트리 {
    static Node root;
    static StringBuilder sb = new StringBuilder();
    static class Node {
        int value;
        Node left, right;

        public Node(int value) {
            this.value = value;

        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();
        root = new Node(Integer.parseInt(input));
        while ((input = br.readLine()) != null) {
            int value = Integer.parseInt(input);
            makeTree(root, value);
        }

        postOrder(root);
        System.out.println(sb);
    }

    private static void postOrder(Node now) {
        if (now.left != null) postOrder(now.left);
        if (now.right != null) postOrder(now.right);
        sb.append(now.value).append('\n');
    }

    private static void makeTree(Node now, int value) {
        if (value < now.value) {
            if (now.left == null) {
                now.left = new Node(value);
            } else {
                makeTree(now.left, value);
            }
        } else {
            if (now.right == null) {
                now.right = new Node(value);
            } else {
                makeTree(now.right, value);
            }
        }
    }



}