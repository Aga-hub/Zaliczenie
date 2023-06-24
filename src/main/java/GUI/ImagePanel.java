package GUI;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import javax.swing.*;
import org.dcm4che2.data.*;
import org.dcm4che2.io.*;
import org.dcm4che2.util.*;

public class ImagePanel extends JPanel {
    public static final Color   BACKGROUND_COLOR=Color.WHITE;
    protected int   mWidth=600;
    protected int   mHeight=500;
    protected BufferedImage mImage=null;

    public ImagePanel(int width,int height) { //konstruktor
        this.mWidth=width;
        this.mHeight=height;
        this.setImage(null);
    }
    public void setImage(BufferedImage image){ //metoda dodająca obrazek do panelu
        int width=0;
        int height=0;
        if ((this.mImage=image)!=null) {
            this.mWidth=this.mImage.getWidth();
            this.mHeight=this.mImage.getHeight();
        }
        width=this.mWidth;
        height=this.mHeight;
        this.setPreferredSize(new Dimension(width,height));
        this.setSize(this.getPreferredSize());
    }
    public void paintComponent(Graphics g) { //metoda rysująca obrazek na panelu
        Graphics2D  g2d=null;
        double      xScale=1.0,yScale=1.0;
        g2d=(Graphics2D)g;
        this.setBackground(ImagePanel.BACKGROUND_COLOR);
        super.paintComponent(g2d);
        if (this.mImage!=null) {
            xScale=(double)(this.getWidth())/(double)this.mImage.getWidth();
            yScale=(double)this.getHeight()/(double)this.mImage.getHeight();
            if (xScale/yScale>((double)this.mImage.getWidth()/(double)this.mImage.getWidth()))
                xScale=yScale;
            else
                yScale=xScale;
            g2d.drawImage(this.mImage,new AffineTransformOp(AffineTransform.getScaleInstance(xScale,yScale),AffineTransformOp.TYPE_BILINEAR),0,0);
        }
    }
}
