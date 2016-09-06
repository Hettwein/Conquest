package de.htwg.conquest.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.google.inject.Inject;

import de.htwg.conquest.controller.IController;
import de.htwg.conquest.util.observer.IObserver;

public class GUI extends JFrame implements ActionListener, IObserver {

	private static final long serialVersionUID = 7762280579254800122L;
	
	private IController controller;
	private JButton[][] cells;
	private JPanel gameField;

	private GridBagConstraints grid;

	private JButton position;

	@Inject
	public GUI(IController controller) {
		this.controller = controller;
		controller.addObserver(this);
	}

	@Override
	public void update() {
		int size = controller.getSize();
		if(cells == null) {
			cells = new JButton[size][size];
			
			this.setTitle("GoBang");
			this.setLayout(new BorderLayout());
			
			// GameField
			gameField = new JPanel();
			gameField.setLayout(new GridBagLayout());

			grid = new GridBagConstraints();
			grid.fill = GridBagConstraints.HORIZONTAL;
			grid.ipadx = 5;
			grid.ipady = 5;
			grid.weightx = 8;

			for (int i = 0; i < size; i++) {
				for (int k = 0; k < size; k++) {
					grid.gridx = i;
					grid.gridy = k;
					position = new JButton();
					position.setName((i) + "," + (k));
					gameField.add(position, grid);
					position.addActionListener(this);
					cells[i][k] = position;
					cells[i][k].setMargin(new Insets(0,0,0,0));
					cells[i][k].setPreferredSize(new Dimension(25, 25));
					cells[i][k].setFont(new Font("Arial", Font.PLAIN, 15));
				}
			}
			
			this.add(gameField, BorderLayout.CENTER);

			this.pack();
			this.setResizable(false);
			this.setVisible(true);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		}
		for (int i = 0; i < size; i++){
			for (int n = 0; n < size; n++){
				cells[i][n].setBackground(controller.getField().getCell(i, n).getColor());
				if(controller.getField().getCell(i, n).isOwned()) {
					cells[i][n].setText(String.valueOf(controller.getField().getCell(i, n).getOwner().getName().charAt(0)));
					cells[i][n].setEnabled(false);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
