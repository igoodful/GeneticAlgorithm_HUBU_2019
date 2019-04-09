package com.xkpt;

import java.sql.SQLException;
import java.util.Random;

public class Population {

    public int populationSize;    //群体大小
    public int evolutionGenerationSize;    //到目前为止，种群当前进化代数
    public int chromosomeLength;    //基因长度

    public double averageFitness;    //平均适应度
    public double[] relativeFitness;    //相对适应度
    public double[] accumulateRelativeFitness; //累积适应度

    public Individual[] populationArray;    //当前种群集合
    public Individual bestIndividual;    //当前代适应度最好的个体
    public int bestIndividualIndex;    //bestIndividual对应的数组下标
    public Individual worstIndividual;   //当前代适应度最差的个体
    public int worstIndividualIndex; //worstIndividual对应的数组下标
    public Individual currentBestIndividual; //到目前代为止最好的个体


    //********************************************************************************
    //初始化种群，随机产生populationSize个个体,并更新每个个体的属性。
    public void initPopulation() {
        for (int i = 0; i < this.populationSize; i++) {
            populationArray[i].randomGenerate();
        }
    }

    //只有当个体的编号发生改变后，就必须要更新个体属性。
    public void updateIndividual() {
        for (int i = 0; i < populationSize; i++) {
            try {
                populationArray[i].calculateProperty();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //更新种群属性。先更新每个个体属性。
    public void updateProperty() {
        //首先，更新个体属性。
        updateIndividual();
        //更新种群相关属性。
        updateFitness();
        //更新最好和最差个体
        updateBestAndWorst();
        //更新进化代数
        updateEvolutionGenerationSize();
        //更新到目前为止的最佳个体
        updateCurrentBestIndividual();
    }

    //更新最佳适应度和最坏适应度的个体，需要保证每个个体属性先更新
    public void updateBestAndWorst() {
        bestIndividual = populationArray[0];
        worstIndividual = populationArray[0];
        for (int i = 1; i < populationSize; i++) {
            if (populationArray[i].fitness > bestIndividual.fitness) {
                bestIndividual = populationArray[i].clone();
                bestIndividualIndex = i;
            }
            if (populationArray[i].fitness < worstIndividual.fitness) {
                worstIndividual = populationArray[i].clone();
                worstIndividualIndex = i;
            }
        }
    }

    //更新直到当前为止最佳的个体，需要保证每个个体属性先更新
    public void updateCurrentBestIndividual() {
        if (evolutionGenerationSize == 1) {
            currentBestIndividual = bestIndividual.clone();
        } else {
            if (bestIndividual.fitness > currentBestIndividual.fitness) {
                currentBestIndividual = bestIndividual.clone();
            }
        }
    }

    //更新种群进化代数，需要保证每个个体属性先更新。
    public void updateEvolutionGenerationSize() {
        evolutionGenerationSize++;
    }


    //更新种群的适应度相关属性。需要保证每个个体属性先更新。
    public void updateFitness() {
        double totalFitness = 0.0;
        //计算种群的适应度之和
        for (int i = 0; i < populationSize; i++) {
            totalFitness = totalFitness + populationArray[i].fitness;
        }
        //
        relativeFitness[0] = populationArray[0].fitness / totalFitness;
        accumulateRelativeFitness[0] = relativeFitness[0];
        double tmp = relativeFitness[0];
        for (int i = 1; i < populationSize - 1; i++) {
            relativeFitness[i] = populationArray[i].fitness / totalFitness;
            tmp = tmp + relativeFitness[i];
            accumulateRelativeFitness[i] = relativeFitness[i] + accumulateRelativeFitness[i - 1];
        }
        //计算最后一个元素的相对适应度
        relativeFitness[populationSize - 1] = 1.0 - tmp;
        //计算累积适应度
        accumulateRelativeFitness[populationSize - 1] = 1;
        //计算平均适应度。
        averageFitness = totalFitness / populationSize;
    }


    //选择，采用保存最优个体和轮盘方法结合。
    //最佳个体替换掉最差个体。
    private void optimumAndRouletteWheelSelection() {
        populationArray[worstIndividualIndex] = populationArray[bestIndividualIndex].clone();
        Individual[] childPopulation = new Individual[populationSize];
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            double tmpRandom = random.nextDouble();
            if (i != bestIndividualIndex) {
                for (int j = 0; j < populationSize; j++) {
                    if (tmpRandom < accumulateRelativeFitness[j]) {
                        childPopulation[i] = populationArray[j].clone();
                        break;
                    }
                }
            } else {
                childPopulation[bestIndividualIndex] = populationArray[bestIndividualIndex];
            }
        }
        for (int k = 0; k < populationSize; k++) {
            System.arraycopy(childPopulation, 0, populationArray, 0, childPopulation.length);
        }
    }

    //单点交叉操作。
    public void singleCross() {
        int[] randomMatch = produceRandomMatch(populationSize);
        Random random = new Random();
        //确定交叉点
        int point;
        double crossRate;
        for (int i = 0; i < randomMatch.length; i += 2) {
            crossRate = random.nextDouble();
            if (crossRate <= GeneticParameter.getCrossoverRate()) {
                point = random.nextInt(GeneticParameter.groupExpertSize);
                exchange(point, populationArray[randomMatch[i]], populationArray[randomMatch[i + 1]]);
            }
        }
    }
    //均匀交叉
    private void averageCross() throws SQLException {
        Random random = new Random();
        int[] randomMatch = produceRandomMatch(populationSize);
        for (int i = 0; i < randomMatch.length; i += 2) {
            double averageCrossRate = random.nextDouble();
            if (averageCrossRate <= GeneticParameter.getCrossoverRate()) {
                if (randomMatch[i] != bestIndividualIndex && randomMatch[i + 1] != bestIndividualIndex) {
                    produceGeneration(populationArray[randomMatch[i]].chrome.chromosome, populationArray[randomMatch[i + 1]].chrome.chromosome);
                } else {
                    if (randomMatch[i] == bestIndividualIndex) {
                        Individual tmpBestIndividual = bestIndividual.clone();
                        Individual another = populationArray[randomMatch[i + 1]].clone();
                        produceGeneration(populationArray[randomMatch[i]].chrome.chromosome, populationArray[randomMatch[i + 1]].chrome.chromosome);
                        populationArray[randomMatch[i]].calculateProperty();
                        populationArray[randomMatch[i + 1]].calculateProperty();
                        if (populationArray[randomMatch[i]].fitness < tmpBestIndividual.fitness && populationArray[randomMatch[i + 1]].fitness < tmpBestIndividual.fitness) {
                            populationArray[randomMatch[i]] = tmpBestIndividual.clone();
                            populationArray[randomMatch[i + 1]] = another.clone();
                        }
                    } else {
                        Individual tmpBestIndividual = bestIndividual.clone();
                        Individual another = populationArray[randomMatch[i]].clone();
                        produceGeneration(populationArray[randomMatch[i]].chrome.chromosome, populationArray[randomMatch[i + 1]].chrome.chromosome);
                        populationArray[randomMatch[i]].calculateProperty();
                        populationArray[randomMatch[i + 1]].calculateProperty();
                        if (populationArray[randomMatch[i]].fitness < tmpBestIndividual.fitness && populationArray[randomMatch[i + 1]].fitness < tmpBestIndividual.fitness) {
                            populationArray[randomMatch[i]] = another.clone();
                            populationArray[randomMatch[i + 1]] = tmpBestIndividual.clone();
                        }
                    }
                }
            }
        }
    }

    private void produceGeneration(int[][] father, int mother[][]) {
        int[][] son = new int[GeneticParameter.groupSize][GeneticParameter.getGroupExpertSize()];
        int[][] girl = new int[GeneticParameter.groupSize][GeneticParameter.getGroupExpertSize()];
        Random random = new Random();
        for (int i = 0; i < GeneticParameter.groupSize; i++) {
            int selectSon = random.nextInt(2);
            int selectGirl = random.nextInt(2);
            if (selectSon == 0) {
                System.arraycopy(father[i], 0, son[i], 0, father[i].length);
            } else {
                System.arraycopy(mother[i], 0, son[i], 0, mother[i].length);
            }
            if (selectGirl == 0) {
                System.arraycopy(father[i], 0, mother[i], 0, father[i].length);
            }
        }
        for (int j = 0; j < GeneticParameter.groupSize; j++) {
            System.arraycopy(son[j], 0, father[j], 0, son[j].length);
        }
    }

    //产生随机组合的数组来配对
    public static int[] produceRandomMatch(int bound) {
        if (bound < 2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        Random random = new Random();
        int[] indexTmp = new int[bound / 2 * 2];
        for (int i = 0; i < bound / 2 * 2; i++) {
            indexTmp[i] = random.nextInt(bound);
            for (int j = 0; j < i; j++) {
                if (indexTmp[j] == indexTmp[i]) {
                    indexTmp[i] = random.nextInt(bound);
                    j = -1;
                }
            }
        }
        return indexTmp;
    }

    private void exchange(int point, Individual a, Individual b) {
        if (point > GeneticParameter.groupExpertSize - 1 || point < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        int[] tmp = new int[GeneticParameter.groupExpertSize];
        System.arraycopy(a.chrome.chromosome[point],0, tmp, 0, a.chrome.chromosome[point].length);
        System.arraycopy(b.chrome.chromosome[point],0, a.chrome.chromosome[point], 0, b.chrome.chromosome[point].length);
        System.arraycopy(tmp,0, b.chrome.chromosome[point], 0, tmp.length);
    }

    //单点变异。
    private void singleMutate() throws SQLException {
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            double mutateRateTMP = random.nextDouble();
            int groupIndex = random.nextInt(GeneticParameter.groupSize);
            int expertIndex = random.nextInt(GeneticParameter.groupExpertSize);
            int singleGene = produceSingleGene(populationArray[i].chrome.chromosome[groupIndex]);
            if (mutateRateTMP <= GeneticParameter.SingleMutateRate) {
                int tmp = populationArray[i].chrome.chromosome[groupIndex][expertIndex];
                populationArray[i].calculateProperty();
                double fitnessTMP = populationArray[i].fitness;
                populationArray[i].chrome.chromosome[groupIndex][expertIndex] = singleGene;
                populationArray[i].calculateProperty();
                if (populationArray[i].fitness < fitnessTMP) {
                    populationArray[i].chrome.chromosome[groupIndex][expertIndex] = tmp;
                }
            }
        }
    }

    //产生单个专家编号。但是需要排除同组的专家编号。
    private int produceSingleGene(int[] target) {
        Random random = new Random();
        int tmp = random.nextInt(GeneticParameter.getExpertNumberMax()) + GeneticParameter.getExpertNumberMin();
        for (int i = 0; i < target.length; i++) {
            if (target[i] == tmp) {
                tmp = random.nextInt(GeneticParameter.getExpertNumberMax()) + GeneticParameter.getExpertNumberMin();
                i = -1;
            }
        }
        return tmp;
    }

    //确定变异，当变异发生在最佳个体上时，需要判断变异是否变得更好。
    private void mutate() throws SQLException {
        Random random = new Random();
        int[] expertGroupTmp;
        for (int i = 0; i < populationSize; i++) {
            double mutateRateTmp = random.nextDouble();
            int pointTmp = random.nextInt(GeneticParameter.getGroupExpertSize());
            if (mutateRateTmp <= GeneticParameter.getMutateRate()) {
                expertGroupTmp = populationArray[i].randomGroup(populationArray[i].chrome.chromosome[pointTmp]);
                if (i == bestIndividualIndex) {
                    Individual tmp = populationArray[i].clone();
                    populationArray[i].setGroupExpert(expertGroupTmp, pointTmp);
                    populationArray[i].calculateProperty();
                    if (populationArray[i].fitness < bestIndividual.fitness) {
                        populationArray[i] = tmp.clone();
                    } else {
                        System.out.println(populationArray[i]);
                    }
                } else {
                    populationArray[i].setGroupExpert(expertGroupTmp, pointTmp);
                }
            }
        }
    }

    //更新个体和种群属性，然后选择、交叉、变异。并循环此操作就是进化。每进化一代就需要重新计算个体和种群属性。
    public void evolve() {
        updateProperty();
        //最佳保留和轮盘相结合。
        optimumAndRouletteWheelSelection();
        try {
            averageCross();
            singleMutate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //判断进化代数是否达到设定的值。
    public boolean isEvolutionDone() {
        return evolutionGenerationSize >= GeneticParameter.getEvolutionGenerationSize();
    }

    //×××××××××××××××××××××××××××××××××××××××××××××××××
    public Population(int size) {
        if (size > 0) {
            populationSize = size;
            evolutionGenerationSize = 0;
            averageFitness = 0.0;
            relativeFitness = new double[populationSize];
            populationArray = new Individual[populationSize];
            accumulateRelativeFitness = new double[populationSize];
            for (int i = 0; i < populationSize; i++) {
                populationArray[i] = new Individual();
            }
            this.chromosomeLength = populationArray[0].geneLength;
        }
    }
}
