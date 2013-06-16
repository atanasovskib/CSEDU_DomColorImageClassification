package edu.fcse.domcolorclassifier;

import java.awt.color.CMMException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import edu.fcse.domcolorclassifier.algorithms.AddToMultipleCentersAlgorithm;
import edu.fcse.domcolorclassifier.algorithms.AlgorithmToApply;
import edu.fcse.domcolorclassifier.colorutils.ColorConvertor;
import edu.fcse.domcolorclassifier.colorutils.CustColor;

public class ClassificatorCLI {
	private static List<CustColor> gravityCenters;
	private static AlgorithmToApply algorithm = new AddToMultipleCentersAlgorithm();
	private static boolean fixedValue = true;
	/**
	 * @param args
	 *            InputFolder ColorSpace(rgb,xyz,lab,luv)
	 *            WeightFunction(0=none,1=1/d, 2=exp) useSmoothing(0=no,
	 *            1=avg,2=min) DiscardDistance NumColors(centroids)
	 *            ColorCentroids(name#RRGGBB)
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		args = new String[] {
				"/media/delix/9E1ED1451ED116DD/Users/Delix/Desktop/data1/blue/",
				"rgb", "1", "1", "180", "3", "red#FF0000", "green#00FF00",
				"blue#0000FF" };
		if (args.length < 7) {
			usage();
			System.exit(-1);
		}

		int numCentroids = Integer.parseInt(args[5]);

		gravityCenters = new ArrayList<CustColor>(numCentroids);
		CustColor.ColorSpace space = getColorSpace(args[1]);
		MethodToApply mta = MethodToApplyFactory.getMethod(space, args[2],
				args[3], "0", args[4]);
		try {
			for (int i = 0; i < numCentroids; i++) {
				String color[] = args[i + 6].split("#");
				String colorName = color[0];

				float[] det = convertStringToSpace(space, color[1]);
				gravityCenters.add(new CustColor(colorName, det));
			}
		} catch (IndexOutOfBoundsException e) {
			System.err
					.println("Number of colors (centroids) exceeds entered colors in arguments");
		}
		//args[0] = "/home/delix/tt/";
		File initFolder = new File(args[0]);
		if (!initFolder.exists() || !initFolder.isDirectory()) {
			throw new Exception(args[0] + " not present or not a directory");
		}
		goThroughFiles(initFolder, mta);

		System.out.println("Done!");
		
	}

	private static float[] convertStringToSpace(CustColor.ColorSpace space,
			String colorRGB) {
		int r = Integer.parseInt(colorRGB.substring(0, 2), 16);
		int g = Integer.parseInt(colorRGB.substring(2, 4), 16);
		int b = Integer.parseInt(colorRGB.substring(4), 16);
		float[] rgb = new float[] { r, g, b };
		switch (space) {
		case RGB:
			return rgb;
		case XYZ:
			return ColorConvertor.convertRGB2XYZ(rgb);
		case Lab:
			return ColorConvertor.convertRGB2LabNorm(rgb);
		}
		System.err.println("Only rgb, xyz and Lab supported");
		System.exit(-1);
		return null;
	}

	private static CustColor.ColorSpace getColorSpace(String spaceString) {
		switch (spaceString) {
		case "rgb":
			return CustColor.ColorSpace.RGB;
		case "xyz":
			return CustColor.ColorSpace.XYZ;
		case "lab":
			return CustColor.ColorSpace.Lab;

		default:
			System.err.println("Onlt rgb, xyz and lab supported");
			System.exit(-1);
		}
		return null;
	}

	private static void usage() {
		System.out
				.println("arguments needed: InputFolder ColorSpace(rgb,xyz,lab,luv) "
						+ "WeightFunction(0=none,1=1/d, 2=exp) useSmoothing(0=no,1=avg,2=min)"
						+ "DiscardDistance NumColors(centroids) ColorCentroids(name#RRGGBB)");
	}

	private static void goThroughFiles(File someFolder, MethodToApply method) {
		File[] allFiles = someFolder.listFiles();
		Map<String, Integer> rez = new HashMap<>();
		for (CustColor c : gravityCenters) {
			rez.put(c.getName(), 0);
		}
		System.out.println("--------------------------------\nClassifying...");
		for (File sampleFile : allFiles) {
			if (sampleFile.isDirectory()) {
				goThroughFiles(sampleFile, method);
			} else {
				System.gc();
				try {
					System.out.println("Classyfing image: "
							+ sampleFile.getName());

					BufferedImage loadedImage;
					try {
						loadedImage = ImageIO.read(sampleFile);
					} catch (CMMException ex) {
						System.err.println(ex.getMessage());
						continue;
					}
					System.out.println("WxH: " + loadedImage.getWidth() + ":"
							+ loadedImage.getHeight());

					String detect = algorithm.classifyImage(loadedImage,
							method, gravityCenters, fixedValue);
					if (detect.equals("")) {

						continue;
					}
					System.out.println("Classified as: " + detect);
					System.out.println("----------------------------------");

					rez.put(detect, rez.get(detect) + 1);

				} catch (IOException e) {
					System.err
							.println("Image file damaged, unrecognized format or not an image format at all");
					e.printStackTrace();
				}
			}
		}
		System.out.println("RESULTS: " + rez.toString());
	}

}
