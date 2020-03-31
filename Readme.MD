SECURE FILE UPLOADS
--------------------
This library allows us to upload files in a safe way.  
To do that the following tasks are performed:
1. Check request:
	- Check Content-Type header (white list)
	- Check extension (white list)
2. Check file header
3. Create a new file name
4. Check file size
5. Store file outside the document root
6. Change permissions:
	- Uploaded file will be owned by the web server.
	- Only read/write permissions
	
Everything has been developed with interfaces, so different implementations to do the tasks described above can be done.   

### INTERFACES ###

#### SecureFileUploader ####
	- public File upload(@Nonnull final InputStream inputStream, @Nonnull final String fileName, @Nonnull final String requestContentType) throws SecureFileUploadException;
	- public List<File> list();
	- public File get(@Nonnull final String fileName) throws FileNotFoundException;

#### SecureRequestOperator ####
	- public boolean isValidRequestContentType(@Nonnull final String requestContentType);
	- public boolean isValidFileExtension(@Nonnull final String contentType, @Nonnull final String fileExtension);
	- public boolean isValidStreamHeader(@Nonnull final InputStream inputStream, @Nonnull final String requestContentType);
	
#### SecureFileOperator ####
	- public File createNewFileInSecureStorage(@Nonnull final InputStream inputStream, @Nonnull final String initialFileName) throws SecureFileUploadException;
	- public void setSecureFileOwner(@Nonnull final File file) throws SecureFileUploadException;
	- public void setSecureFilePermissions(@Nonnull final File file) throws SecureFileUploadException;
	- public List<File> listUploadedFiles();
	- public File getUplodadedFile(@Nonnull final String fileName) throws FileNotFoundException;

To get an implementation of SecureFileUploader it is necessary to use the class SecureFileUploaderFactory. This class has three methods:   
#### SecureFileUploaderFactory ####
	- public static SecureFileUploader getStoredSecureFileUploader(): This SecureFileUploader implementation stores the uploaded file first and then makes all the checks (No memory problems).   
	- public static SecureFileUploader getInMemorySecureFileUploader(): This SecureFileUploader implementation makes all the checks in memory and if everything is ok store the file (faster but can be out of memory with very large files).   
	- public static SecureFileUploader getDefaultSecureFileUploader(): The default implementation is the stored implementation.      

Also is possible to use Java Qualifiers. Two Qualifiers have been created, one for each implementation:
#### Java Qualifiers ####
	- @Stored: This qualifier indentifies the stored implementation.
	- @InMemory: This qualifier indentifies the In Memory implementation.
Which implementation to use depends on the project.   
	
The first implementation developed uses the Tika library to detect the content type of the files.   
The Tika library can detect over a thousand different file types and the type detection is based on the first few bytes of a document (if possible).   
Here you can see the full list of Tika supported document formats: https://tika.apache.org/1.14/formats.html   
The library uses two configuration files that can be changed at any time without having to recompile the source code.   

### secure-file-upload_config.properties ###
In this configuration file we have to configure the next properties:   
- max.file.size: Define the maximum file size allowed. If not configured a 10MB size default value is defined.   
- default.storage.folder: Is the folder where all the new uploaded files will be stored. The security recommendation is to establish any folder outside the document root. If not configured "uploadedfiles" folder default value is defined.   
- file.name.mode: There are two options. SECURE to create a new unique file name (recommended) and ORIGINAL to use the original provided file name. If not configured SECURE default value is defined.   
- secure.file.owner: It is recommended that all the uploaded files owner be the container owner. This property is not mandatory.   

```
#Max file size 10 * 1024 * 1024 = 10Mb
max.file.size = 10485760

#Default storage folder
default.storage.folder = uploadedfiles

#File name options:
#ORIGINAL: Same original user name file
#SECURE: Create a new secure name
file.name.mode = SECURE

#Secure file owner
secure.file.owner = jboss
```

### secure-file-upload_whitelist.properties ###
In this file we should establish the allowed Content-types and the extensions allowed for each Content-type.

```
#Comma separated list of allowed extensions for each allowed content type   
text/plain = txt   
application/pdf = pdf   
#Microsoft Office Content-types   
application/msword = doc   
application/vnd.openxmlformats-officedocument.wordprocessingml.document = docx   
application/vnd.ms-excel = xls   
application/vnd.openxmlformats-officedocument.spreadsheetml.sheet = xlsx   
application/vnd.ms-powerpoint = ppt   
application/vnd.openxmlformats-officedocument.presentationml.presentation = pptx   
#Image Content-types   
image/png = png   
image/jpeg = jpg, jpeg   
image/bmp = bmp    
```

### EXECUTION EXAMPLE ### 
```
final SecureFileUploader fileUploader = SecureFileUploaderFactory.getDefaultSecureFileUploader();  
final File newFile = fileUploader.upload(inputStream, initialFileName, requestContentType);

@Inject
@Stored
private SecureFileUploader storedFileUploader;
final File newFile = storedFileUploader.upload(inputStream, initialFileName, requestContentType);

@Inject
@InMemory
private SecureFileUploader inMemoryFileUploader;
final File newFile = inMemoryFileUploader.upload(inputStream, initialFileName, requestContentType);

```