package __15树结构应用;/*
    @author wxg
    @date 2021/10/13-21:20
    */

public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2, 6, 4, 8};
        BinarySortTree binarySortTree = new BinarySortTree();
        //循环的添加结点到二叉排序树
        for (int j : arr) {
            binarySortTree.add(new Node2(j));
        }
        //中序遍历二叉排序树
        System.out.println("中序遍历二叉排序树~");
        binarySortTree.infixOrder(); // 1, 3, 5, 7, 9, 10, 12


        binarySortTree.delNode(12);


        binarySortTree.delNode(5);
        binarySortTree.delNode(10);
        binarySortTree.delNode(2);
        binarySortTree.delNode(3);

        binarySortTree.delNode(9);
        binarySortTree.delNode(1);
        binarySortTree.delNode(7);


        System.out.println("root=" + binarySortTree.getRoot());


        System.out.println("删除结点后");
        binarySortTree.infixOrder();
    }


}

class BinarySortTree{
    private Node2 root;

    public Node2 getRoot() {
        return root;
    }

    //添加结点的方法
    public void add(Node2 node) {
        if(root == null) {
            root = node;//如果root为空则直接让root指向node
        } else {
            root.add(node);
        }
    }

    //中序遍历
    public void infixOrder() {
        if(root != null) root.infixOrder();
        else System.out.println("二叉排序树为空，不能遍历");
    }

    //查找要删除的结点
    public Node2 search(int value) {
        if(root == null) return null;
        else return root.search(value);
    }

    //查找父结点
    public Node2 searchParent(int value) {
        if(root == null)  return null;
        else return root.searchParent(value);
    }

    //删除结点
    public void delNode(int value) {
        if (root != null) {
            //1.需求先去找到要删除的结点 targetNode
            Node2 targetNode = search(value);
            //如果没有找到要删除的结点
            if(targetNode == null) return;
            //如果我们发现当前这颗二叉排序树只有一个结点
            if(root.left == null && root.right == null) {
                root = null;
                return;
            }
            //去找到targetNode的父结点
            Node2 parent = searchParent(value);
            //如果要删除的结点是叶子结点
            if(targetNode.left == null && targetNode.right == null) {
                //判断targetNode 是父结点的左子结点，还是右子结点
                if(parent.left != null && parent.left.value == value) { //是左子结点
                    parent.left = null;
                } else if (parent.right != null && parent.right.value == value) {//是右子结点
                    parent.right = null;
                }
            } else //删除有两颗子树的节点
                if (targetNode.left != null && targetNode.right != null)
                    targetNode.value = delRightTreeMin(targetNode.right);
                else { // 删除只有一颗子树的结点
                //如果要删除的结点有左子结点
                if(targetNode.left != null) {
                    if(parent != null) {
                        //如果 targetNode 是 parent 的左子结点    （targetNode是parent的左子节点，targetNode只有左子节点）
                        if(parent.left.value == value) parent.left = targetNode.left;
                        else { //  targetNode 是 parent 的右子结点 （targetNode是parent的右子节点，targetNode只有左子节点）
                            parent.right = targetNode.left;
                        }
                    } else root = targetNode.left;
                } else { //如果要删除的结点有右子结点
                    if(parent != null) {
                        //如果 targetNode 是 parent 的左子结点    （targetNode是parent的左子节点，targetNode只有右子节点）
                        if(parent.left.value == value)  parent.left = targetNode.right;
                        else { //如果 targetNode 是 parent 的右子结点   （targetNode是parent的右子节点，targetNode只有右子节点）
                            parent.right = targetNode.right;
                        }
                    } else root = targetNode.right;
                }
            }
        }
    }

    private int delRightTreeMin(Node2 node) {
        Node2 target = node;
        //循环的查找左子节点，就会找到最小值
        while(target.left != null) target = target.left;
        //这时 target就指向了最小结点
        //删除最小结点,这里是基本数据类型的值传递机制
        delNode(target.value);
        // 返回的target.value仍然是存在值的
        return target.value;
    }
}

//创建Node结点
class Node2 {
    int value;
    Node2 left;
    Node2 right;
    public Node2(int value) {
        this.value = value;
    }
    /**
     * 添加节点
     * @param node 节点
     */
    public void add(Node2 node) {
        if(node == null)  return;
        //判断传入的结点的值，和当前子树的根结点的值关系
        if(node.value < this.value) {
            //如果当前结点左子结点为null
            if(this.left == null) this.left = node;
            else {
                //递归的向左子树添加
                this.left.add(node);
            }
        } else { //添加的结点的值大于 当前结点的值
            if(this.right == null) this.right = node;
            else {
                //递归的向右子树添加
                this.right.add(node);
            }
        }
    }

    //中序遍历
    public void infixOrder() {
        if(this.left != null) this.left.infixOrder();
        System.out.println(this.value);
        if(this.right != null) this.right.infixOrder();
    }

    /**
     * 查找要删除的结点
     * @param value 查找
     * @return 查找结点
     */
    public Node2 search(int value) {
        if(value == this.value) { //找到就是该结点
            return this;
        } else if(value < this.value) {//如果查找的值小于当前结点，向左子树递归查找
            //如果左子结点为空
            if(this.left  == null)  return null;
            return this.left.search(value);
        } else { //如果查找的值不小于当前结点，向右子树递归查找
            if(this.right == null)  return null;
            return this.right.search(value);
        }
    }

    /**
     * 查找要删除结点的父结点
     * @param value 要找到的结点的值
     * @return 返回的是要删除的结点的父结点，如果没有就返回null
     */
    public Node2 searchParent(int value) {
        //如果当前结点就是要删除的结点的父结点，就返回
        if((this.left != null && this.left.value == value) || (this.right != null && this.right.value == value)) return this;
        else {
            //如果查找的值小于当前结点的值, 并且当前结点的左子结点不为空
            if(value < this.value && this.left != null) return this.left.searchParent(value); //向左子树递归查找
            else if (value >= this.value && this.right != null) return this.right.searchParent(value); //向右子树递归查找
            else return null; // 没有找到父结点
        }
    }


}