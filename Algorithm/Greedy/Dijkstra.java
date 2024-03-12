package greedy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Dijkstra {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] info = br.readLine().split(" ");
        // 노드와 간선의 갯수
        int nodes = Integer.parseInt(info[0]);
        int edges = Integer.parseInt(info[1]);
        // 출발 지점
        int start = Integer.parseInt(br.readLine());

        // 1. 인접행렬 초기화
        int[][] adjMat = new int[nodes][nodes];
        for (int[] row : adjMat) {
            // 일단 다 연결 안되어 있다고 초기화
            Arrays.fill(row, -1);
        }

        // 각 간선 정보 받기
        for (int i = 0; i < edges; i++) {
            String[] edge = br.readLine().split(" ");
            int from = Integer.parseInt(edge[0]);
            int to = Integer.parseInt(edge[1]);
            int cost = Integer.parseInt(edge[2]);

            adjMat[from][to] = cost;
        }
   /*     for (int[] row:adjMat) {
            System.out.println(Arrays.toString(row));
        }*/

        // 1. 정점에 도달했는지 여부를 확인하는 boolean[].
        // 시작부터 도달하는데 걸리는 최소거리를 저장하는 int[]
        boolean[] visited = new boolean[nodes];
        int[] dist = new int[nodes];
        // 2. dist의 모든 값을 무한(Integer.MAX_VALUE)로하고,
        // 출발 지점은 0으로 한다.
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        // 3. Dijkstra Algorithm
        // 모든 노드를 방문하면 종료되기 때문에, 노드의 갯수만큼만 반복
        for (int i = 0; i < nodes; i++) {
            // 3-1. 현재 알고있는 거리 비용 중 최단 비용 노드를 선택한다.
            int minDist = Integer.MAX_VALUE;
            int minDistNode = -1;
            for (int j = 0; j < nodes; j++) {
                // 3-1-1. 방문하지 않았고, 거리 비용이 최소인지를 확인한다.
                if (!visited[j] && dist[j] < minDist){
                    // 3-1-2. 해당 사실을 기록한다.
                    minDist = dist[j];
                    minDistNode = j;
                }
            }
            // 3-2. 해당 노드를 방문처리한다.
            visited[minDistNode] = true;

            // 3-3. 해당 노드에서 도달할 수 있는 노드들의 최단거리를 갱신한다.
            for (int j = 0; j < nodes; j++) {
                // 3-3-1. 인접하지 않았으면 스킵
                if (adjMat[minDistNode][j] == -1) continue;
                // 3-3-2. 현재 노드(minDistNode)에서 인접 노드(j)까지 걸리는 비용을 가져오고
                int cost = adjMat[minDistNode][j];

                // 3-3-3. 인접 노드의 현재 최소비용 (dist[j])
                //  vs 현재 노드(dist[minDistNode]) 까지의 거리 + 현재노드 ~ 인접 노드 까지의 거리(cost)
                if (dist[j] > dist[minDistNode] + cost) {
                    dist[j] = dist[minDistNode] + cost;
                }
            }
        }
        // 4. 최종적으로 각 정점에 도달하는데 걸리는 비용을 출력해보자
        System.out.println(Arrays.toString(dist));
    }
}
/*
// 정점과 간선의 갯수
// 출발 지점
6 8
0
0 1 10
0 2 15
1 3 12
1 5 15
2 4 10
3 4 2
3 5 1
5 4 5
 */