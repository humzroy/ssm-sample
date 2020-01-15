package com.zhen.util;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.zhen.util.sftp.MyUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA
 * <p>
 * Description：SFTP工具类
 * Author：wuhengzhen
 * Date：2018-10-23
 * Time：10:57
 */
public class SftpUtil {
    private static final Logger logger = LoggerFactory.getLogger(SftpUtil.class);

    private static ChannelSftp sftp;
    private static SftpUtil instance = null;
    private final static String BUSIMG_FILENAME_SEPARATOR = "|";

    private SftpUtil() {

    }

    /**
     * 登录本地sftp
     *
     * @param host       ip
     * @param port       端口
     * @param userName   登录用户
     * @param passWord   登录密码
     * @param keyFile    秘钥文件
     * @param passphrase
     * @return
     */
    public static SftpUtil getInstance(String host, int port, String userName, String passWord, String keyFile, String passphrase) {
        if (instance == null) {
            instance = new SftpUtil();
            //获取连接
            sftp = instance.connect(host, port, userName, passWord, keyFile, passphrase);
        } else {
            //获取连接
            sftp = instance.connect(host, port, userName, passWord, keyFile, passphrase);
        }
        return instance;
    }

    /**
     * 连接sftp服务器
     *
     * @param host     主机
     * @param port     端口
     * @param userName 用户名
     * @param passWord 密码
     * @return
     */
    public ChannelSftp connect(String host, int port, String userName, String passWord, String keyFile, String passphrase) {
        ChannelSftp sftp = null;
        try {
            // 打开Session
            JSch jsch = new JSch();
            jsch.getSession(userName, host, port);
            if (StringUtil.isNotEmpty(keyFile)) {
                jsch.addIdentity(keyFile);
            }
            Session sshSession = jsch.getSession(userName, host, port);
            if (StringUtil.isNotEmpty(passphrase)) {
                UserInfo userInfo = new MyUserInfo(passphrase);
                sshSession.setUserInfo(userInfo);
            }
            if (StringUtil.isNotEmpty(passWord)) {
                sshSession.setPassword(passWord);
            }
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            logger.info("SFTP服务器【{}:{}】登录成功...", host, port);
            // 打开Channel
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            logger.info("成功连接SFTP服务器【{}:{}】...", host, port);
        } catch (Exception e) {
            logger.error("连接sftp服务器失败...异常信息：{}", e.getMessage());
        }
        return sftp;
    }

    /**
     * description: （对外调用）上传多个或者一个文件，多个文件以|分割
     * param: [directory, localPath, uploadFile] 上传的远端目录，本地要上传的文件夹地址，本地要上传的文件名称（多个一个|分割）
     * return: boolean
     * author: wuhengzhen
     * date: 2018/8/31 9:48
     */
    public boolean uploadFile(String basePath, String directory, String localPath, String uploadFile) throws Exception {
        try {
            if (directory.endsWith("/") || directory.endsWith("\\")) {
                directory = directory.substring(0, directory.length() - 1);
            }
            // 创建多级目录结构，如果上传的目录不存在，则先创建目录再上传
            //  makeAndCdDirectory(directory);
            makeAndcdDirectory(basePath, directory);
            // 多文件拆分
            if (uploadFile.contains(SftpUtil.BUSIMG_FILENAME_SEPARATOR)) {
                String[] uploadFiles = StringUtil.splitPreserveAllTokens(uploadFile, SftpUtil.BUSIMG_FILENAME_SEPARATOR);
                for (String file : uploadFiles) {
                    upload(localPath + file);
                }
            } else {
                upload(localPath + uploadFile);
            }
            // 上传完成关闭sftp服务器
            disconnect();
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 上传文件(对内引用)
     *
     * @param uploadFile 要上传的文件
     */
    private void upload(String uploadFile) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            File file = new File(uploadFile);
            fileInputStream = new FileInputStream(file);
            sftp.put(fileInputStream, file.getName());
            fileInputStream.close();
            logger.info("文件【{}】成功上传到SFTP服务器！", uploadFile);
        } catch (IOException e) {
            logger.error("文件【{}】上传IO异常！异常信息：{}", uploadFile, e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("文件【{}】上传异常！异常信息：{}", uploadFile, e.getMessage());
            throw e;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                logger.error("SFTP对象关闭异常！" + e);
            }
        }
    }

    /**
     * description: 在远端切换到上传的目录结构，如果不存在则创建远端目录结构
     * param: [basePath, directory] 远端基础路径，远端目标路径
     * return: void
     * author: wuhengzhen
     * date: 2018/8/31 10:58
     */
    private void makeAndcdDirectory(String basePath, String directory) throws SftpException {
        try {
            sftp.cd(basePath);
            sftp.cd(directory);
        } catch (SftpException e) {
            //目录不存在，则创建文件夹
            String[] dirs = directory.split("/");
            String tempPath = basePath;
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)) {
                    continue;
                }
                tempPath += "/" + dir;
                try {
                    sftp.cd(tempPath);
                } catch (SftpException ex) {
                    sftp.mkdir(tempPath);
                    sftp.cd(tempPath);
                }
            }
        }
    }

    /**
     * 在当前工作目录下建立多级目录结构
     *
     * @param directory
     * @throws Exception
     */
    private void makeAndCdDirectory(String directory) throws Exception {
        try {
            StringTokenizer toke = new StringTokenizer(directory, File.separator);
            while (toke.hasMoreElements()) {
                String currentDirectory = (String) toke.nextElement();
                try {
                    sftp.cd(currentDirectory);
                } catch (SftpException e1) {
                    try {
                        sftp.mkdir(currentDirectory);
                        sftp.cd(currentDirectory);
                    } catch (SftpException e) {
                        throw new Exception(e);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("创建远程目录结构异常！", e);
            throw e;
        }
    }

    /**
     * 下载文件(对外调用)
     *
     * @param downLoadFilePath  远程文件目录
     * @param downloadFile      下载的文件，如果是多个文件，以“|”分隔
     * @param localSaveFilePath 本地保存路径
     */
    public boolean downLoadFile(String downLoadFilePath, String downloadFile, String localSaveFilePath) throws Exception {
        try {
            // 如果本地文件夹不存在，则先创建文件夹
            if (!new File(localSaveFilePath).isDirectory()) {
                makeMultiDirectory(localSaveFilePath);
                if (!new File(localSaveFilePath).isDirectory()) {
                    // 创建文件夹失败，抛出异常
                    logger.error("创建本地文件夹【{}】失败！", localSaveFilePath);
                    throw new Exception("创建本地文件夹【" + localSaveFilePath + "】失败！");
                }
            }
            // 进入下载目录
            sftp.cd(downLoadFilePath);
            // 如果是多个下载文件，需要以“|”分隔
            if (downloadFile.contains(SftpUtil.BUSIMG_FILENAME_SEPARATOR)) {
                String[] downloadFiles = StringUtil.splitPreserveAllTokens(downloadFile, SftpUtil.BUSIMG_FILENAME_SEPARATOR);
                for (String file : downloadFiles) {
                    downLoad(file, localSaveFilePath + File.separator + file);
                }
            } else if ("*".equalsIgnoreCase(downloadFile)) {
                String[] fileNames = getAllFilesInPath();
                for (String file : fileNames) {
                    if ("..".equals(file.trim()) || ".".equals(file.trim())) {
                        // “..”和“.”不下载
                        continue;
                    }
                    downLoad(file, localSaveFilePath + File.separator + file);
                }
            } else {
                downLoad(downloadFile, localSaveFilePath + File.separator + downloadFile);
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception();
        } finally {
            disconnect();
        }
    }

    /**
     * 下载文件(对内引用)
     *
     * @param downLoadFile      下载的文件
     * @param localSaveFilePath 存在本地的路径
     */
    private void downLoad(String downLoadFile, String localSaveFilePath) throws Exception {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File(localSaveFilePath);
            fileOutputStream = new FileOutputStream(file);
            sftp.get(downLoadFile, fileOutputStream);
            fileOutputStream.close();
            logger.info("文件【{}】成功从SFTP服务器下载！", downLoadFile);
        } catch (IOException e) {
            logger.error("下载文件【{}】IO异常！", downLoadFile);
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("下载文件【{}】异常！", downLoadFile);
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                logger.error("SFTP对象关闭异常！" + e);
            }
        }
    }

    /**
     * 批量下载文件
     *
     * @param remotePath  远程文件目录
     * @param localPath   本地保存目录
     * @param filepPrefix 文件前缀
     * @param fileSuffix  文件后缀
     * @param del         下载成功后是否删除sftp上的文件
     * @return 批量下载成功的文件名集合
     */
    public List<String> batchDownLoadFile(String remotePath, String localPath, String filepPrefix, String fileSuffix, boolean del) {
        List<String> filenames = new ArrayList<>();
        String dateStr = DateUtil.format(new Date(), DateUtil.PATTERN_STANDARD08W);
        localPath = localPath + dateStr + File.separator;
        try {
            List<String> files = listFiles(remotePath);
            if (files.size() > 0) {
                // 有文件
                logger.info("本次处理文件个数不为零,开始下载...fileSize=" + files.size());
                for (String fileName : files) {
                    boolean flag;
                    String localFileName = localPath + fileName;
                    filepPrefix = filepPrefix == null ? "" : filepPrefix.trim();
                    fileSuffix = fileSuffix == null ? "" : fileSuffix.trim();
                    // 三种情况
                    if (filepPrefix.length() > 0) {
                        if (fileName.startsWith(filepPrefix) && fileName.endsWith(fileSuffix)) {
                            flag = downloadFile(remotePath, fileName, localPath, fileName);
                            if (flag) {
                                filenames.add(localFileName);
                                if (del) {
                                    delete(remotePath, fileName);
                                }
                            }
                        }
                    } else if (fileSuffix.length() > 0 && StringUtil.isBlank(filepPrefix)) {
                        if (fileName.endsWith(fileSuffix)) {
                            flag = downloadFile(remotePath, fileName, localPath, fileName);
                            if (flag) {
                                filenames.add(localFileName);
                                if (del) {
                                    delete(remotePath, fileName);
                                }
                            }
                        }
                    } else {
                        flag = downloadFile(remotePath, fileName, localPath, fileName);
                        if (flag) {
                            filenames.add(localFileName);
                            if (del) {
                                delete(remotePath, fileName);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }

        return filenames;
    }

    /**
     * 下载单个文件
     *
     * @param remotePath：远程下载目录(以路径符号结束)
     * @param remoteFileName：下载文件名
     * @param localPath：本地保存目录(以路径符号结束)
     * @param localFileName：保存文件名
     * @return
     */
    public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        FileOutputStream fieloutput = null;
        try {
            // 如果本地文件夹不存在，则先创建文件夹
            if (!new File(localPath).isDirectory()) {
                makeMultiDirectory(localPath);
                if (!new File(localPath).isDirectory()) {
                    // 创建文件夹失败，抛出异常
                    logger.error("创建本地文件夹【{}】失败！", localPath);
                    return false;
                }
            }
            File file = new File(localPath + localFileName);
            fieloutput = new FileOutputStream(file);
            sftp.get(remotePath + remoteFileName, fieloutput);
            if (logger.isInfoEnabled()) {
                logger.info("download success from sftp, fileName:{}", remoteFileName);
            }
            return true;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage() + "download file is Fail File not Found");
        } catch (SftpException e) {
            logger.error(e.getMessage() + "download file is Fail sftp server error");
        } finally {
            if (null != fieloutput) {
                try {
                    fieloutput.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return false;
    }

    /**
     * 在指定位置（本地）建立多级目录结构
     *
     * @param localSaveFilePath
     */
    private void makeMultiDirectory(String localSaveFilePath) {
        // 检查根目录是否存在
        if (!(new File(localSaveFilePath).isDirectory())) {
            // 依次建立各级目录
            String[] dirStrArray = StringUtil.splitPreserveAllTokens(localSaveFilePath, File.separator);
            StringBuilder dirStr = new StringBuilder(dirStrArray[0]);
            for (int i = 0; i < dirStrArray.length - 1; i++) {
                dirStr.append(File.separator);
                dirStr.append(dirStrArray[i + 1]);
                dirStr.append(File.separator);
                if (!(new File(dirStr.toString()).isDirectory())) {
                    new File(dirStr.toString()).mkdirs();
                }
            }
        }
    }

    /**
     * 路径下的全部文件
     *
     * @return
     */
    private String[] getAllFilesInPath() {
        String[] names = null;
        String curPath = null;
        try {
            curPath = sftp.pwd();

            Vector files = sftp.ls(".");
            logger.info("总共有{}个文件.", files.size());
            for (int i = 0; i < files.size(); i++) {
                LsEntry entry = (LsEntry) files.get(i);
                if ("..".equals(entry.getFilename().trim()) || ".".equals(entry.getFilename().trim())) {
                    files.removeElementAt(i);
                }
                logger.info("文件名称为:{}", entry.getFilename());
            }

            names = new String[files.size()];
            for (int i = 0; i < files.size(); i++) {
                LsEntry entry = (LsEntry) files.get(i);
                names[i] = entry.getFilename();
            }
        } catch (Exception e) {
            logger.error("获取路径[" + curPath + "]下的全部文件出现IO异常" + e);
        }

        return names;
    }

    /**
     * 获得目录下的所有文件名
     *
     * @param directory
     * @return
     * @throws Exception List<String>
     * @Title: listFiles
     * @author
     * @date
     */
    public List<String> listFiles(String directory) throws Exception {
        Vector fileList;
        List<String> fileNameList = new ArrayList<>();
        try {
            fileList = sftp.ls(directory);
        } catch (Exception e) {
            logger.error("Error：SFTP No such file!没有该文件夹！");
            return fileNameList;
        }
        try {
            for (Object aFileList : fileList) {
                String fileName = ((LsEntry) aFileList).getFilename();
                if (".".equals(fileName) || "..".equals(fileName)) {
                    continue;
                }
                fileNameList.add(fileName);
            }
            return fileNameList;
        } catch (Exception e) {
            logger.error("SFTP获取文件名失败！");
            throw new Exception(e);
        }
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
            logger.info("删除文件【{}】成功！", deleteFile);
        } catch (Exception e) {
            logger.info("删除文件【{}】失败！异常信息：{}", deleteFile, e.getMessage());
        }
    }

    /**
     * 删除文件夹
     *
     * @param deleteFolder 要删除的文件夹
     */
    public void deleteFolder(String deleteFolder) {
        try {
            sftp.rmdir(deleteFolder);
            logger.info("删除文件夹【{}】成功！", deleteFolder);
        } catch (Exception e) {
            logger.info("删除文件夹【{}】失败！异常信息：{}", deleteFolder, e.getMessage());
        }
    }

    /**
     * 关闭sftp服务器
     */
    public void disconnect() {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                }
                Session session = sftp.getSession();
                if (session != null) {
                    if (session.isConnected()) {
                        session.disconnect();
                    }
                }
            }
            logger.info("关闭sftp成功！");
        } catch (JSchException e) {
            logger.error("关闭sftp异常！{}", e.getMessage());
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory 要列出的目录
     * @return
     * @throws SftpException
     */
    public static Vector<?> listFiles1(String directory) throws SftpException {
        return sftp.ls(directory);
    }

}
