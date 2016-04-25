package teachingalgorithms.logic.sorting;

import teachingalgorithms.logic.sorting.step.SortingStep;

public abstract class SortingAlgorithm {

    public abstract void doAllSteps();

    public abstract boolean doStep();

    public abstract int getInputSize();

    public abstract int getProtocolSize();

    public abstract SortingStep getStep(int stepNumber);

    public abstract void reset();

    public abstract String protocol2LaTeX();

    public abstract String step2LaTeX(int i);

    public abstract boolean undoStep();

    public abstract String getName();

    public abstract int getStepLimit();


}