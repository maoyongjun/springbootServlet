package org.foxconn.bootstrapTest.dao;

import java.util.List;

import org.foxconn.bootstrapTest.entity.LabelEntity;

public interface LabelDao {
	public void updateLabel(LabelEntity label);
	public void addLabel(LabelEntity label);
	public void deleteLabel(LabelEntity label);
	public List<LabelEntity> findAll(LabelEntity label);
}
