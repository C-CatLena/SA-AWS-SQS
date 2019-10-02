package queue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.amazonaws.services.sqs.model.Message;

import queue.sqs_receive;

public class UI_receive {
	static List<Message> m = null;
	static String queueUrl = null;
	
	public static void main(String[] args) {
	//����JFrame
	JFrame frame = new JFrame("Receive_Message");
    frame.setSize(850, 500);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	//����
	JPanel panel = new JPanel(); 
	frame.add(panel);
	panel.setLayout(null);
	
	//���յ�label
	JLabel label = new JLabel("���յ�����Ϣ");
	label.setFont(new Font("����",Font.BOLD,20));
	label.setBounds(20,20,200,20);
	panel.add(label);
	
	//�����õ��ı���
	JTextArea jt = new JTextArea();
	jt.setFont(new Font("����",Font.BOLD,20));
	jt.setVisible(true);
    JScrollPane jsp = new JScrollPane(jt);//newһ��������
	jsp.setBounds(20, 50,780, 300);
	jt.setVisible(true);
	panel.add(jsp); //�����ı���(������������󣬼���ľͲ������ı�����)
	jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	
	//��հ�ť
	JButton button_clear = new JButton("���");
	button_clear.setFont(new Font("����",Font.BOLD,20));
	button_clear.setBounds(30,370,90,50);
	panel.add(button_clear);
	button_clear.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			jt.setText("");
		}
});
	
	//���Ͱ�ť����
	JButton button = new JButton("����");
	button.setFont(new Font("����",Font.BOLD,20));
	button.setBounds(700,370,90,50);
	panel.add(button);
	//���߳�
	sqs_receive demo=new sqs_receive();
	demo.init();
	button.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			new Thread(new Runnable() {
				public void run() {
					m = demo.run();
					//��ӡ��Ϣ
					for(Message re:m) {
						jt.append("Message: " + re.getBody()+'\n');
					}
					//ɾ����Ϣ
			        for (Message msg : m) {
		        	demo.sqs.deleteMessage(demo.queueUrl, msg.getReceiptHandle());
				}
				}
			}).start();
		}
});


	
	frame.setVisible(true);//�������
	
	}
}
