package com.open.capacity.uaa.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.open.capacity.uaa.model.SysClient;

@Mapper
public interface SysClientDao {

    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into oauth_client_details(client_id, resource_ids, client_secret,client_secret_str, scope, "
    		+ " authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, "
    		+ " additional_information, autoapprove, `status`) "
    		+ " values(#{clientId}, #{resourceIds}, #{clientSecret},#{clientSecretStr}, #{scope}, "
    		+ " #{authorizedGrantTypes}, #{webServerRedirectUri}, #{authorities}, #{accessTokenValidity}, #{refreshTokenValidity}, "
    		+ " #{additionalInformation}, #{autoapprove} ,0 )")
    int save(SysClient client);

    int count(@Param("params") Map<String, Object> params);

    List<SysClient> findList(@Param("params") Map<String, Object> params );

    @Select("select id id , client_id clientId , resource_ids resourceIds ,client_secret clientSecret,client_secret_str clientSecretStr ,web_server_redirect_uri webServerRedirectUri  from oauth_client_details t where t.id = #{id}  ")
    SysClient getById(Long id);

    @Select("select * from oauth_client_details t where t.client_id = #{clientId}  ")
    SysClient getClient(String clientId);

    @Update("update oauth_client_details t set t.client_secret = #{clientSecret},t.client_secret_str = #{clientSecretStr} ,t.`status` = #{status} where t.id = #{id}")
    int update(SysClient client);



    @Delete("delete from oauth_client_details where id = #{id}")
    int delete(Long id);


}
