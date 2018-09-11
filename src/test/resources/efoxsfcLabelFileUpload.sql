CREATE TABLE efoxsfcLabelFileUpload(
	ID				INT IDENTITY(1,1),
	labelId			INT,
	fileId			INT,
	fileSystemName	VARCHAR(100),
	labelName		NVARCHAR(400)
)

--sfcskunolabel