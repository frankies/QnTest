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
import com.qiniu.util.UrlSafeBase64;


/***
 *
 *  队列pipeline 请参阅创建私有队列；转码操作 具体参数请参阅音视频转码；saveas 请参阅处理结果另存。

    Tips：上面的 Demo 只是针对视频转码功能，如果您需要使用比如音视频切片、视频截图、视频拼接等功能只需要修改上面 fops 后面的参数即可，如：$fops = vframe/jpg/offset/1/w/480/h/360/rotate/90 就表示视频截图了。

       可以看到上传成功后的行为主要是由上传凭证中的 上传策略 来指定。其中 上传策略 可以指定的行为不止这些，具体请参阅 上传策略。

 */


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
public class UploadDemoPersist {

	//设置好账号的ACCESS_KEY和SECRET_KEY
	  String ACCESS_KEY = "Access_Key";
	  String SECRET_KEY = "Secret_Key";
	  //要上传的空间
	  String bucketname = "Bucket_Name";
	  //上传到七牛后保存的文件名
	  String key = "my-java.png";
	  //上传文件的路径
	  String FilePath = "/.../...";

	  //设置转码操作参数
	  String fops = "avthumb/mp4/s/640x360/vb/1.25m";
	  //设置转码的队列
	  String pipeline = "yourpipelinename";

	  //可以对转码后的文件进行使用saveas参数自定义命名，当然也可以不指定文件会默认命名并保存在当前空间。
	  String urlbase64 = UrlSafeBase64.encodeToString("目标Bucket_Name:自定义文件key");
	  String pfops = fops + "|saveas/"+ urlbase64;

	  //密钥配置
	  Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	  //创建上传对象
	  UploadManager uploadManager = new UploadManager();

	  //上传策略中设置persistentOps字段和persistentPipeline字段
	  public String getUpToken(){
	      return auth.uploadToken(bucketname,null,3600,new StringMap()
	          .putNotEmpty("persistentOps", pfops)
	          .putNotEmpty("persistentPipeline", pipeline), true);
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
