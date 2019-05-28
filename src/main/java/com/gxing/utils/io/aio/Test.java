package com.gxing.utils.io.aio;

import com.gxing.utils.io.aio.client.Client;
import com.gxing.utils.io.aio.server.Server;

import java.util.Scanner;
/**
 * ���Է���
 * @author yangtao__anxpp.com
 * @version 1.0
 */
public class Test {
	//����������
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception{
		//���з�����
		Server.start();
		//����ͻ������ڷ���������ǰִ�д���
		Thread.sleep(100);
		//���пͻ��� 
		Client.start();
		System.out.println("������������Ϣ��");
		Scanner scanner = new Scanner(System.in);
		while(Client.sendMsg(scanner.nextLine()));
	}
}