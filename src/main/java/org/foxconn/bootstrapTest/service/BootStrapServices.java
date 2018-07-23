package org.foxconn.bootstrapTest.service;

import java.util.List;

import javax.annotation.Resource;

import org.foxconn.bootstrapTest.dao.LabelDao;
import org.foxconn.bootstrapTest.entity.LabelEntity;
import org.foxconn.bootstrapTest.entity.Msg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



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
		System.out.println(label);
		List< LabelEntity> list = labelDao.findAll(label);
		
		return list;
	}
}
