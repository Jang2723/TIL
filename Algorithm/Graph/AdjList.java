package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AdjList {  // 인접 리스트  : 각 정점이 도달할 수 있는 정점들을 리스트로 저장하는 방식

    public static void dfsAdjList() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] info = reader.readLine().split(" ");
        int nodes = Integer.parseInt(info[0]);
        int edges = Integer.parseInt(info[1]);

        List<List<Integer>> adjList = new ArrayList<>();
        // 내부에 빈 리스트를 먼저 만들어준다.
        for (int i = 0; i < nodes; i++) {
            adjList.add(new ArrayList<>());
        }

        // 간선의 정보를 입력받는다.
        for (int i = 0; i < edges; i++) {
            String[] edgeInfo = reader.readLine().split(" ");
            int leftNode = Integer.parseInt(edgeInfo[0]);
            int rightNode = Integer.parseInt(edgeInfo[1]);
            adjList.get(leftNode).add(rightNode);
            adjList.get(rightNode).add(leftNode);
        }

        Stack<Integer> toVisit = new Stack<>();
        List<Integer> visitedOrder = new ArrayList<>();
        boolean[] visited = new boolean[nodes];
        int next = 0;
        toVisit.push(next);
        while (!toVisit.empty()) {
            next = toVisit.pop();
            if (visited[next]) continue;

            visited[next] = true;
            visitedOrder.add(next);

            List<Integer> adjRow = adjList.get(next);
            for (Integer canVisit: adjRow) {
                if (!visited[canVisit])
                    toVisit.push(canVisit);
            }
        }

        // 출력
        System.out.println(visitedOrder);
    }

    public static void bfsAdjList() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] info = reader.readLine().split(" ");
        int nodes = Integer.parseInt(info[0]);
        int edges = Integer.parseInt(info[1]);

        List<List<Integer>> adjList = new ArrayList<>();
        // 내부에 빈 리스트를 먼저 만들어준다.
        for (int i = 0; i < nodes; i++) {
            adjList.add(new ArrayList<>());
        }

        // 간선의 정보를 입력받는다.
        for (int i = 0; i < edges; i++) {
            String[] edgeInfo = reader.readLine().split(" ");
            int leftNode = Integer.parseInt(edgeInfo[0]);
            int rightNode = Integer.parseInt(edgeInfo[1]);
            adjList.get(leftNode).add(rightNode);
            adjList.get(rightNode).add(leftNode);
        }

        Queue<Integer> toVisit = new LinkedList<>();
        List<Integer> visitedOrder = new ArrayList<>();
        boolean[] visited = new boolean[nodes];
        // 첫 방문 대상
        int next = 0;
        // 큐에 넣어둔다.
        toVisit.offer(next);
        // 큐가 차있는 동안 반복한다.
        while (!toVisit.isEmpty()) {
            // 다음 방문할 곳을 가져온다.
            next = toVisit.poll();
            // 이미 방문했다면 다음 반복으로
            if (visited[next]) continue;

            // 방문했다 표시
            visited[next] = true;
            // 방문한 순서 기록
            visitedOrder.add(next);

            // 다음 방문 대상을 검색한다.
            List<Integer> adjRow = adjList.get(next);
            for (Integer canVisit: adjRow) {
                if (!visited[canVisit])
                    toVisit.offer(canVisit);
            }
        }

        // 출력
        System.out.println(visitedOrder);
    }

    public static void main(String[] args) throws IOException {
        dfsAdjList();
//        bfsAdjList();
    }
}
/*
입력 예제
정점의 개수 | 간선의 개수
8 10
0 1
0 2
0 3
1 3
1 4
2 5
3 4
4 7
5 6
6 7
 */