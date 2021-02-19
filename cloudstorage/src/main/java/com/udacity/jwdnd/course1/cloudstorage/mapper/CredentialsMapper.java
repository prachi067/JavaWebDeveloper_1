package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    List<Credentials> getUserCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userid) VALUES(#{url},#{userName},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialsId")
    int insert(Credentials credentials);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialsId}")
    Credentials getCredentials(Integer credentialsId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{userName}, password = #{password} WHERE credentialid = #{credentialsId}")
    Integer update(Integer credentialsId, String url, String userName, String password);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialsId}")
    int delete(Integer credentialsId);

}
