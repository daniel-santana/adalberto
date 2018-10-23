import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
public class Main{
	public static void main(String[] args){
		File DNS_LIST = new File(args[0]);
		final int PORTA = 9876;
		
		ServerUDP servidor = createServer(PORTA,DNS_LIST);
		Thread THREAD_Servidor = new Thread(servidor);
		THREAD_Servidor.start();
		
		ClientUDP cliente = new ClientUDP("localhost",PORTA);
		for(;;){
			System.out.print("Nome: ");
			System.out.println("Endereço: "+cliente.Request(getInput()));
		}
	}
	
	
	
	
	private static ServerUDP createServer(int PORTA,File DNS_LIST){
		ServerUDP servidor = new ServerUDP(PORTA);
		try{
			BufferedReader reader = 
				new BufferedReader(
				new InputStreamReader(
				new FileInputStream(DNS_LIST)
				)
				);
			for(String LINE = reader.readLine(); LINE != null; LINE = reader.readLine())
				if(LINE.length() > 0 && LINE.charAt(0) != '#'){
					System.out.println("Adicionando endereço: "+LINE.split(" ")[0]);
					servidor.AddToTable(LINE.split(" ")[0],LINE.split(" ")[1]);
				}
		}catch(Exception E){
			E.printStackTrace(System.out);
		}
		return servidor;
	}
	private static String getInput(){
		try{
			return new BufferedReader(new InputStreamReader(System.in)).readLine();
		}
		catch(Exception E){
			return getInput();
		}
	}
}