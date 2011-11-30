package assignment4;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ElectionNode extends Remote {
	
	// Node methods
	public String startElection(String senderName, int senderClock) throws RemoteException, DeadNodeException;
	public void newLeader(String newLeaderName) throws RemoteException;
	public String recvMsg(String senderName, String msg) throws RemoteException;
	
	// Election Driver methods
	public void makeChaos(String newName, int ignore) throws RemoteException;

}
