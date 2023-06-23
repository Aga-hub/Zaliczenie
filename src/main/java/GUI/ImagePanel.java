package GUI;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;
public class ImagePanel extends JPanel {
    public static final Color   BACKGROUND_COLOR=Color.BLACK;

    protected int   mWidth=300;
    protected int   mHeight=300;

    protected BufferedImage mImage=null;


    public ImagePanel(int width,int height) {
        this.mWidth=width;
        this.mHeight=height;
        this.setImage(null);
    }

    public void setImage(BufferedImage image){
        Insets  insets=null;
        int     width=-1,height=-1;

        if ((this.mImage=image)!=null) {
            this.mWidth=this.mImage.getWidth();
            this.mHeight=this.mImage.getHeight();
        }
        insets=this.getInsets();
        width=this.mWidth+insets.left+insets.right;
        height=this.mHeight+insets.top+insets.bottom;
        this.setPreferredSize(new Dimension(width,height));
        this.setSize(this.getPreferredSize());
    }
    public void paintComponent(Graphics g) {
        Graphics2D  g2d=null;
        double      xScale=1.0,yScale=1.0;
        Insets		insets=null;

        g2d=(Graphics2D)g;

        this.setBackground(ImagePanel.BACKGROUND_COLOR);
        super.paintComponent(g2d);

        if (this.mImage!=null) {
            insets=this.getInsets();
            xScale=(double)(this.getWidth()-(insets.left+insets.right))/(double)this.mImage.getWidth();
            yScale=(double)(this.getHeight()-(insets.top+insets.bottom))/(double)this.mImage.getHeight();

            if (xScale/yScale>((double)this.mImage.getWidth()/(double)this.mImage.getWidth()))
                xScale=yScale;
            else
                yScale=xScale;
            g2d.drawImage(this.mImage,new AffineTransformOp(AffineTransform.getScaleInstance(xScale,yScale),AffineTransformOp.TYPE_BILINEAR),0,0);
        }
    }
}

