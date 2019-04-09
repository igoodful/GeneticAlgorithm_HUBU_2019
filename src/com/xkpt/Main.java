package com.xkpt;
import javax.jws.WebMethod;
import javax.jws.WebService;
@WebService
public class Main {
    @WebMethod
    public String geneticAlgorithm(int generation, double fitness){
        Population p = new Population(GeneticParameter.getPopulationSize());
        p.initPopulation();
        for (int i = 0; i < generation; i++){
            p.evolve();
            if (fitness <= p.currentBestIndividual.fitness){
                return p.currentBestIndividual.toString();
            }
        }
        return  p.currentBestIndividual.toString();
    }
}