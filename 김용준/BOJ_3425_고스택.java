import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * 풀이 시작 : 3:24
 * 풀이 완료 : 4:22
 * 풀이 시간 : 58분
 *
 * 문제 해석
 * 고스택
 * 스택의 가장 위에 저장된 수부터 첫 번째 수, 두 번째 수, ...
 * 스택의 메서드
 *  - NUM x : X를 스택 가장 위에 저장
 *  - POP : 스택 가장 위의 수를 제거
 *  - INV : 첫 번째 수의 부호를 바꿈
 *  - DUP : 첫 번째 숫자를 하나 더 스택의 가장 위에 저장
 *  - SWP : 첫 번째 숫자와 두 번째 숫자의 위치를 교환
 *  - ADD : 첫 번째 숫자와 두 번째 숫자를 더하여 저장
 *  - SUB : 두 번째 숫자 - 첫 번째 숫자를 저장
 *  - MUL : 첫 번째 숫자 * 두 번째 숫자를 저장
 *  - DIV : 두 번째 숫자 / 첫 번째 숫자를 저장
 *  - MOD : 두 번째 숫자 % 첫 번째 숫자를 저장
 *
 * 프로그램 에러의 조건
 * - 숫자가 부족해서 연산 수행할 수 없는 경우
 * - 0으로 나눈 경우
 * - 연산 결과의 절댓값이 10^9를 넘는 경우
 * 프로그램 에러가 발생하면 현재 프로그램 수행을 멈추고 그 다음 어떤 명령도 수행하지 않음
 *
 * 음수 DIV
 * 1. 제수와 피제수 모두 양수로 가정하고 계산
 * 2. 제수와 피제수의 부호가 다르면 음수, 그렇지 않으면 양수
 *
 * 음수 MOD
 * 1. 제수와 피제수 모두 양수로 가정하고 계산
 * 2. 나머지는 피제수의 부호를 따라감
 *
 * 구해야 하는 것
 * 입력이 들어왔을 때 프로그램의 최종 수행 결과
 * 프로그램 에러가 발생하거나 최종 수행 결과로 남아 있는 수가 1개가 아니라면 "ERROR" 출력
 * 그렇지 않으면 남은 하나의 수 출력
 * 한 프로그램 수행 이후에는 항상 공백 라인 출력
 *
 * 문제 입력
 * 프로그램 영역과 입력 영역으로 나뉨
 * 프로그램 영역
 * 한 줄에 명령어 1개
 * END가 나오면 명령어 종료
 *
 * 입력 영역
 * 첫째 줄 : 프로그램 수행 횟수 N
 * 둘째 줄 ~ N개 줄 : 초기 입력값 V
 * 초기 입력값 V에 대해 프로그램 영역의 모든 과정을 수행해야 함
 * 각각의 기계 설명은 빈 줄로 구분
 * QUIT이 나오면 다음 기계 설명이 없다는 뜻
 *
 * 제한 요소
 * 0 <= N <= 10000
 * 0 <= V <= 10^9
 * 0 <= CMD <= 100_000
 * 0 <= 스택에 저장된 갯수 <= 1000
 *
 * 생각나는 풀이
 * 구현
 *
 * 구현해야 하는 기능
 * 문제 해석에 적은 기능
 */
public class BOJ_3425_고스택 {
    static final int INF = 1_000_000_000; // 가능한 수의 범위
    static Node head = new Node(""); // 명령어를 LinkedList로 저장하기 위해 더미 노드 생성
    // 제한 요소에서 스택에 저장되는 요소의 개수가 1000개 이하이므로 처음부터 크기 1000 할당하고 시작해 효율 높임
    static ArrayDeque<Integer> stack = new ArrayDeque<>(1000);
    static class Node {
        String cmd;
        int num; // NUM 명령어용 변수
        Node next;

        public Node(String cmd) {
            this.cmd = cmd;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;

        String input = "";

        while (!(input = br.readLine()).equals("QUIT")) { // QUIT가 나오면 프로그램 종료
            Node node = head;
            // 현재 수행할 명령어 저장
            while (!input.equals("END")) { // END 전까지
                if (input.startsWith("NUM")) { // NUM이면 수까지 저장
                    st = new StringTokenizer(input);
                    node.next = new Node(st.nextToken());
                    node.next.num = Integer.parseInt(st.nextToken());
                } else { // NUM 제외 명령어는 수가 없으므로 바로 저장
                    node.next = new Node(input);
                }
                node = node.next;
                input = br.readLine();
            }

            int N = Integer.parseInt(br.readLine());

            while (N-- > 0) { // 현재 프로그램 동작 횟수
                sb.append(runGoStack(Integer.parseInt(br.readLine()))).append('\n');
            }
            sb.append('\n'); // 공백으로 프로그램 구분

            input = br.readLine(); // 입력에 필요 없는 공백 라인 받음
        }
        System.out.println(sb);
    }

    private static String runGoStack(int startValue) {
        stack.clear(); // 스택 비워주기
        stack.push(startValue); // 초기 값 세팅
        boolean isError = false; // 에러가 났으면 그 이후 과정 수행하지 않기 위해 체크하는 변수
        for (Node node = head.next; node != null; node = node.next) { // LinkedList의 순회
            String nowCmd = node.cmd; // 현재 수행할 명령어
            if (nowCmd.equals("NUM")) {
                num(node.num); // NUM에서는 오류가 발생하지 않음
            } else if (nowCmd.equals("POP")) {
                isError = !pop();
            } else if (nowCmd.equals("INV")) {
                isError = !inv();
            } else if (nowCmd.equals("DUP")) {
                isError = !dup();
            } else if (nowCmd.equals("SWP")) {
                isError = !swp();
            } else if (nowCmd.equals("ADD")) {
                isError = !add();
            } else if (nowCmd.equals("SUB")) {
                isError = !sub();
            } else if (nowCmd.equals("MUL")) {
                isError = !mul();
            } else if (nowCmd.equals("DIV")) {
                isError = !div();
            } else if (nowCmd.equals("MOD")) {
                isError = !mod();
            }
            if (isError) break;
        }

        if (stack.size() != 1) isError = true; // 스택에 남은 수가 1개가 아니면 에러
        return isError ? "ERROR" : String.valueOf(stack.pop());
    }

    // *  - NUM x : X를 스택 가장 위에 저장
    private static void num(int num) {
        stack.push(num);
    }
    // *  - POP : 스택 가장 위의 수를 제거
    private static boolean pop() {
        if (stack.isEmpty()) return false;
        stack.pop();
        return true;
    }
    // *  - INV : 첫 번째 수의 부호를 바꿈
    private static boolean inv() {
        if (stack.isEmpty()) return false;
        stack.push(~stack.pop() + 1);
        return true;
    }
    // *  - DUP : 첫 번째 숫자를 하나 더 스택의 가장 위에 저장
    private static boolean dup() {
        if (stack.isEmpty()) return false;
        stack.push(stack.peek());
        return true;
    }
    // *  - SWP : 첫 번째 숫자와 두 번째 숫자의 위치를 교환
    private static boolean swp() {
        if (stack.size() <= 1) return false;
        int first = stack.pop();
        int second = stack.pop();
        stack.push(first);
        stack.push(second);
        return true;
    }
    // *  - ADD : 첫 번째 숫자와 두 번째 숫자를 더하여 저장
    private static boolean add() {
        if (stack.size() <= 1) return false;
        int first = stack.pop();
        int second = stack.pop();
        if (first + second > INF) return false;
        stack.push(first + second);
        return true;
    }
    // *  - SUB : 두 번째 숫자 - 첫 번째 숫자를 저장
    private static boolean sub() {
        if (stack.size() <= 1) return false;
        int first = stack.pop();
        int second = stack.pop();
        if (second - first < -INF) return false;
        stack.push(second - first);
        return true;
    }
    // *  - MUL : 첫 번째 숫자 * 두 번째 숫자를 저장
    private static boolean mul() {
        if (stack.size() <= 1) return false;
        int first = stack.pop();
        int second = stack.pop();
        long mul = (long) first * second;
        if (Math.abs(mul) > (long) INF) return false;
        stack.push((int) mul);
        return true;
    }
    // *  - DIV : 두 번째 숫자 / 첫 번째 숫자를 저장
    // * 음수 DIV
    // * 1. 제수와 피제수 모두 양수로 가정하고 계산
    // * 2. 제수와 피제수의 부호가 다르면 음수, 그렇지 않으면 양수
    private static boolean div() {
        if (stack.size() <= 1) return false;
        int first = stack.pop();
        int second = stack.pop();
        if (first == 0) return false;
        int res = Math.abs(second) / Math.abs(first);
        if ((first > 0 && second < 0) || (first < 0 && second > 0)) res = -res;
        stack.push(res);
        return true;
    }
    // *  - MOD : 두 번째 숫자 % 첫 번째 숫자를 저장
    // * 음수 MOD
    // * 1. 제수와 피제수 모두 양수로 가정하고 계산
    // * 2. 나머지는 피제수의 부호를 따라감
    private static boolean mod() {
        if (stack.size() <= 1) return false;
        int first = stack.pop();
        int second = stack.pop();
        if (first == 0) return false;
        int res = Math.abs(second) % Math.abs(first);
        if (second < 0) res = -res;
        stack.push(res);
        return true;
    }
}