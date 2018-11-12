package persistance.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CfgManager implements ICfgManager {
	private static CfgManager INSTANCE = null;
	public static String OTHER_CFG_FILE = "OTHER_CFG_FILE";
	private String EXTERNAL_CFG_FILE = "EXTERNAL_CFG_FILE";
	private Properties prop = new Properties();

	private CfgManager() throws Exception {
		load();
	}

	public static CfgManager getInstance() throws Exception {
		if (INSTANCE == null)
			INSTANCE = new CfgManager();

		return INSTANCE;
	}

	@Override
	public Properties getCfg() {
		// TODO Auto-generated method stub
		return prop;
	}

	public void load() throws Exception {
		InputStream is = null;
		EXTERNAL_CFG_FILE = System.getProperty("EXTERNAL_CFG_FILE");
		OTHER_CFG_FILE = System.getProperty("OTHER_CFG_FILE");

		if (OTHER_CFG_FILE != null)
			is = this.getClass().getClassLoader().getResourceAsStream(OTHER_CFG_FILE);
		else if (EXTERNAL_CFG_FILE != null)
			is = new FileInputStream(EXTERNAL_CFG_FILE);

		try {
			prop.load(is);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw e;
		}
	}
}
