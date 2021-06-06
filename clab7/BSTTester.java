public class BSTTester {
    public static void main(String[] args) {
        BST<Character> tree = new BST<>();
        tree.add('k');
        tree.add('e');
        tree.add('b');
        tree.add('a');
        tree.add('d');
        tree.add('g');
        tree.add('f');
        tree.add('j');
        tree.add('v');
        tree.add('p');
        tree.add('y');
        tree.add('r');
        tree.add('s');

        System.out.println(tree.getDepth('s'));
        System.out.println(tree.keySet());
        double avgDepth = tree.avgDepth();
        System.out.println(avgDepth);


    }
}
