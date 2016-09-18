package demo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.sikuli.script.Sikulix;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import com.itextpdf.text.pdf.PdfWriter;
 

public class Gui {
	Utils u=new Utils();
	JDialog WaitingDialog;
	JLabel label;
	CheckBoxTreeNode rootNode = new CheckBoxTreeNode("root"); 
	JFrame frame = new JFrame("Himalaya_Test"); 
    JTree tree = new JTree();
    Runnable updateGUI=null;
    ArrayList<LinkedList<String>> case_selected = new ArrayList<LinkedList<String>>();
    
    final  private HashMap<LinkedList<String>,String> case_list = new HashMap<LinkedList<String>,String>(){
    	{
    		put(new LinkedList<String> () {{ add("SmokeTest"); add("Abort") ;}} ,"smoketest.abort");
    		put(new LinkedList<String> () {{ add("SmokeTest"); add("Fast Program") ;}},"case2");
    		put(new LinkedList<String> () {{ add("Power_Failure"); add("Scenario 002 003 004") ;}},"case3");
    		put(new LinkedList<String> () {{ add("Power_Failure"); add("Scenario 2x2 2x6 success") ;}},"case4");
    		put(new LinkedList<String> () {{ add("Safe_Reagent"); add("Paraffin Safe Reagent Filling with Suck Empty");}},"case5");
    		put(new LinkedList<String> () {{ add("Safe_Reagent"); add("Paraffin Safe Reagent Filling with Overflow");}},"case6");
    		put(new LinkedList<String> () {{ add("Startup"); add("Test Screen Saver");}},"case7");
    		put(new LinkedList<String> () {{ add("Startup"); add("Software Update reboot MSW will follow the reheat strategy");}},"case8");
    		put(new LinkedList<String> () {{ add("Reagents"); add("Processing Reagents mode can be Cassette, Cycles, Days and Off");}},"case9");
    		put(new LinkedList<String> () {{ add("Reagents"); add("Cleaning Reagents mode can be Cycles, Days and Off");}},"case10");
    		put(new LinkedList<String> () {{ add("Export-Import"); add("User Export on Error status");}},"ExportImport.Export");
    		put(new LinkedList<String> () {{ add("Export-Import"); add("Service Export on Error status");}},"case12");
    		put(new LinkedList<String> () {{ add("Startup"); add("GUI Startup");}},"case13");
    		put(new LinkedList<String> () {{ add("Startup"); add("Screen Saver");}},"case14");
    		put(new LinkedList<String> () {{ add("Application_window"); add("Status bar detail informations");}},"case15");
    		put(new LinkedList<String> () {{ add("Application_window"); add("Status bar's error&warning icon");}},"case16");
    		put(new LinkedList<String> () {{ add("CV_Export_Function"); add("Check the filename of archived packages");}},"case17");
    		put(new LinkedList<String> () {{ add("CV_Export_Function"); add("Export archived packages into destination directory");}},"case18");
    		put(new LinkedList<String> () {{ add("CV_Export_Interface"); add("Open archived file");}},"case19");
    		put(new LinkedList<String> () {{ add("SmokeTest"); add("ProgramLoop");}},"smoketest.ProgramLoop");
    		put(new LinkedList<String> () {{ add("temp"); add("just a test");}},"temp");
    		
    	}
    };
    
	
	protected void Set_tree_value(Element father,CheckBoxTreeNode node) {
		
		List<Element> childElements = father.elements();
		if(childElements.size()!=0) {
			for(Element child:childElements) {
				if(!child.isTextOnly()) {
				CheckBoxTreeNode treenode = new CheckBoxTreeNode(child.getName());
				
				node.add(treenode);
				Set_tree_value(child,treenode);
				}
				else {
					CheckBoxTreeNode treenode = new CheckBoxTreeNode(child.getText());
					node.add(treenode);
				}
			}
		}
			
		
	}
	public void Init()
	{
		frame.setLocation(400, 100);
	    frame.setSize(800, 800);
        
        try {
    	    UIManager
    	      .setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
    	    SwingUtilities.updateComponentTreeUI(tree); 
    	   } catch (Exception ex) {
    	   }
        
        
        
        SAXReader reader = new SAXReader();
        File file = new File("resource/case.xml");
        try {
			Document document = reader.read(file);
			Element root = document.getRootElement();
			Set_tree_value(root,rootNode);

			
//			Iterator it = root.elementIterator();
//			while(it.hasNext()) {
//				Element element = (Element)it.next();
//				System.out.println(element.getName());
//				System.out.println(element.elementIterator().hasNext());
//			}
			
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        
//        CheckBoxTreeNode node1 = new CheckBoxTreeNode("node_1"); 
//        CheckBoxTreeNode node1_1 = new CheckBoxTreeNode("node_1_1"); 
//        CheckBoxTreeNode node1_2 = new CheckBoxTreeNode("node_1_2"); 
//        CheckBoxTreeNode node1_3 = new CheckBoxTreeNode("node_1_3"); 
//        node1.add(node1_1); 
//        node1.add(node1_2); 
//        node1.add(node1_3); 
//        
//        CheckBoxTreeNode node2 = new CheckBoxTreeNode("node_2"); 
//        CheckBoxTreeNode node2_1 = new CheckBoxTreeNode("node_2_1"); 
//        CheckBoxTreeNode node2_2 = new CheckBoxTreeNode("node_2_2_222222"); 
//        node2.add(node2_1); 
//        node2.add(node2_2); 
//        rootNode.add(node1); 
//        rootNode.add(node2); 

        DefaultTreeModel model = new DefaultTreeModel(rootNode); 
        tree.addMouseListener(new CheckBoxTreeNodeSelectionListener()); 
        tree.putClientProperty("JTree.lineStyle","Angled");
        tree.setModel(model); 
        CheckBoxTreeCellRenderer cell = new CheckBoxTreeCellRenderer();
        
        
        
    //    cell.setBackground(Color.ORANGE);
        
//        cell.setOpenIcon(new ImageIcon("C:\\Users\\jerry cheng\\workspace\\ForTest\\img\\leaf.png"));
//        cell.setClosedIcon(closed);
        
        tree.setCellRenderer(cell); 
        
        JScrollPane scroll = new JScrollPane(tree); 
  //      scroll.setBounds(0, 0, 600, 620); 
        
        
//        layout.addLayoutComponent("1", scroll);
//        layout.addLayoutComponent("2", button);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,1));
        panel.add(scroll);
        final JTextField t1 = new JTextField();
        final JLabel l1 = new JLabel();
        l1.setText("Please input IP");
        final JTextField t2 = new JTextField();
        final JLabel l2 = new JLabel();
        l2.setText("Please input xxx");
        JTextField t3 = new JTextField();
        JLabel l3 = new JLabel();
        l2.setText("Please choose program");
        JPanel panel2 = new JPanel();
        JButton ok_button = new JButton("start");
        JButton cancel_button = new JButton("cancel");
        JButton program_icon = new JButton("program icon");
        program_icon.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e) {
        	     JFileChooser jfc = new JFileChooser();
        	     if(jfc.showOpenDialog(frame)==JFileChooser.APPROVE_OPTION ){
        	    	 t2.setText(jfc.getSelectedFile().getAbsolutePath());
        	     }
        	}  
        });
        
        class ButtonclickListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(t1.getText().trim().equals("")){
					JOptionPane.showMessageDialog(frame, "please input IP of instrument!", "Warning",JOptionPane.WARNING_MESSAGE);  
					
				}
				else{
				VisitAllNodes();
				
				exec t = new exec();
				
				
				t.TransferData(t1.getText());
				Thread thread=new Thread(t);
				thread.start();
				}
			}
        	
        }
        
        ok_button.addActionListener(new ButtonclickListener());
        
        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);
        frame.add(panel); 
        frame.add(t1);
        frame.add(l1);
        frame.add(t2);
        frame.add(program_icon);
        frame.add(panel2);
        frame.add(ok_button);
        frame.add(cancel_button);
        
        GridBagConstraints s= new GridBagConstraints();
        s.fill = GridBagConstraints.BOTH;
        s.gridwidth=0;
        s.gridheight=4;
        s.weightx = 0;
        s.weighty=1;
        layout.setConstraints(panel, s);//设置组件
        
        s.gridwidth=4;
        s.weightx = 1;
        s.weighty=0;
        layout.setConstraints(t1, s);
        
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(l1, s);
        
        s.gridwidth=4;
        s.weightx = 1;
        s.weighty=0;
        layout.setConstraints(t2, s);
        
        s.gridwidth=0;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(program_icon, s);
        
        s.gridwidth=4;
        s.weightx = 1;
        s.weighty=0;
        layout.setConstraints(panel2, s);
        
        s.gridwidth=1;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(ok_button, s);
        
        s.gridwidth=1;
        s.weightx = 0;
        s.weighty=0;
        layout.setConstraints(cancel_button, s);
        
        
         
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setVisible(true); 
       
	}
	
	public void VisitAllNodes() {
		CheckBoxTreeNode root = (CheckBoxTreeNode)tree.getModel().getRoot();
		VisitAllNodes(root);
	}
	public void VisitAllNodes(CheckBoxTreeNode node) {
		if (!node.isLeaf()){
			for(Enumeration e = node.children();e.hasMoreElements();){
			CheckBoxTreeNode n = (CheckBoxTreeNode)e.nextElement();
			VisitAllNodes(n);
			}
		}
		else {
			if (node.isSelected) {
					//System.out.println(node.toString());
				LinkedList<String> list = new LinkedList<String>();
				list.add(node.getParent().toString());
				list.add(node.toString());
				case_selected.add(list);									
			}
		}		
	}
	public void SaveResultFile(String result,LinkedList<String> cases_class){
		
		frame.toFront();
		boolean Option_result=Sikulix.popAsk("generate report?",result);
		
		if(Option_result){
			updateGUI=new Runnable(){
				@Override
				public void run(){
					WaitingDialog=new JDialog();
					label=new JLabel();
					
					label.setText("    generate test report...");
					
					WaitingDialog.setSize(200, 200);
					WaitingDialog.getContentPane().add(label);
					WaitingDialog.setModal(true);
					WaitingDialog.setUndecorated(true);
					WaitingDialog.setLocation(frame.getX()+frame.getWidth()/2-WaitingDialog.getWidth()/2, frame.getY()+frame.getHeight()/2-WaitingDialog.getHeight()/2);
					WaitingDialog.setVisible(true);
				}
			};
			SwingUtilities.invokeLater(updateGUI);
			
			
			com.itextpdf.text.Document document=new com.itextpdf.text.Document(PageSize.A4);
			try{
				File file=new File(System.getProperty("user.dir")+"/result.pdf");
				if(file.exists()){
					file.delete();
				}
				FileOutputStream pdfFile;
				
				pdfFile = new FileOutputStream(file);
			
//		        Paragraph paragraph = new Paragraph("My first PDF file with an image ...");  
//		        Image image = Image.getInstance("F:/study/test/洛克 李.jpg");  
    
				PdfWriter writer=PdfWriter.getInstance(document, pdfFile);
				writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
				document.open();
				
				document.newPage();  
	    	    Font font1 = new Font(Font.FontFamily.HELVETICA  , 14, Font.BOLD);
	    	    Font font2 = new Font(Font.FontFamily.HELVETICA  , 12, Font.BOLD);
	    	    Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
	    	    Paragraph title = new Paragraph("Himalaya",font1);  
	    	    Chapter chapter = new Chapter(title,1);
	    	    chapter.setNumberDepth(0);
	    	    chapter.setBookmarkTitle("Himalaya");
	    	    chapter.setBookmarkOpen(false);
	    	    Section section;
				for(String tablename:cases_class){
					section = chapter.addSection(new Paragraph(tablename,font2),0);    
		    	    section.setIndentationLeft(20);
		    	    //tablename should add `` because of tablename contain .
					tablename="`"+tablename+"`";
					LinkedHashMap<String,String> resultfromdatabase =u.GetTableData(tablename);
					
		    	    
		    	    for(Iterator resultkey=resultfromdatabase.keySet().iterator();resultkey.hasNext();){
		    	    	Object key=resultkey.next();
		    	    	chapter.add(new Paragraph(key+":"+resultfromdatabase.get(key),font3));
		    	    }
		    	    chapter.add(Chunk.NEWLINE);
		    	    chapter.add(Chunk.NEWLINE);
		    	    chapter.add(Chunk.NEWLINE);
//		    	    u.DeleteTable(tablename);
				}
				
		      
		          
		        document.add(chapter); 
		        System.out.println("pdf generate successfully...");
//		        pdfDoc.add(image);  
		      
		         
		        }catch(FileNotFoundException | com.itextpdf.text.DocumentException e){
		        	e.printStackTrace();
		        	
		        }finally{
		        	document.close();
		        }
			
		
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			updateGUI=new Runnable(){
				@Override
				public void run(){
					WaitingDialog.setVisible(false);
					WaitingDialog.dispose();
				}
			};
			SwingUtilities.invokeLater(updateGUI);
			
			
		}
		
	}
	
	class exec implements Runnable{
		private String IP;
		private volatile boolean flag=true;
		
		public void TransferData(String IP){
			this.IP=IP;
		}
		@Override
		public void run(){
			try {
				u.StartTightVNC("/home/jerry/backup/tvnjviewer-2.7.2-bin/tightvnc-jviewer.jar", IP);
			} catch (IOException | InterruptedException | MyException e) {
				// TODO Auto-generated catch block
				flag=false;
			}
			if(flag)
			{
			u.InitDatabase();
			// TODO Auto-generated method stub
//			System.out.println(case_selected.size());
			ArrayList<LinkedList<String>> HaveBeenTested=new ArrayList<>();
			LinkedList<String> cases_class_in_runnable=new LinkedList<String>();

			for(LinkedList<String> key:case_selected) {
				String case_class_string = case_list.get(key);
//				String className = "demo."+case_class_string;
//				System.out.println(case_class_string);
				
				cases_class_in_runnable.add(case_class_string);		
				
				try {
					
					Object xyz = Class.forName(case_class_string).newInstance();
					String methodName = "test";
					
					Method getNameMethod = xyz.getClass().getMethod(methodName,String.class);
					getNameMethod.invoke(xyz,IP);
					HaveBeenTested.add(key);
					
				} catch (Exception e1) {
				// TODO Auto-generated catch block
					SaveResultFile("Test Failed",cases_class_in_runnable);
					e1.printStackTrace();
					break;
				}
			
			}
			if(HaveBeenTested.size()==case_selected.size())	SaveResultFile("Test Complete",cases_class_in_runnable);
		}
			
		}
	}
	
}
