import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;

public class SortDisplay extends JButton implements ActionListener {
  private static final int FAST = 0;
  private static final int SLOW = 1;
  private static final int STEP = 2;
  
  private static double[] array;  //array modified by the user
  private static int[] oldPointers;  //where each arrow used to point
  private static int[] pointers;  //where each arrow points
  private static double[] oldObjects;  //old arrangements of objects
  private static double[] objects;  //objects ordered by position on screen
  private static boolean clicked;
  private static int mode = SLOW;
  private static SortDisplay display;
  
  public SortDisplay() {
    display = this;
    
    array = new double[5];
    objects = new double[array.length];
    oldObjects = new double[array.length];
    pointers = new int[array.length];
    oldPointers = new int[array.length];
    for (int i = 0; i < array.length; i++) {
      pointers[i] = i;
      oldPointers[i] = i;
    }
    
    JFrame frame = new JFrame();
    frame.setTitle("Sort Display");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
    
    String[] choices = {"Run", "Step"};
    JComboBox box = new JComboBox(choices);
    box.setMaximumSize(new Dimension(200, 200));
    box.setActionCommand("mode");
    box.addActionListener(this);
    box.setAlignmentX(Component.LEFT_ALIGNMENT);
    frame.getContentPane().add(box);
    
    JButton button = new JButton("Selection Sort");
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setMnemonic(KeyEvent.VK_S);
    button.setActionCommand("selection");
    button.addActionListener(this);
    frame.getContentPane().add(button);
    
    button = new JButton("Insertion Sort");
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setMnemonic(KeyEvent.VK_I);
    button.setActionCommand("insertion");
    button.addActionListener(this);
    frame.getContentPane().add(button);
    
    button = new JButton("Mergesort");
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setMnemonic(KeyEvent.VK_M);
    button.setActionCommand("merge");
    button.addActionListener(this);
    frame.getContentPane().add(button);
    
    button = new JButton("Quicksort");
    button.setAlignmentX(Component.LEFT_ALIGNMENT);
    button.setMnemonic(KeyEvent.VK_Q);
    button.setActionCommand("quick");
    button.addActionListener(this);
    frame.getContentPane().add(button);
    
    setAlignmentX(Component.LEFT_ALIGNMENT);
    setMinimumSize(new Dimension(200, 200));
    setPreferredSize(new Dimension(400, 200));
    setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
    setActionCommand("step");
    addActionListener(this);
    frame.getContentPane().add(this);
    
    frame.pack();
    frame.setVisible(true);
  }
  
  protected void paintComponent(Graphics g) {
    Graphics2D g2 = (Graphics2D)g;
    g2.setColor(Color.WHITE);
    g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
    g2.setColor(Color.BLACK);
    int unit = getWidth() / pointers.length;
    FontMetrics metrics = g2.getFontMetrics();
    for (int i = 0; i < array.length; i++) {
      g2.setColor(Color.BLACK);
      g2.draw(new Rectangle2D.Double(i * unit, 0, unit, unit));
      if (pointers[i] == oldPointers[i])
        g2.setColor(Color.BLUE);
      else
        g2.setColor(Color.RED);
      g2.draw(new Line2D.Double(i * unit + unit / 2, unit / 2,
                                pointers[i] * unit + unit / 2, 2 * unit));
      if (objects[i] == oldObjects[i])
        g2.setColor(Color.BLUE);
      else
        g2.setColor(Color.RED);
      String text = objects[i] + "";
      g2.drawString(text, i * unit + unit / 2 - metrics.stringWidth(text) / 2,
                    2 * unit + metrics.getAscent());
    }
  }
  
  private static int indexOf(double x) {
    for (int i = 0; i < objects.length; i++)
      if (objects[i] == x) return i;
    return -1;
  }
  
  public static void update()
  {
    //print all for testing
    System.out.print("array:  ");
    for (int i = 0; i < array.length; i++)
      System.out.print(array[i] + " ");
    System.out.println();
    System.out.print("objects:  ");
    for (int i = 0; i < objects.length; i++)
      System.out.print(objects[i] + " ");
    System.out.println();
    System.out.print("oldObjects:  ");
    for (int i = 0; i < oldObjects.length; i++)
      System.out.print(oldObjects[i] + " ");
    System.out.println();
    System.out.print("pointers:  ");
    for (int i = 0; i < pointers.length; i++)
      System.out.print(pointers[i] + " ");
    System.out.println();
    System.out.print("oldPointers:  ");
    for (int i = 0; i < oldPointers.length; i++)
      System.out.print(oldPointers[i] + " ");
    System.out.println(); 
    
    //check if anything changed
    boolean changed = false;
    for (int i = 0; i < array.length; i++) {
      if (array[i] != objects[pointers[i]]) {
        changed = true;
        break;
      }
    }
    if (!changed)
      return;
    
    //save old positions
    for (int i = 0; i < array.length; i++) {
      oldPointers[i] = pointers[i];
      oldObjects[i] = objects[i];
    }
    
    //find new positions from array
    for (int i = 0; i < array.length; i++)
      pointers[i] = indexOf(array[i]);
    
    redraw();
    
    //save old positions
    for (int i = 0; i < array.length; i++) {
      oldPointers[i] = pointers[i];
      oldObjects[i] = objects[i];
    }
    
    //find ways to reduce arrow length by swapping objects.
    changed = false;
    for (int i = 0; i < array.length; i++)
      for (int j = i + 1; j < array.length; j++) {
      int iIndex = pointers[i];
      int jIndex = pointers[j];
      
      //find total length of all arrows, before and after
      int oldLength = 0;
      for (int k = 0; k < array.length; k++)
        oldLength += Math.abs(k - pointers[k]);
      int newLength = 0;
      for (int k = 0; k < array.length; k++) {
        int target = pointers[k];
        if (target == iIndex)
          target = jIndex;
        else if (target == jIndex)
          target = iIndex;
        newLength += Math.abs(k - target);
      }
      
      if (newLength < oldLength) {
        changed = true;
        
        //should change ANY pointer that points to either of these two objects
        for (int k = 0; k < array.length; k++) {
          if (pointers[k] == iIndex)
            pointers[k] = jIndex;
          else if (pointers[k] == jIndex)
            pointers[k] = iIndex;
        }
        
        double object = objects[iIndex];
        objects[iIndex] = objects[jIndex];
        objects[jIndex] = object;
      }
    }
    
    if (!changed)
      return;
    
    redraw();
  }
  
  private static void redraw()
  {
    if (mode == FAST)
      return;
    
    display.repaint();
    
    if (mode == SLOW)
      try { Thread.sleep(500); } catch(InterruptedException e) {}
    else { //(mode == STEPPING)
      clicked = false;
      while (!clicked)
        try { Thread.sleep(100); } catch(InterruptedException e) {}
    }
  }
  
  public void actionPerformed(ActionEvent e)
  {
    final String action = e.getActionCommand();
    if (action.equals("step"))
    {
      clicked = true;
      return;
    }
    if (action.equals("mode")) {
      JComboBox box = (JComboBox) e.getSource();
      String item = (String) box.getSelectedItem();
      if (item.equals("Step"))
        mode = STEP;
      else if (item.equals("Run"))
        mode = SLOW;
      clicked = true;
      return;
    }
    
    Thread thread = new Thread() {
      public void run() {
        //finish current sort
        mode = FAST;
        clicked = true;
        try {Thread.sleep(100);} catch(InterruptedException e) {}
        mode = STEP;
        
        createArray();
        
        repaint();
        
        if (action.equals("selection"))
          Sort.selectionSort(array);
        else if (action.equals("insertion"))
          Sort.insertionSort(array);
        else if (action.equals("merge"))
          Sort.mergeSort(array);
        else if (action.equals("quick"))
          Sort.quickSort(array);
        else
          throw new UnsupportedOperationException(action);
        
        update();
      }
    };
    thread.start();
  }
  
  private static void createArray()
  {
    //create array of possible values
    double[] possible = new double[20];
    for (int i = 0; i < possible.length; i++)
      possible[i] = i / 10.0;
    
    //randomly shuffle possible values
    for (int i = possible.length - 1; i >= 0; i--) {
      //choose random value from 0 to i to put in position i
      int index = (int)(Math.random() * (i + 1));
      double temp = possible[i];
      possible[i] = possible[index];
      possible[index] = temp;
    }

    //fill actual array with first several value from shuffled array
    array = new double[(int)(Math.random() * 11) + 5];
    for (int i = 0; i < array.length; i++)
      array[i] = possible[i];
   
    pointers = new int[array.length];
    oldPointers = new int[array.length];
    objects = new double[array.length];
    oldObjects = new double[array.length];
    for (int i = 0; i < array.length; i++) {
      pointers[i] = i;
      oldPointers[i] = i;
      objects[i] = array[i];
      oldObjects[i] = array[i];
    }
    redraw();
  }
  
  public static void main(String[] args) {
    run();
  }
  
  public static void run() {
    new SortDisplay();
  }
}