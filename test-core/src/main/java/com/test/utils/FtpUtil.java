package com.test.utils;

import org.apache.commons.net.ftp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FTPClient工具类
 *
 * @author wuhengzhen
 * @since 2016.7.20
 */
public class FtpUtil {

    public static final String ENCODING_GBK = "GBK";
    public static final String ENCODING_UTF8 = "UTF-8";
    public static final String ENCODING_ISO = "ISO-8859-1";
    public static final String LANGUAGE_CODE_ZH = "zh";
    private static Logger log = LoggerFactory.getLogger(FtpUtil.class);
    private FTPClient ftp;

    public FtpUtil() {
        ftp = new FTPClient();
        // 解决上传文件时文件名乱码
        ftp.setControlEncoding("UTF-8");
    }

    public FtpUtil(String controlEncoding) {
        ftp = new FTPClient();
        // 解决上传文件时文件名乱码
        ftp.setControlEncoding(controlEncoding);
    }

    /**
     * Description: 向FTP服务器上传文件
     *
     * @param host     FTP服务器hostname
     * @param port     FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param basePath FTP服务器基础目录
     * @param filePath FTP服务器文件存放路径。例如分日期存放：/2015/01/01。文件的路径为basePath+filePath
     * @param filename 上传到FTP服务器上的文件名
     * @param input    输入流
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String host, int port, String username, String password, String basePath, String filePath, String filename, InputStream input) {
        boolean result = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            // 连接FTP服务器
            ftp.connect(host, port);
            // 如果采用默认端口，可以使用ftp.connect(host)的方式直接连接FTP服务器
            // 登录
            ftp.login(username, password);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return result;
            }
            //切换到上传目录
            if (!ftp.changeWorkingDirectory(basePath + filePath)) {
                //如果目录不存在创建目录
                String[] dirs = filePath.split("/");
                String tempPath = basePath;
                for (String dir : dirs) {
                    if (null == dir || "".equals(dir)) continue;
                    tempPath += "/" + dir;
                    if (!ftp.changeWorkingDirectory(tempPath)) {
                        if (!ftp.makeDirectory(tempPath)) {
                            return result;
                        } else {
                            ftp.changeWorkingDirectory(tempPath);
                        }
                    }
                }
            }
            //设置上传文件的类型为二进制类型
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //上传文件
            if (!ftp.storeFile(filename, input)) {
                return result;
            }
            input.close();
            ftp.logout();
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return result;
    }


    /**
     * Description: 从FTP服务器下载文件 通过Apache的FTPClient实现文件的下载
     *
     * @param ip         FTP服务器的ip地址
     * @param port       FTP服务器端口，默认为：21
     * @param username   FTP登录账号
     * @param password   FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName   要下载的文件名
     * @param localPath  下载后保存到本地的路径
     * @return flag
     */
    public static boolean downloadFile(String ip, int port, String username, String password, String remotePath, String fileName, String localPath) throws Exception {

        log.info("[downloadFile] begin:" + "--> fileName = " + fileName + DateUtils.formatDateTime(new Date()));
        boolean flag = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(ip, port);
            // 下面三行代码必须要，而且不能改变编码格式，否则不能正确下载中文文件
            ftp.setControlEncoding(ENCODING_GBK);
            // 设置上传文件的类型为二进制类型
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            FTPClientConfig conf = new FTPClientConfig();
            conf.setServerLanguageCode(LANGUAGE_CODE_ZH);

            // 如果采用默认端口，可以使用ftp.connect(url)的方式直接连接FTP服务器
            // 传输模式使用passive
            ftp.enterLocalPassiveMode();
            // 登录
            ftp.login(username, password);
            log.info(DateUtils.formatDateTime(new Date()) + "[downloadFile] 登陆FTP服务器成功！" + "--> fileName=" + fileName);
            reply = ftp.getReplyCode();
            log.info("[downloadFile] ftp.getReplyCode():" + reply + "(注:230:用户登录 530:未登录)");
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return flag;
            }
            log.info("[downloadFile] " + " 文件名:" + fileName + ", 远程存放路径：" + remotePath);
            // 转移到FTP服务器目录
            ftp.changeWorkingDirectory(remotePath);
            String path = ftp.printWorkingDirectory();
            log.info("[downloadFile] 转移到目录:" + path);
            FTPFile[] fs = ftp.listFiles();
            log.info("[downloadFile] 文件个数:" + fs.length);
            for (int i = 0; i < fs.length; i++) {
                FTPFile ff = fs[i];
                if (!".".equals(ff.getName()) && !"..".equals(ff.getName())
                        && ff.getName().equals(fileName)) {
                    File localFile = new File(localPath + File.separator + ff.getName());
                    // 如果文件已存在则不重复下载
                    if (!localFile.exists()) {
                        log.info("[downloadFile] 文件不存在，开始下载！" + "--> fileName=" + fileName);
                        OutputStream is = null;
                        try {
                            is = new FileOutputStream(localFile);
                            // 注意此处retrieveFile的第一个参数由GBK转为ISO-8859-1编码。否则下载后的文件内容为空。
                            // 原因可能是由于aix系统默认的编码为ISO-8859-1
                            ftp.retrieveFile(new String(ff.getName().getBytes(ENCODING_GBK), ENCODING_ISO), is);
                        } finally {
                            if (is != null) {
                                is.close();
                            }
                        }
                    }
                    flag = true;
                }
            }
            log.info("[downloadFile] end:" + "fileName = " + fileName + DateUtils.formatDateTime(new Date()));
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (Exception ioe) {
                    ioe.printStackTrace();
                    throw ioe;
                }
            }
        }
        return flag;
    }

    /**
     * Get file from ftp server into given output stream
     *
     * @param ftpFileName file name on ftp server
     * @param out         OutputStream
     * @throws IOException
     */
    public void retrieveFile(String ftpFileName, OutputStream out) throws IOException {
        try {
            // Get file info.
            FTPFile[] fileInfoArray = ftp.listFiles(ftpFileName);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File '" + ftpFileName + "' was not found on FTP server.");
            }

            // Check file size.
            FTPFile fileInfo = fileInfoArray[0];
            long size = fileInfo.getSize();
            if (size > Integer.MAX_VALUE) {
                throw new IOException("File '" + ftpFileName + "' is too large.");
            }

            // Download file.
            if (!ftp.retrieveFile(ftpFileName, out)) {
                throw new IOException("Error loading file '" + ftpFileName + "' from FTP server. Check FTP permissions and path.");
            }

            out.flush();

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    /**
     * Put file on ftp server from given input stream
     *
     * @param ftpFileName file name on ftp server
     * @param in          InputStream
     * @throws IOException
     */
    public void storeFile(String ftpFileName, InputStream in) throws IOException {
        try {
            if (!ftp.storeFile(ftpFileName, in)) {
                throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
            }
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * 修改名称
     *
     * @param from
     * @param to
     * @throws IOException
     */
    public boolean rename(String from, String to) throws IOException {
        return ftp.rename(from, to);
    }

    /**
     * Delete the file from the FTP server.
     *
     * @param ftpFileName server file name (with absolute path)
     * @throws IOException on I/O errors
     */
    public void deleteFile(String ftpFileName) throws IOException {
        if (!ftp.deleteFile(ftpFileName)) {
            throw new IOException("Can't remove file '" + ftpFileName + "' from FTP server.");
        }
    }

    /**
     * Upload the file to the FTP server.
     *
     * @param ftpFileName server file name (with absolute path)
     * @param localFile   local file to upload
     * @throws IOException on I/O errors
     */
    public void upload(String ftpFileName, File localFile) throws IOException {
        // File check.
        if (!localFile.exists()) {
            throw new IOException("Can't upload '" + localFile.getAbsolutePath() + "'. This file doesn't exist.");
        }

        // Upload.
        InputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(localFile));
            if (!ftp.storeFile(ftpFileName, in)) {
                throw new IOException("Can't upload file '" + ftpFileName + "' to FTP server. Check FTP permissions and path.");
            }

        } finally {
            try {
                in.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * 上传目录（会覆盖)
     *
     * @param remotePath 远程目录 /home/test/a
     * @param localPath  本地目录 D:/test/a
     * @throws IOException
     */
    public void uploadDir(String remotePath, String localPath) throws IOException {
        File file = new File(localPath);
        if (file.exists()) {
            if (!ftp.changeWorkingDirectory(remotePath)) {
                // 创建成功返回true，失败（已存在）返回false
                ftp.makeDirectory(remotePath);
                // 切换成返回true，失败（不存在）返回false
                ftp.changeWorkingDirectory(remotePath);
                String path = ftp.printWorkingDirectory();
                log.info("[uploadDir] 转移到目录:" + path);
            }
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory() && !".".equals(f.getName()) && !"..".equals(f.getName())) {
                        uploadDir(remotePath + "/" + f.getName(), f.getPath());
                    } else if (f.isFile()) {
                        upload(remotePath + "/" + f.getName(), f);
                    }
                }
            }
        }
    }

    /**
     * Download the file from the FTP server.
     *
     * @param ftpFileName server file name (with absolute path)
     * @param localFile   local file to download into
     * @throws IOException on I/O errors
     */
    public void download(String ftpFileName, File localFile) throws IOException {
        // Download.
        OutputStream out = null;
        try {
            // Get file info.
            FTPFile[] fileInfoArray = ftp.listFiles(ftpFileName);
            if (fileInfoArray == null || fileInfoArray.length == 0) {
                throw new FileNotFoundException("File " + ftpFileName + " was not found on FTP server.");
            }

            // Check file size.
            FTPFile fileInfo = fileInfoArray[0];
            long size = fileInfo.getSize();
            if (size > Integer.MAX_VALUE) {
                throw new IOException("File " + ftpFileName + " is too large.");
            }

            // Download file.
            out = new BufferedOutputStream(new FileOutputStream(localFile));
            if (!ftp.retrieveFile(ftpFileName, out)) {
                throw new IOException("Error loading file " + ftpFileName + " from FTP server. Check FTP permissions and path.");
            }
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            log.error("FTP下载异常!" + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    log.error("关闭输出流异常!" + ex.getMessage());
                }
            }
        }
    }

    /**
     * 下载目录（会覆盖)
     *
     * @param remotePath 远程目录 /home/test/a
     * @param localPath  本地目录 D:/test/a
     * @return
     * @throws IOException
     */
    public void downloadDir(String remotePath, String localPath) throws IOException {
        File file = new File(localPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        FTPFile[] ftpFiles = ftp.listFiles(remotePath);
        for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            if (ftpFile.isDirectory() && !".".equals(ftpFile.getName()) && !"..".equals(ftpFile.getName())) {
                downloadDir(remotePath + "/" + ftpFile.getName(), localPath + "/" + ftpFile.getName());
            } else {
                download(remotePath + "/" + ftpFile.getName(), new File(localPath + "/" + ftpFile.getName()));
            }
        }
    }

    /**
     * List the file name in the given FTP directory.
     *
     * @param filePath absolute path on the server
     * @return files relative names list
     * @throws IOException on I/O errors
     */
    public List<String> listFileNames(String filePath) throws IOException {
        List<String> fileList = new ArrayList<String>();

        FTPFile[] ftpFiles = ftp.listFiles(filePath);
        for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
            if (ftpFile.isFile()) {
                fileList.add(ftpFile.getName());
            }
        }

        return fileList;
    }

    /**
     * List the files in the given FTP directory.
     *
     * @param filePath directory
     * @return list
     * @throws IOException
     */
    public List<FTPFile> listFiles(String filePath) throws IOException {
        List<FTPFile> fileList = new ArrayList<FTPFile>();

        FTPFile[] ftpFiles = ftp.listFiles(filePath);
        for (int i = 0; ftpFiles != null && i < ftpFiles.length; i++) {
            FTPFile ftpFile = ftpFiles[i];
//            FfpFileInfo fi = new FfpFileInfo();
//            fi.setName(ftpFile.getName());
//            fi.setSize(ftpFile.getSize());
//            fi.setTimestamp(ftpFile.getTimestamp());
//            fi.setType(ftpFile.isDirectory());
            fileList.add(ftpFile);
        }

        return fileList;
    }


    /**
     * Send an FTP Server site specific command
     *
     * @param args site command arguments
     * @throws IOException on I/O errors
     */
    public void sendSiteCommand(String args) throws IOException {
        if (ftp.isConnected()) {
            try {
                ftp.sendSiteCommand(args);
            } catch (IOException ex) {
            }
        }
    }

    /**
     * Get current directory on ftp server
     *
     * @return current directory
     */
    public String printWorkingDirectory() {
        if (!ftp.isConnected()) {
            return "";
        }

        try {
            return ftp.printWorkingDirectory();
        } catch (IOException e) {
        }

        return "";
    }

    /**
     * Set working directory on ftp server
     *
     * @param dir new working directory
     * @return true, if working directory changed
     */
    public boolean changeWorkingDirectory(String dir) {
        if (!ftp.isConnected()) {
            return false;
        }

        try {
            return ftp.changeWorkingDirectory(dir);
        } catch (IOException e) {
        }

        return false;
    }

    /**
     * Change working directory on ftp server to parent directory
     *
     * @return true, if working directory changed
     */
    public boolean changeToParentDirectory() {
        if (!ftp.isConnected()) {
            return false;
        }

        try {
            return ftp.changeToParentDirectory();
        } catch (IOException e) {
        }

        return false;
    }

    /**
     * Get parent directory name on ftp server
     *
     * @return parent directory
     */
    public String printParentDirectory() {
        if (!ftp.isConnected()) {
            return "";
        }

        String w = printWorkingDirectory();
        changeToParentDirectory();
        String p = printWorkingDirectory();
        changeWorkingDirectory(w);

        return p;
    }

    /**
     * 创建目录
     *
     * @param pathname
     * @throws IOException
     */
    public boolean makeDirectory(String pathname) throws IOException {
        return ftp.makeDirectory(pathname);
    }

    /**
     * 设置超时时间
     *
     * @param defaultTimeoutSecond
     * @param connectTimeoutSecond
     * @param dataTimeoutSecond
     */
    public void setTimeOut(int defaultTimeoutSecond, int connectTimeoutSecond, int dataTimeoutSecond) {
        try {
            ftp.setDefaultTimeout(defaultTimeoutSecond * 1000);
            //ftp.setConnectTimeout(connectTimeoutSecond * 1000); //commons-net-3.5.jar
            // commons-net-1.4.1.jar 连接后才能设置
            ftp.setSoTimeout(connectTimeoutSecond * 1000);
            ftp.setDataTimeout(dataTimeoutSecond * 1000);
        } catch (SocketException e) {
            log.error("setTimeout Exception:", e);
        }
    }

    public FTPClient getFTPClient() {
        return ftp;
    }

    public void setControlEncoding(String charset) {
        ftp.setControlEncoding(charset);
    }

    public void setFileType(int fileType) throws IOException {
        ftp.setFileType(fileType);
    }

    /**
     * Connect to FTP server.
     *
     * @param host     FTP server address or name
     * @param port     FTP server port
     * @param user     user name
     * @param password user password
     * @throws IOException on I/O errors
     */
    public FTPClient connect(String host, int port, String user, String password) throws IOException {
        // Connect to server.
        try {
            ftp.connect(host, port);
        } catch (UnknownHostException ex) {
            throw new IOException("Can't find FTP server '" + host + "'");
        }

        // Check rsponse after connection attempt.
        int reply = ftp.getReplyCode();
        if (!FTPReply.isPositiveCompletion(reply)) {
            disconnect();
            throw new IOException("Can't connect to server '" + host + "'");
        }

        if ("".equals(user)) {
            user = "anonymous";
        }

        // Login.
        if (!ftp.login(user, password)) {
            disconnect();
            throw new IOException("Can't login to server '" + host + "'");
        }

        // Set data transfer mode.
        ftp.setFileType(FTP.BINARY_FILE_TYPE);
        //ftp.setFileType(FTP.ASCII_FILE_TYPE);

        // Use passive mode to pass firewalls.
        ftp.enterLocalPassiveMode();
        return ftp;
    }

    /**
     * Test connection to ftp server
     *
     * @return true, if connected
     */
    public boolean isConnected() {
        return ftp.isConnected();
    }

    /**
     * Disconnect from the FTP server
     *
     * @throws IOException on I/O errors
     */
    public void disconnect() throws IOException {

        if (ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * main test
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        FtpUtil ftpUtil = new FtpUtil("UTF-8");
        ftpUtil.connect("10.10.10.212", 21, "weblogic", "weblogic");
        // ftpUtil.setTimeOut(60, 60, 60);
        ftpUtil.upload("/home/weblogic/文件1.txt", new File("E:/image/FTPClient/FTPClient测试/文件1.txt"));
        ftpUtil.download("/home/weblogic/文件1.txt", new File("E:/image/FTPClient/FTPClient测试/文件1.txt"));
        ftpUtil.uploadDir("/home/weblogic/FTPClient测试", "E:/image/FTPClient/FTPClient测试");
        ftpUtil.downloadDir("/home/weblogic/FTPClient测试", "E:/image/FTPClient/FTPClient测试");
        //自动增长
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ftpUtil.retrieveFile("/home/testuser/文件1.txt", bos);
        System.out.println(bos.size());
        String contentStr = new String(bos.toByteArray(), "GBK");
        System.out.println(contentStr);
        ftpUtil.disconnect();
    }

}