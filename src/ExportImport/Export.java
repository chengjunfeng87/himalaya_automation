package ExportImport;
import java.io.IOException;

import org.sikuli.script.FindFailed;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;
import org.sikuli.script.Region;
public class Export {
	
	public int test() throws FindFailed  {
			Screen s = new Screen();
			s.click("img/Settings.png");
			s.click("img/user_export.png");
			s.wait(1.0);
			boolean b = s.waitVanish("img/user_exporting.png",20.0);
			s.wait(1.0);
			if(!b || s.exists("img/export_successfully.png")==null) {
				return 101;
			}
			s.click("img/1461039077222.png");
			
			return 0;
		
		
	}

}
