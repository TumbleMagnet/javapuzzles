package be.rouget.puzzles.adventofcode.year2023.day19;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class WorkflowTest {

    @Test
    void testParse() {
        Workflow actual = Workflow.parse("cq{a<289:R,a<499:A,m>826:A,A}");
        Workflow expected = new Workflow("cq", List.of(
               new Rule(new RuleCondition("a", ConditionOperator.SMALLER_THAN, 289), RuleOutcome.partRejected()),
               new Rule(new RuleCondition("a", ConditionOperator.SMALLER_THAN, 499), RuleOutcome.partAccepted()),
               new Rule(new RuleCondition("m", ConditionOperator.GREATER_THAN, 826), RuleOutcome.partAccepted()),
               new Rule(null, RuleOutcome.partAccepted())
        ));
        assertThat(actual).isEqualTo(expected);
    }
    
    // 
    @Test
    void testParseWithWorkflowDestinations() {
        Workflow actual = Workflow.parse("qhz{s<906:mxf,s<1178:xc,s<1411:nn,bgc}");
        Workflow expected = new Workflow("qhz", List.of(
                new Rule(new RuleCondition("s", ConditionOperator.SMALLER_THAN, 906), RuleOutcome.moveToWorflow("mxf")),
                new Rule(new RuleCondition("s", ConditionOperator.SMALLER_THAN, 1178), RuleOutcome.moveToWorflow("xc")),
                new Rule(new RuleCondition("s", ConditionOperator.SMALLER_THAN, 1411), RuleOutcome.moveToWorflow("nn")),
                new Rule(null, RuleOutcome.moveToWorflow("bgc"))
        ));
        assertThat(actual).isEqualTo(expected);
    }
}