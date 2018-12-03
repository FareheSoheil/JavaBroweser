 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JBrowser;

/**
 *
 * @author Farehe
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.SwingWorker;


//import org.jsoup.nodes.Document;
import javax.swing.text.Document;
//import java.Jsoup.*;

public class JBrowserFrame extends JFrame {
    String homepage,adress;
    ArrayList< ArrayList<URL>> adresses;
    ArrayList< ArrayList<URL>> historyadresses;
    ArrayList<Integer> cursor ;
    ArrayList<JEditorPane> editorpanes;
    JProgressBar pbr;
    JMenu file;
    JMenu edit;
    JMenu help;
    JMenuItem []menuItem;
    JButton[] buttons ;// toolbar buttons
    Icon toolbarIcons[];
    JTabbedPane jtabbedpane;
    //JEditorPane[] ;
    private JTextField searchadress;
    private final JMenuBar menubar;
    private final JPanel westtoolbarpanel,southtoolbarpanel,middletoolbarpanel,buttonpanel ;
    private final JPanel adresspanel ;
    private static int counter = 0,tabcounter=1,backcounter =0,movecounter=0;
    private Document document;


   public JBrowserFrame() {
    
    super("JBrowser");
    setLayout(new BorderLayout());
    pbr = new JProgressBar();
    pbr.setValue(0);
    pbr.setStringPainted(true);
    pbr.setMaximum(100);
    pbr.setMinimum(0);
    cursor= new ArrayList<Integer>();
    adresses = new ArrayList<ArrayList<URL>>();
    historyadresses = new ArrayList<ArrayList<URL>>();
    editorpanes = new ArrayList<JEditorPane>();
    homepage = new String("http://www.aut.ac.ir");
    adress =  new String();
    jtabbedpane=new JTabbedPane();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    
    /**
     * _____________________generating Menu bar ___________________________________________--
     */
    menubar = new JMenuBar();
    menuItem = new JMenuItem[10];
    file = new JMenu("File");
    edit = new JMenu("Edit");
    help = new JMenu("Help");
    
   //adding menus to the frame 
    menubar.add(file);
    menubar.add(edit);
    menubar.add(help);
    
    /**
     * FILE MENU : new tab, close tab , print , show history , exit
     */
    
    // adding "new tab" submenu to file
    menuItem[0] = new JMenuItem("New Tab");
    menuItem[0].setMnemonic(KeyEvent.VK_N);
    menuItem[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
    file.add(menuItem[0]);
    
   // adding "close tab" submenu to file 
    menuItem[1] = new JMenuItem("Close Tab");
    menuItem[1].setMnemonic(KeyEvent.VK_C);
    menuItem[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,ActionEvent.CTRL_MASK));
    file.add(menuItem[1]);
    file.addSeparator();//adding seperator to submenus
    
    // adding "print" submenu to file
    menuItem[2] = new JMenuItem("Print",new ImageIcon(getClass().getResource("Printer and Fax.png")));
    menuItem[2].setMnemonic(KeyEvent.VK_P);
    menuItem[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
    file.add(menuItem[2]);
    file.addSeparator();//adding seperator to submenus
    
    // adding "Show History" submenu to file
    menuItem[3] = new JMenuItem("Show History",new ImageIcon(getClass().getResource("history.png")));
    menuItem[3].setMnemonic(KeyEvent.VK_S);
  //  menuItem.getMn
    menuItem[3].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
    file.add(menuItem[3]);
    file.addSeparator();//adding seperator to submenus
    
    //adding "Exit" submenu to file
    menuItem[4] = new JMenuItem("Exit",new ImageIcon(getClass().getResource("exit.png")))
            ;
    menuItem[4].setMnemonic(KeyEvent.VK_E);
    menuItem[4].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
    file.add(menuItem[4]);
    
   // new JMenuItem
    
    /**
     * ______________________________-EDIT MENU : -____________________________________________
     */
    
//    copy

      Action copyAction = new DefaultEditorKit.CopyAction();
      copyAction.putValue(Action.NAME, "Copy");


      Action pasteAction = new DefaultEditorKit.PasteAction();
      pasteAction.putValue(Action.NAME, "Paste");

      //adding seperator to submenus
     menuItem[5] = new JMenuItem(copyAction);
     menuItem[5].setMnemonic('c');      
     menuItem[5].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.ALT_MASK));
    edit.add(menuItem[5]);
    
   //paste
    menuItem[6] = new JMenuItem(pasteAction);
    menuItem[6].setMnemonic('p');                
    menuItem[6].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.ALT_MASK));
    edit.add(menuItem[6]);
     edit.addSeparator();
   
    
    // adding "print" submenu to file
    menuItem[7] = new JMenuItem("Edit Home",new ImageIcon("Printer and Fax.png"));
    menuItem[7].setMnemonic(KeyEvent.VK_H);
    menuItem[7].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.ALT_MASK));
    edit.add(menuItem[7]);

    /**
     * ________________________ HELP MENU : _____________________________________________
     */
    // adding "About" submenu to Help
    menuItem[8] = new JMenuItem("About",KeyEvent.VK_B);   
    menuItem[8].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,ActionEvent.CTRL_MASK));
    help.add(menuItem[8]);
    help.addSeparator();//adding seperator to submenus
    
    // adding "JavaDoc" submenu to Help
    menuItem[9] = new JMenuItem("JavaDoc",KeyEvent.VK_D);
    menuItem[9].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,ActionEvent.CTRL_MASK));
    help.add(menuItem[9]);
    
    
    setJMenuBar(menubar);// adding menu bar to the page
  
  /**
   * __________________________generating tool bar buttons______________________________
   */
     buttons = new JButton[6] ;
     toolbarIcons = new ImageIcon[6];
     buttonpanel=new JPanel();
     southtoolbarpanel = new JPanel();
     westtoolbarpanel = new JPanel();
     middletoolbarpanel = new JPanel();
     adresspanel = new JPanel();
     searchadress=new JTextField(40);
    
     westtoolbarpanel.setLayout(new FlowLayout(0));
     southtoolbarpanel.setLayout(new FlowLayout(0));
    
    toolbarIcons[0]= new ImageIcon(getClass().getResource("back.png"));
    buttons[0]= new JButton(" Back",toolbarIcons[0]);
    buttons[0].setToolTipText("Go back on page");
    
    toolbarIcons[1]= new ImageIcon(getClass().getResource("forward2.jpg"));
    buttons[1]= new JButton(" Next",toolbarIcons[1]);
    buttons[1].setToolTipText("Go forward on page");
    
    toolbarIcons[2]= new ImageIcon(getClass().getResource("refresh.jpg"));
    buttons[2]= new JButton(" Refresh",toolbarIcons[2]);//toolbarIcons[2]);
    buttons[2].setToolTipText("Reload current page");
   
    
    toolbarIcons[3]= new ImageIcon(getClass().getResource("stop.jpg"));
    buttons[3]= new JButton(" Stop",toolbarIcons[3]);
    buttons[3].setToolTipText("Stop loading current page ");
    
    toolbarIcons[4]= new ImageIcon(getClass().getResource("home.png"));
    buttons[4]= new JButton(" Home",toolbarIcons[4]);
    buttons[4].setToolTipText("Go to the homepage");
    
    toolbarIcons[5]= new ImageIcon(getClass().getResource("go.jpg"));
    buttons[5]= new JButton(" Go",toolbarIcons[5]);
    buttons[5].setToolTipText("Go to the search adress");
    getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0),"clickButton");
    getRootPane().getActionMap().put("clickButton",new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent ae) {
            buttons[5].doClick();
        }
    });
     
    
   for (int i=0;i<5;i++){
        westtoolbarpanel.add(buttons[i]);            
        westtoolbarpanel.add(searchadress);
        westtoolbarpanel.add(buttons[5]);}
    
    buttonpanel.setLayout(new BorderLayout());
    buttonpanel.add(BorderLayout.NORTH,westtoolbarpanel);
//    buttonpanel.add(BorderLayout.SOUTH,southtoolbarpanel);
    add(BorderLayout.NORTH,buttonpanel);
  
  /**
   * Generating Tabs
   */
 
    TabMaker();
    //_____________________________registering the buttons__________________________________________________

       MenuItemHandlr menuhandler = new MenuItemHandlr();
       ButtonHandler buttonhandler = new ButtonHandler();
        
        for(int i=0;i<6;i++){
            buttons[i].addActionListener(buttonhandler);
       }
        for(int i=0;i<10;i++)
            menuItem[i].addActionListener(menuhandler);
           
      
   }
   //_______________________________ BUTTON HANDLER_____________________________________________
private class ButtonHandler implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent event) {

//___________________________________ back ________________________________________________
        
          if(event.getSource()== buttons[0]){         
               int tabnum,urlcounter=0,i=0;
               URL url;
               tabnum=jtabbedpane.getSelectedIndex();
               if (cursor.get(tabnum) < 0)
                   cursor.set(tabnum,0);
               
               else{
               url =  editorpanes.get(tabnum).getPage();
               System.out.println("tab num"+tabnum);
               
               if(backcounter < 1){
                   try {
                       editorpanes.get(tabnum).setPage(editorpanes.get(tabnum).getPage());
                   } catch (IOException ex) {
                       Logger.getLogger(JBrowserFrame.class.getName()).log(Level.SEVERE, null, ex);}
                   
                       if((editorpanes.get(tabnum).getPage())!=null)
                         adresses.get(tabnum).add(editorpanes.get(tabnum).getPage());
                          backcounter++;}
                          cursor.set(tabnum, cursor.get(tabnum)-1);
               

               if(cursor.get(tabnum)>=0){
                    try {
                        editorpanes.get(tabnum).setPage(adresses.get(tabnum).get(cursor.get(tabnum)));
                     
                    movecounter=1;}
                  catch (IOException ex) {
                   Logger.getLogger(JBrowserFrame.class.getName()).log(Level.SEVERE, null, ex);}
                    if((editorpanes.get(tabnum).getPage())!=null)
                     historyadresses.get(tabnum).add(editorpanes.get(tabnum).getPage());
                   System.out.println("cursur"+cursor.get(tabnum));
                                   
               }}}
          
//__________________________________________forward_____________________________________
          
           if(event.getSource()== buttons[1]){             
               int tabnum,urlcounter=0,i=0;
               URL url;
               tabnum=jtabbedpane.getSelectedIndex();
               if (cursor.get(tabnum) < 0)
                   cursor.set(tabnum,0);                       
               
               url =  editorpanes.get(tabnum).getPage();
               
               if(cursor.get(tabnum)<adresses.get(tabnum).size()-1){
                  
                    try {
                        cursor.set(tabnum, cursor.get(tabnum)+1);
                        editorpanes.get(tabnum).setPage(adresses.get(tabnum).get(cursor.get(tabnum)));
//                         cursor.set(tabnum, cursor.get(tabnum)+1);
                         movecounter=1;
               } catch (IOException ex) {
                       
                   Logger.getLogger(JBrowserFrame.class.getName()).log(Level.SEVERE, null, ex);}
               }
               if((editorpanes.get(tabnum).getPage())!=null)
                historyadresses.get(tabnum).add(editorpanes.get(tabnum).getPage()); 
               System.out.println("selected tab current url" + url);
               System.out.println("cursor"+cursor);
               }
              
  //________________________________refresh____________________________________
           
           if(event.getSource()== buttons[2]){
               int tabnum;
               String url;
               tabnum=jtabbedpane.getSelectedIndex();
              try {
                  editorpanes.get(tabnum).setPage(editorpanes.get(tabnum).getPage());
              } catch (IOException ex) {
                  Logger.getLogger(JBrowserFrame.class.getName()).log(Level.SEVERE, null, ex);
              }
               setVisible(false);
               setVisible(true);
            }
//________________________________stop_________________________________________
           
           if(event.getSource()== buttons[3]){
               System.out.println("editorpaneha"+editorpanes.size());
               System.out.println("tabadress ha"+adresses.size());
               for(int i=0;i<adresses.size();i++){
                   for(int j=0;j<adresses.get(i).size();j++){
                       System.out.println("tab"+"i" +"adress"+"j"+adresses.get(i).get(j));
                   }
               }
           }
  //______________________________________home___________________________________
           
           if(event.getSource()== buttons[4]){
               int tabnum;
               tabnum=jtabbedpane.getSelectedIndex();
              try {
                  editorpanes.get(tabnum).setPage(editorpanes.get(tabnum).getPage());
              } catch (IOException ex) {
                  Logger.getLogger(JBrowserFrame.class.getName()).log(Level.SEVERE, null, ex);
              }
               
              try {
                  editorpanes.get(tabnum).setPage(homepage);
              } catch (IOException ex) {
                  editorpanes.get(tabnum).setContentType("text/html");
                  editorpanes.get(tabnum).setText("<html>Could not load ** </html>");
                  Logger.getLogger(JBrowserFrame.class.getName()).log(Level.SEVERE, null, ex);
                  
              }
              if((editorpanes.get(tabnum).getPage())!=null)
                adresses.get(tabnum).add(editorpanes.get(tabnum).getPage());}
           
   //______________________________________go________________________________________
           if(event.getSource()== buttons[5]){
            
             int tabnum;
             String s =  searchadress.getText();
             tabnum=jtabbedpane.getSelectedIndex();
              
              try {
                  editorpanes.get(tabnum).setPage(s);
                  backcounter = 0;
                  cursor.set(tabnum, cursor.get(tabnum)+1);
                       
              } catch (IOException ex) {
                  editorpanes.get(tabnum).setContentType("text/html");
                  editorpanes.get(tabnum).setText("<html> Could not load the page </html>");
                  Logger.getLogger(JBrowserFrame.class.getName()).log(Level.SEVERE, null, ex);}
              
              if((editorpanes.get(tabnum).getPage())!=null)
                adresses.get(tabnum).add(editorpanes.get(tabnum).getPage());
                historyadresses.get(tabnum).add(editorpanes.get(tabnum).getPage());}      
    }}
//___________________________ MENU HANDLER___________________________________________________________

private class MenuItemHandlr implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent event) {
//_______________________newtab____________________________
            
            if(event.getSource()== menuItem[0])
                TabMaker();
                
//______________________closetab_____________________________
            
            if(event.getSource()== menuItem[1]){
                int tabnum = jtabbedpane.getSelectedIndex();
                editorpanes.remove(tabnum);
                System.out.println("jsize"+editorpanes.size());
                jtabbedpane.remove(tabnum);}
                
//_________________________print________________________
            if(event.getSource()== menuItem[2])
                
                editorpanes.get(jtabbedpane.getSelectedIndex()).printAll(getGraphics());
                
//________________________history________________________
            
           if(event.getSource()== menuItem[3]){
               JTextArea history = new JTextArea();
               int tabnum = jtabbedpane.getSelectedIndex();
                 String address = new String();
            JScrollPane editorScrollPane2 = new JScrollPane(history);
            editorScrollPane2.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            editorScrollPane2.setHorizontalScrollBarPolicy(
           JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
           editorScrollPane2.setPreferredSize(new Dimension(250, 145));
           editorScrollPane2.setMinimumSize(new Dimension(5, 5));
                 if(movecounter == 0)
                      adresses.get(tabnum).add(editorpanes.get(tabnum).getPage());
                 for(int i=0 ; i<historyadresses.size() ;i++){
                     for(int j=0 ; j<historyadresses.get(i).size() ; j++)
                         address =address+"\n"+historyadresses.get(i).get(j);}
                 history.setText(address);
                 jtabbedpane.addTab("HISTORY",editorScrollPane2);
                     
                 System.out.println("HISTORY :"+address);
                 

                
            }
//________________________exit______________________
           
              if(event.getSource()== menuItem[4]){
                  setVisible(false);}
                
//________________________edit home______________________
               if(event.getSource()== menuItem[7]){
                   JFrame home = new JFrame("Edit home");
                   JTextField address = new JTextField(30);
                   JButton ok = new JButton("OK");
                   home.setLayout(new FlowLayout());
                   home.add(address);
                   home.add(ok);
                   home.pack();
                   home.setVisible(true);
                  ok.addActionListener( new ActionListener(){          
                       @Override
                       public void actionPerformed(ActionEvent ae) {
                        homepage = address.getText();
                        System.out.println(""+homepage);
                        home.setVisible(false);}
                       });}   

//________________________ABOUT_____________________________________________
               
               if(event.getSource()== menuItem[8]){
                   JTextArea about = new JTextArea();
                   about.setText("Programmer : Farehe Soheil \n  Email :farehe.soheil@yahoo.com");
                   about.setCaretPosition(5);
                   jtabbedpane.addTab("ABOUT", about);}
                
//______________________JAVADOC_______________________________________________
              if(event.getSource()== menuItem[9]){
                  JEditorPane javadoc = new JEditorPane();
                try {
//                    
                    javadoc.setPage(getClass().getResource("index.html"));
                } catch (IOException ex) {
                    Logger.getLogger(JBrowserFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                   jtabbedpane.addTab("JAVADOC", javadoc);
                  
                
            }
           
                 }
    
}

 //____________________________METHODES_________________________________________________-   
    public void hyperlinkUpdate(HyperlinkEvent he) {
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(he.getEventType() == HyperlinkEvent.EventType.ACTIVATED){
            document = editorpanes.get(jtabbedpane.getSelectedIndex()).getDocument();
        }
        }




      public void TabMaker(){
         JProgressBar pbar = new JProgressBar(0,100);
         ArrayList<URL> tabaddress=new ArrayList<URL>();
         ArrayList<URL> historyaddress=new ArrayList<URL>();
         adresses.add(tabaddress);
         historyadresses.add(historyaddress);
         cursor.add(0);

         JEditorPane editorpane = new JEditorPane();
         editorpane.setEditable(false);
        try {
            editorpane.setPage(homepage);
        } catch (IOException ex) {
            Logger.getLogger(JBrowserFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
         
            
            editorpanes.add(editorpane);
            JScrollPane editorScrollPane = new JScrollPane(editorpane);
            editorScrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            editorScrollPane.setHorizontalScrollBarPolicy(
           JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
           editorScrollPane.setPreferredSize(new Dimension(250, 145));
           editorScrollPane.setMinimumSize(new Dimension(5, 5));
//           jtabbedpane.add(pbr);
     for (int i = 0; i <= 100; i++) {
        final int percent = i;
        try {
        SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            pbar.setValue(percent);
          }
        });
        java.lang.Thread.sleep(10);
      } catch (InterruptedException e) {
            System.out.println("555");
      }
    }
  
       
           jtabbedpane.addTab("Tab"+Integer.toString(tabcounter),new ImageIcon(getClass().getResource("newtab.png")), editorScrollPane);
           add(BorderLayout.CENTER,jtabbedpane);
           
    tabcounter++;
          
      
          System.out.println("jeditorpanes[0]"+editorpanes.get(0).getPage());
      }
   
//    public void ProgressBar() {
        
}
