package com.hzw.homework.data_mining;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Apriori {
    private final static int SUPPORT = 7; // 支持度阈值
    private final static double CONFIDENCE = 0.9; // 置信度阈值
    private final static String ITEM_SPLIT = "\t"; // 项之间的分隔符
    private final static String CON = "->"; // 项之间的分隔符

    /**
     * 算法主程序
     * @param dataList
     * @return
     */
    public Map<String, Integer> apriori(List<String> dataList)
    {
        Map<String, Integer> stepFrequentSetMap = new HashMap<>();
        stepFrequentSetMap.putAll(findFrequentOneSets(dataList));

        Map<String, Integer> frequentSetMap = new HashMap<String, Integer>();//频繁项集
        frequentSetMap.putAll(stepFrequentSetMap);

        while(stepFrequentSetMap!=null && stepFrequentSetMap.size()>0)
        {
            Map<String, Integer> candidateSetMap = aprioriGen(stepFrequentSetMap);

            Set<String> candidateKeySet = candidateSetMap.keySet();

            //扫描D，进行计数
            for(String data:dataList)
            {
                for(String candidate:candidateKeySet)
                {
                    boolean flag = true;
                    String[] strings = candidate.split(ITEM_SPLIT);
                    for(String string:strings)
                    {
                        if(data.indexOf(string+ITEM_SPLIT)==-1)
                        {
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        candidateSetMap.put(candidate, candidateSetMap.get(candidate)+1);
                }
            }

            //从候选集中找到符合支持度的频繁项集
            stepFrequentSetMap.clear();
            for(String candidate:candidateKeySet)
            {
                Integer count = candidateSetMap.get(candidate);
                if(count>=SUPPORT)
                    stepFrequentSetMap.put(candidate, count);
            }

            // 合并全部频繁集
            frequentSetMap.putAll(stepFrequentSetMap);
        }

        return frequentSetMap;
    }

    /**
     * find frequent 1 itemsets
     * @param dataList
     * @return
     */
    private Map<String, Integer> findFrequentOneSets(List<String> dataList)
    {
        Map<String, Integer> resultSetMap = new HashMap<>();

        for(String data:dataList)
        {
            String[] strings = data.split(ITEM_SPLIT);
            for(String string:strings)
            {
                string += ITEM_SPLIT;
                if(resultSetMap.get(string)==null)
                {
                    resultSetMap.put(string, 1);
                }
                else {
                    resultSetMap.put(string, resultSetMap.get(string)+1);
                }
            }
        }
        return resultSetMap;
    }

    /**
     * 依据上一步的频繁项集的集合选出候选集
     * @param setMap
     * @return
     */
    private Map<String, Integer> aprioriGen(Map<String, Integer> setMap)
    {
        Map<String, Integer> candidateSetMap = new HashMap<>();

        Set<String> candidateSet = setMap.keySet();
        for(String s1:candidateSet)
        {
            String[] strings1 = s1.split(ITEM_SPLIT);
            String s1String = "";
            for(String temp:strings1)
                s1String += temp+ITEM_SPLIT;

            for(String s2:candidateSet)
            {
                String[] strings2 = s2.split(ITEM_SPLIT);


                boolean flag = true;
                for(int i=0;i<strings1.length-1;i++)
                {
                    if(strings1[i].compareTo(strings2[i])!=0)
                    {
                        flag = false;
                        break;
                    }
                }
                if(flag && strings1[strings1.length-1].compareTo(strings2[strings1.length-1])<0)
                {
                    //连接步：产生候选
                    String c = s1String+strings2[strings2.length-1]+ITEM_SPLIT;
                    if(hasInfrequentSubset(c, setMap))
                    {
                        //剪枝步：删除非频繁的候选
                    }
                    else {
                        candidateSetMap.put(c, 0);
                    }
                }
            }
        }

        return candidateSetMap;
    }

    /**
     * 使用先验知识，推断候选集是否是频繁项集
     * @param candidate
     * @param setMap
     * @return
     */
    private boolean hasInfrequentSubset(String candidateSet, Map<String, Integer> setMap)
    {
        String[] strings = candidateSet.split(ITEM_SPLIT);

        //找出候选集全部的子集，并推断每一个子集是否属于频繁子集
        for(int i=0;i<strings.length;i++)
        {
            String subString = "";
            for(int j=0;j<strings.length;j++)
            {
                if(j!=i)
                {
                    subString += strings[j]+ITEM_SPLIT;
                }
            }

            if(setMap.get(subString)==null)
                return true;
        }

        return false;
    }

    /**
     * 由频繁项集产生关联规则
     * @param frequentSetMap
     * @return
     */
    public Map<String, Double> getRelationRules(Map<String, Integer> frequentSetMap)
    {
        Map<String, Double> relationsMap = new HashMap<>();
        Set<String> keySet = frequentSetMap.keySet();

        for(String key:keySet)
        {
            List<String> keySubset = subset(key);
            for(String keySubsetItem:keySubset)
            {
                //子集keySubsetItem也是频繁项
                Integer count = frequentSetMap.get(keySubsetItem);
                if(count!=null)
                {
                    Double confidence = (1.0*frequentSetMap.get(key))/(1.0*frequentSetMap.get(keySubsetItem));
                    if(confidence>CONFIDENCE)
                        relationsMap.put(keySubsetItem+CON+expect(key, keySubsetItem), confidence);
                }
            }
        }

        return relationsMap;
    }

    /**
     * 求一个集合全部的非空真子集
     *
     * @param sourceSet
     * @return
     * 为了以后能够用在其它地方。这里我们不是用递归的方法
     *
     * 參考：http://blog.163.com/xiaohui_1123@126/blog/static/3980524020109784356915/
     * 思路：如果集合S（A,B,C,D）。其大小为4。拥有2的4次方个子集，即0-15，二进制表示为0000，0001。...，1111。
     * 相应的子集为空集。{D}，...。{A,B,C,D}。
     */
    private List<String> subset(String sourceSet)
    {
        List<String> result = new ArrayList<>();

        String[] strings = sourceSet.split(ITEM_SPLIT);
        //非空真子集
        for(int i=1;i<(int)(Math.pow(2, strings.length))-1;i++)
        {
            String item = "";
            String flag = "";
            int ii=i;
            do
            {
                flag += ""+ii%2;
                ii = ii/2;
            } while (ii>0);
            for(int j=flag.length()-1;j>=0;j--)
            {
                if(flag.charAt(j)=='1')
                {
                    item = strings[j]+ITEM_SPLIT+item;
                }
            }
            result.add(item);
        }

        return result;
    }

    /**
     * 集合运算，A/B
     * @param A
     * @param B
     * @return
     */
    private String expect(String stringA,String stringB)
    {
        String result = "";

        String[] stringAs = stringA.split(ITEM_SPLIT);
        String[] stringBs = stringB.split(ITEM_SPLIT);

        for(int i=0;i<stringAs.length;i++)
        {
            boolean flag = true;
            for(int j=0;j<stringBs.length;j++)
            {
                if(stringAs[i].compareTo(stringBs[j])==0)
                {
                    flag = false;
                    break;
                }
            }
            if(flag)
                result += stringAs[i]+ITEM_SPLIT;
        }

        return result;
    }



    public static int app(String file, int support) throws IOException {
        ArrayList<String> dataList = new ArrayList<>();

        //导入数据
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = br.readLine()) != null) {
            dataList.add(line);
        }

        long startTime = System.currentTimeMillis();
        Apriori apriori2 = new Apriori();

        System.out.println("=频繁项集==========");

        Map<String, Integer> frequentSetMap = apriori2.apriori(dataList);
        Set<String> keySet = frequentSetMap.keySet();
        for(String key:keySet)
        {
            System.out.println(key+" : "+frequentSetMap.get(key));
            if (frequentSetMap.get(key) >= support) {
                Myfptree2.res += key+" : "+frequentSetMap.get(key) + "<br>";
            }
        }

        System.out.println("=关联规则==========");
        Map<String, Double> relationRulesMap = apriori2.getRelationRules(frequentSetMap);
        Set<String> rrKeySet = relationRulesMap.keySet();
        for (String rrKey : rrKeySet)
        {
            System.out.println(rrKey + "  :  " + relationRulesMap.get(rrKey));
        }
        long endTime = System.currentTimeMillis();

        return (int) (endTime-startTime);
    }
}