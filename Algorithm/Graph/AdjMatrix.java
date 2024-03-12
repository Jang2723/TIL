package graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class AdjMatrix { // 인접행렬
    public static void dfsAdjMatrix() throws IOException  {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] info = br.readLine().split(" ");
        int nodes = Integer.parseInt(info[0]);
        int edges = Integer.parseInt(info[1]);
        // 인접 행렬 : 정점들의 갯수를 n이라고 하면 n * n 이차원 배야ㅕㄹ로 그래프의 연결 관계를 표현하는 방법
        int[][] adjMat = new int[nodes][nodes];
        // 간선의 갯수만큼 반복해서
        for (int i = 0; i < edges; i++) {
            // 간선의 정보를 받는다.
            String[] edgeInfo = br.readLine().split(" ");
            // 연결된 두 정점을 찾는다.
            int leftNode = Integer.parseInt(edgeInfo[0]);
            int rightNode = Integer.parseInt(edgeInfo[1]);
            // 인접 행렬에 인접했다고 기록한다.
            adjMat[leftNode][rightNode] = 1;
            adjMat[rightNode][leftNode] = 1;
        }

        // 다음 방문할 곳들을 기록하기 위한 Stack을 만든다.
        Stack<Integer> toVisit = new Stack<>();
        // 방문한 순서를 기록하기 위한 List를 만든다.
        List<Integer> visitedOrder = new ArrayList<>();
        // 방문했다는 사실을 기록하기 위한 boolean[]을 만든다.
        boolean[] visited = new boolean[nodes];

        // 1. 제일 처음 방문할 곳은 0이다.
        toVisit.push(0);
        // 2. 스택이 빌때까지 반복한다.
        while (!toVisit.empty()) {
            // 1. 이번에 방문할 곳을 받아온다.
            int next =toVisit.pop();
            // 2. 만약 방문한 적이 있디면, 스킵한다.
            if (visited[next]) continue;
            // 3. 방문한 적이 없다면, 방문했다고 표시한다.
            visited[next] = true;
            visitedOrder.add(next);
            // 4. 다음에 방문할 곳을 찾아본다.
            // 정점을 갯수만큼 확인한다.
//            for (int i = 0; i < nodes; i++) {
            for (int i = nodes - 1; i >= 0 ; i--) {
                // 해당 정점에 도달할 수 있는지를 확인한다.
                if (adjMat[next][i] == 1 && !visited[i]) {
                    // 있다면 스택에 푸시한다.
                    toVisit.push(i);
                }
            }
        }
        // 결과를 출력한다.
        System.out.println(visitedOrder);
    }

    public static void dfsRecursive() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] info = br.readLine().split(" ");
        int nodes = Integer.parseInt(info[0]);
        int edges = Integer.parseInt(info[1]);
        // 인접 행렬 : 정점들의 갯수를 n이라고 하면 n * n 이차원 배야ㅕㄹ로 그래프의 연결 관계를 표현하는 방법
        int[][] adjMat = new int[nodes][nodes];
        // 간선의 갯수만큼 반복해서
        for (int i = 0; i < edges; i++) {
            // 간선의 정보를 받는다.
            String[] edgeInfo = br.readLine().split(" ");
            // 연결된 두 정점을 찾는다.
            int leftNode = Integer.parseInt(edgeInfo[0]);
            int rightNode = Integer.parseInt(edgeInfo[1]);
            // 인접 행렬에 인접했다고 기록한다.
            adjMat[leftNode][rightNode] = 1;
            adjMat[rightNode][leftNode] = 1;
        }

        List<Integer> visitedOrder = new ArrayList<>();
        boolean[] visited = new boolean[nodes];
        // 0을 시작으로 재귀호출한다.
        recursive(0, nodes, adjMat, visited, visitedOrder);
        // 결과 확인
        System.out.println(visitedOrder);
    }

    // 재귀호출로 DFS 구현해보기
    private static void recursive(
            // 이번 호출에서 방문할 정점
            int next,
            // 총 정점의 갯수
            int nodes,
            // 인접 행렬 정보
            int[][] adjMat,
            // 방문한 정점들에 대한 정보
            boolean[] visited,
            // 방문할 순서를 기록하기 위한 리스트
            List<Integer> visitOrder
    ) {
//        // 이미 방문한 점이면 돌아간다.
//        if (visited[next]) return;
        // 방문했다고 표시한다.
        visited[next] = true;
        visitOrder.add(next);
        // 다음 방문할 점들을 선별한다.
        for (int i = 0; i < nodes; i++) {
            if (adjMat[next][i] == 1 & !visited[i]) {
                // 재귀호출한다.
                recursive(i, nodes, adjMat, visited, visitOrder);
            }
        }
    }

    public static void bfsAdjMatrix() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] info = br.readLine().split(" ");
        int nodes = Integer.parseInt(info[0]);
        int edges = Integer.parseInt(info[1]);
        // 인접 행렬 : 정점들의 갯수를 n이라고 하면 n * n 이차원 배야ㅕㄹ로 그래프의 연결 관계를 표현하는 방법
        int[][] adjMat = new int[nodes][nodes];
        // 간선의 갯수만큼 반복해서
        for (int i = 0; i < edges; i++) {
            // 간선의 정보를 받는다.
            String[] edgeInfo = br.readLine().split(" ");
            // 연결된 두 정점을 찾는다.
            int leftNode = Integer.parseInt(edgeInfo[0]);
            int rightNode = Integer.parseInt(edgeInfo[1]);
            // 인접 행렬에 인접했다고 기록한다.
            adjMat[leftNode][rightNode] = 1;
            adjMat[rightNode][leftNode] = 1;
        }

        // DFS에서 사용하던 Stack 대신 Queue를 사용한다.
        Queue<Integer> toVisit = new LinkedList<>();
        List<Integer> visitedOrder = new ArrayList<>();
        // 방문한 정보를 저장한다.
        boolean[] visited = new boolean[nodes];
        // 첫 방문 대상은 0
        toVisit.add(0);
        // 큐가 빌떄까지 반복한다.
        while (!toVisit.isEmpty()) {
            // 이번에 방문할 곳을 받아온다.
            int next = toVisit.poll();
            // 만약 방문했다면 스킵
            if (visited[next]) continue;

            // 방문했다고 표시한다.
            visited[next] = true;
            visitedOrder.add(next);

            // 다음 방문할 정점을 확인한다.
            for (int i = 0; i < nodes; i++) {
                if (adjMat[next][i] == 1 &&!visited[i]) {
                    toVisit.offer(i);
                }
            }
        }
        System.out.println(visitedOrder);
    }

    public static void main(String[] args) throws IOException {
        /*
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] info = br.readLine().split(" ");
        int nodes = Integer.parseInt(info[0]);
        int edges = Integer.parseInt(info[1]);
        // 인접 행렬 : 정점들의 갯수를 n이라고 하면 n * n 이차원 배야ㅕㄹ로 그래프의 연결 관계를 표현하는 방법
        int[][] adjMat = new int[nodes][nodes];
        // 간선의 갯수만큼 반복해서
        for (int i = 0; i < edges; i++) {
            // 간선의 정보를 받는다.
            String[] edgeInfo = br.readLine().split(" ");
            // 연결된 두 정점을 찾는다.
            int leftNode = Integer.parseInt(edgeInfo[0]);
            int rightNode = Integer.parseInt(edgeInfo[1]);
            // 인접 행렬에 인접했다고 기록한다.
            adjMat[leftNode][rightNode] = 1;
            adjMat[rightNode][leftNode] = 1;
        }

        // 결과를 출력해보자
        for (int i = 0; i < nodes; i++) {
            System.out.println(Arrays.toString(adjMat[i]));
        }
        */
        // dfsAdjMatrix();
//        dfsRecursive();
        bfsAdjMatrix();
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