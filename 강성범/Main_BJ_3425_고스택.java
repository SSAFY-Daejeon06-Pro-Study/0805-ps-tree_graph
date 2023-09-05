package day0905;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/*
 * [문제 요약]
 *
 * [제약 조건]
 * 명령어는 대문자 알파벳 세 글자
 * 수행횟수 : 0 ≤ N ≤ 10,000
 * 입력값 : 0 ≤ Vi ≤ 109
 *
 * 모든 수행은 독립적
 * QUIT 반복 그만
 * 명령어가 100,000개를 넘어가는 경우와 스택이 수행될 때, 1,000개 이상의 숫자를 저장하는 경우는 없다.
 *
 * 프로그램은 END가 나오면 종료
 * 각 입력 값에서 프로그램 한 번씩 실행
 *
 * 오류
 * 	- 연산을 수행할 수 없거나, 0으로 나눴을 때, 연산의 결과의 절댓값이 10^9를 넘어갈 때
 * 	- 오류가 발생하면 현재 프로그램의 수행을 멈추고 어떤 명령도 수행하지 않음
 * 출력은 빈줄
 *
 * 프로그램 에러가 발생하거나, 모든 수행이 종료됐을 때
 * 스택에 저장되어 있는 숫자가 1개가 아니라면  ERROR 출력
 *
 * [문제 설명]
 * 고스택은 10가지 연산을 수행할 수 있음
 * 	- 전부 스택으로 처리 가능
 * 이항 연산의 경우,
 * 연산을 수행할 때 두 개의 숫자를 모두 제거하고 결과 다시 스택에 저장
 * 음수의 나눗셈
 * 	- 나눗셈의 피 연산자에 음수가 있을 때 그 수에 절댓값을 씌우고 계산
 * 	- 몫과 나머지는 다음과 같이 계산
 * 		- 피연산자중 음수가 한 개일 때 몫의 부호가 음수
 * 		- 이경우를 제외하면 몫의 부호는 항상 양수
 * 		- 나머지 부호는 피제수의 부호와 같음 -> 두 번째와 부호가 같음
 *
 * [문제 풀이 순서]
 * 1. 명령어를 String 형태로 리스트에 저장함
 * 	- END가 나올 때 까지 반복
 * 2. 숫자(N)만큼 반복
 * 	- 각 숫자마다 명령어 수행
 * 3. 에러 발생시 명령어 수행을 하지 않아도 됨
 * 	- 숫자 입력은 받아야 함
 * 4. QUIT 나올 때 까지 반복
 *
 * [시간 복잡도]
 * 1,000 * 100,000 * x = x억
 *
 *
 * */
public class Main_BJ_3425_고스택 {

    private static final int MAX = (int) Math.pow(10, 9);

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true) {
            String str = br.readLine();

            if(str.length() == 0) continue;

            if(str.equals("QUIT")) {
                break;
            }

            List<String> order = new ArrayList<>();
            while(!str.equals("END")) {
                order.add(str);
                str = br.readLine();
            }

            int n = Integer.parseInt(br.readLine());

            Loop1:
            for(int i=0; i<n; i++) {
                Stack<Integer> st = new Stack<>();

                int value = Integer.parseInt(br.readLine());
                st.add(value);

                for(String o : order) { // 명령어 만큼 반복
                    if(o.length() == 0) continue;

                    char c = o.charAt(0);

                    if(c != 'N' && st.isEmpty()) { // N이 아닐 때 스택의 맨 위를 확인해야 함
                        System.out.println("ERROR");
                        continue Loop1;
                    }

                    if(c == 'N') { // NUM
                        int x = Integer.parseInt(o.substring(4));
                        st.add(x);

                    }else if(c == 'P') { // POP
                        st.pop();

                    }else if(c == 'I') { // INV
                        st.add(st.pop() * -1);

                    }else if(c == 'D' && o.charAt(1) == 'U') { // DUP
                        st.add(st.peek());

                    }else if(c == 'S' && o.charAt(1) == 'W') { // SWP
                        if(st.size() == 1) {
                            System.out.println("ERROR");
                            continue Loop1;
                        }

                        int tmp1 = st.pop();
                        int tmp2 = st.pop();
                        st.add(tmp1);
                        st.add(tmp2);

                    }else if(c == 'A') { // ADD
                        if(st.size() == 1) {
                            System.out.println("ERROR");
                            continue Loop1;
                        }

                        int tmp1 = st.pop();
                        int tmp2 = st.pop();

                        if(isOver(tmp1+ tmp2)) { // 두 숫장의 합이 범위를 벗어나는지 확인
                            System.out.println("ERROR");
                            continue Loop1;
                        }
                        st.add(tmp1 + tmp2);

                    }else if(c == 'S') { // SUB
                        if(st.size() == 1) {
                            System.out.println("ERROR");
                            continue Loop1;
                        }

                        int tmp1 = st.pop();
                        int tmp2 = st.pop();

                        if(isOver(tmp2 - tmp1)) { // 두 숫자의 차이가 범위를 벗어나는지 확인
                            System.out.println("ERROR");
                            continue Loop1;
                        }

                        st.add(tmp2 - tmp1);

                    }else if(c == 'M' && o.charAt(1) == 'U') { // MUL
                        if(st.size() == 1) {
                            System.out.println("ERROR");
                            continue Loop1;
                        }

                        long tmp1 = st.pop();
                        long tmp2 = st.pop();

                        long mul = tmp1 * tmp2;
                        if((tmp1 > 0 && tmp2 > 0) || (tmp1 < 0 && tmp2 < 0)) { // 둘이 곱했을 때 양수
                            if(isOver(mul) || mul < 0) { // 값을 넘거가거나, 오버플로 발생시
                                System.out.println("ERROR");
                                continue Loop1;
                            }
                        }else { // 둘이 곱했을 때 음수
                            if(isOver(mul) || mul > 0) { // 값을 넘거가거나, 오버플로 발생시
                                System.out.println("ERROR");
                                continue Loop1;
                            }
                        }

                        st.add((int) mul);

                    }else if(c == 'D') { // DIV
                        if(st.size() == 1) {
                            System.out.println("ERROR");
                            continue Loop1;
                        }

                        int tmp1 = st.pop();
                        int tmp2 = st.pop();

                        if(tmp1 == 0) { // 0으로 나누는 경우
                            System.out.println("ERROR");
                            continue Loop1;
                        }

                        int at1 = Math.abs(tmp1);
                        int at2 = Math.abs(tmp2);
                        int a = at2 / at1;

                        // 피연산자중 음수가 하나일 때 -> 음수
                        if((tmp1 < 0 && tmp2 > 0) || (tmp1 > 0 && tmp2 < 0)) {
                            a *= -1;
                        }
                        st.add(a);

                    }else { // MOD
                        if(st.size() == 1) {
                            System.out.println("ERROR");
                            continue Loop1;
                        }

                        int tmp1 = st.pop();
                        int tmp2 = st.pop();

                        if(tmp1 == 0) {
                            System.out.println("ERROR");
                            continue Loop1;
                        }

                        int at1 = Math.abs(tmp1);
                        int at2 = Math.abs(tmp2);
                        int a = at2 % at1;

                        if(tmp2 < 0) {
                            a *= -1;
                        }
                        st.add(a);

                    }
                }

                if(st.size() != 1) {
                    System.out.println("ERROR");
                }else {
                    System.out.println(st.peek());
                }
            }
            System.out.println();
        }

        br.close();
    }



    private static boolean isOver(int num) {
        return Math.abs(num) > MAX;
    }

    private static boolean isOver(long num) {
        return Math.abs(num) > MAX;
    }

}