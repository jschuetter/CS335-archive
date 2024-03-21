import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

//Posts cited:
//Drawing images using Graphics: https://docs.oracle.com/javase/tutorial/2d/images/drawimage.html
//Cloning bufferedImage: https://forums.oracle.com/ords/apexds/post/how-to-clone-a-java-awt-bufferedimage-0865

public class ImageRotate extends JFrame {
    JLabel rotationLbl;
    JSlider rotateSldr;
    ImagePanel imgPnl;
    JFileChooser filePicker = new JFileChooser(new File("."));

    //Constructor
    ImageRotate() {
        this.setTitle("Rotate Image");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 500);

        //Toolbar
        JMenuBar toolbar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem importBtn = new JMenuItem("Import");
        JMenuItem resetBtn = new JMenuItem("Reset");
        JMenuItem quitBtn = new JMenuItem("Quit");

        importBtn.addActionListener(importFile);
        resetBtn.addActionListener(resetImg);
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(importBtn);
        fileMenu.add(resetBtn);
        fileMenu.add(quitBtn);
        toolbar.add(fileMenu);

        this.add(toolbar, BorderLayout.NORTH);

        //Image edit menu
        JPanel editBar = new JPanel();
        editBar.setLayout(new BoxLayout(editBar, BoxLayout.Y_AXIS));
        rotationLbl = new JLabel("Rotation: 0");
        rotateSldr = new JSlider();

        rotateSldr.setMaximum(360);
        rotateSldr.setMinimum(-360);
        rotateSldr.setValue(0);
        rotateSldr.addChangeListener(sldrUpdate);
        rotateSldr.setMajorTickSpacing(90);
        rotateSldr.setMinorTickSpacing(30);
        rotateSldr.setPaintTicks(true);
        rotateSldr.setPaintLabels(true);
        //rotateSldr.setSnapToTicks(true);

        rotationLbl.setAlignmentX(Component.CENTER_ALIGNMENT);

        editBar.add(rotationLbl);
        editBar.add(rotateSldr);
        this.add(editBar, BorderLayout.SOUTH);

        imgPnl = new ImagePanel();
        this.add(imgPnl);

        this.setVisible(true);
    }

    public class ImagePanel extends JPanel {
        //Define image object and property variables
        private BufferedImage orig, pnlImg; //Store both original image and rotated image
        int dispW = 400, dispH = 300; //Dimensions of image display frame

        ImagePanel() {
            dispW = this.getWidth();
            dispH = this.getHeight();
            this.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    dispW = e.getComponent().getWidth();
                    dispH = e.getComponent().getHeight();
                }
                @Override
                public void componentMoved(ComponentEvent e) {}
                @Override
                public void componentShown(ComponentEvent e) {}
                @Override
                public void componentHidden(ComponentEvent e) {}
            }); //Add listener to resize image display on window resize
        }

        public void setImg(File path) {
            try {
                orig = ImageIO.read(path);
                //pnlImg = ImageIO.read(path);
            } catch (Exception ex) {
                System.out.println("File read error");
                System.out.println(ex);
            }

            //Scale image if needed
            if (orig.getWidth() > dispW) {
                double scaleFactor = (double) dispW/(double) orig.getWidth();
                BufferedImage scaled = new BufferedImage(dispW, dispH, orig.getType());
                AffineTransform at = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                scaled =  scaleOp.filter(orig, scaled);
                orig = imgcpy(scaled);
            }
            if (orig.getHeight() > dispH) {
                int scaleFactor = dispH/orig.getHeight();
                BufferedImage scaled = new BufferedImage(dispW, dispH, orig.getType());
                AffineTransform at = AffineTransform.getScaleInstance(scaleFactor, scaleFactor);
                AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
                scaled =  scaleOp.filter(orig, scaled);
                orig = imgcpy(scaled);
            }

            //Copy original image to panel image
            pnlImg = imgcpy(orig);

            repaint();
        }

        public void resetImg() {
            pnlImg = imgcpy(orig);
            repaint();
        }

        //Rotates image deg degrees from original orientation
        public void rotateAbsolute(double deg) {
            if (orig != null) {
                double rad = Math.toRadians(deg);
                int w = orig.getWidth();
                int h = orig.getHeight();
                int ctrX = w / 2;
                int ctrY = h / 2;

                AffineTransform rotation = AffineTransform.getRotateInstance(rad, ctrX, ctrY);
                AffineTransformOp transform = new AffineTransformOp(rotation, AffineTransformOp.TYPE_BILINEAR);
                pnlImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                transform.filter(orig, pnlImg);

                repaint();
            }
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(pnlImg != null){
                //Calculate center of display frame
                int pnlCtrX = this.getWidth()/2, pnlCtrY = this.getHeight()/2;
                int imgCtrX = pnlImg.getWidth()/2, imgCtrY = pnlImg.getHeight()/2;
                int x = pnlCtrX-imgCtrX, y = pnlCtrY-imgCtrY;

                g.clearRect(x, y, pnlImg.getWidth(), pnlImg.getHeight());
                g.drawImage(pnlImg, x, y, this);
            }
        }
    }
    public static void main(String[] args) {
        new ImageRotate();
    }

    ActionListener importFile = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //Reset UI
            rotateSldr.setValue(0);
            rotationLbl.setText("Rotation: 0");

            //Open file picker
            int filePicked = filePicker.showOpenDialog(ImageRotate.this);
            if (filePicked == JFileChooser.APPROVE_OPTION) {
                File importPath = filePicker.getSelectedFile();
                imgPnl.setImg(importPath);
            }
        }
    };

    ActionListener resetImg = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            rotateSldr.setValue(0);
            rotationLbl.setText("Rotation: 0");
            imgPnl.resetImg();
        }
    };


    ChangeListener sldrUpdate = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            int angle = rotateSldr.getValue();
            rotationLbl.setText("Rotation: " + angle);
            imgPnl.rotateAbsolute(angle);
        }
    };

    public static BufferedImage imgcpy(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();
        int type = img.getType();
        BufferedImage imgOut = new BufferedImage(w, h, type);
        Graphics2D g = imgOut.createGraphics();
        g.drawRenderedImage(img, null);
        g.dispose();
        return imgOut;
    }
}
