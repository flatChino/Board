package com.example.boardproj.service;

import com.example.boardproj.dto.BoardDTO;
import com.example.boardproj.dto.BoardImageDTO;
import com.example.boardproj.entity.Board;
import com.example.boardproj.entity.BoardImage;
import com.example.boardproj.repository.BoardImageRepository;
import com.example.boardproj.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.util.Elements;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class BoardImageSevervice {

    private  final BoardImageRepository boardImageRepository;
    private  final  BoardRepository boardRepository;
    private  final  ModelMapper modelMapper;

    private  final  FileService fileService;
    @Value("${imgLocation}")
    private String imgLocation;


    public void boardImageregister(Long bno , MultipartFile multipartFile){

        //원래 이름 가져오기
        String originalFilename= multipartFile.getOriginalFilename();//파일명에 담긴 경로 삭제


        String ImageName= originalFilename.substring(originalFilename.lastIndexOf("\\")+1);//뒤에서 부터 자르는 작업
        //특별한 난수
        UUID uuid = UUID.randomUUID();
        log.info("생성된 uuid"+uuid);
        //중복되지 않도록 파일이름을 변경해서 저장한다 DB, 물리적 파일 둘다
        String newImageName=uuid.toString()+"_"+ImageName;

        Board board= boardRepository.findById(bno).orElseThrow(EntityNotFoundException::new);
        //참조 대상인 board를 가지고 boardImage를 만들어 저장한다.


        BoardImage boardImage = new BoardImage();
        boardImage.setBoard(board);
        //모델 어트리 뷰트에 저장하기 위해서
                                            //C:/upload+"//"+"board"
        boardImage.setImgPath(imgLocation); //저장경로 //각 경로board noitce member등은
                                //fileservice를 만들때부터
        boardImage.setNewImgName(newImageName); //새로운 이름
        boardImage.setImgName(ImageName); //사진이름
        //DB저장
        boardImageRepository.save(boardImage);

        //물리적인 파일저장
        fileService.regiser(multipartFile, newImageName);
        
    }


    public List<BoardImageDTO> boardImageread(Long bno){
        List<BoardImage> boardImageList= boardImageRepository.findByBoardBno(bno);

        List<BoardImageDTO> boardImageDTOList= boardImageList.stream().map(boardImage ->
                modelMapper.map(boardImage, BoardImageDTO.class)).collect(Collectors.toList());


        return boardImageDTOList;
    }

    public void del(Long ino){

        //db 정보 찾아오기
        //사진 정보 찾아오기
        BoardImage boardImage=
        boardImageRepository.findById(ino).get();
        //경로 가져오기
        String path =
        imgLocation +"\\"+ boardImage.getNewImgName();
        fileService.delFile(path); //물리적 데이터 삭제

        boardImageRepository.deleteById(ino);//디비 삭제
    }




}
