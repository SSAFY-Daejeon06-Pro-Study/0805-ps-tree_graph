package B형스터디2주차;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 
 * 이진트리의 간선 정보가 주어진다.
 * 
 * 이진트리를 구현하고
 * 최소 공통 조상을 찾아보자.
 * 
 * 각 노드는 depths와 parent Node를 가지고 있어야한다.
 * 
 * 공통 조상의 크기는 bfs로 카운트하자
 * 
 * => 간선 정보가 랜덤으로 주어지기떄문에 내가 짠 로직으로는 depths가 뒤죽박죽이어서 bfs를 두번써버렸다.
 * 	  정점, 간선의 개수가 많을수록 비효율적으로 작동하는 코드인듯
 * 
 */
public class Solution_CodeBattle_공통조상_이주혁2 {
	
	static class Node {
		
		int val;
		int depths;
		Node left;
		Node right;
		Node parent;
		
		public Node(int val, int depths, Node parent, Node left, Node right) {
			
			this.val = val;
			this.depths = depths;
			
			this.left = left;
			this.right = right;
			this.parent = parent;
		}
		
		
	}
	

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int T = Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++) {
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			int V = Integer.parseInt(st.nextToken());
			int E = Integer.parseInt(st.nextToken());
			int child1 = Integer.parseInt(st.nextToken());
			int child2 = Integer.parseInt(st.nextToken());
			
			Node[] binaryTree = new Node[V+1];
			
			// 루트 노드 설정
			binaryTree[1] = new Node(1, 1, null, null, null);
			
			st = new StringTokenizer(br.readLine());
			
			// 간선 정보 입력
			for(int i=0; i<E; i++) {
				
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				
				// from Node가 만들어지지 않았다면 생성
				if(binaryTree[from] == null) {
					binaryTree[from] = new Node(from, 1, null, null, null);
				}
				
				Node parent = binaryTree[from];
				
				// 이미 갱신되어있는 간선정보인 경우
				if(binaryTree[to] != null) {
					
					if(parent.left == null) {
						parent.left = binaryTree[to];
						binaryTree[to].parent = parent;
					} else {
						parent.right = binaryTree[to];
						binaryTree[to].parent = parent;
					}
					
				}
				else if(parent.left == null) {
					parent.left = new Node(to, parent.depths+1, parent, null, null);
					binaryTree[to] = parent.left;
				} else {
					parent.right = new Node(to, parent.depths+1, parent, null, null);
					binaryTree[to] = parent.right;
				}
			}
			
			bfs(binaryTree[1]);
			
			Node lca = null;
			
			// depths가 큰 Node가 첫번째 parameter로 넘겨주도록 조정
			if(binaryTree[child1].depths > binaryTree[child2].depths) {
				lca = findCommonAncestor(binaryTree[child1], binaryTree[child2]); 
			} else {
				lca = findCommonAncestor(binaryTree[child2], binaryTree[child1]);
			}
			
			int subTreeSize = bfs(lca);
			
			sb.append('#').append(t).append(' ').append(lca.val).append(' ').append(subTreeSize).append('\n');
			
		}
		System.out.println(sb);
		
	}
	
	private static Node findCommonAncestor(Node findNode, Node goalNode) {
		
		int nowDepths = findNode.depths;
		int goalDepths = goalNode.depths;
		
		// depths를 맞춘다.
		while(goalDepths < nowDepths) {
			findNode = findNode.parent;
			nowDepths = findNode.depths;
		}
		
		// 같은 node를 찾을때까지 부모를 거슬러 올라간다.
		while(goalNode.val != findNode.val) {
			goalNode = goalNode.parent;
			findNode = findNode.parent;
			
		}
		
		// 공통 조상을 리턴한다.
		return goalNode;

	}
	
	
	private static int bfs(Node node) {
		
		// subtree의 루트노드 개수 1
		int sum = 1;
		
		Queue<Node> q = new ArrayDeque<Node>();
		
		q.add(node);
		
		while(!q.isEmpty()) {
			
			Node temp = q.poll();
			
			//depths를 갱신
			if(temp.left != null) {
				sum++;
				temp.left.depths = temp.depths+1;
				q.add(temp.left);
			}
			
			//depths를 갱신
			if(temp.right != null) {
				sum++;
				temp.right.depths = temp.depths+1;
				q.add(temp.right);
			}
			
		}
		
		return sum;

	}
	
}
