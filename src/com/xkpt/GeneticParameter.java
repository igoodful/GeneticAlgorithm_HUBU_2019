package com.xkpt;

public class GeneticParameter {
    //分多少分组？
    public static final    int groupSize =17;
    //每组分配多少专家？
    public static final int groupExpertSize = 9;
    //专家编号最小值
    public static final int expertNumberMin = 1;
    //专家编号最大值
    public static final int expertNumberMax = 1300;

    //每个专家最少评议分组数
    public static final int reviewMin = 1;
    //每个专家最多评议分组数
    public static final int reviewMax = 1;
    //紧密度百分比
    public static double compactnessPercent = 0.6;
    //专家数量百分比
    public static double expertCountPercent = 0.1;
    //惩罚函数百分比
    public static double punishmentTotalPercent = 1.0 - compactnessPercent - expertCountPercent;

    //种群大小
    public static final   int populationSize = 50;
    //最小种群大小
    public static final   int populationSizeMin = 50;
    //最大种群大小
    public static final   int populationSizeMax = 160;
    //进化代数
    public static final  int evolutionGenerationSize = 500000;
    //交叉概率
    public static final  double crossoverRate = 0.7;
    //最小交叉概率
    public static final  double crossoverRateMin = 0.25;
    //最大交叉概率
    public static final  double crossoverRateMax = 0.99;
    //变异概率
    public static final  double mutateRate = 0.8;
    //单基因变异概率
    public static final  double SingleMutateRate = 0.8;
    //最小变异概率
    public static final  double mutateRateMin = 0.001;
    //最大变异概率
    public static final  double mutateRateMax = 0.2;

    public GeneticParameter() {

    }

    public static int getGroupSize() {
        return groupSize;
    }

    public static int getGroupExpertSize() {
        return groupExpertSize;
    }

    public static int getExpertNumberMin() {
        return expertNumberMin;
    }

    public static int getExpertNumberMax() {
        return expertNumberMax;
    }

    public static int getPopulationSize() {
        return populationSize;
    }

    public static int getEvolutionGenerationSize() {
        return evolutionGenerationSize;
    }

    public static double getCrossoverRate() {
        return crossoverRate;
    }

    public static double getMutateRate() {
        return mutateRate;
    }

    @Override
    public  String toString() {
        String tmp =   "分组数量" + groupSize + "   "
                +  "组内专家数量" + groupExpertSize+ "   "
                +  "专家编号最小值" + expertNumberMin+ "   "
                +  "专家编号最大值" + expertNumberMax+ "   "
                +  "reviewMin " + reviewMin+ "   "
                +  "reviewMax " + reviewMax+ "   "
                +  "compactnessPercent " + compactnessPercent+ "   "
                +  "expertCountPercent " + expertCountPercent+ "   "
                +  "punishmentTotalPercent " + punishmentTotalPercent+ "   "
                +  "populationSize " + populationSize+ "   "
                +  "populationSizeMin " + populationSizeMin+ "   "
                +  "populationSizeMax " + populationSizeMax+ "   "
                +  "evolutionGenerationSize " + evolutionGenerationSize+ "   "
                +  "crossoverRate " + crossoverRate+ "   "
                +  "crossoverRateMin " + crossoverRateMin+ "   "
                +  "crossoverRateMax " + crossoverRateMax+ "   "
                +  "mutateRate " + mutateRate+ "   "
                +  "SingleMutateRate " + SingleMutateRate+ "   "
                +  "mutateRateMin " + mutateRateMin+ "   "
                +  "mutateRateMax " + mutateRateMax+ "   ";
        return super.toString() + tmp;
    }
}
