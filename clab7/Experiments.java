import edu.princeton.cs.algs4.StdRandom;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hug.
 */
public class Experiments {
    public static void experiment1() {
        BST<Integer> tree = new BST<>();
        List<Integer> numOfItems = new ArrayList<>();
        List<Double> avgDepth = new ArrayList<>();
        List<Double> OptimalAvgDepth = new ArrayList<>();
        for (int i = 1; i <= 5000; i++) {
            numOfItems.add(i);
            tree.add(StdRandom.uniform(10000));
            avgDepth.add(tree.avgDepth());
            OptimalAvgDepth.add(ExperimentHelper.optimalAverageDepth(i));
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("Average BST", numOfItems, avgDepth);
        chart.addSeries("Optimal average BST", numOfItems, OptimalAvgDepth);

        new SwingWrapper(chart).displayChart();

    }

    public static void experiment2() {
        BST<Integer> tree = new BST<>();
        List<Integer> numOfOps = new ArrayList<>();
        List<Double> avgDepth = new ArrayList<>();

        ExperimentHelper.randomInsert(tree, 20000, 500);
        numOfOps.add(0);
        avgDepth.add(tree.avgDepth());

        for (int i = 1; i <= 200000; i++) {
            numOfOps.add(i);
            ExperimentHelper.DeleteTakingSuccessor(tree);
            ExperimentHelper.randomInsert(tree, 20000, 1);
            avgDepth.add(tree.avgDepth());
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("asymmetric Hibbard Deletion", numOfOps, avgDepth);


        new SwingWrapper(chart).displayChart();

    }

    public static void experiment3() {
        BST<Integer> tree = new BST<>();
        List<Integer> numOfOps = new ArrayList<>();
        List<Double> avgDepth = new ArrayList<>();

        ExperimentHelper.randomInsert(tree, 20000, 5000);
        numOfOps.add(0);
        avgDepth.add(tree.avgDepth());

        for (int i = 1; i <= 20000; i++) {
            numOfOps.add(i);
            ExperimentHelper.DeleteTakingRandom(tree);
            ExperimentHelper.randomInsert(tree, 20000, 1);
            avgDepth.add(tree.avgDepth());
        }

        XYChart chart = new XYChartBuilder().width(800).height(600).xAxisTitle("x label").yAxisTitle("y label").build();
        chart.addSeries("Random Deletion", numOfOps, avgDepth);


        new SwingWrapper(chart).displayChart();

    }

    public static void main(String[] args) {
        //experiment1();
        experiment2();
        //experiment3();
    }
}
