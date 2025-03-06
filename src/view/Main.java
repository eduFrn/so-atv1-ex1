package view;

import javax.swing.JOptionPane;

import controller.RedesController;

public class Main {
	
	public static void main(String[] args) {
		int opc = 0;
		RedesController redesController;
		
		do {
			opc = Integer.parseInt(JOptionPane.showInputDialog("MENU\n1-Consultar adaptador e ip\n2-Ping Google"));
			
			switch (opc) {
				case 1:
					redesController = new RedesController();
					
					JOptionPane.showMessageDialog(null, redesController.ip());
					break;
				case 2:
					redesController = new RedesController();
					
					JOptionPane.showMessageDialog(null, redesController.ping());
					break;
				case 9:
					JOptionPane.showMessageDialog(null, "FIM DO PROGRAMA");
					break;
				default:
					JOptionPane.showMessageDialog(null, "A opção informada é inválida");
			}
		} while (opc != 9);
	}
	
}
