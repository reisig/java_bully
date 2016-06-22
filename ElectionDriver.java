package assignment4;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;
import java.util.TimerTask;

public class ElectionDriver {

	int delay = 5000;
	
	private final int min = 10;
	private final int max = 15;

	private String host;
	
	public ElectionDriver(String hostIn) {
		host = hostIn;
		int period = min + (int) (Math.random() * ((max - min) + 1)) * 1000;
		
		Timer t = new Timer();
		t.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				try {
					Registry reg = LocateRegistry.getRegistry(host);
					for (String nodeName : reg.list()) {
						try {
							int silence = (int) (Math.random() * 5);
							
							ElectionNode node = (ElectionNode) reg.lookup(nodeName);
							node.makeChaos("Node-" + System.currentTimeMillis(), silence);
						} catch (NotBoundException e) {
							System.out.println("Election Driver Error: " + e.toString());
							//e.printStackTrace();
						}
					}
				} catch (RemoteException e) {
					System.out.println("Election Driver Error: " + e.toString());
					//e.printStackTrace();
				}
			}
		}, delay, period);
	}

	public static void main(String[] args) {
		String host = (args.length < 1) ? null : args[0];
		new ElectionDriver(host);
	}

}
