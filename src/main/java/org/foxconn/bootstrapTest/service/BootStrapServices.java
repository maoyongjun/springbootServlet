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
	
	@PostMapping(path="/label/deleteFiles",consumes="application/json",produces="application/json")
	public Msg deleteFiles(@RequestBody LabelFileEntity labelFileEntity){
		labelDao.deleteAllLabelFile(labelFileEntity);
		Msg msg = new Msg();
		msg.setMsg("OK");
		return msg;
	}
	
	
	@PostMapping(path="/label/findFilesize",consumes="application/json",produces="application/json")
	public Msg findFilesize(@RequestBody LabelFileEntity labelFileEntity){
		Integer size =  labelDao.findLabelFilesSize(labelFileEntity);
		Msg msg = new Msg();
		msg.setMsg(size.toString());
		return msg;
	}
	
	
	@PostMapping(path="/label/findLabelFiles",consumes="application/json",produces="application/json")
	public Msg findFiles(@RequestBody LabelFileEntity labelFileEntity){
		List<LabelFileEntity> findLabelFiles = labelDao.findLabelFiles(labelFileEntity);
		Msg msg = new Msg();
		msg.setFiles(findLabelFiles);
		return msg;
	}
	@PostMapping(path = "/file/upload")
	public String getFile(@RequestParam("fileName") MultipartFile file,String labelId,String fileId) {
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
	@RequestMapping(value="/download")
	public void downLoadFile(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String fileid = request.getParameter("fileId");
		String labelid = request.getParameter("labelId");
		logger.info("get files for label:"+labelid+",fileid:"+fileid);
		LabelFileEntity labelFileEntity = new LabelFileEntity();
		Integer LabelNum = Integer.parseInt(labelid);
		Integer fileNum = Integer.parseInt(fileid);
		labelFileEntity.setLabelId(LabelNum);
		labelFileEntity.setFileId(fileNum);
		List<LabelFileEntity> labelFiles =  labelDao.findLabelFiles(labelFileEntity);
		if(labelFiles.size()==0){
			return ;
		}
		response.setContentType("application/x-msdownload");
		
		OutputStream out = response.getOutputStream();
		String fileName = labelFiles.get(0).getLabelName();
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
		out.flush();
		
		
		
		
		
		
		//file.delete();
	}
	//@Test
	public void testSubFileName(){
		String fileName = "测试.doc";
		String prefxFileName = fileName.substring(0, fileName.lastIndexOf("."));
		String subFileName = fileName.substring( fileName.lastIndexOf("."),fileName.length());
		logger.info(prefxFileName); 
		logger.info(subFileName); 
	}
}
