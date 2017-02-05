package com.hmc.web.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.StreamUtils;

import com.hmc.base.BaseConstants;


/**
 * 抽象的ServletRequest包装类<p>
 * <pre>
 * 首先，说明几个类和方法的关系： class HttpServletRequestWrapper extends ServletRequestWrapper implements HttpServletRequest
 *   
 * 在Filter中由于需要的对于inputStream的内容来进行定制性的操作，我们必须将所有input内容读取出来，
 * 但是在Controller层中程序同样需要提取input的内容，进而执行对应的post/put/delete请求，
 * 因此，我们在此对HttpServletRequest进行了封装，
 * 并对封装后的HttpServletRequestWrapper的getInputStream方法进行了重载，
 * 保证重载后的方法可以再次读取到inputStream中的所有内容。
 * <pre>
 * 由于
 * 用于重写ServletRequest流中的数据
 *
 * @author hanmc
 * @date 2016-4-3
 */
public abstract class BaseServletRequestWrapper extends HttpServletRequestWrapper {

    protected String encoding = null;
    protected HttpServletRequest httpServletRequest;

    public BaseServletRequestWrapper (HttpServletRequest request) {
        super(request);
        httpServletRequest = request;
    }

    /**
     * 重写getInputStream，来更改request中流的内容
     */
    @Override
    public ServletInputStream getInputStream ()
        throws IOException {
    
    	String document = documentDeal(httpServletRequest);
    	
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                StringUtils.isEmpty(encoding)? document.getBytes() : document.getBytes(encoding));

        ServletInputStream inputStream = new ServletInputStream() {
            public int read ()
                throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return inputStream;
    }
    
    /**
	 * 获取reqesut的流中的报文
	 * @param request
	 * @return
	 */
    protected String getDocument(HttpServletRequest request){
    	Charset charset = Charset.forName(BaseConstants.UTF_8.getCode());
        String encoding = request.getCharacterEncoding();
        if(StringUtils.isNotBlank(encoding)){
        	charset = Charset.forName(encoding);
        }
		String documunt = "";
		try {
			documunt = StreamUtils.copyToString(request.getInputStream(), charset);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return documunt;
	}
	
	
	/**
	 * 获取reqesut的流中的报文
	 * @param request
	 * @return
	 */
	protected String getDoc(HttpServletRequest request) {
		StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        String encoding = request.getCharacterEncoding();
        try {

           //按照正确的encoding，将inputStream中的内容写入到String中
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                if (StringUtils.isEmpty(encoding)) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                } else {
                    this.encoding = encoding; 
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, encoding));
                }
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } catch (IOException ex) {
        	ex.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException iox) {
                	iox.printStackTrace();
                }
            }
        }
       return stringBuilder.toString();
	}
    
    protected abstract String documentDeal(HttpServletRequest request);

}
