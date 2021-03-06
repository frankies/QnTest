/******************************************************************************/
/* SYSTEM     : IM Step2                                                        */
/*                                                                            */
/* SUBSYSTEM  :                                                            */
/******************************************************************************/
package org.qn.test.upload;

import java.io.IOException;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

/**
 *
 * @author  Lin zhanwang
 * @version 1.0
 * @since   1.0
 *
 * <MODIFICATION HISTORY>
 *  (Rev.)		(Date)     	(Name)        (Comment)
 *  1.0    		2016年4月22日    	Lin zhanwang       New making
 */
public class UploadDemoAndCallback {

	  //设置好账号的ACCESS_KEY和SECRET_KEY
	  String ACCESS_KEY = "UOyG4F61X3PkO_L5YoegV7eSl6ZEAcWcPVeM5l8-";
	  String SECRET_KEY = "kCHUHkBYZBteIdCnbptXTCMIydYuTgz7uCtUZpOS";
	  //要上传的空间
	  String bucketname = "test";
	  //上传到七牛后保存的文件名
	  String key = "gradlew.bat";
	  //上传文件的路径
	  String FilePath = "F:/Gradle_test_lab/test/gradlew.bat";

	  //密钥配置
	  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	  //创建上传对象
	  UploadManager uploadManager = new UploadManager();

	  //设置callbackUrl以及callbackBody,七牛将文件名和文件大小回调给业务服务器
	  public String getUpToken(){
	      return auth.uploadToken(bucketname,null,3600,new StringMap()
	          .put("callbackUrl","http://your.domain.com/callback")
	          .put("callbackBody", "filename=$(fname)&filesize=$(fsize)"));
	  }

	  public void upload() throws IOException{
	    try {
	      //调用put方法上传
	      Response res = uploadManager.put(FilePath, null, getUpToken());
	      //打印返回的信息
	      System.out.println(res.bodyString());
	      } catch (QiniuException e) {
	          Response r = e.response;
	          // 请求失败时打印的异常的信息
	          System.out.println(r.toString());
	          try {
	              //响应的文本信息
	            System.out.println(r.bodyString());
	          } catch (QiniuException e1) {
	              //ignore
	          }
	      }
	  }

	  public static void main(String args[]) throws IOException{
	    new UploadDemo().upload();
	  }
}
