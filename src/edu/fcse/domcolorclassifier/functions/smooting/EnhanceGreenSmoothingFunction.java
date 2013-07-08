package edu.fcse.domcolorclassifier.functions.smooting;

public class EnhanceGreenSmoothingFunction extends SmoothingFunction {

    float percent;

    /**
     *
     * @param percent to enhance green 0-100%
     */
    public EnhanceGreenSmoothingFunction(float percent) {
        this.percent = percent;
    }

    @Override
    public float[][][] smooth(float[][][] tmp) {
        for (int i = 0; i < tmp.length; i++) {
            for (int j = 0; j < tmp[0].length; j++) {
                tmp[i][j][2] += (tmp[i][j][2] / 100.0f) * percent;
                if (tmp[i][j][2] > 255) {
                    tmp[i][j][2] = 255;
                }
            }

        }
        return tmp;
    }
}
