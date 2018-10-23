import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
public class ClientUDP{
	private String DNS_ADDR;
	private int DNS_PORTA;
	public ClientUDP(String DADDR,int PORTA){
		this.DNS_ADDR  = DADDR;
		this.DNS_PORTA = PORTA;
	}
	public String Request(String ADDR){
		byte[] outDado = new byte[1024];
		byte[] inDado  = new byte[1024];
		String result = "";
		try{
			DatagramSocket portaCli = new DatagramSocket();
			InetAddress IPServidor = InetAddress.getByName(this.DNS_ADDR);
			outDado = ADDR.getBytes();
			DatagramPacket outPct = new DatagramPacket(
				outDado, outDado.length, IPServidor, this.DNS_PORTA);
			portaCli.send(outPct);
			DatagramPacket inPct = new DatagramPacket(inDado, inDado.length);
			portaCli.receive(inPct);
			result = new String(inPct.getData()).trim();
			portaCli.close();
		}
		catch(Exception E){
			E.printStackTrace(System.out);
		}
		return result;
	}
}