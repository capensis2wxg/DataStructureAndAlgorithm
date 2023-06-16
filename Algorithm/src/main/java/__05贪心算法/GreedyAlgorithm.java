package __05贪心算法;/*
    @author wxg
    @date 2021/10/25-0:07
    */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播电台,放入到Map
        HashMap<String, HashSet<String>> broadcasts = new HashMap<>();
        //将各个电台放入到broadcasts
        HashSet<String> hashSet1 = new HashSet<>();
        hashSet1.add("北京");
        hashSet1.add("上海");
        hashSet1.add("天津");
        HashSet<String> hashSet2 = new HashSet<>();
        hashSet2.add("广州");
        hashSet2.add("北京");
        hashSet2.add("深圳");
        HashSet<String> hashSet3 = new HashSet<>();
        hashSet3.add("成都");
        hashSet3.add("上海");
        hashSet3.add("杭州");
        HashSet<String> hashSet4 = new HashSet<>();
        hashSet4.add("上海");
        hashSet4.add("天津");
        HashSet<String> hashSet5 = new HashSet<>();
        hashSet5.add("杭州");
        hashSet5.add("大连");
        //加入到map
        broadcasts.put("K1", hashSet1);
        broadcasts.put("K2", hashSet2);
        broadcasts.put("K3", hashSet3);
        broadcasts.put("K4", hashSet4);
        broadcasts.put("K5", hashSet5);
        //allAreas 存放所有的地区
        HashSet<String> allAreas = new HashSet<>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("广州");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("杭州");
        allAreas.add("大连");
        //创建ArrayList, 存放选择的电台集合
        ArrayList<String> selects = new ArrayList<>();
        //定义一个临时的集合，在遍历的过程中，存放遍历过程中的电台覆盖的地区和当前还没有覆盖的地区的交集
        HashSet<String> tempSet = new HashSet<>();
        //定义maxKey, 保存在一次遍历过程中，能够覆盖最大未覆盖的地区对应的电台的key
        //如果maxKey 不为null , 则会加入到selects
        String maxKey;
        while(allAreas.size() != 0) { // 如果allAreas 不为0, 则表示还没有覆盖到所有的地区
            //每进行一次while,需要
            maxKey = null;
            //遍历broadcasts, 取出对应key,注意：这里的allAreas在整个for循环内是不会发生变化的。
            for(String key : broadcasts.keySet()) {
                //每进行一次for
                tempSet.clear();
                // 记录此时maxKey所覆盖的地区与还未覆盖地区的交集, 以便用来与key对应的覆盖的地区与还未覆盖地区的交集比较大小
                HashSet<String> comparedIntersection = null;
                if(maxKey != null){
                    // 这里comparedIntersection不会为null，因为凡是进入这里的maxKey都是由上一次的key得到的,而这个key满足tempSet.size()> 0
                    comparedIntersection = broadcasts.get(maxKey);
                    comparedIntersection.retainAll(allAreas);
                }
                //当前这个key能够覆盖的地区
                HashSet<String> areas = broadcasts.get(key);
                tempSet.addAll(areas);
                //求出tempSet 和 allAreas 集合的交集, 交集会赋给 tempSet
                tempSet.retainAll(allAreas);
                //如果当前这个集合包含的未覆盖地区的数量，比maxKey指向的集合地区还多
                //就需要重置maxKey
                // tempSet.size() >broadcasts.get(maxKey).size()) 体现出贪心算法的特点,每次都选择最优的
                if(tempSet.size() > 0 &&
                        /*
                         这里注意:每次将maxKey置为null后, 只有tempSet.size()>0为真并且maxKey==null为假，comparedIntersection.size()才会执行，
                         这时comparedIntersection不会为空，不用担心comparedIntersection赋值时使用了默认值null。
                         */
                        (maxKey == null || tempSet.size() > comparedIntersection.size())){
                    maxKey = key;
                }
            }
            //maxKey != null, 就应该将maxKey 加入selects
            if(maxKey != null) {
                selects.add(maxKey);
                //将maxKey指向的广播电台覆盖的地区，从 allAreas 去掉
                allAreas.removeAll(broadcasts.get(maxKey));
            }
        }
        System.out.println("得到的选择结果是" + selects);//[K1,K2,K3,K5]
    }
}
