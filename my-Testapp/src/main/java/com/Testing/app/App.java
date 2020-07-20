package com.Testing.app;

import java.io.File;
import java.io.FileInputStream;

import jp.go.aist.six.vuln.core.SixVulnContext;
import jp.go.aist.six.vuln.model.scap.nvd.Nvd;
import jp.go.aist.six.util.xml.XmlMapper;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        
        String  local_src = "nvdcve-2.0-2013.xml";
        File  src_file = new File( local_src );
        XmlMapper  xml_mapper = SixVulnContext.Nvd.basic().getXmlMapper();
        Object  obj1 = xml_mapper.unmarshal( new FileInputStream( src_file ) );

        xml_mapper.marshal( obj1, System.out );

            /* type-safe method, may throw ClassCastException */
        Nvd  nvd1 = xml_mapper.unmarshal( new FileInputStream( src_file ), Nvd.class );
    }
}
