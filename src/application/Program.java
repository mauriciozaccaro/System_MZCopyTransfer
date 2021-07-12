package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import services.CopiarColar;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import java.awt.Choice;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.awt.event.ActionEvent;

public class Program {

	private JFrame frame;
	private JPanel panel;
	private JTextField txtOrigem;
	private Button btDestino;
	private JTextField txtDestino;
	private JLabel lblNewLabel_1;
	private Button btCopiar;
	private Button btSair;

	public static final String caminhoPath = "C:\\SystemCopyTransfer";
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Program window = new Program();
					window.frame.setVisible(true);
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void abreArquivoOrigem() {
		
			String origem, destino = null;
			String caminho =  caminhoPath + "\\DadosCopiaCola.txt";
			File path = new File(caminho);
			
			try(BufferedReader br = new BufferedReader(new FileReader(path))) {
			
				String line = br.readLine();
				String[] fields = line.split(",");
				
				txtOrigem.setText(fields[0]);
				txtDestino.setText(fields[1]);
				
			}catch(IOException e) {
				e.printStackTrace();
			}
	}
	
	public static void criaPastaDiretorio() {
		File newPath = new File(caminhoPath);
		newPath.mkdir();
	}	
		
	
	public Boolean verificaExisteArquivo() {
		
		String caminhoArquivoOrigem = caminhoPath + "\\DadosCopiaCola.txt";
		File fl = new File(caminhoArquivoOrigem);
		
		if(fl.exists()) {
			abreArquivoOrigem();
			return true;
		}else {
			criaPastaDiretorio();
			return false;
		}
	}
	
	public void fazCopia() {
		
		String origem = txtOrigem.getText();
		String destino = txtDestino.getText();
		
		FileChannel sourceChannel = null;
		FileChannel targetChannel = null;
		
		File origemFile = new File(origem);
		File[] arquivx = origemFile.listFiles(File::isFile); // pega somente o que for arquivo (ingnora as pastas)
		
		for(File fl : arquivx) {
			
			File aux = new File(fl.getPath()); 
			String aux2 = destino + "\\" + fl.getName();
			File aux3 = new File(aux2);
			
			long ultimaModificacao = aux.lastModified();
			
			try {
				sourceChannel = new FileInputStream(aux).getChannel();
				targetChannel = new FileOutputStream(aux3).getChannel();
				
				try {
					sourceChannel.transferTo(0, sourceChannel.size(), targetChannel);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				System.out.println(fl.getName() + " ... Copiado com Sucesso!");
				aux3.setLastModified(ultimaModificacao);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public Program() {
		initialize();
		verificaExisteArquivo();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 411, 224);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Pasta de Origem...");
		lblNewLabel.setBounds(10, 11, 111, 14);
		panel.add(lblNewLabel);
		
		txtOrigem = new JTextField();
		txtOrigem.setBounds(10, 26, 283, 20);
		panel.add(txtOrigem);
		txtOrigem.setColumns(10);
				
		Button btOrigem = new Button("...");
		btOrigem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showOpenDialog(btOrigem);
				File path = fc.getSelectedFile();
				
				txtOrigem.setText(path.getPath());
			}
		});
		btOrigem.setBounds(297, 24, 23, 22);
		panel.add(btOrigem);
		
		btDestino = new Button("...");
		btDestino.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser();
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fc.showOpenDialog(btDestino);
				File path = fc.getSelectedFile();
				
				txtDestino.setText(path.getPath());
			}
		});
		btDestino.setBounds(297, 70, 23, 22);
		panel.add(btDestino);
		
		txtDestino = new JTextField();
		txtDestino.setColumns(10);
		txtDestino.setBounds(10, 72, 283, 20);
		panel.add(txtDestino);
		
		lblNewLabel_1 = new JLabel("Pasta de Destino...");
		lblNewLabel_1.setBounds(10, 57, 111, 14);
		panel.add(lblNewLabel_1);
		
		btCopiar = new Button("Copiar");
		btCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(txtOrigem.getText() == null || txtDestino.getText() == null) {
					throw new IllegalStateException("Campos de Origem/Destino não pode ser nulos");
				}
				fazCopia();
			}
		});
		btCopiar.setBounds(51, 105, 70, 22);
		panel.add(btCopiar);
		
		btSair = new Button("Sair");
		btSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btSair.setBounds(142, 105, 70, 22);
		panel.add(btSair);
	}
}
