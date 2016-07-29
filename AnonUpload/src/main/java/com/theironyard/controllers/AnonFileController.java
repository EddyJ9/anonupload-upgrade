package com.theironyard.controllers;

import com.theironyard.entities.AnonFile;
import com.theironyard.services.AnonFileRepository;
import com.theironyard.services.PasswordStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by jeff on 7/27/16.
 */
@RestController
public class AnonFileController {

    @Autowired
    AnonFileRepository repository;

    public static final int LIMIT = 5;

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public void upload(MultipartFile file, HttpServletResponse response, String permanent, String comment,String password) throws IOException, PasswordStorage.CannotPerformOperationException {
        File dir = new File("public/files");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File f = File.createTempFile("anonfile", file.getOriginalFilename(), dir);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());
        AnonFile anonFile = new AnonFile(f.getName(), file.getOriginalFilename());
        if(anonFile.getPassword() == null){
            anonFile.setPassword(PasswordStorage.createHash(password));
        }
        if(!comment.isEmpty()){
            anonFile.setComment(comment);
        }
        if(permanent == null){
            anonFile.setPermanent(false);
        }else{
            anonFile.setPermanent(true);
        }

        long nonPermFiles = repository.countByPermanentFalse();
        if(nonPermFiles >= LIMIT ){
            AnonFile deleteFile = repository.findFirstByPermanentFalseOrderByIdAsc();
            repository.delete(deleteFile);
        }
        repository.save(anonFile);

        response.sendRedirect("/");
    }

    @RequestMapping(path = "/files", method = RequestMethod.GET)
    public List<AnonFile> getFiles(){
        return repository.findAll();
    }

    @RequestMapping(path = "/delete",method = RequestMethod.POST)
    public void deleteFile(String fileName, String password, HttpServletResponse respone) throws Exception {
        AnonFile fileToDelete = repository.findByFilename(fileName);
        if(fileToDelete.getPassword().isEmpty()){
            repository.delete(fileToDelete);
        }else{
            if(PasswordStorage.verifyPassword(password, fileToDelete.getPassword())){
                repository.delete(fileToDelete);
            }else{
                throw new Exception();
            }
        }
        respone.sendRedirect("/");
    }
}
