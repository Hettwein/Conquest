package de.htwg.conquest.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.inject.Inject;

import de.htwg.conquest.controller.IController;
import de.htwg.conquest.model.IPlayer;
import de.htwg.conquest.util.observer.IObserver;

public class GUI extends JFrame implements ActionListener, IObserver {

	private static final long serialVersionUID = 7762280579254800122L;
	
	private IController controller;
	private JButton[][] cells;
	private JPanel gameField;

	private GridBagConstraints grid;

	private JButton position;

	private JPanel sidePanel;

	@Inject
	public GUI(IController controller) {
		this.controller = controller;
		controller.addObserver(this);
	}

	@Override
	public void update() {
		int size = controller.getSize();
		if(cells == null) {
			init(size);
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
		((JLabel)sidePanel.getComponent(1)).setText(controller.getCurrentPlayer().getName());
		for(int i = 0; i < controller.getPlayers().size(); i++) {
			IPlayer p = controller.getPlayers().get(i);
			((JLabel)sidePanel.getComponent(i + 2)).setText(p.getName() + ": " + p.getCellCount());
		}
	}

	private void init(int size) {
		cells = new JButton[size][size];
		
		this.setTitle("Conquest");
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
//				position.addActionListener(this);
				cells[i][k] = position;
				cells[i][k].setMargin(new Insets(0,0,0,0));
				cells[i][k].setPreferredSize(new Dimension(25, 25));
				cells[i][k].setFont(new Font("Arial", Font.PLAIN, 15));
			}
		}

		JPanel colorPanel = new JPanel();
		colorPanel.setLayout(new FlowLayout());
		colorPanel.add(new JLabel("Choose a color:"));
		
		JButton color1 = new JButton();
		color1.setBackground(Color.RED);
		color1.setPreferredSize(new Dimension(25, 25));
		color1.addActionListener(this);
		colorPanel.add(color1);
		
		JButton color2 = new JButton();
		color2.setBackground(Color.BLUE);
		color2.setPreferredSize(new Dimension(25, 25));
		color2.addActionListener(this);
		colorPanel.add(color2);
		
		JButton color3 = new JButton();
		color3.setBackground(Color.GREEN);
		color3.setPreferredSize(new Dimension(25, 25));
		color3.addActionListener(this);
		colorPanel.add(color3);
		
		JButton color4 = new JButton();
		color4.setBackground(Color.YELLOW);
		color4.setPreferredSize(new Dimension(25, 25));
		color4.addActionListener(this);
		colorPanel.add(color4);
		
		JButton color5 = new JButton();
		color5.setBackground(Color.MAGENTA);
		color5.setPreferredSize(new Dimension(25, 25));
		color5.addActionListener(this);
		colorPanel.add(color5);
		
		sidePanel = new JPanel();
		sidePanel.setLayout(new GridBagLayout());

		JLabel currentPlayerLabel = new JLabel("current player: ");
		JLabel currentPlayerText = new JLabel(controller.getCurrentPlayer().getName());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 5;
		c.ipady = 5;
		c.weightx = 1;

		c.gridx = 0;
		c.gridy = 0;
		sidePanel.add(currentPlayerLabel, c);

		c.gridx = 1;
		c.gridy = 0;
		sidePanel.add(currentPlayerText, c);
		
		for(int i = 0; i < controller.getPlayers().size(); i++) {
			c.gridx = 0;
			c.gridy = i + 1;
			sidePanel.add(new JLabel(controller.getPlayers().get(i).getName() + ": " + controller.getPlayers().get(i).getCellCount()), c);
		}
		
		this.add(gameField, BorderLayout.CENTER);
		this.add(colorPanel, BorderLayout.SOUTH);
		this.add(sidePanel, BorderLayout.EAST);

		this.pack();
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton)e.getSource();
		controller.changeColor(source.getBackground());
//		switch(source.getName()) {
//			case "red":
//				controller.changeColor(source.getBackground());
//			case "blue":
//			case "green":
//			case "yellow":
//			case "magenta":
//			default:
//				
//		}
	}
}
