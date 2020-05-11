import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.io.File;

public class droste extends PApplet {





PImage imgb;
PImage loaded1;
String theNameThatHasBeenEntered = "";

PImage imgMask;
PImage img[];
PImage loaded[];
PImage blur[];
int bNum = 3;
int r;
float s;
float strength = 1.0f;
int nPics = 20;
int j;
int mPosY = width/2  ;
int mPosX = height/2;
float aPos = 1.0f;
float bPos = 1.0f;
PImage imgMask2;
PVector  v1;
boolean help = true;
String  info= 
  "Interact with keys and mouse:\n b = cycle blur \n left and right = cycle size \n up and down = rotate \n 8/2 + 4/6 = crop size \n '+'/'-'= cycle Strength    \n s = save \n  u = upload image \n r = resize to frame \n H = Toggle This text ";



public void setup() {
  

  if (theNameThatHasBeenEntered == "") {


    surface.setResizable(true);
    surface.setTitle(info);
    surface.setLocation(0, 0);

    loaded1= loadImage("TAME.jpg");
    imgb  = loaded1;
    imgb.resize(width, height);

    img = new PImage[nPics];
    loaded = new PImage[nPics];
    blur = new PImage[5];

    blur[0] = loadImage("mask.jpg");
    blur[1] = loadImage("mask1.jpg");
    blur[2] = loadImage("mask2.jpg");
    blur[3] = loadImage("mask3.jpg");
    blur[4] = loadImage("mask4.jpg");


    imgMask = blur[bNum];
    imgMask = imgMask.get(PApplet.parseInt((imgMask.width-(imgMask.width*bPos))/2), PApplet.parseInt((imgMask.height -(imgMask.height*aPos))/2), PApplet.parseInt(imgMask.width*bPos), PApplet.parseInt(imgMask.height*aPos));
    imgMask.resize(width, height );

    for ( j = 2; j < nPics; j++) {

      img[j] =  loadImage("TAME.jpg");
      int rwidth = PApplet.parseInt(abs((width/j*(2.5f + s))+ j* strength));
      int rheight = PApplet.parseInt(abs((height/j*(2.5f + s))+j* strength));
      img[j].resize(rwidth, rheight  );
      imgMask.resize(rwidth, rheight );
      img[j].mask(imgMask);
    }
  } else {
    loaded1= loadImage(theNameThatHasBeenEntered);
    imgb  = loaded1;
    imgb.resize(width, height);

    for ( j = 2; j < nPics; j++) {



      img[j] =  loadImage(theNameThatHasBeenEntered);
      int rwidth = PApplet.parseInt(abs((width/j*(2.5f + s))+ j* strength));
      int rheight = PApplet.parseInt(abs((height/j*(2.5f + s))+j* strength));
      img[j].resize(rwidth, rheight  );
      imgMask.resize(rwidth, rheight );
      img[j].mask(imgMask);
    }
  }
  imageMode(CENTER);
  textAlign(CENTER);
}
public void keyPressed() {
  if (key == 'b' ) {
    if (bNum <4) {
      bNum ++;
      setup();
    } else {
      bNum = 0;
      setup();
    }
  }
  if (key == 'u' ) {
    selectInput("Select a file to process:", "fileSelected");
  }
  if (key == 'h' ) {
    help = !help;
  }
  if (key == '+' ) {
    strength = strength - 1;
    setup();
  }
  if (key == '8' ) {
    aPos = aPos + .1f;
    setup();
  }
  if (key == '2' ) {
    aPos = aPos - .1f;
    setup();
  }
  if (key == '4' ) {
    bPos = bPos + .1f;
    setup();
  }
  if (key == '6' ) {
    bPos = bPos - .1f;
    setup();
  }
  if (key == '-' ) {
    strength = strength + 1;
    setup();
  }
  if (key == 'r' ) {
    setup();
  }
  if (key == 's' ) {
    saveFrame();
  }
  if (keyCode == UP) {
    r = r + 5;
  } else if (keyCode == DOWN) {
    r = r - 5;
  } 

  if (keyCode == LEFT) {
    s = s + 0.3f;
    setup();
  } else if (keyCode == RIGHT) {

    s = s - 0.3f;
    setup();
  }
}

public void fileSelected(File selection) {
  if (selection == null) {
    println("Window was closed or the user hit cancel.");
  } else {
    println("User selected " + selection.getAbsolutePath());
    theNameThatHasBeenEntered  = selection.getAbsolutePath(); 

    setup();

    imageMode(CENTER);
  }
}

public void draw() {

  background(1, 102, 153);
  image(imgb, width/2, height/2);

  int placeY = height /9;
  int placeX = width /9;

  for (int i = 2; i < j; i++) {
    pushMatrix(); 
    translate( width/2 + placeX/20, height/2 + placeY/20);
    rotate(radians(r*( i * 0.5f))); 
    image(img[i], 0, 0);

    popMatrix();

    placeY = mPosY - PApplet.parseInt(height/2) + placeY ;
    placeX = mPosX - PApplet.parseInt(width/2) + placeX ;
  }

  if (help) {
    int rwidth = imgb.width;
    int  rheight =  imgb.height;
    rect(width/2 - rwidth/2, height/2 - rheight/2, rwidth, rheight);
    fill(255); 
    text(info, width/2 - rwidth/2, height/2 - rheight/2, rwidth, rheight);

    textSize(32);
    textAlign(CENTER);
    fill(0, 90);
  }
}
public void mouseDragged() {
  mPosY =mouseY;
  mPosX =mouseX;
}
public void mousePressed() {
  mPosY =mouseY;
  mPosX =mouseX;
}
  public void settings() {  size(680, 680); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "droste" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
