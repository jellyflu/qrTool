package com.tingcream.qrTool.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.commons.io.FileUtils;

import com.tingcream.qrTool.qrcode.QRCodeUtil;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QRJFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private  JPanel   panel1 ;//tab中面板1  面板2
	
	private  JLabel label1 ;//qr码 图形显示框
	private JLabel label2;
	private JLabel lblQr;
	private JButton btn1;//panel1 生成
	private JButton btn3;//panel1 清空
	
	
	private JTextArea textArea1;//原文 文本域
	
	
	private String recentDir;//最近访问的目录
	
	private JButton btn4;// 打开img文件
	private JButton btn5;//panel1 保存
	
 
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QRJFrame frame = new QRJFrame();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);//主窗体居中
					
					 
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public QRJFrame() throws IOException {
		setResizable(false);
		setFont(new Font("微软雅黑", Font.PLAIN, 18));
		setTitle("QR生成器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 464, 636);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		 panel1 = new JPanel();
		tabbedPane.addTab("QR生成", null, panel1, null);
		
		 label1 = new JLabel("");
		label1.setVerticalAlignment(SwingConstants.TOP);
		label1.setBackground(Color.WHITE);
		
		setDefaultQrImg();
		
		label1.setHorizontalAlignment(SwingConstants.LEFT);
		label1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		label2 = new JLabel("原文内容:");
		label2.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		
		lblQr = new JLabel("QR码  :");
		lblQr.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		btn1 = new JButton("生成↓");
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				generateQRImg(e);
			}
		});
		btn1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		btn3 = new JButton("清空");
		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea1.setText("");
				setDefaultQrImg();
			}
		});
		btn3.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		btn5 = new JButton("保存");
		btn5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveQrImg(e);
			}
		});
		btn5.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		
		btn4 = new JButton("选择");
		btn4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openQrImg(e);
			}
		});
		btn4.setFont(new Font("微软雅黑", Font.PLAIN, 18));
 
		GroupLayout	 gl_panel1 = new GroupLayout(panel1);
		gl_panel1.setHorizontalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel1.createSequentialGroup()
							.addGap(19)
							.addGroup(gl_panel1.createParallelGroup(Alignment.TRAILING)
								.addComponent(label2)
								.addComponent(lblQr, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel1.createSequentialGroup()
							.addContainerGap()
							.addComponent(btn4, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel1.createSequentialGroup()
							.addContainerGap()
							.addComponent(btn5, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel1.createSequentialGroup()
							.addComponent(btn1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn3, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
						.addComponent(label1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(scrollPane))
					.addContainerGap(39, Short.MAX_VALUE))
		);
		gl_panel1.setVerticalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING)
						.addComponent(label2)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn1)
						.addComponent(btn3, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel1.createSequentialGroup()
							.addComponent(lblQr, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn5, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addGap(15)
							.addComponent(btn4, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addComponent(label1))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		
		 textArea1 = new JTextArea();
		textArea1.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		scrollPane.setViewportView(textArea1);
		panel1.setLayout(gl_panel1);
		
	}
  
	//显示默认qr图形  空白图片
	private  void setDefaultQrImg() {
		label1.setIcon(new ImageIcon(QRJFrame.class.getResource("/com/tingcream/qrTool/img/blank_300_300.png")));
	}
	
	/**
	 * 保存qr图形 到指定文件中
	 * @author jelly
	 * @param e
	 */
	  void saveQrImg(ActionEvent e) {
		  String text = textArea1.getText();
			 if(text==null||text.trim().equals("")) {
				 JOptionPane.showMessageDialog(null, "原文内容不能为空!"); 
				 return ;
			 }
			 
			 FileOutputStream  output=null;
			 try {
				
				 JFileChooser wjsave=null;
	             if(recentDir!=null&&recentDir.trim().length()>0){
	                 wjsave=new JFileChooser(recentDir);
	             }else{
	                 wjsave=new JFileChooser();
	             }
	          
	             wjsave.setDialogType(JFileChooser.SAVE_DIALOG);
	             wjsave.setVisible(true);
	             wjsave.setDialogTitle("保存QR码到文件");
	             
		         int value= wjsave.showSaveDialog(null);
		         if(value==JFileChooser.APPROVE_OPTION){//用户点击了保存
		        	 
		        	 File savefile= wjsave.getSelectedFile();// 保存的文件
		        	 recentDir= savefile.getParent();//将文件目录保存到成员变量
	     
	                   output=new FileOutputStream(savefile); 
	                   QRCodeUtil.encode(text, null, output, true);
		        	 
		         }
	            
			} catch (Exception e2) {
				 e2.printStackTrace();
			}finally {
				if(output!=null) {
					try {
						output.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		
	}
   
	  /**
	   * 从本地选择(打开)qr图片文件
	   * @author jelly
	   * @param e
	   */
	  void openQrImg(ActionEvent e) {
		  try {
			  JFileChooser wjopen=null;
	         if(recentDir!=null&&recentDir.trim().length()>0){
	             wjopen=new JFileChooser(recentDir);
	         }else{
	             wjopen=new JFileChooser();
	         }
	         wjopen.setDialogTitle("打开QR码文件");
	         wjopen.setDialogType(JFileChooser.OPEN_DIALOG);
	         wjopen.setVisible(true);
	        wjopen.addChoosableFileFilter(new FileNameExtensionFilter("PNG文件(png)","png"));
	        int value=wjopen.showOpenDialog(null);
	        
	        if(value==JFileChooser.APPROVE_OPTION){//用户点击了打开
	        	
	        	File openfile=  wjopen.getSelectedFile();
	      
	        	byte[] bytes =FileUtils.readFileToByteArray(openfile);
	        	
	        	label1.setIcon(new ImageIcon(bytes));
	        	
	        	
	        	
	        	 String text =QRCodeUtil.decode(new FileInputStream(openfile));
	        	 
	        	 textArea1.setText(text);
	 
         }
	        
	     
	         
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	  }
 
	  
	/**
	 * 生成qr图形 并在label1中显示
	 * @author jelly
	 * @param e
	 */
	 void generateQRImg(ActionEvent e) {
		 String text = textArea1.getText();
		 if(text==null||text.trim().equals("")) {
			 JOptionPane.showMessageDialog(null, "原文内容不能为空!"); 
			 return ;
		 }
		 ByteArrayOutputStream output=new ByteArrayOutputStream();
		 try {
		 	QRCodeUtil.encode(text, null, output, true);
		 } catch (Exception e1) {
		 	e1.printStackTrace();
		 	JOptionPane.showMessageDialog(null, e1.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
		 }
		 byte[] bytes =output.toByteArray();

		 label1.setIcon(new ImageIcon(bytes));
	}
 

}
