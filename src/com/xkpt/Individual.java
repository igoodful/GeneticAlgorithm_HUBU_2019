package com.xkpt;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Individual implements Cloneable {
    //个体基因型:一个基因型染色体由多个基因组成，凡是修改了个体基因型的个体，那么该个体的一些属性也会改变，因此需要同步个体的属性
    public Chromosome chrome;
    //专家总的数量
    public int expertCount = 0;
    //专家总的数量的规范化为1
    public double expertCountUniform = 0.0;
    //总的紧密度
    public int compactnessTotal = 0;
    //总的紧密度的规划为1
    public double compactnessTotalUniform = 0.0;
    //惩罚函数值
    public int punishmentTotal = 0;
    //惩罚函数值的规划为1
    public double punishmentTotalUniform = 0.0;
    //基因长度
    public int geneLength;
    //目标函数值
    public double targetValue;
    //适应度
    public double fitness;


    //随机产生一个个体，要求同一组内的专家编号不相同，不同组内的专家编号可以相同，也可以不同.
    public void randomGenerate() {
        for (int i = 0; i < chrome.getGroupSize(); i++) {
            for (int j = 0; j < chrome.getGroupExpertSize(); j++) {
                Random random = new Random();
                chrome.chromosome[i][j] = random.nextInt(GeneticParameter.getExpertNumberMax()) + 1;
                for (int k = 0; k < j; k++) {
                    if (chrome.chromosome[i][k] == (chrome.chromosome[i][j])) {
                        chrome.chromosome[i][j] = random.nextInt(GeneticParameter.getExpertNumberMax()) + 1;
                        k = -1;
                    }
                }
            }
        }
    }

    //更新本类的属性
    public void calculateProperty() throws SQLException {
        fitness = 0.0;
        targetValue = 0.0;
        expertCount = 0;
        punishmentTotal = 0;
        compactnessTotal = 0;
        compactnessTotalUniform = 0;
        punishmentTotalUniform = 0;
        expertCountUniform = 0;

        //统计每一个专家参与评议的分组个数
        Map<Integer, Integer> expertMap = new HashMap<>();
        //统计专家总数。
        Set<Integer> expertCountTmp = new HashSet<>();
        //材料分组
        MaterialGroup materialGroup = new MaterialGroup();
        //SQL连接
        Connection connection = DBUtil.getConnection();
        Statement statement = connection.createStatement();
        ExpertGroup expertGroup = new ExpertGroup();

        //获取专家的信用等级，并转换成数字。
        int gradeTmp = 0;
        int subjectCompactnessMax = 1;
        //针对每一个专家来统计。
        //分组数量循环
        for (int i = 0; i < chrome.getGroupSize(); i++) {
            //每组专家数量循环
            for (int j = 0; j < chrome.getGroupExpertSize(); j++) {
                //根据专家编号查询专家学科和门类，以及专家等级，并对应构建专家属性分组
                String str = "select t.备用字段四,t.备用字段二,t.备用字段五 from TA_专家 t where t.备用字段六 = " + "'" + chrome.chromosome[i][j] + "'";
                ResultSet resultSet = statement.executeQuery(str);
                while (resultSet.next()) {
                    //门类
                    String subjectCategory = resultSet.getString("备用字段四");
                    //一级学科
                    String firstSubject = resultSet.getString("备用字段二");
                    //等级
                    String grade = resultSet.getString("备用字段五");
                    expertGroup.experts[i][j] = new Expert(chrome.chromosome[i][j], subjectCategory, firstSubject, grade);
                }
                if (expertGroup.experts[i][j].grade.equals("A")) {
                    gradeTmp = 5;
                } else if (expertGroup.experts[i][j].grade.equals("B")) {
                    gradeTmp = 4;
                } else if (expertGroup.experts[i][j].grade.equals("C")) {
                    gradeTmp = 3;
                } else if (expertGroup.experts[i][j].grade.equals("D")) {
                    gradeTmp = 2;
                } else if (expertGroup.experts[i][j].grade.equals("E")) {
                    gradeTmp = 1;
                } else {
                    gradeTmp = 0;
                }

                //统计每位专家对该组材料的匹配度
                for (int m = 0; m < materialGroup.material[i].length; m++) {
                    if (expertGroup.experts[i][j].getFirstSubject().equals(materialGroup.material[i][m].getFirstSubject())) {
                        subjectCompactnessMax = 8;
                        break;
                    }
                    if (expertGroup.experts[i][j].subjectCategory.equals(materialGroup.material[i][m].getSubjectCategory())) {
                        subjectCompactnessMax = 4;
                    }
                }
                expertGroup.experts[i][j].compactness = subjectCompactnessMax * gradeTmp;


                //统计总的专家数量。
                expertCountTmp.add(chrome.chromosome[i][j]);
                //统计分配的专家的总的紧密度和。
                compactnessTotal = compactnessTotal + expertGroup.experts[i][j].compactness;
                //
                if (expertMap.size() > 0) {
                    if (expertMap.containsKey(expertGroup.experts[i][j].expertNumber)) {
                        expertMap.put(expertGroup.experts[i][j].expertNumber, expertMap.get(expertGroup.experts[i][j].expertNumber) + 1);
                    } else {
                        expertMap.put(expertGroup.experts[i][j].expertNumber, 1);
                    }
                } else {
                    expertMap.put(expertGroup.experts[i][j].expertNumber, 1);
                }
                //临时变量归位
                subjectCompactnessMax = 1;
                gradeTmp = 0;
            }

        }

        //更新属性：专家数量属性。
        expertCount = expertCountTmp.size();
        expertCountUniform = 1 - (double) expertCount / (double) (GeneticParameter.groupSize * GeneticParameter.groupExpertSize);
        compactnessTotalUniform = (double) compactnessTotal / (double) (8 * 4 * GeneticParameter.groupSize * GeneticParameter.groupExpertSize);
        //计算罚值函数值。
        int tmpMax = 0;
        for (int key : expertMap.keySet()) {
            int tmp = expertMap.get(key);
            if (tmp < GeneticParameter.reviewMin || tmp > GeneticParameter.reviewMax) {
                if (tmp < GeneticParameter.reviewMin) {
                    tmpMax = (tmpMax < (GeneticParameter.reviewMin - tmp)) ? (GeneticParameter.reviewMin - tmp) : tmpMax;
                    punishmentTotal = punishmentTotal + (GeneticParameter.reviewMin - tmp);
                } else {
                    tmpMax = (tmpMax < (tmp - GeneticParameter.reviewMax)) ? (tmp - GeneticParameter.reviewMax) : tmpMax;
                    punishmentTotal = punishmentTotal + (tmp - GeneticParameter.reviewMax);
                }
            }
        }
        if (tmpMax == 0) {
            tmpMax = 1;
        }
        punishmentTotalUniform = 1 - (double) punishmentTotal / (double) (expertMap.size() * (tmpMax));
        targetValue = compactnessTotalUniform * GeneticParameter.compactnessPercent + expertCountUniform * GeneticParameter.expertCountPercent + punishmentTotalUniform * GeneticParameter.punishmentTotalPercent;
        fitness = targetValue;
        connection.close();
    }

    //产生排除target中元素的随机数组,获得的是某一个分组的编码数组，而不是所有分组的所有专家编号
    public  int[] randomGroup(int[] target) {
        int[] tmp = new int[GeneticParameter.getGroupExpertSize()];
        Random random = new Random();
        int expertNumber;
        for (int i = 0; i < GeneticParameter.getGroupExpertSize(); i++) {
            expertNumber = random.nextInt(GeneticParameter.getExpertNumberMax()) + GeneticParameter.getExpertNumberMin();

            //排除编号在指定数组中
            for (int j = 0; j < GeneticParameter.getGroupExpertSize(); j++) {
                if (target[j] == expertNumber) {
                    expertNumber = random.nextInt(GeneticParameter.getExpertNumberMax()) + GeneticParameter.getExpertNumberMin();
                    j = -1;
                }
            }

            //排除编号与前面的重复
            for (int k = 0; k < i; k++) {
                if (expertNumber == tmp[k]) {
                    expertNumber = random.nextInt(GeneticParameter.getExpertNumberMax()) + GeneticParameter.getExpertNumberMin();
                    k = -1;
                }
            }
            //经过验证后，把数组放在tmp中
            tmp[i] = expertNumber;
        }
        return tmp;
    }

    //修改某一组的某一专家编号，因此需要提供哪一组，以及具体数据。
    public void setGroupExpert(int[] target, int point) {
        if (target.length != GeneticParameter.getGroupExpertSize()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int i = 0; i < GeneticParameter.getGroupExpertSize(); i++) {
            chrome.chromosome[point][i] = target[i];
        }
    }

    //--------------------------------------------------------------------


    public Individual() {
        chrome = new Chromosome();
        geneLength = chrome.getChromosomeLength();
        fitness = 0.0;
        targetValue = 0.0;
        expertCount = 0;
        punishmentTotal = 0;
        compactnessTotal = 0;
        compactnessTotalUniform = 0;
        punishmentTotalUniform = 0;
        expertCountUniform = 0;
    }

    @Override
    public Individual clone() {
        Individual individualTmp = null;
        try {
            individualTmp = (Individual) super.clone();
            individualTmp.chrome = this.chrome.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return individualTmp;
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < GeneticParameter.groupSize; i++) {
            for (int j = 0; j < GeneticParameter.getGroupExpertSize(); j++) {
                str = str + " " + chrome.chromosome[i][j] + " ";
            }
            str = str + " @  ";
        }
        return super.toString() + "适应度是：" + fitness + str;
    }

}
