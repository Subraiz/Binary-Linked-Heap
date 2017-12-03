public class MinPQ{

  private class Node {
    private Node parent;
    private Node leftChild;
    private Node rightChild;
    private int info;
    private int id;

    Node(int info, int id) {
        this.info = info;
        this.parent = null;
        this.rightChild = null;
        this.leftChild = null;
        this.id = id;
    }
  }

  private Node root; // Keeps track of the top most Node in the tree.
  private Node last; // Keeps track of the most current Node added.
  int N = 0;
  private int counter;


  public MinPQ(){ // Constructor function.
    root = null;
  }

  public boolean isEmpty() {
    return N == 0;
  }

  public void insert(int info){
    Node temp = new Node(info, N+1);
    if (this.isEmpty()) { // First item is always the root.
      this.root = temp;
    }
    else{
      balance(temp, root); // This function makes sure the tree is balanced.
    }
    N++;
  }

  public void balance(Node key, Node target){ // Assign each Node an id corresponding to where it should be in an array.

    if (key.id == 2*target.id || (key.id == (2*target.id + 1))){
      if (key.id == 2*target.id){
        target.leftChild = key;
        key.parent = target;
        last = key;
      }

      else {
        target.rightChild = key;
        key.parent = target;
        last = key;
      }

      if (key.info < target.info) {
        swim(key);
        return;
      }
    }

    else if (target.leftChild == null || target.rightChild == null) return; // Recursively go through the linked list until the right Node is found.
    else {
      balance(key, target.leftChild);
      balance(key, target.rightChild);
    }
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

  public int size(){
    return N;
  }


//-----------------------------Helper Functions---------------------------------//


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
    MinPQ heap = new MinPQ();

    for (int n = 1; n < 20; n++) {
      heap.insert(n);
    }

    System.out.println(heap.size());
    heap.show();

    // Test Cases



  }
}
