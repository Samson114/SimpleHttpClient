package ONE;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
class Demo
{
	
//	http://www.myexception.cn/program/669436.html
	
  public static void main(String[] args) throws Exception
  {
    new MyWindow();
  }

  public static void sop(Object obj) //��ӡ
  {
    System.out.println(obj);
  }
}

class MyWindow
{
  private Frame frm;
  private TextField textd;
  private Button but;
  private TextArea texta;
  private Dialog dlg;  //�Ի���Ӧ������Ҫ��ʱ���ٳ�ʼ��
  private Label lab;
  private Button okButn;
  
  MyWindow() //���췽��
  {
    init();  //��ʼ��
  }
  
  public void init() //��ʼ��
  {
    frm= new Frame("window");        //��������
    frm.setBounds(300,100,600,500);  //����300,����100,����600,����500
    frm.setLayout(new FlowLayout()); //����ģʽ:�߽�ģʽ
    
    textd = new TextField(60);       //���������ı���
    but= new Button("ת��");         //������ť
    texta = new TextArea(27,82);     //�����ı�����
    
    dlg = new Dialog(frm,"��ʾ:�Ի���!",true); //�����Ի���
    lab = new Label("����ȷ��ַ��·��!");      //������ǩ.�ɺ��������ı�
    okButn = new Button("ȷ��!!!");            //���������Ի����е�"ȷ��"��ť
    
    dlg.setBounds(400,200,300,100);            //���öԻ�������
    dlg.setLayout(new FlowLayout());           //���öԻ������Ϊ�߽�ģʽ
      
    dlg.add(lab);                              //�ڶԻ����м��������ǩ�Ͱ�ť
    dlg.add(okButn);
    
    frm.add(textd);                            //�ڴ����м������
    frm.add(but);
    frm.add(texta);

    myEvent();                                 //��ʼ���¼�������
    frm.setVisible(true);
  }

  private void showSerData() throws Exception
  { 
    texta.setText("");                //�������ı����
    String urlStr = textd.getText();  //��ȡ��ַ���е��ַ���
    if(urlStr.equals(""))             //�����ַ��Ϊ��,������ʾ��
    {
      texta.setText("��������ַ");  
      String worningInfo = "��������ַ";
      lab.setText(worningInfo);
      dlg.setVisible(true);           //��ʾ��ʾ��
      return;                                                   
    }  
    
    if(!urlStr.endsWith("/"))
    {
      urlStr=urlStr+"/";              //�����ַû��б�ܾ����
    }

    int index1=urlStr.indexOf("//")+2;     //��ָ���ַ�����ʼ����,��2�����ip��ʼ��
    int index2=urlStr.indexOf("/",index1); //��index1��ʼȡ,Ҳ���Ǵ�host��ʼ
    String str= urlStr.substring(index1,index2);// www.tom.com:80
    sop("str: "+str);
    int port=0;
    String host=null;
    String path=null;
    if(!str.contains(":"))            //���ֻдhost,δд�˿ں�
    {
      host=str;
      port=80;
      path="";
    }
    else                              //���ָ��host��ַ�Ͷ˿�,���߻�����·��
    {
      String hostPort[] =str.split(":"); //�ָ��host��ַ�Ͷ˿ں�
      host=hostPort[0];
      port =Integer.parseInt(hostPort[1]);
      path = urlStr.substring(index2+1); //·������������ߵ�б��,  /C:/webpages   
    }
    sop(host+port+path);
    client(host,port,path); //��ַ�������,׼������
  }
  
  public void client(String host,int port,String path) throws Exception
  {
    Socket sock = new Socket(host,port); //׼�������˷������������
    PrintWriter priOut= new PrintWriter(sock.getOutputStream(),true);
    priOut.println("GET /"+path+" HTTP/1.1");
    priOut.println("Accept: */*");
    priOut.println("Accept-Language: zh-cn");
    priOut.println("Accept-Encoding: gzip, deflate");
    priOut.println("Host: ������д");
    priOut.println("Connection: Keep-Alive");
    priOut.println(); //����
    
    sop("�������������>>>>>>>>>>>>>>>>>>>>>>>>>>");
    sop("׼�����ܷ��������......................");
    
    //���շ��������ص���Ϣ
    BufferedReader bufr = new BufferedReader(new InputStreamReader(sock.getInputStream())); 
    BufferedWriter bufw = new BufferedWriter(new FileWriter("d:\\rrr.txt"));//д��Ӳ����
    String lineIn=null;
    while(true)
    {
      lineIn=bufr.readLine();
      if(lineIn!=null)
      {
        bufw.write(lineIn);//д��rrr.txt��
        bufw.newLine();
        texta.append(lineIn+System.getProperty("line.separator")); //UI�����е���ʾ
        sop(lineIn);//����̨Ҳ��ӡ��
      }
      else
      {
        break;
      }
    }
    bufw.flush();
    bufw.close();
    sock.close();
  }
  
  private void myEvent()      //Ϊ�����¼�Դ�������¼�������
  {
    okButn.addActionListener  //���ť��¼�������
    (
      new ActionListener()
      {
        public void actionPerformed(ActionEvent e)
        { 
          dlg.setVisible(false); //���ضԻ���
          textd.setText("");
          texta.setText("");
        }

      }
    );
    
    dlg.addWindowListener//.�Ի����������
    (
      new WindowAdapter()
      {
        public void windowClosing(WindowEvent e) //����Ի���Ĳ���
        {
          
          dlg.setVisible(false);  //���ضԻ���
          textd.setText("");
          texta.setText("");
        }
      }
    );
  
    but.addActionListener         //"ת��" ��ť�������
    (
      new ActionListener()
      {      
        public void actionPerformed(ActionEvent e)  //ֻ����һ������Ҫ����
        { 
          try
          {
          showSerData();  
          }
          catch(Exception e2)
          {}
        }
      }  
    );
       
    frm.addWindowListener         //���������
    (
      new WindowAdapter()
      {
        public void windowClosing(WindowEvent e)
        {
          System.exit(0);
        }
      }
    );
    
    //������ǲ����������"ת��"��ť,��ֱ�Ӱ��س�����ɲ���?
    //ʲô���¼�Դ?   ��textd�齨  ,��Ϊ��ʱ���㻹��textd�����ı�����
    textd.addKeyListener
    (
      new KeyAdapter()
      {
        public void keyPressed(KeyEvent e) 
        {
          if(e.getKeyCode()==KeyEvent.VK_ENTER) //���س�
          {
            try
            {
              showSerData();
            }
            catch(Exception e1)
            {}
          }
        }
      }
    );

  }
  
  public static void sop(Object obj) //��ӡ
  {
    System.out.println(obj);
  }
}