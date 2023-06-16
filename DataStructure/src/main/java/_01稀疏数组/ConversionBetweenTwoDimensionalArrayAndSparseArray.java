package _01稀疏数组;
/*
@author wxg
@date 2021/9/4-21:19
 */

import org.junit.Test;

import java.io.*;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @author capensis
 */

public class ConversionBetweenTwoDimensionalArrayAndSparseArray {
    public static void main(String[] args) {
        System.out.println("=============================稀疏矩阵===================================");
        //  定义一个稀疏数组
        int[][] twoDimensionalArray = new int[6][7];
        twoDimensionalArray[2][3] = 5;
        twoDimensionalArray[4][1] = 6;
        //  遍历这个稀疏数组
        Arrays.stream(twoDimensionalArray).forEach(row -> {
            Arrays.stream(row).forEachOrdered(data -> System.out.printf("%d\t", data));
            System.out.println();
        });
        //  将稀疏数组转化为稀疏矩阵
        //TODO 获取二维数组非0的数据个数
        int sum = Arrays
                .stream(twoDimensionalArray)
                // 返回一个流，该流中记录了每个一维数组中非零的个数
                .mapToInt(row ->
                        (int) Arrays
                                .stream(row)
                                .filter(data -> data != 0)
                                .count()
                )
                // 对每行的非零个数求和
                .sum();
        System.out.println("twoDimensionalArray 稀疏矩阵的非零个数为：" + sum + "个");

        //TODO 创建二维数组
        int count = 0;
        int[][] sparseArr = new int[sum + 1][3];
        // 首行数组表示（稀疏矩阵的行数，列数，非零个数）
        sparseArr[0][0] = twoDimensionalArray.length;
        sparseArr[0][1] = twoDimensionalArray[0].length;
        sparseArr[0][2] = sum;
        //TODO 将稀疏矩阵中非0的元素赋值给二位数组
        for (int i = 0; i < twoDimensionalArray.length; i++) {
            for (int j = 0; j < twoDimensionalArray[0].length; j++) {
                if (twoDimensionalArray[i][j] != 0) {
                    // 一个非零值就要独占一行
                    count++;
                    // 返回非零值在稀疏矩阵中的坐标，并分别赋给稀疏数组
                    sparseArr[count][0] = i;
                    sparseArr[count][1] = j;
                    sparseArr[count][2] = twoDimensionalArray[i][j];
                }
            }
        }
        //TODO 遍历二位数组
        System.out.println("=============================二位数组====================================");
        Arrays.stream(sparseArr).forEach(rows -> System.out.printf("%d\t%d\t%d\t\n", rows[0], rows[1], rows[2]));

        System.out.println("------------------------------将二维数组转换为稀疏数组----------------------------------------------");
        //TODO: 将二维数组转换为稀疏数组
        int rows = sparseArr[0][0];
        int columns = sparseArr[0][1];
        int[][] twoArrays = new int[rows][columns];
        IntStream.range(1, sparseArr.length).forEach(i -> {
            int r = sparseArr[i][0];
            int c = sparseArr[i][1];
            twoArrays[r][c] = sparseArr[i][2];
        });
        //TODO 遍历这个新建的二维数组
        //  遍历这个二维数组
        Arrays.stream(twoArrays).forEach(row -> {
            Arrays.stream(row).forEach(data -> System.out.printf("%d\t", data));
            System.out.println();
        });
        ConversionBetweenTwoDimensionalArrayAndSparseArray cbtas = new ConversionBetweenTwoDimensionalArrayAndSparseArray();
        cbtas.storeArrays(sparseArr);

    }

    /**
     * 将数组对象存储到文件中
     *
     * @param sparseArrays _01二维数组
     */
    public void storeArrays(int[][] sparseArrays) {
        // IDEA默认的当前路径是在当前项目下
        File file = new File("LinearStructure/src/main/java/_01稀疏数组/sparseArrays.txt");
        //  创建一个文件写入流
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file);
            for (int[] sparseArray : sparseArrays) {
                for (int j = 0; j < 3; j++) {
                    fileWriter.write(sparseArray[j] + "\t");
                }
                fileWriter.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("二维数组已经存储完成");
    }

    /**
     * 从文件中读取二维数组
     */
    @Test
    public void readArrays() {
        String[][] twoArrs = new String[3][];
        String line;
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            // 在Test测试环境下，默认路径是在当前模块下
            fileReader = new FileReader("src/main/java/_01稀疏数组/sparseArrays.txt");
            bufferedReader = new BufferedReader(fileReader);
            //  读取文件内容
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                // System.out.println(line);
                String[] split = line.split("\t");
                twoArrs[count] = split;
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        //  遍历二维数组
        Arrays.stream(twoArrs).forEach(twoArr -> {
            Arrays.stream(twoArr).forEach(s -> System.out.printf("%s\t", s));
            System.out.println();
        });
    }
}






