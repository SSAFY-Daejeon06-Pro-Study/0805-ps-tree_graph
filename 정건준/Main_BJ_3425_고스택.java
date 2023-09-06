package 정건준;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;
import java.util.Stack;
import java.util.StringTokenizer;

/***
 * [입력]
 * 기계 설명
 * 1.프로그램 명령어(최대 100,000)
 * 2. N (프로그램 수행 횟수, 0<=N<=10000), V(입력값, 0<=Vi<=10의 9승)
 *
 * [문제]
 * NUM X, POP, INV, DUP, SWP, ADD, SUB, MUL, DIV, MOD
 * 숫자가 부족해서 연산을 수행할 수 없을때, 0으로 나눴을 때(DIV, MOD),
 * 연산 결과의 절대값이 10의 9승을 넘어갈 때, 프로그램 종료 시 스택에 저장되어 있는 숫자가 1개가 아닐 때 모두 프로그램 에러
 * 스택에는 절대값 10의 9승보다 작은 값만 저장됨
 */

public class Main_BJ_3425_고스택 {
    static Stack<Long> stack;
    static String[] commandArr;
    static int[] operand;
    static int size;
    static final int MAX_VALUE = 1000000000;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        commandArr = new String[100000];
        operand = new int[100000];

        while(true) {
            String line = br.readLine();

            if(line.equals("QUIT")) break;

            size = 0;
            //END가 나올때까지 명령 입력
            while(!line.equals("END")) {
                String[] splitLine = line.split(" ");

                //POP, INV, DUP, SWP, ADD, SUB, MUL, DIV, MOD
                if(splitLine.length == 1) {
                    commandArr[size] = splitLine[0];
                }
                //NUM
                else {
                    commandArr[size] = splitLine[0];
                    operand[size] = Integer.parseInt(splitLine[1]);
                }
                size++;
                line = br.readLine();
            }

            //프로그램 수행 횟수, 초기 값 입력
            int N = Integer.parseInt(br.readLine());
            
            for(int i=0; i<N; i++) {
                int startVal = Integer.parseInt(br.readLine());
                if(runProgram(startVal, size))
                    sb.append(stack.pop()).append('\n');
                else
                    sb.append("ERROR").append('\n');
            }
            sb.append('\n');
            br.readLine();
        }
        System.out.println(sb.toString().trim());
    }

    static boolean runProgram(long n, int size) {
        stack = new Stack<>();
        stack.push(n);

        for(int i=0; i<size; i++) {
            String command = commandArr[i];

            if(command.equals("NUM")) {
                NUM(operand[i]);
                continue;
            }

            if(!(command.equals("POP") || command.equals("INV") || command.equals("DUP")) && stack.size() < 2)
                return false;

            if(stack.size() < 1)
                return false;

            if(command.equals("POP")) {
                POP();
            }
            else if(command.equals("INV")) {
                INV();
            }
            else if(command.equals("DUP")) {
                DUP();
            }
            else if(command.equals("SWP")) {
                SWP();
            }
            else if(command.equals("ADD")) {
                ADD();
                if(IsOverflow()) return false;
            }
            else if(command.equals("SUB")) {
                SUB();
                if(IsOverflow()) return false;
            }
            else if(command.equals("MUL")) {
                MUL();
                if(IsOverflow()) return false;
            }
            else if(command.equals("DIV")) {
                if(IsTopZero()) return false;
                DIV();
            }
            else if(command.equals("MOD")) {
                if(IsTopZero()) return false;
                MOD();
            }
        }

        if(stack.size() != 1) return false;
        return true;
    }

    static boolean IsOverflow() {
        return Math.abs(stack.peek()) > MAX_VALUE;
    }

    static boolean IsTopZero() {
        return stack.peek() == 0;
    }

    static void NUM(long X) {
        stack.push(X);
    }

    static void POP() {
        stack.pop();
    }

    static void INV() {
        stack.push(-stack.pop());
    }

    static void DUP() {
        stack.push(stack.peek());
    }

    static void SWP() {
        long first = stack.pop();
        long second = stack.pop();
        stack.push(first);
        stack.push(second);
    }

    static void ADD() {
        long first = stack.pop();
        long second = stack.pop();
        stack.push(second + first);
    }

    static void SUB() {
        long first = stack.pop();
        long second = stack.pop();
        stack.push(second - first);
    }

    static void MUL() {
        long first = stack.pop();
        long second = stack.pop();
        long result = first * second;
        stack.push(result);
    }

    static void DIV() {
        long first = stack.pop();
        long second = stack.pop();
        stack.push(second / first);
    }

    static void MOD() {
        long first = stack.pop();
        long second = stack.pop();
        stack.push(second % first);
    }
}


