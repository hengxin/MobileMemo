package ics.mobilememo.script;

/**
 * script for uninstalling apks
 * @author hengxin
 * @date Jul 1, 2014
 */
public class UninstallAPK
{
	public static void main(String[] args)
	{
		ADBExecutor adb_executor = new ADBExecutor("D:\\AndroidSDK\\platform-tools\\adb.exe ");
		
		// "adb -s [device] forward tcp: tcp: "
		adb_executor.execAdbOnlineDevicesPortForward();
		
		// uninstall apk "ics.android_usb_computer"
		adb_executor.uninstall("ics.android_usb_computer");
		// uninstall apk "ics.mobilememo"
		adb_executor.uninstall("ics.mobilememo");
	}
}
