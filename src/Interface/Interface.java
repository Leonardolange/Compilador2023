package Interface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import compilador.*;
//Alunos: Marcella Coelho Brito Nunes, Leonardo André Lange,Felipe Melio Tomelin

public class Interface {
	private JFrame frame;
	private JTextArea taEditor;
	private JTextArea taAreaMensagens;
	private JLabel lblStatus;
	private static JButton btnCopiar;
	private static JButton btnAbrir;
	private JButton btnSalvar;
	private String caminhoArquivoSelecionado = "";
	private JFileChooser fileChooser;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interface window = new Interface();
					window.frame.setVisible(true); 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public Interface() {
		initialize();
	}
	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				taEditor.requestFocus();
			}
		});
		frame.setBounds(100, 100, 900, 318);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(910, 600));
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setLocationRelativeTo(null);
		
		JPanel pnBarraFerramentas = new JPanel();
		pnBarraFerramentas.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		frame.getContentPane().add(pnBarraFerramentas, BorderLayout.NORTH);
		pnBarraFerramentas.setPreferredSize(new Dimension(900, 70));
		pnBarraFerramentas.setLayout(null);
		
		JButton btnNovo = new JButton("novo [ctrl-n]", new ImageIcon(Interface.class.getResource("/imagens/Novo.png")));
		btnNovo.setBackground(SystemColor.menu);
		btnNovo.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNovo.setBorder(new LineBorder(SystemColor.desktop));
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				novo();
			}
		});
		btnNovo.setBounds(0, 0, 110, 70);		
		pnBarraFerramentas.add(btnNovo);
		
		btnAbrir = new JButton("abrir [ctrl-o]");
		btnAbrir.setIcon(new ImageIcon(Interface.class.getResource("/imagens/Abrir.png")));
		btnAbrir.setBackground(SystemColor.menu);
		btnAbrir.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnAbrir.setBorder(new LineBorder(Color.BLACK));
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrir();
			}
		});
		btnAbrir.setBounds(108, 0, 110, 70);
		pnBarraFerramentas.add(btnAbrir);
		
		btnSalvar = new JButton("salvar [ctrl-s]");
		btnSalvar.setIcon(new ImageIcon(Interface.class.getResource("/imagens/Salvar.png")));
		btnSalvar.setBackground(SystemColor.menu);
		btnSalvar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSalvar.setBorder(new LineBorder(Color.BLACK));
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		btnSalvar.setBounds(215, 0, 110, 70);
		pnBarraFerramentas.add(btnSalvar);
		
		btnCopiar = new JButton("copiar [ctrl-c]");
		btnCopiar.setIcon(new ImageIcon(Interface.class.getResource("/imagens/Copiar.png")));
		btnCopiar.setBackground(SystemColor.menu);
		btnCopiar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCopiar.setBorder(new LineBorder(Color.BLACK));
		btnCopiar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StringSelection selecao = new StringSelection(taEditor.getSelectedText());
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selecao, selecao);
			}
		});
		btnCopiar.setBounds(324, 0, 117, 70);
		pnBarraFerramentas.add(btnCopiar);
		
		JButton btnColar = new JButton("colar [ctrl-v]");
		btnColar.setIcon(new ImageIcon(Interface.class.getResource("/imagens/Colar.png")));
		btnColar.setBackground(SystemColor.menu);
		btnColar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnColar.setBorder(new LineBorder(Color.BLACK));
		btnColar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
			    Transferable t = c.getContents(this);
			    if (t == null)
			        return;
			    try { 
			        taEditor.insert((String) t.getTransferData(DataFlavor.stringFlavor), taEditor.getCaretPosition());
			    } catch (Exception ex){
			        ex.printStackTrace();
			    }
			}
		});
		btnColar.setBounds(438, 0, 110, 70);
		pnBarraFerramentas.add(btnColar);
		
		JButton btnRecortar = new JButton("recortar [ctrl-x]");
		btnRecortar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnRecortar.setIcon(new ImageIcon(Interface.class.getResource("/imagens/Recortar.png")));
		btnRecortar.setBackground(SystemColor.menu);
		btnRecortar.setBorder(new LineBorder(Color.BLACK));
		btnRecortar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (taEditor.getSelectedText() != null) {
					StringSelection selecao = new StringSelection(taEditor.getSelectedText());
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(selecao, selecao);
					taEditor.replaceSelection("");
				}
			}
		});
		btnRecortar.setBounds(546, 0, 134, 70);
		pnBarraFerramentas.add(btnRecortar);
		
		JButton btnCompilar = new JButton("compilar [F7]");
		btnCompilar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCompilar.setIcon(new ImageIcon(Interface.class.getResource("/imagens/Compilar.png")));
		btnCompilar.setBackground(SystemColor.menu);
		btnCompilar.setBorder(new LineBorder(Color.BLACK));
		btnCompilar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				compilar(); 
			}
		});
		btnCompilar.setBounds(678, 0, 110, 70);
		pnBarraFerramentas.add(btnCompilar);
		
		JButton btnEquipe = new JButton("equipe [F1]");
		btnEquipe.setIcon(new ImageIcon(Interface.class.getResource("/imagens/Equipe.png")));
		btnEquipe.setBackground(SystemColor.menu);
		btnEquipe.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnEquipe.setBorder(new LineBorder(Color.BLACK));
		btnEquipe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				equipe(); 
			}
		});
		btnEquipe.setBounds(786, 0, 110, 70);
		pnBarraFerramentas.add(btnEquipe);
		
		JPanel pnBarraStatus = new JPanel();
		pnBarraStatus.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		frame.getContentPane().add(pnBarraStatus, BorderLayout.SOUTH);
		pnBarraStatus.setPreferredSize(new Dimension(900, 25));
		pnBarraStatus.setLayout(new FlowLayout(FlowLayout.LEADING, 5, 5));
		
		lblStatus = new JLabel("Nenhum arquivo aberto");
		pnBarraStatus.add(lblStatus);
		
		fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de Texto", "txt");
        fileChooser.setFileFilter(filter);

        JButton openButton = new JButton("Abrir Arquivo");
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrir();
                
                }
            
        });
   
		JPanel pnAreaCriacao = new JPanel();
		frame.getContentPane().add(pnAreaCriacao, BorderLayout.CENTER);
		pnAreaCriacao.setLayout(new BorderLayout(0, 0));
		
		JSplitPane spAreaCriacao = new JSplitPane();
		spAreaCriacao.setOrientation(JSplitPane.VERTICAL_SPLIT);
		spAreaCriacao.setBounds(6, 0, 724, 537);
		pnAreaCriacao.add(spAreaCriacao);
		
		taEditor = new JTextArea();
		taEditor.setFont(new Font("Courier New", Font.PLAIN, 14));
		taEditor.setTransferHandler(null);
		taEditor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(java.awt.event.KeyEvent evt) {
				Clipboard clipboard = null;
			    if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_C) {
			        StringSelection selecao = new StringSelection(taEditor.getSelectedText());
			        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			        clipboard.setContents(selecao, selecao);
			    } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_X) {
			        if (taEditor.getSelectedText() != null) {
			            StringSelection selecao = new StringSelection(taEditor.getSelectedText());
			            clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			            clipboard.setContents(selecao, selecao);
			            taEditor.replaceSelection("");
			        }
			    } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_V) {
			        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
			        Transferable t = c.getContents(null);
			        if (t == null)
			            return;
			        if (t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			            try {
			                String data = (String) t.getTransferData(DataFlavor.stringFlavor);
			                taEditor.insert(data, taEditor.getCaretPosition());
			            } catch (Exception ex) {
			                ex.printStackTrace();
			            }
			        }
	            } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_N) {
	            	novo();
	            } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_O) {
	            	abrir();
	            } else if (evt.isControlDown() && evt.getKeyCode() == KeyEvent.VK_S) {
	            	salvar();
	            } else if (evt.getKeyCode() == KeyEvent.VK_F7) {
	            	compilar();
	            } else if (evt.getKeyCode() == KeyEvent.VK_F1) {
	            	equipe();
	            }
	        }
		});
		taEditor.setBorder(new NumberedBorder());
		JScrollPane scrollEditor = new JScrollPane(taEditor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spAreaCriacao.setLeftComponent(scrollEditor);
		
		taAreaMensagens = new JTextArea();
		taAreaMensagens.setEditable(false);
		JScrollPane scrollAreaMensagens = new JScrollPane(taAreaMensagens, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		spAreaCriacao.setRightComponent(scrollAreaMensagens);
		spAreaCriacao.setResizeWeight(0.8);
	}
	
	private void novo() {
		taEditor.selectAll();
		taEditor.replaceSelection("");
	
		taAreaMensagens.selectAll();
		taAreaMensagens.replaceSelection("");
		
		taEditor.requestFocus();
		
		lblStatus.setText("          ");
		
		caminhoArquivoSelecionado = "";
	}
	
	private void abrir() {
		JFileChooser abrir = new JFileChooser();
		
		abrir.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		int resultado = abrir.showOpenDialog(btnAbrir);
		
		
		if (resultado == JFileChooser.APPROVE_OPTION) {
			File ArquivoSelecionado = abrir.getSelectedFile();
			try {
				
			    FileReader fr = new FileReader(ArquivoSelecionado);
			    BufferedReader leitor = new BufferedReader(fr);
			    
			   
			    taEditor.read(leitor, "taEditor");
			    
			  
			    taAreaMensagens.selectAll();
				taAreaMensagens.replaceSelection("");
				
				int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String statusText = "Pasta: " + selectedFile.getParent() + " - Arquivo: " + selectedFile.getName();
                    lblStatus.setText(statusText);
                }
				lblStatus.setText(ArquivoSelecionado.getAbsolutePath().trim());
			
				caminhoArquivoSelecionado = ArquivoSelecionado.getAbsolutePath().trim();
			} catch(IOException io) {
				System.out.println(io);
			}
		}
	}
	
	private void salvar() {
		if (caminhoArquivoSelecionado != "") {
		
			File arquivo = new File(caminhoArquivoSelecionado);
			
			
			if (arquivo.isFile() && !arquivo.isDirectory()) {
				try {
				
					try (
					    BufferedReader leitor = new BufferedReader(new StringReader(taEditor.getText()));
			            PrintWriter escritor = new PrintWriter(new FileWriter(arquivo));
					) {
						leitor.lines().forEach(line -> escritor.println(line));
					}
				} catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
			}
		} else {
			
			JFileChooser salvarComo = new JFileChooser();
		
			FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
			salvarComo.setFileFilter(filter);
			
			
			salvarComo.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
			int retorno = salvarComo.showSaveDialog(btnSalvar);
			
			
			if (retorno == JFileChooser.APPROVE_OPTION) {
				File arquivoSalvo = salvarComo.getSelectedFile();
				if (arquivoSalvo == null)
					return;						
				
				
				if (!arquivoSalvo.getName().toLowerCase().endsWith(".txt" )) 
					arquivoSalvo = new File(arquivoSalvo.getParentFile(), arquivoSalvo.getName() + ".txt");
				
				try {
					
					try (
					    BufferedReader leitor = new BufferedReader(new StringReader(taEditor.getText()));
			            PrintWriter escritor = new PrintWriter(new FileWriter(arquivoSalvo));
					) {
						leitor.lines().forEach(line -> escritor.println(line));
					}
				} catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
				
				caminhoArquivoSelecionado = arquivoSalvo.getAbsolutePath();
				lblStatus.setText(caminhoArquivoSelecionado);
			}
		}
		
	    taAreaMensagens.selectAll();
		taAreaMensagens.replaceSelection("");
	}
	
	public boolean gera_codigo_objeto(Queue<String> codigoObjeto) {
		try {
			File f = new File(caminhoArquivoSelecionado);
			
			if (f.exists()) {
		        FileWriter arq = new FileWriter(f.getParent() + "\\" + mudaExtensaoArquivo(f, ".il"));
		        PrintWriter gravarArq = new PrintWriter(arq);
		
		        while (codigoObjeto.peek() != null) {
			        gravarArq.printf("%s", codigoObjeto.poll());
		        }
		
			    arq.close();
			    return true;
			} else {
				if (JOptionPane.showConfirmDialog(null, "Salvar programa para compilar, deseja salvar agora?", "WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				    salvar();
				    return gera_codigo_objeto(codigoObjeto);
				} else {
					return false;
				}	
			}
		} catch (IOException e) {
			return false;
		}
	}
	
	public String mudaExtensaoArquivo(File arq, String ext) {
		int i = arq.getName().lastIndexOf('.');
		String nome = arq.getName().substring(0,i);
		return new File(arq.getParent(), nome + ext).getName();
	}
	
	private void compilar() {
		taAreaMensagens.selectAll();
		taAreaMensagens.replaceSelection("");
		String textoEditor = taEditor.getText().replaceAll("\r", "");
		Lexico lexico = new Lexico();
		StringBuilder saida;
		Sintatico sintatico = new Sintatico();
		Semantico semantico = new Semantico();
		//...
		lexico.setInput(textoEditor);
		String tokenErro = "";
		//...
		try {
			sintatico.parse(lexico, semantico);    // tradução dirigida pela sintaxe
		}
		// mensagem: programa compilado com sucesso - área reservada para mensagens

		catch (LexicalError e) {
			taAreaMensagens.selectAll();
			taAreaMensagens.replaceSelection("");
			if (e.getMessage() == ScannerConstants.SCANNER_ERROR[0]
					|| e.getMessage() == ScannerConstants.SCANNER_ERROR[17]
					|| e.getMessage() == ScannerConstants.SCANNER_ERROR[33]) {
				for (int i = e.getPosition(); i < textoEditor.length(); i++) {
					if ((textoEditor.charAt(i) == ' ' || textoEditor.charAt(i) == '\n') && i != 0)
						break;
					tokenErro += textoEditor.charAt(i);
				}

				taAreaMensagens.append("Erro na linha " + e.getPosition() + " - " + tokenErro + " " + e.getMessage());
			} else {
				taAreaMensagens.append("Erro na linha " + e.getPosition() + " - " + e.getMessage());
			}
		} catch (SyntaticError e) {

			taAreaMensagens.append("Erro na linha " + e.getPosition() + " - " + tokenErro + " " + e.getMessage());
		} catch (SemanticError e) {
			//Trata erros semânticos
		}

	}

	private void equipe() {
		taAreaMensagens.selectAll();
		taAreaMensagens.replaceSelection("");
		taAreaMensagens.append("Equipe.: Marcella Coelho Brito Nunes\n"
                             + "                Leonardo André Lange\n"
                             + "                Felipe Melio Tomelin");
	}
}
