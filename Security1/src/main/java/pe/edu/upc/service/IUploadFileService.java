package pe.edu.upc.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadFileService {

	public Resource load(String filename) throws MalformedURLException; // CARGA

	public String copy(MultipartFile file) throws IOException; // COPIA

	public boolean delete(String filename); // ELIMINA

}