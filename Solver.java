import java.util.ArrayList;
import java.util.List;
class Node {
    String name;
    boolean empty = false;
    int x;
    int y;
    public Node(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }
}

class Solver {
    static List<Node> nodes = new ArrayList<>();
    static int best = 0;
    static int total = 0;
    static int optimal = 0;
    static List<StringBuilder> boardMoves = new ArrayList<>();
    static List<StringBuilder> bestMoves = new ArrayList<>();
    
    private static Node findNode(int x, int y) {
        // TODO: Cache this
        for (Node node : nodes) {
            if (node.x == x && node.y == y) {
                return node;
            }
        }
        return null;
    }
    
    private static boolean isValid(Node st, Node inter, Node dest) {
        return (inter != null && 
            dest != null && 
            !inter.empty &&
            dest.empty &&
            !st.empty);
    }
    
    private static List<List<Node>> findValidMoves() {
        List<List<Node>> validMoves = new ArrayList<>();
        // [source, intermediate, destination]
        for (Node node : nodes) {
            int currX = node.x;
            int currY = node.y;
            // downwards
            int leftX1 = currX + 1;
            int leftY1 = currY - 1;
            int leftX2 = currX + 2;
            int leftY2 = currY - 2;
            
            int rightX = currX + 1;
            int rightY = currY + 1;
            int rightX2 = currX + 2;
            int rightY2 = currY + 2;
            
            Node leftNode1 = findNode(leftX1, leftY1);
            Node leftNode2 = findNode(leftX2, leftY2);
            if (isValid(node, leftNode1, leftNode2))
                validMoves.add(List.of(node, leftNode1, leftNode2));
            
            Node rightNode1 = findNode(rightX, rightY);
            Node rightNode2 = findNode(rightX2, rightY2);
            if (isValid(node, rightNode1, rightNode2))
                validMoves.add(List.of(node, rightNode1, rightNode2));
            
            // upwards
            leftX1 = currX - 1;
            leftY1 = currY - 1;
            leftX2 = currX - 2;
            leftY2 = currY - 2;
            
            rightX = currX - 1;
            rightY = currY + 1;
            rightX2 = currX - 2;
            rightY2 = currY + 2;
            
            leftNode1 = findNode(leftX1, leftY1);
            leftNode2 = findNode(leftX2, leftY2);
            if (isValid(node, leftNode1, leftNode2))
                validMoves.add(List.of(node, leftNode1, leftNode2));
                
            rightNode1 = findNode(rightX, rightY);
            rightNode2 = findNode(rightX2, rightY2);
            if (isValid(node, rightNode1, rightNode2))
                validMoves.add(List.of(node, rightNode1, rightNode2));
                
            // left
            leftX1 = currX;
            leftY1 = currY - 2;
            leftX2 = currX;
            leftY2 = currY - 4;
            
            rightX = currX;
            rightY = currY + 2;
            rightX2 = currX;
            rightY2 = currY + 4;
            
            leftNode1 = findNode(leftX1, leftY1);
            leftNode2 = findNode(leftX2, leftY2);
            if (isValid(node, leftNode1, leftNode2))
                validMoves.add(List.of(node, leftNode1, leftNode2));
                
            rightNode1 = findNode(rightX, rightY);
            rightNode2 = findNode(rightX2, rightY2);
            if (isValid(node, rightNode1, rightNode2))
                validMoves.add(List.of(node, rightNode1, rightNode2));
        }
        return validMoves;
    }
    
    private static void assess(List<StringBuilder> board, List<StringBuilder> moves) {
        int numEmpty = 0;
        for (Node node : nodes) {
            if (node.empty) numEmpty++;
        }
        if (numEmpty == 14) optimal++;
        if (numEmpty > best && numEmpty == 14) {
            best = numEmpty;
            for (StringBuilder sb : board) {
                boardMoves.add(new StringBuilder(sb.toString()));
            }
            for (StringBuilder sb : moves) {
                bestMoves.add(new StringBuilder(sb.toString()));
            }
        }
        best = Math.max(best, numEmpty);
    }
    
    private static void play(List<StringBuilder> pMoves, List<StringBuilder> moves) {
        List<List<Node>> validMoves = findValidMoves();
        if (validMoves.isEmpty()) total++;
        
        for (List<Node> route : validMoves) {
            // take a move
            for (Node node : route) node.empty = !node.empty;
            moves.add(new StringBuilder(route.get(0).name + "-->" + route.get(2).name));
            List<StringBuilder> board = getBoard();
            pMoves.addAll(board);
            assess(pMoves, moves);
            play(pMoves, moves);
            // undo the move
            for (Node node : route) node.empty = !node.empty;
            pMoves.removeAll(board);
            moves.remove(moves.size() - 1);
        }
    }
    
    private static void printGameBoard(List<StringBuilder> board) {
        for (StringBuilder sb: board) {
            System.out.println(sb.toString());
        }
    }
    private static List<StringBuilder> getBoard() {
        List<StringBuilder> board = new ArrayList<>();
        board.add(new StringBuilder("         "));
        board.add(new StringBuilder("         "));
        board.add(new StringBuilder("         "));
        board.add(new StringBuilder("         "));
        board.add(new StringBuilder("         "));
        for (Node node : nodes) {
            StringBuilder sb = board.get(node.x);
            sb.setCharAt(node.y, node.empty ? 'O' : 'X');
        }
        return board;
    }
    
    
    public static void main(String[] args) {
        nodes.add(new Node("A1", 0, 4));
        nodes.add(new Node("B1", 1, 3));
        nodes.add(new Node("B2", 1, 5));
        nodes.add(new Node("C1", 2, 2));
        nodes.add(new Node("C2", 2, 4));
        nodes.add(new Node("C3", 2, 6));
        nodes.add(new Node("D1", 3, 1));
        nodes.add(new Node("D2", 3, 3));
        nodes.add(new Node("D3", 3, 5));
        nodes.add(new Node("D4", 3, 7));
        nodes.add(new Node("E1", 4, 0));
        nodes.add(new Node("E2", 4, 2));
        nodes.add(new Node("E3", 4, 4));
        nodes.add(new Node("E4", 4, 6));
        nodes.add(new Node("E5", 4, 8));
        for (Node node : nodes) {
            node.empty = true;
            System.out.println("Emptying node: " + node.name);
            try {
                play(new ArrayList<>(), new ArrayList<>());
            } catch (Exception e) {
                System.out.println("error");
            }
            System.out.println("Best score from ( " + node.name + " ): " + best);
            best = 0;
            node.empty = false;
        }
        
        System.out.println("Possible perfect games: " + optimal + " / " + total);
        for (StringBuilder sb : bestMoves) {
            System.out.println(sb.toString());
        }
        printGameBoard(boardMoves);
    }
}