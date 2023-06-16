package __15树结构应用;/*
    @author wxg
    @date 2021/10/11-1:14
    */

import java.io.*;
import java.util.*;

public class HuffmanCode {
    public static void main(String[] args) {

        //测试压缩文件
//		String srcFile = "d://Uninstall.xml";
//		String dstFile = "d://Uninstall.zip";
//		zipFile(srcFile, dstFile);
//		System.out.println("压缩文件ok~~");

        //测试解压文件
        String zipFile = "d://Uninstall.zip";
        String dstFile = "d://Uninstall2.xml";
        unZipFile(zipFile, dstFile);
        System.out.println("解压成功!");

        // 1、生成字节数组
        String content = "i like like like java do you like java like like";
        byte[] contentBytes = content.getBytes();
        // 2、将字节数组传入，统计每个字节出现的次数
        List<Node1> nodes = getNodes(contentBytes);
        System.out.println("nodes=" + nodes);
        // 3、利用统计后的List[HashMap[key,value],.......]创建赫夫曼树
        System.out.println("赫夫曼树");
        Node1 huffmanTreeRoot = createHuffmanTree(nodes);
        System.out.println(contentBytes.length); //40
        // 4、利用赫夫曼树生成对应的赫夫曼编码
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        // 5、将赫夫曼编码进行压缩
        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        // 封装上述方法
//        byte[] huffmanCodeBytes= huffmanZip(contentBytes);
        System.out.println("压缩后的结果是:" + Arrays.toString(huffmanCodeBytes) + " 长度= " + huffmanCodeBytes.length);
        // 6、解压字符串
        byte[] sourceBytes = decode(huffmanCodes, huffmanCodeBytes);
        System.out.println("原来的字符串=" + new String(sourceBytes));
        /*// 2、将字节数组传入，统计每个字节出现的次数
        List<Node1> nodes = getNodes(contentBytes);
        System.out.println("nodes=" + nodes);
        // 3、利用统计后的List[HashMap[key,value],.......]创建赫夫曼树
        System.out.println("赫夫曼树");
        Node1 huffmanTreeRoot = createHuffmanTree(nodes);
        System.out.println("前序遍历");
        huffmanTreeRoot.preOrder();
        // 4、利用赫夫曼树生成对应的赫夫曼编码
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        System.out.println("~生成的赫夫曼编码表= " + huffmanCodes);
        // 5、将赫夫曼编码进行压缩
        //zip(contentBytes, huffmanCodes);
        byte[] huffmanCodeBytes = zip(contentBytes, huffmanCodes);
        System.out.println("huffmanCodeBytes=" + Arrays.toString(huffmanCodeBytes));//17*/
    }

    /**
     *  编写一个方法，完成对压缩文件的解压
     * @param zipFile 准备解压的文件
     * @param dstFile 将文件解压到哪个路径
     */
    public static void unZipFile(String zipFile, String dstFile) {
        //定义文件输入流
        InputStream is = null;
        //定义一个对象输入流
        ObjectInputStream ois = null;
        //定义文件的输出流
        OutputStream os = null;
        try {
            //创建文件输入流
            is = new FileInputStream(zipFile);
            //创建一个和 is关联的对象输入流
            ois = new ObjectInputStream(is);
            //读取byte数组huffmanBytes
            byte[] huffmanBytes = (byte[])ois.readObject();
            //读取赫夫曼编码表
            Map<Byte,String> huffmanCodes = (Map<Byte,String>)ois.readObject();
            //解码
            byte[] bytes = decode(huffmanCodes, huffmanBytes);
            //将bytes 数组写入到目标文件
            os = new FileOutputStream(dstFile);
            //写数据到 dstFile 文件
            os.write(bytes);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                Objects.requireNonNull(os).close();
                ois.close();
                is.close();
            } catch (Exception e2) {
                System.out.println(e2.getMessage());
            }
        }
    }
    /**
     *  编写方法，将一个文件进行压缩
     * @param srcFile 你传入的希望压缩的文件的全路径
     * @param dstFile 我们压缩后将压缩文件放到哪个目录
     */
    public static void zipFile(String srcFile, String dstFile) {
        //创建输出流
        OutputStream os = null;
        ObjectOutputStream oos = null;
        //创建文件的输入流
        FileInputStream is = null;
        try {
            //创建文件的输入流
            is = new FileInputStream(srcFile);
            //创建一个和源文件大小一样的byte[]
            byte[] b = new byte[is.available()];
            //读取文件
            int read = is.read(b);
            System.out.println(read);
            //直接对源文件压缩
            byte[] huffmanBytes = huffmanZip(b);
            //创建文件的输出流, 存放压缩文件
            os = new FileOutputStream(dstFile);
            //创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //把赫夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes);
            //这里我们以对象流的方式写入赫夫曼编码，是为了以后我们恢复源文件时使用
            //注意一定要把赫夫曼编码写入压缩文件
            oos.writeObject(huffmanCodes);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                Objects.requireNonNull(is).close();
                Objects.requireNonNull(oos).close();
                os.close();
            }catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    /**
     *  编写一个方法，完成对压缩数据的解码
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 赫夫曼编码得到的字节数组
     * @return 就是原来的字符串对应的数组
     */
    private static byte[] decode(Map<Byte,String> huffmanCodes, byte[] huffmanBytes) {
        // 1、先得到huffmanBytes对应的二进制的字符串, 形式1010100010111......
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            byte b = huffmanBytes[i];
            // 判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, b));
        }
        System.out.println("解压测试 stringBuilder~~~=" + stringBuilder.toString() + " " + "stringBuilder的长度" + stringBuilder.length());
        //把字符串按照指定的赫夫曼编码进行解码, 这里需要将赫夫曼编码表进行调换，因为反向查询 a->100 100->a
        HashMap<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) map.put(entry.getValue(), entry.getKey());
        System.out.println(map);
        // 创建集合存放存放查找到的byte
        ArrayList<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length();) {
            int count  = 1;
            boolean flag = true;
            Byte b = null;
            while(flag){
                String key = stringBuilder.substring(i, i+count);
                b = map.get(key);
                // 没找到
                if(b == null) count++;
                // 匹配到
                else flag = false;
            }
            list.add(b);
            // 如果某个key在编码表中找到了,那么i就要移动key.length的count个位置
            i += count;
        }
        System.out.println(list);
        //当for循环结束后，我们list中就存放了所有的字符"105, 32, 108, 105, 107, 101, 32, 108, 105, 107, 101, 32, 108, 105, 107, 101, 32, 106, 97, 118, 97, 32, 100, 111, 32, 121, 111, 117, 32, 108, 105, 107, 101, 32, 97, 32, 106, 97, 118, 97"
        //把list 中的数据放入到byte[] 并返回
        byte[] b = new byte[list.size()];
        for(int i = 0;i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }

    /**
     * 将一个byte 转成一个二进制的字符串, 如果看不懂，可以参考我讲的Java基础 二进制的原码，反码，补码
     * @param b 传入的 byte
     * @param flag 标志是否需要补高位如果是true ，表示需要补高位，如果是false表示不补, 如果是最后一个字节，无需补高位
     * @return 是该b 对应的二进制的字符串，（注意是按补码返回）
     */
    private static String byteToBitString(boolean flag, byte b){
        //使用变量保存 b
        int temp = b; //将 b 转成 int
        //如果是正数我们还存在补高位
        if(flag) temp |= 256; //按位与 256  1 0000 0000  | 0000 0001 => 1 0000 0001
        String str = Integer.toBinaryString(temp); //返回的是temp对应的二进制的补码
        if(flag) return str.substring(str.length() - 8);
        else return str;
    }

    /**
     * 使用一个方法，将前面的方法封装起来，便于我们的调用
     * @param bytes 原始的字符串对应的字节数组
     * @return 是经过 赫夫曼编码处理后的字节数组(压缩后的数组)
     */
    private static byte[] huffmanZip(byte[] bytes) {
        List<Node1> nodes = getNodes(bytes);
        //根据 nodes 创建的赫夫曼树
        Node1 huffmanTreeRoot = createHuffmanTree(nodes);
        //对应的赫夫曼编码(根据 赫夫曼树)
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        //根据生成的赫夫曼编码，压缩得到压缩后的赫夫曼编码字节数组
        return zip(bytes, huffmanCodes);
    }


    /**
     * 编写一个方法，将字符串对应的byte[] 数组，通过生成的赫夫曼编码表，返回一个赫夫曼编码 压缩后的byte[]
     * @param contentBytes 这时原始的字符串对应的 byte[]
     * @param huffmanCodes 生成的赫夫曼编码map，是一种二进制字符串的形式
     * @return 返回赫夫曼编码处理后的 byte[]
     * 举例： String content = "i like like like java do you like a java"; =》 byte[] contentBytes = content.getBytes();
     * 返回的是 二进制字符串 "1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100"
     * => 对应的byte[] huffmanCodeBytes，即 8位对应一个 byte,放入到 huffmanCodeBytes
     * huffmanCodeBytes[0] =  10101000(补码) => byte  [推导  10101000=> 10101000 - 1 = 10100111(反码)=> 11011000= -88 ]
     * huffmanCodeBytes[0] = -88
     */
    private static byte[] zip(byte[] contentBytes, Map<Byte, String> huffmanCodes) {
        //1.利用 huffmanCodes 将 bytes 转成 赫夫曼编码对应的字符串
        StringBuilder stringBuilder = new StringBuilder();
        //遍历bytes 数组
        for(byte b: contentBytes) stringBuilder.append(huffmanCodes.get(b));
        System.out.println("压缩测试 stringBuilder~~~=" + stringBuilder.toString() + " " + "stringBuilder的长度" + stringBuilder.length());
        //将 二进制字符串"1010100010111111110..." 转成 byte[], 并统计返回byte[] huffmanCodeBytes长度
        //一句话 int len = (stringBuilder.length() + 7) / 8;
        int len;
        if(stringBuilder.length() % 8 == 0) len = stringBuilder.length() / 8;
        else  len = stringBuilder.length() / 8 + 1;
        //创建 存储压缩后的 byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;
        //因为是每8位对应一个byte,所以步长 +8
        for (int i = 0; i < stringBuilder.length(); i+=8) {
            String strByte;
            if(i+8 > stringBuilder.length()) strByte = stringBuilder.substring(i);
            else strByte = stringBuilder.substring(i, i+8);
            //将strByte转成一个byte,放入到huffmanCodeBytes
            huffmanCodeBytes[index] = (byte)Integer.parseInt(strByte,2);
            index++;
        }
        return huffmanCodeBytes;
    }

    //生成赫夫曼树对应的赫夫曼编码 思路:
    //1. 将赫夫曼编码表存放在 Map<Byte,String> 形式
    //   生成的赫夫曼编码表{32=01, 97=100, 100=11000, 117=11001, 101=1110, 118=11011, 105=101, 121=11010, 106=0010, 107=1111, 108=000, 111=0011}
    static Map<Byte, String> huffmanCodes = new HashMap<>();
    //2. 在生成赫夫曼编码表示，需要去拼接路径, 定义一个StringBuilder 存储某个叶子结点的路径
    static StringBuilder stringBuilder = new StringBuilder();

    //为了调用方便，我们重载 getCodes
    private static Map<Byte, String> getCodes(Node1 root) {
        if(root == null)  return null;
        //处理root的左子树
        getCodes(root.left, "0", stringBuilder);
        //处理root的右子树
        getCodes(root.right, "1", stringBuilder);
        return huffmanCodes;
    }

    /**
     * 功能：将传入的node结点的所有叶子结点的赫夫曼编码得到，并放入到huffmanCodes集合
     * @param node  树或者子树的节点
     * @param code  路径： 左子结点是 0, 右子结点 1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node1 node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将code 加入到 stringBuilder2
        stringBuilder2.append(code);
        if(node != null) { //如果node == null不处理
            //判断当前node 是叶子结点还是非叶子结点
            if(node.data == null) { //非叶子结点
                //递归处理
                //向左递归
                getCodes(node.left, "0", stringBuilder2);
                //向右递归
                getCodes(node.right, "1", stringBuilder2);
            } else { //说明是一个叶子结点
                //就表示找到某个叶子结点的最后, 将其加入到HashMap中
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }
    }

    //前序遍历的方法
    private static void preOrder(Node1 root) {
        if(root != null) root.preOrder();
        else System.out.println("赫夫曼树为空");
    }

    //可以通过List 创建对应的赫夫曼树
    private static Node1 createHuffmanTree(List<Node1> nodes) {
        while(nodes.size() > 1) {
            //排序, 从小到大
            Collections.sort(nodes);
            //取出第一颗最小的二叉树
            Node1 leftNode = nodes.get(0);
            //取出第二颗最小的二叉树
            Node1 rightNode = nodes.get(1);
            //创建一颗新的二叉树,它的根节点 没有data, 只有权值
            Node1 parent = new Node1(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;
            //将已经处理的两颗二叉树从nodes删除
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            //将新的二叉树，加入到nodes
            nodes.add(parent);
        }
        //nodes 最后的结点，就是赫夫曼树的根结点
        return nodes.get(0);
    }

    /**
     *  统计字节数组中每个字节出现的次数
     * @param bytes 接收字节数组
     * @return 返回的就是 List 形式   [Node[date=97 ,weight = 5], Node[date=32,weight = 9]......],
     */
    private static List<Node1> getNodes(byte[] bytes) {
        //1创建一个ArrayList
        ArrayList<Node1> nodes = new ArrayList<>();
        //遍历 bytes , 统计 每一个byte出现的次数->map[key,value]
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            // Map还没有这个字符数据,第一次
            if (count == null) counts.put(b, 1);
            else counts.put(b, count + 1);
        }
        // 遍历Map,把每一个键值对转成一个Node 对象，并加入到nodes集合
        for(Map.Entry<Byte, Integer> entry: counts.entrySet())  nodes.add(new Node1(entry.getKey(), entry.getValue()));
        return nodes;
    }
}

//创建Node1 ,待数据和权值
class Node1 implements Comparable<Node1>{
    Byte data; // 存放数据(字符)本身，比如'a' => 97 ' ' => 32
    int weight; //权值, 表示字符出现的次数
    Node1 left;//
    Node1 right;
    public Node1(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }
    public String toString() {
        return "Node1 [data = " + data + " weight=" + weight + "]";
    }
    @Override
    public int compareTo(Node1 o) {
        // 从小到大排序
        return this.weight - o.weight;
    }
    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if(this.left != null) this.left.preOrder();
        if(this.right != null)  this.right.preOrder();
    }
}