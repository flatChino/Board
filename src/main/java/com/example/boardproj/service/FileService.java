package com.example.boardproj.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
public class FileService {

    @Value("${imgLocation}")
    private String imgLocaiton;


    public void regiser(MultipartFile multipartFile,String newImageName){
        //들어온 사진이름

        String imgname=
                multipartFile.getOriginalFilename();
                System.out.println(imgname);
                System.out.println(imgname);

        //사진 경로 및 이름
        File file = new File(imgLocaiton+"\\"+newImageName);
        //사진 저장
        try {
            multipartFile.transferTo(file);
        }catch (IOException e){

        }

    }


    //물리 파일 삭제
    public void delFile(String filePath){
        File deleteFilek = new File(filePath);
        //if(파일 객체변수명 deleteFielk 존재한다면)
        if(deleteFilek.exists()){
            deleteFilek.delete();
            System.out.println("파일 삭제함");
            System.out.println("파일 삭제함");
            System.out.println("파일 삭제함");

        }else {
            System.out.println("파일 못삭제함");
            System.out.println("파일 못삭제함");
            System.out.println("파일 못삭제함");
            System.out.println("파일 못삭제함");
        }


    }


}
