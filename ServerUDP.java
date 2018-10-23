import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Hashtable;
public class ServerUDP implements Runnable{
	private DatagramSocket PORTA;
	private Hashtable<String,String> TABELA_DNS;
	public ServerUDP(int N_PORTA){
		try{
			this.PORTA = new DatagramSocket(N_PORTA);
		}
		catch(Exception e){
			e.printStackTrace(System.out);
		}
		this.TABELA_DNS = new Hashtable<String,String>();
	}
	public void AddToTable(String addr, String IPad){
		this.TABELA_DNS.put(addr,IPad);
	}
	public void run(){
		for(;;){
			byte[] inDado  = new byte[1024];
			byte[] outDado = new byte[1024];
			DatagramPacket inPct = new DatagramPacket(inDado, inDado.length);
			try{
				this.PORTA.receive(inPct);
			}
			catch(Exception e){
				e.printStackTrace(System.out);
			}
			String inADDR = new String(inPct.getData()).trim();
			InetAddress IPCliente = inPct.getAddress();
			int cliPort = inPct.getPort();
			String outADDR;
			if(this.TABELA_DNS.get(inADDR) != null)
				outADDR = this.TABELA_DNS.get(inADDR);
			else
				outADDR = "NOT FOUND";
			outDado = outADDR.getBytes();
			DatagramPacket outPct = 
			new DatagramPacket(outDado, outDado.length, IPCliente, cliPort);
			try{
				this.PORTA.send(outPct);
			}
			catch(Exception e){
				e.printStackTrace(System.out);
			}
		}
	}
}