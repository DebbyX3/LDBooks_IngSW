package provaSwing;

import java.awt.Container;

import javax . swing .*;

public class GUIApp 
{
	//public static void main ( String [] args ) 
	//{
	public GUIApp()
	{
		// Crea una finestra con titolo " Hello World "
		JFrame frm = new JFrame ("Hello World!") ;
		frm.setSize (400 , 500);
		frm.setLocation(500, 300);
		//frm.pack();	//minimo spazio possibile
		// Rende la finestra visibile (di default non lo è!)
		frm.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		
		Container frmContentPane = frm.getContentPane();
		frmContentPane.add(new JLabel("Buona lezione!"));
		
		frm.setVisible (true);		
	}
}