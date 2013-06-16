package edu.fcse.domcolorclassifier;

import edu.fcse.domcolorclassifier.colorutils.CustColor;
import edu.fcse.domcolorclassifier.functions.distance.DistanceFunction;
import edu.fcse.domcolorclassifier.functions.distance.EuclideanDistanceFunction;
import edu.fcse.domcolorclassifier.functions.smooting.AVGSmoothingFunction;
import edu.fcse.domcolorclassifier.functions.smooting.MINSmoothingFunction;
import edu.fcse.domcolorclassifier.functions.smooting.SmoothingFunction;
import edu.fcse.domcolorclassifier.functions.weight.ExpWeightFunction;
import edu.fcse.domcolorclassifier.functions.weight.ReciWeightFunction;
import edu.fcse.domcolorclassifier.functions.weight.WeightFunction;

public class MethodToApplyFactory {

    public static MethodToApply getMethod(CustColor.ColorSpace space,
            String weight, String smoothing, String distance,
            String discardDistance, boolean fixedValue) {
        int we = Integer.parseInt(weight);
        WeightFunction wf = null;
        switch (we) {
            case 1:
                wf = new ReciWeightFunction();
                break;
            case 2:
                wf = new ExpWeightFunction();
                break;
            default:
                wf = new WeightFunction();
        }
        int sm = Integer.parseInt(smoothing);
        SmoothingFunction sf = null;
        switch (sm) {
            case 1:
                sf = new AVGSmoothingFunction(2);
                break;
            case 2:
                sf = new MINSmoothingFunction();
                break;
            default:
                sf = new SmoothingFunction();
        }
        int di = Integer.parseInt(distance);
        DistanceFunction df = null;
        switch (di) {
            case 0:
                df = new EuclideanDistanceFunction();

                break;
            default:
                System.err
                        .println("MethodToApplyFactory: EuclideanDistance implemented only");
                System.exit(-1);
        }
        return new MethodToApply(sf, space, wf, df,
                Double.parseDouble(discardDistance), fixedValue);
    }
}
