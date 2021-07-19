public class RedBlackTree {
    private RBNode root;
    private int height;

    public RedBlackTree(){
        root = null;
        height = 0;
    }

    /**
     * Insert() Inserts a new node into the RB tree
     * @param data the number to be added
     */
    public void insert(int data){
        RBNode newNode = new RBNode(data);
        //System.out.println("Adding " + data);
        if(root != null){
            root = BSTInsert(null, root, newNode);
            RBInsertHelp(newNode);
        }else{ //root is null
            root = newNode;
            root.setColor('B');  
            root.setParent(null);
        }
        height = determineHeight(root);
    }

    /**
     * RBInsertHelp() fixes the tree starting with the newNode being added. 
     * Runs through each case. Rule 1, Rule 2 LLR LRR RRR RLR
     * @param currNode the new node that needs to be checked. 
     */
    private void RBInsertHelp(RBNode currNode){
        if(currNode == root){
            currNode.setColor('B');
            return;
        }

        RBNode parent = currNode.getParent();
        RBNode grandParent = parent.getParent();
        RBNode uncle = findUncle(parent, grandParent);

        if(parent.getColor() != 'B'){
            if(uncle != null && uncle.getColor() == 'R'){
                uncle.flipColor();
                parent.flipColor();
                grandParent.flipColor();    
                RBInsertHelp(grandParent);
            }else{ //uncle is black node
                //4 cases

                //left subtree
                if(grandParent.getLeftChild() == parent){
                    //left left case
                    if(parent.getLeftChild() == currNode){
                        leftLeftRotationCase(currNode, parent, grandParent);
                    }
                    //left right case
                    else if(parent.getRightChild() == currNode){ 
                        parent = rotateLeft(parent);
                        currNode = parent.getLeftChild();
                        grandParent.setLeftChild(parent);
                        leftLeftRotationCase(currNode, parent, grandParent);
                    }
                }

                else if(grandParent.getRightChild() == parent){
                    //right right case
                    if(parent.getRightChild() == currNode){ 
                        rightRightRotationCase(currNode, parent, grandParent);
                    }
                    //right left case
                    if(parent.getLeftChild() == currNode){ 
                        parent = rotateRight(parent);
                        currNode = parent.getRightChild();
                        grandParent.setRightChild(parent);
                        rightRightRotationCase(currNode, parent, grandParent);
                    }
                }
            }
        }
        
    }

    /**
     * Right Right rotation case. The currnode is the right child of the parent. there needs to be a left
     * rotation on the grandparent
     * @param currNode currNode
     * @param parent Parent node
     * @param grandParent grandparent node
     */
    private void rightRightRotationCase(RBNode currNode, RBNode parent, RBNode grandParent){
        if(grandParent != root){
            RBNode greatGrandParent = grandParent.getParent();
            if(determineLeftRightChild(grandParent, greatGrandParent) == 'L')
                greatGrandParent.setLeftChild(rotateLeft(grandParent));
            else if(determineLeftRightChild(grandParent, greatGrandParent) == 'R')
                greatGrandParent.setRightChild(rotateLeft(grandParent));

            parent.setParent(greatGrandParent);
        }else
            rotateLeft(grandParent);
            
        if(grandParent == root){
            root = parent;
            parent.setParent(null);

        }
        parent.flipColor();
        grandParent.flipColor();
    }

    /**
     * Left Left rotation case. When the currNode is the left child of the parent
     * and they are both red and there needs to be a right rotation at the grandparent level
     * @param currNode currNode 
     * @param parent parent node
     * @param grandParent grandparent node
     */
    private void leftLeftRotationCase(RBNode currNode, RBNode parent, RBNode grandParent){
        if(grandParent != root){ //if grandparent is not the root node then..
            RBNode greatGrandParent = grandParent.getParent(); //get its parent, CurrNode's greatGrandparent
            if(determineLeftRightChild(grandParent, greatGrandParent) == 'L') //if grandparent is the left child of ggrandparent
                greatGrandParent.setLeftChild(rotateRight(grandParent)); //set greatGrandparents left child to the 'new' parent
            else if(determineLeftRightChild(grandParent, greatGrandParent) == 'R') //if grandparent is the right child
                greatGrandParent.setRightChild(rotateRight(grandParent)); //set greatGrandparents right child to the 'new' parent

            parent.setParent(greatGrandParent); //set the 'new' parents parent to greatGrandparents
        }else{ //if the grandparent is the root
            rotateRight(grandParent); 
        }
        if(grandParent == root){
            root = parent;
            parent.setParent(null);
        }
        parent.flipColor();
        grandParent.flipColor();
    }

    /**
     * Determines whether the parent is the left or right child of the grandparent
     * @param parent the parent node
     * @param grandParent the grandparent node
     * @return returns 'L' if parent is the left child , 'R' if right child, ' ' if neither
     */
    private char determineLeftRightChild(RBNode parent, RBNode grandParent){
       if(grandParent.getLeftChild() == parent){
           return 'L';
       }else if(grandParent.getRightChild() == parent){
           return 'R';
       }
       return ' ';
   }

   /**
    * Finds the uncle of the child
    * @param child node that unlce is being found for
    * @param parent Parent of the child
    * @return returns the uncle of the node, null if parent is null
    */
    private RBNode findUncle(RBNode child, RBNode parent){
        if(parent != null){
            if(parent.getLeftChild() != child){
                return parent.getLeftChild();
            }else{
                return parent.getRightChild();
            }
        }
        return null;

    }

    /**
     * Standard Binary Search Tree insert. 
     * @param parent Keeps track of the parent of the newNode
     * @param currRoot The current root of the subtree
     * @param newNode New node being added to the tree
     * @return the new tree
     */
    private RBNode BSTInsert(RBNode parent, RBNode currRoot, RBNode newNode){
        if(currRoot == null){
            newNode.setParent(parent);
            return newNode;
        }else{
            if(newNode.getData() < currRoot.getData()){ //going left
                currRoot.setLeftChild(BSTInsert(currRoot, currRoot.getLeftChild(), newNode));
            }else{ //go right
                currRoot.setRightChild(BSTInsert(currRoot, currRoot.getRightChild(), newNode));
            }
        }
        return currRoot;
    }

    /**
     * Rotates a node to the left
     * @param node node to rotate
     * @return the new root of the subtree
     */
    private RBNode rotateLeft(RBNode node){
        RBNode rightChild = node.getRightChild();
        RBNode rightsLeftChild = rightChild.getLeftChild(); 
        rightChild.setLeftChild(node);
        node.setRightChild(rightsLeftChild);
        node.setParent(rightChild);
        if(rightsLeftChild != null){
            rightsLeftChild.setParent(node);
        }

        return rightChild; 
    }

    /**
     * Rotates a node to the right
     * @param node node to rotate
     * @return the new root of the subtree
     */
    private RBNode rotateRight(RBNode node){
        RBNode leftChild = node.getLeftChild();
        RBNode leftsRightChild = leftChild.getRightChild();
        leftChild.setRightChild(node);
        node.setLeftChild(leftsRightChild);
        node.setParent(leftChild);

        if(leftsRightChild != null){
            leftsRightChild.setParent(node);
        }

        return leftChild;
    }

    public void inorderTraverse(){
        inorder(root);
        System.out.println();
        System.out.println("The height of the tree is: " + height);
    }

    /**
     * Inorder Private method is a recursive function that follows the inorder procedure for traversing a tree
     * @param currRoot the current root of the subtree
     */
    private void inorder(RBNode currRoot){
        if(currRoot != null){
            inorder(currRoot.getLeftChild());
            System.out.print("CurrRoot is: " + currRoot.getData() + " ");
            System.out.print("Their color is: " + currRoot.getColor() + " ");
            if(currRoot.getParent() != null)
                System.out.print("Their parent is: " + currRoot.getParent().getData() + "\n");
            else
                System.out.print("Their parent is: null\n");
            inorder(currRoot.getRightChild());
        }
    }

    public void printByLevel(){
        for(int i = 1; i <= height+1; i++){
            printLevelRecursive(root, i);
            System.out.println();
        }
    }

    private void printLevelRecursive(RBNode root, int level){
        if(root == null) 
            return;

        String rootColor = "";
        String parentColor = "";
        if(level == 1){
            if(root.getColor() == 'R') rootColor = "-";
            
            if(root.getParent() != null){
                if(root.getParent().getColor() == 'R') parentColor = "-";
                System.out.print("(" + rootColor + root.getData()  + ", " + parentColor + root.getParent().getData() + ") ");
            }else   
                System.out.print("(" + rootColor + root.getData()  + ", " + root.getParent() + ") ");

        }else if(level > 1){
            printLevelRecursive(root.getLeftChild(), level-1);
            printLevelRecursive(root.getRightChild(), level-1);
        }

    }

    private int determineHeight(RBNode root){
        if(root == null)
            return -1;

        int leftDepth = determineHeight(root.getLeftChild());
        int rightDepth = determineHeight(root.getRightChild());

        if(leftDepth > rightDepth){
            return leftDepth+1;
        }else{
            return rightDepth+1;
        }
    }
}
 /*
        if(currNode != root){
            RBNode parent = currNode.getParent();
            RBNode grandParent = parent.getParent();
            RBNode uncle = findUncle(parent, grandParent);
            if(currNode.getColor() == 'R' && parent.getColor() == 'R'){
                //Rule one, CurrNode, parent and uncle are red. Flip colors
                if(uncle != null){
                    if(uncle != parent && uncle.getColor() == 'R'){
                        parent.flipColor();
                        uncle.flipColor();
                        if(grandParent != root){
                            grandParent.flipColor();
                            RBInsertHelp(grandParent);
                        }
                    }
                }else if(uncle.getColor() == 'R'){ //uncle is null so uncle is black node
                    //Left Left Case
                    if(parent.getLeftChild() == currNode){
                        rotateRight(grandParent);
                        if(grandParent == root){
                            root = parent;
                        }
                        parent.flipColor();
                        grandParent.flipColor();
                        

                    }
                }
            }
        }
    */
