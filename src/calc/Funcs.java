package calc;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class Funcs {
	public static BufferedImage rotate(BufferedImage image, double _theta, int _thetaInDegrees) {

	    AffineTransform xform = new AffineTransform();

	    if (image.getWidth() > image.getHeight()) {
	        xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getWidth());
	        xform.rotate(_theta);

	        int diff = image.getWidth() - image.getHeight();

	        switch (_thetaInDegrees) {
	            case 90:
	                xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
	                break;
	            case 180:
	                xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth() + diff);
	                break;
	            default:
	                xform.translate(-0.5 * image.getWidth(), -0.5 * image.getWidth());
	                break;
	        }
	    } else if (image.getHeight() > image.getWidth()) {
	        xform.setToTranslation(0.5 * image.getHeight(), 0.5 * image.getHeight());
	        xform.rotate(_theta);

	        int diff = image.getHeight() - image.getWidth();

	        switch (_thetaInDegrees) {
	            case 180:
	                xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
	                break;
	            case 270:
	                xform.translate(-0.5 * image.getHeight() + diff, -0.5 * image.getHeight());
	                break;
	            default:
	                xform.translate(-0.5 * image.getHeight(), -0.5 * image.getHeight());
	                break;
	        }
	    } else {
	        xform.setToTranslation(0.5 * image.getWidth(), 0.5 * image.getHeight());
	        xform.rotate(_theta);
	        xform.translate(-0.5 * image.getHeight(), -0.5 * image.getWidth());
	    }

	    AffineTransformOp op = new AffineTransformOp(xform, AffineTransformOp.TYPE_BILINEAR);

	    return op.filter(image, null);
	}
}
