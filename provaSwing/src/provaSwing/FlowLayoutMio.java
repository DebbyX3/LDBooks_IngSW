package provaSwing;
import javax.swing.*;
import java.awt.*;
import java.awt.Container;

public final class FlowLayoutMio extends JFrame
{	
	private static final String titolo = "Flow layout";
	private static final JButton uno = new JButton("uno");
	private static final JButton due = new JButton("due");
	private static final JButton tre = new JButton("tre");
	private static final JButton quattro = new JButton("quattro");
	private static final JButton cinque = new JButton("cinque");
	
	public FlowLayoutMio()
	{		
		super(titolo);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(300, 100);
		Container frmContentPane = this.getContentPane();
		frmContentPane.setLayout(new FlowLayout());
		frmContentPane.add(uno);
		frmContentPane.add(due);
		frmContentPane.add(tre);
		frmContentPane.add(quattro);
		frmContentPane.add(cinque);
		this.setVisible(true);		
	}
}
