package kr.ac.lecture.baekjoon.Num1001_10000;

import java.io.*;

/*
* [문제 요약]
* 트리의 전위 순회 결과가 주어질 때, 후위 순회한 결과를 구하시오
*
* [제약 사항]
* 노드에 들어있는 키의 값은 106보다 작은 양의 정수
* 노드의 수는 10,000개 이하
* 같은 키를 가지는 노드는 없음
*
* [문제 설명]
* 전위 순회는 출력 -> 왼쪽 -> 오른쪽 순으로 탐색함
* 즉, 첫 번째가 루트 노드임
* 루트 노드부터 트리를 만들기 시작
*
* [문제 풀이 순서]
* 1. 첫 번째 입력을 루트 노드로 삼음
* 2. 두 번째 입력부터 노드들의 값을 비교하여 이진 탐색 트리를 만듦
*   - key보다 작으면 왼쪽 서브트리
*       - 비어있으면 해당 위치에 저장
*   - key보다 크면 오른쪽 서브트리
*       - 비어있으면 해당 위치에 저장
*
* */
public class Main_BJ_5639_이진검색트리 {

    private static final StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int root = Integer.parseInt(br.readLine());
        BinarySearchTree tree = new BinarySearchTree(root);

        while (true){
            String input = br.readLine();
            if(input == null) break;

            tree.insert(Integer.parseInt(input)); // 새로운 노드 삽입
        }

        postOrder(tree);
        System.out.println(sb.toString());

        br.close();
    }

    private static void postOrder(BinarySearchTree tree){
        if(tree.left != null) postOrder(tree.left);
        if(tree.right != null) postOrder(tree.right);
        sb.append(tree.data).append(System.lineSeparator());
    }

    private static class BinarySearchTree {
        int data;
        BinarySearchTree left, right;

        public BinarySearchTree(int data) {
            this.data = data;
        }

        // 노드 삽입
        private void insert(int num){
            if(data < num){ // 삽입하려는 원소가 현재 노드의 값 보다 클 때
                if(right == null){
                    right = new BinarySearchTree(num);
                }else{
                    right.insert(num);
                }

            }else{ // 삽입하려는 원소가 현재 노드의 값 보다 작을 때
                if(left == null){
                    left = new BinarySearchTree(num);
                }else{
                    left.insert(num);
                }
            }
        }
    }
}
