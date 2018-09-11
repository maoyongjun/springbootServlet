package org.foxconn.bootstrapTest.dao;

import java.util.List;

import org.foxconn.bootstrapTest.entity.LabelEntity;
import org.foxconn.bootstrapTest.entity.LabelFileEntity;
import org.springframework.dao.DataAccessException;

public interface LabelDao {
	public void updateLabel(LabelEntity label) throws DataAccessException;
	public void addLabel(LabelEntity label) throws DataAccessException;
	public void deleteLabel(LabelEntity label) throws DataAccessException;
	public List<LabelEntity> findAll(LabelEntity label) throws DataAccessException;
	public List<LabelFileEntity> findLabelFiles(LabelFileEntity labelFileEntity) throws DataAccessException;
	public Integer findLabelFilesSize(LabelFileEntity labelFileEntity) throws DataAccessException;
	public void addLabelFile(LabelFileEntity labelFileEntity) throws DataAccessException;
	public void deleteLabelFile(LabelFileEntity labelFileEntity) throws DataAccessException;
	public void deleteAllLabelFile(LabelFileEntity labelFileEntity) throws DataAccessException;
}
