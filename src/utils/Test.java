package utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test extends JDBCBean{


	/**
	 * @param args
	 */
	public static void main(String[] args) {
			try {
				Runtime rt = Runtime.getRuntime();
	
						// 调用 调用mysql的安装目录的命令
				Process child = rt.exec("E:\\mysql\\bin\\mysqldump -h localhost -uroot -proot qhd-ant");
				// 设置导出编码为utf-8。这里必须是utf-8
				// 把进程执行中的控制台输出信息写入.sql文件，即生成了备份文件。注：如果不对控制台信息进行读出，则会导致进程堵塞无法运行
				InputStream in = child.getInputStream();// 控制台的输出信息作为输入流
				InputStreamReader xx = new InputStreamReader(in, "utf-8");
				// 设置输出流编码为utf-8。这里必须是utf-8，否则从流中读入的是乱码
				
				String inStr;
				StringBuffer sb = new StringBuffer("");
				String outStr;
				// 组合控制台输出信息字符串
				BufferedReader br = new BufferedReader(xx);
				while ((inStr = br.readLine()) != null) {
					sb.append(inStr + "\r\n");
				}
				outStr = sb.toString();
				
				// 要用来做导入用的sql目标文件：
				SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
				Date date=new Date();
				String dateFileName = dateFormater.format(date);
				FileOutputStream fout = new FileOutputStream("G:\\douban\\"+dateFileName+".sql");
				OutputStreamWriter writer = new OutputStreamWriter(fout, "utf-8");
				writer.write(outStr);
				writer.flush();
				in.close();
				xx.close();
				br.close();
				writer.close();
				fout.close();
				
				System.out.println("");
				
				} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	


}
