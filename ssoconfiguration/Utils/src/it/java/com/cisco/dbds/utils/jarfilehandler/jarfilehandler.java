package com.cisco.dbds.utils.jarfilehandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class jarfilehandler {
	public static void copyResourcesToDirectory(JarFile fromJar, String jarDir, String destDir)
		      throws IOException {
		    for (Enumeration<JarEntry> entries = fromJar.entries(); entries.hasMoreElements();) {
		      JarEntry entry = entries.nextElement();
		      if (entry.getName().startsWith(jarDir + "/") && !entry.isDirectory()) {
		        File dest = new File(destDir + "/" + entry.getName().substring(jarDir.length() + 1));
		        File parent = dest.getParentFile();
		        if (parent != null) {
		          parent.mkdirs();
		        }

		        FileOutputStream out = new FileOutputStream(dest);
		        InputStream in = fromJar.getInputStream(entry);

		        try {
		          byte[] buffer = new byte[8 * 1024];

		          int s = 0;
		          while ((s = in.read(buffer)) > 0) {
		            out.write(buffer, 0, s);
		          }
		        } catch (IOException e) {
		          throw new IOException("Could not copy asset from jar file", e);
		        } finally {
		          try {
		            in.close();
		          } catch (IOException ignored) {}
		          try {
		            out.close();
		          } catch (IOException ignored) {}
		        }
		      }
		    }

		  }
	
	  public static JarFile jarForClass(Class<?> clazz, JarFile defaultJar) {
		    String path = "/" + clazz.getName().replace('.', '/') + ".class";
		    URL jarUrl = clazz.getResource(path);
		    if (jarUrl == null) {
		      return defaultJar;
		    }

		    String url = jarUrl.toString();
		    int bang = url.indexOf("!");
		    String JAR_URI_PREFIX = "jar:file:";
		    if (url.startsWith(JAR_URI_PREFIX) && bang != -1) {
		      try {
		        return new JarFile(url.substring(JAR_URI_PREFIX.length(), bang));
		      } catch (IOException e) {
		        throw new IllegalStateException("Error loading jar file.", e);
		      }
		    } else {
		      return defaultJar;
		    }
		  }
	  
	  public static JarFile jarForClass(Class<?> clazz) {
		    String path = "/" + clazz.getName().replace('.', '/') + ".class";
		    URL jarUrl = clazz.getResource(path);
		    if (jarUrl == null) {
		    	throw new IllegalStateException("Error loading jar file.");
		    }

		    String url = jarUrl.toString();
		    int bang = url.indexOf("!");
		    String JAR_URI_PREFIX = "jar:file:";
		    if (url.startsWith(JAR_URI_PREFIX) && bang != -1) {
		      try {
		        return new JarFile(url.substring(JAR_URI_PREFIX.length(), bang));
		      } catch (IOException e) {
		        throw new IllegalStateException("Error loading jar file.", e);
		      }
		    } else {
		    	throw new IllegalStateException("Error loading jar file.");
		    }
		  }
}
