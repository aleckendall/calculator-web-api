package CalculatorWebApp;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class MathProblemStorageSingleton
{
    private List<MathProblemPojo> mathProblems;

    public MathProblemStorageSingleton()
    {
        this.mathProblems = new ArrayList<>();
    }

    public List<MathProblemPojo> getMathProblems() {
        return mathProblems;
    }

    public void addMathProblem(MathProblemPojo mathProblem) {
        this.mathProblems.add(mathProblem);
    }
}
