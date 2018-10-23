package com.activitys.activityAction;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.activitys.activityService.ActivityServiceImpl;
import com.bean.Activitys;
import com.bean.Member;
import com.bean.MemberActivity;
import com.member.memberService.MemberServiceImpl;

@Controller
public class ActivityAction {
	@Resource MemberServiceImpl memberservice;
	@Resource ActivityServiceImpl activityservice;
	
	@RequestMapping(value = "/saveActivityAndMember.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Boolean saveActivityAndMember(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("theme") String theme, @RequestParam("time") String time,@RequestParam("activity_site") String activity_site,
			@RequestParam("remark") String remark) {
		Activitys activity = new Activitys();
		activity.setTheme(theme);
		activity.setTime(time);
		activity.setSite(activity_site);
		activity.setRemark(remark);
		
		activityservice.setActivity(activity);
		
		return true;
	}
	
	@RequestMapping(value = "/saveAttendActivity.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public void saveAttendActivity(HttpServletRequest request, HttpServletResponse response, @RequestParam("memberList") String memberList) {
		
		List<MemberActivity> list = new ArrayList<MemberActivity>();
		JSONArray jsonArray = JSONArray.fromObject(memberList);
		list = (List)jsonArray.toCollection(jsonArray, MemberActivity.class);
		
		for(int i =0;i<list.size();i++) {
			activityservice.setMemberAndActivity(list.get(i));
			int count = activityservice.getActivityCountByMember(list.get(i).getIdentity());
			memberservice.updateMemberActivityCount(list.get(i).getIdentity(), count);
		}
		try {
			response.sendRedirect("pages/index.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getAllActivityList.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public List<Activitys> getAllActivityList(HttpServletRequest request, HttpServletResponse response) {
		List<Activitys> list = new ArrayList<Activitys>();
		list = activityservice.getActivitysList();
		
		for(int i =0;i<list.size();i++) {
			int memberCount = activityservice.getMemberCountByActivityTheme(list.get(i).getTheme());
			list.get(i).setMemberCount(memberCount);
		}
		return list;
	}
	
	@RequestMapping(value = "/gotoEditActivity.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Boolean gotoEditActivity(HttpServletRequest request, HttpServletResponse response, @RequestParam("theme") String theme) {
		HttpSession session = request.getSession();
		session.setAttribute("theme", theme);
		return true;
	}
	
	@RequestMapping(value = "/getActivityInfo.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Activitys getActivityInfo(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String theme = (String)session.getAttribute("theme");
		Activitys activity = new Activitys();
		activity = activityservice.getActivityByTheme(theme);
		return activity;
	}
	
	@RequestMapping(value = "/getActivityMemberInfo.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public List<Member> getActivityMemberInfo(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String theme = (String)session.getAttribute("theme");
		List<Member> list = new ArrayList<Member>();
		list = activityservice.getMemberByActivity(theme);
		return list;
	}
	
	@RequestMapping(value = "/getPartActivityList.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public List<Activitys> getPartActivityList(HttpServletRequest request, HttpServletResponse response) {
		List<Activitys> list = new ArrayList<Activitys>();
		list = activityservice.getActivitysPartList();
		return list;
	}
	
	@RequestMapping(value = "/updateActivityAndMember.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Boolean updateActivityAndMember(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("theme") String theme, @RequestParam("time") String time,@RequestParam("activity_site") String activity_site,
			@RequestParam("remark") String remark,@RequestParam("old_theme") String old_theme) {
		Activitys activity = new Activitys();
		activity.setTheme(theme);
		activity.setTime(time);
		activity.setSite(activity_site);
		activity.setRemark(remark);
		
		activityservice.updateActivity(activity, old_theme);
		
		return true;
	}
	
	@RequestMapping(value = "/updateAttendActivity.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Boolean updateAttendActivity(HttpServletRequest request, HttpServletResponse response, @RequestParam("memberList") String memberList,
			@RequestParam("old_theme") String old_theme) {
		
		List<MemberActivity> list = new ArrayList<MemberActivity>();
		JSONArray jsonArray = JSONArray.fromObject(memberList);
		list = (List)jsonArray.toCollection(jsonArray, MemberActivity.class);
		
		for(int i =0;i<list.size();i++) {
			activityservice.updateAttendActivityByTheme(list.get(i), old_theme);
			int count = activityservice.getActivityCountByMember(list.get(i).getIdentity());
			memberservice.updateMemberActivityCount(list.get(i).getIdentity(), count);
		}
		try {
			response.sendRedirect("pages/index.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	@RequestMapping(value = "/delectAttendActivity.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public Boolean delectAttendActivity(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("identityList") String identityList, @RequestParam("old_theme") String old_theme) {
		String[] identityStr = identityList.split(",");
		for(int i=0;i<identityStr.length;i++) {
			activityservice.delectAttendActivityByIdentity(identityStr[i],old_theme);
			int count = activityservice.getActivityCountByMember(identityStr[i]);
			memberservice.updateMemberActivityCount(identityStr[i], count);
		}
		
		return true;
	}
	
	@RequestMapping(value = "/downloadActivityFile.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	public void downloadActivityFile(HttpServletRequest request, HttpServletResponse response,@RequestParam("theme") String theme) throws IOException {
		Activitys activity = activityservice.getActivityByTheme(theme);
		List<Member> list = activityservice.getMemberByActivity(theme);
		WritableWorkbook book = null;
		String filePath=request.getSession().getServletContext().getRealPath("/");;
	        try {
	            // 打开文件
	            book = Workbook.createWorkbook(new File(filePath+"WEB-INF\\classes\\excelFile\\"+theme+".xls"));
	            // 生成名为"学生"的工作表，参数0表示这是第一页
	            WritableSheet sheet = book.createSheet(theme, 0);
	            // 指定单元格位置是第一列第一行(0, 0)以及单元格内容为张三
	            //Label label = new Label(0, 0, activity.getTheme());
	            // 将定义好的单元格添加到工作表中
	            sheet.addCell(new Label(0, 0, activity.getTheme()));
	            sheet.addCell(new Label(1, 0, activity.getTime()));
	            sheet.addCell(new Label(2, 0, activity.getSite()));
	            sheet.addCell(new Label(3, 0, activity.getRemark()));
	            
	            sheet.addCell(new Label(0, 1, "序号"));
	            sheet.addCell(new Label(1, 1, "名字"));
	            sheet.addCell(new Label(2, 1, "性别"));
	            sheet.addCell(new Label(3, 1, "电话"));
	            sheet.addCell(new Label(4, 1, "身份证号"));
	            sheet.addCell(new Label(5, 1, "上车地点"));
	            
	            for(int i =0;i<list.size();i++) {
	            	sheet.addCell(new Label(0, i+2, String.valueOf(i+1)));
		            sheet.addCell(new Label(1, i+2, list.get(i).getName()));
		            sheet.addCell(new Label(2, i+2, list.get(i).getSex()));
		            sheet.addCell(new Label(3, i+2, list.get(i).getPhone()));
		            sheet.addCell(new Label(4, i+2, list.get(i).getIdentity()));
		            sheet.addCell(new Label(5, i+2, list.get(i).getSite()));
	            }
	            // 保存数字的单元格必须使用Number的完整包路径
//	            jxl.write.Number number = new jxl.write.Number(1, 0, 30);
//	            sheet.addCell(number);
	            // 写入数据并关闭文件
	            book.write();
	        } catch (Exception e) {
	            System.out.println(e);
	        }finally{
	            if(book!=null){
	                try {
	                    book.close();
	                } catch (Exception e) {
	                    e.printStackTrace();
	                } 
	            }
	        }
	        File file = new File(filePath+"WEB-INF\\classes\\excelFile\\"+theme+".xls");
			if(file.exists()) {
				response.setCharacterEncoding("utf-8");
				response.setContentType("multipart/form-data");
				response.setContentType("/application/vnd.ms-excel");
				response.setHeader("Content-Disposition", "attachment;filename="+new String(file.getName().getBytes("GBK"),"ISO8859-1"));
				response.setHeader("Content-type","application-download");
				byte[] buffer = new byte[1024];
				FileInputStream fis = null;
				BufferedInputStream bis = null;
				try {
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					OutputStream os = response.getOutputStream();
					int i = bis.read(buffer);
					while(i != -1) {
						os.write(buffer, 0, i);
						i = bis.read(buffer);
					}
					os.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}finally {
					if(bis != null) {
						bis.close();
					}
					if(fis != null) {
						fis.close();
					}
					
				}
				
			}
	}
	
	@RequestMapping(value = "/deleteActivity.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Boolean deleteActivity(HttpServletRequest request, HttpServletResponse response,@RequestParam("theme") String theme) {
		List<Member> list = new ArrayList<Member>();
		list = memberservice.getMemberByTheme(theme);
		
		activityservice.deleteActivity(theme);
		System.out.println("成功删除主题为:"+theme);
		
		for(int i = 0;i<list.size();i++) {
			int count = activityservice.getActivityCountByMember(list.get(i).getIdentity());
			memberservice.updateMemberActivityCount(list.get(i).getIdentity(), count);
		}
		return true;
	}
	
	@RequestMapping(value = "/isActivity.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Boolean isActivity(HttpServletRequest request, HttpServletResponse response,@RequestParam("theme") String theme) {
		Activitys activity = activityservice.getActivityByTheme(theme);
		if(activity.getTheme() != null && activity.getTheme() != "") {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * <p>Title: deleteAttendActivity</p>
	* <p>Description: 将活动用户信息删除</p>
	* <p>Company: </p> 
	* <p>return type: Boolean</p>
	* <p>Parameter: </p>
	* @author ZhengbinChen
	* @date 2017-7-3
	 */
	@RequestMapping(value = "/deleteAttendActivity.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	public Boolean deleteAttendActivity(HttpServletRequest request, HttpServletResponse response,@RequestParam("theme") String theme) {
		List<Member> list = new ArrayList<Member>();
		list = memberservice.getMemberByTheme(theme);
		
		activityservice.deleteAttendActivity(theme);
		System.out.println("删除主题为:"+theme +"的用户参加活动信息");
		
		for(int i = 0;i<list.size();i++) {
			int count = activityservice.getActivityCountByMember(list.get(i).getIdentity());
			memberservice.updateMemberActivityCount(list.get(i).getIdentity(), count);
		}
		return true;
	}
	
}
