package com.xinlan.yokirender.core.math;

import android.graphics.Color;


/**
 * A four-element color represented by single precision floating point
 * x, y, z, and w values.  The x, y, z, and w values represent the red,
 * blue, green, and alpha color values, respectively. Color and alpha
 * components should be in the range [0.0, 1.0].
 * <p>
 * Java 3D assumes that a linear (gamma-corrected) visual is used for
 * all colors.
 *
 */
public class Color4f extends Tuple4f implements java.io.Serializable {

    // Compatible with 1.1
    static final long serialVersionUID = 8577680141580006740L;

    /**
     * Constructs and initializes a Color4f from the specified xyzw
     * coordinates.
     * @param x the red color value
     * @param y the green color value
     * @param z the blue color value
     * @param w the alpha value
     */
    public Color4f(float x, float y, float z, float w) {
	super(x,y,z,w);
    }


    /**
     * Constructs and initializes a Color4f from the array of length 4.
     * @param c the array of length 4 containing r,g,b,a in order
     */
    public Color4f(float[] c) {
	super(c);
    }


    /**
     * Constructs and initializes a Color4f from the specified Color4f.
     * @param c1 the Color4f containing the initialization r,g,b,a data
     */
    public Color4f(Color4f c1) {
	super(c1);
    }


    /**
     * Constructs and initializes a Color4f from the specified Tuple4f.
     * @param t1 the Tuple4f containing the initialization r,g,b,a data
     */
    public Color4f(Tuple4f t1) {
	super(t1);
    }


    /**
     * Constructs and initializes a Color4f from the specified Tuple4d.
     * @param t1 the Tuple4d containing the initialization r,g,b,a data
     */
    public Color4f(Tuple4d t1) {
	super(t1);
    }


    /**
     * Constructs and initializes a Color4f from the specified AWT
     * Color object.
     * No conversion is done on the color to compensate for
     * gamma correction.
     *
     * @param color the AWT color with which to initialize this
     * Color4f object
     *
     * @since vecmath 1.2
     */
    public Color4f(Color color) {
	super((float)color.red() / 255.0f,
	      (float)color.green() / 255.0f,
	      (float)color.blue() / 255.0f,
	      (float)color.alpha() / 255.0f);
    }


    /**
     * Constructs and initializes a Color4f to (0.0, 0.0, 0.0, 0.0).
     */
    public Color4f() {
	super();
    }


    /**
     * Sets the r,g,b,a values of this Color4f object to those of the
     * specified AWT Color object.
     * No conversion is done on the color to compensate for
     * gamma correction.
     *
     * @param color the AWT color to copy into this Color4f object
     *
     * @since vecmath 1.2
     */
    public final void set(Color color) {
	x = (float)color.red() / 255.0f;
	y = (float)color.green() / 255.0f;
	z = (float)color.blue() / 255.0f;
	w = (float)color.alpha() / 255.0f;
    }
}
