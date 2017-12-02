public class linkedheap{

  private class Node {
    private Node parent;
    private Node leftChild;
    private Node rightChild;
    private int info;

    Node(int info) {
        this.info = info;
        this.parent = null;
        this.rightChild = null;
        this.leftChild = null;
    }
  }

  private Node root; // Keeps track of the top most Node in the tree.
  private Node last; // Keeps track of the most current Node added.
  int N = 0;
  int depth = 1;


  public linkedheap(){ // Constructor function.
    root = null;
    last = root;
  }

  public boolean isEmpty() {
    return N == 0;
  }

  public int size(){
    return N;
  }

  public void insert(int info){
    Node temp = new Node(info);
    if (this.isEmpty()) { // First item is always the root.
      this.root = temp;
    }
    else{
      balance(temp, root, N, depth); // This function makes sure the tree is balanced.
    }
    N++;
  }

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

  public void delMin(){
    root.info = last.info; // Switches the last node with the first node.
    if (last.parent.rightChild.info == last.info) {last.parent.rightChild = null;} // Need to delete the last node. The only way to do is to make the pointer of the parent point to null.
    else if (last.parent.leftChild.info == last.info) {last.parent.leftChild = null;}
    sink(root);
  }

//-----------------------------Helper Functions---------------------------------//

  public void balance(Node key, Node target, int size, int depth){ // Assign each Node an id corresponding to where it should be in an array.
    if (target == null) {key = target;}
    if ((size + 1) == Math.pow(2, depth)){depth++;}
    else {
      if ((Math.pow(2, depth) - size - 1) > Math.pow(depth - 2, 2)){
        balance(key, target.leftChild, Math.ceil(size/2), (depth - 1));
      }
      else if ((Math.pow(2, depth) - size - 1) <= Math.pow(depth - 2, 2)){
        balance(key, target.rightChild, Math.ceil(size/2), (depth - 1));
      }
    }
  }

  private void show(Node toShow){ // Just a simple Pre Order Traversal.
    if (toShow == null) return;
    else{
      if (toShow.parent != null){
        if (toShow.info % 2 == 0){System.out.println(toShow.info + ": Left Child of " + toShow.parent.info);}
        else {System.out.println(toShow.info + ": Right Child of " + toShow.parent.info);}
      }
      else {System.out.println(root.info + ": Root");}
      show(toShow.leftChild);
      show(toShow.rightChild);
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
    if (x.leftChild == null && x.rightChild == null) return;
    else if(x.info < x.leftChild.info && x.info < x.rightChild.info) return;
    else {
      if (x.leftChild.info < x.rightChild.info){
        swap(x, x.leftChild);
        sink(x.leftChild);
      }
      else if (x.leftChild.info > x.rightChild.info){
        swap(x, x.rightChild);
        sink(x.rightChild);
      }
      else if (x.rightChild == null && x.info > x.leftChild.info){
        swap(x, x.leftChild);
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
    linkedheap heap = new linkedheap();

    for (int n = 1; n < 20; n++) {
      heap.insert(n);
    }

    heap.show();

    // Test Cases
    System.out.println();
    System.out.println("The size of the heap is: " + heap.size());
    System.out.println("The minimum value is: " + heap.min());
    System.out.println("Is the heap empty: " + heap.isEmpty());
    System.out.println();



  }
}
