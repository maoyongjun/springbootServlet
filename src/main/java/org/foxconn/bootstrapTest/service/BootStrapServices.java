package org.foxconn.bootstrapTest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.foxconn.bootstrapTest.dao.LabelDao;
import org.foxconn.bootstrapTest.entity.LabelEntity;
import org.foxconn.bootstrapTest.entity.LabelFileEntity;
import org.foxconn.bootstrapTest.entity.Msg;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
public class BootStrapServices {
	Logger logger = LoggerFactory.getLogger(BootStrapServices.class);
	@Resource
	LabelDao labelDao;
	
	@PostMapping(path="/label/add",consumes="application/json",produces="application/json")
	public Msg addLabel(@RequestBody LabelEntity label){
		labelDao.addLabel(label);
		Msg msg = new Msg();
		msg.setMsg("OK");
		return msg;
	}
	
	@PostMapping(path="/label/addList",consumes="application/json",produces="application/json")
	public Msg addLabelList(@RequestBody List<LabelEntity> label){
		for(LabelEntity e:label) {
			labelDao.addLabel(e);
		}
		Msg msg = new Msg();
		msg.setMsg("OK");
		return msg;
	}
	@PostMapping(path="/label/update",consumes="application/json")
	public void updateLabel(@RequestBody LabelEntity label){
		labelDao.updateLabel(label);
	}
	
	@PostMapping(path="/label/delete",consumes="application/json",produces="application/json")
	public Msg deleteLabel(@RequestBody LabelEntity label){
		labelDao.deleteLabel(label);
		Msg msg = new Msg();
		msg.setMsg("OK");
		return msg;
	}
	@GetMapping(path="/label/query",produces="application/json")
	public List< LabelEntity> queryLabel(LabelEntity label){
		logger.info(label.toString());
		List< LabelEntity> list = labelDao.findAll(label);
		
		return list;
	}
	
	@PostMapping(path="/label/findFilesize",consumes="application/json",produces="application/json")
	public Msg deleteLabel(@RequestBody LabelFileEntity labelFileEntity){
		Integer size =  labelDao.findLabelFilesSize(labelFileEntity);
		Msg msg = new Msg();
		msg.setMsg(size.toString());
		return msg;
	}
	
	@PostMapping(path = "/file/upload", consumes = "multipart/form-data")
	public String getFile(@RequestParam("fileName") MultipartFile file,String labelId) {
	    if(file.isEmpty()){
            return "false";
        }
        String fileName = file.getOriginalFilename();
        int size = (int) file.getSize();
        logger.info(fileName + "-->" + size);
        String path = "d:/uploadFiles" ;
       
        try {
           
            LabelFileEntity fileEntity = new LabelFileEntity();
            Integer labelnum=-1;
            try {
            	labelnum = Integer.parseInt(labelId);
            } catch (Exception e) {
            	logger.error("labeid error",e);
				return "false";
			}
            fileEntity.setLabelId(labelnum);
            Long fileSystem = new Date().getTime();
            String prefxFileName = fileName.substring(0, fileName.lastIndexOf("."));
            String subFileName = fileName.substring( fileName.lastIndexOf("."),fileName.length());
            fileEntity.setFileSystemName(fileSystem.toString()+subFileName);
            fileEntity.setLabelName(prefxFileName+subFileName);
            Integer labelsize=  labelDao.findLabelFilesSize(fileEntity);
            fileEntity.setFileId(labelsize+1);
            File dest = new File(path + "/" + fileSystem+subFileName);
            if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
                dest.getParentFile().mkdir();
            }
            file.transferTo(dest); //保存文件
            labelDao.addLabelFile(fileEntity);
            return "true";
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "false";
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
	}
	@RequestMapping("/download")
	public void downLoadFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String labelid = request.getParameter("labelid");
		logger.info("get files for label:"+labelid);
		LabelFileEntity labelFileEntity = new LabelFileEntity();
		Integer LabelNum = Integer.parseInt(labelid);
		labelFileEntity.setLabelId(LabelNum);
		List<LabelFileEntity> labelFiles =  labelDao.findLabelFiles(labelFileEntity);
		if(labelFiles.size()==0){
			return ;
		}
		response.setContentType("application/x-msdownload");
		
		OutputStream out = response.getOutputStream();
		for(int i=0;i<labelFiles.size();i++){
			String fileName = labelFiles.get(i).getLabelName();
			String systemFileName = labelFiles.get(0).getFileSystemName();
			logger.info("fileName:"+fileName+",systemFileName:"+systemFileName);
			//2、下载
			File file = new File("d:/uploadFiles/"+systemFileName);
			fileName = new String(fileName.getBytes("utf-8"),"ISO8859_1");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName+ "\"");
			int len = (int)file.length();
			byte []buf = new byte[len];
			FileInputStream fis = new FileInputStream(file);
			len = fis.read(buf);
			out.write(buf, 0, len);
			fis.close();
			logger.info(file.toString());
		}
		out.flush();
		
		
		
		
		
		
		//file.delete();
	}
	@Test
	public void testSubFileName(){
		String fileName = "测试.doc";
		String prefxFileName = fileName.substring(0, fileName.lastIndexOf("."));
		String subFileName = fileName.substring( fileName.lastIndexOf("."),fileName.length());
		logger.info(prefxFileName); 
		logger.info(subFileName); 
	}
}
