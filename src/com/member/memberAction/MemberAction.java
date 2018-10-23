package com.member.memberAction;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import utils.BackupsDatabase;

import com.activitys.activityService.ActivityServiceImpl;
import com.bean.Member;
import com.bean.MemberActivity;
import com.member.memberService.MemberServiceImpl;

@Controller
public class MemberAction {
	@Resource MemberServiceImpl memberservice;
	@Resource ActivityServiceImpl activityservice;
	
	@RequestMapping("/uploadMemberFile.do")
	@ResponseBody
	/**
	 * 会员信息Excel表格上传
	 * @param request
	 * @param response
	 * @param file
	 * return member_import.html
	 */
	public void uploadMemberFile(HttpServletRequest request, HttpServletResponse response, @RequestParam("member_file") MultipartFile file) {
		try {
			Workbook workbook = Workbook.getWorkbook(file.getInputStream());
			List<Member> list = new ArrayList<Member>();
			int sheetIndex = workbook.getNumberOfSheets();
			for(int i=0;i<sheetIndex;i++) {
				Sheet sheet = workbook.getSheet(i);
				System.out.println(sheet.getRows()+"sgdhf"+sheet.getColumns());
				for(int j=1;j<sheet.getRows();j++) {
					Member member = new Member();
					MemberActivity memberActivity = new MemberActivity();
					for(int k=1;k<sheet.getColumns();k++) {
						Cell cell = sheet.getCell(k, j);
						String cellContent = cell.getContents().trim();
						if(k == 1) {
							member.setName(cellContent);
						} else if(k == 2) {
							member.setSex(cellContent);
						} else if(k == 4) {
							member.setPhone(cellContent);
						} else if(k == 3) {
							if(cellContent.length() >= 18) {
								member.setIdentity(cellContent.substring(0, 18));
							}else {
								member.setIdentity(cellContent);
							}
						} else if(k == 5) {
							member.setSite(cellContent);
						} else if(k == 6) {
							memberActivity.setTheme(cellContent);
						}

					}
					memberActivity.setIdentity(member.getIdentity());
					
					String identity = member.getIdentity();
					System.out.println("test:"+identity+"name:"+member.getName());
					if(identity == null && member.getName() == "") {
						member = null;
						memberActivity = null;
						break;
					}else if ((identity == null || identity == "" || identity.equals("")) && (member.getName() == null || member.getName() == "" || member.getName().equals(""))) {
						member = null;
						memberActivity = null;
						break;
					}else if(identity == null || identity == "" || identity.equals("")) {
						member = null;
						memberActivity = null;
						continue;
					}
					activityservice.setMemberAndActivity(memberActivity);
					int total_num = activityservice.getActivityCountByMember(identity);
					member.setJoin_activity(String.valueOf(total_num));
					String year = identity.substring(6, 10);
					String month = identity.substring(10, 14);
					member.setYear(year);
					member.setMonth(month);
					//判断该会员是否已经存在数据库中
					if(!memberservice.memberIsInDatabase(member)) {
						list.add(member);
					} else {
						Member memberIndatabase = memberservice.getMemberByIdentity(member.getIdentity());
						if(memberIndatabase.getPhone() != "" && memberIndatabase.getPhone() != "null" && !memberIndatabase.getPhone().equals("")) {
							if(member.getPhone() == "" || member.getPhone() == "null" || member.getPhone().equals("")) {
								member.setPhone(memberIndatabase.getPhone());
							}
						}
						if(memberIndatabase.getSex() != "" && memberIndatabase.getSex() != "null" && !memberIndatabase.getSex().equals("")) {
							if(member.getSex() == "" || member.getSex() == "null" || member.getSex().equals("")) {
								member.setSex(memberIndatabase.getSex());
							}
						}
						if(memberIndatabase.getSite() != "" && memberIndatabase.getSite() != "null" && !memberIndatabase.getSite().equals("")) {
							if(member.getSite() == "" || member.getSite() == "null" || member.getSite().equals("")) {
								member.setSite(memberIndatabase.getSite());
							}
						}
						memberservice.updateMember(member, member.getIdentity());
						System.out.println(member.getName()+member.getIdentity()+"用户已经存在系统中！");
					}
					member = null;
					memberActivity = null;
				}
			}
			
			memberservice.setMemberList(list);
			
			response.sendRedirect("pages/member_import.html");
		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/getMemberList.do")
	@ResponseBody
	/**
	 * 获取会员信息列表
	 * @param reequest
	 * @param response
	 * @return list
	 */
	public List<Member> getMemberList(HttpServletRequest reequest,HttpServletResponse response) {
		List<Member> list = new ArrayList<Member>();
		list = memberservice.getAllMember();
		return list;
	}
	
	@RequestMapping(value = "/setMemberInfo.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	/**
	 * 添加一条会员信息
	 * @param request
	 * @param response
	 * @param name
	 * @param phone
	 * @param identity
	 * @param site
	 * @param activitys
	 */
	public void setMemberInfo(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("name") String name,@RequestParam("phone") String phone,
			@RequestParam("identity") String identity,@RequestParam("site") String site,
			@RequestParam("activitys") String activitys,@RequestParam("sex") String sex) {
		Member member = new Member();
		MemberActivity memberActivity = new MemberActivity();
		member.setName(name);
		member.setPhone(phone);
		member.setIdentity(identity);
		member.setSite(site);
		member.setSex(sex);
		
		if(activitys == "" || "".equals(activitys)) {
			
		} else {
			memberActivity.setIdentity(identity);
			memberActivity.setTheme(activitys);
			activityservice.setMemberAndActivity(memberActivity);
		}
		int total_num = activityservice.getActivityCountByMember(identity);
		
		member.setJoin_activity(String.valueOf(total_num));
		String year = identity.substring(6, 10);
		String month = identity.substring(10, 14);
		member.setYear(year);
		member.setMonth(month);
		if(!memberservice.memberIsInDatabase(member))
			memberservice.setMember(member);
		try {
			String path = request.getHeader("Referer");
			if(path.contains("member_import.html"))
				response.sendRedirect("pages/member_import.html");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@RequestMapping(value = "/updateMemberInfo.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	/**
	 * 更新一条会员信息
	 * @param request
	 * @param response
	 * @param name
	 * @param phone
	 * @param identity
	 * @param site
	 * @param activitys
	 */
	public void updateMemberInfo(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("name") String name,@RequestParam("phone") String phone,
			@RequestParam("identity") String identity,@RequestParam("identity_old") String identity_old,
			@RequestParam("site") String site,@RequestParam("sex") String sex) {
		
		Member member = new Member();
		MemberActivity memberActivity = new MemberActivity();
		member.setName(name);
		member.setPhone(phone);
		member.setIdentity(identity);
		member.setSite(site);
		member.setSex(sex);
		
//		memberActivity.setIdentity(identity);
//		memberActivity.setTheme(activitys);
//		activityservice.updateAttendActivity(memberActivity, identity_old);
		int total_num = activityservice.getActivityCountByMember(identity);
		
		member.setJoin_activity(String.valueOf(total_num));
		String year = identity.substring(6, 10);
		String month = identity.substring(10, 14);
		member.setYear(year);
		member.setMonth(month);
		memberservice.updateMember(member, identity_old);
		try {
			String path = request.getHeader("Referer");
			if(path.contains("member_import.html"))
				response.sendRedirect("pages/member_import.html");
			else
				response.sendRedirect("pages/search.html");
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/memberListOrderBy.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	/**
	 * 通过前台传得值进行会员信息排序
	 * @param request
	 * @param response
	 * @param way
	 * @param order
	 * @return list
	 */
	public List<Member> memberListOrderBy(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("way") String way,@RequestParam("order") String order) {
		String sql = "select * from member_info order by ";
		if(Integer.valueOf(way) == 0) {
			sql += "abs(year) ";
			if(Integer.valueOf(order) == 0) {
				sql += "desc";
			} else {
				sql += "asc";
			}
		} else if(Integer.valueOf(way) == 1) {
			sql += "abs(month) ";
			if(Integer.valueOf(order) == 0) {
				sql += "asc";
			} else {
				sql += "desc";
			}
		}
		else {
			sql += "abs(join_activity) ";
			if(Integer.valueOf(order) == 0) {
				sql += "asc";
			} else {
				sql += "desc";
			}
		}
		List<Member> list = new ArrayList<Member>();
		list = memberservice.getMemberOrderBy(sql);
		
		return list;
	}
	
	@RequestMapping(value = "/searchMember.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	/**
	 * 首页查询，还有活动创建时的获取查询信息，并跳转页面
	 * @param request
	 * @param response
	 * @param option
	 * @param search_main
	 */
	public List<Member> searchMember(HttpServletRequest request, HttpServletResponse response,
			 @RequestParam("search_main") String search_main) {
		try {
			String path = request.getHeader("Referer");
			if(path.contains("create_activitys.html") || path.contains("edit_activitys.html")) {
				
				return memberservice.getMemberBySearchMain(search_main);
			}
			else {
				HttpSession session = request.getSession();
				session.setAttribute("searchStr", search_main);
				response.sendRedirect("pages/search.html");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	@RequestMapping(value = "/getSearchMember.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	/**
	 * 将查询的结果返回到页面中
	 * @param request
	 * @param response
	 * @return
	 */
	public List<Member> getSearchMember(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		List<Member> list = new ArrayList<Member>();
		String str = (String)session.getAttribute("searchStr");
		return memberservice.getMemberBySearchMain(str);
	}
	
	@RequestMapping(value = "/getSearchMemberOrderBy.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.POST })
	@ResponseBody
	/**
	 * 对查询的结果进行排序
	 * @param request
	 * @param response
	 * @param way
	 * @param order
	 * @return
	 */
	public List<Member> getSearchMemberOrderBy(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("way") String way,@RequestParam("order") String order) {
		HttpSession session = request.getSession();
		String str = (String)session.getAttribute("searchStr");
		List<Member> list = new ArrayList<Member>();
//		String sql = "select * from member_info where ";
//		
//		String[] string = str.split(",");
//		String option = string[0];
//		String search_main = string[1];
//		if(Integer.valueOf(option) == 0) {
//			sql += "name like '%"+search_main+"%' order by ";
//		} else if(Integer.valueOf(option) == 1) {
//			sql += "phone like '"+search_main+"' order by ";
//		} else {
//			sql = "SELECT m.* FROM `attend_activity` a ,member_info m WHERE a.theme LIKE '%"+search_main+"%' AND a.identity = m.identity order by ";
//		}
		
		String sql = "SELECT m.* FROM `member_info` m , attend_activity a  WHERE m.identity = '"+str+"' OR m.phone = '"+str+"' OR m.`name` LIKE '%"+str+"%' OR (a.theme LIKE '%"+str+"%' AND a.identity = m.identity) order by ";
		if(Integer.valueOf(way) == 0) {
			sql += "abs(year) ";
			if(Integer.valueOf(order) == 0) {
				sql += "desc";
			} else {
				sql += "asc";
			}
		} else if(Integer.valueOf(way) == 1) {
			sql += "abs(month) ";
			if(Integer.valueOf(order) == 0) {
				sql += "asc";
			} else {
				sql += "desc";
			}
		} else {
			sql += "abs(join_activity) ";
			if(Integer.valueOf(order) == 0) {
				sql += "asc";
			} else {
				sql += "desc";
			}
		}
		
		System.out.println(sql);
		list = memberservice.getMemberOrderBy(sql);
		
		return list;
	}
	
	@RequestMapping(value = "/exportSearchMember.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	/**
	 * 将查询的结果导出表格
	 * @param request
	 * @param response
	 * @return
	 */
	public void exportSearchMember(HttpServletRequest request, HttpServletResponse response,@RequestParam("theme") String theme) throws IOException {
		HttpSession session = request.getSession();
		List<Member> list = new ArrayList<Member>();
		String str = (String)session.getAttribute("searchStr");
		list = memberservice.getMemberBySearchMain(str);
		WritableWorkbook book = null;
		String filePath=request.getSession().getServletContext().getRealPath("/");;
	        try {
	            // 打开文件
	            book = Workbook.createWorkbook(new File(filePath+"WEB-INF\\classes\\excelFile\\"+str+".xls"));
	            // 生成名为"学生"的工作表，参数0表示这是第一页
	            WritableSheet sheet = book.createSheet(str, 0);
	            // 指定单元格位置是第一列第一行(0, 0)以及单元格内容为张三
	            //Label label = new Label(0, 0, activity.getTheme());
	            // 将定义好的单元格添加到工作表中
	            
	            sheet.addCell(new Label(0, 0, "序号"));
	            sheet.addCell(new Label(1, 0, "名字"));
	            sheet.addCell(new Label(2, 0, "性别"));
	            sheet.addCell(new Label(3, 0, "电话"));
	            sheet.addCell(new Label(4, 0, "身份证号"));
	            sheet.addCell(new Label(5, 0, "上车地点"));
	            
	            for(int i =0;i<list.size();i++) {
	            	sheet.addCell(new Label(0, i+1, String.valueOf(i+1)));
		            sheet.addCell(new Label(1, i+1, list.get(i).getName()));
		            sheet.addCell(new Label(2, i+1, list.get(i).getSex()));
		            sheet.addCell(new Label(3, i+1, list.get(i).getPhone()));
		            sheet.addCell(new Label(4, i+1, list.get(i).getIdentity()));
		            sheet.addCell(new Label(5, i+1, list.get(i).getSite()));
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
	        File file = new File(filePath+"WEB-INF\\classes\\excelFile\\"+str+".xls");
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
	
	@RequestMapping(value = "/backupsDatabase.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	/**
	 * 数据库备份
	 * @param request
	 * @param response
	 * @return
	 */
	public boolean backupsDatabase(HttpServletRequest request,HttpServletResponse response) {
		BackupsDatabase backupsDatabase = new BackupsDatabase();
		if(backupsDatabase.backupsdatabase()) {
			return true;
		}
		return false;
	}
	
	@RequestMapping(value = "/getActivitysByIdentity.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	/**
	 * 获取用户参与活动信息
	 * @param request
	 * @param response
	 * @return
	 */
	public String getActivitysByIdentity(HttpServletRequest request,HttpServletResponse response,@RequestParam("identity") String identity) {
		if(identity.equals("") || "".equals(identity) || identity == "") {
			return "";
		}
		return activityservice.getActivitysByidentity(identity);
	}
	
	@RequestMapping(value = "/removeMemberInfo.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	/**
	 * 删除指定会员的信息
	 * @param request
	 * @param response
	 * @return
	 */
	public Boolean removeMemberInfo(HttpServletRequest request,HttpServletResponse response,@RequestParam("identity") String identity) {
		if(identity.equals("") || "".equals(identity) || identity == "") {
			return false;
		}
		memberservice.deleteMemberByIdentity(identity);
		System.out.println("成功删除"+identity);
		return true;
	}
	
	@RequestMapping(value = "/isMember.do", produces = "application/json;charset=UTF-8", method = { RequestMethod.GET })
	@ResponseBody
	/**
	 * 判断是否有会员的信息
	 * @param request
	 * @param response
	 * @return
	 */
	public Boolean isMember(HttpServletRequest request,HttpServletResponse response,@RequestParam("identity") String identity) {
		
		Member member = memberservice.getMemberByIdentity(identity);
		System.out.println(member.getIdentity());
		if(member.getIdentity() != null && member.getIdentity() != "") {
			return true;
		} else {
			return false;
		}
		
	}
	
}
