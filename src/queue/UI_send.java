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
	//创建JFrame
	JFrame frame = new JFrame("Send_Message");
    frame.setSize(850, 250);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//画布
	JPanel panel = new JPanel(); 
	frame.add(panel);
	panel.setLayout(null);
	
	//发送的label
	JLabel label = new JLabel("要发送的消息");
	label.setFont(new Font("黑体",Font.BOLD,20));
	label.setBounds(20,20,200,20);
	panel.add(label);
	
	//发送所在文本框
	JTextField jt = new JTextField();
	jt.setFont(new Font("黑体",Font.BOLD,20));
	jt.setBounds(20,50,780,50);
    jt.setVisible(true);
	panel.add(jt);
	
	//发送按钮设置
	JButton button = new JButton("发送");
	button.setFont(new Font("黑体",Font.BOLD,20));
	button.setBounds(700,110,90,50);
	panel.add(button);
	
	//开线程
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
	
	frame.setVisible(true);//放在最后
	
	}
}
