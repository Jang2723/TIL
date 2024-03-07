public class TreeArray {
    // 이진 트리는 배열로 표현이 가능하다.

    // 총 노드의 갯수
    private int nodes;

    // 실제 트리를 담고 있는 배열
    private int[] arr;

    // 배열을 담는다.
    public void setArr(int[] arr) {
        this.arr = arr;
        this.nodes = arr.length;
    }

    // 전위순회 : VLR
    // node : 현재 트리의 루트 노드 index
    public void traversePreorder(int node) {
        // node가 배열을 벗어나지 않고,
        // 데이터가 저장되어 있다. (0이 아니다)
        if (node < nodes && arr[node] != 0) {
            // V 방문
            System.out.print(arr[node] + ", ");
            // L 방문
            this.traversePreorder(node * 2 );
            // R 방문
            this.traversePreorder(node * 2 + 1);
        }
    }

    // 중위순회 : LVR
    public void traverseInorder(int node) {
        if (node < nodes && arr[node] != 0) {
            // L 방문
            this.traverseInorder(node * 2 );
            // V 방문
            System.out.print(arr[node] + ", ");
            // R 방문
            this.traverseInorder(node * 2 + 1);
        }
    }

    // 후위순회: LRV
    public void traversePostorder(int node) {
        if (node < nodes && arr[node] != 0) {
            // L 방문
            this.traversePostorder(node * 2 );
            // R 방문
            this.traversePostorder(node * 2 + 1);
            // V 방문
            System.out.print(arr[node] + ", ");
        }
    }

    public static void main(String[] args) {
        TreeArray tree = new TreeArray();
        tree.setArr(new int[] {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15
        });
        // 트리의 루트 노드는 1이다.
        tree.traversePreorder(1);
        System.out.println();
        tree.traverseInorder(1);
        System.out.println();
        tree.traversePostorder(1);
    }
}
