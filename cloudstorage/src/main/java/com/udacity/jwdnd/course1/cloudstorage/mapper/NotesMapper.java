package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    List<Notes> getUserNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle,notedescription,userid) VALUES(#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Notes notes);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Notes getNote(Integer noteId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} WHERE noteid = #{noteId}")
    Integer update(Integer noteId, String noteTitle, String noteDescription);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    int delete(Integer noteId);
}
