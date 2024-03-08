// https://www.acmicpc.net/problem/1417 우선순위 큐

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main1417 {
    public static void main(String[] args) throws IOException {
        // 다솜이는 기호 1번
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 총 후보자들
        int n = Integer.parseInt(br.readLine());
        // 내(다솜) 득표수
        int dasom = Integer.parseInt(br.readLine());
        // 다솜이를 제외하고 다른 후보자들의 표를 관리할 우선순위 큐
        PriorityQueue<Integer> queue   // (o1, o2) -> o2 - o1과 같은 Collections.reverseOrder
                = new PriorityQueue<>(Collections.reverseOrder());

        // 나버지 후보자들의 득표를 우선순위 큥에 넣어준다.
        for(int i=1; i<n; i++) {
            queue.offer(Integer.parseInt(br.readLine()));
        }

        // 우선순위 큐에서 가장 높은 득표수에서 한명을 매수해서 다솜에이게 표 주기
        // 다솜이가 제일 표가 많아지면 그만
        int cnt = 0;
        while(!queue.isEmpty() && dasom <= queue.peek()){
            // 기장 큰 값에서 1빼고 다시 넣어주기
            queue.offer(queue.poll()-1); 	// 우선순위 큐 가장 위의 득표수 -1
            // 다솜 득표수 +1
            dasom++;
            // 조작표 수 기록
            cnt++;
        }
        System.out.println(cnt);
    }
}
