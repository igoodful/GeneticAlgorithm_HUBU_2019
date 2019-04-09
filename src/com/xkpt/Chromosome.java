package com.xkpt;

public class Chromosome implements Cloneable{

    public int[][] chromosome;
    public int groupSize;
    public int groupExpertSize;
    public int chromosomeLength;//染色体长度

    public Chromosome() {
        this.groupSize = GeneticParameter.groupSize;
        this.groupExpertSize = GeneticParameter.groupExpertSize;
        this.chromosome = new int[GeneticParameter.groupSize][GeneticParameter.groupExpertSize];
        this.chromosomeLength = GeneticParameter.groupSize * GeneticParameter.groupExpertSize;
        for (int i = 0; i < this.groupSize; i++){
            for (int j = 0; j < this.groupExpertSize; j++)
            this.chromosome[i][j] = 0;
        }
    }

    public boolean setGene(int indexGroup, int indexExpert, Integer gene){
        if ( indexGroup < 0 || indexGroup > this.groupSize + 1 || indexExpert < 0 || indexExpert > this.groupExpertSize + 1)
            return false;
        if ( gene < GeneticParameter.expertNumberMin || gene > GeneticParameter.expertNumberMax)
            return false;
        this.chromosome[indexGroup][indexExpert] = gene;
        return true;
    }

    public int setGene(int indexGroup, int indexExpert){
        if ( indexGroup < 0 || indexGroup > this.groupSize + 1 || indexExpert < 0 || indexExpert > this.groupExpertSize + 1)
            return 0;
        return this.chromosome[indexGroup][indexExpert];
    }

    public int getGroupSize() {
        return groupSize;
    }

    public int getGroupExpertSize() {
        return groupExpertSize;
    }

    public int getChromosomeLength() {
        return chromosomeLength;
    }

    @Override
    public Chromosome clone()  {
        Chromosome tmp = null;
        try {
            tmp = (Chromosome) super.clone();
            tmp.chromosome = new int[GeneticParameter.groupSize][GeneticParameter.groupExpertSize];
            for (int i = 0; i < GeneticParameter.groupSize; i++){
                tmp.chromosome[i] = this.chromosome[i].clone();
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return tmp;
    }
}
