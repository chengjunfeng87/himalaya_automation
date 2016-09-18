package smoketest;

import java.io.IOException;
import java.util.LinkedHashMap;

import org.sikuli.script.FindFailed;

import demo.Utils;

public class ProgramLoop {
	Utils u=new Utils();
	String IP,Path;
	
	public void test(String IP) throws FindFailed, InterruptedException, IOException{
		//firstly call in each case
		
		//tablename should add `` because of tablename contain .
		String tablename="`"+getClass().getName()+"`";
		u.CreateTable(tablename);

		LinkedHashMap<String,String> testresult=new LinkedHashMap<String,String>();
		IP=IP;
		String[] reagents={"Formalin"};
	
			u.LoginWithSupervisor();
			
			u.NewAProgram("img/program/program1.png","test",reagents);
			u.wait(3.0); //at least 2.0s
			u.ApplyProgram();
		
		String end_time;
		
			testresult.put("start time", u.Current_GUI_Time());
			end_time = u.StartProgram("img/program/program1.png", 0,100);
			System.out.println("set end time: "+end_time);
			u.WaitProgramFinish(end_time);
			
			if(u.DrainRetort()) System.out.println("Drain retort...");
			else {System.out.println("Can't find Drain retort 5mins later after program endtime"); System.exit(1);}
			u.wait(90.0);
			
			u.CompleteAndRemoveSpecimen(IP);
			System.out.println("program complete and remove specimen...");
			u.wait(80.0);
			u.ConfirmReadyToStartNewProgram();
			System.out.println("ready to start a new program...");
			u.LoginWithSupervisor();
			u.DeletePrograms();
		
		testresult.put("end time", u.Current_GUI_Time());
		u.UpdateData(tablename, testresult);
		
	}
	
}
