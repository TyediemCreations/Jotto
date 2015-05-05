
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Vector;
import java.awt.geom.NoninvertibleTransformException;
//*/

/**
 * A building block for creating your own shapes that can be
 * transformed and that can respond to input. This class is
 * provided as an example; you will likely need to modify it
 * to meet the assignment requirements.
 * 
 * Michael Terry
 */
public abstract class Sprite {
    
    /**
     * Tracks our current interaction mode after a mouse-down
     */
    protected enum InteractionMode {
        IDLE,
        DRAGGING,
        SCALING,
        ROTATING
    }

    private     Vector<Sprite>      children            = new Vector<Sprite>();     // Holds all of our children
    private     Sprite              parent              = null;                     // Pointer to our parent

    protected   Point2D             lastPoint           = null;                     // Last mouse point
    protected   InteractionMode     interactionMode     = InteractionMode.IDLE;     // Current interaction mode
    
    //
    private int axisX=0;
    private int axisY=0;
    private int footMultiplier = 1;	//to handle feet having an initial rotation not recorded in 'currentRotation'
    private int initLength;

    private double currentRotation=0;
    private double maxRotation;
    private Sprite otherLeg = null;

    //previously 'transform'; split into scaling versus non-scaling transformations
    private AffineTransform scaleTransform = new AffineTransform();
    private AffineTransform nonScaleTransform = new AffineTransform();

    private AffineTransform initialTransform = new AffineTransform();

    //Information used for scaling
    private double xOffset;
    private double xScale = 1;
    private double oldLength;
    private double newLength;
    //
    
    public Sprite(int x, int y, int fullRotation) {
        axisX = x;
	axisY = y;
	maxRotation = Math.toRadians(fullRotation);
    }
    public Sprite(int x, int y, int fullRotation, int length) {
        axisX = x;
	axisY = y;
	maxRotation = Math.toRadians(fullRotation);
	initLength = length;
	newLength = initLength;
    }
    
    public Sprite(Sprite parent, int x, int y, int fullRotation) {
        if (parent != null) {
            parent.addChild(this);
        }
	axisX = x;
	axisY = y;
	maxRotation = Math.toRadians(fullRotation);
    }
    

    public void addChild(Sprite s) {
        children.add(s);
        s.setParent(this);
    }
    public void addOtherLeg(Sprite s) {
       	this.otherLeg = s;
    }
    public Sprite getParent() {
        return parent;
    }
    private void setParent(Sprite s) {
        this.parent = s;
    }

    /**
     * Test whether a point, in world coordinates, is within our sprite.
     */
    public abstract boolean pointInside(Point2D p);

    

    /*
     * returns where a given mouse point is in relation to the 'actual' image being selected. 
	Aids in various rotation calculations
     */
    public Point2D rotateHalp(Point2D p){
	AffineTransform fullTransform = this.getFullTransform();
        AffineTransform inverseTransform = null;
        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        Point2D newPoint = (Point2D)p.clone();
        inverseTransform.transform(newPoint, newPoint);

	return newPoint;
    }

    /**
     * Handles a mouse down event, assuming that the event has already
     * been tested to ensure the mouse point is within our sprite.
     */
    protected void handleMouseDownEvent(MouseEvent e) {
        lastPoint = e.getPoint();	

        if (e.getButton() == MouseEvent.BUTTON1) {
	    if (interactionMode == InteractionMode.SCALING && otherLeg != null) {} 
            else if (parent == null) interactionMode = InteractionMode.DRAGGING;
	    else interactionMode = InteractionMode.ROTATING;
        }

        // Handle rotation, scaling mode depending on input
    }

    /**
     * Handle mouse drag event, with the assumption that we have already
     * been "selected" as the sprite to interact with.
     * This is a very simple method that only works because we
     * assume that the coordinate system has not been modified
     * by scales or rotations.
     */
    protected void handleMouseDragEvent(MouseEvent e) {
        
        Point2D oldPoint = lastPoint;
        Point2D newPoint = e.getPoint();

	Point2D transformedOld = this.rotateHalp(oldPoint); 
	Point2D transformedPoint = this.rotateHalp(newPoint);
	double newX = transformedPoint.getX();
	double oldX = transformedOld.getX();

        switch (interactionMode) {
            case IDLE:
                ; // no-op (shouldn't get here)
                break;
            case DRAGGING:
                double x_diff = newPoint.getX() - oldPoint.getX();
                double y_diff = newPoint.getY() - oldPoint.getY();
                nonScaleTransform.translate(x_diff, y_diff);

                break;
            case ROTATING:
		double theta;
		if (newX==0) theta=0;
		else  
			theta = Math.atan((transformedPoint.getY()-transformedOld.getY())/newX);
		if (Math.abs(currentRotation+theta) > maxRotation){
			if (maxRotation == Math.toRadians(360)) currentRotation=0;
			else if (currentRotation+theta > 0) theta = maxRotation-currentRotation;
			else theta = -maxRotation-currentRotation;
		}
		currentRotation += theta;
		nonScaleTransform.rotate(theta,axisX,axisY);
		
                break;
            case SCALING:
		this.scaleHalp(newX, oldX);
		otherLeg.scaleHalp(newX, oldX);
		
                break;
                
        }
        // Save our last point, if it's needed next time around
        lastPoint = e.getPoint();
    }
    
    /*
     * Mouse up event, if the object selected was a leg 
	then interaction is set to SCALING for the next Mouse down event
     */
    protected void handleMouseUp(MouseEvent e) {
	
	if (otherLeg!=null && interactionMode!=InteractionMode.SCALING) {
		interactionMode = InteractionMode.SCALING;}
	else interactionMode = InteractionMode.IDLE;
    }

    /*
     *  Translates a sprite based on the angle and new length of a scaled parent
     */
    public void translateHalp(double offset){
	double theta;
	double newAngle = currentRotation;

	if (children.isEmpty()) //inferred to be feet
		newAngle = footMultiplier*(Math.toRadians(90)+(footMultiplier)*currentRotation);

	theta = Math.toRadians(90-Math.toDegrees(-newAngle));
	nonScaleTransform.translate(offset*Math.sin(theta), offset*Math.cos(theta));
    }

    /*
     *	Scales a sprite and provides enough information for the children 
	to be translated appropriately 
     */    
    public void scaleHalp(double newX, double oldX){
	double scaleDistance = (newX-oldX)/50;
	if (scaleDistance > 0) scaleDistance+=1;
	else scaleDistance=1+scaleDistance;

	oldLength = newLength;
	if (oldLength < 5 && scaleDistance < 1) return;	//these legs are waaay too small to be scaled down

	xScale = xScale * scaleDistance;
	newLength = initLength*xScale;
	xOffset = newLength-oldLength;

	scaleTransform.scale(scaleDistance,1);	
	if (!children.isEmpty()) children.get(0).translateHalp(xOffset);
    }

    /**
     * Locates the sprite that was hit by the given event.
     * You *may* need to modify this method, depending on
     * how you modify other parts of the class.
     * 
     * @return The sprite that was hit, or null if no sprite was hit
     */
    public Sprite getSpriteHit(MouseEvent e) {
        for (Sprite sprite : children) {
            Sprite s = sprite.getSpriteHit(e);
            if (s != null) {
                return s;
            }
        }
        if (this.pointInside(e.getPoint())) {
            return this;
        }
        return null;
    }
    
    /**
     * Returns the full transform to this object from the root
     */
    public AffineTransform getFullTransform() {
        AffineTransform returnTransform = new AffineTransform();
	Sprite curSprite = this;
	returnTransform.preConcatenate(curSprite.getNecessaryTransform());
        while (curSprite != null) {
            returnTransform.preConcatenate(curSprite.getLocalTransform());
            curSprite = curSprite.getParent();
        }
        return returnTransform;
    }

    /**
     * Returns our local (non-scaling) transform
     */
    public AffineTransform getLocalTransform() {
	return (AffineTransform)nonScaleTransform.clone();
    }
    /*
     *	Returns our local (scaling) transform 
     */
    public AffineTransform getNecessaryTransform(){
	return (AffineTransform) scaleTransform.clone();
    }
    /**
     * Performs an arbitrary transform on this sprite
     */
    public void transform(AffineTransform t) {
	if (t.equals(AffineTransform.getRotateInstance(Math.toRadians(-90),0,5)))
		footMultiplier = -1;	//handles the awkward nature of the right foot
	nonScaleTransform.concatenate(t);
	initialTransform.concatenate(t);	//saved in case of reset
    }

    

    /**
     * Draws the sprite. This method will call drawSprite after
     * the transform has been set up for this sprite.
     */
    public void draw(Graphics2D g) {
        AffineTransform oldTransform = g.getTransform();

	AffineTransform fullTransform = this.getFullTransform();
	//update fulltransform to take the initial menu into account
	fullTransform.preConcatenate(oldTransform);
        // Set to our transform
        g.setTransform(fullTransform);
        
        // Draw the sprite (delegated to sub-classes)
        this.drawSprite(g);
        
        // Restore original transform
        g.setTransform(oldTransform);

        // Draw children
        for (Sprite sprite : children) {
            sprite.draw(g);
        }
    }

    /**
     * Recursively resets the sprite and its children to their 
	initial positions and states
     */	
    public void resetSelf(){
	currentRotation = 0;
	xScale = 1;
	xOffset = 0;
	oldLength = initLength;
	newLength = oldLength;
	nonScaleTransform = (AffineTransform) initialTransform.clone();
	scaleTransform = new AffineTransform();
	for (Sprite sprite : children) {
            sprite.resetSelf();
        }
    }
   
    /**
     * The method that actually does the sprite drawing. This method
     * is called after the transform has been set up in the draw() method.
     * Sub-classes should override this method to perform the drawing.
     */
    protected abstract void drawSprite(Graphics2D g);
}
