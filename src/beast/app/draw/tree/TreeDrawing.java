package beast.app.draw.tree;

import beast.core.Description;
import beast.core.Input;
import beast.core.Plugin;
import beast.evolution.tree.Tree;
import beast.evolution.tree.coalescent.TreeIntervals;

import java.util.Arrays;

/**
 * @author Alexei Drummond
 */

@Description("Encapsulates a tree and options to draw it within the context of a TikzTreeFigure.")
public class TreeDrawing extends Plugin {

    enum TreeDrawingStyle {triangle, square, vertical, unrooted}

    public Input<Tree> treeInput = new Input<Tree>("tree", "a phylogenetic tree", Input.Validate.REQUIRED);
    public Input<Double> lineThicknessInput = new Input<Double>("lineThickness", "indicates the thickness of the lines", 1.0);
    public Input<Double> labelOffsetInput = new Input<Double>("labelOffset", "indicates the distance from leaf node to its label in pts", 5.0);
    public Input<Boolean> showLeafLabelsInput = new Input<Boolean>("showLeafLabels", "if true then the taxa labels are displayed", true);
    public Input<String> branchLabelsInput = new Input<String>("branchLabels", "the attribute name of values to display on the branches, or empty string if no branch labels to be displayed", "");
    public Input<Boolean> showInternalNodeTimes = new Input<Boolean>("showInternalNodeTimes", "if true then dotted lines are displayed at each internal node height.", false);
    public Input<Boolean> showLeafTimes = new Input<Boolean>("showLeafTimes", "if true then dotted lines are displayed at each unique leaf height.", true);
    public Input<TreeDrawingStyle> treeDrawingStyleInput = new Input<TreeDrawingStyle>("style", "The drawing style for trees. Valid values are " +
            Arrays.toString(TreeDrawingStyle.values()) + " (default 'square')", TreeDrawingStyle.square, TreeDrawingStyle.values());

    private TreeIntervals treeIntervals;
    TreeComponent treeComponent;

    public TreeDrawing() {
    }

    public TreeDrawing(Tree tree) throws Exception {
        init(tree);
    }

    public TreeComponent getComponent() {
        return treeComponent;
    }

    public void initAndValidate() throws Exception {
        if (showInternalNodeTimes.get() || showLeafTimes.get()) {
            treeIntervals = new TreeIntervals(treeInput.get());
        }
        switch (treeDrawingStyleInput.get()) {
            case triangle:
                treeComponent = new TreeComponent(this, true);
                break;
            case square:
                treeComponent = new SquareTreeComponent(this);
                break;
            case vertical:
                treeComponent = new VerticalTreeComponent(this);
                break;
            case unrooted:
                treeComponent = new UnrootedTreeComponent(this);
                break;
        }
    }

    public Tree getTree() {
        return treeInput.get();
    }

    public TreeIntervals getTreeIntervals() {
        return treeIntervals;
    }

    public String getBranchLabels() {
        return branchLabelsInput.get();
    }

    public double getLabelOffset() {
        return labelOffsetInput.get();
    }

    public boolean showInternalNodeTimes() {
        return showInternalNodeTimes.get();
    }

    public boolean showLeafTimes() {
        return showLeafTimes.get();
    }

    public boolean showLeafNodes() {
        return showLeafLabelsInput.get();
    }

    public double getLineThickness() {
        return lineThicknessInput.get();
    }
}
