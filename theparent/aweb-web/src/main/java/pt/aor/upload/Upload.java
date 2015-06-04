package pt.aor.upload;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.servlet.http.Part;

@ManagedBean
@ApplicationScoped
public class Upload implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5537671907313363474L;

	private Part file;
	
	private String path;

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	//Cuidado com as excepções
	public void upload() throws IOException{
		String path=generatePath();
		
		if(validExtension(path)){
			file.write(path);
		}
	}
	
	private boolean validExtension(String path) {
		String extension=path.substring(path.length()-3);
		
		if(extension.equals("mp3"))
			return true;
		
		return false;
	}

	private String generatePath() {
		return "C:\\SpotiflyServerFileStorage\\"+getFilename(file);
	}

	private static String getFilename(Part part) {  
		for (String cd : part.getHeader("content-disposition").split(";")) {  
			if (cd.trim().startsWith("filename")) {  
				String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");  
				return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
			}
		}
		return null;  
	}

}