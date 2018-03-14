package com.qxlt;

public class MyStringUtil {
	public static String replaceVar(String source, String varName, String value) {
		if(value!=null)
		   value = value.replaceAll("\\\\", "\\\\\\\\");
		
		String dest = source
				.replaceAll("(?i)\\$(\\{" + varName + "\\})", value==null?"":value);
		return dest;
	}

	public static void main(String a[]) {    
        String s = "\\scwb-nx3000\\Chinalife\\Unit1\\scwb-NiceIC\\Storage Rule 1\\2010_Mar_10\\SC_1_132110_63488_Mar 10 2010  7 16AM_Mar 10 2010  7 16AM_Mar 10 2010  7 15AM_Mar 10 2010  7 15AM__401997_400908_38671.nmf  varName:vcarchivepath";    
        
        System.out.println(s);
        s = s.replaceAll("\\\\", "\\\\\\\\");
        
        
        System.out.println(s);
         
      }
}