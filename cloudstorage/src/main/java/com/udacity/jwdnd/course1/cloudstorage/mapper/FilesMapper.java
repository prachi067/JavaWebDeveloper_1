package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userid}")
    List<Files> getUserFiles(Integer userid);


    @Insert("INSERT INTO FILES (filename,contenttype,filesize,filedata,userid) VALUES(#{fileName},#{contentType},#{fileSize},#{fileData},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(Files file);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    Files getFile(String filename);

    @Delete("DELETE FROM FILES WHERE filename=#{filename}")
    void delete(String filename);
}
