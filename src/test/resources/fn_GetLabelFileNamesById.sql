ALTER FUNCTION dbo.fn_GetLabelFileNamesById
(
	@labelId		INT
)

RETURNS NVARCHAR (3000)

BEGIN 
	DECLARE	@fileNames NVARCHAR (400)
	SET @fileNames=''
	SELECT	@fileNames=@fileNames+labelName+','
	FROM	efoxsfcLabelFileUpload
	WHERE	labelId=@labelId
	ORDER BY fileId
	
	RETURN @fileNames
END