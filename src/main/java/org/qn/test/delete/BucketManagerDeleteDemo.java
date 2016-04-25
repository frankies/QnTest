/******************************************************************************/
/* SYSTEM     : IM Step2                                                        */
/*                                                                            */
/* SUBSYSTEM  :                                                            */
/******************************************************************************/
package org.qn.test.delete;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.util.Auth;

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
public class BucketManagerDeleteDemo {
	public static void main(String args[]){
	    //设置需要操作的账号的AK和SK
	    String ACCESS_KEY = "Access_Key";
	    String SECRET_KEY = "Secret_Key";
	    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
	    //实例化一个BucketManager对象
	    BucketManager bucketManager = new BucketManager(auth);
	    //要测试的空间和key，并且这个key在你空间中存在
	    String bucket = "Bucket_Name";
	    String key = "Bucket_key";
	    try {
	      //调用delete方法移动文件
	      bucketManager.delete(bucket, key);
	    } catch (QiniuException e) {
	      //捕获异常信息
	      Response r = e.response;
	      System.out.println(r.toString());
	    }
	  }
}
