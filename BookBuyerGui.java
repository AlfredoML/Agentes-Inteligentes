package Books.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Books.agents.BookBuyerAgent;

public class BookBuyerGui extends JFrame {
    private BookBuyerAgent myAgent;
	
    private JTextField titleBookField;
    JPanel p;
        
    public BookBuyerGui(BookBuyerAgent a, int caso, String nombre, int precio) {
        super(a.getLocalName());
        myAgent = a;
        p = new JPanel();
        p.setLayout(new GridLayout(2, 2));
        
        switch (caso) {
            case 1:
                {
                    p.add(new JLabel("Nombre del libro a comprar:"));
                    titleBookField = new JTextField(15);
                    p.add(titleBookField);
                    getContentPane().add(p, BorderLayout.CENTER);
                    JButton addButton = new JButton("Comprar");
                    addButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ev) {
                            try {
                                String title = titleBookField.getText().trim();
                                myAgent.buscarLibro(title);
                                titleBookField.setText("");
                            }catch(Exception e) {
                                JOptionPane.showMessageDialog(BookBuyerGui.this, "Invalid values"+e,"Error",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });     
                    p = new JPanel();
                    p.add(addButton);
                    getContentPane().add(p, BorderLayout.SOUTH);
                    break;
                }
            case 2:
                p.add(new JLabel("Buscando Libro... "));
                getContentPane().add(p, BorderLayout.CENTER);
                break;
            case 3:
                {
                    p.add(new JLabel("Lo sentimos no se encontro ningun libro a la venta con el nombre ingresado"));
                    getContentPane().add(p, BorderLayout.CENTER);
                    JButton addButton = new JButton("Cerrar");
                    addButton.addActionListener((ActionEvent ev) -> {
                        try {
                            myAgent.doDelete();
                            this.dispose();
                        }catch(Exception e) {
                            JOptionPane.showMessageDialog(BookBuyerGui.this, "Invalid values"+e,"Error",JOptionPane.ERROR_MESSAGE);
                        }
                    });     
                    p = new JPanel();
                    p.add(addButton);
                    getContentPane().add(p, BorderLayout.SOUTH);
                    break;
                }
                
                
            case 4:
                {
                    p.add(new JLabel("COMPRA EXITOSA \n"));
                    p.add(new JLabel("Libro: "+ nombre));
                    p.add(new JLabel("Precio: "+precio));
                    getContentPane().add(p, BorderLayout.CENTER);
                    JButton addButton = new JButton("Cerrar");
                    addButton.addActionListener((ActionEvent ev) -> {
                        try {
                            myAgent.doDelete();
                            this.dispose();
                        }catch(Exception e) {
                            JOptionPane.showMessageDialog(BookBuyerGui.this, "Invalid values"+e,"Error",JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    p = new JPanel();
                    p.add(addButton);
                    getContentPane().add(p, BorderLayout.SOUTH);
                    break;
                }
            case 5:
                {
                    p.add(new JLabel("Lo sentimos el libro que busca ya se ha vendido"));
                    getContentPane().add(p, BorderLayout.CENTER);
                    JButton addButton = new JButton("Cerrar");
                    addButton.addActionListener((ActionEvent ev) -> {
                        try {
                            myAgent.doDelete();
                            this.dispose();
                        }catch(Exception e) {
                            JOptionPane.showMessageDialog(BookBuyerGui.this, "Invalid values"+e,"Error",JOptionPane.ERROR_MESSAGE);
                        }
                    });     
                    p = new JPanel();
                    p.add(addButton);
                    getContentPane().add(p, BorderLayout.SOUTH);
                    break;
                }
            default:
                break;
        }
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                myAgent.doDelete();
            }
        });
        	
        setResizable(false);
    }
	
    public void showGui() {
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int centerX = (int)screenSize.getWidth() / 2;
        int centerY = (int)screenSize.getHeight() / 2;
      
        setLocation(centerX - getWidth() / 2, centerY - getHeight() / 2);
        super.setVisible(true);
    }

}
