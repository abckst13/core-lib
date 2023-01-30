package kr.aipeppers.pep.core.util;

public class ServerUtil {

	/**
	 * Gets the server system name.
	 *
	 * @return the server system name
	 */
	public static String getWorkerName() {
		String serverSystemName = System.getProperty("workernode.name");
		if(StringUtil.isEmpty(serverSystemName)) {
			serverSystemName = ConfigUtil.getString("workernode.name");
		}

		return serverSystemName;
	}

	/**
	 * Gets the server hostname.
	 *
	 * @return the server hostname
	 */
	@Deprecated
	public static String getServerHostname() {
		String serverHostname = System.getProperty("server.host.name");
		if(StringUtil.isEmpty(serverHostname)) {
			serverHostname = ConfigUtil.getString("server.host.name");
		}
		return serverHostname;
	}

}
