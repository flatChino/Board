package com.example.boardproj.repository;

import com.example.boardproj.entity.BoardImage;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImageRepository extends JpaRepository<BoardImage,Long> {

    public List<BoardImage> findByBoardBno(Long bno);


}
