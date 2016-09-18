package demo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import org.sikuli.script.App;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Screen;
import org.sikuli.script.Sikulix;
import org.sikuli.basics.Settings;

import edu.unh.iol.dlc.VNCScreen;

import org.sikuli.script.Match;
import org.sikuli.script.Mouse;
import org.sikuli.script.Pattern;
import org.sikuli.script.Region;
class MyException extends Exception{
	public MyException(String msg){
		super(msg);
	}
}
public class Utils {
	private Screen s;
	private Region r=new Region(0,0,800,800);
	public Utils(){
		Settings.OcrTextSearch=true;
		Settings.OcrTextRead=true;
		s = new Screen();
	
	}
	public void StartTightVNC(String VNCPath,String IP) throws IOException, InterruptedException, MyException{
		Process process=null;
		String command="java -jar "+ VNCPath+" "+IP +" "+"-port 5900 -FullScreen=no";
		process=Runtime.getRuntime().exec(command);
		Thread.sleep(2*1000);
		
		Region region=new Region(s.w/2-260,s.h/2-150,450,250);
		Match match=region.exists("img/vnc/connect.png");
		
		match.click();
		int loop=0;
		while(true){
			loop++;
			Thread.sleep(2*1000);
			match=region.exists("img/vnc/new_connection.png");
			if(match==null) {
				region=new Region(0,0,150,150);
				match=region.exists("img/vnc/maxium.png");
				match.click();
				break;
			}
				
			match=region.exists("img/vnc/connect_error.png");
			if(match!=null) {
				Sikulix.popError("Remote Connect Failed !");	
				throw new MyException("connect failed");
			}
			if(loop>5) {
				Sikulix.popError("Remote Connect Failed !");
				throw new MyException("connect failed");
			}
		}
//		match=region.exists("img/vnc/connect_error.png");
//		
//		if(match!=null){
//			Sikulix.popError("Remote Connect Failed !");
//			System.out.println("connect failed");
//		}
		
	}
	public void LoginWithSupervisor() throws FindFailed{
		while (r.exists(new Pattern("img/supervisor_status.png").exact()) == null){
			
				
				r.click("img/user.png");
				s.wait(1.0);
				r.click("img/supervisor.png");
				s.wait(1.0);
				r.type("Histocore");
				r.click("img/OK_keyboard.png");
				r.wait("img/supervisor_status.png");
				

		}
		
	}
	public void NewAProgram(String program_icon,String program_name,String[] reagents) throws FindFailed{
		System.out.println("new a program...");
		r.click("img/programlist.png");
		s.wait(1.0);
		r.click("img/new_button.png");
		s.wait(1.0);
		Region r2=r.exists("img/program_name.png").grow(300);

		r2.click("img/new_program_name.png");
		s.wait(1.0);
		r.type(program_name);
		r.click("img/OK_keyboard.png");
		s.wait(1.0);
		r.click("img/new_icon.png");
		s.wait(1.0);
		r.click(program_icon);
		s.wait(1.0);
		r.click("img/Ok_button.png");
		s.wait(1.0);
		
		Match match=null;
		for(String reagent:reagents){
			r.click("img/NewStep.png");
			s.wait(1.0);
			switch(reagent){
			case "Formalin":
				r.exists("img/reagents/formalin.png").click();break;
			case "Processing Water":
				r.exists("img/reagents/process_water.png").click();break;
			case "Ethanol70%":
				r.exists("img/reagents/ethanol70.png").click();break;
			case "Ethanol80%":
				r.exists("img/reagents/ethanol80.png").click();break;
			case "Ethanol90%":
				r.exists("img/reagents/ethanol90.png").click();break;
			case "Ethanol95%":
				r.exists("img/reagents/ethanol95.png").click();break;
			case "Ethanol100%":
				r.exists("img/reagents/ethanol100.png").click();break;
			case "Xylene":
				r.exists("img/reagents/xylene.png").click();break;
			case "Paraffin":
				Match temp=r.exists(new Pattern("img/next_page.png").exact());
				if(temp!=null) {
					Match temp2=temp.exists("img/next_page_down.png");
					if(temp2!=null){
						temp2.click();
					}
					else System.out.println("debug2");
				}
				else System.out.println("debug3");
				
				s.wait(1.0);
				r.exists("img/reagents/paraffin.png").click();
				s.wait(2.0);
				Match p=r.exists("img/new_program_degree.png").above(200).exists(new Pattern("img/50.png").exact());

				for(int i=0;i<15;i++){
					p.dragDrop(p,new Location(p.x+p.w/2,p.y-50));
					s.wait(1.0);
				}
				break;
			default:
				break;
			}
			match=r.exists("img/new_program_min.png");
			Match m=match.above(300).exists("img/00.png");
			m.dragDrop(m,new Location(m.x+m.w/2,m.y-50));
			r.exists("img/save.png").click();
			s.wait(1.0);
		}
		r.exists("img/save.png").click();
		
	}
	public void DeletePrograms() throws FindFailed{
		System.out.println("delete programs...");
		r.click("img/programlist.png");
		s.wait(1.0);
		r.exists(new Pattern("img/program/program_number1.png").exact()).click();
		while(true){
			Match m=r.exists(new Pattern("img/program/program_number4.png").exact());
			if(m!=null){
				m.click();
				s.wait(1.0);
				r.exists("img/delete.png").click();
				s.wait(1.0);
				if(r.exists("img/confirm_delete_program.png")!=null){
					Mouse.move(100,100);
					r.exists("img/Yes_button.png").click();
					s.wait(1.0);
				}
			}
			else break;
		}
	}
	public void ApplyProgram(){
		r.exists("img/apply.png").click();
	}
	//need to add new cassettes
	//delaymin=0:ASAP
	//cassettes=0: not cassettes mode
	public String StartProgram (String program_icon,int delaymin,int cassettes) throws FindFailed, InterruptedException{
			System.out.println("Start a new program...");
		
			r.click("img/dashboard.png");
			s.wait(1.0);
			r.click(program_icon);
			s.wait(1.0);
			r.click("img/start_button.png");
			s.wait(1.0);
			Match res_ok;
			Match res_ok2;
			Match res_yes;	
			Match asap;
			Match enter_cassettes_number;
			int loop=0;
			
			while(true){
				res_ok=r.exists("img/OK_button.png");
				res_yes=r.exists("img/Yes_button.png");
				asap=r.exists("img/ASAP_button.png");
				enter_cassettes_number=r.exists("img/program/enter_cassettes_number.png");
				loop++;
				System.out.println(loop);
				if(enter_cassettes_number!=null){
					Match origin_cassettes_number=r.exists("img/program/origin_cassettes_number");
					int hundred_number=cassettes/100;
					System.out.println("100: "+hundred_number);
					int tens_number=(cassettes-100*hundred_number)/10;
					System.out.println("10: "+tens_number);
					int units_number=cassettes-100*hundred_number-tens_number*10;
					System.out.println("1: "+units_number);
					for(int i=0;i<hundred_number;i++){
						r.dragDrop(new Location(origin_cassettes_number.x+origin_cassettes_number.w/6,origin_cassettes_number.y+origin_cassettes_number.h/2)
								,new Location(origin_cassettes_number.x+origin_cassettes_number.w/6,origin_cassettes_number.y-origin_cassettes_number.h/2));
					}
					for(int i=0;i<tens_number;i++){
						r.dragDrop(new Location(origin_cassettes_number.x+origin_cassettes_number.w/2,origin_cassettes_number.y+origin_cassettes_number.h/2)
								,new Location(origin_cassettes_number.x+origin_cassettes_number.w/2,origin_cassettes_number.y-origin_cassettes_number.h/2));
					}
					for(int i=0;i<units_number;i++){
						r.dragDrop(new Location(origin_cassettes_number.x+origin_cassettes_number.w-origin_cassettes_number.w/6,origin_cassettes_number.y+origin_cassettes_number.h/2)
								,new Location(origin_cassettes_number.x+origin_cassettes_number.w-origin_cassettes_number.w/6,origin_cassettes_number.y-origin_cassettes_number.h/2));
					}
					
					r.click("img/OK_button");
					
				}
				if(asap!=null){
					System.out.println("click asap....");
					asap.click();
					s.wait(1.0);
					break;
				}
				if(res_ok!=null){
					System.out.println("res_ok");
					res_ok.click();
					s.wait(1.0);
				}
				else if(res_yes!=null){
					System.out.println("res_yes");
					res_yes.click();
					s.wait(1.0);
				}
				else {
					System.out.println("no asap???????");
					break;
				}
			
			}
			r.click("img/Run_button.png");
			s.wait(1.0);
			for(int i=0;i<3;i++){
				res_ok=r.exists("img/OK_button.png");
				res_ok2=r.exists("img/Ok_button.png");
				res_yes=r.exists("img/Yes_button.png");
				if(res_ok!=null){
					res_ok.click();
					InitMouse();
					s.wait(1.0);
				}
				else if(res_yes!=null){
					res_yes.click();
					s.wait(1.0);
					InitMouse();
				}
				else if(res_ok2!=null){
					res_ok2.click();
					s.wait(1.0);
					InitMouse();
				}
				else {
					s.wait(1.0);
				}
			}
//			DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Match end_of_program;
			
			end_of_program = r.exists("img/end_of_program");
			Region reg = new Region(end_of_program.x,end_of_program.y,end_of_program.w,end_of_program.h*3);
			String[] read_end_time=reg.text().toString().split("\n");
			String date=read_end_time[2].replace(" ","").replace("—", "-").replace("-", "");
			String date2=read_end_time[1].replace(" ","").replace("—", "-");
			String end_time=date.substring(0,4)+"-"+date.substring(4,6)+"-"+date.substring(6,8)+" "+date2;
//			Date myDate2 = dateFormat2.parse(end_time);
			
			return end_time;
		
	}
	public String Current_GUI_Time(){
		
		Match network_icon = r.exists("img/network_status");
	
		Region reg = new Region(0,network_icon.y,network_icon.w*6,network_icon.h);
		
		//System.out.println(reg.text().toString());
		System.out.println(reg.text().toString());
		String[] time_list=reg.text().toString().split("\n");
		int length = time_list.length;
		
		String current_gui_time=time_list[length-2].replace(" ", "").replace("—", "-")+" "+time_list[length-1].replace(" ", "").replace("—", "-");
		
//		guidate = dateFormat2.parse(time_list[2].replace(" ", "")+" "+time_list[1].replace(" ", ""));
		return current_gui_time;
	}
	public void WaitProgramFinish(String endtime) throws InterruptedException{
		try {
			int loop=0;
			while(true){
				DateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				dateFormat2.setLenient(false);
				
				Date guidate = dateFormat2.parse(Current_GUI_Time());
				Date program_end_time=dateFormat2.parse(endtime);
				long current_time_value1 =guidate.getYear()*100000000+guidate.getMonth()*1000000+guidate.getDay()*10000+guidate.getHours()*100+guidate.getMinutes();
				long endtime_value2 =program_end_time.getYear()*100000000+program_end_time.getMonth()*1000000+program_end_time.getDay()*10000+program_end_time.getHours()*100+program_end_time.getMinutes();
				if(current_time_value1<endtime_value2){
					s.wait(60.0);
				}
				else {
					if(loop>0) break;
					s.wait(60.0);
					loop++;
				}
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean DrainRetort() throws FindFailed{
		if(r.exists("img/ConfirmationMessage.png")!=null){
			r.click("img/OK_button");
			InitMouse();
			return true;
		}
		else return false;
	}
	public void CompleteAndRemoveSpecimen(String IP) throws FindFailed, IOException, InterruptedException{
		
			String path = new String("./bash/ssh.sh ");
			System.out.println("instrument ip is..."+IP);
			
			int result = 9999;
			while (true) {
				Process process = null;
				Match m=r.exists("img/ConfirmationMessage");
				if( m != null){
					Match OK_button=r.exists("img/OK_button");
						process = Runtime.getRuntime().exec(path + IP);
						result = process.waitFor();
						s.wait(1.0);
						OK_button.click();
						s.wait(2.0);
				}
				else break;
				
				
			
			}
		
	}
	public void ConfirmReadyToStartNewProgram() throws FindFailed{
		if(r.exists("img/ready_to_start_newprogram.png")!=null){
			Match OK_button=r.find("img/OK_button");
			OK_button.click();
			System.out.println("Program completed,ready to start a new program...");
		}
		else{
			System.out.println("can't find ready to start a new program");
		}
		InitMouse();
		System.out.println("Program complete!!!");
	}
	public void wait(double waitseconds) {
		s.wait(waitseconds);
	}
	public void InitMouse(){
		Location location=Mouse.at();
//		Mouse.move(-(location.x),-(location.y));
		Mouse.move(200,200);
	}
	public void CreateTable(String tablename){
		Connection conn=null;
    	String sql;
    	String url="jdbc:mysql://localhost:3306/himalaya?"
                + "user=root&password=123456";
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			sql = "create table "+tablename+"(id INT NOT NULL,item varchar(100),value varchar(100),primary key(id))";
			int result ;
			result = stmt.executeUpdate(sql);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
	public void UpdateData(String tablename,LinkedHashMap<String,String> eachcase_result){
		Connection conn=null;
    	String sql;
    	String url="jdbc:mysql://localhost:3306/himalaya?"
                + "user=root&password=123456";
    	try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url);
			Statement stmt = conn.createStatement();
			
			int i=1;
			for(String key:eachcase_result.keySet()){
				sql = "insert into "+tablename+" values ("+i+","+"'"+key+"'"+","+"'"+eachcase_result.get(key)+"'"+")";
				System.out.println("sql: "+sql);
				stmt.executeUpdate(sql);
				i++;
				
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    	finally{
    		try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
	}
	
	public void DeleteTable(String tablename){
		Connection conn=null;
    	String sql;
    	String url="jdbc:mysql://localhost:3306/himalaya?"
                + "user=root&password=123456";
    	
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				sql="drop table "+tablename;
				stmt.executeUpdate(sql);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
    	
	}
	public void InitDatabase(){
		Connection conn=null;
    	String sql;
    	String url="jdbc:mysql://localhost:3306/mysql?"
                + "user=root&password=123456";
    	Statement stmt;
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url);
				stmt = conn.createStatement();
				ResultSet set=stmt.executeQuery("show databases");
				while(set.next()){
					if(set.getString(1).equals("himalaya")){
						
						sql="drop database himalaya";
						stmt.executeUpdate(sql);
						break;
					}
				}
				sql="create database himalaya";
				stmt.executeUpdate(sql);
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				sql="create database himalaya";
				
				e.printStackTrace();
			}
			finally{
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
    	
	}
	@SuppressWarnings("finally")
	public LinkedHashMap<String,String> GetTableData(String tablename){
		Connection conn=null;
    	String sql;
    	ResultSet rs=null;
    	LinkedHashMap<String,String> data=new LinkedHashMap<String,String>();
    	String url="jdbc:mysql://localhost:3306/himalaya?"
                + "user=root&password=123456";
    	
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				sql="select * from "+tablename;
				rs=stmt.executeQuery(sql);
				while(rs.next()){
					data.put(rs.getString("item"), rs.getString("value"));
				}
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				try {
					conn.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return data;
			}
	}
	
	public void test(){
		for(int i=0;i<10;i++){
		System.out.println(Current_GUI_Time());
		s.wait(3.0);
		}
	}
	
}
