package queue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import queue.sqs_send;
public class UI_send {
	public static void main(String[] args) {
	//����JFrame
	JFrame frame = new JFrame("Send_Message");
    frame.setSize(850, 250);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//����
	JPanel panel = new JPanel(); 
	frame.add(panel);
	panel.setLayout(null);
	
	//���͵�label
	JLabel label = new JLabel("Ҫ���͵���Ϣ");
	label.setFont(new Font("����",Font.BOLD,20));
	label.setBounds(20,20,200,20);
	panel.add(label);
	
	//���������ı���
	JTextField jt = new JTextField();
	jt.setFont(new Font("����",Font.BOLD,20));
	jt.setBounds(20,50,780,50);
    jt.setVisible(true);
	panel.add(jt);
	
	//���Ͱ�ť����
	JButton button = new JButton("����");
	button.setFont(new Font("����",Font.BOLD,20));
	button.setBounds(700,110,90,50);
	panel.add(button);
	
	//���߳�
	sqs_send demo=new sqs_send();
	demo.init();
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Thread(new Runnable() {
				public void run() {
					demo.msg = jt.getText();
					demo.run();
				}		
			}).start();
		}
});
	
	frame.setVisible(true);//�������
	
	}
}
