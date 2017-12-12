import java.util.Scanner;
public class MinPQ{

  private class Node {
    private Node parent;
    private Node left;
    private Node right;
    private int info;

    Node(int info) {
        this.info = info;
        this.parent = null;
        this.right = null;
        this.left = null;
    }
  }

  private Node root; // Keeps track of the top most Node in the tree.
  private Node last; // Keeps track of the most current Node added.
  int N = 0;
  private int counter;
  private int MSB;


  public MinPQ(){ // Constructor function.
    root = null;
  }

  public boolean isEmpty() {
    return N == 0;
  }

//-------------------------------Insertion Function O(log(n)) runtime---------------//
  public void insert(int info){
    Node temp = new Node(info);
    if (this.isEmpty()) { // First item is always the root.
      this.root = temp;
    }
    else{
      MSB = 0;
      String binary = Integer.toBinaryString(N+1);
      insert(root, temp, binary); // This function makes sure the tree is balanced.
    }
    N++;
  }

  public void insert(Node x, Node temp, String binary){
    char index = binary.charAt(0);
    int bit = Character.getNumericValue(index);

    if (x.left == null || x.right == null){
      if (x.left == null){
        x.left = temp;
        temp.parent = x;
        last = temp;
      }

      else if (x.right == null){
        x.right = temp;
        temp.parent = x;
        last = temp;
      }
    }

    else if (bit == 0 && MSB == 0){
      insert(x, temp, binary.substring(1));
    }

    else if (bit == 1 && MSB == 0){
      MSB = MSB + 1;
      insert(x, temp, binary.substring(1));
    }

    else if (bit == 0 && MSB != 0){
      insert(x.left, temp, binary.substring(1));
    }

    else if (bit == 1 && MSB != 0){
      insert(x.right, temp, binary.substring(1));
    }

  }
//--------------------------------------------------------------------------------//


  public void show(){
    String str = "Pre Order Traversal";
    String dashes = "_";
    String star = "*";
    for (int n = 1; n < str.length(); n++) {
      dashes = dashes + "_";
      star = star + "*";
    }
    System.out.println(star);
    System.out.println("Pre Order Traversal");
    System.out.println(dashes);
    System.out.println();
    show(root);
    System.out.println(dashes);
    System.out.println(star);

  }

  public int min(){
    return root.info;
  }

  public int delete(){
    root.info = last.info; // Switches the last node with the first node.
    if (last.parent.right.info == last.info) {last.parent.right = null;} // Need to delete the last node. The only way to do is to make the pointer of the parent point to null.
    else if (last.parent.left.info == last.info) {last.parent.left = null;}
    sink(root);
    return root.info;
  }

  public int size(){
    return N;
  }


//-----------------------------Helper Functions---------------------------------//


  private void show(Node toShow){ // Just a simple Pre Order Traversal.
    if (toShow == null) return;
    else{
      if (toShow.parent != null){
        if (toShow.info == toShow.parent.left.info){System.out.println(toShow.info + ": Left Child of " + toShow.parent.info);}
        else {System.out.println(toShow.info + ": Right Child of " + toShow.parent.info);}
      }
      else {System.out.println(root.info + ": Root");}
      show(toShow.left);
      show(toShow.right);
    }
  }


  public void swim(Node x){ // Recursive function that helps the minimum value move up the tree.
    if (x.parent == null) return;
    else if (x.info < x.parent.info){
      swap(x, x.parent);
      swim(x.parent);
    }
  }

  public void sink(Node x){
    if (x.left == null && x.right == null) return;
    else if(x.info < x.left.info && x.info < x.right.info) return;
    else {
      if (x.left.info < x.right.info){
        swap(x, x.left);
        sink(x.left);
      }
      else if (x.left.info > x.right.info){
        swap(x, x.right);
        sink(x.right);
      }
      else if (x.right == null && x.info > x.left.info){
        swap(x, x.left);
        return;
      }
    }
  }


  public void swap(Node x, Node y){ // Swapping function.
    int xTemp = x.info;
    int yTemp = y.info;

    x.info = yTemp;
    y.info = xTemp;
  }

//------------------------------------------------------------------------------//

  public static void main(String[] args) {
    MinPQ heap = new MinPQ();

    for (int n = 1; n < 8; n++) {
      heap.insert(n);
    }

    System.out.println(heap.isEmpty());

    heap.show();
    System.out.println(heap.delete());
    heap.show();

    // Test Cases



  }
}
