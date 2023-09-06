package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/***
 * 이진 검색 트리 ADD
 * void appendNode(Node root, int value)
 * 1. 탐색 위치를 찾음
 * 1.1 if(root.value < value) 삽입 위치는 오른쪽
 *         루트 노드가 리프 노드면 삽입
 *         if(root.right == null) root.right = new Node(null, value, null)
 *         삽입 위치 이동
 *         appendNode(root.right, value)
 * 1.2 else 삽입 위치는 왼쪽
 *         루트 노드가 리프 노드면 삽입
 *         if(root.left == null) root.left = new Node(null, value, null)
 *         삽입 위치 이동
 *         appendNode(root.left, value)
 */

public class Main_BJ_5639_이진검색트리 {
    static class Node {
        Node left;
        int value;
        Node right;
        Node(Node left, int value, Node right) {
            this.left = left;
            this.value = value;
            this.right = right;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Node root = null;

        root = new Node(null, Integer.parseInt(br.readLine()), null);

        while(true) {
            String input = br.readLine();
            if(input == null || input.equals("")) break;

            int num = Integer.parseInt(input);
            appendNode(root, num);
        }

        postOrderTraversal(root, sb);
        System.out.println(sb);
    }


    static void appendNode(Node root, int value) {
        if(root.value < value) {
            if(root.right == null) root.right = new Node(null, value, null);
            else appendNode(root.right, value);
        }
        else {
            if(root.left == null) root.left = new Node(null, value, null);
            else appendNode(root.left, value);
        }
    }

    static void postOrderTraversal(Node root, StringBuilder sb) {
        if(root == null) return;

        postOrderTraversal(root.left, sb);
        postOrderTraversal(root.right, sb);
        sb.append(root.value).append('\n');
    }
}
