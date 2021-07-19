
public class RBNode{
    private int data;
    private RBNode left;
    private RBNode right;
    private char color;
    private RBNode parent;

    public RBNode(int data){
        this.data = data;
        left = null;
        right = null;
        color = 'R';
        parent = null;
    }

    public int getData(){
        return data;
    }

    public RBNode getLeftChild(){
        return left;
    }

    public RBNode getRightChild(){
        return right;
    }

    public char getColor(){
        return color;
    }

    public RBNode getParent(){
        return parent;
    }

    public void setData(int data){
        this.data = data;
    }

    public void setLeftChild(RBNode left){
        this.left = left;
    }

    public void setRightChild(RBNode right){
        this.right = right;
    }

    public void setColor(char color){
        this.color = color;
    }

    public void flipColor(){
        if(color == 'R'){
            color = 'B';
        }else if (color == 'B'){
            color = 'R';
        }
    }

    public void setParent(RBNode parent){
        this.parent = parent;
    }


}