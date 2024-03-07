public class BinarySearchTree {
    // 트리의 노드를 나타내는 클래스
    private static class Node {
        // 담고있는 데이터
        int key;
        // 왼쪽 자식
        Node left;
        // 오른쪽 자식
        Node right;

        public Node(int key) {
            this.key = key;
            left = null;
            right = null;
        }
    }
    // 루트 노드
    private Node root;

    // 데이터 추가
    // 데이터를 탐색해서 실패한 위치까지 가서 데이터(새로운 노드)를 추가해준다.
    public void insert(int key) {
        root = insertNode(root, key);
    }

    private Node insertNode(Node node, int key) {
        // 탐색 실패하는 경우 새 노드를 만든다.
        if (node == null) {
            node = new Node(key);
            return node;
        }

        // 현재 노드보다 데이터가 작을경우 왼쪽 노드를 확인한다.
        if (key < node.key) {
            node.left = insertNode(node.left, key);
        }
        // 현재 노드보다 데이터가 클 경우 오른쪽 노드를 확인한다.
        else if (key > node.key) {
            node.right = insertNode(node.right, key);
        }

        return node;
    }

    // 탐색
    public boolean search(int key) {
        return searchNode(root, key);
    }

    private boolean searchNode(Node node, int key) {
        // 현재 노드가 없을 경우 탐색 실패
        if (node == null) {
            return false;
        }

        // 현재 노드의 데이터와 일치하는 경우 탐색 성공
        if (node.key == key) {
            return true;
        }

        // 현재 노드보다 데이터가 작을경우 왼쪽으로
        if (key < node.key) {
            return searchNode(node.left, key);
        }
        // 현재 노드보다 데이터가 클 경우 오른쪽으로
        else {
            return searchNode(node.right, key);
        }
    }

    // 중위순회시 오름차순으로 데이터가 출력된다.
    public void traverseInorder() {
        inorder(root);
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.key + " ");
            inorder(node.right);
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        bst.insert(50);
        bst.insert(30);
        bst.insert(70);
        bst.insert(20);
        bst.insert(40);
        bst.insert(60);
        bst.insert(80);

        bst.traverseInorder();
        System.out.println();
        System.out.println(bst.search(40));
        System.out.println(bst.search(85));
        System.out.println(bst.search(30));

    }
}