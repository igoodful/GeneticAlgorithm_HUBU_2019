package com.xkpt;

public class ExpertGroup {
    public Expert[][] experts;




    public ExpertGroup() {
        experts = new Expert[GeneticParameter.groupSize][GeneticParameter.getGroupExpertSize()];
    }

}
