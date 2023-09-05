package day0905;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

/*
 * [문제 요약]
 * 이진 검색 트리의 모든 노드들의 높이 합을 구하시오
 *
 * [문제 설명]
 * 입력 순서대로 이진검색 트리를 만들면 됨
 * 단, 입력 값의 범위가 너무 커서 트리를 만들고 높이를 구하는 것은 안될듯
 * 노드가 삽입될 때 마다 높이를 저장시켜야 됨 -> 위치를 찾아가는 반복 횟수로 알 수 있을 듯
 *
 * 시간 초과 발생
 *
 * AVL과 같은 BBST로 할 수 있을거 같아서 구현해서 해 봤음
 *
 * BBST와 BST의 높이가 다르고, BBST로 BST의 높이를 구하는 방법을 모르겠음
 *
 * 브로그를 보니 아래와 같은 방법을 treeset으로 해결했음
 * 1. 삽입하는 노드보다 크면서 가장 작은 노드를 찾음
 * 2. 삽입하는 노드보다 작으면서 가장 큰 노드를 찾음
 * 두 개의 높이중 최대에서 +1한게 삽입하는 노드의 높이임
 *
 * 삽입하려는 노드는 해당 노드보다 크면서 가장 작은 노드 왼쪽에 붙을 것음 - ok
 * 삽입하려는 노드는 해당 노드보다 작으면서 가장 큰 도으 오른쪽에 붙을 것임 - ok
 *
 * 왜 최대일까?
 *
 * 몇 번 트리를 그려본 결과 더 높이가 높은 쪽에 붙을 수 밖에 없는 경우만 있었음...
 * 
 * 똑같이 삽입하려는 노드보다 작으면서 가장 큰, 노드보다 크면서 가장 작은 것의 최대 높이를 구해서 저장
 *
 *
 */
public class Main_BJ_1539_이진검색트리_hard {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        AvlTree tree = new AvlTree();

        while(n-- > 0) {
            int num = Integer.parseInt(br.readLine());

            tree.add(num);
        }

        System.out.println(tree.total);

        br.close();
    }

    public static class AvlTree {

        private final long[] nodeHeight = new long[250_002];

        private AvlNode root;
        private long total = 0L;

        public void add(int value) {
            int largestLessThanValue = getLargestLessThanValue(value);
            int smallestGreaterThanValue = getSmallestGreaterThanValue(value);

            root = addValue(root, value);

            if(largestLessThanValue == -1 && smallestGreaterThanValue == -1){
                nodeHeight[value] = 1;
            }else{
                long a = ((largestLessThanValue == -1) ? 0 : nodeHeight[largestLessThanValue]);
                long b = ((smallestGreaterThanValue == -1) ? 0 : nodeHeight[smallestGreaterThanValue]);
                nodeHeight[value] = Math.max(a, b) + 1;
            }

            // System.out.println(value + " : " + nodeHeight[value]);
            total += nodeHeight[value];
        }

        private AvlNode addValue(AvlNode node, int value) {

            if(node == null) {
                node = new AvlNode(value);
            }

            // 삽입하려는 노드가 현재 노드보다 작을 때 -> 왼쪽으로 이동
            else if(node.data > value) {
                
                // 재귀
                node.left = addValue(node.left, value);

                // 왼쪽 서브트리의 높이가 오른쪽 서브트리의 높이보다 2클 때 -> 불균형
                if(getHeight(node.left) - getHeight(node.right) == 2) {
                    if(value < node.left.data) { // LL
                        node = ll(node);

                    }else { // LR
                        node = lr(node);
                    }
                }
            }

            else if(node.data < value) {
                node.right = addValue(node.right, value);

                // 왼 서브트리의 높이가 오른쪽 서브트리의 높이보다 2작을 때 -> 불균형
                if(getHeight(node.left) - getHeight(node.right)  == -2) {
                    if(value > node.right.data) { // RR
                        node = rr(node);

                    }else { // RL
                        node = rl(node);
                    }
                }
            }

            // 높이 갱신
            node.height = getMaxHeight(node.left, node.right) + 1;

            return node;
        }

        // LL
        private AvlNode ll(AvlNode parent) {
            AvlNode child = parent.left;
            parent.left = child.right;
            child.right = parent;

            // 높이 갱신
            parent.height = getMaxHeight(parent.left, parent.right) + 1;
            child.height = getMaxHeight(child.left, child.right) + 1;

            return child;
        }

        // RR
        private AvlNode rr(AvlNode parent) {
            AvlNode child = parent.right;
            parent.right = child.left;
            child.left = parent;

            // 높이 갱신
            parent.height = getMaxHeight(parent.left, parent.right) + 1;
            child.height = getMaxHeight(child.left, child.right) + 1;

            return child;
        }

        // LR
        private AvlNode lr(AvlNode parent) {
            parent.left = rr(parent.left);
            return ll(parent);
        }

        // RL
        private AvlNode rl(AvlNode parent) {
            parent.right = ll(parent.right);
            return rr(parent);
        }

        private int getMaxHeight(AvlNode left, AvlNode right) {
            int lh = getHeight(left);
            int rh = getHeight(right);
            return lh > rh ? lh : rh;
        }

        private int getHeight(AvlNode node) {
            return node == null ? -1 : node.height;
        }

        // value보다 작으면서 가장 큰 값을 찾음
        private int getLargestLessThanValue(int value){
            int result = -1;
            AvlNode tmp = root;

            while (tmp != null){
                if(tmp.data < value){ // 작으니 큰 값을 찾기 위해 오른쪽으로 이동
                    result = tmp.data;
                    tmp = tmp.right;
                }else if(tmp.data > value) { // 크니 작은 값을 찾기 위해 왼쪽으로 이동
                    tmp = tmp.left;
                }else{
                    break; // 노드가 중복되는 경우가 없으니 같아지는 경우는 발생하지 않음
                }
            }
            return result;
        }

        // value보다 크면서 가장 작은 값을 찾음
        private int getSmallestGreaterThanValue(int value){
            int result = -1;
            AvlNode tmp = root;

            while (tmp != null){
                if(tmp.data > value){ // 크니 작은 값을 찾기 위해 왼쪽으로 이동
                    result = tmp.data;
                    tmp = tmp.left;
                }else if(tmp.data < value) { // 작으니 큰 값을 찾기 위해 오른쪽으로 이동
                    tmp = tmp.right;
                }else{
                    break; // 노드가 중복되는 경우가 없으니 같아지는 경우는 발생하지 않음
                }
            }
            return result;
        }

    }

    public static class AvlNode {
        int data, height;
        AvlNode left, right;

        public AvlNode(int data) {
            this.data = data;
        }
    }
}
