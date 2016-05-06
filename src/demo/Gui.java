package demo;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class Gui {
	CheckBoxTreeNode rootNode = new CheckBoxTreeNode("root"); 
	JFrame frame = new JFrame("CheckBoxTreeDemo"); 
	
    JTree tree = new JTree();
    int i = 0;
    ArrayList<LinkedList<String>> case_selected = new ArrayList<LinkedList<String>>();
    
    final  private HashMap<LinkedList<String>,String> case_list = new HashMap<LinkedList<String>,String>(){
    	{
    		put(new LinkedList<String> () {{ add("SmokeTest"); add("Abort") ;}} ,"abort");
    		put(new LinkedList<String> () {{ add("SmokeTest"); add("Fast Program") ;}},"case2");
    		put(new LinkedList<String> () {{ add("Power_Failure"); add("Scenario 002 003 004") ;}},"case3");
    		put(new LinkedList<String> () {{ add("Power_Failure"); add("Scenario 2x2 2x6 success") ;}},"case4");
    		put(new LinkedList<String> () {{ add("Safe_Reagent"); add("Paraffin Safe Reagent Filling with Suck Empty");}},"case5");
    		put(new LinkedList<String> () {{ add("Safe_Reagent"); add("Paraffin Safe Reagent Filling with Overflow");}},"case6");
    		put(new LinkedList<String> () {{ add("Startup"); add("Test Screen Saver");}},"case7");
    		put(new LinkedList<String> () {{ add("Startup"); add("Software Update reboot MSW will follow the reheat strategy");}},"case8");
    		put(new LinkedList<String> () {{ add("Reagents"); add("Processing Reagents mode can be Cassette, Cycles, Days and Off");}},"case9");
    		put(new LinkedList<String> () {{ add("Reagents"); add("Cleaning Reagents mode can be Cycles, Days and Off");}},"case10");
    		put(new LinkedList<String> () {{ add("Export-Import"); add("User Export on Error status");}},"Export");
    		put(new LinkedList<String> () {{ add("Export-Import"); add("Service Export on Error status");}},"case12");
    		put(new LinkedList<String> () {{ add("Startup"); add("GUI Startup");}},"case13");
    		put(new LinkedList<String> () {{ add("Startup"); add("Screen Saver");}},"case14");
    		put(new LinkedList<String> () {{ add("Application_window"); add("Status bar detail informations");}},"case15");
    		put(new LinkedList<String> () {{ add("Application_window"); add("Status bar's error&warning icon");}},"case16");
    		put(new LinkedList<String> () {{ add("CV_Export_Function"); add("Check the filename of archived packages");}},"case17");
    		put(new LinkedList<String> () {{ add("CV_Export_Function"); add("Export archived packages into destination directory");}},"case18");
    		put(new LinkedList<String> () {{ add("CV_Export_Interface"); add("Open archived file");}},"case19");
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
        JTextField t1 = new JTextField();
        JLabel l1 = new JLabel();
        l1.setText("Please input IP");
        JTextField t2 = new JTextField();
        JLabel l2 = new JLabel();
        l2.setText("Please input xxx");
        JPanel panel2 = new JPanel();
        JButton ok_button = new JButton("start");
        JButton cancel_button = new JButton("cancel");
        
        class ButtonclickListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				VisitAllNodes();
				Thread t = new Thread(new exec());
				t.start();
			}
        	
        }
        
        ok_button.addActionListener(new ButtonclickListener());
        
        GridBagLayout layout = new GridBagLayout();
        frame.setLayout(layout);
        frame.add(panel); 
        frame.add(t1);
        frame.add(l1);
        frame.add(t2);
        frame.add(l2);
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
        layout.setConstraints(l2, s);
        
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
	
	class exec implements Runnable{

		@Override
		public void run(){
			// TODO Auto-generated method stub
			for(LinkedList<String> key:case_selected) {
				String className = "demo."+case_list.get(key);
			
				try {
					
					Object xyz = Class.forName(className).newInstance();
					String methodName = "test";
					Method getNameMethod = xyz.getClass().getMethod(methodName);
					int result = (int)getNameMethod.invoke(xyz);
				} catch (Exception e1) {
				// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
		}
	}
}
