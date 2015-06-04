//package dei.uc.pt.ar.paj.upload;
//
//import java.io.IOException;
//import java.io.Serializable;
//
//import javax.enterprise.context.ApplicationScoped;
//import javax.faces.bean.ManagedBean;
//import javax.servlet.http.Part;
//
//@ManagedBean
//@ApplicationScoped
//public class Upload implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = -5537671907313363474L;
//
//	private Part file;
//
//	private String path;
//	private String fileName;
//
//	public String getFileName() {
//		return fileName;
//	}
//
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//
//	public Part getFile() {
//		return file;
//	}
//
//	public void setFile(Part file) {
//		this.file = file;
//	}
//
//	public String getPath() {
//		return path;
//	}
//
//	public void setPath(String path) {
//		this.path = path;
//	}
//	
//	public void init() {
//		this.file=null;
//		this.fileName=this.path="";
//	}
//
//	public boolean validExtension() {
//		if(this.file!=null){
//			this.fileName=getFilename(this.file);
//
//			if(this.fileName.length()>4){
//				String extension=path.substring(path.length()-3);
//
//				if(extension.equals("mp3"))
//					return true;
//
//			}
//		}
//		
//		return false;
//	}
//
//	
//	private String generatePath(Long musicId) {
//		return "C:\\SpotiflyServerFileStorage\\"+musicId+".mp3";
//	}
//
//	
//	private static String getFilename(Part part) {  
//		for (String cd : part.getHeader("content-disposition").split(";")) {  
//			if (cd.trim().startsWith("filename")) {  
//				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");  
//				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
//			}
//		}
//		return "";  
//	}
//
//	//Cuidado com as excep��es
//	public String upload(Long musicId){
//		String path=generatePath(musicId);
//
//		try {
//			file.write(path);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		return path;
//	}
//
//
//}